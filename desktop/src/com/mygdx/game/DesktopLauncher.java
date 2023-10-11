package com.mygdx.game;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.mygdx.game.TheWalkingTec;

// Please note that on macOS your application needs to be started with the -XstartOnFirstThread JVM argument
public class DesktopLauncher {
	public static void main (String[] arg) {
		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
		config.setForegroundFPS(60);
		config.setTitle("theWalkingTec");
		config.setWindowedMode(850, 850);
		config.setResizable(false);
		config.setWindowIcon("grassTexture1.jpg");
		new Lwjgl3Application(new TheWalkingTec(), config);
	}
}
