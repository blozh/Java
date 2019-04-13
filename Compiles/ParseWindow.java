package Compiles;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.Enumeration;

import javax.swing.*;
import javax.swing.table.*;

class ParseWindow extends ModWindow implements ItemListener {
    //用于表格变色的类
    class EvenOddRenderer implements TableCellRenderer {

        DefaultTableCellRenderer DEFAULT_RENDERER =
                new DefaultTableCellRenderer();

        public Component getTableCellRendererComponent(JTable table, Object value,
                                                       boolean isSelected, boolean hasFocus, int row, int column) {
            Component renderer =
                    DEFAULT_RENDERER.getTableCellRendererComponent(table, value,
                            isSelected, hasFocus, row, column);
            Color foreground = Color.BLACK;
            Color background = Color.WHITE;

            String temp = table.getValueAt(row, column).toString();
            if (temp.length() != 0) {
                if (temp.substring(temp.length() - 1, temp.length()).equals(" ")) {
                    foreground = Color.BLACK;
                    background = new Color(0, 176, 244);
                }
            } else {
                foreground = Color.BLACK;
                background = Color.WHITE;
            }
            renderer.setForeground(foreground);
            renderer.setBackground(background);
            return renderer;
        }
    }

    JLabel label = new JLabel("语法分析方法：");
    JButton b = new JButton("分析");
    ButtonGroup bg = new ButtonGroup();
    String[] radioButtonText = {"算符优先", "SLR(1)","递归下降"};
    JRadioButton[] radioButton = new JRadioButton[3];


    DefaultTableModel model2 = new DefaultTableModel();
    JTable t2 = new JTable(model2);
    JScrollPane sp2 = new JScrollPane(t2);

    String[] taText = {"步骤", "分析栈", "符号栈", "表达式"};
    DefaultTableModel model = new DefaultTableModel();
    JTable t = new JTable(model);
    JScrollPane sp = new JScrollPane(t);

    JButton b3 = new JButton("保存");
    String[] taText3 = {"中间代码"};
    DefaultTableModel model3 = new DefaultTableModel();
    JTable t3 = new JTable(model3);
    JScrollPane sp3 = new JScrollPane(t3);

    int flag = 0;//表示是否为算符运算符，用于点击分析按钮时的判断

    ParseWindow() {
        panel.setLayout(gb);
        gbc.weightx = 3;//不为0时，网格横向扩大
        gbc.fill = GridBagConstraints.HORIZONTAL;//组件允许横向扩大
        addJComponent(label, 0, 0, 2, 1);
        addJComponent(b, 5, 0, 2, 1);
        b.setBackground(Color.white);
        b.addActionListener(this);
        addJComponent(b3, 7, 0, 1, 1);
        b3.setBackground(Color.white);
        b3.addActionListener(this);

        for (int i = 0; i < 3; i++) {
            radioButton[i] = new JRadioButton(radioButtonText[i], true);
            radioButton[i].addItemListener(this);
            bg.add(radioButton[i]);
            addJComponent(radioButton[i], 2 + i, 0, 1, 1);
        }
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weighty = 5;
        addJComponent(sp2, 0, 1, 5, 1);
        model.setColumnIdentifiers(taText);
        gbc.weighty = 2;
        addJComponent(sp, 5, 1, 2, 2);


        model3.setColumnIdentifiers(taText3);
        gbc.weightx = 1;
        gbc.weighty = 2;
        addJComponent(sp3, 7, 1, 1, 2);

        t.getTableHeader().setFont(font);
        t.setFont(font);
        t.setRowHeight(30);
        t2.getTableHeader().setFont(font);
        t2.setFont(font);
        t2.setRowHeight(30);
        t2.setDefaultRenderer(Object.class, new EvenOddRenderer());
        sheetChanged();

        DefaultTableCellRenderer r = new DefaultTableCellRenderer();
        r.setHorizontalAlignment(JLabel.CENTER);
        t3.setDefaultRenderer(Object.class, r);
        t3.getTableHeader().setFont(font);
        t3.setFont(new Font("XHei Apple",Font.PLAIN,24));
        t3.setRowHeight(50);

    }

    void sheetChanged() {
        Enumeration<AbstractButton> radioBtns = bg.getElements();
        while (radioBtns.hasMoreElements()) {
            AbstractButton btn = radioBtns.nextElement();
            //算符优先
            if (btn.isSelected() && btn.getText() == radioButtonText[0]) {
                flag = 0;
                String[][] sheet = new String[][]{
                        //     "+","-","*","/",   "i","(",")","#"
                        {"+", ">", ">", "<", "", "<", "<", ">", ">"},
                        {"-", ">", ">", "<", "", "<", "<", ">", ">"},
                        {"*", ">", ">", ">", ">", "<", "<", ">", ">"},
                        {"/", ">", ">", ">", ">", "<", "<", ">", ">"},

                        {"i", ">", ">", ">", ">", "", "", ">", ">"},
                        {"(", "<", "<", "<", "<", "<", "<", "=", ""},
                        {")", ">", ">", ">", ">", "", "", ">", ">"},
                        {"#", "<", "<", "<", "<", "<", "<", "", "="},
                };
                model2.setColumnIdentifiers(new String[]{"", "+", "-", "*", "/", "i", "(", ")", "#"});
                model2.setRowCount(0);//清空表格
                for (int i = 0; i < sheet.length; i++) {
                    model2.addRow(sheet[i]);
                }
            }
            //LR（0）
            if (btn.isSelected() && btn.getText() == radioButtonText[1]) {
                flag = 1;
                String[][] sheet = new String[][]{
                        {"0", "S4", "", "", "", "", "", "S5", "", "1", "2", "3"},
                        {"1", "", "", "S6", "S7", "", "", "", "Acc", "", "", ""},
                        {"2", "", "R3", "R3", "R3", "S8", "S9", "", "R3", "", "", ""},
                        {"3", "", "R6", "R6", "R6", "R6", "R6", "", "R6", "", "", ""},
                        {"4", "S4", "", "", "", "", "", "S5", "", "10", "2", "3"},
                        {"5", "", "R8", "R8", "R8", "R8", "R8", "", "R8", "", "", ""},
                        {"6", "S4", "", "", "", "", "", "S5", "", "", "11", "3"},
                        {"7", "S4", "", "", "", "", "", "S5", "", "", "12", "3"},
                        {"8", "S4", "", "", "", "", "", "S5", "", "", "", "13"},
                        {"9", "S4", "", "", "", "", "", "S5", "", "", "", "14"},
                        {"10", "", "S15", "S6", "S7", "", "", "", "", "", "", ""},
                        {"11", "", "R1", "R1", "R1", "S8", "S9", "", "R1", "", "", ""},
                        {"12", "", "R2", "R2", "R2", "S8", "S9", "", "R2", "", "", ""},
                        {"13", "", "R4", "R4", "R4", "R4", "R4", "", "R4", "", "", ""},
                        {"14", "", "R5", "R5", "R5", "R5", "R5", "", "R5", "", "", ""},
                        {"15", "", "R7", "R7", "R7", "R7", "R7", "", "R7", "", "", ""}
                };
                model2.setColumnIdentifiers(new String[]{"", "(", ")", "+", "-", "*", "/", "i", "#", "E", "T", "F"});
                model2.setRowCount(0);//清空表格
                for (int i = 0; i < sheet.length; i++) {
                    model2.addRow(sheet[i]);
                }
            }
            if (btn.isSelected() && btn.getText() == radioButtonText[2]) {
                flag = 2;
                String[][] sheet = new String[][]{
                        {"E","(E)Z21","iZ21","",""},
                        {"T","(E)Z22","iZ22","",""},
                        {"F","(E)Z23","iZ23","",""},
                        {"Z11","+TZ11","-TZ11","ε",""},
                        {"Z12","+TZ12","-TZ12","ε",""},
                        {"Z13","+TZ13","-TZ13","ε",""},
                        {"Z21","Z11","*FZ21","/FZ21","ε"},
                        {"Z22","Z12","*FZ22","/FZ22","ε"},
                        {"Z23","Z13","*FZ23","/FZ23","ε"}
                };
                model2.setColumnIdentifiers(new String[]{"非终结符", "产生式", "", "", ""});
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
        if (actionEvent.getActionCommand() == "分析"){
            if (flag==0) {
                new Thread() {
                    @Override
                    public void run() {
                        Analysis.ParseAnalysis(model, model2);
                    }
                }.start();
            }
            if (flag==1) {
                new Thread() {
                    @Override
                    public void run() {
                        Analysis.ParseAnalysis_SLR(model, model2,model3);
                    }
                }.start();
            }
            if (flag==2) {
                new Thread() {
                    @Override
                    public void run() {
                        Analysis.DGnum=0;
                        Analysis.ParseAnalysis_DG(model,model2);
                    }
                }.start();
            }
        }

        if (actionEvent.getActionCommand() == "保存") {
            String temp="";
            for(int i=0;i<model3.getRowCount();i++){
                temp+=model3.getValueAt(i,0).toString();
                temp+="\n";
            }
            JFileChooser jfc = new JFileChooser();
            jfc.setFileSelectionMode(JFileChooser.SAVE_DIALOG);
            jfc.showDialog(new JLabel(), "保存");
            File file = jfc.getSelectedFile();
            try {
                OutputStreamWriter write = new OutputStreamWriter(new FileOutputStream(file));
                BufferedWriter bw = new BufferedWriter(write);
                bw.write(temp);
                bw.flush();
                write.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}