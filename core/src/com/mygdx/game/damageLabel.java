package com.mygdx.game;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class damageLabel extends Label{

    private int x;
    private int y;
    private float time = 60;

    public damageLabel(CharSequence text, Skin skin, int x, int y) {
        super(text, skin);
        this.x = x;
        this.y = y;
        super.setPosition(x, y);
    }

    public void update(){
        time--;
        if (time <= 0){
            this.remove();
        }
    }
    
}
