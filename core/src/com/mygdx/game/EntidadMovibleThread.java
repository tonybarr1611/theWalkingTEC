package com.mygdx.game;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class EntidadMovibleThread extends Thread{
    private EntidadMovible entidad;
    boolean movimientoDiagonal;
    private int x, y; // Posicion en el grid
    private float x_real, y_real; // Posicion real en la pantalla
    private int x_destino, y_destino; // Posicion destino en el grid
    boolean running = false;
    private final float speed = (float)17;
    private SpriteBatch batch;
    
    public EntidadMovibleThread(EntidadMovible entidad, boolean movimientoDiagonal){
        super();
        this.entidad = entidad;
        this.movimientoDiagonal = movimientoDiagonal;
        x = entidad.getDestinoX();
        y = entidad.getDestinoY();
        x_real = entidad.getPosicionXReal();
        y_real = entidad.getPosicionYReal();
        x_destino = entidad.getDestinoX();
        y_destino = entidad.getDestinoY();
        batch = entidad.getBatch();
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
            if(x_real + speed >= x_destino)
                entidad.setPosicionReal(x_destino - x_real, (float)0);
            else
                entidad.setPosicionReal(speed, (float)0);
            hasMoved = true;
        }else if((x_real - 2 > x_destino || x > x_destino) && (!hasMoved || movimientoDiagonal)){
            if(x_real - x_destino <= speed)
                entidad.setPosicionReal(-(x_real - x_destino), (float)0);
            else
                entidad.setPosicionReal(-speed, (float)0);
            hasMoved = true;
        }if((y_real - 2 < y_destino || y < y_destino) && (!hasMoved || movimientoDiagonal)){
            if(y_real + speed >= y_destino)
                entidad.setPosicionReal((float)0, y_destino - y_real);
            else
                entidad.setPosicionReal((float)0, speed);
            hasMoved = true;
        }else if((y_real > y_destino || y > y_destino) && (!hasMoved || movimientoDiagonal)){
            if(y_real - y_destino <= speed)
                entidad.setPosicionReal((float)0, -(y_real - y_destino));
            else
                entidad.setPosicionReal((float)0, -speed);
            hasMoved = true;
        }
        if(hasMoved)
            updateValues();
    }

    public void run(){
        System.out.println("Thread running");
        running = true;
        while(running){
            try{
                update();
                // draw();
                Thread.sleep(100);
            }catch(InterruptedException e){
                System.out.println("Thread interrupted");
            }
        }
    }
}
