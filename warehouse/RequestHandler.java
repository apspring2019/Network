package warehouse;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonStreamParser;

import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class RequestHandler extends Thread
{
    private Socket client;

    public RequestHandler(Socket client)
    {
        this.client = client;
    }

    @Override
    public void run()
    {
        try (InputStreamReader reader = new InputStreamReader(client.getInputStream());
             OutputStreamWriter writer = new OutputStreamWriter(client.getOutputStream()))
        {
            Gson gson = new Gson();
            JsonStreamParser parser = new JsonStreamParser(reader);
            Request req = gson.fromJson(parser.next(), Request.class);
            System.err.println("Request received. Type = " + req.getType());
            Response res;
            try
            {
                switch (req.getType())
                {
                    case ADD:
                        res = requestAdd(req);
                        break;
                    case DELETE:
                        res = requestDelete(req);
                        break;
                    default:
                        throw new IllegalArgumentException("Invalid Request Type!");
                }
            }
            catch (Exception ex)
            {
                res = new Response();
                res.setCode(Response.Codes.ERROR);
                res.getContent().addProperty("message", ex.getMessage());
            }
            gson.toJson(res, writer);
            writer.flush();
        }
        catch (Exception ex) { ex.printStackTrace(); }
        finally
        {
            if (!client.isClosed())
                try { client.close(); }
                catch (Exception ignored) {}
        }
    }

    private Response requestAdd(Request req) throws OutOfSpaceException
    {
        Response res = new Response();
        int newCount = Warehouse.getInstance().addToWarehouse(
                req.getContent().get("name").getAsString(),
                req.getContent().get("count").getAsInt());
        res.setCode(Response.Codes.OK);
        res.getContent().addProperty("newCount", newCount);
        return res;
    }

    private Response requestDelete(Request req)
    {
        Response res = new Response();
        Warehouse.getInstance().removeAll(req.getContent().get("name").getAsString());
        res.setCode(Response.Codes.OK);
        return res;
    }
}
