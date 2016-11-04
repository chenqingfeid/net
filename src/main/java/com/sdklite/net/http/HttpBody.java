package com.sdklite.net.http;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;

import com.sdklite.io.IOUtil;
import com.sdklite.net.MimeType;

/**
 * The basic HTTP message entity implementation
 * 
 * @author johnsonlee
 *
 */
public abstract class HttpBody implements HttpEntity {

    public static HttpBody newInstance(final MimeType contentType, final byte[] buf, final int offset, final int length) {
        return new HttpBody() {
            @Override
            public MimeType getContentType() {
                return contentType;
            }

            @Override
            public long getContentLength() {
                return length;
            }

            @Override
            public InputStream getContent() {
                return new ByteArrayInputStream(buf, offset, length);
            }
        };
    }

    public static HttpBody newInstance(final String contentType, final byte[] buf, final int offset, final int length) {
        return newInstance(MimeType.parse(contentType), buf, offset, length);
    }

    public static HttpBody newInstance(final MimeType contentType, final byte[] buf) {
        return newInstance(contentType, buf, 0, buf.length);
    }

    public static HttpBody newInstance(final String contentType, final byte[] buf) {
        return newInstance(MimeType.parse(contentType), buf);
    }

    public static HttpBody newInstance(final MimeType contentType, final String s) {
        return newInstance(contentType, s.getBytes(contentType.getCharset(Constants.UTF_8)));
    }

    public static HttpBody newInstance(final String contentType, final String s) {
        return newInstance(MimeType.parse(contentType), s);
    }

    public static HttpBody newInstance(final MimeType contentType, final File file) {
        return new HttpBody() {
            @Override
            public MimeType getContentType() {
                return contentType;
            }

            @Override
            public long getContentLength() {
                return file.length();
            }

            @Override
            public InputStream getContent() throws FileNotFoundException {
                return new FileInputStream(file);
            }
        };
    }

    public static HttpBody newInstance(final String contentType, final File file) {
        return newInstance(MimeType.parse(contentType), file);
    }

    protected HttpBody() {
    }

    @Override
    public Charset getCharset() {
        final MimeType contentType = getContentType();
        if (null != contentType) {
            return contentType.getCharset();
        }

        return null;
    }

    public Charset getCharset(final Charset defaultCharset) {
        final Charset charset = getCharset();
        if (null != charset) {
            return charset;
        }

        return defaultCharset;
    }

    @Override
    public long getContentLength() {
        return -1;
    }

    @Override
    public void writeTo(final OutputStream out) throws IOException {
        IOUtil.copy(getContent(), out);
    }

}
