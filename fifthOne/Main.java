package fifthOne;

/*编写一个有两个线程的程序
第一个线程用来计算1~100之间的偶数及个数
第二个线程用来计算1-100之间的偶数及个数。
* */
class Mod extends Thread{
    static int no=0;
    Mod(){
        super();
        no++;
        this.setName("线程"+no);
    }
    @Override
    public void run() {
        int sum=0;
        for (int i = 1; i < 101; i++) {
            if(i%2==0){
                sum++;
                System.out.println(this.getName()+":"+i);
            }
        }
        System.out.println(this.getName()+":偶数个数总共有"+sum);
    }
}

public class Main extends Thread{
    public static void main(String[] args) {
        Mod[] m=new Mod[2];
        for (int i = 0; i < 2; i++) {
            m[i]=new Mod();
        }
        m[0].start();
        m[1].start();
    }
}
