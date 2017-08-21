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


import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import java.io.Serializable;

 public class GameEntity implements Serializable{
      private int x;
      private int y;
      float dt;
      transient Texture t;
      transient Rectangle r;
      private int stamina = 6;
      private Boolean waitMove = false;
      private Boolean isMoving = false;
     
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
public void move(float x,float y,float dt){   ////////dt is deltatime
   this.dt = dt;
   if(dt > .1f){
     if(this.x != x || this.y != y){
       if(this.x != x){
          setX(this.x - 1);
          sethitboxX(getX());
       }
       if(this.y != y){
         setY(this.y - 1);
          sethitboxY(getY());
       }
     }
   }
   if(this.x == x && this.y == y){isMoving = false;}
  
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
public void setStamina(int i){
  stamina = i;
}
public int getStamina(){
  return stamina;
}
public Vector2 getLocation(){
   Vector2 v2 = new Vector2();
   return v2;
}
public void setMoving(){
isMoving = true;
waitMove = false;
}
public void setWaiting(){
  waitMove = true;
}
public Boolean getWaiting(){
  return waitMove;
}
public Boolean getMoving(){
  return isMoving;
}
 }
