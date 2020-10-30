package com.ewha.heydongdong.service;

import com.ewha.heydongdong.model.domain.Store;
import com.ewha.heydongdong.repository.StoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StoreService {

    @Autowired
    private StoreRepository storeRepository;

    public List<Store> getAllStores() {
        return storeRepository.findAll();
    }
}
