package ku.cs.palmoilmnger.service;

import ku.cs.palmoilmnger.entity.User;
import ku.cs.palmoilmnger.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public boolean isUsernameAvailable(String username){
        return userRepository.findByUsername(username) == null;
    }

    public void createManager(User user){
        User record = new User();
        record.setName(user.getName());
        record.setUsername(user.getUsername());

        String hashedPassword = passwordEncoder.encode(user.getPassword());
        record.setPassword(hashedPassword);

        userRepository.save(record);
    }

    public User getManager(String username){
        return userRepository.findByUsername(username);
    }
}
