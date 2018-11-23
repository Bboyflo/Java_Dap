package fr.ynov.dap.dap.microsoft.auth;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.UUID;

import org.springframework.web.util.UriComponentsBuilder;

/**
 * @author Florian
 */
public class AuthHelper {
    /**.
     * Déclaration de AUTHORITY
     */
    private static final String AUTHORITY = "https://login.microsoftonline.com";
    /**.
     * Déclaration de AUTHORIZEURL
     */
    private static final String AUTHORIZEURL = AUTHORITY + "/common/oauth2/v2.0/authorize";

    /**.
     * Déclaration du tableau scopes
     */
    private static String[] scopes = {
            "openid",
            "offline_access",
            "profile",
            "User.Read",
            "Mail.Read"
    };

    /**.
     * Déclaration de appId
     */
    private static String appId = null;
    /**.
     * Déclaration de appPassword
     */
    private static String appPassword = null;
    /**.
     * Déclaration de redirectUrl
     */
    private static String redirectUrl = null;

    /**
     * @return appId
     */
    private static String getAppId() {
        if (appId == null) {
            try {
                loadConfig();
            } catch (Exception e) {
                return null;
            }
        }
        return appId;
    }

    /**
     * @return appPassword
     */
    private static String getAppPassword() {
        if (appPassword == null) {
            try {
                loadConfig();
            } catch (Exception e) {
                return null;
            }
        }
        return appPassword;
    }

    /**
     * @return redirectUrl
     */
    private static String getRedirectUrl() {
        if (redirectUrl == null) {
            try {
                loadConfig();
            } catch (Exception e) {
                return null;
            }
        }
        return redirectUrl;
    }

    /**
     * @return un StringBuilder
     */
    private static String getScopes() {
        StringBuilder sb = new StringBuilder();
        for (String scope : scopes) {
            sb.append(scope + " ");
        }
        return sb.toString().trim();
    }

    /**
     * @throws IOException fonction
     */
    private static void loadConfig() throws IOException {
        String authConfigFile = "auth.properties";
        InputStream authConfigStream = AuthHelper.class.getClassLoader().getResourceAsStream(authConfigFile);

        if (authConfigStream != null) {
            Properties authProps = new Properties();
            try {
                authProps.load(authConfigStream);
                appId = authProps.getProperty("appId");
                appPassword = authProps.getProperty("appPassword");
                redirectUrl = authProps.getProperty("redirectUrl");
            } finally {
                authConfigStream.close();
            }
        } else {
            throw new FileNotFoundException("Property file '" + authConfigFile + "' not found in the classpath.");
        }
    }

    /**
     * @param state le statut
     * @param nonce .
     * @return urlBuilder
     */
    public static String getLoginUrl(final UUID state, final UUID nonce) {

        UriComponentsBuilder urlBuilder = UriComponentsBuilder.fromHttpUrl(AUTHORIZEURL);
        urlBuilder.queryParam("client_id", getAppId());
        urlBuilder.queryParam("redirect_uri", getRedirectUrl());
        urlBuilder.queryParam("response_type", "code id_token");
        urlBuilder.queryParam("scope", getScopes());
        urlBuilder.queryParam("state", state);
        urlBuilder.queryParam("nonce", nonce);
        urlBuilder.queryParam("response_mode", "form_post");

        return urlBuilder.toUriString();
    }
}