package Compiles;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;

public class MainWindow {
    private JFrame frame=new JFrame("编译原理实验程序-计164-160393");
    private JTabbedPane tab=new JTabbedPane();

    MainWindow(){
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(tab);
        tab.addTab("词法分析",new LexicalWindow().getPanel());
        tab.addTab("语法及语义分析",new ParseWindow().getPanel());
        tab.setSelectedIndex(0);//设置默认选项卡
        frame.setSize(800,800);
        frame.setLocationRelativeTo(null);//把窗口设置在屏幕中央
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setVisible(true);
    }

    public static void main(String[] args){
        new MainWindow();
    }
}
