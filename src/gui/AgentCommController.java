package gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

import static gui.Utilities.acceptOnlyNumbersTextField;

public class AgentCommController implements Initializable {
    @FXML
    public    TextArea txt_display_agtak;
    @FXML
    public   TableView <Offer> tab_results = new TableView();




    //windowab fiels
    private Stage stage;
    private Scene scene;
    private Parent root;


    public void switchToLaptopWin(ActionEvent event) throws Exception {
        Utilities u = new Utilities();
        u.switchWindow(event, "AchatLaptop.fxml", root, stage, scene);
    }

    public void switchToVoitureWin(ActionEvent event) throws Exception {
        Utilities u = new Utilities();
        u.switchWindow(event, "Voiture.fxml", root, stage, scene);
    }

    public void switchToAeroportWin(ActionEvent event) throws Exception {
        Utilities u = new Utilities();
        u.switchWindow(event, "Aeroport.fxml", root, stage, scene);
    }

    public void acceptOffer(ActionEvent e )throws Exception {

    }





    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        //initializing results table
        TableColumn column1 = new TableColumn<>("Companie");
        column1.setCellValueFactory(new PropertyValueFactory<>("companie"));

        TableColumn column4 = new TableColumn<>("Escale");
        column4.setCellValueFactory(new PropertyValueFactory<>("escale"));

        TableColumn column5 = new TableColumn<>("Prix");
        column5.setCellValueFactory(new PropertyValueFactory<>("prix"));

        tab_results.getColumns().addAll(column1,column4, column5);

    }

}
