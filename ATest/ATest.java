import java.util.Scanner;

public class ATest {
        public static void main(String[] args)
        {
            Scanner sc=new Scanner(System.in);
            //输入一个数据在新创建键盘录入对象之后
            int number=sc.nextInt();
            String s=zuoMiMa(number);
            System.out.println("字符串为:"+s);
        }

        public static String zuoMiMa(int number)
        {
            int[] arr=new int[10];
            int i=0,count=0;
            while(number>0)
            {
                arr[i]=number%10;//得到的是最后一位数，因为要得到逆序输出
                number/=10;
                count++;
                i++;
            }
            System.out.println("这是逆序输出：");
            for(i=0;i<count;i++)
            {
                System.out.print(arr[i]+",");
            }
            System.out.println("$$$$$$$$$$");
            String s="";
            for(i=0;i<count;i++)
            {
                s+=arr[i];
            }
            return s ;
        }

}
