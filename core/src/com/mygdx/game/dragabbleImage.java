package com.mygdx.game;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop.Payload;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop.Target;
import com.mycompany.gestorcomponentes.Componentes.Defensa.DefensaImpactoPrototipo;
import com.mycompany.gestorcomponentes.Componentes.Defensa.DefensaMedianoAlcancePrototipo;
import com.mycompany.gestorcomponentes.Componentes.Defensa.DefensaAtaqueMultiplePrototipo;
import com.mygdx.game.Componentes.Defensa.*;
import com.mycompany.gestorcomponentes.ComponentePrototipo;


public class dragabbleImage extends DragAndDrop.Source{
    private MenuOpciones menu;
    private Image image;
    private DragAndDrop dragAndDrop;
    private SpriteBatch batch;
    private Partida partida;
    private ArrayList<EntidadMovible> defensas;
    private Texture tex;
    private ComponentePrototipo prototipo;

    public dragabbleImage(MenuOpciones menu, Image image, DragAndDrop dragAndDrop, Partida partida, ArrayList<EntidadMovible> defensas, SpriteBatch batch, Texture tex, ComponentePrototipo prototipo) {
        super(image);
        this.menu = menu;
        this.image = image;
        this.dragAndDrop = dragAndDrop;
        this.partida = partida;
        this.defensas = defensas;
        this.batch = batch;
        this.tex = tex;
        this.prototipo = prototipo;
    }

    private Class<? extends Componente> getDefensa(String clase){
        switch (clase) {
            case "DEFENSAAEREAPROTOTIPO":
                return defensasEnum.DEFENSAAEREAPROTOTIPO.getDefensa();
            case "DEFENSAATAQUEMULTIPLEPROTOTIPO":
                return defensasEnum.DEFENSAATAQUEMULTIPLEPROTOTIPO.getDefensa();
            case "DEFENSABLOQUEPROTOTIPO":
                return defensasEnum.DEFENSABLOQUEPROTOTIPO.getDefensa();
            case "DEFENSADECONTACTOPROTOTIPO":
                return defensasEnum.DEFENSADECONTACTOPROTOTIPO.getDefensa();
            case "DEFENSAIMPACTOPROTOTIPO":
                return defensasEnum.DEFENSAIMPACTOPROTOTIPO.getDefensa();
            case "DEFENSAMEDIANOALCANCEPROTOTIPO":
                return defensasEnum.DEFENSAMEDIANOALCANCEPROTOTIPO.getDefensa();
            default:
                break;
        }
        return null;
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
                // System.out.println(prototipo.getClass());
                String clase = prototipo.getClass().getSimpleName().toString();
                clase = clase.toUpperCase();
                System.out.println(clase);
                Class<? extends Componente> claseDefensa = getDefensa(clase);
                System.out.println(claseDefensa);
                Componente defensa;
                try{
                    int alcance = 0;
                    if (prototipo instanceof DefensaMedianoAlcancePrototipo)
                        alcance = ((DefensaMedianoAlcancePrototipo) prototipo).getAlcance();
                    if (prototipo instanceof DefensaImpactoPrototipo)
                        alcance = ((DefensaImpactoPrototipo) prototipo).getRango();
                    defensa = claseDefensa.getDeclaredConstructor(String.class, String.class, ArrayList.class, int.class, int.class, int.class, int.class, int.class, int.class).newInstance(prototipo.getNombre(), prototipo.getTipoApariencia(), prototipo.getSprites(), prototipo.getVida(), prototipo.getCantidadGolpes(), prototipo.getNivel(), prototipo.getCampos(), prototipo.getNivelAparicion(), alcance);
                }catch(Exception e){
                    return;
                }
                EntidadMovible entidad = new EntidadMovible(tex, pos_x, pos_y, batch, true, partida, defensa);
                defensa.setEntidad(entidad);
                defensa.setPartida(partida);
                if (menu.verifyCampos(defensa.getCampos()) && partida.addDefensa(entidad)){
                    defensas.add(entidad);
                    entidad.setX(casilla_x*30+100);
                    entidad.setY(casilla_y*30);
                    entidad.setDestino(casilla_x*30+100, casilla_y*30);
                    System.out.println("done");
                }
            }
            dragAndDrop.setDragActorPosition(350, 780);
            image.setPosition(350, 780);
            
        };

    
}

enum defensasEnum{
    DEFENSAAEREAPROTOTIPO (DefensaAerea.class),
    DEFENSAATAQUEMULTIPLEPROTOTIPO (DefensaAtaqueMultiple.class),
    DEFENSABLOQUEPROTOTIPO (DefensaBloque.class),
    DEFENSADECONTACTOPROTOTIPO (DefensaDeContacto.class),
    DEFENSAIMPACTOPROTOTIPO (DefensaImpacto.class),
    DEFENSAMEDIANOALCANCEPROTOTIPO (DefensaMedianoAlcance.class);

    private Class<? extends Componente> defensa;

    defensasEnum(Class<? extends Componente> defensa){
        this.defensa = defensa;
    }

    defensasEnum(){
        this.defensa = null;
    }

    public Class<? extends Componente> getDefensa(){
        return this.defensa;
    }
}