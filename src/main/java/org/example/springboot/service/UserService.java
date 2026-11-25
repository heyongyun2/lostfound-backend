package org.example.springboot.service;

import org.example.springboot.entity.User;

public interface UserService {
    boolean register(User user);
    // 登录参数改为 studentId 和 password
    User login(String studentId, String password);
    User findByEmail(String email);
    void updateUser(User user);
}
