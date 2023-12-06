package com.example.inst.controllers;

import org.brunocvcunha.instagram4j.Instagram4j;
import org.brunocvcunha.instagram4j.requests.InstagramGetUserFollowersRequest;
import org.brunocvcunha.instagram4j.requests.InstagramGetUserFollowingRequest;
import org.brunocvcunha.instagram4j.requests.InstagramSearchUsernameRequest;
import org.brunocvcunha.instagram4j.requests.payload.InstagramGetUserFollowersResult;
import org.brunocvcunha.instagram4j.requests.payload.InstagramSearchUsernameResult;
import org.brunocvcunha.instagram4j.requests.payload.InstagramUserSummary;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;


public class folowersContrl {
    Instagram4j instagram;
    String NickName;
    String maxId1 = null;
    String maxId = null;
    folowersContrl(Instagram4j instagram4j, String name){
        this.NickName =name;
        this.instagram=instagram4j;
    }
    public List<InstagramUserSummary> RaznostPodpischipov(){
        List<InstagramUserSummary> massivPodpishikov = new ArrayList<>();
        List<InstagramUserSummary> massivPodpisok = new ArrayList<>();
        
        try {
            InstagramSearchUsernameResult usernameResult = instagram.sendRequest(
                    new InstagramSearchUsernameRequest(NickName)); // АККАУНТ
            
            maxId=null;
            while (true){ //подписчики
                InstagramGetUserFollowersResult followers = instagram
                   .sendRequest(new InstagramGetUserFollowersRequest(usernameResult.getUser().getPk(), maxId));
                System.out.println("Slept for 10 seconds");

                  maxId=followers.getNext_max_id();
                if (followers.getUsers().size()!=0&&massivPodpishikov.size()!=0) {
                    System.out.println(" вошел в иф");
                
                    for (int j = 0; j < massivPodpishikov.size(); j++) {
                        System.out.println(massivPodpishikov.size());
                        if (massivPodpishikov.get(massivPodpishikov.size()-1)==followers.getUsers().get(j)) {
                            System.out.println("второй иф"+followers.getUsers().get(j));
                            int bbb=Integer.parseInt(maxId)+(j-1);
                            maxId=String.valueOf(bbb);
                            break;
                        }
                    }
                        
                    }
                
                    massivPodpishikov.addAll(followers.getUsers());
                  

                  System.out.println("ШАГ ЗАПРОСОВ ПОДПИСЧИКИ:"+ maxId);
                    if(maxId==null){break;}
           }
           maxId1=null;
            while (true){ //подписки
                InstagramGetUserFollowersResult following = instagram //
                        .sendRequest(new InstagramGetUserFollowingRequest(usernameResult.getUser().pk, maxId1));
                massivPodpisok.addAll(following.getUsers());
                TimeUnit.SECONDS.sleep(10);
                System.out.println("Slept for 10 seconds");
                maxId1=following.getNext_max_id();
                System.out.println("ШАГ ЗАПРОСОВ ПОДПИСКИ:"+maxId1);
                if(maxId1==null){break;}
            }

            for (int i = 0; i < massivPodpisok.size(); i++) {    //вычитание
                for (int j = 0; j <massivPodpishikov.size() ; j++) {
                    if (massivPodpisok.get(i).getPk()==massivPodpishikov.get(j).getPk()){
                        massivPodpisok.remove(i);
                       i--;
                       break;
                    
                    }
                }
            }
            System.out.println("сайз подписчиков"+massivPodpisok.size());
            for (InstagramUserSummary s:massivPodpisok) {
                System.out.println("ОТПИСЧИКИ:"+s.username);
            }
        }            catch (IOException e) {System.out.println("ЭКСЕПШН");System.out.println(e.getMessage());
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return massivPodpisok;
    }

}
