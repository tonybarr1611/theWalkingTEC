package com.mygdx.game;

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
        tex = new Texture("zombie.png");
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
                DragAndDrop.Payload payload = new DragAndDrop.Payload();
                payload.setDragActor(draggableImage);
                dragAndDrop.setDragActorPosition(getActor().getWidth()/2, -getActor().getHeight()/2);
                return payload;
            }

            @Override
            public void dragStop(InputEvent inputevent, float x, float y, int pointer, Payload payload, Target target) {
                System.out.println("x: " + getActor().getX() + " y: " + getActor().getY());
                int x_casilla = Math.round((getActor().getX() - 100) / 30);
                int y_casilla = Math.round(getActor().getY() / 30);
                System.out.println("x_casilla: " + x_casilla + " y_casilla: " + y_casilla);
                if (x_casilla < 0 || x_casilla > 24 || y_casilla < 0 || y_casilla > 24){
                    dragAndDrop.setDragActorPosition(100, 700);
                    System.out.println("No se puede colocar en esa casilla");
                    return;
                }
                if (gridComponentes[x_casilla][y_casilla] == null){
                    DefensaBloque defensa = new DefensaBloque();
                    EntidadMovible entidad = new EntidadMovible(tex, x_casilla, y_casilla, batch, true, partida, defensa);
                    defensa.setEntidad(entidad);
                    defensa.setPartida(partida);
                    entidad.setPosicion(x_casilla, y_casilla);
                    entidad.setPosicionReal(getActor().getX(), getActor().getY());
                    partida.addDefensa(entidad);
                    entidad.startEntidad();
                    dragAndDrop.setDragActorPosition(100, 700);
                }
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
    }
}
