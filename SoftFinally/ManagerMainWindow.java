package SoftFinally;

import java.awt.BorderLayout;
import java.awt.Insets;
import java.sql.Statement;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

class ManagerMainWindow{
    ManagerMainWindow(Statement stmt){
        this.stmt=stmt;
    }

    JFrame f=new JFrame("管理人员分配权限");
    JTabbedPane tp = new JTabbedPane();
    ManagerStudent ms =new ManagerStudent();
    ManagerTeacher mt =new ManagerTeacher();

    Statement stmt;

    void initWindow() throws Exception {


        //创建选项卡面板
        tp.addTab("学生管理", ms.initPanel(stmt));
        tp.addTab("教师管理", mt.initPanel(stmt));
        tp.setSelectedIndex(0);//设置默认选项卡为学生成绩管理
        JPanel pp=new JPanel();
        pp.setLayout(new BorderLayout());
        pp.add(tp);
        f.add(pp);
        f.setBounds(350,300,650,1000);
        f.setLocationRelativeTo(null);
        f.setVisible(true);
    }
}
