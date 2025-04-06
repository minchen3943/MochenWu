package com.mochenwu.data.controller;

import com.mochenwu.data.service.DataService;
import com.mochenwu.general.response.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 数据统计控制器
 *
 * @author 瞑尘
 * @date 2025-02-21
 */
@RestController
@RequestMapping("/api/data/*")
public class DataController {

    /**
     * 数据统计服务
     */
    private final DataService dataService;

    /**
     * 构造函数注入服务
     *
     * @param dataService 数据统计服务实例
     */
    @Autowired
    public DataController(DataService dataService) {
        this.dataService = dataService;
    }

    /**
     * 增加访问人数统计
     *
     * @return 包含最新访问人数的响应实体
     */
    @PutMapping("/visitor/add")
    public ResponseEntity<ApiResponse<Integer>> addVisitorCount() {
        return ResponseEntity.ok(
                new ApiResponse<>(200, "添加访问人数成功", dataService.addVisitorCount())
        );
    }

    /**
     * 获取当前访问人数
     *
     * @return 包含当前访问人数的响应实体
     */
    @GetMapping("/visitor/get")
    public ResponseEntity<ApiResponse<Integer>> getVisitorCount() {
        return ResponseEntity.ok(
                new ApiResponse<>(200, "查询访问人数成功", dataService.getVisitorCount())
        );
    }

    /**
     * 增加喜欢人数统计
     *
     * @return 包含最新喜欢人数的响应实体
     */
    @PutMapping("/like/add")
    public ResponseEntity<ApiResponse<Integer>> addLikeCount() {
        return ResponseEntity.ok(
                new ApiResponse<>(200, "添加喜欢人数成功", dataService.addLikeCount())
        );
    }

    /**
     * 获取当前喜欢人数
     *
     * @return 包含当前喜欢人数的响应实体
     */
    @GetMapping("/like/get")
    public ResponseEntity<ApiResponse<Integer>> getLikeCount() {
        return ResponseEntity.ok(
                new ApiResponse<>(200, "查询喜欢人数成功", dataService.getLikeCount())
        );
    }
}