package Compiles;

import java.util.ArrayList;
import java.util.List;

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
    public static void ParseAnalysis(String input){
    }
    /*语义分析*/
    public static void SenmanticAnalysis(String input){
    }
}
