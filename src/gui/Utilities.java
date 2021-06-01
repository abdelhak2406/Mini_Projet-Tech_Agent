package gui;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

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
         TODO: si dans achat LapotopController il y'a modification ant initNomDuTruc
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
      if (displayed.equals("iOs")){
         return "ios";
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

}
