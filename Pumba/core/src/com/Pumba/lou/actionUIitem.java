/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.Pumba.lou;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

/**
 *
 * @author Stefan
 */
public class actionUIitem {
    
    Rectangle r;
    int x;
    int y ;
    int traveled = 0;
    String s;
    Vector2 rcentre;
    public actionUIitem(int place,int x,int y,String s){
        this.s = s;
    r = new Rectangle();
    r.set(x, (y+ (place*128)),( Gdx.graphics.getWidth()/10)*2, Gdx.graphics.getHeight()/22);
    rcentre = new Vector2();
    rcentre = r.getCenter(rcentre);
    }
    public actionUIitem(int x,int y,String s){
    this.s = s;
    r = new Rectangle();
    r.set(x,y ,(Gdx.graphics.getWidth()/10)*5, Gdx.graphics.getHeight()/2);
    rcentre = new Vector2();
    
    }
    public void render(SpriteBatch sb,ShapeRenderer sr,BitmapFont bf,OrthographicCamera camera){
        rcentre = r.getCenter(rcentre);
       sr.setColor(Color.PINK);
       sr.box(r.x, r.y, 0, r.width, r.height, 0);
        bf.setColor(Color.BLACK);
        bf.getData().setLineHeight(Gdx.graphics.getHeight()/10);
        bf.draw(sb, s, rcentre.x, rcentre.y);
    }
   
}
