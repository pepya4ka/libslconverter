package com.pepyachka.jar;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarInputStream;
import java.util.stream.Stream;

public class JarInputStreamPoly extends JarInputStream {

    public JarInputStreamPoly(InputStream in) throws IOException {
        super(in);
    }

    public Stream<JarEntry> getJarEntries() throws IOException {
        List<JarEntry> jarEntries = new ArrayList<>();
        JarEntry nextJarEntry = getNextJarEntry();
        while (nextJarEntry!= null) {
            jarEntries.add(nextJarEntry);
            nextJarEntry = getNextJarEntry();
        }
        return jarEntries.stream();
    }
}
