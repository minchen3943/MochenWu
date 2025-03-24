package com.mochenwu.data.controller;


import com.mochenwu.data.service.DataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 瞑尘
 **/

@RestController
@RequestMapping("/data/*")
public class DataController {

    private final DataService dataService;

    @Autowired
    public DataController(DataService dataService) {
        this.dataService = dataService;
    }

    @PutMapping("/add")
    public int add() {
        return dataService.addVisitorCount();
    }

    @GetMapping("/get")
    public int getVisitorCount() {
        return dataService.getVisitorCount();
    }
}
