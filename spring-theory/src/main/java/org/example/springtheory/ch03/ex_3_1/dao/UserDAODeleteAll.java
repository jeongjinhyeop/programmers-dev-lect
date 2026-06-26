package org.example.springtheory.ch03.ex_3_1.dao;
import org.example.springtheory.ch03.ex_3_2.dao.StatementStrategy;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UserDAODeleteAll extends UserDAO {


    @Override
    protected PreparedStatement makeStatement(Connection conn) throws ClassNotFoundException, SQLException {
        return null;
    }
}