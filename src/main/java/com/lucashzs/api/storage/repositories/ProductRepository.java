package com.lucashzs.api.storage.repositories;

import com.lucashzs.api.storage.entities.Product;
import com.lucashzs.api.storage.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository <Product, Long> {

    Optional<Product> findByName(String name);
}
