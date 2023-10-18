package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mycompany.gestorcomponentes.ComponentePrototipo;
import com.mycompany.gestorcomponentes.Componentes.*;


public class EntidadMovible extends Sprite{
    // private Componente entidad;
    private int x, y; // Posicion en el grid
    private float x_real, y_real; // Posicion real en la pantalla
    private int x_destino, y_destino; // Posicion destino en el grid
    private boolean movimientoDiagonal;
    private SpriteBatch batch;
    private Componente entidad;
    private Partida partida;

    EntidadMovible(Texture texture, int x, int y, SpriteBatch batch, boolean movimientoDiagonal, Partida partida, Componente entidad){
        super(texture);
        super.setSize(30, 30);
        super.setPosition(x, y);
        this.x = x;
        this.y = y;
        this.x_real = x;
        this.y_real = y;
        this.batch = batch;
        this.movimientoDiagonal = movimientoDiagonal;
        this.partida = partida;
        this.entidad = entidad;
    }

    public void setPosicion(int x, int y){
        this.x = x;
        this.y = y;
    }
    
    public boolean setPosicionReal(float x, float y){
        int casilla_x_actual = Math.round((Math.round(x_real)-100)/30);
        int casilla_x = Math.round((Math.round(x_real += x)-100)/30); // Esta formula es la interpolacion del grid en el eje x
        int casilla_y = Math.round((Math.round(y_real += y))/30); // Esta formula es la interpolacion del grid en el eje y
        int casilla_y_actual = Math.round((Math.round(y_real))/30);
        if (partida.verificarCasilla(casilla_x, casilla_y, entidad)){
            super.translate(x, y);
            x_real = super.getX();
            y_real = super.getY();
            Componente[][] gridComponentes = partida.getGridComponentes();
            for (int i = 0; i < 25; i++){
                for (int j = 0; j < 25; j++){
                    if (gridComponentes[i][j] == entidad){
                        gridComponentes[i][j] = null;
                        break;
                    }
                }
            }
            gridComponentes[casilla_x][casilla_y] = entidad;
            return true;
        }
        Componente[][] gridComponentes = partida.getGridComponentes();
        for (int i = 0; i < 25; i++){
            for (int j = 0; j < 25; j++){
                if (gridComponentes[i][j] == entidad){
                    super.setX(i * 30 + 100);
                    super.setY(j * 30);
                    break;
                }
            }
        }
        this.x_real = casilla_x_actual * 30 + 100;
        this.y_real = casilla_y_actual * 30;
        this.x_destino = (int)this.x_real;
        this.y_destino = (int)this.y_real;
        return false;
    }

    public void setDestino(int x, int y){
        if (x < 100)
            x = 100;
        if (x > 820)
            x = 820;
        if (y < 0)
            y = 0;
        if (y > 720)
            y = 720;
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

    public Componente getEntidad(){
        return entidad;
    }
    
    public void translateEntidad(float x, float y){
        super.translate(x, y);
        x_real += x;
        y_real += y;
    }

    public void startEntidad(){
        EntidadMovibleThread thread = new EntidadMovibleThread(this, movimientoDiagonal, (float)entidad.getVida() + 3, partida);
        thread.start();
    }



    
}
