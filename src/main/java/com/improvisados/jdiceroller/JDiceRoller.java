package com.improvisados.jdiceroller;

import java.util.Arrays;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Joaquin Martinez <juacom04@gmail.com>
 */
public class JDiceRoller {

    private Pattern validationPattern;
    public static final int KEEP_HIGHET = 1;
    public static final int KEEP_LOWER = -1;

    /**
     * Constructor of the class
     */
    public JDiceRoller() {
        validationPattern = Pattern.compile("\\d+[dD]\\d+([+-](\\d+[dD]\\d+|\\d+))*");
    }

    /**
     *
     * @param rollExp
     * @return A JSon reperesentation of the roll
     */
      public String  roll(String rollExp){
        String sRoll=rollExp;
        String out="";
        if (validationPattern.matcher(rollExp).matches())
        {
            
            Pattern splitter = Pattern.compile("([+-]?)(\\d+[dD]\\d+|\\d+)");
            Matcher m=splitter.matcher(rollExp);
            Matcher exp;
            int[] roll;
            int total=0;
            
            Pattern isRoll = Pattern.compile("([+-]?)((\\d+)[dD](\\d+))");
             int subtotal=0;
             String operation="";
             
             //While there are expressions
            while(m.find())
            {
                exp=isRoll.matcher(m.group());
                
                //if is a roll
                if(exp.matches())
                {
                    roll=roll(Integer.parseInt(exp.group(3)),Integer.parseInt(exp.group(4)));
                    subtotal=add(roll);
                    operation=exp.group(1);
                    sRoll=sRoll.replace(exp.group(2),toString(roll));
                }
                else
                {
                    subtotal=Integer.parseInt(m.group(2));
                    operation=m.group(1);
                }
                
                if(operation.equals(""))
                {
                    operation="+";
                }
                
                
                if(operation.equals("+"))
                {
                   total+=subtotal; 
                }
                else if(operation.equals("-"))
                {
                    total-=subtotal;
                }                    
            }
              out= "{'success':true,'expression':'"+rollExp+"','resultText':'" + sRoll + "',total:" + total + "}";   
        }
        else
        {
            out="{'success':false,'errorMsg':'"+rollExp+" is an invalid roll expression'}";  
        }
        
        return out;
    }

    public int[] roll(int ammount, int faces) {
        int[] ret = new int[ammount];
        Random rnd = new Random();
        for (int i = 0; i < ammount; i++) {
            ret[i] = rnd.nextInt(faces) + 1;
        }

        return ret;
    }

    public int[] roll(int ammount, int faces, int keep, int criterion) throws InvalidRollException {

        if (keep > 0) {
            if (ammount >= keep) {
                int[] ret = roll(ammount, faces);
                Arrays.sort(ret, 0, ret.length);
                int start = 0;
                int end = 0;

                if (criterion == JDiceRoller.KEEP_LOWER) {
                    start = 0;
                    end = keep;
                } else if (criterion == JDiceRoller.KEEP_HIGHET) {
                    start = ammount - keep;
                    end = ammount;
                }

                ret = Arrays.copyOfRange(ret, start, end);

                return ret;
            } else {
                throw new InvalidRollException("You must roll more dices than you want to keep");
            }
        } else {
            throw new InvalidRollException("You must keep at least 1 dice from this roll");
        }
    }

    public int add(int[] array) {
        int total = 0;
        for (int i = 0; i < array.length; i++) {
            total += array[i];
        }
        return total;
    }

    
    private String toString(int[] roll)
    {
        String out="[";
        
        for(int i=0;i<roll.length;i++)
        {
            if(i==0)
            {
                out+=roll[i];
            }
            else
            {
                out+=","+roll[i];
            }
        }
        out+="]";
        return out;
    }
}
