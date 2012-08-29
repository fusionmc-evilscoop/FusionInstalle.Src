package com.jidesoft.swing;

import javax.swing.ButtonModel;

public abstract interface SplitButtonModel extends ButtonModel
{
  public abstract void setButtonSelected(boolean paramBoolean);

  public abstract boolean isButtonSelected();

  public abstract void setButtonEnabled(boolean paramBoolean);

  public abstract boolean isButtonEnabled();

  public abstract void setButtonRollover(boolean paramBoolean);

  public abstract boolean isButtonRollover();
}

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     com.jidesoft.swing.SplitButtonModel
 * JD-Core Version:    0.6.0
 */