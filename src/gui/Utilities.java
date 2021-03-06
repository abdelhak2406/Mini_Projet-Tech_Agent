package gui;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
//import org.jetbrains.annotations.NotNull;

import java.io.IOException;

/*
    this class is aimed to contain useful methods that we will need
    in the gui.

 */
public class Utilities {

   public  static  void acceptOnlyNumbersTextField(TextField txt){
      /*
         just accept Numbers in a specific textField
       */
      txt.textProperty().addListener(new ChangeListener<String>() {
         @Override
         public void changed(ObservableValue<? extends String> observable, String oldValue,
                             String newValue) {
            if (!newValue.matches("\\d*")) {
               txt.setText(newValue.replaceAll("[^\\d]", ""));
            }
         }
      });
   }

   public static String displaydToJsonName(String displayed){
      /*
        take the name given in the UI and returns the one in the json
        so everything is coherent.
        ATTENTION: 
         **Warning: si dans achat LapotopController il y'a modification dans initNomDuTruc on doit modifier cette fonction
         il faut modifier meme ici 
       */
      
      //software needs
      if (displayed.equals("User Friendly")){
         return "user_friendly";
      }
      if (displayed.equals("Final Cut Pro")){
         return "finalCutPro";
      }
      if (displayed.equals("Gaming")){
         return "gaming";
      }
      if (displayed.equals("New user interface")){
         return "niche_user_interface";
      }
      
      //Desktop Envirnment 
      if (displayed.equals("Kde Plasma")){
         return "kde_plasma";
      }
      if (displayed.equals("XFCE")){
         return "xfce";
      }
      if (displayed.equals("Cinamone")){
         return "cinnamon";
      }
      
      // gnu/linux
      if (displayed.equals("Manjaro")){
         return "Manjaro";
      }
      if (displayed.equals("Linux Mint")){
         return "LinuxMint";
      }
      if (displayed.equals("Kubuntu")){
         return "Kubuntu";
      }
      
      //Os
      if (displayed.equals("GNU/Linux")){
         return "linux";
      }
      if (displayed.equals("Windows")){
         return "windows";
      }
      if (displayed.equals("iOS")){
         return "iOS";
      }
      return null;
   }


   public static void addBlancChoice(ComboBox cbx){
       /*
            when a user selects an item in the choice box and wants to go back
            we need to add a "" item that's what this function do
        */

      cbx.getItems().add("");
        /*
        cbx_softwareNeeds.getItems().add("");
        cbx_desktopEnvirnment.getItems().add("");
        cbx_linuxDistro.getItems().add("");
        cbx_os.getItems().add("");
        */

   }

   public static Boolean isFilled(TextField input){
      //works !
      return  !(input.getText() == null || input.getText().trim().isEmpty()) ;
   }

   public void switchWindow(ActionEvent event, String fxmlWindow, Parent root, Stage stage, Scene scene) throws IOException {

      root = FXMLLoader.load(getClass().getResource(fxmlWindow));
      stage =(Stage) ((Node) event.getSource()).getScene().getWindow();
      scene = new Scene(root);
      stage.setScene(scene);
      stage.show();
   }

}
