package com.windcoder.qycms.core.system;

import com.windcoder.qycms.core.system.dto.UserDto;
import com.windcoder.qycms.core.system.entity.User;
import com.windcoder.qycms.core.system.service.UserService;
import com.windcoder.qycms.utils.ModelMapperUtils;
import org.apache.commons.lang3.StringUtils;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Type;
import java.util.List;

@RestController
@RequestMapping("api/users")
public class UserApiController {

    @Autowired
    private UserService userService;

    @ModelAttribute(name = "userForUpdate")
    public User getUser(@RequestParam(name = "id", required = false) Long id) {
        if (null != id) {
            User user = userService.findOne(id);
            return user;
        } else {
            return new User();
        }
    }

    @PutMapping("")
    public UserDto add(@RequestBody() User user) {
        user = userService.save(user);

        return ModelMapperUtils.map(user, UserDto.class);
    }

    @PostMapping("/{userId}")
    public UserDto update(@PathVariable("userId") Long userId, @ModelAttribute(name = "userForUpdate") User user,
                          @RequestParam(name = "password", required = false) String password) {
        user = userService.update(user);

        return ModelMapperUtils.map(user, UserDto.class);
    }

    @GetMapping("/{userId}")
    public UserDto get(@PathVariable("userId") Long userId) {
        User user = userService.findOne(userId);

        return ModelMapperUtils.map(user, UserDto.class);
    }

    @GetMapping("")
    public Page<UserDto> allActivities(User user,
                                    @RequestParam(name= "searchText", required=false)String searchText,
                                    @PageableDefault(direction= Sort.Direction.DESC,sort={"lastModifiedDate"}) Pageable pageable){
        if(StringUtils.isNotBlank(searchText)) {
            user.setUsername(searchText);
        }
        Page<User> users = userService.findAll(user,pageable);
        Type type = new TypeToken<List<UserDto>>() {}.getType();
        List<UserDto> userDtos = ModelMapperUtils.map(users.getContent(),type);
        return  new PageImpl<>(userDtos,pageable,users.getTotalElements());
    }

    @GetMapping("checkUser")
    public int countByUsername( @RequestParam(name = "username", required = true) String username){
        return userService.countByUsername(username);
    }
}
