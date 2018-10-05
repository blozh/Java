package ATest;

import java.util.Scanner;

public class Matrix {
    private double[][] matrix;

    //矩阵
    Matrix(int m,int n){
        matrix=new double[m][];
        for(int i=0;i<m;i++){
            matrix[i]=new double[n];
        }
    }
    //方阵
    Matrix(int n){
        this(n,n);
    }

    //设置矩阵的值
    public void set(){
        Scanner sc=new Scanner(System.in);
        System.out.println("请依次输入各元素的值：");
        for(int i=0;i<matrix.length;i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                matrix[i][j]=sc.nextDouble();
            }
        }
    }

    //拷贝构造函数
    Matrix(Matrix m){
        int mm=m.matrix.length;
        int n=m.matrix[0].length;
        matrix=new double[mm][];
        for(int i=0;i<mm;i++){
            matrix[i]=new double[n];
        }
        for(int i=0;i<matrix.length;i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                matrix[i][j]=m.matrix[i][j];
            }
        }
    }

    public void print(){
        for(int i=0;i<matrix.length;i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                System.out.print(matrix[i][j]+"\t");
            }
            System.out.println();
        }
        System.out.println();
    }

    public Matrix transpose(){
        Matrix temp=new Matrix(this.matrix[0].length,this.matrix.length);
        for(int i=0;i<temp.matrix.length;i++) {
            for (int j = 0; j < temp.matrix[0].length; j++) {
                temp.matrix[i][j]=this.matrix[j][i];
            }
        }
        return temp;
    }

    public void add(Matrix b){
        if(this.matrix.length==b.matrix.length&&this.matrix[0].length==b.matrix[0].length){
            for(int i=0;i<this.matrix.length;i++) {
                for (int j = 0; j < this.matrix[0].length; j++) {
                    this.matrix[i][j]+=b.matrix[i][j];
                }
            }
        }else {
            System.out.println("所选矩阵无法相加");
        }
    }

    public Matrix plus(Matrix b){
        Matrix temp=new Matrix(this);
        if(this.matrix.length==b.matrix.length&&this.matrix[0].length==b.matrix[0].length){
            for(int i=0;i<this.matrix.length;i++) {
                for (int j = 0; j < this.matrix[0].length; j++) {
                    temp.matrix[i][j]+=b.matrix[i][j];
                }
            }
        }else {
            System.out.println("所选矩阵无法相加");
        }
        return temp;
    }
}
