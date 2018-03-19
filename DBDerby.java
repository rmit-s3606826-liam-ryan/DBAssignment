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

            Statement createTable = connection.createStatement();
            createTable.executeUpdate("create table Businesses("
                               + "BN_NAME varchar(30), "
                               + "BN_STATUS varchar(30), "
                               + "BN_REG_DT varchar(30), "
                               + "BN_CANCEL_DT varchar(30), "
                               + "BN_RENEW_DT varchar(30), "
                               + "BN_STATE_NUM varchar(30), "
                               + "BN_STATE_OF_REG varchar(30), "
                               + "BN_ABN varchar(30), "
                               + "primary key(BN_ABN))");
            System.out.println("table created");

            Statement insert = connection.createStatement();

            insert.executeUpdate("insert into Businesses ("
                               + "BN_NAME, "
                               + "BN_STATUS, "
                               + "BN_REG_DT, "
                               + "BN_CANCEL_DT, "
                               + "BN_RENEW_DT, "
                               + "BN_STATE_NUM, "
                               + "BN_STATE_OF_REG, "
                               + "BN_ABN) "
                               + "values ("
                               + "'Warby Wares', "
                               + "'Deregistered', "
                               + "'31/05/2015', "
                               + "'12/10/2017', "
                               + "'31/05/2017', "
                               + "'', "
                               + "'', "
                               + "'48697696446')");
            System.out.println("insert complete");
            
            Statement get_row = connection.createStatement();
            ResultSet res = get_row.executeQuery("select * from Businesses");

            while (res.next())
            {
                String name = res.getString("BN_NAME");
                String abn = res.getString("BN_ABN");
                System.out.println("Business name: " + name + "\nABN: " + abn);
            }
        }
        catch (SQLException e)
        {
            System.err.println("Whoops: " + e.getMessage());
        }
    }
}
