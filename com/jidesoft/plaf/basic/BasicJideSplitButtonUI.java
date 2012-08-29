/*      */ package com.jidesoft.plaf.basic;
/*      */ 
/*      */ import com.jidesoft.icons.IconsFactory;
/*      */ import com.jidesoft.plaf.UIDefaultsLookup;
/*      */ import com.jidesoft.plaf.vsnet.VsnetMenuUI;
/*      */ import com.jidesoft.swing.ComponentStateSupport;
/*      */ import com.jidesoft.swing.DefaultSplitButtonModel;
/*      */ import com.jidesoft.swing.JideSplitButton;
/*      */ import com.jidesoft.swing.JideSwingUtilities;
/*      */ import com.jidesoft.swing.SplitButtonModel;
/*      */ import com.jidesoft.utils.SecurityUtils;
/*      */ import com.sun.java.swing.plaf.windows.WindowsLookAndFeel;
/*      */ import java.awt.Color;
/*      */ import java.awt.Component;
/*      */ import java.awt.ComponentOrientation;
/*      */ import java.awt.Container;
/*      */ import java.awt.Dimension;
/*      */ import java.awt.Font;
/*      */ import java.awt.FontMetrics;
/*      */ import java.awt.Graphics;
/*      */ import java.awt.Point;
/*      */ import java.awt.Rectangle;
/*      */ import java.awt.event.ActionEvent;
/*      */ import java.awt.event.FocusEvent;
/*      */ import java.awt.event.FocusListener;
/*      */ import java.awt.event.MouseEvent;
/*      */ import java.util.ArrayList;
/*      */ import javax.swing.AbstractButton;
/*      */ import javax.swing.ButtonModel;
/*      */ import javax.swing.Icon;
/*      */ import javax.swing.ImageIcon;
/*      */ import javax.swing.InputMap;
/*      */ import javax.swing.JComponent;
/*      */ import javax.swing.JMenu;
/*      */ import javax.swing.JMenuItem;
/*      */ import javax.swing.JPopupMenu;
/*      */ import javax.swing.KeyStroke;
/*      */ import javax.swing.MenuElement;
/*      */ import javax.swing.MenuSelectionManager;
/*      */ import javax.swing.SwingUtilities;
/*      */ import javax.swing.event.MouseInputListener;
/*      */ import javax.swing.plaf.ComponentUI;
/*      */ import javax.swing.plaf.UIResource;
/*      */ import javax.swing.text.View;
/*      */ 
/*      */ public class BasicJideSplitButtonUI extends VsnetMenuUI
/*      */ {
/*      */   protected ThemePainter _painter;
/*      */   protected Color _shadowColor;
/*      */   protected Color _darkShadowColor;
/*      */   protected Color _highlight;
/*      */   protected Color _lightHighlightColor;
/*      */   protected int _splitButtonMargin;
/*      */   protected int _splitButtonMarginOnMenu;
/*      */   protected boolean _isFloatingIcon;
/*      */   private FocusListener _focusListener;
/*      */   private static final String propertyPrefix = "JideSplitButton";
/*      */ 
/*      */   public BasicJideSplitButtonUI()
/*      */   {
/*   39 */     this._splitButtonMargin = 12;
/*   40 */     this._splitButtonMarginOnMenu = 20;
/*      */ 
/*   42 */     this._isFloatingIcon = false;
/*      */   }
/*      */ 
/*      */   public static ComponentUI createUI(JComponent x)
/*      */   {
/*   50 */     return new BasicJideSplitButtonUI();
/*      */   }
/*      */ 
/*      */   protected String getPropertyPrefix()
/*      */   {
/*   55 */     return "JideSplitButton";
/*      */   }
/*      */ 
/*      */   protected void installDefaults()
/*      */   {
/*   60 */     this._painter = ((ThemePainter)UIDefaultsLookup.get("Theme.painter"));
/*   61 */     this._isFloatingIcon = UIDefaultsLookup.getBoolean("Icon.floating");
/*      */ 
/*   63 */     this._shadowColor = UIDefaultsLookup.getColor("JideButton.shadow");
/*   64 */     this._darkShadowColor = UIDefaultsLookup.getColor("JideButton.darkShadow");
/*   65 */     this._highlight = UIDefaultsLookup.getColor("JideButton.highlight");
/*   66 */     this._lightHighlightColor = UIDefaultsLookup.getColor("JideButton.light");
/*      */ 
/*   68 */     this.menuItem.setRolloverEnabled(true);
/*      */ 
/*   70 */     super.installDefaults();
/*      */   }
/*      */ 
/*      */   protected void uninstallDefaults()
/*      */   {
/*   75 */     this._painter = null;
/*      */ 
/*   77 */     this._shadowColor = null;
/*   78 */     this._highlight = null;
/*   79 */     this._lightHighlightColor = null;
/*   80 */     this._darkShadowColor = null;
/*      */ 
/*   82 */     super.uninstallDefaults();
/*      */   }
/*      */ 
/*      */   protected void installListeners()
/*      */   {
/*   87 */     super.installListeners();
/*   88 */     if (this._focusListener == null)
/*   89 */       this._focusListener = new FocusListener() {
/*      */         public void focusGained(FocusEvent e) {
/*   91 */           BasicJideSplitButtonUI.this.menuItem.repaint();
/*      */         }
/*      */ 
/*      */         public void focusLost(FocusEvent e) {
/*   95 */           BasicJideSplitButtonUI.this.menuItem.repaint();
/*      */         }
/*      */       };
/*   99 */     this.menuItem.addFocusListener(this._focusListener);
/*      */   }
/*      */ 
/*      */   protected void uninstallListeners()
/*      */   {
/*  104 */     super.uninstallListeners();
/*  105 */     if (this._focusListener != null)
/*  106 */       this.menuItem.removeFocusListener(this._focusListener);
/*      */   }
/*      */ 
/*      */   static Object getUIOfType(ComponentUI ui, Class clazz)
/*      */   {
/*  118 */     if (clazz.isInstance(ui)) {
/*  119 */       return ui;
/*      */     }
/*  121 */     return null;
/*      */   }
/*      */ 
/*      */   public InputMap getInputMap(int condition, JComponent c)
/*      */   {
/*  133 */     if (condition == 0) {
/*  134 */       BasicJideSplitButtonUI ui = (BasicJideSplitButtonUI)getUIOfType(((JideSplitButton)c).getUI(), BasicJideSplitButtonUI.class);
/*      */ 
/*  136 */       if (ui != null) {
/*  137 */         return (InputMap)UIDefaultsLookup.get(ui.getPropertyPrefix() + ".focusInputMap");
/*      */       }
/*      */     }
/*  140 */     return null;
/*      */   }
/*      */ 
/*      */   protected void installKeyboardActions()
/*      */   {
/*  145 */     super.installKeyboardActions();
/*  146 */     AbstractButton b = this.menuItem;
/*      */ 
/*  148 */     LazyActionMap.installLazyActionMap(b, BasicJideSplitButtonUI.class, "JideSplitButton.actionMap");
/*      */ 
/*  151 */     InputMap km = getInputMap(0, b);
/*      */ 
/*  153 */     SwingUtilities.replaceUIInputMap(b, 0, km);
/*      */   }
/*      */ 
/*      */   protected void uninstallKeyboardActions()
/*      */   {
/*  158 */     AbstractButton b = this.menuItem;
/*  159 */     SwingUtilities.replaceUIInputMap(b, 2, null);
/*      */ 
/*  161 */     SwingUtilities.replaceUIInputMap(b, 0, null);
/*  162 */     SwingUtilities.replaceUIActionMap(b, null);
/*  163 */     super.uninstallKeyboardActions();
/*      */   }
/*      */ 
/*      */   protected MouseInputListener createMouseInputListener(JComponent c)
/*      */   {
/*  168 */     return new MouseInputHandler();
/*      */   }
/*      */ 
/*      */   protected void paintBackground(Graphics g, JMenuItem menuItem, Color bgColor)
/*      */   {
/*  173 */     ButtonModel model = menuItem.getModel();
/*      */ 
/*  176 */     int orientation = JideSwingUtilities.getOrientationOf(menuItem);
/*      */     int menuHeight;
/*      */     int menuWidth;
/*      */     int menuHeight;
/*  177 */     if (orientation == 0) {
/*  178 */       int menuWidth = menuItem.getWidth();
/*  179 */       menuHeight = menuItem.getHeight();
/*      */     }
/*      */     else {
/*  182 */       menuWidth = menuItem.getHeight();
/*  183 */       menuHeight = menuItem.getWidth();
/*      */     }
/*      */ 
/*  186 */     orientation = 0;
/*      */ 
/*  189 */     Object o = menuItem.getClientProperty("JideSplitButton.alwaysPaintBackground");
/*      */     boolean paintBackground;
/*      */     boolean paintBackground;
/*  190 */     if ((o instanceof Boolean)) {
/*  191 */       paintBackground = ((Boolean)o).booleanValue();
/*      */     }
/*      */     else {
/*  194 */       paintBackground = menuItem.isOpaque();
/*      */     }
/*      */ 
/*  197 */     if (!((JMenu)menuItem).isTopLevelMenu()) {
/*  198 */       super.paintBackground(g, menuItem, bgColor);
/*  199 */       if (menuItem.isEnabled()) {
/*  200 */         if ((model.isArmed()) || (model.isPressed()) || (isMouseOver())) {
/*  201 */           g.setColor(this.selectionForeground);
/*  202 */           g.drawLine(menuWidth - this._splitButtonMarginOnMenu, 0, menuWidth - this._splitButtonMarginOnMenu, menuHeight - 2);
/*  203 */           JideSwingUtilities.paintArrow(g, this.selectionForeground, menuWidth - this._splitButtonMarginOnMenu / 2 - 2, menuHeight / 2 - 3, 7, 1);
/*      */         }
/*      */         else {
/*  206 */           g.setColor(getForegroundOfState(menuItem));
/*  207 */           g.drawLine(menuWidth - this._splitButtonMarginOnMenu, 0, menuWidth - this._splitButtonMarginOnMenu, menuHeight - 2);
/*  208 */           JideSwingUtilities.paintArrow(g, getForegroundOfState(menuItem), menuWidth - this._splitButtonMarginOnMenu / 2 - 2, menuHeight / 2 - 3, 7, 1);
/*      */         }
/*      */       }
/*      */       else {
/*  212 */         g.setColor(UIDefaultsLookup.getColor("controlDkShadow"));
/*  213 */         g.drawLine(menuWidth - this._splitButtonMarginOnMenu, 0, menuWidth - this._splitButtonMarginOnMenu, menuHeight - 2);
/*  214 */         JideSwingUtilities.paintArrow(g, UIDefaultsLookup.getColor("controlDkShadow"), menuWidth - this._splitButtonMarginOnMenu / 2 - 2, menuHeight / 2 - 3, 7, 1);
/*      */       }
/*  216 */       return;
/*      */     }
/*      */ 
/*  219 */     if (paintBackground) {
/*  220 */       if (menuItem.getParent() != null) {
/*  221 */         g.setColor(menuItem.getParent().getBackground());
/*      */       }
/*      */       else {
/*  224 */         g.setColor(menuItem.getBackground());
/*      */       }
/*  226 */       g.fillRect(0, 0, menuWidth, menuHeight);
/*      */     }
/*      */ 
/*  229 */     JideSplitButton b = (JideSplitButton)menuItem;
/*  230 */     if (b.getButtonStyle() == 0) {
/*  231 */       if (model.isSelected()) {
/*  232 */         if (isAlwaysDropdown(b)) {
/*  233 */           Rectangle rect = new Rectangle(0, 0, menuWidth, menuHeight);
/*  234 */           getPainter().paintButtonBackground(b, g, rect, orientation, 2);
/*      */         }
/*  236 */         else if (b.getClientProperty("JButton.segmentPosition") != null) {
/*  237 */           Rectangle rect = getButtonRect(b, orientation, menuWidth, menuHeight);
/*  238 */           if (b.isButtonEnabled()) {
/*  239 */             getPainter().paintButtonBackground(b, g, rect, orientation, 2);
/*      */           }
/*  241 */           else if (paintBackground) {
/*  242 */             getPainter().paintButtonBackground(b, g, rect, orientation, 6);
/*      */           }
/*  244 */           rect = getDropDownRect(b, orientation, menuWidth, menuHeight);
/*  245 */           getPainter().paintButtonBackground(b, g, rect, orientation, 1);
/*      */         }
/*      */         else {
/*  248 */           Rectangle rect = getButtonRect(b, orientation, menuWidth, menuHeight);
/*  249 */           if (b.isButtonEnabled()) {
/*  250 */             getPainter().paintButtonBackground(b, g, rect, orientation, 3);
/*      */           }
/*  252 */           else if (paintBackground) {
/*  253 */             getPainter().paintButtonBackground(b, g, rect, orientation, 5);
/*      */           }
/*  255 */           rect = getDropDownRect(b, orientation, menuWidth, menuHeight);
/*  256 */           getPainter().paintButtonBackground(b, g, rect, orientation, 3);
/*  257 */           getPainter().paintSelectedMenu(b, g, new Rectangle(0, 0, menuWidth, menuHeight), orientation, 3);
/*      */         }
/*      */       }
/*  260 */       else if ((model.isArmed()) || (model.isPressed())) {
/*  261 */         Rectangle rect = getButtonRect(b, orientation, menuWidth, menuHeight);
/*  262 */         if (b.isButtonEnabled()) {
/*  263 */           getPainter().paintButtonBackground(b, g, rect, orientation, 1);
/*      */         }
/*  265 */         else if (paintBackground) {
/*  266 */           getPainter().paintButtonBackground(b, g, rect, orientation, 4);
/*      */         }
/*  268 */         rect = getDropDownRect(b, orientation, menuWidth, menuHeight);
/*  269 */         getPainter().paintButtonBackground(b, g, rect, orientation, 2);
/*      */       }
/*  271 */       else if (((model instanceof SplitButtonModel)) && (((DefaultSplitButtonModel)model).isButtonSelected())) {
/*  272 */         if (((isMouseOver()) || (b.hasFocus())) && (model.isEnabled())) {
/*  273 */           Rectangle rect = getDropDownRect(b, orientation, menuWidth, menuHeight);
/*  274 */           getPainter().paintButtonBackground(b, g, rect, orientation, 2);
/*  275 */           rect = getButtonRect(b, orientation, menuWidth, menuHeight);
/*  276 */           if (b.isButtonEnabled()) {
/*  277 */             getPainter().paintButtonBackground(b, g, rect, orientation, 1);
/*      */           }
/*  279 */           else if (paintBackground)
/*  280 */             getPainter().paintButtonBackground(b, g, rect, orientation, 4);
/*      */         }
/*      */         else
/*      */         {
/*  284 */           Rectangle rect = getDropDownRect(b, orientation, menuWidth, menuHeight);
/*  285 */           getPainter().paintButtonBackground(b, g, rect, orientation, 0);
/*  286 */           rect = getButtonRect(b, orientation, menuWidth, menuHeight);
/*  287 */           if (b.isButtonEnabled()) {
/*  288 */             getPainter().paintButtonBackground(b, g, rect, orientation, 3);
/*      */           }
/*  290 */           else if (paintBackground) {
/*  291 */             getPainter().paintButtonBackground(b, g, rect, orientation, 5);
/*      */           }
/*      */         }
/*      */       }
/*  295 */       else if (((b.isRolloverEnabled()) && (isMouseOver())) || ((b.hasFocus()) && (model.isEnabled()))) {
/*  296 */         if (isAlwaysDropdown(b)) {
/*  297 */           Rectangle rect = new Rectangle(0, 0, menuWidth, menuHeight);
/*  298 */           getPainter().paintButtonBackground(b, g, rect, orientation, 2);
/*      */         }
/*      */         else
/*      */         {
/*  302 */           Rectangle rect = getButtonRect(b, orientation, menuWidth, menuHeight);
/*  303 */           if (b.isButtonEnabled()) {
/*  304 */             getPainter().paintButtonBackground(b, g, rect, orientation, 2);
/*      */           }
/*  306 */           else if (paintBackground) {
/*  307 */             getPainter().paintButtonBackground(b, g, rect, orientation, 6);
/*      */           }
/*  309 */           rect = getDropDownRect(b, orientation, menuWidth, menuHeight);
/*  310 */           getPainter().paintButtonBackground(b, g, rect, orientation, 2);
/*      */         }
/*      */ 
/*      */       }
/*  314 */       else if (paintBackground) {
/*  315 */         Rectangle rect = getButtonRect(b, orientation, menuWidth, menuHeight);
/*  316 */         if ((b.isEnabled()) && (b.isButtonEnabled())) {
/*  317 */           getPainter().paintButtonBackground(b, g, rect, 0, 0);
/*      */         }
/*      */         else {
/*  320 */           getPainter().paintButtonBackground(b, g, rect, 0, 4);
/*      */         }
/*  322 */         if ("true".equals(SecurityUtils.getProperty("shadingtheme", "false"))) {
/*  323 */           JideSwingUtilities.fillGradient(g, rect, 0);
/*      */         }
/*  325 */         rect = getDropDownRect(b, orientation, menuWidth, menuHeight);
/*  326 */         if (b.isEnabled()) {
/*  327 */           getPainter().paintButtonBackground(b, g, rect, 0, 0);
/*      */         }
/*      */         else {
/*  330 */           getPainter().paintButtonBackground(b, g, rect, 0, 4);
/*      */         }
/*  332 */         if ("true".equals(SecurityUtils.getProperty("shadingtheme", "false"))) {
/*  333 */           JideSwingUtilities.fillGradient(g, rect, 0);
/*      */         }
/*      */       }
/*      */ 
/*      */     }
/*  338 */     else if (b.getButtonStyle() == 2) {
/*  339 */       if (model.isSelected())
/*      */       {
/*  341 */         getPainter().paintSelectedMenu(b, g, new Rectangle(0, 0, menuWidth, menuHeight), orientation, 3);
/*      */       }
/*  343 */       else if ((model.isArmed()) || (model.isPressed())) {
/*  344 */         Rectangle rect = getButtonRect(b, orientation, menuWidth, menuHeight);
/*  345 */         if (b.isButtonEnabled()) {
/*  346 */           JideSwingUtilities.paintBackground(g, rect, this._highlight, this._highlight);
/*      */         }
/*  348 */         rect = getDropDownRect(b, orientation, menuWidth, menuHeight);
/*  349 */         JideSwingUtilities.paintBackground(g, rect, this._highlight, this._highlight);
/*      */ 
/*  351 */         if (!b.isOpaque()) {
/*  352 */           rect = getButtonRect(b, orientation, menuWidth, menuHeight);
/*  353 */           paintSunkenBorder(g, rect);
/*  354 */           rect = getDropDownRect(b, orientation, menuWidth, menuHeight);
/*  355 */           paintRaisedBorder(g, rect);
/*      */         }
/*      */       }
/*  358 */       else if (((model instanceof SplitButtonModel)) && (((DefaultSplitButtonModel)model).isButtonSelected())) {
/*  359 */         if (((isMouseOver()) || (b.hasFocus())) && (model.isEnabled())) {
/*  360 */           Rectangle rect = getDropDownRect(b, orientation, menuWidth, menuHeight);
/*  361 */           JideSwingUtilities.paintBackground(g, rect, this._highlight, this._highlight);
/*  362 */           rect = getButtonRect(b, orientation, menuWidth, menuHeight);
/*  363 */           if (b.isButtonEnabled()) {
/*  364 */             JideSwingUtilities.paintBackground(g, rect, this._highlight, this._highlight);
/*      */           }
/*  366 */           if (!b.isOpaque()) {
/*  367 */             rect = getButtonRect(b, orientation, menuWidth, menuHeight);
/*  368 */             paintSunkenBorder(g, rect);
/*  369 */             rect = getDropDownRect(b, orientation, menuWidth, menuHeight);
/*  370 */             paintRaisedBorder(g, rect);
/*      */           }
/*      */         }
/*      */         else
/*      */         {
/*  375 */           if (b.isOpaque()) {
/*  376 */             Rectangle rect = getDropDownRect(b, orientation, menuWidth, menuHeight);
/*  377 */             JideSwingUtilities.paintBackground(g, rect, this._highlight, this._highlight);
/*      */           }
/*  379 */           Rectangle rect = getButtonRect(b, orientation, menuWidth, menuHeight);
/*  380 */           JideSwingUtilities.paintBackground(g, rect, this._highlight, this._highlight);
/*      */ 
/*  382 */           if (!b.isOpaque()) {
/*  383 */             rect = getButtonRect(b, orientation, menuWidth, menuHeight);
/*  384 */             paintSunkenBorder(g, rect);
/*      */           }
/*      */ 
/*      */         }
/*      */ 
/*      */       }
/*  392 */       else if (((b.isRolloverEnabled()) && (isMouseOver())) || ((b.hasFocus()) && (model.isEnabled())))
/*      */       {
/*  394 */         Rectangle rect = getButtonRect(b, orientation, menuWidth, menuHeight);
/*  395 */         if (b.isButtonEnabled()) {
/*  396 */           JideSwingUtilities.paintBackground(g, rect, this._highlight, this._highlight);
/*      */         }
/*  398 */         rect = getDropDownRect(b, orientation, menuWidth, menuHeight);
/*  399 */         JideSwingUtilities.paintBackground(g, rect, this._highlight, this._highlight);
/*      */ 
/*  401 */         if (isAlwaysDropdown(b)) {
/*  402 */           rect = new Rectangle(0, 0, menuWidth, menuHeight);
/*  403 */           paintRaisedBorder(g, rect);
/*      */         }
/*      */         else {
/*  406 */           rect = getButtonRect(b, orientation, menuWidth, menuHeight);
/*  407 */           paintRaisedBorder(g, rect);
/*  408 */           rect = getDropDownRect(b, orientation, menuWidth, menuHeight);
/*  409 */           paintRaisedBorder(g, rect);
/*      */         }
/*      */ 
/*      */       }
/*  413 */       else if (b.isOpaque()) {
/*  414 */         Rectangle rect = getButtonRect(b, orientation, menuWidth, menuHeight);
/*  415 */         if (b.isButtonEnabled()) {
/*  416 */           getPainter().paintButtonBackground(b, g, rect, orientation, 0);
/*      */         }
/*  418 */         rect = getDropDownRect(b, orientation, menuWidth, menuHeight);
/*  419 */         getPainter().paintButtonBackground(b, g, rect, orientation, 0);
/*      */       }
/*      */ 
/*      */     }
/*  424 */     else if (b.getButtonStyle() == 1) {
/*  425 */       if (model.isSelected())
/*      */       {
/*  427 */         getPainter().paintSelectedMenu(b, g, new Rectangle(0, 0, menuWidth, menuHeight), orientation, 3);
/*      */       }
/*  429 */       else if ((model.isArmed()) || (model.isPressed())) {
/*  430 */         Rectangle rect = getButtonRect(b, orientation, menuWidth, menuHeight);
/*  431 */         if (b.isButtonEnabled()) {
/*  432 */           getPainter().paintButtonBackground(b, g, rect, orientation, 1);
/*      */         }
/*  434 */         rect = getDropDownRect(b, orientation, menuWidth, menuHeight);
/*  435 */         getPainter().paintButtonBackground(b, g, rect, orientation, 2);
/*      */ 
/*  437 */         if (!b.isOpaque()) {
/*  438 */           rect = getButtonRect(b, orientation, menuWidth, menuHeight);
/*  439 */           paintSunken2Border(g, rect);
/*  440 */           rect = getDropDownRect(b, orientation, menuWidth, menuHeight);
/*  441 */           paintRaisedBorder(g, rect);
/*      */         }
/*      */       }
/*  444 */       else if (((model instanceof SplitButtonModel)) && (((DefaultSplitButtonModel)model).isButtonSelected())) {
/*  445 */         if ((isMouseOver()) && (model.isEnabled())) {
/*  446 */           Rectangle rect = getDropDownRect(b, orientation, menuWidth, menuHeight);
/*  447 */           getPainter().paintButtonBackground(b, g, rect, orientation, 2);
/*  448 */           rect = getButtonRect(b, orientation, menuWidth, menuHeight);
/*  449 */           if (b.isButtonEnabled()) {
/*  450 */             getPainter().paintButtonBackground(b, g, rect, orientation, 1);
/*      */           }
/*  452 */           if (!b.isOpaque()) {
/*  453 */             rect = getButtonRect(b, orientation, menuWidth, menuHeight);
/*  454 */             paintSunken2Border(g, rect);
/*  455 */             rect = getDropDownRect(b, orientation, menuWidth, menuHeight);
/*  456 */             paintRaisedBorder(g, rect);
/*      */           }
/*      */         }
/*      */         else
/*      */         {
/*  461 */           if (b.isOpaque()) {
/*  462 */             Rectangle rect = getDropDownRect(b, orientation, menuWidth, menuHeight);
/*  463 */             getPainter().paintButtonBackground(b, g, rect, orientation, 0);
/*      */           }
/*  465 */           Rectangle rect = getButtonRect(b, orientation, menuWidth, menuHeight);
/*  466 */           getPainter().paintButtonBackground(b, g, rect, orientation, 3);
/*      */ 
/*  468 */           if (!b.isOpaque()) {
/*  469 */             rect = getButtonRect(b, orientation, menuWidth, menuHeight);
/*  470 */             paintSunken2Border(g, rect);
/*  471 */             rect = getDropDownRect(b, orientation, menuWidth, menuHeight);
/*  472 */             paintRaisedBorder(g, rect);
/*      */           }
/*      */ 
/*      */         }
/*      */ 
/*      */       }
/*  478 */       else if ((b.isRolloverEnabled()) && (isMouseOver()) && (model.isEnabled()))
/*      */       {
/*  480 */         if (isAlwaysDropdown(b)) {
/*  481 */           Rectangle rect = new Rectangle(0, 0, menuWidth, menuHeight);
/*  482 */           getPainter().paintButtonBackground(b, g, rect, orientation, 2);
/*  483 */           paintRaised2Border(g, rect);
/*      */         }
/*      */         else {
/*  486 */           Rectangle rect = getButtonRect(b, orientation, menuWidth, menuHeight);
/*  487 */           if (b.isButtonEnabled()) {
/*  488 */             getPainter().paintButtonBackground(b, g, rect, orientation, 2);
/*      */           }
/*  490 */           rect = getDropDownRect(b, orientation, menuWidth, menuHeight);
/*  491 */           getPainter().paintButtonBackground(b, g, rect, orientation, 2);
/*  492 */           rect = getButtonRect(b, orientation, menuWidth, menuHeight);
/*  493 */           paintRaised2Border(g, rect);
/*  494 */           rect = getDropDownRect(b, orientation, menuWidth, menuHeight);
/*  495 */           paintRaised2Border(g, rect);
/*      */         }
/*      */ 
/*      */       }
/*  499 */       else if (b.isOpaque()) {
/*  500 */         Rectangle rect = getButtonRect(b, orientation, menuWidth, menuHeight);
/*  501 */         if (b.isButtonEnabled()) {
/*  502 */           getPainter().paintButtonBackground(b, g, rect, orientation, 0);
/*      */         }
/*  504 */         rect = getDropDownRect(b, orientation, menuWidth, menuHeight);
/*  505 */         getPainter().paintButtonBackground(b, g, rect, orientation, 0);
/*      */       }
/*  508 */       else if (isAlwaysDropdown(b)) {
/*  509 */         Rectangle rect = new Rectangle(0, 0, menuWidth, menuHeight);
/*  510 */         paintRaisedBorder(g, rect);
/*      */       }
/*      */       else {
/*  513 */         Rectangle rect = getButtonRect(b, orientation, menuWidth, menuHeight);
/*  514 */         paintRaisedBorder(g, rect);
/*  515 */         rect = getDropDownRect(b, orientation, menuWidth, menuHeight);
/*  516 */         paintRaisedBorder(g, rect);
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/*  523 */     paintArrow(menuItem, g);
/*      */   }
/*      */ 
/*      */   protected void paintArrow(JMenuItem menuItem, Graphics g)
/*      */   {
/*  529 */     int orientation = JideSwingUtilities.getOrientationOf(menuItem);
/*      */     int menuHeight;
/*      */     int menuWidth;
/*      */     int menuHeight;
/*  530 */     if (orientation == 0) {
/*  531 */       int menuWidth = menuItem.getWidth();
/*  532 */       menuHeight = menuItem.getHeight();
/*      */     }
/*      */     else {
/*  535 */       menuWidth = menuItem.getHeight();
/*  536 */       menuHeight = menuItem.getWidth();
/*      */     }
/*      */     int startX;
/*      */     int startX;
/*  539 */     if (menuItem.getComponentOrientation().isLeftToRight()) {
/*  540 */       startX = menuWidth - 9;
/*      */     }
/*      */     else {
/*  543 */       startX = 4;
/*      */     }
/*  545 */     if (menuItem.isEnabled()) {
/*  546 */       JideSwingUtilities.paintArrow(g, getForegroundOfState(menuItem), startX, menuHeight / 2 - 1, 5, 0);
/*      */     }
/*      */     else
/*  549 */       JideSwingUtilities.paintArrow(g, UIDefaultsLookup.getColor("controlShadow"), startX, menuHeight / 2 - 1, 5, 0);
/*      */   }
/*      */ 
/*      */   protected Rectangle getDropDownRect(JComponent c, int orientation, int width, int height)
/*      */   {
/*  563 */     Object position = c.getClientProperty("JButton.segmentPosition");
/*      */     Rectangle rect;
/*      */     Rectangle rect;
/*  565 */     if (c.getComponentOrientation().isLeftToRight()) {
/*  566 */       rect = new Rectangle(width - this._splitButtonMargin - 1 + getOffset(), 0, this._splitButtonMargin - getOffset(), height);
/*      */     }
/*      */     else {
/*  569 */       rect = new Rectangle(0, 0, this._splitButtonMargin - getOffset(), height);
/*      */     }
/*  571 */     if ((position != null) && (!"only".equals(position)))
/*      */     {
/*  573 */       if ("first".equals(position)) {
/*  574 */         if (orientation == 0) {
/*  575 */           rect.width += 1;
/*      */         }
/*      */         else {
/*  578 */           rect.height += 1;
/*      */         }
/*      */       }
/*  581 */       else if ("middle".equals(position)) {
/*  582 */         if (orientation == 0) {
/*  583 */           rect.width += 1;
/*      */         }
/*      */         else {
/*  586 */           rect.height += 1;
/*      */         }
/*  589 */       } else if (!"last".equals(position));
/*      */     }
/*  591 */     return rect;
/*      */   }
/*      */ 
/*      */   protected Rectangle getButtonRect(JComponent c, int orientation, int width, int height)
/*      */   {
/*      */     Rectangle rect;
/*      */     Rectangle rect;
/*  605 */     if ((orientation == 0) && (c.getComponentOrientation().isLeftToRight())) {
/*  606 */       rect = new Rectangle(0, 0, width - this._splitButtonMargin, height);
/*      */     }
/*      */     else {
/*  609 */       rect = new Rectangle(this._splitButtonMargin - 1, 0, width - this._splitButtonMargin, height);
/*      */     }
/*  611 */     return rect;
/*      */   }
/*      */ 
/*      */   protected void paintSunkenBorder(Graphics g, Rectangle b) {
/*  615 */     Color old = g.getColor();
/*  616 */     g.setColor(this._shadowColor);
/*  617 */     g.drawLine(b.x, b.y, b.x + b.width - 1, b.y);
/*  618 */     g.drawLine(b.x, b.y, b.x, b.y + b.height - 1);
/*      */ 
/*  620 */     g.setColor(this._lightHighlightColor);
/*  621 */     g.drawLine(b.x, b.y + b.height - 1, b.x + b.width - 1, b.y + b.height - 1);
/*  622 */     g.drawLine(b.x + b.width - 1, b.y, b.x + b.width - 1, b.y + b.height - 1);
/*  623 */     g.setColor(old);
/*      */   }
/*      */ 
/*      */   protected void paintSunken2Border(Graphics g, Rectangle b) {
/*  627 */     Color old = g.getColor();
/*  628 */     g.setColor(this._darkShadowColor);
/*  629 */     g.drawLine(b.x, b.y, b.x + b.width - 2, b.y);
/*  630 */     g.drawLine(b.x, b.y, b.x, b.y + b.height - 2);
/*      */ 
/*  632 */     g.setColor(this._shadowColor);
/*  633 */     g.drawLine(b.x + 1, b.y + 1, b.x + b.width - 3, b.y + 1);
/*  634 */     g.drawLine(b.x + 1, b.y + 1, b.x + 1, b.y + b.height - 3);
/*      */ 
/*  636 */     g.setColor(this._lightHighlightColor);
/*  637 */     g.drawLine(b.x, b.y + b.height - 1, b.x + b.width - 1, b.y + b.height - 1);
/*  638 */     g.drawLine(b.x + b.width - 1, b.x, b.x + b.width - 1, b.y + b.height - 1);
/*  639 */     g.setColor(old);
/*      */   }
/*      */ 
/*      */   protected void paintRaised2Border(Graphics g, Rectangle b) {
/*  643 */     Color old = g.getColor();
/*  644 */     g.setColor(this._lightHighlightColor);
/*  645 */     g.drawLine(b.x, b.y, b.x + b.width - 1, b.y);
/*  646 */     g.drawLine(b.x, b.y, b.x, b.y + b.height - 1);
/*      */ 
/*  648 */     g.setColor(this._shadowColor);
/*  649 */     g.drawLine(b.x + 1, b.y + b.height - 2, b.x + b.width - 2, b.y + b.height - 2);
/*  650 */     g.drawLine(b.x + b.width - 2, 1, b.x + b.width - 2, b.y + b.height - 2);
/*      */ 
/*  652 */     g.setColor(this._darkShadowColor);
/*  653 */     g.drawLine(b.x, b.y + b.height - 1, b.x + b.width - 1, b.y + b.height - 1);
/*  654 */     g.drawLine(b.x + b.width - 1, b.y, b.x + b.width - 1, b.y + b.height - 1);
/*  655 */     g.setColor(old);
/*      */   }
/*      */ 
/*      */   protected void paintRaisedBorder(Graphics g, Rectangle b) {
/*  659 */     Color old = g.getColor();
/*  660 */     g.setColor(this._lightHighlightColor);
/*  661 */     g.drawLine(b.x, b.y, b.x + b.width - 1, b.y);
/*  662 */     g.drawLine(b.x, b.y, b.x, b.y + b.height - 1);
/*      */ 
/*  664 */     g.setColor(this._shadowColor);
/*  665 */     g.drawLine(b.x, b.y + b.height - 1, b.x + b.width - 1, b.y + b.height - 1);
/*  666 */     g.drawLine(b.x + b.width - 1, b.y, b.x + b.width - 1, b.y + b.height - 1);
/*  667 */     g.setColor(old);
/*      */   }
/*      */ 
/*      */   public Dimension getPreferredSize(JComponent c)
/*      */   {
/*  888 */     if ((!(c instanceof JMenu)) || (!((JMenu)c).isTopLevelMenu())) {
/*  889 */       return super.getPreferredSize(c);
/*      */     }
/*      */ 
/*  892 */     AbstractButton b = (AbstractButton)c;
/*      */ 
/*  894 */     boolean isHorizontal = JideSwingUtilities.getOrientationOf(c) == 0;
/*      */ 
/*  896 */     Dimension d = JideSwingUtilities.getPreferredButtonSize(b, this.defaultTextIconGap, true);
/*      */ 
/*  898 */     if (BasicJideButtonUI.shouldWrapText(c)) {
/*  899 */       if ((c instanceof JideSplitButton))
/*  900 */         d.width += getAdjustExtraWidth(b, b.getText(), 8);
/*      */     }
/*      */     else
/*      */     {
/*  904 */       d.width += getRightMargin();
/*      */ 
/*  906 */       if (isDownArrowVisible(b.getParent())) {
/*  907 */         d.width += 1;
/*      */       }
/*      */     }
/*      */ 
/*  911 */     if (isHorizontal) {
/*  912 */       return d;
/*      */     }
/*      */ 
/*  915 */     return new Dimension(d.height, d.width);
/*      */   }
/*      */ 
/*      */   public Dimension getMinimumSize(JComponent c)
/*      */   {
/*  920 */     if ((!(c instanceof JMenu)) || (!((JMenu)c).isTopLevelMenu())) {
/*  921 */       return super.getMinimumSize(c);
/*      */     }
/*      */ 
/*  924 */     Dimension d = getPreferredSize(c);
/*  925 */     View v = (View)c.getClientProperty("html");
/*  926 */     if (v != null) {
/*  927 */       if (JideSwingUtilities.getOrientationOf(c) == 0)
/*      */       {
/*      */         Dimension tmp51_50 = d; tmp51_50.width = (int)(tmp51_50.width - (v.getPreferredSpan(0) - v.getMinimumSpan(0)));
/*      */       }
/*      */       else
/*      */       {
/*      */         Dimension tmp76_75 = d; tmp76_75.height = (int)(tmp76_75.height - (v.getPreferredSpan(0) - v.getMinimumSpan(0)));
/*      */       }
/*      */     }
/*  933 */     return d;
/*      */   }
/*      */ 
/*      */   public Dimension getMaximumSize(JComponent c)
/*      */   {
/*  938 */     if ((!(c instanceof JMenu)) || (!((JMenu)c).isTopLevelMenu())) {
/*  939 */       return super.getMaximumSize(c);
/*      */     }
/*      */ 
/*  942 */     Dimension d = getPreferredSize(c);
/*  943 */     View v = (View)c.getClientProperty("html");
/*  944 */     if (v != null) {
/*  945 */       if (JideSwingUtilities.getOrientationOf(c) == 0)
/*      */       {
/*      */         Dimension tmp51_50 = d; tmp51_50.width = (int)(tmp51_50.width + (v.getMaximumSpan(0) - v.getPreferredSpan(0)));
/*      */       }
/*      */       else
/*      */       {
/*      */         Dimension tmp76_75 = d; tmp76_75.height = (int)(tmp76_75.height + (v.getMaximumSpan(0) - v.getPreferredSpan(0)));
/*      */       }
/*      */     }
/*  951 */     return d;
/*      */   }
/*      */ 
/*      */   protected void paintText(Graphics g, JMenuItem menuItem, Rectangle textRect, String text)
/*      */   {
/*  957 */     ButtonModel model = menuItem.getModel();
/*      */ 
/*  959 */     if (((!(menuItem instanceof JMenu)) || (!((JMenu)menuItem).isTopLevelMenu())) && 
/*  960 */       (menuItem.getComponentOrientation().isLeftToRight())) {
/*  961 */       int defaultTextIconGap = UIDefaultsLookup.getInt("MenuItem.textIconGap");
/*  962 */       int defaultShadowWidth = UIDefaultsLookup.getInt("MenuItem.shadowWidth");
/*  963 */       textRect.x = (defaultShadowWidth + defaultTextIconGap);
/*      */     }
/*      */ 
/*  970 */     FontMetrics fm = g.getFontMetrics();
/*  971 */     int mnemonicIndex = menuItem.getDisplayedMnemonicIndex();
/*      */ 
/*  973 */     if (WindowsLookAndFeel.isMnemonicHidden()) {
/*  974 */       mnemonicIndex = -1;
/*      */     }
/*      */ 
/*  977 */     Color oldColor = g.getColor();
/*      */ 
/*  979 */     if ((!model.isEnabled()) || (((menuItem instanceof JideSplitButton)) && (!((JideSplitButton)menuItem).isButtonEnabled()))) {
/*  980 */       if (menuItem.getParent() != null)
/*      */       {
/*  982 */         g.setColor(menuItem.getParent().getBackground().brighter());
/*      */ 
/*  986 */         drawStringUnderlineCharAt(menuItem, g, text, mnemonicIndex, textRect.x, textRect.y + fm.getAscent());
/*      */ 
/*  988 */         g.setColor(menuItem.getParent().getBackground().darker());
/*      */       }
/*      */ 
/*  993 */       drawStringUnderlineCharAt(menuItem, g, text, mnemonicIndex, textRect.x - 1, textRect.y + fm.getAscent() - 1);
/*      */     }
/*      */     else
/*      */     {
/*  998 */       if (model.isSelected()) {
/*  999 */         g.setColor(this.selectionForeground);
/*      */       }
/*      */       else {
/* 1002 */         g.setColor(getForegroundOfState(menuItem));
/*      */       }
/* 1004 */       drawStringUnderlineCharAt(menuItem, g, text, mnemonicIndex, textRect.x, textRect.y + fm.getAscent());
/*      */     }
/*      */ 
/* 1009 */     g.setColor(oldColor);
/*      */   }
/*      */ 
/*      */   private Color getForegroundOfState(JMenuItem menuItem) {
/* 1013 */     int state = JideSwingUtilities.getButtonState(menuItem);
/* 1014 */     Color foreground = null;
/* 1015 */     if ((menuItem instanceof ComponentStateSupport)) {
/* 1016 */       foreground = ((ComponentStateSupport)menuItem).getForegroundOfState(state);
/*      */     }
/* 1018 */     if ((foreground == null) || ((foreground instanceof UIResource))) {
/* 1019 */       foreground = menuItem.getForeground();
/*      */     }
/* 1021 */     return foreground;
/*      */   }
/*      */ 
/*      */   protected void drawStringUnderlineCharAt(JComponent c, Graphics g, String text, int underlinedIndex, int x, int y)
/*      */   {
/* 1026 */     JideSwingUtilities.drawStringUnderlineCharAt(c, g, text, underlinedIndex, x, y);
/*      */   }
/*      */ 
/*      */   protected void paintIcon(JMenuItem b, Graphics g)
/*      */   {
/* 1031 */     ButtonModel model = b.getModel();
/*      */ 
/* 1034 */     if (b.getIcon() != null)
/*      */     {
/* 1040 */       Icon icon = getIcon(b);
/*      */ 
/* 1042 */       if (icon != null) {
/* 1043 */         boolean enabled = (model.isEnabled()) && ((!(model instanceof SplitButtonModel)) || (((SplitButtonModel)model).isButtonEnabled()));
/* 1044 */         if ((isFloatingIcon()) && (enabled)) {
/* 1045 */           if ((model.isRollover()) && (!model.isPressed()) && (!model.isSelected())) {
/* 1046 */             if (!"true".equals(SecurityUtils.getProperty("shadingtheme", "false"))) {
/* 1047 */               if ((icon instanceof ImageIcon)) {
/* 1048 */                 ImageIcon shadow = IconsFactory.createGrayImage(((ImageIcon)icon).getImage());
/* 1049 */                 shadow.paintIcon(b, g, iconRect.x + 1, iconRect.y + 1);
/*      */               }
/*      */               else {
/* 1052 */                 ImageIcon shadow = IconsFactory.createGrayImage(b, icon);
/* 1053 */                 shadow.paintIcon(b, g, iconRect.x + 1, iconRect.y + 1);
/*      */               }
/* 1055 */               icon.paintIcon(b, g, iconRect.x - 1, iconRect.y - 1);
/*      */             }
/*      */             else {
/* 1058 */               icon.paintIcon(b, g, iconRect.x, iconRect.y);
/*      */             }
/*      */           }
/*      */           else {
/* 1062 */             icon.paintIcon(b, g, iconRect.x, iconRect.y);
/*      */           }
/*      */         }
/*      */         else
/* 1066 */           icon.paintIcon(b, g, iconRect.x, iconRect.y);
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   protected boolean isFloatingIcon()
/*      */   {
/* 1079 */     return this._isFloatingIcon;
/*      */   }
/*      */ 
/*      */   protected Icon getIcon(AbstractButton b)
/*      */   {
/* 1084 */     ButtonModel model = b.getModel();
/* 1085 */     Icon icon = b.getIcon();
/* 1086 */     Icon tmpIcon = null;
/* 1087 */     if ((!model.isEnabled()) || (!((JideSplitButton)this.menuItem).isButtonEnabled())) {
/* 1088 */       if (model.isSelected()) {
/* 1089 */         tmpIcon = b.getDisabledSelectedIcon();
/*      */       }
/*      */       else {
/* 1092 */         tmpIcon = b.getDisabledIcon();
/*      */       }
/*      */ 
/* 1096 */       if (tmpIcon == null) {
/* 1097 */         if ((icon instanceof ImageIcon)) {
/* 1098 */           icon = IconsFactory.createGrayImage(((ImageIcon)icon).getImage());
/*      */         }
/*      */         else {
/* 1101 */           icon = IconsFactory.createGrayImage(b, icon);
/*      */         }
/*      */       }
/*      */     }
/* 1105 */     else if ((model.isPressed()) && (model.isArmed()))
/*      */     {
/* 1106 */       tmpIcon = b.getPressedIcon();
/* 1107 */       if (tmpIcon == null);
/*      */     }
/* 1112 */     else if ((b.isRolloverEnabled()) && (model.isRollover())) {
/* 1113 */       if (model.isSelected()) {
/* 1114 */         tmpIcon = b.getRolloverSelectedIcon();
/*      */       }
/*      */       else {
/* 1117 */         tmpIcon = b.getRolloverIcon();
/*      */       }
/*      */     }
/* 1120 */     else if (model.isSelected()) {
/* 1121 */       tmpIcon = b.getSelectedIcon();
/*      */     }
/*      */ 
/* 1124 */     if (tmpIcon != null) {
/* 1125 */       icon = tmpIcon;
/*      */     }
/* 1127 */     return icon;
/*      */   }
/*      */ 
/*      */   protected int getOffset()
/*      */   {
/* 1136 */     return 0;
/*      */   }
/*      */ 
/*      */   protected boolean isAlwaysDropdown(JMenuItem menuItem) {
/* 1140 */     return ((menuItem instanceof JideSplitButton)) && (((JideSplitButton)menuItem).isAlwaysDropdown());
/*      */   }
/*      */ 
/*      */   protected int getRightMargin()
/*      */   {
/* 1145 */     return this._splitButtonMargin - 1;
/*      */   }
/*      */ 
/*      */   public static void loadActionMap(LazyActionMap map)
/*      */   {
/* 1204 */     map.put(new Actions("pressed"));
/* 1205 */     map.put(new Actions("released"));
/* 1206 */     map.put(new Actions("downPressed"));
/* 1207 */     map.put(new Actions("downReleased"));
/*      */   }
/*      */ 
/*      */   protected void updateMnemonicBinding()
/*      */   {
/* 1212 */     super.updateMnemonicBinding();
/* 1213 */     int mnemonic = this.menuItem.getModel().getMnemonic();
/* 1214 */     if ((mnemonic != 0) && (this.windowInputMap != null)) {
/* 1215 */       int[] shortcutKeys = (int[])(int[])UIDefaultsLookup.get("Menu.shortcutKeys");
/* 1216 */       if (shortcutKeys == null) {
/* 1217 */         shortcutKeys = new int[] { 8 };
/*      */       }
/* 1219 */       for (int shortcutKey : shortcutKeys) {
/* 1220 */         this.windowInputMap.put(KeyStroke.getKeyStroke(mnemonic, shortcutKey, false), "pressed");
/*      */ 
/* 1223 */         this.windowInputMap.put(KeyStroke.getKeyStroke(mnemonic, shortcutKey, true), "released");
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   protected static void downButtonPressed(JMenu menu)
/*      */   {
/* 1231 */     MenuSelectionManager manager = MenuSelectionManager.defaultManager();
/* 1232 */     if (menu.isTopLevelMenu()) {
/* 1233 */       if (menu.isSelected()) {
/* 1234 */         manager.clearSelectedPath();
/*      */       }
/*      */       else
/*      */       {
/* 1238 */         Container cnt = getFirstParentMenuElement(menu);
/*      */ 
/* 1240 */         if ((cnt != null) && ((cnt instanceof MenuElement))) {
/* 1241 */           ArrayList parents = new ArrayList();
/* 1242 */           while ((cnt instanceof MenuElement)) {
/* 1243 */             parents.add(0, cnt);
/* 1244 */             if ((cnt instanceof JPopupMenu)) {
/* 1245 */               cnt = (Container)((JPopupMenu)cnt).getInvoker(); continue;
/*      */             }
/*      */ 
/* 1249 */             cnt = getFirstParentMenuElement(cnt);
/*      */           }
/*      */ 
/* 1253 */           MenuElement[] me = new MenuElement[parents.size() + 1];
/* 1254 */           for (int i = 0; i < parents.size(); i++) {
/* 1255 */             Container container = (Container)parents.get(i);
/* 1256 */             me[i] = ((MenuElement)container);
/*      */           }
/* 1258 */           me[parents.size()] = menu;
/* 1259 */           manager.setSelectedPath(me);
/*      */         }
/*      */         else {
/* 1262 */           MenuElement[] me = new MenuElement[1];
/* 1263 */           me[0] = menu;
/* 1264 */           manager.setSelectedPath(me);
/*      */         }
/*      */       }
/*      */     }
/*      */ 
/* 1269 */     MenuElement[] selectedPath = manager.getSelectedPath();
/* 1270 */     if ((selectedPath.length > 0) && (selectedPath[(selectedPath.length - 1)] != menu.getPopupMenu()))
/*      */     {
/* 1272 */       if ((menu.isTopLevelMenu()) || (menu.getDelay() == 0))
/*      */       {
/* 1274 */         appendPath(selectedPath, menu.getPopupMenu());
/*      */       }
/*      */       else
/* 1277 */         setupPostTimer(menu);
/*      */     }
/*      */   }
/*      */ 
/*      */   protected static Container getFirstParentMenuElement(Component comp)
/*      */   {
/* 1283 */     Container parent = comp.getParent();
/*      */ 
/* 1285 */     while (parent != null) {
/* 1286 */       if ((parent instanceof MenuElement)) {
/* 1287 */         return parent;
/*      */       }
/* 1289 */       parent = parent.getParent();
/*      */     }
/*      */ 
/* 1292 */     return null;
/*      */   }
/*      */ 
/*      */   public static int getAdjustExtraWidth(Component c, String text, int extraWidth)
/*      */   {
/* 1303 */     String[] lines = getWrappedText(text);
/* 1304 */     Font font = c.getFont();
/* 1305 */     FontMetrics fm = c.getFontMetrics(font);
/* 1306 */     int line1Width = fm.stringWidth(lines[0]);
/* 1307 */     int line2Width = lines.length <= 1 ? 0 : fm.stringWidth(lines[1]);
/* 1308 */     int oldMaxWidth = Math.max(line1Width, line2Width);
/* 1309 */     line2Width += extraWidth;
/* 1310 */     int maxWidth = Math.max(line1Width, line2Width);
/* 1311 */     return maxWidth - oldMaxWidth;
/*      */   }
/*      */ 
/*      */   public static String getMaxLengthWord(String text) {
/* 1315 */     if (text.indexOf(' ') == -1) {
/* 1316 */       return text;
/*      */     }
/*      */ 
/* 1319 */     int minDiff = text.length();
/* 1320 */     int minPos = -1;
/* 1321 */     int mid = text.length() / 2;
/*      */ 
/* 1323 */     int pos = -1;
/*      */     while (true) {
/* 1325 */       pos = text.indexOf(' ', pos + 1);
/* 1326 */       if (pos == -1) {
/*      */         break;
/*      */       }
/* 1329 */       int diff = Math.abs(pos - mid);
/* 1330 */       if (diff < minDiff) {
/* 1331 */         minDiff = diff;
/* 1332 */         minPos = pos;
/*      */       }
/*      */     }
/* 1335 */     return minPos >= mid ? text.substring(0, minPos) : text.substring(minPos + 1);
/*      */   }
/*      */ 
/*      */   public static String[] getWrappedText(String text)
/*      */   {
/* 1347 */     String[] words = text.split(" ");
/* 1348 */     if (words.length <= 2) {
/* 1349 */       return words;
/*      */     }
/* 1351 */     if (words.length >= 3) {
/* 1352 */       int minDiff = text.length();
/* 1353 */       int minPos = -1;
/* 1354 */       int pos = -1;
/* 1355 */       int mid = text.length() / 2;
/*      */       while (true) {
/* 1357 */         pos = text.indexOf(' ', pos + 1);
/* 1358 */         if (pos == -1) {
/*      */           break;
/*      */         }
/* 1361 */         int diff = Math.abs(pos - mid);
/* 1362 */         if (diff < minDiff) {
/* 1363 */           minDiff = diff;
/* 1364 */           minPos = pos;
/*      */         }
/*      */       }
/* 1367 */       return new String[] { text.substring(0, minPos), text.substring(minPos + 1) };
/*      */     }
/*      */ 
/* 1370 */     return words;
/*      */   }
/*      */ 
/*      */   private static class Actions extends UIAction
/*      */   {
/*      */     private static final String PRESS = "pressed";
/*      */     private static final String RELEASE = "released";
/*      */     private static final String DOWN_PRESS = "downPressed";
/*      */     private static final String DOWN_RELEASE = "downReleased";
/*      */ 
/*      */     Actions(String name)
/*      */     {
/* 1159 */       super();
/*      */     }
/*      */ 
/*      */     public void actionPerformed(ActionEvent e) {
/* 1163 */       AbstractButton b = (AbstractButton)e.getSource();
/* 1164 */       String key = getName();
/*      */ 
/* 1167 */       if (("pressed".equals(key)) && (((JideSplitButton)b).isAlwaysDropdown())) {
/* 1168 */         key = "downPressed";
/*      */       }
/*      */ 
/* 1171 */       if ("pressed".equals(key)) {
/* 1172 */         ButtonModel model = b.getModel();
/* 1173 */         model.setArmed(true);
/* 1174 */         model.setPressed(true);
/* 1175 */         if (!b.hasFocus()) {
/* 1176 */           b.requestFocus();
/*      */         }
/*      */       }
/* 1179 */       else if ("released".equals(key)) {
/* 1180 */         ButtonModel model = b.getModel();
/* 1181 */         model.setPressed(false);
/* 1182 */         model.setArmed(false);
/*      */       }
/* 1184 */       else if ("downPressed".equals(key)) {
/* 1185 */         BasicJideSplitButtonUI.downButtonPressed((JMenu)b);
/*      */       }
/* 1187 */       else if (!"downReleased".equals(key));
/*      */     }
/*      */ 
/*      */     public boolean isEnabled(Object sender)
/*      */     {
/* 1193 */       return (sender == null) || (!(sender instanceof AbstractButton)) || (((AbstractButton)sender).getModel().isEnabled());
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
/*  672 */       cancelMenuIfNecessary(e);
/*      */     }
/*      */ 
/*      */     public void mousePressed(MouseEvent e)
/*      */     {
/*  682 */       JMenu menu = (JMenu)BasicJideSplitButtonUI.this.menuItem;
/*  683 */       if (!menu.isEnabled()) {
/*  684 */         return;
/*      */       }
/*  686 */       BasicJideSplitButtonUI.this.setMouseOver(true);
/*      */ 
/*  688 */       if (!SwingUtilities.isLeftMouseButton(e)) {
/*  689 */         return;
/*      */       }
/*  691 */       if (isClickOnButton(e, menu)) {
/*  692 */         if (((JideSplitButton)BasicJideSplitButtonUI.this.menuItem).isButtonEnabled())
/*      */         {
/*  694 */           menu.getModel().setArmed(true);
/*  695 */           menu.getModel().setPressed(true);
/*      */         }
/*  697 */         if ((!menu.hasFocus()) && (menu.isRequestFocusEnabled()))
/*  698 */           menu.requestFocus();
/*      */       }
/*      */       else
/*      */       {
/*  702 */         BasicJideSplitButtonUI.downButtonPressed(menu);
/*      */       }
/*      */     }
/*      */ 
/*      */     private boolean isClickOnButton(MouseEvent e, JMenu menu) {
/*  707 */       if (((JideSplitButton)menu).isAlwaysDropdown()) {
/*  708 */         return false;
/*      */       }
/*      */ 
/*  711 */       boolean clickOnDropDown = false;
/*  712 */       if (BasicJideButtonUI.shouldWrapText(BasicJideSplitButtonUI.this.menuItem)) {
/*  713 */         int size = 27;
/*  714 */         if (JideSwingUtilities.getOrientationOf(BasicJideSplitButtonUI.this.menuItem) == 0) {
/*  715 */           if (e.getPoint().getY() < menu.getHeight() - size) {
/*  716 */             clickOnDropDown = true;
/*      */           }
/*      */ 
/*      */         }
/*  720 */         else if (e.getPoint().getY() < menu.getHeight() - size) {
/*  721 */           clickOnDropDown = true;
/*      */         }
/*      */       }
/*      */       else
/*      */       {
/*  726 */         int size = ((JMenu)BasicJideSplitButtonUI.this.menuItem).isTopLevelMenu() ? BasicJideSplitButtonUI.this._splitButtonMargin : BasicJideSplitButtonUI.this._splitButtonMarginOnMenu;
/*  727 */         if (JideSwingUtilities.getOrientationOf(BasicJideSplitButtonUI.this.menuItem) == 0) {
/*  728 */           if (menu.getComponentOrientation().isLeftToRight()) {
/*  729 */             if (e.getPoint().getX() < menu.getWidth() - size) {
/*  730 */               clickOnDropDown = true;
/*      */             }
/*      */ 
/*      */           }
/*  734 */           else if (e.getPoint().getX() >= size) {
/*  735 */             clickOnDropDown = true;
/*      */           }
/*      */ 
/*      */         }
/*  740 */         else if (e.getPoint().getY() < menu.getHeight() - size) {
/*  741 */           clickOnDropDown = true;
/*      */         }
/*      */       }
/*      */ 
/*  745 */       return clickOnDropDown;
/*      */     }
/*      */ 
/*      */     public void mouseReleased(MouseEvent e)
/*      */     {
/*  754 */       JMenu menu = (JMenu)BasicJideSplitButtonUI.this.menuItem;
/*  755 */       if (!menu.isEnabled()) {
/*  756 */         return;
/*      */       }
/*  758 */       if (!isClickOnButton(e, menu))
/*      */       {
/*  760 */         BasicJideSplitButtonUI.this.menuItem.getModel().setArmed(false);
/*  761 */         BasicJideSplitButtonUI.this.menuItem.getModel().setPressed(false);
/*      */       }
/*  763 */       cancelMenuIfNecessary(e);
/*      */     }
/*      */ 
/*      */     private void cancelMenuIfNecessary(MouseEvent e) {
/*  767 */       JMenu menu = (JMenu)BasicJideSplitButtonUI.this.menuItem;
/*  768 */       if (!menu.isEnabled())
/*  769 */         return;
/*  770 */       if ((isClickOnButton(e, menu)) && 
/*  771 */         (((JideSplitButton)BasicJideSplitButtonUI.this.menuItem).isButtonEnabled()))
/*      */       {
/*  774 */         if (SwingUtilities.isLeftMouseButton(e)) {
/*  775 */           menu.getModel().setPressed(false);
/*  776 */           menu.getModel().setArmed(false);
/*      */         }
/*      */         else {
/*  779 */           menu.getModel().setArmed(false);
/*  780 */           menu.getModel().setPressed(false);
/*      */         }
/*      */ 
/*  783 */         MenuSelectionManager manager = MenuSelectionManager.defaultManager();
/*  784 */         MenuElement[] menuElements = manager.getSelectedPath();
/*  785 */         for (int i = menuElements.length - 1; i >= 0; i--) {
/*  786 */           MenuElement menuElement = menuElements[i];
/*  787 */           if (((menuElement instanceof JPopupMenu)) && (((JPopupMenu)menuElement).isAncestorOf(menu))) {
/*  788 */             menu.getModel().setRollover(false);
/*  789 */             BasicJideSplitButtonUI.this.setMouseOver(false);
/*  790 */             manager.clearSelectedPath();
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */ 
/*      */     public void mouseEntered(MouseEvent e)
/*      */     {
/*  812 */       JMenu menu = (JMenu)BasicJideSplitButtonUI.this.menuItem;
/*  813 */       if (!menu.isEnabled()) {
/*  814 */         return;
/*      */       }
/*  816 */       MenuSelectionManager manager = MenuSelectionManager.defaultManager();
/*      */ 
/*  818 */       MenuElement[] selectedPath = manager.getSelectedPath();
/*  819 */       if (!menu.isTopLevelMenu()) {
/*  820 */         if ((selectedPath.length <= 0) || (selectedPath[(selectedPath.length - 1)] != menu.getPopupMenu()))
/*      */         {
/*  823 */           if (menu.getDelay() == 0) {
/*  824 */             BasicJideSplitButtonUI.access$1600(BasicJideSplitButtonUI.this.getPath(), menu.getPopupMenu());
/*      */           }
/*      */           else {
/*  827 */             manager.setSelectedPath(BasicJideSplitButtonUI.this.getPath());
/*  828 */             BasicJideSplitButtonUI.access$1700(menu);
/*      */           }
/*      */         }
/*      */ 
/*      */       }
/*  833 */       else if ((selectedPath.length > 0) && (selectedPath[0] == menu.getParent()))
/*      */       {
/*  835 */         MenuElement[] newPath = new MenuElement[3];
/*      */ 
/*  838 */         newPath[0] = ((MenuElement)menu.getParent());
/*  839 */         newPath[1] = menu;
/*  840 */         newPath[2] = menu.getPopupMenu();
/*  841 */         manager.setSelectedPath(newPath);
/*      */       }
/*      */ 
/*  845 */       if (!SwingUtilities.isLeftMouseButton(e)) {
/*  846 */         BasicJideSplitButtonUI.this.setMouseOver(true);
/*      */       }
/*  848 */       BasicJideSplitButtonUI.this.menuItem.repaint();
/*      */     }
/*      */ 
/*      */     public void mouseExited(MouseEvent e) {
/*  852 */       BasicJideSplitButtonUI.this.setMouseOver(false);
/*  853 */       BasicJideSplitButtonUI.this.menuItem.repaint();
/*      */     }
/*      */ 
/*      */     public void mouseDragged(MouseEvent e)
/*      */     {
/*  864 */       JMenu menu = (JMenu)BasicJideSplitButtonUI.this.menuItem;
/*  865 */       if (!menu.isEnabled())
/*  866 */         return;
/*  867 */       MenuSelectionManager.defaultManager().processMouseEvent(e);
/*      */     }
/*      */ 
/*      */     public void mouseMoved(MouseEvent e) {
/*  871 */       JMenu menu = (JMenu)BasicJideSplitButtonUI.this.menuItem;
/*  872 */       if (!menu.isEnabled()) {
/*  873 */         return;
/*      */       }
/*  875 */       if ((BasicJideSplitButtonUI.this.menuItem instanceof JideSplitButton))
/*  876 */         if (isClickOnButton(e, (JMenu)BasicJideSplitButtonUI.this.menuItem)) {
/*  877 */           ((SplitButtonModel)((JideSplitButton)BasicJideSplitButtonUI.this.menuItem).getModel()).setButtonRollover(true);
/*      */         }
/*      */         else
/*  880 */           ((SplitButtonModel)((JideSplitButton)BasicJideSplitButtonUI.this.menuItem).getModel()).setButtonRollover(false);
/*      */     }
/*      */   }
/*      */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     com.jidesoft.plaf.basic.BasicJideSplitButtonUI
 * JD-Core Version:    0.6.0
 */