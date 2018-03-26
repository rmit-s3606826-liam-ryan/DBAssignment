
public class dbquery
{
    public static void main(String[] args)
    {
        int page_size = 0;
        String query = "";
        try
        {
            if (args[0].equals("-p"))
            {
                page_size = Integer.parseInt(args[1]);
                query = args[2];
            }
            else if (args[1].equals("-p"))
            {
                page_size = Integer.parseInt(args[2]);
                query = args[0];
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
        
        new querier(page_size, query);
    }
}
