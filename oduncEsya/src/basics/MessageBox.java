package basics;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class MessageBox
{
    public static void showMessage(String title, String content)
    {
        showMessage(title, content, "info");
    }

    public static void showMessage(String title, String content, String type)
    {
    	//Alert nesnesini showMessage ile oluşturup çağırmak. Böylelikle C#'daki gibi kolay bir kullanım
    	//elde edebiliriz.
    	
        Alert alert = null;

        if (type == "warning")
        {
            alert = new Alert(AlertType.WARNING);
        }
        else if(type == "error")
        {
            alert = new Alert(AlertType.ERROR);
        }
        else
        {
            alert = new Alert(AlertType.INFORMATION);
        }

        alert.setTitle(title);
        alert.setContentText(content);
        alert.setHeaderText(null);

        alert.showAndWait();
    }
}
