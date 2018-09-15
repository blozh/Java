package fourthTwo;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

class Calculator{
    private JFrame f=new JFrame("计算器");
    private Font font=new Font("Microsoft Yahei",Font.PLAIN,30);
    private GridBagLayout gb=new GridBagLayout();
    private GridBagConstraints gbc=new GridBagConstraints();
    private JButton[] b=new JButton[20];
    private JTextField show=new JTextField("0");
    private double first=0;//第一运算数
    private String key="";//运算符

    void display(String z){//把字符串z显示到文本框中
        if(z.length()>10)
            z=z.substring(0,11);//把字符串的长度限制在12位
        double x=Double.parseDouble(z);
        int y=(int)x;
        if(x==y)
            show.setText(String.valueOf(y));
        else
            show.setText(String.valueOf(x));
    }

    class NumKeyListener implements ActionListener{//用于数字键的事件监听器
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            String temp=show.getText();
            String key=actionEvent.getActionCommand();//获取事件源名称
            display(temp+key);
        }
    }

    class CalculateListener implements ActionListener{//运算符，包括加减乘除，小数点，正负

        void equal(String temp){
            if(Calculator.this.key=="＋")
                first=first+Double.parseDouble(temp);
            if(Calculator.this.key=="－")
                first=first-Double.parseDouble(temp);
            if(Calculator.this.key=="×")
                first=first*Double.parseDouble(temp);
            if(Calculator.this.key=="÷")
                first=first/Double.parseDouble(temp);
        }

        public void actionPerformed(ActionEvent actionEvent) {
            String temp=show.getText();
            String key=actionEvent.getActionCommand();
            switch(key){
                case "＋":{}
                case "－":{}
                case "×":{}
                case "÷":{
                    Calculator.this.key=key;
                    first=Double.parseDouble(show.getText());
                    show.setText("0");
                    break;
                }
                case " √ ":{
                    first=Math.sqrt(Double.parseDouble(temp));
                    display(String.valueOf(first));
                    break;
                }
                case "AC":{
                    first=0;
                    show.setText("0");
                    break;
                }
                case "CE":{
                    if(temp.length()>1){
                        first=Double.parseDouble(temp.substring(0,temp.length()-1));
                    }
                    else{
                        first=0;
                    }
                    display(String.valueOf(first));
                    break;
                }
                case "±":{
                    first=0-Double.parseDouble(temp);
                    display(String.valueOf(first));
                    break;
                }
                case "·":{
                    show.setText(temp+".");
                    break;
                }
                case "＝":{
                    equal(temp);
                    display(String.valueOf(first));
                    break;
                }
            }

        }
    }

    //组件设定&添加
    void addJComponent(JComponent b){
        b.setFont(font);
        gb.setConstraints(b,gbc);
        f.add(b);
    }

    void init(){
        f.setLayout(gb);
        gbc.fill=GridBagConstraints.BOTH;//所有组件都可以在横向和纵向上扩大
        gbc.weightx=1;
        gbc.weighty=1;
        gbc.gridwidth=GridBagConstraints.REMAINDER;
        //设置文本框右对齐
        show.setHorizontalAlignment(JTextField.RIGHT);
        show.setOpaque(false);//设置透明
        show.setEditable(false);
        addJComponent(show);
        show.setFont(new Font("Microsoft Yahei",Font.PLAIN,40));

        for(int i=0;i<20;i++){
            b[i]=new JButton("按钮"+i);
            b[i].setBackground(Color.white);
            gbc.insets=new Insets(3, 3, 3, 3);//按钮四周间距
            gbc.gridwidth=1;//设置宽度为1，也即“取消设置为最后一个的状态
            if(i%4==3)
                gbc.gridwidth=GridBagConstraints.REMAINDER;
            addJComponent(b[i]);
        }

        //设置按钮文字
        b[0].setText(" √ ");//根号前后加空格是为了和AC那一列的宽度一样
        b[1].setText("AC");//全清
        b[2].setText("CE");//退格
        b[3].setText("÷");
        b[4].setText("7");
        b[5].setText("8");
        b[6].setText("9");
        b[7].setText("×");
        b[8].setText("4");
        b[9].setText("5");
        b[10].setText("6");
        b[11].setText("－");
        b[12].setText("1");
        b[13].setText("2");
        b[14].setText("3");
        b[15].setText("＋");
        b[16].setText("±");
        b[17].setText("0");
        b[18].setText("·");
        b[19].setText("＝");

        /*绑定事件*/
        //数字键
        b[4].addActionListener(new NumKeyListener());
        b[5].addActionListener(new NumKeyListener());
        b[6].addActionListener(new NumKeyListener());
        b[8].addActionListener(new NumKeyListener());
        b[9].addActionListener(new NumKeyListener());
        b[10].addActionListener(new NumKeyListener());
        b[12].addActionListener(new NumKeyListener());
        b[13].addActionListener(new NumKeyListener());
        b[14].addActionListener(new NumKeyListener());
        b[17].addActionListener(new NumKeyListener());
        //运算键
        b[18].addActionListener(new CalculateListener());
        b[19].addActionListener(new CalculateListener());
        b[16].addActionListener(new CalculateListener());
        b[15].addActionListener(new CalculateListener());
        b[11].addActionListener(new CalculateListener());
        b[7].addActionListener(new CalculateListener());
        b[3].addActionListener(new CalculateListener());
        b[2].addActionListener(new CalculateListener());
        b[1].addActionListener(new CalculateListener());
        b[0].addActionListener(new CalculateListener());

        f.pack();
        f.setVisible(true);
    }

    public static void main(String[] args){
        new Calculator().init();
    }
}