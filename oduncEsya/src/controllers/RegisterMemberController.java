package controllers;

import basics.MessageBox;
import basics.SQL;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class RegisterMemberController {

    @FXML
    private TextField mail;

    @FXML
    private TextField phone;

    @FXML
    private TextField name;

    void register(String name, String phone, String mail)
    {
        try
        {
            Connection connection = SQL.getConnection();

            //members tablosuna üye kaydı alıyoruz. 
            String query = "Insert into members(name, phone, mail) values(?, ?, ?)";
            PreparedStatement ps;

            ps = connection.prepareStatement(query);
            ps.setString(1, name);
            ps.setString(2, phone);
            ps.setString(3, mail);

            ps.execute();

            connection.close();

            MessageBox.showMessage("Ödünç Eşya Bilgi Sistemi","Üye kaydı başarıyla oluşturuldu.");
        }
        catch (Exception e)
        {
            MessageBox.showMessage("Ödünç Eşya Bilgi Sistemi","Kayıt oluşturulamadı. Kayıt esnasında bir şeyler ters gitti.", "error");
        }
    }

    @FXML
    void addMember(ActionEvent event)
    {
    	//textfield kontrolü
        if(name.getText().isEmpty() || phone.getText().isEmpty() || mail.getText().isEmpty())
        {
            MessageBox.showMessage("Ödünç Eşya Bilgi Sistemi","Lütfen tüm alanları doldurunuz!", "error");
        }
        else
        {
            register(name.getText(), phone.getText(), mail.getText());

            name.setText("");
            phone.setText("");
            mail.setText("");
        }
    }

}
