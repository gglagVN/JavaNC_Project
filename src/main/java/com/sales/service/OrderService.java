package com.sales.service;

import com.sales.dto.OrderRequestDTO;
import com.sales.dto.StatisticsDTO;
import com.sales.entity.Customer;
import com.sales.entity.Order;
import com.sales.entity.OrderItem;
import com.sales.entity.Product;
import com.sales.repository.CustomerRepository;
import com.sales.repository.OrderRepository;
import com.sales.repository.ProductRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.WebApplicationException;
import java.time.LocalDateTime;
import java.util.List;

@ApplicationScoped
public class OrderService {

    @Inject OrderRepository orderRepository;
    @Inject ProductRepository productRepository;
    @Inject CustomerRepository customerRepository;

    @Transactional
    public Order createOrder(OrderRequestDTO dto) {
        // 1. Kiểm tra khách hàng
        Customer customer = customerRepository.findById(dto.customerId);
        if (customer == null) throw new WebApplicationException("Khách hàng không tồn tại", 404);

        Order order = new Order();
        order.customer = customer;
        order.orderDate = LocalDateTime.now();
        order.totalAmount = 0.0;

        // 2. Duyệt qua từng sản phẩm trong DTO
        for (var itemDto : dto.items) {
            Product product = productRepository.findById(itemDto.productId);

            if (product == null) throw new WebApplicationException("Sản phẩm ID " + itemDto.productId + " không tồn tại", 404);
            if (product.stockQuantity < itemDto.quantity) {
                throw new WebApplicationException("Sản phẩm " + product.name + " không đủ tồn kho", 400);
            }

            // 3. Tạo chi tiết đơn hàng
            OrderItem detail = new OrderItem();
            detail.product = product;
            detail.quantity = itemDto.quantity;
            detail.priceAtPurchase = product.price;

            // 4. Trừ số lượng tồn kho
            product.stockQuantity -= itemDto.quantity;

            // 5. Thêm vào đơn hàng và cộng dồn tổng tiền
            order.addItem(detail);
            order.totalAmount += (detail.priceAtPurchase * detail.quantity);
        }

        // 6. Lưu đơn hàng (Cascade sẽ tự lưu OrderItems)
        orderRepository.persist(order);
        return order;
    }

    public List<Order> findAll() {
        return orderRepository.listAll();
    }

    public Order findById(Long id) {
        return orderRepository.findById(id);
    }

    public StatisticsDTO getStatistics() {
        Double revenue = orderRepository.getEntityManager()
                .createQuery("SELECT SUM(o.totalAmount) FROM Order o", Double.class)
                .getSingleResult();

        Long count = orderRepository.count();

        StatisticsDTO stats = new StatisticsDTO();
        stats.totalRevenue = (revenue != null) ? revenue : 0.0;
        stats.totalOrders = count;
        // Phần topProducts bạn có thể query thêm bằng Group By nếu cần
        return stats;
    }
}