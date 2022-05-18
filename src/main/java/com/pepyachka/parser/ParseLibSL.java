package com.pepyachka.parser;

import org.jetbrains.research.libsl.LibSL;
import org.jetbrains.research.libsl.asg.Library;

import java.io.File;

public class ParseLibSL {

    private static final String PATH_LIBSL_FILE = "src/main/resources/okhttpLibSL.lsl";
    private static final String PATH_LIBSL_FILE_OKHTTP3 = "src/main/resources/okhttp3.lsl";

    public static void main(String[] args) {
        File lslFile = new File(PATH_LIBSL_FILE);
        LibSL libSL = new LibSL(PATH_LIBSL_FILE);
        Library library = libSL.loadFromFile(lslFile);

        System.out.println(library);
    }

}
