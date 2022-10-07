package kr.ac.kumoh.cattle;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public Optional<User> getUser(Integer userId) {
        return userRepository.findById(userId);
    }

    public void insertUser(User user){
        userRepository.save(user);
    }

    public void deleteUser(Integer userId) {
        userRepository.deleteById(userId);
    }

    public List<User> getUserAll(){return userRepository.findAll();}
}
