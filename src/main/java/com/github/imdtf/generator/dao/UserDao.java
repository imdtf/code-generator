package com.github.imdtf.generator.dao;

import com.github.imdtf.generator.entity.User;
import com.github.imdtf.generator.mapper.UserMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * 后台用户表 Dao
 * </p>
 *
 * @author DTF
 * @since 2023-07-30 20:45:46
 */
@Repository
public class UserDao extends ServiceImpl<UserMapper, User> {
}
