package com.sdklite.net.http;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import com.sdklite.net.MimeType;

class FilePartBody extends PartBody {

    private final File file;

    public FilePartBody(final File file) {
        this(file, MimeType.guess(file, Constants.APPLICATION_OCTET_STREAM));
    }

    public FilePartBody(final File file, final MimeType contentType) {
        super(contentType, file.getName());
        this.file = file;
    }

    @Override
    public InputStream getContent() throws IOException {
        return new FileInputStream(this.file);
    }

    @Override
    public long getContentLength() {
        return file.length();
    }

    @Override
    public String getContentTransferEncoding() {
        return Constants.CONTENT_TRANSFER_ENCODING_BINARY;
    }
}
