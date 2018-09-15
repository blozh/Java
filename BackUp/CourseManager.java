package BackUp;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

class CourseManager implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        if(actionEvent.getActionCommand()=="添加"){
        }
        if(actionEvent.getActionCommand()=="删除"){
        }
        if(actionEvent.getActionCommand()=="查询"){
        }
        /*if(actionEvent.getActionCommand()!="课程表管理"){
            try {
                rs=stmt.executeQuery("select * from users where name = '"+tf[0].getText()+"' and passwords= '"+tf[1].getText()+"'");
                if(rs.next()){
                    JOptionPane.showMessageDialog(null, "登陆成功", "您好", JOptionPane.INFORMATION_MESSAGE);
                }
                else{
                    JOptionPane.showMessageDialog(null, "用户名或密码不正确", "错误", JOptionPane.ERROR_MESSAGE);
                }
            }catch(java.sql.SQLException e){
                e.printStackTrace();
            }
        }*/
    }

    Statement stmt;
    ResultSet rs;
    Font font=new Font("Microsoft Yahei",Font.PLAIN,15);
    JLabel[] l=new JLabel[5];
    JTextField[] tf=new JTextField[4];
    JButton[] b=new JButton[3];
    String[][] data={};
    String[] Name = { "班级", "课程", "时间", "地点" };//表头
    JTable t=new JTable(data,Name);
    JScrollPane sp=new JScrollPane(t);
    JPanel p=new JPanel();
    GridBagLayout gb=new GridBagLayout();
    GridBagConstraints gbc=new GridBagConstraints();

    JPanel initPanel(){
        p.setLayout(gb);
        l[0]=new JLabel("班级");
        l[1]=new JLabel("课程");
        l[2]=new JLabel("时间");
        l[3]=new JLabel("地点");
        l[4]=new JLabel("课程信息");

        b[0]=new JButton("添加");
        b[1]=new JButton("查询");
        b[2]=new JButton("统计");
        //f.setLayout(gb);
        for(JButton bb:b){
            bb=new JButton();
            bb.setBackground(Color.white);
        }
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
        addJComponent(b[2],3,5,1,1);
        for(int i=0;i<3;i++){
            b[i].addActionListener(this);
        }
        gbc.weightx=0;
        addJComponent(b[0],0,5,2,1);

        gbc.weightx=6;
        gbc.ipadx=300;
        gbc.insets=new Insets(6, 3, 6, 3);//组件的横向间距
        gbc.weighty=1;
        addJComponent(sp,4,0,4,6);
        return p;
    }
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
}
