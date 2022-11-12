/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyecto1eypc;

/**
 *
 * @author otreb
 */
public class ErrorDeCompilacionException extends Exception{
    String numero;
    String descripcion;
    String linea;
    
    public ErrorDeCompilacionException(String numero,String descripcion,String linea) {
        this.numero=numero;
        this.descripcion=descripcion;
        this.linea=linea;
    }
    
    public String getNumero(){
        return this.numero;
    }
    public String getDescripcion(){
        return this.descripcion;
    }
    public String getLinea(){
        return this.linea;
    }
}
