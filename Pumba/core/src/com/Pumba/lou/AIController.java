/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.Pumba.lou;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

/**
 *
 * @author Stefan
 */
public class AIController {
    PathFinding pfsub;
    GameEntityManager gemsub;
    GameEntityManager gemsub2;
    public AIController(PathFinding pf,GameEntityManager gem,GameEntityManager gem2){
       pfsub = pf;
       gemsub = gem;
       gemsub2 = gem2;
    }
    public Boolean SelectAUnit(){
      for(GameEntity e : gemsub.entitys){
         if(!e.getDead() && !e.getMoved()){
           e.setWaiting(Boolean.TRUE);
            pfsub.getpathable(e);
           System.out.println("set a unit waiting");
           return true;
         }
      }
      return false;
    }
    public GameEntity getNearestEnemy(GameEntity ge){
        int x = ge.getX();
        int y = ge.getY();
        int distance = 100000 ;
        GameEntity nearestEnemey = new GameEntity();
           for(GameEntity e : gemsub2.entitys){
           if(Math.abs((e.getX() + e.getY()) - (x+y))< distance){
               distance = Math.abs((e.getX() + e.getY()));
               nearestEnemey = e;
            }
          }
           for(GameEntity e : gemsub2.towns){
           if(Math.abs((e.getX() + e.getY()) - (x+y))< distance){
               distance = Math.abs((e.getX() + e.getY()));
               nearestEnemey = e;
            }
          }
            System.out.println(nearestEnemey); 
        return nearestEnemey;
    }
    public Vector3 SelectLocationToMoveTo(){
       
    GameEntity nearestEnemey = getNearestEnemy(gemsub.getWaitingEntity());
     
      return new Vector3(nearestEnemey.getX()-128,nearestEnemey.getY(),0); 
       
       
    }
    public void Attack(){}
    
    
    
}
