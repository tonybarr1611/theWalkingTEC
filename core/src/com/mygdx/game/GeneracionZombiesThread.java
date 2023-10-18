package com.mygdx.game;

import java.util.ArrayList;
import java.util.Random;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mycompany.gestorcomponentes.ComponentePrototipo;
import com.mycompany.gestorcomponentes.Componentes.Zombies.ZombieChoquePrototipo;
import com.mycompany.gestorcomponentes.Componentes.Zombies.ZombieMedianoAlcancePrototipo;
import com.mygdx.game.Componentes.Zombies.*;

public class GeneracionZombiesThread extends Thread{
    Partida partida;
    int zombiesRestantes;
    ArrayList<ComponentePrototipo> prototipos;
    SpriteBatch batch;
    Texture[] sprites;
    
    GeneracionZombiesThread(Partida partida, SpriteBatch batch, int zombiesRestantes, ArrayList<ComponentePrototipo> prototipos, Texture[] sprites){
        this.partida = partida;
        this.batch = batch;
        this.zombiesRestantes = zombiesRestantes;
        this.prototipos = prototipos;
        this.sprites = sprites;
    }

    private Class<? extends Componente> getZombie(String clase){
        for (zombiesEnum zombie : zombiesEnum.values()){
            if (zombie.toString().contentEquals(clase))
                return zombie.getZombie();
        }
        return null;
    }

    private void generador(){
        Random rand = new Random();
        int posib = rand.nextInt(400);
        if (posib % 3 == 0){
            int x = rand.nextInt(25);
            int y = rand.nextInt(25);
            if (x > 2 && x < 22 && y > 2 && y < 22){
                return;
            }
            x = x * 30 + 100;
            y = y * 30;
            System.out.println(x + " " + y);
            int index = rand.nextInt(prototipos.size());
            String clase = prototipos.get(index).getClass().toString();
            while (clase.contains("."))
                clase = clase.substring(clase.indexOf(".")+1);
            clase = clase.toUpperCase();
            Class<? extends Componente> claseZombie = getZombie(clase);
            Componente zombie;
            ComponentePrototipo prototipo = prototipos.get(index);
            if (zombiesRestantes - prototipo.getCampos() < 0)
                return;
            try{
                int alcance = 0;
                if (prototipo instanceof ZombieMedianoAlcancePrototipo)
                    alcance = ((ZombieMedianoAlcancePrototipo) prototipo).getAlcance();
                if (prototipo instanceof ZombieChoquePrototipo)
                    alcance = ((ZombieChoquePrototipo) prototipo).getRango();
                Texture tex = sprites[index];
                zombie = claseZombie.getDeclaredConstructor(String.class, String.class, ArrayList.class, int.class, int.class, int.class, int.class, int.class, int.class).newInstance(prototipo.getNombre(), prototipo.getTipoApariencia(), prototipo.getSprites(), prototipo.getVida(), prototipo.getCantidadGolpes(), prototipo.getNivel(), prototipo.getCampos(), prototipo.getNivelAparicion(), alcance);
                EntidadMovible entidad = new EntidadMovible(tex, x, y, batch, false, partida, zombie);
                zombie.setEntidad(entidad);
                zombie.setPartida(partida);
                if (partida.addZombie(entidad)){
                    // entidad.setDestino(400, 450);
                    entidad.startEntidad();
                    System.out.println("Se agrego el zombie");
                }
                System.out.println(prototipo.getCampos());
                zombiesRestantes = zombiesRestantes - prototipo.getCampos();
            }catch(Exception e){
                System.out.println("Error al crear el zombie");
            }finally{
                try{
                    Thread.sleep(2000);
                }catch(Exception e){
                    System.out.println("Error al dormir el thread");
                }
            }
        }
    }

    public void run(){
        boolean flag = true;
        while (flag){
            generador();
            if (zombiesRestantes <= 0)
                flag = false;
        }
    }
}

enum zombiesEnum{
    ZOMBIEAEREOPROTOTIPO (ZombieAereo.class),
    ZOMBIECHOQUEPROTOTIPO (ZombieChoque.class),
    ZOMBIEDECONTACTOPROTOTIPO (ZombieDeContacto.class),
    ZOMBIEMEDIANOALCANCEPROTOTIPO (ZombieMedianoAlcance.class);

    private Class<? extends Componente> zombie;

    private zombiesEnum(Class<? extends Componente> zombie){
        this.zombie = zombie;
    }

    public Class<? extends Componente> getZombie(){
        return zombie;
    }
}