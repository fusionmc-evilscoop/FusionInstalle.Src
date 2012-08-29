package com.jidesoft.plaf.basic;

import java.awt.Graphics;
import java.awt.Rectangle;
import javax.swing.JComponent;
import javax.swing.plaf.UIResource;

public abstract interface Painter extends UIResource
{
  public abstract void paint(JComponent paramJComponent, Graphics paramGraphics, Rectangle paramRectangle, int paramInt1, int paramInt2);
}

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     com.jidesoft.plaf.basic.Painter
 * JD-Core Version:    0.6.0
 */