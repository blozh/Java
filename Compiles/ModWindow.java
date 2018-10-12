package Compiles;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComponent;
import javax.swing.JPanel;

abstract class ModWindow implements ActionListener{
    JPanel panel=new JPanel();
    GridBagLayout gb=new GridBagLayout();
    GridBagConstraints gbc=new GridBagConstraints();
    Font font=new Font("XHei Apple",Font.PLAIN,16);
    String input="";
    String output="";
    //添加组件，横坐标x 纵坐标y gx为横向占据网格数 x以0为开始
    void addJComponent (JComponent c, int x, int y, int gx, int gy){
        gbc.gridx=x;
        gbc.gridy=y;
        gbc.gridwidth=gx;
        gbc.gridheight=gy;
        gb.setConstraints(c,gbc);
        c.setFont(font);
        panel.add(c);
    }

    JPanel getPanel(){
        return panel;
    }


}