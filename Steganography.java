/*--------------------------------------------------------------------------
GWU CSCI1112 Spring 2021
author: Lindsey Cicero

This class encapsulates the logic necessary to perform simple steganography 
cyphering.
--------------------------------------------------------------------------*/

public class Steganography {

    //--------------------------------------------------------------------- 
    // Base Problems
    //--------------------------------------------------------------------- 
    /// Performs a deep copy of the input pixels and returns the copy
    /// @param px the pixels from an image to copy
    /// @return the deep copy of the pixels that were copied
    public static int[][][] copy(int[][][] px) {
        
	    //creates new 3d array with the demensions of px
	    int[][][] dCopy= new int[px.length][px[0].length][4];

	    //loops through and copies each pixel value from px to dCopy
	    for(int i=0;i<px.length;i++){
		    for(int j=0;j<px[0].length;j++){
			    for(int k=0;k<4;k++){
				    dCopy[i][j][k]=px[i][j][k];
			    }
		    }
	    }
	//returns the copy
        return dCopy;
    }

    //--------------------------------------------------------------------- 
    /// Computes the error in the individual color channels (RGB only) 
    /// between a pixel in the key image and a pixel in the cypher image.  
    /// The pixels must be valid before attempting to carry out these 
    /// operations.
    /// @param pxkey An array containing ARGB values that represents a pixel
    ///        in the key image    
    /// @param pxcypher An array containing ARGB values that represents a 
    ///        pixel in the cypher image
    /// @return An array containing the error (positive values only) between
    ///         the RGB channels of the input pixels. 
    public static int[] colorError( int[] pxkey, int[] pxcypher ) {

	//check that the pixels sent are valid
	if(!(Utilities.isPixelValid(pxkey)&&Utilities.isPixelValid(pxcypher))){
		return null;
	}

        int[] error = new int[3];
	int value=0;

	//loops through finding the difference between the two pixels and putting that value in the error array
	for(int i=0; i<3;i++){
		value= pxkey[i+1]-pxcypher[i+1];
		//accounts for potential negative differences 
		if(value<0)
			value=value*(-1);
		
		error[i]=value;
	}

        return error;
    }

    //--------------------------------------------------------------------- 
    /// Computes the RGB error based on the position of a character in the 
    /// alphabet.  The error is represented using the same value in each
    /// cell of the array.
    /// @param chpos The characters ordinal position in the alphabet
    /// @return an array of three values that represents the error to 
    ///         introduce into to a color 
    public static int[] positionToError( int chpos ) {
       
	int[] error = new int[3];
	int value =0;
	
	//loops through filling the error array with the error 
	for(int i=0;i<3;i++){
		value=chpos+1;
		error[i]=value;
	}


        return error;
    }

    //--------------------------------------------------------------------- 
    /// Computes the ordinal position of a character based on the error 
    /// uniform represented in all cells in an input array of three values.
    /// @param error An array of RGB values (Note that this excludes the 
    ///        alpha channel).  
    /// @return The ordinal position of a character in the alphabet based on
    ///         the amount of error in the input 
    public static int errorToPosition( int[] error ) {
       	    
	    //subtracts 1 from the position to account for a zero based sytem
	    int position=error[0]-1;

        return position;
    }

    //--------------------------------------------------------------------- 
    /// Encrypts a string of characters into a copy of the key image
    /// @input s the string of characters to encrypt
    /// @input pxKey the image to encrypt the string into
    /// @return the encrypted image
    public static int[][][] encrypt(String s, int[][][] pxKey) {
        int[][][] pxCypher = copy(pxKey);
	int[] error;
	//keeps track of the spot in the string
	int count=0;

	//loops through the image changing pixels 
        for(int i=0;i<pxCypher.length;i+=10){
		for(int j=0; j<pxCypher[0].length;j+=10){
				//checks if there are still letter in the string that need to be encrypted 
				//and does so by subtracting the error value from the original
				if(count<s.length()){
					//alpha value doesn't change
					pxCypher[i][j][0]=pxKey[i][j][0];
					//finds the error value of a given char (subtract 'A' to make the value ordinal)
					error=positionToError(((int)s.charAt(count))-(int)'A');
					pxCypher[i][j][1]=(pxCypher[i][j][1]-error[0]);
					pxCypher[i][j][2]=(pxCypher[i][j][2]-error[1]);
					pxCypher[i][j][3]=(pxCypher[i][j][3]-error[2]);
					count++;
				}

			
			}
		}
	

	return pxCypher;
    }

    //--------------------------------------------------------------------- 
    /// Decrypts a string of characters encoded into an image by comparing
    /// pixels in the cypher with the key image
    /// @input pxCypher the encrypted image containing the message
    /// @input pxKey the key image that was used for the encryption
    /// @return the decrypted string of characters
    public static String decrypt(int[][][] pxCypher, int[][][] pxKey) {
        String msg = "";
	int position=0;
	int[] error;

	//loops through the image
	for(int i=0; i<pxCypher.length;i+=10){
		for(int j=0; j<pxCypher[0].length;j+=10){
			//finds the error value at the pixel [i][j]
			error=colorError(pxKey[i][j],pxCypher[i][j]);
			//makes sure that the message hasn't ended
			if(error[0]==0){
				return msg;
			}
			//finds the char position in the alphabet and adds its letter to the string
			else{
				position=errorToPosition(error);
				msg=msg+(char)(position+(int)'A');
			}
		}
	}
        return msg;
    }

    //--------------------------------------------------------------------- 
    // Extension Problems
    //--------------------------------------------------------------------- 
    /// Computes the RGB error based on the position of a character in the 
    /// alphabet.  The error is spread across each cell in the array.
    /// @param chpos The characters ordinal position in the alphabet
    /// @return an array of three values that represents the error to 
    ///         introduce into to a color 
    public static int[] positionToError2( int chpos ) {
        
	    int[] error= new int[3];
	    //makes the positino value non-ordinal 
	    chpos++;

	    //loops through the error array adding one to each idex
	    //until the char positiion is zero
	    while(chpos>0){
	    	for(int i=0;i<3;i++){
			if(chpos>0){
				error[i]++;
		   		chpos--;
			}
	    	}
	    }

        return error;
    }

    //--------------------------------------------------------------------- 
    /// Computes the ordinal position of a character based on the error 
    /// spread across different cells in an input array of three values.
    /// @param error An array of RGB values (Note that this excludes the 
    /// alpha channel).  
    /// @return The ordinal position of a character in the alphabet based on
    ///         the amount of error in the input 
    public static int errorToPosition2( int[] error ) {
	//add together all three indexes of the error array
        int sum = error[0]+error[1]+error[2];
	//subtracts one to make the postion ordinal
	int position=sum-1;

        return position;
    }

    //--------------------------------------------------------------------- 
    /// Encrypts a string of characters into a copy of the key image
    /// @input s the string of characters to encrypt
    /// @input pxKey the image to encrypt the string into
    /// @return the encrypted image
    public static int[][][] encrypt2(String s, int[][][] pxKey) {
        int[][][] pxCypher = copy(pxKey);

        	int[] error;
	//keeps track of the spot in the string
	int count=0;

	//loops through the image changing pixels 
        for(int i=0;i<pxCypher.length;i+=10){
		for(int j=0; j<pxCypher[0].length;j+=10){
			pxCypher[i][j][0]=pxKey[i][j][0];
				for(int k=1; k<4;k++){
					//checks to see if the color channel is closer to being full color
					// and subtracts the error value
					if(pxKey[i][j][k]>=128){
						if(count<s.length()){
							error=positionToError2(((int)s.charAt(count))-(int)'A');
							pxCypher[i][j][k]=(pxCypher[i][j][k]-error[k-1]);
						}
					}
					//checks to see if the color channel is furhter from being full
					//add adds the error value
					else if(pxKey[i][j][k]<128){
						if(count<s.length()){
							error=positionToError2(((int)s.charAt(count))-(int)'A');
							pxCypher[i][j][k]=(pxCypher[i][j][k]+error[k-1]);
						}
					}
				}
			//increments the index being looked at in the string
			count++;
		}
	}
				
        return pxCypher;
    }

    //------------------------------------------------------------------------- 
    /// Decrypts a string of characters encoded into an image by comparing
    /// pixels in the cypher with the key image
    /// @input pxCypher the encrypted image containing the message
    /// @input pxKey the key image that was used for the encryption
    /// @return the decrypted string of characters
    public static String decrypt2(int[][][] pxCypher, int[][][] pxKey) {

        String msg = "";

        int position=0;
	int[] error;

	//loops through the image
	for(int i=0; i<pxCypher.length;i+=10){
		for(int j=0; j<pxCypher[0].length;j+=10){
			//finds the error value for the pixel at [i][j]
			error=colorError(pxKey[i][j],pxCypher[i][j]);
			//makes sure that the message hasn't ended
			if(error[0]==0){
				return msg;
			}
			//finds the char position in the alphabet and adds its letter to the string
			else{
				position=errorToPosition2(error);
				msg=msg+(char)(position+(int)'A');
			}
		}
	}
        return  msg;
    }
}
