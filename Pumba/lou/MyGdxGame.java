package com.Pumba.lou;

import com.Pumba.lou.Constants.GameState;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.TextInputListener;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
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
         int width  ;
         int height ;
         int hitboxWidth;
        
         PumbaManger pm;
         MainGame mg;
        
         Queue<String> q;
         int newmapwidth;
         Texture grass;
         Texture road;
         Texture water;
         
        
        
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
            
            	
	}

	@Override
	public void render () {
                //k7OiU72XUpy5
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
                sb.setProjectionMatrix(camera.combined);
                sr.setProjectionMatrix(camera.combined);
                camera.update();
               
                
                
               if(gs == gs.MainMenu) {
                 sb.begin();
                
                bf.setColor(Color.BLACK);
                bf.draw(sb, "Editor", Start.getCenter(Vector2.Zero).x,  Start.getCenter(Vector2.Zero).y);
                bf.draw(sb, "Load",  Load.getCenter(Vector2.Zero).x, Load.getCenter(Vector2.Zero).y);
                sb.end();
                
                sr.begin(ShapeRenderer.ShapeType.Line);
                sr.box(Start.x, Start.y, 0, Start.width, Start.height, 0);
                sr.box(Load.x, Load.y, 0, Load.width, Load.height, 0);
                sr.end(); }
               if(gs == gs.Edit){
                  
                   pm.render(sb,sr);
               
               }
               if(gs == gs.Game){
                   mg.render(sb,sr);
               }
               
                
		
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
         System.out.println("you clicked start");
         Gdx.input.getTextInput(this, "select map width", "32", "");
         Gdx.input.getTextInput(this, "select map height", "32", "");
         Gdx.input.getTextInput(this, "select tile size", "32", "");}
        if(Load.contains(v3.x, v3.y)){
           try {System.out.println("trying");
         FileInputStream fileIn = new FileInputStream("maps/example.map");
         ObjectInputStream in = new ObjectInputStream(fileIn);
               map m = new map();
               m = (map) in.readObject();
               System.out.println("map");
               mg = new MainGame(camera);
               mg.setMap(m);
               for(int q = 0 ; q < mg.getMap().tList.size();q++){
               m.tList.get(q).setTile(m.tList.get(q).type, getTexture(m.tList.get(q).type));
                  }
               Gdx.input.setInputProcessor(mg);
               gs = gs.Game;
              
               
         in.close();
         fileIn.close();
      }catch(IOException ip) {
         ip.printStackTrace();
         
      }catch(ClassNotFoundException c) {
         System.out.println("map not found");
         c.printStackTrace();
        
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

    private Vector3 unproject(int i ,int i2) {
         Vector3 v3 = new Vector3();
        v3.set(i,i2,0);
        v3 = camera.unproject(v3);
        return v3;
    }

    @Override
    public void input(String string) {
      q.add(string);
       
        if(q.size() == 3){
           int temph = Integer.parseInt(q.poll());
           int tempw = Integer.parseInt(q.poll());
           int temps = Integer.parseInt(q.poll());
           pm = new PumbaManger(temph,tempw,temps,camera);
           Gdx.input.setInputProcessor(pm);
           gs = gs.Edit;
           
          
        }
      
    }

    @Override
    public void canceled() {
        }
   public Texture getTexture(String type){
       
       if(type == "grass"){
       grass = new Texture(Gdx.files.internal("grass.jpg"));
       return grass;
       }
        if(type == "road"){
     road = new Texture(Gdx.files.internal("road.png"));
     return road;
        }
         if(type == "water"){
     water  = new Texture(Gdx.files.internal("water.png"));
     return water;
         }
     return grass;
   }

}
