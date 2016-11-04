package com.sdklite.net.http;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;

import com.sdklite.io.IOUtil;
import com.sdklite.net.MimeType;

abstract class PartBody implements PartEntity {

    private final MimeType contentType;

    private final String filename;

    public PartBody(final MimeType contentType, final String filename) {
        this.contentType = null != contentType ? contentType : null != filename ? MimeType.guess(filename, Constants.APPLICATION_OCTET_STREAM) : Constants.APPLICATION_OCTET_STREAM;
        this.filename = filename;
    }

    @Override
    public final MimeType getContentType() {
        return this.contentType;
    }

    @Override
    public String getContentTransferEncoding() {
        return Constants.CONTENT_TRANSFER_ENCODING_7BIT;
    }

    @Override
    public Charset getCharset() {
        return getContentType().getCharset(Constants.ISO_8859_1);
    }

    @Override
    public void writeTo(final OutputStream out) throws IOException {
        IOUtil.copy(getContent(), out);
    }

    @Override
    public final String getFilename() {
        return this.filename;
    }

}
