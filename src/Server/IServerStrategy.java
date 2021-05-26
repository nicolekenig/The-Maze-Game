package Server;

import java.io.InputStream;
import java.io.OutputStream;

public interface IServerStrategy {

     void handleClient(InputStream inputStream, OutputStream outPutStream);
}
