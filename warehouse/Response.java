package warehouse;

import com.google.gson.JsonObject;

public class Response
{
    private int code;
    private JsonObject content = new JsonObject();

    public int getCode()
    {
        return code;
    }

    public JsonObject getContent()
    {
        return content;
    }

    public void setCode(int code)
    {
        this.code = code;
    }

    public void setContent(JsonObject content)
    {
        this.content = content;
    }

    public static class Codes
    {
        public static final int OK = 200;
        public static final int ERROR = 400;
    }
}

