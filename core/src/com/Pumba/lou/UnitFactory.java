/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.Pumba.lou;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;

/**
 *
 * @author Stefan
 */
public class UnitFactory {
       AssetManager ass;
       Texture unit1;
     //Texture unit2;  

    public UnitFactory(AssetManager ass) {
        this.ass = ass;
         ass.load("unit1.png", Texture.class);
         ass.finishLoading();
    }
      
       
       
       public GameEntity newUnit(String type,int x , int y){
        GameEntity u= new GameEntity();
           if(type.equals("unit1")){
         
          u.setX(x);
          u.setY(y);
         
         
          
      //if(type.equals("unit2")){
       //   Unit2 u = new Unit2(){}
       //   u.setX();
       //   u.setY();
       //   u.setT(unit2T);
       //  } 
          
       
}return u;
       }
       
       
       
       /////////UI ELEMENTS//////////
       public actionUIitem newMove(int x,int y ){
        actionUIitem move = new actionUIitem(1,x,y,"move");
        return move;
       }
}