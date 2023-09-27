package com.windcoder.qycms.system;

import com.windcoder.qycms.system.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

//
//@RestController
//@RequestMapping("api/users")
public class UserApiController {

    @Autowired
    private UserService userService;

//    @ModelAttribute(name = "userForUpdate")
//    public User userForUpdate(@RequestParam(name = "id", required = false) Long id) {
//        if (null != id) {
//            User user = userService.findOne(id);
//            return user;
//        } else {
//            return new User();
//        }
//    }

//    @PutMapping("")
//    public UserDto add(@RequestBody() User user) {
//        user = userService.save(user);
//
//        return ModelMapperUtils.map(user, UserDto.class);
//    }
//
//    @PostMapping("/{id}")
//    public UserDto update(@PathVariable("id") Long userId, @ModelAttribute(name = "userForUpdate") User user) {
//        user = userService.update(user);
//
//        return ModelMapperUtils.map(user, UserDto.class);
//    }
//
//    @GetMapping("/{userId}")
//    public UserDto get(@PathVariable("userId") Long userId) {
//        User user = userService.findOne(userId);
//
//        return ModelMapperUtils.map(user, UserDto.class);
//    }
//
//    @GetMapping("")
//    public Page<UserDto> allActivities(User user,
//                                    @RequestParam(name= "searchText", required=false)String searchText,
//                                    @PageableDefault(direction= Sort.Direction.DESC,sort={"lastModifiedDate"}) Pageable pageable){
//        if(StringUtils.isNotBlank(searchText)) {
//            user.setUsername(searchText);
//        }
//        Page<User> users = userService.findAll(user,pageable);
//        Type type = new TypeToken<List<UserDto>>() {}.getType();
//        List<UserDto> userDtos = ModelMapperUtils.map(users.getContent(),type);
//        return  new PageImpl<>(userDtos,pageable,users.getTotalElements());
//    }
//
//    @GetMapping("checkUser")
//    public int countByUsername( @RequestParam(name = "username", required = true) String username){
//        return userService.countByUsername(username);
//    }


}
