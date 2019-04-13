package EightSix;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

class ManagerTeacher implements ActionListener {
    Statement stmt;
    ResultSet rs;
    Font font=new Font("Microsoft Yahei",Font.PLAIN,15);
    JLabel[] l=new JLabel[5];
    JTextField[] tf=new JTextField[4];
    JButton[] b=new JButton[4];
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
        l[0]=new JLabel("班级");
        l[1]=new JLabel("课程");
        l[2]=new JLabel("时间");
        l[3]=new JLabel("地点");
        l[4]=new JLabel("课程信息");

        b[0]=new JButton("插入");
        b[1]=new JButton("删除");
        b[2]=new JButton("查询");

        for(int i=0;i<3;i++){
            b[i].addActionListener(this);
        }

        try {
            DataIntoTable();
        }catch(java.sql.SQLException e){
            e.printStackTrace();
            DefaultTableModel model=new DefaultTableModel();
            model.setColumnIdentifiers(new String[]{ "班级", "课程", "时间", "地点" });
            t = new JTable(model);
        }
        sp=new JScrollPane(t);//当t=null时，如果执行sp=new JScrollPane(t)然后再让t指向一个有内容的Jtable的话，JScroll依然为空

        gbc.insets=new Insets(3, 3, 3, 3);//组件的横向间距
        gbc.weightx=0;//组件大小变化的增量值,如果想让某一列的网格宽度不变，那么这一列的网格的这个值必须都为0

        l[4].setFont(new Font("Microsoft Yahei",Font.PLAIN,20));
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
        addJComponent(b[2],0,6,3,1);

        gbc.weightx=1;
        addJComponent(b[0],0,5,2,1);

        gbc.weightx=1;
        gbc.ipadx=300;
        gbc.insets=new Insets(6, 3, 6, 3);//组件的横向间距
        gbc.weighty=1;
        addJComponent(sp,4,0,4,7);
        return p;
    }


    void DataIntoTable() throws java.sql.SQLException{
        /*当发生异常时
        此函数最后一句话 t=new JTable(model);就不会执行
        这样会导致窗口中对应的Jtable为空 无法初始化
        所以在主函数中一定要对这个函数进行异常处理
        */
        rs = stmt.executeQuery("select * from courses");
        DefaultTableModel model=new DefaultTableModel();
        //创建表头
        model.setColumnIdentifiers(new String[]{ "班级", "课程", "时间", "地点" });
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

        //最后，用模型生成表格
        t = new JTable(model);
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
            String []rowValues = {tf[0].getText(),tf[1].getText(),tf[2].getText(),tf[3].getText()};
            dtm.addRow(rowValues);  //添加一行
        }

        if(actionEvent.getActionCommand()=="删除"){
            DefaultTableModel dtm=(DefaultTableModel)t.getModel();
            int selectedRow = t.getSelectedRow();//获得选中行的索引
            if(selectedRow!=-1)  //存在选中行
            {
                dtm.removeRow(selectedRow);  //删除行
            }
        }

        if(actionEvent.getActionCommand()=="查询"){
            //根据班级和课程查询并激活所在行
            int i;
            for(i = 0; i < t.getRowCount(); i++){
                if(tf[0].getText().equals(t.getValueAt(i,0).toString())&&tf[1].getText().equals(t.getValueAt(i,1).toString())){
                    t.setRowSelectionInterval(i,i);//选中第i行到第i行
                    break;
                }
            }
            if(i==t.getRowCount()){
                JOptionPane.showMessageDialog(null, "查询的数据不存在", "提示", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }
}
