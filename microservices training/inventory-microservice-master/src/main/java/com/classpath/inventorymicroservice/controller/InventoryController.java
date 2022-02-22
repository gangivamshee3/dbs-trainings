package com.classpath.inventorymicroservice.controller;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/inventory")
@Slf4j
public class InventoryController {

    private static int counter = 1000;
    @PostMapping
    public Integer updateQty(){
      log.info("Inside the update qty method of Inventory controller");
      return --counter;
    }

    @GetMapping
    public Integer counter(){
        log.info("Inside the counter method of Inventory controller");
        return counter;
    }
}