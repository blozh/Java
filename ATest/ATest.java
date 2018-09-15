package ATest;

class ATest {
    public static void main(String [] args){
        double[] a=new double[4];
        int c=0;
        for(int i=0;i<4;i++){
            a[i]=i;
        }
        for(double b:a){
            System.out.println(b);
        }
    }
}
