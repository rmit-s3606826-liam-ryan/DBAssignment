
/**
 * Driver class for loading data into a heap file
 */
public class dbload
{
    public static void main(String[] args)
    {
        int page_size = 0;
        String data_file = "";
        try
        {
            // handling command line input
            if (args[0].equals("-p"))
            {
                page_size = Integer.parseInt(args[1]);
                data_file = args[2];
            }
            else if (args[1].equals("-p"))
            {
                page_size = Integer.parseInt(args[2]);
                data_file = args[0];
            }
            else
            {
                System.err.println("Check arguments...");
            }
        }
        catch (NumberFormatException e)
        {
            System.err.println("Page size should be a number: " + e.getMessage());
        }
        catch (ArrayIndexOutOfBoundsException e)
        {
            System.err.println("Too few arguments: " + e.getMessage());
        }
        
        // create new loader object using inputs from the command line
        new loader(page_size, data_file);
    }
}
