package SoftEngineer;

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
     static String ip;
    JFrame f=new JFrame("辅导员界面");
    JTabbedPane tp = new JTabbedPane();
    ResultManager rm=new ResultManager();
    JFrame before;

    void initWindow()  {
        //创建选项卡面板
        tp.addTab("成绩录入和查询", rm.initPanel(ip));
        tp.setSelectedIndex(0);//设置默认选项卡为学生成绩管理
        f.add(tp);
        f.setBounds(350,300,900,600);
        f.setLocationRelativeTo(null);
        f.addWindowListener(this);//添加窗口关闭的事件监听器

        f.setVisible(true);
    }

    @Override
    public void windowClosing(WindowEvent windowEvent) {
        super.windowClosing(windowEvent);
        before.setVisible(true);
    }

    MainWindow(String ip, JFrame before){
        MainWindow.ip=ip;
        this.before=before;
        initWindow();
    }

}
