package org.example.springtheory.ch01.ex_1_1.dao;

import java.sql.Connection;
import java.sql.SQLException;

public class DuserDAO_3 extends UserDao_3 {
    @Override
    public Connection getConnection() throws ClassNotFoundException, SQLException {
        return null;
    }
}
