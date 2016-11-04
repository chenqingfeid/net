package com.sdklite.net.http;

/**
 * Represents the HTTP methods, including WebDAV methods
 * 
 * @author johnsonlee
 *
 */
public enum HttpMethod {

    /**
     * GET
     */
    GET,

    /**
     * HEAD
     */
    HEAD,

    /**
     * POST
     */
    POST,

    /**
     * PUT
     */
    PUT,

    /**
     * DELETE
     */
    DELETE,

    /**
     * PATCH
     */
    PATCH,

    /**
     * MOVE
     */
    MOVE,

    /**
     * OPTIONS
     */
    OPTIONS,

    /**
     * REPORT
     */
    REPORT,

    /**
     * PROPFIND
     */
    PROPFIND,

    /**
     * PROPPATCH
     */
    PROPPATCH,

    /**
     * MKCOL
     */
    MKCOL,

    /**
     * LOCK
     */
    LOCK,

    /**
     * UNLOCK
     */
    UNLOCK;

    /**
     * Determine if cache should be invalidated
     */
    public boolean invalidatesCache() {
        return this == POST || this == PATCH || this == PUT || this == DELETE || this == MOVE;
    }

    /**
     * Determine if request body is required
     */
    public boolean requiresRequestBody() {
        return this == POST || this == PATCH || this == PUT || this == PROPPATCH || this == REPORT;
    }

    /**
     * Determine if request body is permitted 
     */
    public boolean permitsRequestBody() {
        return this.requiresRequestBody() || this == DELETE || this == OPTIONS || this == PROPFIND || this == MKCOL || this == LOCK;
    }
}
