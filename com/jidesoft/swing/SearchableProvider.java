package com.jidesoft.swing;

import java.awt.event.KeyEvent;

public abstract interface SearchableProvider
{
  public abstract String getSearchingText();

  public abstract boolean isPassive();

  public abstract void processKeyEvent(KeyEvent paramKeyEvent);
}

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     com.jidesoft.swing.SearchableProvider
 * JD-Core Version:    0.6.0
 */