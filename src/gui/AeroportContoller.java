package gui;

import jade.LaunchAgents;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import rule.JsonToRule;
import rule.RuleBase;
import rule.RuleVariable;

import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Vector;

import static gui.Utilities.*;

public class AeroportContoller  implements Initializable {

    Map variableObjects;
    Map rulesObjects;
    RuleBase rb;
    //window fiels
    private Stage stage;
    private Scene scene;
    private Parent root;

    //jfx fiels
    @FXML
    TextField  txt_nb_billet,txt_nb_bb,txt_nb_enfant,txt_nb_vieux;
    @FXML
    ComboBox cbx_depart,cbx_destination;
    @FXML
    CheckBox check_escale;

    public void createRules() throws Exception {
        JsonToRule rules = new JsonToRule("resources/vente_billets_1.json");
        this.variableObjects  = rules.getVariableObjects();
        this.rulesObjects  = rules.getRuleObjects();
        this.rb = rules.getRb();
    }

    public void submitForm(ActionEvent e )throws Exception {
        //TODO: gerer le cas ou la sommes de bb enfant vieux est plus
                // grande que nb_billet!
        int nbBillet;
        String depart, destination;
        int nbBb= 0;
        int nbEnfant= 0;
        int nbVieux= 0;
        boolean escale;
        if (    (isFilled(txt_nb_billet)) &&
                (isSelectedCombo(cbx_depart)) &&
                (isSelectedCombo(cbx_destination))

        ){
            nbBillet= Integer.parseInt(txt_nb_billet.getText());

            depart = (String) cbx_depart.getSelectionModel().getSelectedItem();
            destination = (String) cbx_destination.getSelectionModel().getSelectedItem();

            escale = check_escale.isSelected(); //


            if (isFilled(txt_nb_bb)){
                nbBb= Integer.parseInt(txt_nb_billet.getText());
            }

            if (isFilled(txt_nb_enfant)){
                nbEnfant= Integer.parseInt(txt_nb_billet.getText());
            }

            if (isFilled(txt_nb_vieux)){
                nbVieux= Integer.parseInt(txt_nb_billet.getText());
            }

            //comboBoxes
            if (isSelectedCombo(cbx_depart)){

                depart = (String) cbx_depart.getSelectionModel().getSelectedItem();


            }

            if (isSelectedCombo(cbx_destination)){
                destination = (String) cbx_destination.getSelectionModel().getSelectedItem();
            }

        }else{
           throw new Exception("Veuillez remplir les cases depart destination et nbBillet") ;
        }

        //TODO: gerer le cas ou les nom
        //apeler la fonction jade(
        LaunchAgents launchObj = new LaunchAgents();
        launchObj.launchAgents(nbBillet,nbBb,nbEnfant,nbVieux,depart,destination,escale);

    }


    public Boolean isSelectedCombo(ComboBox field){
        //TODO: test this
        return field.getSelectionModel().getSelectedItem() != null;
    }


    public void initDepartCombo(){
        RuleVariable a = (RuleVariable)this.variableObjects.get("depart") ;

        Vector elements= a.getLabels();
        for (Object element: elements){
            cbx_depart.getItems().add((String)element);
        }
        cbx_depart.getItems().add("");
    }

    public void initDestinationCombo() {
        RuleVariable a = (RuleVariable)this.variableObjects.get("destination") ;
        Vector elements= a.getLabels();
        for (Object element: elements){
            cbx_destination.getItems().add((String)element);
        }
        cbx_destination.getItems().add("");
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
         try {
                createRules();
            } catch (Exception e) {
                e.printStackTrace();
            }
        initDepartCombo();
        initDestinationCombo();
        acceptOnlyNumbersTextField(txt_nb_enfant);
        acceptOnlyNumbersTextField(txt_nb_vieux);
        acceptOnlyNumbersTextField(txt_nb_bb);
        acceptOnlyNumbersTextField(txt_nb_billet);

    }


    public void switchToLaptopWin (ActionEvent event) throws Exception{
        Utilities u = new Utilities();
        u.switchWindow(event,"AchatLaptop.fxml",root,stage,scene );
    }

    public void switchToVoitureWin (ActionEvent event) throws Exception{
        Utilities u = new Utilities();
        u.switchWindow(event,"Voiture.fxml",root,stage,scene );
    }

}
