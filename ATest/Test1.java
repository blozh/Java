package ATest;

import java.sql.*;
public class Test1 {
    final static String cfn = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
    final static String url = "jdbc:sqlserver://localhost:1433;DatabaseName=TEST";

    public static void main(String[] args) {
        Connection con = null;
        PreparedStatement statement = null;
        ResultSet res = null;
        try {
            Class.forName(cfn);
            con = DriverManager.getConnection(url,"loghder","12345678");

            String sql = "select * from part";//查询test表
            statement = con.prepareStatement(sql);
            res = statement.executeQuery();
            while(res.next()){
                String title = res.getString(1);//获取test_name列的元素                                                                                                                                                    ;
                System.out.println("姓名："+title);
            }

        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }finally{
            try {
                if(res != null) res.close();
                if(statement != null) statement.close();
                if(con != null) con.close();
            } catch (Exception e2) {
                // TODO: handle exception
                e2.printStackTrace();
            }
        }
    }
}