package com.jidesoft.hints;

import javax.swing.JComponent;

public abstract interface IntelliHints
{
  public static final String CLIENT_PROPERTY_INTELLI_HINTS = "INTELLI_HINTS";

  public abstract JComponent createHintsComponent();

  public abstract boolean updateHints(Object paramObject);

  public abstract Object getSelectedHint();

  public abstract void acceptHint(Object paramObject);
}

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     com.jidesoft.hints.IntelliHints
 * JD-Core Version:    0.6.0
 */