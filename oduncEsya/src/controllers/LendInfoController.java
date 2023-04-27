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
import models.LendModel;
import models.MemberModel;
import models.StuffModel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class LendInfoController {

    @FXML
    private Label date;

    @FXML
    private Label member_id;

    @FXML
    private Label stuff_type;

    @FXML
    private Label member_phone;

    @FXML
    private AnchorPane scenePane;

    @FXML
    private Label stuff_name;

    @FXML
    private Label member_mail;

    @FXML
    private Label stuff_id;

    @FXML
    private Label member_name;

    @FXML
    private TextField searchBox;

    @FXML
    void back_clicked(MouseEvent event)
    {
        Stage currStage = (Stage) scenePane.getScene().getWindow();
        currStage.close();
    }

    ObservableList<StuffModel> stuffList = FXCollections.observableArrayList();

    ObservableList<MemberModel> memberList = FXCollections.observableArrayList();

    ObservableList<LendModel>lendList = FXCollections.observableArrayList();

    void getMembers()
    {
        try
        {
            memberList.clear();

            Connection connection = SQL.getConnection();

            String query = "Select * from members";
            PreparedStatement ps;
            ResultSet rs;

            ps = connection.prepareStatement(query);
            rs = ps.executeQuery();

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

    void getStuff()
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

    void getLendings()
    {
        try
        {
            lendList.clear();

            Connection connection = SQL.getConnection();

            String query = "Select * from lending";
            PreparedStatement ps;
            ResultSet rs;

            ps = connection.prepareStatement(query);
            rs = ps.executeQuery();

            while (rs.next())
            {
                lendList.add(new LendModel(rs.getInt("stuff_id"), rs.getInt("member_id"), rs.getString("date")));
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
        getLendings();
        getStuff();

        searchBox.textProperty().addListener(((observable, oldValue, newValue) -> {
            LendModel lendModel = null;

            if (newValue.isEmpty())
                newValue = "-1";

            for(LendModel model:lendList)
            {
                if (Integer.parseInt(newValue) == model.getStuff_id())
                {
                        lendModel=model;
                        break;
                }
            }

            if(lendModel != null) {

                StuffModel stuffModel = null;
                MemberModel memberModel = null;

                for (StuffModel model : stuffList) {
                    if (lendModel.getStuff_id() == model.getId()) {
                        stuffModel = model;
                        break;
                    }
                }

                for (MemberModel model : memberList) {
                    if (lendModel.getMember_id() == model.getId()) {
                        memberModel = model;
                        break;
                    }
                }

                date.setText(lendModel.getDate());

                stuff_id.setText(stuffModel.getId()+"");
                stuff_name.setText(stuffModel.getName());
                stuff_type.setText(stuffModel.getType());

                member_id.setText(memberModel.getId()+"");
                member_name.setText(memberModel.getName());
                member_phone.setText(memberModel.getPhone());
                member_mail.setText(memberModel.getMail());
            }
            else
            {
                stuff_id.setText("");
                stuff_name.setText("");
                stuff_type.setText("");

                member_id.setText("");
                member_name.setText("");
                member_phone.setText("");
                member_mail.setText("");

                date.setText("");
            }

        }));
    }

    void changeStatus(int stuff_id)
    {
        try
        {
            Connection connection = SQL.getConnection();

            String query = "Update `stuff` SET `status` = ? where `stuff`.`id` = ?";
            PreparedStatement ps;

            ps = connection.prepareStatement(query);
            ps.setString(1, "Ödünç Allınabilir");
            ps.setInt(2, stuff_id);
            ps.executeUpdate();

            connection.close();
        }
        catch (Exception e){e.printStackTrace();}
    }

    void deleteLend(int stuff_id)
    {
        try
        {
            Connection connection = SQL.getConnection();

            String query = "Delete from `lending` where `stuff_id` = ?";
            PreparedStatement ps;

            ps = connection.prepareStatement(query);
            ps.setInt(1, stuff_id);
            ps.executeUpdate();

            connection.close();
        }
        catch (Exception e){e.printStackTrace();}
    }

    @FXML
    void lendBack_clicked(ActionEvent event)
    {
    	//Ödünç alınan eşya geri verileceği zaman öncelikle o eşyanın durumunu pozitif yapıyoruz.
    	//Ardınan lending tablosundan bu ödünç alma işlemini kaldırıyoruz.
    	
        if(date.getText().isEmpty())
        {
            MessageBox.showMessage("Ödünç Eşya Bilgi Sistemi", "Lütfen geçerli bir eşya ID'si giriniz!", "error");
        }
        else
        {
            changeStatus(Integer.parseInt(searchBox.getText()));
            deleteLend(Integer.parseInt(searchBox.getText()));

            MessageBox.showMessage("Ödünç Eşya Bilgi Sistemi","İşlem başarılı.");

            stuff_id.setText("");
            stuff_name.setText("");
            stuff_type.setText("");

            member_id.setText("");
            member_name.setText("");
            member_phone.setText("");
            member_mail.setText("");

            date.setText("");
            searchBox.setText("");
        }
    }

}
