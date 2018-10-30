package Compiles;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Enumeration;

import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

class ParseWindow extends ModWindow implements ItemListener {

    JLabel label=new JLabel("语法分析方法：");
    JButton b=new JButton("分析");
    ButtonGroup bg=new ButtonGroup();
    String[] radioButtonText={"算符优先","SLR(1)"};
    JRadioButton[] radioButton=new JRadioButton[2];


    DefaultTableModel model2=new DefaultTableModel();
    JTable t2=new JTable(model2) {
        @Override
        public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
            int modelRow = convertRowIndexToModel(row);
            int modelColumn = convertColumnIndexToModel(column);
            Component comp = super.prepareRenderer(renderer, row, column);
            if (!isRowSelected(modelRow)) {
                if (modelRow == row && modelColumn == column)                   //此处加入条件判断
                    comp.setBackground(Color.WHITE);
                else                                                     //不符合条件的保持原表格样式
                    comp.setBackground(Color.BLUE);
            }
            return comp;
        }
    };
    JScrollPane sp2=new JScrollPane(t2);

    String [] taText={"步骤","分析栈","符号栈","表达式"};
    DefaultTableModel model=new DefaultTableModel();
    JTable t=new JTable(model);
    JScrollPane sp=new JScrollPane(t);

    boolean isSuanfu=false;//表示是否为算符运算符，用于点击分析按钮时的判断

    ParseWindow(){
        panel.setLayout(gb);
        gbc.weightx=1;//不为0时，网格横向扩大
        gbc.fill= GridBagConstraints.HORIZONTAL;//组件允许横向扩大
        addJComponent(label,0,0,2,1);
        addJComponent(b,4,0,2,1);
        b.setBackground(Color.white);
        b.addActionListener(this);
        for (int i = 0; i < 2; i++) {
            radioButton[i]=new JRadioButton(radioButtonText[i],true);
            radioButton[i].addItemListener(this);
            bg.add(radioButton[i]);
            addJComponent(radioButton[i],2+i,0,1,1);
        }
        gbc.fill=GridBagConstraints.BOTH;
        gbc.weighty=2;
        addJComponent(sp2,0,1,6,1);
        model.setColumnIdentifiers(taText);
        gbc.weighty=4;
        addJComponent(sp,0,4,6,1);

        t.getTableHeader().setFont(font);
        t.setFont(font);
        t.setRowHeight(30);
        t2.getTableHeader().setFont(font);
        t2.setFont(font);
        t2.setRowHeight(30);
        sheetChanged();
    }

    void sheetChanged() {
        Enumeration<AbstractButton> radioBtns = bg.getElements();
        while (radioBtns.hasMoreElements()) {
            AbstractButton btn = radioBtns.nextElement();
            //算符优先
            if (btn.isSelected() && btn.getText() == radioButtonText[0]) {
                isSuanfu=true;
                String[][] sheet = new String[][]{
                        //     "+","-","*","/",   "i","(",")","#"
                        {"+", ">", ">", "<", "",    "<", "<", ">",">"},
                        {"-", ">", ">", "<", "",    "<", "<", ">", ">"},
                        {"*", ">", ">", ">", ">",    "<", "<", ">", ">"},
                        {"/", ">", ">", ">", ">",    "<", "<", ">", ">"},

                        {"i", ">", ">", ">", ">",    "", "", ">", ">"},
                        {"(", "<", "<", "<", "<",    "<", "<", "=", ""},
                        {")", ">", ">", ">", ">",    "", "", ">", ">"},
                        {"#", "<", "<", "<", "<",    "<", "<", "", "="},
                };
                model2.setColumnIdentifiers(new String[]{"","+","-","*","/","i","(",")","#"});
                model2.setRowCount(0);//清空表格
                for (int i = 0; i < sheet.length; i++) {
                    model2.addRow(sheet[i]);
                }
            }
            //LR（0）
            if (btn.isSelected() && btn.getText() == radioButtonText[1]) {
                isSuanfu=false;
                String[][] sheet = new String[][]{
                        {"0","S4","","","","","","S5","","1","2","3"},
                        {"1","","","S6","S7","","","","Acc","","",""},
                        {"2","","R3","R3","R3","S8","S9","","R3","","",""},
                        {"3","","R6","R6","R6","R6","R6","","R6","","",""},
                        {"4","S4","","","","","","S5","","10","2","3"},
                        {"5","","R8","R8","R8","R8","R8","","R8","","",""},
                        {"6","S4","","","","","","S5","","","11","3"},
                        {"7","S4","","","","","","S5","","","12","3"},
                        {"8","S4","","","","","","S5","","","","13"},
                        {"9","S4","","","","","","S5","","","","14"},
                        {"10","","S15","S6","S7","","","","","","",""},
                        {"11","","R1","R1","R1","S8","S9","","R1","","",""},
                        {"12","","R2","R2","R2","S8","S9","","R2","","",""},
                        {"13","","R4","R4","R4","R4","R4","","R4","","",""},
                        {"14","","R5","R5","R5","R5","R5","","R5","","",""},
                        {"15","","R7","R7","R7","R7","R7","","R7","","",""}
                };
                model2.setColumnIdentifiers(new String[]{"","(",")","+","-","*","/","i","#","E","T","F"});
                model2.setRowCount(0);//清空表格
                for (int i = 0; i < sheet.length; i++) {
                    model2.addRow(sheet[i]);
                }
            }

        }
    }

    @Override
    public void itemStateChanged(ItemEvent itemEvent) {
        sheetChanged();
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        if(isSuanfu){
            new Thread(){
                @Override
                public void run() {
                    Analysis.ParseAnalysis(model,model2);
                }
            }.start();
        }
        if(!isSuanfu){
            new Thread(){
                @Override
                public void run() {
                    Analysis.ParseAnalysis_SLR(model,model2);
                }
            }.start();
        }
    }
}