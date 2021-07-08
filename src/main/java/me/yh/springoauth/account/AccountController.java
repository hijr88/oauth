package me.yh.springoauth.account;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import me.yh.springoauth.oauth.SessionUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;

@RequiredArgsConstructor
@Controller
public class AccountController {

    @GetMapping("/")
    public String index(Model model, HttpSession httpSession) {

        SessionUser user = (SessionUser) httpSession.getAttribute("user");

        if (user != null) {
            model.addAttribute("user", user);
        }

        return "index";
    }
}
