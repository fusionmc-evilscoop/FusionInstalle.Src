package org.apache.commons.io.output;

import java.io.IOException;
import java.io.OutputStream;

public class NullOutputStream extends OutputStream
{
  public void write(byte[] b, int off, int len)
  {
  }

  public void write(int b)
  {
  }

  public void write(byte[] b)
    throws IOException
  {
  }
}

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     org.apache.commons.io.output.NullOutputStream
 * JD-Core Version:    0.6.0
 */