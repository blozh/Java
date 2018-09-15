package firstOne;

import java.util.Scanner;

/**
 * 王献辉 23 1995-03-18 90
 * 二狗子 22 2222-22-22 22
 */

class StudentDemo {
    public static void main(String[] args){
        int num=2;
        Scanner in=new Scanner(System.in);
        Student[] a=new Student[num];
        System.out.println("请依次输入"+num+"个学生的信息");
        System.out.println("格式为姓名 年龄 日期 分数");
        for(Student b:a){
            b=new Student(in.next(),in.nextInt(),in.next(),in.nextDouble());
        }

        Student.outputAver();
        for(int i=0;i<num;i++){
            a[i].output();
        }
    }
}
