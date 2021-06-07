package gui;

import javafx.event.ActionEvent;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class AeroportContoller {

    private Stage stage;
    private Scene scene;
    private Parent root;

    public void switchToLaptopWin (ActionEvent event) throws Exception{
        Utilities u = new Utilities();
        u.switchWindow(event,"AchatLaptop.fxml",root,stage,scene );
    }

    public void switchToVoitureWin (ActionEvent event) throws Exception{
        Utilities u = new Utilities();
        u.switchWindow(event,"Voiture.fxml",root,stage,scene );
    }

}
