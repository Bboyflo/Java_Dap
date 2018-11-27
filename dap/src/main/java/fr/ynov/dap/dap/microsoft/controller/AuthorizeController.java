package fr.ynov.dap.dap.microsoft.controller;

import java.io.IOException;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import fr.ynov.dap.dap.microsoft.auth.AuthHelper;
import fr.ynov.dap.dap.microsoft.auth.IdToken;
import fr.ynov.dap.dap.microsoft.auth.TokenResponse;
import fr.ynov.dap.dap.microsoft.service.OutlookService;
import fr.ynov.dap.dap.microsoft.service.OutlookServiceBuilder;
import fr.ynov.dap.dap.microsoft.service.OutlookUser;

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
     * @param model .
     * @return la page html de mail
     */
    @RequestMapping(value = "/authorize", method = RequestMethod.POST)
    public String authorize(
            @RequestParam("code") final String code,
            @RequestParam("id_token") final String idToken,
            @RequestParam("state") final UUID state,
            final HttpServletRequest request, final Model model) {
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
                // Get user info
                OutlookService outlookService = OutlookServiceBuilder.getOutlookService(tokenResponse.getAccessToken(),
                        null);
                OutlookUser user;
                try {
                    user = outlookService.getCurrentUser().execute().body();
                    session.setAttribute("userEmail", user.getMail());
                } catch (IOException e) {
                    session.setAttribute("error", e.getMessage());
                }
                session.setAttribute("userTenantId", idTokenObj.getTenantId());
            } else {
                session.setAttribute("error", "ID token failed validation.");
            }
        } else {
            session.setAttribute("error", "Unexpected state returned from authority.");
        }

        model.addAttribute("authCode", code);
        model.addAttribute("idToken", idToken);

        return "redirect:/mail";
    }

    /**
     * @param request .
     * @return sur la page html index
     */
    @RequestMapping("/logout")
    public String logout(final HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.invalidate();
        return "redirect:/";
    }
}
