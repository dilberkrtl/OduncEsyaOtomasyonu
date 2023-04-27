package controllers;

import basics.MessageBox;
import basics.SQL;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class AddStuffController {

    @FXML
    private TextField name;

    @FXML
    private TextField type;

    void add(String type, String name)
    {
        try
        {
            Connection connection = SQL.getConnection();

            //stuff tablosuna eşya ekliyoruz
            String query = "Insert into stuff(type, name, status) values(?, ?, ?)";
            PreparedStatement ps;

            ps = connection.prepareStatement(query);
            ps.setString(1, type);
            ps.setString(2, name);
            ps.setString(3, "Ödünç Alınabilir");//Her eklediğimiz eşyanın default durumu Ödünç Alınabilir

            ps.execute();

            connection.close();

            MessageBox.showMessage("Ödünç Eşya Bilgi Sistemi","Eşya ekleme başarıyla gerçekleşti.");
        }
        catch (Exception e)
        {
            MessageBox.showMessage("Ödünç Eşya Bilgi Sistemi","Kayıt oluşturulamadı. Kayıt esnasında bir şeyler ters gitti.", "error");
        }
    }

    @FXML
    void addStuff(ActionEvent event)
    {
        if(name.getText().isEmpty() || type.getText().isEmpty()) //TextField kontrolü
        {
            MessageBox.showMessage("Ödüç Eşya Bilgi Sistemi","Lütfen tüm alanları doldurunuz!", "error");
        }
        else
        {
            add(type.getText(), name.getText());

            name.setText("");
            type.setText("");
        }
    }

}
