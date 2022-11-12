/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyecto1eypc;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import org.apache.commons.io.FilenameUtils;

/**
 *
 * @author dell
 */
public class Principal {

    static void iniciar(String rutaArchivo,String nombreArchivo) throws FileNotFoundException, IOException {
        String nombreSinExtension = FilenameUtils.removeExtension(nombreArchivo);
        
        ArrayList<String> inherente = ManejoArchivosExcel.obtenerMnemonicos("68hc11inherente.xlsx");
        ArrayList<String> relativo = ManejoArchivosExcel.obtenerMnemonicos("68hc11relativo.xlsx");
        ArrayList<String> excepcional = ManejoArchivosExcel.obtenerMnemonicos("68hc11Excepcional.xlsx");
        ArrayList<String> comparte = ManejoArchivosExcel.obtenerMnemonicos("68hc11compartido.xlsx");

        ArrayList<ArrayList<String>> constantes = new ArrayList<>();
        
        ArrayList<ArrayList<String>> opCodeAEscribir = new ArrayList<>();
        ArrayList<ArrayList<String>> porCalcular = new ArrayList<>();
        ArrayList<ArrayList<String>> etiquetas = new ArrayList<>();
        
        ArrayList<ArrayList<String>> errores = new ArrayList<>();
        
        ArrayList<String> lineasFuente = new ArrayList<>();
        ArrayList<String> direcciones = new ArrayList<>();
        ArrayList<ArrayList<String>> opCodeParaLST = new ArrayList<>();
        
        
        Scanner scp = new Scanner(new File(rutaArchivo+nombreArchivo));
        int i = 0;
        //scp.nextLine();
        String direccionActual=null;
        boolean end=false;
        while (scp.hasNextLine()&&end==false) {
            try{
                boolean orgPrevio=false;

                String linea = scp.nextLine();
                Scanner sc1 = new Scanner(linea).useDelimiter("\t| ");
                sc1.useDelimiter("");
                ArrayList<String> lineaOpCodeParaLST = new ArrayList<>();

                lineasFuente.add(linea);
                System.out.println("Se leeyo la linea: " + linea);

                i++;
                System.out.println("Leyendo linea: " + i);
                //System.out.println(sc1.hasNext("\t"));
                //sc1.useDelimiter("\\s+");
                if (sc1.hasNext("\\s+")) {
                    sc1.useDelimiter("\\s+");
                    if(sc1.hasNext("\\*.*")){
                        System.out.println("Es comentario separado del borde");
                        lineaOpCodeParaLST.add(null);
                        lineaOpCodeParaLST.add(null);
                        lineaOpCodeParaLST.add(null);

                    }
                    //sc1.next("\t");
                }else if(sc1.hasNext("\\s*\\*.*")|!sc1.hasNext()){
                    //es comentario
                    System.out.println("Es comentario");
                    lineaOpCodeParaLST.add(null);
                    lineaOpCodeParaLST.add(null);
                    lineaOpCodeParaLST.add(null);
                }else {
                    System.out.println("Constante variable o etiqueta");
                    sc1.useDelimiter("\\s+");
                    //constante variable o etiqueta
                    ArrayList<String> listaAux = new ArrayList<>();
                    listaAux.add(sc1.next());
                    
                    if(inherente.contains(listaAux.get(0).toLowerCase())|relativo.contains(listaAux.get(0).toLowerCase())|excepcional.contains(listaAux.get(0).toLowerCase())|comparte.contains(listaAux.get(0).toLowerCase())|listaAux.get(0).equalsIgnoreCase("EQU")|listaAux.get(0).equalsIgnoreCase("FCB")|listaAux.get(0).equalsIgnoreCase("END")|listaAux.get(0).equalsIgnoreCase("ORG"))
                    {
                        lineaOpCodeParaLST.add(0,null);
                        lineaOpCodeParaLST.add(1,null);
                        lineaOpCodeParaLST.add(2,null);
                        opCodeParaLST.add(lineaOpCodeParaLST);
                        throw new ErrorDeCompilacionException("009","INSTRUCCION CARECE DE AL MENOS UN ESPACIO RELATIVO AL MARGEN",Integer.toString(i));
                        
                    }
                    sc1.useDelimiter("\\s+|\\s*\\*.*");
                    System.out.println("lei: "+listaAux);
                    System.out.println("antes del equ");
                    if (sc1.hasNext("EQU")||sc1.hasNext("equ")||sc1.hasNext("Equ")) {
                        System.out.println("despues del equ");
                                //es constante o variable
                        System.out.println("Es constante o variable");
                        //sc1.next("\t");
                        sc1.next();
                        sc1.useDelimiter("\\d|\\s+|\\s*\\*.*");
                        sc1.next("\\$");
                        sc1.useDelimiter("\\s+|\\s*\\*.*");

                        String valorConstante=sc1.next();
                        listaAux.add(valorConstante);
                        constantes.add(listaAux);

                        lineaOpCodeParaLST.add(null);
                        lineaOpCodeParaLST.add(valorConstante);
                        lineaOpCodeParaLST.add(null);
                        System.out.println("lista aux: " + listaAux + " tiene sig: " + sc1.hasNext());
                    }if (sc1.hasNext("FCB")||sc1.hasNext("fcb")||sc1.hasNext("Fcb")) {
                        System.out.println("despues del fcb");
                                //es fcb
                        sc1.next();

                        sc1.useDelimiter("\\s+|\\s*\\*.*");
                        String parametros=new String();
                        String parametrosTemporal=sc1.next();
                        Scanner scex = new Scanner(parametrosTemporal).useDelimiter("\\$|\\#|,");
                        while(scex.hasNext()){
                            parametros=parametros+scex.next();
                        }

                        lineaOpCodeParaLST.add(null);
                        lineaOpCodeParaLST.add(parametros);
                        lineaOpCodeParaLST.add(direccionActual);
                    } else {
                        //es etiqueta al inicio
                        System.out.println("Es etiqueta al inicio");
                        listaAux.add(direccionActual);
                        etiquetas.add(listaAux);

                        if(sc1.hasNext()){
                            //es etiqueta junto a instruccion dif de EQU y FCB
                        }else{
                            //solo etiqueta o etiqueta con comentario
                            lineaOpCodeParaLST.add(null);
                            lineaOpCodeParaLST.add(null);
                            lineaOpCodeParaLST.add(direccionActual);
                        }

                    }

                }


                if (sc1.hasNext()&&!sc1.hasNext("\\s*\\*.*")) {
                    sc1.useDelimiter("\\s+|\\s*\\*.*");
                    String mnemo = sc1.next();

                    if(mnemo.equalsIgnoreCase("END")){
                        end=true;
                        lineaOpCodeParaLST.add("END");
                        lineaOpCodeParaLST.add(null);
                        lineaOpCodeParaLST.add(null);
                    }else if(mnemo.equalsIgnoreCase("ORG")){
                        lineaOpCodeParaLST.add(0,null);
                        lineaOpCodeParaLST.add(1,null);
                        lineaOpCodeParaLST.add(2,null);
                        opCodeParaLST.add(lineaOpCodeParaLST);
                        sc1.useDelimiter("\\#|\\s+|\\s*\\*.*");

                        String parametros;
                        if (sc1.hasNext("\\$.*")) {
                            sc1.useDelimiter("\\d|\\s+|\\s*\\*.*");
                            sc1.next("\\$");
                            sc1.useDelimiter("\\s+|\\s*\\*.*");
                            parametros = sc1.next();
                        } else if(sc1.hasNext()) {
                            parametros = sc1.next();
                            boolean existe=false;
                            for (ArrayList<String> listaAux : constantes) {
                                if (listaAux.contains(parametros)) {
                                    parametros = listaAux.get(1);
                                    existe=true;
                                    break;
                                }
                            }
                            if (!existe){
                                lineaOpCodeParaLST.add(0,null);
                                lineaOpCodeParaLST.add(1,null);
                                lineaOpCodeParaLST.add(2,null);
                                opCodeParaLST.add(lineaOpCodeParaLST);
                                throw new ErrorDeCompilacionException("001","CONSTANTE INEXISTENTE",Integer.toString(i));
                            }
                            //es constante o var

                        }else{
                            System.out.println("error carece de operandos");
                            lineaOpCodeParaLST.add(0,null);
                            lineaOpCodeParaLST.add(1,null);
                            lineaOpCodeParaLST.add(2,null);
                            opCodeParaLST.add(lineaOpCodeParaLST);
                            throw new ErrorDeCompilacionException("005","INSTRUCCION CARECE DE OPERANDOS",Integer.toString(i));
                        }
                        System.out.println("Es ORG");        
                        lineaOpCodeParaLST.add(null);
                        lineaOpCodeParaLST.add(parametros);
                        lineaOpCodeParaLST.add(null);

                        direccionActual=parametros.toUpperCase();



                    }else if (mnemo.equalsIgnoreCase("FCB")) {
                        System.out.println("es fcb");
                                //es fcb

                        sc1.useDelimiter("\\s+|\\s*\\*.*");
                        String parametros=new String();
                        if(sc1.hasNext()){
                            String parametrosTemporal=sc1.next();
                            Scanner scex = new Scanner(parametrosTemporal).useDelimiter("\\$|\\#|,");
                            while(scex.hasNext()){
                                parametros=parametros+scex.next();
                            }

                            ArrayList<String> lineaTemp = new ArrayList<>();
                            lineaTemp.add(null);
                            lineaTemp.add(parametros);
                            opCodeAEscribir.add(lineaTemp);

                            lineaOpCodeParaLST.add(null);
                            lineaOpCodeParaLST.add(parametros);
                            lineaOpCodeParaLST.add(direccionActual);

                            int decimal = Integer.parseInt(direccionActual, 16);
                            decimal=decimal+parametros.length()/2;
                            direccionActual= Integer.toHexString(decimal).toUpperCase();
                        }else{
                            System.out.println("error carece de operandos");
                            lineaOpCodeParaLST.add(0,null);
                            lineaOpCodeParaLST.add(1,null);
                            lineaOpCodeParaLST.add(2,null);
                            opCodeParaLST.add(lineaOpCodeParaLST);
                            throw new ErrorDeCompilacionException("005","INSTRUCCION CARECE DE OPERANDOS",Integer.toString(i));
                        }
                    }else if(excepcional.contains(mnemo.toLowerCase())){
                        //es excepcional
                        System.out.println("Es excepcional");
                        sc1.useDelimiter("\\s+|\\s*\\*.*");
                        String parametros=new String();
                        if(sc1.hasNext()){
                            String parametrosTemporal=sc1.next();
                            Scanner scex = new Scanner(parametrosTemporal).useDelimiter("\\$|\\#|,");
                            while(scex.hasNext()){
                                parametros=parametros+scex.next();
                            }
                            System.out.println("Los parametros excepcionales leidos son:"+parametros);
                            ArrayList<String> opcode = obtenerOpcodeExcepcional(mnemo.toLowerCase(), parametros);

                            String parametrosSinIndexado=new String();
                            scex = new Scanner(parametros).useDelimiter("x|X|y|Y");
                            while(scex.hasNext()){
                                parametrosSinIndexado=parametrosSinIndexado+scex.next();
                            }

                            System.out.println("Los parametros sin indexado leidos son:"+parametrosSinIndexado);

                            ArrayList<String> lineaTemp = new ArrayList<>();
                            lineaTemp.add(opcode.get(0));
                            lineaTemp.add(parametrosSinIndexado);

                            if(mnemo.equalsIgnoreCase("BSET")|mnemo.equalsIgnoreCase("BCLR")){
                                System.out.println("Es BSET o BCLR (sin etiqueta)");
                                lineaOpCodeParaLST.add(opcode.get(0));
                                lineaOpCodeParaLST.add(parametrosSinIndexado);
                                lineaOpCodeParaLST.add(direccionActual);

                                int decimal = Integer.parseInt(direccionActual, 16);
                                decimal=decimal+opcode.get(0).length()/2+parametrosSinIndexado.length()/2;
                                direccionActual= Integer.toHexString(decimal).toUpperCase();

                            }else{
                                System.out.println("Es excepcional con etiqueta");
                                String etiqueta=sc1.next();
                                System.out.println("El sig es:"+etiqueta+"asd");
                                lineaTemp.add(direccionActual);
                                lineaTemp.add(etiqueta);
                                lineaTemp.add(Integer.toString(i));
                                System.out.println("etiqueta detectada:"+etiqueta);
                                porCalcular.add(lineaTemp);
                                opCodeParaLST.add(lineaTemp);

                                int decimal = Integer.parseInt(direccionActual, 16);
                                decimal=decimal+opcode.get(0).length()/2+parametrosSinIndexado.length()/2+1;
                                direccionActual= Integer.toHexString(decimal).toUpperCase();
                            }

                            opCodeAEscribir.add(lineaTemp);




                            /*
                            Los de etiquetas
                            */
                            scex.close();
                        }else{
                            System.out.println("error carece de operandos");
                            lineaOpCodeParaLST.add(0,null);
                            lineaOpCodeParaLST.add(1,null);
                            lineaOpCodeParaLST.add(2,null);
                            opCodeParaLST.add(lineaOpCodeParaLST);
                            throw new ErrorDeCompilacionException("005","INSTRUCCION CARECE DE OPERANDOS",Integer.toString(i));
                        }
                    }else if (inherente.contains(mnemo.toLowerCase())) {
                        //es inherente
                        //System.out.println(sinCompartir);
                        //System.out.println("no comparte: " + inherente.contains(mnemo));
                        if(sc1.hasNext()){
                            System.out.println("error carece de operandos");
                            lineaOpCodeParaLST.add(0,null);
                            lineaOpCodeParaLST.add(1,null);
                            lineaOpCodeParaLST.add(2,null);
                            opCodeParaLST.add(lineaOpCodeParaLST);
                            throw new ErrorDeCompilacionException("006","INSTRUCCION NO LLEVA OPERANDOS",Integer.toString(i));
                        }
                        
                        ArrayList<String> opcode = obtenerOpcodeInherente(mnemo.toLowerCase());

                        ArrayList<String> lineaTemp = new ArrayList<>();
                        lineaTemp.add(opcode.get(0));
                        lineaTemp.add(null);
                        opCodeAEscribir.add(lineaTemp);

                        System.out.println("Es inherente");

                        lineaOpCodeParaLST.add(opcode.get(0));
                        lineaOpCodeParaLST.add(null);
                        lineaOpCodeParaLST.add(direccionActual);

                        /*
                            Aqui se hace la suma de hexadecimal a la direccion
                        */
                        int decimal = Integer.parseInt(direccionActual, 16);
                        decimal=decimal+opcode.get(0).length()/2;
                        direccionActual= Integer.toHexString(decimal).toUpperCase();

                    } else if (relativo.contains(mnemo.toLowerCase())) {
                        //es relativo
                        //System.out.println(sinCompartir);
                        //System.out.println("no comparte: " + relativo.contains(mnemo));
                        ArrayList<String> opcode = obtenerOpcodeRelativo(mnemo.toLowerCase());

                        sc1.useDelimiter("\\s+|\\s*\\*.*");
                        if(sc1.hasNext()){
                            String parametros = sc1.next();
                            System.out.println("el opcode es: "+opcode.get(0));
                            ArrayList<String> lineaTemp = new ArrayList<>();
                            lineaTemp.add(opcode.get(0));
                            String operandoPorCalcular=new String();
                            lineaTemp.add(null);
                            /*////////////////////////////
                                Aqui falta leer la etiqueta y guardarla; idea: guardarla en la lineaTemp como otro elemento del arreglo
                            */////////////////////////////

                            System.out.println("Es relativo");

                            lineaTemp.add(direccionActual);
                            lineaTemp.add(parametros);
                            lineaTemp.add(Integer.toString(i));
                            
                            opCodeAEscribir.add(lineaTemp);
                            porCalcular.add(lineaTemp);

                            
                            opCodeParaLST.add(lineaTemp);
                            //lineaOpCodeParaLST.add(opcode.get(0));
                            //lineaOpCodeParaLST.add(lineaTemp.get(1));
                            //lineaOpCodeParaLST.add(direccionActual);

                            /*
                                Aqui se hace la suma de hexadecimal a la direccion
                            */
                            int decimal = Integer.parseInt(direccionActual, 16);
                            decimal=decimal+opcode.get(0).length()/2+1;
                            direccionActual= Integer.toHexString(decimal).toUpperCase();
                        }else{
                            System.out.println("error carece de operandos");
                            lineaOpCodeParaLST.add(0,null);
                            lineaOpCodeParaLST.add(1,null);
                            lineaOpCodeParaLST.add(2,null);
                            opCodeParaLST.add(lineaOpCodeParaLST);
                            throw new ErrorDeCompilacionException("005","INSTRUCCION CARECE DE OPERANDOS",Integer.toString(i));
                        }
                        
                    } else if (comparte.contains(mnemo.toLowerCase())) {
                        //System.out.println(comparte);
                        //System.out.println("Comparte : " + comparte.contains(mnemo));
                        //sc1.next("\t");
                        sc1.useDelimiter("\\s+|\\s*\\*.*");
                        if(sc1.hasNext()){
                            sc1.useDelimiter("\\w|\\$.*|\\s+|[a-z]|\\s*\\*.*");
                            boolean gato;
                            if (sc1.hasNext("#.*")) {
                                sc1.next("#");
                                gato = true;
                            } else {
                                gato = false;
                            }

                            String parametros;

                            sc1.useDelimiter("\\#|\\s+|\\s*\\*.*");
                            //System.out.println("mnemonico: " + mnemo + " gato: " + gato);

                            if (sc1.hasNext("\\$.*")) {
                                sc1.useDelimiter("\\w|\\d|\\s+|\\s*\\*.*");

                                sc1.next("\\$");
                                sc1.useDelimiter("\\s+|\\s*\\*.*");
                                parametros = sc1.next();
                            } else {
                                parametros = sc1.next();
                                boolean existe=false;
                                for (ArrayList<String> listaAux : constantes) {
                                    if (listaAux.contains(parametros)) {
                                        parametros = listaAux.get(1);
                                        existe=true;
                                        break;
                                    }
                                   
                                }
                                if (!existe){
                                    lineaOpCodeParaLST.add(0,null);
                                    lineaOpCodeParaLST.add(1,null);
                                    lineaOpCodeParaLST.add(2,null);
                                    opCodeParaLST.add(lineaOpCodeParaLST);
                                     throw new ErrorDeCompilacionException("001","CONSTANTE INEXISTENTE",Integer.toString(i));
                                }
                                //es constante o var

                            }

                            //System.out.println("mnemonico: " + mnemo + " gato: " + gato + " parametros: " + parametros);

                            int clasifica = ManejoArchivosExcel.clasifica(gato, parametros);
                            //System.out.println("clasificado en:" + clasifica);
                            //System.out.println("opcode= "+obtenerOpcodeCompartido(clasifica,mnemo,parametros));
                            ArrayList<String> opcode;
                            /* PARA OPTIMIZACION*/

                            if(parametros.length()==4 && parametros.substring(0,2).contentEquals("00") && clasifica==11){
                                String newPara = parametros.substring(2);
                                opcode = obtenerOpcodeCompartido(5, mnemo.toLowerCase(), newPara);
                                if(opcode.get(0).contains("--")){
                                    opcode = obtenerOpcodeCompartido(clasifica, mnemo.toLowerCase(), parametros);
                                }else{
                                    parametros=newPara;
                                }
                            }else{
                                if(parametros.length()==4 && parametros.substring(0,2).contentEquals("00") && clasifica==3){
                                    parametros = parametros.substring(2);
                                }
                                opcode = obtenerOpcodeCompartido(clasifica, mnemo.toLowerCase(), parametros);
                            }


                            ArrayList<String> lineaTemp = new ArrayList<>();
                            lineaTemp.add(opcode.get(0));
                            lineaTemp.add(parametros);
                            System.out.println("opcode:"+opcode.get(0)+"asd Parametros:"+parametros+"asd");
                            opCodeAEscribir.add(lineaTemp);

                            lineaOpCodeParaLST.add(opcode.get(0));
                            lineaOpCodeParaLST.add(parametros);
                            lineaOpCodeParaLST.add(direccionActual);

                            /*
                                Aqui se hace la suma de hexadecimal a la direccion
                            */
                            int decimal = Integer.parseInt(direccionActual, 16);
                            decimal=decimal+opcode.get(0).length()/2+parametros.length()/2;
                            direccionActual= Integer.toHexString(decimal).toUpperCase();
                            //Archivos.escribeOpCode(opcode.get(0), parametros);
                        }else{
                            System.out.println("error carece de operandos");
                            lineaOpCodeParaLST.add(0,null);
                            lineaOpCodeParaLST.add(1,null);
                            lineaOpCodeParaLST.add(2,null);
                            opCodeParaLST.add(lineaOpCodeParaLST);
                            throw new ErrorDeCompilacionException("005","INSTRUCCION CARECE DE OPERANDOS",Integer.toString(i));
                        }

                    } else {
                        //mnemonico inexistente
                        System.out.println("error mnemonico inexistente");
                        lineaOpCodeParaLST.add(0,null);
                        lineaOpCodeParaLST.add(1,null);
                        lineaOpCodeParaLST.add(2,null);
                        opCodeParaLST.add(lineaOpCodeParaLST);
                        throw new ErrorDeCompilacionException("004","MNEMONICO INEXISTENTE",Integer.toString(i));
                    }



                }
                if(!lineaOpCodeParaLST.isEmpty())
                    System.out.println("La linea es: instruccion:"+lineaOpCodeParaLST.get(0)+" operando:"+lineaOpCodeParaLST.get(1));
                if(!lineaOpCodeParaLST.isEmpty())
                    opCodeParaLST.add(lineaOpCodeParaLST);

            }catch(ErrorDeCompilacionException e){
                ArrayList<String> temporal= new ArrayList<>();
                temporal.add(e.numero);
                temporal.add(e.descripcion);
                temporal.add(e.linea);
                errores.add(temporal);
                System.out.println(e.descripcion);
            }
        }
        
        if(end==false){
            //ERROR NO SE ENCUENTRA END.
            ArrayList<String> temporal= new ArrayList<>();
            temporal.add("010");
            temporal.add("NO SE ENCUENTRA END");
            temporal.add(Integer.toString(i));
            errores.add(temporal);
            System.out.println("No se encuentra END");
        }
            
            calcularRelativos(etiquetas, porCalcular,errores);
            
            
        
            EscrituraCodigoObjeto.escribeOpCodeHTMLUtil(opCodeAEscribir, rutaArchivo, nombreSinExtension);
            EscrituraCodigoObjeto.escribeOpCodeUtil(opCodeAEscribir, rutaArchivo, nombreSinExtension);
            System.out.println("La primer linea para LST es:"+opCodeParaLST.get(0));
            LST.escribeLSTUtil(opCodeParaLST,lineasFuente , direcciones, rutaArchivo, nombreSinExtension,errores);
            S19.escribeS19Util(opCodeAEscribir,direcciones,rutaArchivo,nombreSinExtension, end);
            
            informar(errores);
    }

    static ArrayList<String> obtenerOpcodeCompartido(int clasificacion, String mnemonico, String parametros) {
        ArrayList<String> datos = new ArrayList<>();
        ArrayList<ArrayList<String>> tabla = ManejoArchivosExcel.ManejoArch(new File("68hc11compartido.xlsx"));
        
        String opcode = new String();
        String bytes = new String();
        
        for (ArrayList<String> temp : tabla) {
            if (temp.get(1).equalsIgnoreCase(mnemonico)) {
                opcode = temp.get(clasificacion - 1).split("\\.|$")[0];
                bytes = temp.get(clasificacion).split("\\.|$")[0];
                break;
            }

        }
            datos.add(opcode);
            datos.add(bytes);
        
        return datos;
    }
    static ArrayList<String> obtenerOpcodeInherente(String mnemonico) {
        ArrayList<String> datos = new ArrayList<>();
        ArrayList<ArrayList<String>> tabla = ManejoArchivosExcel.ManejoArch(new File("68hc11inherente.xlsx"));

        String opcode = new String();
        String bytes = new String();

        for (ArrayList<String> temp : tabla) {
            if (temp.get(1).equalsIgnoreCase(mnemonico)) {
                opcode = temp.get(2).split("\\.|$")[0];
                bytes = temp.get(3).split("\\.|$")[0];
                break;
            }

        }
        Scanner scinh= new Scanner(opcode).useDelimiter("\\s+");
        String opCodeEnFormato=new String();
        while(scinh.hasNext()){
            opCodeEnFormato=opCodeEnFormato+scinh.next();
        }
        datos.add(opCodeEnFormato);
        datos.add(bytes);

        return datos;
    }
    static ArrayList<String> obtenerOpcodeRelativo(String mnemonico) {
        ArrayList<String> datos = new ArrayList<>();
        ArrayList<ArrayList<String>> tabla = ManejoArchivosExcel.ManejoArch(new File("68hc11relativo.xlsx"));

        String opcode = new String();
        String bytes = new String();

        for (ArrayList<String> temp : tabla) {
            if (temp.get(1).equalsIgnoreCase(mnemonico)) {
                opcode = temp.get(2).split("\\.|$")[0];
                bytes = temp.get(3).split("\\.|$")[0];
                System.out.println("opcode encontrado: "+opcode);
                break;
            }

        }

        datos.add(opcode);
        datos.add(bytes);

        return datos;
    }
    
    static ArrayList<String> obtenerOpcodeExcepcional(String mnemonico, String parametros) {
        ArrayList<String> datos = new ArrayList<>();
        ArrayList<ArrayList<String>> tabla = ManejoArchivosExcel.ManejoArch(new File("68hc11Excepcional.xlsx"));

        String opcode = new String();
        String bytes = new String();
        int clasificacion;
        if(parametros.length()==5){ //SI ES INDEXADO  00X00
            if(parametros.contains("X")||parametros.contains("x")){
                clasificacion = 4;
            }
            else{
                clasificacion = 6;
            }
        }else{
            clasificacion = 2;
        }
        for (ArrayList<String> temp : tabla) {
            if (temp.get(1).equalsIgnoreCase(mnemonico)) {
                opcode = temp.get(clasificacion).split("\\.|$")[0];
                bytes = temp.get(clasificacion+1).split("\\.|$")[0];
                break;
            }

        }

        datos.add(opcode);
        datos.add(bytes);

        return datos;
    }
    
    static void calcularRelativos(ArrayList<ArrayList<String>> etiquetas, ArrayList<ArrayList<String>> porCalcular,ArrayList<ArrayList<String>> errores){
        for(ArrayList<String> instruccionAux:porCalcular){
            boolean existe=false;
            for(ArrayList<String> etiquetaAux:etiquetas){
                if(instruccionAux.get(3).equalsIgnoreCase(etiquetaAux.get(0))){
                    /*
                    Agregar error si el salto es demasiado grande
                    */
                    existe=true;
                    int decimal;
                    System.out.println("Las direccions son instruccion:"+instruccionAux.get(2)+" etiqueta: "+etiquetaAux.get(1));
                    
                    
                    if(Integer.parseInt(instruccionAux.get(2), 16)==Integer.parseInt(etiquetaAux.get(1), 16)){
                        decimal=0;
                    }else if(Integer.parseInt(instruccionAux.get(2), 16)<Integer.parseInt(etiquetaAux.get(1), 16)){
                        decimal = Integer.parseInt(etiquetaAux.get(1), 16)-Integer.parseInt(instruccionAux.get(2), 16);
                         
                    }else{
                        decimal = 255-(Integer.parseInt(instruccionAux.get(2), 16)-Integer.parseInt(etiquetaAux.get(1), 16))+1;
                    }
                    
                    if(Math.abs(Integer.parseInt(etiquetaAux.get(1), 16)-Integer.parseInt(instruccionAux.get(2), 16))>128){
                        instruccionAux.set(1, null);
                        ArrayList<String> temporal=new ArrayList<String>();
                        temporal.add("008");
                        temporal.add("SALTO RELATIVO MUY LEJANO");
                        temporal.add(instruccionAux.get(4));
                        errores.add(temporal);
                    }else if(instruccionAux.get(1)==null){
                        if(Integer.toHexString(decimal).toUpperCase().length()<2){
                             instruccionAux.set(1, "0"+Integer.toHexString(decimal).toUpperCase());
                         }else{
                            instruccionAux.set(1, Integer.toHexString(decimal).toUpperCase());
                        }
                    }else{
                        if(Integer.toHexString(decimal).toUpperCase().length()<2){
                             instruccionAux.set(1,instruccionAux.get(1)+ "0"+Integer.toHexString(decimal).toUpperCase());
                         }else{
                            instruccionAux.set(1,instruccionAux.get(1)+ Integer.toHexString(decimal).toUpperCase());
                        }
                    }
                    System.out.println("el decimal es:"+decimal+" el salto sera de:"+instruccionAux.get(1));
                    
                    break;
                }
                
            }
            if(!existe){
                instruccionAux.set(1, null);
                ArrayList<String> temporal=new ArrayList<String>();
                temporal.add("003");
                temporal.add("ETIQUETA INEXISTENTE");
                temporal.add(instruccionAux.get(4));
                errores.add(temporal);
            }
        }
    }
    static void informar(ArrayList<ArrayList<String>> errores) {
        if(errores.isEmpty()){
            new Informador("Se completo exitosamente la compilacion con un total de 0 errores");
        }else{
            String recuento="\n\tLinea\tNo.error\tDescripcion";
            for (ArrayList<String> error : errores) {
                recuento=recuento+"\n\t"+error.get(2)+"\t"+error.get(0)+"\t"+error.get(1);
            }
            
            
            new Informador("Se completo  la compilacion con un total de "+errores.size()+"errores:"+recuento);
        }
        

    }

}
