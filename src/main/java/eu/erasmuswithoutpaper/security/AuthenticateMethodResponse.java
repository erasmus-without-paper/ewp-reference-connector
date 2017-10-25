package eu.erasmuswithoutpaper.security;

import javax.ws.rs.core.Response;

public class AuthenticateMethodResponse {
    private final boolean requiredMethodInfoFulfilled;
    private final String errorMessage;
    private final Response.Status status;
    
    private AuthenticateMethodResponse(boolean requiredMethodInfoFulfilled, String errorMessage, Response.Status status) {
        this.requiredMethodInfoFulfilled = requiredMethodInfoFulfilled;
        this.errorMessage = errorMessage;
        this.status = status;
    }
    
    public boolean isRequiredMethodInfoFulfilled() {
        return requiredMethodInfoFulfilled;
    }

    public boolean isVerifiedOk() {
        return status == Response.Status.OK;
    }
    
    public Response.Status status() {
        return status;
    }

    public boolean hasErrorMessage() {
        return errorMessage != null;
    }

    public String errorMessage() {
        return errorMessage;
    }
    
    public static AuthenticateMethodResponseBuilder builder() {
        return new AuthenticateMethodResponseBuilder();
    }

    public static class AuthenticateMethodResponseBuilder {
        private boolean requiredMethodInfoFulfilled = true;
        private String errorMessage;
        private Response.Status status = Response.Status.OK;

        public AuthenticateMethodResponseBuilder withRequiredMethodInfoFulfilled(boolean methodIsValid) {
            this.requiredMethodInfoFulfilled = methodIsValid;
            return this;
        }

        public AuthenticateMethodResponseBuilder withErrorMessage(String errorMessage) {
            this.errorMessage = errorMessage;
            return this;
        }
        
        public AuthenticateMethodResponseBuilder withResponseCode(Response.Status status) {
            this.status = status;
            return this;
        }

        public AuthenticateMethodResponse build() {
            return new AuthenticateMethodResponse(requiredMethodInfoFulfilled, errorMessage, status);
        }
    }
}
