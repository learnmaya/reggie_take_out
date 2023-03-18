package com.itheima.reggie.service;

import com.itheima.reggie.common.Result;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;

public interface CommonService {
    Result<String> upload(MultipartFile file);

    void download(String name, HttpServletResponse response);
}
