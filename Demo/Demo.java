package Demo;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.Reader;
import java.sql.*;


class ABC{
    public static void main(String argv[]) throws Exception {
        ABC m=new ABC();
        System.out.println(m.ff());
    }

    public int ff() throws Exception{
        try {
            FileInputStream dis=new FileInputStream("Hell23o.txt");
        } catch (FileNotFoundException fne) {
            System.out.print("No such file found, ");
            throw fne;
        } finally {
            System.out.print("Doing finally, ");
        }
        return 0;
    }
}
