package com.mygdx.game;

import java.util.ArrayList;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.mycompany.gestorcomponentes.ComponentePrototipo;
import com.mygdx.game.Componentes.Defensa.DefensaBloque;

public class TheWalkingTec extends ApplicationAdapter {
	private SpriteBatch batch;
	private OrthographicCamera camera;
	private FitViewport viewport;	
	Animation<TextureRegion> animation;
	private GameGrid grid;
	EntidadMovible zombie;
	int x = 100;
	int y = 0;
	ArrayList<EntidadMovible> zombies = new ArrayList<EntidadMovible>();
	ArrayList<EntidadMovible> defensas = new ArrayList<EntidadMovible>();
	ArrayList<damageLabel> labels = new ArrayList<damageLabel>();
	Partida partida;
	MenuOpciones menu;
	Stage labelStage;

	
	@Override
	public void create () {
		componentManager manager = new componentManager();
		ArrayList<ComponentePrototipo>[] componentes = manager.getComponents(15);
		System.out.println("hereher");
		System.out.println(componentes[0].size());
		System.out.println(componentes[1].size());
		// SpriteBatch is used to draw 2D images
		batch = new SpriteBatch();
		// We initialize the camera
		camera = new OrthographicCamera();
		viewport = new FitViewport(850, 850, camera);
		camera.setToOrtho(false, (float)850, (float)850);
		camera.translate(0, 0);
		batch.setProjectionMatrix(camera.combined);
		camera.update();	
		// We initialize the grid
		labelStage = new Stage(viewport, batch);
		partida = new Partida(batch, grid, 1, componentes[0], defensas, zombies, labelStage, manager, null);
		grid = new GameGrid(batch, viewport, partida);
		partida.setGrid(grid);
		// We initialize the zombie
		// partida.startGame();
		menu = new MenuOpciones(grid.getStage(), batch, partida);
		menu.create();
		partida.setMenu(menu);
		Array<String> defensas = new Array<String>();
		defensas.add("Bloque");
		defensas.add("Arma");
		menu.setDefensasDisponibles(componentes[1]);
	}

	@Override
	public void render () {
		// We clear the screen
		ScreenUtils.clear(0, (float)0.1, 0, 1);
		// We update the camera
		camera.update();
		// We render the grid
		menu.renderMenu();
		grid.render();
		// We draw the zombie
		batch.begin();
		for (int i = 0; i < zombies.size(); i++){
			zombies.get(i).draw(batch);
			zombies.get(i).getEntidad().setCantidadGolpes(100);
		}
		for (int i = 0; i < defensas.size(); i++){
			// defensas.get(i).getEntidad().setCantidadGolpes(50);
			defensas.get(i).draw(batch);
		}
		batch.end();
		Actor[] actors = labelStage.getActors().items;
		for (int i = 0; i < actors.length; i++) {
			if (actors[i] instanceof damageLabel){
				damageLabel label = (damageLabel)actors[i];
				label.update();
			}
		}
		labelStage.act(Gdx.graphics.getDeltaTime());
		labelStage.draw();
		if (partida.validarFin())
			partida.siguienteNivel();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		grid.dispose();
	}
}
