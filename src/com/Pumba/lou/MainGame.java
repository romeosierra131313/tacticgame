/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.Pumba.lou;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

/**
 *
 * @author Stefan
 */
public class MainGame extends MyGdxGame implements InputProcessor  {
     map m;
    
    
    public MainGame(OrthographicCamera camera,AssetManager ass,map m){
      this.m = m;
       this.ass = ass;
     this.camera = camera;
     camera.setToOrtho(true, 1024, 720);

     
    }
    
  public void render(SpriteBatch sb,ShapeRenderer sr,OrthographicCamera camera){
      Gdx.gl.glClearColor(1, 1, 1, 1);
      Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
      
     m.render(sb, sr, camera);

   sb.begin();
   drawgrid(sb, sr,ass);
       
        sb.end();
    
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
    
        private void rebuildmap(map m,AssetManager ass) {
           
           for(int i = 0 ; i < m.tList.size();i++){
               
             
              m.getTile(i).setTexture(ass.get(m.getTile(i).type,Texture.class));
              
            }
            
              
            
            
    }
}
