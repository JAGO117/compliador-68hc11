package proyecto1eypc;

import java.io.*;
import java.text.*;
import java.util.ArrayList;

public class LST {

    static void escribeLST(String codigo, String operandos, String fuente, String localidad,File f) {

        String line;
        DecimalFormat fo = new DecimalFormat("0000");
        
        try {

            BufferedReader br = new BufferedReader(new FileReader(f));
            PrintWriter pw = new PrintWriter(new FileWriter(f, true));
            String strline = null;
            String tmp;

            while ((tmp = br.readLine()) != null) { //AQUI OBTENEMOS LA ULTIMA LINEA DEL ARCHIVO 
                strline = tmp;
            }
            String ultLin = strline;
            line = strline.substring(0, 4);//OBTENEMOS LA ULTIMA LOCALIDAD DE MEMORIA 
            System.out.println("localidad: " + line);
            int nextLine = Integer.parseInt(line) + 1; //SUMAMOS UNO A NUESTRA LOCALIDAD PARA A�ADIRLA CUANDDO HAYA SALTO DE LINEA
            System.out.println("sumando: " + nextLine);
            String l2 = String.valueOf(fo.format(nextLine)); //LA PASAMOS A STRING PARA PODER ESCRIBIRLA

            if (codigo != null && codigo.equalsIgnoreCase("END")&& operandos == null && fuente != null && localidad == null) {//comentario

                pw.append("        " + "         " + fuente);
            }else if (codigo == null && operandos == null && fuente == null && localidad == null) { //linea vac�a

                pw.append("0000");
                pw.write(System.getProperty("line.separator"));
                pw.append(l2 + " A ");
            } else if (codigo != null && operandos != null && fuente != null && localidad != null) {//contiene todo
                
                pw.append(localidad + " " + codigo + operandos + "          " + fuente);
                pw.write(System.getProperty("line.separator"));
                pw.append(l2 + " A ");
            } else if (codigo == null && operandos == null && fuente != null && localidad != null && fuente.contains("ORG")) {//ORG

                pw.append("         " + localidad + "     " + fuente);
                pw.write(System.getProperty("line.separator"));
                pw.append(l2 + " A ");
            } else if (codigo == null && operandos == null && fuente != null && localidad != null) {//etiquetas

                pw.append(localidad + "             " + fuente);
                pw.write(System.getProperty("line.separator"));
                pw.append(l2 + " A ");

            } else if (codigo == null && operandos != null && fuente != null && localidad == null) {//constantes

                pw.append("     " + operandos + "        " + fuente);
                pw.write(System.getProperty("line.separator"));
                pw.append(l2 + " A ");
            } else if (codigo == null && operandos == null && fuente != null && localidad == null) {//comentario

                pw.append("        " + "         " + fuente);
                pw.write(System.getProperty("line.separator"));
                pw.append(l2 + " A ");
            }else if (codigo != null && operandos == null && fuente != null && localidad != null) {//inherentes
                
                pw.append(localidad + " " + codigo + "           " + fuente);
                pw.write(System.getProperty("line.separator"));
                pw.append(l2 + " A ");
            }else if (codigo == null && operandos != null && fuente != null && localidad != null) {//FCB

                pw.append(localidad +" " + operandos + "         " + fuente);
                pw.write(System.getProperty("line.separator"));
                pw.append(l2 + " A ");
            }

            pw.close();
            br.close();

        } catch (IOException e) {
        };
        
        
    }
        static void escribeLSTUtil(ArrayList<ArrayList<String>> opCodeAEscribir ,ArrayList<String> lineasFuente ,ArrayList<String> direcciones,String rutaArchivo,String nombreSinExtension,ArrayList<ArrayList<String>> errores) throws IOException{
            File f = new File(rutaArchivo+nombreSinExtension+".LST");
        
        
            PrintWriter pw = new PrintWriter(new FileWriter(f));
            pw.write("0001 A ");
            pw.close();

            for(int i=0;i<lineasFuente.size();i++){
                    System.out.println("Estoy por escribir la linea: "+(i+1));
                    System.out.println("voy a escribir en LST:"+/*opCodeAEscribir.get(i).get(0)+/*opCodeAEscribir.get(i).get(1)+*/lineasFuente.get(i));
                    escribeLST(opCodeAEscribir.get(i).get(0),opCodeAEscribir.get(i).get(1),lineasFuente.get(i),opCodeAEscribir.get(i).get(2),f);
                    for (ArrayList<String> error : errores) {
                        if(Integer.parseInt(error.get(2))==i+1){
                            escribeLST(null,null,"ERROR "+error.get(0)+"\t"+error.get(1),null,f);
                        }
                    }
            }
        }
    /*public static void main(String[] args) {
     LST a = new LST();
     a.escribeLST("4856", "AABB", "     LDAA     #$0056", "8002");
     a.escribeLST(null, null, null, null);
     a.escribeLST(null, null, "     ORG    $8000", "8000");
     a.escribeLST(null, null, "ETIQUETA", "8008");
     a.escribeLST(null, "1026", "POIO     EQU     $1026", null);
     a.escribeLST(null, null, "*********COM*********", null);
     }*/
}
