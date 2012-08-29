package com.jidesoft.swing;

import java.awt.Insets;
import javax.swing.JComponent;
import javax.swing.SwingConstants;

public abstract interface Overlayable extends SwingConstants
{
  public static final String CLIENT_PROPERTY_OVERLAYABLE = "Overlayable.overlayable";

  public abstract void addOverlayComponent(JComponent paramJComponent);

  public abstract void addOverlayComponent(JComponent paramJComponent, int paramInt);

  public abstract void addOverlayComponent(JComponent paramJComponent, int paramInt1, int paramInt2);

  public abstract void removeOverlayComponent(JComponent paramJComponent);

  public abstract JComponent[] getOverlayComponents();

  public abstract void setOverlayLocation(JComponent paramJComponent, int paramInt);

  public abstract int getOverlayLocation(JComponent paramJComponent);

  public abstract Insets getOverlayLocationInsets();

  public abstract void setOverlayLocationInsets(Insets paramInsets);

  public abstract void setOverlayVisible(boolean paramBoolean);
}

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     com.jidesoft.swing.Overlayable
 * JD-Core Version:    0.6.0
 */