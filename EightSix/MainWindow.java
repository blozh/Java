package EightSix;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

class MainWindow extends WindowAdapter{
    JFrame f=new JFrame("教学信息管理程序");
    JTabbedPane tp = new JTabbedPane();
    ResultManager rm=new ResultManager();
    CourseManager cm=new CourseManager();

    Statement stmt;

    void initWindow() throws Exception {
        Class.forName("com.mysql.cj.jdbc.Driver");

        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/EightSix?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true","loghder","8426784");
        //Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/EightSix", "root", "");
        stmt=con.createStatement();

        //如果要创建的table已经存在的话，就对异常进行处理
        //两句话要分开try，如果合起来try的话，当一个表格不存在而另一个表格存在时，不存在的表格就永远无法被创建
        try {
            stmt.executeUpdate("create table students (num int,name varchar(10),subject varchar(10),score double)");
            //stmt.executeUpdate("create table students (num int primary key,name varchar(10),subject varchar(10),score double)");
        }catch(java.sql.SQLSyntaxErrorException e){
            System.out.println("数据表已经存在，无需创建，直接添加数据");
        }
        try {stmt.executeUpdate("create table courses (class varchar(10),subject varchar(10),time varchar(10),place varchar(10))");
        }catch(java.sql.SQLSyntaxErrorException e){
            System.out.println("数据表已经存在，无需创建，直接添加数据");
        }

        //创建选项卡面板
        tp.addTab("学生成绩管理", rm.initPanel(stmt));
        tp.addTab("课程表管理", cm.initPanel(stmt));
        tp.setSelectedIndex(0);//设置默认选项卡为学生成绩管理
        f.add(tp);
        f.setBounds(350,300,600,400);
        f.addWindowListener(this);//添加窗口关闭的事件监听器

        f.setVisible(true);
    }

    @Override
    public void windowClosing(WindowEvent windowEvent) {
        try {
            //对students的数据进行清空再写入
            JTable table=rm.getJTable();

            int column = table.getColumnCount();		// 表格列数
            int row = table.getRowCount();		// 表格行数
            // value数组存放表格中的所有数据
            String[][] value = new String[row][column];

            for(int i = 0; i < table.getRowCount(); i++){
                for(int j = 0; j < table.getColumnCount(); j++){
                    value[i][j] = table.getValueAt(i, j).toString();
                }
            }

            //删除table中所有数据
            stmt.executeUpdate("truncate students");

            //写入table
            for(int i = 0; i < row; i++){
                String str="insert into students values("+value[i][0]+" , '" +value[i][1]+"','"+value[i][2]+"',"+value[i][3]+")";
                stmt.executeUpdate(str);
            }

            //对courses的数据进行清空再写入
            JTable table2=cm.getJTable();

            column = table2.getColumnCount();		// 表格列数
            row = table2.getRowCount();		// 表格行数
            // value数组存放表格中的所有数据
            String[][] value2 = new String[row][column];

            for(int i = 0; i < table2.getRowCount(); i++){
                for(int j = 0; j < table2.getColumnCount(); j++){
                    value2[i][j] = table2.getValueAt(i, j).toString();
                }
            }

            //删除table中所有数据
            stmt.executeUpdate("truncate courses");

            //写入table
            for(int i = 0; i < row; i++){
                String str="insert into courses values('"+value2[i][0]+"' , '" +value2[i][1]+"','"+value2[i][2]+"','"+value2[i][3]+"')";
                stmt.executeUpdate(str);
            }
        }catch(java.sql.SQLException e){
            e.printStackTrace();
        }
        super.windowClosing(windowEvent);
    }

    public static void main(String [] args) throws Exception{
        new MainWindow().initWindow();
    }
}
