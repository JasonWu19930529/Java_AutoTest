package com.example.demo.service;

import com.example.demo.entity.Student;
import org.springframework.stereotype.Service;

@Service
public interface StudentFind {

    public Student selectUserList(String name);

}
