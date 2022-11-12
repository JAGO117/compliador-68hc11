/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyecto1eypc;

import java.awt.FlowLayout;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.WindowConstants;

//clase que se encarga de la interfaz grafica de la ventana principal del programa
//en esta ventana se introduce la funcion y se muestra el titulo del programa asi como
//el nombre de los integrantes del equipo
// contiene botones para continuar con el programa asi como para ver las instrucciones
public class MenuPrincipal extends Stage {

    private Button compilarBtn;
    private Button examinarBtn;
    private Label agregarLbl;
    private TextField agregarTxt;
    ArrayList<String> archivo;
    
    public MenuPrincipal() {
        
        System.out.println("ëstoy corriendo");
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);

        Text encabezado1 = new Text("Estructura y programacion de computadoras\nProyecto: Compilador");
        encabezado1.setFont(Font.font("Tahoma", FontWeight.SEMI_BOLD, 20));
        encabezado1.setTextAlignment(TextAlignment.CENTER);

        Text encabezado2 = new Text("Presentado por:\nDiaz Garcia Porfirio\nGonzalez Ochoa Jose Antonio\nPeralta Correa Jose Roberto");
        encabezado2.setFont(Font.font("Courier New", FontWeight.NORMAL, 14));
        //encabezado2.setTextAlignment(TextAlignment.CENTER);

        //datos del formulario
        this.agregarLbl = new Label("Archivo a compilar:");
        this.agregarLbl.setAlignment(Pos.CENTER_RIGHT);
        this.agregarTxt = new TextField();
        
        agregarTxt.setEditable(false);
        
        //botones para instrucciones y siguiente, el boton siguiente toma el texto introducido
        //y lo manda a la clase MenuGrafico para la impresion de resultados
        this.examinarBtn = new Button("Examinar");
        this.examinarBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
               
                archivo = elegirArchivo();
               
                agregarTxt.setText(archivo.get(0)+archivo.get(1));
            }
        });
        this.compilarBtn = new Button("Compilar");
        this.compilarBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (agregarTxt.getText().equals("")|| archivo.isEmpty()) {
                    new Informador("Necesita elegir un archivo");
                } else {
                    try {
                        Principal.iniciar(archivo.get(0), archivo.get(1));
                    } catch (FileNotFoundException ex) {
                        Logger.getLogger(MenuPrincipal.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (IOException ex) {
                        Logger.getLogger(MenuPrincipal.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        });
        this.compilarBtn.setAlignment(Pos.CENTER_RIGHT);

        //se aniaden los elementos creados al grid
        grid.add(encabezado1, 0, 0, 2, 1);
        grid.add(encabezado2, 0, 1, 2, 1);
        grid.add(this.agregarLbl, 0, 3);
        grid.add(this.agregarTxt, 1, 3);
        grid.add(this.examinarBtn, 0, 4);
        grid.add(this.compilarBtn, 1, 4);

        //para que el boton se alinie a la derecha
        GridPane.setHalignment(this.compilarBtn, HPos.RIGHT);

        //creacion de la escena
        Scene sceneGrid = new Scene(grid, 600, 500);

        this.setTitle("Compilador");
        this.setScene(sceneGrid);
        this.initModality(Modality.NONE);
        this.show();
        
    }

    public static ArrayList<String> elegirArchivo() {
        
        ArrayList<String> archivo = new ArrayList<>();
        JFrame frame = new JFrame("Centered");
        
        String filename = File.separator + "tmp";
        JFileChooser fileChooser = new JFileChooser(new File(filename));

        // abre el dialogo del file chooser
        fileChooser.showOpenDialog(frame);
        
        //guarda la ruta y el nombre del archivo por separado en la cadena
        archivo.add(fileChooser.getSelectedFile().getParent()+"\\");
        archivo.add(fileChooser.getSelectedFile().getName());
        
        System.out.println("archivo: "+archivo);
        return archivo;
    }
}
