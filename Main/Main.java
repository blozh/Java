package Main;

import java.io.*;
class Main {
    public static void main(String arg[]) throws Exception {
        File myfile1 = new File("D:\\myfile1");
        File myfile2 = new File("D:\\myfile2");
        FileWriter fw = new FileWriter(myfile1);
        BufferedWriter bw = new BufferedWriter(fw);
        InputStreamReader ir = new InputStreamReader(System.in);
        BufferedReader br = new BufferedReader(ir);
        String s = br.readLine();
        bw.write(s);
        bw.newLine();
        while (!s.equals("bye")) {
            bw.write(s);
            bw.newLine();
            s = br.readLine();
        }
        br.close();
        bw.close();
    }
}