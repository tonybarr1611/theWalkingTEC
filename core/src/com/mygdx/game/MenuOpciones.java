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
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
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
    Texture tex;
    ArrayList<EntidadMovible> defensas = new ArrayList<EntidadMovible>();


    public MenuOpciones(Stage stage, SpriteBatch batch, Partida partida){
        this.stage = stage;
        this.batch = batch;
        this.partida = partida;
        Skin skin = new Skin(Gdx.files.internal("theWalkingTEC\\core\\src\\com\\mygdx\\game\\Skins\\Glassy\\glassy-ui.json"));
        Skin skinBotones = new Skin(Gdx.files.internal("theWalkingTEC\\core\\src\\com\\mygdx\\game\\Skins\\Arcade\\arcade-ui.json"));
        defensasDisponibles = new SelectBox<String>(skin);
        botonIniciar = new Button(skinBotones);
        botonGuardar = new Button(skinBotones);
        botonCargar = new Button(skinBotones);
        botonSalir = new Button(skinBotones);
        nombreUsuario = new TextField("", skin);
        tex = new Texture("caja.png");
        draggableImage = new Image(tex);
        draggableImage.setSize(30, 30);
        dragAndDrop = new DragAndDrop();
        gridComponentes = partida.getGridComponentes();

        stage.addActor(defensasDisponibles);
        defensasDisponibles.setX(100);
        defensasDisponibles.setY(600);
        defensasDisponibles.setWidth(150);
        stage.addActor(botonIniciar);
        botonIniciar.setX(100);
        botonIniciar.setY(100);
        stage.addActor(botonGuardar);
        botonGuardar.setX(100);
        botonGuardar.setY(200);
        stage.addActor(botonCargar);
        botonCargar.setX(100);
        botonCargar.setY(300);
        stage.addActor(botonSalir);
        botonSalir.setX(100);
        botonSalir.setY(400);
        stage.addActor(nombreUsuario);
        nombreUsuario.setX(100);
        nombreUsuario.setY(500);
        stage.addActor(draggableImage);
        draggableImage.setX(100);
        draggableImage.setY(700);
    }

    public void create(){
        dragAndDrop.addSource(new DragAndDrop.Source(draggableImage) {
            @Override
            public Payload dragStart(InputEvent inputevent, float x, float y, int pointer) {
                System.out.println("Drag start");
                DragAndDrop.Payload payload = new DragAndDrop.Payload();
                payload.setDragActor(draggableImage);
                dragAndDrop.setDragActorPosition(getActor().getWidth()/2, -getActor().getHeight()/2);
                return payload;
            }

            @Override
            public void dragStop(InputEvent inputevent, float x, float y, int pointer, Payload payload, Target target) {
                int pos_x = (int)getActor().getX();
                int pos_y = (int)getActor().getY();
                int casilla_x = Math.round((pos_x-100)/30);
                int casilla_y = Math.round((pos_y)/30);
                System.out.println("Posicion " + pos_x + " " + pos_y);
                System.out.println("Casillas " + casilla_x + " " + casilla_y);

                DefensaBloque defensa = new DefensaBloque("Dragged", "GIF", new ArrayList<String>(), 100, 1, 1, 1, 1);
                EntidadMovible entidad = new EntidadMovible(tex, pos_x, pos_y, batch, true, partida, defensa);
                defensa.setEntidad(entidad);
                defensa.setPartida(partida);
                if (partida.addDefensa(entidad)){
                    defensas.add(entidad);
                    entidad.setX(casilla_x*30+100);
                    entidad.setY(casilla_y*30);
                    System.out.println("done");
                }
                dragAndDrop.setDragActorPosition(100, 700);
                draggableImage.setPosition(100, 700);
            }

        });
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
}
