package com.Pumba.lou;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import java.util.ArrayList;
import java.util.HashMap;
import com.Pumba.lou.Constants;
import com.badlogic.gdx.graphics.Color;

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
       ArrayList<Tile> openlist;
       ArrayList<Tile> closedlist;
       HashMap<Vector2,Tile> maplist;
       Vector2 loca = new Vector2();//get e.x e.y
       Vector2 temploca = new Vector2();
       Vector2 diff = new Vector2();
       Vector2 xAxis = new Vector2();
       Vector2 yAxis = new Vector2();
        float easti;
        float westi;
        int tsize = Constants.TileWidth;
        int steps = 1;
       Boolean ready;
       
       
       
     public PathFinding(HashMap<Vector2,Tile> maplist){
        this.maplist = maplist;  
        openlist = new  ArrayList<Tile>();
        closedlist = new  ArrayList<Tile>();
     }
     public void render(SpriteBatch sb, ShapeRenderer sr, OrthographicCamera camera) {
          sr.set(ShapeRenderer.ShapeType.Line);
          sr.setColor(com.badlogic.gdx.graphics.Color.BLUE);
          if(moveable.size()>0){
             
          for(int i = 0;i < moveable.size()-1; i++){
             sr.box(moveable.get(i).x   ,moveable.get(i).y, 0,
                      tsize, tsize, 0);}
          }   
         // sr.set(ShapeRenderer.ShapeType.Filled);
          sr.setColor(Color.ORANGE);
          if(neighbours.size() > 0){
            for(Tile t: neighbours ){
              if(!t.isOccupied){
                sr.box(t.getX(), t.getY(), 0, tsize, tsize, 0);
              }
            }
          }
          sr.set(ShapeRenderer.ShapeType.Line);
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
          
         temploca = temploca.set(temploca.x, temploca.y+tsize);
          
         if(!moveable.contains(maplist.get(temploca)) && maplist.containsKey(temploca)  ){
            if(maplist.get(temploca).isSet && maplist.get(temploca).isPathable){
                if( !maplist.get(temploca).isOccupied){
             checkNext.add(new Vector2(temploca.x,temploca.y));////adding north   
          moveable.add(maplist.get(temploca));}}}
          
         
          temploca = temploca.set(temploca.x, temploca.y-tsize);
          
          
         // temploca.set(loca.x, loca.y-32);////setting south
          temploca = temploca.set(temploca.x, temploca.y-tsize);
          if(!moveable.contains(maplist.get(temploca))&& maplist.containsKey(temploca)){
              if(maplist.get(temploca).isSet && maplist.get(temploca).isPathable){
                   if( !maplist.get(temploca).isOccupied){
              checkNext.add(new Vector2(temploca.x,temploca.y));////adding south
           moveable.add(maplist.get(temploca));
             }}}
          temploca = temploca.set(temploca.x, temploca.y+tsize);
           
           
         // temploca.set(loca.x+32, loca.y);////setting east
         temploca = temploca.set(temploca.x+tsize, temploca.y);
         if(!moveable.contains(maplist.get(temploca))&& maplist.containsKey(temploca)){
             if(maplist.get(temploca).isSet && maplist.get(temploca).isPathable){
                  if( !maplist.get(temploca).isOccupied){
          checkNext.add(new Vector2(temploca.x,temploca.y));////adding east
           moveable.add(maplist.get(temploca)); 
             }}}
          temploca = temploca.set(temploca.x-tsize, temploca.y);
          
           
           
         // temploca.set(loca.x-32, loca.y);////setting west
          temploca = temploca.set(temploca.x-tsize, temploca.y);
          if(!moveable.contains(maplist.get(temploca))&& maplist.containsKey(temploca)){
              if(maplist.get(temploca).isSet && maplist.get(temploca).isPathable){
                   if( !maplist.get(temploca).isOccupied){
          checkNext.add(new Vector2(temploca.x,temploca.y));////adding west
           moveable.add(maplist.get(temploca));
            }}}
          temploca = temploca.set(temploca.x+tsize, temploca.y);
         
         
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
          System.out.println("setting path");
         
        openlist.add(maplist.get(start));
        maplist.get(start).setParent(maplist.get(start));
        getNeighborsInOpenList(maplist.get(start));
         System.out.println(openlist.size());       
        while(openlist.size() > 0){
             System.out.println("while");
            
                   setDistanceToEnd(openlist.get(0),end);
                   setHeuristic(openlist.get(0));
                  getNeighborsInOpenList(openlist.get(0));
                             
              System.out.println("switching");
                   closedlist.add(openlist.get(0));
                   openlist.remove(openlist.get(0));
               for(Tile t : closedlist){
                       if(t.getDistance() == 0){openlist.clear();}
                    System.out.println("distance:" +  t.getDistance() +"heuristic "+t.getHeuristic());
                  }
                   steps++;
                       
                 }
                Tile current = maplist.get(end);
                Path.add(maplist.get(end));
                while(!Path.contains(maplist.get(start))) {
                    Path.add(current.getParent());
                    current = current.getParent();
                }
                for (Tile t : Path){
                     System.out.println(t.getX() + "+"+ t.getY());
                }
               
                ready = true;
  
               } 
      
      
      

      
    public void getPath(Vector2 start){
    
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
   
        neighbours.add(maplist.get(new Vector2(t.getX()+tsize,t.getY())));
        neighbours.add(maplist.get(new Vector2(t.getX()-tsize,t.getY())));
        neighbours.add(maplist.get(new Vector2(t.getX(),t.getY()+tsize)));
        neighbours.add(maplist.get(new Vector2(t.getX(),t.getY()-tsize)));
     }
public void setDistanceToEnd(Tile current,Vector2 end){
        current.setDistance((Math.abs(current.getX() - Math.round(end.x))) +(Math.abs(current.getY()-Math.round(end.y))));

     }
public void setHeuristic(Tile current){
        current.setHeuristic(current.getDistance() + 10*steps);
}
public void getNeighborsInOpenList(Tile t){

        System.out.println("nighbor");
        if(moveable.contains(maplist.get(new Vector2(t.getX()+tsize,t.getY()))) && !closedlist.contains(maplist.get(new Vector2(t.getX()+tsize,t.getY())))){
            openlist.add(maplist.get(new Vector2(t.getX()+tsize,t.getY())));
            maplist.get(new Vector2(t.getX()+tsize,t.getY())).setParent(maplist.get(new Vector2(t.x,t.y)));}
        if(moveable.contains(maplist.get(new Vector2(t.getX()-tsize,t.getY()))) && !closedlist.contains(maplist.get(new Vector2(t.getX()-tsize,t.getY())))){
            openlist.add(maplist.get(new Vector2(t.getX()-tsize,t.getY())));
            maplist.get(new Vector2(t.getX()-tsize,t.getY())).setParent(maplist.get(new Vector2(t.x,t.y)));}
        if(moveable.contains(maplist.get(new Vector2(t.getX(),t.getY()+tsize))) && !closedlist.contains(maplist.get(new Vector2(t.getX(),t.getY()+tsize)))){
            openlist.add(maplist.get(new Vector2(t.getX(),t.getY()+tsize)));
            maplist.get(new Vector2(t.getX(),t.getY()+tsize)).setParent(maplist.get(new Vector2(t.x,t.y)));}
        if(moveable.contains(maplist.get(new Vector2(t.getX(),t.getY()-tsize))) && !closedlist.contains(maplist.get(new Vector2(t.getX(),t.getY()-tsize)))){
            openlist.add(maplist.get(new Vector2(t.getX(),t.getY()-tsize)));
            maplist.get(new Vector2(t.getX(),t.getY()-tsize)).setParent(maplist.get(new Vector2(t.x,t.y)));}
     
                    }
}