package reverser;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonStreamParser;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

public class ReverserJsonClient
{
    public static void main(String[] args) throws IOException
    {
        Scanner inputScanner = new Scanner(System.in);
        System.out.print("Enter server port: ");
        Socket socket = new Socket("localhost", Integer.parseInt(inputScanner.nextLine()));
        System.out.println("Listening on port: " + socket.getLocalPort());
        try (OutputStreamWriter writer = new OutputStreamWriter(socket.getOutputStream());
             InputStreamReader reader = new InputStreamReader(socket.getInputStream()))
        {
            Gson gson = new Gson();
            JsonStreamParser parser = new JsonStreamParser(reader);
            while (inputScanner.hasNextLine())
            {
                JsonObject obj = new JsonObject();
                obj.addProperty("text", inputScanner.nextLine());
                gson.toJson(obj, writer);
                writer.flush();

                JsonElement response = parser.next();
                System.out.println("Server said: " + response.getAsJsonObject().get("reversed").getAsString());
            }
        }
    }
}
