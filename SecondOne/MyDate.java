package SecondOne;

import java.util.Scanner;

/**
 * Created by loghd on 2018/8/1.
 */


class MyDate {
    int year;
    int month;
    int date;

    static boolean isLegal(int year,int month,int date){
        if(year < 0 || month < 1 || month > 12 || date <1 || date > 31)
            return false;

        if(1 == month || 3 == month || 5 == month || 7 == month || 8 == month || 10 == month || 12 == month){
            return true;
        }
        else if(date>30)
            return false;
        if(((year%4 == 0) && (year%100 != 0)) || (year%400 == 0)){
            if(2 == month && date<=29)
                return true;
        }
        else {
            if(2 == month && date<=28)
            return true;
        }
        return false;
    }
    MyDate(int year,int month,int date){
        if(!isLegal(year, month, date)){
            System.out.println("日期输入不合法，请重试");
        }
        this.year=year;
        this.month=month;
        this.date=date;
    }
}
