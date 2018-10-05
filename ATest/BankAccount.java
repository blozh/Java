package ATest;

import java.text.SimpleDateFormat;
import java.util.Date;

public class BankAccount {
    private String account;//账号
    private String name;
    private Date date;//开户时间
    private String IDCard;//身份证号
    private double money=0;//存款余额(元)

    //开户
    BankAccount(String account,String name,String IDCard){
        this.account=account;
        this.name=name;
        this.IDCard=IDCard;
        date=new Date();
        System.out.println("开户信息如下，请核实：");
        this.display();
    }

    //显示账户信息，包括余额
    void display(){
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        System.out.println("账号："+account+"\n储户姓名："+name+"\n身份证号："+IDCard+"\n开户日期："+sdf.format(date)+"\n余额："+money+'\n');
    }

    //存款
    void saveMoney(double money){
        this.money+=money;
    }

    //取款
    void takeOfMoney(double money){
        this.money-=money;
    }

}
