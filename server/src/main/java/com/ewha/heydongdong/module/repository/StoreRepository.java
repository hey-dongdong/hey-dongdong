package com.ewha.heydongdong.module.repository;

import com.ewha.heydongdong.module.model.domain.Store;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StoreRepository extends JpaRepository<Store, Integer> {
}
