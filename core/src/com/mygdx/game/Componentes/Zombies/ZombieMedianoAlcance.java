/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mygdx.game.Componentes.Zombies;

import com.mygdx.game.Componentes.MedianoAlcance;

import java.util.ArrayList;

/**
 *
 * @author barra
 */
public class ZombieMedianoAlcance extends MedianoAlcance{
    
    public ZombieMedianoAlcance(String nombre, String tipoApariencia, ArrayList<String> sprites, int vida, int cantidadGolpes, int nivel, int campos, int nivelAparicion, int alcance){
        super(nombre, tipoApariencia, sprites, vida, cantidadGolpes, nivel, campos, nivelAparicion, alcance);
    }
}
