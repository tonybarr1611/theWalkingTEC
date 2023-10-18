/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mygdx.game;

import java.io.Serializable;
import java.util.ArrayList;

import javax.print.attribute.standard.Media;

import com.mycompany.gestorcomponentes.ComponentePrototipo;
import com.mycompany.gestorcomponentes.Componentes.ChoqueImpactoPrototipo;
import com.mycompany.gestorcomponentes.Componentes.MedianoAlcancePrototipo;

/**
 *
 * @author barra
 */
public abstract class Componente implements Serializable{
    private String nombre, tipoApariencia;
    private ArrayList<String> sprites;
    private int vida, cantidadGolpes, nivel, campos, nivelAparicion, rango;
    private EntidadMovible entidad;
    private Partida partida;

    public Componente(String nombre, String tipoApariencia, ArrayList<String> sprites, int vida, int cantidadGolpes, int nivel, int campos, int nivelAparicion, int rango){
        this.nombre = nombre;
        this.tipoApariencia = tipoApariencia;
        this.sprites = sprites;
        this.vida = vida;
        this.cantidadGolpes = cantidadGolpes;
        this.nivel = nivel;
        this.campos = campos;
        this.nivelAparicion = nivelAparicion;
        this.rango = rango;
    }

    public Componente(String nombre, String tipoApariencia, ArrayList<String> sprites, int vida, int cantidadGolpes, int nivel, int campos, int nivelAparicion, int rango, Partida partida){
        this.nombre = nombre;
        this.tipoApariencia = tipoApariencia;
        this.sprites = sprites;
        this.vida = vida;
        this.cantidadGolpes = cantidadGolpes;
        this.nivel = nivel;
        this.campos = campos;
        this.nivelAparicion = nivelAparicion;
        this.rango = rango;
        this.partida = partida;
    }

    public Componente(ComponentePrototipo componente, Partida partida){
        this.nombre = componente.getNombre();
        this.tipoApariencia = componente.getTipoApariencia();
        this.sprites = componente.getSprites();
        this.vida = componente.getVida();
        this.cantidadGolpes = componente.getCantidadGolpes();
        this.nivel = componente.getNivel();
        this.campos = componente.getCampos();
        this.nivelAparicion = componente.getNivelAparicion();
        rango = 0;
        if (componente instanceof ChoqueImpactoPrototipo){
            ChoqueImpactoPrototipo comp = (ChoqueImpactoPrototipo)componente;
            this.rango = comp.getRango();
        }
        if (componente instanceof MedianoAlcancePrototipo){
            MedianoAlcancePrototipo comp = (MedianoAlcancePrototipo)componente;
            this.rango = comp.getAlcance();
        }
    }

    public void setEntidad(EntidadMovible entidad){
        this.entidad = entidad;
    }

    public void setPartida(Partida partida){
        this.partida = partida;
    }

    public EntidadMovible getEntidad(){
       return entidad;
    }
    

    // Getters y Setters
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTipoApariencia() {
        return tipoApariencia;
    }

    public void setTipoApariencia(String tipoApariencia) {
        this.tipoApariencia = tipoApariencia;
    }

    public ArrayList<String> getSprites() {
        return sprites;
    }

    public void setSprites(ArrayList<String> sprites) {
        this.sprites = sprites;
    }

    public int getVida() {
        return vida;
    }

    public void setVida(int vida) {
        this.vida = vida;
    }

    public int getCantidadGolpes() {
        return cantidadGolpes;
    }

    public void setCantidadGolpes(int cantidadGolpes) {
        this.cantidadGolpes = cantidadGolpes;
    }

    public int getNivel() {
        return nivel;
    }

    public void setNivel(int nivel) {
        this.nivel = nivel;
    }

    public int getCampos() {
        return campos;
    }

    public void setCampos(int campos) {
        this.campos = campos;
    }

    public int getNivelAparicion() {
        return nivelAparicion;
    }

    public void setNivelAparicion(int nivelAparicion) {
        this.nivelAparicion = nivelAparicion;
    }
}
