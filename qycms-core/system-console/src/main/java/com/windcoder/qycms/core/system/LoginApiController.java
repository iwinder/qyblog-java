package com.windcoder.qycms.core.system;

import com.windcoder.qycms.core.system.entity.User;
import com.windcoder.qycms.exception.BusinessException;
import com.windcoder.qycms.utils.ReturnResult;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.UnauthenticatedException;
import org.apache.shiro.subject.Subject;
import org.json.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("api/")
public class LoginApiController {

    @PostMapping("login")
    public ReturnResult adminLogin(User user){
        ReturnResult result = new ReturnResult();
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(user.getUsername(), user.getPassword());
        try {
            subject.login(token);
            Map<String, Object> rMap = new HashMap<>();
            rMap.put("isLoggedIn", true);
            rMap.put("token", subject.getSession().getId());
            result.setToken(subject.getSession().getId());
            result.setResult(rMap);
            result.setMsg("登录成功");
            result.setCode(200);
        }  catch (IncorrectCredentialsException e) {
//            result.setMsg("密码错误");
//            result.setCode(400);
            throw new BusinessException("密码错误");
        } catch (LockedAccountException e) {
//            result.setMsg("登录失败，该用户已被冻结");
//            result.setCode(400);
            throw new BusinessException("登录失败，该用户已被冻结");
        } catch (AuthenticationException e) {
//            result.setMsg("该用户不存在");
//            result.setCode(400);
            throw new BusinessException("该用户不存在");
        } catch (Exception e) {
            throw new BusinessException(e.toString());
//            e.printStackTrace();
        }
        return result;
    }





    /**
     * 未登录，shiro应重定向到登录界面，此处返回未登录状态信息由前端控制跳转页面
     * @return
     */
    @RequestMapping(value = "/unauth")
    public ReturnResult unauth() {
//        ReturnResult result = new ReturnResult();
//        result.setMsg("未登录");
//        result.setCode(400);
//        return result;
        throw new BusinessException("未登录");
    }

    @RequestMapping(value = "/status")
    public void status(){
        if(!SecurityUtils.getSubject().isAuthenticated()){
            throw new UnauthenticatedException();
        }
    }

    @RequestMapping(value = "/loginfo")
    public ReturnResult loginfo() {
        if(!SecurityUtils.getSubject().isAuthenticated()){
            throw new UnauthenticatedException();
        }
        ReturnResult result = new ReturnResult();
        Subject subject = SecurityUtils.getSubject();
        User user = (User) subject.getPrincipal();
        Map<String, Object> rMap = new HashMap<>();
        rMap.put("isLoggedIn", true);
        rMap.put("token", subject.getSession().getId());
        rMap.put("username", user.getUsername());
        result.setToken(subject.getSession().getId());
        result.setResult(rMap);
        result.setMsg("登录成功");
        result.setCode(200);
        return result;
//        return modelMapper.map((UserToken)SecurityUtils.getSubject().getPrincipal(), UserTokenDto.class);
    }

}
