package com.jidesoft.plaf;

import java.awt.Component;
import javax.swing.plaf.TabbedPaneUI;

public abstract class JideTabbedPaneUI extends TabbedPaneUI
{
  public abstract Component getTabPanel();

  public abstract boolean editTabAt(int paramInt);

  public abstract boolean isTabEditing();

  public abstract void stopTabEditing();

  public abstract void cancelTabEditing();

  public abstract int getEditingTabIndex();

  public abstract void ensureActiveTabIsVisible(boolean paramBoolean);
}

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     com.jidesoft.plaf.JideTabbedPaneUI
 * JD-Core Version:    0.6.0
 */