package ATest;

import java.io.*;
import java.net.*;
import java.util.Scanner;

class Receive extends Thread{
    BufferedReader br;
    BufferedWriter bw;
    Receive(BufferedReader br,BufferedWriter bw){
        this.br=br;
        this.bw=bw;
    }

    public void run() {
        try {
            do{
                String str=br.readLine();
                System.out.println(str);
            }while(true);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
class Sender extends Thread{
    BufferedReader br;
    BufferedWriter bw;
    Sender(BufferedReader br,BufferedWriter bw){
        this.br=br;
        this.bw=bw;
    }

    public void run() {
        try {
            do{
                Scanner sc=new Scanner(System.in);
                String str=sc.nextLine();
                if(str.equals("bye"))
                    break;
                System.out.println(str);
                bw.write(str);
                bw.newLine();
                bw.flush();
            }while(true);
            br.close();
            bw.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}

class Server {
    public static void main(String[] args) {
        try (ServerSocket ss = new ServerSocket(2000);
             Socket s=ss.accept();
             BufferedReader br=new BufferedReader(new InputStreamReader(s.getInputStream()));
             BufferedWriter bw=new BufferedWriter(new OutputStreamWriter(s.getOutputStream())))
        {
            new Receive(br,bw).start();
            new Sender(br,bw).start();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}

class Client {
    public static void main(String[] args) {
        try (
             Socket s=new Socket("localhost",2000);
             BufferedReader br=new BufferedReader(new InputStreamReader(s.getInputStream()));
             BufferedWriter bw=new BufferedWriter(new OutputStreamWriter(s.getOutputStream())))
        {
            new Receive(br,bw).start();
            new Sender(br,bw).start();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}