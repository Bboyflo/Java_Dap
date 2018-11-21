package fr.ynov.dap.dap.web;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.google.api.client.auth.oauth2.StoredCredential;
import com.google.api.client.util.store.DataStore;

import fr.ynov.dap.dap.service.GoogleService;

/**
 * @author Florian
 */
@Controller
public class Welcome extends GoogleService {

    /**
     * @throws Exception constructeur
     * @throws IOException constructeur
     */
    public Welcome() throws Exception, IOException {

    }

    /**
     * @param model renvois les valeurs a la JSP
     * @return le nom de la JSP
     * @throws IOException fonction
     * @throws GeneralSecurityException fonction
     */
    @RequestMapping("/admin")
    public String welcome(final ModelMap model) throws IOException, GeneralSecurityException {

        DataStore<StoredCredential> storedCredential = super.getFlow().getCredentialDataStore();
        Map<String, StoredCredential> map = new HashMap<String, StoredCredential>();

        for (String stKey : storedCredential.keySet()) {
            map.put(stKey, storedCredential.get(stKey));
        }

        final Integer nbemailunreal = 12;
        model.addAttribute("nbEmails", nbemailunreal);

        model.addAttribute("map", map);

        return "welcome";
    }
}
