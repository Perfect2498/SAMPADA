package sampada.util;
import javax.crypto.*;
import java.security.AlgorithmParameters;
import javax.crypto.spec.SecretKeySpec;


public class DesEncrypter
{
     Cipher ecipher;
     Cipher dcipher;

     public DesEncrypter()
     {
        try
        {
            ecipher = Cipher.getInstance("DES");
            dcipher = Cipher.getInstance("DES");

            byte[] raw = new byte[]{0x01, 0x72, 0x43, 0x3E, 0x1C, 0x7A, 0x55};
            byte[] keyBytes = addParity(raw);

            SecretKey key = new SecretKeySpec(keyBytes, "DES");

            ecipher.init(Cipher.ENCRYPT_MODE, key);
            dcipher.init(Cipher.DECRYPT_MODE, key);

        }
        catch (javax.crypto.NoSuchPaddingException e)
        {
        }
        catch (java.security.NoSuchAlgorithmException e)
        {
        }
        catch (java.security.InvalidKeyException e)
        {
        }
     }

     public static byte[] addParity(byte[] in)
     {
	        byte[] result = new byte[8];

	        // Keeps track of the bit position in the result
	        int resultIx = 1;

	        // Used to keep track of the number of 1 bits in each 7-bit chunk
	        int bitCount = 0;

	        // Process each of the 56 bits
	        for (int i=0; i<56; i++) {
	            // Get the bit at bit position i
	            boolean bit = (in[6-i/8]&(1<<(i%8))) > 0;

	            // If set, set the corresponding bit in the result
	            if (bit) {
	                result[7-resultIx/8] |= (1<<(resultIx%8))&0xFF;
	                bitCount++;
	            }

	            // Set the parity bit after every 7 bits
	            if ((i+1) % 7 == 0) {
	                if (bitCount % 2 == 0) {
	                    // Set low-order bit (parity bit) if bit count is even
	                    result[7-resultIx/8] |= 1;
	                }
	                resultIx++;
	                bitCount = 0;
	            }
	            resultIx++;
	        }
	        return result;
	    }

    /*  public String encrypt(String str)
      {
        try
        {
            // Encode the string into bytes using utf-8
            System.out.println("DesEncrypter.java " + str);
            byte[] utf8 = str.getBytes("UTF8");
            System.out.println("Des 1 " + utf8);
            // Encrypt
            byte[] enc = ecipher.doFinal(utf8);
            System.out.println("DES 2 " +enc);
            System.out.println("DEC # " +new sun.misc.BASE64Encoder().encode(enc));
            // Encode bytes to base64 to get a string
            return new sun.misc.BASE64Encoder().encode(enc);
        } catch (javax.crypto.BadPaddingException e) {
        } catch (IllegalBlockSizeException e) {
        } catch (java.io.IOException e) {
        }
        System.out.println(" returning null");
        return null;
    }*/

     public String encrypt(String str)
	    {
	        try
	        {
	            // Encode the string into bytes using utf-8
	            
	            byte[] utf8 = str.getBytes("UTF8");
	 			
	            // Encrypt
	            byte[] enc = ecipher.doFinal(utf8);				
	            // Encode bytes to base64 to get a string     
	            return new sun.misc.BASE64Encoder().encode(enc);
	        } catch (javax.crypto.BadPaddingException e) {
	        } catch (IllegalBlockSizeException e) {
	        } catch (java.io.IOException e) {
	        }
	        return null;
    }

    public String decrypt(String str)
    {
      try
      {
            // Decode base64 to get bytes
          byte[] dec = new sun.misc.BASE64Decoder().decodeBuffer(str);

          // Decrypt
          byte[] utf8 = dcipher.doFinal(dec);
          // Decode using utf-8
          return new String(utf8, "UTF8");
      } catch (javax.crypto.BadPaddingException e) {
      } catch (IllegalBlockSizeException e) {
      } catch (java.io.IOException e) {
      }
      return null;
    }
}