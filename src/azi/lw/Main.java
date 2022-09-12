package azi.lw;
import java.util.Scanner;

public class Main {
    //global variable specifying the length of the S state
    static final int N = 256;
    //global variable that will be used to permute in the S state
    static int temp = 0;
    //the main method that calls the encryption method and inputs and outputs data
    public static void main(String[] args) {
        //entering the input text and key into the console
        Scanner in = new Scanner(System.in);
        System.out.print("Enter the input text: ");
        String originaltext = in.next();
        System.out.print("Enter the key: ");
        String key = in.next();
        //call the RC4 method with the input text and key arguments to generate the ciphertext
        String cipherText = RC4(originaltext, key);
        //call the RC4 method with the ciphertext and key arguments to generate the decrypted text
        String decryptText = RC4(cipherText, key);
        //outputting results to the console
        System.out.println("Result:");
        System.out.println("Encrypted text: " + cipherText);
        System.out.println("Decoded text: " + decryptText);
    }
    //method that implements encryption, with parameters input/ciphertext and key
    static String RC4(final String text, final String key){
        //creation of state array variable S, keystream keyStream and encrypted/decrypted text result
        int [] S = new int[N];
        String keyStream = "";
        String result = "";
        //initialization of state S using the key planning algorithm
        S = KSA(S, key);
        //initialization of the keyStream key stream using a pseudo-random generation algorithm
        keyStream = PRGA(S, keyStream, text.length());
        //initialization of ciphertext/decrypted text by XOR (^) operation between input/ciphertext and key stream
        for (int i = 0; i < text.length(); ++i) {
           result += (char)(text.charAt(i)^keyStream.charAt(i));
        }
        //return the value of the encrypted/decrypted text result
            return result;
    }
    //a method that implements a pseudo-random algorithm of generation, with parameters state S, key stream and text length
    static String PRGA(int[] S, String keyStream, final int textLen){
        //initialization of iterators with an initial value
        int i = 0, j = 0;
        //repeat (text length-1) times the generation of the key stream
        for (int k = 0; k < textLen; ++k) {
            //determination of new values ​​of iterators i and j
            i = (i + 1) % N;
            j = (j + S[i]) % N;
            //permutation in state S with iterators i and j using temp variable
            temp = S[i];
            S[i] = S[j];
            S[j] = temp;
            //key flow generation
            keyStream += (char) ((S[(S[i] + S[j]) % N]));
        }
        //return keyStream key stream value
        return  keyStream;
    }
    //method that implements the key planning algorithm, with parameters state S and key
    static int [] KSA(int[] S, final String key) {
        //initialization of the initial state S with values ​​from 0 to -1
        for (int i = 0; i < N; ++i) {
            S[i] = i;
        }
        //initialization of the iterator j with the initial value and length of the text kLen
        int j = 0;
        int kLen = key.length();
        //repeat (N-1) times the permutation in state S
        for (int i = 0; i < N; ++i) {
            //defining the new value of the iterator j
            j = (j + S[i] + (int)key.charAt(i % kLen)) % N;
            //permutation in state S with iterators i and j using a variable temp
            temp = S[i];
            S[i] = S[j];
            S[j] = temp;
        }
        //return the value of the state array S
        return S;
    }
}

