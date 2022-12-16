import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

/*
SHA1 Class

This class hosts the Java code for the SHA1 hashing algorithm. It will take a string
input or a file input and automatically hash the input. Users can get back the hash
result by calling the public method hexResult().

 */

public class SHA1 {

    // Initial hashing constants provided for SHA1 hash algorithm
    private int[] hashVars = {
            0x67452301, 0xEFCDAB89, 0x98BADCFE, 0x10325476, 0xC3D2E1F0
    };

    // Constructs a SHA1 object with string data type
    public SHA1(String data) {
        byte[] bytes = data.getBytes(StandardCharsets.UTF_8);
        compute(bytes);
    }

    // Constructs a SHA1 object with an imported file
    public SHA1(File file) throws IOException {
        byte[] bytes = Files.readAllBytes(file.toPath());
        compute(bytes);
    }

    // Get a padded 8-bit binary string
    // (input: byte value; output: a padded 8-bit binary string)
    private static String getPaddedBinary(byte val) {
        return String.format("%8s",
                             Integer.toBinaryString(Byte.toUnsignedInt(val))).replace(" ", "0");
    }

    // Get a padded n-bit binary string
    // Overloading (input: int value and number of bits; output: a padded n-bit binary string)
    private static String getPaddedBinary(int val, int num) {
        return String.format("%" + num + "s",
                             Integer.toBinaryString(val)).replace(" ", "0");
    }

    // Computes the hash with the algorithm
    // (input: a byte array; no return value--manipulates the hash variables)
    // Referenced SHA1 Wikipedia
    // https://en.wikipedia.org/wiki/SHA-1
    private void compute(byte[] bytes) {
        // Convert the bytes into a binaryString
        StringBuilder binary = new StringBuilder();
        for (int i = 0; i < bytes.length; i++) {
            binary.append(getPaddedBinary(bytes[i]));
        }

        // Step 0: pad the message to have a length of a multiple of 512-bits
        int initialLength = binary.length();

        String binaryInitalLength = getPaddedBinary(initialLength, 64);
        int noPaddedZero = 512 - (initialLength % 512) - 64; // Leave 64 for length
        binary.append("1");
        for (int i = 1; i < noPaddedZero; i++) {
            binary.append("0");
        }
        binary.append(binaryInitalLength);

        int CHUNK_SIZE = 512;
        int NUM_WORDS = 80;

        // Step 1: Loop through every 512-bits as a chunk
        for (int startingPoint = 0; startingPoint < binary.length(); startingPoint += CHUNK_SIZE) {

            String chunk = binary.substring(startingPoint, startingPoint + CHUNK_SIZE);

            String[] words = new String[NUM_WORDS];
            // Step 2: Break the 512-bit chunk into 16 32-bit words
            for (int i = 0; i < 16; i++) {
                words[i] = chunk.substring(i * 32, i * 32 + 32);
            }

            // Step 3: Extend the 16 32-bit words into 80 32-bit words using bitwise operators
            for (int i = 16; i < NUM_WORDS; i++) {

                long wordA = Long.parseLong(words[i - 3], 2);
                long wordB = Long.parseLong(words[i - 8], 2);
                long wordC = Long.parseLong(words[i - 14], 2);
                long wordD = Long.parseLong(words[i - 16], 2);

                int bitwiseResult = (int) (wordA ^ wordB ^ wordC ^ wordD);

                words[i] = getPaddedBinary(Integer.rotateLeft(bitwiseResult, 1), 32);

            }

            // Step 4: Initialize hash variables
            int a = hashVars[0];
            int b = hashVars[1];
            int c = hashVars[2];
            int d = hashVars[3];
            int e = hashVars[4];

            // Step 5: Run the different functions in 4 stages (0-19, 20-39, 40-59, 60-79)
            // and save it
            for (int i = 0; i < NUM_WORDS; i++) {
                int functionOutput;
                int constant;

                // Stage 1
                if (i < 20) {
                    functionOutput = (b & c) ^ ((~b) & d);
                    constant = 0x5A827999;
                }
                // Stage 2
                else if (i < 40) {
                    functionOutput = b ^ c ^ d;
                    constant = 0x6ED9EBA1;
                }
                // Stage 3
                else if (i < 60) {
                    functionOutput = (b & c) ^ (b & d) ^ (c & d);
                    constant = 0x8F1BBCDC;
                }
                // Stage 4
                else {
                    functionOutput = b ^ c ^ d;
                    constant = 0xCA62C1D6;
                }

                int temp = (int) ((Integer.rotateLeft(a, 5)) + functionOutput + e + constant
                        + (Long.parseLong(words[i], 2)));
                e = d;
                d = c;
                c = Integer.rotateLeft(b, 30);
                b = a;
                a = temp;

            }

            // Step 6: Save the chunk hash for the next chunk
            hashVars[0] = hashVars[0] + a;
            hashVars[1] = hashVars[1] + b;
            hashVars[2] = hashVars[2] + c;
            hashVars[3] = hashVars[3] + d;
            hashVars[4] = hashVars[4] + e;
        }
    }

    // Converts the binaryString into padded hexadecimal
    // (input: which hash variable; output: a padded hexadecimal string)
    private String getHex(int hashNum) {
        String hexString = Integer.toHexString(hashVars[hashNum]);
        while (hexString.length() < 8) {
            hexString = "0" + hexString;
        }
        return hexString;
    }

    // Combines the five hash variables into a 40-character hexadecimal string
    // (input: none; output: 40-character hexadecimal string)
    public String hexResult() {
        return getHex(0) + getHex(1) + getHex(2) + getHex(3) + getHex(4);
    }

    // Main Testing Method
    public static void main(String[] args) {

        int total = 0;
        int correct = 0;

        // Tests for Feature 1: SHA1 Hashing
        while (StdIn.hasNextLine()) {
            String input = StdIn.readLine();
            String expected = StdIn.readLine();
            StdIn.readLine();

            StdOut.println("Input: " + input);

            total++;

            // Hash
            SHA1 testHash = new SHA1(input);
            StdOut.printf("Hash: %s\n", testHash.hexResult());

            // The hash computed by the program must be equal to the one in the test file
            if (testHash.hexResult().equals(expected)) {
                StdOut.println("Hash correct.");
                correct++;
            }
            else {
                StdOut.printf("Incorrect. \n Expect: %s \n", expected);
            }
        }

        StdOut.printf("Correct: %d/%d \n", correct, total);

    }
}
