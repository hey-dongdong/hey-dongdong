package com.ewha.heydongdong.module.service;

import com.ewha.heydongdong.module.repository.MenuRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MenuService {

    @Autowired
    private MenuRepository menuRepository;


}
