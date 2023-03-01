/*
* Aiden Sanghyeop Hyun
*
* 260974945*/



package assignment2;

public class SolitaireCipher {
    public Deck key;

    public SolitaireCipher(Deck key) {
        this.key = new Deck(key); // deep copy of the deck
    }

    /*
     * TODO: Generates a keystream of the given size
     */

    /*public static void main(String[] args){

     getKeyStram
    }*/
    public int[] getKeystream(int size) {

        int[] result = new int[size];

        for (int i = 0; i < size; i++) {

            int keyValue = this.key.generateNextKeystreamValue();

            if (keyValue == 0) {

                i--;

            } else {

                result[i] = keyValue;

            }
        }

        return result;

    }

    private char encodeLetter(char letter, int num) {

        System.out.println(Integer.valueOf(num) + " is the key");
        System.out.println("and the letter is " + letter);


        char[] alphabet = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};
        int letterindex;
        char result;

        for (int i = 0; i < 26; i++) {


            if (Character.toUpperCase(alphabet[i]) == Character.toUpperCase(letter)) {


                result = Character.toUpperCase(alphabet[(i + num) % 26]);
                return result;

            }


        }

        return 'f';
    }

    private int numOfLetter(char[] charArray) {
        int j = 0;
        for (int c = 0; c < charArray.length; c++) {
            if (Character.isLetter(charArray[c])) {
                j += 1;
            }

        }
        return j;
    }

    private char decodeLetter(char letter, int num) {

        System.out.println(Integer.valueOf(num) + " is the key");
        System.out.println("and the letter is " + letter);


        char[] alphabet = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};
        int letterindex;
        char result;

        for (int i = 0; i <26; i++) {


            if (Character.toUpperCase(alphabet[i]) == Character.toUpperCase(letter)) {

                int index = (i - num) % 26;

                if(index < 0){
                    index +=26;
                }



                result = Character.toUpperCase(alphabet[index]);
                return result;

            }


        }

        return 'f';
    }



    private String toUpperCaseString(String msg) {
        String string = "";

        char[] charArray = msg.toCharArray();

        int newLength = numOfLetter(charArray);
        char[] newArray = new char[newLength];
        int j = 0;

        for (int i = 0; msg.length() > i; i++) {
            if (Character.isLetter(charArray[i])) {
                string += Character.toUpperCase(charArray[i]);
            }
        }

        return string;
    }

    /*
     * TODO: Encodes the input message using the algorithm described in the pdf.
     */
    public String encode(String msg) {
        int[] keys = getKeystream(12);

        String string = "";


        String newMsg = toUpperCaseString(msg);
        char[] charArray = newMsg.toCharArray();
        int j = 0;

        for (int d = 0; d < charArray.length; d++) {
            string += encodeLetter(charArray[d], keys[d%12]);
            System.out.println("String is : " + string);
        }


        return string;
    }

    /*
     * TODO: Decodes the input message using the algorithm described in the pdf.
     */
    public String decode(String msg) {
        String string = "";
        int[] keys = getKeystream(12);
        char[] charArray = msg.toCharArray();

        for (int i=0;i< charArray.length;i++){
        char newLetter = decodeLetter(charArray[i],keys[i%12]);
        string += newLetter;
        }


        return string;
    }
}
