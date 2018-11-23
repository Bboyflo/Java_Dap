package fr.ynov.dap.dap.microsoft.auth;

import java.util.Calendar;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Florian
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class TokenResponse {
    /**.
     * Déclaration de tokenType
     */
    @JsonProperty("token_type")
    private String tokenType;
    /**.
     * Déclaration de scope
     */
    private String scope;
    /**.
     * Déclaration de expiresIn
     */
    @JsonProperty("expires_in")
    private int expiresIn;
    /**.
     * Déclaration de accessToken
     */
    @JsonProperty("access_token")
    private String accessToken;
    /**.
     * Déclaration de refreshToken
     */
    @JsonProperty("refresh_token")
    private String refreshToken;
    /**.
     * Déclaration de idToken
     */
    @JsonProperty("id_token")
    private String idToken;
    /**.
     * Déclaration de error
     */
    private String error;
    /**.
     * Déclaration de errorDescription
     */
    @JsonProperty("error_description")
    private String errorDescription;
    /**.
     * Déclaration de errorCodes
     */
    @JsonProperty("error_codes")
    private int[] errorCodes;
    /**.
     * Déclaration de expirationTime
     */
    private Date expirationTime;

    /**
     * @return tokenType
     */
    public String getTokenType() {
        return tokenType;
    }

    /**
     * @param tokenType .
     */
    public void setTokenType(final String tokenType) {
        this.tokenType = tokenType;
    }

    /**
     * @return scope
     */
    public String getScope() {
        return scope;
    }

    /**
     * @param scope .
     */
    public void setScope(final String scope) {
        this.scope = scope;
    }

    /**
     * @return expiresIn
     */
    public int getExpiresIn() {
        return expiresIn;
    }

    /**
     * @param expiresIn .
     */
    public void setExpiresIn(final int expiresIn) {
        this.expiresIn = expiresIn;
        Calendar now = Calendar.getInstance();
        now.add(Calendar.SECOND, expiresIn);
        this.expirationTime = now.getTime();
    }

    /**
     * @return accessToken
     */
    public String getAccessToken() {
        return accessToken;
    }

    /**
     * @param accessToken .
     */
    public void setAccessToken(final String accessToken) {
        this.accessToken = accessToken;
    }

    /**
     * @return refreshToken
     */
    public String getRefreshToken() {
        return refreshToken;
    }

    /**
     * @param refreshToken .
     */
    public void setRefreshToken(final String refreshToken) {
        this.refreshToken = refreshToken;
    }

    /**
     * @return idToken
     */
    public String getIdToken() {
        return idToken;
    }

    /**
     * @param idToken .
     */
    public void setIdToken(final String idToken) {
        this.idToken = idToken;
    }

    /**
     * @return error
     */
    public String getError() {
        return error;
    }

    /**
     * @param error .
     */
    public void setError(final String error) {
        this.error = error;
    }

    /**
     * @return errorDescription
     */
    public String getErrorDescription() {
        return errorDescription;
    }

    /**
     * @param errorDescription .
     */
    public void setErrorDescription(final String errorDescription) {
        this.errorDescription = errorDescription;
    }

    /**
     * @return errorCodes
     */
    public int[] getErrorCodes() {
        return errorCodes;
    }

    /**
     * @param errorCodes .
     */
    public void setErrorCodes(final int[] errorCodes) {
        this.errorCodes = errorCodes;
    }

    /**
     * @return expirationTime
     */
    public Date getExpirationTime() {
        return expirationTime;
    }
}
