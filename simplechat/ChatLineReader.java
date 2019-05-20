package simplechat;


import java.io.InputStream;
import java.util.LinkedList;
import java.util.Scanner;

public class ChatLineReader extends Thread
{
    private InputStream input;

    private LinkedList<OnMessageReceivedListener> listeners = new LinkedList<>();

    public ChatLineReader(InputStream input)
    {
        this.input = input;
    }

    @Override
    public void run()
    {
        Scanner scanner = new Scanner(input);
        while (scanner.hasNextLine())
            callOnMessageReceived(scanner.nextLine());
    }

    public void addListener(OnMessageReceivedListener listener)
    {
        listeners.add(listener);
    }

    private void callOnMessageReceived(String message)
    {
        listeners.forEach(l -> l.onMessageReceived(message));
    }
}
