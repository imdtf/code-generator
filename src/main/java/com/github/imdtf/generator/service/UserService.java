package com.github.imdtf.generator.service;

import com.github.imdtf.generator.dao.UserDao;
import com.github.imdtf.generator.entity.User;
import com.github.imdtf.generator.other.User.UserDTO;
import com.github.imdtf.generator.other.User.UserVO;
import com.github.imdtf.mnt.dto.PageDTO;
import com.github.imdtf.mnt.vo.PageVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * 后台用户表 服务类
 *
 * @author DTF
 * @since 2023-07-30 20:45:46
 */

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserDao userDao;

    public void create(UserDTO userDto) {
        userDao.save(null);
    }

    public void delete(Long id) {
        userDao.removeById(id);
    }

    public void update(UserDTO userDto) {

    }

    public UserVO infoById(Long id) {
        User user = userDao.getById(id);
        return null;
    }

    public PageVO<UserVO> page(PageDTO pageDto) {
        return PageVO.of();
    }
}
