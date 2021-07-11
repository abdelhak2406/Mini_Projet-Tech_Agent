package gui;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import gui.Utilities.*;
import rule.JsonToRule;
import rule.Rule;
import rule.RuleBase;
import rule.RuleVariable;

import java.net.URL;
import java.util.*;

import static gui.Utilities.acceptOnlyNumbersTextField;
import static gui.Utilities.isFilled;

public class VoitureController implements Initializable {
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

        JsonToRule rules = new JsonToRule("resources/voitures.json") ;
        Map variableObjects  = rules.getVariableObjects() ;
        Map rulesObjects  = rules.getRuleObjects() ;
        RuleBase rb = rules.getRb() ;
        //comboBoxes
        if(isSelectedCombo(cbx_vehiculeType)) {
            RuleVariable vehiculeType = (RuleVariable) variableObjects.get("vehiculeType");
            String vehicValue = (String) cbx_vehiculeType.getSelectionModel().getSelectedItem();
            vehiculeType.setValue(vehicValue) ;
            //set
        }
        if(isSelectedCombo(cbx_motor)) {
            RuleVariable motor = (RuleVariable) variableObjects.get("motor");
            String motorValue = (String) cbx_motor.getSelectionModel().getSelectedItem();
            motor.setValue(motorValue);
        }
        //textFields
        if (isFilled(txt_numDoors)){
            RuleVariable numDor = (RuleVariable)  variableObjects.get("numDoors");
            numDor.setValue(txt_numDoors.getText());
        }
        if (isFilled(txt_numWheels)){
            RuleVariable numWhe = (RuleVariable)  variableObjects.get("numWheels");
            numWhe.setValue(txt_numWheels.getText());
        }
        //dissplay stuff!!
        String strRules = rb.displayRules();
        //display the rules in the rule  textArea
        txt_display_rules.setText(strRules);

        HashMap<String, ArrayList> outputs = rb.forwardChain();
        ArrayList<Rule> firedRules = outputs.get("fired");
        ArrayList<Vector> conflictSets = outputs.get("conflictSet");
        int lim =firedRules.size();

        for(int i=0; i<lim;i++){
            Rule firedTmp = firedRules.get(i);
            Vector tmpConf = conflictSets.get(i);
            System.out.println("conflict set "+i+"\n\n------------*------------");
            String cnf = rb.displayConflictSet(tmpConf);
            txt_display_conflictSet.appendText(cnf+"fired: --> "+firedTmp.getName()+"\n\n");

            System.out.println("fired "+i+" "+firedTmp.getName());

        }


        String strListVars = rb.displayVariables();
        txt_display_vehicule.setText(rb.displayVarValue("vehicule"));





    }
    public void initVehiculeTypeCombo() {
        cbx_vehiculeType.getItems().add("cycle");
        cbx_vehiculeType.getItems().add("automobile");
    }
    public void initMotorCombo(){
        cbx_motor.getItems().add("yes");
        cbx_motor.getItems().add("no");
    }

    public Boolean isSelectedCombo(ComboBox field){
        //TODO: test this
        return field.getSelectionModel().getSelectedItem()!=null;
    }



    public void switchToLaptopWin (ActionEvent event) throws Exception{
        Utilities u = new Utilities();
        u.switchWindow(event,"AchatLaptop.fxml",root,stage,scene );
    }

    public void switchToAeroportWin(ActionEvent event) throws Exception {
        Utilities u = new Utilities();
        u.switchWindow(event, "Aeroport.fxml", root, stage, scene);
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initMotorCombo();
        initVehiculeTypeCombo();
        acceptOnlyNumbersTextField(txt_numDoors);
        acceptOnlyNumbersTextField(txt_numWheels);
    }


}
