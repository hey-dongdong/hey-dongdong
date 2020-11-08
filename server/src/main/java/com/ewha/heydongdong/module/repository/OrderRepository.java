package com.ewha.heydongdong.module.repository;

import com.ewha.heydongdong.module.model.domain.Order;
import com.ewha.heydongdong.module.model.domain.Store;
import com.ewha.heydongdong.module.model.domain.User;
import com.ewha.heydongdong.module.model.domain.datatype.Progress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findByUser(User user);
    List<Order> findByUserAndProgress(User user, Progress progress);
    List<Order> findByStoreAndProgress(Store store, Progress progress);

}
