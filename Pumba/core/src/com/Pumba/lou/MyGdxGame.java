package com.Pumba.lou;

import com.Pumba.lou.Constants.GameState;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.TextInputListener;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.LinkedList;
import java.util.Queue;

public class MyGdxGame extends ApplicationAdapter implements InputProcessor ,TextInputListener{
	
        GameState gs;
        SpriteBatch sb;
        BitmapFont bf;
	Rectangle Start;
        Rectangle Load;
        ShapeRenderer sr;
        OrthographicCamera camera;
        AssetManager ass;
         int width  ;
         int height ;
         int hitboxWidth;
        
         Editor editor;
         MainGame mg;
        
         Queue<String> q;
         int newmapwidth;
         Texture grass;
         Texture road;
         Texture water;
         Texture town;
        
        
	@Override
    public void create () {
            width = Gdx.graphics.getWidth();
            height = Gdx.graphics.getHeight();
            hitboxWidth = width/10;
            gs  = GameState.MainMenu;
            sb = new SpriteBatch();
            bf = new BitmapFont();
            
            Start = new Rectangle();
            Load = new Rectangle();
            
            
            
            Start.set((width/2) - (hitboxWidth ), height - (hitboxWidth*2), hitboxWidth*2,  hitboxWidth);
            Load.set((width/2) - (hitboxWidth ), height - (hitboxWidth*4), hitboxWidth*2,  hitboxWidth);
            sr = new ShapeRenderer();
            sr.setAutoShapeType(true);
            
            Gdx.input.setInputProcessor(this);
            camera = new OrthographicCamera();
            camera.setToOrtho(false, 800, 480);
            camera.position.set(800/2f,480/2f, 0);
            
            
            q = new LinkedList<String>();
            ass = new AssetManager();
            
            loadtiles(ass);
            
            

            	
	}
@Override
    public void render () {
                //k7OiU72XUpy5
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
                sb.setProjectionMatrix(camera.combined);
                sr.setProjectionMatrix(camera.combined);
                
                camera.update();
               
               if(gs == gs.MainMenu) {
                 sb.begin(); 
                
                
                bf.draw(sb, "Editor", Start.getCenter(Vector2.Zero).x,  Start.getCenter(Vector2.Zero).y);
                bf.draw(sb, "Load",  Load.getCenter(Vector2.Zero).x, Load.getCenter(Vector2.Zero).y);
                sb.end();
                
                sr.begin(ShapeRenderer.ShapeType.Line);
                sr.box(Start.x, Start.y, 0, Start.width, Start.height, 0);
                sr.box(Load.x, Load.y, 0, Load.width, Load.height, 0);
                sr.end(); }
               if(gs == gs.Edit){
                   
                   editor.render(sb,sr,bf,camera);
                  
                   if(editor.main){
                      while(!editor.ready){
                       editor.getMapName();
                       changeState();
                       Gdx.input.setInputProcessor(this);
                       setupCamera();
                       editor.ready = true;
                      }
                      editor.dispose();
                   }
                   
               }
               if(gs == gs.Game ){
                   sb.begin();
                   sr.begin();
                   mg.render(sb,sr,bf);
                   sb.end();
                   sr.end();
               }
               
               sb.begin();
               bf.setColor(Color.BLACK);
                bf.draw(sb, Integer.toString(Gdx.graphics.getFramesPerSecond()), camera.position.x -400 , camera.position.y -200);
              if(mg != null)  {
//                bf.draw(sb, Integer.toString(mg.gem.gold), camera.position.x -390 , camera.position.y -100);
              }
                
                sb.end(); 
		
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
        if(Start.contains(v3.x,v3.y)){
           getmapsize(); 
        }
        if(Load.contains(v3.x, v3.y)){
            bf.getData().scale(3f);  
          LoadMap();   //later on change to accpet filename
          LoadMapdata();
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
          Vector3 v3 = new Vector3();
          v3 = unproject(Gdx.input.getX(),Gdx.input.getY());
        if(Start.contains(v3.x,v3.y) || Load.contains(v3.x, v3.y)){
        
         sr.setColor(Color.GREEN);
         
        }
        if(!Start.contains(v3.x,v3.y) && !Load.contains(v3.x, v3.y)){
        
         sr.setColor(Color.BLACK);
         
        }  
        return true; }
 @Override
    public boolean scrolled(int i) {
        return true; }
    public Vector3 unproject(int i ,int i2) {
         Vector3 v3 = new Vector3();
        v3.set(i,i2,0);
        v3 = camera.unproject(v3);
        return v3;
    }
@Override
    public void input(String string) {
      q.add(string);
       
        if(q.size() == 3){
           int tempw = Integer.parseInt(q.poll());
           int temph = Integer.parseInt(q.poll());
           int temps = Integer.parseInt(q.poll());
           editor = new Editor(temph,tempw,temps,camera,ass);
           Gdx.input.setInputProcessor(editor);
           gs = GameState.Edit;
           
          
        }
      
    }
@Override
    public void canceled() {
        }
    public Texture getTexture(String type, AssetManager ass){
     
       if("grass".equals(type)){
          grass =    ass.get("grass.png", Texture.class);
          return grass; 
       
       }
        if("road".equals(type)){
     road =    ass.get("road.png", Texture.class);
     return road;
        }
         if("water".equals(type)){
     water =    ass.get("water.png", Texture.class);
     return water;
         }
         if("town".equals(type)){
   town =    ass.get("town.png", Texture.class);
     return town;
         }
          if("tree1".equals(type)){
   town =    ass.get("tree1.png", Texture.class);
     return town;
         }
     ////add more tiles here
     return grass;
   
   

}  
    private void loadtiles(AssetManager ass) {
       ass.load("grass.png", Texture.class);
       ass.load("water.png",Texture.class);
       ass.load("road.png",Texture.class);
       ass.load("town.png",Texture.class); 
       ass.load("unit1.png",Texture.class);
       ass.load("dead.png", Texture.class);
       ass.load("tree1.png",Texture.class);
       ass.finishLoading();
           }
    private void getmapsize() {
        
         Gdx.input.getTextInput(this, "select map width", "128", "");
         Gdx.input.getTextInput(this, "select map height", "32", "");
         Gdx.input.getTextInput(this, "select tile size", "32", "");  }
    public void LoadMap() {
    
          try {System.out.println("trying");
         FileInputStream fileIn = new FileInputStream("maps/ex.map");
         ObjectInputStream in = new ObjectInputStream(fileIn);
               map m = new map();
               m = (map) in.readObject();
               mg = new MainGame(camera,ass, m);
               loadtiles(ass);
               mg.rebuildmap(m);
               Gdx.input.setInputProcessor(mg);
               gs = GameState.Game;
              
               
         in.close();
         fileIn.close();
      }catch(IOException ip) {
         ip.printStackTrace();
         
      }catch(ClassNotFoundException c) {
         System.out.println("map not found");
         c.printStackTrace();
        
      } }

    private void LoadMapdata() {
       try {System.out.println("trying");
         FileInputStream fileIn = new FileInputStream("maps/ex.dat");
         ObjectInputStream in = new ObjectInputStream(fileIn);
               GameEntityManager gem = new GameEntityManager();
               gem = (GameEntityManager) in.readObject();
               mg.putMapData(gem);
             //  mg.gem.setEntitys(gem.entitys);
             //  for(GameEntity e : gem.entitys){
             //    e.setT(ass.get("unit1.png", Texture.class)); }
                                    ////for editor mode
               
              
               
         in.close();
         fileIn.close();
      }catch(IOException ip) {
         ip.printStackTrace();
         
      }catch(ClassNotFoundException c) {
         System.out.println("data not found");
         c.printStackTrace();
        
      }  }

public void changeState(){
   gs = GameState.MainMenu;
 }

    private void setupCamera() {
            camera = new OrthographicCamera();
            camera.setToOrtho(false, 800, 480);
            camera.position.set(800/2f,480/2f, 0); }
}
