package SevenTwo;

import java.awt.*;
import javax.swing.*;

public class MyFrame extends Thread{
    JFrame f;
    GridBagLayout gb;
    GridBagConstraints gbc;
    Font font=new Font("Microsoft Yahei",Font.PLAIN,25);
    JButton b=new JButton("发送");
    JTextArea[] ta=new JTextArea[2];

    void addJComponent (JComponent c, int x, int y, int gx, int gy){
        gbc.gridx=x;
        gbc.gridy=y;
        gbc.gridwidth=gx;
        gbc.gridheight=gy;
        gb.setConstraints(c,gbc);
        c.setFont(font);
        f.add(c);
    }
    public void run(){
        f=new JFrame(this.getName());
        gb=new GridBagLayout();
        gbc=new GridBagConstraints();
        f.setBounds(500,100,600,800);
        f.setLayout(gb);

        b.setBackground(Color.white);

        gbc.insets=new Insets(3, 3, 3, 3);//组件的横向间距
        gbc.fill=GridBagConstraints.HORIZONTAL;//组件是否允许横向纵向扩大
        gbc.weightx=1;
        addJComponent(b,0,2,1,1);
        for(int i=0;i<2;i++){
            ta[i]=new JTextArea();
        }
        gbc.fill=GridBagConstraints.BOTH;
        gbc.weighty=5;//是否允许网格缩放，以及网格缩放的比重
        addJComponent(ta[0],0,0,1,1);
        ta[0].setEditable(false);
        gbc.weighty=1;
        addJComponent(ta[1],0,1,1,1);

        f.setVisible(true);
        f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }
}
