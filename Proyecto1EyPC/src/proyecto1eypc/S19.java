package proyecto1eypc;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class S19 {

    static void escribeS19(Boolean END, String codigo, String operandos, File f) {
        S19 a = new S19();
        int contador;
        String chsum;
        contador = 0;
        try {
            BufferedReader br = new BufferedReader(new FileReader(f));
            PrintWriter pw = new PrintWriter(new FileWriter(f, true));
            String strline = null;
            String tmp;
            int numeroLineas = 0;
            while ((tmp = br.readLine()) != null) { //AQUI OBTENEMOS LA ULTIMA LINEA DEL ARCHIVO 
                strline = tmp;
                numeroLineas++;
            }
            int tamanio = strline.length();
            if (numeroLineas == 1) {
                tamanio = 0;
            }
            numeroLineas--;
            Integer Localidad = 8000 + (numeroLineas * 10);
            String loc = String.valueOf(Localidad);

            contador = tamanio;
            if (tamanio == 40) {
                chsum = a.getCheckSum(strline.substring(2));
                pw.append(chsum);
                pw.write(System.getProperty("line.separator"));
                contador = 0;
            }
            if(END==false && codigo==null && operandos==null){
                chsum = a.getCheckSum(strline.substring(2));
                pw.append(chsum);
            }else if (END == true) {
                chsum = a.getCheckSum(strline.substring(2));
                pw.append(chsum);
                pw.write(System.getProperty("line.separator"));
                pw.append("S9030000FC");
                pw.write(System.getProperty("line.separator"));

            } else {
                if (contador == 0) {
                    pw.append("S113" + loc);
                    contador = contador + 8;
                }
                if(codigo==null){
                    
                }else if (codigo.length() == 5) { //PARA TENER TODO AGRUPADO DE DOS EN DOS
                    String uno = codigo.substring(0, 2);
                    String dos = codigo.substring(3, 5);
                    pw.append(uno);
                    strline = strline + uno;
                    contador = contador + 2;
                    if (contador >= 40) { //VERIFICAMOS SI ESTAMOS AUN DENTRO DEL RANGO
                        chsum = a.getCheckSum(strline.substring(2));
                        pw.append(chsum);
                        pw.write(System.getProperty("line.separator"));
                        contador = 0;
                    }
                    if (contador == 0) {
                        pw.append("S113" + loc);
                        contador = contador + 8;

                    }
                    pw.append(dos);
                    contador = contador + 2;
                    strline = strline + dos;

                    if (contador >= 40) {
                        chsum = a.getCheckSum(strline.substring(2));
                        pw.append(chsum);
                        pw.write(System.getProperty("line.separator"));
                        contador = 0;
                    }
                } else if(codigo.length()<2){
                } else {
                    pw.append(codigo);
                    contador = contador + 2;
                    strline = strline + codigo;

                    if (contador >= 40) {
                        chsum = a.getCheckSum(strline.substring(2));
                        pw.append(chsum);
                        pw.write(System.getProperty("line.separator"));
                        contador = 0;
                    }
                }
                if (contador == 0) {
                    pw.append("S113" + loc);
                    contador = contador + 8;

                }
                
                if(operandos==null){
                    
                }else if (operandos.length() == 6) {//PARA TENER TODO AGRUPADO DE DOS EN DOS
                    String s1 = operandos.substring(0, 2);
                    String s2 = operandos.substring(2, 4);
                    String s3 = operandos.substring(4, 6);
                    pw.append(s1);
                    contador = contador + 2;
                    strline = strline + s1;

                    if (contador >= 40) {
                        chsum = a.getCheckSum(strline.substring(2));
                        pw.append(chsum);
                        pw.write(System.getProperty("line.separator"));
                        contador = 0;
                    }
                    if (contador == 0) {
                        pw.append("S113" + loc);
                        contador = contador + 8;

                    }
                    pw.append(s2);
                    contador = contador + 2;
                    strline = strline + s2;

                    if (contador >= 40) {
                        chsum = a.getCheckSum(strline.substring(2));
                        pw.append(chsum);
                        pw.write(System.getProperty("line.separator"));
                        contador = 0;
                    }
                    if (contador == 0) {
                        pw.append("S113" + loc);
                        contador = contador + 8;

                    }
                    pw.append(s3);
                    contador = contador + 2;
                    strline = strline + s2;

                    if (contador >= 40) {
                        chsum = a.getCheckSum(strline.substring(2));
                        pw.append(chsum);
                        pw.write(System.getProperty("line.separator"));
                        contador = 0;
                    }
                }else if (operandos.length() == 4) {//PARA TENER TODO AGRUPADO DE DOS EN DOS
                    String s1 = operandos.substring(0, 2);
                    String s2 = operandos.substring(2, 4);
                    pw.append(s1);
                    contador = contador + 2;
                    strline = strline + s1;

                    if (contador >= 40) {
                        chsum = a.getCheckSum(strline.substring(2));
                        pw.append(chsum);
                        pw.write(System.getProperty("line.separator"));
                        contador = 0;
                    }
                    if (contador == 0) {
                        pw.append("S113" + loc);
                        contador = contador + 8;

                    }
                    pw.append(s2);
                    contador = contador + 2;
                    strline = strline + s2;

                    if (contador >= 40) {
                        chsum = a.getCheckSum(strline.substring(2));
                        pw.append(chsum);
                        pw.write(System.getProperty("line.separator"));
                        contador = 0;
                    }
                } else {
                    pw.append(operandos);
                    contador = contador + 2;
                    strline = strline + operandos;

                    if (contador >= 40) {
                        chsum = a.getCheckSum(strline.substring(2));
                        pw.append(chsum);
                        pw.write(System.getProperty("line.separator"));
                        contador = 0;

                    }
                }
                if (contador == 0) {
                    pw.append("S113" + loc);
                    contador = contador + 8;
                }
            }
            pw.close();
            br.close();

        } catch (IOException e) {
        };
    }

    String getCheckSum(String numeros) {
        int sum = 0;
        String suma;
        int x = 0;
        int y = 2;
        for (int i = 0; i < numeros.length() / 2; i++) {

            sum = sum + Integer.parseInt(numeros.substring(x, y), 16);
            x = x + 2;
            y = y + 2;
        }
        suma = Integer.toString(sum);
        if (suma.length() > 2) {
            StringBuilder builder = new StringBuilder(suma);
            String inversa = builder.reverse().toString();

            String ult = inversa.substring(0, 2);
            StringBuilder build = new StringBuilder(ult);
            inversa = build.reverse().toString();
            sum = Integer.parseInt("FF", 16) - Integer.parseInt(inversa, 16);
            String hex = Integer.toHexString(sum);
            return hex.toUpperCase();
        } else {
            sum = Integer.parseInt("FF", 16) - sum;
            String hex = Integer.toHexString(sum);
            return hex.toUpperCase();
        }

    }
    static void escribeS19Util(ArrayList<ArrayList<String>> opCodeAEscribir,ArrayList<String> direcciones,String rutaArchivo,String nombreSinExtension, boolean END) throws IOException{
            File f = new File(rutaArchivo+nombreSinExtension+".s19");
        
        
            PrintWriter pw = new PrintWriter(new FileWriter(f));
            pw.write("S00B00002020202020202020F4");
            pw.write(System.getProperty("line.separator"));
            pw.close();

            for(ArrayList<String> lineaPorEscribir:opCodeAEscribir){
                escribeS19(false,lineaPorEscribir.get(0),lineaPorEscribir.get(1),f);
          }
            if(END){
                escribeS19(true,null,null,f);
            }else{
                escribeS19(false,null,null,f);
            }
        }
    /*public static void main(String[] args) {
        S19 a = new S19();

        a.escribeS19(false, "25", "15");
        a.escribeS19(false, "12 34", "AB12");
        a.escribeS19(false, "23", "16");
        a.escribeS19(false, "11 22", "BA21");
        a.escribeS19(false, "66 70", "DDDD");
        a.escribeS19(false, "76 88", "EEEE");
        a.escribeS19(false, "25", "15");
        a.escribeS19(false, "12 34", "AB12");
        a.escribeS19(false, "55 20", "ACDD");
        a.escribeS19(false, "66 99", "FFFF");
        a.escribeS19(false, "23", "16");
        a.escribeS19(false, "11 22", "BA21");
        a.escribeS19(false, "66 70", "DDDD");
        a.escribeS19(false, "76 88", "EEEE");
        a.escribeS19(false, "25", "15");
        a.escribeS19(false, "12 34", "AB12");
        a.escribeS19(false, "55 20", "ACDD");
        a.escribeS19(false, "66 99", "FFFF");
        a.escribeS19(false, "23", "16");
        a.escribeS19(false, "11 22", "BA21");
        a.escribeS19(false, "66 70", "DDDD");
        a.escribeS19(false, "76 88", "EEEE");
        a.escribeS19(false, "25", "15");
        a.escribeS19(false, "12 34", "AB12");
        a.escribeS19(false, "55 20", "ACDD");
        a.escribeS19(false, "66 99", "FFFF");
        a.escribeS19(false, "23", "16");
        a.escribeS19(false, "11 22", "BA21");
        a.escribeS19(false, "66 70", "DDDD");
        a.escribeS19(false, "76 88", "EEEE");
        a.escribeS19(false, "25", "15");
        a.escribeS19(false, "12 34", "AB12");
        a.escribeS19(false, "55 20", "ACDD");
        a.escribeS19(false, "66 99", "FFFF");
        a.escribeS19(false, "23", "16");
        a.escribeS19(false, "11 22", "BA21");
        a.escribeS19(false, "66 70", "DDDD");
        a.escribeS19(false, "76 88", "EEEE");
        a.escribeS19(false, "25", "15");
        a.escribeS19(false, "12 34", "AB12");
        a.escribeS19(false, "55 20", "ACDD");
        a.escribeS19(false, "66 99", "FFFF");
        a.escribeS19(false, "23", "16");
        a.escribeS19(false, "11 22", "BA21");
        a.escribeS19(false, "66 70", "DDDD");
        a.escribeS19(false, "76 88", "EEEE");
        a.escribeS19(false, "25", "15");
        a.escribeS19(false, "12 34", "AB12");
        a.escribeS19(false, "55 20", "ACDD");
        a.escribeS19(false, "66 99", "FFFF");
        a.escribeS19(false, "23", "16");
        a.escribeS19(false, "11 22", "BA21");
        a.escribeS19(false, "66 70", "DDDD");
        a.escribeS19(false, "76 88", "EEEE");
        a.escribeS19(true, "25", "15");

    }*/
}
