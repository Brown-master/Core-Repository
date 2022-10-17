package kr.ac.kumoh.cattle.Service;

import kr.ac.kumoh.cattle.Entity.User;
import kr.ac.kumoh.cattle.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService{

    @Autowired
    private UserRepository userRepository;

    public Optional<User> getUser(Integer userId) {
        return userRepository.findById(userId);
    }

    public User insertUser(@RequestBody User user){
        userRepository.save(user);
        return user;
    }

    public void deleteUser(Integer userId) {userRepository.deleteById(userId);
    }

    public List<User> getUserAll(){return userRepository.findAll();}
}
