//TODO:add a switch between the car rules and our own rules

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


}
