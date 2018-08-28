package com.windcoder.qycms.core.system;

import com.windcoder.qycms.core.system.entity.User;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/")
public class LoginApiController {

    @PostMapping("login")
    public JSONObject adminLogin(User user){
        JSONObject result = new JSONObject();
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(user.getUsername(), user.getPassword());
        try {
            subject.login(token);
            result.put("token",subject.getSession().getId());
            result.put("msg","登录成功");
            result.put("code",200);
        }  catch (IncorrectCredentialsException e) {
            result.put("msg", "密码错误");
            result.put("code",400);
        } catch (LockedAccountException e) {
            result.put("msg", "登录失败，该用户已被冻结");
            result.put("code",400);
        } catch (AuthenticationException e) {
            result.put("msg", "该用户不存在");
            result.put("code",400);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}
