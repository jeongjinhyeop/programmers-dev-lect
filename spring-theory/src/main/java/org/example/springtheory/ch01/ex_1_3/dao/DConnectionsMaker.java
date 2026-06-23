package org.example.springtheory.ch01.ex_1_3.dao;

import org.example.springtheory.ch01.ex_1_2.dao.SimpleConnectionMaker_2;

import java.sql.Connection;
import java.sql.SQLException;

public class DConnectionsMaker implements SimpleConnectionMaker {
    @Override
    public Connection makeNewConnection() throws ClassNotFoundException, SQLException {
        return null;
    }
}
