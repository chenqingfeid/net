package com.sdklite.net.http;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import com.sdklite.net.MimeType;

class ByteArrayPartBody extends PartBody {

    private final byte[] data;
    
    private final int offset;
    
    private final int length;

    public ByteArrayPartBody(final byte[] data) {
        this(data, 0, data.length);
    }

    public ByteArrayPartBody(final byte[] data, final int offset, final int length) {
        this(data, offset, length, Constants.APPLICATION_OCTET_STREAM, null);
    }

    public ByteArrayPartBody(final byte[] data, final MimeType contentType) {
        this(data, 0, data.length, contentType, null);
    }

    public ByteArrayPartBody(final byte[] data, final int offset, final int length, final MimeType contentType) {
        this(data, offset, length, contentType, null);
    }

    public ByteArrayPartBody(final byte[] data, final MimeType contentType, final String filename) {
        this(data, 0, data.length, contentType, filename);
    }

    public ByteArrayPartBody(final byte[] data, final int offset, final int length, final MimeType contentType, final String filename) {
        super(contentType, filename);
        this.data = data;
        this.offset = offset;
        this.length = length;
    }

    @Override
    public InputStream getContent() throws IOException {
        return new ByteArrayInputStream(this.data, this.offset, this.length);
    }

    @Override
    public void writeTo(final OutputStream out) throws IOException {
        out.write(this.data, this.offset, this.length);
    }

    @Override
    public long getContentLength() {
        return this.length;
    }

    @Override
    public String getContentTransferEncoding() {
        return Constants.CONTENT_TRANSFER_ENCODING_BINARY;
    }
}
