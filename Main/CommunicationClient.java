package Main;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

/**
 * Created by Jim on 2018/12/4.
 */

public class CommunicationClient {
    Scanner input;
    Socket clientA;
    BufferedReader reader;
    PrintWriter writer;
    public static void main(String[] args) {
        CommunicationClient client = new CommunicationClient();
        client.go();
    }

    public void go() {
        try {
            clientA = new Socket("127.0.0.1",5000);
            input = new Scanner(System.in);
            reader = new BufferedReader(new InputStreamReader(clientA.getInputStream()));
            writer = new PrintWriter(clientA.getOutputStream());
            Thread thread = new Thread(new IncomingReader());
            thread.start();
            input.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public class IncomingReader implements Runnable {
        public void run() {
            try {
                // 1)读取服务器反馈信息
                String reply = reader.readLine();
                // 2)如果反馈信息不是Verifying Server! 则提示Server Wrong! 并退出程序
                if (!reply.equals("Verifying Server!")) {
                    System.out.println("Server Wrong!");
                }
                else {
                    while (true) {
                        System.out.println(reply);
                        // 3) 输入password
                        System.out.print("Please input the password: ");
                        String password = input.nextLine();
                        writer.println(password);
                        // 4)读取反馈信息
                        reply = reader.readLine();
                        // 5)若反馈信息是Illegal User! 则发出提示，退出程序
                        if (reply.equals("Illegal User!")) {
                            System.out.println(reply);
                            break;
                        }
                        // 6)若反馈信息是PassWord Wrong! 则发出提示，重新输入password
                        else if (reply.equals("PassWord Wrong!")) {
                            System.out.println(reply);
                        }
                        // 7)输出Registration Successful!
                        else {
                            System.out.println("Registration Successful!");
                            break;
                        }
                    }
                }
            } catch (Exception ex) {ex.printStackTrace();}
        }
    }
}
