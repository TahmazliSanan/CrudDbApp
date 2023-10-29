package com.mycompany.cruddbapp.dao.context;

import com.mycompany.cruddbapp.dao.impl.UserDaoImpl;
import com.mycompany.cruddbapp.dao.inter.UserDaoInter;

public class Context {
    public static UserDaoInter instanceOfUserDaoImpl() {
        return new UserDaoImpl();
    }
}