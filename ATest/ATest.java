package ATest;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.io.FileOutputStream;
import java.io.OutputStream;

import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import sun.misc.BASE64Decoder;

public class ATest {
    JFrame f=new JFrame();
    GridBagLayout gb=new GridBagLayout();
    GridBagConstraints gbc=new GridBagConstraints();
    Font font=new Font("XHei Apple",Font.PLAIN,16);
    String input="";
    String output;
    //添加组件，横坐标x 纵坐标y gx为横向占据网格数 x以0为开始
    void addJComponent (JComponent c, int x, int y, int gx, int gy){
        gbc.gridx=x;
        gbc.gridy=y;
        gbc.gridwidth=gx;
        gbc.gridheight=gy;
        gb.setConstraints(c,gbc);
        c.setFont(font);
        f.add(c);
    }

    public static boolean GenerateImage(String imgStr)
    { //对字节数组字符串进行Base64解码并生成图片       
        if (imgStr == null) //图像数据为空

            return false;
        BASE64Decoder decoder = new BASE64Decoder();
        try
        {
//Base64解码

            byte[] b = decoder.decodeBuffer(imgStr);
            for(int i=0;i<b.length;++i)
            {
                if(b[i]<0)
                {//调整异常数据        
                    b[i]+=256;
                }
            }
            //生成jpeg图片
            String imgFilePath = "D:\\new.jpg";//新生成的图片
            OutputStream out = new FileOutputStream(imgFilePath);
            out.write(b);
            out.flush();
            out.close();
            return true;
        }

catch (Exception e){
        return false;}
    }

    void init(){
        f.setLayout(new FlowLayout());
        JLabel label=new JLabel();
        label.setIcon(new ImageIcon("C:\\Users\\loghd\\Desktop\\编译原理\\词法分析状态转换图.png"));
        JScrollPane jsp=new JScrollPane(label);
        jsp.setPreferredSize(new Dimension(600,900));
        jsp.setSize(200,200);
        addJComponent(jsp,0,0,1,1);
        f.setVisible(true);
        f.pack();
    }

    public static void main(String[] args){
        new ATest().init();
    }
}
