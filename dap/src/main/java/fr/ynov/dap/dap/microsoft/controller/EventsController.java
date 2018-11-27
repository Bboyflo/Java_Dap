package fr.ynov.dap.dap.microsoft.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import fr.ynov.dap.dap.microsoft.auth.AuthHelper;
import fr.ynov.dap.dap.microsoft.auth.TokenResponse;
import fr.ynov.dap.dap.microsoft.service.Event;
import fr.ynov.dap.dap.microsoft.service.OutlookService;
import fr.ynov.dap.dap.microsoft.service.OutlookServiceBuilder;
import fr.ynov.dap.dap.microsoft.service.PagedResult;

/**
 * @author Florian
 */
@Controller
public class EventsController {

    /**.
     * Déclaration de MAXRESULTS
     */
    private static final int MAXRESULTS = 10;

    /**
     * @param model .
     * @param request .
     * @param redirectAttributes .
     * @return La page event
     */
    @RequestMapping("/events")
    public String events(Model model, HttpServletRequest request, RedirectAttributes redirectAttributes) {
        HttpSession session = request.getSession();
        TokenResponse tokens = (TokenResponse) session.getAttribute("tokens");
        if (tokens == null) {
            // No tokens in session, user needs to sign in
            redirectAttributes.addFlashAttribute("error", "Please sign in to continue.");
            return "redirect:/";
        }

        String tenantId = (String) session.getAttribute("userTenantId");

        tokens = AuthHelper.ensureTokens(tokens, tenantId);

        String email = (String) session.getAttribute("userEmail");

        OutlookService outlookService = OutlookServiceBuilder.getOutlookService(tokens.getAccessToken(), email);

        // Sort by start time in descending order
        String sort = "start/dateTime DESC";
        // Only return the properties we care about
        String properties = "organizer,subject,start,end";

        try {
            PagedResult<Event> events = outlookService.getEvents(sort, properties, MAXRESULTS).execute().body();
            model.addAttribute("events", events.getValue());
            model.addAttribute("logoutUrl", "/logout");
            model.addAttribute("mail", "/mail");
            model.addAttribute("contact", "/contacts");
        } catch (IOException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/";
        }

        return "event";
    }
}
