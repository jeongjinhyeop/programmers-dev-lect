package org.example.springtheory.ch03.ex_3_2.dao;

import java.sql.Connection;
import java.sql.SQLException;

public class NConnectionsMaker implements SimpleConnectionMaker {
    @Override
    public Connection makeNewConnection() throws ClassNotFoundException, SQLException {
        return null;
    }
}
