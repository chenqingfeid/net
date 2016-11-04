package com.sdklite.net;

import static org.junit.Assert.*;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URLEncoder;

import org.junit.Test;

import com.sdklite.io.IOUtil;
import com.sdklite.net.http.FormBody;

public class FormBodyTest {

    @Test
    public void formBodyWith1FieldShouldBeWrittenProperly() throws IOException {
        final FormBody form = new FormBody.Builder()
                .add("groupId", Build.GROUP_ID)
                .build();
        final StringBuilder builder = new StringBuilder().append(URLEncoder.encode("groupId", "UTF-8")).append("=").append(URLEncoder.encode(Build.GROUP_ID, "UTF-8"));
        assertEquals(builder.toString(), IOUtil.readFully(new InputStreamReader(form.getContent())));
    }

    @Test
    public void formBodyWith2FieldsShouldBeWrittenProperly() throws IOException {
        final FormBody form = new FormBody.Builder()
                .add("groupId", Build.GROUP_ID)
                .add("artifactId", Build.ARTIFACT_ID)
                .build();
        final StringBuilder builder = new StringBuilder();
        builder.append(URLEncoder.encode("groupId", "UTF-8")).append("=").append(URLEncoder.encode(Build.GROUP_ID, "UTF-8")).append("&")
               .append(URLEncoder.encode("artifactId", "UTF-8")).append("=").append(URLEncoder.encode(Build.ARTIFACT_ID, "UTF-8"));
        assertEquals(builder.toString(), IOUtil.readFully(new InputStreamReader(form.getContent())));
    }

    @Test
    public void formBodyWith3FieldsShouldBeWrittenProperly() throws IOException {
        final FormBody form = new FormBody.Builder()
                .add("groupId", Build.GROUP_ID)
                .add("artifactId", Build.ARTIFACT_ID)
                .add("version", Build.VERSION)
                .build();
        final StringBuilder builder = new StringBuilder();
        builder.append(URLEncoder.encode("groupId", "UTF-8")).append("=").append(URLEncoder.encode(Build.GROUP_ID, "UTF-8")).append("&")
               .append(URLEncoder.encode("artifactId", "UTF-8")).append("=").append(URLEncoder.encode(Build.ARTIFACT_ID, "UTF-8")).append("&")
               .append(URLEncoder.encode("version", "UTF-8")).append("=").append(URLEncoder.encode(Build.VERSION, "UTF-8"));
        assertEquals(builder.toString(), IOUtil.readFully(new InputStreamReader(form.getContent())));
    }

    @Test
    public void formBodyWithMultipleValuesShouldBeWrittenProperly() throws IOException {
        final FormBody form = new FormBody.Builder()
                .add("version", "1.0", "2.0", "3.0", "4.0")
                .build();
        assertEquals("version=1.0&version=2.0&version=3.0&version=4.0", IOUtil.readFully(new InputStreamReader(form.getContent())));
    }
    
}
