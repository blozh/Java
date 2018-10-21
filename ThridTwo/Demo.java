package ThridTwo;

import java.util.Scanner;

//设计一个Java程序，自定义异常类，从命令行（键盘）输入一个字符串，如果该字符串值为“XYZ”，则抛出一个异常信息“This is a XYZ”，如果从命令行输入ABC，则没有抛出异常。
//（只有XYZ和ABC两种输入）。
class Modify extends Exception{
    Modify(String x){//因为子类并不能继承父类的构造方法，所以需要这一步
        super(x);
    }
}
class Demo {
    public static void main(String[] args) throws Modify{
        String a=new Scanner(System.in).next();
        if(a.equals("XYZ")){
            throw new Modify("This is XYZ");
        }
    }
}
