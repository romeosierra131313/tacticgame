/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.Pumba.lou;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;

/**
 *
 * @author Stefan
 */
public class PixmapEditor {
    Pixmap pixmap;
    Texture pixmaptex;
    Vector3 v3;
    public PixmapEditor(AssetManager ass) {
       
        pixmap = new Pixmap(Gdx.files.internal("tree1.png"));
        pixmaptex = new Texture( pixmap );

      
    }
    public void render(SpriteBatch sb){
       sb.draw(pixmaptex, 0, 0);
    
    }
    public Texture  dosomething(){
      pixmap.setColor( 0, 1, 0, 1 );
        pixmap.fillCircle(16, 5, 10);
        pixmaptex = new Texture( pixmap );
        
        return pixmaptex;
    }
  


}
