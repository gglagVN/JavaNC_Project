package com.sales.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;

@Entity
public class Customer extends PanacheEntityBase {
    @Id
    // Xóa dòng @GeneratedValue ở đây để có thể dùng chung ID với bảng User
    public Long id;

    public String name;
    public String phone;
    public String email;
}