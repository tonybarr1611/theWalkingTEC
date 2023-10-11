package com.mygdx.game;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class EntidadMovibleThread extends Thread{
    private EntidadMovible entidad;
    private int x, y; // Posicion en el grid
    private float x_real, y_real; // Posicion real en la pantalla
    private int x_destino, y_destino; // Posicion destino en el grid
    boolean running = false;
    private final float speed = (float)17;
    private SpriteBatch batch;
    
    public EntidadMovibleThread(EntidadMovible entidad){
        super();
        this.entidad = entidad;
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

    private void update(){
        if(x_real < x_destino || x < x_destino){
            if(x_real + speed >= x_destino)
                entidad.setPosicionReal(x_destino - x_real, (float)0);
            else
                entidad.setPosicionReal(speed, (float)0);
        }else if(x_real - 2 > x_destino || x > x_destino){
            if(x_real - x_destino <= speed)
                entidad.setPosicionReal(-(x_real - x_destino), (float)0);
            else
                entidad.setPosicionReal(-speed, (float)0);
        }else if(y_real - 2 < y_destino || y < y_destino){
            if(y_real + speed >= y_destino)
                entidad.setPosicionReal((float)0, y_destino - y_real);
            else
                entidad.setPosicionReal((float)0, speed);
        }else if(y_real > y_destino || y > y_destino){
            if(y_real - y_destino <= speed)
                entidad.setPosicionReal((float)0, -(y_real - y_destino));
            else
                entidad.setPosicionReal((float)0, -speed);
        }
        x_real = entidad.getPosicionXReal();
        y_real = entidad.getPosicionYReal();
        x = Math.round(x_real);
        y = Math.round(y_real);
        entidad.setPosicion(x, y);
        x_destino = entidad.getDestinoX();
        y_destino = entidad.getDestinoY();
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
