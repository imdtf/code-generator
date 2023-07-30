package com.github.imdtf.generator.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import com.github.imdtf.mnt.response.Response;
import com.github.imdtf.mnt.dto.PageDTO;
import com.github.imdtf.mnt.vo.PageVO;
import org.springframework.web.bind.annotation.*;
import com.github.imdtf.generator.service.UserService;
import com.github.imdtf.generator.other.User.UserDTO;
import com.github.imdtf.generator.other.User.UserVO;


/**
 * 后台用户表Controller
 *
 * @author DTF
 * @since 2023-07-30 20:45:46
 */
@RequiredArgsConstructor
@Api(tags = "user接口")
@RestController
@RequestMapping("/generator/user")
public class UserController {

    private final UserService service;

    @PostMapping("/create")
    @ApiOperation(value = "新增")
    public Response<Void> create(@RequestBody UserDTO userDto) {
        service.create(userDto);
        return Response.ok();
    }

    @DeleteMapping("/delete/{id}")
    @ApiOperation(value = "删除")
    public Response<Void> delete(@PathVariable("id") Long id) {
        service.delete(id);
        return Response.ok();
    }

    @PutMapping("/update")
    @ApiOperation(value = "修改")
    public Response<Void> update(@RequestBody UserDTO userDto) {
        service.update(userDto);
        return Response.ok();
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "单条查询")
    public Response<UserVO> info(@PathVariable("id") Long id) {
        return Response.ok(service.infoById(id));
    }

    @PostMapping("/findPage")
    @ApiOperation(value = "分页查询")
    public Response<PageVO<UserVO>> page(@RequestBody PageDTO pageDto) {
        return Response.ok(service.page(pageDto));
    }
}
