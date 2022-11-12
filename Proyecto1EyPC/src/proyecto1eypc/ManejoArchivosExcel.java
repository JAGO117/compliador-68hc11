package proyecto1eypc;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ManejoArchivosExcel {
	public static ArrayList<ArrayList<String>> ManejoArch(File fileName){
        List cellData = new ArrayList();
        try{
            FileInputStream fileInputStream = new FileInputStream(fileName);
            XSSFWorkbook workBook =new XSSFWorkbook(fileInputStream);
            XSSFSheet hssfSheet = workBook.getSheetAt(0);
            Iterator rowIterator =  hssfSheet.rowIterator();
            //***EN ESTOS WHILES SE LEEN LOS DATOS DEL ARCHIVO POR FILAS, Y DE CADA FILA SE LEEN LAS CELDAS***
            while(rowIterator.hasNext()){
                XSSFRow hssfRow = (XSSFRow) rowIterator.next();
                Iterator iterator = hssfRow.cellIterator();
                List cellTemp = new ArrayList();
                while(iterator.hasNext()){
                    XSSFCell hssfCell = (XSSFCell) iterator.next();
                    cellTemp.add(hssfCell); //GUARDAMOS LO QUE HAY EN LA CELDA
                }
                cellData.add(cellTemp); //GUARDAMOS LA FILA  Y REPETIMOS EL PROCESO
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return obtenerTabla(cellData);
    }
	static ArrayList<ArrayList<String>> obtenerTabla(List cellDataList) {
		ArrayList<ArrayList<String>> a = new ArrayList<>(); //el array list grande
		//****AQUI LOS FOR FUNCIONAN COMO CUALQUIER OTRA LECTURA DE ARREGLOS 
		for(int i = 0; i<cellDataList.size(); i++) {//FILA
			List cellTempList = (List) cellDataList.get(i);
			ArrayList<String> b = new ArrayList<>(); //CREAMOS UN ARRAYLIST TEMPORAL PARA CADA FILA
			a.add(b); //GUARDAMOS EL TEMPORAL EN EL PRINCIPAL, YA QUE SE ACTUALIZARA EN CADA ITERACION
			for(int j = 0; j<cellTempList.size(); j++) {//COLUMNA
				XSSFCell hssfCell = (XSSFCell) cellTempList.get(j);
				String stringCellValue = hssfCell.toString();
				b.add(stringCellValue);
				//System.out.print(stringCellValue +" "); //IMPRESION DEL ARCHIVO EXCEL
			}
			
			//System.out.println();
		}
		
			for(int j = 0; j<a.size(); j++) { //IMPRESION DEL ARRAYLIST ANIDADO
				//System.out.println(a.get(j));
			}
			return a;
	}
	
	public static ArrayList<String> obtenerMnemonicos(String nombreArchivoMnemonicos){
		ArrayList<ArrayList<String>> a = new ArrayList<>();
		File f = new File(nombreArchivoMnemonicos);
	       if (f.exists()) { 
	    	    ManejoArchivosExcel obj = new ManejoArchivosExcel(); 
	    	    a= obj.ManejoArch(f);
	    	    ArrayList<String> mnemo = new ArrayList<>();
				ArrayList<String> temp = new ArrayList<>();
				String mnemonico;
                    for (ArrayList<String> a1 : a) {
                        temp.addAll(a1);
                        mnemonico = temp.get(1);
                        temp.clear();
                        mnemo.add(mnemonico);
                    }
	    	   return mnemo;
	       } 
	       ArrayList<String> b = new ArrayList<>();
	      return b;
	       
	}
	
	public static int clasifica(boolean gato, String operando) {
            try{
                System.out.println("voy a clasificar gato:"+gato+" operando:"+operando);
		if(gato) {
			return 3;
		}else if(operando.length()==2) {
			return 5;
		}else if(operando.charAt(2)==',') {
			if(operando.charAt(3)=='x'||operando.charAt(3)=='X') {
				return 7;
			}else {
				return 9;
			}
		}else if(operando.length()==4) {
			return 11;
		}
		else {
		return 0; //AQUI PODEMOS A�ADIR EL ERROR DE OPERANDO IVALIDO PARA EL METODO DE DIRECCIONAMIENTO
		}
            }catch(java.lang.StringIndexOutOfBoundsException e){
                return 0;
            }
	}

}
