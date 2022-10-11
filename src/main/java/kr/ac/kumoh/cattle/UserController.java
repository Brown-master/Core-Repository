package kr.ac.kumoh.cattle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/find")
    public String getUserById(@RequestParam(value = "id",required = false)Integer userId){
        return userService.getUser(userId).get().getFirstName()+userService.getUser(userId).get().getLastName();
    }

    @GetMapping("/findAll")
    public List<User> getUserAll(){
        return userService.getUserAll();
    }

    @PostMapping("/insert")
    public User insertUser(@RequestBody User user){
        return userService.insertUser(user);
    }

    @GetMapping("/delete")
    public String deleteUser(@RequestParam(value = "id")Integer id){
        userService.deleteUser(id);
        return  "user/delete";
    }

}
