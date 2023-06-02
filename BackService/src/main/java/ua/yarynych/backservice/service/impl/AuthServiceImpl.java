package ua.yarynych.backservice.service.impl;

import org.springframework.stereotype.Service;
import ua.yarynych.backservice.entity.car.User;
import ua.yarynych.backservice.service.AuthService;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

@Service
public class AuthServiceImpl implements AuthService {

    private final Map<String, User> users = new HashMap<>();

    @PostConstruct
    private void initialize() {
        if(0 == users.size()) {
            users.put("email1@gmail.com", new User("email1@gmail.com", "Vitoldo"));
            users.put("email2@gmail.com", new User("email2@gmail.com", "Vadimiliano"));
            users.put("email3@gmail.com", new User("email3@gmail.com", "Danilian"));
        }
    }

    public User login(String email) throws Exception {
        if(users.containsKey(email)) {
            return users.get(email);
        } else {
            throw new Exception("User not found");
        }
    }

    public User registration(User user) throws Exception {
        if(users.containsKey(user.getEmail())) {
            throw new Exception("User already exist");
        } else {
            users.put(user.getEmail(), user);
            return user;
        }
    }

    public User checkUser(String email) throws Exception {
        if(users.containsKey(email)) {
            return users.get(email);
        } else {
            throw new Exception("User not found");
        }
    }

    public Map<String, User> getUsers() {
        return users;
    }
}
