package gui;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;



public class VoitureController{
    /*TODO: add the setUp of comboBoxes and textAreas,
        witout the Initialize thing(like with LaptopController)
    */
    @FXML
    TextField txt_numWheels,txt_numDoors;
    @FXML
    TextArea txt_display_rules,txt_display_vehicule,
                txt_display_conflictSet;

    @FXML
    ComboBox cbx_motor,cbx_vehiculeType;

    private Stage stage;
    private  Scene scene;
    private Parent root;

    public void startForwardChain(ActionEvent e) throws Exception {
        //TODO: implement this method




    }
    public void initVehiculeTypeCombo() {
        cbx_vehiculeType.getItems().add("cycle");
        cbx_vehiculeType.getItems().add("automobile");
    }
    public void initMotorCombo(){
        cbx_motor.getItems().add("yes");
        cbx_motor.getItems().add("no");
    }




    public void switchToLaptopWin (ActionEvent event) throws Exception{
        Utilities u = new Utilities();
        u.switchWindow(event,"AchatLaptop.fxml",root,stage,scene );
    }

    public void switchToAeroportWin(ActionEvent event) throws Exception {
        Utilities u = new Utilities();
        u.switchWindow(event, "Aeroport.fxml", root, stage, scene);
    }



}
