package SecondThree;

class Vehicle{
    int wheels;
    double weight;
    
    Vehicle(int wheels,double weight){
        this.wheels=wheels;
        this.weight=weight;
    }
    
    void print(){
        System.out.println("车轮个数："+wheels);
        System.out.println("车重："+weight+"吨");
    }
}

class Car extends Vehicle{
    int loader;
    
    Car(int wheels,double weight,int loader){
        super(wheels,weight);
        this.loader=loader;
    }
    
    void print(){
        super.print();
        System.out.println("载人数："+loader+"人");
    }
}

class Truck extends Car{
    double payload;
    
    Truck(int wheels,double weight,int loader,double payload){
        super(wheels,weight,loader);
        this.payload=payload;
    }
    void print(){
        super.print();
        System.out.println("载重量："+payload+"吨");
    }
}

public class Demo {
    public static void main(String[] args) {
        Vehicle[] v=new Vehicle[3];
        v[0]=new Vehicle(4,3);
        v[1]=new Car(4,2,4);
        v[2]=new Truck(6,10,2,20);
        for (Vehicle t:
             v) {
            t.print();
        }
    }
}
