package com.lucashzs.api.storage.entities;

import com.lucashzs.api.storage.dtos.ProductDto;
import jakarta.persistence.*;

@Table(name = Product.TABLE_NAME)
@Entity
public class Product {

    public static final String TABLE_NAME = "products";

    @ManyToOne
    private User user;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String sector;

    private Long amount;

    public Product() {
    }

    public Product(ProductDto productDto) {
        this.name = productDto.name();
        this.sector = productDto.sector();
        this.amount = productDto.amount();
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSector() {
        return sector;
    }

    public void setSector(String sector) {
        this.sector = sector;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }
}
