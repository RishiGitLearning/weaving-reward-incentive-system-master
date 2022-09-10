package com.technoride.RewardIncentiveSystem.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class demoController {
    @GetMapping("/index/header")
    private String getHeader(Model model)
    {
        model.addAttribute("message","access");
        return "header";
    }
    @GetMapping("/demoLoader")
    private String getDemo()
    {
        return "demoLoader";
    }

    @RequestMapping(value="/index/getmsg", method= RequestMethod.GET,produces = "application/json")
    public @ResponseBody String display1(@RequestParam(value = "empNo") String empNo)
    {
        System.out.println("welcome"+empNo);
        return empNo;
    }

}
