package SoftEngineer;

import com.alibaba.fastjson.JSON;

import java.util.HashMap;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class RegisterService {
    static void Run(JTextField[] tf, String ip, JFrame f){
        Map<String,String> m=new HashMap<>();
        m.put("number",tf[0].getText());
        m.put("password",tf[1].getText());
        String s="";
        try {
            s=Demo.post(m,"http://"+ip+":8080/ComprehensiveEvaluation/user/login");
        } catch (Exception e) {
            e.printStackTrace();
        }
        User ss =JSON.parseObject(s,User.class);
        if(s.equals("")){
            JOptionPane.showMessageDialog(null, "您输入的用户名或密码不正确", "警告", JOptionPane.INFORMATION_MESSAGE);
        }
        else if(ss.getType()==1){
            new StudentUI(ip,ss.getNumber(),f);
            f.setVisible(false);
        }
        else if(ss.getType()==2){
            new MainWindow(ip,f);
            f.setVisible(false);
        }
        else if(ss.getType()==3){
            new SMMainWindow(ip,f);
            f.setVisible(false);
        }
    }
}
