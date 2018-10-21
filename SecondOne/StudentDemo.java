package SecondOne;

import java.util.Scanner;

/**
 * 王献辉 23 1995 02 28 90
 * 二狗子 22 2222-22-22 22
 */

class StudentDemo {
    public static void main(String[] args){
        int num=2;
        Scanner in=new Scanner(System.in);
        Student[] a=new Student[num];
        System.out.println("请依次输入"+num+"个学生的信息");
        System.out.println("格式为姓名 年龄 日期 分数");
        for (int i=0;i<num;i++){
            //姓名 年龄 日期 分数
            String name=in.next();
            int age=in.nextInt();
            int year,month,date;
             year=in.nextInt();
             month=in.nextInt();
             date=in.nextInt();
            double grade=in.nextDouble();
            while(!MyDate.isLegal(year,month,date)){
                System.out.println("日期输入不合法，请重新输入日期");
                year=in.nextInt();
                month=in.nextInt();
                date=in.nextInt();
            }
            a[i]=new Student(name,age,year,month,date,grade);
        }
        for(int i=0;i<num;i++){
            a[i].output();
        }
        Student.outputAver();
    }
}
