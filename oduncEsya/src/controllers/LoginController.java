package controllers;

import basics.MessageBox;
import basics.SQL;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class LoginController {

    @FXML
    private PasswordField password;

    @FXML
    private AnchorPane scenePane;

    @FXML
    private TextField username;

    @FXML
    void login(ActionEvent event) {

        if (username.getText().isEmpty() || password.getText().isEmpty()) {
            MessageBox.showMessage("Ödünç Eşya Bilgi Sistemi", "Lütfen kullanıcı adınızı ve şifrenizi giriniz!", "error");
        }
        else
        {
            boolean isThere = false; //Sistem kullanıcısının tabloda olup olmadığını gösteren boolean

            try
            {
                Connection connection = SQL.getConnection();

                String query = "Select * from admins";
                PreparedStatement ps;
                ResultSet rs;

                ps = connection.prepareStatement(query);
                rs = ps.executeQuery();

                while (rs.next())
                {
                    if (username.getText().equals(rs.getString("username")) && password.getText().equals(rs.getString("password")))
                    {
                        isThere = true;
                        break; //eğer tabloda sistem kullanıcısı varsa aramayı bitir
                    }
                }

                connection.close();
            }
            catch (Exception e)
            {
                isThere = false;
                //e.printStackTrace();
            }

            if(isThere) // Sistem kullanıcısı giriş yaptıysa ana sayfa penceresini aç
            {
                try {
                    Stage stage = new Stage();
                    Parent root = FXMLLoader.load(getClass().getResource("../designs/homePage.fxml"));
                    stage.setTitle("Ödünç Eşya Otomasyonu");
                    stage.setScene(new Scene(root));
                    stage.initStyle(StageStyle.UTILITY);
                    stage.show();
                }catch (Exception e) {
                    e.printStackTrace();
                }

                Stage currStage = (Stage) scenePane.getScene().getWindow(); //login penceresini kapat
                currStage.close();
            }

            else
            {
                MessageBox.showMessage("Ödünç Eşya Bilgi Sistemi","Böyle bir kayıt bulunmamaktadır!", "warning");
            }
        }
    }
}