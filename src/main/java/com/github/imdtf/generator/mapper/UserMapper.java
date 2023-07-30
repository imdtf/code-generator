package com.github.imdtf.generator.mapper;

import com.github.imdtf.generator.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 后台用户表 Mapper 接口
 *
 * @author DTF
 * @since 2023-07-30 20:45:46
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {

}
