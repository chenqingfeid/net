package com.sdklite.net.http;

import java.nio.charset.Charset;

import com.sdklite.net.MimeType;

interface Constants {

    static final MimeType APPLICATION_OCTET_STREAM = MimeType.parse("application/octet-stream");

    static final MimeType TEXT_PLAIN = MimeType.parse("text/plain");

    static final String CONTENT_TRANSFER_ENCODING_7BIT = "7bit";

    static final String CONTENT_TRANSFER_ENCODING_8BIT = "8bit";

    static final String CONTENT_TRANSFER_ENCODING_BINARY = "binary";

    static final String CONTENT_TRANSFER_ENCODING_BASE64 = "base64";

    static final Charset UTF_8 = Charset.forName("UTF-8");

    static final Charset US_ASCII = Charset.forName("US-ASCII");

    static final Charset ISO_8859_1 = Charset.forName("ISO-8859-1");

}
