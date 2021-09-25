/*-------------------------------------------------------------------------
GWU CSCI1112 Spring 2020
author: James Taylor

This program allows a user to visualize the cypher image generated by 
encoding a message using the Steganography class.  The program uses a
very simple menu system to allow users to pick among all predefined options
and visually validate the cypher image and the decrypted message. 
-------------------------------------------------------------------------*/
import java.awt.Image;
import java.util.Scanner;

public class Viewer {
    static ImageTool imTool = new ImageTool ();
    static Scanner scanner = new Scanner(System.in);

    // Entry point
    public static void main( String[] args ) {
        viewCypher( inputMessage(), inputImage(), inputAlgorithm() );
    }

    // Input function so a user can select the message to encode
    // @return the message string chosen
    public static String inputMessage() {
        int option = 0;
        String menu = "";
        menu += "1)Encode \"Hello\"\n";
        menu += "2)Encode pangram\n";
        menu += "3)Encode loremipsum\n";
        menu += "Choose a message: ";

        // iterate until a valid entry is made
        while( true ) {
            System.out.print( menu );
            String input = scanner.nextLine();

            try {
                option = Integer.parseInt( input );
            } catch (Exception ex) {
                System.out.println("ERROR: Invalid entry, try again");
                continue;
            }

            // The entry is valid only if this branch can be entered
            if( option >= 1 && option <= 3) { break; }

            System.out.println("ERROR: Invalid entry, try again");
        }

        // map the numeric option to the corresponding string
        if(option == 1) {
            return "Hello";
        } else if(option == 2) {
            return Utilities.pangram();
        } else {
            return Utilities.loremipsum();
        }
    }

    // Input function so a user can select the image to encode and decode 
    // with
    // @return the filename of the image chosen
    public static String inputImage() {
        int option = 0;
        String menu = "";
        menu += "1)Encode into white.png\n";
        menu += "2)Encode into gradient.png\n";
        menu += "Choose an image: ";

        // iterate until a valid entry is made
        while( true ) {
            System.out.print( menu );
            String input = scanner.nextLine();

            try {
                option = Integer.parseInt( input );
            } catch (Exception ex) {
                System.out.println("ERROR: Invalid entry, try again");
                continue;
            }

            // The entry is valid only if this branch can be entered
            if( option >= 1 && option <= 2) { break; }

            System.out.println("ERROR: Invalid entry, try again");
        }

        // map the numeric option to the corresponding string
        if(option == 1) {
            return "white.png";
        } else {
            return "gradient.png";
        }
    }

    // Input function so a user can select which encode and decode algorithm
    // is used
    // @return 1 if the base algorithms are chosen and 2 if the extension
    //         algorithm is chosen.  All other options are not accepted 
    public static int inputAlgorithm() {
        int option = 0;
        String menu = "";
        menu += "1)Encode using base encoding\n";
        menu += "2)Encode using extension encoding\n";
        menu += "Choose an encryption method: ";

        // iterate until a valid entry is made
        while( true ) {
            System.out.print( menu );
            String input = scanner.nextLine();

            try {
                option = Integer.parseInt( input );
            } catch (Exception ex) {
                System.out.println("ERROR: Invalid entry, try again");
                continue;
            }

            // The entry is valid only if this branch can be entered
            if( option >= 1 && option <= 2) { break; }

            System.out.println("ERROR: Invalid entry, try again");
        }

        return option;
    }

    // Encodes and decodes a message into an image using a specified 
    // algorithm.  Will open a window with the cypher image grossly enlarged
    // to show the user what the cypher image looks like.  Significant 
    // changes by the encryption process to specific pixels should be 
    // visually identifiable.  Also decrypts the message and then prints it
    // to the console for visual validation
    // @param msg The message to encrypt
    // @param imgname The image to use as the key
    // @param algorithm The algorithm (either 1 or 2) to use.  1 indicates 
    //                  use the base algorithm and 2 indicates the extension
    public static void viewCypher(String msg, String imgname, int algorithm) {
        String msgout;
        int[][][] key;
        int[][][] cypher;
        Image img;

        // Clean the string up so it meets the specification
        msg = Utilities.clean(msg);

        // Load the key image
        img = imTool.readImageFile( imgname );
        // Convert the key image to pixels
        key = imTool.imageToPixels( img );

        // encrypt using the requested algorithm
        if( algorithm == 1) {
            cypher = Steganography.encrypt( msg, key );
        } else {
            cypher = Steganography.encrypt2( msg, key );
        }
        if( cypher == null ) {
            return;
        }

        // Show the cypher image for visual validation
        img = imTool.pixelsToImage( Utilities.stretch( cypher, 4 ) );
        imTool.showImage( img, msg, 1300, 1024 );

        // Decrypt the message back out, print it to the console
        if( algorithm == 1) {
            msgout = Steganography.decrypt( cypher, key );
        } else {
            msgout = Steganography.decrypt2( cypher, key );
        }

        // Print the messages for visual validation
        System.out.println( "ENCRYPTED: " + msg );
        System.out.println( "DECRYPTED: " + msgout );
    }
}