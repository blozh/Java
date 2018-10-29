package ATest;

class NoWater extends Exception {}
class NoDrinkableWater extends NoWater {}

public class ABC {
    static int count = 0;
    public static void main(String[] args) throws NoWater {
        while ( true ) {
            try {
                count++;
                if ( count == 1 ) {
                    System.out.println("OK");
                } else if ( count == 2 ) {
                    System.out.println("Exception raised: NoDrinkableWater");
                    throw new NoDrinkableWater();
                } else if ( count == 3 ) {
                    System.out.println("Exception raised: NoWater");
                    throw new NoWater();
                }
            } catch (NoDrinkableWater e) {
                System.out.println(e);
            } finally {
                System.out.println("finally");
                if ( count == 3 )
                    break;
            }
        }
    }
}