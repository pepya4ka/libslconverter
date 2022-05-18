package com.pepyachka.converter;

import java.io.File;
import java.io.InputStream;

public abstract class LibSLConverter {

    protected InputStream source;

    protected LibSLConverter(InputStream source) {
        this.source = source;
    }

    public abstract void createLSL();

    public abstract File getLSL();

    public abstract void printLsl();
}
