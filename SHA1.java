import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class SHA1 {

    // Return the number of bits based on the length of byte arr
    public static int getTotalBits(byte[] arr) {
        return arr.length * 8;
    }

    public static String getPaddedByte(byte val) {
        return String.format("%8s", Integer.toBinaryString(val)).replace(" ", "0");
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

        // Step 0: pad the message to have a length of a multiple of 512-bits
        

        // Step 1: Loop through every 512-bits as a chunk

        // Step 2: Break the 512-bit chunk into 16 32-bit words

        // Step 3: Extend the 16 32-bit words into 80 32-bit words using bitwise operators

        // Step 4: Initialize hash variables

        // Step 5: Run the different functions in 4 stages (0-19, 20-39, 40-59, 60-79) and save it

        // Step 6: Save the chunk hash for the next chunk

        // Step 7: Return the final hash value in hexadecimals
    }
}
