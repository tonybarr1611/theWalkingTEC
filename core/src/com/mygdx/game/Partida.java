package com.mygdx.game;

import java.util.ArrayList;

import com.mygdx.game.Componentes.Aereo;

public class Partida {
    private GameGrid grid;
    private Componente[][] gridComponentes = new Componente[25][25];
    private ArrayList<Componente> zombies = new ArrayList<Componente>();
    private ArrayList<Componente> defensas = new ArrayList<Componente>();
    private int nivel;
    private int espaciosEjercitos = 20;
    
    public Partida(GameGrid grid, int nivel){
        this.grid = grid;
        this.nivel = nivel;
        this.espaciosEjercitos = 20 + (nivel - 1) * 5;
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
        return true;
    }

    public boolean addZombie(EntidadMovible zombie){
        if (!verificarCasilla(zombie.getPosicionX(), zombie.getPosicionY()))
            return false;
        gridComponentes[zombie.getPosicionX()][zombie.getPosicionY()] = zombie.getEntidad();
        zombies.add(zombie.getEntidad());
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
        }

}
