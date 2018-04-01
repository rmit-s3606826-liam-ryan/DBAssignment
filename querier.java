import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;

/**
 *  logic for the queries on the heap file
 **/
public class querier
{
    private final int PAGE_SIZE;
    private final String QUERY;
    
    public querier(int PAGE_SIZE, String QUERY)
    {
        this.PAGE_SIZE = PAGE_SIZE;
        this.QUERY = QUERY;
        
        long start_time = System.nanoTime();
        run();
        long end_time = System.nanoTime();
        long duration = (end_time - start_time);
        System.out.println("Duration: " + duration / 1000000000);
    }
    
    private void run()
    {
        try (FileInputStream pageReader = new FileInputStream("heap." + PAGE_SIZE))
        {
            int pagenum = 1;
            // as long as there is data from the input stream
            while (pageReader.available() != 0)
            {
                byte[] fullPage = getPage(pageReader);
                byte[] trimmedPage = trim(fullPage);
                deserialise(trimmedPage, pagenum);
                pagenum++;
            }
        }
        catch (FileNotFoundException e)
        {
            System.err.println("File error: " + e.getMessage());
        }
        catch (IOException e)
        {
            System.err.println("Stream error: " + e.getMessage());
        }
    }
    
    /**
     * removes the trailing zero byte array from the page
     **/
    private byte[] trim(byte[] fullPage)
    {
        int index = fullPage.length - 1;
        while (index >= 0 && fullPage[index] == 0)
        {
            --index;
        }
        return Arrays.copyOf(fullPage, index + 1);
    }

    /**
     * gets a byte array from the input stream in the required size
     **/
    private byte[] getPage(FileInputStream pageReader) throws IOException
    {
        byte[] page = new byte[PAGE_SIZE];
        pageReader.read(page);
        return page;
    }

    /**
     * turn the byte array back into an ArrayList of Businesses
     **/
    private void deserialise(byte[] page, int pn) throws IOException
    {
        ObjectInputStream ois = null;
        ByteArrayInputStream bais = null;
        try 
        {
            bais = new ByteArrayInputStream(page);
            ois = new ObjectInputStream(bais);
            @SuppressWarnings("unchecked")
            ArrayList<Business> deserializedPage = (ArrayList<Business>) ois.readObject();
            for (Business record : deserializedPage)
            {
                if ((record.getBusinessName().toLowerCase().indexOf(QUERY.toLowerCase())) != -1)
                {
                    System.out.println(record.toString() + "|" + pn);
                }
            }
            ois.reset();
        } 
        // this loop is going to throw an exception everytime it is run, because
        // that is how the ObjectInputStream tells us its done
        catch (Exception e) 
        {
        }
        finally
        {
            if (ois != null) ois.close();
            bais.close();
        }
    }
}
