package basics;

import java.sql.Connection;
import java.sql.DriverManager;

public class SQL
{
    private static String database="oduncesya";
    private static String username = "root";
    private static String password = "";

    //Bu metot aracılığıyla SQL bağlantısı geriye döndürüyoruz. Yukarıdaki bilgilere göre bu bağlantıyı alıyoruz.
    public static Connection getConnection()
    {
        Connection connection = null;
        String url = "jdbc:mysql://localhost/"+database;

        try
        {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(url, username, password);
        }
        catch (Exception ex)
        {
            connection = null;
            ex.printStackTrace();
        }

        return connection;
    }
}
