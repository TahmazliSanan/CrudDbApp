package com.mycompany.cruddbapp.dao.impl;

import com.mycompany.cruddbapp.dao.inter.AbstractDao;
import com.mycompany.cruddbapp.dao.inter.UserDaoInter;
import com.mycompany.cruddbapp.entity.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UserDaoImpl extends AbstractDao implements UserDaoInter {
    private final String getUserByIdSql = "SELECT * "
            + "FROM USERS "
            + "WHERE ID = ";
    
    private final String getUsersSql = "SELECT * "
            + "FROM USERS";
    
    private final String insertUserSql = "INSERT INTO "
            + "USERS (FIRST_NAME, LAST_NAME, EMAIL_ADDRESS, MONTHLY_SALARY) "
            + "VALUES (?, ?, ?, ?)";
    
    private final String updateUserSql = "UPDATE USERS SET "
            + "FIRST_NAME = ?, LAST_NAME = ?, EMAIL_ADDRESS = ?, MONTHLY_SALARY = ? "
            + "WHERE ID = ?";
    
    private final String deleteUserSql = "DELETE FROM USERS "
            + "WHERE ID = ";
    
    private User getUser(ResultSet resultset) throws Exception {
        int id = resultset.getInt("id");
        String firstName = resultset.getString("first_name");
        String lastName = resultset.getString("last_name");
        String emailAddress = resultset.getString("email_address");
        double monthlySalary = resultset.getDouble("monthly_salary");
        return new User(id, firstName, lastName, emailAddress, monthlySalary);
    }
    
    @Override
    public User getUserById(int id) {
        User userResult = null;
        try (Connection connection = getConnection()) {
            Statement statement = connection.createStatement();
            statement.execute(getUserByIdSql + id + ";");
            ResultSet resultSet = statement.getResultSet();
            while (resultSet.next()) {
                userResult = getUser(resultSet);
            }
        } catch (Exception ex) {
            Logger.getLogger(UserDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return userResult;
    }

    @Override
    public List<User> getUsers() {
        List<User> userListResult = new ArrayList<>();
        try (Connection connection = getConnection()) {
            Statement statement = connection.createStatement();
            statement.execute(getUsersSql);
            ResultSet resultSet = statement.getResultSet();
            while (resultSet.next()) {
                userListResult.add(getUser(resultSet));
            }
        } catch (Exception ex) {
            Logger.getLogger(UserDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return userListResult;
    }

    @Override
    public boolean insertUser(User user) {
        try (Connection connection = getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(insertUserSql);
            preparedStatement.setString(1, user.getFirstName());
            preparedStatement.setString(2, user.getLastName());
            preparedStatement.setString(3, user.getEmailAddress());
            preparedStatement.setDouble(4, user.getMonthlySalary());
            return preparedStatement.execute();
        } catch (Exception ex) {
            Logger.getLogger(UserDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    @Override
    public boolean updateUser(User user) {
        try (Connection connection = getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(updateUserSql);
            preparedStatement.setString(1, user.getFirstName());
            preparedStatement.setString(2, user.getLastName());
            preparedStatement.setString(3, user.getEmailAddress());
            preparedStatement.setDouble(4, user.getMonthlySalary());
            preparedStatement.setInt(5, user.getId());
            return preparedStatement.execute();
        } catch (Exception ex) {
            Logger.getLogger(UserDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    @Override
    public boolean deleteUser(int id) {
        try (Connection connection = getConnection()) {
            Statement statement = connection.createStatement();
            return statement.execute(deleteUserSql + id + ";");
        } catch (Exception ex) {
            Logger.getLogger(UserDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
}