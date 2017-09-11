/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.Pumba.lou;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import java.util.ArrayList;

/**
 *
 * @author Stefan
 */
public class UnitFactory {
       AssetManager ass;
       Texture unit1;
       PixmapEditor px;
     //Texture unit2;  

    public UnitFactory(AssetManager ass) {
         this.ass = ass;
         px = new PixmapEditor();
        // ass.load("unit1.png", Texture.class);
        // ass.finishLoading();
    }
      
       /////////////////UNITS///////////////////////////////////
       
       public GameEntity newUnit(String type,int x , int y,Boolean b,Boolean isFlipped){
        GameEntity e= new GameEntity();
           if(type.equals("unit1")){
              e.newEntity(x,y);
              e.setHitbox(x, y, 128, 128);
              e.setHp(10);
              e.setAttack(10);
              e.setType(type);
              e.setisEnemy(b);
               px.PutColor("unit.png",b);
               px.DefineTextureRegionsEntity(isFlipped);
              e.setAnimations(px.getright(), px.getleft(), px.getup(), px.getdown());
              e.turnright();
              
           }
           if(type.equals("unit2")){
              e.newEntity(x,y);
              e.setHitbox(x, y, 128, 128);
              e.setHp(10);
              e.setAttack(10);
              e.setType(type);
              e.setisEnemy(b);
               px.PutColor("unit2.png",b);
               px.DefineTextureRegionsEntity(isFlipped);
              e.setAnimations(px.getright(), px.getleft(), px.getup(), px.getdown());
              e.turnright();
              
           }
           if(type.equals("town")){
              e.newEntity(x,y);
              e.setHitbox(x,y,128   , 128);
              e.setHp(20);
              e.setAttack(1);
              e.setT(ass.get("town.png", Texture.class));
              e.setisEnemy(b);
              e.setType(type);
              e.setisTown(true);
           }
         
         
          
      //if(type.equals("unit2")){
       //   Unit2 u = new Unit2(){}
       //   u.setX();
       //   u.setY();
       //   u.setT(unit2T);
       //  } 
          
       
return e;
       }
      
       
       /////////UI ELEMENTS//////////
       public actionUIitem newUIitem(int place,int x,int y,String label ){
        actionUIitem UIitem = new actionUIitem(place,x,y,label);
        return UIitem;
       }
       public actionUIitem newTurnCard(int x, int y,String label ){
        actionUIitem UIitem = new actionUIitem(x,y,label);
        return UIitem;
       }
       
}