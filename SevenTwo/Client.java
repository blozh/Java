package SevenTwo;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.Random;

class Client extends MyFrame implements ActionListener {

    Socket s;
    BufferedWriter bw;
    BufferedReader br;
    Receive r=new Receive();

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        if(actionEvent.getActionCommand()=="发送"){
            String str=ta[1].getText();
            ta[1].setText("");
            try{
                bw.write(this.getName()+"："+str+"\nend\n");
                bw.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    class Receive extends Thread{
        @Override
        public void run() {
            while(true) {
                String str = "";
                String temp;
                try {
                    while (!(temp = br.readLine()).equals("end")) {
                        str = str + temp + '\n';
                    }
                } catch (Exception e) {
                }
                ta[0].setText(str);
            }
        }
    }

    Client(){
        Random r=new Random();
        this.setName("【"+r.nextInt(1000)+"】");
        try {
            s = new Socket("127.0.0.1", 20000);
            bw = new BufferedWriter(new OutputStreamWriter(s.getOutputStream()));
            br = new BufferedReader(new InputStreamReader(s.getInputStream()));
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        super.run();
        b.addActionListener(this);
    }

    public static void main(String [] args){
        Client c[]=new Client[2];
        for(int i=0;i<2;i++) {
            c[i]= new Client();
            c[i].start();
            c[i].r.start();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}