package warehouse;

import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.Scanner;

public class WarehouseClient
{
    public static void main(String[] args) throws IOException
    {
        Scanner inputScanner = new Scanner(System.in);
        System.out.println("Enter your name: ");
        String name = inputScanner.nextLine();
        System.out.print("Enter server port: ");
        int port = Integer.parseInt(inputScanner.nextLine());

        while (inputScanner.hasNextLine())
        {
            String line = inputScanner.nextLine();
            Request req = new Request();
            if (line.equalsIgnoreCase("del"))
            {
                req.setType(Request.RequestType.DELETE);
            }
            else
            {
                req.setType(Request.RequestType.ADD);
                req.getContent().addProperty("count", Integer.parseInt(line));
            }
            req.getContent().addProperty("name", name);

            Response res = sendRequestBlocking(port, req);
            handleResponse(res);
        }
    }

    private static Response sendRequestBlocking(int port, Request req) throws IOException
    {
        try (Socket socket = new Socket("localhost", port);
             OutputStreamWriter writer = new OutputStreamWriter(socket.getOutputStream());
             InputStreamReader reader = new InputStreamReader(socket.getInputStream()))
        {
            Gson gson = new Gson();
            gson.toJson(req, writer);
            writer.flush();
            return gson.fromJson(reader, Response.class);
        }
    }

    private static void handleResponse(Response res)
    {
        switch (res.getCode())
        {
            case Response.Codes.OK:
                System.out.println("Successful!");
                break;
            case Response.Codes.ERROR:
                System.out.println("Error: " + res.getContent().get("message").getAsString());
                break;
            default:
                System.out.println("Unknown response!");
        }
    }
}
