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

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
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

class TeacherUI{
    Font font=new Font("XHei OSX",Font.PLAIN,20);
    TeacherUI(String tid, Statement stmt){
        this.tid=tid;
        this.stmt=stmt;
    }

    JFrame f=new JFrame("教师授课界面");
    JTabbedPane tp = new JTabbedPane();
    Statement stmt;
    String tid;

    void initWindow() throws Exception {

        ResultSet rs=stmt.executeQuery("select Cname from teachStatus where Tid='"+tid+"'");
        ArrayList<String> str=new ArrayList<>();
        while(rs.next()){
            //创建选项卡面板
            str.add(rs.getString(1));
        }
        for (int i = 0; i < str.size(); i++) {
            tp.addTab(str.get(i), new TeacherUIPanel().initPanel(str.get(i)));
        }
        tp.setSelectedIndex(0);//设置默认选项卡
        tp.setTabPlacement(JTabbedPane.LEFT);
        JPanel pp=new JPanel();
        pp.setLayout(new BorderLayout());
        pp.add(tp);
        f.add(pp);
        f.setBounds(600,400,1000,1000);
        f.setLocationRelativeTo(null);
        f.setVisible(true);
    }

    class TeacherUIPanel implements ActionListener {
        ResultSet rs=null;
        String[] strb={"保存","搜索","上传教学资源","删除教学资源","布置作业","删除作业或测试","批改作业或测试","设置为已批改","布置测试","本科目成绩统计","查看教学资源","论坛"};
        JButton[] b=new JButton[strb.length];
        JTextArea jta=new JTextArea();
        String[] strl={"作业","资源&大纲","作业文件&测试试卷"};
        JLabel[] jl=new JLabel[strl.length];
        JTextField jtf=new JTextField();
        JScrollPane[] jsp=new JScrollPane[2];
        JTable[] t=new JTable[2];
        DefaultTableModel[] dtm=new DefaultTableModel[2];
        String[][] title={{"资源名","类型"},{"作业名","类型"}};
        JTable miniT=null;
        DefaultTableModel miniDTM=null;

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
            //布局代码
            gbc.fill= GridBagConstraints.BOTH;
            gbc.weighty=1;
            gbc.weightx=3;//网格水平可以缩放
            addJComponent(jl[0],0,0,1,1);
            addJComponent(jta,0,1,1,1);
            addJComponent(jl[1],0,2,1,1);
            addJComponent(jsp[0],0,3,1,5);
            addJComponent(jl[2],0,8,1,1);
            addJComponent(jsp[1],0,9,1,7);
            //论坛按钮
            JPanel temp=new JPanel();
            temp.setLayout(new FlowLayout());
            temp.add(b[11]);
            //addJComponent(temp,0,15,2,1);
            gbc.weightx=0;//网格水平不可缩放
            addJComponent(b[0],1,1,1,1);
            addJComponent(jtf,1,3,1,1);
            addJComponent(b[1],1,4,1,1);
            addJComponent(b[2],1,5,1,1);
            addJComponent(b[3],1,6,1,1);
            addJComponent(b[10],1,7,1,1);
            addJComponent(b[4],1,9,1,1);
            addJComponent(b[8],1,10,1,1);
            addJComponent(b[5],1,11,1,1);
            addJComponent(b[6],1,12,1,1);
            addJComponent(b[7],1,13,1,1);
            addJComponent(b[9],1,14,1,1);
            addJComponent(b[11],1,15,1,1);
            b[11].setUI(new BEButtonUI().setNormalColor(BEButtonUI.NormalColor.green));
            b[11].setForeground(Color.white);

            //插入数据
            try {
                InsertResource();
                InsertHomework();
                rs=stmt.executeQuery("select daGang from teachStatus where Tid='"+tid+"' and Cname='"+Cname+"'");
                rs.next();
                jta.setText(rs.getString(1));
            } catch (SQLException e) {
                e.printStackTrace();
            }

            return p;
        }
        //插入数据系列
        void InsertResource() throws SQLException{
            dtm[0].setNumRows(0);
            rs=stmt.executeQuery("select filename,type from resource " +
                    "where Cname='"+Cname+"'");
            while(rs.next()){
                Vector<String> v=new Vector<>();
                for (int i = 0; i < 2; i++) {
                    v.add(rs.getString(i+1));
                }
                dtm[0].addRow(v);
            }
        }
        void InsertHomework() throws SQLException{
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
        }
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            if(actionEvent.getActionCommand().equals(strb[0])){
                try {
                    stmt.executeUpdate("update teachStatus set daGang='"+jta.getText()+"'where Cname='"+Cname+"'");
                    JOptionPane.showMessageDialog(null, "教学大纲已成功修改", "警告", JOptionPane.INFORMATION_MESSAGE);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if(actionEvent.getActionCommand().equals(strb[1])){
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
                }catch(SQLException e){
                    e.printStackTrace();
                }
            }
            if(actionEvent.getActionCommand().equals(strb[2])){
                JFileChooser jfc = new JFileChooser();
                jfc.setFileSelectionMode(JFileChooser.OPEN_DIALOG);
                jfc.showDialog(new JLabel(), "打开上传文件");
                File file = jfc.getSelectedFile();
                String filetype="";
                switch (file.getName().substring(file.getName().lastIndexOf(".") + 1)){
                    case "pptx":
                    case "ppt":filetype="课件";break;
                    case "avi":
                    case "mp4":filetype="教学视频";break;
                    case "aac":
                    case "mp3":filetype="教学音频";break;
                    case "doc":
                    case "docx":filetype="大纲";break;
                    default:filetype=file.getName().substring(file.getName().lastIndexOf(".") + 1);
                }
                try {
                    String str="insert into resource values('"+Cname+
                            "','"+file.getName()+"','"+filetype+"','"+"Z:\\Updated\\"+file.getName()+"')";
                    stmt.executeUpdate(str);
                    Method.copyFile(file.getAbsolutePath(),"Z:\\Updated\\"+file.getName());
                    InsertResource();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if(actionEvent.getActionCommand().equals(strb[3])){
                int selectedRow = t[0].getSelectedRow();//获得选中行的索引
                if(selectedRow!=-1)  //存在选中行
                {
                    String filename=dtm[0].getValueAt(selectedRow,0).toString();
                    try {
                        stmt.executeUpdate("delete from resource where filename='"+filename+"' and Cname='"+Cname+"'");
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    dtm[0].removeRow(selectedRow);  //删除行
                }
            }
            if(actionEvent.getActionCommand().equals(strb[4])){
                JFileChooser jfc = new JFileChooser();
                jfc.setFileSelectionMode(JFileChooser.OPEN_DIALOG);
                jfc.showDialog(new JLabel(), "打开待布置作业");
                File file = jfc.getSelectedFile();
                try {
                    String str="insert into homeworkAllocated values('"+Cname+
                            "','"+file.getName()+"','"+"Z:\\Updated\\"+file.getName()+"','作业')";
                    stmt.executeUpdate(str);
                    InsertHomework();
                    //向作业提交表中插入数据
                    //插入的数据符合的条件为，从作业分配表的对应科目中获取作业名和学号不存在于作业提交表中的数据
                    //插入时状态设置为未下载
                    String str2="insert into homeworkUpdate(Sid,Cname,Hname,isFinished) " +
                            "select ss.sid,ss.Cname,Hname,'未下载' from homeworkAllocated ha ,studentStatus ss " +
                            "where ha.Cname='"+Cname+"' and Hname='"+file.getName()+"' and ha.Cname=ss.Cname and not exists(" +
                            "select * from homeworkUpdate where Sid=ss.sid and Cname=ss.Cname and Hname='"+file.getName()+"')";
                    stmt.executeUpdate(str2);
                    Method.copyFile(file.getAbsolutePath(),"Z:\\Updated\\"+file.getName());
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if(actionEvent.getActionCommand().equals(strb[5])){
                int selectedRow = t[1].getSelectedRow();//获得选中行的索引
                if(selectedRow!=-1)  //存在选中行
                {
                    String Hname=dtm[1].getValueAt(selectedRow,0).toString();
                    try {
                        stmt.executeUpdate("delete from homeworkAllocated where Hname='"+Hname+"' and Cname='"+Cname+"'");
                        stmt.executeUpdate("delete from homeworkUpdate where Hname='"+Hname+"' and Cname='"+Cname+"'");
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    dtm[1].removeRow(selectedRow);  //删除行
                }
            }
            if(actionEvent.getActionCommand().equals(strb[6])){
                int selectedRow = t[1].getSelectedRow();//获得选中行的索引
                String Hname="";
                if(selectedRow!=-1)  //存在选中行
                    Hname=dtm[1].getValueAt(selectedRow,0).toString();
                //根据作业名查询作业情况
                JFrame jf=new JFrame(Hname+"的批改窗口");
                JTable jt;

                try {
                    rs=stmt.executeQuery("select Sid,name,Hname,isFinished,result from HomeworkUpdate h,student s where Cname='"+Cname+"' and h.Sid=s.id and Hname='"+Hname+"'");
                    miniDTM=new DefaultTableModel();
                    //创建表头
                    miniDTM.setColumnIdentifiers(new String[]{"学号","姓名","作业或测试名","状态","成绩"});
                    //添加数据
                    while(rs.next())
                    {
                        Vector row=new Vector();
                        row.add(rs.getString(1));
                        row.add(rs.getString(2));
                        row.add(rs.getString(3));
                        row.add(rs.getString(4));
                        row.add(rs.getString(5));
                        //把以上数据添加到表格模型的一行中
                        miniDTM.addRow(row);
                    }
                    miniT=new JTable(miniDTM);
                    JScrollPane jsp=new JScrollPane(miniT);
                    //线性布局
                    jf.setLayout(new BorderLayout());
                    jf.add(jsp,BorderLayout.CENTER);
                    JButton jb=new JButton("打开提交的作业或测试");
                    JButton jb2=new JButton("提交成绩");
                    jb.setBackground(Color.white);
                    jb2.setBackground(Color.white);
                    jb.addActionListener(this);
                    jb2.addActionListener(this);
                    JPanel jp=new JPanel();
                    jp.setLayout(new FlowLayout());
                    jp.add(jb);
                    jp.add(jb2);
                    jf.add(jp,BorderLayout.SOUTH);
                }catch(SQLException e){
                    e.printStackTrace();
                }
                jf.setBounds(350,300,600,450);
                jf.setVisible(true);
            }
            if(actionEvent.getActionCommand().equals(strb[7])){
                int selectedRow = t[1].getSelectedRow();//获得选中行的索引
                String Hname="";
                if(selectedRow!=-1)  //存在选中行
                    Hname=dtm[1].getValueAt(selectedRow,0).toString();
                try  {
                    stmt.executeUpdate("update homeworkUpdate set isFinished ='已批改' where Cname='"+Cname+"' and Hname='"+Hname+"'");
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
            if(actionEvent.getActionCommand().equals(strb[8])){
                JFileChooser jfc = new JFileChooser();
                jfc.setFileSelectionMode(JFileChooser.OPEN_DIALOG);
                jfc.showDialog(new JLabel(), "打开待布置测试");
                File file = jfc.getSelectedFile();
                try {
                    String str="insert into homeworkAllocated values('"+Cname+
                            "','"+file.getName()+"','"+"Z:\\Updated\\"+file.getName()+"','测试')";
                    stmt.executeUpdate(str);
                    InsertHomework();
                    String str2="insert into homeworkUpdate(Sid,Cname,Hname,isFinished) " +
                            "select ss.sid,ss.Cname,Hname,'未下载' from homeworkAllocated ha ,studentStatus ss " +
                            "where ha.Cname='"+Cname+"' and Hname='"+file.getName()+"' and ha.Cname=ss.Cname and not exists(" +
                            "select * from homeworkUpdate where Sid=ss.sid and Cname=ss.Cname and Hname='"+file.getName()+"')";
                    stmt.executeUpdate(str2);
                    Method.copyFile(file.getAbsolutePath(),"Z:\\Updated\\"+file.getName());
                } catch (SQLException e) {
                    e.printStackTrace();
                }

            }
            if(actionEvent.getActionCommand().equals("打开提交的作业或测试")){
                String filepath="";
                int selectedRow = miniT.getSelectedRow();//获得选中行的索引
                if(selectedRow!=-1)  //存在选中行
                {
                    String Hname=miniDTM.getValueAt(selectedRow,2).toString();
                    String Sid=miniDTM.getValueAt(selectedRow,0).toString();
                    try {
                        rs=stmt.executeQuery("select path from homeworkUpdate where Hname='"+Hname+"' and Sid='"+Sid+"'");
                        rs.next();
                        filepath=rs.getString(1);
                        Desktop.getDesktop().open(new File(filepath));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                else
                    JOptionPane.showMessageDialog(null, "请选择要批改的作业", "警告", JOptionPane.INFORMATION_MESSAGE);
            }
            if(actionEvent.getActionCommand().equals("提交成绩")){
                int row=miniDTM.getRowCount();
                String result="",Sid="",Hname="";
                for (int i = 0; i < row; i++) {
                    try {
                        result = miniDTM.getValueAt(i, 4).toString();
                        Sid=miniDTM.getValueAt(i,0).toString();
                        Hname=miniDTM.getValueAt(i,2).toString();
                        //这句话就是为了测试一下成绩是否为数字，产生异常就跳过
                        if(Double.parseDouble(result)<=100&&Double.parseDouble(result)>=0){
                            int num=stmt.executeUpdate("update homeworkUpdate set result='"+result+"' " +
                                    "where (isFinished='已批改' or isFinished='已提交') and Sid='"+Sid+"' and Cname='"+Cname+"' and Hname='"+Hname+"'");
                            if(num==0){
                                JOptionPane.showMessageDialog(null, "学号为"+Sid+"的测试未提交", "警告", JOptionPane.INFORMATION_MESSAGE);
                            }
                        }else{
                            JOptionPane.showMessageDialog(null, "学号为"+Sid+"的测试成绩在0-100范围之外", "警告", JOptionPane.INFORMATION_MESSAGE);
                        }
                    }catch (NullPointerException e){
                        System.out.println("成绩处未填写内容");
                    }
                    catch (Exception e){
                        JOptionPane.showMessageDialog(null, "学号为"+Sid+"的测试成绩非数字", "警告", JOptionPane.INFORMATION_MESSAGE);
                    }
                }
                //重新载入成绩
                try {
                    rs=stmt.executeQuery("select Sid,name,Hname,isFinished,result from HomeworkUpdate h,student s where Cname='"+Cname+"' and h.Sid=s.id and Hname='"+Hname+"'");
                    miniDTM.setNumRows(0);
                    while(rs.next())
                    {
                        Vector row2=new Vector();
                        row2.add(rs.getString(1));
                        row2.add(rs.getString(2));
                        row2.add(rs.getString(3));
                        row2.add(rs.getString(4));
                        row2.add(rs.getString(5));
                        //把以上数据添加到表格模型的一行中
                        miniDTM.addRow(row2);
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
                JOptionPane.showMessageDialog(null, "填写正确的成绩已提交", "提示", JOptionPane.INFORMATION_MESSAGE);

            }
            if(actionEvent.getActionCommand().equals(strb[9])){
                int a[]={0,0,0,0,0};
                try {
                    rs=stmt.executeQuery("select result from homeworkUpdate hu,homeworkAllocated ha " +
                            "where hu.Cname='"+Cname+"' and hu.Cname=ha.Cname and hu.Hname=ha.Hname");
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
                String str="分数<60:"+a[0]+"人次";
                for(int i=1;i<5;i++){
                    str+="\n"+(i+5)+"0<=分数<"+(i+6)+"0:"+a[i]+"人次";
                }
                JOptionPane.showMessageDialog(null, str, "学科成绩统计情况", JOptionPane.INFORMATION_MESSAGE);
            }
            if(actionEvent.getActionCommand().equals(strb[10])){
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
            if(actionEvent.getActionCommand().equals(strb[11])){
                new DiscussUI(tid,Cname,stmt);
            }
        }
    }
}