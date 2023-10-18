package com.mygdx.game;

import java.io.File;
import java.util.ArrayList;

import com.mycompany.gestorcomponentes.ComponentePrototipo;

public class componentManager extends EntitySerializer{
    private ArrayList<ComponentePrototipo> zombies;
    private ArrayList<ComponentePrototipo> defensas;

    public componentManager(){
        zombies = new ArrayList<ComponentePrototipo>();
        defensas = new ArrayList<ComponentePrototipo>();
        loadComponents();
        print();
    }

    private void loadComponents(){
        String currentDir = System.getProperty("user.dir");
        File folder = new File(currentDir + "/componentes");
        for (File file : folder.listFiles()){
            System.out.println(file.getName());
            if (file.getName().endsWith(".json")){
                try{
                    ComponentePrototipo componente = (ComponentePrototipo)super.readObject(file.toString());
                    if (componente.getNombre().contentEquals("Muro")){
                        defensas.add(componente);
                    }
                    else
                        zombies.add(componente);
                }catch(Exception e){
                    System.out.println(e.getMessage());
                    System.out.println("Error al cargar componente");
                }
            }
        }
    }

    public void print(){
        System.out.println("Zombies");
        for (ComponentePrototipo componente : zombies){
            System.out.println(componente.getNombre());
        }
        System.out.println("Defensas");
        for (ComponentePrototipo componente : defensas){
            System.out.println(componente.getNombre());
        }
    }

    public ArrayList<ComponentePrototipo>[] getComponents(int nivel){
        ArrayList<ComponentePrototipo>[] components = new ArrayList[2];
        components[0] = new ArrayList<ComponentePrototipo>();
        components[1] = new ArrayList<ComponentePrototipo>();
        for (ComponentePrototipo componente : zombies){
            if (componente.getNivelAparicion() <= nivel)
                components[0].add(componente);
            System.out.println(componente.getNombre() + " " + componente.getNivelAparicion());
        }
        for (ComponentePrototipo componente : defensas){
            if (componente.getNivelAparicion() <= nivel)
                components[1].add(componente);
            System.out.println(componente.getNombre() + " " + componente.getNivelAparicion());  
        }
        return components;
    }
}
