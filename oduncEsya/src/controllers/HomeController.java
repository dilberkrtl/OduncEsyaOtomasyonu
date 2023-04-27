package controllers;


import basics.MessageBox;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class HomeController {

    @FXML
    private AnchorPane scenePane;

    @FXML
    void uyeler_clicked(ActionEvent event)
    {

    	//Tıklanan her buton için özelleşmiş pencereler açıyoruz.
    	//FXMLLoader ile bu fxml dosyalarını yüklüyoruz ve bunları show ile gösteriyoruz.
    	
        try {
            Stage stage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("../designs/showMembersPage.fxml"));
            stage.setTitle("Üyeler");
            stage.setScene(new Scene(root));
            stage.initStyle(StageStyle.UTILITY);
            stage.show();
        }catch (Exception e) {
            e.printStackTrace();
        }

    }

    @FXML
    void uyeEkle_clicked(ActionEvent event)
    {
        try {
            Stage stage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("../designs/registerMemberPage.fxml"));
            stage.setTitle("Üye Ekle");
            stage.setScene(new Scene(root));
            stage.initStyle(StageStyle.UTILITY);
            stage.show();
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void esyalar_clicked(ActionEvent event)
    {
        try {
            Stage stage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("../designs/showStuffPage.fxml"));
            stage.setTitle("Eşyalar");
            stage.setScene(new Scene(root));
            stage.initStyle(StageStyle.UTILITY);
            stage.show();
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void esyaEkle_clicked(ActionEvent event)
    {
        try {
            Stage stage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("../designs/addStuffPage.fxml"));
            stage.setTitle("Eşya Ekle");
            stage.setScene(new Scene(root));
            stage.initStyle(StageStyle.UTILITY);
            stage.show();
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void bilgiler_clicked(ActionEvent event)
    {
        MessageBox.showMessage("Ödünç Eşya Bilgi Sistemi","Eşyalar 30 günlüğüne ödünç alınabilir.", "warning");
    }

    @FXML
    void exit_clicked(MouseEvent event)
    {
        Stage currStage = (Stage) scenePane.getScene().getWindow();
        currStage.close();
    }

    @FXML
    void oduncVer_clicked(ActionEvent event)
    {
        try {
            Stage stage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("../designs/lendPage.fxml"));
            stage.setTitle("Ödünç Ver");
            stage.setScene(new Scene(root));
            stage.initStyle(StageStyle.UTILITY);
            stage.show();
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void oduncBilgileri_clicked(ActionEvent event)
    {
        try {
            Stage stage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("../designs/lendInfoPage.fxml"));
            stage.setTitle("Ödünç Bilgileri");
            stage.setScene(new Scene(root));
            stage.initStyle(StageStyle.UTILITY);
            stage.show();
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

}
