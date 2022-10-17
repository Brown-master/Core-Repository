package kr.ac.kumoh.cattle.Controller;

import kr.ac.kumoh.cattle.Entity.User;
import kr.ac.kumoh.cattle.Service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
@CrossOrigin(origins = "http://localhost:5000")
public class UserController {

    @Autowired
    private UserService userService;
    private Logger logger= LoggerFactory.getLogger(UserController.class);

    @GetMapping("/find")
    public String getUserById(@RequestParam(value = "id",required = false)Integer userId){
        return userService.getUser(userId).get().getFirstName()+userService.getUser(userId).get().getLastName();
    }

    @GetMapping("/findAll")
    public List<User> getUserAll(){
        logger.info("good!!");
        return userService.getUserAll();
    }

    @PostMapping("/insert")
    public User insertUser(@RequestBody User user){
        return userService.insertUser(user);
    }

    @DeleteMapping("/delete")
    public String deleteUser(@RequestParam(value = "id")Integer id){
        userService.deleteUser(id);
        return  "user/delete";
    }
}
