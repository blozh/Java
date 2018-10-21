package firstThreetoFive;

import java.util.Scanner;

public class Demo {
    public static void main(String[] args) {
        Scanner sc=new Scanner(System.in);
        int option=sc.nextInt();
        switch(option){
            case 3:
                for (int i = 100; i < 201; i++) {
                    int j;
                    for(j=2;j*j<=i;j++){
                        if(i%j==0){
                            break;
                        }
                    }
                    if(j*j>i)
                        System.out.print(i+" ");
                }
                System.out.println();
                break;
            case 4:
                for (int i = 6; i < 1001; i++) {
                    int sum=0;
                    for(int j=1;j<i;j++){
                        if(i%j==0){
                            sum+=j;
                        }
                    }
                    if(sum==i)
                        System.out.print(i+" ");
                }
                System.out.println();
                break;
            case 5:
                int i,j,k;
                for (i = 0; i < 10; i++) {
                    for (j = 0; j < 10; j++) {
                        for (k = 0; k < 10; k++) {
                            if((100*i+110*j+12*k)==532)
                                System.out.println("x="+i+"\ny="+j+"\nz="+k);
                        }
                    }
                }
                break;
        }
    }
}
