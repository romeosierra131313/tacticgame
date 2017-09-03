/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.Pumba.lou;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Stefan
 */
public class MainGame extends MyGdxGame implements InputProcessor  {
     map m;
     UnitFactory uf;
     GameEntityManager gem;
     PathFinding pf;
     PixmapEditor px;
     float dt;
     float cameradt;
     Rectangle rt;
     actionUI aui;
     Boolean actionUIisOpen = false;
     Boolean moveSelected = false;
     Boolean attackSelected = false;
     Boolean captureSelected = false;
     Boolean waitSelected = false;
     HashMap<Vector2,Tile> maplist;
     HashMap<Vector2,GameEntity> townlist;
     Vector3 v3 = new Vector3();
     Vector2 v2 = new Vector2();
     Vector2 cameracentre = new Vector2();
     
     int movecounter;
      Texture tree;
    
    public MainGame(OrthographicCamera camera,AssetManager ass,map m){
     this.m = m;
     this.ass = ass;
     this.camera = camera;
     rt = new Rectangle();
     camera.setToOrtho(false, 800, 480);
      
      maplist = new HashMap();
      townlist = new HashMap();
      aui = new actionUI();
      uf = new UnitFactory(ass);
      pf = new PathFinding(maplist);
      gem = new  GameEntityManager();
      px = new PixmapEditor(ass);
      tree = ass.get("tree1.png", Texture.class);
       
    }
    
  public void render(SpriteBatch sb,ShapeRenderer sr,BitmapFont bf){
      sb.setProjectionMatrix(camera.combined);
       camera.update();
     dt += Gdx.graphics.getDeltaTime();
     cameradt+= Gdx.graphics.getDeltaTime();
     if(dt >= 1f){
      dt = 0;
  }  
     if(cameradt >= 1f){
      cameradt = 0;
  } 
      movecharacters();
      movecamera();
     m.render(sb, sr, camera);
     rendermap(sb, sr,bf,ass);
     pf.rendermoveable(sb, sr, camera);
     px.render(sb);
     gem.render(sb, sr,bf, camera,dt); 
    
    
    if(actionUIisOpen){
     
      aui.render(sb, sr,bf, camera);
    }
 
    
    
  }  
      private void rendermap(SpriteBatch sb,ShapeRenderer sr,BitmapFont bf,AssetManager ass) {
          
       for(int i = 0; i < m.tList.size() ; i++){
       if(m.getTile(i).type.compareTo("null") == 0 ){}
         else{ 
           sb.draw(ass.get(m.getTile(i).type + ".png"  ,Texture.class) , m.tList.get(i).x, m.tList.get(i).y);
             if(m.getTile(i).type.compareTo("grass") == 0){
                 sb.setColor(Color.GREEN);
                 
                 sb.draw(tree, m.tList.get(i).x,  m.tList.get(i).y);
                  sb.setColor(Color.WHITE);
             }
       }
      
       
       }   }

@Override
  public boolean keyDown(int i) {
         if (Gdx.input.isKeyPressed(Input.Keys.NUM_1)){
          tree = px.dosomething();
         } 
       return true; }
@Override
  public boolean keyUp(int i) {
       return true; }
@Override
  public boolean keyTyped(char c) {
        return true; }
@Override
  public boolean touchDown(int i, int i1, int i2, int i3) {
       v3 = unproject(Gdx.input.getX(),Gdx.input.getY());
        System.out.println("v3 - "+v3);
        shouldOpenMenu(v3);
      if(i3 == 1){
           resetGameState();
        }
         if(moveSelected && !gem.getWaiting().getMoved() ){
               setmovement(v3);
                System.out.println("move");
               resetMenu();
               }
            if(attackSelected && !gem.getWaiting().gethasAttacked()){
               attack(v3);
                 System.out.println("attack");
               resetMenu();}
            if(captureSelected && !gem.getWaiting().gethasCaptured() ){
               captureaTown(v3);
                 System.out.println("capture");
               resetMenu();
               } 
            if(waitSelected && gem.getWaiting().getisTurnDone() ){
             
             waits();
               resetMenu();
               }
       
       

        if(gem.getWaiting() !=null){
           
            
          }
        if( actionUIisOpen){
                getchoice();
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
    public Vector2 whichTile(Vector3 v3){
       v2 = new Vector2();
       for(int p = 0; p <= m.tList.size()-1;p++){
                   if(m.tList.get(p).r.contains(v3.x, v3.y)){
                  
                     
                     v2.set(m.tList.get(p).getX(), m.tList.get(p).getY());
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
              int place = 1;
              System.out.println(e.getLocation());
              cameracentre.set(e.getX(), e.getY());
              pf.reset();
              pf.neighbours.clear();
              pf.getNeighbor(maplist.get(gem.getWaiting().getLocation()));
              if(!e.getMoved()){
              pf.getpathable(gem.getWaiting());
              aui.addaction(uf.newMove(place,e.getX(), e.getY()));
              place++;
              actionUIisOpen = true;
              
                         }
              if(!e.gethasCaptured()){ 
              for(Tile t : pf.neighbours){
                 for(GameEntity f : gem.towns){
                    if(f.getX() == t.getX() && f.getY() == t.getY()){
                       aui.addaction(uf.newCapture(place, e.getX(), e.getY()));
                        System.out.println("attacking");
                       place++;
                    } 
                 }
               }
              }
              int hasneighbour = 0;
         if(!e.gethasAttacked()){ 
           for(GameEntity ge : gem.entitys){    
            for (Tile t : pf.neighbours){
                if(t.getX() == ge.getX() && t.getY() == ge.getY() ){
                    hasneighbour = 1;
                    
                                     
                                   }
                                 }
                                }
                     if(hasneighbour == 1){
                         aui.addaction(uf.newAttack(place, e.getX(), e.getY()));
                        place++;
                        actionUIisOpen = true;
                     }else{}
                               }
            aui.addaction(uf.newWait(place, e.getX(), e.getY()));
            actionUIisOpen = true;
         }
    private void movecharacters() {
     
      if(gem.getMoving() != null && pf.ready == true){ 
          aui.actionUIitems.clear();
          
         if(pf.Path.get(movecounter).x > gem.getMoving().getX()){
                gem.getMoving().East = true;
                gem.getMoving().West = false;
                gem.getMoving().South = false;
                gem.getMoving().North = false;
               
         }
          if(pf.Path.get(movecounter).x < gem.getMoving().getX()){ 
                gem.getMoving().West = true;
                gem.getMoving().East = false;
                gem.getMoving().South = false;
                gem.getMoving().North = false;}
          if(pf.Path.get(movecounter).y > gem.getMoving().getY()){
                gem.getMoving().North = true;
                gem.getMoving().West = false;
                gem.getMoving().East = false;
                gem.getMoving().South = false;;
          } 
           if(pf.Path.get(movecounter).y < gem.getMoving().getY()){
                gem.getMoving().South = true;
                gem.getMoving().North = false;
                gem.getMoving().West = false;
                gem.getMoving().East = false;}
         
         if( gem.getMoving().getX() == pf.Path.get(movecounter).x &&
           gem.getMoving().getY() == pf.Path.get(movecounter).y){
            
           if(movecounter <=pf.Path.size()-2){ 
           movecounter+=1;}
       } 
          
       if( gem.getMoving().getX() != pf.Path.get(movecounter).x || gem.getMoving().getY() != pf.Path.get(movecounter).y){
      
      gem.getMoving().move(pf.Path.get(movecounter).x,pf.Path.get(movecounter).y, dt); 
      
      }
       if( gem.getMoving().getX() == pf.Path.get(pf.Path.size()-1).x &&
           gem.getMoving().getY() == pf.Path.get(pf.Path.size()-1).y){
             if(pf.Path.size()!=0){
                   
                   cameracentre.set(whichTile(v3));
                   maplist.get(gem.getMoving().getLocation()).setOcuppied(true);
                   aui.actionUIitems.clear();
                   gem.getWaiting().setMoved(true);
                   gem.getMoving().setIsMoving(false);
                   openactionUI(gem.getWaiting());
                   moveSelected = false;
             }
         }
      
       }
    }
    private void movecamera() {
       
        if(cameradt > 0.1f){
        if(camera.position.x != cameracentre.x || camera.position.y != cameracentre.y ){
            if(camera.position.x - cameracentre.x > 0){
       camera.position.set(camera.position.x-2, camera.position.y, 0);}
            if(camera.position.y - cameracentre.y > 0){
       camera.position.set(camera.position.x, camera.position.y-2, 0);}
            if(camera.position.x - cameracentre.x < 0){
       camera.position.set(camera.position.x+2, camera.position.y, 0);}
            if(camera.position.y - cameracentre.y < 0){
       camera.position.set(camera.position.x, camera.position.y+2, 0);}
    }}}
    private void getchoice() {
      for (actionUIitem e :  aui.actionUIitems){
        if(e.r.contains(v3.x, v3.y)){
                       actionUIisOpen = false;
        if(e.s.compareTo("move") == 0){
                       moveSelected = true; }
        if(e.s.compareTo("Attack") == 0){
                       attackSelected = true;
                      
                        }
        if(e.s.compareTo("Capture") == 0){
                       captureSelected = true;
                      
                        }
        if(e.s.compareTo("Wait") == 0){
                       waits();
                        }
                       }
                    } 
                   }
    private void resetMenu(){
    
     moveSelected = false;
     attackSelected = false;
     captureSelected = false;
    }
    private void setmovement(Vector3 v3) {
        System.out.println(v3);
                 
                 Vector2 v2start = new Vector2(gem.getWaiting().getX(),gem.getWaiting().getY());   
                 Vector2 end = new Vector2(whichTile(v3));
                 pf.setPath(v2start,end,gem.getWaiting());
                 if(pf.Path.size()==0){
                   actionUIisOpen = true;
                 }
                 
                 if(pf.Path.size()>0){
                 gem.getWaiting().setMoving();
                 maplist.get(gem.getWaiting().getLocation()).setOcuppied(false);
                 }
                 movecounter = 0;
                 }
    private void openmenu(Vector3 v3) {
     aui.actionUIitems.clear();
     System.out.println("opening a menu");
              if( whichEntity(v3) != null){
                whichEntity(v3).setWaiting(true);
                pf.neighbours.clear();
                pf.getNeighbor(pf.maplist.get(gem.getWaiting().getLocation()));
                openactionUI(gem.getWaiting());
                }
             }

    private void attack(Vector3 v3) {
                  int hasAttacked = 3;
                  pf.neighbours.clear();
                pf.getNeighbor(pf.maplist.get(gem.getWaiting().getLocation()));
                 System.out.println("attacking");
                 if(!gem.getWaiting().gethasAttacked()){
                     System.out.println("has not attacked");
                  if(whichEntity(v3) ==null){
                      System.out.println("nobody there"); 
                      hasAttacked = 0;
                  }
                  if(whichEntity(v3) !=null){
                      System.out.println("whichenitity is there");
                       for(Tile til : pf.neighbours){
                          System.out.println(til.getX() + "+a"+ til.getY());
                       if(til.r.contains(v3.x,v3.y)){
                              System.out.println("dmg");
                              hasAttacked = 1;
                              whichEntity(v3).getAttacked( gem.getWaiting().getAttack());
                        if( whichEntity(v3).getDead()){
                              whichEntity(v3).setT(ass.get("dead.png", Texture.class));
                              gem.entitysDead.add(whichEntity(v3));
                              gem.entitys.remove(whichEntity(v3));
                   }
                  } 
                 }
                }
                  if(hasAttacked == 1){
                        gem.getWaiting().sethasAttacked(true);
                  }
                  aui.actionUIitems.clear();
                  openactionUI(gem.getWaiting());
                 
                 
                 
                  
                  }  
                    }

    private void resetGameState() {
          gem.giveGold();
          gem.resetisMoved();
          resetMenu();
          aui.actionUIitems.clear();
          
              for(GameEntity e : gem.entitys){
                 e.setMoved(Boolean.FALSE);
                 e.sethasAttacked(Boolean.FALSE);
                 e.sethasCaptured(Boolean.FALSE);
                 e.setisTurnDone(Boolean.FALSE);
                 e.setIsMoving(Boolean.FALSE);
                 e.resetsetMovingandWaiting();
              }
               for(GameEntity t : gem.towns){
                 
                 t.setisTurnDone(Boolean.FALSE);
                 t.resetsetMovingandWaiting();
              }
    
    
    }

    private void captureaTown(Vector3 v3) {
                pf.neighbours.clear();
                pf.getNeighbor(pf.maplist.get(gem.getWaiting().getLocation()));
                      int i = 3 ;
                      Vector2 townlocation = new Vector2(); 
              for(GameEntity t : gem.towns){
                  if(t.r.contains(v3.x,v3.y)){
                     for(Tile til : pf.neighbours){
                          System.out.println(til.getX() + "+c"+ til.getY());
                       if(til.r.contains(v3.x,v3.y)){
                           if(gem.getWaiting().getisEnemy()){
                             i = 1;
                             townlocation.set(t.getLocation());
                            
                          }
                          if(!gem.getWaiting().getisEnemy()){
                              i = 0;
                              townlocation.set(t.getLocation());
                             
                          }
                  aui.actionUIitems.clear();
                  
                       }
                     }
                  }
              }  
                 
                 if(i == 0){
                      gem.getWaiting().sethasCaptured(true);
                      openactionUI(gem.getWaiting());
                     townlist.get(townlocation).setisEnemy(Boolean.FALSE);
                     System.out.println("friendly");
                 }
                 if(i == 1){
                    gem.getWaiting().sethasCaptured(true);
                   openactionUI(gem.getWaiting());
                     townlist.get(townlocation).setisEnemy(Boolean.TRUE);
                     System.out.println("enemy");
                 }
               
                  
    }
    
    ///////maploading///////////
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
    public void putMapData(GameEntityManager g){
     for(GameEntity e : g.entitys){
         
        uf.newUnit("unit1", e.getX(), e.getY());
        e.setT(ass.get("unit1.png", Texture.class));
        e.setHitbox(e.getX(), e.getY(),32   , 32);
        camera.position.set(e.getX(), e.getY(), 0);
        maplist.get(e.getLocation()).setOcuppied(true);
        e.setHp(10);
        e.setAttack(10);
        gem.addEntity(e);
       
     }  
      for(GameEntity e : g.towns){
        uf.newUnit("unit1", e.getX(), e.getY());
        e.setT(ass.get("town.png", Texture.class));
        e.setHitbox(e.getX(), e.getY(),32   , 32);
        maplist.get(e.getLocation()).setOcuppied(true);
        e.setHp(10);
        e.setAttack(1);
        gem.addtown(e);
        townlist.put(e.getLocation(), e);
       
     }cameracentre.set(gem.entitys.get(0).getLocation());
    
    }

    private void waits() {
                   
                       gem.getWaiting().setisTurnDone(Boolean.TRUE);
                       gem.getWaiting().setWaiting(Boolean.FALSE);
                                     
    }  

  private void shouldOpenMenu(Vector3 v3) {
              if(!actionUIisOpen && gem.getWaiting() == null){
            if(whichEntity(v3) != null && gem.getWaiting() == null){
               if(!whichEntity(v3).getisTurnDone()){
                openmenu(v3);
               }
              
            }
            if(whichEntity(v3) !=null && whichEntity(v3).getisTurnDone()){
              pf.reset();
              pf.getpathable(whichEntity(v3));
               System.out.println("turn is done");
            }
        
        }}
  public void setMap(map m){
    this.m = m;
   }  
  public map getMap(){
    return m;
  }
}
