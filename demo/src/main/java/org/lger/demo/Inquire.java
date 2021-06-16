package org.lger.demo;

import java.sql.*;
import java.util.*;

public class Inquire {
    private static List<Map<String, Object>> inquireSomething(String statement, String[] parameter, int parameterNum) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            System.err.println("缺少MySQL依赖");
        }

        List<Map<String, Object>> rowList = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(
                DataBaseUser.URL.toString(),
                DataBaseUser.USER.toString(),
                DataBaseUser.PWD.toString());
             PreparedStatement preparedStatement = connection.prepareStatement(statement);) {
            for (int i = 1; i <= parameterNum; i++) {
                preparedStatement.setString(i, parameter[i - 1]);
            }
            ResultSet resultSet = preparedStatement.executeQuery();
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
        return rowList;
    }

    public static String getPasswd(String username) {
        final String selectID = " select * from user_info where user_id=?";
        String[] temp = {username};
        List<Map<String, Object>> rowList = inquireSomething(selectID, temp, 1);
        return (String) rowList.get(0).get("user_passwd");
    }
}
