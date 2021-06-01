//TODO:add a switch between the car rules and our own rules

import java.util.*;

import rule.JsonToRule;
import rule.RuleBase;
import rule.RuleVariable;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;


    public class Main extends Application {


        @Override
        public void start(Stage stage) throws Exception {

            Parent root = FXMLLoader.load(getClass().getResource("gui/AchatLaptop.fxml"));
            stage.setTitle("Tech Agent Project");
            Scene scene = new Scene(root);

            Image icon = new Image("gui/assets/2000.png");



            stage.getIcons().add(icon);
            stage.setScene(scene);
            stage.show();
        }

        public static void main(String[] args){

            launch(args);

        }



/*

    //Create the rules
       JsonToRule rules = new JsonToRule("resources/achat_laptop.json") ;
       Map variableObjects  = rules.getVariableObjects() ;
       Map rulesObjects  = rules.getRuleObjects() ;

       RuleVariable user_budget = (RuleVariable) variableObjects.get("user_budget");
       RuleVariable os = (RuleVariable) variableObjects.get("os");
       RuleVariable software_needs = (RuleVariable) variableObjects.get("software_needs");
       RuleVariable ramVar = (RuleVariable) variableObjects.get("ram");
       RuleBase rb = rules.getRb() ;

        //input
        user_budget.setValue("999");
        os.setValue("iOS");
        software_needs.setValue("finalCutPro");
        ramVar.setValue("7");
        rb.displayRules();
        rb.forwardChain();
        rb.displayVariables();
    }

*/

}
