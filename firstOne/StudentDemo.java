package firstOne;

import java.util.Scanner;

/**
 张三 1 2017-3-3 90
 李四 2 2016-2-3 80
 王五 3 2015-1-3 70
 李流 4 2014-2-3 60
 刘⑦ 5 2013-3-3 50
 陈八 6 2012-4-4 40
 黄久 7 2011-5-5 30
 孙二 8 2010-6-6 20
 崔一 9 2009-7-7 10
 丁十 10 2008-8-8 0
 */

class StudentDemo {
    public static void main(String[] args){
        int num=10;
        Scanner in=new Scanner(System.in);
        Student[] a=new Student[num];
        System.out.println("请依次输入"+num+"个学生的信息");
        System.out.println("格式为姓名 年龄 出生年月日 java课程实验成绩");
        for(int i=0;i<num;i++){
            a[i]=new Student(in.next(),in.nextInt(),in.next(),in.nextDouble());
        }

        Student.outputAver();
        for(int i=0;i<num;i++){
            a[i].output();
        }
    }
}
