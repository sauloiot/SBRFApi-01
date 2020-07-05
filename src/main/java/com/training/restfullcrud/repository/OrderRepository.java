package com.training.restfullcrud.repository;

import com.training.restfullcrud.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}