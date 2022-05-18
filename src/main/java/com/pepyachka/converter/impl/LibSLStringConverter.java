package com.pepyachka.converter.impl;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.ImportDeclaration;
import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.PackageDeclaration;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.expr.Expression;
import com.github.javaparser.ast.stmt.Statement;
import com.github.javaparser.printer.YamlPrinter;
import com.github.javaparser.symbolsolver.JavaSymbolSolver;
import com.github.javaparser.symbolsolver.resolution.typesolvers.CombinedTypeSolver;
import com.github.javaparser.symbolsolver.resolution.typesolvers.ReflectionTypeSolver;
import com.pepyachka.converter.LibSLConverter;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.util.List;
import java.util.stream.Collectors;

public class LibSLStringConverter extends LibSLConverter {

    public LibSLStringConverter(String source) {
        super(new ByteArrayInputStream(source.getBytes()));
    }

    @Override
    public void createLSL() {
        // Set up a minimal type solver that only looks at the classes used to run this sample.
        CombinedTypeSolver combinedTypeSolver = new CombinedTypeSolver();
        combinedTypeSolver.add(new ReflectionTypeSolver());

        // Configure JavaParser to use type resolution
        JavaSymbolSolver symbolSolver = new JavaSymbolSolver(combinedTypeSolver);
        StaticJavaParser.getConfiguration().setSymbolResolver(symbolSolver);

        CompilationUnit cu = StaticJavaParser.parse(source);

        YamlPrinter yamlPrinter = new YamlPrinter(true);
        System.out.println("yamlPrinter.output(cu) = " + yamlPrinter.output(cu));

        MethodDeclaration methodDeclaration = cu.getType(0)
                .getMember(0)
                .toMethodDeclaration()
                .orElseThrow(IllegalArgumentException::new);
        List<String> collect1 = methodDeclaration.getBody()
                .orElseThrow(IllegalArgumentException::new)
                .getStatements()
                .stream()
                .filter(Statement::isExpressionStmt)
                .filter(statement -> {
                    List<String> collect = methodDeclaration.getParameters().stream()
                            .map(parameter -> parameter.getName().asString())
                            .collect(Collectors.toList());
                    Expression expression = statement.toExpressionStmt()
                            .orElseThrow(IllegalArgumentException::new)
                            .getExpression();
                    if (expression.isAssignExpr()) {
                        return collect.contains(expression.asAssignExpr()
                                .getTarget()
                                .asNameExpr()
                                .getName()
                                .asString());
                    } else if (expression.isMethodCallExpr()) {
                        return collect.contains(expression.asMethodCallExpr()
                                .getScope()
                                .orElseThrow(IllegalArgumentException::new)
                                .asNameExpr()
                                .getName()
                                .asString());
                    }
                    return false;
                })
                .map(
                        statement -> statement.toExpressionStmt()
                                .orElseThrow(IllegalArgumentException::new)
                                .getExpression()
                )
                .map(expression -> {
                    if (expression.isAssignExpr()) {
                        return expression.asAssignExpr()
                                .getTarget()
                                .asNameExpr()
                                .getName()
                                .asString();
                    } else if (expression.isMethodCallExpr()) {
                        return expression.asMethodCallExpr()
                                .getScope()
                                .orElseThrow(IllegalArgumentException::new)
                                .asNameExpr()
                                .getName()
                                .asString();
                    }
                    return "";
                })
                .collect(Collectors.toList());

        collect1.forEach(System.out::println);

//        System.out.printf("package %s %n", getPackageDeclarationInfo(cu));
//        System.out.printf("%s %n", getImportDeclarationInfo(cu));
//        System.out.printf("types {%n%s%n}", getTypeDeclarationInfo(cu));
    }

    @Override
    public File getLSL() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void printLsl() {

    }

    private String getPackageDeclarationInfo(CompilationUnit cu) {
        return cu.findAll(PackageDeclaration.class).stream().map(packageDeclaration -> packageDeclaration.getName().toString()).reduce("", (subtotal, element) -> String.format("%s%n%s", subtotal, element)).trim();
    }

    private String getImportDeclarationInfo(CompilationUnit cu) {
        return cu.findAll(ImportDeclaration.class).stream().map(Node::toString).reduce("", (subtotal, element) -> String.format("%s%s", subtotal, element)).trim();
    }

    private String getTypeDeclarationInfo(CompilationUnit cu) {//TODO
        return cu.findAll(ClassOrInterfaceDeclaration.class).stream().map(classDeclaration -> classDeclaration.getTypeParameters().stream().map(typeParameter -> typeParameter.getName().asString()).reduce("", (subtotal, element) -> String.format("%s%s", subtotal, element))).reduce("", (subtotal, element) -> String.format("%s%s", subtotal, element)).trim();
    }
}
