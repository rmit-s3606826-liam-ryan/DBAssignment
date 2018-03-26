import java.sql.*;

public class DBDerby
{
    public static void main(String args[])
    {
        try
        {
            System.out.println("Creating/Connecting to database...");
            String db_url = "jdbc:derby:DerbyDb;create=true";
            Connection connection = DriverManager.getConnection(db_url);

            Statement stmt = connection.createStatement();
                stmt.executeUpdate("create table Businesses("
                                   + "BN_NAME varchar(200), "
                                   + "BN_STATUS varchar(100), "
                                   + "BN_REG_DT varchar(100), "
                                   + "BN_CANCEL_DT varchar(100), "
                                   + "BN_RENEW_DT varchar(100), "
                                   + "BN_STATE_NUM varchar(100), "
                                   + "BN_STATE_OF_REG varchar(100), "
                                   + "BN_ABN varchar(100))");
                System.out.println("table created");
            stmt.execute("CALL SYSCS_UTIL.SYSCS_IMPORT_TABLE(null, 'BUSINESSES', 'a.csv', '	', null, null, 0)");

            System.out.println("insert complete");
            
            ResultSet res = stmt.executeQuery("select * from Businesses");

            while (res.next())
            {
                String sor = res.getString("BN_STATE_OF_REG");
                String snum = res.getString("BN_STATE_NUM");
                if (sor != null || snum != null)
                    System.out.println("sor: " + sor + " | snum: " + snum);
            }
        }
        catch (SQLException e)
        {
            System.err.println("Whoops: " + e.getMessage());
        }
    }
}
