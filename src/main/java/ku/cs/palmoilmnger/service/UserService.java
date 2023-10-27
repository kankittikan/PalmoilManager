package ku.cs.palmoilmnger.service;

import ku.cs.palmoilmnger.entity.User;
import ku.cs.palmoilmnger.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public boolean isUsernameAvailable(String username){
        return userRepository.findByUsername(username) == null;
    }

    public boolean isNameAvailable(String name){
        return userRepository.findByName(name) == null;
    }

    public void createManager(User user){
        User record = new User();
        record.setName(user.getName());
        record.setUsername(user.getUsername());
        record.setRole("ROLE_MANAGER");

        String hashedPassword = passwordEncoder.encode(user.getPassword());
        record.setPassword(hashedPassword);

        userRepository.save(record);
    }

    public User getManager(String username){
        return userRepository.findByUsername(username);
    }

    public List<User> getAllUsersRole(String role){
        return userRepository.findByRole(role);
    }

    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

    // changePassword method
    public void changePassword(User user, String newPassword){
        String hashed = passwordEncoder.encode(newPassword);
        user.setPassword(hashed);
        userRepository.save(user);
    }

    // Delete User method
    public void deleteUser(String username){
        User user = this.getManager(username);
        userRepository.delete(user);
    }
}
