package Server;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;


public class Server  implements Serializable {
    private int port;
    private int listeningInterval;
    private IServerStrategy serverStrategy;
    private volatile boolean stop;
    private ThreadPoolExecutor threadPoolExecutor;


    public Server(int port, int listeningInterva, IServerStrategy serverStrategy) {
        new Configurations();
        this.port = port;
        this.listeningInterval = listeningInterva;
        this.serverStrategy = serverStrategy;
        this.stop = false;

        int threadPoolSize = Integer.parseInt(Configurations.getProperty("ThreadPoolSize"));
        this.threadPoolExecutor = (ThreadPoolExecutor) Executors.newFixedThreadPool(threadPoolSize);
//        int threadPoolSize = Runtime.getRuntime().availableProcessors() *2;
//        this.threadPoolExecutor = (ThreadPoolExecutor)Executors.newCachedThreadPool();
        this.threadPoolExecutor.setCorePoolSize(threadPoolSize);
    }

    public void start() {
        new Thread(() -> {
            this.runServer();
        }).start();
    }

    private void runServer(){
        try {
            ServerSocket serverSock = new ServerSocket(this.port) ;
            System.out.println("Server started");
            serverSock.setSoTimeout(1000);
            while (!this.stop)
            {
                try{
                    Socket clientSocket = serverSock.accept();
                    this.threadPoolExecutor.execute(() -> {
                        this.clientHandle(clientSocket);
                    });
                } catch (SocketTimeoutException var3) {
                }
            }
            serverSock.close();
            this.threadPoolExecutor.shutdown();
        } catch (IOException e) {
        }
    }

    private void clientHandle(Socket clientSocket) {
        try {
            InputStream inFromClient = clientSocket.getInputStream();
            OutputStream outToClient = clientSocket.getOutputStream();
            this.serverStrategy.handleClient(inFromClient, outToClient);

            inFromClient.close();
            outToClient.close();
            clientSocket.close();
        } catch (IOException e) {
        }
    }

    public void stop() {
        this.stop = true;
//        this.threadPoolExecutor.shutdown();
//        this.executor.shutdown();
    }

}