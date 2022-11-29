package com.example.internet_magazin.repository;

import com.example.internet_magazin.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer>,
        JpaSpecificationExecutor<Product> {


    @Query(value = "SELECT * FROM products where deleted_at is null and visible is not null", nativeQuery = true)
    Page<Product> findAll(Pageable pageable);

    @Query ("SELECT count(id) FROM products where deletedAt is null")
    Long productCount();

}
