import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;

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
            int pagenum = 1;
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
            e.printStackTrace();
        }
    }
    
    private byte[] trim(byte[] fullPage)
    {
        int index = fullPage.length - 1;
        while (index >= 0 && fullPage[index] == 0)
        {
            --index;
        }
        return Arrays.copyOf(fullPage, index + 1);
    }

    private byte[] getPage(FileInputStream pageReader) throws IOException
    {
        byte[] page = new byte[PAGE_SIZE];
        pageReader.read(page);
        return page;
    }

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
        catch (Exception e) 
        {
//            e.printStackTrace();
        }
        finally
        {
            if (ois != null) ois.close();
            bais.close();
        }
    }
}
