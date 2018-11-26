package fr.ynov.dap.dap.microsoft.service;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * @author Florian
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Message {
    /**.
     * Déclaration de l'id
     */
    private String id;
    /**.
     * Déclaration de receivedDateTime
     */
    private Date receivedDateTime;
    /**.
     * Déclaration de from
     */
    private Recipient from;
    /**.
     * Déclaration de isRead
     */
    private Boolean isRead;
    /**.
     * Déclaration de subject
     */
    private String subject;
    /**.
     * Déclaration de bodyPreview
     */
    private String bodyPreview;

    /**
     * @return l'id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id changement de la valeur
     */
    public void setId(final String id) {
        this.id = id;
    }

    /**
     * @return receivedDateTime
     */
    public Date getReceivedDateTime() {
        return receivedDateTime;
    }

    /**
     * @param receivedDateTime changement de la valeur
     */
    public void setReceivedDateTime(final Date receivedDateTime) {
        this.receivedDateTime = receivedDateTime;
    }

    /**
     * @return from
     */
    public Recipient getFrom() {
        return from;
    }

    /**
     * @param from changement de la valeur
     */
    public void setFrom(final Recipient from) {
        this.from = from;
    }

    /**
     * @return isRead
     */
    public Boolean getIsRead() {
        return isRead;
    }

    /**
     * @param isRead changement de la valeur
     */
    public void setIsRead(final Boolean isRead) {
        this.isRead = isRead;
    }

    /**
     * @return subject
     */
    public String getSubject() {
        return subject;
    }

    /**
     * @param subject changement de la valeur
     */
    public void setSubject(final String subject) {
        this.subject = subject;
    }

    /**
     * @return bodyPreview
     */
    public String getBodyPreview() {
        return bodyPreview;
    }

    /**
     * @param bodyPreview changement de la valeur
     */
    public void setBodyPreview(final String bodyPreview) {
        this.bodyPreview = bodyPreview;
    }
}