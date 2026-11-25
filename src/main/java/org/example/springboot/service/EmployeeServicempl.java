package org.example.springboot.service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.example.springboot.entity.Employee;
import org.example.springboot.mapper.EmployeeMapper;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;
import java.lang.Override;


@Service
public class EmployeeServicempl implements  EmployeeService{
    @Autowired
    private EmployeeMapper employeeMapper;
    @Override
    public PageInfo<Employee> getEmpPage(Integer pageNum, Integer pageSize){
        PageHelper.startPage(pageNum,pageSize);
        List<Employee> employeeList=employeeMapper.selectAll();
        return new PageInfo<>(employeeList);

    }



}
