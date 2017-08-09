/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.Pumba.lou;

import com.badlogic.gdx.graphics.OrthographicCamera;
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
      public GameEntityManager(){
           entitys = new ArrayList<GameEntity>();
        
      }
      public void render(SpriteBatch sb,ShapeRenderer sr,OrthographicCamera camera){
         
         for(GameEntity ge : entitys){
           sb.draw(ge.t,ge.getX(),ge.getY());
         }
         
      }
      public void setEntitys( ArrayList<GameEntity> entitys){
         this.entitys = entitys;
      }
      public GameEntity getEntity(int index){
         return entitys.get(index);
       }
      public void addEntity(GameEntity ge){
         entitys.add(ge);
      }
      public void removeEntity(GameEntity ge){
         entitys.remove(ge);
      }
      
 

}
