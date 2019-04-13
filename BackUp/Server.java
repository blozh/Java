package BackUp;

import java.io.*;
import java.net.*;
import java.util.Scanner;

class Server {
    public static void main(String[] args) {
        try {
            ServerSocket ss = new ServerSocket(2000);
            Socket s=ss.accept();
            BufferedReader br=new BufferedReader(new InputStreamReader(s.getInputStream()));
            do{
                String str=br.readLine();
                if(str.equals("bye"))
                    break;
                System.out.println(str);
            }while(true);
            br.close();
            s.close();
            ss.close();
        }catch (Exception e){
        }
    }
}

class Client {
    public static void main(String[] args) {
        try(Socket s=new Socket("localhost",2000);
        BufferedWriter bw=new BufferedWriter(new OutputStreamWriter(s.getOutputStream())))
        {
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
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}