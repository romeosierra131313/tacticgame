/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.Pumba.lou;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import java.io.Serializable;
import java.util.ArrayList;


public class map implements Serializable{
   ArrayList<Tile> tList = new ArrayList<Tile>();
    
   
   
   
   int width;
   int height;
   int tilewidth;
   
   public map(){}
   public void render(SpriteBatch sb,ShapeRenderer sr,OrthographicCamera camera){
      
         sr.setProjectionMatrix(camera.combined);
        
         sr.setColor(Color.BLACK);
        
         
         
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
       public int getMapSize(){
         return tList.size();
       }
       }
