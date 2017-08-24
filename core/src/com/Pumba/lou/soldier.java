/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.Pumba.lou;

import java.io.Serializable;

/**
 *
 * @author Stefan
 */
public class soldier extends GameEntity implements Serializable{
       String type;
       private int hp;
       private int attack;
       
       public void setType(String type){
         this.type = type;
       }
       public String getType(){
         return type;
       }
       public int getHp(){
         return hp;
       }
       public void setHp(int amount){
         this.hp = amount;
       }
       public void minushp(int amount){
         hp -= amount; 
       }
       public void gainHp(int amount){
         hp += amount;
       }
       public void setAttack(int amount){
         this.attack = amount;
       }
       public int getAttack(){
         return attack;
       }
       
     

}