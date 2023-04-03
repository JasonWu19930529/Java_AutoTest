package com.example.demo.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class Student implements Serializable {

    String name;

    int age;

    String favorite;

}
