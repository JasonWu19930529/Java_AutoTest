package com.example.demo.mapper;
import com.example.demo.entity.Student;
import com.example.demo.entity.StudentJoin;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.ArrayList;
import java.util.Map;

@Mapper
public interface UserMapper {

     Student selectUserList(@Param("name") String name);

     ArrayList<StudentJoin> selectStudentJoin();

     void updateStudent(Map<String, Object> map);

}
