/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.Pumba.lou;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import static com.badlogic.gdx.graphics.Color.rgba8888;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 *
 * @author Stefan
 */
public class PixmapEditor extends MyGdxGame {
    Pixmap pixie;
    Texture pixmaptex;
    
    Vector3 v3;
      final int cols = 13;
      final int rows = 21;
      Animation walkright;
      Animation walkleft;
      Animation walkdown;
      Animation walkup;
    public PixmapEditor() {
      
      
        
///////////////////////////////////////////////////////////white  =  -1         /////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////grey1  =  -437918209/////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////grey2  =  -858993409/////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////grey3  =  -1296911617/////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////grey4  =  -1717986817/////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////grey5  =   2139062271/////////////////////////////////////////////////////////
      
    }
    public void render(SpriteBatch sb){
      
    
    }
    public void  PutColor(String g,Boolean isRed){
      pixie = new Pixmap(Gdx.files.internal(g));
     pixie.setColor( 1, 1, 1, 1 );
     //  pixie = new Pixmap(Gdx.files.internal("unit1.png"));
    if(!isRed){
     for(int x = 0 ; x < pixie.getWidth(); x++){
        for(int y =0 ; y < pixie.getHeight();y++){
            if(pixie.getPixel(x, y) == 2139062271){
             pixie.drawPixel(x, y, rgba8888(0,0,1f,1f));
             ///agbr
            }
            if(pixie.getPixel(x, y) == -1){
             pixie.drawPixel(x, y, rgba8888(0,0,1f,1f));
             
             }
          if(pixie.getPixel(x, y) == -437918209){
           pixie.drawPixel(x, y, rgba8888(0,0,1f,1f));
           
          }
          if(pixie.getPixel(x, y) == -858993409){
           pixie.drawPixel(x, y, rgba8888(0,0,1f,1f));
           
          }
         if(pixie.getPixel(x, y) == -1296911617){
          pixie.drawPixel(x, y, rgba8888(0,0,1f,1f));
          
         }
         if(pixie.getPixel(x, y) == -1717986817){
         pixie.drawPixel(x, y, rgba8888(0,0,1f,1f));
          
         }
  //         System.out.println(x+"+"+y);
           
        }
     }
    }
    if(isRed){
     for(int x = 0 ; x < pixie.getWidth(); x++){
        for(int y =0 ; y < pixie.getHeight();y++){
            if(pixie.getPixel(x, y) == 2139062271){
             pixie.drawPixel(x, y, rgba8888(1f,0,0,1f));
             ///agbr
            }
            if(pixie.getPixel(x, y) == -1){
             pixie.drawPixel(x, y, rgba8888(1f,0,0,1f));
             
             }
          if(pixie.getPixel(x, y) == -437918209){
           pixie.drawPixel(x, y, rgba8888(1f,0,0,1f));
           
          }
          if(pixie.getPixel(x, y) == -858993409){
           pixie.drawPixel(x, y, rgba8888(1f,0,0,1f));
           
          }
         if(pixie.getPixel(x, y) == -1296911617){
          pixie.drawPixel(x, y, rgba8888(1f,0,0,1f));
          
         }
         if(pixie.getPixel(x, y) == -1717986817){
         pixie.drawPixel(x, y, rgba8888(1f,0,0,1f));
          
         }
  //         System.out.println(x+"+"+y);
           
        }
     }
    }
        pixmaptex = new Texture( pixie );
        
    }

  public void DefineTextureRegionsEntity(Boolean isFlipped){
    System.out.println("define");
              
        TextureRegion[][] tmp = TextureRegion.split(pixmaptex,128,128);
        TextureRegion[]anim = new TextureRegion[273];
        int index = 0 ;
        for (int i = 0; i<cols; i++){
          for (int j = 0; j <rows; j++){
            
            anim[index++] = tmp[j][i];
           
            if(isFlipped){
            anim[index-1].flip(false, true);}
          }
        }
        walkright(anim);
        walkdown(anim);
        walkleft(anim);
        walkup(anim);
        
    }
    public void walkright(TextureRegion[]anim){
                   walkright = new Animation(0.25f,anim[11],anim[32],anim[53],anim[74],anim[95],
                                                 anim[116],anim[137],anim[158],anim[179]);
                                              }
    public void walkdown(TextureRegion[]anim){
                   walkdown = new Animation(0.25f,anim[10],anim[31],anim[52],anim[73],anim[94],
                                                 anim[115],anim[136],anim[157],anim[178]);
                                             }
    public void walkleft(TextureRegion[]anim){
                   walkleft = new Animation(0.25f,anim[9],anim[30],anim[51],anim[72],anim[93],
                                                 anim[114],anim[135],anim[156],anim[177]);
                                             }
    public void walkup(TextureRegion[]anim){
                   walkup = new Animation(0.25f,anim[8],anim[29],anim[50],anim[71],anim[92],
                                                 anim[113],anim[134],anim[155],anim[176]);
                                             }
    public Animation getright(){
       return walkright;
    }
    public Animation getleft(){
      return walkleft;
    }
    public Animation getup(){
      return walkup;
    }
    public Animation getdown(){
      return walkdown;
    }
public void getPixelColor(Vector3 v3,GameEntity e){
  System.out.println(pixie.getPixel(Math.round(v3.x)-e.getX(), Math.round(v3.y)-e.getY()));
  System.out.println(Math.round(v3.x)-e.getX()+ "+"+  (Math.round(v3.y)-e.getY()));
}

}
