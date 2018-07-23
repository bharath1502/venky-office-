package com.chatak.pay.util;

import java.math.BigInteger;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

import com.chatak.pg.util.Constants;

public class JCryption
{

    private KeyPair keyPair;
    private int keyLength;

    public JCryption()
    {
        keyPair = null;
        keyLength = Constants.ONE_ZERO_TWO_FOUR;
        generateKeyPair(keyLength);
    }

    public JCryption(int keyLength)
    {
        keyPair = null;
        this.keyLength = Constants.ONE_ZERO_TWO_FOUR;
        generateKeyPair(keyLength);
    }

    public JCryption(KeyPair keyPair)
    {
        this.keyPair = null;
        keyLength = Constants.ONE_ZERO_TWO_FOUR;
        setKeyPair(keyPair);
    }

    public KeyPair getKeyPair()
    {
        return keyPair;
    }

    public void setKeyPair(KeyPair keyPair)
    {
        this.keyPair = keyPair;
        keyLength = ((RSAPublicKey)keyPair.getPublic()).getModulus().bitLength();
    }

    public int getKeyLength()
    {
        return keyLength;
    }

    public void generateKeyPair(int keyLength)
    {
        try
        {
            KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
            kpg.initialize(keyLength);
            keyPair = kpg.generateKeyPair();
            this.keyLength = keyLength;
        }
        catch(NoSuchAlgorithmException e)
        {
            throw new CryptoException("Error obtaining RSA algorithm", e);
        }
    }

    public String getKeyModulus()
    {
        RSAPublicKey publicKey = (RSAPublicKey)keyPair.getPublic();
        return publicKey.getModulus().toString(Constants.SIXTEEN);
    }

    public String getPublicExponent()
    {
        RSAPublicKey publicKey = (RSAPublicKey)keyPair.getPublic();
        return publicKey.getPublicExponent().toString(Constants.SIXTEEN);
    }

    public int getMaxDigits()
    {
        return (keyLength * Constants.TWO) / Constants.SIXTEEN + Constants.THREE;
    }

    public String decrypt(String encrypted)
    {
        RSAPublicKey publicKey = (RSAPublicKey)keyPair.getPublic();
        RSAPrivateKey privateKey = (RSAPrivateKey)keyPair.getPrivate();
        String blocks[] = encrypted.split("\\s");
        String result = "";
        for(int i = 0; i < blocks.length; i++)
        {
            BigInteger data = new BigInteger(blocks[i], Constants.SIXTEEN);
            BigInteger decryptedBlock = data.modPow(privateKey.getPrivateExponent(), publicKey.getModulus());
            result = (new StringBuilder(String.valueOf(result))).append(decodeBigIntToHex(decryptedBlock)).toString();
        }

        return redundancyCheck(result);
    }

    private String decodeBigIntToHex(BigInteger bigint)
    {
        String message;
        BigInteger ascii;
        for(message = ""; bigint.compareTo(new BigInteger("0")) != 0; message = (new StringBuilder(String.valueOf(message))).append((char)ascii.intValue()).toString())
        {
            ascii = bigint.mod(new BigInteger("256"));
            bigint = bigint.divide(new BigInteger("256"));
        }

        return message;
    }

    private String redundancyCheck(String string)
    {
        String r1 = string.substring(0, Constants.TWO);
        String r2 = string.substring(Constants.TWO);
        int check = Integer.parseInt(r1, Constants.SIXTEEN);
        String value = r2;
        int sum = 0;
        for(int i = 0; i < value.length(); i++)
        {
            sum += value.charAt(i);
        }

        if(check == (sum & 0xff))
        {
            return value;
        } else
        {
            return null;
        }
    }
}
