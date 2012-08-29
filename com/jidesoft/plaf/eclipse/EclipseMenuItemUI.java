/*      */ package com.jidesoft.plaf.eclipse;
/*      */ 
/*      */ import com.jidesoft.icons.IconsFactory;
/*      */ import com.jidesoft.plaf.UIDefaultsLookup;
/*      */ import com.jidesoft.plaf.basic.ThemePainter;
/*      */ import com.jidesoft.swing.JideSwingUtilities;
/*      */ import com.jidesoft.swing.TopLevelMenuContainer;
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
/*      */ import java.awt.geom.AffineTransform;
/*      */ import java.beans.PropertyChangeEvent;
/*      */ import java.beans.PropertyChangeListener;
/*      */ import javax.swing.AbstractAction;
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
/*      */ public class EclipseMenuItemUI extends MenuItemUI
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
/*      */   InputMap windowInputMap;
/*      */   private static final boolean DEBUG = false;
/*      */   protected Color shadowColor;
/*      */   protected int defaultAccelEndGap;
/*      */   protected int defaultShadowWidth;
/*      */   private Color borderColor;
/*      */   private Color backgroundColor;
/*      */   static final String MAX_TEXT_WIDTH = "maxTextWidth";
/*      */   static final String MAX_ACC_WIDTH = "maxAccWidth";
/*      */   protected ThemePainter _painter;
/*  369 */   static Rectangle zeroRect = new Rectangle(0, 0, 0, 0);
/*  370 */   static Rectangle iconRect = new Rectangle();
/*  371 */   static Rectangle textRect = new Rectangle();
/*  372 */   static Rectangle acceleratorRect = new Rectangle();
/*  373 */   static Rectangle checkIconRect = new Rectangle();
/*  374 */   static Rectangle arrowIconRect = new Rectangle();
/*  375 */   static Rectangle viewRect = new Rectangle(32767, 32767);
/*  376 */   static Rectangle r = new Rectangle();
/*      */ 
/*      */   public EclipseMenuItemUI()
/*      */   {
/*   36 */     this.menuItem = null;
/*      */ 
/*   52 */     this.arrowIcon = null;
/*   53 */     this.checkIcon = null;
/*      */   }
/*      */ 
/*      */   public static ComponentUI createUI(JComponent c)
/*      */   {
/*   79 */     return new EclipseMenuItemUI();
/*      */   }
/*      */ 
/*      */   public void installUI(JComponent c)
/*      */   {
/*   84 */     this.menuItem = ((JMenuItem)c);
/*      */ 
/*   86 */     installDefaults();
/*   87 */     installComponents(this.menuItem);
/*   88 */     installListeners();
/*   89 */     installKeyboardActions();
/*      */   }
/*      */ 
/*      */   protected void installDefaults() {
/*   93 */     this._painter = ((ThemePainter)UIDefaultsLookup.get("Theme.painter"));
/*   94 */     String prefix = getPropertyPrefix();
/*      */ 
/*   96 */     this.acceleratorFont = UIDefaultsLookup.getFont("MenuItem.acceleratorFont");
/*      */ 
/*   98 */     this.menuItem.setOpaque(true);
/*   99 */     if ((this.menuItem.getMargin() == null) || ((this.menuItem.getMargin() instanceof UIResource)))
/*      */     {
/*  104 */       this.menuItem.setMargin(UIDefaultsLookup.getInsets(prefix + ".margin"));
/*      */     }
/*      */ 
/*  108 */     this.defaultTextIconGap = UIDefaultsLookup.getInt("MenuItem.textIconGap");
/*  109 */     this.defaultAccelEndGap = UIDefaultsLookup.getInt("MenuItem.accelEndGap");
/*  110 */     this.defaultShadowWidth = UIDefaultsLookup.getInt("MenuItem.shadowWidth");
/*      */ 
/*  113 */     this.borderColor = UIDefaultsLookup.getColor("MenuItem.selectionBorderColor");
/*  114 */     this.backgroundColor = UIDefaultsLookup.getColor("MenuItem.background");
/*  115 */     this.shadowColor = UIDefaultsLookup.getColor("MenuItem.shadowColor");
/*      */ 
/*  117 */     LookAndFeel.installBorder(this.menuItem, prefix + ".border");
/*  118 */     this.oldBorderPainted = this.menuItem.isBorderPainted();
/*  119 */     Object value = UIDefaultsLookup.get(prefix + ".borderPainted");
/*  120 */     this.menuItem.setBorderPainted((value instanceof Boolean) ? ((Boolean)value).booleanValue() : false);
/*  121 */     LookAndFeel.installColorsAndFont(this.menuItem, prefix + ".background", prefix + ".foreground", prefix + ".font");
/*      */ 
/*  127 */     if ((this.selectionBackground == null) || ((this.selectionBackground instanceof UIResource)))
/*      */     {
/*  129 */       this.selectionBackground = UIDefaultsLookup.getColor(prefix + ".selectionBackground");
/*      */     }
/*      */ 
/*  132 */     if ((this.selectionForeground == null) || ((this.selectionForeground instanceof UIResource)))
/*      */     {
/*  134 */       this.selectionForeground = UIDefaultsLookup.getColor(prefix + ".selectionForeground");
/*      */     }
/*      */ 
/*  137 */     if ((this.disabledForeground == null) || ((this.disabledForeground instanceof UIResource)))
/*      */     {
/*  139 */       this.disabledForeground = UIDefaultsLookup.getColor(prefix + ".disabledForeground");
/*      */     }
/*      */ 
/*  142 */     if ((this.acceleratorForeground == null) || ((this.acceleratorForeground instanceof UIResource)))
/*      */     {
/*  144 */       this.acceleratorForeground = UIDefaultsLookup.getColor(prefix + ".acceleratorForeground");
/*      */     }
/*      */ 
/*  147 */     if ((this.acceleratorSelectionForeground == null) || ((this.acceleratorSelectionForeground instanceof UIResource)))
/*      */     {
/*  149 */       this.acceleratorSelectionForeground = UIDefaultsLookup.getColor(prefix + ".acceleratorSelectionForeground");
/*      */     }
/*      */ 
/*  153 */     this.acceleratorDelimiter = UIDefaultsLookup.getString("MenuItem.acceleratorDelimiter");
/*      */ 
/*  155 */     if (this.acceleratorDelimiter == null) {
/*  156 */       this.acceleratorDelimiter = "+";
/*      */     }
/*      */ 
/*  159 */     if ((this.arrowIcon == null) || ((this.arrowIcon instanceof UIResource)))
/*      */     {
/*  161 */       this.arrowIcon = UIDefaultsLookup.getIcon(prefix + ".arrowIcon");
/*      */     }
/*  163 */     if ((this.checkIcon == null) || ((this.checkIcon instanceof UIResource)))
/*      */     {
/*  165 */       this.checkIcon = UIDefaultsLookup.getIcon(prefix + ".checkIcon");
/*      */     }
/*      */   }
/*      */ 
/*      */   protected void installComponents(JMenuItem menuItem)
/*      */   {
/*  173 */     BasicHTML.updateRenderer(menuItem, menuItem.getText());
/*      */   }
/*      */ 
/*      */   protected String getPropertyPrefix() {
/*  177 */     return "MenuItem";
/*      */   }
/*      */ 
/*      */   protected void installListeners() {
/*  181 */     if ((this.mouseInputListener = createMouseInputListener(this.menuItem)) != null) {
/*  182 */       this.menuItem.addMouseListener(this.mouseInputListener);
/*  183 */       this.menuItem.addMouseMotionListener(this.mouseInputListener);
/*      */     }
/*  185 */     if ((this.menuDragMouseListener = createMenuDragMouseListener(this.menuItem)) != null) {
/*  186 */       this.menuItem.addMenuDragMouseListener(this.menuDragMouseListener);
/*      */     }
/*  188 */     if ((this.menuKeyListener = createMenuKeyListener(this.menuItem)) != null) {
/*  189 */       this.menuItem.addMenuKeyListener(this.menuKeyListener);
/*      */     }
/*  191 */     if ((this.propertyChangeListener = createPropertyChangeListener(this.menuItem)) != null)
/*  192 */       this.menuItem.addPropertyChangeListener(this.propertyChangeListener);
/*      */   }
/*      */ 
/*      */   protected void installKeyboardActions()
/*      */   {
/*  197 */     ActionMap actionMap = getActionMap();
/*      */ 
/*  199 */     SwingUtilities.replaceUIActionMap(this.menuItem, actionMap);
/*  200 */     updateAcceleratorBinding();
/*      */   }
/*      */ 
/*      */   public void uninstallUI(JComponent c)
/*      */   {
/*  205 */     this.menuItem = ((JMenuItem)c);
/*  206 */     uninstallDefaults();
/*  207 */     uninstallComponents(this.menuItem);
/*  208 */     uninstallListeners();
/*  209 */     uninstallKeyboardActions();
/*      */ 
/*  212 */     Container parent = this.menuItem.getParent();
/*  213 */     if ((parent != null) && ((parent instanceof JComponent)) && ((!(this.menuItem instanceof JMenu)) || (!((JMenu)this.menuItem).isTopLevelMenu())))
/*      */     {
/*  215 */       JComponent p = (JComponent)parent;
/*  216 */       p.putClientProperty("maxAccWidth", null);
/*  217 */       p.putClientProperty("maxTextWidth", null);
/*      */     }
/*      */ 
/*  220 */     this.menuItem = null;
/*      */   }
/*      */ 
/*      */   protected void uninstallDefaults()
/*      */   {
/*  225 */     this._painter = null;
/*  226 */     LookAndFeel.uninstallBorder(this.menuItem);
/*  227 */     this.menuItem.setBorderPainted(this.oldBorderPainted);
/*  228 */     if ((this.menuItem.getMargin() instanceof UIResource))
/*  229 */       this.menuItem.setMargin(null);
/*  230 */     if ((this.arrowIcon instanceof UIResource))
/*  231 */       this.arrowIcon = null;
/*  232 */     if ((this.checkIcon instanceof UIResource))
/*  233 */       this.checkIcon = null;
/*      */   }
/*      */ 
/*      */   protected void uninstallComponents(JMenuItem menuItem)
/*      */   {
/*  240 */     BasicHTML.updateRenderer(menuItem, "");
/*      */   }
/*      */ 
/*      */   protected void uninstallListeners() {
/*  244 */     if (this.mouseInputListener != null) {
/*  245 */       this.menuItem.removeMouseListener(this.mouseInputListener);
/*  246 */       this.menuItem.removeMouseMotionListener(this.mouseInputListener);
/*      */     }
/*  248 */     if (this.menuDragMouseListener != null) {
/*  249 */       this.menuItem.removeMenuDragMouseListener(this.menuDragMouseListener);
/*      */     }
/*  251 */     if (this.menuKeyListener != null) {
/*  252 */       this.menuItem.removeMenuKeyListener(this.menuKeyListener);
/*      */     }
/*  254 */     if (this.propertyChangeListener != null) {
/*  255 */       this.menuItem.removePropertyChangeListener(this.propertyChangeListener);
/*      */     }
/*      */ 
/*  258 */     this.mouseInputListener = null;
/*  259 */     this.menuDragMouseListener = null;
/*  260 */     this.menuKeyListener = null;
/*  261 */     this.propertyChangeListener = null;
/*      */   }
/*      */ 
/*      */   protected void uninstallKeyboardActions() {
/*  265 */     SwingUtilities.replaceUIActionMap(this.menuItem, null);
/*  266 */     if (this.windowInputMap != null) {
/*  267 */       SwingUtilities.replaceUIInputMap(this.menuItem, 2, null);
/*      */ 
/*  269 */       this.windowInputMap = null;
/*      */     }
/*      */   }
/*      */ 
/*      */   protected MouseInputListener createMouseInputListener(JComponent c) {
/*  274 */     return new MouseInputHandler();
/*      */   }
/*      */ 
/*      */   protected MenuDragMouseListener createMenuDragMouseListener(JComponent c) {
/*  278 */     return new MenuDragMouseHandler(null);
/*      */   }
/*      */ 
/*      */   protected MenuKeyListener createMenuKeyListener(JComponent c) {
/*  282 */     return new MenuKeyHandler(null);
/*      */   }
/*      */ 
/*      */   private PropertyChangeListener createPropertyChangeListener(JComponent c) {
/*  286 */     return new PropertyChangeHandler(null);
/*      */   }
/*      */ 
/*      */   ActionMap getActionMap() {
/*  290 */     String propertyPrefix = getPropertyPrefix();
/*  291 */     String uiKey = propertyPrefix + ".actionMap";
/*  292 */     ActionMap am = (ActionMap)UIDefaultsLookup.get(uiKey);
/*  293 */     if (am == null) {
/*  294 */       am = createActionMap();
/*  295 */       UIManager.getLookAndFeelDefaults().put(uiKey, am);
/*      */     }
/*  297 */     return am;
/*      */   }
/*      */ 
/*      */   ActionMap createActionMap() {
/*  301 */     ActionMap map = new ActionMapUIResource();
/*  302 */     map.put("doClick", new ClickAction(null));
/*      */ 
/*  310 */     return map;
/*      */   }
/*      */ 
/*      */   InputMap createInputMap(int condition) {
/*  314 */     if (condition == 2) {
/*  315 */       return new ComponentInputMapUIResource(this.menuItem);
/*      */     }
/*  317 */     return null;
/*      */   }
/*      */ 
/*      */   void updateAcceleratorBinding() {
/*  321 */     KeyStroke accelerator = this.menuItem.getAccelerator();
/*      */ 
/*  323 */     if (this.windowInputMap != null) {
/*  324 */       this.windowInputMap.clear();
/*      */     }
/*  326 */     if (accelerator != null) {
/*  327 */       if (this.windowInputMap == null) {
/*  328 */         this.windowInputMap = createInputMap(2);
/*      */ 
/*  330 */         SwingUtilities.replaceUIInputMap(this.menuItem, 2, this.windowInputMap);
/*      */       }
/*      */ 
/*  333 */       this.windowInputMap.put(accelerator, "doClick");
/*      */     }
/*      */   }
/*      */ 
/*      */   public Dimension getMinimumSize(JComponent c)
/*      */   {
/*  339 */     Dimension d = null;
/*  340 */     View v = (View)c.getClientProperty("html");
/*  341 */     if (v != null) {
/*  342 */       d = getPreferredSize(c);
/*      */       Dimension tmp23_22 = d; tmp23_22.width = (int)(tmp23_22.width - (v.getPreferredSpan(0) - v.getMinimumSpan(0)));
/*      */     }
/*  345 */     return d;
/*      */   }
/*      */ 
/*      */   public Dimension getPreferredSize(JComponent c)
/*      */   {
/*  350 */     return getPreferredMenuItemSize(c, this.checkIcon, this.arrowIcon, this.defaultTextIconGap);
/*      */   }
/*      */ 
/*      */   public Dimension getMaximumSize(JComponent c)
/*      */   {
/*  358 */     Dimension d = null;
/*  359 */     View v = (View)c.getClientProperty("html");
/*  360 */     if (v != null) {
/*  361 */       d = getPreferredSize(c);
/*      */       Dimension tmp23_22 = d; tmp23_22.width = (int)(tmp23_22.width + (v.getMaximumSpan(0) - v.getPreferredSpan(0)));
/*      */     }
/*  364 */     return d;
/*      */   }
/*      */ 
/*      */   private void resetRects()
/*      */   {
/*  379 */     iconRect.setBounds(zeroRect);
/*  380 */     textRect.setBounds(zeroRect);
/*  381 */     acceleratorRect.setBounds(zeroRect);
/*  382 */     checkIconRect.setBounds(zeroRect);
/*  383 */     arrowIconRect.setBounds(zeroRect);
/*  384 */     viewRect.setBounds(0, 0, 32767, 32767);
/*  385 */     r.setBounds(zeroRect);
/*      */   }
/*      */ 
/*      */   protected Dimension getPreferredMenuItemSize(JComponent c, Icon checkIcon, Icon arrowIcon, int textIconGap)
/*      */   {
/*  392 */     JMenuItem b = (JMenuItem)c;
/*  393 */     Icon icon = b.getIcon();
/*  394 */     String text = b.getText();
/*  395 */     KeyStroke accelerator = b.getAccelerator();
/*  396 */     String acceleratorText = "";
/*      */ 
/*  398 */     if (accelerator != null) {
/*  399 */       int modifiers = accelerator.getModifiers();
/*  400 */       if (modifiers > 0) {
/*  401 */         acceleratorText = KeyEvent.getKeyModifiersText(modifiers);
/*      */ 
/*  403 */         acceleratorText = acceleratorText + this.acceleratorDelimiter;
/*      */       }
/*  405 */       int keyCode = accelerator.getKeyCode();
/*  406 */       if (keyCode != 0) {
/*  407 */         acceleratorText = acceleratorText + KeyEvent.getKeyText(keyCode);
/*      */       }
/*      */       else {
/*  410 */         acceleratorText = acceleratorText + accelerator.getKeyChar();
/*      */       }
/*      */     }
/*      */ 
/*  414 */     Font font = b.getFont();
/*  415 */     FontMetrics fm = b.getFontMetrics(font);
/*  416 */     FontMetrics fmAccel = b.getFontMetrics(this.acceleratorFont);
/*      */ 
/*  418 */     resetRects();
/*      */ 
/*  420 */     layoutMenuItem(fm, text, fmAccel, acceleratorText, icon, checkIcon, arrowIcon, b.getVerticalAlignment(), b.getHorizontalAlignment(), b.getVerticalTextPosition(), b.getHorizontalTextPosition(), viewRect, iconRect, textRect, acceleratorRect, checkIconRect, arrowIconRect, text == null ? 0 : textIconGap, this.defaultAccelEndGap);
/*      */ 
/*  427 */     r.setBounds(textRect);
/*  428 */     if (!iconRect.isEmpty()) {
/*  429 */       r = SwingUtilities.computeUnion(iconRect.x, iconRect.y, iconRect.width, iconRect.height, r);
/*      */     }
/*      */ 
/*  439 */     Container parent = this.menuItem.getParent();
/*      */ 
/*  442 */     if ((parent != null) && ((parent instanceof JComponent)) && ((!(this.menuItem instanceof JMenu)) || (!((JMenu)this.menuItem).isTopLevelMenu())))
/*      */     {
/*  444 */       JComponent p = (JComponent)parent;
/*      */ 
/*  447 */       Integer maxTextWidth = (Integer)p.getClientProperty("maxTextWidth");
/*  448 */       Integer maxAccWidth = (Integer)p.getClientProperty("maxAccWidth");
/*      */ 
/*  450 */       int maxTextValue = maxTextWidth != null ? maxTextWidth.intValue() : 0;
/*  451 */       int maxAccValue = maxAccWidth != null ? maxAccWidth.intValue() : 0;
/*      */ 
/*  454 */       if (r.width < maxTextValue) {
/*  455 */         r.width = maxTextValue;
/*      */       }
/*      */       else {
/*  458 */         p.putClientProperty("maxTextWidth", Integer.valueOf(r.width));
/*      */       }
/*      */ 
/*  462 */       if (acceleratorRect.width > maxAccValue) {
/*  463 */         maxAccValue = acceleratorRect.width;
/*  464 */         p.putClientProperty("maxAccWidth", Integer.valueOf(acceleratorRect.width));
/*      */       }
/*      */ 
/*  468 */       r.width += maxAccValue;
/*  469 */       r.width += textIconGap;
/*  470 */       r.width += this.defaultAccelEndGap;
/*      */     }
/*      */ 
/*  473 */     if (icon != null) {
/*  474 */       r.width += textIconGap;
/*      */     }
/*  476 */     Insets insets = b.getInsets();
/*  477 */     if (useCheckAndArrow()) {
/*  478 */       insets = UIDefaultsLookup.getInsets("MenuItem.margin");
/*  479 */       r.width += 5;
/*      */     }
/*  481 */     if (insets != null) {
/*  482 */       r.width += insets.left + insets.right;
/*  483 */       r.height += insets.top + insets.bottom;
/*      */     }
/*      */ 
/*  488 */     if (r.width % 2 == 0) {
/*  489 */       r.width += 1;
/*      */     }
/*      */ 
/*  494 */     if (r.height % 2 == 0) {
/*  495 */       r.height += 1;
/*      */     }
/*      */ 
/*  498 */     if (JideSwingUtilities.getOrientationOf(this.menuItem) == 0) {
/*  499 */       return r.getSize();
/*      */     }
/*      */ 
/*  502 */     return new Dimension(r.height, r.width);
/*      */   }
/*      */ 
/*      */   public void update(Graphics g, JComponent c)
/*      */   {
/*  512 */     paint(g, c);
/*      */   }
/*      */ 
/*      */   public void paint(Graphics g, JComponent c)
/*      */   {
/*  517 */     paintMenuItem(g, c, this.checkIcon, this.arrowIcon, this.selectionBackground, this.selectionForeground, this.defaultTextIconGap);
/*      */   }
/*      */ 
/*      */   protected void paintMenuItem(Graphics g, JComponent c, Icon checkIcon, Icon arrowIcon, Color background, Color foreground, int defaultTextIconGap)
/*      */   {
/*  527 */     JMenuItem b = (JMenuItem)c;
/*  528 */     ButtonModel model = b.getModel();
/*      */ 
/*  530 */     int menuWidth = 0;
/*  531 */     int menuHeight = 0;
/*      */ 
/*  533 */     if (JideSwingUtilities.getOrientationOf(this.menuItem) == 0)
/*      */     {
/*  535 */       menuWidth = b.getWidth();
/*  536 */       menuHeight = b.getHeight();
/*      */     }
/*      */     else
/*      */     {
/*  540 */       menuWidth = b.getHeight();
/*  541 */       menuHeight = b.getWidth();
/*  542 */       Graphics2D g2d = (Graphics2D)g;
/*  543 */       AffineTransform oldAt = g2d.getTransform();
/*  544 */       g2d.rotate(1.570796326794897D);
/*  545 */       g2d.translate(0, -menuHeight + 1);
/*      */     }
/*      */ 
/*  548 */     Insets i = c.getInsets();
/*      */ 
/*  550 */     resetRects();
/*      */ 
/*  552 */     viewRect.setBounds(0, 0, menuWidth, menuHeight);
/*      */ 
/*  554 */     viewRect.x += i.left;
/*  555 */     viewRect.y += i.top;
/*  556 */     viewRect.width -= i.right + viewRect.x;
/*  557 */     viewRect.height -= i.bottom + viewRect.y;
/*      */ 
/*  560 */     Font holdf = g.getFont();
/*  561 */     Font f = c.getFont();
/*  562 */     g.setFont(f);
/*  563 */     FontMetrics fm = g.getFontMetrics(f);
/*  564 */     FontMetrics fmAccel = g.getFontMetrics(this.acceleratorFont);
/*      */ 
/*  567 */     KeyStroke accelerator = b.getAccelerator();
/*  568 */     String acceleratorText = "";
/*  569 */     if (accelerator != null) {
/*  570 */       int modifiers = accelerator.getModifiers();
/*  571 */       if (modifiers > 0) {
/*  572 */         acceleratorText = KeyEvent.getKeyModifiersText(modifiers);
/*      */ 
/*  574 */         acceleratorText = acceleratorText + this.acceleratorDelimiter;
/*      */       }
/*      */ 
/*  577 */       int keyCode = accelerator.getKeyCode();
/*  578 */       if (keyCode != 0) {
/*  579 */         acceleratorText = acceleratorText + KeyEvent.getKeyText(keyCode);
/*      */       }
/*      */       else {
/*  582 */         acceleratorText = acceleratorText + accelerator.getKeyChar();
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/*  587 */     String text = layoutMenuItem(fm, b.getText(), fmAccel, acceleratorText, b.getIcon(), checkIcon, arrowIcon, b.getVerticalAlignment(), b.getHorizontalAlignment(), b.getVerticalTextPosition(), b.getHorizontalTextPosition(), viewRect, iconRect, textRect, acceleratorRect, checkIconRect, arrowIconRect, b.getText() == null ? 0 : defaultTextIconGap, defaultTextIconGap);
/*      */ 
/*  597 */     paintBackground(g, b, background);
/*      */ 
/*  599 */     Color holdc = g.getColor();
/*      */ 
/*  602 */     if (((c.getUIClassID().indexOf("CheckBoxMenu") >= 0) || (c.getUIClassID().indexOf("RadioButtonMenu") >= 0)) && (checkIcon != null)) {
/*  603 */       paintCheckBox(b, g, checkIcon);
/*  604 */       g.setColor(holdc);
/*      */     }
/*      */ 
/*  607 */     paintIcon(b, g);
/*      */ 
/*  610 */     if (text != null) {
/*  611 */       View v = (View)c.getClientProperty("html");
/*  612 */       if (v != null) {
/*  613 */         v.paint(g, textRect);
/*      */       }
/*      */       else {
/*  616 */         paintText(g, b, textRect, text);
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/*  621 */     if ((acceleratorText != null) && (!acceleratorText.equals("")))
/*      */     {
/*  624 */       int accOffset = 0;
/*  625 */       Container parent = this.menuItem.getParent();
/*  626 */       if ((parent != null) && ((parent instanceof JComponent))) {
/*  627 */         JComponent p = (JComponent)parent;
/*  628 */         Integer maxValueInt = (Integer)p.getClientProperty("maxAccWidth");
/*  629 */         int maxValue = maxValueInt != null ? maxValueInt.intValue() : acceleratorRect.width;
/*      */ 
/*  633 */         accOffset = maxValue - acceleratorRect.width;
/*      */       }
/*      */ 
/*  636 */       g.setFont(this.acceleratorFont);
/*  637 */       if (!model.isEnabled())
/*      */       {
/*  639 */         if (this.disabledForeground != null) {
/*  640 */           g.setColor(this.disabledForeground);
/*  641 */           JideSwingUtilities.drawString(this.menuItem, g, acceleratorText, acceleratorRect.x - accOffset, acceleratorRect.y + fmAccel.getAscent());
/*      */         }
/*      */         else
/*      */         {
/*  646 */           g.setColor(b.getBackground().brighter());
/*  647 */           JideSwingUtilities.drawString(this.menuItem, g, acceleratorText, acceleratorRect.x - accOffset, acceleratorRect.y + fmAccel.getAscent());
/*      */ 
/*  650 */           g.setColor(b.getBackground().darker());
/*  651 */           JideSwingUtilities.drawString(this.menuItem, g, acceleratorText, acceleratorRect.x - accOffset - 1, acceleratorRect.y + fmAccel.getAscent() - 1);
/*      */         }
/*      */ 
/*      */       }
/*      */       else
/*      */       {
/*  658 */         if ((model.isArmed()) || (((c instanceof JMenu)) && (model.isSelected()))) {
/*  659 */           g.setColor(this.acceleratorSelectionForeground);
/*      */         }
/*      */         else {
/*  662 */           g.setColor(this.acceleratorForeground);
/*      */         }
/*  664 */         JideSwingUtilities.drawString(this.menuItem, g, acceleratorText, acceleratorRect.x - accOffset, acceleratorRect.y + fmAccel.getAscent());
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/*  671 */     if (arrowIcon != null) {
/*  672 */       if ((model.isArmed()) || (((c instanceof JMenu)) && (model.isSelected())))
/*  673 */         g.setColor(foreground);
/*  674 */       if (useCheckAndArrow())
/*  675 */         arrowIcon.paintIcon(c, g, arrowIconRect.x, arrowIconRect.y);
/*      */     }
/*  677 */     g.setColor(holdc);
/*  678 */     g.setFont(holdf);
/*      */   }
/*      */ 
/*      */   private void paintCheckBox(JMenuItem b, Graphics g, Icon checkIcon) {
/*  682 */     boolean selected = false;
/*  683 */     ButtonModel model = b.getModel();
/*  684 */     if ((b instanceof JCheckBoxMenuItem))
/*  685 */       selected = b.isSelected();
/*  686 */     else if ((b instanceof JRadioButtonMenuItem))
/*  687 */       selected = b.isSelected();
/*  688 */     if ((selected) && 
/*  689 */       (b.getIcon() == null))
/*  690 */       if ((model.isArmed()) || (((b instanceof JMenu)) && (model.isSelected()))) {
/*  691 */         if ((checkIcon instanceof ImageIcon)) {
/*  692 */           ImageIcon image = IconsFactory.createMaskImage(b, checkIcon, Color.BLACK, this.selectionForeground);
/*  693 */           image.paintIcon(b, g, checkIconRect.x, checkIconRect.y);
/*      */         }
/*      */         else {
/*  696 */           ImageIcon image = IconsFactory.createNegativeImage(b, checkIcon);
/*  697 */           image.paintIcon(b, g, checkIconRect.x, checkIconRect.y);
/*      */         }
/*      */ 
/*      */       }
/*  701 */       else if ((checkIcon instanceof ImageIcon)) {
/*  702 */         ImageIcon image = IconsFactory.createMaskImage(b, checkIcon, Color.BLACK, b.getForeground());
/*  703 */         image.paintIcon(b, g, checkIconRect.x, checkIconRect.y);
/*      */       }
/*      */       else {
/*  706 */         checkIcon.paintIcon(b, g, checkIconRect.x, checkIconRect.y);
/*      */       }
/*      */   }
/*      */ 
/*      */   private void paintIcon(JMenuItem b, Graphics g)
/*      */   {
/*  714 */     ButtonModel model = b.getModel();
/*      */ 
/*  716 */     if (b.getIcon() != null)
/*      */     {
/*      */       Icon icon;
/*  718 */       if (!model.isEnabled()) {
/*  719 */         Icon icon = b.getDisabledIcon();
/*  720 */         if (icon == null) {
/*  721 */           icon = b.getIcon();
/*  722 */           if ((icon instanceof ImageIcon)) {
/*  723 */             icon = IconsFactory.createGrayImage(((ImageIcon)icon).getImage());
/*      */           }
/*      */           else {
/*  726 */             icon = IconsFactory.createGrayImage(b, icon);
/*      */           }
/*      */         }
/*      */       }
/*  730 */       else if ((model.isPressed()) && (model.isArmed())) {
/*  731 */         Icon icon = b.getPressedIcon();
/*  732 */         if (icon == null)
/*      */         {
/*  734 */           icon = b.getIcon();
/*      */         }
/*      */       }
/*      */       else {
/*  738 */         icon = b.getIcon();
/*      */       }
/*      */ 
/*  741 */       if (icon != null)
/*  742 */         icon.paintIcon(b, g, iconRect.x, iconRect.y);
/*      */     }
/*      */   }
/*      */ 
/*      */   protected void paintBackground(Graphics g, JMenuItem menuItem, Color bgColor)
/*      */   {
/*  756 */     ButtonModel model = menuItem.getModel();
/*  757 */     Color oldColor = g.getColor();
/*  758 */     int menuWidth = menuItem.getWidth();
/*  759 */     int menuHeight = menuItem.getHeight();
/*      */ 
/*  761 */     if (JideSwingUtilities.getOrientationOf(menuItem) == 0) {
/*  762 */       menuWidth = menuItem.getWidth();
/*  763 */       menuHeight = menuItem.getHeight();
/*      */     }
/*      */     else {
/*  766 */       menuWidth = menuItem.getHeight();
/*  767 */       menuHeight = menuItem.getWidth();
/*      */     }
/*      */ 
/*  770 */     if (menuItem.isOpaque()) {
/*  771 */       if ((menuItem.getBackground() instanceof UIResource)) {
/*  772 */         g.setColor(this.backgroundColor);
/*      */       }
/*      */       else {
/*  775 */         g.setColor(menuItem.getBackground());
/*      */       }
/*  777 */       g.fillRect(0, 0, menuWidth, menuHeight);
/*      */ 
/*  779 */       if ((model.isArmed()) || (((menuItem instanceof JMenu)) && (model.isSelected()))) {
/*  780 */         g.setColor(bgColor);
/*  781 */         g.fillRect(1, 1, menuWidth - 2, menuHeight - 2);
/*      */       }
/*  783 */       g.setColor(oldColor);
/*      */     }
/*      */   }
/*      */ 
/*      */   protected void paintText(Graphics g, JMenuItem menuItem, Rectangle textRect, String text)
/*      */   {
/*  798 */     ButtonModel model = menuItem.getModel();
/*      */ 
/*  800 */     if (!model.isEnabled())
/*      */     {
/*  802 */       WindowsGraphicsUtils.paintText(g, menuItem, textRect, text, 0);
/*      */     }
/*      */     else {
/*  805 */       FontMetrics fm = g.getFontMetrics();
/*  806 */       int mnemonicIndex = menuItem.getDisplayedMnemonicIndex();
/*      */ 
/*  808 */       if (WindowsLookAndFeel.isMnemonicHidden() == true) {
/*  809 */         mnemonicIndex = -1;
/*      */       }
/*      */ 
/*  812 */       Color oldColor = g.getColor();
/*      */ 
/*  815 */       if ((model.isArmed()) || (((menuItem instanceof JMenu)) && (model.isSelected()))) {
/*  816 */         g.setColor(this.selectionForeground);
/*      */       }
/*  818 */       JideSwingUtilities.drawStringUnderlineCharAt(menuItem, g, text, mnemonicIndex, textRect.x, textRect.y + fm.getAscent());
/*      */ 
/*  822 */       g.setColor(oldColor);
/*      */     }
/*      */   }
/*      */ 
/*      */   private String layoutMenuItem(FontMetrics fm, String text, FontMetrics fmAccel, String acceleratorText, Icon icon, Icon checkIcon, Icon arrowIcon, int verticalAlignment, int horizontalAlignment, int verticalTextPosition, int horizontalTextPosition, Rectangle viewRect, Rectangle iconRect, Rectangle textRect, Rectangle acceleratorRect, Rectangle checkIconRect, Rectangle arrowIconRect, int textIconGap, int menuItemGap)
/*      */   {
/*  851 */     SwingUtilities.layoutCompoundLabel(this.menuItem, fm, text, icon, verticalAlignment, horizontalAlignment, verticalTextPosition, horizontalTextPosition, viewRect, iconRect, textRect, textIconGap);
/*      */ 
/*  857 */     viewRect.x = (viewRect.y = 0);
/*  858 */     if (JideSwingUtilities.getOrientationOf(this.menuItem) == 0)
/*      */     {
/*  860 */       viewRect.height = this.menuItem.getHeight();
/*  861 */       viewRect.width = this.menuItem.getWidth();
/*      */     }
/*      */     else
/*      */     {
/*  865 */       viewRect.height = this.menuItem.getWidth();
/*  866 */       viewRect.width = this.menuItem.getHeight();
/*      */     }
/*      */ 
/*  873 */     if ((acceleratorText == null) || (acceleratorText.equals(""))) {
/*  874 */       acceleratorRect.width = (acceleratorRect.height = 0);
/*  875 */       acceleratorText = "";
/*      */     }
/*      */     else {
/*  878 */       acceleratorRect.width = SwingUtilities.computeStringWidth(fmAccel, acceleratorText);
/*  879 */       acceleratorRect.height = fmAccel.getHeight();
/*      */     }
/*      */ 
/*  882 */     if ((text == null) || (text.equals(""))) {
/*  883 */       textRect.width = (textRect.height = 0);
/*  884 */       text = "";
/*      */     }
/*      */     else {
/*  887 */       boolean textIsEmpty = (text == null) || (text.equals(""));
/*  888 */       int lsb = 0;
/*      */ 
/*  890 */       View v = null;
/*  891 */       v = this.menuItem != null ? (View)this.menuItem.getClientProperty("html") : null;
/*  892 */       if (v != null) {
/*  893 */         textRect.width = (int)v.getPreferredSpan(0);
/*  894 */         textRect.height = (int)v.getPreferredSpan(1);
/*      */       }
/*      */       else {
/*  897 */         textRect.width = SwingUtilities.computeStringWidth(fm, text);
/*  898 */         textRect.height = fm.getHeight();
/*      */       }
/*      */     }
/*      */ 
/*  902 */     if (icon == null) {
/*  903 */       if (useCheckAndArrow())
/*  904 */         iconRect.width = (iconRect.height = 16);
/*      */       else
/*  906 */         iconRect.width = (iconRect.height = 0);
/*      */     }
/*      */     else {
/*  909 */       iconRect.width = icon.getIconWidth();
/*  910 */       iconRect.height = icon.getIconHeight();
/*      */     }
/*      */ 
/*  913 */     if (arrowIcon == null) {
/*  914 */       arrowIconRect.width = (arrowIconRect.height = 0);
/*      */     }
/*      */     else {
/*  917 */       arrowIconRect.width = arrowIcon.getIconWidth();
/*  918 */       arrowIconRect.height = arrowIcon.getIconHeight();
/*      */     }
/*      */ 
/*  921 */     if (checkIcon == null) {
/*  922 */       checkIconRect.width = (checkIconRect.height = 0);
/*      */     }
/*      */     else {
/*  925 */       checkIconRect.width = checkIcon.getIconWidth();
/*  926 */       checkIconRect.height = checkIcon.getIconHeight();
/*      */     }
/*      */ 
/*  929 */     if (this.menuItem.getComponentOrientation().isLeftToRight())
/*      */     {
/*  931 */       if (useCheckAndArrow()) {
/*  932 */         iconRect.x = (this.defaultShadowWidth - iconRect.width >> 1);
/*  933 */         textRect.x = (this.defaultShadowWidth + textIconGap);
/*      */       }
/*  936 */       else if (icon != null) {
/*  937 */         iconRect.x = this.menuItem.getInsets().left;
/*  938 */         textRect.x = (iconRect.x + iconRect.width + textIconGap);
/*      */       }
/*      */       else {
/*  941 */         textRect.x = this.menuItem.getInsets().left;
/*      */       }
/*      */ 
/*  946 */       acceleratorRect.x = (viewRect.x + viewRect.width - this.defaultAccelEndGap - acceleratorRect.width);
/*      */ 
/*  949 */       if (useCheckAndArrow()) {
/*  950 */         checkIconRect.x = (this.defaultShadowWidth - checkIconRect.width >> 1);
/*  951 */         arrowIconRect.x = (viewRect.x + viewRect.width - menuItemGap - arrowIconRect.width);
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/*  959 */     if (verticalTextPosition == 0)
/*      */     {
/*  961 */       textRect.y = ((viewRect.height - textRect.height >> 1) + 1);
/*  962 */       iconRect.y = ((viewRect.height - iconRect.height >> 1) + 1);
/*      */     }
/*      */ 
/*  965 */     Rectangle labelRect = iconRect.union(textRect);
/*      */ 
/*  969 */     acceleratorRect.y = (labelRect.y + (labelRect.height >> 1) - (acceleratorRect.height >> 1));
/*      */ 
/*  971 */     if (useCheckAndArrow()) {
/*  972 */       arrowIconRect.y = ((viewRect.height - arrowIconRect.height >> 1) + 1);
/*  973 */       checkIconRect.y = ((viewRect.height - checkIconRect.height >> 1) + 1);
/*      */     }
/*      */ 
/*  983 */     return text;
/*      */   }
/*      */ 
/*      */   private boolean useCheckAndArrow()
/*      */   {
/*  991 */     boolean b = true;
/*  992 */     if (((this.menuItem instanceof JMenu)) && (((JMenu)this.menuItem).isTopLevelMenu()))
/*      */     {
/*  994 */       b = false;
/*      */     }
/*  996 */     return b;
/*      */   }
/*      */ 
/*      */   public MenuElement[] getPath() {
/* 1000 */     MenuSelectionManager m = MenuSelectionManager.defaultManager();
/* 1001 */     MenuElement[] oldPath = m.getSelectedPath();
/*      */ 
/* 1003 */     int i = oldPath.length;
/* 1004 */     if (i == 0)
/* 1005 */       return new MenuElement[0];
/* 1006 */     Component parent = this.menuItem.getParent();
/*      */     MenuElement[] newPath;
/* 1007 */     if (oldPath[(i - 1)].getComponent() == parent)
/*      */     {
/* 1009 */       MenuElement[] newPath = new MenuElement[i + 1];
/* 1010 */       System.arraycopy(oldPath, 0, newPath, 0, i);
/* 1011 */       newPath[i] = this.menuItem;
/*      */     }
/*      */     else
/*      */     {
/* 1021 */       for (int j = oldPath.length - 1; (j >= 0) && 
/* 1022 */         (oldPath[j].getComponent() != parent); j--);
/* 1025 */       newPath = new MenuElement[j + 2];
/* 1026 */       System.arraycopy(oldPath, 0, newPath, 0, j + 1);
/* 1027 */       newPath[(j + 1)] = this.menuItem;
/*      */     }
/*      */ 
/* 1036 */     return newPath;
/*      */   }
/*      */ 
/*      */   protected void doClick(MenuSelectionManager msm)
/*      */   {
/* 1254 */     if (msm == null) {
/* 1255 */       msm = MenuSelectionManager.defaultManager();
/*      */     }
/* 1257 */     msm.clearSelectedPath();
/* 1258 */     this.menuItem.doClick(0);
/*      */   }
/*      */ 
/*      */   public ThemePainter getPainter() {
/* 1262 */     return this._painter;
/*      */   }
/*      */ 
/*      */   protected boolean isDownArrowVisible(Container c) {
/* 1266 */     if (((c instanceof TopLevelMenuContainer)) && (((TopLevelMenuContainer)c).isMenuBar())) {
/* 1267 */       return false;
/*      */     }
/* 1269 */     if (((c instanceof TopLevelMenuContainer)) && (!((TopLevelMenuContainer)c).isMenuBar())) {
/* 1270 */       return true;
/*      */     }
/*      */ 
/* 1273 */     return !(c instanceof JMenuBar);
/*      */   }
/*      */ 
/*      */   private static class ClickAction extends AbstractAction
/*      */   {
/*      */     public void actionPerformed(ActionEvent e)
/*      */     {
/* 1221 */       JMenuItem mi = (JMenuItem)e.getSource();
/* 1222 */       MenuSelectionManager.defaultManager().clearSelectedPath();
/* 1223 */       mi.doClick();
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
/* 1201 */       String name = e.getPropertyName();
/*      */ 
/* 1203 */       if ((name.equals("labelFor")) || (name.equals("displayedMnemonic")) || (name.equals("accelerator")))
/*      */       {
/* 1205 */         EclipseMenuItemUI.this.updateAcceleratorBinding();
/*      */       }
/* 1207 */       else if ((name.equals("text")) || ("font".equals(name)) || ("foreground".equals(name)))
/*      */       {
/* 1212 */         JMenuItem lbl = (JMenuItem)e.getSource();
/* 1213 */         String text = lbl.getText();
/* 1214 */         BasicHTML.updateRenderer(lbl, text);
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
/* 1175 */       if ((EclipseMenuItemUI.this.menuItem != null) && (EclipseMenuItemUI.this.menuItem.isEnabled())) {
/* 1176 */         int key = EclipseMenuItemUI.this.menuItem.getMnemonic();
/* 1177 */         if ((key == 0) || (e.getPath().length != 2))
/* 1178 */           return;
/* 1179 */         if (lower((char)key) == lower(e.getKeyChar())) {
/* 1180 */           MenuSelectionManager manager = e.getMenuSelectionManager();
/*      */ 
/* 1182 */           EclipseMenuItemUI.this.doClick(manager);
/* 1183 */           e.consume();
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
/* 1195 */       return Character.toLowerCase(keyChar);
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
/* 1140 */       if ((EclipseMenuItemUI.this.menuItem != null) && (EclipseMenuItemUI.this.menuItem.isEnabled())) {
/* 1141 */         MenuSelectionManager manager = e.getMenuSelectionManager();
/* 1142 */         MenuElement[] path = e.getPath();
/* 1143 */         manager.setSelectedPath(path);
/*      */       }
/*      */     }
/*      */ 
/*      */     public void menuDragMouseExited(MenuDragMouseEvent e) {
/*      */     }
/*      */ 
/*      */     public void menuDragMouseReleased(MenuDragMouseEvent e) {
/* 1151 */       if ((EclipseMenuItemUI.this.menuItem != null) && (EclipseMenuItemUI.this.menuItem.isEnabled())) {
/* 1152 */         MenuSelectionManager manager = e.getMenuSelectionManager();
/* 1153 */         Point p = e.getPoint();
/* 1154 */         if ((p.x >= 0) && (p.x < EclipseMenuItemUI.this.menuItem.getWidth()) && (p.y >= 0) && (p.y < EclipseMenuItemUI.this.menuItem.getHeight()))
/*      */         {
/* 1156 */           EclipseMenuItemUI.this.doClick(manager);
/*      */         }
/*      */         else
/* 1159 */           manager.clearSelectedPath();
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
/* 1070 */       if (!SwingUtilities.isLeftMouseButton(e)) {
/* 1071 */         return;
/*      */       }
/*      */ 
/* 1074 */       if ((EclipseMenuItemUI.this.menuItem != null) && (EclipseMenuItemUI.this.menuItem.isEnabled())) {
/* 1075 */         MenuSelectionManager manager = MenuSelectionManager.defaultManager();
/* 1076 */         Point p = e.getPoint();
/* 1077 */         if ((p.x >= 0) && (p.x < EclipseMenuItemUI.this.menuItem.getWidth()) && (p.y >= 0) && (p.y < EclipseMenuItemUI.this.menuItem.getHeight()))
/*      */         {
/* 1079 */           EclipseMenuItemUI.this.doClick(manager);
/*      */         }
/*      */         else
/* 1082 */           manager.processMouseEvent(e);
/*      */       }
/*      */     }
/*      */ 
/*      */     public void mouseEntered(MouseEvent e)
/*      */     {
/* 1088 */       if ((EclipseMenuItemUI.this.menuItem != null) && (EclipseMenuItemUI.this.menuItem.isEnabled())) {
/* 1089 */         MenuSelectionManager manager = MenuSelectionManager.defaultManager();
/* 1090 */         int modifiers = e.getModifiers();
/*      */ 
/* 1092 */         if ((modifiers & 0x1C) != 0)
/*      */         {
/* 1094 */           MenuSelectionManager.defaultManager().processMouseEvent(e);
/*      */         }
/*      */         else
/* 1097 */           manager.setSelectedPath(EclipseMenuItemUI.this.getPath());
/*      */       }
/*      */     }
/*      */ 
/*      */     public void mouseExited(MouseEvent e)
/*      */     {
/* 1103 */       if ((EclipseMenuItemUI.this.menuItem != null) && (EclipseMenuItemUI.this.menuItem.isEnabled())) {
/* 1104 */         MenuSelectionManager manager = MenuSelectionManager.defaultManager();
/*      */ 
/* 1106 */         int modifiers = e.getModifiers();
/*      */ 
/* 1108 */         if ((modifiers & 0x1C) != 0)
/*      */         {
/* 1110 */           MenuSelectionManager.defaultManager().processMouseEvent(e);
/*      */         }
/*      */         else
/*      */         {
/* 1114 */           MenuElement[] path = manager.getSelectedPath();
/* 1115 */           if (path.length > 1) {
/* 1116 */             MenuElement[] newPath = new MenuElement[path.length - 1];
/*      */ 
/* 1118 */             int i = 0; for (int c = path.length - 1; i < c; i++)
/* 1119 */               newPath[i] = path[i];
/* 1120 */             manager.setSelectedPath(newPath);
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */ 
/*      */     public void mouseDragged(MouseEvent e) {
/* 1127 */       MenuSelectionManager.defaultManager().processMouseEvent(e);
/*      */     }
/*      */ 
/*      */     public void mouseMoved(MouseEvent e)
/*      */     {
/*      */     }
/*      */   }
/*      */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     com.jidesoft.plaf.eclipse.EclipseMenuItemUI
 * JD-Core Version:    0.6.0
 */