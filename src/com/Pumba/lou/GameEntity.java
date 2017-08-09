/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.Pumba.lou;

/**
 *
 * @author Stefan
 */


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import java.io.Serializable;

 public class GameEntity implements Serializable{
      private int x;
      private int y;
      float dt;
      transient Texture t;
      transient Rectangle r;
     
      public GameEntity(){}



public int getX(){
   return x;
}
public void setX(int x){
   this.x = x;
}

public int getY(){
   return y;
}
public void setY(int y){
   this.y = y;
}
public Texture getT(){
  return t;
}
public void setT(Texture t){
  this.t = t;
}
public void move(int x,int y,float dt){   ////////dt is deltatime
   this.dt = dt;
   if(dt > .75f){
     if(this.x != x && this.y != y){
       if(this.x != x){
          setX(this.x + 1);
          sethitboxX(getX());
       }
       if(this.y != y){
         setY(this.y + 1);
          sethitboxY(getY());
       }
     }
   }
   
  
}
public void setHitbox(int x,int y,int w,int h){
   r = new Rectangle();
   r.set(x, y, w, h);
  } 
public void sethitboxX(int x){
   r.x = x;
}
public void sethitboxY(int y){
   r.y = y;
}
 }
