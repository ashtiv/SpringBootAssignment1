package com.example.SpringBootAssignment1.Service;

import com.example.SpringBootAssignment1.Repository.MyUser;
import com.example.SpringBootAssignment1.Repository.UserSearchCriteria;

import java.util.List;

public interface UserService {
    MyUser createUser(MyUser myUser);
    List<MyUser> searchUsers(UserSearchCriteria criteria);
    MyUser updateUser(Long id, MyUser myUser);
    void deleteUser(Long id);

    List<MyUser> getAllUsers();
}
