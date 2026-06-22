package org.example.springtheory.ch01.ex_1_2.dao;

import java.sql.Connection;
import java.sql.SQLException;

public class DConnectionsMaker_2 implements SimpleConnectionMaker_2{
    @Override
    public Connection makeNewConnection() throws ClassNotFoundException, SQLException {
        return null;
    }
}
