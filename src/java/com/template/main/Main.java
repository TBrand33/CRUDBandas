package com.template.main;

import com.template.controller.MainController;
import com.template.service.BandaService;
import com.template.service.IBandaService;
import com.template.validator.BandaValidator;
import com.template.validator.IBandaValidator;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application
{
    @Override
    public void start(Stage stage) throws Exception
    {
        IBandaService bandaService = new BandaService();

        FXMLLoader loader = new FXMLLoader(Main.class.getResource("/com/template/Main.fxml"));
        loader.setControllerFactory(controllerClass -> {
            if(controllerClass == MainController.class){
                return new MainController(bandaService );
            }
            try{
                return  controllerClass.newInstance();
            }catch(Exception e){
                throw new RuntimeException(e);
            }
        });

        Parent root = loader.load();
        Scene scene = new Scene(root, 1720,980);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args)
    {
        launch();
    }
}
