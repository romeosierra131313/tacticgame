package com.Pumba.lou;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Stefan
 */
public class PathFinding {
       ArrayList<Vector2> checkNext = new ArrayList<Vector2>();
       ArrayList<Tile> moveable = new  ArrayList<Tile>();
       ArrayList<Tile> Path = new  ArrayList<Tile>();
       HashMap<Vector2,Tile> maplist;
       Vector2 loca = new Vector2();//get e.x e.y
       Vector2 temploca = new Vector2();
       
       
     public PathFinding(HashMap<Vector2,Tile> maplist){
        this.maplist = maplist;   
     }
     public     void rendermoveable(SpriteBatch sb, ShapeRenderer sr, OrthographicCamera camera) {
          sr.set(ShapeRenderer.ShapeType.Line);
          sr.setColor(com.badlogic.gdx.graphics.Color.BLUE);
          if(moveable.size()>0){
             
          for(int i = 0;i < moveable.size()-1; i++){
             sr.box(moveable.get(i).x   ,moveable.get(i).y, 0,
                      32, 32, 0);}
          }   
         } 
     public  void getpathable(GameEntity e){
         System.out.println("getpathable");
          int stamina = e.getStamina();
          loca.set(e.getX(), e.getY());
          checkNext.add(loca);
           System.out.println(loca);
          for(int i = stamina ; i >-1; i --){
             for(int o = checkNext.size(); o >0; o--){
               temploca.set(checkNext.get(o-1).x,   checkNext.get(o-1).y);
              
              checknext(stamina);
             System.out.println(moveable.size());}
              stamina -=1;}
          
          
          
     }
    public void checknext(int stamina){
        if(stamina > -1){
          
         temploca = temploca.set(temploca.x, temploca.y+32);
          System.out.println(temploca);
         if(!moveable.contains(maplist.get(temploca)) && maplist.containsKey(temploca)  ){
            if(maplist.get(temploca).isSet && maplist.get(temploca).isPathable){
             checkNext.add(new Vector2(temploca.x,temploca.y));////adding north   
          moveable.add(maplist.get(temploca));}}
          
         
          temploca = temploca.set(temploca.x, temploca.y-32);
          
          
         // temploca.set(loca.x, loca.y-32);////setting south
          temploca = temploca.set(temploca.x, temploca.y-32);
          if(!moveable.contains(maplist.get(temploca))&& maplist.containsKey(temploca)){
              if(maplist.get(temploca).isSet && maplist.get(temploca).isPathable){
              checkNext.add(new Vector2(temploca.x,temploca.y));////adding south
           moveable.add(maplist.get(temploca));
             }}
          temploca = temploca.set(temploca.x, temploca.y+32);
           
           
         // temploca.set(loca.x+32, loca.y);////setting east
         temploca = temploca.set(temploca.x+32, temploca.y);
         if(!moveable.contains(maplist.get(temploca))&& maplist.containsKey(temploca)){
             if(maplist.get(temploca).isSet && maplist.get(temploca).isPathable){
          checkNext.add(new Vector2(temploca.x,temploca.y));////adding east
           moveable.add(maplist.get(temploca)); 
             }}
          temploca = temploca.set(temploca.x-32, temploca.y);
          
           
           
         // temploca.set(loca.x-32, loca.y);////setting west
          temploca = temploca.set(temploca.x-32, temploca.y);
          if(!moveable.contains(maplist.get(temploca))&& maplist.containsKey(temploca)){
              if(maplist.get(temploca).isSet && maplist.get(temploca).isPathable){
          checkNext.add(new Vector2(temploca.x,temploca.y));////adding west
           moveable.add(maplist.get(temploca));
            }}
          temploca = temploca.set(temploca.x+32, temploca.y);
         
         
        }   
    
    }
    public void reset(){
        loca = null;
        temploca = null;
        checkNext.clear();
        moveable.clear();
        Path.clear();
    }
    public void setPath(Vector2 start,Vector2 end){
         
          Vector2 diff = new Vector2();
          diff.set(start.x-end.x, start.y-end.y);
          checkNext.add(start);
          Path.add(maplist.get(end));
     }
    public ArrayList getPath(){
       return Path;
    }



}
