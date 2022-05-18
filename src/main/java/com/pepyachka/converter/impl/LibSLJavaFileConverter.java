package com.pepyachka.converter.impl;

import com.pepyachka.converter.LibSLConverter;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class LibSLJavaFileConverter extends LibSLConverter {

    public LibSLJavaFileConverter(String filePath) throws FileNotFoundException {
        super(new FileInputStream(filePath));
    }

    @Override
    public void createLSL() {

    }

    @Override
    public File getLSL() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void printLsl() {

    }
}
