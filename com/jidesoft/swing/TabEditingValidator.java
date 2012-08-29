package com.jidesoft.swing;

import java.awt.event.MouseEvent;

public abstract interface TabEditingValidator
{
  public abstract boolean shouldStartEdit(int paramInt, MouseEvent paramMouseEvent);

  public abstract boolean isValid(int paramInt, String paramString);

  public abstract boolean alertIfInvalid(int paramInt, String paramString);
}

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     com.jidesoft.swing.TabEditingValidator
 * JD-Core Version:    0.6.0
 */