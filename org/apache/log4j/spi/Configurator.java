package org.apache.log4j.spi;

import java.net.URL;

public abstract interface Configurator
{
  public static final String INHERITED = "inherited";
  public static final String NULL = "null";

  public abstract void doConfigure(URL paramURL, LoggerRepository paramLoggerRepository);
}

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     org.apache.log4j.spi.Configurator
 * JD-Core Version:    0.6.0
 */