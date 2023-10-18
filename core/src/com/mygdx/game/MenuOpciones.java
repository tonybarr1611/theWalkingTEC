package com.mygdx.game;

import java.util.ArrayList;
import java.util.Random;
import java.util.random.RandomGenerator;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop.Payload;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop.Target;
import com.badlogic.gdx.utils.Array;
import com.mycompany.gestorcomponentes.ComponentePrototipo;
import com.mygdx.game.dragabbleImage;

public class MenuOpciones {
    // Stage for the menu
    Stage stage;
    // Dropdown menu
    SelectBox<String> defensasDisponibles;
    // Start game button
    Button botonIniciar;
    // Save game button
    Button botonGuardar;
    // Load game button
    Button botonCargar;
    // Exit game button
    Button botonSalir;
    // Buttons labels array
    Label[] labels = new Label[4];
    // Username text field
    TextField nombreUsuario;
    // Dragabble sprite
    Image draggableImage;
    // Drag and drop object
    DragAndDrop dragAndDrop;
    // GameGrid object
    private Componente[][] gridComponentes;
    // Batch
    SpriteBatch batch;
    // Partida object
    Partida partida;
    // Texture for the draggable sprite
    Texture tex;
    // Array of defensas
    ArrayList<EntidadMovible> defensas = new ArrayList<EntidadMovible>();
    // Array prototipos
    ArrayList<ComponentePrototipo> prototipos = new ArrayList<ComponentePrototipo>();
    ComponentePrototipo currentPrototipo;
    // Espacios ejercito
    int espaciosEjercitoOcupados = 0;
    int espaciosEjercitoDisponibles = 20;
    Label espaciosEjercito, nivel;

    dragabbleImage dragabbleImagee;


    public MenuOpciones(Stage stage, SpriteBatch batch, Partida partida){
        this.stage = stage;
        this.batch = batch;
        this.partida = partida;
        this.espaciosEjercitoDisponibles = partida.getEspaciosEjercitos();
        this.currentPrototipo = null;
        Skin skin = new Skin(Gdx.files.internal("theWalkingTEC\\core\\src\\com\\mygdx\\game\\Skins\\Glassy\\glassy-ui.json"));
        Skin skinBotones = new Skin(Gdx.files.internal("theWalkingTEC\\core\\src\\com\\mygdx\\game\\Skins\\Arcade\\arcade-ui.json"));
        defensasDisponibles = new SelectBox<String>(skin);
        botonIniciar = new Button(skinBotones);
        botonGuardar = new Button(skinBotones);
        botonCargar = new Button(skinBotones);
        botonSalir = new Button(skinBotones);
        nombreUsuario = new TextField("", skin);
        tex = new Texture("zombie.png");
        draggableImage = new Image(tex);
        draggableImage.setSize(30, 30);
        dragAndDrop = new DragAndDrop();
        dragabbleImagee = new dragabbleImage(this, draggableImage, dragAndDrop, partida, defensas, batch, tex, currentPrototipo);
        labels = new Label[4];
        labels[0] = new Label("Iniciar", skin);
        labels[1] = new Label("Guardar", skin);
        labels[2] = new Label("Cargar", skin);
        labels[3] = new Label("Salir", skin);
        nivel = new Label("Nivel: ", skin);

        gridComponentes = partida.getGridComponentes();
        espaciosEjercito = new Label("Espacios ejercito: " + espaciosEjercitoOcupados + "/" + espaciosEjercitoDisponibles, skin);
    }
    
    public void create(){
        stage.addActor(defensasDisponibles);
        defensasDisponibles.setX(450);
        defensasDisponibles.setY(780);
        defensasDisponibles.setWidth(150);
        stage.addActor(botonIniciar);
        botonIniciar.setX(10);
        botonIniciar.setY(100);
        stage.addActor(botonGuardar);
        botonGuardar.setX(10);
        botonGuardar.setY(200);
        stage.addActor(botonCargar);
        botonCargar.setX(10);
        botonCargar.setY(300);
        stage.addActor(botonSalir);
        botonSalir.setX(10);
        botonSalir.setY(400);
        stage.addActor(nombreUsuario);
        nombreUsuario.setX(10);
        nombreUsuario.setY(780);
        stage.addActor(draggableImage);
        draggableImage.setX(350);
        draggableImage.setY(780);
        stage.addActor(espaciosEjercito);
        espaciosEjercito.setX(620);
        espaciosEjercito.setY(770);
        espaciosEjercito.setWidth(150);
        espaciosEjercito.setHeight(50);
        dragAndDrop.addSource(dragabbleImagee);
        for (int i = 0; i < 4; i++) {
            stage.addActor(labels[i]);
            labels[i].setX(10);
            labels[i].setY(75 + i * 100);
        }
        stage.addActor(nivel);
        nivel.setX(620);
        nivel.setY(750);
        nivel.setWidth(150);
        nivel.setHeight(50);

        botonIniciar.addListener(new com.badlogic.gdx.scenes.scene2d.utils.ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                if (!(nombreUsuario.getText().length() > 0))
                    return;
                nombreUsuario.setDisabled(true);
                dragAndDrop.clear();
                defensasDisponibles.setDisabled(true);
                partida.startGame();
            }
        }
        );

        botonGuardar.addListener(new com.badlogic.gdx.scenes.scene2d.utils.ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                System.out.println("Guardar");
                updateDraggable(new Texture("caja.png"));
                
            }
        }
        );

        botonCargar.addListener(new com.badlogic.gdx.scenes.scene2d.utils.ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                System.out.println("Cargar");
            }
        }
        );

        botonSalir.addListener(new com.badlogic.gdx.scenes.scene2d.utils.ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                Gdx.app.exit();
            }
        }
        );

        defensasDisponibles.addListener(new com.badlogic.gdx.scenes.scene2d.utils.ChangeListener(){
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                System.out.println(defensasDisponibles.getSelected());
                for (int i = 0; i < prototipos.size(); i++){
                    if (prototipos.get(i).getNombre().contentEquals(defensasDisponibles.getSelected())){
                        currentPrototipo = prototipos.get(i);
                        updateDraggable(new Texture(currentPrototipo.getSprites().get(0)));
                        break;
                    }
                }
            }
        }
        );

    }

    public void setGridComponentes(Componente[][] gridComponentes){
        this.gridComponentes = gridComponentes;
    }

    public void renderMenu(){
        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
        batch.begin();
        for (int i = 0; i < defensas.size(); i++) {
            defensas.get(i).draw(batch);
        }
        batch.end();
    }

    private void updateDraggable(Texture texNueva){
        draggableImage.remove();
        draggableImage = new Image(texNueva);
        draggableImage.setSize(30, 30);
        draggableImage.setPosition(350, 780);
        stage.addActor(draggableImage);
        dragAndDrop.clear();
        dragAndDrop = new DragAndDrop();
        dragabbleImagee = new dragabbleImage(this, draggableImage, dragAndDrop, partida, defensas, batch, texNueva, currentPrototipo);
        dragAndDrop.addSource(dragabbleImagee);
    }

    public ArrayList<ComponentePrototipo> aumentarNivel(ArrayList<ComponentePrototipo> prototipos){
        Random RandomGenerator = new Random();
        for (ComponentePrototipo prototipo : prototipos) {
            prototipo.setNivel(prototipo.getNivel() + 1);
            prototipo.setVida(Math.round(prototipo.getVida() * ((RandomGenerator.nextInt(15)+105)/100)));
            prototipo.setCantidadGolpes(Math.round(prototipo.getCantidadGolpes() * ((RandomGenerator.nextInt(15)+105)/100)));
        }
        return prototipos;
    }

    public void setDefensasDisponibles(ArrayList<ComponentePrototipo> defensasDisponibles){
        prototipos.clear();
        this.prototipos = defensasDisponibles;
        defensas.clear();
        Array<String> defensas = new Array<String>();
        for (int i = 0; i < defensasDisponibles.size(); i++) {
            defensas.add(defensasDisponibles.get(i).getNombre());
        }
        this.prototipos = aumentarNivel(this.prototipos);

        if (defensas.size == 0)
            return;
        this.defensasDisponibles.setItems(defensas);
        this.defensasDisponibles.setSelectedIndex(0);
        this.currentPrototipo = defensasDisponibles.get(0);

        int _nivel = partida.getNivel();
        nivel.setText("Nivel: " + _nivel);
        espaciosEjercitoOcupados = 0;
        espaciosEjercitoDisponibles = 20 + (_nivel - 1) * 5;
        espaciosEjercito.setText("Espacios ejercito: " + espaciosEjercitoOcupados + "/" + espaciosEjercitoDisponibles);

    }

    public boolean verifyCampos(int campos){
        if (espaciosEjercitoOcupados + campos <= espaciosEjercitoDisponibles){
            espaciosEjercitoOcupados += campos;
            espaciosEjercito.setText("Espacios ejercito: " + espaciosEjercitoOcupados + "/" + espaciosEjercitoDisponibles);
            return true;
        }
        return false;
    }

    public void turnOn(){
        defensasDisponibles.setDisabled(false);
        nombreUsuario.setDisabled(false);
    }
}
