package SecondFour;

interface Shape{
    abstract double getArea();
    abstract double getPerimeter();
}

class Coordinates implements Shape
{
    long x;
    long y;
    Coordinates(){
        this(0, 0);
    }
    Coordinates(long x, long y)
    {
        this.x=x;
        this.y=y;
    }
    @Override
    public double getArea() {
        return 0;
    }

    @Override
    public double getPerimeter() {
        return 0;
    }
}

class Rectangle extends Coordinates implements Shape{
    double a,b;//长宽
    Rectangle(long x, long y,double a,double b){
        super(x,y);
        this.a=a;this.b=b;
        }

    @Override
    public double getArea() {
        return a*b;
    }

    @Override
    public double getPerimeter() {
        return 2*(a+b);
    }
}


class Circle extends Coordinates implements Shape{
    double r;
    Circle(long x, long y,double r){
        super(x,y);
        this.r=r;
    }
    public double getArea(){
        return (Math.PI*r*r);
    }
    public double getPerimeter(){
        return (2*Math.PI*r);
    }
}

class Triangle extends Coordinates implements Shape{
    Coordinates c2;
    Coordinates c3;
    //边长
    double x;
    double y;
    double z;

    Triangle(long x, long y,long a,long b,long c,long d){
        super(x,y);
        c2=new Coordinates(a,b);
        c3=new Coordinates(c,d);
        this.x=(long)Math.sqrt(Math.pow(x-a,2)+Math.pow(y-b,2));
        this.y=(long)Math.sqrt(Math.pow(x-c,2)+Math.pow(y-d,2));
        this.z=(long)Math.sqrt(Math.pow(c-a,2)+Math.pow(d-b,2));
        System.out.println();
    }
    public double getArea(){
        double p=(x+y+z)/2;
        double area = Math.sqrt(p * (p - x) * (p - y) * (p - z));
        return area;
    }
    public double getPerimeter(){
        return x+y+z;
    }
}

public class Demo {
    public static void main(String[] args) {
        Coordinates[] c=new Coordinates[3];
        //两边长度分别为3和4的矩形
        c[0]=new Rectangle(0,0,3,4);
        //半径为8的圆
        c[1]=new Circle(0,0,1);
        //三点坐标分别为(0,0),(3,0),(3,4)的三角形
        c[2]=new Triangle(0,0,3,0,3,4);
        for (Coordinates t:
             c) {
            System.out.println("面积："+t.getArea());
            System.out.println("周长："+t.getPerimeter());
            System.out.println();
        }
    }
}
