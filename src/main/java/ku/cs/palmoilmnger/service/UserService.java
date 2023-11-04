package ku.cs.palmoilmnger.service;

import ku.cs.palmoilmnger.entity.User;
import ku.cs.palmoilmnger.exception.UserException;
import ku.cs.palmoilmnger.model.ChangePassword;
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

    @Autowired
    private WorkRoundService workRoundService;

    private boolean isUsernameNotAvailable(String username){
        return userRepository.findByUsername(username) == null;
    }

    private boolean isNameNotAvailable(String name){
        return userRepository.findByName(name) == null;
    }

    public void createManager(User user) throws UserException {
        if(!isNameNotAvailable(user.getName())) throw new UserException("ชื่อมีในระบบแล้ว");
        if(!isUsernameNotAvailable(user.getUsername())) throw new UserException("ชื่อผู้ใช้มีในระบบแล้ว");

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
    public void changePassword(ChangePassword changePassword) throws UserException {
        if(!changePassword.getPassword().equals(changePassword.getConfirmPassword())) throw new UserException("รหัสผ่านไม่ตรงกัน");

        User record = userRepository.findByUsername(changePassword.getUsername());
        if(record == null) throw new UserException("ไม่พบผู้ใช้ในระบบ");

        String hashed = passwordEncoder.encode(changePassword.getPassword());
        record.setPassword(hashed);
        userRepository.save(record);
    }

    // Delete User method
    public void deleteUser(String username){
        User user = this.getManager(username);

        userRepository.delete(user);
    }
}
