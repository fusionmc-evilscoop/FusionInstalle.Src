package com.jidesoft.swing;

import java.awt.Color;

public abstract interface ComponentStateSupport
{
  public abstract Color getBackgroundOfState(int paramInt);

  public abstract void setBackgroundOfState(int paramInt, Color paramColor);

  public abstract Color getForegroundOfState(int paramInt);

  public abstract void setForegroundOfState(int paramInt, Color paramColor);
}

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     com.jidesoft.swing.ComponentStateSupport
 * JD-Core Version:    0.6.0
 */