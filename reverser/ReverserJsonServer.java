package reverser;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonStreamParser;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class ReverserJsonServer
{
    public static void main(String[] args) throws IOException
    {
        ServerSocket server = new ServerSocket(0);
        System.out.println(server.getInetAddress());
        System.out.println(server.getLocalPort());

        Socket client = server.accept();
        System.out.println("Sending to port: " + client.getPort());

        try (OutputStreamWriter writer = new OutputStreamWriter(client.getOutputStream());
             InputStreamReader reader = new InputStreamReader(client.getInputStream()))
        {
            Gson gson = new Gson();
            JsonStreamParser parser = new JsonStreamParser(reader);
            while (parser.hasNext())
            {
                JsonObject obj = parser.next().getAsJsonObject();
                JsonObject response = new JsonObject();
                response.addProperty("reversed", new StringBuilder(obj.get("text").getAsString()).reverse().toString());
                gson.toJson(response, writer);
                writer.flush();
            }
        }
    }
}
