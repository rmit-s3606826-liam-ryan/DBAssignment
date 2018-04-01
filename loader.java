import java.io.*;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class loader
{
    private final int PAGE_SIZE;
    private final String DATA_FILE;
    
    public loader(int page_size, String data_file)
    {
        PAGE_SIZE = page_size;
        DATA_FILE = data_file;
        long start_time = System.nanoTime();
        run();
        long end_time = System.nanoTime();
        long duration = (end_time - start_time);
        System.out.println("Load time: " + duration / 1000000000);
    }
    
    private void run()
    {
        try (FileOutputStream fos =   new FileOutputStream("heap." + PAGE_SIZE);
             BufferedReader    br =   new BufferedReader(
                                      new FileReader(DATA_FILE)))
        {
            // get header line and discard
            String line = br.readLine();
            ArrayList<Business> page = new ArrayList<Business>();
            int pageSize = 0;
            while ((line = br.readLine()) != null)
            {
                Business record = buildRecord(line);
                page.add(record);
                pageSize = getSize(page);
                // if the page is bigger than the PAGE_SIZE 
                // this ensures that the page will be at maximum capacity
                // but will still fit in the specified page size
                if (pageSize > PAGE_SIZE)
                {
                    page.remove(record);
                    writePage(fos, page, pageSize);
                    page.clear();
                    page.add(record);
                }
            }
            // makes sure the last page is written
            writePage(fos, page, pageSize);
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
    
    /*
     * writes the pageand filler to file
     */
    private void writePage(FileOutputStream fos, ArrayList<Business> page, int pageSize) throws IOException
    {
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        byte[] filler = new byte[PAGE_SIZE - pageSize];
        oos.writeObject(page);
        fos.write(filler);
    }

    /**
     *  Get the size of the page because java is a terrible language
     *  introduces some redundany and overhead, but I need to know how 
     *  big the page is, including the ObjectOutputStream header
     *  and I would need to convert it to a byte array to get the size a
     *  anyway because.... java is a terrible language
     **/
    private int getSize(ArrayList<Business> page) throws IOException
    {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = null;
        try
        {
            oos = new ObjectOutputStream(baos);
            oos.writeObject(page);
        }
        catch (IOException e)
        {
            System.err.println("Stream error: " + e.getMessage());
        }
        oos.close();
        baos.close();
        return baos.toByteArray().length;
    }

    /**
     *  takes the string from file, tokenizes it and builds a record object
     **/
    private Business buildRecord(String line) throws IOException
    {
        String[] record = line.split("\t", -1);
        Long abn = null; 
        if (!record[8].equals(""))
        {
            abn = Long.parseLong(record[8]);
        }
        Business bus = new Business(record[1], record[2], record[3], record[4], record[5], record[6], record[7], abn);
        
        return bus;
    }
}
