package uml;

import java.util.*;
import java.util.regex.*;
import java.io.*;
import java.nio.file.*;
import java.lang.reflect.*;

public class UML {

    private final Class C;

    public UML(Class C) {
        this.C = C;
    }
    
    public String getName() {
        return C.getName();
    }
    
    public String[] getFields() {
        Field[] instanceVariables = getInstanceVariables();
        String[] out = new String[instanceVariables.length];
        for (int i=0; i<out.length; i++) {
            String s = "";
            int mod = instanceVariables[i].getModifiers();
            s += Modifier.isPublic(mod) ? "+" :
                 Modifier.isPrivate(mod) ? "-" : 
                 Modifier.isProtected(mod) ? "#" : "~";
            s += instanceVariables[i].getName();
            s += ": ";
            s += simplify(instanceVariables[i].getType());
            out[i] = s;
        }
        return out;
    }
    
    private Field[] getInstanceVariables() {
        ArrayList<Field> arr = new ArrayList<Field>();
        for (Field field : C.getDeclaredFields()) {
            // Don't include class constants (static final)
            if (!Modifier.isFinal(field.getModifiers())
             && !Modifier.isStatic(field.getModifiers()))
                arr.add(field);
        }
        return arr.toArray(new Field[arr.size()]);
    }
    
    public String[] getMethods() {
        Method[] methods = C.getDeclaredMethods();
        String[] out = new String[methods.length];
        for (int i=0; i<out.length; i++) {
            String s = "";
            int mod = methods[i].getModifiers();
            s += Modifier.isPublic(mod) ? "+" :
                 Modifier.isPrivate(mod) ? "-" : " ";
            s += methods[i].getName();
            s += "(";
            for (Class c : methods[i].getParameterTypes())
                s += simplify(c);
            s += ")";
            s += ": ";
            s += simplify(methods[i].getReturnType());
            out[i] = s;
        }
        return out;
    }
    
    @Override
    public String toString() {
        return BoxDrawer.toString(this);
    }
    
    private static String simplify(Type type) {
        String name = type.toString().replace("class ", "");
        
        int count = 0;
        while (name.charAt(count) == '[')
            count++;
        name = name.replaceAll("[\\[\\];]", "");
        name = name.substring(name.lastIndexOf(".") + 1);
        // Primative values have different array names, so those need to be corrected
        if (count > 0) {
            if (name.equals("I"))
                name = "int";
            else if (name.equals("J"))
                name = "long";
            else if (name.equals("D"))
                name = "double";
            else if (name.equals("F"))
                name = "float";
            else if (name.equals("C"))
                name = "char";
            else if (name.equals("B"))
                name = "byte";
            else if (name.equals("S"))
                name = "short";
            else if (name.equals("Z"))
                name = "boolean";
            
        }
        
        name = name + "[]".repeat(count);
        return name;
    }
    
    private static String capitolize(String str) {
        return Character.toUpperCase(str.charAt(0)) + str.substring(1);
    }
    
    private static String getHeader(Field field) {
        String out = "";
        out += Modifier.toString(field.getModifiers()) + " ";
        out += simplify(field.getType()) + " ";
        out += field.getName();
        return out.trim();
    }

}