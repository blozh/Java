package SoftFinally;

import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

class ManagerStudent implements ActionListener {
    Statement stmt;
    ResultSet rs;
    Font font=new Font("XHei OSX",Font.PLAIN,20);
    JLabel[] l=new JLabel[3];
    JTextField[] tf=new JTextField[3];
    JButton[] b=new JButton[3];
    JTable t;
    JScrollPane sp;/* 用JScrollPane装载JTable，这样超出范围的列就可以通过滚动条来查看 */
    JPanel p=new JPanel();
    GridBagLayout gb=new GridBagLayout();
    GridBagConstraints gbc=new GridBagConstraints();
    JComboBox comboBox=new JComboBox();

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
        int px=10;
        gbc.insets=new Insets(px,px,px,px);
        this.stmt=stmt;

        p.setLayout(gb);
        l[0]=new JLabel("学号");
        l[1]=new JLabel("科目");
        l[2]=new JLabel("工号");

        b[0]=new JButton("插入");
        b[1]=new JButton("删除");
        b[2]=new JButton("查询");
        JPanel jppp=new JPanel();
        jppp.setLayout(new FlowLayout());
        for(int i=0;i<b.length;i++){
            b[i].addActionListener(this);
            jppp.add(b[i]);
        }

        try {
            t=new JTable(DataIntoTable("select s.id,s.name,ss.Cname,t.name from studentStatus ss,student s,teacher t where s.id=ss.Sid and t.id=ss.Tid"));
        }catch(java.sql.SQLException e){
            DefaultTableModel model=new DefaultTableModel();
            model.setColumnIdentifiers(new String[]{ "班级", "课程", "时间", "地点" });
            t = new JTable(model);
        }
        sp=new JScrollPane(t);//当t=null时，如果执行sp=new JScrollPane(t)然后再让t指向一个有内容的Jtable的话，JScroll依然为空

        gbc.insets=new Insets(3, 3, 3, 3);//组件的横向间距
        gbc.weightx=0;//组件大小变化的增量值,如果想让某一列的网格宽度不变，那么这一列的网格的这个值必须都为0

        gbc.fill= GridBagConstraints.HORIZONTAL;//组件是否允许横向纵向扩大
        addJComponent(l[0],0,1,1,1);
        String[] kemu={"编译原理","操作系统","软件工程","面向对象","数据库","数值分析","java","系统结构"};
        for (int i = 0; i < kemu.length; i++) {
            comboBox.addItem(kemu[i]);
        }
        addJComponent(l[1],0,2,1,1);

        addJComponent(l[2],0,3,1,1);
        gbc.weightx=1;
        for(int i=0;i<tf.length;i++){
            tf[i]=new JTextField();
            if(i!=1)
            addJComponent(tf[i],1,i+1,3,1);
        }
        addJComponent(comboBox,1,2,3,1);
        gbc.fill=GridBagConstraints.BOTH;
        gbc.weighty=0;

        gbc.weightx=1;
        addJComponent(jppp,0,5,4,1);
        for (int i = 0; i < b.length; i++) {
            b[i].setFont(new Font("XHei OSX",Font.PLAIN,25));
        }

        gbc.weightx=1;
        gbc.ipadx=300;
        gbc.insets=new Insets(6, 3, 6, 3);//组件的横向间距
        gbc.weighty=1;
        addJComponent(sp,0,6,4,10);
        return p;
    }
    DefaultTableModel DataIntoTable(String str) throws SQLException{
        rs=stmt.executeQuery(str);
        DefaultTableModel model=new DefaultTableModel();
        //创建表头
        model.setColumnIdentifiers(new String[]{"学号","姓名","科目","老师"});
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
        if(actionEvent.getActionCommand()=="插入"){
            //修改了tablemodel就相当于修改了table
            //大概是因为 getmodel方法返回的是tablemodel类的地址？
            DefaultTableModel dtm=(DefaultTableModel)t.getModel();
            String str="insert into studentStatus values ('"+tf[0].getText()+"','"+comboBox.getSelectedItem().toString()+"','"+tf[2].getText()+"')";
            try {
                stmt.executeUpdate(str);
                DefaultTableModel model=(DefaultTableModel)t.getModel();
                model.setNumRows(0);//清空表格
                rs=stmt.executeQuery("select s.id,s.name,ss.Cname,t.name from studentStatus ss,student s,teacher t where s.id=ss.Sid and t.id=ss.Tid");
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
            } catch (SQLException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "插入的数据有误，请检查", "警告", JOptionPane.INFORMATION_MESSAGE);
            }
        }

        if(actionEvent.getActionCommand()=="删除"){
            DefaultTableModel dtm=(DefaultTableModel)t.getModel();
            int selectedRow = t.getSelectedRow();//获得选中行的索引
            if(selectedRow!=-1)  //存在选中行
            {
                String Sid=dtm.getValueAt(selectedRow,0).toString();
                String Cname=dtm.getValueAt(selectedRow,2).toString();
                try {
                    stmt.executeUpdate("delete from studentStatus where sid='"+Sid+"' and Cname='"+Cname+"'");
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                dtm.removeRow(selectedRow);  //删除行
            }
        }

        if(actionEvent.getActionCommand()=="查询"){
            //根据学号查询选课情况
            JFrame jf=new JFrame("学号为"+tf[0].getText()+"的学生的选课情况");
            JTable jt;

            try {
                jt=new JTable(DataIntoTable(
                        "select s.id,s.name,ss.Cname,t.name from studentStatus ss,student s,teacher t " +
                                "where s.id=ss.Sid and t.id=ss.Tid and s.id='"+tf[0].getText()+"'"));
                jf.add(jt);
            }catch(SQLException e){
                DefaultTableModel model=new DefaultTableModel();
                model.setColumnIdentifiers(new String[]{ "学号","姓名","科目","老师" });
                t = new JTable(model);
            }
            jf.setBounds(350,300,600,450);
            jf.setVisible(true);
        }
    }
}
