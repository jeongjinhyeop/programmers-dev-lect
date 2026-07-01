package org.example.springtheory.ch02.ex_2_1.dao;

import java.sql.Connection;
import java.sql.SQLException;

public class NConnectionsMaker implements SimpleConnectionMaker {
    @Override
    public Connection makeNewConnection() throws ClassNotFoundException, SQLException {
        return null;
    }
}
