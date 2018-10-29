package fifthThree;

class Person{
    int five;
    int ten;
    int twenty;
}

class Buyer extends Person implements Runnable{
    String name;
    int ticket;
    Seller s;

    Buyer(String name,int five,int ten,int twenty,int ticket){
        this.name=name;
        this.ticket=ticket;
        this.five=five;
        this.ten=ten;
        this.twenty=twenty;
    }

    void setSeller(Seller s){
        this.s=s;
    }

    @Override
    public void run() {
        while(s.sell(this));
    }
}

class Seller extends Person{
    Seller(){
        five=1;
    }

    synchronized boolean sell(Buyer b){
        if(b.twenty==1&&b.ticket==2&&ten>=1){
            twenty++;
            ten--;
            System.out.println(b.name+"购票成功"+"\n此时售票员有二十元"+twenty+"张，"+"十元"+ten+"张，"+"五元"+five+"张。");
            return false;
        }
        if(b.twenty==1&&b.ticket==1&&ten>=1&&five>=1){
            twenty++;
            ten--;
            five--;
            System.out.println(b.name+"购票成功"+"\n此时售票员有二十元"+twenty+"张，"+"十元"+ten+"张，"+"五元"+five+"张。");
            return false;
        }
        if(b.ten==1&&b.ticket==1&&five>=1){
            ten++;
            five--;
            System.out.println(b.name+"购票成功"+"\n此时售票员有二十元"+twenty+"张，"+"十元"+ten+"张，"+"五元"+five+"张。");
            return false;
        }
        //购买者拿出了所有的钱且无需找零时
        if(b.ticket*5==(b.ten*10+b.five*5+b.twenty*20)){
            five+=b.five;
            ten+=b.ten;
            twenty+=b.twenty;
            System.out.println(b.name+"购票成功"+"\n此时售票员有二十元"+twenty+"张，"+"十元"+ten+"张，"+"五元"+five+"张。");
            return false;
        }
        System.out.println(b.name+"购票失败");
        return true;
    }

}

public class Main {
    public static void main(String args[])
    {
        Buyer[] b=new Buyer[5];
        b[0]=new Buyer("赵",0,0,1,2);
        b[1]=new Buyer("钱",0,0,1,1);
        b[2]=new Buyer("孙",0,1,0,1);
        b[3]=new Buyer("李",0,1,0,2);
        b[4]=new Buyer("周",1,0,0,1);
        Seller s=new Seller();
        Thread[] t=new Thread[5];
        for (int i = 0; i < 5; i++) {
            b[i].setSeller(s);
            t[i]=new Thread(b[i]);
        }
        for (int i = 0; i < 5; i++) {
            t[i].start();
        }
    }
}