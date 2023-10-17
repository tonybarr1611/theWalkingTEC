package com.mygdx.game;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
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
import com.mygdx.game.Componentes.Defensa.DefensaBloque;

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
    // Espacios ejercito
    int espaciosEjercitoOcupados = 0;
    int espaciosEjercitoDisponibles = 20;
    Label espaciosEjercito;

    dragabbleImage dragabbleImagee;


    public MenuOpciones(Stage stage, SpriteBatch batch, Partida partida){
        this.stage = stage;
        this.batch = batch;
        this.partida = partida;
        this.espaciosEjercitoDisponibles = partida.getEspaciosEjercitos();
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
        dragabbleImagee = new dragabbleImage(draggableImage, dragAndDrop, partida, defensas, batch, tex);
        labels = new Label[4];
        labels[0] = new Label("Iniciar", skin);
        labels[1] = new Label("Guardar", skin);
        labels[2] = new Label("Cargar", skin);
        labels[3] = new Label("Salir", skin);

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

        botonIniciar.addListener(new com.badlogic.gdx.scenes.scene2d.utils.ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                System.out.println("Iniciar");
                updateDraggable(new Texture("zombie.png"));
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
                System.out.println("Salir");
            }
        }
        );

    }

    public void setGridComponentes(Componente[][] gridComponentes){
        this.gridComponentes = gridComponentes;
    }

    public void setDefensasDisponibles(Array<String> arrayDefensas) {
        defensasDisponibles.setItems(arrayDefensas);
        defensasDisponibles.setSelectedIndex(0);
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
        dragabbleImagee = new dragabbleImage(draggableImage, dragAndDrop, partida, defensas, batch, texNueva);
        dragAndDrop.addSource(dragabbleImagee);
    }
}
