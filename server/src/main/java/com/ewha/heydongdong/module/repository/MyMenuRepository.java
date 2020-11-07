package com.ewha.heydongdong.module.repository;

import com.ewha.heydongdong.module.model.domain.MenuInOrder;
import com.ewha.heydongdong.module.model.domain.MyMenu;
import com.ewha.heydongdong.module.model.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MyMenuRepository extends JpaRepository<MyMenu, Long> {
    List<MyMenu> findByUser(User user);

    void deleteById(Long myMenuId);

    Optional<MyMenu> findByUserAndMenuInOrder(User build, MenuInOrder build1);
}
