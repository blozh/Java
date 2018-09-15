package SevenTwo;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by loghd on 2018/8/7.
 */

class Server {
    public static void main(String[] args) throws IOException{
        ServerSocket ss=new ServerSocket(30000);
        while(true) {
            Socket s = ss.accept();
            BufferedReader br = new BufferedReader(new InputStreamReader(s.getInputStream()));
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(s.getOutputStream()));
            bw.write("连接成功");
            bw.newLine();
            bw.flush();
            System.out.println("客户的地址：" + s.getInetAddress());
            System.out.println("正在监听");

            while (br.readLine().equals("保持连接")) {
                double[] a = new double[3];
                double p = 0;
                for (int i = 0; i < 3; i++) {
                    a[i] = Double.parseDouble(br.readLine());
                    p = p + a[i];
                }
                p /= 2;
                double area = Math.sqrt(p * (p - a[0]) * (p - a[1]) * (p - a[2]));
                bw.write(String.valueOf(area));
                bw.newLine();
                bw.flush();
            }
            br.close();
            bw.close();
            s.close();
            System.out.println("客户离开");
        }
    }
}
