/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.Pumba.lou;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Input.TextInputListener;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author Stefan
 */
public class Editor extends MyGdxGame implements InputProcessor , Serializable ,TextInputListener{
     int mHeight;
     int mWidth;
     int tSize;
     Tile t;
     Boolean main = false;
     Boolean ready = false;
     Rectangle mouse;
     int mousex;
     int mousey;
      String typeSelected = "grass";
     map m;
     GameEntityManager gem;
     ArrayList<Tile> ui ;
     
    
    
     public Editor(int height, int width,int size,OrthographicCamera camera, AssetManager ass){
           
     mHeight = height;
     mWidth = width;
     this.ass = ass;
     this.camera = camera;
     m = new map();
     m.height = height;
     m.width = width;
     m.tilewidth = size;
     
     tSize = size;
     gem = new GameEntityManager();
     camera.setToOrtho(true, 1024, 720);
     //tList = new ArrayList<Tile>();
     mouse = new Rectangle();
     mouse.set(0, 0, 1, 1);
      creategrid(mWidth,mHeight);
      ui = new ArrayList<Tile>();
      createUI();
     
      
      
     }
           
     
     @Override
     public void create(){
          
         
     }
     void render(SpriteBatch sb,ShapeRenderer sr,OrthographicCamera camera){
         
         sr.setProjectionMatrix(camera.combined);
         movemouse();
         
         camera.update();
         sr.begin(ShapeRenderer.ShapeType.Line);
         
        // gem.render(sb, sr, camera, width);
         sr.setColor(Color.BLACK);
         drawgrid(sb, sr);
         drawentitys(sb,sr);
         drawui(sb,sr);
         sr.end();
         
     
     }

    @Override
    public boolean keyDown(int i) {
        if (Gdx.input.isKeyPressed(Keys.NUM_1)){
           typeSelected = "grass";
         } 
        if (Gdx.input.isKeyPressed(Keys.NUM_2)){
           typeSelected = "road";
         } 
        if (Gdx.input.isKeyPressed(Keys.NUM_3)){
           typeSelected = "water";
         } 
        if (Gdx.input.isKeyPressed(Keys.NUM_4)){
           typeSelected = "town";
         } 
        if (Gdx.input.isKeyPressed(Keys.NUM_5)){
           typeSelected = "entity";
         }
        if (Gdx.input.isKeyPressed(Keys.NUM_6)){
           typeSelected = "entitye";
         }
        if (Gdx.input.isKeyPressed(Keys.SPACE)){
           main = true;
         }
        if (Gdx.input.isKeyPressed(Keys.Z)){
           typeSelected = "grass";
           int h =0;
           for(Tile t : m.tList){
              m.tList.get(h).setTile("grass",getTexture(typeSelected,ass));
              m.tList.get(h).isSet = true;
              
              h++;
           }
         } 
        if (Gdx.input.isKeyPressed(Keys.X)){
           typeSelected = "water";
           int h =0;
           for(Tile t : m.tList){
              m.tList.get(h).setTile("water",getTexture(typeSelected,ass));
              m.tList.get(h).isSet = true;
               m.tList.get(h).isPathable = false; 
              h++;
           }
         } 
         if (Gdx.input.isKeyPressed(Keys.ENTER)){
            // getMapName();
             for(GameEntity e : gem.entitys){
         System.out.println(e.getisEnemy());
        
       }
         //  Gdx.input.getTextInput(this, "name", "example", "");
         } 
        return true; }

    @Override
    public boolean keyUp(int i) {
         return true;}

    @Override
    public boolean keyTyped(char c) {
         return true; }

    @Override
    public boolean touchDown(int i, int i1, int i2, int i3) {
         
           Vector3 v3 = new Vector3();
          v3 = unproject(Gdx.input.getX(),Gdx.input.getY());
          for(Tile t : ui){
             
             if(t.r.contains(v3.x,v3.y)){
                   System.out.println(t.type);
        if ("grass".equals(t.type)){
           typeSelected = "grass";
         } 
        if ("road".equals(t.type)){
           typeSelected = "road";
         } 
        if ("water".equals(t.type)){
           typeSelected = "water";
         } 
        if ("town".equals(t.type)){
           typeSelected = "town";
         } 
        if ("entity".equals(t.type)){
           typeSelected = "entity";
         }
               return true;
             }
          }
          for(int h = 0 ; h < mWidth * mHeight; h++ ){
            if(m.tList.get(h).r.contains(v3.x,v3.y)){
               System.out.println(h);
            if(typeSelected == "road" ){
               m.tList.get(h).setTile("road", getTexture(typeSelected,ass));
               m.tList.get(h).isPathable=true;
            }
            if(typeSelected == "water" ){
               m.tList.get(h).setTile("water", getTexture(typeSelected,ass));
               m.tList.get(h).isPathable=false; }
            if(typeSelected =="grass" ){
               m.tList.get(h).setTile("grass",getTexture(typeSelected,ass));
             m.tList.get(h).isPathable=true;}
            if(typeSelected == "town" ){
                m.tList.get(h).setTile("town",getTexture(typeSelected,ass));
                GameEntity e = new GameEntity();
                e.newEntity(m.tList.get(h).getX(), m.tList.get(h).getY());
                m.tList.get(h).setOcuppied(Boolean.TRUE);
                e.setT(ass.get("town.png", Texture.class));
                gem.addtown(e);
            }
            if(typeSelected == "entity" ){
              GameEntity e = new GameEntity();
              e.newEntity(m.tList.get(h).getX(), m.tList.get(h).getY());
              m.tList.get(h).setOcuppied(Boolean.TRUE);
              e.setT(ass.get("unit1.png", Texture.class));
              e.setisEnemy(false);
              gem.addEntity(e);
            }  
            if(typeSelected == "entitye" ){
              GameEntity e = new GameEntity();
              e.newEntity(m.tList.get(h).getX(), m.tList.get(h).getY());
              m.tList.get(h).setOcuppied(Boolean.TRUE);
              e.setT(ass.get("unit1.png", Texture.class));
              e.setisEnemy(true);
              gem.addEntity(e);
            }
            
             m.tList.get(h).isSet = true;
            
          }}
         return true; }

    @Override
    public boolean touchUp(int i, int i1, int i2, int i3) {
        return true;}

    @Override
    public boolean touchDragged(int i, int i1, int i2) {
          Vector3 v3 = new Vector3();
          v3 = unproject(Gdx.input.getX(),Gdx.input.getY());
          
          for(int h = 0 ; h < mWidth * mHeight; h++ ){
            if(m.tList.get(h).r.contains(v3.x,v3.y)){
            if(typeSelected == "road" ){
               m.tList.get(h).setTile("road", getTexture(typeSelected,ass));
                m.tList.get(h).isSet = true;}
            if(typeSelected == "water" ){
               m.tList.get(h).setTile("water", getTexture(typeSelected,ass));
                m.tList.get(h).isSet = true;
                m.tList.get(h).isPathable=false;}
            if(typeSelected =="grass" ){
               m.tList.get(h).setTile("grass",getTexture(typeSelected,ass));
               m.tList.get(h).isSet = true;
             m.tList.get(h).isPathable=true;}
            if(typeSelected =="town" ){
              GameEntity e = new GameEntity();
              e.newEntity(m.tList.get(h).getX(), m.tList.get(h).getY());
              m.tList.get(h).setOcuppied(Boolean.TRUE);
              e.setT(ass.get("town.png", Texture.class));
              gem.addtown(e);
               m.tList.get(h).setTile("town",getTexture(typeSelected,ass));
                m.tList.get(h).isSet = true;}
            
             m.tList.get(h).isSet = true;
               System.out.println(h);
         
            }
          }
        return true; }

    @Override
    public boolean mouseMoved(int i, int i1) {
        mouse.x = i;
        mouse.y = i1;
        mousex = i;
        mousey = i1;
          
        
        return true; }

    @Override
    public boolean scrolled(int i) {
        
        if(i < 0){
        camera.zoom -= 0.02f;
        camera.update();}else{
         camera.zoom += 0.02f;
        }
        
        return true; }

    private void creategrid(int mWidth, int mHeight) {
        int tempx = 0;
        int tempy = 0;
        int mSize = mWidth * mHeight;
        
        
        for (int i = 0; i < mSize ; i++ ){  
            
            /// add all tiles
            
             t = new Tile(tempx,tempy,tSize,tSize);
             
             m.tList.add(t);
             
               if(tempx != ( mWidth * tSize) ){
                  tempx +=tSize;
               }
               if(tempx == ( mWidth * tSize)){
                 tempx = 0;
                 tempy +=tSize;
               }
              
          } 
    
    }
    private void drawgrid(SpriteBatch sb,ShapeRenderer sr) {
        
       for(int i = 0; i < m.tList.size() ; i++){
           sr.box(m.tList.get(i).x, m.tList.get(i).y, 0, tSize , tSize, 0);
           if(m.tList.get(i).isSet == true){
             sb.begin();
              sb.draw(ass.get(m.tList.get(i).type + ".png",Texture.class)  , m.tList.get(i).x, m.tList.get(i).y);
              sb.end();
             
              
          } 
          
         
  //
       } 
     
    }

    private void movemouse() {
       if (Gdx.input.isKeyPressed(Keys.A)){
           camera.translate(-3, 0, 0);
           setUIboxLocation();
         }
         if (Gdx.input.isKeyPressed(Keys.S)){
           camera.translate(0, -3, 0);
         setUIboxLocation();}
         if (Gdx.input.isKeyPressed(Keys.D)){
           camera.translate(3, 0, 0);
           setUIboxLocation();
         }
         if (Gdx.input.isKeyPressed(Keys.W)){
             camera.translate(0, 3, 0);
             setUIboxLocation();
         }
        camera.update(); }
    
    public void Save(String temp){
        
       try {
         FileOutputStream fileOut =
         new FileOutputStream( "./maps/" + temp + ".map");
         ObjectOutputStream out = new ObjectOutputStream(fileOut);
         out.writeObject(m);
         out.close();
         fileOut.close();
         
      }catch(IOException i) {
         i.printStackTrace();
      }
      try {
         FileOutputStream fileOut =
         new FileOutputStream( "./maps/" + temp + ".dat");
         ObjectOutputStream out = new ObjectOutputStream(fileOut);
         out.writeObject(gem);
         out.close();
         fileOut.close();
         
      }catch(IOException i) {
         i.printStackTrace();
      }
   }
     
    
    
      ////text in put listener methods
    @Override
    public void input(String string) {
        String temp = string;
       
        Save(temp);
        
      }

    @Override
    public void canceled() {
        }

    private void drawentitys(SpriteBatch sb, ShapeRenderer sr) {
         sb.begin();
       for(GameEntity e : gem.entitys){
         sb.draw(ass.get("unit1.png", Texture.class ),e.getX(),e.getY());
        
       } sb.end();
    }
     @Override
     public void LoadMap() {
    
               
               mg = new MainGame(camera,ass, m);
               mg.rebuildmap(m);
               Gdx.input.setInputProcessor(mg);
               gs = gs.Game;
              
         }

    private void LoadMapdata() {
      
               mg.putMapData(gem);
            }
 

    public void getMapName() {
      Gdx.input.getTextInput(this, "name", "example", "");  }

    private void createUI() {
         Tile t  = new Tile(Math.round(camera.viewportHeight/10),Math.round(camera.viewportHeight/10),
                            Math.round( camera.viewportHeight/10),Math.round(camera.viewportHeight/10));
         t.setTile("town",getTexture(typeSelected,ass));
         t.type = "town";
  ui.add(t);
         Tile t2  = new Tile(Math.round(camera.viewportHeight/10),Math.round(camera.viewportHeight/10),
                            Math.round( camera.viewportHeight/10),Math.round(camera.viewportHeight/10));
         t2.setTile("unit1",getTexture(typeSelected,ass));
         t2.type = "entity";
  ui.add(t2);
    Tile t3  = new Tile(Math.round(camera.viewportHeight/10),Math.round(camera.viewportHeight/10),
                            Math.round( camera.viewportHeight/10),Math.round(camera.viewportHeight/10));
         t3.setTile("road",getTexture(typeSelected,ass));
         t3.type = "road";
  ui.add(t3);
         Tile t4  = new Tile(Math.round(camera.viewportHeight/10),Math.round(camera.viewportHeight/10),
                            Math.round( camera.viewportHeight/10),Math.round(camera.viewportHeight/10));
         t4.setTile("water",getTexture(typeSelected,ass));
         t4.type = "water";
  ui.add(t4);
         Tile t5  = new Tile(Math.round(camera.viewportHeight/10),Math.round(camera.viewportHeight/10),
                            Math.round( camera.viewportHeight/10),Math.round(camera.viewportHeight/10));
         t5.setTile("grass",getTexture(typeSelected,ass));
         t5.type = "grass";
  ui.add(t5);
         
         for(Tile ty : ui){
              System.out.println(ty.type);
             
          }
         
         
         
         setUIboxLocation();
       
    }
    private void setUIboxLocation(){
        int counter = 0;
       for(Tile r : ui){
           r.setX(Math.round(camera.position.x) - (Gdx.graphics.getWidth()/2));
           r.setY(Math.round(camera.position.y) - counter );
           r.setr(Math.round(camera.position.x) - (Gdx.graphics.getWidth()/2), 
                 (Math.round(camera.position.y) - counter ), r.rwidth    , r.rheight);
           counter +=32;
     } 
           counter = 0;
    }
    public Tile getUI(int i){
      return ui.get(i);
    }
    public void addUI(Tile t){
      ui.add(t);
    }

    public void drawui(SpriteBatch sb, ShapeRenderer sr) {
       
       sr.setColor(Color.BLUE);
       sr.circle(ui.get(0).x, ui.get(0).y, tSize);
       sr.circle(ui.get(0).x, ui.get(1).y, tSize);
       sr.circle(ui.get(0).x, ui.get(2).y, tSize);
       sr.circle(ui.get(0).x, ui.get(3).y, tSize);
       sr.circle(ui.get(0).x, ui.get(4).y, tSize);
       sb.begin();
       sb.draw(ass.get("grass.png", Texture.class ),ui.get(0).x,ui.get(4).y);
       sb.draw(ass.get("water.png", Texture.class ),ui.get(1).x,ui.get(3).y);
        sb.draw(ass.get("road.png", Texture.class ),ui.get(2).x,ui.get(2).y);
        sb.draw(ass.get("town.png", Texture.class ),ui.get(3).x,ui.get(0).y);
       sb.draw(ass.get("unit1.png", Texture.class ),ui.get(4).x,ui.get(1).y);
       
       sb.end();
      
    }
}
