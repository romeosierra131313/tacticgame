/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.Pumba.lou;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.graphics.Color;
import static com.badlogic.gdx.graphics.Color.argb8888;
import static com.badlogic.gdx.graphics.Color.rgba8888;

/**
 *
 * @author Stefan
 */
public class PixmapEditor {
    Pixmap pixie;
    Texture pixmaptex;
    Vector3 v3;
    public PixmapEditor(AssetManager ass) {
       
        pixie = new Pixmap(Gdx.files.internal("unit1.png"));
        pixmaptex = new Texture( pixie );
///////////////////////////////////////////////////////////white  =  -1         /////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////grey1  =  -437918209/////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////grey2  =  -858993409/////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////grey3  =  -1296911617/////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////grey4  =  -1717986817/////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////grey5  =   2139062271/////////////////////////////////////////////////////////
      
    }
    public void render(SpriteBatch sb){
       sb.draw(pixmaptex, 0, 0);
    
    }
    public Texture  BlueUnit1(GameEntity e){
      pixie.setColor( 1, 1, 1, 1 );
      
   if(e.getType().compareTo("unit1") == 0){


       
     //  pixie = new Pixmap(Gdx.files.internal("unit1.png"));
       for(int x = 0 ; x < pixie.getWidth(); x++){
          for(int y =0 ; y < pixie.getHeight();y++){
              if(pixie.getPixel(x, y) == 2139062271){
               System.out.println(x+"+"+y);
               System.out.println(pixie.getPixel(x, y));
               pixie.drawPixel(x, y);
               pixie.drawPixel(x, y, rgba8888(0,0,0.1f,1f));
               ///agbr
              }
              if(pixie.getPixel(x, y) == -1){
               System.out.println(x+"+"+y);
               System.out.println(pixie.getPixel(x, y));
               pixie.drawPixel(x, y);
               pixie.drawPixel(x, y, rgba8888(0,0,1f,1f));
               
              }
              if(pixie.getPixel(x, y) == -437918209){
               System.out.println(x+"+"+y);
               System.out.println(pixie.getPixel(x, y));
               pixie.drawPixel(x, y);
               pixie.drawPixel(x, y, rgba8888(0,0,.8f,1f));
               
              }
              if(pixie.getPixel(x, y) == -858993409){
               System.out.println(x+"+"+y);
               System.out.println(pixie.getPixel(x, y));
               pixie.drawPixel(x, y);
               pixie.drawPixel(x, y, rgba8888(0,0,.6f,1f));
               
              }
              if(pixie.getPixel(x, y) == -1296911617){
               System.out.println(x+"+"+y);
               System.out.println(pixie.getPixel(x, y));
               pixie.drawPixel(x, y);
               pixie.drawPixel(x, y, rgba8888(0,0,.4f,1f));
               
              }
              if(pixie.getPixel(x, y) == -1717986817){
              System.out.println(x+"+"+y);
              System.out.println(pixie.getPixel(x, y));
              pixie.drawPixel(x, y);
              pixie.drawPixel(x, y, rgba8888(0,0,.2f,1f));
               
              }
              }
              
          }
       }
   
       
      // pixie.fillCircle(64, 64, 10);
        pixmaptex = new Texture( pixie );
        
        return pixmaptex;
    }
  
public void getPixelColor(Vector3 v3,GameEntity e){
  System.out.println(pixie.getPixel(Math.round(v3.x)-e.getX(), Math.round(v3.y)-e.getY()));
  System.out.println(Math.round(v3.x)-e.getX()+ "+"+  (Math.round(v3.y)-e.getY()));
}

}
