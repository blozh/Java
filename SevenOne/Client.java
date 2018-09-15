package SevenOne;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.Scanner;

class Client extends Thread{
    @Override
    public void run() {
        try{
            Socket s=new Socket("127.0.0.1",3002);
            BufferedReader br=new BufferedReader(new InputStreamReader(s.getInputStream()));
            BufferedWriter bw=new BufferedWriter(new OutputStreamWriter(s.getOutputStream()));
            String str=br.readLine();
            String str2;
            if(str.equals("Verifying Server!")) {
                do {
                    System.out.println("请输入密码：");
                    bw.write(new Scanner(System.in).nextLine());
                    bw.newLine();
                    bw.flush();
                    str2=br.readLine();
                    if (str2.equals("Illegal User!") ){
                        System.out.println("Illegal User!");
                        break;
                    }
                    if(str2.equals("PassWord Wrong!"))
                        System.out.println("PassWord Wrong!");
                    if(str2.equals("Registration Successful!"))
                        System.out.println("Registration Successful!");
                }while(str2.equals("PassWord Wrong!"));
            }
            s.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public static void main(String [] args){
        new Client().start();
    }
}
