package com.ewha.heydongdong.repository;

import com.ewha.heydongdong.model.domain.Menu;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MenuRepository extends JpaRepository<Menu, Integer> {
}
