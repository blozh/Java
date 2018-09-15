package ThirdThree;

import java.sql.Date;

/**
 * Created by loghd on 2018/8/1.
 */


class Student implements Average{
    String name;
    int age;
    MyDate birth;
    double grade;
    static double max=0;
    static double min=100;
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
        if(grade>max)
            max=grade;
        if(grade<min)
            min=grade;
    }

    void output(){
        System.out.println(name+"的年龄为"+age);
    }

    @Override
    public void outputAver() {
        System.out.println("学生的平均分数为"+sumGrade/num);
    }
}