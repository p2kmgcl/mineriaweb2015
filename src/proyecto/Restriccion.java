/**
 * @author Pablo Molina y Gregorio Carazo
 */

package proyecto;

public class Restriccion {
    public final static int TIPO_ENLACE = 0;
    public final static int TIPO_PAGINA = 1;
    
    private int tipo;
    private boolean invertir;
    private boolean expresion;
    private String valor;
    
    public Restriccion(String _tipo, boolean _invertir, boolean _expresion, String _valor){
        this.setTipo(_tipo);
        this.setInvertir(_invertir);
        this.setExpresion(_expresion);
        this.setValor(_valor);
    }
    
    //metodos get
    public int getTipo() { return tipo; }
    public boolean getInvertir() { return invertir; } 
    public boolean getExpresion() { return expresion; } 
    public String getValor() { return valor; }
    
    //metodos set
    public void setTipo(String _tipo) {
        switch (_tipo) {
            case "Enlace": this.tipo = TIPO_ENLACE; break;
            case "Página": this.tipo = TIPO_PAGINA; break;
        }
    }
    public void setInvertir(boolean _invertir) { this.invertir = _invertir; } 
    public void setExpresion(boolean _expresion) { this.expresion = _expresion; } 
    public void setValor(String _valor) { this.valor = _valor; }

    @Override
    public String toString () {
        return tipo + "|" + invertir + "|" + expresion + "|" + valor;
    }
}
