package com.example.demo.controller;

import com.example.demo.entity.Student;
import com.example.demo.service.imp.StudentImp;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@Slf4j
@RequestMapping(value = "/RequestController01")
public class RequestController01 {

    @Autowired
    StudentImp studentImp;

    @GetMapping(value = "index01")
    @ResponseBody
    public Student index01(@RequestParam(value = "name") String name){
        return studentImp.selectUserList(name);
    }
}
