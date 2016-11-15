package com.sdklite.net.http;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import com.sdklite.net.MimeType;

/**
 * Represents the multipart form data entity
 * 
 * @author johnsonlee
 *
 */
public class MultipartBody extends HttpBody {

    private static final byte[] DASHES = "--".getBytes();

    private static final byte[] CR_LF = "\r\n".getBytes();

    private static final char[] MULTIPART_CHARS = "-_1234567890abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();

    static String generateBoundary() {
        final StringBuilder buffer = new StringBuilder();
        final Random rand = new Random();
        final int count = rand.nextInt(11) + 30; // a random size from 30 to 40

        for (int i = 0; i < count; i++) {
            buffer.append(MULTIPART_CHARS[rand.nextInt(MULTIPART_CHARS.length)]);
        }

        return buffer.toString();
    }

    private final Charset charset;

    private final String boundary;

    private final List<Part> parts;

    private MultipartBody(final Builder builder) {
        this.charset = builder.charset;
        this.boundary = builder.boundary;
        this.parts = Collections.unmodifiableList(builder.parts);
    }

    @Override
    public Charset getCharset() {
        return this.charset;
    }

    /**
     * Returns the boundary
     */
    public String getBoundary() {
        return this.boundary;
    }

    /**
     * Returns the parts
     */
    public List<Part> getParts() {
        return this.parts;
    }

    @Override
    public MimeType getContentType() {
        final StringBuilder builder = new StringBuilder();
        builder.append("multipart/form-data; boundary=").append(this.boundary);

        if (null != this.charset) {
            builder.append("; charset=").append(this.charset.name());
        }

        return MimeType.parse(builder.toString());
    }

    @Override
    public InputStream getContent() throws IOException {
        final ByteArrayOutputStream out = new ByteArrayOutputStream();
        writeTo(out);
        return new ByteArrayInputStream(out.toByteArray());
    }

    @Override
    public void writeTo(final OutputStream out) throws IOException {
        final byte[] boundary = this.boundary.getBytes();

        for (final Part part : this.parts) {
            out.write(DASHES);
            out.write(boundary);
            out.write(CR_LF);

            for (final HttpHeader header : part.getHeaders()) {
                out.write(header.toString().getBytes());
                out.write(CR_LF);
            }

            out.write(CR_LF);
            part.getEntity().writeTo(out);
            out.write(CR_LF);
        }

        out.write(DASHES);
        out.write(boundary);
        out.write(DASHES);
        out.write(CR_LF);
    }

    /**
     * Create a {@link Builder}
     * 
     * @return a builder
     */
    public Builder newBuilder() {
        return new Builder(this);
    }

    /**
     * The builder of {@link MultipartBody}
     * 
     * @author johnsonlee
     *
     */
    public static final class Builder {

        private Charset charset = Charset.defaultCharset();

        private String boundary = generateBoundary();

        private final List<Part> parts = new ArrayList<Part>();

        /**
         * Default constructor
         */
        public Builder() {
        }

        private Builder(final MultipartBody body) {
            this.charset = body.charset;
            this.boundary = body.boundary;
            this.parts.addAll(body.parts);
        }

        /**
         * Sets the charset
         * 
         * @param charset
         *            The charset
         * @return this builder
         */
        public Builder setCharset(final Charset charset) {
            this.charset = charset;
            return this;
        }

        /**
         * Sets the boundary
         * 
         * @param boundary
         *            The boundary
         * @return this builder
         */
        public Builder setBoundary(final String boundary) {
            this.boundary = boundary;
            return this;
        }

        /**
         * Appends the specified part into parts
         * 
         * @param part
         *            A part
         * @return this builder
         */
        public Builder addPart(final Part part) {
            this.parts.add(part);
            return this;
        }

        /**
         * Appends the specified name and part entity into parts
         * 
         * @param name
         *            The name of part
         * @param entity
         *            The entity of part
         * @return this builder
         */
        public Builder addPart(final String name, final PartEntity entity) {
            return this.addPart(new Part.Builder().setName(name).setEntity(entity).build());
        }

        /**
         * Appends the specified name and part entity into parts
         * 
         * @param name
         *            The name of part
         * @param data
         *            The data of part entity
         * @return this builder
         */
        public Builder addPart(final String name, final byte[] data) {
            return this.addPart(name, new ByteArrayPartBody(data));
        }

        /**
         * Appends the specified name and part entity into parts
         * 
         * @param name
         *            The name of part
         * @param data
         *            The data of part entity
         * @param contentType
         *            The content type of part entity
         * @return this builder
         */
        public Builder addPart(final String name, final byte[] data, final MimeType contentType) {
            return this.addPart(name, new ByteArrayPartBody(data, contentType));
        }

        /**
         * Appends the specified name and part entity into parts
         * 
         * @param name
         *            The name of part
         * @param file
         *            The data of part entity
         * @return this builder
         */
        public Builder addPart(final String name, final File file) {
            return this.addPart(name, new FilePartBody(file));
        }

        /**
         * Appends the specified name and part entity into parts
         * 
         * @param name
         *            The name of part
         * @param file
         *            The data of part entity
         * @param contentType
         *            The content type of part entity
         * @return this builder
         */
        public Builder addPart(final String name, final File file, final MimeType contentType) {
            return this.addPart(name, new FilePartBody(file, contentType));
        }

        /**
         * Appends the specified name and part entity into parts
         * 
         * @param name
         *            The name of part
         * @param text
         *            The data of part entity
         * @return this builder
         */
        public Builder addPart(final String name, final String text) {
            return this.addPart(name, new StringPartBody(text));
        }

        /**
         * Appends the specified name and part entity into parts
         * 
         * @param name
         *            The name of part
         * @param text
         *            The data of part entity
         * @param contentType
         *            The content type of part entity
         * @return this builder
         */
        public Builder addPart(final String name, final String text, final MimeType contentType) {
            return this.addPart(name, new StringPartBody(text, contentType));
        }

        /**
         * Instantialize a multipart body
         * 
         * @return an instance of {@link MultipartBody}
         */
        public MultipartBody build() {
            return new MultipartBody(this);
        }
    }
}
