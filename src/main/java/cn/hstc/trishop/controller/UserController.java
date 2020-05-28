package cn.hstc.trishop.controller;

import cn.hstc.trishop.pojo.User;
import cn.hstc.trishop.result.Result;
import cn.hstc.trishop.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.HtmlUtils;

/**
 * 用户管理控制类
 * 控制用户登录、注册、获取及修改用户信息
 */
@RestController
@Api(tags = "用户相关接口")
public class UserController {
    @Autowired
    UserService userService;

    @ApiOperation(value = "用户登录", notes = "进行数据库查询用户，返回响应码" +
            "\n<b>只需发送account和password字段</b>")
    @PostMapping(value = "api/user/login")
    @ResponseBody
    public Result login(@RequestBody User requestUser) throws Exception {
        // 对html标签进行转义，防止xss攻击
        String account = requestUser.getAccount();
        account = HtmlUtils.htmlEscape(account);

        User user = userService.get(account, requestUser.getPassword());
        if (null == user) {
            return new Result(400);
        } else {
            System.out.println("id:"+user.getId()+" account:"+account+" login");
            return new Result(200);
        }
    }

    @ApiOperation(value = "用户忘记密码", notes = "通过用户名查询，返回String用户信息")
    @PostMapping(value = "api/user/forget")
    @ResponseBody
    public String forget(@RequestParam(name = "account") String account) throws Exception {
        // 对html标签进行转义，防止xss攻击
        String account1 = HtmlUtils.htmlEscape(account);

        User user = userService.getByAccount(account1);
        if (null == user) {
            return "查无此人";
        } else {
            return user.toString();
        }
    }

    @ApiOperation(value = "用户注册帐号", notes = "发送用户信息，新增帐号\n" +
            "必填项：account password ")
    @PostMapping(value = "api/user/register")
    @ResponseBody
    public Result register(@RequestBody User requestUser) {
        if (null!=requestUser.getAccount()
            && null != requestUser.getPassword()) {
            // 对html标签进行转义，防止xss攻击
            requestUser.setAccount(HtmlUtils.htmlEscape(requestUser.getAccount()));

            return userService.add(requestUser);
        } else {
            return new Result(400);
        }
    }
}
