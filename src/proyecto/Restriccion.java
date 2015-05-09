/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package proyecto;

/**
 *
 * @author Gregorio
 */
public class Restriccion {
    private int tipo;
    private boolean cumplirse;
    private int elemento;
    private String valor;
    
    public Restriccion(String _tipo, boolean _cumplirse, String _elemento, String _valor){
        switch (_tipo) {
            case "Enlace":
                this.tipo = 0;
                break;
            case "Pagina":
                this.tipo = 1;
                break;
        }
        this.cumplirse = _cumplirse;
        switch (_elemento) {
            case "Titulo":
                this.elemento = 0;
                break;
            case "Etiqueta":
                this.elemento = 1;
                break;
            default:
                this.elemento = 2;
                break;
        }
        this.valor = _valor;
    }
    //metodos get
    public int getTipo(){
        return tipo;
    }
    public boolean getCumplir(){
        return cumplirse;
    }
    public int getElemento(){
        return elemento;
    }
    public String getValor(){
        return valor;
    }
    //metodos set
    public void setTipo( String _tipo){
        switch (_tipo) {
            case "Enlace":
                this.tipo = 0;
                break;
            case "Pagina":
                this.tipo = 1;
                break;
        }
    }
    public void setCumplir(boolean _cumplirse){
        this.cumplirse = _cumplirse;
    }
    public void setElemento( String _elemento){
        switch (_elemento) {
            case "Titulo":
                this.elemento = 0;
                break;
            case "Etiqueta":
                this.elemento = 1;
                break;
            default:
                this.elemento = 2;
                break;
        }
    }
    public void setValor(String _valor){
        this.valor = _valor;
    }
}
