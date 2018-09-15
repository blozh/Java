package SevenTwo;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

class Area implements ActionListener {

    Socket s;
    BufferedWriter bw;
    BufferedReader br;
    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        if(actionEvent.getActionCommand()=="连接服务器") {
            try{
            s = new Socket("127.0.0.1", 30000);
            bw = new BufferedWriter(new OutputStreamWriter(s.getOutputStream()));
            br = new BufferedReader(new InputStreamReader(s.getInputStream()));
            if(br.readLine().equals("连接成功")) {
                ta.setText("服务器连接成功");
                b[0].setText("断开连接");
            }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if(actionEvent.getActionCommand()=="断开连接") {
            try{
                bw.write("请关闭连接");
                bw.newLine();
                bw.flush();

                br.close();
                bw.close();
                s.close();
                b[0].setText("连接服务器");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if(actionEvent.getActionCommand()=="求三角形面积")
        {
            try {
                bw.write("保持连接");
                bw.newLine();
                for (int i=0;i<3;i++) {
                    bw.write(tf[i].getText());
                    bw.newLine();
                }
                bw.flush();
                ta.setText("三角形的面积为"+br.readLine());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    JFrame f;
    GridBagLayout gb;
    GridBagConstraints gbc;
    Font font=new Font("Microsoft Yahei",Font.PLAIN,25);
    JButton[] b=new JButton[2];
    JLabel[] l=new JLabel[3];
    JTextField[] tf=new JTextField[3];
    JTextArea ta;

    void addJComponent (JComponent c,int x,int y,int gx,int gy){
        gbc.gridx=x;
        gbc.gridy=y;
        gbc.gridwidth=gx;
        gbc.gridheight=gy;
        gb.setConstraints(c,gbc);
        c.setFont(font);
        f.add(c);
    }
    void init(){
        f=new JFrame("客户端");
        gb=new GridBagLayout();
        gbc=new GridBagConstraints();
        f.setBounds(500,400,600,400);
        f.setLayout(gb);

        b[0]=new JButton("连接服务器");
        b[0].setBackground(Color.white);
        gbc.insets=new Insets(3, 3, 3, 3);//组件的横向间距
        gbc.fill=GridBagConstraints.HORIZONTAL;//组件是否允许横向纵向扩大
        addJComponent(b[0],0,0,1,3);

        gbc.fill=GridBagConstraints.BOTH;
        gbc.weighty=1;//是否允许网格缩放，以及网格缩放的比重
        gbc.weightx=0;
        for(int i=0;i<3;i++){
            l[i]=new JLabel();
            addJComponent(l[i],1,i,1,1);
        }

        l[0].setText("输入边A");
        l[1].setText("输入边B");
        l[2].setText("输入边C");

        gbc.weightx=1;
        for(int i=0;i<3;i++){
            tf[i]=new JTextField(i);
            addJComponent(tf[i],2,i,1,1);
        }

        gbc.weightx=0;
        gbc.fill=GridBagConstraints.HORIZONTAL;
        b[1]=new JButton("求三角形面积");
        b[1].setBackground(Color.white);

        gbc.weightx=1;
        gbc.weighty=3;
        addJComponent(b[1],0,3,1,3);

        gbc.fill=GridBagConstraints.BOTH;
        ta=new JTextArea();
        addJComponent(ta,1,3,2,3);
        ta.setEditable(false);

        f.setVisible(true);

        b[0].addActionListener(this);
        b[1].addActionListener(this);
    }
    public static void main(String [] args){
        new Area().init();
    }

}
