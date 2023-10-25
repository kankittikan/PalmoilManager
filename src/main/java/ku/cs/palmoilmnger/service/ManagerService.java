package ku.cs.palmoilmnger.service;

import ku.cs.palmoilmnger.entity.Manager;
import ku.cs.palmoilmnger.repository.ManagerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class ManagerService {
    @Autowired
    private ManagerRepository managerRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public boolean isUsernameAvailable(String username){
        return managerRepository.findByUsername(username) == null;
    }

    public void createManager(Manager user){
        Manager record = new Manager();
        record.setName(user.getName());
        record.setUsername(user.getUsername());

        String hashedPassword = passwordEncoder.encode(user.getPassword());
        record.setPassword(hashedPassword);

        managerRepository.save(record);
    }

    public Manager getManager(String username){
        return managerRepository.findByUsername(username);
    }
}
