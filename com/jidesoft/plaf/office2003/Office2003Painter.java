/*      */ package com.jidesoft.plaf.office2003;
/*      */ 
/*      */ import com.jidesoft.icons.IconsFactory;
/*      */ import com.jidesoft.plaf.LookAndFeelFactory;
/*      */ import com.jidesoft.plaf.UIDefaultsLookup;
/*      */ import com.jidesoft.plaf.XPUtils;
/*      */ import com.jidesoft.plaf.basic.BasicPainter;
/*      */ import com.jidesoft.plaf.basic.ThemePainter;
/*      */ import com.jidesoft.swing.ComponentStateSupport;
/*      */ import com.jidesoft.swing.JideSwingUtilities;
/*      */ import com.jidesoft.swing.JideTabbedPane;
/*      */ import com.jidesoft.utils.ColorUtils;
/*      */ import com.jidesoft.utils.SystemInfo;
/*      */ import java.awt.Color;
/*      */ import java.awt.ComponentOrientation;
/*      */ import java.awt.Graphics;
/*      */ import java.awt.Graphics2D;
/*      */ import java.awt.Insets;
/*      */ import java.awt.Rectangle;
/*      */ import java.awt.Toolkit;
/*      */ import java.awt.geom.RoundRectangle2D.Float;
/*      */ import java.beans.PropertyChangeEvent;
/*      */ import java.beans.PropertyChangeListener;
/*      */ import java.util.Collection;
/*      */ import java.util.Map;
/*      */ import java.util.TreeMap;
/*      */ import javax.swing.ImageIcon;
/*      */ import javax.swing.JComponent;
/*      */ import javax.swing.UIManager;
/*      */ import javax.swing.border.Border;
/*      */ import javax.swing.plaf.ColorUIResource;
/*      */ import javax.swing.plaf.UIResource;
/*      */ 
/*      */ public class Office2003Painter extends BasicPainter
/*      */ {
/*      */   private static Office2003Painter _instance;
/*   80 */   private String _colorName = "Default";
/*      */ 
/*   82 */   private static boolean _native = (SystemInfo.isWindowsXP()) || (SystemInfo.isWindowsVistaAbove());
/*      */ 
/*   84 */   private static Office2003Theme _defaultTheme = new DefaultOffice2003Theme();
/*   85 */   private static Office2003Theme _normalTheme = new Office2003Theme("Gray");
/*   86 */   private static Office2003Theme _blueTheme = new Office2003Theme("NormalColor");
/*   87 */   private static Office2003Theme _homeSteadTheme = new Office2003Theme("HomeStead");
/*   88 */   private static Office2003Theme _metallicTheme = new Office2003Theme("Metallic");
/*      */ 
/*   90 */   private static Map<String, Office2003Theme> _themeCache = new TreeMap();
/*      */ 
/*      */   public static ThemePainter getInstance()
/*      */   {
/*   37 */     if (_instance == null) {
/*   38 */       _instance = new Office2003Painter();
/*   39 */       PropertyChangeListener listener = new PropertyChangeListener() {
/*      */         public void propertyChange(PropertyChangeEvent evt) {
/*   41 */           if (Office2003Painter.isNative())
/*   42 */             if ("win.xpstyle.colorName".equals(evt.getPropertyName())) {
/*   43 */               if (evt.getNewValue() != null) {
/*   44 */                 Office2003Painter._instance.setColorName((String)evt.getNewValue());
/*      */               }
/*      */               else {
/*   47 */                 Office2003Painter._instance.setColorName("");
/*      */               }
/*      */             }
/*   50 */             else if ("win.xpstyle.themeActive".equals(evt.getPropertyName()))
/*   51 */               if (evt.getNewValue().equals(Boolean.FALSE))
/*   52 */                 Office2003Painter._instance.setColorName("");
/*      */               else
/*   54 */                 Office2003Painter._instance.setColorName(XPUtils.getColorName());
/*      */         }
/*      */       };
/*   60 */       Toolkit.getDefaultToolkit().addPropertyChangeListener("win.xpstyle.colorName", listener);
/*   61 */       Toolkit.getDefaultToolkit().addPropertyChangeListener("win.xpstyle.themeActive", listener);
/*      */ 
/*   63 */       if (isNative()) {
/*      */         try {
/*   65 */           if (XPUtils.isXPStyleOn()) {
/*   66 */             _instance.setColorName(XPUtils.getColorName());
/*      */           }
/*      */           else
/*   69 */             _instance.setColorName("");
/*      */         }
/*      */         catch (UnsupportedOperationException e)
/*      */         {
/*   73 */           _instance.setColorName("");
/*      */         }
/*      */       }
/*      */     }
/*   77 */     return _instance;
/*      */   }
/*      */ 
/*      */   public void addTheme(Office2003Theme theme)
/*      */   {
/*  409 */     _themeCache.put(theme.getThemeName(), theme);
/*      */   }
/*      */ 
/*      */   public Office2003Theme getTheme(String themeName) {
/*  413 */     return (Office2003Theme)_themeCache.get(themeName);
/*      */   }
/*      */ 
/*      */   public void removeTheme(String themeName) {
/*  417 */     _themeCache.remove(themeName);
/*      */   }
/*      */ 
/*      */   public Collection<Office2003Theme> getAvailableThemes() {
/*  421 */     return _themeCache.values();
/*      */   }
/*      */ 
/*      */   public void installDefaults()
/*      */   {
/*  426 */     Boolean highContrast = Boolean.valueOf(UIManager.getBoolean("Theme.highContrast"));
/*  427 */     if (highContrast.booleanValue())
/*  428 */       super.installDefaults();
/*      */   }
/*      */ 
/*      */   public void uninstallDefaults()
/*      */   {
/*  434 */     Boolean highContrast = Boolean.valueOf(UIManager.getBoolean("Theme.highContrast"));
/*  435 */     if (highContrast.booleanValue())
/*  436 */       super.uninstallDefaults();
/*      */   }
/*      */ 
/*      */   public String getColorName()
/*      */   {
/*  441 */     return this._colorName;
/*      */   }
/*      */ 
/*      */   public void setColorName(String colorName) {
/*  445 */     this._colorName = colorName;
/*      */   }
/*      */ 
/*      */   public static boolean isNative() {
/*  449 */     return _native;
/*      */   }
/*      */ 
/*      */   public static void setNative(boolean aNative) {
/*  453 */     _native = aNative;
/*      */   }
/*      */ 
/*      */   public Office2003Theme getCurrentTheme() {
/*  457 */     if ((getColorName() == null) || (getColorName().trim().length() == 0) || (_themeCache.get(getColorName()) == null)) {
/*  458 */       return (Office2003Theme)_themeCache.get("Default");
/*      */     }
/*      */ 
/*  461 */     return (Office2003Theme)_themeCache.get(getColorName());
/*      */   }
/*      */ 
/*      */   public void paintButtonBackground(JComponent c, Graphics g, Rectangle rect, int orientation, int state, boolean showBorder)
/*      */   {
/*  467 */     Boolean highContrast = Boolean.valueOf(UIManager.getBoolean("Theme.highContrast"));
/*  468 */     if (highContrast.booleanValue()) {
/*  469 */       super.paintButtonBackground(c, g, rect, orientation, state, showBorder);
/*  470 */       return;
/*      */     }
/*      */ 
/*  473 */     Color startColor = null;
/*  474 */     Color endColor = null;
/*  475 */     Color background = null;
/*  476 */     switch (state) {
/*      */     case 0:
/*  478 */       background = c.getBackground();
/*  479 */       if (!(background instanceof UIResource)) {
/*  480 */         startColor = ColorUtils.getDerivedColor(background, 0.6F);
/*  481 */         endColor = ColorUtils.getDerivedColor(background, 0.4F);
/*  482 */         showBorder = false;
/*      */       }
/*      */       else {
/*  485 */         startColor = getCurrentTheme().getColor("controlLt");
/*  486 */         endColor = getCurrentTheme().getColor("controlDk");
/*      */       }
/*      */ 
/*  489 */       break;
/*      */     case 2:
/*  491 */       if ((c instanceof ComponentStateSupport)) {
/*  492 */         background = ((ComponentStateSupport)c).getBackgroundOfState(state);
/*      */       }
/*  494 */       if ((background != null) && (!(background instanceof UIResource))) {
/*  495 */         startColor = ColorUtils.getDerivedColor(background, 0.6F);
/*  496 */         endColor = ColorUtils.getDerivedColor(background, 0.4F);
/*      */       }
/*      */       else {
/*  499 */         startColor = getCurrentTheme().getColor("selection.RolloverLt");
/*  500 */         endColor = getCurrentTheme().getColor("selection.RolloverDk");
/*      */       }
/*  502 */       break;
/*      */     case 3:
/*  504 */       if ((c instanceof ComponentStateSupport)) {
/*  505 */         background = ((ComponentStateSupport)c).getBackgroundOfState(state);
/*      */       }
/*  507 */       if ((background != null) && (!(background instanceof UIResource))) {
/*  508 */         startColor = ColorUtils.getDerivedColor(background, 0.6F);
/*  509 */         endColor = ColorUtils.getDerivedColor(background, 0.4F);
/*      */       }
/*      */       else {
/*  512 */         startColor = getCurrentTheme().getColor("selection.SelectedLt");
/*  513 */         endColor = getCurrentTheme().getColor("selection.SelectedDk");
/*      */       }
/*  515 */       break;
/*      */     case 1:
/*  517 */       if ((c instanceof ComponentStateSupport)) {
/*  518 */         background = ((ComponentStateSupport)c).getBackgroundOfState(state);
/*      */       }
/*  520 */       if ((background != null) && (!(background instanceof UIResource))) {
/*  521 */         startColor = ColorUtils.getDerivedColor(background, 0.4F);
/*  522 */         endColor = ColorUtils.getDerivedColor(background, 0.6F);
/*      */       }
/*      */       else {
/*  525 */         startColor = getCurrentTheme().getColor("selection.PressedDk");
/*  526 */         endColor = getCurrentTheme().getColor("selection.PressedLt");
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/*  531 */     if ((startColor != null) && (endColor != null))
/*  532 */       paintBackground(c, (Graphics2D)g, rect, showBorder ? getCurrentTheme().getColor("selection.border") : null, startColor, endColor, orientation);
/*      */   }
/*      */ 
/*      */   protected void paintBackground(JComponent c, Graphics2D g2d, Rectangle rect, Color borderColor, Color startColor, Color endColor, int orientation)
/*      */   {
/*  537 */     if (borderColor != null) {
/*  538 */       if ((startColor != null) && (endColor != null)) {
/*  539 */         JideSwingUtilities.fillGradient(g2d, new Rectangle(rect.x, rect.y, rect.width, rect.height), startColor, endColor, orientation == 0);
/*      */       }
/*  541 */       boolean paintDefaultBorder = true;
/*  542 */       Object o = c.getClientProperty("JideButton.paintDefaultBorder");
/*  543 */       if ((o instanceof Boolean)) {
/*  544 */         paintDefaultBorder = ((Boolean)o).booleanValue();
/*      */       }
/*  546 */       if (paintDefaultBorder) {
/*  547 */         Color oldColor = g2d.getColor();
/*  548 */         g2d.setColor(borderColor);
/*  549 */         Object position = c.getClientProperty("JButton.segmentPosition");
/*  550 */         if ((position == null) || ("only".equals(position))) {
/*  551 */           g2d.drawRect(rect.x, rect.y, rect.width - 1, rect.height - 1);
/*      */         }
/*  553 */         else if ("first".equals(position)) {
/*  554 */           if (orientation == 0) {
/*  555 */             g2d.drawRect(rect.x, rect.y, rect.width, rect.height - 1);
/*      */           }
/*      */           else {
/*  558 */             g2d.drawRect(rect.x, rect.y, rect.width - 1, rect.height);
/*      */           }
/*      */         }
/*  561 */         else if ("middle".equals(position)) {
/*  562 */           if (orientation == 0) {
/*  563 */             g2d.drawRect(rect.x, rect.y, rect.width, rect.height - 1);
/*      */           }
/*      */           else {
/*  566 */             g2d.drawRect(rect.x, rect.y, rect.width - 1, rect.height);
/*      */           }
/*      */         }
/*  569 */         else if ("last".equals(position)) {
/*  570 */           if (orientation == 0) {
/*  571 */             g2d.drawRect(rect.x, rect.y, rect.width - 1, rect.height - 1);
/*      */           }
/*      */           else {
/*  574 */             g2d.drawRect(rect.x, rect.y, rect.width - 1, rect.height - 1);
/*      */           }
/*      */         }
/*  577 */         g2d.setColor(oldColor);
/*      */       }
/*      */ 
/*      */     }
/*  581 */     else if ((startColor != null) && (endColor != null)) {
/*  582 */       JideSwingUtilities.fillGradient(g2d, new Rectangle(rect.x, rect.y, rect.width, rect.height), startColor, endColor, orientation == 0);
/*      */     }
/*      */   }
/*      */ 
/*      */   public void paintChevronBackground(JComponent c, Graphics g, Rectangle rect, int orientation, int state)
/*      */   {
/*  589 */     Graphics2D g2d = (Graphics2D)g;
/*  590 */     if (state == 0) {
/*  591 */       paintChevron(c, g2d, getCurrentTheme().getColor("Chevron.backgroundLt"), getCurrentTheme().getColor("Chevron.backgroundDk"), rect, orientation);
/*      */     }
/*  593 */     else if (state == 2) {
/*  594 */       paintChevron(c, g2d, getCurrentTheme().getColor("selection.RolloverLt"), getCurrentTheme().getColor("selection.RolloverDk"), rect, orientation);
/*      */     }
/*  596 */     else if (state == 3) {
/*  597 */       paintChevron(c, g2d, getCurrentTheme().getColor("selection.SelectedDk"), getCurrentTheme().getColor("selection.SelectedLt"), rect, orientation);
/*      */     }
/*  599 */     else if (state == 1)
/*  600 */       paintChevron(c, g2d, getCurrentTheme().getColor("selection.PressedDk"), getCurrentTheme().getColor("selection.PressedLt"), rect, orientation);
/*      */   }
/*      */ 
/*      */   public void paintDividerBackground(JComponent c, Graphics g, Rectangle rect, int orientation, int state)
/*      */   {
/*  606 */     Graphics2D g2d = (Graphics2D)g;
/*  607 */     JideSwingUtilities.fillGradient(g2d, rect, getCurrentTheme().getColor("Divider.backgroundLt"), getCurrentTheme().getColor("Divider.backgroundDk"), true);
/*      */   }
/*      */ 
/*      */   protected void paintChevron(JComponent c, Graphics2D g2d, Color color1, Color color2, Rectangle rect, int orientation)
/*      */   {
/*  612 */     if (orientation == 0)
/*      */     {
/*  614 */       if (!c.getComponentOrientation().isLeftToRight()) {
/*  615 */         JideSwingUtilities.fillGradient(g2d, new Rectangle(rect.x, rect.y + 2, rect.width - 2, rect.height - 4), color1, color2, true);
/*  616 */         g2d.setColor(color1);
/*  617 */         g2d.drawLine(rect.x + 2, rect.y, rect.x + rect.width - 1, rect.y);
/*  618 */         g2d.drawLine(rect.x + 1, rect.y + 1, rect.x + rect.width - 2, rect.y + 1);
/*  619 */         g2d.setColor(color2);
/*  620 */         g2d.drawLine(rect.x + 1, rect.y + rect.height - 2, rect.x + rect.width - 2, rect.y + rect.height - 2);
/*  621 */         g2d.drawLine(rect.x + 2, rect.y + rect.height - 1, rect.x + rect.width - 1, rect.y + rect.height - 1);
/*      */       }
/*      */       else {
/*  624 */         JideSwingUtilities.fillGradient(g2d, new Rectangle(rect.x + 2, rect.y + 2, rect.width - 2, rect.height - 4), color1, color2, true);
/*  625 */         g2d.setColor(color1);
/*  626 */         g2d.drawLine(rect.x, rect.y, rect.x + rect.width - 3, rect.y);
/*  627 */         g2d.drawLine(rect.x + 1, rect.y + 1, rect.x + rect.width - 2, rect.y + 1);
/*  628 */         g2d.setColor(color2);
/*  629 */         g2d.drawLine(rect.x + 1, rect.y + rect.height - 2, rect.x + rect.width - 2, rect.y + rect.height - 2);
/*  630 */         g2d.drawLine(rect.x, rect.y + rect.height - 1, rect.x + rect.width - 3, rect.y + rect.height - 1);
/*      */       }
/*      */     }
/*      */     else
/*      */     {
/*  635 */       JideSwingUtilities.fillGradient(g2d, new Rectangle(rect.x + 2, rect.y + 2, rect.width - 4, rect.height - 2), color1, color2, false);
/*  636 */       g2d.setColor(color1);
/*  637 */       g2d.drawLine(rect.x, rect.y, rect.x, rect.y + rect.height - 3);
/*  638 */       g2d.drawLine(rect.x + 1, rect.y + 1, rect.x + 1, rect.y + rect.height - 2);
/*  639 */       g2d.setColor(color2);
/*  640 */       g2d.drawLine(rect.x + rect.width - 2, rect.y + 1, rect.x + rect.width - 2, rect.y + rect.height - 2);
/*  641 */       g2d.drawLine(rect.x + rect.width - 1, rect.y, rect.x + rect.width - 1, rect.y + rect.height - 3);
/*      */     }
/*      */   }
/*      */ 
/*      */   public Color getColor(Object key)
/*      */   {
/*  647 */     return getCurrentTheme().getColor(key);
/*      */   }
/*      */ 
/*      */   public void paintCommandBarBackground(JComponent c, Graphics g, Rectangle rect, int orientation, int state)
/*      */   {
/*  652 */     Boolean highContrast = Boolean.valueOf(UIManager.getBoolean("Theme.highContrast"));
/*  653 */     if (highContrast.booleanValue()) {
/*  654 */       super.paintCommandBarBackground(c, g, rect, orientation, state);
/*  655 */       return;
/*      */     }
/*      */ 
/*  658 */     Graphics2D g2d = (Graphics2D)g;
/*  659 */     JideSwingUtilities.fillGradient(g2d, new RoundRectangle2D.Float(rect.x, rect.y, rect.width, rect.height, 4.0F, 4.0F), getCurrentTheme().getColor("controlLt"), getCurrentTheme().getColor("controlDk"), orientation == 0);
/*      */ 
/*  661 */     g2d.setColor(getCurrentTheme().getColor("controlShadow"));
/*  662 */     if (orientation == 0) {
/*  663 */       g2d.drawLine(rect.x + 2, rect.y + rect.height - 1, rect.x + rect.width - 3, rect.y + rect.height - 1);
/*      */     }
/*      */     else
/*  666 */       g2d.drawLine(rect.x + rect.width - 1, rect.y + 2, rect.x + rect.width - 1, rect.y + rect.height - 3);
/*      */   }
/*      */ 
/*      */   public void paintFloatingCommandBarBackground(JComponent c, Graphics g, Rectangle rect, int orientation, int state)
/*      */   {
/*  672 */     Graphics2D g2d = (Graphics2D)g;
/*  673 */     JideSwingUtilities.fillGradient(g2d, rect, getCurrentTheme().getColor("controlLt"), getCurrentTheme().getColor("controlDk"), orientation == 0);
/*      */   }
/*      */ 
/*      */   public void paintMenuShadow(JComponent c, Graphics g, Rectangle rect, int orientation, int state)
/*      */   {
/*  679 */     Graphics2D g2d = (Graphics2D)g;
/*  680 */     if (c.getComponentOrientation().isLeftToRight()) {
/*  681 */       JideSwingUtilities.fillGradient(g2d, rect, getCurrentTheme().getColor("controlLt"), getCurrentTheme().getColor("controlDk"), orientation != 0);
/*      */     }
/*      */     else
/*  684 */       JideSwingUtilities.fillGradient(g2d, rect, getCurrentTheme().getColor("controlDk"), getCurrentTheme().getColor("controlLt"), orientation != 0);
/*      */   }
/*      */ 
/*      */   public Color getControl()
/*      */   {
/*  690 */     return getCurrentTheme().getColor("control");
/*      */   }
/*      */ 
/*      */   public Color getControlLt()
/*      */   {
/*  695 */     return getCurrentTheme().getColor("controlLt");
/*      */   }
/*      */ 
/*      */   public Color getControlDk()
/*      */   {
/*  700 */     return getCurrentTheme().getColor("controlDk");
/*      */   }
/*      */ 
/*      */   public Color getControlShadow()
/*      */   {
/*  705 */     return getCurrentTheme().getColor("controlShadow");
/*      */   }
/*      */ 
/*      */   public Color getGripperForeground()
/*      */   {
/*  710 */     return getCurrentTheme().getColor("Gripper.foreground");
/*      */   }
/*      */ 
/*      */   public Color getGripperForegroundLt()
/*      */   {
/*  715 */     return getCurrentTheme().getColor("Gripper.foregroundLt");
/*      */   }
/*      */ 
/*      */   public Color getSeparatorForeground()
/*      */   {
/*  720 */     return getCurrentTheme().getColor("Separator.foreground");
/*      */   }
/*      */ 
/*      */   public Color getSeparatorForegroundLt()
/*      */   {
/*  725 */     return getCurrentTheme().getColor("Separator.foregroundLt");
/*      */   }
/*      */ 
/*      */   public Color getCollapsiblePaneContentBackground()
/*      */   {
/*  730 */     return getCurrentTheme().getColor("CollapsiblePane.contentBackground");
/*      */   }
/*      */ 
/*      */   public Color getCollapsiblePaneTitleForeground()
/*      */   {
/*  735 */     return getCurrentTheme().getColor("CollapsiblePaneTitlePane.foreground");
/*      */   }
/*      */ 
/*      */   public Color getCollapsiblePaneFocusTitleForeground()
/*      */   {
/*  740 */     return getCurrentTheme().getColor("CollapsiblePaneTitlePane.foreground.focus");
/*      */   }
/*      */ 
/*      */   public Color getCollapsiblePaneTitleForegroundEmphasized()
/*      */   {
/*  745 */     return getCurrentTheme().getColor("CollapsiblePaneTitlePane.foreground.emphasized");
/*      */   }
/*      */ 
/*      */   public Color getCollapsiblePaneFocusTitleForegroundEmphasized()
/*      */   {
/*  750 */     return getCurrentTheme().getColor("CollapsiblePaneTitlePane.foreground.focus.emphasized");
/*      */   }
/*      */ 
/*      */   public ImageIcon getCollapsiblePaneUpIcon()
/*      */   {
/*  755 */     return (ImageIcon)getCurrentTheme().getIcon("CollapsiblePane.upIcon");
/*      */   }
/*      */ 
/*      */   public ImageIcon getCollapsiblePaneDownIcon()
/*      */   {
/*  760 */     return (ImageIcon)getCurrentTheme().getIcon("CollapsiblePane.downIcon");
/*      */   }
/*      */ 
/*      */   public ImageIcon getCollapsiblePaneUpIconEmphasized()
/*      */   {
/*  765 */     return (ImageIcon)getCurrentTheme().getIcon("CollapsiblePane.upIcon.emphasized");
/*      */   }
/*      */ 
/*      */   public ImageIcon getCollapsiblePaneDownIconEmphasized()
/*      */   {
/*  770 */     return (ImageIcon)getCurrentTheme().getIcon("CollapsiblePane.downIcon.emphasized");
/*      */   }
/*      */ 
/*      */   public ImageIcon getCollapsiblePaneTitleButtonBackground()
/*      */   {
/*  775 */     return (ImageIcon)getCurrentTheme().getIcon("CollapsiblePane.titleButtonBackground");
/*      */   }
/*      */ 
/*      */   public ImageIcon getCollapsiblePaneTitleButtonBackgroundEmphasized()
/*      */   {
/*  780 */     return (ImageIcon)getCurrentTheme().getIcon("CollapsiblePane.titleButtonBackground.emphasized");
/*      */   }
/*      */ 
/*      */   public ImageIcon getCollapsiblePaneUpMask()
/*      */   {
/*  785 */     return (ImageIcon)getCurrentTheme().getIcon("CollapsiblePane.upMask");
/*      */   }
/*      */ 
/*      */   public ImageIcon getCollapsiblePaneDownMask()
/*      */   {
/*  790 */     return (ImageIcon)getCurrentTheme().getIcon("CollapsiblePane.downMask");
/*      */   }
/*      */ 
/*      */   public Color getBackgroundDk()
/*      */   {
/*  795 */     return getCurrentTheme().getColor("backgroundDk");
/*      */   }
/*      */ 
/*      */   public Color getBackgroundLt()
/*      */   {
/*  800 */     return getCurrentTheme().getColor("backgroundLt");
/*      */   }
/*      */ 
/*      */   public Color getSelectionSelectedDk()
/*      */   {
/*  805 */     return getCurrentTheme().getColor("selection.SelectedDk");
/*      */   }
/*      */ 
/*      */   public Color getSelectionSelectedLt()
/*      */   {
/*  810 */     return getCurrentTheme().getColor("selection.SelectedLt");
/*      */   }
/*      */ 
/*      */   public Color getMenuItemBorderColor()
/*      */   {
/*  815 */     return getCurrentTheme().getColor("selection.border");
/*      */   }
/*      */ 
/*      */   public Color getMenuItemBackground()
/*      */   {
/*  820 */     return getCurrentTheme().getColor("MenuItem.background");
/*      */   }
/*      */ 
/*      */   public Color getCommandBarTitleBarBackground()
/*      */   {
/*  825 */     return getCurrentTheme().getColor("CommandBar.titleBarBackground");
/*      */   }
/*      */ 
/*      */   public Color getDockableFrameTitleBarActiveForeground()
/*      */   {
/*  830 */     return getCurrentTheme().getColor("DockableFrameTitlePane.activeForeground");
/*      */   }
/*      */ 
/*      */   public Color getDockableFrameTitleBarInactiveForeground()
/*      */   {
/*  835 */     return getCurrentTheme().getColor("DockableFrameTitlePane.inactiveForeground");
/*      */   }
/*      */ 
/*      */   public Color getTitleBarBackground()
/*      */   {
/*  840 */     return getCurrentTheme().getColor("DockableFrameTitlePane.backgroundDk");
/*      */   }
/*      */ 
/*      */   public Color getOptionPaneBannerForeground()
/*      */   {
/*  845 */     return getCurrentTheme().getColor("OptionPane.bannerForeground");
/*      */   }
/*      */ 
/*      */   public Color getTabbedPaneSelectDk()
/*      */   {
/*  850 */     return getCurrentTheme().getColor("TabbedPane.selectDk");
/*      */   }
/*      */ 
/*      */   public Color getTabbedPaneSelectLt()
/*      */   {
/*  855 */     return getCurrentTheme().getColor("TabbedPane.selectLt");
/*      */   }
/*      */ 
/*      */   public Color getOptionPaneBannerDk()
/*      */   {
/*  860 */     return getCurrentTheme().getColor("OptionPane.bannerDk");
/*      */   }
/*      */ 
/*      */   public Color getOptionPaneBannerLt()
/*      */   {
/*  865 */     return getCurrentTheme().getColor("OptionPane.bannerLt");
/*      */   }
/*      */ 
/*      */   public void paintContentBackground(JComponent c, Graphics g, Rectangle rect, int orientation, int state)
/*      */   {
/*  870 */     Graphics2D g2d = (Graphics2D)g;
/*  871 */     JideSwingUtilities.fillGradient(g2d, rect, getBackgroundDk(), getBackgroundLt(), false);
/*      */   }
/*      */ 
/*      */   public void paintGripper(JComponent c, Graphics g, Rectangle rect, int orientation, int state)
/*      */   {
/*  876 */     if (rect.width > 30) {
/*  877 */       orientation = 1;
/*      */     }
/*  879 */     else if (rect.height > 30) {
/*  880 */       orientation = 0;
/*      */     }
/*      */ 
/*  883 */     int h = orientation == 0 ? rect.height : rect.width;
/*  884 */     int count = Math.min(9, (h - 6) / 4);
/*  885 */     int y = rect.y;
/*  886 */     int x = rect.x;
/*      */ 
/*  888 */     if (orientation == 0) {
/*  889 */       y += rect.height / 2 - count * 2;
/*  890 */       x += rect.width / 2 - 1;
/*      */     }
/*      */     else {
/*  893 */       x += rect.width / 2 - count * 2;
/*  894 */       y += rect.height / 2 - 1;
/*      */     }
/*      */ 
/*  897 */     for (int i = 0; i < count; i++) {
/*  898 */       g.setColor(getGripperForegroundLt());
/*  899 */       g.fillRect(x + 1, y + 1, 2, 2);
/*  900 */       g.setColor(getGripperForeground());
/*  901 */       g.fillRect(x, y, 2, 2);
/*  902 */       if (orientation == 0) {
/*  903 */         y += 4;
/*      */       }
/*      */       else
/*  906 */         x += 4;
/*      */     }
/*      */   }
/*      */ 
/*      */   public void paintChevronMore(JComponent c, Graphics g, Rectangle rect, int orientation, int state)
/*      */   {
/*  913 */     int startX = rect.x + 4;
/*  914 */     int startY = rect.x + 5;
/*      */ 
/*  916 */     int oppositeOrientation = orientation == 0 ? 1 : 0;
/*      */ 
/*  918 */     if (orientation == 0) {
/*  919 */       if (!c.getComponentOrientation().isLeftToRight()) {
/*  920 */         startX = rect.width - 8;
/*  921 */         startX--;
/*  922 */         startY++;
/*  923 */         JideSwingUtilities.paintArrow(c, g, Color.WHITE, startX, startY, 3, oppositeOrientation);
/*  924 */         startX -= 4;
/*  925 */         JideSwingUtilities.paintArrow(c, g, Color.WHITE, startX, startY, 3, oppositeOrientation);
/*  926 */         startX++;
/*  927 */         startX += 4;
/*  928 */         startY--;
/*  929 */         JideSwingUtilities.paintArrow(c, g, Color.BLACK, startX, startY, 3, oppositeOrientation);
/*  930 */         startX -= 4;
/*  931 */         JideSwingUtilities.paintArrow(c, g, Color.BLACK, startX, startY, 3, oppositeOrientation);
/*      */       }
/*      */       else {
/*  934 */         startX++;
/*  935 */         startY++;
/*  936 */         JideSwingUtilities.paintArrow(g, Color.WHITE, startX, startY, 3, oppositeOrientation);
/*  937 */         startX += 4;
/*  938 */         JideSwingUtilities.paintArrow(g, Color.WHITE, startX, startY, 3, oppositeOrientation);
/*  939 */         startX--;
/*  940 */         startX -= 4;
/*  941 */         startY--;
/*  942 */         JideSwingUtilities.paintArrow(g, Color.BLACK, startX, startY, 3, oppositeOrientation);
/*  943 */         startX += 4;
/*  944 */         JideSwingUtilities.paintArrow(g, Color.BLACK, startX, startY, 3, oppositeOrientation);
/*      */       }
/*      */     }
/*  947 */     else if (orientation == 1) {
/*  948 */       startX++;
/*  949 */       startY++;
/*  950 */       JideSwingUtilities.paintArrow(g, Color.WHITE, startX, startY, 3, oppositeOrientation);
/*  951 */       startY += 4;
/*  952 */       JideSwingUtilities.paintArrow(g, Color.WHITE, startX, startY, 3, oppositeOrientation);
/*  953 */       startX--;
/*  954 */       startY--;
/*  955 */       startY -= 4;
/*  956 */       JideSwingUtilities.paintArrow(g, Color.BLACK, startX, startY, 3, oppositeOrientation);
/*  957 */       startY += 4;
/*  958 */       JideSwingUtilities.paintArrow(g, Color.BLACK, startX, startY, 3, oppositeOrientation);
/*      */     }
/*      */   }
/*      */ 
/*      */   public void paintChevronOption(JComponent c, Graphics g, Rectangle rect, int orientation, int state)
/*      */   {
/*      */     int startY;
/*  966 */     if (orientation == 0)
/*      */     {
/*      */       int startY;
/*  967 */       if (!c.getComponentOrientation().isLeftToRight()) {
/*  968 */         int startX = rect.x + 2;
/*  969 */         startY = rect.y + rect.height - 10;
/*      */       }
/*      */       else {
/*  972 */         int startX = rect.x + rect.width - 8;
/*  973 */         startY = rect.y + rect.height - 10;
/*      */       }
/*      */     }
/*      */     else
/*      */     {
/*      */       int startY;
/*  976 */       if (orientation == 1) {
/*  977 */         int startX = rect.x + rect.width - 10;
/*  978 */         startY = rect.y + rect.height - 8;
/*      */       }
/*      */       else {
/*  981 */         return;
/*      */       }
/*      */     }
/*      */     int startY;
/*      */     int startX;
/*  984 */     startX++;
/*  985 */     startY++;
/*  986 */     g.setColor(Color.WHITE);
/*  987 */     paintDown(g, startX, startY, orientation);
/*      */ 
/*  989 */     startX--;
/*  990 */     startY--;
/*  991 */     g.setColor(Color.BLACK);
/*  992 */     paintDown(g, startX, startY, orientation);
/*      */   }
/*      */ 
/*      */   private void paintDown(Graphics g, int startX, int startY, int orientation) {
/*  996 */     if (orientation == 0) {
/*  997 */       g.drawLine(startX, startY, startX + 4, startY);
/*  998 */       JideSwingUtilities.paintArrow(g, g.getColor(), startX, startY + 3, 5, 0);
/*      */     }
/*      */     else {
/* 1001 */       g.drawLine(startX, startY, startX, startY + 4);
/* 1002 */       JideSwingUtilities.paintArrow(g, g.getColor(), startX + 3, startY, 5, orientation);
/*      */     }
/*      */   }
/*      */ 
/*      */   public void paintDockableFrameBackground(JComponent c, Graphics g, Rectangle rect, int orientation, int state)
/*      */   {
/* 1008 */     if (!c.isOpaque()) {
/* 1009 */       return;
/*      */     }
/* 1011 */     Graphics2D g2d = (Graphics2D)g;
/* 1012 */     JideSwingUtilities.fillGradient(g2d, new Rectangle(rect.x, rect.y, rect.width, rect.height), getCurrentTheme().getColor("DockableFrame.backgroundLt"), getCurrentTheme().getColor("DockableFrame.backgroundDk"), orientation == 0);
/*      */   }
/*      */ 
/*      */   public void paintDockableFrameTitlePane(JComponent c, Graphics g, Rectangle rect, int orientation, int state)
/*      */   {
/* 1021 */     Boolean highContrast = Boolean.valueOf(UIManager.getBoolean("Theme.highContrast"));
/* 1022 */     if (highContrast.booleanValue()) {
/* 1023 */       super.paintDockableFrameTitlePane(c, g, rect, orientation, state);
/* 1024 */       return;
/*      */     }
/*      */ 
/* 1027 */     int x = rect.x;
/* 1028 */     int y = rect.y;
/* 1029 */     int w = rect.width;
/* 1030 */     int h = rect.height;
/* 1031 */     if (c.getBorder() != null) {
/* 1032 */       Insets insets = c.getBorder().getBorderInsets(c);
/* 1033 */       x += insets.left;
/* 1034 */       y += insets.top;
/* 1035 */       w -= insets.right + insets.left;
/* 1036 */       h -= insets.top + insets.bottom;
/*      */     }
/* 1038 */     rect = new Rectangle(x, y, w, h);
/*      */ 
/* 1040 */     boolean active = state == 3;
/* 1041 */     Graphics2D g2d = (Graphics2D)g;
/* 1042 */     JideSwingUtilities.fillGradient(g2d, rect, active ? getCurrentTheme().getColor("selection.SelectedLt") : getCurrentTheme().getColor("DockableFrameTitlePane.backgroundLt"), active ? getCurrentTheme().getColor("selection.SelectedDk") : getCurrentTheme().getColor("DockableFrameTitlePane.backgroundDk"), orientation == 0);
/*      */   }
/*      */ 
/*      */   private void paintCollapsiblePaneTitlePane(Graphics2D g2d, Color colorLt, Color colorDk, int orientation, Rectangle rect)
/*      */   {
/* 1049 */     Color old = g2d.getColor();
/* 1050 */     g2d.setColor(colorLt);
/* 1051 */     switch (orientation) {
/*      */     case 3:
/* 1053 */       g2d.drawLine(rect.x + 2, rect.y, rect.x + rect.width - 1, rect.y);
/* 1054 */       g2d.drawLine(rect.x + 1, rect.y + 1, rect.x + rect.width - 1, rect.y + 1);
/* 1055 */       JideSwingUtilities.fillGradient(g2d, new Rectangle(rect.x, rect.y + 2, rect.width, rect.height - 4), colorLt, colorDk, true);
/*      */ 
/* 1060 */       g2d.setColor(colorDk);
/* 1061 */       g2d.drawLine(rect.x + 1, rect.y + rect.height - 2, rect.x + rect.width - 1, rect.y + rect.height - 2);
/* 1062 */       g2d.drawLine(rect.x + 2, rect.y + rect.height - 1, rect.x + rect.width - 1, rect.y + rect.height - 1);
/* 1063 */       break;
/*      */     case 7:
/* 1065 */       g2d.drawLine(rect.x, rect.y, rect.x + rect.width - 3, rect.y);
/* 1066 */       g2d.drawLine(rect.x, rect.y + 1, rect.x + rect.width - 1, rect.y + 1);
/* 1067 */       JideSwingUtilities.fillGradient(g2d, new Rectangle(rect.x, rect.y + 2, rect.width, rect.height - 4), colorLt, colorDk, true);
/*      */ 
/* 1072 */       g2d.setColor(colorDk);
/* 1073 */       g2d.drawLine(rect.x, rect.y + rect.height - 2, rect.x + rect.width - 1, rect.y + rect.height - 2);
/* 1074 */       g2d.drawLine(rect.x, rect.y + rect.height - 1, rect.x + rect.width - 2, rect.y + rect.height - 1);
/* 1075 */       break;
/*      */     case 1:
/* 1077 */       g2d.drawLine(rect.x, rect.y, rect.x, rect.y + rect.height - 2);
/* 1078 */       g2d.drawLine(rect.x + 1, rect.y, rect.x + 1, rect.y + rect.height - 1);
/* 1079 */       JideSwingUtilities.fillGradient(g2d, new Rectangle(rect.x + 2, rect.y, rect.width - 4, rect.height), colorLt, colorDk, false);
/*      */ 
/* 1084 */       g2d.setColor(colorDk);
/* 1085 */       g2d.drawLine(rect.x + rect.width - 2, rect.y, rect.x + rect.width - 2, rect.y + rect.height - 1);
/* 1086 */       g2d.drawLine(rect.x + rect.width - 1, rect.y, rect.x + rect.width - 1, rect.y + rect.height - 2);
/* 1087 */       break;
/*      */     case 5:
/* 1089 */       g2d.drawLine(rect.x, rect.y + 2, rect.x, rect.y + rect.height - 1);
/* 1090 */       g2d.drawLine(rect.x + 1, rect.y + 1, rect.x + 1, rect.y + rect.height - 1);
/* 1091 */       JideSwingUtilities.fillGradient(g2d, new Rectangle(rect.x + 2, rect.y, rect.width - 4, rect.height), colorLt, colorDk, false);
/*      */ 
/* 1096 */       g2d.setColor(colorDk);
/* 1097 */       g2d.drawLine(rect.x + rect.width - 2, rect.y + 1, rect.x + rect.width - 2, rect.y + rect.height - 1);
/* 1098 */       g2d.drawLine(rect.x + rect.width - 1, rect.y + 2, rect.x + rect.width - 1, rect.y + rect.height - 1);
/*      */     case 2:
/*      */     case 4:
/* 1101 */     case 6: } g2d.setColor(old);
/*      */   }
/*      */ 
/*      */   public void paintCollapsiblePaneTitlePaneBackground(JComponent c, Graphics g, Rectangle rect, int orientation, int state)
/*      */   {
/* 1106 */     Boolean highContrast = Boolean.valueOf(UIManager.getBoolean("Theme.highContrast"));
/* 1107 */     if (highContrast.booleanValue()) {
/* 1108 */       super.paintCollapsiblePaneTitlePaneBackground(c, g, rect, orientation, state);
/* 1109 */       return;
/*      */     }
/* 1111 */     Graphics2D g2d = (Graphics2D)g;
/* 1112 */     Color background = c.getBackground();
/*      */     Color colorDk;
/*      */     Color colorLt;
/*      */     Color colorDk;
/* 1115 */     if (!(background instanceof UIResource)) {
/* 1116 */       Color colorLt = ColorUtils.getDerivedColor(background, 0.6F);
/* 1117 */       colorDk = ColorUtils.getDerivedColor(background, 0.5F);
/*      */     }
/*      */     else {
/* 1120 */       colorLt = getCurrentTheme().getColor("CollapsiblePaneTitlePane.backgroundLt");
/* 1121 */       colorDk = getCurrentTheme().getColor("CollapsiblePaneTitlePane.backgroundDk");
/*      */     }
/* 1123 */     paintCollapsiblePaneTitlePane(g2d, colorLt, colorDk, orientation, rect);
/*      */   }
/*      */ 
/*      */   public void paintCollapsiblePaneTitlePaneBackgroundEmphasized(JComponent c, Graphics g, Rectangle rect, int orientation, int state)
/*      */   {
/* 1129 */     Boolean highContrast = Boolean.valueOf(UIManager.getBoolean("Theme.highContrast"));
/* 1130 */     if (highContrast.booleanValue()) {
/* 1131 */       super.paintCollapsiblePaneTitlePaneBackgroundEmphasized(c, g, rect, orientation, state);
/* 1132 */       return;
/*      */     }
/* 1134 */     Graphics2D g2d = (Graphics2D)g;
/* 1135 */     Color background = c.getBackground();
/*      */     Color colorDk;
/*      */     Color colorLt;
/*      */     Color colorDk;
/* 1138 */     if (!(background instanceof UIResource)) {
/* 1139 */       Color colorLt = ColorUtils.getDerivedColor(background, 0.5F);
/* 1140 */       colorDk = ColorUtils.getDerivedColor(background, 0.4F);
/*      */     }
/*      */     else {
/* 1143 */       colorLt = getCurrentTheme().getColor("CollapsiblePaneTitlePane.backgroundLt.emphasized");
/* 1144 */       colorDk = getCurrentTheme().getColor("CollapsiblePaneTitlePane.backgroundDk.emphasized");
/*      */     }
/* 1146 */     paintCollapsiblePaneTitlePane(g2d, colorLt, colorDk, orientation, rect);
/*      */   }
/*      */ 
/*      */   public void paintCollapsiblePanesBackground(JComponent c, Graphics g, Rectangle rect, int orientation, int state)
/*      */   {
/* 1151 */     if (!c.isOpaque()) {
/* 1152 */       return;
/*      */     }
/* 1154 */     Boolean highContrast = Boolean.valueOf(UIManager.getBoolean("Theme.highContrast"));
/* 1155 */     if (highContrast.booleanValue()) {
/* 1156 */       super.paintCollapsiblePanesBackground(c, g, rect, orientation, state);
/* 1157 */       return;
/*      */     }
/* 1159 */     Graphics2D g2d = (Graphics2D)g;
/* 1160 */     if (!(c.getBackground() instanceof UIResource)) {
/* 1161 */       JideSwingUtilities.fillGradient(g2d, new Rectangle(rect.x, rect.y, rect.width, rect.height), ColorUtils.getDerivedColor(c.getBackground(), 0.6F), c.getBackground(), orientation == 0);
/*      */     }
/*      */     else
/*      */     {
/* 1168 */       JideSwingUtilities.fillGradient(g2d, new Rectangle(rect.x, rect.y, rect.width, rect.height), getCurrentTheme().getColor("CollapsiblePanes.backgroundLt"), getCurrentTheme().getColor("CollapsiblePanes.backgroundDk"), orientation == 0);
/*      */     }
/*      */   }
/*      */ 
/*      */   private void paintCollapsiblePaneTitlePanePlain(Graphics2D g2d, Color colorDk, Color colorLt, int orientation, Rectangle rect)
/*      */   {
/*      */     Rectangle rectangle;
/* 1179 */     switch (orientation) {
/*      */     case 3:
/* 1181 */       rectangle = new Rectangle(rect.x + rect.width - 1, rect.y, 1, rect.height);
/* 1182 */       break;
/*      */     case 7:
/* 1184 */       rectangle = new Rectangle(rect.x, rect.y, 1, rect.height);
/* 1185 */       break;
/*      */     case 1:
/* 1187 */       rectangle = new Rectangle(rect.x, rect.y, rect.width, 1);
/* 1188 */       break;
/*      */     case 2:
/*      */     case 4:
/*      */     case 5:
/*      */     case 6:
/*      */     default:
/* 1191 */       rectangle = new Rectangle(rect.x, rect.y + rect.height - 1, rect.width, 1);
/*      */     }
/*      */ 
/* 1194 */     JideSwingUtilities.fillGradient(g2d, rectangle, colorLt, colorDk, (orientation == 1) || (orientation == 5));
/*      */   }
/*      */ 
/*      */   public void paintCollapsiblePaneTitlePaneBackgroundPlainEmphasized(JComponent c, Graphics g, Rectangle rect, int orientation, int state)
/*      */   {
/* 1202 */     Boolean highContrast = Boolean.valueOf(UIManager.getBoolean("Theme.highContrast"));
/* 1203 */     if (highContrast.booleanValue()) {
/* 1204 */       super.paintCollapsiblePaneTitlePaneBackgroundPlainEmphasized(c, g, rect, orientation, state);
/* 1205 */       return;
/*      */     }
/* 1207 */     Graphics2D g2d = (Graphics2D)g;
/* 1208 */     Color colorLt = getCurrentTheme().getColor("CollapsiblePaneTitlePane.backgroundLt.emphasized");
/* 1209 */     Color colorDk = getCurrentTheme().getColor("CollapsiblePaneTitlePane.backgroundDk.emphasized");
/* 1210 */     paintCollapsiblePaneTitlePanePlain(g2d, colorDk, colorLt, orientation, rect);
/*      */   }
/*      */ 
/*      */   public void paintCollapsiblePaneTitlePaneBackgroundPlain(JComponent c, Graphics g, Rectangle rect, int orientation, int state)
/*      */   {
/* 1216 */     Boolean highContrast = Boolean.valueOf(UIManager.getBoolean("Theme.highContrast"));
/* 1217 */     if (highContrast.booleanValue()) {
/* 1218 */       super.paintCollapsiblePaneTitlePaneBackgroundPlain(c, g, rect, orientation, state);
/* 1219 */       return;
/*      */     }
/* 1221 */     Graphics2D g2d = (Graphics2D)g;
/* 1222 */     Color colorLt = getCurrentTheme().getColor("CollapsiblePaneTitlePane.backgroundLt");
/* 1223 */     Color colorDk = getCurrentTheme().getColor("CollapsiblePaneTitlePane.backgroundDk");
/* 1224 */     paintCollapsiblePaneTitlePanePlain(g2d, colorDk, colorLt, orientation, rect);
/*      */   }
/*      */ 
/*      */   public void paintCollapsiblePaneTitlePaneBackgroundSeparatorEmphasized(JComponent c, Graphics g, Rectangle rect, int orientation, int state)
/*      */   {
/* 1229 */     Boolean highContrast = Boolean.valueOf(UIManager.getBoolean("Theme.highContrast"));
/* 1230 */     if (highContrast.booleanValue()) {
/* 1231 */       super.paintCollapsiblePaneTitlePaneBackgroundSeparatorEmphasized(c, g, rect, orientation, state);
/* 1232 */       return;
/*      */     }
/* 1234 */     Graphics2D g2d = (Graphics2D)g;
/* 1235 */     Color colorLt = getCurrentTheme().getColor("CollapsiblePaneTitlePane.backgroundLt.emphasized");
/* 1236 */     Color colorDk = getCurrentTheme().getColor("CollapsiblePaneTitlePane.backgroundDk.emphasized");
/* 1237 */     JideSwingUtilities.fillGradient(g2d, new Rectangle(rect.x, rect.y, rect.width, rect.height), colorDk, colorLt, (orientation == 1) || (orientation == 5));
/*      */   }
/*      */ 
/*      */   public void paintCollapsiblePaneTitlePaneBackgroundSeparator(JComponent c, Graphics g, Rectangle rect, int orientation, int state)
/*      */   {
/* 1246 */     Boolean highContrast = Boolean.valueOf(UIManager.getBoolean("Theme.highContrast"));
/* 1247 */     if (highContrast.booleanValue()) {
/* 1248 */       super.paintCollapsiblePaneTitlePaneBackgroundSeparator(c, g, rect, orientation, state);
/* 1249 */       return;
/*      */     }
/* 1251 */     Graphics2D g2d = (Graphics2D)g;
/* 1252 */     Color colorLt = getCurrentTheme().getColor("CollapsiblePaneTitlePane.backgroundLt");
/* 1253 */     Color colorDk = getCurrentTheme().getColor("CollapsiblePaneTitlePane.backgroundDk");
/* 1254 */     JideSwingUtilities.fillGradient(g2d, new Rectangle(rect.x, rect.y, rect.width, rect.height), colorLt, colorDk, (orientation == 1) || (orientation == 5));
/*      */   }
/*      */ 
/*      */   public void paintTabAreaBackground(JComponent c, Graphics g, Rectangle rect, int orientation, int state)
/*      */   {
/* 1264 */     Boolean highContrast = Boolean.valueOf(UIManager.getBoolean("Theme.highContrast"));
/* 1265 */     if (highContrast.booleanValue()) {
/* 1266 */       super.paintTabAreaBackground(c, g, rect, orientation, state);
/* 1267 */       return;
/*      */     }
/*      */ 
/* 1270 */     if (((c instanceof JideTabbedPane)) && (((JideTabbedPane)c).getColorTheme() != 2)) {
/* 1271 */       super.paintTabAreaBackground(c, g, rect, orientation, state);
/*      */     }
/* 1275 */     else if (c.isOpaque()) {
/* 1276 */       Object o = c.getClientProperty("JideTabbedPane.gradientTabArea");
/* 1277 */       boolean useGradient = (o instanceof Boolean) ? ((Boolean)o).booleanValue() : UIDefaultsLookup.getBoolean("JideTabbedPane.gradientTabArea", true);
/* 1278 */       if (((c instanceof JideTabbedPane)) && (useGradient)) {
/* 1279 */         Graphics2D g2d = (Graphics2D)g;
/* 1280 */         Color startColor = getTabAreaBackgroundDk();
/* 1281 */         Color endColor = getTabAreaBackgroundLt();
/* 1282 */         int placement = ((JideTabbedPane)c).getTabPlacement();
/* 1283 */         switch (placement) {
/*      */         case 1:
/* 1285 */           JideSwingUtilities.fillGradient(g2d, new Rectangle(rect.x, rect.y, rect.width, rect.height), startColor, endColor, true);
/* 1286 */           break;
/*      */         case 3:
/* 1288 */           JideSwingUtilities.fillGradient(g2d, new Rectangle(rect.x, rect.y, rect.width, rect.height), endColor, startColor, true);
/* 1289 */           break;
/*      */         case 2:
/* 1291 */           JideSwingUtilities.fillGradient(g2d, new Rectangle(rect.x, rect.y, rect.width, rect.height), startColor, endColor, false);
/* 1292 */           break;
/*      */         case 4:
/* 1294 */           JideSwingUtilities.fillGradient(g2d, new Rectangle(rect.x, rect.y, rect.width, rect.height), endColor, startColor, false);
/*      */         }
/*      */       }
/*      */       else
/*      */       {
/* 1299 */         g.setColor(UIDefaultsLookup.getColor("control"));
/* 1300 */         g.fillRect(rect.x, rect.y, rect.width, rect.height);
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   public void paintHeaderBoxBackground(JComponent c, Graphics g, Rectangle rect, int orientation, int state)
/*      */   {
/* 1308 */     super.paintHeaderBoxBackground(c, g, rect, orientation, state);
/* 1309 */     if (state == 2) {
/* 1310 */       g.setColor(ColorUtils.getDerivedColor(getCurrentTheme().getColor("selection.Rollover"), 0.3F));
/* 1311 */       g.drawLine(rect.x + 1, rect.y + rect.height - 3, rect.x + rect.width - 2, rect.y + rect.height - 3);
/* 1312 */       g.setColor(ColorUtils.getDerivedColor(getCurrentTheme().getColor("selection.Rollover"), 0.35F));
/* 1313 */       g.drawLine(rect.x + 2, rect.y + rect.height - 2, rect.x + rect.width - 3, rect.y + rect.height - 2);
/* 1314 */       g.setColor(ColorUtils.getDerivedColor(getCurrentTheme().getColor("selection.Rollover"), 0.4F));
/* 1315 */       g.drawLine(rect.x + 3, rect.y + rect.height - 1, rect.x + rect.width - 4, rect.y + rect.height - 1);
/*      */     }
/*      */   }
/*      */ 
/*      */   public void paintToolBarSeparator(JComponent c, Graphics g, Rectangle rect, int orientation, int state)
/*      */   {
/* 1321 */     int h = orientation == 0 ? rect.height : rect.width;
/* 1322 */     h -= 9;
/*      */ 
/* 1326 */     if (JideSwingUtilities.getOrientationOf(c) == 0) {
/* 1327 */       int y = rect.y + 5;
/* 1328 */       int x = rect.x + 1;
/* 1329 */       g.setColor(getSeparatorForeground());
/* 1330 */       g.drawLine(x, y, x, y + h);
/* 1331 */       g.setColor(getSeparatorForegroundLt());
/* 1332 */       g.drawLine(x + 1, y + 1, x + 1, y + h + 1);
/*      */     }
/*      */     else {
/* 1335 */       int y = rect.y + 1;
/* 1336 */       int x = rect.x + 5;
/* 1337 */       g.setColor(getSeparatorForeground());
/* 1338 */       g.drawLine(x, y, x + h, y);
/* 1339 */       g.setColor(getSeparatorForegroundLt());
/* 1340 */       g.drawLine(x + 1, y + 1, x + 1 + h, y + 1);
/*      */     }
/*      */   }
/*      */ 
/*      */   public void paintPopupMenuSeparator(JComponent c, Graphics g, Rectangle rect, int orientation, int state)
/*      */   {
/* 1346 */     int defaultShadowWidth = UIDefaultsLookup.getInt("MenuItem.shadowWidth");
/* 1347 */     int defaultTextIconGap = UIDefaultsLookup.getInt("MenuItem.textIconGap");
/*      */ 
/* 1349 */     if (c.getComponentOrientation().isLeftToRight()) {
/* 1350 */       paintMenuShadow(c, g, new Rectangle(rect.x, rect.y, defaultShadowWidth, rect.height), 0, 0);
/*      */ 
/* 1352 */       g.setColor(getMenuItemBackground());
/* 1353 */       g.fillRect(rect.x + defaultShadowWidth, rect.y, rect.width - defaultShadowWidth, rect.height);
/*      */ 
/* 1355 */       g.setColor(getSeparatorForeground());
/* 1356 */       g.drawLine(rect.x + defaultShadowWidth + defaultTextIconGap, rect.y + 1, rect.x + rect.width, rect.y + 1);
/*      */     }
/*      */     else {
/* 1359 */       paintMenuShadow(c, g, new Rectangle(rect.x + rect.width - defaultShadowWidth, rect.y, defaultShadowWidth, rect.height), 0, 0);
/*      */ 
/* 1361 */       g.setColor(getMenuItemBackground());
/* 1362 */       g.fillRect(rect.x, rect.y, rect.width - defaultShadowWidth, rect.height);
/*      */ 
/* 1364 */       g.setColor(getSeparatorForeground());
/* 1365 */       g.drawLine(rect.x, rect.y + 1, rect.x + rect.width - defaultShadowWidth - defaultTextIconGap, rect.y + 1);
/*      */     }
/*      */   }
/*      */ 
/*      */   public void paintStatusBarSeparator(JComponent c, Graphics g, Rectangle rect, int orientation, int state) {
/* 1370 */     int h = orientation == 0 ? c.getHeight() : c.getWidth();
/* 1371 */     h -= 3;
/*      */ 
/* 1375 */     if (orientation == 0) {
/* 1376 */       int x = rect.x;
/* 1377 */       int y = rect.y + 1;
/* 1378 */       g.setColor(UIDefaultsLookup.getColor("controlShadow"));
/* 1379 */       g.drawLine(x, y, x, y + h);
/* 1380 */       g.setColor(UIDefaultsLookup.getColor("controlLtHighlight"));
/* 1381 */       g.drawLine(x + 1, y, x + 1, y + h);
/*      */     }
/*      */     else {
/* 1384 */       int x = rect.x + 1;
/* 1385 */       int y = rect.y;
/* 1386 */       g.setColor(UIDefaultsLookup.getColor("controlShadow"));
/* 1387 */       g.drawLine(x, y, x + h, y);
/* 1388 */       g.setColor(UIDefaultsLookup.getColor("controlLtHighlight"));
/* 1389 */       g.drawLine(x, y + 1, x + h, y + 1);
/*      */     }
/*      */   }
/*      */ 
/*      */   public void fillBackground(JComponent c, Graphics g, Rectangle rect, int orientation, int state, Color color)
/*      */   {
/* 1395 */     Graphics2D g2d = (Graphics2D)g.create();
/* 1396 */     if (orientation == 0) {
/* 1397 */       JideSwingUtilities.fillGradient(g2d, new Rectangle(rect.x, rect.y, rect.width, rect.height), ColorUtils.getDerivedColor(color, 0.6F), ColorUtils.getDerivedColor(color, 0.4F), true);
/*      */     }
/*      */     else
/*      */     {
/* 1401 */       JideSwingUtilities.fillGradient(g2d, new Rectangle(rect.x, rect.y, rect.width, rect.height), ColorUtils.getDerivedColor(color, 0.55F), color, false);
/*      */     }
/*      */ 
/* 1404 */     g2d.dispose();
/*      */   }
/*      */ 
/*      */   static
/*      */   {
/*   93 */     _themeCache.put(_defaultTheme.getThemeName(), _defaultTheme);
/*   94 */     _themeCache.put(_normalTheme.getThemeName(), _normalTheme);
/*   95 */     _themeCache.put(_blueTheme.getThemeName(), _blueTheme);
/*   96 */     _themeCache.put(_homeSteadTheme.getThemeName(), _homeSteadTheme);
/*   97 */     _themeCache.put(_metallicTheme.getThemeName(), _metallicTheme);
/*      */ 
/*   99 */     int SIZE = 20;
/*  100 */     int MASK_SIZE = 11;
/*      */ 
/*  102 */     int products = LookAndFeelFactory.getProductsUsed();
/*      */ 
/*  104 */     Object[] uiDefaultsNormal = { "control", new ColorUIResource(219, 216, 209), "controlLt", new ColorUIResource(245, 244, 242), "controlDk", new ColorUIResource(213, 210, 202), "controlShadow", new ColorUIResource(128, 128, 128), "TabbedPane.selectDk", new ColorUIResource(230, 139, 44), "TabbedPane.selectLt", new ColorUIResource(255, 199, 60), "OptionPane.bannerLt", new ColorUIResource(0, 52, 206), "OptionPane.bannerDk", new ColorUIResource(45, 96, 249), "OptionPane.bannerForeground", new ColorUIResource(255, 255, 255), "Separator.foreground", new ColorUIResource(166, 166, 166), "Separator.foregroundLt", new ColorUIResource(255, 255, 255), "Gripper.foreground", new ColorUIResource(160, 160, 160), "Gripper.foregroundLt", new ColorUIResource(255, 255, 255), "Chevron.backgroundLt", new ColorUIResource(160, 160, 160), "Chevron.backgroundDk", new ColorUIResource(128, 128, 128), "Divider.backgroundLt", new ColorUIResource(110, 110, 110), "Divider.backgroundDk", new ColorUIResource(90, 90, 90), "backgroundLt", new ColorUIResource(245, 245, 244), "backgroundDk", new ColorUIResource(212, 208, 200), "selection.border", new ColorUIResource(0, 0, 128), "MenuItem.background", new ColorUIResource(249, 248, 247), "DockableFrameTitlePane.backgroundLt", new ColorUIResource(243, 242, 240), "DockableFrameTitlePane.backgroundDk", new ColorUIResource(212, 208, 200), "DockableFrameTitlePane.activeForeground", new ColorUIResource(0, 0, 0), "DockableFrameTitlePane.inactiveForeground", new ColorUIResource(0, 0, 0), "DockableFrame.backgroundLt", new ColorUIResource(234, 232, 228), "DockableFrame.backgroundDk", new ColorUIResource(234, 232, 228), "CommandBar.titleBarBackground", new ColorUIResource(128, 128, 128) };
/*      */ 
/*  145 */     _normalTheme.putDefaults(uiDefaultsNormal);
/*      */ 
/*  147 */     if ((products & 0x2) != 0) {
/*  148 */       ImageIcon collapsiblePaneImage = IconsFactory.getImageIcon(Office2003WindowsUtils.class, "icons/collapsible_pane_gray.png");
/*  149 */       ImageIcon collapsiblePaneMask = IconsFactory.getImageIcon(Office2003WindowsUtils.class, "icons/collapsible_pane_mask.png");
/*  150 */       ImageIcon normalIcon = IconsFactory.getIcon(null, collapsiblePaneImage, 0, 0, 20, 20);
/*  151 */       ImageIcon emphasizedIcon = IconsFactory.getIcon(null, collapsiblePaneImage, 20, 0, 20, 20);
/*  152 */       ImageIcon downMark = IconsFactory.getIcon(null, collapsiblePaneMask, 0, 0, 11, 11);
/*  153 */       ImageIcon upMark = IconsFactory.getIcon(null, collapsiblePaneMask, 0, 11, 11, 11);
/*  154 */       uiDefaultsNormal = new Object[] { "CollapsiblePane.contentBackground", new ColorUIResource(255, 255, 255), "CollapsiblePanes.backgroundLt", new ColorUIResource(160, 160, 160), "CollapsiblePanes.backgroundDk", new ColorUIResource(128, 128, 128), "CollapsiblePaneTitlePane.backgroundLt", new ColorUIResource(255, 255, 255), "CollapsiblePaneTitlePane.backgroundDk", new ColorUIResource(213, 210, 202), "CollapsiblePaneTitlePane.foreground", new ColorUIResource(91, 91, 91), "CollapsiblePaneTitlePane.foreground.focus", new ColorUIResource(137, 137, 137), "CollapsiblePaneTitlePane.backgroundLt.emphasized", new ColorUIResource(68, 68, 68), "CollapsiblePaneTitlePane.backgroundDk.emphasized", new ColorUIResource(94, 94, 94), "CollapsiblePaneTitlePane.foreground.emphasized", new ColorUIResource(255, 255, 255), "CollapsiblePaneTitlePane.foreground.focus.emphasized", new ColorUIResource(230, 230, 230), "CollapsiblePane.downIcon", IconsFactory.getOverlayIcon(null, normalIcon, downMark, 0), "CollapsiblePane.upIcon", IconsFactory.getOverlayIcon(null, normalIcon, upMark, 0), "CollapsiblePane.downIcon.emphasized", IconsFactory.getOverlayIcon(null, emphasizedIcon, downMark, 0), "CollapsiblePane.upIcon.emphasized", IconsFactory.getOverlayIcon(null, emphasizedIcon, upMark, 0), "CollapsiblePane.upMask", upMark, "CollapsiblePane.downMask", downMark, "CollapsiblePane.titleButtonBackground", normalIcon, "CollapsiblePane.titleButtonBackground.emphasized", emphasizedIcon };
/*      */ 
/*  175 */       _normalTheme.putDefaults(uiDefaultsNormal);
/*      */     }
/*      */ 
/*  178 */     Object[] uiDefaultsBlue = { "control", new ColorUIResource(196, 219, 249), "controlLt", new ColorUIResource(218, 234, 253), "controlDk", new ColorUIResource(129, 169, 226), "controlShadow", new ColorUIResource(59, 67, 156), "TabbedPane.selectDk", new ColorUIResource(230, 139, 44), "TabbedPane.selectLt", new ColorUIResource(255, 199, 60), "OptionPane.bannerLt", new ColorUIResource(0, 52, 206), "OptionPane.bannerDk", new ColorUIResource(45, 96, 249), "OptionPane.bannerForeground", new ColorUIResource(255, 255, 255), "Separator.foreground", new ColorUIResource(106, 140, 203), "Separator.foregroundLt", new ColorUIResource(241, 249, 255), "Gripper.foreground", new ColorUIResource(39, 65, 118), "Gripper.foregroundLt", new ColorUIResource(255, 255, 255), "Chevron.backgroundLt", new ColorUIResource(117, 166, 241), "Chevron.backgroundDk", new ColorUIResource(0, 53, 145), "Divider.backgroundLt", new ColorUIResource(89, 135, 214), "Divider.backgroundDk", new ColorUIResource(0, 45, 150), "backgroundLt", new ColorUIResource(195, 218, 249), "backgroundDk", new ColorUIResource(158, 190, 245), "selection.border", new ColorUIResource(0, 0, 128), "MenuItem.background", new ColorUIResource(246, 246, 246), "DockableFrameTitlePane.backgroundLt", new ColorUIResource(218, 234, 253), "DockableFrameTitlePane.backgroundDk", new ColorUIResource(123, 164, 224), "DockableFrameTitlePane.activeForeground", new ColorUIResource(0, 0, 0), "DockableFrameTitlePane.inactiveForeground", new ColorUIResource(0, 0, 0), "DockableFrame.backgroundLt", new ColorUIResource(221, 236, 254), "DockableFrame.backgroundDk", new ColorUIResource(221, 236, 254), "CommandBar.titleBarBackground", new ColorUIResource(42, 102, 201) };
/*      */ 
/*  219 */     _blueTheme.putDefaults(uiDefaultsBlue);
/*      */ 
/*  221 */     if ((products & 0x2) != 0) {
/*  222 */       ImageIcon collapsiblePaneImage = IconsFactory.getImageIcon(Office2003WindowsUtils.class, "icons/collapsible_pane_blue.png");
/*  223 */       ImageIcon collapsiblePaneMask = IconsFactory.getImageIcon(Office2003WindowsUtils.class, "icons/collapsible_pane_mask.png");
/*  224 */       ImageIcon normalIcon = IconsFactory.getIcon(null, collapsiblePaneImage, 0, 0, 20, 20);
/*  225 */       ImageIcon emphasizedIcon = IconsFactory.getIcon(null, collapsiblePaneImage, 20, 0, 20, 20);
/*  226 */       ImageIcon upMark = IconsFactory.getIcon(null, collapsiblePaneMask, 0, 11, 11, 11);
/*  227 */       ImageIcon downMark = IconsFactory.getIcon(null, collapsiblePaneMask, 0, 0, 11, 11);
/*  228 */       uiDefaultsNormal = new Object[] { "CollapsiblePane.contentBackground", new ColorUIResource(214, 223, 247), "CollapsiblePanes.backgroundLt", new ColorUIResource(123, 162, 231), "CollapsiblePanes.backgroundDk", new ColorUIResource(103, 125, 217), "CollapsiblePaneTitlePane.backgroundLt", new ColorUIResource(255, 255, 255), "CollapsiblePaneTitlePane.backgroundDk", new ColorUIResource(198, 211, 247), "CollapsiblePaneTitlePane.foreground", new ColorUIResource(33, 93, 198), "CollapsiblePaneTitlePane.foreground.focus", new ColorUIResource(65, 142, 254), "CollapsiblePaneTitlePane.backgroundLt.emphasized", new ColorUIResource(0, 73, 181), "CollapsiblePaneTitlePane.backgroundDk.emphasized", new ColorUIResource(41, 93, 206), "CollapsiblePaneTitlePane.foreground.emphasized", new ColorUIResource(255, 255, 255), "CollapsiblePaneTitlePane.foreground.focus.emphasized", new ColorUIResource(65, 142, 254), "CollapsiblePane.downIcon", IconsFactory.getOverlayIcon(null, normalIcon, downMark, 0), "CollapsiblePane.upIcon", IconsFactory.getOverlayIcon(null, normalIcon, upMark, 0), "CollapsiblePane.downIcon.emphasized", IconsFactory.getOverlayIcon(null, emphasizedIcon, downMark, 0), "CollapsiblePane.upIcon.emphasized", IconsFactory.getOverlayIcon(null, emphasizedIcon, upMark, 0), "CollapsiblePane.upMask", upMark, "CollapsiblePane.downMask", downMark, "CollapsiblePane.titleButtonBackground", normalIcon, "CollapsiblePane.titleButtonBackground.emphasized", emphasizedIcon };
/*      */ 
/*  250 */       _blueTheme.putDefaults(uiDefaultsNormal);
/*      */     }
/*      */ 
/*  254 */     Object[] uiDefaultsHomeStead = { "control", new ColorUIResource(209, 222, 173), "controlLt", new ColorUIResource(244, 247, 222), "controlDk", new ColorUIResource(183, 198, 145), "controlShadow", new ColorUIResource(96, 128, 88), "TabbedPane.selectDk", new ColorUIResource(207, 114, 37), "TabbedPane.selectLt", new ColorUIResource(227, 145, 79), "OptionPane.bannerLt", new ColorUIResource(150, 185, 120), "OptionPane.bannerDk", new ColorUIResource(179, 214, 149), "OptionPane.bannerForeground", new ColorUIResource(255, 255, 255), "Separator.foreground", new ColorUIResource(96, 128, 88), "Separator.foregroundLt", new ColorUIResource(244, 247, 242), "Gripper.foreground", new ColorUIResource(81, 94, 51), "Gripper.foregroundLt", new ColorUIResource(255, 255, 255), "Chevron.backgroundLt", new ColorUIResource(176, 194, 140), "Chevron.backgroundDk", new ColorUIResource(96, 119, 107), "Divider.backgroundLt", new ColorUIResource(120, 142, 111), "Divider.backgroundDk", new ColorUIResource(73, 91, 67), "backgroundLt", new ColorUIResource(242, 240, 228), "backgroundDk", new ColorUIResource(217, 217, 167), "selection.border", new ColorUIResource(63, 93, 56), "MenuItem.background", new ColorUIResource(244, 244, 238), "DockableFrameTitlePane.backgroundLt", new ColorUIResource(237, 242, 212), "DockableFrameTitlePane.backgroundDk", new ColorUIResource(181, 196, 143), "DockableFrameTitlePane.activeForeground", new ColorUIResource(0, 0, 0), "DockableFrameTitlePane.inactiveForeground", new ColorUIResource(0, 0, 0), "DockableFrame.backgroundLt", new ColorUIResource(243, 242, 231), "DockableFrame.backgroundDk", new ColorUIResource(243, 242, 231), "CommandBar.titleBarBackground", new ColorUIResource(116, 134, 94) };
/*      */ 
/*  296 */     _homeSteadTheme.putDefaults(uiDefaultsHomeStead);
/*      */ 
/*  298 */     if ((products & 0x2) != 0) {
/*  299 */       ImageIcon collapsiblePaneImage = IconsFactory.getImageIcon(Office2003WindowsUtils.class, "icons/collapsible_pane_homestead.png");
/*  300 */       ImageIcon collapsiblePaneMask = IconsFactory.getImageIcon(Office2003WindowsUtils.class, "icons/collapsible_pane_mask.png");
/*  301 */       ImageIcon normalIcon = IconsFactory.getIcon(null, collapsiblePaneImage, 0, 0, 20, 20);
/*  302 */       ImageIcon emphasizedIcon = IconsFactory.getIcon(null, collapsiblePaneImage, 20, 0, 20, 20);
/*  303 */       ImageIcon downMark = IconsFactory.getIcon(null, collapsiblePaneMask, 0, 0, 11, 11);
/*  304 */       ImageIcon upMark = IconsFactory.getIcon(null, collapsiblePaneMask, 0, 11, 11, 11);
/*  305 */       uiDefaultsNormal = new Object[] { "CollapsiblePane.contentBackground", new ColorUIResource(246, 246, 246), "CollapsiblePanes.backgroundLt", new ColorUIResource(204, 217, 173), "CollapsiblePanes.backgroundDk", new ColorUIResource(165, 189, 132), "CollapsiblePaneTitlePane.backgroundLt", new ColorUIResource(254, 252, 236), "CollapsiblePaneTitlePane.backgroundDk", new ColorUIResource(224, 231, 184), "CollapsiblePaneTitlePane.foreground", new ColorUIResource(86, 102, 45), "CollapsiblePaneTitlePane.foreground.focus", new ColorUIResource(114, 146, 29), "CollapsiblePaneTitlePane.backgroundLt.emphasized", new ColorUIResource(119, 140, 64), "CollapsiblePaneTitlePane.backgroundDk.emphasized", new ColorUIResource(150, 168, 103), "CollapsiblePaneTitlePane.foreground.emphasized", new ColorUIResource(255, 255, 255), "CollapsiblePaneTitlePane.foreground.focus.emphasized", new ColorUIResource(224, 231, 151), "CollapsiblePane.downIcon", IconsFactory.getOverlayIcon(null, normalIcon, downMark, 0), "CollapsiblePane.upIcon", IconsFactory.getOverlayIcon(null, normalIcon, upMark, 0), "CollapsiblePane.downIcon.emphasized", IconsFactory.getOverlayIcon(null, emphasizedIcon, downMark, 0), "CollapsiblePane.upIcon.emphasized", IconsFactory.getOverlayIcon(null, emphasizedIcon, upMark, 0), "CollapsiblePane.upMask", upMark, "CollapsiblePane.downMask", downMark, "CollapsiblePane.titleButtonBackground", normalIcon, "CollapsiblePane.titleButtonBackground.emphasized", emphasizedIcon };
/*      */ 
/*  326 */       _homeSteadTheme.putDefaults(uiDefaultsNormal);
/*      */     }
/*      */ 
/*  329 */     Object[] uiDefaultsMetallic = { "control", new ColorUIResource(219, 218, 228), "controlLt", new ColorUIResource(243, 244, 250), "controlDk", new ColorUIResource(153, 151, 181), "controlShadow", new ColorUIResource(124, 124, 148), "TabbedPane.selectDk", new ColorUIResource(230, 139, 44), "TabbedPane.selectLt", new ColorUIResource(255, 200, 60), "OptionPane.bannerLt", new ColorUIResource(181, 195, 222), "OptionPane.bannerDk", new ColorUIResource(120, 140, 167), "OptionPane.bannerForeground", new ColorUIResource(255, 255, 255), "Separator.foreground", new ColorUIResource(110, 109, 143), "Separator.foregroundLt", new ColorUIResource(255, 255, 255), "Gripper.foreground", new ColorUIResource(84, 84, 117), "Gripper.foregroundLt", new ColorUIResource(255, 255, 255), "Chevron.backgroundLt", new ColorUIResource(179, 178, 200), "Chevron.backgroundDk", new ColorUIResource(118, 116, 146), "Divider.backgroundLt", new ColorUIResource(168, 167, 191), "Divider.backgroundDk", new ColorUIResource(119, 118, 151), "backgroundLt", new ColorUIResource(243, 243, 247), "backgroundDk", new ColorUIResource(215, 215, 229), "selection.border", new ColorUIResource(75, 75, 111), "MenuItem.background", new ColorUIResource(253, 250, 255), "DockableFrameTitlePane.backgroundLt", new ColorUIResource(240, 240, 248), "DockableFrameTitlePane.backgroundDk", new ColorUIResource(147, 145, 176), "DockableFrameTitlePane.activeForeground", new ColorUIResource(0, 0, 0), "DockableFrameTitlePane.inactiveForeground", new ColorUIResource(0, 0, 0), "DockableFrame.backgroundLt", new ColorUIResource(238, 238, 244), "DockableFrame.backgroundDk", new ColorUIResource(238, 238, 244), "CommandBar.titleBarBackground", new ColorUIResource(122, 121, 153) };
/*      */ 
/*  370 */     _metallicTheme.putDefaults(uiDefaultsMetallic);
/*      */ 
/*  372 */     if ((products & 0x2) != 0) {
/*  373 */       ImageIcon collapsiblePaneImage = IconsFactory.getImageIcon(Office2003WindowsUtils.class, "icons/collapsible_pane_metallic.png");
/*  374 */       ImageIcon collapsiblePaneMask = IconsFactory.getImageIcon(Office2003WindowsUtils.class, "icons/collapsible_pane_mask.png");
/*  375 */       ImageIcon normalIcon = IconsFactory.getIcon(null, collapsiblePaneImage, 0, 0, 20, 20);
/*  376 */       ImageIcon emphasizedIcon = IconsFactory.getIcon(null, collapsiblePaneImage, 20, 0, 20, 20);
/*  377 */       ImageIcon downMark = IconsFactory.getIcon(null, collapsiblePaneMask, 0, 0, 11, 11);
/*  378 */       ImageIcon upMark = IconsFactory.getIcon(null, collapsiblePaneMask, 0, 11, 11, 11);
/*  379 */       uiDefaultsNormal = new Object[] { "CollapsiblePane.contentBackground", new ColorUIResource(240, 241, 245), "CollapsiblePanes.backgroundLt", new ColorUIResource(196, 200, 212), "CollapsiblePanes.backgroundDk", new ColorUIResource(177, 179, 200), "CollapsiblePaneTitlePane.backgroundLt", new ColorUIResource(255, 255, 255), "CollapsiblePaneTitlePane.backgroundDk", new ColorUIResource(214, 215, 224), "CollapsiblePaneTitlePane.foreground", new ColorUIResource(63, 61, 61), "CollapsiblePaneTitlePane.foreground.focus", new ColorUIResource(126, 124, 124), "CollapsiblePaneTitlePane.backgroundLt.emphasized", new ColorUIResource(119, 119, 145), "CollapsiblePaneTitlePane.backgroundDk.emphasized", new ColorUIResource(180, 182, 199), "CollapsiblePaneTitlePane.foreground.emphasized", new ColorUIResource(255, 255, 255), "CollapsiblePaneTitlePane.foreground.focus.emphasized", new ColorUIResource(230, 230, 230), "CollapsiblePane.downIcon", IconsFactory.getOverlayIcon(null, normalIcon, downMark, 0), "CollapsiblePane.upIcon", IconsFactory.getOverlayIcon(null, normalIcon, upMark, 0), "CollapsiblePane.downIcon.emphasized", IconsFactory.getOverlayIcon(null, emphasizedIcon, downMark, 0), "CollapsiblePane.upIcon.emphasized", IconsFactory.getOverlayIcon(null, emphasizedIcon, upMark, 0), "CollapsiblePane.upMask", upMark, "CollapsiblePane.downMask", downMark, "CollapsiblePane.titleButtonBackground", normalIcon, "CollapsiblePane.titleButtonBackground.emphasized", emphasizedIcon };
/*      */ 
/*  401 */       _metallicTheme.putDefaults(uiDefaultsNormal);
/*      */     }
/*      */   }
/*      */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     com.jidesoft.plaf.office2003.Office2003Painter
 * JD-Core Version:    0.6.0
 */