package com.example.security2.controller;

import com.example.security2.dto.ResultDto;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api")
@RestController
public class ApiController {

    @PostMapping("/test")
    public ResultDto test() {

        return new ResultDto(true, "test ok!", null);
    }
}
