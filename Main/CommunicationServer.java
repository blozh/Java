package Main;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Jim on 2018/11/27.
 */

public class CommunicationServer {
    String[] password = {"123456","12306","12580","67414"};
    List commandList = Arrays.asList(password);
    PrintWriter writer;

    public static void main(String[] args) {
        new CommunicationServer().go();
    }

    public void go() {
        try {
            int count = 0;
            ServerSocket server = new ServerSocket(5000);       // TCP端口号: 5000
            Socket clientSocket = server.accept();
            writer = new PrintWriter(clientSocket.getOutputStream());
            // 1)向客户端发送"Verifying Server!"
            writer.println("Verifying Server!");
            writer.flush();
            while (true) {
                // 2)若读口令次数超过3次，则发送Illegal User!给客户端
                if (count > 3) {
                    writer.println("Illegal User!");
                    break;
                }
                Thread thread = new Thread(new ClientHandle(clientSocket));
                thread.start();
                count++;
            }
        } catch (Exception ex) { ex.printStackTrace(); }
    }

    class ClientHandle implements Runnable{
        BufferedReader reader;
        Socket socket;

        public ClientHandle(Socket s) {
            try {
                socket = s;
                InputStreamReader isReader = new InputStreamReader(socket.getInputStream());
                reader = new BufferedReader(isReader);
                writer = new PrintWriter(socket.getOutputStream());
            } catch (Exception ex) {ex.printStackTrace();}
        }

        public void run() {
            try {
                // 读取客户端发出的指令
                String command = reader.readLine();
                if (commandList.contains(command)) {        // 若指令正确，输出Registration Successful!
                    writer.println("Registration Successful!");
                }
                else {      // 若指令错误，输出PassWord Wrong!
                    writer.println("PassWord Wrong!");
                }
            } catch (Exception ex) {ex.printStackTrace();}
        }
    }
}
