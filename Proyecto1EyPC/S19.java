import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class S19 {
	void escribeS19(Boolean END, String codigo,String operandos) {
		File f;
		S19 a = new S19();
		int contador;
		String chsum;
		contador = 0;
		f= new File("D:/QUINTO SEMESTRE/ESTRUCTURA Y PROGRAMACIÓN DE COMPUTADORAS/POIO.txt");
		
		try {
			BufferedReader br = new BufferedReader(new FileReader(f));
			PrintWriter pw = new PrintWriter(new FileWriter(f, true));
			String strline = null;
			String tmp;
			int numeroLineas=0;
			while((tmp = br.readLine())!=null) { //AQUI OBTENEMOS LA ULTIMA LINEA DEL ARCHIVO 
				strline = tmp;
				numeroLineas++;
			}
			int tamanio = strline.length();
			if(numeroLineas==1) {
				tamanio = 0;
			}
			numeroLineas--;
			Integer Localidad= 8000 + (numeroLineas*10);
			String loc = String.valueOf(Localidad);
			
			contador = tamanio;
			if(tamanio==40) { 
				chsum = a.getCheckSum(strline.substring(2));
				pw.append(chsum);
				pw.write(System.getProperty("line.separator"));
				contador = 0;
			}
			if(END == true)	{
				pw.write(System.getProperty("line.separator"));
            	pw.append("S9030000FC");
				pw.write(System.getProperty("line.separator"));

			}
			else {
				if(contador==0) {
					pw.append("S113"+loc);
					contador = contador +8;
				}
				if(codigo.length()==5) { //PARA TENER TODO AGRUPADO DE DOS EN DOS
					String uno = codigo.substring(0, 2);
					String dos = codigo.substring(3, 5);
					pw.append(uno);
					strline = strline +uno;
					contador = contador +2;
					if(contador>=40) { //VERIFICAMOS SI ESTAMOS AUN DENTRO DEL RANGO
						chsum = a.getCheckSum(strline.substring(2));
						pw.append(chsum);
						pw.write(System.getProperty("line.separator"));
						contador = 0;
					}
					if(contador==0) {
						pw.append("S113"+loc);
						contador = contador +8;

					}
					pw.append(dos);
					contador = contador +2;
					strline = strline+dos;

					if(contador>=40) {
						chsum = a.getCheckSum(strline.substring(2));
						pw.append(chsum);
						pw.write(System.getProperty("line.separator"));
						contador =0;
					}
				}
				
				else {
					pw.append(codigo);
					contador = contador + 2;
					strline = strline+codigo;

					if(contador>=40) {
						chsum = a.getCheckSum(strline.substring(2));
						pw.append(chsum);
						pw.write(System.getProperty("line.separator"));
						contador = 0;
					}
				}
				if(contador==0) {
					pw.append("S113"+loc);
					contador = contador +8;

				}
				if(operandos.length()==4) {//PARA TENER TODO AGRUPADO DE DOS EN DOS
					String s1 = operandos.substring(0, 2);
					String s2 = operandos.substring(2, 4);
					pw.append(s1);
					contador = contador +2;
					strline = strline+s1;

					if(contador>=40) {
						chsum = a.getCheckSum(strline.substring(2));
						pw.append(chsum);
						pw.write(System.getProperty("line.separator"));
						contador =0;
					}
					if(contador==0) {
						pw.append("S113"+loc);
						contador = contador +8;

					}
					pw.append(s2);
					contador = contador +2;
					strline = strline+s2;

					if(contador>=40) {
						chsum = a.getCheckSum(strline.substring(2));
						pw.append(chsum);
						pw.write(System.getProperty("line.separator"));
						contador =0;
					}			
				}
				
				else {
					pw.append(operandos);
					contador = contador +2;
					strline = strline+operandos;

					if(contador>=40) {
						chsum = a.getCheckSum(strline.substring(2));
						pw.append(chsum);
						pw.write(System.getProperty("line.separator"));
						contador =0;
						
					}
				}
				if(contador==0) {
					pw.append("S113"+loc);
					contador = contador +8;
				}
			}
		pw.close();
		br.close();
		
		}catch(IOException e) {};
	}
	
	String getCheckSum(String numeros) {
		int sum = 0;
		String suma;
		int x = 0;
		int y = 2;
		for(int i = 0; i<numeros.length()/2;i++) {
			
			sum = sum + Integer.parseInt(numeros.substring(x,y),16);	
			x = x+2;
			y= y+2;
		}
		suma = Integer.toString(sum);
		if(suma.length()>2) {
			StringBuilder builder = new StringBuilder(suma);
			String inversa = builder.reverse().toString();

			String ult = inversa.substring(0, 2);
			StringBuilder build = new StringBuilder(ult);
			inversa = build.reverse().toString();
			sum = Integer.parseInt("FF", 16)-Integer.parseInt(inversa, 16);
			String hex = Integer.toHexString(sum);
			return hex.toUpperCase();
		}
		else {
			sum = Integer.parseInt("FF", 16)-sum;
			String hex = Integer.toHexString(sum);
			return hex.toUpperCase();
		}
		
	}
	
	public static void main(String[] args) {
	      S19 a = new S19();

	      a.escribeS19(false,"25", "15");
	      a.escribeS19(false, "12 34", "AB12");
	      a.escribeS19(false, "23", "16");
	      a.escribeS19(false, "11 22", "BA21");
	      a.escribeS19(false, "66 70", "DDDD");
	      a.escribeS19(false, "76 88", "EEEE");
	      a.escribeS19(false,"25", "15");
	      a.escribeS19(false, "12 34", "AB12");
	      a.escribeS19(false, "55 20", "ACDD");
	      a.escribeS19(false, "66 99", "FFFF");
	      a.escribeS19(false, "23", "16");
	      a.escribeS19(false, "11 22", "BA21");
	      a.escribeS19(false, "66 70", "DDDD");
	      a.escribeS19(false, "76 88", "EEEE");
	      a.escribeS19(false,"25", "15");
	      a.escribeS19(false, "12 34", "AB12");
	      a.escribeS19(false, "55 20", "ACDD");
	      a.escribeS19(false, "66 99", "FFFF");
	      a.escribeS19(false, "23", "16");
	      a.escribeS19(false, "11 22", "BA21");
	      a.escribeS19(false, "66 70", "DDDD");
	      a.escribeS19(false, "76 88", "EEEE");
	      a.escribeS19(false,"25", "15");
	      a.escribeS19(false, "12 34", "AB12");
	      a.escribeS19(false, "55 20", "ACDD");
	      a.escribeS19(false, "66 99", "FFFF");
	      a.escribeS19(false, "23", "16");
	      a.escribeS19(false, "11 22", "BA21");
	      a.escribeS19(false, "66 70", "DDDD");
	      a.escribeS19(false, "76 88", "EEEE");
	      a.escribeS19(false,"25", "15");
	      a.escribeS19(false, "12 34", "AB12");
	      a.escribeS19(false, "55 20", "ACDD");
	      a.escribeS19(false, "66 99", "FFFF");
	      a.escribeS19(false, "23", "16");
	      a.escribeS19(false, "11 22", "BA21");
	      a.escribeS19(false, "66 70", "DDDD");
	      a.escribeS19(false, "76 88", "EEEE");
	      a.escribeS19(false,"25", "15");
	      a.escribeS19(false, "12 34", "AB12");
	      a.escribeS19(false, "55 20", "ACDD");
	      a.escribeS19(false, "66 99", "FFFF");
	      a.escribeS19(false, "23", "16");
	      a.escribeS19(false, "11 22", "BA21");
	      a.escribeS19(false, "66 70", "DDDD");
	      a.escribeS19(false, "76 88", "EEEE");
	      a.escribeS19(false,"25", "15");
	      a.escribeS19(false, "12 34", "AB12");
	      a.escribeS19(false, "55 20", "ACDD");
	      a.escribeS19(false, "66 99", "FFFF");
	      a.escribeS19(false, "23", "16");
	      a.escribeS19(false, "11 22", "BA21");
	      a.escribeS19(false, "66 70", "DDDD");
	      a.escribeS19(false, "76 88", "EEEE");
	      a.escribeS19(true,"25", "15");
	
	    }
}
