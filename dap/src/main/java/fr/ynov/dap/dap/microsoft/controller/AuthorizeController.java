package fr.ynov.dap.dap.microsoft.controller;

import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

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
            session.setAttribute("authCode", code);
            session.setAttribute("idToken", idToken);
        } else {
            session.setAttribute("error", "Unexpected state returned from authority.");
        }
        return "mail";
    }
}
