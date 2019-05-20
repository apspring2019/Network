package reverser;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

public class ReverserClient
{
    public static void main(String[] args) throws IOException
    {
        Scanner inputScanner = new Scanner(System.in);
        System.out.print("Enter server port: ");
        Socket socket = new Socket("localhost", Integer.parseInt(inputScanner.nextLine()));
        System.out.println("Listening on port: " + socket.getLocalPort());
        try (PrintStream printer = new PrintStream(socket.getOutputStream());
             Scanner socketScanner = new Scanner(socket.getInputStream()))
        {
            while (inputScanner.hasNextLine())
            {
                printer.println(inputScanner.nextLine());
                System.out.println("Server said: " + socketScanner.nextLine());
            }
        }
    }
}
