package simplechat;

import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class ChatClient
{
    public static void main(String[] args) throws IOException
    {
        Scanner inputScanner = new Scanner(System.in);
        System.out.print("Enter server port: ");
        Socket client = new Socket("localhost", Integer.parseInt(inputScanner.nextLine()));
        System.out.println("Sending to port: " + client.getPort());

        ChatLineWriter outputWriter = new ChatLineWriter(System.out);
        outputWriter.start();

        ChatLineWriter clientWriter = new ChatLineWriter(client.getOutputStream());
        clientWriter.start();

        ChatLineReader clientReader = new ChatLineReader(client.getInputStream());
        clientReader.addListener(m -> outputWriter.writeLine("Friend: " + m));
        clientReader.start();

        ChatLineReader inputReader = new ChatLineReader(System.in);
        inputReader.addListener(m -> {
            outputWriter.writeLine("Me: " + m);
            clientWriter.writeLine(m);
        });
        inputReader.start();
    }
}
