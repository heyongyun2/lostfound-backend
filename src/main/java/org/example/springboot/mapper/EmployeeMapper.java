package org.example.springboot.mapper;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;
import org.example.springboot.entity.Employee;
import org.apache.ibatis.annotations.Select;




public interface EmployeeMapper {
    List<Employee>selectAll();
    @Select("select * from employee where id=#{id}")
    Employee selectbyId(Integer id);
    int updataByID(Employee employee);
    int addData(Employee employee);
    int delectdata(Integer id);
    List<Employee>search(@Param("name") String name);
    Employee login(@Param("name") String name ,@Param("id") Integer id);




}
