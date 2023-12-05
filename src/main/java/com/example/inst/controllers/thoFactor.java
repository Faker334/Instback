package com.example.inst.controllers;

import org.brunocvcunha.instagram4j.Instagram4j;
import org.brunocvcunha.instagram4j.requests.InstagramGetChallengeRequest;
import org.brunocvcunha.instagram4j.requests.InstagramResetChallengeRequest;
import org.brunocvcunha.instagram4j.requests.InstagramSelectVerifyMethodRequest;
import org.brunocvcunha.instagram4j.requests.InstagramSendSecurityCodeRequest;
import org.brunocvcunha.instagram4j.requests.payload.InstagramGetChallengeResult;
import org.brunocvcunha.instagram4j.requests.payload.InstagramLoginResult;
import org.brunocvcunha.instagram4j.requests.payload.InstagramSelectVerifyMethodResult;

import java.util.Objects;
import java.util.Scanner;

public class thoFactor {
    String Login;
    String Password;
    Instagram4j instagram;
    thoFactor(String login, String password){
        this.Login=login;
        this.Password=password;
        try {
            instagram = Instagram4j.builder()
                    .username(Login)
                    .password(Password)
                    .build();
            instagram.setup();

            InstagramLoginResult instagramLoginResult = instagram.login();

            if (Objects.equals(instagramLoginResult.getStatus(), "ok")) {

                System.out.println("login success");

            } else {
                if (Objects.equals(instagramLoginResult.getError_type(), "checkpoint_challenge_required")) {
                    // Challenge required

                    // Get challenge URL
                    String challengeUrl = instagramLoginResult.getChallenge().getApi_path().substring(1);

                    // Reset challenge
                    String resetChallengeUrl = challengeUrl.replace("challenge", "challenge/reset");
                    InstagramGetChallengeResult getChallengeResult = instagram
                            .sendRequest(new InstagramResetChallengeRequest(resetChallengeUrl));

                    // If action is close
                    if (Objects.equals(getChallengeResult.getAction(), "close")) {
                        // Get challenge
                        getChallengeResult = instagram
                                .sendRequest(new InstagramGetChallengeRequest(challengeUrl));
                    }

                    if (Objects.equals(getChallengeResult.getStep_name(), "select_verify_method")) {

                        // Get security code
                        InstagramSelectVerifyMethodResult postChallengeResult = instagram
                                .sendRequest(new InstagramSelectVerifyMethodRequest(challengeUrl,
                                        getChallengeResult.getStep_data().getChoice()));

                        System.out.println("input security code");
                        String securityCode = null;
                        try (Scanner scanner = new Scanner(System.in)) {
                            securityCode = scanner.nextLine();
                        }

                        // Send security code
                        InstagramLoginResult securityCodeInstagramLoginResult = instagram
                                .sendRequest(new InstagramSendSecurityCodeRequest(challengeUrl, securityCode));

                        if (Objects.equals(securityCodeInstagramLoginResult.getStatus(), "ok")) {
                            System.out.println("login success");

                        } else {
                            System.out.println("login failed");
                        }
                    }
                }
            }
        }catch (Exception e){
            System.out.println("faf");
            System.out.println(e.getMessage());
        }

    }

}
