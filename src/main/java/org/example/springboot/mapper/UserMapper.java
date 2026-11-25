package org.example.springboot.mapper;
import org.example.springboot.entity.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;


public interface UserMapper {
    User findByStudentId(String studentId);

    // 2. 根据邮箱查询用户
    User findByEmail(String email);

    // 3. 插入新用户
    int insert(User user);
    // 4. 根据用户 ID 更新信息（用于重置密码、更新验证码等）
    int updateById(User user);
}
