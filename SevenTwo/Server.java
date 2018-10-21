package SevenTwo;


import java.io.*;
import java.net.*;
import java.util.*;

class Server{
    ServerSocket ss;
    Socket s;
    ArrayList<Socket> list;
    Receiver r;
    ArrayList<Receiver> rlist;
    String name="管理员";

    class Receiver extends Thread{
        String str;
        BufferedWriter bw;
        BufferedReader br;
        @Override
        public void run() {
            try {
                bw = new BufferedWriter(new OutputStreamWriter(s.getOutputStream()));
                br = new BufferedReader(new InputStreamReader(s.getInputStream()));
                new Sender().start();
                while(true){
                    str=br.readLine();
                    System.out.println(str);
                    //发送str到所有的客户端，所以需要知道所有客户端的bw
                    for(int i=0;i<rlist.size();i++){
                        rlist.get(i).bw.write(str);
                        rlist.get(i).bw.newLine();
                        rlist.get(i).bw.flush();
                    }
                }
            }catch (IOException e){
                e.printStackTrace();
            }
        }

        class Sender extends Thread{
            @Override
            public void run() {
                Scanner sc=new Scanner(System.in);
                while(true){
                    String str=name+":"+sc.nextLine();
                    try {
                        System.out.println(str);
                        for(int i=0;i<rlist.size();i++){
                            rlist.get(i).bw.write(str);
                            rlist.get(i).bw.newLine();
                            rlist.get(i).bw.flush();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    void Init(){
        try {
            ss=new ServerSocket(20000);
            list=new ArrayList<>();
            rlist=new ArrayList<>();
            while(true){
                s=ss.accept();
                list.add(s);
                //每当有一个客户端连接服务器的时候，都会新建一个线程，用于接收这个服务器的消息
                //每一个Receive类都有一个自己的bw和br
                r= new Receiver();
                rlist.add(r);
                r.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new Server().Init();
    }
}