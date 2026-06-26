package org.example.springtheory.ch03.ex_3_3.dao;

import java.sql.Connection;
import java.sql.SQLException;

public class DConnectionsMaker implements SimpleConnectionMaker {
    @Override
    public Connection makeNewConnection() throws ClassNotFoundException, SQLException {
        return null;
    }
}
