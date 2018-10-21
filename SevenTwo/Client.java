package SevenTwo;


import java.io.*;
import java.net.Socket;
import java.util.Scanner;

class Client {
    Socket s;
    BufferedWriter bw;
    BufferedReader br;
    String name;

    void Init(){
        try {
            s=new Socket("127.0.0.1",20000);
            bw = new BufferedWriter(new OutputStreamWriter(s.getOutputStream()));
            br = new BufferedReader(new InputStreamReader(s.getInputStream()));
            //Receiver类用于时刻接收服务器端发送的消息
            //所以要单独开一个线程，和下面的while语句同时进行
            //下面的while语句的功能是，时刻接收用户在终端的输入，并把输入发送给客户端
            new Receiver().start();
            Scanner sc=new Scanner(System.in);
            System.out.print("请输入昵称：");
            name=sc.nextLine();
            while(true){
                bw.write(name+":"+sc.nextLine());
                bw.newLine();
                bw.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    class Receiver extends Thread{
        @Override
        public void run() {
            try {
                while(true){
                    String str=br.readLine();
                    System.out.println(str);
                }
            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        new Client().Init();
    }
}
