package com.sdklite.net;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * {@link DnsResolver} is used for DNS resolving during network communication
 * 
 * @author johnsonlee
 *
 */
public interface DnsResolver {

    /**
     * The system wide DNS resolver
     */
    public static final DnsResolver SYSTEM = new DnsResolver() {
        @Override
        public InetAddress resolve(final String hostname) throws UnknownHostException {
            return InetAddress.getByName(hostname);
        }
    };

    /**
     * Resolve the specified hostname as IP address
     * 
     * @param hostname
     *            The hostname to be resolved
     * @return resolved IP address
     * @throws UnknownHostException
     *             if the hostname could not be resolved
     */
    public InetAddress resolve(final String hostname) throws UnknownHostException;

}
