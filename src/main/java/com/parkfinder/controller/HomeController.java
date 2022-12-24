package com.parkfinder.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.IOException;

@Controller
public class HomeController {

    @GetMapping("/map")
    public String sayHello(Model model) throws IOException {
//        String mapUrl = "https://maps.googleapis.com/maps/api/staticmap?" +
//                "center=Berkeley,CA&zoom=14&size=400x400" +
//                "&key=AIzaSyBPBjwslCsB5pffDskDq-2EfEgzJec_UqI";
//        URL url = new URL(mapUrl);
//        BufferedImage image = ImageIO.read(url.openStream());
//
//        model.addAttribute("image", image);

        return "index";
    }
}
