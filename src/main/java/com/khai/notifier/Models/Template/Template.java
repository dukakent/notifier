package com.khai.notifier.Models.Template;

import com.khai.notifier.Managers.File.File;

import java.io.Reader;
import java.lang.reflect.Method;
import java.util.ArrayList;

/**
 * Created by inokentii on 17.12.16.
 */
public class Template {

    private String sourceTemplate = "";
    private String compiledTemplate = "";
    private ArrayList<String> properties;

    public Template() {}

    public Template(String template) {
        this.sourceTemplate = template;
        this.getProperties();
    }

    public Template(Reader file) {
        this.sourceTemplate = File.read(file);
        this.getProperties();
    }

    private void getProperties() {
        String[] parts = this.sourceTemplate.split("\\$\\{");

        this.properties = new ArrayList<String>();

        for (int i = 1; i < parts.length; i++) {
            String property = parts[i].split("}")[0];
            this.properties.add(property);
        }
    }

    private String getterName(String prop) {
        return "get" + prop.substring(0, 1).toUpperCase() + prop.substring(1);
    }


    public String compile(Object object) {
        Class<?> cl = object.getClass();
        Method method;
        String res = this.sourceTemplate;
        String value = "";

        for (String prop: this.properties) {
            try {
                String getterName = this.getterName(prop);
                method = cl.getMethod(getterName, null);
                value = (String)method.invoke(object, null);
                res = res.replace("${" + prop + "}", value);

            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }

        this.compiledTemplate = res;
        return this.compiledTemplate;
    }

}
