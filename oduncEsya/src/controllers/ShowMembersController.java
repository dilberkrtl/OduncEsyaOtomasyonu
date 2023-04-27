package controllers;

import basics.MessageBox;
import basics.SQL;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import models.MemberModel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ShowMembersController {

    @FXML
    private TextField mail;

    @FXML
    private TextField phone;

    @FXML
    private TableView<MemberModel> member_table;

    @FXML
    private TextField name;

    @FXML
    private TableColumn<MemberModel, String> table_mail;

    @FXML
    private TableColumn<MemberModel, String> table_telefon;

    @FXML
    private TableColumn<MemberModel, String> table_id;

    @FXML
    private TableColumn<MemberModel, String> table_name;

    @FXML
    private TextField searchBox;

    ObservableList<MemberModel> memberList = FXCollections.observableArrayList();

    void updateMember(int ID, String Name, String Phone, String Mail)
    {
        try
        {
            Connection connection = SQL.getConnection();

            //Üyenin ad, telefon, mail gibi bilgilerini id numarasına göre güncelleme sorgusu
            String query = "Update `members` SET `name` = ?, `phone`= ?, `mail` = ? where `members`.`id` = ?";
            PreparedStatement ps;

            ps = connection.prepareStatement(query);
            ps.setString(1, Name);
            ps.setString(2, Phone);
            ps.setString(3, Mail);
            ps.setInt(4, ID);
            ps.executeUpdate();

            connection.close();
        }
        catch (Exception e){e.printStackTrace();}
    }

    @FXML
    void update_clicked(ActionEvent event)
    {
        MemberModel model = member_table.getSelectionModel().getSelectedItem(); //Tablodan tıkladığımız üyeyi al

        if (model == null)
        {
            MessageBox.showMessage("Ödünç Eşya Bilgi Sistemi","Lütfen güncellemek istediğiniz üyeyi tablodan seçiniz.", "warning");
        }
        else
        {
            updateMember(model.getId(), name.getText(), phone.getText(), mail.getText());
            getMembers();

            MessageBox.showMessage("Ödünç Eşya Bilgi Sistemi", model.getName()+" adlı üyenin bilgileri güncellendi.");
        }
    }

    void getMembers()
    {
    	//Tüm üyeleri memberList listesine çekiyoruz.
    	
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
                member_table.setItems(memberList);
            }

            connection.close();
        }
        catch (Exception e){
            e.printStackTrace();
        }

        //Çektiğimiz üyeleri ilgili tablo sütunlarına tek tek veriyoruz.
        table_id.setCellValueFactory(new PropertyValueFactory<>("id"));
        table_name.setCellValueFactory(new PropertyValueFactory<>("name"));
        table_telefon.setCellValueFactory(new PropertyValueFactory<>("phone"));
        table_mail.setCellValueFactory(new PropertyValueFactory<>("mail"));
    }

    void deleteMember(int id)
    {
        try
        {
            Connection connection = SQL.getConnection();

            String query = "DELETE from members where id = ?";
            PreparedStatement ps;

            ps = connection.prepareStatement(query);
            ps.setInt(1, id);
            ps.execute();

            connection.close();
        }
        catch (Exception e){}
    }

    @FXML
    void delete_clicked(ActionEvent event)
    {
        MemberModel model = member_table.getSelectionModel().getSelectedItem();

        if (model != null)
        {
            deleteMember(model.getId());

            getMembers();

            MessageBox.showMessage("Ödünç Eşya Bilgi Sistemi", name.getText()+" üyesi sistemden kaldırıldı.");

            name.setText("");
            phone.setText("");
            mail.setText("");
        }
        else
        {
            MessageBox.showMessage("Ödünç Eşya Bilgi Sistemi", "Öncelikle silmek istediğiniz üyeyi seçmelisiniz!");
        }
    }

    @FXML
    void kullanici_table_clicked(MouseEvent event)
    {
        try
        {
            MemberModel model = member_table.getSelectionModel().getSelectedItem();

            name.setText(model.getName());
            phone.setText(model.getPhone());
            mail.setText(model.getMail());

        } catch(Exception ex){}
    }

    @FXML
    private void initialize()
    {
        getMembers();

        FilteredList<MemberModel> filteredData = new FilteredList<>(memberList, p->true);

        searchBox.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(member -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                String lowerCaseFilter = newValue.toLowerCase();

                if (member.getName().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                }
                return false;
            });
        });

        SortedList<MemberModel> sortedData = new SortedList<>(filteredData);

        sortedData.comparatorProperty().bind(member_table.comparatorProperty());

        member_table.setItems(sortedData);
    }
}
