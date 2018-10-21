package ThirdThree;

import java.sql.Date;

//去掉一个最高分和一个最低分后，再将总分求平均
class Average_1 implements Average{
    @Override
    public double aver(int num, double sumGrade, double min, double max) {
        return (sumGrade-min-max)/(num-2);
    }
}

//全部数值相加后求平均值
class Average_2 implements Average{
    @Override
    public double aver(int num, double sumGrade, double min, double max) {
        return sumGrade/num;
    }
}

class Student extends Average_2{
//class Student extends Average_1{
    String name;
    int age;
    MyDate birth;
    double grade;
    static double max=-1;
    static double min=200;
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

    public void outputAver() {
        System.out.println("学生的平均分数为"+aver(num,sumGrade,min,max));
    }
}