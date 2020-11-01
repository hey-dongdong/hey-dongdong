package com.ewha.heydongdong.module.service;

import com.ewha.heydongdong.module.repository.MenuInOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MenuInOrderService {

    @Autowired
    private MenuInOrderRepository menuInOrderRepository;
}
