package SixthTwo;
import java.io.*;
import java.util.Scanner;

class Copy {
    static String sno;
    static String name;
    static String profession;
    static String classNo;
    static String address;
    public static void main(String [] args) throws IOException {
        File in=new File("myfile1.txt");
        File out=new File("myfile2.txt");
        in.createNewFile();
        out.createNewFile();
        FileReader input=new FileReader(in);
        FileWriter output=new FileWriter(in);

        /*
        * 从标准设备中输入多名学生信息
        * 如学号，姓名，专业，班级，家庭住址等
        * 待输入"bye"时结束
        * 将输入内容保存在myfile1.txt文件中。
        * */
        Scanner sc=new Scanner(System.in);
        System.out.println("请输入学生信息，输入bye结束输入：");
        System.out.println("学号 姓名 专业 班级 家庭住址");
        while(!(sno=sc.next()).equals("bye")){
            name=sc.next();
            profession=sc.next();
            classNo=sc.next();
            address=sc.next();
            output.write(sno+" "+name+" "+profession+" "+classNo+" "+address+"\n");
        }
        output.flush();
        output.close();
        FileWriter output2=new FileWriter(out);
        char[] temp=new char[1];//这里，如果char[100]的话，char默认字符会被写入到文件中去，但是用FileOutput什么流的时候，byte的默认字符不会
        try {
            while (input.read(temp)>0) {
                output2.write(temp);
                output2.flush();
            }
        }catch(Exception e){

        }
    }
}
