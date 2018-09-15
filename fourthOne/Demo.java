package fourthOne;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Arc2D;

import javax.swing.*;

public class Demo {
    public static void main(String[] args) {
        final JFrame f=new JFrame("显示整数数字");
        final JOptionPane d=new JOptionPane();
        //Font font=new Font("Microsoft YaHei",Font.PLAIN,18);
       // d.setFont(font);
        f.setLayout(new GridLayout(4,2,2,2));//设置为网格布局
        f.setBounds(710,290,400,200);
        //f.setFont(font);
        f.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent windowEvent) {
                System.exit(0);
            }
        });
        //组件 四个标签四个文本行
        JLabel L1= new JLabel("整数");
        JLabel L2= new JLabel("百位");
        JLabel L3= new JLabel("十位");
        JLabel L4= new JLabel("个位");
        final JTextField[] T=new JTextField[4];
        for(int i=0;i<4;i++){
            T[i]=new JTextField(3);
            if (i!=0)
               T[i].setEditable(false);
        }
        T[0].addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                try{
                    int x=(int)Double.parseDouble(T[0].getText());
                    T[1].setText(String.valueOf(x/100%10));
                    T[2].setText(String.valueOf(x/10%10));
                    T[3].setText(String.valueOf(x%10));
                }
                catch(NumberFormatException e)
                {
                    d.showMessageDialog(f,T[0].getText()+"不能转换成整数，请重新输入" , "消息", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });

        f.getContentPane().add(L1);
        f.getContentPane().add(T[0]);
        f.getContentPane().add(L2);
        f.getContentPane().add(T[1]);
        f.getContentPane().add(L3);
        f.getContentPane().add(T[2]);
        f.getContentPane().add(L4);
        f.getContentPane().add(T[3]);

        f.setVisible(true);
    }
}


