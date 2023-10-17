package com.mygdx.game;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop.Payload;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop.Target;
import com.mygdx.game.Componentes.Defensa.DefensaBloque;

public class dragabbleImage extends DragAndDrop.Source{
    Image image;
    DragAndDrop dragAndDrop;
    SpriteBatch batch;
    Partida partida;
    ArrayList<EntidadMovible> defensas;
    Texture tex;

    public dragabbleImage(Image image, DragAndDrop dragAndDrop, Partida partida, ArrayList<EntidadMovible> defensas, SpriteBatch batch, Texture tex) {
        super(image);
        this.image = image;
        this.dragAndDrop = dragAndDrop;
        this.partida = partida;
        this.defensas = defensas;
        this.batch = batch;
        this.tex = tex;

    }

    @Override
    public Payload dragStart(InputEvent event, float x, float y, int pointer) {
        System.out.println("Drag start");
        DragAndDrop.Payload payload = new DragAndDrop.Payload();
        payload.setDragActor(this.getActor());
        dragAndDrop.setDragActorPosition(getActor().getWidth()/2, -getActor().getHeight()/2);
        image.setPosition(getActor().getWidth()/2, -getActor().getHeight()/2);
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
            // Espacio reservado para zombies
            if (!(casilla_x < 3 || casilla_x > 21 || casilla_y < 3 || casilla_y > 21)){
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
            }
            dragAndDrop.setDragActorPosition(350, 780);
            image.setPosition(350, 780);
            
        };

    
}
