package com.sdklite.net.http;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Standard HTTP headers
 *
 * @author johnsonlee
 */
public abstract class HttpHeaders {

    private static final Comparator<String> HEADER_NAME_COMPARATOR = new Comparator<String>() {
        @Override
        public int compare(final String a, final String b) {
            return a == b ? 0 : (null == a ? -1 : (null == b ? 1 : String.CASE_INSENSITIVE_ORDER.compare(a, b)));
        }
    };

    /**
     * Return all headers of HTTP message as a map
     * 
     * @param message
     *            The HTTP message
     * @return all headers
     */
    public static Map<String, List<String>> getHeaders(final HttpMessage message) {
        final Map<String, List<String>> map = new TreeMap<String, List<String>>(HEADER_NAME_COMPARATOR);

        for (final HttpHeader header : message.getHeaders()) {
            final String name = header.getName();
            final String value = header.getValue();
            final List<String> values = map.containsKey(name) ? map.get(name) : new ArrayList<String>();

            values.add(value);
            map.put(name, values);
        }

        return Collections.unmodifiableMap(map);
    }

    /**
     * Returns the {@code Content-Length} header value
     * 
     * @param message
     *            The HTTP message
     * @return the content length
     */
    public static long getContentLength(final HttpMessage message) {
        final String contentLength = message.getHeader("Content-Length");
        if (null == contentLength) {
            return -1;
        }

        try {
            return Long.parseLong(contentLength);
        } catch (final NumberFormatException e) {
            return -1;
        }
    }

    /**
     * Parse the specified string as a HTTP header
     * 
     * @param header
     *            A string representing a HTTP header
     * @return an instance of {@link HttpHeader}
     */
    public static HttpHeader parse(final String header) {
        final int colon = header.indexOf(':');
        if (colon < 1 || colon == header.length() - 1) {
            throw new IllegalArgumentException("Malformed HTTP header");
        }

        return new SimpleHttpHeader(header.substring(0, colon).trim(), header.substring(colon + 1).trim());
    }

    private HttpHeaders() {
    }
}
