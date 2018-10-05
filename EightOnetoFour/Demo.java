package EightOnetoFour;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Random;

class Demo {
    public static void main(String[] args) throws Exception{
        //加载驱动

        Class.forName("org.sqlite.JDBC");

        Connection con = DriverManager.getConnection("jdbc:sqlite:C:\\sqlite\\EightSix.db");
        Statement stmt=con.createStatement();

        //如果要创建的table已经存在的话，就对异常进行处理
        try {
            stmt.executeUpdate("create table student (Name varchar(10),Sex varchar(2),Age Int)");
        }catch(org.sqlite.SQLiteException e){
            System.out.println("数据表已经存在，无需创建，直接添加数据");
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
