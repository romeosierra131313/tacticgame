/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.Pumba.lou;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author Stefan
 */
public class MainGame extends MyGdxGame implements InputProcessor  {
     map m;
     UnitFactory uf;
     GameEntityManager gem;
     PathFinding pf;
     float dt;
     Rectangle rt;
     actionUI aui;
     Boolean actionUIisOpen = false;
     HashMap<Vector2,Tile> maplist;
     GameEntity anentity;
     Vector3 v3 = new Vector3();
     Vector2 v2 = new Vector2();
     int movecounter;
     
    
    public MainGame(OrthographicCamera camera,AssetManager ass,map m){
     this.m = m;
     this.ass = ass;
     this.camera = camera;
     rt = new Rectangle();
     camera.setToOrtho(true, 1024, 720);
     maplist = new HashMap();
      aui = new actionUI();
      uf = new UnitFactory(ass);
      pf = new PathFinding(maplist);
      
      anentity = uf.newUnit("soldier", 0, 0);
      anentity.setX(256);
      anentity.setY(160);
      anentity.setT(ass.get("unit1.png", Texture.class));
      anentity.setHitbox(anentity.getX(), anentity.getY(), 256, 160);
      gem = new  GameEntityManager();
      gem.addEntity(anentity);
     
    }
    
  public void render(SpriteBatch sb,ShapeRenderer sr,OrthographicCamera camera){
       camera.update();
     dt += Gdx.graphics.getDeltaTime();
     if(dt >= 1f){
      dt = 0;
  }  
      movecharacters();
      
     m.render(sb, sr, camera);
     drawgrid(sb, sr,ass);
     pf.rendermoveable(sb, sr, camera);
     gem.render(sb, sr, camera,dt); 
    
    if(actionUIisOpen){
     
      aui.render(sb, sr, camera);
    }
 
    
    
  }  
      private void drawgrid(SpriteBatch sb,ShapeRenderer sr,AssetManager ass) {
          
       for(int i = 0; i < m.tList.size() ; i++){
       if(m.getTile(i).type.compareTo("null") == 0 ){}
         else{ sb.draw(ass.get(m.getTile(i).type + ".png"  ,Texture.class) 
         , m.tList.get(i).x, m.tList.get(i).y);}
      
       
       }   }
  public void setMap(map m){
    this.m = m;
   }  
  public map getMap(){
    return m;
  }

    @Override
    public boolean keyDown(int i) {
        
       return true; }

    @Override
    public boolean keyUp(int i) {
       return true; }

    @Override
    public boolean keyTyped(char c) {
        return true; }

    @Override
    public boolean touchDown(int i, int i1, int i2, int i3) {
        // gem.getEntity(0).move(288 ,160, dt);
 System.out.println("which tile :");
       v3 = unproject(Gdx.input.getX(),Gdx.input.getY());
        if(!actionUIisOpen){
               
             whichTile(v3);
            if( whichEntity(v3) != null){
              openactionUI(whichEntity(v3));
            }
            
            }
       
            if(gem.getWaiting() !=null){
                v2 = whichTile(v3); 
                Vector2 v2start = new Vector2(gem.getWaiting().getX(),gem.getWaiting().getY());
                 
               // pf.setPath(v2start, v2,gem.getWaiting());
                gem.getWaiting().setMoving();
                movecounter = 0;
               }
      
            if( actionUIisOpen){
               for (actionUIitem e :  aui.actionUIitems){
                   if(e.r.contains(v3.x, v3.y)){
                       actionUIisOpen = false;
                       pf.reset();
                       pf.getpathable(anentity);
                       
                       anentity.setWaiting();
                      
                       }
                     }
                    }
                  
                
                
             
                return true; }

    @Override
    public boolean touchUp(int i, int i1, int i2, int i3) {
       
       
           
            
       return true; }

    @Override
    public boolean touchDragged(int i, int i1, int i2) {
      return true; }

    @Override
    public boolean mouseMoved(int i, int i1) {
      return true; }

    @Override
    public boolean scrolled(int i) {
         if(i < 0){
        camera.zoom -= 0.02f;
        camera.update();}else{
         camera.zoom += 0.02f;
        }
     return true; }
    
        public void rebuildmap(map m) {
           
           for(int i = 0 ; i < m.tList.size();i++){
              m.getTile(i).r = new Rectangle();
              Tile temp = new Tile(m.getTile(i).x,m.getTile(i).y,m.getTile(i).rwidth,m.getTile(i).rwidth);
              v2 = new Vector2();
              v2.add(temp.x, temp.y);
              temp.type = m.getTile(i).type;
              if("null".equals(temp.type)) {}else{
                      temp.isSet = true;
                      temp.isPathable = true;
              }
                  if("water".equals(temp.type)){          //////////SET BACK NON PATHABLE HERE/////////
                      temp.isPathable = false;
                      temp.isSet =true;
                      
                  }
               
              
              maplist.put(v2, temp);
              m.tList.remove(i);
              m.tList.add(i, temp);
               
             
             
              
            }
            
              
            
            
    }
    public Vector2 whichTile(Vector3 v3){
       v2 = new Vector2();
       for(int p = 0; p <= m.tList.size()-1;p++){
                   if(m.tList.get(p).r.contains(v3.x, v3.y)){
                    System.out.println(m.tList.get(p).r.x + " + " + m.tList.get(p).r.y); 
                     
                     v2.set(m.tList.get(p).getX(), m.tList.get(p).getY());
                      System.out.println("which tile :" + v2);
                     return v2;
                   } /// does contain click
                }//end of loop does not contain click
       return null;
    }

    public GameEntity whichEntity(Vector3 v3) {
       for (GameEntity e : gem.entitys){
           if(e.r.contains(v3.x, v3.y)){
          return e;
       }
    }      return null;
    }
    public void openactionUI(GameEntity e){
        aui.addaction(uf.newMove(e.getX(), e.getY()));
           actionUIisOpen = true;
           
           }

    private void movecharacters() {
     
      if(gem.getMoving() != null && pf.ready == true){ 
          aui.actionUIitems.clear();
           System.out.println("stamina" +gem.getMoving().getStamina());
         if(pf.Path.get(movecounter).x > gem.getMoving().getX()){
                gem.getMoving().East = true;
                gem.getMoving().West = false;
                gem.getMoving().South = false;
                gem.getMoving().North = false;
                System.out.println("moving to E");
         }
          if(pf.Path.get(movecounter).x < gem.getMoving().getX()){ 
                gem.getMoving().West = true;
                gem.getMoving().East = false;
                gem.getMoving().South = false;
                gem.getMoving().North = false;
                System.out.println("moving to W");}
          if(pf.Path.get(movecounter).y > gem.getMoving().getY()){
                gem.getMoving().North = true;
                gem.getMoving().West = false;
                gem.getMoving().East = false;
                gem.getMoving().South = false;
                System.out.println("moving to N");
          } 
           if(pf.Path.get(movecounter).y < gem.getMoving().getY()){
                gem.getMoving().South = true;
                gem.getMoving().North = false;
                gem.getMoving().West = false;
                gem.getMoving().East = false;
                   System.out.println("moving to S");}
         
         if( gem.getMoving().getX() == pf.Path.get(movecounter).x &&
           gem.getMoving().getY() == pf.Path.get(movecounter).y){
            
           if(movecounter <=pf.Path.size()-2){ 
           movecounter+=1;}
       } 
          
       if( gem.getMoving().getX() != pf.Path.get(movecounter).x || gem.getMoving().getY() != pf.Path.get(movecounter).y){
           System.out.println("moving to "+ pf.Path.get(movecounter).x +"+"+pf.Path.get(movecounter).y);
           
      gem.getMoving().move(pf.Path.get(movecounter).x,pf.Path.get(movecounter).y, dt); 
      
      }
       if( gem.getMoving().getX() == pf.Path.get(pf.Path.size()-1).x &&
           gem.getMoving().getY() == pf.Path.get(pf.Path.size()-1).y){
           gem.getMoving().resetsetMovingandWaiting();
          // pf.reset();
           aui.actionUIitems.clear();
         }
      
       } }
    
   


    

}
