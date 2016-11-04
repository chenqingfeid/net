package com.sdklite.net.http;

class SimpleHttpHeader implements HttpHeader {

    private final String name;
    private final String value;

    public SimpleHttpHeader(final String name, final String value) {
        this.name = name;
        this.value = value;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public String getValue() {
        return this.value;
    }
    
    @Override
    public String toString() {
        return this.name + ": " + this.value;
    }

}
