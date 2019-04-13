package SoftEngineer;

import com.alibaba.fastjson.JSON;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class LoginUI implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        RegisterService.Run(tf,ip,f);
    }
    String ip="172.20.10.4";
    Font font=new Font("Simsun",Font.PLAIN,15);
    JLabel[] l=new JLabel[2];
    JTextField[] tf=new JTextField[2];
    JButton b=new JButton("登陆");
    GridBagLayout gb=new GridBagLayout();
    GridBagConstraints gbc=new GridBagConstraints();
    JFrame f=new JFrame("综合测评管理系统");
    String [] str=new String[]{"用户名：","密码："};

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

    LoginUI(){
        f.setLayout(gb);
        gbc.weighty=1;
        gbc.weightx=0;
        gbc.fill= GridBagConstraints.HORIZONTAL;
        for (int i = 0; i < l.length; i++) {
            l[i]=new JLabel(str[i]);
            addJComponent(l[i],0,i,1,1);
        }
        gbc.weightx=1;
        tf[0]=new JTextField();
        addJComponent(tf[0],1,0,3,1);
        JPasswordField temp=new JPasswordField();
        temp.setEchoChar('*');
        tf[1]=temp;
        addJComponent(tf[1],1,1,3,1);
        gbc.fill= GridBagConstraints.NONE;
        addJComponent(b,0,2,4,1);
        b.addActionListener(this);
        f.setSize(600,200);
        f.setLocationRelativeTo(null);
        f.setVisible(true);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public static void main(String[] args) {
        new LoginUI();
    }
}
