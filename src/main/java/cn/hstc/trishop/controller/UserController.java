package cn.hstc.trishop.controller;

import cn.hstc.trishop.pojo.Order;
import cn.hstc.trishop.pojo.Product;
import cn.hstc.trishop.pojo.User;
import cn.hstc.trishop.result.Result;
import cn.hstc.trishop.service.UserService;
import cn.hstc.trishop.utils.Constants;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.HtmlUtils;

import javax.servlet.http.HttpSession;
import java.util.List;

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
    public Result login(@RequestBody User requestUser, HttpSession session) throws Exception {
        // 对html标签进行转义，防止xss攻击
        String account = requestUser.getAccount();
        account = HtmlUtils.htmlEscape(account);

        User user = userService.get(account, requestUser.getPassword());
        if (null != user) {
            System.out.println("id:"+user.getId()+" account: "+account+" logged");
            session.setAttribute("user", user);
            return new Result(Constants.code_success, user.toString());
        } else {
            return new Result(Constants.code_nofind, "帐号或密码错误");
        }
    }

    @ApiOperation(value = "用户忘记密码", notes = "通过用户名查询，返回String用户信息")
    @PostMapping(value = "api/user/forget")
    @ResponseBody
    public Result forget(@RequestParam(name = "account") String account) throws Exception {
        // 对html标签进行转义，防止xss攻击
        String account1 = HtmlUtils.htmlEscape(account);

        User user = userService.getByAccount(account1);
        if (null == user) {
            return new Result(Constants.code_nofind, "查无此人");
        } else {
            return new Result(Constants.code_success, user.getPassword());
        }
    }

    @ApiOperation(value = "新增/编辑 用户", notes = "发送用户信息，新增帐号.  必填项：account password \n" +
            "若要编辑用户信息，发送更新后的相关字段时同时发送用户id\n" +
            "<b>新增用户不要设置ID，编辑用户需要上传ID</b>\n")
    @PostMapping(value = "api/user/register")
    @ResponseBody
    public Result register(@RequestBody User requestUser) {
        if (null!=requestUser.getAccount()
            && null != requestUser.getPassword()) {
            // 对html标签进行转义，防止xss攻击
            requestUser.setAccount(HtmlUtils.htmlEscape(requestUser.getAccount()));

            return userService.addOrUpdate(requestUser);
        } else {
            return new Result(Constants.code_success, "");
        }
    }

    @ApiOperation(value = "根据用户 account 或 id 获取用户信息")
    @GetMapping(value = "api/user/get")
    @ResponseBody
    public User getUserInfo(
            @RequestParam(name = "account", required = false, defaultValue = "") String account,
            @RequestParam(name = "id", required = false, defaultValue = "0") Integer id) {
        account = HtmlUtils.htmlEscape(account);
        if (!"".equals(account))
            return userService.getByAccount(account);
        else if (0 != id)
            return userService.getById(id);
        return null;
    }

    @ApiOperation(value = "获取所有用户信息")
    @GetMapping(value = "api/user/getall")
    @ResponseBody
    public List<User> getAllUserInfo() {
        return userService.list();
    }

    @ApiOperation(value = "获取用户的所有订单")
    @GetMapping(value = "api/user/getorders")
    @ResponseBody
    public List<Order> getUserOrders(@RequestParam int userId) {
        return userService.getOrders(userId);
    }

    @ApiOperation(value = "删除某个订单")
    @GetMapping(value = "api/user/deleteorder")
    @ResponseBody
    public Result deleteOrder(@RequestParam int orderId) {
        return userService.deleteOrder(orderId);
    }
}
