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
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
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
public class PumbaManger extends MyGdxGame implements InputProcessor , Serializable ,TextInputListener{
     int mHeight;
     int mWidth;
     int tSize;
     Tile t;
     ArrayList<Tile> tList ;
     transient Rectangle mouse;
      transient int mousex;
     transient  int mousey;
      String type = "grass";
     
     
    
    
     public PumbaManger(int height, int width,int size,OrthographicCamera camera){
     mHeight = height;
     mWidth = width;
    
      
     tSize = size;
     this.camera = camera;
     camera.setToOrtho(true, 1024, 720);
     tList = new ArrayList<Tile>();
     mouse = new Rectangle();
     mouse.set(0, 0, 1, 1);
      creategrid(mWidth,mHeight);
     
     
      
      
     }
           
     
     @Override
     public void create(){
          
         
     }
     void render(SpriteBatch sb,ShapeRenderer sr){
         Gdx.gl.glClearColor(1, 1, 1, 1);
	 Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
         sr.setProjectionMatrix(camera.combined);
         movemouse();
         camera.update();
         sr.begin(ShapeRenderer.ShapeType.Line);
         sr.setColor(Color.BLACK);
         drawgrid(sb, sr);
         sr.end();
     
     }

    @Override
    public boolean keyDown(int i) {
        if (Gdx.input.isKeyPressed(Keys.NUM_1)){
           type = "grass";
         } 
        if (Gdx.input.isKeyPressed(Keys.NUM_2)){
           type = "road";
         } 
        if (Gdx.input.isKeyPressed(Keys.NUM_3)){
           type = "water";
         } 
         if (Gdx.input.isKeyPressed(Keys.ENTER)){
           Gdx.input.getTextInput(this, "name", "example", "");
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
          
          for(int h = 0 ; h < mWidth * mHeight; h++ ){
            if(tList.get(h).r.contains(v3.x,v3.y)){
               System.out.println(h);
            if(type == "road" ){
               tList.get(h).setTile("road", getTexture(type));
            }
            if(type == "water" ){
               tList.get(h).setTile("water", getTexture(type));}
            if(type =="grass" ){
               tList.get(h).setTile("grass",getTexture(type));}
             tList.get(h).isSet = true;
            
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
            if(tList.get(h).r.contains(v3.x,v3.y)){
                 if(type == "road" ){
               tList.get(h).setTile("road", getTexture(type));
            }
            if(type == "water" ){
               tList.get(h).setTile("water", getTexture(type));}
            if(type =="grass" ){
               tList.get(h).setTile("grass",getTexture(type));}
             tList.get(h).isSet = true;
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
             
             tList.add(t);
             
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
       for(int i = 0; i < tList.size() ; i++){
          sr.box(tList.get(i).x, tList.get(i).y, 0, tSize , tSize, 0);
          if(tList.get(i).isSet == true){
              sb.begin();
              sb.draw(tList.get(i).t  , tList.get(i).x, tList.get(i).y);
          sb.end();} else {}
       
       }   
    }
       private Vector3 unproject(int i ,int i2) {
         Vector3 v3 = new Vector3();
        v3.set(i,i2,0);
        v3 = camera.unproject(v3);
        return v3;
    } 

    private void movemouse() {
       if (Gdx.input.isKeyPressed(Keys.A)){
           camera.translate(-3, 0, 0);
         }
         if (Gdx.input.isKeyPressed(Keys.S)){
           camera.translate(0, -3, 0);}
         if (Gdx.input.isKeyPressed(Keys.D)){
           camera.translate(3, 0, 0);
         }
         if (Gdx.input.isKeyPressed(Keys.W)){
             camera.translate(0, 3, 0);
         }
        camera.update(); }
    
    public void Save(String temp){
        map m = new map();
        m.tList = tList;
        m.width = mWidth;
        m.height = mHeight;
        m.tilewidth = tSize;
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
   
}
