/*      */ package com.jidesoft.plaf.vsnet;
/*      */ 
/*      */ import com.jidesoft.icons.IconsFactory;
/*      */ import com.jidesoft.plaf.UIDefaultsLookup;
/*      */ import com.jidesoft.plaf.basic.ThemePainter;
/*      */ import com.jidesoft.swing.JideSplitButton;
/*      */ import com.jidesoft.swing.JideSwingUtilities;
/*      */ import com.jidesoft.swing.TopLevelMenuContainer;
/*      */ import com.jidesoft.utils.SecurityUtils;
/*      */ import com.sun.java.swing.plaf.windows.WindowsGraphicsUtils;
/*      */ import com.sun.java.swing.plaf.windows.WindowsLookAndFeel;
/*      */ import java.awt.Color;
/*      */ import java.awt.Component;
/*      */ import java.awt.ComponentOrientation;
/*      */ import java.awt.Container;
/*      */ import java.awt.Dimension;
/*      */ import java.awt.Font;
/*      */ import java.awt.FontMetrics;
/*      */ import java.awt.Graphics;
/*      */ import java.awt.Graphics2D;
/*      */ import java.awt.Insets;
/*      */ import java.awt.Point;
/*      */ import java.awt.Rectangle;
/*      */ import java.awt.event.ActionEvent;
/*      */ import java.awt.event.KeyEvent;
/*      */ import java.awt.event.MouseEvent;
/*      */ import java.beans.PropertyChangeEvent;
/*      */ import java.beans.PropertyChangeListener;
/*      */ import javax.swing.AbstractAction;
/*      */ import javax.swing.AbstractButton;
/*      */ import javax.swing.ActionMap;
/*      */ import javax.swing.ButtonModel;
/*      */ import javax.swing.Icon;
/*      */ import javax.swing.ImageIcon;
/*      */ import javax.swing.InputMap;
/*      */ import javax.swing.JCheckBoxMenuItem;
/*      */ import javax.swing.JComponent;
/*      */ import javax.swing.JMenu;
/*      */ import javax.swing.JMenuBar;
/*      */ import javax.swing.JMenuItem;
/*      */ import javax.swing.JRadioButtonMenuItem;
/*      */ import javax.swing.KeyStroke;
/*      */ import javax.swing.LookAndFeel;
/*      */ import javax.swing.MenuElement;
/*      */ import javax.swing.MenuSelectionManager;
/*      */ import javax.swing.SwingUtilities;
/*      */ import javax.swing.UIDefaults;
/*      */ import javax.swing.UIManager;
/*      */ import javax.swing.event.MenuDragMouseEvent;
/*      */ import javax.swing.event.MenuDragMouseListener;
/*      */ import javax.swing.event.MenuKeyEvent;
/*      */ import javax.swing.event.MenuKeyListener;
/*      */ import javax.swing.event.MouseInputListener;
/*      */ import javax.swing.plaf.ActionMapUIResource;
/*      */ import javax.swing.plaf.ComponentInputMapUIResource;
/*      */ import javax.swing.plaf.ComponentUI;
/*      */ import javax.swing.plaf.MenuItemUI;
/*      */ import javax.swing.plaf.UIResource;
/*      */ import javax.swing.plaf.basic.BasicHTML;
/*      */ import javax.swing.text.View;
/*      */ 
/*      */ public class VsnetMenuItemUI extends MenuItemUI
/*      */ {
/*      */   protected JMenuItem menuItem;
/*      */   protected Color selectionBackground;
/*      */   protected Color selectionForeground;
/*      */   protected Color disabledForeground;
/*      */   protected Color acceleratorForeground;
/*      */   protected Color acceleratorSelectionForeground;
/*      */   private String acceleratorDelimiter;
/*      */   protected int defaultTextIconGap;
/*      */   protected Font acceleratorFont;
/*      */   protected MouseInputListener mouseInputListener;
/*      */   protected MenuDragMouseListener menuDragMouseListener;
/*      */   protected MenuKeyListener menuKeyListener;
/*      */   private PropertyChangeListener propertyChangeListener;
/*      */   protected Icon arrowIcon;
/*      */   protected Icon checkIcon;
/*      */   protected boolean oldBorderPainted;
/*      */   protected InputMap windowInputMap;
/*      */   private static final boolean DEBUG = false;
/*      */   protected Color shadowColor;
/*      */   protected int defaultAccelEndGap;
/*      */   protected int defaultShadowWidth;
/*      */   protected Color borderColor;
/*      */   protected Color backgroundColor;
/*      */   static final String MAX_TEXT_WIDTH = "maxTextWidth";
/*      */   static final String MAX_ACC_WIDTH = "maxAccWidth";
/*      */   protected boolean _isFloatingIcon;
/*      */   private ThemePainter _painter;
/*  376 */   protected static Rectangle zeroRect = new Rectangle(0, 0, 0, 0);
/*  377 */   protected static Rectangle iconRect = new Rectangle();
/*  378 */   protected static Rectangle textRect = new Rectangle();
/*  379 */   protected static Rectangle acceleratorRect = new Rectangle();
/*  380 */   protected static Rectangle checkIconRect = new Rectangle();
/*  381 */   protected static Rectangle arrowIconRect = new Rectangle();
/*  382 */   protected static Rectangle viewRect = new Rectangle(32767, 32767);
/*  383 */   static Rectangle r = new Rectangle();
/*      */ 
/*      */   public VsnetMenuItemUI()
/*      */   {
/*   38 */     this.menuItem = null;
/*      */ 
/*   54 */     this.arrowIcon = null;
/*   55 */     this.checkIcon = null;
/*      */ 
/*   79 */     this._isFloatingIcon = false;
/*      */ 
/*   81 */     this._painter = ((ThemePainter)UIDefaultsLookup.get("Theme.painter"));
/*      */   }
/*      */   public static ComponentUI createUI(JComponent c) {
/*   84 */     return new VsnetMenuItemUI();
/*      */   }
/*      */ 
/*      */   public void installUI(JComponent c)
/*      */   {
/*   89 */     this.menuItem = ((JMenuItem)c);
/*      */ 
/*   91 */     installDefaults();
/*   92 */     installComponents(this.menuItem);
/*   93 */     installListeners();
/*   94 */     installKeyboardActions();
/*      */   }
/*      */ 
/*      */   protected void installDefaults()
/*      */   {
/*   99 */     String prefix = getPropertyPrefix();
/*      */ 
/*  101 */     this.acceleratorFont = UIDefaultsLookup.getFont("MenuItem.acceleratorFont");
/*      */ 
/*  103 */     this.menuItem.setOpaque(true);
/*  104 */     if ((this.menuItem.getMargin() == null) || ((this.menuItem.getMargin() instanceof UIResource)))
/*      */     {
/*  106 */       this.menuItem.setMargin(UIDefaultsLookup.getInsets(prefix + ".margin"));
/*      */     }
/*      */ 
/*  110 */     this.defaultTextIconGap = UIDefaultsLookup.getInt(prefix + ".textIconGap");
/*  111 */     this.defaultAccelEndGap = UIDefaultsLookup.getInt("MenuItem.accelEndGap");
/*  112 */     this.defaultShadowWidth = UIDefaultsLookup.getInt("MenuItem.shadowWidth");
/*      */ 
/*  115 */     this.borderColor = UIDefaultsLookup.getColor("MenuItem.selectionBorderColor");
/*  116 */     this.backgroundColor = UIDefaultsLookup.getColor("MenuItem.background");
/*  117 */     this.shadowColor = UIDefaultsLookup.getColor("MenuItem.shadowColor");
/*      */ 
/*  119 */     LookAndFeel.installBorder(this.menuItem, prefix + ".border");
/*  120 */     this.oldBorderPainted = this.menuItem.isBorderPainted();
/*  121 */     Object borderPainted = UIDefaultsLookup.get(prefix + ".borderPainted");
/*  122 */     if ((borderPainted instanceof Boolean)) {
/*  123 */       this.menuItem.setBorderPainted(((Boolean)borderPainted).booleanValue());
/*      */     }
/*  125 */     LookAndFeel.installColorsAndFont(this.menuItem, prefix + ".background", prefix + ".foreground", prefix + ".font");
/*      */ 
/*  131 */     if ((this.selectionBackground == null) || ((this.selectionBackground instanceof UIResource)))
/*      */     {
/*  133 */       this.selectionBackground = UIDefaultsLookup.getColor(prefix + ".selectionBackground");
/*      */     }
/*      */ 
/*  136 */     if ((this.selectionForeground == null) || ((this.selectionForeground instanceof UIResource)))
/*      */     {
/*  138 */       this.selectionForeground = UIDefaultsLookup.getColor(prefix + ".selectionForeground");
/*      */     }
/*      */ 
/*  141 */     if ((this.disabledForeground == null) || ((this.disabledForeground instanceof UIResource)))
/*      */     {
/*  143 */       this.disabledForeground = UIDefaultsLookup.getColor(prefix + ".disabledForeground");
/*      */     }
/*      */ 
/*  146 */     if ((this.acceleratorForeground == null) || ((this.acceleratorForeground instanceof UIResource)))
/*      */     {
/*  148 */       this.acceleratorForeground = UIDefaultsLookup.getColor(prefix + ".acceleratorForeground");
/*      */     }
/*      */ 
/*  151 */     if ((this.acceleratorSelectionForeground == null) || ((this.acceleratorSelectionForeground instanceof UIResource)))
/*      */     {
/*  153 */       this.acceleratorSelectionForeground = UIDefaultsLookup.getColor(prefix + ".acceleratorSelectionForeground");
/*      */     }
/*      */ 
/*  157 */     this.acceleratorDelimiter = UIDefaultsLookup.getString("MenuItem.acceleratorDelimiter");
/*      */ 
/*  159 */     if (this.acceleratorDelimiter == null) {
/*  160 */       this.acceleratorDelimiter = "+";
/*      */     }
/*      */ 
/*  163 */     if ((this.arrowIcon == null) || ((this.arrowIcon instanceof UIResource)))
/*      */     {
/*  165 */       this.arrowIcon = UIDefaultsLookup.getIcon(prefix + ".arrowIcon");
/*      */     }
/*  167 */     if ((this.checkIcon == null) || ((this.checkIcon instanceof UIResource)))
/*      */     {
/*  169 */       this.checkIcon = UIDefaultsLookup.getIcon(prefix + ".checkIcon");
/*      */     }
/*      */ 
/*  172 */     this._isFloatingIcon = UIDefaultsLookup.getBoolean("Icon.floating");
/*      */   }
/*      */ 
/*      */   protected void installComponents(JMenuItem menuItem)
/*      */   {
/*  180 */     BasicHTML.updateRenderer(menuItem, menuItem.getText());
/*      */   }
/*      */ 
/*      */   protected String getPropertyPrefix() {
/*  184 */     return "MenuItem";
/*      */   }
/*      */ 
/*      */   protected void installListeners() {
/*  188 */     if ((this.mouseInputListener = createMouseInputListener(this.menuItem)) != null) {
/*  189 */       this.menuItem.addMouseListener(this.mouseInputListener);
/*  190 */       this.menuItem.addMouseMotionListener(this.mouseInputListener);
/*      */     }
/*  192 */     if ((this.menuDragMouseListener = createMenuDragMouseListener(this.menuItem)) != null) {
/*  193 */       this.menuItem.addMenuDragMouseListener(this.menuDragMouseListener);
/*      */     }
/*  195 */     if ((this.menuKeyListener = createMenuKeyListener(this.menuItem)) != null) {
/*  196 */       this.menuItem.addMenuKeyListener(this.menuKeyListener);
/*      */     }
/*  198 */     if ((this.propertyChangeListener = createPropertyChangeListener(this.menuItem)) != null)
/*  199 */       this.menuItem.addPropertyChangeListener(this.propertyChangeListener);
/*      */   }
/*      */ 
/*      */   protected void installKeyboardActions()
/*      */   {
/*  204 */     ActionMap actionMap = getActionMap();
/*      */ 
/*  206 */     SwingUtilities.replaceUIActionMap(this.menuItem, actionMap);
/*  207 */     updateAcceleratorBinding();
/*      */   }
/*      */ 
/*      */   public void uninstallUI(JComponent c)
/*      */   {
/*  212 */     this.menuItem = ((JMenuItem)c);
/*  213 */     uninstallDefaults();
/*  214 */     uninstallComponents(this.menuItem);
/*  215 */     uninstallListeners();
/*  216 */     uninstallKeyboardActions();
/*      */ 
/*  219 */     Container parent = this.menuItem.getParent();
/*  220 */     if ((parent != null) && ((parent instanceof JComponent)) && ((!(this.menuItem instanceof JMenu)) || (!((JMenu)this.menuItem).isTopLevelMenu())))
/*      */     {
/*  222 */       JComponent p = (JComponent)parent;
/*  223 */       p.putClientProperty("maxAccWidth", null);
/*  224 */       p.putClientProperty("maxTextWidth", null);
/*      */     }
/*      */ 
/*  227 */     this.menuItem = null;
/*      */   }
/*      */ 
/*      */   protected void uninstallDefaults()
/*      */   {
/*  232 */     LookAndFeel.uninstallBorder(this.menuItem);
/*  233 */     this.menuItem.setBorderPainted(this.oldBorderPainted);
/*  234 */     if ((this.menuItem.getMargin() instanceof UIResource))
/*  235 */       this.menuItem.setMargin(null);
/*  236 */     if ((this.arrowIcon instanceof UIResource))
/*  237 */       this.arrowIcon = null;
/*  238 */     if ((this.checkIcon instanceof UIResource))
/*  239 */       this.checkIcon = null;
/*      */   }
/*      */ 
/*      */   protected void uninstallComponents(JMenuItem menuItem)
/*      */   {
/*  247 */     BasicHTML.updateRenderer(menuItem, "");
/*      */   }
/*      */ 
/*      */   protected void uninstallListeners() {
/*  251 */     if (this.mouseInputListener != null) {
/*  252 */       this.menuItem.removeMouseListener(this.mouseInputListener);
/*  253 */       this.menuItem.removeMouseMotionListener(this.mouseInputListener);
/*      */     }
/*  255 */     if (this.menuDragMouseListener != null) {
/*  256 */       this.menuItem.removeMenuDragMouseListener(this.menuDragMouseListener);
/*      */     }
/*  258 */     if (this.menuKeyListener != null) {
/*  259 */       this.menuItem.removeMenuKeyListener(this.menuKeyListener);
/*      */     }
/*  261 */     if (this.propertyChangeListener != null) {
/*  262 */       this.menuItem.removePropertyChangeListener(this.propertyChangeListener);
/*      */     }
/*      */ 
/*  265 */     this.mouseInputListener = null;
/*  266 */     this.menuDragMouseListener = null;
/*  267 */     this.menuKeyListener = null;
/*  268 */     this.propertyChangeListener = null;
/*      */   }
/*      */ 
/*      */   protected void uninstallKeyboardActions() {
/*  272 */     SwingUtilities.replaceUIActionMap(this.menuItem, null);
/*  273 */     if (this.windowInputMap != null) {
/*  274 */       SwingUtilities.replaceUIInputMap(this.menuItem, 2, null);
/*      */ 
/*  276 */       this.windowInputMap = null;
/*      */     }
/*      */   }
/*      */ 
/*      */   protected MouseInputListener createMouseInputListener(JComponent c) {
/*  281 */     return new MouseInputHandler();
/*      */   }
/*      */ 
/*      */   protected MenuDragMouseListener createMenuDragMouseListener(JComponent c) {
/*  285 */     return new MenuDragMouseHandler(null);
/*      */   }
/*      */ 
/*      */   protected MenuKeyListener createMenuKeyListener(JComponent c) {
/*  289 */     return new MenuKeyHandler(null);
/*      */   }
/*      */ 
/*      */   private PropertyChangeListener createPropertyChangeListener(JComponent c) {
/*  293 */     return new PropertyChangeHandler(null);
/*      */   }
/*      */ 
/*      */   ActionMap getActionMap() {
/*  297 */     String propertyPrefix = getPropertyPrefix();
/*  298 */     String uiKey = propertyPrefix + ".actionMap";
/*  299 */     ActionMap am = (ActionMap)UIDefaultsLookup.get(uiKey);
/*  300 */     if (am == null) {
/*  301 */       am = createActionMap();
/*  302 */       UIManager.getLookAndFeelDefaults().put(uiKey, am);
/*      */     }
/*  304 */     return am;
/*      */   }
/*      */ 
/*      */   protected ActionMap createActionMap() {
/*  308 */     ActionMap map = new ActionMapUIResource();
/*  309 */     map.put("doClick", new ClickAction(null));
/*      */ 
/*  317 */     return map;
/*      */   }
/*      */ 
/*      */   protected InputMap createInputMap(int condition) {
/*  321 */     if (condition == 2) {
/*  322 */       return new ComponentInputMapUIResource(this.menuItem);
/*      */     }
/*  324 */     return null;
/*      */   }
/*      */ 
/*      */   void updateAcceleratorBinding() {
/*  328 */     KeyStroke accelerator = this.menuItem.getAccelerator();
/*      */ 
/*  330 */     if (this.windowInputMap != null) {
/*  331 */       this.windowInputMap.clear();
/*      */     }
/*  333 */     if (accelerator != null) {
/*  334 */       if (this.windowInputMap == null) {
/*  335 */         this.windowInputMap = createInputMap(2);
/*      */ 
/*  337 */         SwingUtilities.replaceUIInputMap(this.menuItem, 2, this.windowInputMap);
/*      */       }
/*      */ 
/*  340 */       this.windowInputMap.put(accelerator, "doClick");
/*      */     }
/*      */   }
/*      */ 
/*      */   public Dimension getMinimumSize(JComponent c)
/*      */   {
/*  346 */     Dimension d = null;
/*  347 */     View v = (View)c.getClientProperty("html");
/*  348 */     if (v != null) {
/*  349 */       d = getPreferredSize(c);
/*      */       Dimension tmp23_22 = d; tmp23_22.width = (int)(tmp23_22.width - (v.getPreferredSpan(0) - v.getMinimumSpan(0)));
/*      */     }
/*  352 */     return d;
/*      */   }
/*      */ 
/*      */   public Dimension getPreferredSize(JComponent c)
/*      */   {
/*  357 */     return getPreferredMenuItemSize(c, this.checkIcon, this.arrowIcon, this.defaultTextIconGap);
/*      */   }
/*      */ 
/*      */   public Dimension getMaximumSize(JComponent c)
/*      */   {
/*  365 */     Dimension d = null;
/*  366 */     View v = (View)c.getClientProperty("html");
/*  367 */     if (v != null) {
/*  368 */       d = getPreferredSize(c);
/*      */       Dimension tmp23_22 = d; tmp23_22.width = (int)(tmp23_22.width + (v.getMaximumSpan(0) - v.getPreferredSpan(0)));
/*      */     }
/*  371 */     return d;
/*      */   }
/*      */ 
/*      */   private void resetRects()
/*      */   {
/*  386 */     iconRect.setBounds(zeroRect);
/*  387 */     textRect.setBounds(zeroRect);
/*  388 */     acceleratorRect.setBounds(zeroRect);
/*  389 */     checkIconRect.setBounds(zeroRect);
/*  390 */     arrowIconRect.setBounds(zeroRect);
/*  391 */     viewRect.setBounds(0, 0, 32767, 32767);
/*  392 */     r.setBounds(zeroRect);
/*      */   }
/*      */ 
/*      */   protected Dimension getPreferredMenuItemSize(JComponent c, Icon checkIcon, Icon arrowIcon, int textIconGap)
/*      */   {
/*  399 */     JMenuItem b = (JMenuItem)c;
/*  400 */     Icon icon = b.getIcon();
/*  401 */     String text = b.getText();
/*  402 */     KeyStroke accelerator = b.getAccelerator();
/*  403 */     String acceleratorText = "";
/*      */ 
/*  405 */     if (accelerator != null) {
/*  406 */       int modifiers = accelerator.getModifiers();
/*  407 */       if (modifiers > 0) {
/*  408 */         acceleratorText = KeyEvent.getKeyModifiersText(modifiers);
/*      */ 
/*  410 */         acceleratorText = acceleratorText + this.acceleratorDelimiter;
/*      */       }
/*  412 */       int keyCode = accelerator.getKeyCode();
/*  413 */       if (keyCode != 0) {
/*  414 */         acceleratorText = acceleratorText + KeyEvent.getKeyText(keyCode);
/*      */       }
/*      */       else {
/*  417 */         acceleratorText = acceleratorText + accelerator.getKeyChar();
/*      */       }
/*      */     }
/*      */ 
/*  421 */     Font font = b.getFont();
/*  422 */     FontMetrics fm = b.getFontMetrics(font);
/*  423 */     FontMetrics fmAccel = b.getFontMetrics(this.acceleratorFont);
/*      */ 
/*  425 */     resetRects();
/*      */ 
/*  427 */     layoutMenuItem(fm, text, fmAccel, acceleratorText, icon, checkIcon, arrowIcon, b.getVerticalAlignment(), b.getHorizontalAlignment(), b.getVerticalTextPosition(), b.getHorizontalTextPosition(), viewRect, iconRect, textRect, acceleratorRect, checkIconRect, arrowIconRect, text == null ? 0 : textIconGap, this.defaultTextIconGap);
/*      */ 
/*  435 */     if ((text != null) && (text.trim().length() != 0)) {
/*  436 */       r.setBounds(textRect);
/*      */     }
/*  438 */     if (!iconRect.isEmpty()) {
/*  439 */       r = SwingUtilities.computeUnion(iconRect.x, iconRect.y, iconRect.width, iconRect.height, r);
/*      */     }
/*      */ 
/*  450 */     Container parent = this.menuItem.getParent();
/*      */ 
/*  453 */     if ((parent != null) && ((parent instanceof JComponent)) && ((!(this.menuItem instanceof JMenu)) || (!((JMenu)this.menuItem).isTopLevelMenu())))
/*      */     {
/*  455 */       JComponent p = (JComponent)parent;
/*      */ 
/*  458 */       Integer maxTextWidth = (Integer)p.getClientProperty("maxTextWidth");
/*  459 */       Integer maxAccWidth = (Integer)p.getClientProperty("maxAccWidth");
/*      */ 
/*  461 */       int maxTextValue = maxTextWidth != null ? maxTextWidth.intValue() : 0;
/*  462 */       int maxAccValue = maxAccWidth != null ? maxAccWidth.intValue() : 0;
/*      */ 
/*  465 */       if (r.width < maxTextValue) {
/*  466 */         r.width = maxTextValue;
/*      */       }
/*      */       else {
/*  469 */         p.putClientProperty("maxTextWidth", Integer.valueOf(r.width));
/*      */       }
/*      */ 
/*  473 */       if (acceleratorRect.width > maxAccValue) {
/*  474 */         maxAccValue = acceleratorRect.width;
/*  475 */         p.putClientProperty("maxAccWidth", Integer.valueOf(acceleratorRect.width));
/*      */       }
/*      */ 
/*  478 */       r.width += maxAccValue;
/*  479 */       r.width += textIconGap;
/*  480 */       r.width += this.defaultAccelEndGap;
/*      */     }
/*      */ 
/*  483 */     if ((text != null) && (text.trim().length() != 0) && 
/*  484 */       (icon != null)) {
/*  485 */       r.width += textIconGap;
/*      */     }
/*      */ 
/*  489 */     Insets insets = b.getInsets();
/*      */ 
/*  491 */     if (useCheckAndArrow()) {
/*  492 */       insets.left = 0;
/*  493 */       insets.right = 0;
/*  494 */       r.width += 5;
/*      */     }
/*      */ 
/*  497 */     if (isDownArrowVisible(parent)) {
/*  498 */       insets.left = 0;
/*  499 */       insets.right = 0;
/*  500 */       r.width += 7;
/*      */     }
/*      */ 
/*  503 */     if (insets != null) {
/*  504 */       r.width += insets.left + insets.right;
/*  505 */       r.height += insets.top + insets.bottom;
/*      */     }
/*      */ 
/*  510 */     if (r.width % 2 == 0) {
/*  511 */       r.width += 1;
/*      */     }
/*      */ 
/*  520 */     if (JideSwingUtilities.getOrientationOf(this.menuItem) == 0) {
/*  521 */       return r.getSize();
/*      */     }
/*      */ 
/*  525 */     return new Dimension(r.height, r.width);
/*      */   }
/*      */ 
/*      */   public void update(Graphics g, JComponent c)
/*      */   {
/*  535 */     paint(g, c);
/*      */   }
/*      */ 
/*      */   public void paint(Graphics g, JComponent c)
/*      */   {
/*  540 */     paintMenuItem(g, c, this.checkIcon, this.arrowIcon, this.selectionBackground, this.selectionForeground, this.defaultTextIconGap);
/*      */   }
/*      */ 
/*      */   protected void paintMenuItem(Graphics g, JComponent c, Icon checkIcon, Icon arrowIcon, Color background, Color foreground, int defaultTextIconGap)
/*      */   {
/*  550 */     JMenuItem b = (JMenuItem)c;
/*  551 */     ButtonModel model = b.getModel();
/*      */     int menuHeight;
/*      */     int menuWidth;
/*      */     int menuHeight;
/*  556 */     if (JideSwingUtilities.getOrientationOf(this.menuItem) == 0) {
/*  557 */       int menuWidth = b.getWidth();
/*  558 */       menuHeight = b.getHeight();
/*      */     }
/*      */     else
/*      */     {
/*  562 */       menuWidth = b.getHeight();
/*  563 */       menuHeight = b.getWidth();
/*  564 */       Graphics2D g2d = (Graphics2D)g;
/*  565 */       g2d.rotate(1.570796326794897D);
/*  566 */       g2d.translate(0, -menuHeight + 1);
/*      */     }
/*      */ 
/*  569 */     Insets i = c.getInsets();
/*      */ 
/*  571 */     resetRects();
/*      */ 
/*  573 */     viewRect.setBounds(0, 0, menuWidth, menuHeight);
/*      */ 
/*  575 */     if (isDownArrowVisible(b.getParent())) {
/*  576 */       viewRect.x += 3;
/*  577 */       viewRect.width -= 7;
/*      */     }
/*      */     else {
/*  580 */       viewRect.x += i.left;
/*  581 */       viewRect.width -= i.right + viewRect.x;
/*      */     }
/*  583 */     viewRect.y += i.top;
/*  584 */     viewRect.height -= i.bottom + viewRect.y;
/*      */ 
/*  587 */     Font holdf = g.getFont();
/*  588 */     Font f = c.getFont();
/*  589 */     g.setFont(f);
/*  590 */     FontMetrics fm = g.getFontMetrics(f);
/*  591 */     FontMetrics fmAccel = g.getFontMetrics(this.acceleratorFont);
/*      */ 
/*  594 */     KeyStroke accelerator = b.getAccelerator();
/*  595 */     String acceleratorText = "";
/*  596 */     if (accelerator != null) {
/*  597 */       int modifiers = accelerator.getModifiers();
/*  598 */       if (modifiers > 0) {
/*  599 */         acceleratorText = KeyEvent.getKeyModifiersText(modifiers);
/*      */ 
/*  601 */         acceleratorText = acceleratorText + this.acceleratorDelimiter;
/*      */       }
/*      */ 
/*  604 */       int keyCode = accelerator.getKeyCode();
/*  605 */       if (keyCode != 0) {
/*  606 */         acceleratorText = acceleratorText + KeyEvent.getKeyText(keyCode);
/*      */       }
/*      */       else {
/*  609 */         acceleratorText = acceleratorText + accelerator.getKeyChar();
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/*  614 */     String text = layoutMenuItem(fm, b.getText(), fmAccel, acceleratorText, b.getIcon(), checkIcon, arrowIcon, b.getVerticalAlignment(), b.getHorizontalAlignment(), b.getVerticalTextPosition(), b.getHorizontalTextPosition(), viewRect, iconRect, textRect, acceleratorRect, checkIconRect, arrowIconRect, b.getText() == null ? 0 : defaultTextIconGap, defaultTextIconGap);
/*      */ 
/*  624 */     paintBackground(g, b, background);
/*      */ 
/*  626 */     Color holdc = g.getColor();
/*      */ 
/*  629 */     if (((c.getUIClassID().indexOf("CheckBoxMenu") >= 0) || (c.getUIClassID().indexOf("RadioButtonMenu") >= 0)) && (checkIcon != null)) {
/*  630 */       paintCheckBox(b, g, checkIcon);
/*  631 */       g.setColor(holdc);
/*      */     }
/*      */ 
/*  635 */     if (JideSwingUtilities.getOrientationOf(this.menuItem) == 1)
/*      */     {
/*  637 */       Graphics2D g2d = (Graphics2D)g;
/*  638 */       g2d.rotate(-1.570796326794897D);
/*  639 */       g2d.translate(-menuHeight + 1, 0);
/*  640 */       int save = iconRect.x;
/*      */ 
/*  642 */       iconRect.x = iconRect.y;
/*  643 */       iconRect.y = save;
/*      */     }
/*      */ 
/*  646 */     paintIcon(b, g);
/*      */ 
/*  648 */     if (JideSwingUtilities.getOrientationOf(this.menuItem) == 1) {
/*  649 */       int save = iconRect.x;
/*      */ 
/*  651 */       iconRect.x = iconRect.y;
/*  652 */       iconRect.y = save;
/*      */ 
/*  654 */       Graphics2D g2d = (Graphics2D)g;
/*  655 */       g2d.rotate(1.570796326794897D);
/*  656 */       g2d.translate(0, -menuHeight + 1);
/*      */     }
/*      */ 
/*  660 */     if (text != null) {
/*  661 */       View v = (View)c.getClientProperty("html");
/*  662 */       if (v != null) {
/*  663 */         v.paint(g, textRect);
/*      */       }
/*      */       else {
/*  666 */         paintText(g, b, textRect, text);
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/*  671 */     if ((acceleratorText != null) && (!acceleratorText.equals("")))
/*      */     {
/*  674 */       int accOffset = 0;
/*  675 */       Container parent = this.menuItem.getParent();
/*  676 */       if ((parent != null) && ((parent instanceof JComponent))) {
/*  677 */         JComponent p = (JComponent)parent;
/*  678 */         Integer maxValueInt = (Integer)p.getClientProperty("maxAccWidth");
/*  679 */         int maxValue = maxValueInt != null ? maxValueInt.intValue() : acceleratorRect.width;
/*      */ 
/*  683 */         accOffset = maxValue - acceleratorRect.width;
/*      */       }
/*  685 */       if (!parent.getComponentOrientation().isLeftToRight())
/*      */       {
/*  687 */         accOffset = 0;
/*      */       }
/*      */ 
/*  690 */       g.setFont(this.acceleratorFont);
/*  691 */       if (!model.isEnabled())
/*      */       {
/*  693 */         if (this.disabledForeground != null) {
/*  694 */           g.setColor(this.disabledForeground);
/*  695 */           JideSwingUtilities.drawString(this.menuItem, g, acceleratorText, acceleratorRect.x - accOffset, acceleratorRect.y + fmAccel.getAscent());
/*      */         }
/*      */         else
/*      */         {
/*  700 */           g.setColor(b.getBackground().brighter());
/*  701 */           JideSwingUtilities.drawString(this.menuItem, g, acceleratorText, acceleratorRect.x - accOffset, acceleratorRect.y + fmAccel.getAscent());
/*      */ 
/*  704 */           g.setColor(b.getBackground().darker());
/*  705 */           JideSwingUtilities.drawString(this.menuItem, g, acceleratorText, acceleratorRect.x - accOffset - 1, acceleratorRect.y + fmAccel.getAscent() - 1);
/*      */         }
/*      */ 
/*      */       }
/*      */       else
/*      */       {
/*  712 */         if ((model.isArmed()) || (((c instanceof JMenu)) && (model.isSelected()))) {
/*  713 */           g.setColor(this.acceleratorSelectionForeground);
/*      */         }
/*      */         else {
/*  716 */           g.setColor(this.acceleratorForeground);
/*      */         }
/*  718 */         JideSwingUtilities.drawString(this.menuItem, g, acceleratorText, acceleratorRect.x - accOffset, acceleratorRect.y + fmAccel.getAscent());
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/*  725 */     if (arrowIcon != null) {
/*  726 */       if ((model.isArmed()) || (((c instanceof JMenu)) && (model.isSelected())))
/*  727 */         g.setColor(foreground);
/*  728 */       if (useCheckAndArrow()) {
/*  729 */         arrowIcon.paintIcon(c, g, arrowIconRect.x, arrowIconRect.y);
/*      */       }
/*      */     }
/*  732 */     g.setColor(holdc);
/*  733 */     g.setFont(holdf);
/*      */   }
/*      */ 
/*      */   protected void paintCheckBox(JMenuItem b, Graphics g, Icon checkIcon) {
/*  737 */     ButtonModel model = b.getModel();
/*  738 */     boolean isSelected = false;
/*  739 */     if ((b instanceof JCheckBoxMenuItem))
/*  740 */       isSelected = b.isSelected();
/*  741 */     else if ((b instanceof JRadioButtonMenuItem))
/*  742 */       isSelected = b.isSelected();
/*  743 */     if (isSelected)
/*  744 */       if ((model.isArmed()) || (model.isSelected())) {
/*  745 */         getPainter().paintMenuItemBackground(b, g, checkIconRect, 0, 1);
/*  746 */         if (b.getIcon() == null)
/*  747 */           checkIcon.paintIcon(b, g, checkIconRect.x, checkIconRect.y);
/*      */       }
/*      */       else
/*      */       {
/*  751 */         getPainter().paintMenuItemBackground(b, g, checkIconRect, 0, 3);
/*  752 */         if (b.getIcon() == null)
/*  753 */           checkIcon.paintIcon(b, g, checkIconRect.x, checkIconRect.y);
/*      */       }
/*      */   }
/*      */ 
/*      */   protected void paintIcon(JMenuItem b, Graphics g)
/*      */   {
/*  760 */     if (b.getIcon() != null) {
/*  761 */       Icon icon = getIcon(b);
/*      */ 
/*  763 */       ButtonModel model = b.getModel();
/*  764 */       if (icon != null)
/*  765 */         if (isFloatingIcon()) {
/*  766 */           if ((model.isArmed()) || (((b instanceof JMenu)) && (model.isSelected()))) {
/*  767 */             if ((icon instanceof ImageIcon)) {
/*  768 */               ImageIcon shadow = IconsFactory.createGrayImage(((ImageIcon)icon).getImage());
/*  769 */               shadow.paintIcon(b, g, iconRect.x + 1, iconRect.y + 1);
/*      */             }
/*      */             else {
/*  772 */               ImageIcon shadow = IconsFactory.createGrayImage(b, icon);
/*  773 */               shadow.paintIcon(b, g, iconRect.x + 1, iconRect.y + 1);
/*      */             }
/*  775 */             icon.paintIcon(b, g, iconRect.x - 1, iconRect.y - 1);
/*      */           }
/*      */           else {
/*  778 */             icon.paintIcon(b, g, iconRect.x, iconRect.y);
/*      */           }
/*      */         }
/*      */         else
/*  782 */           icon.paintIcon(b, g, iconRect.x, iconRect.y);
/*      */     }
/*      */   }
/*      */ 
/*      */   protected void paintBackground(Graphics g, JMenuItem menuItem, Color bgColor)
/*      */   {
/*  797 */     ButtonModel model = menuItem.getModel();
/*  798 */     Color oldColor = g.getColor();
/*      */     int menuHeight;
/*      */     int menuWidth;
/*      */     int menuHeight;
/*  801 */     if (JideSwingUtilities.getOrientationOf(menuItem) == 0) {
/*  802 */       int menuWidth = menuItem.getWidth();
/*  803 */       menuHeight = menuItem.getHeight();
/*      */     }
/*      */     else {
/*  806 */       menuWidth = menuItem.getHeight();
/*  807 */       menuHeight = menuItem.getWidth();
/*      */     }
/*      */ 
/*  810 */     if (menuItem.isOpaque())
/*  811 */       if (menuItem.getComponentOrientation().isLeftToRight()) {
/*  812 */         if ((menuItem.getBackground() instanceof UIResource)) {
/*  813 */           g.setColor(getPainter().getMenuItemBackground());
/*      */         }
/*      */         else {
/*  816 */           g.setColor(menuItem.getBackground());
/*      */         }
/*  818 */         g.fillRect(this.defaultShadowWidth, 0, menuWidth - this.defaultShadowWidth, menuHeight);
/*  819 */         getPainter().paintMenuShadow(menuItem, g, new Rectangle(0, 0, this.defaultShadowWidth, menuHeight), 0, 0);
/*      */ 
/*  821 */         if ((model.isArmed()) || (((menuItem instanceof JMenu)) && (model.isSelected()))) {
/*  822 */           getPainter().paintMenuItemBackground(menuItem, g, new Rectangle(1, 0, menuWidth - 2, menuHeight), 0, 2);
/*      */         }
/*      */ 
/*  826 */         if ("true".equals(SecurityUtils.getProperty("shadingtheme", "false"))) {
/*  827 */           JideSwingUtilities.fillSingleGradient(g, new Rectangle(0, 0, this.defaultShadowWidth, menuHeight), 3, 255);
/*      */         }
/*      */ 
/*  830 */         g.setColor(oldColor);
/*      */       }
/*      */       else {
/*  833 */         if ((menuItem.getBackground() instanceof UIResource)) {
/*  834 */           g.setColor(getPainter().getMenuItemBackground());
/*      */         }
/*      */         else {
/*  837 */           g.setColor(menuItem.getBackground());
/*      */         }
/*  839 */         g.fillRect(0, 0, menuWidth - this.defaultShadowWidth, menuHeight);
/*  840 */         getPainter().paintMenuShadow(menuItem, g, new Rectangle(menuWidth - this.defaultShadowWidth, 0, this.defaultShadowWidth, menuHeight), 0, 0);
/*      */ 
/*  842 */         if ((model.isArmed()) || (((menuItem instanceof JMenu)) && (model.isSelected()))) {
/*  843 */           getPainter().paintMenuItemBackground(menuItem, g, new Rectangle(0, 0, menuWidth, menuHeight), 0, 2);
/*      */         }
/*      */ 
/*  846 */         if ("true".equals(SecurityUtils.getProperty("shadingtheme", "false"))) {
/*  847 */           JideSwingUtilities.fillSingleGradient(g, new Rectangle(menuWidth - this.defaultShadowWidth, 0, this.defaultShadowWidth, menuHeight), 7, 255);
/*      */         }
/*      */ 
/*  850 */         g.setColor(oldColor);
/*      */       }
/*      */   }
/*      */ 
/*      */   protected void paintText(Graphics g, JMenuItem menuItem, Rectangle textRect, String text)
/*      */   {
/*  866 */     ButtonModel model = menuItem.getModel();
/*      */ 
/*  868 */     FontMetrics fm = g.getFontMetrics();
/*      */ 
/*  870 */     if (!model.isEnabled())
/*      */     {
/*  872 */       WindowsGraphicsUtils.paintText(g, menuItem, textRect, text, 0);
/*      */     }
/*      */     else {
/*  875 */       int mnemonicIndex = menuItem.getDisplayedMnemonicIndex();
/*      */ 
/*  877 */       if (WindowsLookAndFeel.isMnemonicHidden()) {
/*  878 */         mnemonicIndex = -1;
/*      */       }
/*      */ 
/*  881 */       Color oldColor = g.getColor();
/*      */ 
/*  884 */       if ((model.isArmed()) || (((menuItem instanceof JMenu)) && (model.isSelected()))) {
/*  885 */         g.setColor(this.selectionForeground);
/*      */       }
/*  887 */       JideSwingUtilities.drawStringUnderlineCharAt(menuItem, g, text, mnemonicIndex, textRect.x, textRect.y + fm.getAscent());
/*      */ 
/*  891 */       g.setColor(oldColor);
/*      */     }
/*      */   }
/*      */ 
/*      */   private String layoutMenuItem(FontMetrics fm, String text, FontMetrics fmAccel, String acceleratorText, Icon icon, Icon checkIcon, Icon arrowIcon, int verticalAlignment, int horizontalAlignment, int verticalTextPosition, int horizontalTextPosition, Rectangle viewRect, Rectangle iconRect, Rectangle textRect, Rectangle acceleratorRect, Rectangle checkIconRect, Rectangle arrowIconRect, int textIconGap, int menuItemGap)
/*      */   {
/*  914 */     if ((icon != null) && (
/*  915 */       (icon.getIconHeight() == 0) || (icon.getIconWidth() == 0))) {
/*  916 */       icon = null;
/*      */     }
/*  918 */     viewRect.width -= getRightMargin();
/*  919 */     String newText = SwingUtilities.layoutCompoundLabel(this.menuItem, fm, text, icon, verticalAlignment, horizontalAlignment, verticalTextPosition, horizontalTextPosition, viewRect, iconRect, textRect, textIconGap);
/*      */ 
/*  924 */     if (getRightMargin() > 0) {
/*  925 */       text = newText;
/*      */     }
/*  927 */     viewRect.width += getRightMargin();
/*      */ 
/*  929 */     Insets insets = isDownArrowVisible(this.menuItem.getParent()) ? new Insets(0, 0, 0, 0) : this.menuItem.getInsets();
/*      */ 
/*  931 */     viewRect.x = insets.left;
/*  932 */     viewRect.y = insets.top;
/*  933 */     if (JideSwingUtilities.getOrientationOf(this.menuItem) == 0)
/*      */     {
/*  935 */       viewRect.height = (this.menuItem.getHeight() - insets.top - insets.bottom);
/*  936 */       viewRect.width = (this.menuItem.getWidth() - insets.left - insets.right);
/*      */     }
/*      */     else
/*      */     {
/*  940 */       viewRect.height = (this.menuItem.getWidth() - insets.top - insets.bottom);
/*  941 */       viewRect.width = (this.menuItem.getHeight() - insets.left - insets.right);
/*      */     }
/*      */ 
/*  948 */     if ((acceleratorText == null) || (acceleratorText.equals(""))) {
/*  949 */       acceleratorRect.width = (acceleratorRect.height = 0);
/*      */     }
/*      */     else {
/*  952 */       acceleratorRect.width = SwingUtilities.computeStringWidth(fmAccel, acceleratorText);
/*  953 */       acceleratorRect.height = fmAccel.getHeight();
/*      */     }
/*      */ 
/*  956 */     if ((text == null) || (text.equals(""))) {
/*  957 */       textRect.width = (textRect.height = 0);
/*  958 */       text = "";
/*      */     }
/*      */     else
/*      */     {
/*  962 */       View v = this.menuItem != null ? (View)this.menuItem.getClientProperty("html") : null;
/*  963 */       if (v != null) {
/*  964 */         textRect.width = (int)v.getPreferredSpan(0);
/*  965 */         textRect.height = (int)v.getPreferredSpan(1);
/*      */       }
/*      */       else {
/*  968 */         textRect.width = SwingUtilities.computeStringWidth(fm, text);
/*  969 */         textRect.height = fm.getHeight();
/*      */       }
/*      */     }
/*      */ 
/*  973 */     if (icon == null) {
/*  974 */       if (useCheckAndArrow())
/*  975 */         iconRect.width = (iconRect.height = 16);
/*      */       else
/*  977 */         iconRect.width = (iconRect.height = 0);
/*      */     }
/*      */     else {
/*  980 */       iconRect.width = icon.getIconWidth();
/*  981 */       iconRect.height = icon.getIconHeight();
/*      */     }
/*      */ 
/*  984 */     if (arrowIcon == null) {
/*  985 */       arrowIconRect.width = (arrowIconRect.height = 0);
/*      */     }
/*      */     else {
/*  988 */       arrowIconRect.width = arrowIcon.getIconWidth();
/*  989 */       arrowIconRect.height = arrowIcon.getIconHeight();
/*      */     }
/*      */ 
/*  992 */     if (checkIcon == null) {
/*  993 */       checkIconRect.width = (checkIconRect.height = 0);
/*      */     }
/*      */     else {
/*  996 */       checkIconRect.width = checkIcon.getIconWidth();
/*  997 */       checkIconRect.height = checkIcon.getIconHeight();
/*      */     }
/*      */ 
/* 1001 */     if (useCheckAndArrow()) {
/* 1002 */       iconRect.x = (this.defaultShadowWidth - iconRect.width >> 1);
/* 1003 */       if ((text != null) && (!text.equals(""))) {
/* 1004 */         textRect.x = (viewRect.x + this.defaultShadowWidth + textIconGap);
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/* 1032 */     acceleratorRect.x = (viewRect.x + viewRect.width - menuItemGap - acceleratorRect.width);
/*      */ 
/* 1036 */     if (useCheckAndArrow()) {
/* 1037 */       checkIconRect.x = (viewRect.x + (this.defaultShadowWidth - checkIconRect.width) >> 1);
/* 1038 */       arrowIconRect.x = (viewRect.x + viewRect.width - menuItemGap - 5 - arrowIconRect.width);
/*      */     }
/*      */ 
/* 1042 */     if (verticalTextPosition == 0)
/*      */     {
/* 1044 */       if ((text != null) && (!text.equals(""))) {
/* 1045 */         viewRect.y += (viewRect.height - textRect.height >> 1);
/*      */       }
/* 1047 */       viewRect.y += (viewRect.height - iconRect.height >> 1);
/*      */     }
/*      */ 
/* 1050 */     Rectangle labelRect = iconRect.union(textRect);
/*      */ 
/* 1054 */     acceleratorRect.y = (labelRect.y + (labelRect.height >> 1) - (acceleratorRect.height >> 1));
/*      */ 
/* 1056 */     if (useCheckAndArrow()) {
/* 1057 */       viewRect.y += (viewRect.height - arrowIconRect.height >> 1);
/* 1058 */       viewRect.y += (viewRect.height - checkIconRect.height >> 1);
/*      */     }
/*      */ 
/* 1061 */     if (((useCheckAndArrow()) || ((this.menuItem instanceof JideSplitButton))) && (!this.menuItem.getComponentOrientation().isLeftToRight())) {
/* 1062 */       checkIconRect.x = (viewRect.width - checkIconRect.width - checkIconRect.x);
/* 1063 */       iconRect.x = (viewRect.width - iconRect.width - iconRect.x);
/* 1064 */       textRect.x = (viewRect.width - textRect.width - textRect.x);
/* 1065 */       acceleratorRect.x = (viewRect.width - acceleratorRect.width - acceleratorRect.x);
/* 1066 */       arrowIconRect.x = (viewRect.width - arrowIconRect.width - arrowIconRect.x);
/*      */     }
/*      */ 
/* 1074 */     return text;
/*      */   }
/*      */ 
/*      */   protected boolean useCheckAndArrow()
/*      */   {
/* 1082 */     boolean b = true;
/* 1083 */     if (((this.menuItem instanceof JMenu)) && (((JMenu)this.menuItem).isTopLevelMenu()))
/*      */     {
/* 1085 */       b = false;
/*      */     }
/* 1087 */     return b;
/*      */   }
/*      */ 
/*      */   public MenuElement[] getPath() {
/* 1091 */     MenuSelectionManager m = MenuSelectionManager.defaultManager();
/* 1092 */     MenuElement[] oldPath = m.getSelectedPath();
/*      */ 
/* 1094 */     int i = oldPath.length;
/* 1095 */     if (i == 0)
/* 1096 */       return new MenuElement[0];
/* 1097 */     Component parent = this.menuItem.getParent();
/*      */     MenuElement[] newPath;
/* 1098 */     if (oldPath[(i - 1)].getComponent() == parent)
/*      */     {
/* 1100 */       MenuElement[] newPath = new MenuElement[i + 1];
/* 1101 */       System.arraycopy(oldPath, 0, newPath, 0, i);
/* 1102 */       newPath[i] = this.menuItem;
/*      */     }
/*      */     else
/*      */     {
/* 1112 */       for (int j = oldPath.length - 1; (j >= 0) && 
/* 1113 */         (oldPath[j].getComponent() != parent); j--);
/* 1116 */       newPath = new MenuElement[j + 2];
/* 1117 */       System.arraycopy(oldPath, 0, newPath, 0, j + 1);
/* 1118 */       newPath[(j + 1)] = this.menuItem;
/*      */     }
/*      */ 
/* 1127 */     return newPath;
/*      */   }
/*      */ 
/*      */   protected void doClick(MenuSelectionManager msm)
/*      */   {
/* 1367 */     if (msm == null) {
/* 1368 */       msm = MenuSelectionManager.defaultManager();
/*      */     }
/* 1370 */     msm.clearSelectedPath();
/* 1371 */     this.menuItem.doClick(0);
/*      */   }
/*      */ 
/*      */   protected ThemePainter getPainter() {
/* 1375 */     return this._painter;
/*      */   }
/*      */ 
/*      */   protected boolean isDownArrowVisible(Container c) {
/* 1379 */     if (((c instanceof TopLevelMenuContainer)) && (((TopLevelMenuContainer)c).isMenuBar())) {
/* 1380 */       return false;
/*      */     }
/* 1382 */     if (((c instanceof TopLevelMenuContainer)) && (!((TopLevelMenuContainer)c).isMenuBar())) {
/* 1383 */       return true;
/*      */     }
/*      */ 
/* 1386 */     return !(c instanceof JMenuBar);
/*      */   }
/*      */ 
/*      */   protected boolean isFloatingIcon()
/*      */   {
/* 1394 */     return this._isFloatingIcon;
/*      */   }
/*      */ 
/*      */   protected Icon getIcon(AbstractButton b) {
/* 1398 */     ButtonModel model = b.getModel();
/* 1399 */     Icon icon = b.getIcon();
/* 1400 */     Icon tmpIcon = null;
/* 1401 */     if (!model.isEnabled()) {
/* 1402 */       if (model.isSelected()) {
/* 1403 */         tmpIcon = b.getDisabledSelectedIcon();
/*      */       }
/*      */       else {
/* 1406 */         tmpIcon = b.getDisabledIcon();
/*      */       }
/*      */ 
/* 1410 */       if (tmpIcon == null) {
/* 1411 */         if ((icon instanceof ImageIcon)) {
/* 1412 */           icon = IconsFactory.createGrayImage(((ImageIcon)icon).getImage());
/*      */         }
/*      */         else {
/* 1415 */           icon = IconsFactory.createGrayImage(b, icon);
/*      */         }
/*      */       }
/*      */     }
/* 1419 */     else if ((model.isPressed()) && (model.isArmed())) { tmpIcon = b.getPressedIcon();
/* 1421 */       if (tmpIcon == null);
/*      */     }
/* 1424 */     else if ((b.isRolloverEnabled()) && (model.isRollover())) {
/* 1425 */       if (model.isSelected()) {
/* 1426 */         tmpIcon = b.getRolloverSelectedIcon();
/*      */       }
/*      */       else {
/* 1429 */         tmpIcon = b.getRolloverIcon();
/*      */       }
/*      */     }
/* 1432 */     else if (model.isSelected()) {
/* 1433 */       tmpIcon = b.getSelectedIcon();
/*      */     }
/*      */ 
/* 1436 */     if (tmpIcon != null) {
/* 1437 */       icon = tmpIcon;
/*      */     }
/* 1439 */     return icon;
/*      */   }
/*      */ 
/*      */   protected int getRightMargin() {
/* 1443 */     return 0;
/*      */   }
/*      */ 
/*      */   private static class ClickAction extends AbstractAction
/*      */   {
/*      */     private static final long serialVersionUID = -8707660318949717143L;
/*      */ 
/*      */     public void actionPerformed(ActionEvent e)
/*      */     {
/* 1334 */       JMenuItem mi = (JMenuItem)e.getSource();
/* 1335 */       MenuSelectionManager.defaultManager().clearSelectedPath();
/* 1336 */       mi.doClick();
/*      */     }
/*      */   }
/*      */ 
/*      */   private class PropertyChangeHandler
/*      */     implements PropertyChangeListener
/*      */   {
/*      */     private PropertyChangeHandler()
/*      */     {
/*      */     }
/*      */ 
/*      */     public void propertyChange(PropertyChangeEvent e)
/*      */     {
/* 1300 */       String name = e.getPropertyName();
/*      */ 
/* 1302 */       if ((name.equals("labelFor")) || (name.equals("displayedMnemonic")) || (name.equals("accelerator")))
/*      */       {
/* 1304 */         VsnetMenuItemUI.this.updateAcceleratorBinding();
/*      */       }
/* 1306 */       else if (("buttonStyle".equals(name)) || ("opaque".equals(name)) || ("contentAreaFilled".equals(name)))
/*      */       {
/* 1310 */         JMenuItem lbl = (JMenuItem)e.getSource();
/* 1311 */         lbl.repaint();
/*      */       }
/* 1313 */       else if (name.equals("orientation")) {
/* 1314 */         JMenuItem lbl = (JMenuItem)e.getSource();
/* 1315 */         lbl.invalidate();
/* 1316 */         lbl.repaint();
/*      */       }
/* 1318 */       else if ((name.equals("text")) || ("font".equals(name)) || ("foreground".equals(name)))
/*      */       {
/* 1323 */         JMenuItem lbl = (JMenuItem)e.getSource();
/* 1324 */         String text = lbl.getText();
/* 1325 */         BasicHTML.updateRenderer(lbl, text);
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   private class MenuKeyHandler
/*      */     implements MenuKeyListener
/*      */   {
/*      */     private MenuKeyHandler()
/*      */     {
/*      */     }
/*      */ 
/*      */     public void menuKeyTyped(MenuKeyEvent e)
/*      */     {
/* 1274 */       if ((VsnetMenuItemUI.this.menuItem != null) && (VsnetMenuItemUI.this.menuItem.isEnabled())) {
/* 1275 */         int key = VsnetMenuItemUI.this.menuItem.getMnemonic();
/* 1276 */         if ((key == 0) || (e.getPath().length != 2))
/* 1277 */           return;
/* 1278 */         if (lower((char)key) == lower(e.getKeyChar())) {
/* 1279 */           MenuSelectionManager manager = e.getMenuSelectionManager();
/*      */ 
/* 1281 */           VsnetMenuItemUI.this.doClick(manager);
/* 1282 */           e.consume();
/*      */         }
/*      */       }
/*      */     }
/*      */ 
/*      */     public void menuKeyPressed(MenuKeyEvent e) {
/*      */     }
/*      */ 
/*      */     public void menuKeyReleased(MenuKeyEvent e) {
/*      */     }
/*      */ 
/*      */     private char lower(char keyChar) {
/* 1294 */       return Character.toLowerCase(keyChar);
/*      */     }
/*      */   }
/*      */ 
/*      */   private class MenuDragMouseHandler
/*      */     implements MenuDragMouseListener
/*      */   {
/*      */     private MenuDragMouseHandler()
/*      */     {
/*      */     }
/*      */ 
/*      */     public void menuDragMouseEntered(MenuDragMouseEvent e)
/*      */     {
/*      */     }
/*      */ 
/*      */     public void menuDragMouseDragged(MenuDragMouseEvent e)
/*      */     {
/* 1239 */       if ((VsnetMenuItemUI.this.menuItem != null) && (VsnetMenuItemUI.this.menuItem.isEnabled())) {
/* 1240 */         MenuSelectionManager manager = e.getMenuSelectionManager();
/* 1241 */         MenuElement[] path = e.getPath();
/* 1242 */         manager.setSelectedPath(path);
/*      */       }
/*      */     }
/*      */ 
/*      */     public void menuDragMouseExited(MenuDragMouseEvent e) {
/*      */     }
/*      */ 
/*      */     public void menuDragMouseReleased(MenuDragMouseEvent e) {
/* 1250 */       if ((VsnetMenuItemUI.this.menuItem != null) && (VsnetMenuItemUI.this.menuItem.isEnabled())) {
/* 1251 */         MenuSelectionManager manager = e.getMenuSelectionManager();
/* 1252 */         Point p = e.getPoint();
/* 1253 */         if ((p.x >= 0) && (p.x < VsnetMenuItemUI.this.menuItem.getWidth()) && (p.y >= 0) && (p.y < VsnetMenuItemUI.this.menuItem.getHeight()))
/*      */         {
/* 1255 */           VsnetMenuItemUI.this.doClick(manager);
/*      */         }
/*      */         else
/* 1258 */           manager.clearSelectedPath();
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   protected class MouseInputHandler
/*      */     implements MouseInputListener
/*      */   {
/*      */     protected MouseInputHandler()
/*      */     {
/*      */     }
/*      */ 
/*      */     public void mouseClicked(MouseEvent e)
/*      */     {
/*      */     }
/*      */ 
/*      */     public void mousePressed(MouseEvent e)
/*      */     {
/*      */     }
/*      */ 
/*      */     public void mouseReleased(MouseEvent e)
/*      */     {
/* 1161 */       if ((VsnetMenuItemUI.this.menuItem != null) && (VsnetMenuItemUI.this.menuItem.isEnabled())) {
/* 1162 */         MenuSelectionManager manager = MenuSelectionManager.defaultManager();
/* 1163 */         Point p = e.getPoint();
/* 1164 */         if ((p.x >= 0) && (p.x < VsnetMenuItemUI.this.menuItem.getWidth()) && (p.y >= 0) && (p.y < VsnetMenuItemUI.this.menuItem.getHeight()))
/*      */         {
/* 1166 */           VsnetMenuItemUI.this.doClick(manager);
/*      */         }
/*      */         else
/* 1169 */           manager.processMouseEvent(e);
/*      */       }
/*      */     }
/*      */ 
/*      */     public void mouseEntered(MouseEvent e)
/*      */     {
/* 1175 */       if ((VsnetMenuItemUI.this.menuItem != null) && (VsnetMenuItemUI.this.menuItem.isEnabled())) {
/* 1176 */         MenuSelectionManager manager = MenuSelectionManager.defaultManager();
/* 1177 */         int modifiers = e.getModifiers();
/*      */ 
/* 1179 */         if ((modifiers & 0x1C) != 0)
/*      */         {
/* 1181 */           MenuSelectionManager.defaultManager().processMouseEvent(e);
/*      */         }
/*      */         else
/* 1184 */           manager.setSelectedPath(VsnetMenuItemUI.this.getPath());
/*      */       }
/*      */     }
/*      */ 
/*      */     public void mouseExited(MouseEvent e)
/*      */     {
/* 1190 */       if ((VsnetMenuItemUI.this.menuItem != null) && (VsnetMenuItemUI.this.menuItem.isEnabled())) {
/* 1191 */         MenuSelectionManager manager = MenuSelectionManager.defaultManager();
/*      */ 
/* 1193 */         int modifiers = e.getModifiers();
/*      */ 
/* 1195 */         if ((modifiers & 0x1C) != 0)
/*      */         {
/* 1197 */           MenuSelectionManager.defaultManager().processMouseEvent(e);
/*      */         }
/*      */         else
/*      */         {
/* 1201 */           MenuElement[] path = manager.getSelectedPath();
/* 1202 */           if (path.length > 1) {
/* 1203 */             MenuElement[] newPath = new MenuElement[path.length - 1];
/*      */ 
/* 1205 */             int i = 0; for (int c = path.length - 1; i < c; i++)
/* 1206 */               newPath[i] = path[i];
/* 1207 */             manager.setSelectedPath(newPath);
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */ 
/*      */     public void mouseDragged(MouseEvent e) {
/* 1214 */       MenuSelectionManager.defaultManager().processMouseEvent(e);
/*      */     }
/*      */ 
/*      */     public void mouseMoved(MouseEvent e) {
/* 1218 */       if ((VsnetMenuItemUI.this.menuItem != null) && (VsnetMenuItemUI.this.menuItem.isEnabled())) {
/* 1219 */         MenuSelectionManager manager = MenuSelectionManager.defaultManager();
/* 1220 */         int modifiers = e.getModifiers();
/*      */ 
/* 1222 */         if ((modifiers & 0x1C) == 0)
/*      */         {
/* 1224 */           MenuElement[] path = manager.getSelectedPath();
/* 1225 */           if (VsnetMenuItemUI.this.getPath().length > path.length)
/* 1226 */             manager.setSelectedPath(VsnetMenuItemUI.this.getPath());
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     com.jidesoft.plaf.vsnet.VsnetMenuItemUI
 * JD-Core Version:    0.6.0
 */