package com.ewha.heydongdong.repository;

import com.ewha.heydongdong.model.domain.Store;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StoreRepository extends JpaRepository<Store, Integer> {
}
