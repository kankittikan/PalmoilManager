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
    public boolean changePassword(User manager, String newPassword, String confirmPassword){
        if(newPassword.equals(confirmPassword)){
            String hashedPassword = passwordEncoder.encode(newPassword);
            manager.setPassword(hashedPassword);
            userRepository.save(manager);
            return true;
        }else{
            return false;
        }

    }

    // Delete User method
    public boolean deleteUser(String username){
        User user = this.getManager(username);
        if(user == null){
            return false;
        }
        userRepository.delete(user);

        return true;
    }
}