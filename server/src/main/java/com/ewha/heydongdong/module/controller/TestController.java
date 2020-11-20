package com.ewha.heydongdong.module.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api")
public class TestController {


    @GetMapping(value = "/get-test", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> getTest() {
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping(value = "/post-test", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> postTest() {
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
