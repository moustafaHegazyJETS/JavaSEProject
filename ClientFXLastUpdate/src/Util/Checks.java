
package Util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Checks {
    
    private final static  Pattern EMAIL_PATTERN = Pattern.compile("^[_A-Za-z0-9-.]+([A-Za-z0-9-_.]+)*@[A-Za-z0-9-]+(.[A-Za-z0-9-]+)*(.[A-Za-z]{2,})$");
    private final static  Pattern USERNAME_PATTERN = Pattern.compile("[a-zA-Z0-9_.]{3,20}");
    private final static  Pattern NAME_PATTERN = Pattern.compile("[a-zA-Z]{3,10}");
    private final static  Pattern IP_PATTERN = Pattern.compile("^(([0-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5])\\.){3}([0-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5])$");
   
    private static Matcher match;

    private Checks() {
    }
    
    
  
   
    public static boolean checkEmail(String email){
         match = EMAIL_PATTERN.matcher(email);
         return !email.trim().equals("") && match.matches();
    }
    
    
    public static boolean checkUserName(String username){
        match = USERNAME_PATTERN.matcher(username);
        return   checkStringEmpty(username) && match.matches();
    }
    
    
    
    public static boolean checkName(String name){
        match = NAME_PATTERN.matcher(name);
        return   checkStringEmpty(name) && match.matches();
    }
    
    
    public static boolean checkIP(String ip){
        match = IP_PATTERN.matcher(ip);
        return match.matches();
    }
    
    
    
    public static boolean checkStringEmpty(String str ){
         return !(str == null || str.trim().length() == 0 || "".equals(str.trim())) ;
    }
    
    
   
    public static boolean checkStringLength(String str , int min , int max){
        return !(str.length() > max || str.length() < min );
    }
}
