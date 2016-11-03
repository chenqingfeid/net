package com.sdklite.net;

import java.util.Arrays;
import java.util.Collections;
import java.util.Set;
import java.util.TreeSet;

/**
 * The abstraction of {@link UriHandler}
 * 
 * @author johnsonlee
 *
 */
public abstract class AbstractUriHandler implements UriHandler {

    private final Set<String> schemes;
    
    public AbstractUriHandler(final String... schemes) {
        final Set<String> set = new TreeSet<String>(String.CASE_INSENSITIVE_ORDER);
        set.addAll(Arrays.asList(schemes));
        this.schemes = Collections.unmodifiableSet(set);
    }

    @Override
    public Set<String> getSupportedSchemes() {
        return this.schemes;
    }

    @Override
    public boolean isSchemeSupported(final String scheme) {
        return this.schemes.contains(scheme);
    }

}
