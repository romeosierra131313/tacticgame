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
import com.Pumba.lou.UnitFactory;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

/**
 *
 * @author Stefan
 */
public class MainGame extends MyGdxGame implements InputProcessor  {
     map m;
     UnitFactory uf;
     GameEntityManager gem;
     float dt;
     Rectangle rt;
     actionUI aui;
     Boolean actionUIisOpen = false;
    
    public MainGame(OrthographicCamera camera,AssetManager ass,map m){
     this.m = m;
     this.ass = ass;
     this.camera = camera;
     rt = new Rectangle();
     camera.setToOrtho(true, 1024, 720);
     
      aui = new actionUI();
      uf = new UnitFactory(ass);
      
      GameEntity e = uf.newUnit("soldier", 0, 0);
      e.setX(256);
      e.setY(160);
      e.setT(ass.get("unit1.png", Texture.class));
      e.setHitbox(e.getX(), e.getY(), 256, 160);
      gem = new  GameEntityManager();
      gem.addEntity(e);
     
    }
    
  public void render(SpriteBatch sb,ShapeRenderer sr,OrthographicCamera camera){
       camera.update();
     dt += Gdx.graphics.getDeltaTime();
     if(dt >= 1f){
      dt = 0;
  }
     m.render(sb, sr, camera);
     drawgrid(sb, sr,ass);
     gem.render(sb, sr, camera); 
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
        
       Vector3 v3 = new Vector3();
       v3 = unproject(Gdx.input.getX(),Gdx.input.getY());
            if(! actionUIisOpen){
             whichTile(v3);
             whichEntity(v3);}
            if( actionUIisOpen){
               for (actionUIitem e :  aui.actionUIitems){
                   if(e.r.contains(v3.x, v3.y)){
                   System.out.println("move goddamit");
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
              temp.type = m.getTile(i).type;
              m.tList.remove(i);
              m.tList.add(i, temp);
             
             
              
            }
            
              
            
            
    }
    public void whichTile(Vector3 v3){
       for(int p = 0; p <= m.tList.size()-1;p++){
                   if(m.tList.get(p).r.contains(v3.x, v3.y)){
                    System.out.println(m.tList.get(p).r.x + " + " + m.tList.get(p).r.y); 
                  
                   } /// does contain click
                }//end of loop does not contain click
    }

    public void whichEntity(Vector3 v3) {
       for (GameEntity e : gem.entitys){
           if(e.r.contains(v3.x, v3.y)){
          aui.addaction(uf.newMove(e.getX(), e.getY()));
           actionUIisOpen = true;
           
           }
       }  
    }
        



}
