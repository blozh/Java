package Main;
import java.util.Scanner;

import java.io.FileReader;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileNotFoundException;

public class SLR1 {
	static String []result=new String[50];
	static int step=0;
	String []stus=new String[20];
	static char []vt= {'(',')','+','-','*','/',
			'i','#'};
	static char []vn= {'E','T','F'};
	static String [][]ACTION= {{"S4","","","","","","S5",""},//0
						{"","","S6","S7","","","","ACC"},//1
						{"","R3","R3","R3","S8","S9","","R3"},//2
						{"","R6","R6","R6","R6","R6","","R6"},//3
						{"S4","","","","","","S5",""},//4
						{"","R8","R8","R8","R8","R8","","R8"},//5
						{"S4","","","","","","S5",""},//6
						{"S4","","","","","","S5",""},//7
						{"S4","","","","","","S5",""},//8
						{"S4","","","","","","S5",""},//9
						{"","S15","S6","S7","","","",""},//10
						{"","R1","R1","R1","S8","S9","","R1"},//11
						{"","R2","R2","R2","S8","S9","","R2"},//12
						{"","R4","R4","R4","R4","R4","","R4"},//13
						{"","R5","R5","R5","R5","R5","","R5"},//14
						{"","R7","R7","R7","R7","R7","","R7"},
						};
	static int [][]GOTO= { {1,2,3},
					{0,0,0},
					{0,0,0},
					{0,0,0},
					{10,2,3},
					{0,0,0},
					{0,11,3},
					{0,12,3},
					{0,0,13},
					{0,0,14},
					{0,0,0},
					{0,0,0},
					{0,0,0},
					{0,0,0},
					{0,0,0},
					{0,0,0},};
	char []line=new char[20];//读入要分析的字符串
	char []stack=new char[20];
	int []status=new int[20];
	char []value=new char[20];
	
	int []lala=new int[20];//产生式编号栈
	int index=0;
	int iv=0;
	int count=0;//产生式编号
	String act="",gt="";
	
	int li=0;//list数组l的下标
	void init() {
		//Scanner sc=new Scanner(System.in);
		//String str=sc.nextLine();
		String str="(3+4)*5+(6-7)/8#";
		line=str.toCharArray();
		status[0]=0;
		stack[0]='#';
		value[0]='#';
		lala[0]=0;
		for(int i=0;i<20;i++) {
			result[i]=new String();
			
		}
	}
	int isVt(char c) {//是否为终结符
		for(int i=0;i<8;i++) {
			if(c>='0'&&c<='9')return 6;
			if(vt[i]==c){
				return i;
			}
		}
			
		return 0;
	}
	void Printlala() {
		System.out.print("产生式符号栈的序号为：");
		for(int i=0;i<index;i++)System.out.print(lala[i]+" ");
		System.out.println();
	}
	int Reduce(int begin,int n,int k) {//分析栈，状态栈内容要改，产生式直接给出来了
		try {//1,2,4,5
			FileReader fr=new FileReader("F:\\focin\\production2.txt");
			BufferedReader br=new BufferedReader(fr);
			String str="";
			for(int i=0;i<=n;i++)
				str=br.readLine();//str就是目标产生式
				String []sep=str.split(" ");
			int len=sep[1].length();
			stack[begin-len+1]=str.charAt(0);//分析栈内容更改完成
			int i;
			for(i=0;i<3;i++)
				if(vn[i]==str.charAt(0))break;
			int last=status[k-len];
			status[k-len+1]=GOTO[last][i];
			if(len!=1) {//这种规约要生成四元式
				if(n==7) {iv-=2;value[iv]='T';
				
				//Printlala();
				}
				else {
					char op=value[iv-1];
					count++;
					if(value[iv-2]=='T') {
						int r=count-1;
						String op1='T'+""+r;
						if(value[iv]=='T') {
							System.out.println("四元式：("+op+" , "+op1+" , "+value[iv]+" , "+"T"+count+" )");
							lala[++index]=count;
							//Printlala();
							}
						else {
						System.out.println("四元式：("+op+" , "+op1+" , "+value[iv]+" , "+"T"+count+" )");
						lala[++index]=count;
						//Printlala();
						}
					}
					else {
						System.out.println("四元式：("+op+" , "+value[iv-2]+" , "+value[iv]+" , T"+count+" )");
						lala[++index]=count;
						//Printlala();
					}
					
					iv-=2;
					value[iv]='T';
				}
			}
			br.close();
			return len;
		}catch(Exception e) {
			e.printStackTrace();
		}
		return 0;
	}
	int ry(int i) {
		int j;
		for(j=0;j<8;j++)
			if(line[i]>='0'&&line[i]<='9')
				return 6;
			else if(vt[j]==line[i])
				return j;
		return -1;
	}
	void Print(int n) {
		System.out.print("语义栈信息为：");
		for(int i=0;i<=n;i++)System.out.print(lala[i]);
		System.out.println();
	}
	int SLR1Analyse(){//
		init();
		int i=0,j=0,k=0;//i是输入串line的下标，j是栈stack的下标，k是状态栈status的下标
		int x=status[k];
		int y=ry(i);
		
		while(ACTION[x][y].equals("ACC")==false) {//如果到了ACTION[k][i]或发现是空的就报错并且结束
			if(ACTION[x][y].equals(null))return 0;
			else {	
					String sub=ACTION[x][y].substring(1,ACTION[x][y].length());
					int a=Integer.parseInt(sub);//a为规约用的产生式或者移进的状态
					if(ACTION[x][y].charAt(0)=='S') {//移进
						stack[++j]=line[i++];
						value[++iv]=stack[j];
						Print(j);
						status[++k]=a;
						result[step++]=new String("移进"+ACTION[x][y]);
						System.out.println(result[step-1]);
					}//移进
					else if(ACTION[x][y].charAt(0)=='R') {//规约
						int len=Reduce(j,a,k);
						j=j-len+1;
						k=k-len+1;
						result[step++]=new String("规约"+ACTION[x][y]);
						System.out.println(result[step-1]);
					}//规约
					x=status[k];
					y=ry(i);
				}//else
			}//while
		
		return 1;
	}
	public static void main(String args[]) {
		SLR1 obj=new SLR1();
		if(obj.SLR1Analyse()==1)System.out.println("hahasuccess!");
		else System.out.println("failed!");
	}
	
}
