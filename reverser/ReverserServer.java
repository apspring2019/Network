package reverser;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class ReverserServer
{
    public static void main(String[] args) throws IOException
    {
        ServerSocket server = new ServerSocket(0);
        System.out.println(server.getInetAddress());
        System.out.println(server.getLocalPort());

        while (!server.isClosed())
        {
            Socket client = server.accept();

            new Thread(() ->
            {
                System.out.println("Sending to port: " + client.getPort());

                try (PrintStream printer = new PrintStream(client.getOutputStream());
                     Scanner reader = new Scanner(client.getInputStream()))
                {
                    while (reader.hasNextLine())
                        printer.println(new StringBuilder(reader.nextLine()).reverse());
                }
                catch (Exception ex) { ex.printStackTrace(); }
            }).start();
        }
    }
}
