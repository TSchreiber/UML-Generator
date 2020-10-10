package uml;

public class BoxDrawer {
    
    //   
    public static final String HORIZONTAL           = "\u2500"; 
    public static final String VERTICAL             = "\u2502"; 
    public static final String DOWN_AND_RIGHT       = "\u250c"; 
    public static final String DOWN_AND_LEFT        = "\u2510"; 
    public static final String UP_AND_RIGHT         = "\u2514"; 
    public static final String UP_AND_LEFT          = "\u2518"; 
    public static final String VERTICAL_AND_RIGHT   = "\u251c"; 
    public static final String VERTICAL_AND_LEFT    = "\u2524"; 
    public static final String HORIZONTAL_AND_DOWN  = "\u252c"; 
    public static final String HORIZONTAL_AND_UP    = "\u2534"; 
    
    public static String toString(UML uml) {
        String[] fields = uml.getFields();
        String[] methods = uml.getMethods();
        
        int max = 0;
        for (String s : fields)
            if (s.length() > max) 
                max = s.length();
        for (String s : methods)
            if (s.length() > max) 
                max = s.length();
        if (uml.getName().length() > max)
            max = uml.getName().length();
        int width = max;
        
        String title = centerString(uml.getName(), ' ', width);
        
        String out = "";
        out += DOWN_AND_RIGHT + HORIZONTAL.repeat(width) + DOWN_AND_LEFT + "\n";
        out += VERTICAL + title + VERTICAL + "\n";
        out += VERTICAL_AND_RIGHT + HORIZONTAL.repeat(width) + VERTICAL_AND_LEFT + "\n";
        
        for (String s : fields)
            out += VERTICAL + padEnd(s, ' ', width) + VERTICAL + "\n";
        out += VERTICAL_AND_RIGHT + HORIZONTAL.repeat(width) + VERTICAL_AND_LEFT + "\n";
        for (String s : methods) 
            out += VERTICAL + padEnd(s, ' ', width) + VERTICAL + "\n";
        out += UP_AND_RIGHT + HORIZONTAL.repeat(width) + UP_AND_LEFT + "\n";
        
        return out;
    }
    
    private static String centerString(String str, char c, int width) {
        String out = "";
        int paddingLeft = (width - str.length()) / 2;
        int paddingRight = width - str.length() - paddingLeft;
        for (int i=0; i<paddingLeft; i++)
            out += c;
        out += str;
        for (int i=0; i<paddingRight; i++)
            out += c;
        return out;
    }
    
    private static String padEnd(String str, char c, int width) {
        String out = str;
        int pad = width - str.length();
        for (int i=0; i<pad; i++)
            out += c;
        return out;
    }
    
}