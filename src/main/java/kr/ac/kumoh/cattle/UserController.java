package kr.ac.kumoh.cattle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/{id}")
    public String getUserNameById(@PathVariable("id")Integer userId){
        return userService.getUserFullName(userId);
    }

    @GetMapping("/insert")
    public  String insertUser(@RequestParam(value = "id",required = false)Integer id,
                              @RequestParam(value = "firstName",required = false)String firstName,
                              @RequestParam(value = "lastName",required = false)String lastName){
        User user=new User();
        user.setId(2);
        user.setLastName("fff");
        user.setLastName("asd");
        userService.insertUser(user);
        return "user/insert";
    }
}
