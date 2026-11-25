package org.example.springboot.service;
import com.github.pagehelper.PageInfo;
import org.example.springboot.entity.Employee;

public interface EmployeeService {
    PageInfo<Employee> getEmpPage(Integer pageNum,Integer pageSize);
}
