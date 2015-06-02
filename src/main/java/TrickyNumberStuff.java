/*
 * The MIT License
 *
 * Copyright 2014 DJ Spiess and DeegeU.com.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

/**
 * Demonstration of different issues you may run into while working with 
 * numbers in Java. This code accompanies the Tricky Java Number Stuff video
 * on http://www.deegeu.com/videos/tricky-java-number-stuff/
 * 
 * @author dspiess
 */
public class TrickyNumberStuff {

    /**
     * The main application and entry point
     * 
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        TrickyNumberStuff app = new TrickyNumberStuff();
        app.runExamples();
    }
    
    /**
     * Runs all the examples from the Tricky Java Number Stuff video.
     */
    final public void runExamples() {
        divideByZero();
        comparingInfinity();
        dividingZeroByZero();
        comparingNaNs();
        minimumValues();
        byteLiterals();
        byteUnaryAddition();
        addOneToByte();
        integerAutoboxing();
    }
    
    /**
     * Examples of code where divide by zero has different results depending on
     * the data type you are using.
     */
    private void divideByZero() {
        System.out.println("------------- running divideByZero---------------");
        
        // The following line will cause a runtime exception. You cannot divide
        // integers by zero.
        // System.out.println(1 / 0);
        
        // You can divide floats and doubles by zero.
        System.out.println("1.0 / 0.0 = " + (1.0 / 0.0));
    }
    
    /**
     * Comparing infinity is ok.
     */
    private void comparingInfinity() {
        System.out.println("------------- running comparingInfinity ---------");
        
        System.out.println("1.0 / 0.0 == Infinity : " 
                + ((1.0 / 0.0) == Double.POSITIVE_INFINITY));
        System.out.println("1.0 / 0.0 == (- Infinity) : " 
                + ((1.0 / 0.0) == Double.NEGATIVE_INFINITY));
    }
    
    /**
     * It's safe to divide zero by zero. It produces a Double.NaN
     */
    private void dividingZeroByZero() {
        System.out.println("------------ running dividingZeroByZero ---------");
        System.out.println("0.0 / 0.0 = " + (0.0 / 0.0));
    }
    
    /**
     * You cannot compare NaNs. If you think about it, that makes sense. If you
     * compared purple (not a number) and banana (not a number), are they equal?
     * To test for NaNs, you need to use the Double.isNaN method.
     */
    private void comparingNaNs() {
        System.out.println("------------- running comparingNaNs -------------");
        System.out.println("0.0 / 0.0 == 0.0 / 0.0 : " 
                + ((0.0 / 0.0) == (0.0 / 0.0)));
        System.out.println("0.0 / 0.0 == NaN : " 
                + ((0.0 / 0.0) == Double.NaN));
        System.out.println("NaN == NaN : " 
                + (Double.NaN == Double.NaN));
        System.out.println("isNaN(NaN) = " 
                + (Double.isNaN(0.0 / 0.0)));
    }
    
    /**
     * Integers and floating point numbers have minimum values that differ in
     * sign.
     */
    private void minimumValues() {
        System.out.println("------------- running minimumValues -------------");
        
        System.out.println("Minimum integer = " + Math.min(Integer.MIN_VALUE, 0));
        System.out.println("Minimum float = " + Math.min(Float.MIN_VALUE, 0.0f));
        System.out.println("Minimum double = " + Math.min(Double.MIN_VALUE, 0.0d));
    }
    
    /**
     * Byte literals are not 2's complement numbers. If you need a negative byte
     * as a binary literal, you need to use the negative sign operator.
     */
    private void byteLiterals() {
        System.out.println("------------- running byteLiterals --------------");
        
        // this will not compile. Literals are not 2's complement numbers,
        // despite that's what Java stores.
        //byte b = 0b11111111;
        
        // This is how you define negative 1 as bits
        byte b = -0b00000001;
        System.out.println("byte (-1) = " + b);
    }
    
    /**
     * Not all byte increments are equal.  Addition requires a cast, while
     * unary operators do not.
     */
    private void addOneToByte() {
        System.out.println("------------- running addOneToByte --------------");
        
        byte b = 0; 
        
        b++;
        System.out.println("byte (1) = " + b);
        
        b+=1;
        System.out.println("byte (2) = " + b);
        
        // This line will not compile. The reason is the 1 and the addition cause
        // a widening conversion to an integer.
        //b = b + 1;
        
        // This is how you'd add one using the addition operator.
        b = (byte) (b + 1);
        System.out.println("byte (3) = " + b);
    }
    
    /**
     * You can use the unary operators to add very large numbers to a byte without
     * problem. The answer you get is congruent modulo to the result.
     */
    private void byteUnaryAddition() {
        System.out.println("------------- running byteUnaryAddition ---------"); 
        byte b = 0; 
       
        // 134 is a short at best. It does not fit into a byte, but we can still
        // use it in unary operations. It prints -122.
        b+=134;
        System.out.println("byte = " + b);
       
        // We can even do numbers way too large for a byte.
        b=0;
        b+=50000;
        System.out.println("byte = " + b);
    }
    
    /**
     * Java caches Integer class instances that are less than a number that 
     * fits into a byte (-128 to 127). So instance will be equal.  Comparing 
     * classes this way is very bad, and the fact you can get the result you 
     * think you're expecting is even worse.
     */
    private void integerAutoboxing() {
        System.out.println("------------- running integerAutoboxing ---------");
        
        Integer a = 1;
        Integer b = 1;
        
        System.out.println("Testing a == b when they equal 1: " + (a == b));
        
        a = 128;
        b = 128;
        System.out.println("Testing a == b when they equal 128: " + (a == b));
    }
}
