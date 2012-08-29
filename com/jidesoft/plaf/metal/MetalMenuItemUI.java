/*      */ package com.jidesoft.plaf.metal;
/*      */ 
/*      */ import com.jidesoft.plaf.UIDefaultsLookup;
/*      */ import com.jidesoft.plaf.basic.ThemePainter;
/*      */ import com.jidesoft.swing.JideSwingUtilities;
/*      */ import com.jidesoft.swing.TopLevelMenuContainer;
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
/*      */ import java.io.PrintStream;
/*      */ import javax.swing.AbstractAction;
/*      */ import javax.swing.ActionMap;
/*      */ import javax.swing.ButtonModel;
/*      */ import javax.swing.Icon;
/*      */ import javax.swing.InputMap;
/*      */ import javax.swing.JComponent;
/*      */ import javax.swing.JMenu;
/*      */ import javax.swing.JMenuBar;
/*      */ import javax.swing.JMenuItem;
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
/*      */ public class MetalMenuItemUI extends MenuItemUI
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
/*      */   private static final boolean TRACE = false;
/*      */   private static final boolean VERBOSE = false;
/*      */   private static final boolean DEBUG = false;
/*      */   static final String MAX_TEXT_WIDTH = "maxTextWidth";
/*      */   static final String MAX_ACC_WIDTH = "maxAccWidth";
/*      */   protected ThemePainter _painter;
/*  350 */   static Rectangle zeroRect = new Rectangle(0, 0, 0, 0);
/*  351 */   static Rectangle iconRect = new Rectangle();
/*  352 */   static Rectangle textRect = new Rectangle();
/*  353 */   static Rectangle acceleratorRect = new Rectangle();
/*  354 */   static Rectangle checkIconRect = new Rectangle();
/*  355 */   static Rectangle arrowIconRect = new Rectangle();
/*  356 */   static Rectangle viewRect = new Rectangle(32767, 32767);
/*  357 */   static Rectangle r = new Rectangle();
/*      */ 
/*      */   public MetalMenuItemUI()
/*      */   {
/*   33 */     this.menuItem = null;
/*      */ 
/*   49 */     this.arrowIcon = null;
/*   50 */     this.checkIcon = null;
/*      */   }
/*      */ 
/*      */   public static ComponentUI createUI(JComponent c)
/*      */   {
/*   74 */     return new MetalMenuItemUI();
/*      */   }
/*      */ 
/*      */   public void installUI(JComponent c)
/*      */   {
/*   79 */     this.menuItem = ((JMenuItem)c);
/*      */ 
/*   81 */     installDefaults();
/*   82 */     installComponents(this.menuItem);
/*   83 */     installListeners();
/*   84 */     installKeyboardActions();
/*      */   }
/*      */ 
/*      */   protected void installDefaults()
/*      */   {
/*   90 */     this._painter = ((ThemePainter)UIDefaultsLookup.get("Theme.painter"));
/*      */ 
/*   92 */     String prefix = getPropertyPrefix();
/*      */ 
/*   94 */     this.acceleratorFont = UIDefaultsLookup.getFont("MenuItem.acceleratorFont");
/*      */ 
/*   96 */     this.menuItem.setOpaque(true);
/*   97 */     if ((this.menuItem.getMargin() == null) || ((this.menuItem.getMargin() instanceof UIResource)))
/*      */     {
/*   99 */       this.menuItem.setMargin(UIDefaultsLookup.getInsets(prefix + ".margin"));
/*      */     }
/*      */ 
/*  102 */     this.defaultTextIconGap = 4;
/*      */ 
/*  104 */     LookAndFeel.installBorder(this.menuItem, prefix + ".border");
/*  105 */     this.oldBorderPainted = this.menuItem.isBorderPainted();
/*  106 */     this.menuItem.setBorderPainted(((Boolean)(Boolean)UIDefaultsLookup.get(prefix + ".borderPainted")).booleanValue());
/*  107 */     LookAndFeel.installColorsAndFont(this.menuItem, prefix + ".background", prefix + ".foreground", prefix + ".font");
/*      */ 
/*  113 */     if ((this.selectionBackground == null) || ((this.selectionBackground instanceof UIResource)))
/*      */     {
/*  115 */       this.selectionBackground = UIDefaultsLookup.getColor(prefix + ".selectionBackground");
/*      */     }
/*      */ 
/*  118 */     if ((this.selectionForeground == null) || ((this.selectionForeground instanceof UIResource)))
/*      */     {
/*  120 */       this.selectionForeground = UIDefaultsLookup.getColor(prefix + ".selectionForeground");
/*      */     }
/*      */ 
/*  123 */     if ((this.disabledForeground == null) || ((this.disabledForeground instanceof UIResource)))
/*      */     {
/*  125 */       this.disabledForeground = UIDefaultsLookup.getColor(prefix + ".disabledForeground");
/*      */     }
/*      */ 
/*  128 */     if ((this.acceleratorForeground == null) || ((this.acceleratorForeground instanceof UIResource)))
/*      */     {
/*  130 */       this.acceleratorForeground = UIDefaultsLookup.getColor(prefix + ".acceleratorForeground");
/*      */     }
/*      */ 
/*  133 */     if ((this.acceleratorSelectionForeground == null) || ((this.acceleratorSelectionForeground instanceof UIResource)))
/*      */     {
/*  135 */       this.acceleratorSelectionForeground = UIDefaultsLookup.getColor(prefix + ".acceleratorSelectionForeground");
/*      */     }
/*      */ 
/*  139 */     this.acceleratorDelimiter = UIDefaultsLookup.getString("MenuItem.acceleratorDelimiter");
/*      */ 
/*  141 */     if (this.acceleratorDelimiter == null) {
/*  142 */       this.acceleratorDelimiter = "+";
/*      */     }
/*      */ 
/*  145 */     if ((this.arrowIcon == null) || ((this.arrowIcon instanceof UIResource)))
/*      */     {
/*  147 */       this.arrowIcon = UIDefaultsLookup.getIcon(prefix + ".arrowIcon");
/*      */     }
/*  149 */     if ((this.checkIcon == null) || ((this.checkIcon instanceof UIResource)))
/*      */     {
/*  151 */       this.checkIcon = UIDefaultsLookup.getIcon(prefix + ".checkIcon");
/*      */     }
/*      */   }
/*      */ 
/*      */   protected void installComponents(JMenuItem menuItem)
/*      */   {
/*  159 */     BasicHTML.updateRenderer(menuItem, menuItem.getText());
/*      */   }
/*      */ 
/*      */   protected String getPropertyPrefix() {
/*  163 */     return "MenuItem";
/*      */   }
/*      */ 
/*      */   protected void installListeners() {
/*  167 */     if ((this.mouseInputListener = createMouseInputListener(this.menuItem)) != null) {
/*  168 */       this.menuItem.addMouseListener(this.mouseInputListener);
/*  169 */       this.menuItem.addMouseMotionListener(this.mouseInputListener);
/*      */     }
/*  171 */     if ((this.menuDragMouseListener = createMenuDragMouseListener(this.menuItem)) != null) {
/*  172 */       this.menuItem.addMenuDragMouseListener(this.menuDragMouseListener);
/*      */     }
/*  174 */     if ((this.menuKeyListener = createMenuKeyListener(this.menuItem)) != null) {
/*  175 */       this.menuItem.addMenuKeyListener(this.menuKeyListener);
/*      */     }
/*  177 */     if ((this.propertyChangeListener = createPropertyChangeListener(this.menuItem)) != null)
/*  178 */       this.menuItem.addPropertyChangeListener(this.propertyChangeListener);
/*      */   }
/*      */ 
/*      */   protected void installKeyboardActions()
/*      */   {
/*  183 */     ActionMap actionMap = getActionMap();
/*      */ 
/*  185 */     SwingUtilities.replaceUIActionMap(this.menuItem, actionMap);
/*  186 */     updateAcceleratorBinding();
/*      */   }
/*      */ 
/*      */   public void uninstallUI(JComponent c)
/*      */   {
/*  191 */     this.menuItem = ((JMenuItem)c);
/*  192 */     uninstallDefaults();
/*  193 */     uninstallComponents(this.menuItem);
/*  194 */     uninstallListeners();
/*  195 */     uninstallKeyboardActions();
/*      */ 
/*  198 */     Container parent = this.menuItem.getParent();
/*  199 */     if ((parent != null) && ((parent instanceof JComponent)) && ((!(this.menuItem instanceof JMenu)) || (!((JMenu)this.menuItem).isTopLevelMenu())))
/*      */     {
/*  201 */       JComponent p = (JComponent)parent;
/*  202 */       p.putClientProperty("maxAccWidth", null);
/*  203 */       p.putClientProperty("maxTextWidth", null);
/*      */     }
/*      */ 
/*  206 */     this.menuItem = null;
/*      */   }
/*      */ 
/*      */   protected void uninstallDefaults()
/*      */   {
/*  212 */     this._painter = null;
/*      */ 
/*  214 */     LookAndFeel.uninstallBorder(this.menuItem);
/*  215 */     this.menuItem.setBorderPainted(this.oldBorderPainted);
/*  216 */     if ((this.menuItem.getMargin() instanceof UIResource))
/*  217 */       this.menuItem.setMargin(null);
/*  218 */     if ((this.arrowIcon instanceof UIResource))
/*  219 */       this.arrowIcon = null;
/*  220 */     if ((this.checkIcon instanceof UIResource))
/*  221 */       this.checkIcon = null;
/*      */   }
/*      */ 
/*      */   protected void uninstallComponents(JMenuItem menuItem)
/*      */   {
/*  228 */     BasicHTML.updateRenderer(menuItem, "");
/*      */   }
/*      */ 
/*      */   protected void uninstallListeners() {
/*  232 */     if (this.mouseInputListener != null) {
/*  233 */       this.menuItem.removeMouseListener(this.mouseInputListener);
/*  234 */       this.menuItem.removeMouseMotionListener(this.mouseInputListener);
/*      */     }
/*  236 */     if (this.menuDragMouseListener != null) {
/*  237 */       this.menuItem.removeMenuDragMouseListener(this.menuDragMouseListener);
/*      */     }
/*  239 */     if (this.menuKeyListener != null) {
/*  240 */       this.menuItem.removeMenuKeyListener(this.menuKeyListener);
/*      */     }
/*  242 */     if (this.propertyChangeListener != null) {
/*  243 */       this.menuItem.removePropertyChangeListener(this.propertyChangeListener);
/*      */     }
/*      */ 
/*  246 */     this.mouseInputListener = null;
/*  247 */     this.menuDragMouseListener = null;
/*  248 */     this.menuKeyListener = null;
/*  249 */     this.propertyChangeListener = null;
/*      */   }
/*      */ 
/*      */   protected void uninstallKeyboardActions() {
/*  253 */     SwingUtilities.replaceUIActionMap(this.menuItem, null);
/*  254 */     if (this.windowInputMap != null) {
/*  255 */       SwingUtilities.replaceUIInputMap(this.menuItem, 2, null);
/*      */ 
/*  257 */       this.windowInputMap = null;
/*      */     }
/*      */   }
/*      */ 
/*      */   protected MouseInputListener createMouseInputListener(JComponent c) {
/*  262 */     return new MouseInputHandler();
/*      */   }
/*      */ 
/*      */   protected MenuDragMouseListener createMenuDragMouseListener(JComponent c) {
/*  266 */     return new MenuDragMouseHandler(null);
/*      */   }
/*      */ 
/*      */   protected MenuKeyListener createMenuKeyListener(JComponent c) {
/*  270 */     return new MenuKeyHandler(null);
/*      */   }
/*      */ 
/*      */   private PropertyChangeListener createPropertyChangeListener(JComponent c) {
/*  274 */     return new PropertyChangeHandler(null);
/*      */   }
/*      */ 
/*      */   ActionMap getActionMap() {
/*  278 */     String propertyPrefix = getPropertyPrefix();
/*  279 */     String uiKey = propertyPrefix + ".actionMap";
/*  280 */     ActionMap am = (ActionMap)UIDefaultsLookup.get(uiKey);
/*  281 */     if (am == null) {
/*  282 */       am = createActionMap();
/*  283 */       UIManager.getLookAndFeelDefaults().put(uiKey, am);
/*      */     }
/*  285 */     return am;
/*      */   }
/*      */ 
/*      */   ActionMap createActionMap() {
/*  289 */     ActionMap map = new ActionMapUIResource();
/*  290 */     map.put("doClick", new ClickAction(null));
/*  291 */     return map;
/*      */   }
/*      */ 
/*      */   InputMap createInputMap(int condition) {
/*  295 */     if (condition == 2) {
/*  296 */       return new ComponentInputMapUIResource(this.menuItem);
/*      */     }
/*  298 */     return null;
/*      */   }
/*      */ 
/*      */   void updateAcceleratorBinding() {
/*  302 */     KeyStroke accelerator = this.menuItem.getAccelerator();
/*      */ 
/*  304 */     if (this.windowInputMap != null) {
/*  305 */       this.windowInputMap.clear();
/*      */     }
/*  307 */     if (accelerator != null) {
/*  308 */       if (this.windowInputMap == null) {
/*  309 */         this.windowInputMap = createInputMap(2);
/*      */ 
/*  311 */         SwingUtilities.replaceUIInputMap(this.menuItem, 2, this.windowInputMap);
/*      */       }
/*      */ 
/*  314 */       this.windowInputMap.put(accelerator, "doClick");
/*      */     }
/*      */   }
/*      */ 
/*      */   public Dimension getMinimumSize(JComponent c)
/*      */   {
/*  320 */     Dimension d = null;
/*  321 */     View v = (View)c.getClientProperty("html");
/*  322 */     if (v != null) {
/*  323 */       d = getPreferredSize(c);
/*      */       Dimension tmp23_22 = d; tmp23_22.width = (int)(tmp23_22.width - (v.getPreferredSpan(0) - v.getMinimumSpan(0)));
/*      */     }
/*  326 */     return d;
/*      */   }
/*      */ 
/*      */   public Dimension getPreferredSize(JComponent c)
/*      */   {
/*  331 */     return getPreferredMenuItemSize(c, this.checkIcon, this.arrowIcon, this.defaultTextIconGap);
/*      */   }
/*      */ 
/*      */   public Dimension getMaximumSize(JComponent c)
/*      */   {
/*  339 */     Dimension d = null;
/*  340 */     View v = (View)c.getClientProperty("html");
/*  341 */     if (v != null) {
/*  342 */       d = getPreferredSize(c);
/*      */       Dimension tmp23_22 = d; tmp23_22.width = (int)(tmp23_22.width + (v.getMaximumSpan(0) - v.getPreferredSpan(0)));
/*      */     }
/*  345 */     return d;
/*      */   }
/*      */ 
/*      */   private void resetRects()
/*      */   {
/*  360 */     iconRect.setBounds(zeroRect);
/*  361 */     textRect.setBounds(zeroRect);
/*  362 */     acceleratorRect.setBounds(zeroRect);
/*  363 */     checkIconRect.setBounds(zeroRect);
/*  364 */     arrowIconRect.setBounds(zeroRect);
/*  365 */     viewRect.setBounds(0, 0, 32767, 32767);
/*  366 */     r.setBounds(zeroRect);
/*      */   }
/*      */ 
/*      */   protected Dimension getPreferredMenuItemSize(JComponent c, Icon checkIcon, Icon arrowIcon, int defaultTextIconGap)
/*      */   {
/*  373 */     JMenuItem b = (JMenuItem)c;
/*  374 */     Icon icon = b.getIcon();
/*  375 */     String text = b.getText();
/*  376 */     KeyStroke accelerator = b.getAccelerator();
/*  377 */     String acceleratorText = "";
/*      */ 
/*  379 */     if (accelerator != null) {
/*  380 */       int modifiers = accelerator.getModifiers();
/*  381 */       if (modifiers > 0) {
/*  382 */         acceleratorText = KeyEvent.getKeyModifiersText(modifiers);
/*      */ 
/*  384 */         acceleratorText = acceleratorText + this.acceleratorDelimiter;
/*      */       }
/*  386 */       int keyCode = accelerator.getKeyCode();
/*  387 */       if (keyCode != 0) {
/*  388 */         acceleratorText = acceleratorText + KeyEvent.getKeyText(keyCode);
/*      */       }
/*      */       else {
/*  391 */         acceleratorText = acceleratorText + accelerator.getKeyChar();
/*      */       }
/*      */     }
/*      */ 
/*  395 */     Font font = b.getFont();
/*  396 */     FontMetrics fm = b.getFontMetrics(font);
/*  397 */     FontMetrics fmAccel = b.getFontMetrics(this.acceleratorFont);
/*      */ 
/*  399 */     resetRects();
/*      */ 
/*  401 */     layoutMenuItem(fm, text, fmAccel, acceleratorText, icon, checkIcon, arrowIcon, b.getVerticalAlignment(), b.getHorizontalAlignment(), b.getVerticalTextPosition(), b.getHorizontalTextPosition(), viewRect, iconRect, textRect, acceleratorRect, checkIconRect, arrowIconRect, text == null ? 0 : defaultTextIconGap, defaultTextIconGap);
/*      */ 
/*  408 */     r.setBounds(textRect);
/*  409 */     r = SwingUtilities.computeUnion(iconRect.x, iconRect.y, iconRect.width, iconRect.height, r);
/*      */ 
/*  420 */     Container parent = this.menuItem.getParent();
/*      */ 
/*  423 */     if ((parent != null) && ((parent instanceof JComponent)) && ((!(this.menuItem instanceof JMenu)) || (!((JMenu)this.menuItem).isTopLevelMenu())))
/*      */     {
/*  425 */       JComponent p = (JComponent)parent;
/*      */ 
/*  428 */       Integer maxTextWidth = (Integer)p.getClientProperty("maxTextWidth");
/*  429 */       Integer maxAccWidth = (Integer)p.getClientProperty("maxAccWidth");
/*      */ 
/*  431 */       int maxTextValue = maxTextWidth != null ? maxTextWidth.intValue() : 0;
/*  432 */       int maxAccValue = maxAccWidth != null ? maxAccWidth.intValue() : 0;
/*      */ 
/*  435 */       if (r.width < maxTextValue) {
/*  436 */         r.width = maxTextValue;
/*      */       }
/*      */       else {
/*  439 */         p.putClientProperty("maxTextWidth", Integer.valueOf(r.width));
/*      */       }
/*      */ 
/*  443 */       if (acceleratorRect.width > maxAccValue) {
/*  444 */         maxAccValue = acceleratorRect.width;
/*  445 */         p.putClientProperty("maxAccWidth", Integer.valueOf(acceleratorRect.width));
/*      */       }
/*      */ 
/*  449 */       r.width += maxAccValue;
/*  450 */       r.width += defaultTextIconGap;
/*      */     }
/*      */ 
/*  454 */     if (useCheckAndArrow())
/*      */     {
/*  456 */       r.width += checkIconRect.width;
/*  457 */       r.width += defaultTextIconGap;
/*      */ 
/*  460 */       r.width += defaultTextIconGap;
/*  461 */       r.width += arrowIconRect.width;
/*      */     }
/*      */ 
/*  464 */     r.width += 2 * defaultTextIconGap;
/*      */ 
/*  466 */     Insets insets = b.getInsets();
/*  467 */     if (insets != null) {
/*  468 */       r.width += insets.left + insets.right;
/*  469 */       r.height += insets.top + insets.bottom;
/*      */     }
/*      */ 
/*  474 */     if (r.width % 2 == 0) {
/*  475 */       r.width += 1;
/*      */     }
/*      */ 
/*  480 */     if (r.height % 2 == 0) {
/*  481 */       r.height += 1;
/*      */     }
/*      */ 
/*  495 */     if (JideSwingUtilities.getOrientationOf(this.menuItem) == 0) {
/*  496 */       return r.getSize();
/*      */     }
/*      */ 
/*  499 */     return new Dimension(r.height, r.width);
/*      */   }
/*      */ 
/*      */   public void update(Graphics g, JComponent c)
/*      */   {
/*  509 */     paint(g, c);
/*      */   }
/*      */ 
/*      */   public void paint(Graphics g, JComponent c)
/*      */   {
/*  514 */     paintMenuItem(g, c, this.checkIcon, this.arrowIcon, this.selectionBackground, this.selectionForeground, this.defaultTextIconGap);
/*      */   }
/*      */ 
/*      */   protected void paintMenuItem(Graphics g, JComponent c, Icon checkIcon, Icon arrowIcon, Color background, Color foreground, int defaultTextIconGap)
/*      */   {
/*  524 */     JMenuItem b = (JMenuItem)c;
/*  525 */     ButtonModel model = b.getModel();
/*      */ 
/*  527 */     int menuWidth = 0;
/*  528 */     int menuHeight = 0;
/*      */ 
/*  530 */     if (JideSwingUtilities.getOrientationOf(this.menuItem) == 0)
/*      */     {
/*  532 */       menuWidth = b.getWidth();
/*  533 */       menuHeight = b.getHeight();
/*      */     }
/*      */     else
/*      */     {
/*  537 */       menuWidth = b.getHeight();
/*  538 */       menuHeight = b.getWidth();
/*  539 */       Graphics2D g2d = (Graphics2D)g;
/*  540 */       AffineTransform oldAt = g2d.getTransform();
/*  541 */       g2d.rotate(1.570796326794897D);
/*  542 */       g2d.translate(0, -menuHeight + 1);
/*      */     }
/*      */ 
/*  545 */     Insets i = c.getInsets();
/*      */ 
/*  547 */     resetRects();
/*      */ 
/*  549 */     viewRect.setBounds(0, 0, menuWidth, menuHeight);
/*      */ 
/*  551 */     viewRect.x += i.left;
/*  552 */     viewRect.y += i.top;
/*  553 */     viewRect.width -= i.right + viewRect.x;
/*  554 */     viewRect.height -= i.bottom + viewRect.y;
/*      */ 
/*  557 */     Font holdf = g.getFont();
/*  558 */     Font f = c.getFont();
/*  559 */     g.setFont(f);
/*  560 */     FontMetrics fm = g.getFontMetrics(f);
/*  561 */     FontMetrics fmAccel = g.getFontMetrics(this.acceleratorFont);
/*      */ 
/*  564 */     KeyStroke accelerator = b.getAccelerator();
/*  565 */     String acceleratorText = "";
/*  566 */     if (accelerator != null) {
/*  567 */       int modifiers = accelerator.getModifiers();
/*  568 */       if (modifiers > 0) {
/*  569 */         acceleratorText = KeyEvent.getKeyModifiersText(modifiers);
/*      */ 
/*  571 */         acceleratorText = acceleratorText + this.acceleratorDelimiter;
/*      */       }
/*      */ 
/*  574 */       int keyCode = accelerator.getKeyCode();
/*  575 */       if (keyCode != 0) {
/*  576 */         acceleratorText = acceleratorText + KeyEvent.getKeyText(keyCode);
/*      */       }
/*      */       else {
/*  579 */         acceleratorText = acceleratorText + accelerator.getKeyChar();
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/*  584 */     String text = layoutMenuItem(fm, b.getText(), fmAccel, acceleratorText, b.getIcon(), checkIcon, arrowIcon, b.getVerticalAlignment(), b.getHorizontalAlignment(), b.getVerticalTextPosition(), b.getHorizontalTextPosition(), viewRect, iconRect, textRect, acceleratorRect, checkIconRect, arrowIconRect, b.getText() == null ? 0 : defaultTextIconGap, defaultTextIconGap);
/*      */ 
/*  594 */     paintBackground(g, b, background);
/*      */ 
/*  596 */     Color holdc = g.getColor();
/*      */ 
/*  599 */     if (checkIcon != null) {
/*  600 */       if ((model.isArmed()) || (((c instanceof JMenu)) && (model.isSelected()))) {
/*  601 */         g.setColor(foreground);
/*      */       }
/*      */       else {
/*  604 */         g.setColor(holdc);
/*      */       }
/*  606 */       if (useCheckAndArrow())
/*  607 */         checkIcon.paintIcon(c, g, checkIconRect.x, checkIconRect.y);
/*  608 */       g.setColor(holdc);
/*      */     }
/*      */ 
/*  612 */     if (b.getIcon() != null)
/*      */     {
/*      */       Icon icon;
/*      */       Icon icon;
/*  614 */       if (!model.isEnabled()) {
/*  615 */         icon = b.getDisabledIcon();
/*      */       }
/*  617 */       else if ((model.isPressed()) && (model.isArmed())) {
/*  618 */         Icon icon = b.getPressedIcon();
/*  619 */         if (icon == null)
/*      */         {
/*  621 */           icon = b.getIcon();
/*      */         }
/*      */       }
/*      */       else {
/*  625 */         icon = b.getIcon();
/*      */       }
/*      */ 
/*  628 */       if (icon != null) {
/*  629 */         icon.paintIcon(c, g, iconRect.x, iconRect.y);
/*      */       }
/*      */     }
/*      */ 
/*  633 */     if (text != null) {
/*  634 */       View v = (View)c.getClientProperty("html");
/*  635 */       if (v != null) {
/*  636 */         v.paint(g, textRect);
/*      */       }
/*      */       else {
/*  639 */         paintText(g, b, textRect, text);
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/*  644 */     if ((acceleratorText != null) && (!acceleratorText.equals("")))
/*      */     {
/*  647 */       int accOffset = 0;
/*  648 */       Container parent = this.menuItem.getParent();
/*  649 */       if ((parent != null) && ((parent instanceof JComponent))) {
/*  650 */         JComponent p = (JComponent)parent;
/*  651 */         Integer maxValueInt = (Integer)p.getClientProperty("maxAccWidth");
/*  652 */         int maxValue = maxValueInt != null ? maxValueInt.intValue() : acceleratorRect.width;
/*      */ 
/*  656 */         accOffset = maxValue - acceleratorRect.width;
/*      */       }
/*      */ 
/*  659 */       g.setFont(this.acceleratorFont);
/*  660 */       if (!model.isEnabled())
/*      */       {
/*  662 */         if (this.disabledForeground != null) {
/*  663 */           g.setColor(this.disabledForeground);
/*  664 */           JideSwingUtilities.drawString(this.menuItem, g, acceleratorText, acceleratorRect.x - accOffset, acceleratorRect.y + fmAccel.getAscent());
/*      */         }
/*      */         else
/*      */         {
/*  669 */           g.setColor(b.getBackground().brighter());
/*  670 */           JideSwingUtilities.drawString(this.menuItem, g, acceleratorText, acceleratorRect.x - accOffset, acceleratorRect.y + fmAccel.getAscent());
/*      */ 
/*  673 */           g.setColor(b.getBackground().darker());
/*  674 */           JideSwingUtilities.drawString(this.menuItem, g, acceleratorText, acceleratorRect.x - accOffset - 1, acceleratorRect.y + fmAccel.getAscent() - 1);
/*      */         }
/*      */ 
/*      */       }
/*      */       else
/*      */       {
/*  681 */         if ((model.isArmed()) || (((c instanceof JMenu)) && (model.isSelected()))) {
/*  682 */           g.setColor(this.acceleratorSelectionForeground);
/*      */         }
/*      */         else {
/*  685 */           g.setColor(this.acceleratorForeground);
/*      */         }
/*  687 */         JideSwingUtilities.drawString(this.menuItem, g, acceleratorText, acceleratorRect.x - accOffset, acceleratorRect.y + fmAccel.getAscent());
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/*  694 */     if (arrowIcon != null) {
/*  695 */       if ((model.isArmed()) || (((c instanceof JMenu)) && (model.isSelected())))
/*  696 */         g.setColor(foreground);
/*  697 */       if (useCheckAndArrow())
/*  698 */         arrowIcon.paintIcon(c, g, arrowIconRect.x, arrowIconRect.y);
/*      */     }
/*  700 */     g.setColor(holdc);
/*  701 */     g.setFont(holdf);
/*      */   }
/*      */ 
/*      */   protected void paintBackground(Graphics g, JMenuItem menuItem, Color bgColor)
/*      */   {
/*  713 */     ButtonModel model = menuItem.getModel();
/*  714 */     Color oldColor = g.getColor();
/*  715 */     int menuWidth = menuItem.getWidth();
/*  716 */     int menuHeight = menuItem.getHeight();
/*      */ 
/*  718 */     if (menuItem.isOpaque()) {
/*  719 */       if ((model.isArmed()) || (((menuItem instanceof JMenu)) && (model.isSelected()))) {
/*  720 */         g.setColor(bgColor);
/*  721 */         g.fillRect(0, 0, menuWidth, menuHeight);
/*      */       }
/*      */       else {
/*  724 */         g.setColor(menuItem.getBackground());
/*  725 */         g.fillRect(0, 0, menuWidth, menuHeight);
/*      */       }
/*  727 */       g.setColor(oldColor);
/*      */     }
/*      */   }
/*      */ 
/*      */   protected void paintText(Graphics g, JMenuItem menuItem, Rectangle textRect, String text)
/*      */   {
/*  742 */     ButtonModel model = menuItem.getModel();
/*  743 */     FontMetrics fm = g.getFontMetrics();
/*  744 */     int mnemIndex = menuItem.getDisplayedMnemonicIndex();
/*      */ 
/*  746 */     if (!model.isEnabled())
/*      */     {
/*  748 */       if ((UIDefaultsLookup.get("MenuItem.disabledForeground") instanceof Color)) {
/*  749 */         g.setColor(UIDefaultsLookup.getColor("MenuItem.disabledForeground"));
/*  750 */         JideSwingUtilities.drawStringUnderlineCharAt(menuItem, g, text, mnemIndex, textRect.x, textRect.y + fm.getAscent());
/*      */       }
/*      */       else
/*      */       {
/*  755 */         g.setColor(menuItem.getBackground().brighter());
/*  756 */         JideSwingUtilities.drawStringUnderlineCharAt(menuItem, g, text, mnemIndex, textRect.x, textRect.y + fm.getAscent());
/*      */ 
/*  759 */         g.setColor(menuItem.getBackground().darker());
/*  760 */         JideSwingUtilities.drawStringUnderlineCharAt(menuItem, g, text, mnemIndex, textRect.x - 1, textRect.y + fm.getAscent() - 1);
/*      */       }
/*      */ 
/*      */     }
/*      */     else
/*      */     {
/*  767 */       if ((model.isArmed()) || (((menuItem instanceof JMenu)) && (model.isSelected()))) {
/*  768 */         g.setColor(this.selectionForeground);
/*      */       }
/*  770 */       JideSwingUtilities.drawStringUnderlineCharAt(menuItem, g, text, mnemIndex, textRect.x, textRect.y + fm.getAscent());
/*      */     }
/*      */   }
/*      */ 
/*      */   private String layoutMenuItem(FontMetrics fm, String text, FontMetrics fmAccel, String acceleratorText, Icon icon, Icon checkIcon, Icon arrowIcon, int verticalAlignment, int horizontalAlignment, int verticalTextPosition, int horizontalTextPosition, Rectangle viewRect, Rectangle iconRect, Rectangle textRect, Rectangle acceleratorRect, Rectangle checkIconRect, Rectangle arrowIconRect, int textIconGap, int menuItemGap)
/*      */   {
/*  801 */     if ((icon != null) && (
/*  802 */       (icon.getIconHeight() == 0) || (icon.getIconWidth() == 0))) {
/*  803 */       icon = null;
/*      */     }
/*  805 */     viewRect.width -= getRightMargin();
/*  806 */     String newText = SwingUtilities.layoutCompoundLabel(this.menuItem, fm, text, icon, verticalAlignment, horizontalAlignment, verticalTextPosition, horizontalTextPosition, viewRect, iconRect, textRect, textIconGap);
/*      */ 
/*  812 */     if (getRightMargin() > 0) {
/*  813 */       text = newText;
/*      */     }
/*  815 */     viewRect.width += getRightMargin();
/*      */ 
/*  821 */     if ((acceleratorText == null) || (acceleratorText.equals(""))) {
/*  822 */       acceleratorRect.width = (acceleratorRect.height = 0);
/*  823 */       acceleratorText = "";
/*      */     }
/*      */     else {
/*  826 */       acceleratorRect.width = SwingUtilities.computeStringWidth(fmAccel, acceleratorText);
/*  827 */       acceleratorRect.height = fmAccel.getHeight();
/*      */     }
/*      */ 
/*  833 */     if (useCheckAndArrow()) {
/*  834 */       if (checkIcon != null) {
/*  835 */         checkIconRect.width = checkIcon.getIconWidth();
/*  836 */         checkIconRect.height = checkIcon.getIconHeight();
/*      */       }
/*      */       else {
/*  839 */         checkIconRect.width = (checkIconRect.height = 0);
/*      */       }
/*      */ 
/*  845 */       if (arrowIcon != null) {
/*  846 */         arrowIconRect.width = arrowIcon.getIconWidth();
/*  847 */         arrowIconRect.height = arrowIcon.getIconHeight();
/*      */       }
/*      */       else {
/*  850 */         arrowIconRect.width = (arrowIconRect.height = 0);
/*      */       }
/*      */     }
/*      */ 
/*  854 */     Rectangle labelRect = iconRect.union(textRect);
/*  855 */     if (this.menuItem.getComponentOrientation().isLeftToRight()) {
/*  856 */       if (getRightMargin() == 0) {
/*  857 */         textRect.x += menuItemGap;
/*  858 */         iconRect.x += menuItemGap;
/*      */       }
/*      */ 
/*  862 */       acceleratorRect.x = (viewRect.x + viewRect.width - arrowIconRect.width - menuItemGap - acceleratorRect.width);
/*      */ 
/*  866 */       if (useCheckAndArrow()) {
/*  867 */         viewRect.x += menuItemGap;
/*  868 */         textRect.x += menuItemGap + checkIconRect.width;
/*  869 */         iconRect.x += menuItemGap + checkIconRect.width;
/*  870 */         arrowIconRect.x = (viewRect.x + viewRect.width - menuItemGap - arrowIconRect.width);
/*      */       }
/*      */     }
/*      */     else
/*      */     {
/*  875 */       if (getRightMargin() == 0) {
/*  876 */         textRect.x -= menuItemGap;
/*  877 */         iconRect.x -= menuItemGap;
/*      */       }
/*      */ 
/*  881 */       acceleratorRect.x = (viewRect.x + arrowIconRect.width + menuItemGap);
/*      */ 
/*  884 */       if (useCheckAndArrow()) {
/*  885 */         checkIconRect.x = (viewRect.x + viewRect.width - menuItemGap - checkIconRect.width);
/*      */ 
/*  887 */         textRect.x -= menuItemGap + checkIconRect.width;
/*  888 */         iconRect.x -= menuItemGap + checkIconRect.width;
/*  889 */         viewRect.x += menuItemGap;
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/*  895 */     acceleratorRect.y = (labelRect.y + labelRect.height / 2 - acceleratorRect.height / 2);
/*  896 */     if (useCheckAndArrow()) {
/*  897 */       arrowIconRect.y = (labelRect.y + labelRect.height / 2 - arrowIconRect.height / 2);
/*  898 */       checkIconRect.y = (labelRect.y + labelRect.height / 2 - checkIconRect.height / 2);
/*      */     }
/*      */ 
/*  908 */     return text;
/*      */   }
/*      */ 
/*      */   private boolean useCheckAndArrow()
/*      */   {
/*  916 */     boolean b = true;
/*  917 */     if (((this.menuItem instanceof JMenu)) && (((JMenu)this.menuItem).isTopLevelMenu()))
/*      */     {
/*  919 */       b = false;
/*      */     }
/*  921 */     return b;
/*      */   }
/*      */ 
/*      */   public MenuElement[] getPath() {
/*  925 */     MenuSelectionManager m = MenuSelectionManager.defaultManager();
/*  926 */     MenuElement[] oldPath = m.getSelectedPath();
/*      */ 
/*  928 */     int i = oldPath.length;
/*  929 */     if (i == 0)
/*  930 */       return new MenuElement[0];
/*  931 */     Component parent = this.menuItem.getParent();
/*      */     MenuElement[] newPath;
/*  932 */     if (oldPath[(i - 1)].getComponent() == parent)
/*      */     {
/*  934 */       MenuElement[] newPath = new MenuElement[i + 1];
/*  935 */       System.arraycopy(oldPath, 0, newPath, 0, i);
/*  936 */       newPath[i] = this.menuItem;
/*      */     }
/*      */     else
/*      */     {
/*  946 */       for (int j = oldPath.length - 1; (j >= 0) && 
/*  947 */         (oldPath[j].getComponent() != parent); j--);
/*  950 */       newPath = new MenuElement[j + 2];
/*  951 */       System.arraycopy(oldPath, 0, newPath, 0, j + 1);
/*  952 */       newPath[(j + 1)] = this.menuItem;
/*      */     }
/*      */ 
/*  961 */     return newPath;
/*      */   }
/*      */ 
/*      */   void printMenuElementArray(MenuElement[] path, boolean dumpStack) {
/*  965 */     System.out.println("Path is(");
/*      */ 
/*  967 */     int i = 0; for (int j = path.length; i < j; i++) {
/*  968 */       for (int k = 0; k <= i; k++)
/*  969 */         System.out.print("  ");
/*  970 */       MenuElement me = path[i];
/*  971 */       if ((me instanceof JMenuItem))
/*  972 */         System.out.println(((JMenuItem)me).getText() + ", ");
/*  973 */       else if (me == null)
/*  974 */         System.out.println("NULL , ");
/*      */       else
/*  976 */         System.out.println("" + me + ", ");
/*      */     }
/*  978 */     System.out.println(")");
/*      */ 
/*  980 */     if (dumpStack == true)
/*  981 */       Thread.dumpStack();
/*      */   }
/*      */ 
/*      */   protected void doClick(MenuSelectionManager msm)
/*      */   {
/* 1162 */     if (msm == null) {
/* 1163 */       msm = MenuSelectionManager.defaultManager();
/*      */     }
/* 1165 */     msm.clearSelectedPath();
/* 1166 */     this.menuItem.doClick(0);
/*      */   }
/*      */ 
/*      */   private boolean isInternalFrameSystemMenu()
/*      */   {
/* 1177 */     String actionCommand = this.menuItem.getActionCommand();
/*      */ 
/* 1182 */     return (actionCommand.equals("Close")) || (actionCommand.equals("Minimize")) || (actionCommand.equals("Restore")) || (actionCommand.equals("Maximize"));
/*      */   }
/*      */ 
/*      */   public ThemePainter getPainter()
/*      */   {
/* 1191 */     return this._painter;
/*      */   }
/*      */ 
/*      */   protected boolean isDownArrowVisible(Container c) {
/* 1195 */     if (((c instanceof TopLevelMenuContainer)) && (((TopLevelMenuContainer)c).isMenuBar())) {
/* 1196 */       return false;
/*      */     }
/* 1198 */     if (((c instanceof TopLevelMenuContainer)) && (!((TopLevelMenuContainer)c).isMenuBar())) {
/* 1199 */       return true;
/*      */     }
/*      */ 
/* 1202 */     return !(c instanceof JMenuBar);
/*      */   }
/*      */ 
/*      */   protected int getRightMargin()
/*      */   {
/* 1210 */     return 0;
/*      */   }
/*      */ 
/*      */   private static class ClickAction extends AbstractAction
/*      */   {
/*      */     public void actionPerformed(ActionEvent e)
/*      */     {
/* 1143 */       JMenuItem mi = (JMenuItem)e.getSource();
/* 1144 */       MenuSelectionManager.defaultManager().clearSelectedPath();
/* 1145 */       mi.doClick();
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
/* 1123 */       String name = e.getPropertyName();
/*      */ 
/* 1125 */       if ((name.equals("labelFor")) || (name.equals("displayedMnemonic")) || (name.equals("accelerator")))
/*      */       {
/* 1127 */         MetalMenuItemUI.this.updateAcceleratorBinding();
/*      */       }
/* 1129 */       else if ((name.equals("text")) || ("font".equals(name)) || ("foreground".equals(name)))
/*      */       {
/* 1134 */         JMenuItem lbl = (JMenuItem)e.getSource();
/* 1135 */         String text = lbl.getText();
/* 1136 */         BasicHTML.updateRenderer(lbl, text);
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
/* 1097 */       if ((MetalMenuItemUI.this.menuItem != null) && (MetalMenuItemUI.this.menuItem.isEnabled())) {
/* 1098 */         int key = MetalMenuItemUI.this.menuItem.getMnemonic();
/* 1099 */         if ((key == 0) || (e.getPath().length != 2))
/* 1100 */           return;
/* 1101 */         if (lower((char)key) == lower(e.getKeyChar())) {
/* 1102 */           MenuSelectionManager manager = e.getMenuSelectionManager();
/*      */ 
/* 1104 */           MetalMenuItemUI.this.doClick(manager);
/* 1105 */           e.consume();
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
/* 1117 */       return Character.toLowerCase(keyChar);
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
/* 1062 */       if ((MetalMenuItemUI.this.menuItem != null) && (MetalMenuItemUI.this.menuItem.isEnabled())) {
/* 1063 */         MenuSelectionManager manager = e.getMenuSelectionManager();
/* 1064 */         MenuElement[] path = e.getPath();
/* 1065 */         manager.setSelectedPath(path);
/*      */       }
/*      */     }
/*      */ 
/*      */     public void menuDragMouseExited(MenuDragMouseEvent e) {
/*      */     }
/*      */ 
/*      */     public void menuDragMouseReleased(MenuDragMouseEvent e) {
/* 1073 */       if ((MetalMenuItemUI.this.menuItem != null) && (MetalMenuItemUI.this.menuItem.isEnabled())) {
/* 1074 */         MenuSelectionManager manager = e.getMenuSelectionManager();
/* 1075 */         Point p = e.getPoint();
/* 1076 */         if ((p.x >= 0) && (p.x < MetalMenuItemUI.this.menuItem.getWidth()) && (p.y >= 0) && (p.y < MetalMenuItemUI.this.menuItem.getHeight()))
/*      */         {
/* 1078 */           MetalMenuItemUI.this.doClick(manager);
/*      */         }
/*      */         else
/* 1081 */           manager.clearSelectedPath();
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
/*  992 */       if (!SwingUtilities.isLeftMouseButton(e)) {
/*  993 */         return;
/*      */       }
/*      */ 
/*  996 */       if ((MetalMenuItemUI.this.menuItem != null) && (MetalMenuItemUI.this.menuItem.isEnabled())) {
/*  997 */         MenuSelectionManager manager = MenuSelectionManager.defaultManager();
/*  998 */         Point p = e.getPoint();
/*  999 */         if ((p.x >= 0) && (p.x < MetalMenuItemUI.this.menuItem.getWidth()) && (p.y >= 0) && (p.y < MetalMenuItemUI.this.menuItem.getHeight()))
/*      */         {
/* 1001 */           MetalMenuItemUI.this.doClick(manager);
/*      */         }
/*      */         else
/* 1004 */           manager.processMouseEvent(e);
/*      */       }
/*      */     }
/*      */ 
/*      */     public void mouseEntered(MouseEvent e)
/*      */     {
/* 1010 */       if ((MetalMenuItemUI.this.menuItem != null) && (MetalMenuItemUI.this.menuItem.isEnabled())) {
/* 1011 */         MenuSelectionManager manager = MenuSelectionManager.defaultManager();
/* 1012 */         int modifiers = e.getModifiers();
/*      */ 
/* 1014 */         if ((modifiers & 0x1C) != 0)
/*      */         {
/* 1016 */           MenuSelectionManager.defaultManager().processMouseEvent(e);
/*      */         }
/*      */         else
/* 1019 */           manager.setSelectedPath(MetalMenuItemUI.this.getPath());
/*      */       }
/*      */     }
/*      */ 
/*      */     public void mouseExited(MouseEvent e)
/*      */     {
/* 1025 */       if ((MetalMenuItemUI.this.menuItem != null) && (MetalMenuItemUI.this.menuItem.isEnabled())) {
/* 1026 */         MenuSelectionManager manager = MenuSelectionManager.defaultManager();
/*      */ 
/* 1028 */         int modifiers = e.getModifiers();
/*      */ 
/* 1030 */         if ((modifiers & 0x1C) != 0)
/*      */         {
/* 1032 */           MenuSelectionManager.defaultManager().processMouseEvent(e);
/*      */         }
/*      */         else
/*      */         {
/* 1036 */           MenuElement[] path = manager.getSelectedPath();
/* 1037 */           if (path.length > 1) {
/* 1038 */             MenuElement[] newPath = new MenuElement[path.length - 1];
/*      */ 
/* 1040 */             int i = 0; for (int c = path.length - 1; i < c; i++)
/* 1041 */               newPath[i] = path[i];
/* 1042 */             manager.setSelectedPath(newPath);
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */ 
/*      */     public void mouseDragged(MouseEvent e) {
/* 1049 */       MenuSelectionManager.defaultManager().processMouseEvent(e);
/*      */     }
/*      */ 
/*      */     public void mouseMoved(MouseEvent e)
/*      */     {
/*      */     }
/*      */   }
/*      */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     com.jidesoft.plaf.metal.MetalMenuItemUI
 * JD-Core Version:    0.6.0
 */