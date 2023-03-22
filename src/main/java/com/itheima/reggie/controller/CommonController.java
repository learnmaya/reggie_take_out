package com.itheima.reggie.controller;


import com.itheima.reggie.common.Result;
import com.itheima.reggie.service.CommonService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;

@RestController
@Slf4j
@RequestMapping("/common")
@Api(tags = "Common-related API")
public class CommonController {

    @Autowired
    private CommonService commonService;

    @PostMapping("/upload")
    public Result<String> upload(MultipartFile file) {
        log.info("Getting the file：{}", file.toString());
        return commonService.upload(file);
    }

    @GetMapping("/download")
    public void download(String name, HttpServletResponse response) {
        commonService.download(name, response);
    }
}
