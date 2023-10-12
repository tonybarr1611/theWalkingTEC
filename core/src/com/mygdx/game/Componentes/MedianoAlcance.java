/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mygdx.game.Componentes;

import com.mygdx.game.Componente;

import java.util.ArrayList;

/**
 *
 * @author barra
 */
public class MedianoAlcance extends Componente{
    private int alcance;
    
    public MedianoAlcance(String nombre, String tipoApariencia, ArrayList<String> sprites, int vida, int cantidadGolpes, int nivel, int campos, int nivelAparicion, int alcance){
        super(nombre, tipoApariencia, sprites, vida, cantidadGolpes, nivel, campos, nivelAparicion);
        this.alcance = alcance;
    }

    public int getAlcance() {
        return alcance;
    }

    public void setAlcance(int alcance) {
        if(alcance > 0){
            this.alcance = alcance;
        }
    }
}
