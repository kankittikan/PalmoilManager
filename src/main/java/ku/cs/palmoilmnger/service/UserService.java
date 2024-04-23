package ku.cs.palmoilmnger.service;

import ku.cs.palmoilmnger.entity.User;
import ku.cs.palmoilmnger.exception.UserException;
import ku.cs.palmoilmnger.model.ChangePassword;
import ku.cs.palmoilmnger.repository.UserRepository;
import ku.cs.palmoilmnger.repository.WorkRoundRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.regex.Pattern;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private WorkRoundRepository workRoundRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private WorkRoundService workRoundService;

    private boolean isUsernameNotAvailable(String username) {
        return userRepository.findByUsername(username) == null;
    }

    private boolean isNameNotAvailable(String name) {
        return userRepository.findByName(name) == null;
    }

    public void createManager(User user) throws UserException {
        if (!isNameNotAvailable(user.getName())) throw new UserException("ชื่อมีในระบบแล้ว");
        if (!isUsernameNotAvailable(user.getUsername())) throw new UserException("ชื่อผู้ใช้มีในระบบแล้ว");

        if(user.getUsername().length() < 3) throw new UserException("ชื่อผู้ใช้ต้องมีอย่างน้อย 3 ตัว");
        if(user.getName().length() < 3) throw new UserException("ชื่อจริงต้องมีอย่างน้อย 3 ตัว");
        if(user.getPassword().length() < 3) throw new UserException("รหัสผ่านต้องมีอย่างน้อย 3 ตัว");

        if(Pattern.compile("[\"\'/]").matcher(user.getUsername()).find()) throw new UserException("ชื่อผู้ใช้ห้ามประกอบด้วยตัวอักษรพิเศษ \" \' /");
        if(Pattern.compile(" ").matcher(user.getUsername()).find()) throw new UserException("ชื่อผู้ใช้ห้ามมีเว้นว่าง");
        if(Pattern.compile("[0-9]").matcher(user.getName()).find()) throw new UserException("ชื่อจริงห้ามมีตัวเลข");
        if(Pattern.compile("[\"\'/]").matcher(user.getName()).find()) throw new UserException("ชื่อจริงห้ามประกอบด้วยตัวอักษรพิเศษ \" \' /");
        if(Pattern.compile("  +").matcher(user.getName()).find()) throw new UserException("ชื่อจริงห้ามช่องว่างเกิน 1 ช่อง");
        if(Pattern.compile(" +").matcher(user.getPassword()).find()) throw new UserException("รหัสผ่านห้ามมีช่องว่าง");

        User record = new User();
        record.setName(user.getName());
        record.setUsername(user.getUsername());
        record.setRole("ROLE_MANAGER");

        String hashedPassword = passwordEncoder.encode(user.getPassword());
        record.setPassword(hashedPassword);

        userRepository.save(record);
    }

    public User getManager(String username) {
        return userRepository.findByUsername(username);
    }

    public List<User> getAllUsersRole(String role) {
        return userRepository.findByRole(role);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // changePassword method
    public void changePassword(ChangePassword changePassword) throws UserException {
        if (!changePassword.getPassword().equals(changePassword.getConfirmPassword()))
            throw new UserException("รหัสผ่านไม่ตรงกัน");
    
        User record = userRepository.findByUsername(changePassword.getUsername());
        if (record == null) throw new UserException("ไม่พบผู้ใช้ในระบบ");
        if(changePassword.getPassword().length() < 3) throw new UserException("รหัสผ่านต้องมีอย่างน้อย 3 ตัว");

        if(Pattern.compile(" +").matcher(changePassword.getPassword()).find()) throw new UserException("รหัสผ่านห้ามมีช่องว่าง");

        String hashed = passwordEncoder.encode(changePassword.getPassword());
        record.setPassword(hashed);
        userRepository.save(record);
    }

    // Delete User method
    public void deleteUser(String username) throws UserException {
        User user = this.getManager(username);
        if (!workRoundRepository.findByUser(user).isEmpty()) {
            throw new UserException("มีรอบที่ผู้ใช้ทำงานอยู่");
        }
        userRepository.delete(user);
    }
}
