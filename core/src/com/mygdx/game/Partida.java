package com.mygdx.game;

import java.util.ArrayList;
import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.mycompany.gestorcomponentes.ComponentePrototipo;
import com.mygdx.game.Componentes.Zombies.*;


public class Partida {
    private GameGrid grid;
    private Componente[][] gridComponentes = new Componente[25][25];
    private ArrayList<Componente> zombies = new ArrayList<Componente>();
    private ArrayList<Componente> defensas = new ArrayList<Componente>();
    private ArrayList<EntidadMovible> zombiesMovibles = new ArrayList<EntidadMovible>();
    private ArrayList<EntidadMovible> defensasMovibles = new ArrayList<EntidadMovible>();
    private ArrayList<ComponentePrototipo> prototipos = new ArrayList<ComponentePrototipo>();
    private ArrayList<EntidadMovible> muertos = new ArrayList<EntidadMovible>();
    private int nivel;
    private int espaciosEjercitos = 20;
    private int zombiesRestantes;
    private SpriteBatch batch;
    private Stage labelStage;
    private Skin skin;

    
    public Partida(SpriteBatch batch, GameGrid grid, int nivel, ArrayList<ComponentePrototipo> prototipos, ArrayList<EntidadMovible> defensasMovibles, ArrayList<EntidadMovible> zombiesMovibles, Stage labelStage){
        this.batch = batch;
        this.grid = grid;
        this.nivel = nivel;
        this.espaciosEjercitos = 20 + (nivel - 1) * 5;
        this.zombiesRestantes = espaciosEjercitos;
        this.prototipos = prototipos;
        this.defensasMovibles = defensasMovibles;
        this.zombiesMovibles = zombiesMovibles;
        this.labelStage = labelStage;
        this.skin = new Skin(Gdx.files.internal("theWalkingTEC\\core\\src\\com\\mygdx\\game\\Skins\\Glassy\\glassy-ui.json"));
    }
    
    public boolean verificarCasilla(int x, int y){
        if (x < 0 || x > 24 || y < 0 || y > 24)
            return false;
        return gridComponentes[x][y] == null;
    }

    public boolean verificarCasilla(int x, int y, Componente componente){
        if (x < 0 || x > 24 || y < 0 || y > 24)
            return false;
        if (gridComponentes[x][y] == componente)
            return true;
        return gridComponentes[x][y] == null;
    }

    public boolean placeDefensa(Componente componente, int x, int y){
        if (!verificarCasilla(x, y))
        return false;
        gridComponentes[x][y] = componente;
        defensas.add(componente);
        return true;
    }

    public boolean placeZombie(Componente componente, int x, int y){
        if (!verificarCasilla(x, y))
            return false;
        gridComponentes[x][y] = componente;
        zombies.add(componente);
        return true;
    }

    public boolean addDefensa(EntidadMovible defensa){
        System.out.println(defensa.getPosicionX() + " " + defensa.getPosicionY());
        int casilla_x = Math.round((defensa.getPosicionX()-100)/30);
        int casilla_y = Math.round((defensa.getPosicionY())/30);
        if (!verificarCasilla(casilla_x, casilla_y))
            return false;
        gridComponentes[casilla_x][casilla_y] = defensa.getEntidad();
        System.out.println(gridComponentes[casilla_x][casilla_y]);
        defensas.add(defensa.getEntidad());
        defensasMovibles.add(defensa);
        return true;
    }

    public boolean addZombie(EntidadMovible zombie){
        System.out.println(zombie.getPosicionX() + " " + zombie.getPosicionY());
        int casilla_x = Math.round((zombie.getPosicionX()-100)/30);
        int casilla_y = Math.round((zombie.getPosicionY())/30);
        if (!verificarCasilla(casilla_x, casilla_y))
            return false;
        gridComponentes[casilla_x][casilla_y] = zombie.getEntidad();
        System.out.println(gridComponentes[casilla_x][casilla_y]);
        zombies.add(zombie.getEntidad());
        zombiesMovibles.add(zombie);
        return true;
    }

    public void setGridComponentes(Componente[][] gridComponentes){
        this.gridComponentes = gridComponentes;
    }

    public void setZombies(ArrayList<Componente> zombies){
        this.zombies = zombies;
    }

    public void setDefensas(ArrayList<Componente> defensas){
        this.defensas = defensas;
    }

    public void setNivel(int nivel){
        this.nivel = nivel;
    }

    public Componente[][] getGridComponentes(){
        return gridComponentes;
    }

    public int getEspaciosEjercitos(){
        return espaciosEjercitos;
    }

    public void startGame(){
        for (int i = 0; i < 25; i++){
            for (int j = 0; j < 25; j++){
                if (gridComponentes[i][j] != null){
                    gridComponentes[i][j].getEntidad().startEntidad();
                }
            }
        }
        Texture[] sprites = new Texture[prototipos.size()];
        for (int i = 0; i < prototipos.size(); i++)
            sprites[i] = new Texture(prototipos.get(i).getSprites().get(0));
        Thread generacionZombies = new GeneracionZombiesThread(this, batch, zombiesRestantes, prototipos, sprites);
        generacionZombies.start();
    }
    
    public void addDamageLabel(int x, int y, int damage){
        damageLabel label = new damageLabel(Integer.toString(damage), skin, x, y);
        labelStage.addActor(label);
    }

    public void removeEntidad(EntidadMovible entidad){
        String clase = entidad.getEntidad().getClass().getSimpleName().toString();
        clase = clase.toUpperCase();
        if (clase.contains("ZOMBIE"))
            zombies.remove(entidad.getEntidad());
        else
            defensas.remove(entidad.getEntidad());
        for (int i = 0; i < 25; i++){
            for (int j = 0; j < 25; j++){
                if (gridComponentes[i][j] == entidad.getEntidad()){
                    gridComponentes[i][j] = null;
                    break;
                }
            }
        }
        entidad.getEntidad().setVida(0);
        entidad.setPosition(99999, 9999);
        muertos.add(entidad);
    }
}

