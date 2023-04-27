package controllers;

import basics.MessageBox;
import basics.SQL;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import models.MemberModel;
import models.StuffModel;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;

public class LendController {

    @FXML
    private TextField member_id;

    @FXML
    private Label stuff_type;

    @FXML
    private AnchorPane scenePane;

    @FXML
    private Label stuff_status;

    @FXML
    private Label stuff_name;

    @FXML
    private TextField stuff_id;

    @FXML
    private Label member_name;

    ObservableList<StuffModel> stuffList = FXCollections.observableArrayList();

    ObservableList<MemberModel> memberList = FXCollections.observableArrayList();

    void getMembers()
    {
        try
        {
            memberList.clear();

            Connection connection = SQL.getConnection();

            String query = "Select * from members"; //Tüm üyeleri çekiyoruz
            PreparedStatement ps;
            ResultSet rs;

            ps = connection.prepareStatement(query);
            rs = ps.executeQuery();

            //Her okuduğumuz satır için memberList listesine onu ekliyoruz
            while (rs.next())
            {
                memberList.add(new MemberModel(rs.getInt("id"), rs.getString("name"), rs.getString("phone"), rs.getString("mail")));
            }

            connection.close();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    void getStuff() //Tüm eşyaları stuffList listesine dolduruyoruz
    {
        try
        {
            stuffList.clear();

            Connection connection = SQL.getConnection();

            String query = "Select * from stuff";
            PreparedStatement ps;
            ResultSet rs;

            ps = connection.prepareStatement(query);
            rs = ps.executeQuery();

            while (rs.next())
            {
                stuffList.add(new StuffModel(rs.getInt("id"), rs.getString("type"), rs.getString("name"), rs.getString("status")));
            }

            connection.close();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    @FXML
    private void initialize()
    {
        getMembers();
        getStuff();

        //stuff_id textfield objesi için bir listener oluşturduk. 
        //Bu getText() metodunun değişimini gözlüyor. Her değiştiğinde içindeki metot çalışıyor.
        stuff_id.textProperty().addListener((observable, oldValue, newValue) -> {
            StuffModel temp = null;
            if(newValue.isEmpty())
                newValue = "-1";
            for(StuffModel model:stuffList)
            {
                if((model.getId() == Integer.parseInt(newValue)))
                {
                    temp = model;
                    break;
                }
            }

            if(temp != null)//Aradığımız eşyayı bulduktan sonra ilgili labellara yazıyoruz
            {
                stuff_name.setText(temp.getName());
                stuff_type.setText(temp.getType());
                stuff_status.setText(temp.getStatus());
            }
            else
            {
                stuff_name.setText("");
                stuff_type.setText("");
                stuff_status.setText("");
            }

        });

        member_id.textProperty().addListener((observable, oldValue, newValue) -> {
            MemberModel temp = null;
            if(newValue.isEmpty())
                newValue = "-1";
            for(MemberModel model:memberList)
            {
                if((model.getId() == Integer.parseInt(newValue)))
                {
                    temp = model;
                    break;
                }
            }

            if(temp != null)//Aradığımız üyeyi bultan sonra ilgili labela yazıyoruz
            {
                member_name.setText(temp.getName());
            }
            else
            {
                member_name.setText("");
            }

        });

    }

    void lendStuff(int stuff_id, int member_id)
    {
        SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss z"); //Tarihi ve saati alıyoruz
        Date date = new Date(System.currentTimeMillis());

        try
        {
            Connection connection = SQL.getConnection();

            String query = "Insert into lending(stuff_id, member_id, date) values(?, ?, ?)";
            PreparedStatement ps;

            ps = connection.prepareStatement(query);
            ps.setInt(1, stuff_id);
            ps.setInt(2, member_id);
            ps.setString(3, formatter.format(date));

            ps.execute();

            connection.close();
        }
        catch (Exception e){}
    }

    void changeStatus(int stuff_id)
    {
        try
        {
            Connection connection = SQL.getConnection();

            String query = "Update `stuff` SET `status` = ? where `stuff`.`id` = ?";
            PreparedStatement ps;

            ps = connection.prepareStatement(query);
            ps.setString(1, "Ödünç Alınamaz"); //Bir üye bir eşyayı aldıktan sonra o eşyayı Ödünç Alınamaz yapıyoruz
            ps.setInt(2, stuff_id);
            ps.executeUpdate();

            connection.close();
        }
        catch (Exception e){e.printStackTrace();}
    }

    @FXML
    void lend_clicked(ActionEvent event)
    {
        if (stuff_name.getText().isEmpty())
        {
            MessageBox.showMessage("Ödünç Eşya Bilgi Sistemi", "Lütfen geçerli bir eşya ID'si giriniz!", "error");
        }

        else if(member_name.getText().isEmpty())
        {
            MessageBox.showMessage("Ödünç Eşya Bilgi Sistemi", "Lütfen geçerli bir üye ID'si giriniz!", "error");
        }
        else
        {
            lendStuff(Integer.parseInt(stuff_id.getText()), Integer.parseInt(member_id.getText()));
            changeStatus(Integer.parseInt(stuff_id.getText()));

            MessageBox.showMessage("Ödünç Eşya Bilgi Sistemi","İşlem başarılı.");

            stuff_name.setText("");
            stuff_type.setText("");
            stuff_status.setText("");

            member_name.setText("");

            stuff_id.setText("");

            member_id.setText("");
        }
    }

    @FXML
    void back_clicked(MouseEvent event)
    {
        Stage currStage = (Stage) scenePane.getScene().getWindow();
        currStage.close();
    }

}
