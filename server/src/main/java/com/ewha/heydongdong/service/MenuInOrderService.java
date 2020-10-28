package com.ewha.heydongdong.service;

import com.ewha.heydongdong.repository.MenuInOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MenuInOrderService {

    @Autowired
    private MenuInOrderRepository menuInOrderRepository;
}
