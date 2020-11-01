package com.ewha.heydongdong.module.repository;

import com.ewha.heydongdong.module.model.domain.Menu;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MenuRepository extends JpaRepository<Menu, Integer> {
}
