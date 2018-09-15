package firstTwo;

import java.util.Arrays;
import java.util.Scanner;

class ArraysDemo{
    public static void main(String[] args){
        int a[]={12,34,9,-23,45,6,90,123,19,45,34};
        int x=new Scanner(System.in).nextInt();
        Arrays.sort(a);
        System.out.println("排序后的数组为\n"+Arrays.toString(a));
        int result=Arrays.binarySearch(a,x);
        if(result==-2)
            System.out.println("该整数不在数组中");
        else
            System.out.println("该整数是数组的第"+(result+1)+"个元素");
    }
}