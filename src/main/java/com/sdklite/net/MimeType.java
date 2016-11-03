package com.sdklite.net;

import java.io.File;
import java.io.Serializable;
import java.nio.charset.Charset;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.net.Uri;
import android.webkit.MimeTypeMap;

/**
 * Represents the MIME type as defined in <a href="https://www.ietf.org/rfc/rfc2046.txt">Multipurpose Internet Mail Extensions (MIME) Part Two : Media Types</a>
 * 
 * @author johnsonlee
 * @see <a href="https://www.ietf.org/rfc/rfc2046.txt">RFC 2046</a>
 *
 */
@SuppressWarnings("serial")
public class MimeType implements Serializable {

    private static final String TOKEN = "([a-zA-Z0-9-!#$%&'*+.^_`{|}~]+)";

    private static final String QUOTED = "(\"[^\"]*\")";

    private static final Pattern TYPE_SUBTYPE = Pattern.compile(TOKEN + "/" + TOKEN);

    private static final Pattern PARAMETER = Pattern.compile(";\\s*(?:" + TOKEN + "=(?:" + TOKEN + "|" + QUOTED + "))?");

    /**
     * Parse string as {@link MimeType}
     * 
     * @param string
     *            The MIME type string
     * @return an instance of {@link MimeType}
     * @throws MalformedMimeTypeException
     *             if the the specified MIME Type string is malformed
     */
    public static MimeType parse(final String string) throws MalformedMimeTypeException {
        final Matcher typeMatcher = TYPE_SUBTYPE.matcher(string);
        if (!typeMatcher.lookingAt()) {
            return null;
        }

        final String type = typeMatcher.group(1).toLowerCase(Locale.US);
        final String subtype = typeMatcher.group(2).toLowerCase(Locale.US);
        final Matcher parametersMatcher = PARAMETER.matcher(string);
        final Map<String, String> parameters = new LinkedHashMap<String, String>();

        for (int i = typeMatcher.end(), n = string.length(); i < n; i = parametersMatcher.end()) {
            parametersMatcher.region(i, string.length());
            if (!parametersMatcher.lookingAt()) {
                throw new MalformedMimeTypeException(string);
            }

            final String name = parametersMatcher.group(1);
            if (name == null) {
                continue;
            }

            final String value = parametersMatcher.group(2) != null
                    ? parametersMatcher.group(2) // Value is a token.
                    : parametersMatcher.group(3); // Value is a quoted string.
            parameters.put(name, value);
        }

        return new MimeType(type, subtype, parameters);
    }

    /**
     * Guess the MIME type from the specified URL
     * 
     * @param url The URL to guess
     * 
     * @return an instanceof {@link MimeType} or null if the file extension has
     *         not registered
     */
    public static MimeType guess(final String url) {
        final String ext = MimeTypeMap.getFileExtensionFromUrl(url);
        final MimeTypeMap mtm = MimeTypeMap.getSingleton();
        if (mtm.hasExtension(ext)) {
            return MimeType.parse(mtm.getMimeTypeFromExtension(ext));
        }

        return null;
    }

    /**
     * Guess the MIME type from the specified file
     * 
     * @param file
     *            The file to guess
     * @return an instanceof {@link MimeType} or null if the file extension has
     *         not registered
     */
    public static MimeType guess(final Uri uri) {
        return guess(uri.toString());
    }

    /**
     * Guess the MIME type from the specified file
     * 
     * @param file
     *            The file to guess
     * @return an instanceof {@link MimeType} or null if the file extension has
     *         not registered
     */
    public static MimeType guess(final File file) {
        return guess(Uri.fromFile(file));
    }

    private final String type;

    private final String subtype;

    private final Map<String, String> parameters;

    /**
     * Clone an instance from {@code mimeType}
     * 
     * @param mimeType
     *            The original mimeType
     */
    public MimeType(final MimeType mimeType) {
        this.type = mimeType.type;
        this.subtype = mimeType.subtype;
        this.parameters = Collections.unmodifiableMap(mimeType.parameters);
    }

    /**
     * Create an instance
     * 
     * @param type
     *            The MIME media type
     */
    public MimeType(final String type) throws MalformedMimeTypeException {
        this(type, "*");
    }

    /**
     * Create an instance
     * 
     * @param type
     *            The MIME media type
     * @param subtype
     *            The MIME media subtype
     */
    public MimeType(final String type, final String subtype) throws MalformedMimeTypeException {
        this(type, subtype, Collections.<String, String>emptyMap());
    }

    /**
     * Create an instance
     * 
     * @param type
     *            The MIME media type
     * @param subtype
     *            The MIME media subtype
     * @param parameters
     *            The parameters
     */
    public MimeType(final String type, final String subtype, final Map<String, String> parameters) throws MalformedMimeTypeException {
        if (null == type) {
            throw new IllegalArgumentException("Type is required");
        }

        if (!type.matches(TOKEN)) {
            throw new MalformedMimeTypeException("Malformed type: " + type);
        }

        if (null != subtype && !subtype.matches(TOKEN)) {
            throw new MalformedMimeTypeException("Malformed subtype: " + subtype);
        }

        for (final Map.Entry<String, String> parameter : parameters.entrySet()) {
            final String name = parameter.getKey();
            final String value = parameter.getValue();

            if (!name.matches(TOKEN)) {
                throw new MalformedMimeTypeException("Malformed parameter name: " + name);
            }

            if (!value.matches("(?:" + TOKEN + "|" + QUOTED + ")")) {
                throw new MalformedMimeTypeException("Malformed parameter value: " + value);
            }
        }

        final TreeMap<String, String> sorted = new TreeMap<String, String>(new Comparator<String>() {
            @Override
            public int compare(final String a, final String b) {
                if ("charset".equalsIgnoreCase(a) && "charset".equalsIgnoreCase(b)) {
                    return 0;
                }

                return a.compareTo(b);
            }
        });
        sorted.putAll(parameters);

        this.type = type;
        this.subtype = subtype;
        this.parameters = Collections.unmodifiableMap(sorted);
    }

    /**
     * Returns the type
     */
    public String getType() {
        return this.type;
    }

    /**
     * Returns the subtype
     */
    public String getSubtype() {
        return this.subtype;
    }

    /**
     * Returns the parameter names
     */
    public Map<String, String> getParameters() {
        return this.parameters;
    }

    /**
     * Determine if the specified parameter exists
     * 
     * @param name
     *            The parameter name to determine
     * @return true only if the parameter exists
     */
    public boolean hasParameter(final String name) {
        return this.parameters.containsKey(name);
    }

    /**
     * Returns the value of the specified parameter or null if not exists
     * 
     * @param name
     *            The parameter name
     * @return the value of parameter
     */
    public String getParameter(final String name) {
        final String value = this.parameters.get(name);
        if (null != value && value.startsWith("\"") && value.endsWith("\"")) {
            return value.substring(1, value.length() - 1);
        }

        return value;
    }

    /**
     * Returns the value of the specified parameter or null if not exists
     * 
     * @param name
     *            The parameter name
     * @return the value of parameter
     */
    public String getParameter(final String name, final String defaultValue) {
        final String value = getParameter(name);
        if (null != value) {
            return value;
        }
        return defaultValue;
    }

    /**
     * Returns the charset parameter or the defualt charset {@code US-ASCII} if
     * charset parameter absent
     * 
     * @return the charset
     */
    public Charset getCharset() {
        return Charset.forName(getParameter("charset", "US-ASCII"));
    }

    /**
     * Returns the charset parameter or the defualt charset if charset parameter
     * absent
     * 
     * @param defaultCharset
     *            The default charset to return if charset parameter absent
     * 
     * @return the charset
     */
    public Charset getCharset(final String defaultCharset) {
        return Charset.forName(getParameter("charset", defaultCharset));
    }

    /**
     * Determine if the type is the wildcard character {@code *}
     * @return
     */
    public boolean isWildcardType() {
        return "*".equals(this.type);
    }

    /**
     * Determine if the subtype is the wildcard character {@code *} or the wildcard
     * character followed by a suffix (e.g. {@code *+xml}).
     * 
     * @return true the subtype is a wildcard, otherwise false is returned
     */
    public boolean isWildcardSubtype() {
        return null != this.subtype && this.subtype.startsWith("*");
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(this.type);

        if (null != this.subtype) {
            builder.append("/").append(this.subtype);
        }

        if (this.parameters.size() > 0) {
            for (final Map.Entry<String, String> parameter : this.parameters.entrySet()) {
                final String name = parameter.getKey();
                final String value = parameter.getValue();
                builder.append("; ").append("charset".equalsIgnoreCase(name) ? "charset" : name).append("=").append(value);
            }
        }

        return builder.toString();
    }
}
