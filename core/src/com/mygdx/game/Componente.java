/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mygdx.game;

import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author barra
 */
public abstract class Componente implements Serializable{
    private String nombre, tipoApariencia;
    private ArrayList<String> sprites;
    private int vida, cantidadGolpes, nivel, campos, nivelAparicion;
    private EntidadMovible entidad;
    private Partida partida;

    public Componente(String nombre, String tipoApariencia, ArrayList<String> sprites, int vida, int cantidadGolpes, int nivel, int campos, int nivelAparicion){
        this.nombre = nombre;
        this.tipoApariencia = tipoApariencia;
        this.sprites = sprites;
        this.vida = vida;
        this.cantidadGolpes = cantidadGolpes;
        this.nivel = nivel;
        this.campos = campos;
        this.nivelAparicion = nivelAparicion;
    }

    public Componente(String nombre, String tipoApariencia, ArrayList<String> sprites, int vida, int cantidadGolpes, int nivel, int campos, int nivelAparicion, Partida partida){
        this.nombre = nombre;
        this.tipoApariencia = tipoApariencia;
        this.sprites = sprites;
        this.vida = vida;
        this.cantidadGolpes = cantidadGolpes;
        this.nivel = nivel;
        this.campos = campos;
        this.nivelAparicion = nivelAparicion;
        this.partida = partida;
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
