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
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class TheWalkingTec extends ApplicationAdapter {
	private SpriteBatch batch;
	private OrthographicCamera camera;
	private FitViewport viewport;	
	Animation<TextureRegion> animation;
	private GameGrid grid;
	EntidadMovible zombie;
	int x = 0;
	int y = 0;
	ArrayList<EntidadMovible> zombies = new ArrayList<EntidadMovible>();

	
	@Override
	public void create () {
		// SpriteBatch is used to draw 2D images
		batch = new SpriteBatch();
		zombie = new EntidadMovible(new Texture("zombie.png"), 100, 0, batch, true);
		// animation = GifDecoder.loadGIFAnimation(Animation.PlayMode.LOOP, Gdx.files.internal("zombie.gif").read());
		// We initialize the camera
		camera = new OrthographicCamera();
		viewport = new FitViewport(850, 850, camera);
		camera.setToOrtho(false, (float)850, (float)850);
		camera.translate(0, 0);
		batch.setProjectionMatrix(camera.combined);
		camera.update();	
		// We initialize the grid
		grid = new GameGrid(batch, viewport);
		// We initialize the zombie
		for (int i = 0; i < 10; i++) {
			zombies.add(new EntidadMovible(new Texture("zombie.png"), x, y, batch, i % 2 == 0));
			x += 50;
			y += 50;
			zombies.get(i).setDestino(829, 450);
			zombies.get(i).startEntidad();
		}
	}
	float ratio = 850 / 850;

	@Override
	public void render () {
		// We clear the screen
		ScreenUtils.clear(0, 0, 0, 1);
		// We update the camera
		camera.update();
		// We render the grid
		grid.render();
		// We draw the zombie
		batch.begin();
		for (int i = 0; i < 10; i++) {
			zombies.get(i).draw(batch);
			if (zombies.get(i).getPosicionX() == zombies.get(i).getDestinoX() && zombies.get(i).getPosicionY() == zombies.get(i).getDestinoY()) {
				if (zombies.get(i).getDestinoX() == 829)
					zombies.get(i).setDestino(0, 0);
				else
					zombies.get(i).setDestino(829, 600);
			}
		}
		batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		grid.dispose();
	}
}
