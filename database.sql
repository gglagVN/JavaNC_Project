CREATE DATABASE sales_management CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

USE sales_management;
SELECT * FROM Product;
SELECT * FROM Customer;
SELECT * FROM orders;
SELECT * FROM order_items;
USE sales_management;

-- Xóa dữ liệu cũ (nếu muốn làm sạch bảng trước khi thêm)
-- SET FOREIGN_KEY_CHECKS = 0;
-- TRUNCATE TABLE Product;
-- SET FOREIGN_KEY_CHECKS = 1;

-- Thêm các sản phẩm công nghệ mẫu
INSERT INTO Product (name, price, stockQuantity) VALUES 
('Laptop MacBook Air M2', 30000000, 15),
('iPhone 15 Pro Max', 35000000, 20),
('Tai nghe AirPods Pro', 5500000, 50),
('Chuột Magic Mouse', 2200000, 30),
('Bàn phím cơ AKKO', 1500000, 10),
('Màn hình Dell UltraSharp', 12000000, 5),
('Sạc dự phòng Anker', 800000, 100);

-- Kiểm tra lại kết quả
SELECT * FROM Product;

INSERT INTO Customer (name, phone, email) VALUES 
('Nguyễn Văn A', '0901234567', 'anv@gmail.com'),
('Trần Thị B', '0912345678', 'bttran@gmail.com');


SELECT * FROM User; 
SELECT * FROM Product; 
SELECT * FROM orders; 
