package com.mygdx.game;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.mycompany.gestorcomponentes.ComponentePrototipo;
import com.mycompany.gestorcomponentes.Componentes.MedianoAlcancePrototipo;
import com.mygdx.game.Componentes.MedianoAlcance;
import com.mygdx.game.Componentes.Defensa.DefensaBloque;


public class Partida{
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
    private DefensaBloque reliquia;
    private EntidadMovible reliquiaMovible;
    private GeneracionZombiesThread generacionZombies;
    private componentManager manager;
    private MenuOpciones menu;
    private ArrayList<String> sprites;


    
    public Partida(SpriteBatch batch, GameGrid grid, int nivel, ArrayList<ComponentePrototipo> prototipos, ArrayList<EntidadMovible> defensasMovibles, ArrayList<EntidadMovible> zombiesMovibles, Stage labelStage, componentManager manager, MenuOpciones menu){
        this.batch = batch;
        this.grid = grid;
        this.nivel = nivel;
        this.espaciosEjercitos = 20 + (nivel - 1) * 5;
        this.zombiesRestantes = espaciosEjercitos;
        this.prototipos = prototipos;
        this.defensasMovibles = defensasMovibles;
        this.zombiesMovibles = zombiesMovibles;
        this.labelStage = labelStage;
        this.manager = manager;
        this.menu = menu;
        this.skin = new Skin(Gdx.files.internal("theWalkingTEC\\core\\src\\com\\mygdx\\game\\Skins\\Glassy\\glassy-ui.json"));
        sprites = new ArrayList<String>();
        sprites.add("reliquia.png");
        this.reliquia = new DefensaBloque("Reliquia", "PNG", sprites, 300, 0, 1, 1, 1, 0);
        this.reliquiaMovible = new EntidadMovible(new Texture(Gdx.files.internal(sprites.get(0))), 12 * 30 + 100, 12 * 30, this.batch, false, this, this.reliquia);
        reliquia.setEntidad(reliquiaMovible);
        reliquia.setPartida(this);
        this.addDefensa(reliquiaMovible);
        reliquiaMovible.setDestino(12 * 30 + 100, 12 * 30);
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

    public int getNivel(){
        return nivel;
    }
    
    public Stage getStage(){
        return grid.getStage();
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
                    gridComponentes[i][j].getEntidad().agregarBitacora(gridComponentes[i][j].getVida() + "HP.\n");
                    gridComponentes[i][j].getEntidad().startEntidad();
                }
            }
        }
        Texture[] sprites = new Texture[prototipos.size()];
        String currentDir = System.getProperty("user.dir");
        for (int i = 0; i < prototipos.size(); i++){
            sprites[i] = new Texture(currentDir + "/Componentes/Assets/" + prototipos.get(i).getNombre() + ".png");
            System.out.println(prototipos.get(i).getSprites().get(0));
        }
        generacionZombies = new GeneracionZombiesThread(this, batch, zombiesRestantes, prototipos, sprites);
        generacionZombies.setSkin(skin);
        generacionZombies.start();
    }
    
    public void addDamageLabel(int x, int y, int damage){
        addDamageLabel(x, y, Integer.toString(damage));
    }

    public void addDamageLabel(int x, int y, String damage){
        damageLabel label = new damageLabel(damage, skin, x, y);
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

    public void setGrid(GameGrid grid){
        this.grid = grid;
    }

    public void setMenu(MenuOpciones menu){
        this.menu = menu;
    }

    public void verInformacionCasilla(int x, int y){
        x = Math.round((x - 100) / 30);
        y = Math.round(y / 30);
        if (x < 0 || x > 24 || y < 0 || y > 24)
            return;
        if (gridComponentes[x][y] == null)
            return;
        System.out.println(gridComponentes[x][y].getNombre());
        if (gridComponentes[x][y] != null){
            Componente componente = gridComponentes[x][y];
            Dialog dialog = new Dialog(componente.getNombre(), skin);
            dialog.setColor(Color.BLACK);
            dialog.text(componente.getNombre() + "\n" + componente.getVida() + " HP\n");
            dialog.text("\n\n");
            dialog.text(componente.getBitacora());
            TextButton button = new TextButton("OK", skin);
            button.addCaptureListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    Gdx.input.setInputProcessor(grid.getStage());
                }
            });
            dialog.button(button);
            dialog.show(labelStage);
            Gdx.input.setInputProcessor(labelStage);
            dialog.toFront();
        }
    }

    public boolean validarFin(){
        try{
            zombiesRestantes = generacionZombies.getzombiesRestantes();
        }catch(Exception NullPointerException){
            return false;
        }
        if (reliquia.getVida() <= 0 && muertos.contains(reliquia.getEntidad())){
           return true;
        }
        if (zombies.size() == 0 && zombiesRestantes == 0){
            return true;
        }
        return false;
    }

    public void wipeEntities(){
        for (EntidadMovible entidad : zombiesMovibles){
            entidad.stopEntidad();
        }
        for (EntidadMovible entidad : defensasMovibles){
            entidad.stopEntidad();
        }
        for (EntidadMovible entidad : muertos){
            entidad.stopEntidad();
        }
        zombiesMovibles.clear();
        defensasMovibles.clear();
        muertos.clear();
        for (int i = 0; i < 25; i++){
            for (int j = 0; j < 25; j++){
                gridComponentes[i][j] = null;
            }
        }
    }

    public void updatePrototipos(){
        if (nivel == 1)
            reliquia.setVida(300);
        nivel++;
        reliquia.setVida(reliquia.getVida() + 100);
        espaciosEjercitos = 20 + (nivel - 1) * 5;
        zombiesRestantes = espaciosEjercitos;
        ArrayList<ComponentePrototipo>[] componentes = manager.getComponents(nivel);
        prototipos = componentes[0];
        Random RandomGenerator = new Random();
        menu.setDefensasDisponibles(componentes[1]);
        menu.turnOn();
    }

    public void siguienteNivel(){
        if (reliquia.getVida() <= 0){
            reliquia.setVida(100);
            Dialog dialog = new Dialog("DERROTA", skin);
            dialog.setColor(Color.BLACK);
            dialog.text("Has perdido la partida. ");
            dialog.text("Desea saltarse el nivel?");
            TextButton button = new TextButton("OK", skin);
            button.addCaptureListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    Gdx.input.setInputProcessor(grid.getStage());
                }
            });
            dialog.button(button);
            dialog.show(labelStage);
            Gdx.input.setInputProcessor(labelStage);
            dialog.toFront();
            for (Componente componente : zombies){
                componente.getEntidad().stopEntidad();
            }
            generacionZombies.stopThread();
        }
        else{
            Dialog dialog = new Dialog("VICTORIA", skin);
            dialog.setColor(Color.BLACK);
            dialog.text("Has ganado la partida. ");
            TextButton button = new TextButton("OK", skin);
            button.addCaptureListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    Gdx.input.setInputProcessor(grid.getStage());
                }
            });
            dialog.button(button);
            dialog.show(labelStage);
            Gdx.input.setInputProcessor(labelStage);
            dialog.toFront();
            reliquia.setVida(100);
            for (Componente componente : zombies){
                componente.getEntidad().stopEntidad();
            }
            generacionZombies.stopThread();
            generacionZombies = null;
        }
        ArrayList<Componente> componentesUsados = new ArrayList<Componente>();
        muertos.remove(reliquia.getEntidad());
        reliquia.setVida(0);
        componentesUsados.add(reliquia);
        

        for (Componente comp : zombies){
            comp.setNombre(comp.getNombre() + "_" + (comp.getEntidad().getPosicionX() + 100 ) / 30 + "_" + comp.getEntidad().getPosicionY() / 30);
            componentesUsados.add(comp);
        }
        for (Componente comp : defensas){
            comp.setNombre(comp.getNombre() + "_" + (comp.getEntidad().getPosicionX() + 100 ) / 30 + "_" + comp.getEntidad().getPosicionY() / 30);
            componentesUsados.add(comp);
        }
        for (EntidadMovible ent : muertos){
            if (ent.getEntidad().getNombre() == "Reliquia")
                continue;
            if (ent.getPosicionX() == 99999){
                int i = 0;
                String nombre = ent.getEntidad().getNombre() + "_" + Integer.toString(i);
                while(true){
                    for (Componente agregado : componentesUsados){
                        if (agregado.getNombre() == nombre){
                            i++;
                            nombre = ent.getEntidad().getNombre() + "_" + Integer.toString(i);
                            continue;
                        }
                    }
                    break;
                }
            }
            componentesUsados.add(ent.getEntidad());
        }
        for (Componente comp : componentesUsados){
            System.out.println(comp.getNombre());
        }
        ventanaBitacora bitacorasVentana = new ventanaBitacora(componentesUsados);
        bitacorasVentana.setVisible(true);
        wipeEntities();
        updatePrototipos();
        this.reliquia = new DefensaBloque("Reliquia", "PNG", sprites, 300 + (nivel - 1) * 100, 0, 1, 1, 1, 0);
        this.reliquiaMovible = new EntidadMovible(new Texture(Gdx.files.internal(sprites.get(0))), 12 * 30 + 100, 12 * 30, this.batch, false, this, this.reliquia);
        reliquia.setEntidad(reliquiaMovible);
        reliquia.setPartida(this);
        this.addDefensa(reliquiaMovible);
        reliquiaMovible.setDestino(12 * 30 + 100, 12 * 30);
    }

    public void saveGame(){
        String nombreUsuario = menu.getNombreUsuario();
        if (nombreUsuario == null){
            Dialog dialog = new Dialog("ERROR", skin);
            dialog.setColor(Color.BLACK);
            dialog.text("No se ha ingresado un nombre de usuario.");
            TextButton button = new TextButton("OK", skin);
            button.addCaptureListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    Gdx.input.setInputProcessor(grid.getStage());
                }
            });
            dialog.button(button);
            dialog.show(labelStage);
            Gdx.input.setInputProcessor(labelStage);
            dialog.toFront();
            return;
        }
        String currentDir = System.getProperty("user.dir");
        File filename = new File(currentDir + "/Saves/" + nombreUsuario + ".txt");
        try{
            filename.createNewFile();
        }catch(IOException e){
            e.printStackTrace();
        }
        if (filename.exists()){
            FileWriter writer;
            try{
                writer = new FileWriter(filename);
                writer.write(Integer.toString(nivel) + "\n");
                writer.close();
            }catch(IOException e){
                e.printStackTrace();
            }
        }
    }

    public void loadGame(){
        String nombreUsuario = menu.getNombreUsuario();
        if (nombreUsuario == null){
            Dialog dialog = new Dialog("ERROR", skin);
            dialog.setColor(Color.BLACK);
            dialog.text("No se ha ingresado un nombre de usuario.");
            TextButton button = new TextButton("OK", skin);
            button.addCaptureListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    Gdx.input.setInputProcessor(grid.getStage());
                }
            });
            dialog.button(button);
            dialog.show(labelStage);
            Gdx.input.setInputProcessor(labelStage);
            dialog.toFront();
            return;
        }
        String currentDir = System.getProperty("user.dir");
        File filename = new File(currentDir + "/Saves/" + nombreUsuario + ".txt");
        if (filename.exists()){
            Scanner fileReader;
            int nivelGuardadoInt;
            try {
                fileReader = new Scanner(filename);
                String nivelGuardado = "";
                while (fileReader.hasNextLine()){
                   nivelGuardado = fileReader.nextLine().trim();
                   break;
                }
                nivelGuardadoInt = Integer.parseInt(nivelGuardado);
                System.out.println(nivelGuardadoInt);  
                if (nivelGuardadoInt > nivel){
                    while (nivelGuardadoInt > nivel){
                        updatePrototipos();
                    }
                }else if (nivelGuardadoInt < nivel){
                    nivel = 1;
                    updatePrototipos();
                }
                Random RandomGenerator = new Random();
                for (ComponentePrototipo prototipo : prototipos){
                    for(int i = 0; i < nivel; i++){
                        prototipo.setNivel(prototipo.getNivel() + 1);
                        System.out.println(prototipo.getVida());
                        float factor = (float)(((float)RandomGenerator.nextInt(15)+105)/100);
                        prototipo.setVida(Math.round(prototipo.getVida() * factor));
                        prototipo.setCantidadGolpes(Math.round(prototipo.getCantidadGolpes() * factor));
                        if (prototipo instanceof MedianoAlcancePrototipo)
                            ((MedianoAlcancePrototipo)prototipo).setAlcance(Math.round(((MedianoAlcancePrototipo)prototipo).getAlcance() * factor));
                    }
                }
                reliquia.setVida(300 + (nivel - 1) * 100);
                try{
                    wipeEntities();
                }catch (NullPointerException e){
                    System.out.println("No hay entidades que borrar");
                }

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }
}

