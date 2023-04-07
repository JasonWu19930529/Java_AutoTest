package com.example.demo.service.imp;

import com.example.demo.entity.Student;
import com.example.demo.mapper.UserMapper;
import com.example.demo.service.StudentFind;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StudentImp implements StudentFind {

    @Autowired
    UserMapper userMapper;

    public Student selectUserList(String name){
        return userMapper.selectUserList(name);
    }
}
