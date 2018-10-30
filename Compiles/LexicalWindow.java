package Compiles;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.RenderingHints;
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
import java.util.ArrayList;

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
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.table.DefaultTableModel;

class LexicalWindow extends ModWindow {
    private JButton[] buttons = new JButton[4];
    private JTextArea[] textAreas = new JTextArea[2];
    private JScrollPane[] scrollPanes = new JScrollPane[3];
    private DefaultTableModel model = new DefaultTableModel();
    private JTable table = new JTable(model);

    LexicalWindow() {
        model.setColumnIdentifiers(new String[]{"类别", "单词"});
        buttons[0] = new JButton("打开");
        buttons[1] = new JButton("分析");
        buttons[2] = new JButton("保存");
        buttons[3] = new JButton("状态转换图");
        textAreas[0] = new JTextArea();
        textAreas[1] = new JTextArea();
        textAreas[1].setEditable(false);
        scrollPanes[0] = new JScrollPane(textAreas[0]);
        scrollPanes[1] = new JScrollPane(textAreas[1]);
        scrollPanes[2] = new JScrollPane(table);
        panel.setLayout(gb);
        gbc.weightx = 1;//不为0时，网格横向扩大
        gbc.fill = GridBagConstraints.HORIZONTAL;//组件允许横向扩大
        for (int i = 0; i < 4; i++) {
            addJComponent(buttons[i], i, 0, 1, 1);
            buttons[i].addActionListener(this);//添加监听
            buttons[i].setBackground(Color.WHITE);
        }
        gbc.fill = GridBagConstraints.BOTH;
        for (int i = 0; i < 2; i++) {
            textAreas[i].setFont(font);
            gbc.weighty = 6 - i * 5;//纵向扩大程度
            addJComponent(scrollPanes[i], 0, i + 1, 3, 1);
        }
        addJComponent(scrollPanes[2], 3, 1, 1, 2);
        textAreas[1].setBackground(new Color(230, 230, 230));
    }


    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        if (actionEvent.getActionCommand() == "打开") {
            input = "";
            JFileChooser jfc = new JFileChooser();
            jfc.setFileSelectionMode(JFileChooser.OPEN_DIALOG);
            jfc.showDialog(new JLabel(), "打开代码文件");
            File file = jfc.getSelectedFile();
            try {
                InputStreamReader read = new InputStreamReader(new FileInputStream(file));
                BufferedReader bufferedReader = new BufferedReader(read);
                String temp;
                while ((temp = bufferedReader.readLine()) != null) {
                    input = input + temp + '\n';
                }
                read.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            textAreas[0].setText(input);
            textAreas[0].setCaretPosition(0);//光标置顶
        }
        if (actionEvent.getActionCommand() == "保存") {
            JFileChooser jfc = new JFileChooser();
            jfc.setFileSelectionMode(JFileChooser.SAVE_DIALOG);
            jfc.showDialog(new JLabel(), "保存");
            File file = jfc.getSelectedFile();
            try {
                OutputStreamWriter write = new OutputStreamWriter(new FileOutputStream(file));
                BufferedWriter bw = new BufferedWriter(write);
                bw.write(output);
                bw.flush();
                write.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (actionEvent.getActionCommand() == "分析") {
            model.setRowCount(0);//清空表格
            input = textAreas[0].getText();
            Analysis.LexicalAnalysis(input);//分析字符串
            ArrayList<String> values = Analysis.getValues();
            ArrayList<String> types = Analysis.getTypes();
            for (int i = 0; i < values.size(); i++) {
                model.addRow(new String[]{types.get(i), values.get(i)});//必须先添加一行才能赋值，当然也可以addRow一个向量
            }
            ArrayList<String> errors = Analysis.getErrors();
            ArrayList<String> errorvalues = Analysis.getErrorvalues();
            ArrayList<Integer> errorsRows = Analysis.getErrorsRows();
            if (errors.isEmpty()) {
                textAreas[1].setText("NO ERROR!!");
            } else {
                output = "";
                for (int i = 0; i < errors.size(); i++) {
                    output += (errorsRows.get(i) + " 行：" + errorvalues.get(i) + "  " + errors.get(i) + '\n');
                }
                textAreas[1].setText(output);
                output += "---------------------\n";
                for (int i = 0; i < values.size(); i++) {
                    output += ("(" + types.get(i) + "," + values.get(i) + ")" + '\n');
                }
            }
        }
        if (actionEvent.getActionCommand() == "状态转换图") {
            JFrame f = new JFrame("状态转换图");
            //f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            f.setSize(800, 800);
            f.setLocationRelativeTo(null);
            DrawPanel dp = new DrawPanel();
            dp.setPreferredSize(new Dimension(3000, 3000));
            JScrollPane jsp = new JScrollPane(dp);
            jsp.setPreferredSize(new Dimension(1000, 600));
            jsp.getVerticalScrollBar().setUnitIncrement(20);//设置滚动速度
            f.add(jsp);
            f.setExtendedState(JFrame.MAXIMIZED_BOTH);
            f.setVisible(true);
        }
    }

    class DrawPanel extends JPanel {

        private Graphics2D g2d;

        private void drawStatus(int x, int y, int str) {
            g2d.setFont(new Font("XHei Apple", Font.PLAIN, 25));
            g2d.drawOval(x, y, 50, 50);
            g2d.drawString(str + "", x + 20, y + 35);
        }

        private void drawFinalStatus(int x, int y, int str) {
            g2d.drawOval(x + 3, y + 3, 44, 44);
            drawStatus(x, y, str);
        }

        //循环
        private void drawCycle(int x, int y, String str) {
            g2d.setFont(new Font("XHei Apple", Font.PLAIN, 20));
            g2d.drawOval(x + 7, y - 35, 35, 35);
            g2d.drawString(str, x, y - 45);
            g2d.drawPolyline(new int[]{x + 30, x + 25, x + 30}, new int[]{y - 30, y - 35, y - 40}, 3);
        }

        //起点x,y 宽度固定200
        private void drawX(int x, int y, String str) {
            g2d.setFont(new Font("XHei Apple", Font.PLAIN, 20));
            g2d.drawLine(x + 50, y + 25, x + 200, y + 25);
            g2d.fillPolygon(new int[]{x + 200, x + 180, x + 180}, new int[]{y + 25, y + 30, y + 20}, 3);
            g2d.drawString(str + "", x + 60, y + 25);
        }


        String changeInput(int x) {
            switch (x) {
                case 0:
                    return "空格&tab";
                case 1:
                    return "l";
                case 2:
                    return "d";
                case 3:
                    return "=";
                case 4:
                    return "双界符";
                case 5:
                    return "单界符";
                case 6:
                    return "+";
                case 7:
                    return "-";
                case 8:
                    return "*";
                case 9:
                    return "/";
                case 10:
                    return ":";
                case 11:
                    return "<";
                case 12:
                    return ">";
                case 13:
                    return "|";
                case 14:
                    return "&";
                case 15:
                    return "小数点";
                case 16:
                    return "\\n";
            }
            return "其它";
        }

        boolean isFinal(int x) {
            int a[] = {2, 4, 7, 8, 9, 10, 11, 12, 13, 14, 15, 17, 20, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33};
            for (int i = 0; i < a.length; i++) {
                if (x == a[i])
                    return true;
            }
            return false;
        }

        @Override
        protected void paintComponent(Graphics graphics) {
            super.paintComponent(graphics);
            g2d = (Graphics2D) graphics.create();

            int[][] a;//存放状态转换矩阵
            a = new int[][]{{0, 1, 3, 9, 10, 11, 12, 13, 14, 15, 21, 24, 27, 29, 31, -1, -1, 33},
                    {-1, 1, 1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 2},
                    {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1},
                    {4, -1, 3, -1, 4, 4, -1, -1, -1, -1, -1, -1, -1, 4, 4, 5, 4, 8},
                    {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1},
                    {-1, -1, 6, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 8},
                    {7, -1, 6, -1, 7, 7, -1, -1, -1, -1, -1, -1, -1, 7, 7, -1, 4, -1},
                    {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1},
                    {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1},
                    {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1},
                    {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1},
                    {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1},
                    {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1},
                    {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1},
                    {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1},
                    {-1, -1, -1, -1, -1, -1, -1, -1, 18, 16, -1, -1, -1, -1, -1, -1, -1, -1},
                    {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 17, 16},
                    {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1},
                    {-1, -1, -1, -1, -1, -1, -1, -1, 19, -1, -1, -1, -1, -1, -1, -1, -1, 18},
                    {-1, -1, -1, -1, -1, -1, -1, -1, -1, 20, -1, -1, -1, -1, -1, -1, -1, 18},
                    {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1},
                    {-1, -1, -1, 23, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 22},
                    {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1},
                    {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1},
                    {-1, -1, -1, 25, -1, -1, -1, -1, -1, -1, -1, -1, 26, -1, -1, -1, -1, -1},
                    {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1},
                    {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1},
                    {-1, -1, -1, 28, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1},
                    {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1},
                    {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 30, -1, -1, -1, -1},
                    {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1},
                    {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 32, -1, -1, -1},
                    {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1},
                    {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1}
            };


            g2d.setColor(Color.BLACK);
            g2d.setStroke(new BasicStroke(2.0f));//设置线宽
            g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);//消除文字锯齿
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);//消除画图锯齿
            g2d.setFont(new Font("XHei Apple", Font.PLAIN, 20));

            int[] flag = new int[a.length];//是否已画出标记
            int[] x = new int[a.length];//x坐标
            int[] y = new int[a.length];//y坐标
            flag[0] = 1;
            x[0] = 150;
            y[0] = 95;
            g2d.drawArc(x[0] - 50, y[0] - 75, 100, 100, -180, 90);
            g2d.drawPolyline(new int[]{x[0] - 5, x[0], x[0] - 5}, new int[]{y[0] + 20, y[0] + 25, y[0] + 30}, 3);
            g2d.drawString("init", x[0] - 25, y[0]);
            drawStatus(x[0], y[0], 0);

            int[] s = new int[100];//存放起始状态
            int[] e = new int[100];//存放终止状态
            int edgeNum = 0;

            for (int i = 0; i < a.length; i++) {
                int num = 0;//记录此状态有几个下一个状态
                for (int j = 0; j < a[0].length; j++) {
                    //如果这两个边已经有边相连，则跳过这次循环
                    int flag2 = 0;
                    for (int w = 0; w < edgeNum; w++) {
                        if (s[w] == i && e[w] == a[i][j])
                            flag2 = 1;
                    }
                    if (flag2 == 1)
                        continue;
                    //接下来的状况就是两个状态没有被边相连
                    //如果下一个状态存在
                    if (a[i][j] >= 0) {
                        String str = changeInput(j);
                        for (int k = j + 1; k < a[0].length; k++) {
                            if (a[i][k] == a[i][j])
                                str = str + "," + changeInput(k);
                        }
                        //如果下一个状态未被画出
                        //画出下一个状态并设置其状态为已画出
                        //并记录下一个状态的x坐标和y坐标
                        //下一个状态的数量+1
                        if (flag[a[i][j]] == 0) {
                            //如果下一个状态有两个
                            if (num == 1) {
                                g2d.drawLine(x[i] + 25, y[i] + 100 * num + 45, x[i] + 25, y[i] + 100 * num - 50);
                                g2d.drawLine(x[i] + 50, y[i] + 120 * num + 25, x[i] + 25, y[i] + 120 * num + 25);
                            }
                            //下一个状态有两个以上的时候，表示状态的圆球就不占据长度了，和有两个状态的时候不同
                            if (num > 1) {
                                g2d.drawLine(x[i] + 25, y[i] + 120 * num + 25, x[i] + 25, y[i] + 120 * num - 100);
                                g2d.drawLine(x[i] + 50, y[i] + 120 * num + 25, x[i] + 25, y[i] + 120 * num + 25);
                            }

                            drawX(x[i], y[i] + 120 * num, str);
                            //判断是否为终态
                            if (isFinal(a[i][j])) {
                                drawFinalStatus(x[i] + 200, y[i] + 120 * num, a[i][j]);
                            } else {
                                drawStatus(x[i] + 200, y[i] + 120 * num, a[i][j]);
                            }
                            flag[a[i][j]] = 1;
                            x[a[i][j]] = x[i] + 200;
                            y[a[i][j]] = y[i] + 120 * num;
                            num++;
                        }
                        //如果下一个状态已被画出
                        else {
                            //如果下一个状态就是自身
                            if (i == a[i][j]) {
                                drawCycle(x[i], y[i], str);
                            } else {
                                //计算x和y的距离
                                g2d.drawLine(x[i] + 50, y[i] + 25, x[a[i][j]] + 25, y[a[i][j]] + 50);
                                g2d.fillRect(x[a[i][j]] + 25, y[a[i][j]] + 50, 8, 8);
                                g2d.drawString(str, (x[a[i][j]] + x[i] + 75) / 2, (y[a[i][j]] + y[i] + 75) / 2);

                            }
                        }
                        s[edgeNum] = i;
                        e[edgeNum] = a[i][j];
                        edgeNum++;
                    }
                }
            }
            g2d.dispose();// 自己创建的副本用完要销毁掉
        }
    }
}
