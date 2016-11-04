package com.sdklite.net.http;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;

import com.sdklite.net.MimeType;

class StringPartBody extends PartBody {

    private final byte[] text;

    public StringPartBody(final String text) {
        this(text, Constants.TEXT_PLAIN, null);
    }

    public StringPartBody(final String text, final MimeType contentType) {
        this(text, contentType, null);
    }

    public StringPartBody(final String text, final MimeType contentType, final String filename) {
        super(contentType, filename);
        final Charset charset = contentType.getCharset(Constants.US_ASCII);
        this.text = text.getBytes(charset);
    }

    @Override
    public InputStream getContent() throws IOException {
        return new ByteArrayInputStream(this.text);
    }

    @Override
    public long getContentLength() {
        return this.text.length;
    }

    @Override
    public String getContentTransferEncoding() {
        return Constants.CONTENT_TRANSFER_ENCODING_8BIT;
    }

    @Override
    public void writeTo(final OutputStream out) throws IOException {
        out.write(this.text);
    }

}
