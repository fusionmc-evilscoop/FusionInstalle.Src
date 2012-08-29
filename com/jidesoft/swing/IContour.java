package com.jidesoft.swing;

import java.awt.Component;
import java.awt.Container;
import java.awt.Rectangle;
import javax.swing.JComponent;

public abstract interface IContour
{
  public abstract Rectangle getBounds();

  public abstract boolean isLightweight();

  public abstract void setBounds(Rectangle paramRectangle);

  public abstract void setBounds(int paramInt1, int paramInt2, int paramInt3, int paramInt4);

  public abstract int getTabHeight();

  public abstract void setTabHeight(int paramInt);

  public abstract boolean isTabDocking();

  public abstract void setTabDocking(boolean paramBoolean);

  public abstract int getTabSide();

  public abstract void setTabSide(int paramInt);

  public abstract boolean isFloating();

  public abstract void setFloating(boolean paramBoolean);

  public abstract Component getAttachedComponent();

  public abstract void setAttachedComponent(Component paramComponent);

  public abstract int getAttachedSide();

  public abstract void setAttachedSide(int paramInt);

  public abstract boolean isSingle();

  public abstract void setSingle(boolean paramBoolean);

  public abstract boolean isAllowDocking();

  public abstract void setAllowDocking(boolean paramBoolean);

  public abstract Container getRelativeContainer();

  public abstract void setRelativeContainer(Container paramContainer);

  public abstract int getSaveX();

  public abstract int getSaveY();

  public abstract int getSaveMouseModifier();

  public abstract JComponent getSaveDraggedComponent();

  public abstract void setDraggingInformation(JComponent paramJComponent, int paramInt1, int paramInt2, int paramInt3);

  public abstract void cleanup();

  public abstract void setVisible(boolean paramBoolean);

  public abstract boolean isVisible();

  public abstract void setGlassPane(Component paramComponent);

  public abstract Component getGlassPane();

  public abstract void setChangeCursor(boolean paramBoolean);
}

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     com.jidesoft.swing.IContour
 * JD-Core Version:    0.6.0
 */