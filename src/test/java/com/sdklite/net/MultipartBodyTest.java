package com.sdklite.net;

import java.io.IOException;
import java.io.InputStreamReader;

import org.junit.Test;

import com.sdklite.io.IOUtil;
import com.sdklite.net.http.MultipartBody;

public class MultipartBodyTest {

    @Test
    public void multipartBodyShouldBeWrittenProperly() throws IOException {
        final MultipartBody multipart = new MultipartBody.Builder()
                .addPart("groupId", Build.GROUP_ID)
                .addPart("artifactId", Build.ARTIFACT_ID)
                .addPart("version", Build.VERSION)
                .build();
        System.out.println(IOUtil.readFully(new InputStreamReader(multipart.getContent())));
    }
    
}
