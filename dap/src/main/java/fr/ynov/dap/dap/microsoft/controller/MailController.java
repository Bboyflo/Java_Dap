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
import fr.ynov.dap.dap.microsoft.service.Message;
import fr.ynov.dap.dap.microsoft.service.OutlookService;
import fr.ynov.dap.dap.microsoft.service.OutlookServiceBuilder;
import fr.ynov.dap.dap.microsoft.service.PagedResult;

/**
 * @author Florian
 */
@Controller
public class MailController {

    /**
     * Déclaration de la constante MAXMAILAFFICHE
     */
    private static final int MAXMAILAFFICHE = 10;

    /**
     * @param model .
     * @param request .
     * @param redirectAttributes .
     * @return la page mail
     */
    @RequestMapping("/mail")
    public String mail(final Model model, final HttpServletRequest request,
            final RedirectAttributes redirectAttributes) {
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

        // Retrieve messages from the inbox
        String folder = "inbox";
        // Sort by time received in descending order
        String sort = "receivedDateTime DESC";
        // Only return the properties we care about
        String properties = "receivedDateTime,from,isRead,subject,bodyPreview";
        // Return at most 10 messages
        Integer maxResults = MAXMAILAFFICHE;

        try {
            PagedResult<Message> messages = outlookService.getMessages(
                    folder, sort, properties, maxResults)
                    .execute().body();

            //PageResultForAllMessage messagesUnreadAll = outlookService.getAllMessages(folder).execute().body();
            model.addAttribute("messages", messages.getValue());
            //model.addAttribute("nbMessagesUnread", messagesUnreadAll.getUnreadItemCount());
            model.addAttribute("logoutUrl", "/logout");
        } catch (IOException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/";
        }

        return "mail";
    }
}
