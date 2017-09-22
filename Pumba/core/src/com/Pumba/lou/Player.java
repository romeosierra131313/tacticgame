/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.Pumba.lou;

/**
 *
 * @author Stefan
 */
public class Player {
    int Gold = 0;
    Boolean isEnemy;

    public Player() {
      }

    public int getGold(){
      return Gold;
    }
    public void increaseGold(int amount, Boolean isEnemy){
        if(this.isEnemy = isEnemy){
         Gold += amount;}
    }
    public void setisEnemy(Boolean b){
      isEnemy = b;
    }
    public Boolean getisEnenmy(){
      return isEnemy;
    }

}
