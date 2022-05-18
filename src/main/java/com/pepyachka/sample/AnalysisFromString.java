package com.pepyachka.sample;

import com.pepyachka.converter.LibSLConverter;
import com.pepyachka.converter.impl.LibSLStringConverter;

public class AnalysisFromString {

    public static void main(String[] args) {
        String code = "package com.pepyachka.sample; import java.io.ByteArrayInputStream; import java.io.File; class A { int y; public A() {this.y = 3;} public void setY(int y) {this.y = y;} public int getY() {return y;} } class X { int x(A a) { a.setY(2); int y = a.getY(); return y; } }";
        String codeWithoutA = "package com.pepyachka.sample; import java.io.ByteArrayInputStream; import java.io.File; class X { int x(A a) { a.setY(2); int y = a.getY(); return y; } }";
        String method = "class LibSLTempClass { int x(A a) { a.setY(2); int y = 0; y = a.getY(); return y; } }";

        LibSLConverter libSLConverter = new LibSLStringConverter(method);
        libSLConverter.createLSL();
    }
}