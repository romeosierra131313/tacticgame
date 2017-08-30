/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.Pumba.lou;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import java.util.ArrayList;


/**
 *
 * @author Stefan
 */
public class actionUI {
    ArrayList<actionUIitem> actionUIitems;
    
    public actionUI(){
    actionUIitems = new ArrayList<actionUIitem>();
    }
    public void render(SpriteBatch sb,ShapeRenderer sr,BitmapFont bf,OrthographicCamera camera){
        sr.set(ShapeRenderer.ShapeType.Line);
        sr.setColor(Color.RED);
        for (actionUIitem i : actionUIitems){
          i.render(sb, sr,bf, camera);
        }
    
    }
    public void addaction(actionUIitem i){
    actionUIitems.add(i);
    }
    public void removeaction(actionUIitem i){
    actionUIitems.remove(i);}
}
