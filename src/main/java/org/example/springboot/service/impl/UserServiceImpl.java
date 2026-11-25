package org.example.springboot.service.impl;

import org.example.springboot.entity.User;
import org.example.springboot.mapper.UserMapper;
import org.example.springboot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;

    @Override
    public boolean register(User user) {
        // 1. 检查学号是否已存在
        User existingByStudentId = userMapper.findByStudentId(user.getStudentId());
        if (existingByStudentId != null) {
            return false;
        }
        if (user.getUserName() == null || user.getUserName().trim().isEmpty()) {
            user.setUserName(user.getStudentId());
        }

        // 2. 检查邮箱是否已存在
        User existingByEmail = userMapper.findByEmail(user.getEmail());
        if (existingByEmail != null) {
            return false;
        }

        // 3. 设置默认角色为普通用户
        if (user.getRole() == null || user.getRole().isEmpty()) {
            user.setRole("user");
        }


        // 4. 保存到数据库
        int rows = userMapper.insert(user);
        return rows > 0;
    }

    @Override
    public User login(String studentId, String password) {
        // 严格只通过学号查询
        User user = userMapper.findByStudentId(studentId);
        if (user != null && user.getPassword().equals(password)) {
            return user;
        }
        return null;
    }
    @Override
    public User findByEmail(String email) {
        return userMapper.findByEmail(email);
    }

    @Override
    public void updateUser(User user) {
        // 假设你的 UserMapper 里有根据 id 更新的方法，如果没有可以对照你的 mapper 改名
        userMapper.updateById(user);
    }
}
