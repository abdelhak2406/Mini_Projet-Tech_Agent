package gui;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import rule.JsonToRule;
import rule.RuleBase;
import rule.RuleVariable;

import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;

import static gui.Utilities.*;

//TODO: find a way to get the string that contains the conflictSet
//TODO: add blanc choise to the comboBox

public class LaptopController implements Initializable {
    @FXML
    TextField txt_ram,txt_budget;

    @FXML
    ComboBox cbx_os,cbx_desktopEnvirnment,cbx_linuxDistro,
              cbx_softwareNeeds;
    @FXML
    TextArea txt_display_conflictSet,txt_display_laptop,
                txt_display_rules;

   @FXML
    public void startForwardChain(ActionEvent e) throws Exception {
       //TODO: refactor this function

       //just to make sure at least 1 element is set.
       int numSelected=0;

       JsonToRule rules = new JsonToRule("resources/achat_laptop.json") ;
       Map variableObjects  = rules.getVariableObjects() ;
       Map rulesObjects  = rules.getRuleObjects() ;
       RuleBase rb = rules.getRb() ;

       //combo Boxes
        if(isSelectedCombo(cbx_softwareNeeds)) {
            numSelected++;
            System.out.println("we are in software needs");
            RuleVariable software_needs = (RuleVariable) variableObjects.get("software_needs");
            //we need to get the name of it in the json file so and then we set the value
            String strContent  = (String) cbx_softwareNeeds.getSelectionModel().getSelectedItem();
            String fieldContent = displaydToJsonName(strContent);
            software_needs.setValue(fieldContent);

        }
        if(isSelectedCombo(cbx_desktopEnvirnment)) {
            numSelected++;
            RuleVariable desktopEnv = (RuleVariable) variableObjects.get("desktop_environement");
            //we need to get the name of it in the json file so and then we set the value
            String strContent  = (String) cbx_desktopEnvirnment.getSelectionModel().getSelectedItem();
            String fieldContent = displaydToJsonName(strContent);
            desktopEnv.setValue(fieldContent);

        }
        if(isSelectedCombo(cbx_linuxDistro)) {
           numSelected++;
           RuleVariable linuxDistro = (RuleVariable) variableObjects.get("linux_distro");
            String strContent  = (String) cbx_linuxDistro.getSelectionModel().getSelectedItem();
            String fieldContent = displaydToJsonName(strContent);
            linuxDistro.setValue(fieldContent);
        }
        if(isSelectedCombo(cbx_os)){
            numSelected++;
            RuleVariable os = (RuleVariable) variableObjects.get("os");
            String strContent  = (String) cbx_os.getSelectionModel().getSelectedItem();
            String fieldContent = displaydToJsonName(strContent);
            os.setValue(fieldContent);
        }

        //txtfields
        if(isFilled(txt_ram)){
            numSelected++;
            RuleVariable ramVar = (RuleVariable) variableObjects.get("ram");
            ramVar.setValue(txt_ram.getText());
        }
        if(isFilled(txt_budget)){
           numSelected++;
           RuleVariable user_budget = (RuleVariable) variableObjects.get("user_budget");
           user_budget.setValue(txt_budget.getText());
        }

       String strRules = rb.displayRules();
       //display the rules in the rule  textArea
       txt_display_rules.setText(strRules);

       //TODO: find a way to get the strings that contain the conflict set ect
       rb.forwardChain();
       String strListVars = rb.displayVariables();
       txt_display_laptop.setText(rb.displayVarValue("laptop"));
    }

    public void reset(ActionEvent e) {
       //TODO: find some use to this

    }
    public void addBlancChoiceToCombos(){
        addBlancChoice(cbx_softwareNeeds);
        addBlancChoice(cbx_desktopEnvirnment);
        addBlancChoice(cbx_linuxDistro);
        addBlancChoice(cbx_os);

    }


    public  void initSoftwareCombo(){
        cbx_softwareNeeds.getItems().add("User Friendly");
        cbx_softwareNeeds.getItems().add("Final Cut Pro");
        cbx_softwareNeeds.getItems().add("Gaming");
        cbx_softwareNeeds.getItems().add("New user interface");
    }

    public  void initDesktopCombo(){
        cbx_desktopEnvirnment.getItems().add("Kde Plasma");
        cbx_desktopEnvirnment.getItems().add("XFCE");
        cbx_desktopEnvirnment.getItems().add("Cinamone");
        }

    public  void initLinuxCombo(){
        cbx_linuxDistro.getItems().add("Manjaro");
        cbx_linuxDistro.getItems().add("Linux Mint");
        cbx_linuxDistro.getItems().add("Kubuntu");
    }

    public  void initOsCombo(){
       /*
       si modifier alors modifier Utilities.displaydToJsonName
        */
        cbx_os.getItems().add("GNU/Linux");
        cbx_os.getItems().add("Windows");
        cbx_os.getItems().add("iOs");
    }

    void disableUserInput(){
        txt_display_laptop.editableProperty().setValue(false);
        txt_display_conflictSet.editableProperty().setValue(false);
        txt_display_rules.editableProperty().setValue(false);
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initSoftwareCombo();
        initDesktopCombo();
        initLinuxCombo();
        initOsCombo();
        disableUserInput();
        addBlancChoiceToCombos();
        acceptOnlyNumbersTextField(txt_ram);
        acceptOnlyNumbersTextField(txt_budget);
    }

    public Boolean isSelectedCombo(ComboBox field){
       //TODO: test this
           return field.getSelectionModel().getSelectedItem()!=null;
    }


    public  Boolean isFilled(TextField input){
       //works !
        return  !(input.getText() == null || input.getText().trim().isEmpty()) ;
    }


    public void getInputs(){
        System.out.println("\n\n\n WE ARE IN getInputs\n");
        System.out.println( cbx_os.getSelectionModel().getSelectedItem());
        String os = (String) cbx_os.getSelectionModel().getSelectedItem();
        String desktopEnv = (String) cbx_desktopEnvirnment.getSelectionModel().getSelectedItem();
        String linuxDistro =(String)  cbx_linuxDistro.getSelectionModel().getSelectedItem();
        cbx_softwareNeeds.getSelectionModel().getSelectedItem();
/*
                ,cbx_desktopEnvirnment,cbx_linuxDistro,
                cbx_softwareNeeds;
*/
    }



}

