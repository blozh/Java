package SevenOne;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;

//正确口令是8426784
class Server extends Thread{
    ServerSocket ss=null;
    Socket s=null;
    BufferedWriter bw=null;
    BufferedReader br=null;

    @Override
    public void run() {
        try {
            ss = new ServerSocket(3002);
            s = ss.accept();
            bw=new BufferedWriter(new OutputStreamWriter(s.getOutputStream()));
            br=new BufferedReader(new InputStreamReader(s.getInputStream()));
            bw.write("Verifying Server!");
            bw.newLine();
            bw.flush();
            int count=0;
            String str=null;
            String pw="8426784";
            while(true) {
                str = br.readLine();
                System.out.println(pw.equals(str));
                if (pw.equals(str)) {
                    bw.write("Registration Successful!");
                    bw.newLine();
                    bw.flush();
                } else {
                    if(count>=2){
                        bw.write("Illegal User!");
                        bw.newLine();
                        bw.flush();
                    }else {
                        bw.write("PassWord Wrong!");
                        bw.newLine();
                        bw.flush();
                        count++;
                    }
                }
            }
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    public static void main(String [] args){
        new Server().start();
    }
}
