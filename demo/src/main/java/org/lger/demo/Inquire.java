package org.lger.demo;

import javax.naming.*;
import javax.sql.DataSource;
import java.sql.*;
import java.util.*;

public class Inquire {
    private static List<Map<String, Object>> inquireSomething(String statement, String[] parameter, int parameterNum) {
        /* 直接连接数据库
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            System.err.println("缺少MySQL依赖");
        }

        List<Map<String, Object>> rowList = new ArrayList<>(); // 用于返回数据的容器
        *//* try-with-catch块创建需关闭的对象 *//*
        try (Connection connection = DriverManager.getConnection(
                DataBaseUser.URL.toString(),
                DataBaseUser.USER.toString(),
                DataBaseUser.PWD.toString()); // 连接数据库
             PreparedStatement preparedStatement = connection.prepareStatement(statement);) {
            for (int i = 1; i <= parameterNum; i++) {
                preparedStatement.setString(i, parameter[i - 1]);
            } // 补完sql语句
            ResultSet resultSet = preparedStatement.executeQuery(); // 获得执行结果
            ResultSetMetaData resMD = resultSet.getMetaData();// 获取键名
            int columnCount = resMD.getColumnCount();// 获取列的数量
            while (resultSet.next()) {
                Map<String, Object> rowData = new HashMap<>();//声明Map
                for (int i = 1; i <= columnCount; i++) {
                    rowData.put(resMD.getColumnName(i), resultSet.getObject(i));//获取键名及值
                }
                rowList.add(rowData);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("数据库处理异常");
        }
        return rowList;*/
        /* 通过数据源连接数据库 */
        List<Map<String, Object>> rowList = new ArrayList<>(); // 用于返回数据的容器
        try {
            Context context = new InitialContext();
            DataSource dataSource = (DataSource) context.lookup("java:comp/env/jdbc/Questionnaire");

            try (Connection connection = dataSource.getConnection();
                 PreparedStatement preparedStatement = connection.prepareStatement(statement)) {
                for (int i = 1; i <= parameterNum; i++) {
                    preparedStatement.setString(i, parameter[i - 1]);
                } // 补完sql语句
                ResultSet resultSet = preparedStatement.executeQuery(); // 获得执行结果
                ResultSetMetaData resMD = resultSet.getMetaData();// 获取键名
                int columnCount = resMD.getColumnCount();// 获取列的数量
                while (resultSet.next()) {
                    Map<String, Object> rowData = new HashMap<>();//声明Map
                    for (int i = 1; i <= columnCount; i++) {
                        rowData.put(resMD.getColumnName(i), resultSet.getObject(i));//获取键名及值
                    }
                    rowList.add(rowData);
                }
            } catch (SQLException e) {
                e.printStackTrace();
                System.err.println("数据库处理异常");
            }
        } catch (NamingException e) {
            System.err.println("数据源异常");
            e.printStackTrace();
        }
        return rowList;
    }

    public static String getPasswd(String username) {
        final String selectID = " select * from user_info where user_id=?";
        String[] temp = {username};
        List<Map<String, Object>> rowList = inquireSomething(selectID, temp, 1);
        return (String) rowList.get(0).get("user_passwd");
    }
}
