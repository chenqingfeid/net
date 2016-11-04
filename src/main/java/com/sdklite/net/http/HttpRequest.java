package com.sdklite.net.http;

/**
 * Represents the HTTP request message
 * 
 * @author johnsonlee
 *
 */
public class HttpRequest extends HttpMessage {

    private final HttpMethod method;

    private final String url;

    private HttpRequest(final Builder builder) {
        super(builder);
        this.url = builder.url;
        this.method = builder.method;
    }

    /**
     * Returns the HTTP method
     */
    public HttpMethod getMethod() {
        return this.method;
    }

    /**
     * Returns the requested URL
     */
    public String getUrl() {
        return this.url;
    }

    /**
     * Determine if the protocol is secure
     * 
     * @return true if the protocol is HTTPS
     */
    public boolean isSecure() {
        final int colon = this.url.indexOf(":");
        if (colon > 0 && colon < this.url.length()) {
            return "https".equalsIgnoreCase(this.url.substring(0, colon));
        }

        return false;
    }

    @Override
    public Builder newBuilder() {
        return new Builder(this);
    }

    /**
     * Represents the builder of HTTP request
     * 
     * @author johnsonlee
     *
     */
    public static final class Builder extends HttpMessage.Builder {

        private String url;

        private HttpMethod method;

        /**
         * Default constructor
         */
        public Builder() {
        }

        private Builder(final HttpRequest request) {
            super(request);
        }

        /**
         * Sets the HTTP method
         * 
         * @param method
         *            The HTTP method
         * @return this builder
         */
        public Builder setMethod(final HttpMethod method) {
            this.method = method;
            return this;
        }

        /**
         * Set the request URL
         * 
         * @param url
         *            The request URL
         * @return this builder
         */
        public Builder setUrl(final String url) {
            this.url = url;
            return this;
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

        @Override
        public HttpRequest build() {
            return new HttpRequest(this);
        }

    }

}
