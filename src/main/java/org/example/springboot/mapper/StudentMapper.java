package org.example.springboot.mapper;
import org.apache.ibatis.annotations.Param;
import org.example.springboot.entity.Student;

public interface StudentMapper {
    Integer post(@Param("student") Student student);
}
