package Compiles;

//方法类
final class Analysis {
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

    private static String outputResult(String mark, String value,String output){
        return output+'('+mark+','+value+')'+'\n';
    }

    private static String errorResult(int rowNum, String value, String type,String error) {
        return error+"Error："+rowNum+"行,"+value+','+type+'\n';
    }

    public static String LexicalAnalysis(String input){
        String output="";
        String error="";
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
                        output=outputResult(s.toUpperCase(),"",output);
                    }
                    else{
                        output=outputResult("ID","\""+s+"\"",output);
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
                        output=outputResult("UCON_整数",s,output);
                    }
                    else if(temp=='.'&&isNumber(input.charAt(i+1))){
                        s+='.';
                        temp=input.charAt(++i);
                        while(isNumber(temp)){
                            s+=temp;
                            temp=input.charAt(++i);
                        }
                        if(isEnd(temp)){
                            output=outputResult("UCON_小数",s,output);
                        }
                        else{
                            while(!isEnd(temp)){
                                s+=temp;
                                temp=input.charAt(++i);
                            }
                            error=errorResult(rowNum,s,"小数错误",error);
                        }
                    }
                    else{
                        while(!isEnd(temp)){
                            s+=temp;
                            temp=input.charAt(++i);
                        }//eg 12.3edfe,此循环用于略过edfe这四个字母
                        error=errorResult(rowNum,s,"数字错误",error);
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
                            output=outputResult("双界符",s,output);
                            i++;
                            break;
                        case ',':
                        case '.':
                        case ';':
                            output=outputResult("单界符",s,output);
                            i++;
                            break;
                        case '+':
                            output=outputResult("PL",s,output);
                            i++;
                            break;
                        case '-':
                            output=outputResult("MI",s,output);
                            i++;
                            break;
                        case '*':
                            output=outputResult("MU",s,output);
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
                                output=outputResult("DI",s,output);
                                i++;
                                break;
                            }
                        case':':
                            if(input.charAt(i+1)=='='){
                                s+='=';
                                i+=2;
                                output=outputResult("IS",s,output);
                            }
                            else {
                                error=errorResult(rowNum,s,"IS",error);
                                i++;//略过错误号
                            }
                            break;
                        case'|':
                        case'&':
                            if(input.charAt(i+1)==input.charAt(i)){
                                s+=input.charAt(i);
                                i+=2;
                                output=outputResult("短路逻辑运算符",s,output);
                            }
                            else {
                                output=outputResult("非短路逻辑运算符",s,output);
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
                                case">":output=outputResult("GT",s,output);break;
                                case">=":output=outputResult("GE",s,output);break;
                                case"<":output=outputResult("LT",s,output);break;
                                case"<=":output=outputResult("LE",s,output);break;
                                case"<>":output=outputResult("NE",s,output);break;
                            }
                            break;
                        case '=':
                            output=outputResult("EQ",s,output);
                            i++;
                            break;
                        default:
                            i++;
                            error=errorResult(rowNum,s,"未知错误",error);
                    }
                }
                i--;//字符指针退一位
            }
        }
        if(error.equals("")){
            return "NO ERROR\n- - - - - - - - - - -\n"+output ;
        }else {
            return error + "- - - - - - - - - - -\n" + output;
        }
    }

    public static String ParseAnalysis(String input){
        String output="";
        return output;
    }

    public static String SenmanticAnalysis(String input){
        String output="";
        return output;
    }
}
