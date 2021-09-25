/*-------------------------------------------------------------------------
GWU CSCI1112 Spring 2020
author: James Taylor

This file contains utility methods that can be useful in a number of
circumstances.  It includes methods for cleaning strings, generating 
standard test strings, and image/pixel scaling and validation operations
-------------------------------------------------------------------------*/

public class Utilities {

    /// Trims out all characters that are not alphas and uppercases the 
    /// string.  This is not ideal because we cannot have numeric 
    /// information in the string; however, it is good enough for this 
    /// simple approach.
    /// @param s the string to clean
    /// @return the cleaned string 
    public static String clean(String s) {
        return s.replaceAll("[^a-zA-Z]","").toUpperCase();
    }

    public static String alphabet() {
        return "abcdefghijklmnopqrstuvwxyz";
    }

    /// Generates the quick brown fox text.  It can be used to test for all
    /// alphas
    /// @return a string containing the quick brown text
    public static String pangram() {
        return "The quick brown fox jumps over the lazy dog";
    }

    /// Generates the standard lorem ipsum text.  It can be used to test 
    /// large blocks of text
    /// @return a string containing lorem ipsum text
    public static String loremipsum() {
        return "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.";
    }

    /// Scales an image using a pixel level shallow copy.  Primarily used
    /// for viewing the image at a larger scale so that individual pixels
    /// can be easily seen
    /// @param px an array containing the pixel data
    /// @param factor the multiple used to scale the image
    /// @return an array containing the scaled image
    public static int[][][] stretch(int[][][] px, int factor) {
        int[][][] pxout = new int[px.length * factor][px[0].length * factor][];
        for (int i=0; i < px.length; i++) {
            for (int j=0; j < px[0].length; j++) {
                for(int ii=0; ii < factor; ii++) {
                    for(int jj=0; jj < factor; jj++) {
                        pxout[i*factor+ii][j*factor+jj] = px[i][j];
                    }
                }
            }
        }
        return pxout; 
    }

    /// Validation utility that checks if a pixel is valid
    /// @param px an array of four intensity values
    /// @return false if the pixel is null, the wrong size, or the values
    ///         are outside the legal intensity ranges for a 32-bit color
    public static boolean isPixelValid( int[] px ) {
        if(px == null) return false;
        if(px.length != 4) return false;
        if(px[0] < 0 || px[1] < 0 || px[2] < 0 || px[3] < 0 ) return false;
        if(px[0] > 255 || px[1] > 255 || px[2] > 255 || px[3] > 255 ) return false;

        return true;
    }

}
