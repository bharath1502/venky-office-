package com.chatak.pg.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*(     # Start of group
    (?=.*\d)    #   must contains one digit from 0-9
    (?=.*[a-z])   #   must contains one lowercase characters
    (?=.*[A-Z])   #   must contains one uppercase characters
    (?=.*[@#$%])    #   must contains one special symbols in the list "@#$%"
                .   #     match anything with previous condition checking
                  {6,20}  #        length at least 8 characters and maximum of 20 
  )     # End of group*/
public class PasswordValidator {
  
  private static final String PASS_WORD_PATTERN = "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%]).{8,20})";
  
    private static Pattern pattern = Pattern.compile(PASS_WORD_PATTERN);
    private static Matcher matcher;
          
    private PasswordValidator(){
      pattern = Pattern.compile(PASS_WORD_PATTERN);
    }
    
    /**
     * Validate password with regular expression
     * @param password password for validation
     * @return true valid password, false invalid password
     */
    public static boolean validate(final String password){
      
      matcher = pattern.matcher(password);
      return matcher.matches();
            
  }

}
