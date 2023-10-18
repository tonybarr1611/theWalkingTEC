package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class GameGrid{
   	private SpriteBatch batch;
    private Texture img1, img2;	
	private Stage stage;
	private FitViewport viewport;	
	private Drawable grass1, grass2;

	private final float squareWidth = (float)30;
	private final float squareHeight = (float)30;

    GameGrid(SpriteBatch batch, FitViewport viewport){
        this.batch = batch;
        this.viewport = viewport;
		// We load the images from the assets folder
		img1 = new Texture(Gdx.files.internal("grassTexture1.jpg"));	
		img2 = new Texture(Gdx.files.internal("grassTexture2.jpg"));
        // Initialize the stage
		stage = new Stage(viewport, batch);
		Gdx.input.setInputProcessor(stage);
		// We create the grass textures fot the buttons
		grass1 = new TextureRegionDrawable(img1);
		grass2 = new TextureRegionDrawable(img2);
        // Create buttons in a grid like fashion
		for(int y = 0; y < 25; y++){
			for(int x = 0; x < 25; x++){
				if ((x + y) % 2 == 0)
					stage.addActor(new ImageButton(grass1, grass2));
				else
					stage.addActor(new ImageButton(grass2, grass1));
			}
 		}	
    };

    public void render(){
        // We update the buttons
		for(int y = 0; y < 25; y++){
			for(int x = 0; x < 25; x++){
				Actor actors = stage.getActors().get(x + y * 25);
				if (actors instanceof ImageButton){
					ImageButton button = (ImageButton)actors;
					button.setX(x * squareWidth + 100);
					button.setY(y * squareHeight);
					button.setWidth(30);
					button.setHeight(30);
				}
			}
		}
        // We draw the stage
		stage.act(Gdx.graphics.getDeltaTime());
		stage.draw();
    }

    public void dispose(){
        img1.dispose();
        img2.dispose();
        stage.dispose();
    }

	public Stage getStage(){
		return stage;
	}
}
