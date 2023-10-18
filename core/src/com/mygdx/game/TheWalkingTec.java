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
		partida = new Partida(batch, grid, 1, componentes[0], defensas, zombies, labelStage);
		grid = new GameGrid(batch, viewport, partida);
		partida.setGrid(grid);
		zombie = new EntidadMovible(new Texture("zombie.png"), 490, 600, batch, true, partida, new DefensaBloque("Bloque", "Bloque", new ArrayList<String>(), 100, 1, 1, 1, 1, 1));
		// animation = GifDecoder.loadGIFAnimation(Animation.PlayMode.LOOP, Gdx.files.internal("zombie.gif").read());
		// We initialize the zombie
		for (int i = 0; i < 10; i++) {
			DefensaBloque defensa = new DefensaBloque("Bloque", "Bloque", new ArrayList<String>(), i, 1, 1, 1, 1, 1);
			EntidadMovible entidad = new EntidadMovible(new Texture("zombie.png"), x, y, batch, i % 2 == 0, partida, defensa);
			defensa.setEntidad(entidad);
			defensa.setPartida(partida);
			if (partida.addDefensa(entidad)){
				zombies.add(entidad);
				if (zombies.size() == 1)
					entidad.setDestino(829, 450);
				else 
					entidad.setDestino(830, 600);
				System.out.println("Se agrego el zombie" + i);
			}
			if (i % 2 == 0){
				x += 50;
				y += 50;
			}
			// zombies.get(i).startEntidad();
		}
		// partida.startGame();
		menu = new MenuOpciones(grid.getStage(), batch, partida);
		menu.create();
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
		zombie.draw(batch);
		for (int i = 0; i < zombies.size(); i++)
		zombies.get(i).draw(batch);
		for (int i = 0; i < defensas.size(); i++)
		defensas.get(i).draw(batch);
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
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		grid.dispose();
	}
}
