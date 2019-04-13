package SoftFinally;

import org.jb2011.lnf.beautyeye.ch3_button.BEButtonUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.RowSorter;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableRowSorter;


class DiscussUI extends MouseAdapter implements ActionListener {
    public void mouseClicked(MouseEvent e) {
        if (e.getClickCount() == 2){
            //你可以在這里再進行是點擊哪一行的判斷
            try {
                int selectedRow = t[0].getSelectedRow();//获得选中行的索引
                if(selectedRow!=-1)  //存在选中行
                {
                    maintitle=dtm[0].getValueAt(selectedRow,0).toString();
                    dtm[1]=new DefaultTableModel();
                    dtm[1].setColumnIdentifiers(title[1]);
                    dtm[1].setNumRows(0);
                    t[1]=new JTable(dtm[1]);
                    try {
                        rs=stmt.executeQuery("select floor,id,context,Lzid from discussData where Cname='"+Cname+"' and title='"+maintitle+"' order by floor asc");
                        ArrayList<String[]> arrayStr=new ArrayList<>();
                        while(rs.next()){
                            String[] strr=new String[3];
                            for (int i = 0; i < 3; i++) {
                                strr[i]=rs.getString(i+1);
                            }
                            arrayStr.add(strr);
                            Lzid=rs.getString(4);
                        }
                        for (int i = 0; i < arrayStr.size(); i++) {
                            String[] temp=arrayStr.get(i);
                            if(temp[1].length()==5){
                                rs=stmt.executeQuery("select name from teacher where id='"+temp[1]+"'");
                                rs.next();
                                Vector<String> v=new Vector<>();
                                v.add(temp[0]);
                                v.add(temp[1]);
                                v.add(rs.getString(1));
                                v.add(temp[2]);
                                dtm[1].addRow(v);
                            }else {
                                rs=stmt.executeQuery("select name from student where id='"+temp[1]+"'");
                                rs.next();
                                Vector<String> v=new Vector<>();
                                v.add(temp[0]);
                                v.add(temp[1]);
                                v.add(rs.getString(1));
                                v.add(temp[2]);
                                dtm[1].addRow(v);
                            }
                        }

                        JFrame f=new JFrame("帖子内容");

                        t[1].setRowHeight(50);
                        t[1].getTableHeader().setFont(font);
                        t[1].setFont(font);
                        MyJPanel p=new MyJPanel();
                        jsp[1]=new JScrollPane(t[1]);

                        JLabel lnew=new JLabel(maintitle);
                        lnew.setFont(new Font("XHei OSX",Font.PLAIN,30));

                        p.addJComponent(jsp[1],0,1,8,6);
                        p.addJComponent(jta2,0,7,8,4);
                        JButton jb=new JButton("发布回复");
                        jb.addActionListener(this);
                        jb.setUI(new BEButtonUI().setNormalColor(BEButtonUI.NormalColor.blue));
                        jb.setForeground(Color.white);
                        jb.setFont(new Font("XHei OSX",Font.PLAIN,20));
                        jta2.setFont(new Font("XHei OSX",Font.PLAIN,20));
                        p.gbc.weighty=0;
                        p.gbc.fill=GridBagConstraints.NONE;
                        p.addJComponent(jb,6,11,1,1);
                        p.addJComponent(lnew,0,0,8,1);
                        f.setLayout(new BorderLayout());
                        f.setSize(1000,1000);
                        f.add(p);
                        f.setLocationRelativeTo(null);
                        f.setVisible(true);
                    } catch (SQLException ee) {
                        ee.printStackTrace();
                    }
                    //FitTableColumns(t[1]);
                }
                else
                    JOptionPane.showMessageDialog(null, "请选择要打开的主题帖", "警告", JOptionPane.INFORMATION_MESSAGE);
            }catch (Exception ee){
                ee.printStackTrace();
            }
        }
    }
    //表格列宽自适应
    public void FitTableColumns(JTable myTable) {
        JTableHeader header = myTable.getTableHeader();
        int rowCount = myTable.getRowCount();

        Enumeration columns = myTable.getColumnModel().getColumns();
        while (columns.hasMoreElements()) {
            TableColumn column = (TableColumn) columns.nextElement();
            int col = header.getColumnModel().getColumnIndex(column.getIdentifier());
            int width = (int) myTable.getTableHeader().getDefaultRenderer()
                    .getTableCellRendererComponent(myTable, column.getIdentifier()
                            , false, false, -1, col).getPreferredSize().getWidth();
            for (int row = 0; row < rowCount; row++) {
                int preferedWidth = (int) myTable.getCellRenderer(row, col).getTableCellRendererComponent(myTable,
                        myTable.getValueAt(row, col), false, false, row, col).getPreferredSize().getWidth();
                width = Math.max(width, preferedWidth);
            }
            header.setResizingColumn(column); // 此行很重要
            column.setWidth(width + myTable.getIntercellSpacing().width);
        }
    }
    String id,Lzid;
    String Cname;
    JPanel f=new JPanel();
    JFrame p=new JFrame();
    Statement stmt;
    Font font=new Font("XHei OSX",Font.PLAIN,20);
    ResultSet rs=null;
    String[] strb={"搜索","打开","删除","发布主题","发布回复","刷新主题列表"};
    JButton[] b=new JButton[strb.length];
    JTextField jtf=new JTextField(),searchBar=new JTextField();
    JTextArea jta=new JTextArea();
    JTextArea jta2=new JTextArea();
    String[] strl={"论坛","题目","内容","讨论串"};
    JLabel[] jl=new JLabel[strl.length];
    //表格相关
    JScrollPane[] jsp=new JScrollPane[2];
    JTable[] t=new JTable[2];
    DefaultTableModel[] dtm=new DefaultTableModel[2];
    String[][] title={{"主题"},{"楼层","号码","姓名","内容"}};
    String maintitle;
    //布局相关
    GridBagLayout gb=new GridBagLayout();
    GridBagConstraints gbc=new GridBagConstraints();

    DiscussUI(String id, String Cname, Statement stmt){
        int px=10;
        gbc.insets=new Insets(px,px,px,px);
        this.id=id;
        this.Cname=Cname;
        this.stmt=stmt;
        f.setLayout(gb);
        p.setTitle(Cname+"论坛");
        //初始化组件数组
        for(int i=0;i<b.length;i++){
            b[i]=new JButton(strb[i]);
            b[i].addActionListener(this);
            if(i!=3&&i!=4)
                b[i].setUI(new BEButtonUI().setNormalColor(BEButtonUI.NormalColor.green));
            else
                b[i].setUI(new BEButtonUI().setNormalColor(BEButtonUI.NormalColor.blue));
            b[i].setForeground(Color.white);
        }
        for (int i = 0; i < jl.length; i++) {
            jl[i]=new JLabel(strl[i]);
        }
        Vector<String> v=new Vector<>();
        v.add("主题");
        dtm[0]=new MyTableModel_Mold_BOM(new Vector(),v);
        t[0]=new JTable(dtm[0]);
        jsp[0]=new JScrollPane(t[0]);
        b[5].setOpaque(false);
        //表格外观设置
        for (int i = 0; i < 1; i++) {
            t[i].setRowHeight(50);
            t[i].getTableHeader().setFont(font);
            t[i].setFont(font);
        }
        //双击弹窗
        t[0].addMouseListener(this);
        //布局代码
        gbc.fill= GridBagConstraints.BOTH;
        gbc.weighty=1;
        gbc.weightx=1;//网格水平可以缩放
        addJComponent(t[0],1,1,8,5);
        gbc.weightx=10;
        //addJComponent(t[1],9,1,8,11);
        gbc.weightx=1;
        gbc.weighty=0;
        addJComponent(b[0],1,0,1,1);
        gbc.weightx=5;
       addJComponent(searchBar,2,0,4,1);
        gbc.weightx=1;
        //addJComponent(b[1],6,0,1,1);
        if(id.length()==5)
                addJComponent(b[2],7,0,1,1);
        addJComponent(b[5],8,0,1,1);
        addJComponent(b[3],8,12,1,1);
        //addJComponent(b[4],8,12,1,1);
        addJComponent(jtf,1,6,8,1);
        gbc.weighty=1;
        addJComponent(jta,1,7,8,5);
        gbc.weightx=1;//网格水平不可缩放
        gbc.weighty=0;
        addJComponent(jl[0],0,0,1,1);
        addJComponent(jl[1],0,6,1,1);
        addJComponent(jl[2],0,7,1,1);
        gbc.weighty=1;

        //插入数据
        try {
            InsertData();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        p.setLayout(new BorderLayout());
        p.add(f);
        p.setSize(1000,1000);
        p.setLocationRelativeTo(null);
        //p.setExtendedState(JFrame.MAXIMIZED_BOTH);
        p.setVisible(true);
    }

    void InsertData() throws SQLException{
        dtm[0].setNumRows(0);
        rs=stmt.executeQuery("select distinct title from discussData where Cname='"+Cname+"';");
        while (rs.next()){
            Vector<String> v=new Vector<>();
            v.add(rs.getString(1));
            dtm[0].addRow(v);
        }
    }
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

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        if(actionEvent.getActionCommand().equals(strb[0])){
            if(searchBar.getText().length()==0){
                JOptionPane.showMessageDialog(null, "请输入查询条件", "警告", JOptionPane.INFORMATION_MESSAGE);
            }
            else{
                boolean isZero=true;
                try {
                    dtm[0].setNumRows(0);
                    rs=stmt.executeQuery("select distinct title from discussData where Cname='"+Cname+"' and title like '%"+searchBar.getText()+"%'");
                    while(rs.next()){
                        Vector<String> v=new Vector<>();
                        v.add(rs.getString(1));
                        dtm[0].addRow(v);
                        isZero=false;
                    }
                }catch(SQLException e){
                    e.printStackTrace();
                }
                if(isZero){
                    try {
                        dtm[0].setNumRows(0);
                        rs=stmt.executeQuery("select distinct title from discussData where Cname='"+Cname+"'");
                        while(rs.next()){
                            Vector<String> v=new Vector<>();
                            v.add(rs.getString(1));
                            dtm[0].addRow(v);
                        }
                        JOptionPane.showMessageDialog(null, "没有符合查询条件的主题", "警告", JOptionPane.INFORMATION_MESSAGE);
                    }catch(SQLException e){
                        e.printStackTrace();
                    }
                }
            }
        }
        if(actionEvent.getActionCommand().equals(strb[1])){
            try {
                int selectedRow = t[0].getSelectedRow();//获得选中行的索引
                if(selectedRow!=-1)  //存在选中行
                {
                    maintitle=dtm[0].getValueAt(selectedRow,0).toString();
                    dtm[1].setNumRows(0);
                    try {
                        rs=stmt.executeQuery("select floor,id,context,Lzid from discussData where Cname='"+Cname+"' and title='"+maintitle+"' order by floor asc");
                        ArrayList<String[]> arrayStr=new ArrayList<>();
                        while(rs.next()){
                            String[] strr=new String[3];
                            for (int i = 0; i < 3; i++) {
                                strr[i]=rs.getString(i+1);
                            }
                            arrayStr.add(strr);
                            Lzid=rs.getString(4);
                        }
                        for (int i = 0; i < arrayStr.size(); i++) {
                            String[] temp=arrayStr.get(i);
                            if(temp[1].length()==5){
                                rs=stmt.executeQuery("select name from teacher where id='"+temp[1]+"'");
                                rs.next();
                                Vector<String> v=new Vector<>();
                                v.add(temp[0]);
                                v.add(temp[1]);
                                v.add(rs.getString(1));
                                v.add(temp[2]);
                                dtm[1].addRow(v);
                            }else {
                                rs=stmt.executeQuery("select name from student where id='"+temp[1]+"'");
                                rs.next();
                                Vector<String> v=new Vector<>();
                                v.add(temp[0]);
                                v.add(temp[1]);
                                v.add(rs.getString(1));
                                v.add(temp[2]);
                                dtm[1].addRow(v);
                            }
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    //FitTableColumns(t[1]);
                }
                else
                    JOptionPane.showMessageDialog(null, "请选择要打开的主题帖", "警告", JOptionPane.INFORMATION_MESSAGE);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        if(actionEvent.getActionCommand().equals(strb[2])){
            try {
                int selectedRow = t[0].getSelectedRow();//获得选中行的索引
                if(selectedRow!=-1)  //存在选中行
                {
                    String title=dtm[0].getValueAt(selectedRow,0).toString();
                    try {
                        //删除主题帖相关记录
                        stmt.executeUpdate("delete from discussData where Cname='"+Cname+"' and title='"+title+"'");
                        //重新载入主题帖数据
                        InsertData();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
                else
                    JOptionPane.showMessageDialog(null, "请选择要删除的主题帖", "警告", JOptionPane.INFORMATION_MESSAGE);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        if(actionEvent.getActionCommand().equals(strb[3])){
            try {
                stmt.executeUpdate("insert into discussData values ('"+Cname+"','"+jtf.getText()+"','"+id+"',"+1+",'"+id+"','"+jta.getText()+"')");
                //重新载入主题帖数据
                InsertData();
                //清空文本框
                jta.setText("");
                jtf.setText("");
                stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if(actionEvent.getActionCommand().equals(strb[4])){
            try {
                //计算帖子楼层数
                rs=stmt.executeQuery("select floor from discussData where Cname='"+Cname+"' and title='"+maintitle+"'");
                int rowCount=1;
                while (rs.next())
                    rowCount++;
                //回复帖子
                stmt.executeUpdate("insert into discussData values ('"+Cname+"','"+maintitle+"','"+Lzid+"',"+rowCount+",'"+id+"','"+jta2.getText()+"')");
                //重新载入帖子数据
                dtm[1].setNumRows(0);
                try {
                    rs=stmt.executeQuery("select floor,id,context,Lzid from discussData where Cname='"+Cname+"' and title='"+maintitle+"' order by floor asc");
                    ArrayList<String[]> arrayStr=new ArrayList<>();
                    while(rs.next()){
                        String[] strr=new String[3];
                        for (int i = 0; i < 3; i++) {
                            strr[i]=rs.getString(i+1);
                        }
                        arrayStr.add(strr);
                        Lzid=rs.getString(4);
                    }
                    for (int i = 0; i < arrayStr.size(); i++) {
                        String[] temp=arrayStr.get(i);
                        if(temp[1].length()==5){
                            rs=stmt.executeQuery("select name from teacher where id='"+temp[1]+"'");
                            rs.next();
                            Vector<String> v=new Vector<>();
                            v.add(temp[0]);
                            v.add(temp[1]);
                            v.add(rs.getString(1));
                            v.add(temp[2]);
                            dtm[1].addRow(v);
                        }else {
                            rs=stmt.executeQuery("select name from student where id='"+temp[1]+"'");
                            rs.next();
                            Vector<String> v=new Vector<>();
                            v.add(temp[0]);
                            v.add(temp[1]);
                            v.add(rs.getString(1));
                            v.add(temp[2]);
                            dtm[1].addRow(v);
                        }
                    }
                    stmt.close();
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                //FitTableColumns(t[1]);
                //清空文本框
                jta2.setText("");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if(actionEvent.getActionCommand().equals(strb[5])){
            try {
                dtm[0].setNumRows(0);
                rs=stmt.executeQuery("select distinct title from discussData where Cname='"+Cname+"'");
                while(rs.next()){
                    Vector<String> v=new Vector<>();
                    v.add(rs.getString(1));
                    dtm[0].addRow(v);
                }
            }catch(SQLException e){
                e.printStackTrace();
            }
        }
    }
    class MyTableModel_Mold_BOM extends  DefaultTableModel{

        public MyTableModel_Mold_BOM(Vector data,Vector columns){
            super(data,columns);
        }
        public   boolean   isCellEditable(int   row,int   column){
            //设置是否可编辑
            return false;
        }
        public Class<?> getColumnClass(int columnIndex) {

            return Object.class;
        }
    }
}

class MyJPanel extends JPanel{
    //布局相关
    GridBagLayout gb=new GridBagLayout();
    GridBagConstraints gbc=new GridBagConstraints();

    MyJPanel(){
        //布局相关
        super();
        gb=new GridBagLayout();
        gbc=new GridBagConstraints();
        gbc.fill= GridBagConstraints.BOTH;
        gbc.weighty=1;
        gbc.weightx=1;//网格水平可以缩放
        this.setLayout(gb);
    }
    void addJComponent (JComponent c, int x, int y, int gx, int gy){
        gbc.gridx=x;
        gbc.gridy=y;
        gbc.gridwidth=gx;
        gbc.gridheight=gy;
        gb.setConstraints(c,gbc);
        this.add(c);
    }
}