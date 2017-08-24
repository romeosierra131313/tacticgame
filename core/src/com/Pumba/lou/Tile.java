/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.Pumba.lou;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import java.io.Serializable;

/**
 *
 * @author Stefan
 */
public class Tile implements Serializable {
     String type = "null";
     
      transient Rectangle r;
      int rwidth;
      int rheight;
      int x;
      int y;
      transient Texture t;
      Boolean isPathable=true;
      Boolean isSet = false;
      
      
    public Tile(int x, int y,int width,int height){
      this.rwidth = width;
      this.rheight = height;
      this.x =x;
      this.y = y;
      r = new Rectangle();
      r.set(x, y, width, height);
      
      
      
    }
    
    public void setTile(String type,Texture t){
       this.type = type;
       this.t = t;
    }
    public void setTexture(Texture t){
     
     this.t = t;
    }
    public Texture getTexture(){
     return t;
    }
    public void setX(int x){
    this.x = x;
    }
    public int getX(){return x;}
    public void setY(int y){
    this.y = y;
    }
    public int getY(){return y;}
}
