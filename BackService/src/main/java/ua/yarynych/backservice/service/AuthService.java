package ua.yarynych.backservice.service;

import netscape.javascript.JSObject;
import ua.yarynych.backservice.entity.car.User;

import java.util.Map;

public interface AuthService {
    User login(String email) throws Exception;
    User registration(User user) throws Exception;
    Map<String, User> getUsers();
    User checkUser(String email) throws Exception;
}
