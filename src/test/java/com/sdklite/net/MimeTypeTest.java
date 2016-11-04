package com.sdklite.net;

import static org.junit.Assert.assertEquals;

import java.io.File;

import org.junit.Test;

public class MimeTypeTest {

    @Test
    public void guessMimeTypeFromFileNameShouldBeOk() {
        final File pom = new File(System.getProperty("user.dir"), "pom.xml");
        assertEquals("application/xml", MimeType.guess(pom.getName()).toString());
        assertEquals("application/xml", MimeType.guess(pom).toString());
        assertEquals("application/xml", MimeType.guess("file://" + pom.getAbsolutePath()).toString());
    }
    
}
