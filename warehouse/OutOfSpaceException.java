package warehouse;

public class OutOfSpaceException extends Exception
{
    public OutOfSpaceException()
    {
        super("Not enough space.");
    }
}
