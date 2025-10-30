package it.unibo.exceptions.fakenetwork.api;

import java.io.IOException;

public class NetworkException extends IOException
{
    public NetworkException() throws IOException
    {
        throw new IOException("Network error: no response");
    }
    public NetworkException(String message) throws IOException
    {
        throw new IOException("Network error while sending message: " + message);
    }
}