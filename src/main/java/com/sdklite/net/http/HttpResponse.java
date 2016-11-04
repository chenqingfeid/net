package com.sdklite.net.http;

import static java.net.HttpURLConnection.HTTP_MOVED_PERM;
import static java.net.HttpURLConnection.HTTP_MOVED_TEMP;
import static java.net.HttpURLConnection.HTTP_MULT_CHOICE;
import static java.net.HttpURLConnection.HTTP_SEE_OTHER;

/**
 * Represents the HTTP response message
 * 
 * @author johnsonlee
 *
 */
public class HttpResponse extends HttpMessage {

    public static final int HTTP_TEMP_REDIRECT = 307;

    public static final int HTTP_PERM_REDIRECT = 308;

    private final String protocol;

    private final int statusCode;

    private final String reasonPhrase;

    private HttpResponse(final Builder builder) {
        super(builder);
        this.protocol = builder.protocol;
        this.statusCode = builder.statusCode;
        this.reasonPhrase = builder.reasonPhrase;
    }

    public String getProtocol() {
        return this.protocol;
    }

    public int getStatusCode() {
        return this.statusCode;
    }

    public String getReasonPhrase() {
        return this.reasonPhrase;
    }

    public boolean isSuccessful() {
        return this.statusCode >= 200 && this.statusCode < 300;
    }

    public boolean isRedirect() {
        switch (this.statusCode) {
        case HTTP_MULT_CHOICE:
        case HTTP_MOVED_PERM:
        case HTTP_MOVED_TEMP:
        case HTTP_SEE_OTHER:
        case HTTP_PERM_REDIRECT:
        case HTTP_TEMP_REDIRECT:
            return true;
        default:
            return false;
        }
    }

    @Override
    public Builder newBuilder() {
        return new Builder(this);
    }

    /**
     * Represents the builder of HTTP response message
     * 
     * @author johnsonlee
     *
     */
    public static final class Builder extends HttpMessage.Builder {

        private String protocol;
        private int statusCode;
        private String reasonPhrase;

        public Builder() {
        }

        private Builder(final HttpResponse response) {
            super(response);
        }

        @Override
        public Builder addHeaders(final HttpHeader... headers) {
            super.addHeaders(headers);
            return this;
        }

        @Override
        public Builder addHeader(final String name, final String value) {
            super.addHeader(name, value);
            return this;
        }

        @Override
        public Builder setEntity(final HttpEntity entity) {
            super.setEntity(entity);
            return this;
        }

        /**
         * Sets the protocol identifier
         * 
         * @param protocol
         *            The protocol identifier in status line
         * @return this builder
         */
        public Builder setProtocol(final String protocol) {
            this.protocol = protocol;
            return this;
        }

        /**
         * Sets the status code
         * 
         * @param statusCode
         *            The status code in status line
         * @return this builder
         */
        public Builder setStatusCode(final int statusCode) {
            this.statusCode = statusCode;
            return this;
        }

        /**
         * Sets the reason phrase
         * 
         * @param reasonPhrase
         *            The reason phrase in status line
         * @return
         */
        public Builder setReasonPhrase(final String reasonPhrase) {
            this.reasonPhrase = reasonPhrase;
            return this;
        }

        @Override
        public HttpResponse build() {
            return new HttpResponse(this);
        }

    }

}
