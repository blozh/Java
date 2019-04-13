package SoftEngineer;

import com.alibaba.fastjson.JSON;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;

public class StudentUI extends WindowAdapter implements ActionListener {
    JFrame before;
    @Override
    public void windowClosing(WindowEvent windowEvent) {
        super.windowClosing(windowEvent);
        before.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        //发布反馈消息
        //http://localhost:8080/ComprehensiveEvaluation/notice/getNotice
        Map m=new HashMap<String, String>();
        m.put("notice",""+number+":"+ta[2].getText());
        m.put("type","1");
        String s="";
        try {
            s=Demo.post(m,"http://"+ip+":8080/ComprehensiveEvaluation/notice/postNotice");
        } catch (Exception e) {
            e.printStackTrace();
        }
        ta[2].setText("");
    }
    String ip="192.168.31.248";
    String number;
    Font font=new Font("Simsun",Font.PLAIN,15);
    JLabel[] l=new JLabel[4];
    JTextArea[] ta=new JTextArea[3];
    JButton b=new JButton("反馈");
    GridBagLayout gb=new GridBagLayout();
    GridBagConstraints gbc=new GridBagConstraints();
    JFrame f=new JFrame("学生成绩查看");
    String [] str=new String[]{"公告：","学生个人信息：","反馈信息："};
    String info="";

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

    void getInfomation(){
        try {
            String s=Demo.get("http://"+ip+":8080/ComprehensiveEvaluation/student/getScore/"+number);
            Student h=JSON.parseObject(s,Student.class);
            info="学号："+h.getStuId()+"\n"
                    +"姓名："+h.getName()+"\n"
                    +"性别："+h.getSex()+"\n"
                    +"年龄："+h.getAge()+"\n"
                    +"班级："+h.getClassid()+"\n"
                    +"学院："+h.getCollegeid()+"\n"
                    +"--------------"+"\n"
                    +"综测："+h.getScore().getComprehensive()+"\n"
                    +"英语："+h.getScore().getEnglish()+"\n"
                    +"体育："+h.getScore().getSports()+"\n"
                    +"软工："+h.getScore().getSoftware()+"\n";
            ta[1].setText(info);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    StudentUI(String ip,String number,JFrame before){
        this.before=before;
        this.ip=ip;
        this.number=number;
        f.setLayout(gb);
        gbc.weighty=0;
        gbc.weightx=1;
        gbc.fill= GridBagConstraints.HORIZONTAL;
        for (int i = 0; i < l.length; i++) {
            l[i]=new JLabel();
            if(i<str.length)
                l[i].setText(str[i]);
        }
        addJComponent(l[0],0,0,1,1);
        addJComponent(l[3],1,1,3,1);
        addJComponent(l[1],0,2,1,1);
        addJComponent(l[2],0,4,3,1);
        f.addWindowListener(this);
        l[0].setFont(new Font("Simsun",Font.BOLD,20));
        l[1].setFont(new Font("Simsun",Font.BOLD,20));
        for (int i = 0; i < ta.length; i++) {
            ta[i]=new JTextArea();
        }
        gbc.fill= GridBagConstraints.BOTH;
        ta[0].setEditable(false);
        ta[0].setOpaque(false);
        ta[1].setEditable(false);
        ta[1].setOpaque(false);
        ta[2].setBorder(BorderFactory.createLineBorder(Color.gray,1));
        gbc.weightx=3;
        gbc.weighty=2;
        addJComponent(ta[0],0,2,3,1);
        gbc.weighty=6;
        addJComponent(ta[1],0,3,4,1);
        gbc.weighty=1;
        addJComponent(ta[2],0,5,3,1);
        gbc.weightx=1;
        addJComponent(b,3,5,1,1);
        b.addActionListener(this);
        getInfomation();
        f.setSize(600,600);
        f.setLocationRelativeTo(null);
        f.setVisible(true);

        //获取公告
        //http://localhost:8080/ComprehensiveEvaluation/notice/getNotice
        Map m=new HashMap<String, String>();
        m.put("type","2");
        List<Notice> note = null;
        try {
            note = JSON.parseArray(Demo.post(m,"http://"+ip+":8080/ComprehensiveEvaluation/notice/getNotice"),Notice.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        String result="";
        for (int i = 0; i < note.size(); i++) {
            result=result+note.get(i).getCreatedTime().toString()+":"+note.get(i).getNotice()+"\n";
        }
        ta[0].setText(result);
    }

}
