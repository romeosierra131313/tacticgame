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
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
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
     PixmapEditor px;
     float dt;
     float cameradt;
     Rectangle rt;
     actionUI aui;
     Boolean actionUIisOpen = false;
     Boolean townUIisOpen = false;
     Boolean moveSelected = false;
     Boolean attackSelected = false;
     Boolean captureSelected = false;
     Boolean waitSelected = false;
     Boolean unitBeingBought = false;
     String unit = "null";
     HashMap<Vector2,Tile> maplist;
     HashMap<Vector2,GameEntity> townlist;
     Vector3 v3 = new Vector3();
     Vector2 v2 = new Vector2();
     Vector2 cameracentre = new Vector2();
     int close = 1;
     int movecounter;
      Texture tree;
    
    public MainGame(OrthographicCamera camera,AssetManager ass,map m){
     this.m = m;
     this.ass = ass;
     this.camera = camera;
     rt = new Rectangle();
     camera.setToOrtho(false, 800, 480);
     camera.zoom += 2f;
      
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
     pf.render(sb, sr, camera);
     px.render(sb);
     gem.render(sb, sr,bf, camera,dt); 
    
    if(actionUIisOpen || townUIisOpen){
     
      aui.render(sb, sr,bf, camera);
    }
 
    
    
  }  
      private void rendermap(SpriteBatch sb,ShapeRenderer sr,BitmapFont bf,AssetManager ass) {
          
       for(int i = 0; i < m.tList.size() ; i++){
       if(m.getTile(i).type.compareTo("null") == 0 ){}
         else{ 
           sb.draw(ass.get(m.getTile(i).type + ".png"  ,Texture.class) , m.tList.get(i).x, m.tList.get(i).y);
          }
       } 
      
       for(int i = 0; i < m.tList.size() ; i++){
       if(m.getTile(i).type.compareTo("null") == 0 ){}
         else{ 
             if(m.getTile(i).type.compareTo("grass") == 0){
                 if(m.getTile(i).gethasDecorations() != 0){
                   sb.draw(tree, m.tList.get(i).getDecorX(),  m.tList.get(i).getDecorY());
                     if(m.getTile(i).gethasDecorations() >= 2){
                       sb.draw(tree, m.tList.get(i).getDecorX1(),  m.tList.get(i).getDecorY1());
                     }
                 
                 
                 } 
             }
       }
      
       
       } 
      }

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
      
        shouldOpenMenu(v3);
      if(i3 == 1){
           resetGameState();
        }
        doAction(v3);
      if(unitBeingBought){
            buyAunit(v3);
                     }
      if( actionUIisOpen){
                getchoice();
                     }
      if(townUIisOpen){
                getchoice();
      }
        
               return true; }
@Override
  public boolean touchUp(int i, int i1, int i2, int i3) {
       return false; }
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
       for (GameEntity t : gem.towns){
           if(t.r.contains(v3.x, v3.y)){
          return t;
       }
       }
       for (GameEntity e : gem.entitys){
           if(e.r.contains(v3.x, v3.y)){
          return e;
       }
    }
       System.out.println("null"); 
       return null;
    }
///////////////////IN GAME MENU THINGS//////////////////////////   
    private void shouldOpenMenu(Vector3 v3) {
           if(!actionUIisOpen || !townUIisOpen){
            if(whichEntity(v3) != null ){ 
                 if(whichEntity(v3).getisTown()){
                   if(!whichEntity(v3).getisTurnDone()){
                      if(gem.getWaitingEntity() == null){
                         if(gem.getWaitingTown() == null){
                      close = 1;
                      whichEntity(v3).setWaiting(Boolean.TRUE);
                      openTownUI(whichEntity(v3));
                      pf.neighbours.clear();
                      pf.getNeighbor(pf.maplist.get(whichTile(v3)));
                      
                       System.out.println("townui");
                      }
                   }
                 }
                return;
            }
                 if(gem.getWaitingEntity() == null && gem.getWaitingTown() == null){
                         if(!whichEntity(v3).getisTown() && !whichEntity(v3).getisTurnDone()){
                              System.out.println("actioui");
                openmenu(v3);
               }
              
            }
            if(!whichEntity(v3).getisTown() && whichEntity(v3).getisTurnDone()){
              pf.reset();
              pf.getpathable(whichEntity(v3));
               System.out.println("turn is done");
            }
        
        }
     }
  }
    private void openmenu(Vector3 v3) {
     aui.actionUIitems.clear();
     System.out.println("opening a menu");
              if( whichEntity(v3) != null && !whichEntity(v3).getisTown()){
                whichEntity(v3).setWaiting(true);
                pf.neighbours.clear();
                pf.getNeighbor(pf.maplist.get(gem.getWaitingEntity().getLocation()));
                openactionUI(gem.getWaitingEntity());
                }
             }   
    public void openactionUI(GameEntity e){
              int place = 1;
              cameracentre.set(e.getX(), e.getY());
              pf.reset();
              pf.neighbours.clear();
              pf.getNeighbor(maplist.get(gem.getWaitingEntity().getLocation()));
              if(!e.getMoved()){
              pf.getpathable(gem.getWaitingEntity());
              aui.addaction(uf.newUIitem(place,e.getX(), e.getY(),"move"));
              place++;
              actionUIisOpen = true;
              
                         }
              if(!e.gethasCaptured()){ 
              for(Tile t : pf.neighbours){
                 for(GameEntity f : gem.towns){
                    if(f.getX() == t.getX() && f.getY() == t.getY()){
                       aui.addaction(uf.newUIitem(place, e.getX(), e.getY(),"capture"));
                       
                    } 
                 }
               } place++;
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
                         aui.addaction(uf.newUIitem(place, e.getX(), e.getY(),"attack"));
                        place++;
                        actionUIisOpen = true;
                     }else{}
                               }
            aui.addaction(uf.newUIitem(place, e.getX(), e.getY(),"wait"));
            actionUIisOpen = true;
         }
    public void openTownUI(GameEntity t){
            pf.moveable.clear();
            aui.actionUIitems.clear();
            townUIisOpen = true;
            int place = 1;
                 aui.addaction(uf.newUIitem(place, t.getX(), t.getY(), "close"));
                 place++;
              cameracentre.set(t.getX(), t.getY());
              if(gem.gold >= 50){
                 aui.addaction(uf.newUIitem(place, t.getX(), t.getY(), "unit1"));
                 place++;
              }
              if(gem.gold >= 150){
                 aui.addaction(uf.newUIitem(place, t.getX(), t.getY(), "unit2"));
                 place++;
              }
              if(gem.gold >= 250){
                 aui.addaction(uf.newUIitem(place, t.getX(), t.getY(), "unit3"));
                 place++;
              }
              
    }
    private void getchoice() {
      if(actionUIisOpen){
       for (actionUIitem e :  aui.actionUIitems){
        if(e.r.contains(v3.x, v3.y)){
                       actionUIisOpen = false;
        if(e.s.compareTo("move") == 0){
                       moveSelected = true; }
        if(e.s.compareTo("attack") == 0){
                       attackSelected = true;
                      
                        }
        if(e.s.compareTo("capture") == 0){
                        System.out.println("capture");
                       captureSelected = true;
                      
                        }
        if(e.s.compareTo("wait") == 0){
                       waits();
                        }
                       }
                      }
                     }
    if(townUIisOpen){
           
        for (actionUIitem e :  aui.actionUIitems){
        if(e.r.contains(v3.x, v3.y)){
                       actionUIisOpen = false;
              if(e.s.compareTo("close") == 0){
                           close = 0;
                           System.out.println("close");
                              }
              if(e.s.compareTo("unit1") == 0){
                           close = 2;
                           unit = "unit1";
                           unitBeingBought = true;
                           System.out.println("1");
                           
                              }
              if(e.s.compareTo("unit2") == 0){
                           System.out.println("2");
                            
                              }
              if(e.s.compareTo("unit3") == 0){
                           System.out.println("3");
                            
                              }
              if(e.s.compareTo("unit4") == 0){
                     System.out.println("4");
                        }
                       }
                      }
        if(close == 0){
          gem.getWaitingTown().setWaiting(Boolean.FALSE);
          aui.actionUIitems.clear();
          townUIisOpen = false;
    
        }
        if(close == 2){
          aui.actionUIitems.clear();
          townUIisOpen = false;
    
        }
    }
  } 
    private void buyAunit(Vector3 v3){
           Vector2 unitlocation = new Vector2();            ///////to set occupied without cocurrent error
           int unitsize = gem.entitys.size();
             for(Tile t : pf.neighbours){
                if(t.r.contains(new Vector2(v3.x,v3.y))){
                 if(!t.getOccupied()){
                   unitlocation.set(t.getX(), t.getY());
                    gem.addEntity(uf.newUnit(unit, t.getX() ,t.getY(),gem.getWaitingTown().getisEnemy()) );
          }        
        }     
      }     
          if(gem.entitys.size() > unitsize){   ///////verify a unit was bought
            pf.maplist.get(unitlocation).setOcuppied(Boolean.TRUE);
            unit = "null";
            gem.getWaitingTown().setisTurnDone(Boolean.TRUE);
             gem.getWaitingTown().setWaiting(Boolean.FALSE);
            unitBeingBought = false;
            aui.actionUIitems.clear();
            townUIisOpen = false;
          }
    }
    private void resetMenu(){
    
     moveSelected = false;
     attackSelected = false;
     captureSelected = false;
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
                   gem.getWaitingEntity().setMoved(true);
                   gem.getMoving().setIsMoving(false);
                   openactionUI(gem.getWaitingEntity());
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
/////////////////////ACTIONUI OPTIONS
    private void doAction(Vector3 v3){
           if(moveSelected && !gem.getWaitingEntity().getMoved() ){
               setmovement(v3);
                System.out.println("move");
               resetMenu();
               }
            if(attackSelected && !gem.getWaitingEntity().gethasAttacked()){
               attack(v3);
                 System.out.println("attack");
               resetMenu();}
            if(captureSelected && !gem.getWaitingEntity().gethasCaptured() ){
               captureaTown(whichEntity(v3));
                 System.out.println("capture");
               resetMenu();
               } 
            if(waitSelected && gem.getWaitingEntity().getisTurnDone() ){
             
             waits();
               resetMenu();
               }
       
       

        if(gem.getWaitingEntity() !=null){
           
            
          }
    
    }
    private void setmovement(Vector3 v3) {
        System.out.println(v3);
                 
                 Vector2 v2start = new Vector2(gem.getWaitingEntity().getX(),gem.getWaitingEntity().getY());   
                 Vector2 end = new Vector2(whichTile(v3));
                 pf.setPath(v2start,end,gem.getWaitingEntity());
                 if(pf.Path.size()==0){
                   actionUIisOpen = true;
                 }
                 
                 if(pf.Path.size()>0){
                 gem.getWaitingEntity().setMoving();
                 maplist.get(gem.getWaitingEntity().getLocation()).setOcuppied(false);
                 }
                 movecounter = 0;
                 }
    private void attack(Vector3 v3) {
                  int hasAttacked = 3;
                  pf.neighbours.clear();
                pf.getNeighbor(pf.maplist.get(gem.getWaitingEntity().getLocation()));
                 System.out.println("attacking");
                 if(!gem.getWaitingEntity().gethasAttacked()){
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
                              whichEntity(v3).getAttacked( gem.getWaitingEntity().getAttack());
                        if( whichEntity(v3).getDead()){
                              whichEntity(v3).setT(ass.get("dead.png", Texture.class));
                              gem.entitysDead.add(whichEntity(v3));
                              gem.entitys.remove(whichEntity(v3));
                   }
                  } 
                 }
                }
                  if(hasAttacked == 1){
                        gem.getWaitingEntity().sethasAttacked(true);
                  }
                  aui.actionUIitems.clear();
                  openactionUI(gem.getWaitingEntity());
                 
                 
                 
                  
                  }  
                    }
    private void waits() {
                   
                       gem.getWaitingEntity().setisTurnDone(Boolean.TRUE);
                       gem.getWaitingEntity().setWaiting(Boolean.FALSE);
                                     
    }     
    private void captureaTown(GameEntity town) {
               
                 System.out.println("town captured");
                pf.neighbours.clear();
                pf.getNeighbor(pf.maplist.get(gem.getWaitingEntity().getLocation()));
                 int i = 3 ;
                // is town a neighbour
              for(Tile til : pf.neighbours){
                 if(til.getX() == town.getX() && til.getY() == town.getY()){
                     if(gem.getWaitingEntity().getisEnemy()){
                             town.setisEnemy(Boolean.TRUE);
                             i = 1;
                          }
                          if(!gem.getWaitingEntity().getisEnemy()){
                              town.setisEnemy(Boolean.FALSE);
                              i=0;
                          }
                 
                 }
              } 
                 
                 if(i == 0){
                      gem.getWaitingEntity().sethasCaptured(true);
                      aui.actionUIitems.clear();
                      openactionUI(gem.getWaitingEntity());
                     System.out.println("friendly");
                 }
                 if(i == 1){
                    gem.getWaitingEntity().sethasCaptured(true);
                    aui.actionUIitems.clear();
                   openactionUI(gem.getWaitingEntity());
                     System.out.println("enemy");
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
               temp.sethasDecorations(m.getTile(i).gethasDecorations());
               if(m.getTile(i).gethasDecorations()==1){
                 temp.setDecorX(m.getTile(i).getDecorX());
                 temp.setDecorY(m.getTile(i).getDecorY());
                 temp.setDecorSize(m.getTile(i).getDecorSize());
               }
               if(m.getTile(i).gethasDecorations()==2){
                 temp.setDecorX1(m.getTile(i).getDecorX1());
                 temp.setDecorY1(m.getTile(i).getDecorY1());
                 temp.setDecorSize1(m.getTile(i).getDecorSize1());
               }
              
              maplist.put(v2, temp);
              m.tList.remove(i);
              m.tList.add(i, temp);
               
             
             
              
            }
            
              
            
            
    }
    public void putMapData(GameEntityManager g){
     for(GameEntity e : g.entitys){
         
      //  uf.newUnit("unit1", e.getX(), e.getY(),e.getisEnemy());
        e.setT(ass.get("unit1.png", Texture.class));
        e.setHitbox(e.getX(), e.getY(),128   , 128);
        camera.position.set(e.getX(), e.getY(), 0);
        maplist.get(e.getLocation()).setOcuppied(true);
        e.setHp(10);
        e.setAttack(10);
        gem.addEntity(e);
       
     }  
      for(GameEntity e : g.towns){
        uf.newUnit("unit1", e.getX(), e.getY(),e.getisEnemy());
        e.setT(ass.get("town.png", Texture.class));
        e.setHitbox(e.getX(), e.getY(),128   , 128);
        maplist.get(e.getLocation()).setOcuppied(true);
        e.setHp(10);
        e.setAttack(1);
        gem.addtown(e);
        townlist.put(e.getLocation(), e);
       
     }cameracentre.set(gem.entitys.get(0).getLocation());
    
    }



  public void setMap(map m){
    this.m = m;
   }  
  public map getMap(){
    return m;
  }
}
