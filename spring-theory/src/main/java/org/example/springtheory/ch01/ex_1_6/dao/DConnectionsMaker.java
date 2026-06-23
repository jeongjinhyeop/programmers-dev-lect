package org.example.springtheory.ch01.ex_1_6.dao;

import java.sql.Connection;
import java.sql.SQLException;

public class DConnectionsMaker implements SimpleConnectionMaker {
    @Override
    public Connection makeNewConnection() throws ClassNotFoundException, SQLException {
        return null;
    }
}
