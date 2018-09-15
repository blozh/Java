package SixthThree;
import java.io.*;
import java.util.Scanner;

//I am a student.
class Copy {
    public static void main(String [] args) throws IOException {
        File in=new File("/Users/loghder/AndroidStudioProjects/Java/JavaDemo/src/main/Java/SixthThree/myfiel.txt");
        File out=new File("/Users/loghder/AndroidStudioProjects/Java/JavaDemo/src/main/Java/SixthThree/myfile2.txt");
        if(in.createNewFile()&out.createNewFile()){
            String a=new Scanner(System.in).nextLine();
            FileReader input=new FileReader(in);
            FileWriter output=new FileWriter(in);
            output.write(a);
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
}
