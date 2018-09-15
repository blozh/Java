package SecondOne;

import java.sql.Date;

/**
 * Created by loghd on 2018/8/1.
 */

class Student {
    String name;
    int age;
    MyDate birth;
    double grade;
    static double sumGrade=0;
    static int num=0;
    //日期格式要求为y-m-d
    Student(String name,int age,int year,int month,int date,double grade){
        this.name=name;
        this.age=age;
        birth=new MyDate(year,month,date);
        this.grade=grade;
        num++;
        sumGrade+=grade;
    }

    void output(){
        System.out.println(name+"的年龄为"+age);
    }

    static void outputAver(){
        System.out.println("学生Java课实验成绩的平均值为"+sumGrade/num);
    }
}
