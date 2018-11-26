package fr.ynov.dap.dap.microsoft.service;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * @author Florian
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class EmailAddress {
    /**.
     * Déclaration de name
     */
    private String name;
    /**.
     * Déclaration de l'adresse
     */
    private String address;

    /**
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name mofication de la valeur
     */
    public void setName(final String name) {
        this.name = name;
    }

    /**
     * @return address
     */
    public String getAddress() {
        return address;
    }

    /**
     * @param address modification de la valeur
     */
    public void setAddress(final String address) {
        this.address = address;
    }
}
