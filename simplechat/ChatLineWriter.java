package simplechat;

import java.io.OutputStream;
import java.io.PrintStream;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class ChatLineWriter extends Thread
{
    private OutputStream output;

    private BlockingQueue<String> pipeline = new LinkedBlockingQueue<>();

    public ChatLineWriter(OutputStream output)
    {
        this.output = output;
    }

    @Override
    public void run()
    {
        try
        {
            PrintStream printer = new PrintStream(output);
            while (!interrupted())
                printer.println(pipeline.take());
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

    public void writeLine(String line)
    {
        pipeline.add(line);
    }
}
