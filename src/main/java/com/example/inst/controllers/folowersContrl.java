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


    folowersContrl(Instagram4j instagram4j, String name){
        this.NickName =name;
        this.instagram=instagram4j;
    }
    public List<InstagramUserSummary> RaznostPodpischipov(){
        List<InstagramUserSummary> massivPodpishikov = new ArrayList<>();
        List<InstagramUserSummary> massivPodpisok = new ArrayList<>();
        List<InstagramUserSummary> otpiski = new ArrayList<>();
        try {
            InstagramSearchUsernameResult usernameResult = instagram.sendRequest(
                    new InstagramSearchUsernameRequest(NickName)); // АККАУНТ

            String maxId1 = null;
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
            String maxId = null;
            while (true){ //подписчики

                InstagramGetUserFollowersResult followers = instagram.sendRequest(new InstagramGetUserFollowersRequest(usernameResult.getUser().getPk(), maxId));
                TimeUnit.SECONDS.sleep(10);
                System.out.println("Slept for 10 seconds");

                for (InstagramUserSummary s : followers.getUsers()) {


                    if (!massivPodpishikov.contains(s)) {

                        massivPodpishikov.add(s);

                    }

                }

                maxId=followers.getNext_max_id();

                System.out.println("ШАГ ЗАПРОСОВ ПОДПИСЧИКИ:"+ maxId);

                if(maxId == null) {

                    break;

                }

            }

            for (InstagramUserSummary s:massivPodpishikov) {
                System.out.println("подписчики:"+s.username);
            }
            for (InstagramUserSummary podpiska : massivPodpisok) {
                boolean hasInPodpiskiAndPodpischiki = false;
                for (InstagramUserSummary podpischik : massivPodpishikov) {
                    if (podpischik.getPk() == podpiska.getPk()) {
                        hasInPodpiskiAndPodpischiki = true;
                    }
                }
                if (!hasInPodpiskiAndPodpischiki) {
                    otpiski.add(podpiska);
                }
            }

            System.out.println("сайз подписчиков"+massivPodpisok.size());
            for (InstagramUserSummary s:otpiski) {
                System.out.println("ОТПИСЧИКИ:"+s.username);
            }
        }            catch (IOException e) {System.out.println("ЭКСЕПШН");System.out.println(e.getMessage());
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return otpiski;
    }

}
