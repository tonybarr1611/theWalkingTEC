package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class EntidadMovible extends Sprite{
    // private Componente entidad;
    private int x, y; // Posicion en el grid
    private float x_real, y_real; // Posicion real en la pantalla
    private int x_destino, y_destino; // Posicion destino en el grid
    private boolean movimientoDiagonal;
    private SpriteBatch batch;

    EntidadMovible(Texture texture, int x, int y, SpriteBatch batch, boolean movimientoDiagonal){
        super(texture);
        super.setSize(30, 30);
        super.setPosition(x, y);
        this.x = x;
        this.y = y;
        this.x_real = x;
        this.y_real = y;
        this.batch = batch;
        this.movimientoDiagonal = movimientoDiagonal;
    }

    public void setPosicion(int x, int y){
        this.x = x;
        this.y = y;
    }
    
    public void setPosicionReal(float x, float y){
        super.translate(x, y);
        x_real += x;
        y_real += y;
    }

    public void setDestino(int x, int y){
        this.x_destino = x;
        this.y_destino = y;
    }

    public int getPosicionX(){
        return x;
    }

    public int getPosicionY(){
        return y;
    }

    public float getPosicionXReal(){
        return super.getX();
    }

    public float getPosicionYReal(){
        return super.getY();
    }

    public int getDestinoX(){
        return x_destino;
    }

    public int getDestinoY(){
        return y_destino;
    }

    public SpriteBatch getBatch(){
        return batch;
    }
    
    public void translateEntidad(float x, float y){
        super.translate(x, y);
        x_real += x;
        y_real += y;
    }

    public void startEntidad(){
        EntidadMovibleThread thread = new EntidadMovibleThread(this, movimientoDiagonal);
        thread.start();
    }



    
}
