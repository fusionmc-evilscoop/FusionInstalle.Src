package org.apache.log4j.spi;

public abstract interface TriggeringEventEvaluator
{
  public abstract boolean isTriggeringEvent(LoggingEvent paramLoggingEvent);
}

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     org.apache.log4j.spi.TriggeringEventEvaluator
 * JD-Core Version:    0.6.0
 */