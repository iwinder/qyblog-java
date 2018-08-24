package com.windcoder.qycms.core.system;

import com.windcoder.qycms.core.system.dto.UserDto;
import com.windcoder.qycms.core.system.entity.User;
import com.windcoder.qycms.core.system.service.UserService;
import com.windcoder.qycms.utils.ModelMapperUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
}
