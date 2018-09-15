package ATest;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Random;

class Demo {
    public static void main(String[] args) throws Exception{
        //加载驱动

        Class.forName("com.mysql.jdbc.Driver");
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/test1", "root", "12.kiozH");
        Statement stmt=con.createStatement();

        try {
            stmt.executeUpdate("create table student (Name varchar(10),Sex varchar(2),Age Int)");
        }catch(java.sql.SQLSyntaxErrorException e){
            e.printStackTrace();
        }

        //插入数据
        Random r=new Random();
        for(int i=0;i<10;i++){
            if(r.nextDouble()>0.5)
                stmt.executeUpdate("insert into student values ( '学生', '男',"+r.nextInt(100)+")");
            else
                stmt.executeUpdate("insert into student values ( '学生', '女',"+r.nextInt(100)+")");
        }

        //查找数据
        ResultSet rs=stmt.executeQuery("select * from student where Age>18");
        while(rs.next()){
            System.out.println(rs.getString(1)+'\t'+rs.getString(2)+'\t'+rs.getInt(3));
        }
    }
}
