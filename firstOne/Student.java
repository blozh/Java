package firstOne;

import java.sql.Date;

/**
 * Created by loghd on 2018/8/1.
 */

class Student {
    String name;
    int age;
    Date birth=new Date(0);
    double grade;
    static double sumGrade=0;
    static int num=0;
    static double max=-1;
    static double min=200;
    //日期格式要求为y-m-d
    Student(String name,int age,String date,double grade){
        this.name=name;
        this.age=age;
        Date.valueOf(date);
        this.grade=grade;
        num++;
        sumGrade+=grade;
        if(grade>max)
            max=grade;
        if(grade<min)
            min=grade;
    }

    void output(){
        System.out.println(name+"的年龄为"+age);
    }



    static void outputAver(){
        System.out.println("学生Java课实验成绩的平均值为"+sumGrade/num);
    }
}
