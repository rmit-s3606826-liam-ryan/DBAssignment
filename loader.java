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
        System.out.println("Load time: " + duration / 1000000);
    }
    
    private void run()
    {
        try (FileOutputStream fos =   new FileOutputStream("heap." + PAGE_SIZE);
             BufferedReader    br =   new BufferedReader(
                                      new FileReader(DATA_FILE)))
        {
            String line = br.readLine();
            ArrayList<Business> page = new ArrayList<Business>();
            int pageSize = 0;
            while ((line = br.readLine()) != null)
            {
                Business record = buildRecord(line);
                page.add(record);
                pageSize = getSize(page);
                if (pageSize >= (.8 * PAGE_SIZE))
                {
                    if (pageSize > PAGE_SIZE)
                    {
                        page.remove(record);
                        writePage(fos, page, pageSize);
                        page.clear();
                        page.add(record);
                        continue;
                    }
                    writePage(fos, page, pageSize);
                    page.clear();
//                        break;
                }
            }
            writePage(fos, page, pageSize);
        }
        catch (FileNotFoundException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        catch (IOException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    
    private void writePage(FileOutputStream fos, ArrayList<Business> page, int pageSize) throws IOException
    {
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        byte[] filler = new byte[PAGE_SIZE - pageSize];
        oos.writeObject(page);
        fos.write(filler);
    }

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
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        oos.close();
        baos.close();
        return baos.toByteArray().length;
    }

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
