package firstThreetoFive;

public class Main {
    public static void main(String[] args) {
        int i,j,k;
        for (i = 0; i < 10; i++) {
            for (j = 0; j < 10; j++) {
                for (k = 0; k < 10; k++) {
                    if((100*i+110*j+12*k)==532)
                        System.out.println("X="+i+"\nY="+j+"\nZ="+k);
                }
            }
        }
    }
}
