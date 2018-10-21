package SecondTwo;

import java.text.SimpleDateFormat;
import java.util.Date;

class Person{
    String name;
    String idCard;
}

class Student extends Person{
    int Sno;
    Date date;//入学日期
    double gpa;
    double money;
    boolean exist;//决定账户是否存在

    //开户
    Student(String name,String idCard,int Sno,double gpa,double money){
        this.name=name;
        this.idCard=idCard;
        this.Sno=Sno;
        this.date=new Date();
        this.gpa=gpa;
        this.exist=true;
        this.money=money;
    }
    //存款
    void saveMoney(double money){
        if(exist)
        this.money+=money;
        else
            System.out.println("已销户");
    }

    //取款
    void takeOfMoney(double money){
        if(exist)
        this.money-=money;
        else
            System.out.println("已销户");
    }

    //查询
    void display(){
        if(exist) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            System.out.println("学号：" + Sno + "\n姓名：" + name + "\ngpa：" + gpa +"\n身份证号：" + idCard + "\n开户日期：" + sdf.format(date) + "\n余额：" + money + '\n');
        }
        else
            System.out.println("已销户");
    }

    //销户
    void disExist(){
        exist=false;
    }
}

public class Demo {
    public static void main(String[] args) {
        Student s=new Student("张三","130926199999999999",160393,3.79,1000);
        s.display();
        s.saveMoney(1000);
        s.display();
        s.disExist();
        s.takeOfMoney(100);
    }
}
