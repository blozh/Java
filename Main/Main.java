package Main;
import java.util.Scanner;
interface Shape
{
    abstract float getArea();//求面积
    abstract float getPerimeter();//求周长
}
class Circle implements Shape
{
    private float r=0;
    private final static float PI=(float)Math.PI;
    public Circle (float r)
    {
        this.r=r;
    }
    public float getArea()
    {
        return PI*r*r;
    }
    public float getPerimeter()
    {
        return 2*PI*r;
    }
}
class rectangle implements Shape
{
    private float length=0;
    private float wide=0;
    public rectangle(float length,float wide)
    {
        this.length=length;
        this.wide=wide;
    }
    public float getArea()
    {
        return (length*wide);
    }
    public float getPerimeter()
    {
        return 2*(length+wide);
    }
}
public class Main
{
    public static void main(String[] args)
    {
        Scanner sc = new Scanner(System.in);
        int a = sc.nextInt();
        for(int i=0;i<a;i++)
        {
            int b = sc.nextInt();
            if(b==1)
            {
                float c=sc.nextFloat();
                Shape circle =new Circle(c);
                System.out.printf("%.2f",circle.getArea());
                System.out.printf(" ");
                System.out.printf("%.2f",circle.getPerimeter());
            }
            if(b==2)
            {
                float d=sc.nextFloat();
                float e=sc.nextFloat();
                Shape rectangle =new rectangle(d,e);
                System.out.printf("%.2f",rectangle.getArea());
                System.out.printf(" ");
                System.out.printf("%.2f",rectangle.getPerimeter());
            }
            System.out.println();
        }
    }
}
