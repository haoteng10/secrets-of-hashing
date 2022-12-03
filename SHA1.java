import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class SHA1 {

    // Return the number of bits based on the length of byte arr
    public static int getTotalBits(byte[] arr) {
        return arr.length * 8;
    }

    // Get a padded n-bit binary string
    public static String getPaddedBinary(long val, int num) {
        return String.format("%" + num + "s",
                             Long.toBinaryString(val)).replace(" ", "0");
    }

    public static String getPaddedBinary(int val, int num) {
        return getPaddedBinary((long) val, num);
    }

    // Get a padded 8-bit binary string
    public static String getPaddedByte(int val) {
        return getPaddedBinary(val, 8);
    }

    public static void main(String[] args) {
        StdOut.print("Please enter your value: ");
        String input = StdIn.readString();
        StdOut.println("Your input is: " + input);

        int h0 = 0x67452301; // 32-bit
        int h1 = 0xEFCDAB89;
        int h2 = 0x98BADCFE;
        int h3 = 0x10325476;
        int h4 = 0xC3D2E1F0;

        byte[] bytes = input.getBytes(StandardCharsets.UTF_8);
        StdOut.println(Arrays.toString(bytes));
        StdOut.println(getTotalBits(bytes));

        StringBuilder binary = new StringBuilder();
        for (int i = 0; i < bytes.length; i++) {
            binary.append(getPaddedByte(bytes[i]));
        }

        StdOut.println(binary.toString());
        StdOut.println("===");

        // Step 0: pad the message to have a length of a multiple of 512-bits
        int initialLength = binary.length();
        String binaryInitalLength = getPaddedBinary(initialLength, 64);
        int noPaddedZero = 512 - (initialLength % 512) - 64; // Leave 64 for length
        for (int i = 0; i < noPaddedZero; i++) {
            binary.append("0");
        }
        binary.append(binaryInitalLength);

        StdOut.println(binary);
        StdOut.println("===");

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
                long bitwiseResult = (Long.parseLong(words[i - 3], 2) ^ Long.parseLong(
                        words[i - 8], 2)
                        ^ Long.parseLong(words[i - 14], 2) ^ Long.parseLong(words[i - 16], 2));
                words[i] = getPaddedBinary(Long.rotateLeft(bitwiseResult, 1), 32);
            }

            StdOut.println(Arrays.toString(words));

            // Step 4: Initialize hash variables

            // Step 5: Run the different functions in 4 stages (0-19, 20-39, 40-59, 60-79) and save it

            // Step 6: Save the chunk hash for the next chunk
        }

        // Step 7: Return the final hash value in hexadecimals
    }
}
