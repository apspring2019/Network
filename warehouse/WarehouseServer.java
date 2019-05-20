package warehouse;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class WarehouseServer
{
    public static void main(String[] args) throws IOException
    {
        ServerSocket server = new ServerSocket(0);
        System.out.println("Listening on port: " + server.getLocalPort());
        new Thread(() ->
        {
            try
            {
                while (true)
                {
                    Socket client = server.accept();
                    System.out.println("New client connected.");
                    new RequestHandler(client).start();
                }
            }
            catch (Exception ex)
            {
                ex.printStackTrace();
            }
        }).start();
    }
}
