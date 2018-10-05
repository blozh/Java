package Compiles;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;

public class MainWindow {
    private JFrame frame=new JFrame("编译原理实验程序-计164-160393");
    private JTabbedPane tab=new JTabbedPane();

    MainWindow(){
        frame.setBounds(350,50,600,900);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(tab);
        tab.addTab("词法分析",new LexicalWindow().getPanel());
        tab.addTab("语法分析",new ParseWindow().getPanel());
        tab.addTab("语义分析",new SenmanticWindow().getPanel());
        frame.setVisible(true);
    }

    public static void main(String[] args){
        new MainWindow();
    }
}
