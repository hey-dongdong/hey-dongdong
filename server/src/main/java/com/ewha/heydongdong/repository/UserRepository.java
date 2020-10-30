package com.ewha.heydongdong.repository;

import com.ewha.heydongdong.model.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

    List<User> findByUserId(String userId);

    List<User> findByEmail(String email);
}
