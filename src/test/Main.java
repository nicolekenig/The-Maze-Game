package test;
import IO.MyCompressorOutputStream;
import IO.MyDecompressorInputStream;
import algorithms.mazeGenerators.*;
import algorithms.search.SearchableMaze;

import java.io.*;
import java.nio.file.DirectoryStream;
import java.nio.file.FileSystem;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;


public class Main {

    public static void main(String[] args) throws IOException {


//        IMazeGenerator mg = new MyMazeGenerator();
//        Maze maze = mg.generate(300, 300);
//        SearchableMaze searchableMaze = new SearchableMaze(maze);
//        byte[] b = maze.toByteArray();
        byte[] b = new byte[4];
        int[] copyB = new int[b.length];
        int  i = 0;
        int num =500;
        b[0] =  Integer.valueOf(num / 256).byteValue();
        System.out.println("b[0] = "+b[0]);

        b[1] =  Integer.valueOf(num % 256).byteValue();
        System.out.println("b[1] = "+ b[1]);

        b[2] =  Integer.valueOf(num / 256).byteValue();
        System.out.println("b[2] = "+b[2]);

        b[3] =  Integer.valueOf(num % 256).byteValue();
        System.out.println("b[3] = "+ b[3]);

        for(int index=0; index<b.length ; index++){
            if(b[index ]< 0)
                copyB[index] = b[index] + 256;
            else
                copyB[index] = b[index];
            System.out.println("copyB["+index+"] = "+copyB[index]);
        }
        int copyNum = copyB[0] * 256 + copyB[1];
        System.out.println("copyNum = "+copyNum);


//        System.out.println("b["+i+"]= "+255);
//        System.out.println("b["+i+"]= "+num);
//        b[i] = (byte)num;
//        b[i+1] = (byte)-24;

//        for( i=0; i< b.length;i++)
//        {
//            if(b[i] != -24)
//                System.out.print(b[i]& 0xFF);
//            else
//                System.out.print("|");
//        }
//        System.out.println();
//        for( i=0; i< b.length;i++)
//        {
//            if(b[i] != -24)
//                System.out.print(b[i]);
//            else
//                System.out.print("|");
//        }



//        System.out.println();
//        byte[] a = {(byte)255};
//        System.out.println(a[0]& 0xff );
//        int x =-24;
//        byte y = (byte)x;
//        System.out.println("(byte)x = "+y); // -22
//        int i2 = y & 0xFF;
//        System.out.println("y & 0xff = "+i2); // 234
//        ArrayList<Integer> a = new ArrayList<>();
//        a.add(8);
//        a.add(0,10);
//        System.out.println(a);

//        HashMap<Integer,Integer> d = new HashMap<>();
//        d.put(0,5);
//        String s ="";
//        s += 0;
//        System.out.println(d.get(0));
//        if(d.containsKey(Integer.parseInt(s)))
//            System.out.println("works");

//        s= "abc,d";
//        String tmp ;
//        tmp= s.substring(0,s.length()-s.lastIndexOf(",")+1);
//        System.out.println(tmp);
//        tmp = s.substring(s.length()-s.lastIndexOf(",")+1);
//        System.out.println(tmp);

//        String path = "C:\\Users\\tomdu\\Downloads\\ATP - Project - PartA (1)\\ATP - Project - PartA\\savingMazes";
//        String tempDirectoryPath = System.getProperty("java.io.tmpdir");
//        System.out.println(tempDirectoryPath);
//        File dir = new File(tempDirectoryPath);
//        if(!dir.exists())
//            dir.mkdir();
//
//        byte [] bytes = {0,0,1};
//        byte [] bytes2 = {0,6,1};
//        byte [] bytes3 = {0,5,1};
//        File m1 = new File(dir,"m1.maze");
////        if(!m1.exists())
////            m1.createNewFile();
//
//        File m2 = new File(dir ,"m2.maze");
////        if(!m2.exists())
////            m2.createNewFile();
//
//        File m3 = new File(dir, "m3.maze");
////        if(!m3.exists())
////            m3.createNewFile();
//
//        try{
//            OutputStream d = new MyCompressorOutputStream(new FileOutputStream(m1.getName()));
//            d.write(bytes);
//            d.flush();
//            d.close();
//
//            d = new MyCompressorOutputStream(new FileOutputStream(m2.getName()));
//            d.write(bytes2);
//            d.flush();
//            d.close();
//
//
//            d = new MyCompressorOutputStream(new FileOutputStream(m3.getName()));
//            d.write(bytes3);
//            d.flush();
//            d.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        byte[] sevedMaze = new byte[bytes.length];
////        DirectoryStream<Path> stream = Files.newDirectoryStream(dir.toPath());
//        File[] stream = dir.listFiles();
//        if(stream != null){
//        for (File file : stream){
//            int cut = file.getName().indexOf(".");
////            System.out.println(file.getName().substring(cut+1));
//            if(file.getName().substring(cut+1).equals("maze")){
//            InputStream in = new MyDecompressorInputStream(new FileInputStream(file.getName()));
//            in.read(sevedMaze);
//            in.close();
//            boolean areMazedEqual = Arrays.equals(sevedMaze,bytes);
//            System.out.println(areMazedEqual);}
//        }
//        }
//        int counter = 8;
//        String binary = "a";
//
//        byte a = Integer.valueOf(Integer.parseInt(binary, 2)).byteValue();
//        binary = "";
//        counter = 0;


        int a = 00000010 & 255;




    }
}
