package com.penguin.controller;

import com.penguin.mapper.UserMapper;
import com.penguin.pojo.Result;
import com.penguin.pojo.User;
import com.penguin.service.UserService;
import com.penguin.utils.JwtUtil;
import com.penguin.utils.Md5Util;
import com.penguin.utils.ThreadLocalUtil;
import jakarta.validation.constraints.Pattern;
import org.hibernate.validator.constraints.URL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/user")
@Validated
public class UserController{
    @Autowired
    private UserService userService;
    @Autowired
    private UserMapper userMapper;

    @PostMapping("/register")
    public Result register(@Pattern(regexp = "^\\S{5,16}$") String username,@Pattern(regexp = "^\\S{5,16}$") String  password){
        //用户是否已被占用
        User u = userService.findUserName(username);
        //注册
        if(u==null){
            //没有占用
            userService.register(username,password);
            return Result.success("成功注册！");
        }else {
            //占用
            return Result.error("用户名已经被占用");
        }
    }

    @PostMapping("/login")
    public Result<String>  login(@Pattern(regexp = "^\\S{5,16}$") String username,@Pattern(regexp = "^\\S{5,16}$") String  password){
        User loginUser = userService.findUserName(username);

        if(loginUser==null){
            return Result.error("用户名错误");
        }

        if (Md5Util.getMD5String(password).equals(loginUser.getPassword())){

            Map<String,Object> claims = new HashMap<>();
            claims.put("username",loginUser.getUsername());
            claims.put("id",loginUser.getId());
            String token = JwtUtil.genToken(claims);
            return Result.success(token);
        }

        return Result.error("密码错误");
    }



    @GetMapping("/userInfo")
    public Result<User> userInfo(/*@RequestHeader(name = "Authorization") String token*/) {
        //根据用户名查询用户
/*
        Map<String, Object> map = JwtUtil.parseToken(token);
        String username = (String) map.get("username");
*/
        Map <String,Object> map =  ThreadLocalUtil.get();
        String username = map.get("username").toString();
        User user = userService.findUserName(username);
        return Result.success(user);
    }

    @PutMapping("/update")
    public Result update(@RequestBody@Validated User user){
        userService.update(user);
        return Result.success("更新成功");
    }

    @PatchMapping("/updateAvater")
    public Result updateAvater(@RequestParam@URL String avatarUrl){
        userService.updateavatar(avatarUrl);
        return Result.success();
    }

    @PatchMapping("/updatePwd")
    public Result updatePwd(@RequestBody Map<String,String> params){
        //校验参数
        String oldPwd = params.get("oldPwd");
        String newPwd = params.get("newPwd");
        String rePwd = params.get("rePwd");

        if (StringUtils.hasLength(oldPwd) && StringUtils.hasLength(newPwd) && StringUtils.hasLength(rePwd)){

            Map<String,Object> map =  ThreadLocalUtil.get();
            String username = map.get("username").toString();
            User user = userService.findUserName(username);

            if (newPwd.equals(rePwd)) {

                if (user.getPassword().equals(Md5Util.getMD5String(oldPwd))){
                    userService.updatePwd(newPwd);
                    return Result.success("修改成功");
                }

                else return Result.error("原密码错误");

            }
            else   return  Result.error("两次输入密码不一致");


        }else{
            return Result.error("缺少参数");
        }
        //密码更新
    }



}
