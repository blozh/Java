package SevenTwo;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

class Server extends MyFrame implements ActionListener {
    ArrayList<Socket> list;
    Receiver r;
    ArrayList<Receiver> rlist;
    public void actionPerformed(ActionEvent actionEvent) {
        if(actionEvent.getActionCommand().equals("发送")){
            screen=screen+"〖管理员〗："+ta[1].getText()+'\n';
            ta[0].setText(screen);
            ta[1].setText("");
            try{
                for (int i = 0; i < rlist.size(); i++) {
                    rlist.get(i).bw.write(screen + "\nend\n");
                    rlist.get(i).bw.flush();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    static String screen="";

    ServerSocket ss;
    Socket s;

    void Init(){
        this.setName("服务器端");
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

    class Receiver extends Thread {
        String str;
        BufferedWriter bw;
        BufferedReader br;

        @Override
        public void run() {
            try {
                bw = new BufferedWriter(new OutputStreamWriter(s.getOutputStream()));
                br = new BufferedReader(new InputStreamReader(s.getInputStream()));
                while (true) {
                    String temp;
                    while (!(temp = br.readLine()).equals("end")) {
                        System.out.println(temp);
                        screen = screen + temp + '\n';
                    }
                    ta[0].setText(screen);
                    //发送str到所有的客户端，所以需要知道所有客户端的bw
                    for (int i = 0; i < rlist.size(); i++) {
                        rlist.get(i).bw.write(screen + "end\n");
                        rlist.get(i).bw.flush();
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    @Override
    public void run() {
        super.run();
        //移动一下服务器端的位置
        f.setBounds(0,100,600,800);
        //增加监听
        b.addActionListener(this);
    }

    public static void main(String[] args) throws IOException{
        Server s=new Server();
        s.start();
        s.Init();
    }
}