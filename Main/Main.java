package Main;
import java.util.Scanner;
import java.util.Arrays;
class ArrayUtils{
    public static double findMax(double[] arr,int begin, int end) throws Exception{
        double max=arr[begin];
        try {
            if(begin>=end )
                throw new IllegalArgumentException();
            if(begin<0)
                throw new NegativeArraySizeException();
            if(end>arr.length)
                throw new ArrayIndexOutOfBoundsException();
            else
                for(int i=begin; i<end;i++)
                    if(max<arr[i])
                        max=arr[i];
            return max;
        }catch(IllegalArgumentException e1) {
            System.out.println("java.lang.IllegalArgumentException: begin:"+begin+" >= end:"+end);
        }catch(NegativeArraySizeException e2) {
            System.out.println("java.lang.IllegalArgumentException: begin:"+begin+" < 0");
        }catch(ArrayIndexOutOfBoundsException e3) {
            System.out.println("java.lang.IllegalArgumentException: end:"+end+" > arr.length");
        }
        return max;
    }
}
public class Main {
    public static void main(String[] args) throws Exception  {
        Scanner sc=new Scanner(System.in);
        int n=sc.nextInt();
        double a[]=new double[n];
        for(int i=0;i<n;i++)
            a[i]=sc.nextInt();
        while(sc.hasNextInt()) {
            System.out.println(ArrayUtils.findMax(a, sc.nextInt(), sc.nextInt()));
        }
        try {
            System.out.println(ArrayUtils.class.getDeclaredMethod("findMax", double[].class,int.class,int.class));
        } catch (Exception e1) {
        }
    }
}


