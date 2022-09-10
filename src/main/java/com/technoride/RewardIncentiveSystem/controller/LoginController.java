package com.technoride.RewardIncentiveSystem.controller;

import com.technoride.RewardIncentiveSystem.entity.CompanyUser;
import com.technoride.RewardIncentiveSystem.model.LoggedUser;
import com.technoride.RewardIncentiveSystem.model.User;
import com.technoride.RewardIncentiveSystem.repository.CompanyUserRepository;
import com.technoride.RewardIncentiveSystem.repository.UserRepository;
import com.technoride.RewardIncentiveSystem.utility.MBMdecode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class LoginController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CompanyUserRepository companyUserRepository;

  //  String loginId1;
    @GetMapping("/")
    public String getLogin1(Model model)
    {
        model.addAttribute("user", new CompanyUser());
        return "login1";
    }
    CompanyUser user = new CompanyUser();
    @GetMapping("/process_login")
    public String login(@RequestParam("loginId") String loginId, @RequestParam("password") String password)
    {
        System.out.println("User Password"+loginId+"::"+password);
        String pass = MBMdecode.MBMen(password.trim().getBytes());
        System.out.println("Pass"+pass);
        user = companyUserRepository.findByLoginIdAndPassword(loginId, pass);
        if(user == null)
        {
            Logger logger = LoggerFactory.getLogger(LoginController.class);
            logger.error("An exception occurred during login!");
            return "redirect:/login1?error";
        }
        else {
           // loginId1 = user.getLoginId();
             return "redirect:/index";
           // return "redirect:/index?user="+user;
        }
    }
    @GetMapping("/index")
    public String getIndexPage(Model model)
    {
       // String loginId1 = "rakeshdalal";
        LoggedUser.getInstance().setUser(user);
        if(user.getUserName() == null)
        {
            model.addAttribute("user", new CompanyUser());
            return "login1";
        }
        else {
            model.addAttribute("uname", "User " + " " + user.getUserName());
            return "index";
        }
    }

    @GetMapping("/login1")
    public String getLogin(Model model)
    {
        model.addAttribute("user", new CompanyUser());
        return "login1";
    }
/*  @GetMapping("/login")
    public String login(Model model)
    {
        model.addAttribute("user", new User());
        return "login";
    }

    @GetMapping("/list_users")
    public String listUsers(Model model)
    {
        List<User> userList = userRepository.findAll();
        model.addAttribute("userList", userList);
        return "userList";
    }
    @GetMapping("/register")
    public String getSignUpForm(Model model)
    {
        model.addAttribute("user", new User());
        return "signUpForm";
    }
    BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    @PostMapping("/process_register")
    public String processRegister(User user)
    {
        userRepository.save(user);
        return "registerSuccess";
    }
*/
}
