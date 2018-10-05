package ATest;

public class Point {
    private double x;
    private double y;

    Point(double x,double y){
        this.x=x;
        this.y=y;
    }

    @Override
    public String toString() {
        return "("+x+","+y+')';
    }
}

class Color{
    private int red=0;
    private int green=0;
    private int blue=0;

    private boolean judge(int x){
        return x<256 && x>=0;
    }

    Color(int red,int green,int blue){
        if(judge(red)&&judge(green)&&judge(blue)){
            this.red=red;
            this.blue=blue;
            this.green=green;
        }else{
            System.out.println("Error：初始化的颜色数值不符合要求");
        }
    }

    @Override
    public String toString() {
        return "("+red+","+green+","+blue+')';
    }
}

class Pixel extends Point{

    private Color cl;

    Pixel(int r,int g,int b,double x,double y){
        super(x,y);
        cl=new Color(r,g,b);
    }

    @Override
    public String toString() {
        return "像素点"+super.toString()+"，其颜色为："+cl.toString();
    }
}


