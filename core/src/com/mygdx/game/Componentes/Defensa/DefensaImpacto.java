/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mygdx.game.Componentes.Defensa;

import java.util.ArrayList;

import com.mygdx.game.Componentes.ChoqueImpacto;

/**
 *
 * @author barra
 */
public class DefensaImpacto extends ChoqueImpacto {
    
    public DefensaImpacto(String nombre, String tipoApariencia, ArrayList<String> sprites, int vida, int cantidadGolpes, int nivel, int campos, int nivelAparicion, int rango){
        super(nombre, tipoApariencia, sprites, vida, cantidadGolpes, nivel, campos, nivelAparicion, rango);
    }
}
