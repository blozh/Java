package SixthFour;
/*
从键盘输入一个整型数，一个双精度型和一个字符串，
用DataOutputStream将这些数据输出到文件中，
然后用DataInputStream从文件中读出这些数据并打印到标准输出设备。
*/

import java.io.*;
import java.util.Scanner;

class Demo {
    public static void main(String [] args) throws IOException{
        Scanner sc=new Scanner(System.in);
        int i=sc.nextInt();
        double d=sc.nextDouble();
        String s=sc.next();
        File f=new File("/Users/loghder/AndroidStudioProjects/Java" +
                "/JavaDemo/src/main/Java/SixthFour/myfiel.txt");
        f.createNewFile();
        FileOutputStream output=new FileOutputStream(f);
        DataOutputStream out=new DataOutputStream(output);
        out.writeInt(i);
        out.writeDouble(d);
        out.writeUTF(s);

        System.out.println("----------------");
        FileInputStream input=new FileInputStream(f);
        DataInputStream in=new DataInputStream(input);
        System.out.println(in.readInt());
        System.out.println(in.readDouble());
        System.out.println(in.readUTF());

    }
}
