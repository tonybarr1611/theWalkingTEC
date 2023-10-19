package com.mygdx.game;

import com.mygdx.game.Componentes.Defensa.DefensaAerea;
import com.mygdx.game.Componentes.Zombies.ZombieAereo;

public class EntidadMovibleThread extends Thread{
    private EntidadMovible entidad;
    private boolean movimientoDiagonal;
    private Partida partida;
    private int x, y; // Posicion en el grid
    private float x_real, y_real; // Posicion real en la pantalla
    private int x_destino, y_destino; // Posicion destino en el grid
    private boolean running = false;
    private float speed;
    
    public EntidadMovibleThread(EntidadMovible entidad, boolean movimientoDiagonal, float speed, Partida partida){
        super();
        this.entidad = entidad;
        this.movimientoDiagonal = movimientoDiagonal;
        this.speed = speed;
        this.partida = partida;
        x = entidad.getDestinoX();
        y = entidad.getDestinoY();
        x_real = entidad.getPosicionXReal();
        y_real = entidad.getPosicionYReal();
        x_destino = entidad.getDestinoX();
        y_destino = entidad.getDestinoY();
    }
    
    public void stopThread(){
        running = false;
    }
    
    public void updateValues(){
        x_real = entidad.getPosicionXReal();
        y_real = entidad.getPosicionYReal();
        x = Math.round(x_real);
        y = Math.round(y_real);
        entidad.setPosicion(x, y);
        x_destino = entidad.getDestinoX();
        y_destino = entidad.getDestinoY();
    }

    private void update(){
        boolean hasMoved = false;
        if((x_real < x_destino || x < x_destino) && (!hasMoved || movimientoDiagonal)){ // La segunda condicion es una implicacion logica
            if(x_real+ speed >= x_destino)
                hasMoved = entidad.setPosicionReal(x_destino - x_real, (float)0);
            else
                hasMoved = entidad.setPosicionReal(speed, (float)0);
        }else if((x_real > x_destino || x > x_destino) && (!hasMoved || movimientoDiagonal)){
            if(x_real - x_destino <= speed)
                hasMoved = entidad.setPosicionReal(-(x_real - x_destino), (float)0);
            else
                hasMoved = entidad.setPosicionReal(-speed, (float)0);
        }if((y_real< y_destino || y < y_destino) && (!hasMoved || movimientoDiagonal)){
            if(y_real + speed >= y_destino)
                hasMoved = entidad.setPosicionReal((float)0, y_destino - y_real);
            else
                hasMoved = entidad.setPosicionReal((float)0, speed);
        }else if((y_real > y_destino || y > y_destino) && (!hasMoved || movimientoDiagonal)){
            if(y_real - y_destino <= speed)
                hasMoved = entidad.setPosicionReal((float)0, -(y_real - y_destino));
            else
                hasMoved = entidad.setPosicionReal((float)0, -speed);
        }
        if (x_real == x_destino && y_real == y_destino){
            int x_casilla = Math.round((x_destino - 100) / 30);
            int y_casilla = Math.round(y_destino / 30);
            entidad.setX(Math.round(x_casilla * 30 + 100));
            entidad.setY(Math.round(y_casilla * 30));
        }
        updateValues();
    }

    private boolean isDefensa(Componente componente){
        String clase = componente.getClass().getSimpleName();
        clase = clase.toUpperCase();
        return clase.contains("DEFENSA");
    }

    private void searchClosestZombies(){
        int x_casilla = Math.round((x_real - 100) / 30);
        int y_casilla = Math.round(y_real / 30);
        int x_destino = x_casilla;
        int y_destino = y_casilla;
        int distancia = 1000;
        for (int i = 1; i < 25; i++){
            for (int j = 1; j < 25; j++){
                if (partida.getGridComponentes()[i][j] != null && isDefensa(partida.getGridComponentes()[i][j])){
                    if (entidad.getEntidad() instanceof ZombieAereo == partida.getGridComponentes()[i][j] instanceof DefensaAerea){
                        int distancia_actual = Math.abs(i - x_casilla) + Math.abs(j - y_casilla);
                        if (distancia_actual < distancia){
                            distancia = distancia_actual;
                            x_destino = i;
                            y_destino = j;
                        }
                    }
                }
            }
        }
        attack(distancia, x_destino, y_destino);
        x_destino = x_destino * 30 + 100;
        y_destino = y_destino * 30;

        entidad.setDestino(x_destino, y_destino);
        updateValues();
    }

    private void searchClosestDefensas(){
        int x_casilla = Math.round((x_real - 100) / 30);
        int y_casilla = Math.round(y_real / 30);
        int x_destino = x_casilla;
        int y_destino = y_casilla;
        int distancia = 1000;
        for (int i = 1; i < 25; i++){
            for (int j = 1; j < 25; j++){
                if (partida.getGridComponentes()[i][j] != null && !isDefensa(partida.getGridComponentes()[i][j])){
                    if (entidad.getEntidad() instanceof DefensaAerea == partida.getGridComponentes()[i][j] instanceof ZombieAereo){
                        int distancia_actual = Math.abs(i - x_casilla) + Math.abs(j - y_casilla);
                        if (distancia_actual < distancia){
                            distancia = distancia_actual;
                            x_destino = i;
                            y_destino = j;
                        }
                    }
                }
            }
        }
        attack(distancia, x_destino, y_destino);
        entidad.setDestino(entidad.getPosicionX(), entidad.getPosicionY());
        updateValues();
    }

    private void attack(int distancia, int x_destino, int y_destino){
        if (entidad.getEntidad().getRango() != 0){
            if (distancia <= entidad.getEntidad().getRango()){
                Componente defensa = partida.getGridComponentes()[x_destino][y_destino];
                if (defensa != null){
                    entidad.getEntidad().atacar(defensa);
                    try {
                        Thread.sleep(1000);
                    } catch (Exception e) {
                        System.out.println("Error al dormir el thread");
                    }
                }
            }
        }else{
            if (distancia == 1){
                Componente defensa = partida.getGridComponentes()[x_destino][y_destino];
                if (defensa != null){
                    entidad.getEntidad().atacar(defensa);
                    try {
                        Thread.sleep(1000);
                    } catch (Exception e) {
                        System.out.println("Error al dormir el thread");
                    }
                }
            }
        }
    }


    public void run(){
        System.out.println("Thread running");
        running = true;
        while(running){
            if (entidad.getEntidad().getVida() <= 0){
                running = false;
                break;
            }
            try{
                update();
                if (!isDefensa(entidad.getEntidad()))
                    searchClosestZombies();
                else{
                    searchClosestDefensas();
                }
                Thread.sleep(100);
            }catch(InterruptedException e){
                System.out.println("Thread interrupted");
            }
        }
    }
}
