package cn.hstc.trishop.controller;

import cn.hstc.trishop.pojo.User;
import cn.hstc.trishop.result.Result;
import cn.hstc.trishop.service.UserService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.HtmlUtils;

/**
 * 用户管理控制类
 * 控制用户登录、注册、获取及修改用户信息
 */
@Controller
public class UserController {
    @Autowired
    UserService userService;

    @ApiOperation(value = "用户登录", notes = "进行数据库查询用户，返回响应码")
    @PostMapping(value = "user/login")
    @ResponseBody
    public Result login(@RequestBody User requestUser) {
        // 对html标签进行转义，防止xss攻击
        String account = requestUser.getAccount();
        account = HtmlUtils.htmlEscape(account);

        User user = userService.get(account, requestUser.getPassword());
        if (null == user) {
            return new Result(400);
        } else {
            return new Result(200);
        }
    }
}
