package com.jidesoft.swing.event;

import java.util.EventListener;

public abstract interface SidePaneListener extends EventListener
{
  public abstract void sidePaneTabSelected(SidePaneEvent paramSidePaneEvent);

  public abstract void sidePaneTabDeselected(SidePaneEvent paramSidePaneEvent);
}

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     com.jidesoft.swing.event.SidePaneListener
 * JD-Core Version:    0.6.0
 */