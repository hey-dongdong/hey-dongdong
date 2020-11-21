package com.ewha.heydongdong.module.repository;

import com.ewha.heydongdong.module.model.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

    List<User> findByUserId(String userId);
    List<User> findByEmail(String email);

}
