package com.ewha.heydongdong.repository;

import com.ewha.heydongdong.domain.Order;
import com.ewha.heydongdong.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findByUser(User user);
}
