/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.Pumba.lou;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import java.io.Serializable;

/**
 *
 * @author Stefan
 */
public class Tile implements Serializable {
     String type = "null";
     
      transient Rectangle r;
      int rwidth;
       int rheight;
        int x;
        int y;
      int hasdecorations;
      int decorX;
      int decorY;
      int decorSize;
      int decorX1;
      int decorY1;
      int decorSize1;
      int distance;
      transient Texture t;
      Boolean isPathable=true;
      Boolean isSet = false;
      Boolean isOccupied = false;
      
      
    public Tile(int x, int y,int width,int height){
      this.rwidth = width;
      this.rheight = height;
      this.x =x;
      this.y = y;
      r = new Rectangle();
      r.set(x, y, width, height);
      
      
      
    }
    
    public void setTile(String type,Texture t){
       this.type = type;
       this.t = t;
    }
    public void setTexture(Texture t){
     
     this.t = t;
    }
    public Texture getTexture(){
     return t;
    }
    public void setX(int x){
    this.x = x;
    }
    public int getX(){return x;}
    public void setY(int y){
    this.y = y;
    }
    public int getY(){return y;}
    public Boolean getOccupied(){
     return isOccupied;
    }
    public void setOcuppied(Boolean b ){
     isOccupied = b;
    }
    public void setr(int x, int y, int width,int height){
      r.set(x, y, width, height);
    }
    public int gethasDecorations(){
      return hasdecorations;
    }
    public void sethasDecorations(int number){
      hasdecorations = number;
    }
    public int getDecorX(){
      return decorX;
    }
    public void setDecorX(int x){
      decorX = x;
    }
    public int getDecorY(){
      return decorY;
    }
    public void setDecorY(int y ){
      decorY = y;
    } 
    public int getDecorSize(){
      return decorSize;
    }
    public void setDecorSize(int size){
      decorSize = size;
    }
    public int getDecorX1(){
      return decorX1;
    }
    public void setDecorX1(int x){
      decorX1 = x;
    }
    public int getDecorY1(){
      return decorY1;
    }
    public void setDecorY1(int y ){
      decorY1 = y;
    } 
    public int getDecorSize1(){
      return decorSize1;
    }
    public void setDecorSize1(int size){
      decorSize1 = size;
    }
    public int getDistance(){
      return distance;
    }
    public void setDistance(int distance){
      distance = distance;
    }
}