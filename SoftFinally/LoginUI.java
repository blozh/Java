package SoftFinally;

import org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper;
import org.jb2011.lnf.beautyeye.ch3_button.BEButtonUI;

import java.awt.BorderLayout;
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
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.UIManager;


public class LoginUI implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        if(actionEvent.getActionCommand().equals("登陆")){
            String str=tf[0].getText();
            String pw=tf[1].getText();
            int length=str.length(),rowCount;
            ResultSet rs=null;
            ResultSetMetaData rsmd=null;
            try {
                switch (length) {
                    case 5:
                        rs = stmt.executeQuery("select * from teacher where id='" + str + "' and password='"+pw+"'");
                        rowCount=0;
                        while(rs.next())
                            rowCount++;
                        if(rowCount!=0) {
                            new TeacherUI(str,stmt).initWindow();
                        }
                        else
                            JOptionPane.showMessageDialog(null, "您输入的用户名或密码不正确", "警告", JOptionPane.INFORMATION_MESSAGE);
                        break;
                    case 6:
                        rs = stmt.executeQuery("select * from student where id='" + str + "' and password='"+pw+"'");
                        rowCount=0;
                        while(rs.next())
                            rowCount++;
                        if(rowCount!=0)
                            new StudentUI(str,stmt).initWindow();
                        else
                            JOptionPane.showMessageDialog(null, "您输入的用户名或密码不正确", "警告", JOptionPane.INFORMATION_MESSAGE);
                        break;
                    case 8:
                        rs = stmt.executeQuery("select * from manager where id='" + str + "' and password='"+pw+"'");
                        rowCount=0;
                        while(rs.next())
                            rowCount++;
                        if(rowCount!=0)
                            new ManagerMainWindow(stmt).initWindow();
                        else
                            JOptionPane.showMessageDialog(null, "您输入的用户名或密码不正确", "警告", JOptionPane.INFORMATION_MESSAGE);
                        break;
                    default:
                        JOptionPane.showMessageDialog(null, "您输入的用户名或密码不正确", "警告", JOptionPane.INFORMATION_MESSAGE);
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        if(actionEvent.getActionCommand().equals("注册")){
            String str=tf[0].getText();
            String pw=tf[1].getText();
            String regex1 = ".*[a-zA-z].*";
            boolean result3 = pw.matches(regex1);
            if(pw.length()<8){
                JOptionPane.showMessageDialog(null, "注册的密码要求位数大于8", "警告", JOptionPane.INFORMATION_MESSAGE);
            }
            else if (result3){
                try {
                    int ttt=stmt.executeUpdate("insert into student(id,password) values('"+str+"','"+pw+"')");
                    if(ttt==0){
                        JOptionPane.showMessageDialog(null, "此账号已经注册，请检查", "警告", JOptionPane.INFORMATION_MESSAGE);
                    }
                    JOptionPane.showMessageDialog(null, "注册成功", "消息", JOptionPane.INFORMATION_MESSAGE);
                } catch (SQLException ee) {
                    JOptionPane.showMessageDialog(null, "此账号已经注册，请检查", "警告", JOptionPane.INFORMATION_MESSAGE);
                    ee.printStackTrace();
                }
            }
            else {
                JOptionPane.showMessageDialog(null, "注册的密码要求不能是纯数字", "警告", JOptionPane.INFORMATION_MESSAGE);
            }

        }
    }
    Font font=new Font("XHei OSX",Font.PLAIN,20);
    JLabel[] l=new JLabel[2];
    JTextField[] tf=new JTextField[2];
    JButton b=new JButton("登陆");
    GridBagLayout gb=new GridBagLayout();
    GridBagConstraints gbc=new GridBagConstraints();
    JFrame p=new JFrame("在线课程管理系统");
    JPanel f=new JPanel();
    String [] str=new String[]{"用户名：","密码："};
    Statement stmt=null;

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

    void Connect(){
        try {
            Class.forName("org.sqlite.JDBC");
            Connection conn= DriverManager.getConnection("jdbc:sqlite:Z:\\SoftFinally.db");
            stmt=conn.createStatement();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    LoginUI(){
        f.setLayout(gb);
        int px=10;
        gbc.insets=new Insets(px,px,px,px);
        gbc.weighty=1;
        gbc.weightx=0;
        gbc.fill= GridBagConstraints.HORIZONTAL;
        for (int i = 0; i < l.length; i++) {
            l[i]=new JLabel(str[i]);
            addJComponent(l[i],0,i,1,1);
        }
        gbc.weightx=1;
        tf[0]=new JTextField();
        addJComponent(tf[0],1,0,5,1);
        JPasswordField temp=new JPasswordField();
        temp.setEchoChar('*');
        tf[1]=temp;
        addJComponent(tf[1],1,1,5,1);
        gbc.fill= GridBagConstraints.NONE;
        addJComponent(b,0,2,3,1);
        b.setUI(new BEButtonUI().setNormalColor(BEButtonUI.NormalColor.blue));
        b.setForeground(Color.white);
        JButton bb= new JButton("注册");
        bb.addActionListener(this);
        addJComponent(bb,3,2,2,1);
        b.addActionListener(this);
        p.setLayout(new BorderLayout());
        p.add(f,BorderLayout.SOUTH);
        JLabel jppp=new JLabel(new ImageIcon("Z:\\tmb.png"));
        jppp.setOpaque(true);
        p.add(new JPanel().add(jppp));
        p.setSize(400,500);
        p.setLocationRelativeTo(null);
        p.setVisible(true);
        p.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Connect();
    }

    static void setStyle(){
        try
        {
            org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper.launchBeautyEyeLNF();
            BeautyEyeLNFHelper.frameBorderStyle=BeautyEyeLNFHelper.frameBorderStyle.translucencyAppleLike;
            //BeautyEyeLNFHelper.frameBorderStyle=BeautyEyeLNFHelper.frameBorderStyle.generalNoTranslucencyShadow;
            UIManager.put("RootPane.setupButtonVisible", false);
            BeautyEyeLNFHelper.translucencyAtFrameInactive = false;
        }catch(Exception e){

        }
    }
    public static void main(String[] args) {
        setStyle();
        new LoginUI();
    }
}
