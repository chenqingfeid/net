package com.sdklite.net.http;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.sdklite.net.MimeType;

/**
 * Represents the form entity
 * 
 * @author johnsonlee
 *
 */
public class FormBody extends HttpBody {

    private final Map<String, List<String>> values;

    private FormBody(final Builder builder) {
        this.values = Collections.unmodifiableMap(builder.values);
    }

    /**
     * Returns all names in this form
     */
    public Set<String> getNames() {
        return this.values.keySet();
    }

    /**
     * Returns all of the values associated with the specified name
     * @param name
     * @return
     */
    public List<String> getValues(final String name) {
        if (this.values.containsKey(name)) {
            return this.values.get(name);
        }

        return Collections.emptyList();
    }

    @Override
    public MimeType getContentType() {
        return MimeType.parse("application/x-www-form-urlencoded");
    }

    @Override
    public InputStream getContent() throws IOException {
        final ByteArrayOutputStream out = new ByteArrayOutputStream();
        writeTo(out);
        return new ByteArrayInputStream(out.toByteArray());
    }

    @Override
    public void writeTo(final OutputStream out) throws IOException {
        for (final Iterator<Map.Entry<String, List<String>>> i = this.values.entrySet().iterator(); i.hasNext();) {
            final Map.Entry<String, List<String>> entry = i.next();
            final String name = entry.getKey();

            for (final Iterator<String> j = entry.getValue().iterator(); j.hasNext();) {
                final String value = j.next();

                out.write(URLEncoder.encode(name, "UTF-8").getBytes(Constants.UTF_8));
                out.write('=');
                out.write(URLEncoder.encode(value, "UTF-8").getBytes(Constants.UTF_8));

                if (j.hasNext()) {
                    out.write('&');
                }
            }

            if (i.hasNext()) {
                out.write('&');
            }
        }

        out.flush();
    }

    /**
     * Create a builder of {@link FormBody}
     * 
     * @return an instance of {@link FormBody.Builder}
     */
    public Builder newBuilder() {
        return new Builder(this);
    }

    /**
     * Represents the builder of {@link FormBody}
     * 
     * @author johnsonlee
     *
     */
    public static class Builder {

        private final Map<String, List<String>> values = new LinkedHashMap<String, List<String>>();

        /**
         * Default constructor
         */
        public Builder() {
        }

        private Builder(final FormBody form) {
            this.values.putAll(form.values);
        }

        /**
         * Add name and values
         * 
         * @param name
         *            The name
         * @param values
         *            the values
         * @return this builder
         */
        public Builder add(final String name, String... values) {
            final List<String> list = this.values.containsKey(name) ? this.values.get(name) : new ArrayList<String>();
            list.addAll(Arrays.asList(values));
            this.values.put(name, list);
            return this;
        }

        /**
         * Instantialize a {@link FormBody}
         * 
         * @return an instance of {@link FormBody}
         */
        public FormBody build() {
            return new FormBody(this);
        }
    }
}
