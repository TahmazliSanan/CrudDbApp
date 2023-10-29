package com.mycompany.cruddbapp.dao.inter;

import com.mycompany.cruddbapp.entity.User;
import java.util.List;

public interface UserDaoInter {
    User getUserById(int id);
    List<User> getUsers();
    boolean insertUser(User user);
    boolean updateUser(User user);
    boolean deleteUser(int id);
}