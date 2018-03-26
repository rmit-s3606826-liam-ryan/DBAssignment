import java.io.*;

public class HeapFile
{
    static final int PAGESIZE = 4096;
    public static void main(String[] args) throws IOException
    {
        Business bus = new Business("this", "yes", "then", "soon", "now", "that", "there", 1234);
        Business bus2 = new Business("a", "b", "c", "d", "e", "f", "g", 4321);
        
        int bytesInPage = 0;
        int pageNum = 0;
        try (FileOutputStream pageWriter = new FileOutputStream("heap." + PAGESIZE))
        {
            while (pageNum < 3)
            {
                byte[] record = pageNum == 0 ? serialise(bus) : serialise(bus2);
                //System.out.println(record.length + "|" + bytesInPage + "|" + (int)(record.length + bytesInPage));
                if (bytesInPage + record.length >= PAGESIZE)
                {
                    System.out.println("here");
                    byte[] zero = new byte[PAGESIZE - bytesInPage];
                    pageWriter.write(zero);
                    bytesInPage = 0;
                    pageNum++;
                }
                else
                {
                    bytesInPage += record.length;
                    pageWriter.write(record);
                }
                
            }
        }
    }
    
    public static byte[] serialise(Business bus) throws IOException
    {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        
        oos.writeObject(bus);
        return baos.toByteArray();
    }
}
