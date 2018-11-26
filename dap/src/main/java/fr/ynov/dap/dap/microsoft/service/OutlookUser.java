package fr.ynov.dap.dap.microsoft.service;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * @author Florian
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class OutlookUser {
    /**.
     * Déclaration de l'id
     */
    private String id;
    /**.
     * Déclaration du mail
     */
    private String mail;
    /**.
     * Déclaration de displayName
     */
    private String displayName;

    /**
     * @return id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id Changement de la valeur
     */
    public void setId(final String id) {
        this.id = id;
    }

    /**
     * @return mail
     */
    public String getMail() {
        return mail;
    }

    /**
     * @param emailAddress Changement de la valeur
     */
    public void setMail(final String emailAddress) {
        this.mail = emailAddress;
    }

    /**
     * @return displayName
     */
    public String getDisplayName() {
        return displayName;
    }

    /**
     * @param displayName Changement de la valeur
     */
    public void setDisplayName(final String displayName) {
        this.displayName = displayName;
    }
}
