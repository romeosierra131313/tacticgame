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
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import java.io.Serializable;

 public class GameEntity implements Serializable{
      //// common ///////
      private Boolean isEnemy ;
      private Boolean isTurnDone = false;
      String type;
      private int hp ;
      private int attack;
      private int x;
      private int y;
      
      ///unit specific
      
      private Boolean isDead = false;
      private Boolean hasAttacked = false;
      private Boolean hasCaptured = false;
      private Boolean isMoved = false;
     
     
      ////town specific
      private Boolean isTown = false;
      
      float dt;
      transient Texture t;
      transient Rectangle r;
      private int stamina = 3;
      private Boolean waitMove = false;
      private Boolean isMoving = false;
       Boolean North= false  ;
       Boolean South= false  ;
       Boolean East = false ;
       Boolean West = false ;
     
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
       if(this.x != x && East){
          setX(this.x + 2);
          sethitboxX(getX());
       } if(this.x != x && West){
          setX(this.x - 2);
          sethitboxX(getX());
        }
       if(this.y != y && North){
          setY(this.y + 2);
          sethitboxY(getY());
       } if(this.y != y && South){
          setY(this.y - 2);
          sethitboxY(getY());
       }
     }
   }
   if(this.x == x && this.y == y){//isMoving = false;
       
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
public void setStamina(int i){
  stamina = i;
}
public int getStamina(){
  return stamina;
}
public Vector2 getLocation(){
   Vector2 v2 = new Vector2(getX(),getY());
   return v2;
}
public void setMoving(){
isMoving = true;

}
public void resetsetMovingandWaiting(){
isMoving = false;    
waitMove = false;


}
public void setIsMoving(Boolean b){
   isMoving = b;
 }
public Boolean getWaiting(){
  return waitMove;
}
public void setWaiting(Boolean b){
   waitMove = b;
}
public Boolean getMoving(){
  return isMoving;
}
public void newEntity(int x, int y){
   setX(x);
   setY(y);

}
public void setHp(int hp){
  this.hp = hp;
}
public int getHp(){
  return hp;
}
public void setAttack(int attack){
 this.attack = attack;
}
public int getAttack(){
 return attack;
}
public void getAttacked(int attack){
    System.out.println(hp);
 setHp(getHp()-attack);
 if(hp <= 0){
   isDead = true;
 }
 System.out.println(hp);
}
public void setDead(Boolean b  ){
  isDead = b;
}
public Boolean getDead(){
  return isDead;
}
public void setMoved(Boolean b  ){
  isMoved = b;
}
public Boolean getMoved(){
  return isMoved;
}
public void sethasAttacked(Boolean b  ){
  hasAttacked = b;
}
public Boolean gethasAttacked(){
  return hasAttacked;
}
public void sethasCaptured(Boolean b  ){
  hasCaptured = b;
}
public Boolean gethasCaptured(){
  return hasCaptured;
}
public Boolean getisEnemy(){
   return isEnemy;
}
public void setisEnemy(Boolean b){
  isEnemy = b;
}
public Boolean getisTurnDone(){
   return isTurnDone;
}
public void setisTurnDone(Boolean b){
  isTurnDone = b;
}
public Boolean getisTown(){
   return isTown;
}
public void setisTown(Boolean b){
  isTown = b;
}
public String getType(){
   return type;
}
public void setType(String type){
  this.type = type;
}

 }
