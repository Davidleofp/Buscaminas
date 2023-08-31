
package Presentacion;

import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;
import logica.Casilla;

public class Modelo {
    //inicializar variables
    Casilla[][] casillas;
    int numeroFilas;
    int numeroColumnas;
    int numeroMinas;
    int numCasillasAbiertas;
    boolean juegoTerminado;
    
    //Varibales que determinan el estado de la partida y de las casillas
    private Consumer<List<Casilla>> eventoPartidaPerdida;
    private Consumer<List<Casilla>> eventoPartidaGanada;
    private Consumer<Casilla> eventoCasillaAbierta;
    
    //inicializar las casillas determinando el numero de casillas y de columnas que va a tener el tablero
    public Modelo(int numFilas, int numColumnas, int numMinas) {
        this.numeroFilas = numFilas;
        this.numeroColumnas = numColumnas;
        this.numeroMinas=numMinas;
        this.inicializarCasillas();
    }
    //teniendo en cuenta como se inicializaron las casillas antes ahora se deberia de poder opererar un tablero fisico con las casillas
    public void inicializarCasillas(){
        casillas=new Casilla[this.numeroFilas][this.numeroColumnas];
        for (int i = 0; i < casillas.length; i++) {
            for (int j = 0; j < casillas[i].length; j++) {
                casillas[i][j]=new Casilla(i, j);
            }
        }
        generarMinas();
    }
    //Teniendo el numero de casillas ya creadas ahora si podemos determinar la cantidad de minas que puede contener el tablero
    private void generarMinas(){
        int minasGeneradas=0;
        while(minasGeneradas!=numeroMinas){
            int posTmpFila=(int)(Math.random()*casillas.length);
            int posTmpColumna=(int)(Math.random()*casillas[0].length);
            if (!casillas[posTmpFila][posTmpColumna].isMina()){
                casillas[posTmpFila][posTmpColumna].setMina(true);
                minasGeneradas++;
            }
        }
        actualizarNumeroMinasAlrededor();
    }
    //OJITO OJITO QUE ESTO TAL VEZ TOQUE BORRARLO DESPUES JAJA ES SOLO PARA QUE SE MUESTRE POR CONSOLA AL MOMENTO DE HACER LAS PRUEBAS
    public void imprimirTablero() {
        for (int i = 0; i < casillas.length; i++) {
            for (int j = 0; j < casillas[i].length; j++) {
                System.out.print(casillas[i][j].isMina()?"*":"0");
            }
            System.out.println("");
        }
    }
    //Creacion de los numeros que nos permiten conocer la cantidad de minas que tenemos al rededor
    private void imprimirPistas() {
        for (int i = 0; i < casillas.length; i++) {
            for (int j = 0; j < casillas[i].length; j++) {
                System.out.print(casillas[i][j].getNumMinasAlrededor());
            }
            System.out.println("");
        }
    }
    //Cuando se abre una nueva casilla se debe actualizar con el numero de minas que tiene al rededor en caso de no ser una mina
    private void actualizarNumeroMinasAlrededor(){
        for (int i = 0; i < casillas.length; i++) {
            for (int j = 0; j < casillas[i].length; j++) {
                if (casillas[i][j].isMina()){
                    List<Casilla> casillasAlrededor=obtenerCasillasAlrededor(i, j);
                    casillasAlrededor.forEach((c)->c.incrementarNumeroMinasAlrededor());
                }
            }
        }
    }
    //Funciona para la primera apertura del tablero donde se liberan todas las casillas que no contienen una mina
    private List<Casilla> obtenerCasillasAlrededor(int posFila, int posColumna){
        List<Casilla> listaCasillas=new LinkedList<>();
        for (int i = 0; i < 8; i++) {
            int tmpPosFila=posFila;
            int tmpPosColumna=posColumna;
            switch(i){
                case 0: tmpPosFila--;break; //Arriba
                case 1: tmpPosFila--;tmpPosColumna++;break; //Arriba Derecha
                case 2: tmpPosColumna++;break; //Derecha
                case 3: tmpPosColumna++;tmpPosFila++;break; //Derecha Abajo
                case 4: tmpPosFila++;break; //Abajo
                case 5: tmpPosFila++;tmpPosColumna--;break; //Abajo Izquierda
                case 6: tmpPosColumna--;break; //Izquierda
                case 7: tmpPosFila--; tmpPosColumna--;break; //Izquierda Arriba
            }
            
            if (tmpPosFila>=0 && tmpPosFila<this.casillas.length
                    && tmpPosColumna>=0 && tmpPosColumna<this.casillas[0].length){
                listaCasillas.add(this.casillas[tmpPosFila][tmpPosColumna]);
            }
            
        }
        return listaCasillas;
    }
    //Se detecta que casillas tienen mina para terminar el juego
    List<Casilla> obtenerCasillasConMinas() {
        List<Casilla> casillasConMinas = new LinkedList<>();
        for (int i = 0; i < casillas.length; i++) {
            for (int j = 0; j < casillas[i].length; j++) {
                if (casillas[i][j].isMina()) {
                    casillasConMinas.add(casillas[i][j]);
                }
            }
        }
        return casillasConMinas;
    }
    //seleccionar casilla lo unico que hace es llamar al evento de darle click a una casilla y determinar si tiene una mina o no
    //AUN NO ESTA EN FUNCIONAMIENTO PQ SE BORRARON LAS CASILLAS EN LAS QUE SE PODIA APLICAR EL METODO
    /*public void seleccionarCasilla(int posFila, int posColumna) {
        eventoCasillaAbierta.accept(this.casillas[posFila][posColumna]);
        if (this.casillas[posFila][posColumna].isMina()) {
            eventoPartidaPerdida.accept(obtenerCasillasConMinas());
        }else if (this.casillas[posFila][posColumna].getNumMinasAlrededor()==0){
            marcarCasillaAbierta(posFila, posColumna);
            List<Casilla> casillasAlrededor=obtenerCasillasAlrededor(posFila, posColumna);
            for(Casilla casilla: casillasAlrededor){
                if (!casilla.isAbierta()){
                    seleccionarCasilla(casilla.getPosFila(), casilla.getPosColumna());
                }
            }
        }else{
            marcarCasillaAbierta(posFila, posColumna);
        }
        if (partidaGanada()){
           eventoPartidaGanada.accept(obtenerCasillasConMinas());
        }
    }*/
    
    void marcarCasillaAbierta(int posFila, int posColumna){
        if (!this.casillas[posFila][posColumna].isAbierta()){
            numCasillasAbiertas++;
            this.casillas[posFila][posColumna].setAbierta(true);
        }
    }
    //partida ganada
    boolean partidaGanada(){
        return numCasillasAbiertas>=(numeroFilas*numeroColumnas)-numeroMinas;
    }
    
    
    //main provisional para que se pueda ejecutar los metodos por consola mientras se crea el tablero con la interfaz grafica que de momento no se como vrga se hace xdeeeeee
    public static void main(String[] args) {
        Modelo tablero=new Modelo(5, 5, 5);
        tablero.imprimirTablero();
        System.out.println("---");
        tablero.imprimirPistas();
    }

    public void setEventoPartidaPerdida(Consumer<List<Casilla>> eventoPartidaPerdida) {
        this.eventoPartidaPerdida = eventoPartidaPerdida;
    }

    public void setEventoCasillaAbierta(Consumer<Casilla> eventoCasillaAbierta) {
        this.eventoCasillaAbierta = eventoCasillaAbierta;
    }

    public void setEventoPartidaGanada(Consumer<List<Casilla>> eventoPartidaGanada) {
        this.eventoPartidaGanada = eventoPartidaGanada;
    }
    
    
}

