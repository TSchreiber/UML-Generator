package uml;

import java.lang.reflect.*;
import java.io.*;
import java.net.*;

public class Main {
    
    public static void main(String[] args) throws ClassNotFoundException, IOException {
        File dir = new File(".");
        URLClassLoader classLoader = new URLClassLoader(
            new URL[]{ dir.toURI().toURL() });
        
        String className = args[0];
        if (className.endsWith(".class"))
            className = className.substring(0, className.lastIndexOf(".class"));
        
        Class C = Class.forName(className, false, classLoader);
        System.out.println( new UML(C) );
    }
    
}