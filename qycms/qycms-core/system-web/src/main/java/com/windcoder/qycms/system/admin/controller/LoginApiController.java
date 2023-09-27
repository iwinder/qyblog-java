package com.windcoder.qycms.system.admin.controller;

import com.windcoder.qycms.dto.ResponseDto;
import com.windcoder.qycms.system.annotation.BloomIpLimit;
import com.windcoder.qycms.system.annotation.IpApiLimit;
import com.windcoder.qycms.system.config.RedisUtil;
import com.windcoder.qycms.system.entity.User;
import com.windcoder.qycms.system.enums.IpBlackType;
import com.windcoder.qycms.system.service.SysLoginLogService;
import com.windcoder.qycms.system.service.UserService;
import com.windcoder.qycms.utils.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.UnauthenticatedException;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("api/admin")
@Slf4j
public class LoginApiController {
    @Autowired
    private UserService userService;
    @Autowired
    private SysLoginLogService sysLoginLogService;
    @Autowired
    private RedisUtil redisUtil;

    @BloomIpLimit
    @IpApiLimit(limitType= IpApiLimit.LimitType.IP)
    @PostMapping("login")
    public ResponseDto adminLogin(@RequestBody User user){
        ResponseDto result = new ResponseDto();
        ValidatorUtil.require(user.getUsername(), "用户名");
        ValidatorUtil.require(user.getPassword(), "密码");
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(user.getUsername(), user.getPassword());
        try {
            subject.login(token);
            Map<String, Object> rMap = new HashMap<>();
            rMap.put("isLoggedIn", true);
            rMap.put("token", subject.getSession().getId());
            result.setContent(rMap);
            sysLoginLogService.saveLog(token.getUsername());
        } catch (IncorrectCredentialsException e) {
            log.error(e.getMessage());
            result.setSuccess(false);
            result.setMessage("用户名或密码错误");
        } catch (LockedAccountException e) {
            log.error(e.getMessage());
            result.setSuccess(false);
            result.setMessage("用户名或密码错误");
        } catch (ExcessiveAttemptsException e) {
            log.error(e.getMessage());
            result.setSuccess(false);
            result.setMessage("用户名或密码错误");
            redisUtil.saveBlack(IpAddressUtil.getClientRealIp(), AgentUserUtil.getUserAgent(),
                    IpBlackType.LOGIN.name(),user.getUsername() + "密码错误次数超过5次");
        } catch (AuthenticationException e) {
            log.error(e.getMessage());
            result.setSuccess(false);
            result.setMessage(e.getMessage());
        } catch (Exception e) {
            log.error(e.getMessage());
            result.setSuccess(false);
            result.setMessage("用户名或密码错误");
        }

        return result;
    }



    @RequestMapping(value = "/status")
    public ResponseEntity<?> status(){
        if(!SecurityUtils.getSubject().isAuthenticated()){
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
//            throw new UnauthenticatedException();
        }
        return new ResponseEntity<>(HttpStatus.OK);
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
    }

    @RequestMapping(value = "/currentUser")
    public ResponseDto currentUser() {
        if(!SecurityUtils.getSubject().isAuthenticated()){
            throw new UnauthenticatedException();
        }
        ResponseDto result = new ResponseDto();
        result.setContent(userService.currentUser());
        return result;
    }
    @RequestMapping(value = "/logout")
    public ResponseDto logout(HttpServletResponse httpServletResponse) {
        return new ResponseDto();
    }

}
