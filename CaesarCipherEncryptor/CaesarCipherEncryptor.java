package CaesarCipherEncryptor;

public class CaesarCipherEncryptor {

    private void shift(String data,int size)
    {
        char[] c_array = data.toCharArray();
        for (int i=0;i<c_array.length;i++)
        {
            int temp = (int) c_array[i]+size;
            if (temp>122)
            {
                temp = 97 + (temp - 123);
            }

            c_array[i] = (char) temp;
        }

        System.out.println(c_array);
    }
    public static void main(String[] args)
    {
        CaesarCipherEncryptor cce = new CaesarCipherEncryptor();
        String data = "xyzabc";
        int size = 3;
        cce.shift(data,size);
    }
}
