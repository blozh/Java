import java.util.Scanner;

public class ATest {
        public static void main(String[] args)
        {
            Scanner sc=new Scanner(System.in);
            //����һ���������´�������¼�����֮��
            int number=sc.nextInt();
            String s=zuoMiMa(number);
            System.out.println("�ַ���Ϊ:"+s);
        }

        public static String zuoMiMa(int number)
        {
            int[] arr=new int[10];
            int i=0,count=0;
            while(number>0)
            {
                arr[i]=number%10;//�õ��������һλ������ΪҪ�õ��������
                number/=10;
                count++;
                i++;
            }
            System.out.println("�������������");
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
