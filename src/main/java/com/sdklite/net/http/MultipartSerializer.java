package com.sdklite.net.http;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TreeMap;

import com.sdklite.io.AbstractSerializer;
import com.sdklite.util.Introspector;

public class MultipartSerializer extends AbstractSerializer<Object> {

    private final MultipartBody.Builder builder = new MultipartBody.Builder();
    
    @Override
    public InputStream serialize(final Object o) throws IOException {
        final Map<String, Object> parameters;
        
        if (o instanceof Map) {
            if (o instanceof TreeMap) {
                parameters = new TreeMap<String, Object>();
            } else if (o instanceof LinkedHashMap) {
                parameters = new LinkedHashMap<String, Object>();
            } else {
                parameters = new HashMap<String, Object>();
            }

            for (final Map.Entry<?, ?> entry : ((Map<?, ?>) o).entrySet()) {
                parameters.put(String.valueOf(entry.getKey()), entry.getValue());
            }
        } else {
            parameters = Introspector.properties(o);
        }

        final MultipartBody.Builder builder = this.builder.build().newBuilder();
        for (final Map.Entry<String, Object> parameter : parameters.entrySet()) {
            final String name = parameter.getKey();
            final Object value = parameter.getValue();
            if (value instanceof byte[]) {
                builder.addPart(name, (byte[]) value);
            } else if (value instanceof File) {
                builder.addPart(name, (File) value);
            } else if (value instanceof InputStream) {
                builder.addPart(name, (InputStream) value);
            } else if (value instanceof PartEntity) {
                builder.addPart(name, (PartEntity) value);
            } else {
                builder.addPart(name, String.valueOf(value));
            }
        }

        return builder.build().getContent();
    }

    public String getBoundary() {
        return this.builder.getBoundary();
    }
}
