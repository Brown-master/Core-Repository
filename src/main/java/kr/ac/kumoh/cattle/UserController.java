package kr.ac.kumoh.cattle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/find")
    public String getUserNameById(@RequestParam(value = "id",required = false)Integer userId){
        return userService.getUserFullName(userId);
    }

    @GetMapping("/insert")
    public  String insertUser(@RequestParam(value = "id",required = false)Integer id,
                              @RequestParam(value = "firstName",required = false)String firstName,
                              @RequestParam(value = "lastName",required = false)String lastName){
        User user=new User();
        user.setId(id);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        userService.insertUser(user);
        return "user/insert";
    }

    @GetMapping("/delete")
    public  String deleteUser(@RequestParam(value = "id")Integer id){
        userService.deleteUser(id);
        return  "user/delete";
    }
}
