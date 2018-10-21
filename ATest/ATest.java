package ATest;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Scanner;

public class ATest {
    int[] fib(int n){
        int[] a=new int[n];
        if(n==1)
            a[0]=1;
        else if(n==2){
            a[0]=1;
            a[1]=1;
        }
        else{
            a[0]=1;
            a[1]=1;
            for(int i=2;i<n;i++){
                a[i]=a[i-1]+a[i-2];
            }
        }
        return a;
    }
    public static void main(String[] args) {
        Scanner sc=new Scanner(System.in);
        String option;
        Integer[] intArr=new Integer[1];
        while(true){
            option=sc.nextLine();
            switch (option){
                case "fib":
                    int n=sc.nextInt();
                    sc.nextLine();
                    int[]a=new ATest().fib(n);
                    for(int i=0;i<a.length;i++){
                        System.out.print(a[i]+" ");
                    }
                    System.out.println();
                    break;
                case "sort":
                    String str=sc.nextLine();
                    String[] strArr = str.split(" ");
                    intArr = new Integer[strArr.length]; //定义一个长度与上述的字符串数组长度相通的整型数组
                    for(int i=0;i<strArr.length;i++){
                        intArr[i] = Integer.valueOf(strArr[i]); //然后遍历字符串数组，使用包装类Integer的valueOf方法将字符串转为整型
                    }
                    Arrays.sort(intArr);
                    System.out.print("[");
                    for(int i=0;i<strArr.length-1;i++){
                        System.out.print(intArr[i]+",");
                    }
                    System.out.println(intArr[intArr.length-1]+"]");
                    break;
                case "search":
                    int x=sc.nextInt();
                    sc.nextLine();
                    int result=Arrays.binarySearch(intArr,x);
                    if(result<0)
                        System.out.println(-1);
                    else
                        System.out.println(0);
                    break;
                case "getBirthDate":
                    int w=sc.nextInt();
                    sc.nextLine();
                    String[] sfz=new String[w];
                    for(int i=0;i<w;i++){
                        sfz[i]=sc.nextLine();
                    }
                    for(int i=0;i<w;i++){
                        sfz[i]=(sfz[i].substring(6,10)+'-'+sfz[i].substring(10,12)+'-'+sfz[i].substring(12,14));
                        System.out.println(sfz[i]);
                    }
                    break;
                   default:System.out.println("exit");break;
            }
        }
    }
}