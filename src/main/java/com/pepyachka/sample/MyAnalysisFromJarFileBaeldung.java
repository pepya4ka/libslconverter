package com.pepyachka.sample;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class MyAnalysisFromJarFileBaeldung {

    public static final String JAR_PATH = "/Users/pepyachka/IdeaProjects/javasymbolsolver-maven-sample/src/main/resources/SimpleMavenProjectForCreatingJarFile-1.0-SNAPSHOT.jar";

    public static void main(String[] args) throws IOException, ClassNotFoundException {

        MyAnalysisFromJarFileBaeldung myAnalysisFromJarFileBaeldung = new MyAnalysisFromJarFileBaeldung();
        File file = new File(JAR_PATH);
        Set<Class> classesFromJarFile = myAnalysisFromJarFileBaeldung.getClassesFromJarFile(file);
        classesFromJarFile.forEach(clazz -> {
            System.out.println("clazz.toGenericString() = " + clazz.toGenericString());
            System.out.printf("class %s:\n", clazz.getSimpleName());
            Arrays.stream(clazz.getDeclaredMethods()).forEach(clazzMethod -> {
                System.out.println("method " + clazzMethod.getName());
            });
        });
    }

    public Set<String> getClassNamesFromJarFile(File givenFile) throws IOException {
        Set<String> classNames = new HashSet<>();
        try (JarFile jarFile = new JarFile(givenFile)) {
            Enumeration<JarEntry> e = jarFile.entries();
            while (e.hasMoreElements()) {
                JarEntry jarEntry = e.nextElement();
                if (jarEntry.getName().endsWith(".class")) {
                    String className = jarEntry.getName().replace("/", ".").replace(".class", "");
                    classNames.add(className);
                }
            }
            return classNames;
        }
    }

    public Set<Class> getClassesFromJarFile(File jarFile) throws IOException, ClassNotFoundException {
        Set<String> classNames = getClassNamesFromJarFile(jarFile);
        Set<Class> classes = new HashSet<>(classNames.size());
        try (URLClassLoader cl = URLClassLoader.newInstance(new URL[]{new URL("jar:file:" + jarFile + "!/")})) {
            for (String name : classNames) {
                Class clazz = cl.loadClass(name);
                classes.add(clazz);
            }
        }
        return classes;
    }
}
