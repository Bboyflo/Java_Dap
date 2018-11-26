package fr.ynov.dap.dap.microsoft.service;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Florian
 * @param <T>
 */
public class PagedResult<T> {
    /**.
     * Déclaration de nextPageLink
     */
    @JsonProperty("@odata.nextLink")
    private String nextPageLink;
    /**.
     * Déclaration de value
     */
    private T[] value;
    /**.
     * Déclaration du context
     */
    @JsonProperty("@odata.context")
    private String context;

    /**
     * @return nextPageLink
     */
    public String getNextPageLink() {
        return nextPageLink;
    }

    /**
     * @param nextPageLink modification de la valeur
     */
    public void setNextPageLink(final String nextPageLink) {
        this.nextPageLink = nextPageLink;
    }

    /**
     * @return value
     */
    public T[] getValue() {
        return value;
    }

    /**
     * @param value modification de la valeur
     */
    public void setValue(final T[] value) {
        this.value = value;
    }

    /**
     * @return context
     */
    public String getContext() {
        return context;
    }

    /**
     * @param mContext Modification de la valeur
     */
    public void setContext(final String mContext) {
        this.context = mContext;
    }
}
