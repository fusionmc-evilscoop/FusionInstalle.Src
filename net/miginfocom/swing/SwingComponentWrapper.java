package net.miginfocom.swing;

import java.awt.BasicStroke;
import java.awt.Button;
import java.awt.Canvas;
import java.awt.Checkbox;
import java.awt.Choice;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.HeadlessException;
import java.awt.Label;
import java.awt.List;
import java.awt.Point;
import java.awt.ScrollPane;
import java.awt.Scrollbar;
import java.awt.TextComponent;
import java.awt.TextField;
import java.awt.Toolkit;
import java.awt.geom.Point2D.Float;
import java.awt.geom.Rectangle2D;
import java.lang.reflect.Method;
import java.util.IdentityHashMap;
import javax.swing.AbstractButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JSlider;
import javax.swing.JSpinner;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.JViewport;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.text.JTextComponent;
import net.miginfocom.layout.ComponentWrapper;
import net.miginfocom.layout.ContainerWrapper;
import net.miginfocom.layout.PlatformDefaults;

public class SwingComponentWrapper
  implements ComponentWrapper
{
  private static boolean maxSet = false;
  private static boolean vp = true;
  private static final Color DB_COMP_OUTLINE = new Color(0, 0, 200);
  private final Component c;
  private int compType = -1;
  private Boolean bl = null;
  private boolean prefCalled = false;
  private static final IdentityHashMap<FontMetrics, Point2D.Float> FM_MAP = new IdentityHashMap(4);
  private static final Font SUBST_FONT = new Font("sansserif", 0, 11);
  private static Method BL_METHOD = null;
  private static Method BL_RES_METHOD = null;
  private static Method IMS_METHOD;

  public SwingComponentWrapper(Component paramComponent)
  {
    this.c = paramComponent;
  }

  public final int getBaseline(int paramInt1, int paramInt2)
  {
    if (BL_METHOD == null)
      return -1;
    try
    {
      Object[] arrayOfObject = { new Integer(paramInt1 < 0 ? this.c.getWidth() : paramInt1), new Integer(paramInt2 < 0 ? this.c.getHeight() : paramInt2) };
      return ((Integer)BL_METHOD.invoke(this.c, arrayOfObject)).intValue();
    }
    catch (Exception localException)
    {
    }
    return -1;
  }

  public final Object getComponent()
  {
    return this.c;
  }

  public final float getPixelUnitFactor(boolean paramBoolean)
  {
    Object localObject;
    switch (PlatformDefaults.getLogicalPixelBase())
    {
    case 100:
      Font localFont = this.c.getFont();
      FontMetrics localFontMetrics = this.c.getFontMetrics(localFont != null ? localFont : SUBST_FONT);
      Point2D.Float localFloat = (Point2D.Float)FM_MAP.get(localFontMetrics);
      if (localFloat == null)
      {
        localObject = localFontMetrics.getStringBounds("X", this.c.getGraphics());
        localFloat = new Point2D.Float((float)((Rectangle2D)localObject).getWidth() / 6.0F, (float)((Rectangle2D)localObject).getHeight() / 13.277344F);
        FM_MAP.put(localFontMetrics, localFloat);
      }
      return paramBoolean ? localFloat.x : localFloat.y;
    case 101:
      localObject = paramBoolean ? PlatformDefaults.getHorizontalScaleFactor() : PlatformDefaults.getVerticalScaleFactor();
      if (localObject != null)
        return ((Float)localObject).floatValue();
      return (paramBoolean ? getHorizontalScreenDPI() : getVerticalScreenDPI()) / PlatformDefaults.getDefaultDPI();
    }
    return 1.0F;
  }

  public final int getX()
  {
    return this.c.getX();
  }

  public final int getY()
  {
    return this.c.getY();
  }

  public final int getHeight()
  {
    return this.c.getHeight();
  }

  public final int getWidth()
  {
    return this.c.getWidth();
  }

  public final int getScreenLocationX()
  {
    Point localPoint = new Point();
    SwingUtilities.convertPointToScreen(localPoint, this.c);
    return localPoint.x;
  }

  public final int getScreenLocationY()
  {
    Point localPoint = new Point();
    SwingUtilities.convertPointToScreen(localPoint, this.c);
    return localPoint.y;
  }

  public final int getMinimumHeight(int paramInt)
  {
    if (!this.prefCalled)
    {
      this.c.getPreferredSize();
      this.prefCalled = true;
    }
    return this.c.getMinimumSize().height;
  }

  public final int getMinimumWidth(int paramInt)
  {
    if (!this.prefCalled)
    {
      this.c.getPreferredSize();
      this.prefCalled = true;
    }
    return this.c.getMinimumSize().width;
  }

  public final int getPreferredHeight(int paramInt)
  {
    if ((this.c.getWidth() == 0) && (this.c.getHeight() == 0) && (paramInt != -1))
      this.c.setBounds(this.c.getX(), this.c.getY(), paramInt, 1);
    return this.c.getPreferredSize().height;
  }

  public final int getPreferredWidth(int paramInt)
  {
    if ((this.c.getWidth() == 0) && (this.c.getHeight() == 0) && (paramInt != -1))
      this.c.setBounds(this.c.getX(), this.c.getY(), 1, paramInt);
    return this.c.getPreferredSize().width;
  }

  public final int getMaximumHeight(int paramInt)
  {
    if (!isMaxSet(this.c))
      return 32767;
    return this.c.getMaximumSize().height;
  }

  public final int getMaximumWidth(int paramInt)
  {
    if (!isMaxSet(this.c))
      return 32767;
    return this.c.getMaximumSize().width;
  }

  private boolean isMaxSet(Component paramComponent)
  {
    if (IMS_METHOD != null)
      try
      {
        return ((Boolean)IMS_METHOD.invoke(paramComponent, (Object[])null)).booleanValue();
      }
      catch (Exception localException)
      {
        IMS_METHOD = null;
      }
    return isMaxSizeSetOn1_4();
  }

  public final ContainerWrapper getParent()
  {
    Container localContainer = this.c.getParent();
    return localContainer != null ? new SwingContainerWrapper(localContainer) : null;
  }

  public final int getHorizontalScreenDPI()
  {
    return PlatformDefaults.getDefaultDPI();
  }

  public final int getVerticalScreenDPI()
  {
    return PlatformDefaults.getDefaultDPI();
  }

  public final int getScreenWidth()
  {
    try
    {
      return this.c.getToolkit().getScreenSize().width;
    }
    catch (HeadlessException localHeadlessException)
    {
    }
    return 1024;
  }

  public final int getScreenHeight()
  {
    try
    {
      return this.c.getToolkit().getScreenSize().height;
    }
    catch (HeadlessException localHeadlessException)
    {
    }
    return 768;
  }

  public final boolean hasBaseline()
  {
    if (this.bl == null)
      try
      {
        if ((BL_RES_METHOD == null) || (BL_RES_METHOD.invoke(this.c, new Object[0]).toString().equals("OTHER")))
        {
          this.bl = Boolean.FALSE;
        }
        else
        {
          Dimension localDimension = this.c.getMinimumSize();
          this.bl = new Boolean(getBaseline(localDimension.width, localDimension.height) > -1);
        }
      }
      catch (Throwable localThrowable)
      {
        this.bl = Boolean.FALSE;
      }
    return this.bl.booleanValue();
  }

  public final String getLinkId()
  {
    return this.c.getName();
  }

  public final void setBounds(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    this.c.setBounds(paramInt1, paramInt2, paramInt3, paramInt4);
  }

  public boolean isVisible()
  {
    return this.c.isVisible();
  }

  public final int[] getVisualPadding()
  {
    if ((vp) && ((this.c instanceof JTabbedPane)) && (UIManager.getLookAndFeel().getClass().getName().endsWith("WindowsLookAndFeel")))
      return new int[] { -1, 0, 2, 2 };
    return null;
  }

  public static boolean isMaxSizeSetOn1_4()
  {
    return maxSet;
  }

  public static void setMaxSizeSetOn1_4(boolean paramBoolean)
  {
    maxSet = paramBoolean;
  }

  public static boolean isVisualPaddingEnabled()
  {
    return vp;
  }

  public static void setVisualPaddingEnabled(boolean paramBoolean)
  {
    vp = paramBoolean;
  }

  public final void paintDebugOutline()
  {
    if (!this.c.isShowing())
      return;
    Graphics2D localGraphics2D = (Graphics2D)this.c.getGraphics();
    if (localGraphics2D == null)
      return;
    localGraphics2D.setPaint(DB_COMP_OUTLINE);
    localGraphics2D.setStroke(new BasicStroke(1.0F, 2, 0, 10.0F, new float[] { 2.0F, 4.0F }, 0.0F));
    localGraphics2D.drawRect(0, 0, getWidth() - 1, getHeight() - 1);
  }

  public int getComponetType(boolean paramBoolean)
  {
    if (this.compType == -1)
      this.compType = checkType(paramBoolean);
    return this.compType;
  }

  public int getLayoutHashCode()
  {
    Dimension localDimension = this.c.getMaximumSize();
    int i = localDimension.width + (localDimension.height << 5);
    localDimension = this.c.getPreferredSize();
    i += (localDimension.width << 10) + (localDimension.height << 15);
    localDimension = this.c.getMinimumSize();
    i += (localDimension.width << 20) + (localDimension.height << 25);
    if (this.c.isVisible())
      i += 1324511;
    String str = getLinkId();
    if (str != null)
      i += str.hashCode();
    return i;
  }

  private final int checkType(boolean paramBoolean)
  {
    Component localComponent = this.c;
    if (paramBoolean)
      if ((localComponent instanceof JScrollPane))
        localComponent = ((JScrollPane)localComponent).getViewport().getView();
      else if ((localComponent instanceof ScrollPane))
        localComponent = ((ScrollPane)localComponent).getComponent(0);
    if (((localComponent instanceof JTextField)) || ((localComponent instanceof TextField)))
      return 3;
    if (((localComponent instanceof JLabel)) || ((localComponent instanceof Label)))
      return 2;
    if (((localComponent instanceof JToggleButton)) || ((localComponent instanceof Checkbox)))
      return 16;
    if (((localComponent instanceof AbstractButton)) || ((localComponent instanceof Button)))
      return 5;
    if (((localComponent instanceof JComboBox)) || ((localComponent instanceof Choice)))
      return 2;
    if (((localComponent instanceof JTextComponent)) || ((localComponent instanceof TextComponent)))
      return 4;
    if (((localComponent instanceof JPanel)) || ((localComponent instanceof Canvas)))
      return 10;
    if (((localComponent instanceof JList)) || ((localComponent instanceof List)))
      return 6;
    if ((localComponent instanceof JTable))
      return 7;
    if ((localComponent instanceof JSeparator))
      return 18;
    if ((localComponent instanceof JSpinner))
      return 13;
    if ((localComponent instanceof JProgressBar))
      return 14;
    if ((localComponent instanceof JSlider))
      return 12;
    if ((localComponent instanceof JScrollPane))
      return 8;
    if (((localComponent instanceof JScrollBar)) || ((localComponent instanceof Scrollbar)))
      return 17;
    if ((localComponent instanceof Container))
      return 1;
    return 0;
  }

  public final int hashCode()
  {
    return getComponent().hashCode();
  }

  public final boolean equals(Object paramObject)
  {
    if (!(paramObject instanceof ComponentWrapper))
      return false;
    return getComponent().equals(((ComponentWrapper)paramObject).getComponent());
  }

  static
  {
    try
    {
      BL_METHOD = Component.class.getDeclaredMethod("getBaseline", new Class[] { Integer.TYPE, Integer.TYPE });
      BL_RES_METHOD = Component.class.getDeclaredMethod("getBaselineResizeBehavior", new Class[0]);
    }
    catch (Throwable localThrowable1)
    {
    }
    IMS_METHOD = null;
    try
    {
      IMS_METHOD = Component.class.getDeclaredMethod("isMaximumSizeSet", (Class[])null);
    }
    catch (Throwable localThrowable2)
    {
    }
  }
}

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     net.miginfocom.swing.SwingComponentWrapper
 * JD-Core Version:    0.6.0
 */