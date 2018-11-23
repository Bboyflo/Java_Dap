package fr.ynov.dap.dap.microsoft.controller;

import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import fr.ynov.dap.dap.microsoft.auth.AuthHelper;

/***
 * @author Florian
 */
@Controller
public class IndexController {

    /**
     * @param model .
     * @param request .
     * @return la page html index
     */
    @RequestMapping("/index")
    public String index(final Model model, final HttpServletRequest request) {
        UUID state = UUID.randomUUID();
        UUID nonce = UUID.randomUUID();

        // Save the state and nonce in the session so we can
        // verify after the auth process redirects back
        HttpSession session = request.getSession();
        session.setAttribute("expected_state", state);
        session.setAttribute("expected_nonce", nonce);

        String loginUrl = AuthHelper.getLoginUrl(state, nonce);
        model.addAttribute("loginUrl", loginUrl);
        // Name of a definition in WEB-INF/defs/pages.xml
        return "index";
    }
}
