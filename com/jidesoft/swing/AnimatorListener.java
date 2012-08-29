package com.jidesoft.swing;

import java.awt.Component;
import java.util.EventListener;

public abstract interface AnimatorListener extends EventListener
{
  public abstract void animationStarts(Component paramComponent);

  public abstract void animationFrame(Component paramComponent, int paramInt1, int paramInt2);

  public abstract void animationEnds(Component paramComponent);
}

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     com.jidesoft.swing.AnimatorListener
 * JD-Core Version:    0.6.0
 */