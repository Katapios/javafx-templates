package com.katapios;

import java.sql.*;

public class DB {
    private final String HOST = "localhost";
    private final String PORT = "6033";
    private final String DB_NAME = "web";
    private final String LOGIN = "db_user";
    private final String PASS = "db_user_pass";

    private Connection dbConn;


    private Connection getDbConnection() throws ClassNotFoundException, SQLException {
        String connStr = "jdbc:mysql://" + HOST + ":" + PORT + "/" + DB_NAME + "?autoReconnect=true&amp&useSSL=false";
        //Class.forName("com.mysql.cj.jdbc.Driver");
        dbConn = DriverManager.getConnection(connStr, LOGIN, PASS);
        return dbConn;
    }

    public void isConnected() throws SQLException, ClassNotFoundException {
        dbConn = getDbConnection();
        System.out.println(dbConn.isValid(1000));
    }

    public boolean regUser(String login, String email, String password) throws SQLException, ClassNotFoundException {
        String sql = "INSERT INTO `users` (`login`, `email`, `password`) VALUES(?, ?, ?)";

        Statement statement = getDbConnection().createStatement();
        ResultSet res = statement.executeQuery("SELECT * FROM `users` WHERE `login` = '"+ login + "' LIMIT 1");
        if (res.next())
            return false;

        PreparedStatement prSt= getDbConnection().prepareStatement(sql);
        prSt.setString(1, login);
        prSt.setString(2, email);
        prSt.setString(3, password);
        prSt.executeUpdate();
        return true; 
    }

    public boolean authUser(String login, String password) throws ClassNotFoundException, SQLException{
        Statement statement = getDbConnection().createStatement();
        String sql = "SELECT * FROM `users` WHERE `login` = '"+ login + "'AND `password` = '" + password + "' LIMIT 1";
        ResultSet res = statement.executeQuery(sql);
        return res.next();
    }

    public ResultSet getArticles() throws ClassNotFoundException, SQLException {
        String sql = "SELECT `title`, `intro` FROM `articles`";
        Statement statement = getDbConnection().createStatement();
        ResultSet res = statement.executeQuery(sql);
        return res;
    }

    public void addArticle(String title, String intro, String text) throws SQLException, ClassNotFoundException {
        String sql = "INSERT INTO `articles` (`title`, `intro`, `text`, `views`) VALUES(?, ?, ?, ?)";
        PreparedStatement prSt= getDbConnection().prepareStatement(sql);
        prSt.setString(1, title);
        prSt.setString(2, intro);
        prSt.setString(3, text);
        prSt.setInt(4, 15);
        prSt.executeUpdate();
    }
}
