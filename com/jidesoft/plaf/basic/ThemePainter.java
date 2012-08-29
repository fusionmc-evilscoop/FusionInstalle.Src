package com.jidesoft.plaf.basic;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.Shape;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JComponent;

public abstract interface ThemePainter
{
  public static final int STATE_DEFAULT = 0;
  public static final int STATE_PRESSED = 1;
  public static final int STATE_ROLLOVER = 2;
  public static final int STATE_SELECTED = 3;
  public static final int STATE_DISABLE = 4;
  public static final int STATE_DISABLE_SELECTED = 5;
  public static final int STATE_DISABLE_ROLLOVER = 6;
  public static final int STATE_INACTIVE_ROLLOVER = 7;

  public abstract void paintSelectedMenu(JComponent paramJComponent, Graphics paramGraphics, Rectangle paramRectangle, int paramInt1, int paramInt2);

  public abstract void paintButtonBackground(JComponent paramJComponent, Graphics paramGraphics, Rectangle paramRectangle, int paramInt1, int paramInt2);

  public abstract void paintButtonBackground(JComponent paramJComponent, Graphics paramGraphics, Rectangle paramRectangle, int paramInt1, int paramInt2, boolean paramBoolean);

  public abstract void paintMenuItemBackground(JComponent paramJComponent, Graphics paramGraphics, Rectangle paramRectangle, int paramInt1, int paramInt2);

  public abstract void paintMenuItemBackground(JComponent paramJComponent, Graphics paramGraphics, Rectangle paramRectangle, int paramInt1, int paramInt2, boolean paramBoolean);

  public abstract void paintChevronBackground(JComponent paramJComponent, Graphics paramGraphics, Rectangle paramRectangle, int paramInt1, int paramInt2);

  public abstract void paintDividerBackground(JComponent paramJComponent, Graphics paramGraphics, Rectangle paramRectangle, int paramInt1, int paramInt2);

  public abstract void paintCommandBarBackground(JComponent paramJComponent, Graphics paramGraphics, Rectangle paramRectangle, int paramInt1, int paramInt2);

  public abstract void paintFloatingCommandBarBackground(JComponent paramJComponent, Graphics paramGraphics, Rectangle paramRectangle, int paramInt1, int paramInt2);

  public abstract void paintMenuShadow(JComponent paramJComponent, Graphics paramGraphics, Rectangle paramRectangle, int paramInt1, int paramInt2);

  public abstract void paintGripper(JComponent paramJComponent, Graphics paramGraphics, Rectangle paramRectangle, int paramInt1, int paramInt2);

  public abstract void paintChevronMore(JComponent paramJComponent, Graphics paramGraphics, Rectangle paramRectangle, int paramInt1, int paramInt2);

  public abstract void paintChevronOption(JComponent paramJComponent, Graphics paramGraphics, Rectangle paramRectangle, int paramInt1, int paramInt2);

  public abstract void paintFloatingChevronOption(JComponent paramJComponent, Graphics paramGraphics, Rectangle paramRectangle, int paramInt1, int paramInt2);

  public abstract void paintContentBackground(JComponent paramJComponent, Graphics paramGraphics, Rectangle paramRectangle, int paramInt1, int paramInt2);

  public abstract void paintStatusBarBackground(JComponent paramJComponent, Graphics paramGraphics, Rectangle paramRectangle, int paramInt1, int paramInt2);

  public abstract void paintCommandBarTitlePane(JComponent paramJComponent, Graphics paramGraphics, Rectangle paramRectangle, int paramInt1, int paramInt2);

  public abstract void paintDockableFrameBackground(JComponent paramJComponent, Graphics paramGraphics, Rectangle paramRectangle, int paramInt1, int paramInt2);

  public abstract void paintDockableFrameTitlePane(JComponent paramJComponent, Graphics paramGraphics, Rectangle paramRectangle, int paramInt1, int paramInt2);

  public abstract void paintCollapsiblePaneTitlePaneBackground(JComponent paramJComponent, Graphics paramGraphics, Rectangle paramRectangle, int paramInt1, int paramInt2);

  public abstract void paintCollapsiblePaneTitlePaneBackgroundEmphasized(JComponent paramJComponent, Graphics paramGraphics, Rectangle paramRectangle, int paramInt1, int paramInt2);

  public abstract void paintCollapsiblePanesBackground(JComponent paramJComponent, Graphics paramGraphics, Rectangle paramRectangle, int paramInt1, int paramInt2);

  public abstract void paintCollapsiblePaneTitlePaneBackgroundPlainEmphasized(JComponent paramJComponent, Graphics paramGraphics, Rectangle paramRectangle, int paramInt1, int paramInt2);

  public abstract void paintCollapsiblePaneTitlePaneBackgroundPlain(JComponent paramJComponent, Graphics paramGraphics, Rectangle paramRectangle, int paramInt1, int paramInt2);

  public abstract void paintCollapsiblePaneTitlePaneBackgroundSeparatorEmphasized(JComponent paramJComponent, Graphics paramGraphics, Rectangle paramRectangle, int paramInt1, int paramInt2);

  public abstract void paintCollapsiblePaneTitlePaneBackgroundSeparator(JComponent paramJComponent, Graphics paramGraphics, Rectangle paramRectangle, int paramInt1, int paramInt2);

  public abstract void paintTabAreaBackground(JComponent paramJComponent, Graphics paramGraphics, Rectangle paramRectangle, int paramInt1, int paramInt2);

  public abstract void paintTabBackground(JComponent paramJComponent, Graphics paramGraphics, Shape paramShape, Color[] paramArrayOfColor, int paramInt1, int paramInt2);

  public abstract void paintSidePaneItemBackground(JComponent paramJComponent, Graphics paramGraphics, Rectangle paramRectangle, Color[] paramArrayOfColor, int paramInt1, int paramInt2);

  public abstract void paintTabContentBorder(JComponent paramJComponent, Graphics paramGraphics, Rectangle paramRectangle, int paramInt1, int paramInt2);

  public abstract void paintHeaderBoxBackground(JComponent paramJComponent, Graphics paramGraphics, Rectangle paramRectangle, int paramInt1, int paramInt2);

  public abstract void paintToolBarSeparator(JComponent paramJComponent, Graphics paramGraphics, Rectangle paramRectangle, int paramInt1, int paramInt2);

  public abstract void paintStatusBarSeparator(JComponent paramJComponent, Graphics paramGraphics, Rectangle paramRectangle, int paramInt1, int paramInt2);

  public abstract void paintPopupMenuSeparator(JComponent paramJComponent, Graphics paramGraphics, Rectangle paramRectangle, int paramInt1, int paramInt2);

  public abstract void paintSortableTableHeaderColumn(JComponent paramJComponent, Graphics paramGraphics, Rectangle paramRectangle, int paramInt1, int paramInt2, int paramInt3, Icon paramIcon, int paramInt4, Color paramColor, boolean paramBoolean);

  public abstract void fillBackground(JComponent paramJComponent, Graphics paramGraphics, Rectangle paramRectangle, int paramInt1, int paramInt2, Color paramColor);

  public abstract Color getMenuItemBorderColor();

  public abstract Color getGripperForeground();

  public abstract Color getGripperForegroundLt();

  public abstract Color getSeparatorForeground();

  public abstract Color getSeparatorForegroundLt();

  public abstract Color getCollapsiblePaneContentBackground();

  public abstract Color getCollapsiblePaneTitleForeground();

  public abstract Color getCollapsiblePaneTitleForegroundEmphasized();

  public abstract Color getCollapsiblePaneFocusTitleForeground();

  public abstract Color getCollapsiblePaneFocusTitleForegroundEmphasized();

  public abstract ImageIcon getCollapsiblePaneUpIcon();

  public abstract ImageIcon getCollapsiblePaneDownIcon();

  public abstract ImageIcon getCollapsiblePaneUpIconEmphasized();

  public abstract ImageIcon getCollapsiblePaneDownIconEmphasized();

  public abstract ImageIcon getCollapsiblePaneTitleButtonBackground();

  public abstract ImageIcon getCollapsiblePaneTitleButtonBackgroundEmphasized();

  public abstract ImageIcon getCollapsiblePaneUpMask();

  public abstract ImageIcon getCollapsiblePaneDownMask();

  public abstract Color getBackgroundDk();

  public abstract Color getBackgroundLt();

  public abstract Color getSelectionSelectedDk();

  public abstract Color getSelectionSelectedLt();

  public abstract Color getMenuItemBackground();

  public abstract Color getCommandBarTitleBarBackground();

  public abstract Color getColor(Object paramObject);

  public abstract Color getControl();

  public abstract Color getControlLt();

  public abstract Color getControlDk();

  public abstract Color getControlShadow();

  public abstract Color getDockableFrameTitleBarActiveForeground();

  public abstract Color getDockableFrameTitleBarInactiveForeground();

  public abstract Color getTitleBarBackground();

  public abstract Color getOptionPaneBannerDk();

  public abstract Color getOptionPaneBannerLt();

  public abstract Color getOptionPaneBannerForeground();

  public abstract Color getTabbedPaneSelectDk();

  public abstract Color getTabbedPaneSelectLt();

  public abstract Color getTabAreaBackgroundDk();

  public abstract Color getTabAreaBackgroundLt();
}

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     com.jidesoft.plaf.basic.ThemePainter
 * JD-Core Version:    0.6.0
 */