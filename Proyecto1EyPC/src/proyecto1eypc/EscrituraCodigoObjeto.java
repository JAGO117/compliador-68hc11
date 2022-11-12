package proyecto1eypc;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class EscrituraCodigoObjeto {

    static void escribeOpCodeHTML(String codigo, String operandos, File f) {
        
        int contador;

        String localidad;
        contador = 0;
        

        try {
            BufferedReader br = new BufferedReader(new FileReader(f));
            PrintWriter pw = new PrintWriter(new FileWriter(f, true));
            String strline = null;
            //pw.write("<8000>");
            String tmp;
            while ((tmp = br.readLine()) != null) { //AQUI OBTENEMOS LA ULTIMA LINEA DEL ARCHIVO 
                strline = tmp;
            }

            Scanner sc1 = new Scanner(strline).useDelimiter("<span style=\"color:#FF0000\";>|<p> </p>|</span>|<span style=\"color:#0000FF\";>");
            String aux = new String();
            while (sc1.hasNext()) {
                aux = aux + sc1.next();
            }
            System.out.println(aux);
            String ultLin = aux;
            localidad = strline.substring(1, 5);//OBTENEMOS LA ULTIMA LOCALIDAD DE MEMORIA 
            int localidad2 = Integer.parseInt(localidad) + 10; //SUMAMOS UNO A NUESTRA LOCALIDAD PARA A�ADIRLA CUANDDO HAYA SALTO DE LINEA
            String l2 = String.valueOf(localidad2); //LA PASAMOS A STRING PARA PODER ESCRIBIRLA
            contador = ultLin.length(); //OBTENEMOS LA LONGITUD DE LA ULTIMA LINEA
            if (ultLin.length() == 54) { //54 CONTANDO ESPACIOS, ENTONCES DEBOS SALTAR A LA SIGUIENTE LINEA
                pw.append("<p> </p>");
                pw.write(System.getProperty("line.separator"));

                pw.append("<" + l2 + ">");
                contador = 0;
            }
            if(codigo==null){
            }else if (codigo.length() == 5) { //PARA TENER TODO AGRUPADO DE DOS EN DOS
                String uno = codigo.substring(0, 2);
                String dos = codigo.substring(3, 5);
                pw.append("<span style=\"color:#FF0000\";>");
                pw.append(" " + uno);
                pw.append("</span>");
                contador = contador + 3;
                if (contador >= 54) { //VERIFICAMOS SI ESTAMOS AUN DENTRO DEL RANGO
                    pw.append("<p> </p>");
                    pw.write(System.getProperty("line.separator"));
                    pw.append("<" + l2 + ">");//A�ADIMOS AL INICIO DE LA LINEA LA LOCALIDAD
                    contador = 0;
                }
                pw.append("<span style=\"color:#FF0000\";>");
                pw.append(" " + dos);
                pw.append("</span>");

                contador = contador + 3;
                if (contador >= 54) {
                    pw.append("<p> </p>");
                    pw.write(System.getProperty("line.separator"));
                    pw.append("<" + l2 + ">");
                    contador = 0;
                }
            }else if(codigo.length()<2){
            } else {
                pw.append("<span style=\"color:#FF0000\";>");
                pw.append(" " + codigo);
                pw.append("</span>");
                contador = contador + 3;
                if (contador >= 54) {
                    pw.append("<p> </p>");
                    pw.write(System.getProperty("line.separator"));
                    pw.append("<" + l2 + ">");
                    contador = 0;
                }
            }
            if(operandos==null){
            }else if (operandos.length() == 6) {//PARA TENER TODO AGRUPADO DE DOS EN DOS
                String s1 = operandos.substring(0, 2);
                String s2 = operandos.substring(2, 4);
                String s3 = operandos.substring(4, 6);
                pw.append("<span style=\"color:#0000FF\";>");
                pw.append(" " + s1);
                pw.append("</span>");
                contador = contador + 3;
                if (contador >= 54) {
                    pw.append("<p> </p>");
                    pw.write(System.getProperty("line.separator"));
                    pw.append("<" + l2 + ">");
                    contador = 0;
                }
                pw.append("<span style=\"color:#0000FF\";>");
                pw.append(" " + s2);
                pw.append("</span>");
                contador = contador + 3;
                if (contador >= 54) {
                    pw.append("<p> </p>");
                    pw.write(System.getProperty("line.separator"));
                    pw.append("<" + l2 + ">");
                    contador = 0;
                }
                 pw.append("<span style=\"color:#0000FF\";>");
                pw.append(" " + s3);
                pw.append("</span>");
                contador = contador + 3;
                if (contador >= 54) {
                    pw.append("<p> </p>");
                    pw.write(System.getProperty("line.separator"));
                    pw.append("<" + l2 + ">");
                    contador = 0;
                }
            }else if (operandos.length() == 4) {//PARA TENER TODO AGRUPADO DE DOS EN DOS
                String s1 = operandos.substring(0, 2);
                String s2 = operandos.substring(2, 4);
                pw.append("<span style=\"color:#0000FF\";>");
                pw.append(" " + s1);
                pw.append("</span>");
                contador = contador + 3;
                if (contador >= 54) {
                    pw.append("<p> </p>");
                    pw.write(System.getProperty("line.separator"));
                    pw.append("<" + l2 + ">");
                    contador = 0;
                }
                pw.append("<span style=\"color:#0000FF\";>");
                pw.append(" " + s2);
                pw.append("</span>");
                contador = contador + 3;
                if (contador >= 54) {
                    pw.append("<p> </p>");
                    pw.write(System.getProperty("line.separator"));
                    pw.append("<" + l2 + ">");
                    contador = 0;
                }
            }else if(operandos.length()<2){
            } else {
                pw.append("<span style=\"color:#0000FF\";>");
                pw.append(" " + operandos);
                pw.append("</span>");
                contador = contador + 3;
                if (contador >= 54) {
                    pw.append("<p> </p>");
                    pw.write(System.getProperty("line.separator"));
                    pw.append("<" + l2 + ">");
                    contador = 0;
                }
            }

            pw.close();
            br.close();

        } catch (IOException e) {
        };
    }
    static void escribeOpCodeHTMLUtil(ArrayList<ArrayList<String>> opCodeAEscribir,String rutaArchivo,String nombreSinExtension) throws IOException {
        File f = new File(rutaArchivo+nombreSinExtension+".html");
        
        
        PrintWriter pw = new PrintWriter(new FileWriter(f));
        pw.write("<8000>");
        pw.close();
        
        for(ArrayList<String> lineaPorEscribir:opCodeAEscribir){
                escribeOpCodeHTML(lineaPorEscribir.get(0),lineaPorEscribir.get(1),f);
          }
    }
    
    static void escribeOpCode(String codigo, String operandos, File f) {
        
        int contador;

        String localidad;
        contador = 0;
        

        try {
            BufferedReader br = new BufferedReader(new FileReader(f));
            PrintWriter pw = new PrintWriter(new FileWriter(f, true));
            String strline = null;
            //pw.write("<8000>");
            String tmp;
            while ((tmp = br.readLine()) != null) { //AQUI OBTENEMOS LA ULTIMA LINEA DEL ARCHIVO 
                strline = tmp;
            }

            Scanner sc1 = new Scanner(strline).useDelimiter("<span style=\"color:#FF0000\";>|<p> </p>|</span>|<span style=\"color:#0000FF\";>");
            String aux = new String();
            while (sc1.hasNext()) {
                aux = aux + sc1.next();
            }
            System.out.println(aux);
            String ultLin = aux;
            localidad = strline.substring(1, 5);//OBTENEMOS LA ULTIMA LOCALIDAD DE MEMORIA 
            int localidad2 = Integer.parseInt(localidad) + 10; //SUMAMOS UNO A NUESTRA LOCALIDAD PARA A�ADIRLA CUANDDO HAYA SALTO DE LINEA
            String l2 = String.valueOf(localidad2); //LA PASAMOS A STRING PARA PODER ESCRIBIRLA
            contador = ultLin.length(); //OBTENEMOS LA LONGITUD DE LA ULTIMA LINEA
            if (ultLin.length() == 54) { //54 CONTANDO ESPACIOS, ENTONCES DEBOS SALTAR A LA SIGUIENTE LINEA
                pw.write(System.getProperty("line.separator"));

                pw.append("<" + l2 + ">");
                contador = 0;
            }
            if(codigo==null){
            }else if (codigo.length() == 5) { //PARA TENER TODO AGRUPADO DE DOS EN DOS
                String uno = codigo.substring(0, 2);
                String dos = codigo.substring(3, 5);
                pw.append(" " + uno);
                contador = contador + 3;
                if (contador >= 54) { //VERIFICAMOS SI ESTAMOS AUN DENTRO DEL RANGO
                    pw.write(System.getProperty("line.separator"));
                    pw.append("<" + l2 + ">");//A�ADIMOS AL INICIO DE LA LINEA LA LOCALIDAD
                    contador = 0;
                }
                pw.append(" " + dos);

                contador = contador + 3;
                if (contador >= 54) {
                    pw.write(System.getProperty("line.separator"));
                    pw.append("<" + l2 + ">");
                    contador = 0;
                }
            } else if(codigo.length()<2){
            }else{
                pw.append(" " + codigo);
                contador = contador + 3;
                if (contador >= 54) {
                    pw.write(System.getProperty("line.separator"));
                    pw.append("<" + l2 + ">");
                    contador = 0;
                }
            }
            if(operandos==null){
            }else if (operandos.length() == 6) {//PARA TENER TODO AGRUPADO DE DOS EN DOS
                String s1 = operandos.substring(0, 2);
                String s2 = operandos.substring(2, 4);
                String s3 = operandos.substring(4, 6);
                pw.append(" " + s1);
                contador = contador + 3;
                if (contador >= 54) {
                    pw.write(System.getProperty("line.separator"));
                    pw.append("<" + l2 + ">");
                    contador = 0;
                }
                pw.append(" " + s2);
                contador = contador + 3;
                if (contador >= 54) {
                    pw.write(System.getProperty("line.separator"));
                    pw.append("<" + l2 + ">");
                    contador = 0;
                }
                pw.append(" " + s3);
                contador = contador + 3;
                if (contador >= 54) {
                    pw.write(System.getProperty("line.separator"));
                    pw.append("<" + l2 + ">");
                    contador = 0;
                }
            }else if (operandos.length() == 4) {//PARA TENER TODO AGRUPADO DE DOS EN DOS
                String s1 = operandos.substring(0, 2);
                String s2 = operandos.substring(2, 4);
                pw.append(" " + s1);
                contador = contador + 3;
                if (contador >= 54) {
                    pw.write(System.getProperty("line.separator"));
                    pw.append("<" + l2 + ">");
                    contador = 0;
                }
                pw.append(" " + s2);
                contador = contador + 3;
                if (contador >= 54) {
                    pw.write(System.getProperty("line.separator"));
                    pw.append("<" + l2 + ">");
                    contador = 0;
                }
            } else if(operandos.length()<2){
            }else{
                pw.append(" " + operandos);
                contador = contador + 3;
                if (contador >= 54) {
                    pw.write(System.getProperty("line.separator"));
                    pw.append("<" + l2 + ">");
                    contador = 0;
                }
            }

            pw.close();
            br.close();

        } catch (IOException e) {
        };
    }
    static void escribeOpCodeUtil(ArrayList<ArrayList<String>> opCodeAEscribir,String rutaArchivo,String nombreSinExtension) throws IOException {
        File f = new File(rutaArchivo+nombreSinExtension+".hex");
        
        
        PrintWriter pw = new PrintWriter(new FileWriter(f));
        pw.write("<8000>");
        pw.close();
        
        for(ArrayList<String> lineaPorEscribir:opCodeAEscribir){
                escribeOpCode(lineaPorEscribir.get(0),lineaPorEscribir.get(1),f);
          }
    }
    
}
