package warehouse;

import com.google.gson.JsonObject;

public class Request
{
    enum RequestType
    {
        ADD,
        DELETE
    }

    private RequestType type;
    private JsonObject content = new JsonObject();

    public RequestType getType()
    {
        return type;
    }

    public JsonObject getContent()
    {
        return content;
    }

    public void setType(RequestType type)
    {
        this.type = type;
    }

    public void setContent(JsonObject content)
    {
        this.content = content;
    }
}
