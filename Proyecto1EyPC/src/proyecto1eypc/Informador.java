/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyecto1eypc;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.control.Label;
import javafx.scene.control.Button;

//clase que apartir de un texto recibido genera una ventana con un mensaje
// y contiene un boton para regresar a la ventana anterior
public class Informador extends Stage {
	
	private Label tituloLbl;
	private Button regresarBtn;

	public Informador(String cadena){
		GridPane grid = new GridPane();
                grid.setAlignment(Pos.CENTER);
                grid.setHgap(2);
                grid.setVgap(2);
                this.tituloLbl = new Label(cadena);
                
                //creacion del boton regresar
                this.regresarBtn = new Button("Regresar");
                this.regresarBtn.setOnAction(new EventHandler<ActionEvent>(){
                        @Override
                        public void handle(ActionEvent event){
                                close();
                        }
                });
                
                //se aniaden los elementos al grid
		grid.add(this.tituloLbl,0,1);
		grid.add(this.regresarBtn,0,10);
                GridPane.setHalignment(this.regresarBtn, HPos.RIGHT);
                //parametros para la escena y proceso de dibujo de la escena
	 	Scene sceneGrid = new Scene(grid, 300, 125);
                this.setTitle("Informador");
                this.setScene(sceneGrid);
                this.initModality(Modality.APPLICATION_MODAL);
                this.show();	

	}
}
