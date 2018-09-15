package ThirdThree;

import java.util.InputMismatchException;
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
            int age=0;
            int year=0,month=0,date=0;
            try{
                age=in.nextInt();
                year=in.nextInt();
                month=in.nextInt();
                date=in.nextInt();
            }catch(InputMismatchException e){
                in.next();
                System.out.println("输入的字符串无法转化为数字");
            }
            while(!MyDate.isLegal(year,month,date)){
                System.out.println("日期输入不合法，请重试");
                try{
                    year=in.nextInt();
                    month=in.nextInt();
                    date=in.nextInt();
                }catch(InputMismatchException e){
                    in.next();
                    System.out.println("输入的字符串无法转化为数字");
                }
            }
            double grade=in.nextDouble();
            a[i]=new Student(name,age,year,month,date,grade);
        }
        for(int i=0;i<num;i++){
                a[i].output();
            }
        a[0].outputAver();
    }
}
