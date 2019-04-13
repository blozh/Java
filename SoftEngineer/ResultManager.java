package SoftEngineer;

import com.alibaba.fastjson.JSON;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

class ResultManager implements ActionListener {
    String ip="192.168.31.248";
    Font font=new Font("Simsun",Font.PLAIN,15);
    JLabel[] l=new JLabel[4];
    JTextField[] tf=new JTextField[4];
    JButton[] b=new JButton[2];
    JScrollPane sp;/* 用JScrollPane装载JTable，这样超出范围的列就可以通过滚动条来查看 */
    JPanel p=new JPanel();
    GridBagLayout gb=new GridBagLayout();
    GridBagConstraints gbc=new GridBagConstraints();

    JTextArea[] ta=new JTextArea[2];
    JButton bb=new JButton("发布公告");

    private DefaultTableModel model = new DefaultTableModel();
    private JTable t = new JTable(model);

    //添加组件，横坐标x 纵坐标y gx为横向占据网格数
    void addJComponent (JComponent c, int x, int y, int gx, int gy,int weightx){
        gbc.weightx=weightx;
        gbc.gridx=x;
        gbc.gridy=y;
        gbc.gridwidth=gx;
        gbc.gridheight=gy;
        gb.setConstraints(c,gbc);
        c.setFont(font);
        p.add(c);
    }
    JPanel initPanel(String ip){
        this.ip=ip;
        p.setLayout(gb);
        l[0]=new JLabel("学号");
        l[1]=new JLabel("体育");
        l[2]=new JLabel("英语");
        l[3]=new JLabel("软工");

        b[0]=new JButton("插入");
        b[1]=new JButton("查询");

        for(int i=0;i<b.length;i++){
            b[i].addActionListener(this);
        }

        model.setColumnIdentifiers(new String[]{"学号","姓名","体育","英语","软工","综测"});
        sp=new JScrollPane(t);//当t=null时，如果执行sp=new JScrollPane(t)然后再让t指向一个有内容的Jtable的话，JScroll依然为空

        gbc.insets=new Insets(3, 3, 3, 3);//组件的横向间距

        gbc.fill= GridBagConstraints.HORIZONTAL;//组件是否允许横向纵向扩大
        addJComponent(l[0],0,0,1,1,0);
        addJComponent(l[1],0,1,1,1,0);
        addJComponent(l[2],0,2,1,1,0);
        addJComponent(l[3],0,3,3,1,0);
        for(int i=0;i<4;i++){
            tf[i]=new JTextField();
            addJComponent(tf[i],1,i,3,1,1);
        }
        gbc.fill=GridBagConstraints.BOTH;
        gbc.ipady=50;
        gbc.weighty=0;
        addJComponent(b[0],0,5,4,1,1);
        b[0].setBackground(Color.white);
        b[1].setBackground(Color.white);
        addJComponent(b[1],0,6,4,1,1);

        gbc.ipadx=300;
        gbc.insets=new Insets(6, 3, 6, 3);//组件的横向间距
        gbc.weighty=1;
        addJComponent(sp,4,0,4,7,4);
        for (int i = 0; i < ta.length; i++) {
            ta[i]=new JTextArea();
        }
        addJComponent(bb,0,7,4,1,1);
        bb.setBackground(Color.white);
        addJComponent(ta[0],4,7,4,1,4);
        ta[1].setOpaque(false);
        ta[1].setEditable(false);
        addJComponent(ta[1],0,8,8,3,4);
        Modifyservice.chaxun(model,ip);
        bb.addActionListener(this);

        //获取反馈信息
        //http://localhost:8080/ComprehensiveEvaluation/notice/getNotice
        Map m=new HashMap<String, String>();
        m.put("type","1");
        List<Notice> note = null;
        try {
            note = JSON.parseArray(Demo.post(m,"http://"+ip+":8080/ComprehensiveEvaluation/notice/getNotice"),Notice.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        String result="";
        for (int i = 0; i < note.size(); i++) {
            result=result+note.get(i).getNotice()+"\n";
        }
        ta[1].setText(result);
        return p;
    }

    JTable getJTable(){
        return t;
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        if(actionEvent.getActionCommand().equals("插入")){
            try{
                HashMap map = new HashMap();
                map.put("number",tf[0].getText());
                map.put("sports",tf[1].getText());
                map.put("english",tf[2].getText());
                map.put("software",tf[3].getText());
                String s=Demo.post(map,"http://"+ip+":8080/ComprehensiveEvaluation/teacher/getEvaluation");
                JOptionPane.showMessageDialog(null, s, "消息", JOptionPane.INFORMATION_MESSAGE);
            }catch(Exception e){

            }finally {
                Modifyservice.chaxun(model,ip);
            }
        }

        if(actionEvent.getActionCommand().equals("查询")){
            Modifyservice.chaxun(model,ip);
        }
        if(actionEvent.getActionCommand().equals("发布公告")){
            //发布公告
            //http://localhost:8080/ComprehensiveEvaluation/notice/getNotice
            Map m=new HashMap<String, String>();
            m.put("notice",ta[0].getText());
            m.put("type","2");
            String s="";
            try {
                s=Demo.post(m,"http://"+ip+":8080/ComprehensiveEvaluation/notice/postNotice");
            } catch (Exception e) {
                e.printStackTrace();
            }
            ta[0].setText("");
        }

    }

}
class Modifyservice {
    // post请求提交参数
    public static void post2(String number,String sports,String english,String software,String ip) throws Exception {
        URL url = new URL("http://"+ip+":8080/ComprehensiveEvaluation/teacher/getEvaluation");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");// 提交模式
        // conn.setConnectTimeout(10000);//连接超时 单位毫秒
        // conn.setReadTimeout(2000);//读取超时 单位毫秒
        conn.setDoOutput(true);// 是否输入参数
        // 意思是正文是urlencoded编码过的form参数
        conn.setRequestProperty("Content-type", "application/x-www-form-urlencoded");
        conn.setUseCaches(false);
        StringBuffer params = new StringBuffer();
        // 表单参数与get形式一样
        params.append("number").append("=").append(number).append("&").append("sports").append("=").append(sports)
                .append("&").append("english").append("=").append(english).append("&").append("software").append("=").append(software);
        byte[] bypes = params.toString().getBytes();
        conn.getOutputStream().write(bypes);// 输入参数
        InputStream inStream = conn.getInputStream();
        byte[] bytes = new byte[1024];
        int len = 0;
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        while ((len = inStream.read(bytes)) != -1) {
            outStream.write(bytes, 0, len);
        }
        System.out.println(new String(outStream.toByteArray(),"utf-8"));
    }

    public static void SendNotice(JTextArea[] ta,String ip){
        //发布公告
        //http://localhost:8080/ComprehensiveEvaluation/notice/getNotice
        Map m=new HashMap<String, String>();
        m.put("notice",ta[0].getText());
        m.put("type","2");
        String s="";
        try {
            s=Demo.post(m,"http://"+ip+":8080/ComprehensiveEvaluation/notice/postNotice");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void chaxun(DefaultTableModel model,String ip){
        try {
            model.setNumRows(0);
            List<Student> stu =JSON.parseArray(Demo.get("http://"+ip+":8080/ComprehensiveEvaluation/teacher/getScores"), Student.class);
            String[][] result=new String[stu.size()][];
            for (int i = 0; i < stu.size(); i++) {
                result[i]=new String[]{stu.get(i).getStuId(),stu.get(i).getName(),""+stu.get(i).getScore().getSports(),""+stu.get(i).getScore().getEnglish(),""+stu.get(i).getScore().getSoftware(),""+stu.get(i).getScore().getComprehensive()};
            }
            for (int i = 0; i < result.length; i++) {
                model.addRow(result[i]);
            }
        }catch (Exception e){

        }
    }
}