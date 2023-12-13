package com.example.inst.controllers;




import org.brunocvcunha.instagram4j.requests.InstagramSearchUsernameRequest;
import org.brunocvcunha.instagram4j.requests.payload.InstagramProfilePic;
import org.brunocvcunha.instagram4j.requests.payload.InstagramSearchUsernameResult;
import org.brunocvcunha.instagram4j.requests.payload.InstagramUserSummary;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.async.WebAsyncTask;
import java.io.IOException;
import java.util.List;


/**
 * @author Neil Alishev
 */
@Controller
public class InstController {


    thoFactor atorization = new thoFactor("paraparapa383","Faker001");


    @GetMapping("/main")
    public String inst(@RequestParam("name")String name,Model model){


        try {
            InstagramSearchUsernameResult usernameResult = null;
            usernameResult = atorization.instagram.sendRequest(
                    new InstagramSearchUsernameRequest(name));
            InstagramProfilePic instagramProfilePic =usernameResult.getUser().hd_profile_pic_url_info;

            List<InstagramUserSummary> MasOtpishikov =new folowersContrl(atorization.instagram,name).RaznostPodpischipov();
            for (InstagramUserSummary user:MasOtpishikov) {
                user.setProfile_pic_url(new ResourceController().getCleanURl(user.getProfile_pic_url()));
            }

            model.addAttribute("otpishiki",MasOtpishikov);
            model.addAttribute("nikname",usernameResult.getUser().username);
            model.addAttribute("post",usernameResult.getUser().media_count);
            model.addAttribute("followers", usernameResult.getUser().follower_count);
            model.addAttribute("following",usernameResult.getUser().following_count);
            model.addAttribute("name",usernameResult.getUser().full_name);
            model.addAttribute("pick",new ResourceController().getCleanURl(instagramProfilePic.url));

        } catch (IOException e) {
            System.out.println(e.fillInStackTrace());
        }
        return "/main";
    }
    @GetMapping("/ab")
public void avb(){

        try {
            InstagramSearchUsernameResult   usernameResult = atorization.instagram.sendRequest(
                    new InstagramSearchUsernameRequest("belyash_303"));
            System.out.println(usernameResult.getUser().following_count);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }




}
