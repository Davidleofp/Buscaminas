
package logica;


public class Casilla {
   private int fila;
    private int columnas;
    private boolean mina;

    public Casilla(int i, int j) {
        throw new UnsupportedOperationException("Not supported yet."); 
    }
    
    public int getNumMinasAlrededor() {
    throw new UnsupportedOperationException("Not supported yet."); 
    }

    public boolean isAbierta() {
        throw new UnsupportedOperationException("Not supported yet."); 
    }

    public void setAbierta(boolean b) {
        throw new UnsupportedOperationException("Not supported yet."); 
    }
    
    /**
     *
     */
    public void incrementarNumeroMinasAlrededor() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    public int getFila() {
        return fila;
    }


    public void setFila(int fila) {
        this.fila = fila;
    }


    public int getColumnas() {
        return columnas;
    }


    public void setColumnas(int columnas) {
        this.columnas = columnas;
    }


    public boolean isMina() {
        return mina;
    }


    public void setMina(boolean mina) {
        this.mina = mina;
    } 

}
