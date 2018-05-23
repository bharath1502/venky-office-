package com.chatak.pay.util;


public class CryptoException extends RuntimeException
{

    private static final long serialVersionUID = 0x28c56be976916274L;

    public CryptoException()
    {
    }

    public CryptoException(String message)
    {
        super(message);
    }

    public CryptoException(Throwable cause)
    {
        super(cause);
    }

    public CryptoException(String message, Throwable cause)
    {
        super(message, cause);
    }
}
