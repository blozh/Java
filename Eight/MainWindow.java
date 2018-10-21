package Eight;

import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.text.DecimalFormat;
import java.util.Random;
import java.util.Vector;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

class MainWindow extends WindowAdapter{
    JFrame f=new JFrame("学生信息管理程序");
    ResultManager rm=new ResultManager();

    Statement stmt;

    void initWindow() throws Exception {
        Class.forName("org.sqlite.JDBC");

        Connection con = DriverManager.getConnection("jdbc:sqlite:C:\\sqlite\\Studentinfo.db");
        stmt=con.createStatement();

        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //如果要创建的table已经存在的话，就对异常进行处理
        try {
            stmt.executeUpdate("create table student (Name char(10),Sex char(2),Age Int,Score double)");
        }catch(org.sqlite.SQLiteException e){
            System.out.println("数据表已经存在，无需创建，直接添加数据");
        }

        //随机生成10个数据并插入到数据库中
        Random r=new Random();
        for(int i=0;i<10;i++){
            if(r.nextDouble()>0.5)
                stmt.executeUpdate("insert into student values ( '学生"+new DecimalFormat("#.00").format(Math.sqrt(r.nextDouble()))+"', '男',"+r.nextInt(25)+","+ new DecimalFormat("#.00").format(Math.sqrt(r.nextDouble())*100)+")");
            else
                stmt.executeUpdate("insert into student values ( '学生"+new DecimalFormat("#.00").format(Math.sqrt(r.nextDouble()))+"', '女',"+r.nextInt(25)+","+new DecimalFormat("#.00").format(Math.sqrt(r.nextDouble())*100)+")");
        }

        f.add(rm.initPanel(stmt));
        f.setBounds(350,300,600,450);
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
            stmt.executeUpdate("DELETE FROM student");
            stmt.executeUpdate("VACUUM");

            //写入table
            for(int i = 0; i < row; i++){
                String str="insert into student values('"+value[i][0]+"' , '" +value[i][1]+"',"+value[i][2]+","+value[i][3]+")";
                stmt.executeUpdate(str);
            }

        }catch(SQLException e){
            e.printStackTrace();
        }
        super.windowClosing(windowEvent);
    }

    public static void main(String [] args) throws Exception{
        new MainWindow().initWindow();
    }
}

class ResultManager implements ActionListener {
    Statement stmt;
    ResultSet rs;
    Font font=new Font("Simsun",Font.PLAIN,15);
    JLabel[] l=new JLabel[5];
    JTextField[] tf=new JTextField[4];
    JButton[] b=new JButton[6];
    JTable t;
    JScrollPane sp;/* 用JScrollPane装载JTable，这样超出范围的列就可以通过滚动条来查看 */
    JPanel p=new JPanel();
    GridBagLayout gb=new GridBagLayout();
    GridBagConstraints gbc=new GridBagConstraints();

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

    JPanel initPanel(Statement stmt){
        this.stmt=stmt;

        p.setLayout(gb);
        l[0]=new JLabel("姓名");
        l[1]=new JLabel("性别");
        l[2]=new JLabel("年龄");
        l[3]=new JLabel("成绩");
        l[4]=new JLabel("学生信息：");

        String[] s1={"插入","删除","查询","统计","age>18","all"};
        for(int i=0;i<6;i++){
            b[i]=new JButton(s1[i]);
            b[i].addActionListener(this);
            b[i].setBackground(Color.white);
        }

        try {
            //用模型生成表格
            t=new JTable(DataIntoTable("select * from student"));
        }catch(SQLException e){
            DefaultTableModel model=new DefaultTableModel();
            model.setColumnIdentifiers(new String[]{ "姓名", "性别", "年龄", "成绩" });
            t = new JTable(model);
        }

        sp=new JScrollPane(t);//当t=null时，如果执行sp=new JScrollPane(t)然后再让t指向一个有内容的Jtable的话，JScroll依然为空

        gbc.insets=new Insets(3, 3, 3, 3);//组件的横向间距
        gbc.weightx=0;//组件大小变化的增量值,如果想让某一列的网格宽度不变，那么这一列的网格的这个值必须都为0

        l[4].setFont(new Font("Simsun",Font.PLAIN,20));
        gbc.fill= GridBagConstraints.HORIZONTAL;//组件是否允许横向纵向扩大
        addJComponent(l[4],0,0,3,1);
        addJComponent(l[0],0,1,1,1);
        addJComponent(l[1],0,2,1,1);
        addJComponent(l[2],0,3,1,1);
        addJComponent(l[3],0,4,1,1);
        gbc.weightx=1;
        for(int i=0;i<4;i++){
            tf[i]=new JTextField();
            addJComponent(tf[i],1,i+1,3,1);
        }
        gbc.fill=GridBagConstraints.BOTH;
        gbc.ipady=50;
        gbc.weighty=0;
        addJComponent(b[1],2,5,1,1);
        addJComponent(b[3],2,6,1,1);
        addJComponent(b[0],0,5,2,1);
        addJComponent(b[2],0,6,2,1);
        gbc.weighty=1;
        addJComponent(b[4],0,7,2,1);
        addJComponent(b[5],2,7,1,1);

        gbc.weightx=1;
        gbc.ipadx=300;
        gbc.insets=new Insets(6, 3, 6, 3);//组件的横向间距
        gbc.weighty=1;
        addJComponent(sp,4,0,4,8);
        return p;
    }


    DefaultTableModel DataIntoTable(String str) throws SQLException{
        rs=stmt.executeQuery(str);
        DefaultTableModel model=new DefaultTableModel();
        //创建表头
        model.setColumnIdentifiers(new String[]{"姓名", "性别", "年龄", "成绩"});
        //添加数据
        while(rs.next())
        {
            Vector row=new Vector();
            row.add(rs.getString(1));
            row.add(rs.getString(2));
            row.add(rs.getString(3));
            row.add(rs.getString(4));
            //把以上数据添加到表格模型的一行中
            model.addRow(row);
        }
        return  model;
    }

    JTable getJTable(){
        return t;
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        if(actionEvent.getActionCommand().equals("插入")){
            //修改了tablemodel就相当于修改了table
            DefaultTableModel dtm=(DefaultTableModel)t.getModel();
            String []rowValues = {tf[0].getText(),tf[1].getText(),tf[2].getText(),tf[3].getText()};
            dtm.addRow(rowValues);  //添加一行
            for(int i=0;i<4;i++){
                tf[i].setText("");
            }
        }

        if(actionEvent.getActionCommand().equals("删除")){
            DefaultTableModel dtm=(DefaultTableModel)t.getModel();
            int selectedRow = t.getSelectedRow();//获得选中行的索引
            if(selectedRow!=-1)  //存在选中行
            {
                dtm.removeRow(selectedRow);  //删除行
            }
        }
        if(actionEvent.getActionCommand().equals("查询")){
            //根据学号或姓名查询并激活所在行
            int i;
            for(i = 0; i < t.getRowCount(); i++){
                if(!tf[0].getText().equals("")){
                    if(tf[0].getText().equals(t.getValueAt(i,0).toString())){
                        t.setRowSelectionInterval(i,i);//选中第i行到第i行
                        break;
                    }
                }
            }
            if(i==t.getRowCount()){
                JOptionPane.showMessageDialog(null, "查询的数据不存在", "提示", JOptionPane.INFORMATION_MESSAGE);
            }
        }
        if(actionEvent.getActionCommand().equals("统计")){
            int row = t.getRowCount();		// 表格行数

            double[] value = new double[row];
            int[] a={0,0,0,0,0};

            for(int i = 0; i < t.getRowCount(); i++){
                value[i] = Double.parseDouble(t.getValueAt(i, 3).toString());
                    if (value[i] < 60)
                        a[0]++;
                    else if (value[i] < 70)
                        a[1]++;
                    else if (value[i] < 80)
                        a[2]++;
                    else if (value[i] < 90)
                        a[3]++;
                    else
                        a[4]++;
            }
            String str="分数<60:"+a[0]+"人";
            for(int i=1;i<5;i++){
                str+="\n"+(i+5)+"0<=分数<"+(i+6)+"0:"+a[i]+"人";
            }

            JOptionPane.showMessageDialog(null, str, "成绩统计", JOptionPane.INFORMATION_MESSAGE);
        }
        if(actionEvent.getActionCommand().equals("age>18")){
            JFrame jf=new JFrame("年龄大于18的学生");
            JTable jt;

            try {
                jt=new JTable(DataIntoTable("select * from student where age>18"));
                jf.add(jt);
            }catch(SQLException e){
                DefaultTableModel model=new DefaultTableModel();
                model.setColumnIdentifiers(new String[]{ "姓名", "性别", "年龄", "成绩" });
                t = new JTable(model);
            }
            jf.setBounds(350,300,600,450);
            jf.setVisible(true);
        }
        if(actionEvent.getActionCommand().equals("all")){
            JFrame jf=new JFrame("所有学生");
            JTable jt;

            try {
                jt=new JTable(DataIntoTable("select * from student"));
                jf.add(jt);
            }catch(SQLException e){
                DefaultTableModel model=new DefaultTableModel();
                model.setColumnIdentifiers(new String[]{ "姓名", "性别", "年龄", "成绩" });
                t = new JTable(model);
            }
            jf.setBounds(350,300,600,450);
            jf.setVisible(true);
        }
    }
}