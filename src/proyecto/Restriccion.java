/**
 * @author Pablo Molina y Gregorio Carazo
 */

package proyecto;

public class Restriccion {
    public final static int TIPO_ENLACE = 0;
    public final static int TIPO_PAGINA = 1;
    
    public final static int ELEMENTO_TITULO = 0;
    public final static int ELEMENTO_ETIQUETA = 1;
    public final static int ELEMENTO_CONTENIDO = 2;
    
    private int tipo;
    private boolean cumplirse;
    private int elemento;
    private String valor;
    
    public Restriccion(String _tipo, boolean _cumplirse, String _elemento, String _valor){
        switch (_tipo) {
            case "Enlace": this.tipo = TIPO_ENLACE; break;
            case "Pagina": this.tipo = TIPO_PAGINA; break;
        }
        this.cumplirse = _cumplirse;
        switch (_elemento) {
            case "Titulo": this.elemento = ELEMENTO_TITULO; break;
            case "Etiqueta": this.elemento = ELEMENTO_ETIQUETA; break;
            case "Contenido": this.elemento = ELEMENTO_CONTENIDO; break;
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
