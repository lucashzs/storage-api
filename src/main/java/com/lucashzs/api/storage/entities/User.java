package com.lucashzs.api.storage.entities;

import com.lucashzs.api.storage.dtos.RegisterUserDto;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;

import java.util.ArrayList;
import java.util.List;

@Table(name = User.TABLE_NAME)
@Entity
public class User {

    public static final String TABLE_NAME = "users";

    @OneToMany(mappedBy = "user")
    private List<Product> products = new ArrayList<>();

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    @Column(unique = true)
    private String email;

    @Size(min = 8)
    private String password;

    public User() {
    }

    public User(RegisterUserDto registerUserDto, String encryptPassword) {
        this.username = registerUserDto.username();
        this.email = registerUserDto.email();
        this.password = encryptPassword;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}