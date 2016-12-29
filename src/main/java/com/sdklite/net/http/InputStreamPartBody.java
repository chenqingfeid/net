package com.sdklite.net.http;

import java.io.IOException;
import java.io.InputStream;

import com.sdklite.net.MimeType;

class InputStreamPartBody extends PartBody {

    private final InputStream stream;

    public InputStreamPartBody(final InputStream stream) {
        this(stream, Constants.APPLICATION_OCTET_STREAM);
    }

    public InputStreamPartBody(final InputStream stream, MimeType contentType) {
        this(stream, contentType, null);
    }

    public InputStreamPartBody(final InputStream stream, final MimeType contentType, String filename) {
        super(contentType, filename);
        this.stream = stream;
    }

    @Override
    public InputStream getContent() throws IOException {
        return this.stream;
    }

    @Override
    public long getContentLength() throws IOException {
        return this.stream.available();
    }
    
    @Override
    public String getContentTransferEncoding() {
        return Constants.CONTENT_TRANSFER_ENCODING_BINARY;
    }

}
