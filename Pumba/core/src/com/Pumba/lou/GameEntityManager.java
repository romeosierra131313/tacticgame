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
import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author Stefan
 */
public class GameEntityManager implements Serializable{
    ArrayList<GameEntity> entitys;
    ArrayList<GameEntity> entitysDead;
    ArrayList<GameEntity> towns;
    int Gold;
      public GameEntityManager(){
           entitys = new ArrayList<GameEntity>();
           entitysDead = new ArrayList<GameEntity>();
           towns = new ArrayList<GameEntity>();
        
      }
      public void render(SpriteBatch sb,ShapeRenderer sr,BitmapFont bf,OrthographicCamera camera,float dt){
           sb.setColor(Color.CLEAR);
         rendertowns(sb,sr,bf,dt);
         renderentitys(sb,sr,bf,dt);
         
          sb.setColor(Color.WHITE);
      }
      public void setEntitys( ArrayList<GameEntity> entitys){
         this.entitys = entitys;
      }
      public int getGold(){
        return Gold;
      }
      public void setGold(int amount){
        Gold = amount;
      }
      public GameEntity getEntity(int index){
         return entitys.get(index);
       }
      public void addEntity(GameEntity ge){
         entitys.add(ge);
      }
      public void settowns( ArrayList<GameEntity> towns){
         this.towns = towns;
      }
      public GameEntity gettown(int index){
         return towns.get(index);
       }
      public void addtown(GameEntity ge){
         towns.add(ge);
      }
      public void removeEntity(GameEntity ge){
         entitys.remove(ge);
      }
      public GameEntity getMoving(){
         for (int i =0; i < entitys.size(); i++){
             if(entitys.get(i).getMoving()){
               return entitys.get(i);
             }
         } return null;
      }
      public GameEntity getWaitingEntity(){
         for (int i =0; i < entitys.size(); i++){
             if(entitys.get(i).getWaiting()){
               return entitys.get(i);
             }
         } return null;
      }
      public GameEntity getWaitingTown(){
         for (int i =0; i < towns.size(); i++){
             if(towns.get(i).getWaiting()){
               return towns.get(i);
             }
         } return null;
      }
      public void resetisMoved(){
            for(GameEntity e : entitys){
               e.setMoved(Boolean.FALSE);
            }
      }
      public void resetisAttacked(){
            for(GameEntity e : entitys){
               e.sethasAttacked(Boolean.FALSE);
            }
     
      }
      public void giveGold(Player p){
            for(GameEntity e : towns){
               p.increaseGold(100,e.getisEnemy());
            }
      
      }

    public void rendertowns(SpriteBatch sb, ShapeRenderer sr, BitmapFont bf, float dt) {
        for(GameEntity e : towns){
               if(e.getisEnemy() ==null ){
           sb.setColor(Color.GREEN);
           sr.box(e.getX(), e.getY()  , 0, 128, 128, 0);
           sb.draw(e.t,e.getX(),e.getY());
           bf.draw(sb,Integer.toString(e.getHp()),e.getX()     , e.getY());
              } 
              if(e.getisEnemy() !=null && e.getisEnemy()){
            sr.setColor(com.badlogic.gdx.graphics.Color.RED);
            sb.setColor(Color.RED);
           sr.box(e.getX(), e.getY()  , 0, 128, 128, 0);
           sb.draw(e.t,e.getX(),e.getY());
           bf.draw(sb,Integer.toString(e.getHp()),e.getX()     , e.getY());
              }
          if(e.getisEnemy() !=null && !e.getisEnemy()){
           sr.setColor(com.badlogic.gdx.graphics.Color.BLUE);
           sb.setColor(Color.BLUE);
           sr.box(e.getX(), e.getY()  , 0, 128, 128, 0);
           sb.draw(e.t,e.getX(),e.getY());
           bf.draw(sb,Integer.toString(e.getHp()),e.getX()    , e.getY());
             
          }
         } 
           sb.setColor(Color.WHITE);
    }

    public void renderentitys(SpriteBatch sb, ShapeRenderer sr, BitmapFont bf, float dt) {
        sb.setColor(Color.WHITE);
       for(GameEntity ge : entitys){
            ge.render(sb, sr, bf,dt);

         for(GameEntity d : entitysDead){
            sb.setColor(Color.WHITE);
           sb.draw(d.t,d.getX(),d.getY());
          
          }
        sb.setColor(Color.WHITE);
    }
  }
}
 


