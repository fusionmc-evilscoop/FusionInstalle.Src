package com.jidesoft.swing;

import java.util.EventListener;

public abstract interface TabEditingListener extends EventListener
{
  public abstract void editingStarted(TabEditingEvent paramTabEditingEvent);

  public abstract void editingStopped(TabEditingEvent paramTabEditingEvent);

  public abstract void editingCanceled(TabEditingEvent paramTabEditingEvent);
}

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     com.jidesoft.swing.TabEditingListener
 * JD-Core Version:    0.6.0
 */