package warehouse;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Warehouse
{
    private static Warehouse ourInstance = new Warehouse();

    public static Warehouse getInstance()
    {
        return ourInstance;
    }

    private static final int MAX_CAPACITY = 10;

    private Map<String, Integer> counts = Collections.synchronizedMap(new HashMap<>());

    private Warehouse()
    {
    }

    public int addToWarehouse(String name, int count) throws OutOfSpaceException
    {
        int current = counts.getOrDefault(name, 0);
        if (current + count > MAX_CAPACITY)
            throw new OutOfSpaceException();
        counts.put(name, current + count);
        return current + count;
    }

    public void removeAll(String name)
    {
        if(counts.remove(name) == null)
            throw new IllegalArgumentException("name not found");
    }
}
