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
import models.StuffModel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ShowStuffController {

    @FXML
    private TableColumn<StuffModel, String> table_status;

    @FXML
    private TextField name;

    @FXML
    private TableView<StuffModel> stuff_table;

    @FXML
    private TextField type;

    @FXML
    private TableColumn<StuffModel, String > table_id;

    @FXML
    private TableColumn<StuffModel, String> table_name;

    @FXML
    private TextField searchBox;

    @FXML
    private TableColumn<StuffModel, String> table_type;

    ObservableList<StuffModel> stuffList = FXCollections.observableArrayList();

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
                stuff_table.setItems(stuffList);
            }

            connection.close();
        }
        catch (Exception e){
            e.printStackTrace();
        }

        table_id.setCellValueFactory(new PropertyValueFactory<>("id"));
        table_name.setCellValueFactory(new PropertyValueFactory<>("name"));
        table_type.setCellValueFactory(new PropertyValueFactory<>("type"));
        table_status.setCellValueFactory(new PropertyValueFactory<>("status"));
    }

    @FXML
    private void initialize()
    {
        getStuff();

        FilteredList<StuffModel> filteredData = new FilteredList<>(stuffList, p->true);

        searchBox.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(stuff -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                String lowerCaseFilter = newValue.toLowerCase();

                if (stuff.getName().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                }
                return false;
            });
        });

        SortedList<StuffModel> sortedData = new SortedList<>(filteredData);

        sortedData.comparatorProperty().bind(stuff_table.comparatorProperty());

        stuff_table.setItems(sortedData);
    }

    void updateStuff(int ID, String type, String name)
    {
        try
        {
            Connection connection = SQL.getConnection();

            String query = "Update `stuff` SET `type` = ?, `name`= ? where `stuff`.`id` = ?";
            PreparedStatement ps;

            ps = connection.prepareStatement(query);
            ps.setString(1, type);
            ps.setString(2, name);
            ps.setInt(3, ID);
            ps.executeUpdate();

            connection.close();
        }
        catch (Exception e){e.printStackTrace();}
    }

    @FXML
    void update_clicked(ActionEvent event)
    {
        StuffModel model = stuff_table.getSelectionModel().getSelectedItem();

        if (model == null)
        {
            MessageBox.showMessage("Ödünç Eşya Bilgi Sistemi","Lütfen güncellemek istediğiniz eşyayı tablodan seçiniz.", "warning");
        }
        else
        {
            updateStuff(model.getId(), type.getText(), name.getText());
            getStuff();

            MessageBox.showMessage("Ödünç Eşya Bilgi Sistemi", model.getName()+" adlı eşyanın bilgileri güncellendi.");
        }
    }

    void deleteStuff(int id)
    {
        try
        {
            Connection connection = SQL.getConnection();

            String query = "DELETE from stuff where id = ?";
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
        StuffModel model = stuff_table.getSelectionModel().getSelectedItem();

        if (model != null)
        {
            deleteStuff(model.getId());

            getStuff();

            MessageBox.showMessage("Ödünç Eşya Bilgi Sistemi", name.getText()+" eşyası sistemden kaldırıldı.");

            name.setText("");
            type.setText("");
        }
        else
        {
            MessageBox.showMessage("Ödünç Eşya Bilgi Sistemi", "Öncelikle silmek istediğiniz eşyayı seçmelisiniz!");
        }
    }

    @FXML
    void kullanici_table_clicked(MouseEvent event)
    {
        try
        {
            StuffModel model = stuff_table.getSelectionModel().getSelectedItem();

            name.setText(model.getName());
            type.setText(model.getType());

        } catch(Exception ex){}
    }

}
