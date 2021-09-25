/*-------------------------------------------------------------------------
GWU CSCI1112 Spring 2020
author: James Taylor

This file demonstrates simple shift (caesar) ciphering.
-------------------------------------------------------------------------*/

public class Caesar {
    public static void main(String[] args) {
        int key = 1;
        String inmsg = Utilities.clean( Utilities.alphabet() );

        String cypher = encrypt( inmsg, key );
        System.out.println( cypher );

        String outmsg = decrypt( cypher, key );
        System.out.println( outmsg );       
    }

    public static String encrypt(String s, int shift) {
        int start = (int) 'A';

        int[] ords = new int[s.length()];
        for(int i = 0; i < s.length(); i++) {
            ords[i] = (int) s.charAt(i) - start;
        }

        int[] encodedords = new int[s.length()];
        for(int i = 0; i < s.length(); i++) {
            encodedords[i] = (ords[i] + shift) % 26;
        }

        char[] encodedchars = new char[s.length()];
        for(int i = 0; i < s.length(); i++) {
            encodedchars[i] = (char) (encodedords[i] + start);
        }

        return new String(encodedchars);
    }

    public static String decrypt(String s, int shift) {
        int start = (int) 'A';

        int[] ords = new int[s.length()];
        for(int i = 0; i < s.length(); i++) {
            ords[i] = (int) s.charAt(i) - start;
        }

        int[] decodedords = new int[s.length()];
        for(int i = 0; i < s.length(); i++) {
            if( ords[i] - shift < 0 ) {
                ords[i] += 26;
            }
            decodedords[i] = ords[i] - shift;
        }

        char[] decodedchars = new char[s.length()];
        for(int i = 0; i < s.length(); i++) {
            decodedchars[i] = (char) (decodedords[i] + start);
        }

        return new String(decodedchars);
    }

}
