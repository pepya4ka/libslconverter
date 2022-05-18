package com.pepyachka.sample;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.ImportDeclaration;
import com.github.javaparser.symbolsolver.JavaSymbolSolver;
import com.github.javaparser.symbolsolver.resolution.typesolvers.CombinedTypeSolver;
import com.github.javaparser.symbolsolver.resolution.typesolvers.ReflectionTypeSolver;

import java.io.*;

public class AnalysisFromJavaFile {

    public static void main(String[] args) {
        CombinedTypeSolver combinedTypeSolver = new CombinedTypeSolver();
        combinedTypeSolver.add(new ReflectionTypeSolver());

        // Configure JavaParser to use type resolution
        JavaSymbolSolver symbolSolver = new JavaSymbolSolver(combinedTypeSolver);
        StaticJavaParser.getConfiguration().setSymbolResolver(symbolSolver);

        try (FileInputStream fileInputStream = new FileInputStream("src/main/resources/temp/Employee.java")) {
            CompilationUnit cu = StaticJavaParser.parse(fileInputStream);
            cu.findAll(ImportDeclaration.class).forEach(be -> {
                System.out.println(be.getName());
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
