package com.ewha.heydongdong.repository;

import com.ewha.heydongdong.model.domain.MyMenu;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MyMenuRepository extends JpaRepository<MyMenu, Long> {
}
