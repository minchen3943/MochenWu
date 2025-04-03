package com.mochenwu.data.controller;


import com.mochenwu.data.service.DataService;
import com.mochenwu.general.model.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 瞑尘
 **/

@RestController
@RequestMapping("/api/data/*")
public class DataController {

    private final DataService dataService;

    @Autowired
    public DataController(DataService dataService) {
        this.dataService = dataService;
    }

    @PutMapping("/visitor/add")
    public ResponseEntity<ApiResponse<Integer>> addVisitorCount() {
        return ResponseEntity.ok(new ApiResponse<>(200, "添加访问人数成功", dataService.addVisitorCount()));
    }

    @GetMapping("/visitor/get")
    public ResponseEntity<ApiResponse<Integer>> getVisitorCount() {
        return ResponseEntity.ok(new ApiResponse<>(200, "查询访问人数成功", dataService.getVisitorCount()));
    }

    @PutMapping("/like/add")
    public ResponseEntity<ApiResponse<Integer>> addLikeCount() {
        return ResponseEntity.ok(new ApiResponse<>(200, "添加喜欢人数成功", dataService.addLikeCount()));
    }

    @GetMapping("/like/get")
    public ResponseEntity<ApiResponse<Integer>> getLikeCount() {
        return ResponseEntity.ok(new ApiResponse<>(200, "查询喜欢人数成功", dataService.getLikeCount()));
    }
}
