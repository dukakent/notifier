package com.khai.notifier.Models.Template;

import com.khai.notifier.Managers.File.File;

import java.io.Reader;
import java.lang.reflect.Method;
import java.util.ArrayList;

/**
 * Template responsible for pasting data into specific template
 */
public class Template {

    private String sourceTemplate = "";
    private ArrayList<String> properties;

    /**
     * @param file File Reader Stream where source template is located
     */
    public Template(Reader file) {
        this.sourceTemplate = File.read(file);
        this.getProperties();
    }

    /**
     * gets property names from the source template
     */
    private void getProperties() {
        String[] parts = this.sourceTemplate.split("\\$\\{");

        this.properties = new ArrayList<String>();

        for (int i = 1; i < parts.length; i++) {
            String property = parts[i].split("}")[0];
            this.properties.add(property);
        }
    }

    /**
     * Converts property to getter name (capitalizes property and prepend "get")
     * @param prop property from the source template
     * @return getter name which calling returns property value
     */
    private String getterName(String prop) {
        return "get" + prop.substring(0, 1).toUpperCase() + prop.substring(1);
    }


    /**
     * Creates string of compiled template with pasted values instead of properties
     * @param object Object where values are located
     * @return string with compiled template
     */
    public String compile(Object object) {
        Class<?> cl = object.getClass();
        Method method;
        String res = this.sourceTemplate;
        String value;

        for (String prop : this.properties) {
            try {
                String getterName = this.getterName(prop);
                method = cl.getMethod(getterName, null);
                value = (String) method.invoke(object, null);
                res = res.replace("${" + prop + "}", value);

            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }

        return res;
    }

}
