package net.miginfocom.swt;

import net.miginfocom.layout.ComponentWrapper;
import net.miginfocom.layout.ContainerWrapper;
import net.miginfocom.layout.PlatformDefaults;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.FontMetrics;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.ProgressBar;
import org.eclipse.swt.widgets.Scrollable;
import org.eclipse.swt.widgets.Slider;
import org.eclipse.swt.widgets.Spinner;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.Text;

public class SwtComponentWrapper
  implements ComponentWrapper
{
  private static Color DB_COMP_OUTLINE = new Color(Display.getCurrent(), 0, 0, 200);
  private static boolean vp = false;
  private static boolean mz = false;
  private final Control c;
  private int compType = -1;

  public SwtComponentWrapper(Control paramControl)
  {
    this.c = paramControl;
  }

  public final int getBaseline(int paramInt1, int paramInt2)
  {
    return -1;
  }

  public final Object getComponent()
  {
    return this.c;
  }

  public final float getPixelUnitFactor(boolean paramBoolean)
  {
    switch (PlatformDefaults.getLogicalPixelBase())
    {
    case 100:
      GC localGC = new GC(this.c);
      FontMetrics localFontMetrics = localGC.getFontMetrics();
      float f = paramBoolean ? localFontMetrics.getAverageCharWidth() / 5.0F : localFontMetrics.getHeight() / 13.0F;
      localGC.dispose();
      return f;
    case 101:
      Float localFloat = paramBoolean ? PlatformDefaults.getHorizontalScaleFactor() : PlatformDefaults.getVerticalScaleFactor();
      if (localFloat != null)
        return localFloat.floatValue();
      return (paramBoolean ? getHorizontalScreenDPI() : getVerticalScreenDPI()) / PlatformDefaults.getDefaultDPI();
    }
    return 1.0F;
  }

  public final int getX()
  {
    return this.c.getLocation().x;
  }

  public final int getY()
  {
    return this.c.getLocation().y;
  }

  public final int getWidth()
  {
    return this.c.getSize().x;
  }

  public final int getHeight()
  {
    return this.c.getSize().y;
  }

  public final int getScreenLocationX()
  {
    return this.c.toDisplay(0, 0).x;
  }

  public final int getScreenLocationY()
  {
    return this.c.toDisplay(0, 0).y;
  }

  public final int getMinimumHeight(int paramInt)
  {
    return mz ? 0 : computeSize(false, paramInt).y;
  }

  public final int getMinimumWidth(int paramInt)
  {
    return mz ? 0 : computeSize(true, paramInt).x;
  }

  public final int getPreferredHeight(int paramInt)
  {
    return computeSize(false, paramInt).y;
  }

  public final int getPreferredWidth(int paramInt)
  {
    return computeSize(true, paramInt).x;
  }

  public final int getMaximumHeight(int paramInt)
  {
    return 32767;
  }

  public final int getMaximumWidth(int paramInt)
  {
    return 32767;
  }

  private Point computeSize(boolean paramBoolean, int paramInt)
  {
    int i = paramBoolean ? -1 : paramInt;
    int j = !paramBoolean ? -1 : paramInt;
    if ((i != -1) || (j != -1))
    {
      int k = 0;
      if ((this.c instanceof Scrollable))
      {
        Rectangle localRectangle = ((Scrollable)this.c).computeTrim(0, 0, 0, 0);
        k = paramBoolean ? localRectangle.width : localRectangle.height;
      }
      else
      {
        k = this.c.getBorderWidth() << 1;
      }
      if (i == -1)
        j = Math.max(0, j - k);
      else
        i = Math.max(0, i - k);
    }
    return this.c.computeSize(i, j);
  }

  public final ContainerWrapper getParent()
  {
    return new SwtContainerWrapper(this.c.getParent());
  }

  public int getHorizontalScreenDPI()
  {
    return this.c.getDisplay().getDPI().x;
  }

  public int getVerticalScreenDPI()
  {
    return this.c.getDisplay().getDPI().y;
  }

  public final int getScreenWidth()
  {
    return this.c.getDisplay().getBounds().width;
  }

  public final int getScreenHeight()
  {
    return this.c.getDisplay().getBounds().height;
  }

  public final boolean hasBaseline()
  {
    return false;
  }

  public final String getLinkId()
  {
    return null;
  }

  public final void setBounds(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    this.c.setBounds(paramInt1, paramInt2, paramInt3, paramInt4);
  }

  public boolean isVisible()
  {
    return this.c.getVisible();
  }

  public final int[] getVisualPadding()
  {
    return null;
  }

  public static boolean isUseVisualPadding()
  {
    return vp;
  }

  public static void setUseVisualPadding(boolean paramBoolean)
  {
    vp = paramBoolean;
  }

  public static boolean isMinimumSizeZero()
  {
    return mz;
  }

  public static void setMinimumSizeZero(boolean paramBoolean)
  {
    mz = paramBoolean;
  }

  public int getLayoutHashCode()
  {
    if (this.c.isDisposed())
      return -1;
    Point localPoint1 = this.c.getSize();
    Point localPoint2 = this.c.computeSize(-1, -1, false);
    int i = localPoint2.x + (localPoint2.y << 12) + (localPoint1.x << 22) + (localPoint1.y << 16);
    if (this.c.isVisible())
      i |= 33554432;
    String str = getLinkId();
    if (str != null)
      i += str.hashCode();
    return i;
  }

  public final void paintDebugOutline()
  {
    if (this.c.isDisposed())
      return;
    GC localGC = new GC(this.c);
    localGC.setLineJoin(1);
    localGC.setLineCap(3);
    localGC.setLineStyle(3);
    localGC.setForeground(DB_COMP_OUTLINE);
    localGC.drawRectangle(0, 0, getWidth() - 1, getHeight() - 1);
    localGC.dispose();
  }

  public int getComponetType(boolean paramBoolean)
  {
    if (this.compType == -1)
      this.compType = checkType();
    return this.compType;
  }

  private int checkType()
  {
    int i = this.c.getStyle();
    if (((this.c instanceof Text)) || ((this.c instanceof StyledText)))
      return (i & 0x2) > 0 ? 4 : 3;
    if ((this.c instanceof Label))
      return (i & 0x2) > 0 ? 18 : 2;
    if ((this.c instanceof Button))
    {
      if (((i & 0x20) > 0) || ((i & 0x10) > 0))
        return 16;
      return 5;
    }
    if ((this.c instanceof Canvas))
      return 10;
    if ((this.c instanceof List))
      return 6;
    if ((this.c instanceof Table))
      return 7;
    if ((this.c instanceof Spinner))
      return 13;
    if ((this.c instanceof ProgressBar))
      return 14;
    if ((this.c instanceof Slider))
      return 12;
    if ((this.c instanceof Composite))
      return 1;
    return 0;
  }

  public final int hashCode()
  {
    return this.c.hashCode();
  }

  public final boolean equals(Object paramObject)
  {
    if ((paramObject == null) || (!(paramObject instanceof ComponentWrapper)))
      return false;
    return getComponent().equals(((ComponentWrapper)paramObject).getComponent());
  }
}

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     net.miginfocom.swt.SwtComponentWrapper
 * JD-Core Version:    0.6.0
 */