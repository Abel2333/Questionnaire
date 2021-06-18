package org.lger.demo;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;
import java.sql.Connection;

public class Test {
    public void Process() {
        try {
            Context context = new InitialContext();
            DataSource ds = (DataSource) context.lookup("jdbc/Questionnaire");
            Connection connection = ds.getConnection();
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
}
