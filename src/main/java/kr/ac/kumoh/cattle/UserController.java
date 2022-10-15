package kr.ac.kumoh.cattle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/user")
@CrossOrigin(origins = "http://localhost:5000")
public class UserController {

    private Logger logger= LoggerFactory.getLogger(UserController.class);
    @Autowired
    private UserService userService;

    @ExceptionHandler(value = NoSuchElementException.class)
    public String controllerExceptionHandler(Exception e) {
        logger.error(e.getMessage());
        return "/error/404/";
    }

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
