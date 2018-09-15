package EightFive;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

class UserWindow implements ActionListener{
    Statement stmt;
    ResultSet rs;
    GridBagLayout gb=new GridBagLayout();
    GridBagConstraints gbc=new GridBagConstraints();
    Font font=new Font("Microsoft Yahei",Font.PLAIN,25);
    JFrame f=new JFrame("用户注册与登陆");
    JLabel[] l=new JLabel[2];
    JTextField[] tf=new JTextField[2];
    JButton[] b=new JButton[2];

    //添加组件，横坐标x 纵坐标y gx为横向占据网格数
    void addJComponent (JComponent c, int x, int y, int gx, int gy){
        gbc.gridx=x;
        gbc.gridy=y;
        gbc.gridwidth=gx;
        gbc.gridheight=gy;
        gb.setConstraints(c,gbc);
        c.setFont(font);
        f.add(c);
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        if(actionEvent.getActionCommand()=="登陆"){
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
        }
        if(actionEvent.getActionCommand()=="注册"){
            try {
                rs=stmt.executeQuery("select * from users where name = '"+tf[0].getText()+"'");
                if(!rs.next()){
                    stmt.executeUpdate("insert into users values('"+tf[0].getText()+"' , '" +tf[1].getText()+"')");
                    JOptionPane.showMessageDialog(null, "注册成功", "您好", JOptionPane.INFORMATION_MESSAGE);
                }
                else{
                    JOptionPane.showMessageDialog(null, "用户名已被注册", "错误", JOptionPane.ERROR_MESSAGE);
                }
            }catch(java.sql.SQLException e){
                e.printStackTrace();
            }
        }
    }

    void initWindow (){
        l[0]=new JLabel("用户名：");
        l[0].setHorizontalAlignment(SwingConstants.CENTER);
        l[1]=new JLabel("密码：");
        l[1].setHorizontalAlignment(SwingConstants.CENTER);
        tf[0]=new JTextField();
        tf[1]=new JTextField();
        b[0]=new JButton("登陆");
        b[1]=new JButton("注册");
        f.setLayout(gb);
        b[0].setBackground(Color.white);
        b[1].setBackground(Color.white);
        gbc.insets=new Insets(3, 3, 3, 3);//组件的横向间距
        gbc.fill=GridBagConstraints.HORIZONTAL;//组件是否允许横向纵向扩大
        gbc.weightx=0;//组件大小变化的增量值
        addJComponent(l[0],0,0,1,1);
        addJComponent(l[1],0,1,1,1);
        gbc.weightx=1;
        addJComponent(tf[0],1,0,3,1);
        addJComponent(tf[1],1,1,3,1);
        gbc.insets=new Insets(6, 20, 6, 10);//组件的横向间距
        addJComponent(b[0],0,2,2,1);
        gbc.insets=new Insets(6, 10, 6, 20);//组件的横向间距
        addJComponent(b[1],2,2,2,1);
        b[0].addActionListener(this);
        b[1].addActionListener(this);

        f.setBounds(350,300,600,200);
        f.setVisible(true);

    }

    public void initDatabase() throws java.sql.SQLException,ClassNotFoundException{

        Class.forName("com.mysql.cj.jdbc.Driver");

        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/EightSix?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true","loghder","8426784");
        stmt=con.createStatement();

        //如果要创建的table已经存在的话，就对异常进行处理
        try {
            stmt.executeUpdate("create table users (Name varchar(10) primary key,Passwords varchar(10))");
        }catch(java.sql.SQLSyntaxErrorException e){
            System.out.println("数据表已经存在，无需创建，直接添加数据");
        }

    }

    public static void main(String [] args) throws Exception{
        UserWindow uw =new UserWindow();
        uw.initDatabase();
        uw.initWindow();
    }
}
