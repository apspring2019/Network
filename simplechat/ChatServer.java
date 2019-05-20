package simplechat;

import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;

public class ChatServer
{
    public static void main(String[] args) throws IOException
    {
        ServerSocket server = new ServerSocket(0);
        System.out.println(server.getLocalPort());

        System.out.println("Waiting for the first client...");
        final Socket first = server.accept();
        System.out.println("Waiting for the second client...");
        final Socket second = server.accept();

        ChatLineWriter outputWriter = new ChatLineWriter(System.out);
        outputWriter.start();

        ChatLineReader firstReader = new ChatLineReader(first.getInputStream());
        ChatLineReader secondReader = new ChatLineReader(second.getInputStream());
        firstReader.addListener(new MessageReplicator("first", second, outputWriter));
        secondReader.addListener(new MessageReplicator("second", first, outputWriter));
        firstReader.start();
        secondReader.start();
    }

    private static class MessageReplicator implements OnMessageReceivedListener
    {
        private String name;
        private ChatLineWriter destWriter;
        private ChatLineWriter output;

        public MessageReplicator(String name, Socket destSocket, ChatLineWriter output) throws IOException
        {
            this.name = name;
            this.destWriter = new ChatLineWriter(destSocket.getOutputStream());
            destWriter.start();
            this.output = output;
        }

        @Override
        public void onMessageReceived(String message)
        {
            destWriter.writeLine(message);
            output.writeLine(String.format("%s: %s", name, message));
        }
    }
}
