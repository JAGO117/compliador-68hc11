/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyecto1eypc;


import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.stage.Stage;

//clase inicial del proyecto, aquella que se encarga de lanzar la interfaz grafica
public class Proyecto1EyPC extends Application{
   
    @Override
    public void start (Stage primaryStage) {
    	new MenuPrincipal();
    }
    
    public static void main(String[] args){
        
	launch(args);
    }
 }
