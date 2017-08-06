/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.Pumba.lou;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import java.io.Serializable;
import java.util.ArrayList;


public class map implements Serializable{
   ArrayList<Tile> tList = new ArrayList<Tile>();
   
   
   
   int width;
   int height;
   int tilewidth;
   
   public map(){}
   public void render(SpriteBatch sb,ShapeRenderer sr,OrthographicCamera camera){
      Gdx.gl.glClearColor(1, 1, 1, 1);
	 Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
         sr.setProjectionMatrix(camera.combined);
          sr.begin(ShapeRenderer.ShapeType.Line);
         sr.setColor(Color.BLACK);
        
         sr.end();
         
   }
          public void rendermap(SpriteBatch sb,ShapeRenderer sr) {
        
       for(int i = 0; i < tList.size() ; i++){
           // 
          sr.box(tList.get(i).x, tList.get(i).y, 0, tList.get(i).rwidth ,  tList.get(i).rwidth, 0);
        
          if(tList.get(i).isSet == true){
              sb.begin();
             
              sb.draw(this.tList.get(i).t  , this.tList.get(i).x, this.tList.get(i).y);
                
          sb.end();} else {}
       
       }   
    }
       
       public Tile getTile(int i){
         Tile t = tList.get(i);
         return t; }
       }
