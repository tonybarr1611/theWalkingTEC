package com.mygdx.game;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

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

    public void run(){
        System.out.println("Thread running");
        running = true;
        while(running){
            try{
                update();
                Thread.sleep(100);
            }catch(InterruptedException e){
                System.out.println("Thread interrupted");
            }
        }
    }
}
