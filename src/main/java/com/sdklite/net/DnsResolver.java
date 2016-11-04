package com.sdklite.net;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.List;

/**
 * {@link DnsResolver} is used for DNS resolving to establish socket connection
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
        public List<InetAddress> resolve(final String hostname) throws UnknownHostException {
            return Arrays.asList(InetAddress.getAllByName(hostname));
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
    public List<InetAddress> resolve(final String hostname) throws UnknownHostException;

}
