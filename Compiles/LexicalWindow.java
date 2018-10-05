package Compiles;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.event.ActionEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

class LexicalWindow extends ModWindow{
    private JButton[] buttons=new JButton[4];
    private JTextArea[] textAreas =new JTextArea[2];
    private JScrollPane[] scrollPanes=new JScrollPane[2];

    LexicalWindow(){
        buttons[0]=new JButton("打开");
        buttons[1]=new JButton("分析");
        buttons[2]=new JButton("保存");
        buttons[3]=new JButton("状态转换图");
        textAreas[0]=new JTextArea();
        textAreas[1]=new JTextArea();
        textAreas[1].setEditable(false);
        scrollPanes[0]=new JScrollPane(textAreas[0]);
        scrollPanes[1]=new JScrollPane(textAreas[1]);
        panel.setLayout(gb);
        gbc.weightx=1;//不为1时，网格横向扩大
        gbc.fill= GridBagConstraints.HORIZONTAL;//组件允许横向扩大
        for (int i=0;i<4;i++){
            addJComponent(buttons[i],i,0,1,1);
            buttons[i].addActionListener(this);//添加监听
            buttons[i].setBackground(Color.WHITE);
        }
        gbc.weighty=1;
        gbc.fill=GridBagConstraints.BOTH;
        for (int i=0;i<2;i++){
            textAreas[i].setFont(font);
            addJComponent(scrollPanes[i],0,i+1,5,1);
        }
        textAreas[1].setBackground(new Color(230,230,230));
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        if(actionEvent.getActionCommand()=="打开"){
            input="";
            JFileChooser jfc=new JFileChooser();
            jfc.setFileSelectionMode(JFileChooser.OPEN_DIALOG );
            jfc.showDialog(new JLabel(), "打开代码文件");
            File file=jfc.getSelectedFile();
            try {
                InputStreamReader read = new InputStreamReader(new FileInputStream(file));
                BufferedReader bufferedReader = new BufferedReader(read);
                String temp;
                while ((temp = bufferedReader.readLine()) != null) {
                    input=input+temp+'\n';
                }
                read.close();
            }catch (IOException e){
                e.printStackTrace();
            }
            textAreas[0].setText(input);
            textAreas[0].setCaretPosition(0);//光标置顶
        }
        if(actionEvent.getActionCommand()=="保存"){
            JFileChooser jfc=new JFileChooser();
            jfc.setFileSelectionMode(JFileChooser.SAVE_DIALOG);
            jfc.showDialog(new JLabel(),"保存");
            File file=jfc.getSelectedFile();
            output=textAreas[1].getText();
            try {
                OutputStreamWriter write=new OutputStreamWriter(new FileOutputStream(file));
                BufferedWriter bw=new BufferedWriter(write);
                bw.write(output);
                bw.flush();
                write.close();
            }catch (IOException e){
                e.printStackTrace();
            }
        }
        if(actionEvent.getActionCommand()=="分析"){
            input=textAreas[0].getText();
            textAreas[1].setText(Analysis.LexicalAnalysis(input));
            textAreas[1].setCaretPosition(0);//光标置顶
        }
        if(actionEvent.getActionCommand()=="状态转换图"){
            JFrame f=new JFrame("状态转换图");
            f.setLayout(new FlowLayout());
            JLabel label=new JLabel();
            label.setIcon(new ImageIcon("C:\\Users\\loghd\\Desktop\\编译原理\\词法分析状态转换图.png"));
            JScrollPane jsp=new JScrollPane(label);
            jsp.setPreferredSize(new Dimension(1200,900));
            jsp.setBackground(Color.white);
            jsp.getVerticalScrollBar().setUnitIncrement(20);//设置滚动速度
            f.add(jsp);
            f.setVisible(true);
            f.pack();
        }
        if(actionEvent.getActionCommand()=="词法分析过程"){
        }

    }
}
