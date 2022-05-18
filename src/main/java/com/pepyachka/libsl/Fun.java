package com.pepyachka.libsl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Fun {

    public static final String DEFAULT_RETURN_TYPE_NAME = "void";

    private final List<String> reservedStrings = new ArrayList<>(Arrays.asList(
            "url", "type", "include"
    ));

    private String name;
    private List<String> params;
    private String returnType;

    public Fun() {
        this.params = new ArrayList<>();
    }

    public Fun(String name) {
        this.name = reservedStrings.stream().anyMatch(name::startsWith) ? String.format("`%s`", name) : name;
        this.params = new ArrayList<>();
        this.returnType = DEFAULT_RETURN_TYPE_NAME;
    }

    public Fun(String name, String returnType) {
        this.name = reservedStrings.contains(name) ? String.format("`%s`", name) : name;
        this.returnType = returnType;
        this.params = new ArrayList<>();
    }

    public Fun(String name, List<String> params, String returnType) {
        this.name = reservedStrings.contains(name) ? String.format("`%s`", name) : name;
        this.params = params;
        this.returnType = returnType;
    }

    public String getStringParams() {
        return IntStream.range(0, params.size())
                .mapToObj(i -> String.format("arg%d: %s", i, params.get(i)))
                .collect(Collectors.joining(", "));
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = reservedStrings.contains(name) ? String.format("`%s`", name) : name;
    }

    public List<String> getParams() {
        return params;
    }

    public void setParams(List<String> params) {
        this.params = params;
    }

    public String getReturnType() {
        return returnType;
    }

    public void setReturnType(String returnType) {
        this.returnType = returnType;
    }

    public boolean addParams(String param) {
        return params.add(param);
    }

    @Override
    public String toString() {
        return String.format("fun %s(%s): %s;", getName(), getStringParams(), getReturnType());
    }
}
