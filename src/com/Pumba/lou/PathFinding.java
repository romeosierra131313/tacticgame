package com.Pumba.lou;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import java.util.ArrayList;
import java.util.HashMap;

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
     ArrayList<Tile> neighbours = new  ArrayList<Tile>();
       ArrayList<Vector2> checkNext = new ArrayList<Vector2>();
       ArrayList<Tile> moveable = new  ArrayList<Tile>();
       ArrayList<Tile> Path = new  ArrayList<Tile>();
       ArrayList<Tile> openlist = new  ArrayList<Tile>();
       ArrayList<Tile> closedlist = new  ArrayList<Tile>();
       HashMap<Vector2,Tile> maplist;
       Vector2 loca = new Vector2();//get e.x e.y
       Vector2 temploca = new Vector2();
       Vector2 diff = new Vector2();
       Vector2 xAxis = new Vector2();
       Vector2 yAxis = new Vector2();
        float easti;
        float westi;
        
       Boolean ready;
       
       
       
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
         
          int stamina = e.getStamina();
          loca.set(e.getX(), e.getY());
          checkNext.add(loca);
           
          for(int i = stamina ; i >-1; i --){
             for(int o = checkNext.size(); o >0; o--){
               temploca.set(checkNext.get(o-1).x,   checkNext.get(o-1).y);
               checknext(stamina);
            }
              stamina -=1;
          }
          
          
          
     }
    public void checknext(int stamina){
        if(stamina > -1){
          
         temploca = temploca.set(temploca.x, temploca.y+32);
          
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
       // loca = null;
      //  temploca = null;
        checkNext.clear();
        moveable.clear();
        Path.clear();
        neighbours.clear();
        ready = false;
      
    }
    public void setPath(Vector2 start,Vector2 end ,GameEntity e){
      
      //    diff.set(Math.abs(start.x-end.x),Math.abs(start.y-end.y));
      //    System.out.println("diff:" + diff);
      //   // Path.add(maplist.get(start));
      //    checkNext.add(start);
      //   
      //     for(int i = e.getStamina() ; i >-1; i --){
      //         if(diff.x == 0 && diff.y == 0){i = -1;
      //         }
      //         if(temploca != end){
      //            getPath(end);}
      //       }
      //     Path.add(maplist.get(end));
      //     for(int i = 0 ; i < Path.size()-1; i ++){
      //       System.out.println(Path.get(i).getX() +"+"+ Path.get(i).getY());
      //     }
         Path.add(maplist.get(end));
         neighbours.clear();
         getNeighbor(maplist.get(end));
          
          ready = true;
     }
    public void getPath(Vector2 start){
        
    //    if(diff.x != 0 || diff.y != 0){
    //        
    //        temploca.set(checkNext.get(0));
    //        System.out.println("temploca:" + temploca);
    //        System.out.println("checking:" + new Vector2(temploca.x+32, temploca.y)+"+"+new Vector2(temploca.x-32, temploca.y));
    //       xAxis = checkxAxis(start,new Vector2(temploca.x+32, temploca.y),new Vector2(temploca.x-32, temploca.y));
    //       if(xAxis != checkNext.get(0)){
    //      Path.add(maplist.get(xAxis));
    //      checkNext.add(0,new Vector2(xAxis));
    //      // diff.set(Math.abs(diff.x-32),diff.y); 
    //      // System.out.println("diff:" + diff);
    //       }else{  
    //       yAxis = checkyAxis(start,new Vector2(temploca.x, temploca.y+32),new Vector2(temploca.x, temploca.y-32));
    //        if(yAxis != checkNext.get(0)){
    //         Path.add(maplist.get(checkyAxis(start,new Vector2(temploca.x, temploca.y+32),new Vector2(temploca.x, temploca.y-32))));    
    //          checkNext.add(0,checkyAxis(start,new Vector2(temploca.x, temploca.y+32),new Vector2(temploca.x, temploca.y-32))); 
    //        }
    //       }
    //     
    //      
    //     
    //     }
//         checkNext.remove(1);
    }
            
public Vector2 checkxAxis(Vector2 end,Vector2 east,Vector2 west){
      
      if(moveable.contains(maplist.get(east)) && maplist.containsKey(east)  ){ 
          
          if(diff.x >= 0) {easti =  Math.abs(east.x - end.x) ; 
                           System.out.println("easti:" + easti);} 
                           }else{easti = 100000;
                  System.out.println("easti:" + easti);}
          if(moveable.contains(maplist.get(west)) && maplist.containsKey(west)  ){  
              if(diff.x >= 0) {
                    westi =  Math.abs(west.x - end.x) ;
                           System.out.println("westi:" + westi);}  }else{westi = 100000; System.out.println("westi:" + westi);}
          if(easti > westi){
            xAxis = west;
            temploca = west;
             System.out.println("settingwest:" + west );
           
            return xAxis;
          }
          if(westi>easti){
           xAxis = east;
           //checkNext.add(0,new Vector2(east));
             System.out.println("setting est:" );
             temploca = east;
            
           return xAxis;
          }
    
      
      return xAxis;
}
public Vector2 checkyAxis(Vector2 end,Vector2 north,Vector2 south){
      
      if(moveable.contains(maplist.get(north)) && maplist.containsKey(north)  ){ 
          
          if(diff.x >= 0) {easti =  Math.abs(north.y - end.y) ; 
                           System.out.println("easti:" + easti);} 
                           }else{easti = 100000;
                  System.out.println("easti:" + easti);}
          if(moveable.contains(maplist.get(south)) && maplist.containsKey(south)  ){  
              if(diff.x >= 0) {
                    westi =  Math.abs(south.y - end.y) ;
                           System.out.println("westi:" + westi);}  }else{westi = 100000; System.out.println("westi:" + westi);}
          if(easti > westi){
            yAxis = south;
            temploca = south;
             System.out.println("settingsouth:" );
           
            return xAxis;
          }
          if(westi>easti){
           yAxis = north;
          // checkNext.add(0,new Vector2(south));
             System.out.println("setting north:" );
             temploca = north;
            
           return yAxis;
          }
    
      
      return yAxis;
}
public void getNeighbor(Tile t){
   
        neighbours.add(maplist.get(new Vector2(t.getX()+32,t.getY())));
        neighbours.add(maplist.get(new Vector2(t.getX()-32,t.getY())));
        neighbours.add(maplist.get(new Vector2(t.getX(),t.getY()+32)));
        neighbours.add(maplist.get(new Vector2(t.getX(),t.getY()-32)));
     }


}
