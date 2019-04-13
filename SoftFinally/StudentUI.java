package SoftFinally;

import org.jb2011.lnf.beautyeye.ch3_button.BEButtonUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.RowSorter;
import javax.swing.filechooser.FileSystemView;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

class StudentUI{

    StudentUI(String sid, Statement stmt){
        this.sid=sid;
        this.stmt=stmt;
    }

    JFrame f=new JFrame("学生学习界面");
    JTabbedPane tp = new JTabbedPane();
    Statement stmt;
    String sid;

    void initWindow() throws Exception {


        ResultSet rs=stmt.executeQuery("select Cname from studentStatus where Sid='"+sid+"'");
        ArrayList<String> str=new ArrayList<>();
        while(rs.next()){
            //创建选项卡面板
            str.add(rs.getString(1));
        }
        for (int i = 0; i < str.size(); i++) {
            tp.addTab(str.get(i), new StudentUIPanel().initPanel(str.get(i)));
        }
        try {
            tp.setSelectedIndex(0);//设置默认选项卡
            tp.setTabPlacement(JTabbedPane.LEFT);
            JPanel pp=new JPanel();
            pp.setLayout(new BorderLayout());
            pp.add(tp);
            f.add(pp);
            f.setBounds(350,300,1000,1000);
            f.setLocationRelativeTo(null);
        }catch (IndexOutOfBoundsException e){
            //发生此异常说明此学生没有选课
            JTextArea jta=new JTextArea("您在本系统当中没有选课记录，详情请咨询管理人员");
            jta.setBackground(null);
            jta.setEditable(false);
            jta.setFont(new Font("XHei OSX",Font.PLAIN,40));
            f.add(jta);
            f.setBounds(350,300,1000,200);
        }
        f.setVisible(true);
    }

    Font font=new Font("XHei OSX",Font.PLAIN,20);
    class StudentUIPanel implements ActionListener {

        ResultSet rs=null;
        String[] strb={"搜索","打开教学资源","打开作业或测试","提交作业或测试","个人成绩统计","论坛"};
        JButton[] b=new JButton[strb.length];
        JTextArea jta=new JTextArea();
        String[] strl={"作业","资源&大纲","作业文件&测试文件"};
        JLabel[] jl=new JLabel[strl.length];
        JTextField jtf=new JTextField();
        JScrollPane[] jsp=new JScrollPane[2];
        JTable[] t=new JTable[2];
        DefaultTableModel[] dtm=new DefaultTableModel[2];
        String[][] title={{"资源名","类型"},{"名称","状态","类型","分数"}};

        JPanel p=new JPanel();
        GridBagLayout gb=new GridBagLayout();
        GridBagConstraints gbc=new GridBagConstraints();
        String Cname;

        //添加组件，横坐标x 纵坐标y gx为横向占据网格数
        void addJComponent (JComponent c, int x, int y, int gx, int gy){
            gbc.gridx=x;
            gbc.gridy=y;
            gbc.gridwidth=gx;
            gbc.gridheight=gy;
            gb.setConstraints(c,gbc);
            c.setFont(font);
            p.add(c);
        }
        JPanel initPanel(String Cname){
            int px=10;
            gbc.insets=new Insets(px,px,px,px);
            this.Cname=Cname;
            p.setLayout(gb);
            //初始化组件数组
            for(int i=0;i<b.length;i++){
                b[i]=new JButton(strb[i]);
                b[i].addActionListener(this);
                b[i].setFont(font);
                //b[i].setUI(new BEButtonUI().setNormalColor(BEButtonUI.NormalColor.lightBlue));
            }
            for (int i = 0; i < jl.length; i++) {
                jl[i]=new JLabel(strl[i]);
            }
            for (int i = 0; i < t.length; i++) {
                dtm[i]=new DefaultTableModel();
                dtm[i].setColumnIdentifiers(title[i]);
                t[i]=new JTable(dtm[i]);
                jsp[i]=new JScrollPane(t[i]);
                //点击表头排序
                RowSorter sorter = new TableRowSorter(dtm[i]);
                t[i].setRowSorter(sorter);
            }
            jta.setEditable(false);
            jta.setBackground(null);
            //布局代码
            gbc.fill= GridBagConstraints.BOTH;
            gbc.weighty=1;
            gbc.weightx=3;//网格水平可以缩放
            addJComponent(jl[0],0,0,1,1);
            addJComponent(jta,0,1,2,1);
            addJComponent(jl[1],0,2,1,1);
            addJComponent(jsp[0],0,3,1,4);
            addJComponent(jl[2],0,7,1,1);
            addJComponent(jsp[1],0,8,1,4);
            //论坛按钮
            JPanel temp=new JPanel();
            temp.setLayout(new FlowLayout());
            temp.add(b[5]);
            //addJComponent(temp,0,11,2,1);
            gbc.weightx=0;//网格水平不可缩放
            addJComponent(jtf,1,3,1,1);
            addJComponent(b[0],1,4,1,1);
            addJComponent(b[1],1,5,1,2);
            addJComponent(b[2],1,8,1,1);
            addJComponent(b[3],1,9,1,1);
            addJComponent(b[4],1,10,1,1);
            addJComponent(b[5],1,11,1,1);
            b[5].setUI(new BEButtonUI().setNormalColor(BEButtonUI.NormalColor.green));
            b[5].setForeground(Color.white);

            //插入数据
            try {
                rs=stmt.executeQuery("select filename,type from resource " +
                        "where Cname='"+Cname+"'");
                while(rs.next()){
                    Vector<String> v=new Vector<>();
                    for (int i = 0; i < 2; i++) {
                        v.add(rs.getString(i+1));
                    }
                    dtm[0].addRow(v);
                }
                String str="select daGang from teachStatus where Cname='"+Cname+"'";
                rs=stmt.executeQuery(str);
                rs.next();
                jta.setText(rs.getString(1));
                //作业信息读取
                InsertHomework();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return p;
        }
        void InsertHomework() throws SQLException{
            dtm[1].setNumRows(0);
            rs=stmt.executeQuery("select hu.Hname,isFinished,type,result from homeworkUpdate hu,homeworkAllocated ha where hu.Hname=ha.Hname and" +
                    " hu.Cname='"+Cname+"' and Sid='"+sid+"'");
            while(rs.next()){
                Vector<String> v=new Vector<>();
                for (int i = 0; i < 4; i++) {
                    v.add(rs.getString(i+1));
                }
                dtm[1].addRow(v);
            }
        }

        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            if(actionEvent.getActionCommand().equals(strb[0])){
                try {
                    dtm[0].setNumRows(0);
                    rs=stmt.executeQuery("select filename,type from resource " +
                            "where filename like'%"+jtf.getText()+"%' and Cname='"+Cname+"'");
                    while(rs.next()){
                        Vector<String> v=new Vector<>();
                        for (int i = 0; i < 2; i++) {
                            v.add(rs.getString(i+1));
                        }
                        dtm[0].addRow(v);
                    }
                    dtm[1].setNumRows(0);
                    rs=stmt.executeQuery("select Hname,type from homeworkAllocated " +
                            "where Cname='"+Cname+"'");
                    while(rs.next()){
                        Vector<String> v=new Vector<>();
                        for (int i = 0; i < 2; i++) {
                            v.add(rs.getString(i+1));
                        }
                        dtm[1].addRow(v);
                    }
                }catch(SQLException e){
                    e.printStackTrace();
                }
            }
            if(actionEvent.getActionCommand().equals(strb[1])){
                try {
                    String filepath="";
                    int selectedRow = t[0].getSelectedRow();//获得选中行的索引
                    if(selectedRow!=-1)  //存在选中行
                    {
                        String filename=dtm[0].getValueAt(selectedRow,0).toString();
                        try {
                            rs=stmt.executeQuery("select path from resource where filename='"+filename+"'");
                            rs.next();
                            filepath=rs.getString(1);
                            Desktop.getDesktop().open(new File(filepath));
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    }
                    else
                        JOptionPane.showMessageDialog(null, "请选择要打开的教学资源", "警告", JOptionPane.INFORMATION_MESSAGE);
                    }catch (Exception e){
                    e.printStackTrace();
                }
            }
            if(actionEvent.getActionCommand().equals(strb[2])){
                try {
                    String filepath="";
                    int selectedRow = t[1].getSelectedRow();//获得选中行的索引
                    if(selectedRow!=-1)  //存在选中行
                    {
                        String Hname=dtm[1].getValueAt(selectedRow,0).toString();
                        try {
                            rs=stmt.executeQuery("select path from homeworkAllocated where Hname='"+Hname+"'");
                            rs.next();
                            filepath=rs.getString(1);
                            if(dtm[1].getValueAt(selectedRow,1).equals("未下载"))
                                stmt.executeUpdate("update homeworkUpdate set isFinished='已下载' where Sid='"+sid+"' and Hname='"+Hname+"'");
                            InsertHomework();
                            FileSystemView fsv = FileSystemView.getFileSystemView();
                            File com=fsv.getHomeDirectory();
                            try{
                                Desktop.getDesktop().open(new File(com.getPath()+"\\"+Hname));
                            }catch (Exception e){
                                if(Method.copyFile(filepath,com.getPath()+"\\"+Hname)){
                                    Desktop.getDesktop().open(new File(com.getPath()+"\\"+Hname));
                                }
                            }
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    }
                    else
                        JOptionPane.showMessageDialog(null, "请选择要打开的作业或测试", "警告", JOptionPane.INFORMATION_MESSAGE);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
            if(actionEvent.getActionCommand().equals(strb[3])){
                    String filepath="";
                    int selectedRow = t[1].getSelectedRow();//获得选中行的索引
                    if(selectedRow!=-1)  //存在选中行
                    {
                        String Hname=dtm[1].getValueAt(selectedRow,0).toString();
                        try {
                            stmt.executeUpdate("update homeworkUpdate set isFinished='已提交',path='"+"Z:\\Updated\\"+sid+"-"+Hname+"' where Sid='"+sid+"' and Hname='"+Hname+"'");
                            InsertHomework();
                            FileSystemView fsv = FileSystemView.getFileSystemView();
                            File com=fsv.getHomeDirectory();
                            Method.copyFile(com.getPath()+"\\"+Hname,"Z:\\Updated\\"+sid+"-"+Hname);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    else
                        JOptionPane.showMessageDialog(null, "请选择要提交的作业", "警告", JOptionPane.INFORMATION_MESSAGE);
                }
            if(actionEvent.getActionCommand().equals(strb[4])){
                int a[]={0,0,0,0,0};
                try {
                    rs=stmt.executeQuery("select result from homeworkUpdate hu,homeworkAllocated ha " +
                            "where hu.Sid='"+sid+"' and hu.Cname=ha.Cname and hu.Hname=ha.Hname");
                    while (rs.next()){
                        try {
                            double result=Double.parseDouble(rs.getString(1));
                            if(result<60)
                                a[0]++;
                            else if(result<70)
                                a[1]++;
                            else if(result<70)
                                a[2]++;
                            else if(result<70)
                                a[3]++;
                            else
                                a[4]++;
                        }catch (Exception e){
                            System.out.println("不是数字");
                        }
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                String str="分数<60:"+a[0]+"次测试";
                for(int i=1;i<5;i++){
                    str+="\n"+(i+5)+"0<=分数<"+(i+6)+"0:"+a[i]+"次测试";
                }
                JOptionPane.showMessageDialog(null, str, "个人成绩统计情况", JOptionPane.INFORMATION_MESSAGE);
            }
            if(actionEvent.getActionCommand().equals(strb[5])){
                new DiscussUI(sid,Cname,stmt);
            }
        }
    }

}