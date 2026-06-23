package org.example.springtheory.ch01.ex_1_5.dao;

import java.sql.Connection;
import java.sql.SQLException;

public class NConnectionsMaker implements SimpleConnectionMaker {
    @Override
    public Connection makeNewConnection() throws ClassNotFoundException, SQLException {
        return null;
    }
}
