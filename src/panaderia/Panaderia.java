/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package panaderia;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 *
 * @author usuario
 */
public class Panaderia extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("FXMLPanaderia.fxml"));
        
        Scene scene = new Scene(root);
            
        stage.setScene(scene);
        stage.show();
        
        stage.setTitle("Panaderia");//Nombre de la ventana principal
        Image icono = new Image("/panaderia/images/PAA2.png");//Carga del icono
        stage.getIcons().add(icono);//Icono de la ventana principal
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
