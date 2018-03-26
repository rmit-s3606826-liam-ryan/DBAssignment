import java.io.*;

public class loader
{
    private final int PAGE_SIZE;
    private final String DATA_FILE;
    
    public loader(int page_size, String data_file)
    {
        PAGE_SIZE = page_size;
        DATA_FILE = data_file;
        run();
    }
    
    private void run()
    {
        int bytesInPage = 0;
        try (BufferedReader br = new BufferedReader(new FileReader(DATA_FILE));
             FileOutputStream pageWriter = new FileOutputStream("heap." + PAGE_SIZE))
        {
            // grab and discard first row
            br.readLine();
            String line = "";
            int page_num = 1;
            while ((line = br.readLine()) != null)
            {
                Business bus = buildRecord(line);
//                System.out.println(bus.toString());
                byte[] record = serialise(bus);
//                System.out.println(record.length + "|" + bytesInPage + "|" + (int)(record.length + bytesInPage));
                if (bytesInPage + record.length >= PAGE_SIZE)
                {
                    byte[] zero = new byte[PAGE_SIZE - bytesInPage];
                    pageWriter.write(zero);
                    bytesInPage = 0;
                    page_num++;
                    if (page_num == 10) { break; }
                }
                bytesInPage += record.length;
                pageWriter.write(record);
            }
        }
        catch (FileNotFoundException e)
        {
            System.err.println("Could not open file: " + e.getMessage());
        }
        catch (IOException e)
        {
            System.err.println("Stream error: " + e.getMessage());
        }
    }
    
    private Business buildRecord(String line)
    {
        String[] fields = line.split("\t", -1);
        Long abn_temp = null;
        if (!fields[8].equals(""))
        {
            abn_temp = Long.parseLong(fields[8]);
        }
        return new Business(fields[1], fields[2], fields[3], fields[4], fields[5], fields[6], fields[7], abn_temp);
    }

    private  byte[] serialise(Business bus) throws IOException
    {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        
        oos.writeObject(bus);
        return baos.toByteArray();
    }
}
