package Client;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;

public class Client {

    private IClientStrategy clientStrategy;
    private InetAddress ip;
    private int port;

    public Client(InetAddress ip, int port, IClientStrategy clientStrategy) {
        this.ip = ip;
        this.port = port;
        this.clientStrategy = clientStrategy;
    }

    public void communicateWithServer()
    {
        try
        {
            Socket socket = new Socket(this.ip, this.port);
            System.out.println("Client is connected to server!");
            InputStream inFromServer = socket.getInputStream();
            OutputStream outToServer = socket.getOutputStream();
            this.clientStrategy.clientStrategy(inFromServer, outToServer);
            inFromServer.close();
            outToServer.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

}
