import java.nio.charset.StandardCharsets;

public class SHA1 {

    private int h0 = 0x67452301; // Hash Variable 1
    private int h1 = 0xEFCDAB89; // Hash Variable 2
    private int h2 = 0x98BADCFE; // Hash Variable 3
    private int h3 = 0x10325476; // Hash Variable 4
    private int h4 = 0xC3D2E1F0; // Hash Variable 5

    public SHA1(String data) {
        byte[] bytes = data.getBytes(StandardCharsets.UTF_8);

        // Convert ASCII number into binary
        StringBuilder binary = new StringBuilder();
        for (int i = 0; i < bytes.length; i++) {
            binary.append(getPaddedByte(bytes[i]));
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

        // Step 1: Loop through every 512-bits as a chunk
        for (int startingPoint = 0; startingPoint < binary.length(); startingPoint += 512) {

            String chunk = binary.substring(startingPoint, startingPoint + 512);

            String[] words = new String[80];
            // Step 2: Break the 512-bit chunk into 16 32-bit words
            for (int i = 0; i < 16; i++) {
                words[i] = chunk.substring(i * 32, i * 32 + 32);
            }

            // Step 3: Extend the 16 32-bit words into 80 32-bit words using bitwise operators
            for (int i = 16; i < 80; i++) {

                long wordA = Long.parseLong(words[i - 3], 2);
                long wordB = Long.parseLong(words[i - 8], 2);
                long wordC = Long.parseLong(words[i - 14], 2);
                long wordD = Long.parseLong(words[i - 16], 2);

                int bitwiseResult = (int) (wordA ^ wordB ^ wordC ^ wordD);

                words[i] = getPaddedBinary(Integer.rotateLeft(bitwiseResult, 1), 32);

            }

            // Step 4: Initialize hash variables
            int a = h0;
            int b = h1;
            int c = h2;
            int d = h3;
            int e = h4;

            // Step 5: Run the different functions in 4 stages (0-19, 20-39, 40-59, 60-79) and save it
            for (int i = 0; i < 80; i++) {
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
            h0 = (h0 + a);
            h1 = (h1 + b);
            h2 = (h2 + c);
            h3 = (h3 + d);
            h4 = (h4 + e);

        }
    }

    // Return the number of bits based on the length of byte arr
    private static int getTotalBits(byte[] arr) {
        return arr.length * 8;
    }

    // Get a padded n-bit binary string
    private static String getPaddedBinary(int val, int num) {
        return String.format("%" + num + "s",
                             Integer.toBinaryString(val)).replace(" ", "0");
    }

    // Get a padded 8-bit binary string
    private static String getPaddedByte(int val) {
        return getPaddedBinary(val, 8);
    }

    // Returns the result in hexadecimal string
    public String hexResult() {
        return Integer.toHexString(h0) + Integer.toHexString(h1) + Integer.toHexString(h2)
                + Integer.toHexString(h3) + Integer.toHexString(h4);
    }

    public static void main(String[] args) {
        String input = StdIn.readLine();
        StdOut.println("Your input is: " + input);

        SHA1 testHash = new SHA1(input);
        StdOut.printf("Hash: %s\n", testHash.hexResult());
    }
}
