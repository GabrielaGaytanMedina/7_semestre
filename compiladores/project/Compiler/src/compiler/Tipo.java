package compiler;

/**
 *
 * @author Jorge
 */

public class Tipo {

    private int id;
    private String tipo;
    private Integer numElemtos;
    private Tipo tipoBase;
    private int size = 0;   
    
    public final static Tipo ent = new Tipo("ent", null, null, 4);
    public final static Tipo real = new Tipo("real", null, null, 4);
    public final static Tipo dreal = new Tipo("dreal", null, null, 8);
    public final static Tipo car = new Tipo("car", null, null, 1);
    public final static Tipo sin = new Tipo("sin", null, null, 0);  

    public Tipo(String tipo, Integer numElemtos, Tipo tipoBase) {
        this.tipo = tipo;
        this.numElemtos = numElemtos;
        this.tipoBase = tipoBase;
        this.size = tipoBase.getSize() + numElemtos;
    }

    public Tipo(String tipo, Integer numElemtos, Tipo tipoBase, int size) {
        this.tipo = tipo;
        this.numElemtos = numElemtos;
        this.tipoBase = tipoBase;
        this.size = size;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    
    
    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Integer getNumElemtos() {
        return numElemtos;
    }

    public void setNumElemtos(Integer numElemtos) {
        this.numElemtos = numElemtos;
    }

    public Tipo getTipoBase() {
        return tipoBase;
    }

    public void setTipoBase(Tipo tipoBase) {
        this.tipoBase = tipoBase;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    @Override
    public String toString() {
        return "Tipo{" + "tipo=" + tipo + ", numElemtos=" + numElemtos + ", tipoBase=" + tipoBase + ", size=" + size + '}';
    }
    
    
    
}
