/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utilities;

/**
 *
 * @author Rajesh
 */
public class CodeResourceUtility {  
    public String getFileName(  Class abc ){
        String name = "" ;        
        Class<?> enclosingClass = getClass().getEnclosingClass ();
        if (enclosingClass != null) {
            name = enclosingClass.getName();
          System.out.println(enclosingClass.getName());
        } else {
            name = getClass().getName() ;
          System.out.println(getClass().getName());
        }      
       return name ;
    }  
    
    public int getLineNumber() {
    boolean thisOne = false;
    int thisOneCountDown = 1;
    StackTraceElement[] elements = Thread.currentThread().getStackTrace();
    for(StackTraceElement element : elements) {
        String methodName = element.getMethodName();
        int lineNum = element.getLineNumber();
        if(thisOne && (thisOneCountDown == 0)) {
            return lineNum;
        } else if(thisOne) {
            thisOneCountDown--;
        }
        if(methodName.equals("___8drrd3148796d_Xaf")) {
            thisOne = true;
        }
    }
    return -1;
}
}
