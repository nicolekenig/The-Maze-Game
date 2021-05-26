package Server;

import java.io.*;
import java.util.Properties;

public  class Configurations {

    private static Properties properties = new Properties() ;

    public Configurations(){
        OutputStream output = null;
        try {
            output = new FileOutputStream("resources/config.properties");
            // set the ProjectProperties value
            int poolSize = Runtime.getRuntime().availableProcessors() *2;
            properties.setProperty("ThreadPoolSize", String.valueOf(poolSize));
            properties.setProperty("MazeGenerator", "MyMazeGenerator");
            properties.setProperty("SearchingAlgorithm", "BestFirstSearch");
            // save ProjectProperties to project root folder
            properties.store(output, null);
        } catch(IOException io) {
            io.printStackTrace();
        }finally {
            if(output != null){
                try {
                    output.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    public static void setThreadPoolSize(int num){
        OutputStream output = null;
        try{
            properties.load(new FileInputStream("resources/config.properties"));
            output = new FileOutputStream("resources/config.properties");
            properties.setProperty("ThreadPoolsize",Integer.toString(num));
            properties.store(output, (String)null);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(output != null){
                try {
                    output.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void setSearchingAlgorithm(String searchingAlgorithm){
        OutputStream output = null;
        try{
            properties.load(new FileInputStream("resources/config.properties"));
            output = new FileOutputStream("resources/config.properties");
            properties.setProperty("SearchingAlgorithm",searchingAlgorithm);
            properties.store(output, (String)null);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(output != null){
                try {
                    output.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void setMazeGenerator(String mazeGenerator) {
        OutputStream output = null;
        try {
            properties.load(new FileInputStream("resources/config.properties"));
            output = new FileOutputStream("resources/config.properties");
            properties.setProperty("MazeGenerator", mazeGenerator);
            properties.store(output, (String)null);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }catch (IOException var3) {
            var3.printStackTrace();
        }finally {
            if(output != null){
                try {
                    output.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static String getProperty(String propertyKey){
        InputStream in = null;
        String propertyValue = "";

        try{
            in = new FileInputStream("resources/config.properties");
            properties.load(in);
            propertyValue = properties.getProperty(propertyKey);
        }catch (IOException e){
            e.printStackTrace();
        }
        finally {
            {
                if( in != null){
                    try{
                        in.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return propertyValue;
    }









}
