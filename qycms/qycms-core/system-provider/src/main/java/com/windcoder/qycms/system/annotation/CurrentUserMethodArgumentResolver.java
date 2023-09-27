package com.windcoder.qycms.system.annotation;

import com.windcoder.qycms.system.dto.UserWebDto;
import com.windcoder.qycms.system.entity.User;
import com.windcoder.qycms.system.shiro.UserToken;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Component
public class CurrentUserMethodArgumentResolver implements HandlerMethodArgumentResolver {
//    public CurrentUserMethodArgumentResolver(){}

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        if (parameter.hasParameterAnnotation(CurrentUser.class)) {
            return true;
        }
        return false;
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        CurrentUser currentUserAnnotation = parameter.getParameterAnnotation(CurrentUser.class);
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        UserWebDto userWeb = new UserWebDto();
        if (user == null) {

            return userWeb;
        }
        userWeb.setId(user.getId());
        userWeb.setNickname(user.getNickname());
        userWeb.setAvatar(user.getAvatar());
        return userWeb;
    }
}
