/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.Pumba.lou;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import java.util.ArrayList;

/**
 *
 * @author Stefan
 */
public class MainGame extends MyGdxGame implements InputProcessor  {
    private map m;
    
    public MainGame(OrthographicCamera camera){
        this.camera = camera;
    
    }
    
  public void render(SpriteBatch sb,ShapeRenderer sr){
      Gdx.gl.glClearColor(1, 1, 1, 1);
	 Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
  sb.begin();
  for(int i = 0 ; i < m.tList.size();i++){
     if(m.tList.get(i).type.compareTo("null") == 0 ){
      
     }else{ 
          if(m.tList.get(i).type.compareTo("water") == 0){sb.draw(getTexture("water"), m.tList.get(i).x, m.tList.get(i).y);}
          if(m.tList.get(i).type.compareTo("road") == 0){sb.draw(getTexture("road"), m.tList.get(i).x, m.tList.get(i).y);}
           if(m.tList.get(i).type.compareTo("grass") == 0){sb.draw(getTexture("grass"), m.tList.get(i).x, m.tList.get(i).y);}
         //sb.draw(getTexture(m.tList.get(i).type), m.tList.get(i).x, m.tList.get(i).y);
     System.out.println(m.tList.get(i).type + i);}
  }
  sb.end();
  }  
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
    
    
}
