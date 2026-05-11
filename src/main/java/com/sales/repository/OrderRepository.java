package com.sales.repository;

import com.sales.entity.Order;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import java.util.List;

@ApplicationScoped
public class OrderRepository implements PanacheRepository<Order> {

    /**
     * Lấy danh sách đơn hàng của một khách hàng cụ thể
     */
    public List<Order> findByCustomerId(Long customerId) {
        return list("customer.id", customerId);
    }

    /**
     * Truy vấn top 3 sản phẩm bán chạy nhất
     * Lưu ý: Sử dụng EntityManager để viết HQL/JPQL phức tạp hơn
     */
    public List<Object[]> getTopSellingProducts() {
        return getEntityManager().createQuery(
                        "SELECT i.product.name, SUM(i.quantity) as totalQty " +
                                "FROM OrderItem i " +
                                "GROUP BY i.product.name " +
                                "ORDER BY totalQty DESC", Object[].class)
                .setMaxResults(3)
                .getResultList();
    }
}