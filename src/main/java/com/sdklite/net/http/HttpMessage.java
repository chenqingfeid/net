package com.sdklite.net.http;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Represents the HTTP message
 * 
 * @author johnsonlee
 *
 */
public abstract class HttpMessage {

    protected final List<HttpHeader> headers;

    protected final HttpEntity entity;

    protected HttpMessage(final Builder builder) {
        this.headers = Collections.unmodifiableList(builder.headers);
        this.entity = builder.entity;
    }

    public List<HttpHeader> getHeaders() {
        return this.headers;
    }

    public List<HttpHeader> getHeaders(final String name) {
        final List<HttpHeader> headers = new ArrayList<HttpHeader>();

        for (final HttpHeader header : this.headers) {
            if (header.getName().equalsIgnoreCase(name)) {
                headers.add(header);
            }
        }

        return Collections.unmodifiableList(headers);
    }

    public HttpHeader getHeader(final String name) {
        for (final HttpHeader header : this.headers) {
            if (header.getName().equalsIgnoreCase(name)) {
                return header;
            }
        }

        return null;
    }

    public HttpEntity getEntity() {
        return this.entity;
    }

    public abstract Builder newBuilder();

    public static abstract class Builder {

        protected final List<HttpHeader> headers = new ArrayList<HttpHeader>();

        protected HttpEntity entity;

        public Builder() {
        }

        protected Builder(final HttpMessage message) {
            this.headers.addAll(message.headers);
            this.entity = message.entity;
        }

        public Builder addHeaders(final HttpHeader... headers) {
            this.headers.addAll(Arrays.asList(headers));
            return this;
        }

        public Builder addHeader(final String name, final String value) {
            return addHeaders(new SimpleHttpHeader(name, value));
        }

        public Builder setEntity(final HttpEntity entity) {
            this.entity = entity;
            return this;
        }

        public abstract HttpMessage build();
    }

}
