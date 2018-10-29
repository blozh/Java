package Compiles;

import java.awt.Color;
import java.awt.Component;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

//方法类
final class Analysis {
    /*词法分析*/
    private static ArrayList<String> values;
    private static ArrayList<String> types;
    private static ArrayList<String> errorvalues;
    private static ArrayList<String> errors;
    private static ArrayList<Integer> errorsRows;

    private static boolean isAlpha(char c){
        return ((c<='z')&&(c>='a')) || ((c<='Z')&&(c>='A')) || (c=='_');
    }

    private static boolean isNumber(char c){
        return (c>='0')&&(c<='9');
    }

    private static boolean isKey(String t){
        String[] key={"begin","end","if","then","else","if","then","public","private","protected"};
        for(int i=0;i<key.length;i++){
            if (t.equals(key[i])) {
                return true;
            }
        }
        return false;
    }

    //数字合法结束符号
    private static boolean isEnd(char t){
        return t==' '||t=='\t'||t=='\n'||t=='\0'||
                t==';'||t==','||t=='+'||t=='-'||t=='*'||
                t=='/'||t==')'||t=='}'||t==']'||t=='|'||t=='&';
    }

    public static void LexicalAnalysis(String input){
        values=new ArrayList<>();
        types=new ArrayList<>();
        errors=new ArrayList<>();
        errorvalues=new ArrayList<>();
        errorsRows=new ArrayList<>();

        int rowNum=1;

        char c;
        input+='\0';//作为字符串结束标志，保证字符串读取到最后一个时，还可以提前读取最后一个字符的下一个字符
        //因此for循环中 input.length()-1
        for(int i=0;i<input.length()-1;i++){
            c=input.charAt(i);
            if(c=='\n'){//在界面代码中，已经把所有换行符都转变为\n了，不需要考虑\r
                rowNum++;
            }
            //如果读取到空格或者\t 继续读取下一个字符串 为了删除多行注释这里不能放\n
            else if(c!=' '&&c!='\t'){
                String s="";
                //判断第一个输入的字符
                if(isAlpha(c)){
                    s+=c;
                    char temp=input.charAt(++i);//查看下一个字符
                    //只有下一个字符是字母或数字时循环才运行
                    while(isAlpha(temp)||isNumber(temp)){
                        s+=temp;
                        temp=input.charAt(++i);
                    }
                    if(isKey(s)){
                        types.add(s.toUpperCase());
                        values.add(" ");
                    }
                    else{
                        types.add("ID");
                        values.add(s);
                    }
                }
                else if(isNumber(c)){
                    s+=c;
                    char temp=input.charAt(++i);
                    while(isNumber(temp)){
                        s+=temp;
                        temp=input.charAt(++i);
                    }
                    if(isEnd(temp)){
                        types.add("UCON_整数");
                        values.add(s);
                    }
                    else if(temp=='.'&&isNumber(input.charAt(i+1))){
                        s+='.';
                        temp=input.charAt(++i);
                        while(isNumber(temp)){
                            s+=temp;
                            temp=input.charAt(++i);
                        }
                        if(isEnd(temp)){
                            types.add("UCON_小数");
                            values.add(s);
                        }
                        else{
                            while(!isEnd(temp)){
                                s+=temp;
                                temp=input.charAt(++i);
                            }
                            errors.add("小数错误");
                            errorvalues.add(s);
                            errorsRows.add(rowNum);
                        }
                    }
                    else{
                        while(!isEnd(temp)){
                            s+=temp;
                            temp=input.charAt(++i);
                        }//eg 12.3edfe,此循环用于略过edfe这四个字母
                        errors.add("数字错误");
                        errorvalues.add(s);
                        errorsRows.add(rowNum);
                    }
                }
                else{
                    s+=c;
                    switch (c){
                        case '[':
                        case ']':
                        case '(':
                        case ')':
                        case '{':
                        case '}':
                            types.add("双界符");
                            values.add(s);
                            i++;
                            break;
                        case ',':
                        case '.':
                        case ';':
                            types.add("单界符");
                            values.add(s);
                            i++;
                            break;
                        case '+':
                            types.add("PL");
                            values.add(s);
                            i++;
                            break;
                        case '-':
                            types.add("MI");
                            values.add(s);
                            i++;
                            break;
                        case '*':
                            types.add("MU");
                            values.add(s);
                            i++;
                            break;
                        case'/':
                            if(input.charAt(i+1)=='/'){
                                i++;
                                char temp=input.charAt(++i);
                                while(temp!='\n'&&temp!='\0'){
                                    temp=input.charAt(++i);//略过注释
                                }
                                break;
                            }
                            else if(input.charAt(i+1)=='*'){
                                i++;
                                char temp=input.charAt(++i);
                                while(temp!='*'||input.charAt(i+1)!='/'){
                                    temp=input.charAt(++i);//略过注释
                                }
                                i+=2;
                                break;
                            }
                            else{
                                types.add("DI");
                                values.add(s);
                                i++;
                                break;
                            }
                        case':':
                            if(input.charAt(i+1)=='='){
                                s+='=';
                                i+=2;
                                types.add("IS");
                                values.add(s);
                            }
                            else {
                                errors.add("IS错误");
                                errorvalues.add(s);
                                errorsRows.add(rowNum);
                                i++;//略过错误号
                            }
                            break;
                        case'|':
                        case'&':
                            if(input.charAt(i+1)==input.charAt(i)){
                                s+=input.charAt(i);
                                i+=2;
                                types.add("短路逻辑运算符");
                                values.add(s);
                            }
                            else {
                                types.add("非短路逻辑运算符");
                                values.add(s);
                                i++;//略过错误号
                            }
                            break;
                        case '>':
                        case'<':
                            if(input.charAt(i+1)=='='){
                            s+='=';
                            i+=2;
                            }
                            else if(c=='<'&&input.charAt(i+1)=='>'){
                                s+='>';
                                i+=2;
                            }
                            else {
                                i++;//略过
                            }
                            switch (s){
                                case">":types.add("GT");values.add(s);break;
                                case">=":types.add("GE");values.add(s);break;
                                case"<":types.add("LT");values.add(s);break;
                                case"<=":types.add("LE");values.add(s);break;
                                case"<>":types.add("NE");values.add(s);;break;
                            }
                            break;
                        case '=':
                            types.add("EQ");values.add(s);
                            i++;
                            break;
                        default:
                            i++;
                            errors.add("未知错误");
                            errorvalues.add(s);
                            errorsRows.add(rowNum);
                    }
                }
                i--;//字符指针退一位
            }
        }
    }

    public static ArrayList<String> getTypes() {
        return types;
    }

    public static ArrayList<String> getValues() {
        return values;
    }

    public static ArrayList<String> getErrors() {
        return errors;
    }

    public static ArrayList<String> getErrorvalues() {
        return errorvalues;
    }

    public static ArrayList<Integer> getErrorsRows() {
        return errorsRows;
    }

    /*语法分析*/
    //规约式转换
    static char reduce(String x){
        if(x.length()==1)
            return 'F';
        else if(x.charAt(0)=='(')
            return 'F';
        char[] from={'*','/','+','-'};
        char[] to={'T','T','E','E'};
        for (int i = 0; i < from.length; i++) {
            if(from[i]==x.charAt(1))
                return to[i];
        }
        return ' ';
    }

    //用于输出到表格中的规则式转换
    static String reduceGongshi(String x){
        if(x.length()==1)
            return "F->i";
        else if(x.charAt(0)=='(')
            return "F->(E)";
        char[] from={'*','/','+','-'};
        String[] to={"T->T*F","T->T/F","E->E+T","E->E-T"};
        for (int i = 0; i < from.length; i++) {
            if(from[i]==x.charAt(1))
                return to[i];
        }
        return " ";
    }

    //判断是否为终结符
    static boolean isVt(char x){
        char[] vt={'i','(',')','+','-','*','/','#'};
        int i;
        for (i = 0; i < vt.length; i++) {
            if(vt[i]==x)
                break;
        }
        if(i==vt.length)
            return false;
        else
            return true;
    }

    //优先级判断，根据横纵坐标取出大小于符号,1为前者大于后者，0为等于，-1为小于
    static int prioriyJudge(DefaultTableModel model2,char x,char y){
        int xx=0,yy=0;
        for (int i = 0; i < model2.getRowCount(); i++) {
            if(x==model2.getValueAt(i,0).toString().charAt(0))
                xx=i;
            if(y==model2.getValueAt(i,0).toString().charAt(0))
                yy=i;
        }
        int result=0;
        switch (model2.getValueAt(xx,yy+1).toString()){
            case ">":model2.setValueAt(">    █",xx,yy+1);result=1;break;
            case "=":model2.setValueAt("=    █",xx,yy+1);result=0;break;
            case "<":model2.setValueAt("<    █",xx,yy+1);result=-1;break;
        }
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        switch (result){
            case 1:model2.setValueAt(">",xx,yy+1);break;
            case 0:model2.setValueAt("=",xx,yy+1);break;
            case -1:model2.setValueAt("<",xx,yy+1);break;
        }
        return result;
    }



        public static void ParseAnalysis(DefaultTableModel model, DefaultTableModel model2, JTable t2){

        model.setRowCount(0);//清空表格
        int num=0;//用于记录步骤
        String a="",stack="#",Gongshi="";
        char r;
        //将队列转换为字符串
        for(int i=0;i<types.size();i++){
            if(types.get(i)=="UCON_小数"||types.get(i)=="UCON_整数")
                a+="i";
            else
                a+=values.get(i);
        }
        a+="#";
        //System.out.println(a);
        int i=0,k=0;//i为字符串指针，k为stack指针
        do{
            int j;
            r=a.charAt(i++);
            if(isVt(stack.charAt(k)))
                j=k;
            else
                j=k-1;
            while(prioriyJudge(model2,stack.charAt(j),r)>0){
                char q;
                do{
                    q=stack.charAt(j);
                    if(isVt(stack.charAt(j-1)))
                        j--;
                    else
                        j-=2;
                }while(prioriyJudge(model2,stack.charAt(j),q)>=0);
                char newVn=reduce(stack.substring(j+1,k+1));
                Gongshi=reduceGongshi(stack.substring(j+1,k+1));
                k=j+1;
                stack=stack.substring(0,k);
                stack+=newVn;
                num++;
                model.addRow(new String[]{num+"",stack,a.substring(i,a.length()),""});
                model.setValueAt(Gongshi,model.getRowCount()-2,3);
            }
            if(prioriyJudge(model2,stack.charAt(j),r)<=0){
                stack+=r;
                k++;
                num++;
                model.addRow(new String[]{num+"",stack,a.substring(i,a.length()),""});
            }
            else {
                //写入error到表格
                num++;
                model.addRow(new String[]{"ERROR", "", "", ""});
            }
        }while(r!='#');
    }
    /*语义分析*/
    public static void SenmanticAnalysis(String input){
    }
}


