package org.apache.log4j.net;

import org.apache.log4j.Level;
import org.apache.log4j.Priority;
import org.apache.log4j.spi.LoggingEvent;
import org.apache.log4j.spi.TriggeringEventEvaluator;

class DefaultEvaluator
  implements TriggeringEventEvaluator
{
  public boolean isTriggeringEvent(LoggingEvent paramLoggingEvent)
  {
    return paramLoggingEvent.getLevel().isGreaterOrEqual(Level.ERROR);
  }
}

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     org.apache.log4j.net.DefaultEvaluator
 * JD-Core Version:    0.6.0
 */