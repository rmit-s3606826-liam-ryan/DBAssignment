import java.io.*;

public class querier
{
    private final int PAGE_SIZE;
    private final String QUERY;
    
    public querier(int PAGE_SIZE, String QUERY)
    {
        this.PAGE_SIZE = PAGE_SIZE;
        this.QUERY = QUERY;
        
        run();
    }
    
    private void run()
    {
        try (FileInputStream pageReader = new FileInputStream("heap." + PAGE_SIZE))
        {
            while (pageReader.available() >= PAGE_SIZE)
            {
                byte[] page = getPage(pageReader);
                deserialise(page);
            }
        }
        catch (FileNotFoundException e)
        {
            System.err.println("File error: " + e.getMessage());
        }
        catch (IOException e)
        {
            System.err.println("Stream error: " + e.getMessage());
            e.printStackTrace();
        }
        catch (ClassNotFoundException e)
        {
            System.err.println("Object error: " + e.getMessage());
        }
    }
    
    private byte[] getPage(FileInputStream pageReader) throws IOException
    {
        byte[] page = new byte[PAGE_SIZE];
        pageReader.read(page, 0, PAGE_SIZE);
        return page;
    }

    private void deserialise(byte[] page) throws IOException, ClassNotFoundException
    {
        ByteArrayInputStream bais = new ByteArrayInputStream(page);
        ObjectInputStream ois = new ObjectInputStream(bais);
        Business bus;
        while (true)
        {
            bus = (Business) ois.readObject();
            if ((bus.getBusinessName().indexOf(QUERY.toLowerCase())) != -1)
            {
                System.out.println(bus.toString());
            }
        }
    }

}
