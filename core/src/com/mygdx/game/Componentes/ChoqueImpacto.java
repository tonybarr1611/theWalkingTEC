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
public class ChoqueImpacto extends Componente{
    
    public ChoqueImpacto(String nombre, String tipoApariencia, ArrayList<String> sprites, int vida, int cantidadGolpes, int nivel, int campos, int nivelAparicion, int rango){
        super(nombre, tipoApariencia, sprites, vida, cantidadGolpes, nivel, campos, nivelAparicion, rango);
    }

    public void atacar(Componente objetivo){
        objetivo.recibirDano(super.getCantidadGolpes(), this);
        super.getEntidad().agregarBitacora(super.getNombre() + " ha explotado atacando a " + objetivo.getNombre() + " por " + super.getCantidadGolpes() + " de daño\n");
        super.getPartida().addDamageLabel((int)objetivo.getEntidad().getX() + 20, (int)objetivo.getEntidad().getY() + 20, "¡BAM!\n" + super.getCantidadGolpes() );
        super.morir();
    }
}
