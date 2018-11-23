package fr.ynov.dap.dap.microsoft.controller;

import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import fr.ynov.dap.dap.microsoft.auth.AuthHelper;
import fr.ynov.dap.dap.microsoft.auth.IdToken;
import fr.ynov.dap.dap.microsoft.auth.TokenResponse;

/**
 * @author Florian
 */
@Controller
public class AuthorizeController {

    /**
     * @param code .
     * @param idToken .
     * @param state .
     * @param request .
     * @return la page html de mail
     */
    @RequestMapping(value = "/authorize", method = RequestMethod.POST)
    public String authorize(
            @RequestParam("code") final String code,
            @RequestParam("id_token") final String idToken,
            @RequestParam("state") final UUID state,
            final HttpServletRequest request) {
        // Get the expected state value from the session
        HttpSession session = request.getSession();
        UUID expectedState = (UUID) session.getAttribute("expected_state");
        UUID expectedNonce = (UUID) session.getAttribute("expected_nonce");
        // Make sure that the state query parameter returned matches
        // the expected state
        if (state.equals(expectedState)) {
            IdToken idTokenObj = IdToken.parseEncodedToken(idToken, expectedNonce.toString());
            if (idTokenObj != null) {
                TokenResponse tokenResponse = AuthHelper.getTokenFromAuthCode(code, idTokenObj.getTenantId());
                session.setAttribute("tokens", tokenResponse);
                session.setAttribute("userConnected", true);
                session.setAttribute("userName", idTokenObj.getName());
                session.setAttribute("userTenantId", idTokenObj.getTenantId());
            } else {
                session.setAttribute("error", "ID token failed validation.");
            }
        } else {
            session.setAttribute("error", "Unexpected state returned from authority.");
        }
        return "mail";
    }

    /**
     * @param request .
     * @return sur la page html index
     */
    @RequestMapping("/logout")
    public String logout(final HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.invalidate();
        return "redirect:/index.html";
    }
}
