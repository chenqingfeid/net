package com.sdklite.net.http;

/**
 * Represent the HTTP multipart form data part
 * 
 * @author johnsonlee
 *
 */
public class Part extends HttpMessage {

    private final String name;

    private Part(final Builder builder) {
        super(builder);
        this.name = builder.name;
    }

    /**
     * Returns the name of part
     */
    public String getName() {
        return this.name;
    }

    @Override
    public PartEntity getEntity() {
        return (PartEntity) super.getEntity();
    }

    @Override
    public Builder newBuilder() {
        return new Builder(this);
    }

    /**
     * Represents the builder of {@link Part}
     * 
     * @author johnsonlee
     *
     */
    public static class Builder extends HttpMessage.Builder {

        private String name;

        /**
         * Default constructor
         */
        public Builder() {
        }

        private Builder(final Part part) {
            super(part);
            this.name = part.name;
        }

        /**
         * Sets the name of part
         * 
         * @param name
         *            The name of part
         * @return this builder
         */
        public Builder setName(final String name) {
            this.name = name;
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
        public Part build() {
            this.addHeader("Content-Disposition", getContentDisposition());
            this.addHeader("Content-Type", getContentType());
            this.addHeader("Content-Transfer-Encoding", getContentTransferEncoding());
            return new Part(this);
        }

        private String getContentDisposition() {
            final StringBuilder builder = new StringBuilder();
            builder.append("form-data; name=\"").append(this.name).append("\"");

            if (this.entity instanceof PartEntity) {
                final String filename = ((PartEntity) this.entity).getFilename();
                if (null != filename) {
                    builder.append("; filename=\"").append(filename).append("\"");
                }
            }

            return builder.toString();
        }

        private String getContentType() {
            return this.entity.getContentType().toString();
        }

        private String getContentTransferEncoding() {
            if (this.entity instanceof PartEntity) {
                return ((PartEntity) this.entity).getContentTransferEncoding();
            }

            return Constants.CONTENT_TRANSFER_ENCODING_7BIT;
        }
    }
}
