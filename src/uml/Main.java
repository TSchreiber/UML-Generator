package uml;

import java.lang.reflect.*;
import java.io.*;
import java.net.*;

public class Main {
    
    private static File classPath = new File(".");
    
    public static void main(String[] args) throws ClassNotFoundException, IOException {
        for (int i=0; i<args.length; i++) {
            if (args[i].equals("-cp")) {
                classPath = new File(args[++i]);
            }
            else {
                File f = new File(args[i]);
                if (f.exists()) {
                    if (f.isDirectory()) {
                        searchDir(f);
                    }
                    else if (f.getName().endsWith(".class")){
                        loadAndPrintUML(f);
                    }
                }
            }
        }
    }
    
    private static void searchDir(File dir) throws ClassNotFoundException, MalformedURLException {
        for (File f : dir.listFiles()) {
            if (f.isDirectory()) {
                searchDir(f);
            }
            else if (f.getName().endsWith(".class")){
                loadAndPrintUML(f);
            }
        }
    }
    
    private static void loadAndPrintUML(File classFile) throws ClassNotFoundException, MalformedURLException {
        URLClassLoader classLoader = new URLClassLoader(
            new URL[]{ classPath.toURI().toURL() });
        
        
        String className = classFile.getPath();
        // Remove the overlap in the paths
        className = className.replace( classPath.getPath() + "\\", "" );
        // Remove the .class from the end
        if (className.endsWith(".class"))
            className = className.substring(0, className.length() - 6);
        // Change '\' to '.' to make a valid class name
        className = className.replaceAll("\\\\", ".");
        
        System.out.println(
            new UML( Class.forName(className, false, classLoader) ));
    }
    
}