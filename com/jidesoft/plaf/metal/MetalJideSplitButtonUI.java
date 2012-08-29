/*      */ package com.jidesoft.plaf.metal;
/*      */ 
/*      */ import com.jidesoft.icons.IconsFactory;
/*      */ import com.jidesoft.plaf.UIDefaultsLookup;
/*      */ import com.jidesoft.plaf.basic.LazyActionMap;
/*      */ import com.jidesoft.plaf.basic.ThemePainter;
/*      */ import com.jidesoft.plaf.basic.UIAction;
/*      */ import com.jidesoft.swing.ComponentStateSupport;
/*      */ import com.jidesoft.swing.DefaultSplitButtonModel;
/*      */ import com.jidesoft.swing.JideSplitButton;
/*      */ import com.jidesoft.swing.JideSwingUtilities;
/*      */ import com.jidesoft.swing.SplitButtonModel;
/*      */ import com.jidesoft.utils.SecurityUtils;
/*      */ import java.awt.Color;
/*      */ import java.awt.Component;
/*      */ import java.awt.ComponentOrientation;
/*      */ import java.awt.Container;
/*      */ import java.awt.Dimension;
/*      */ import java.awt.FontMetrics;
/*      */ import java.awt.Graphics;
/*      */ import java.awt.Graphics2D;
/*      */ import java.awt.Point;
/*      */ import java.awt.Rectangle;
/*      */ import java.awt.event.ActionEvent;
/*      */ import java.awt.event.FocusEvent;
/*      */ import java.awt.event.FocusListener;
/*      */ import java.awt.event.MouseEvent;
/*      */ import java.beans.PropertyChangeEvent;
/*      */ import java.beans.PropertyChangeListener;
/*      */ import java.io.PrintStream;
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
/*      */ import javax.swing.MenuElement;
/*      */ import javax.swing.MenuSelectionManager;
/*      */ import javax.swing.SwingUtilities;
/*      */ import javax.swing.event.MouseInputListener;
/*      */ import javax.swing.plaf.ComponentUI;
/*      */ import javax.swing.plaf.UIResource;
/*      */ import javax.swing.plaf.basic.BasicGraphicsUtils;
/*      */ import javax.swing.text.View;
/*      */ 
/*      */ public class MetalJideSplitButtonUI extends MetalMenuUI
/*      */ {
/*      */   protected ThemePainter _painter;
/*      */   protected Color _shadowColor;
/*      */   protected Color _darkShadowColor;
/*      */   protected Color _highlight;
/*      */   protected Color _lightHighlightColor;
/*      */   private int _splitButtonMargin;
/*      */   private int _splitButtonMarginOnMenu;
/*      */   protected PropertyChangeListener _propertyChangeListener;
/*      */   private FocusListener _focusListener;
/*      */ 
/*      */   public MetalJideSplitButtonUI()
/*      */   {
/*   45 */     this._splitButtonMargin = 12;
/*   46 */     this._splitButtonMarginOnMenu = 18;
/*      */   }
/*      */ 
/*      */   protected String getPropertyPrefix()
/*      */   {
/*   53 */     return "JideSplitButton";
/*      */   }
/*      */ 
/*      */   public static ComponentUI createUI(JComponent c)
/*      */   {
/*   58 */     return new MetalJideSplitButtonUI();
/*      */   }
/*      */ 
/*      */   protected void installDefaults()
/*      */   {
/*   63 */     this._painter = ((ThemePainter)UIDefaultsLookup.get("Theme.painter"));
/*      */ 
/*   65 */     this._shadowColor = UIDefaultsLookup.getColor("controlShadow");
/*   66 */     this._darkShadowColor = UIDefaultsLookup.getColor("controlDkShadow");
/*   67 */     this._highlight = UIDefaultsLookup.getColor("controlHighlight");
/*   68 */     this._lightHighlightColor = UIDefaultsLookup.getColor("controlLtHighlight");
/*   69 */     this.menuItem.setRolloverEnabled(true);
/*      */ 
/*   71 */     super.installDefaults();
/*      */   }
/*      */ 
/*      */   protected void uninstallDefaults()
/*      */   {
/*   76 */     this._painter = null;
/*      */ 
/*   78 */     this._shadowColor = null;
/*   79 */     this._highlight = null;
/*   80 */     this._lightHighlightColor = null;
/*   81 */     this._darkShadowColor = null;
/*      */ 
/*   83 */     super.uninstallDefaults();
/*      */   }
/*      */ 
/*      */   protected void installListeners()
/*      */   {
/*   88 */     if (this._propertyChangeListener == null) {
/*   89 */       this._propertyChangeListener = createSplitButtonPropertyChangeListener(this.menuItem);
/*      */     }
/*   91 */     if (this._propertyChangeListener != null)
/*   92 */       this.menuItem.addPropertyChangeListener(this._propertyChangeListener);
/*   93 */     super.installListeners();
/*   94 */     if (this._focusListener == null)
/*   95 */       this._focusListener = new FocusListener() {
/*      */         public void focusGained(FocusEvent e) {
/*   97 */           MetalJideSplitButtonUI.this.menuItem.repaint();
/*      */         }
/*      */ 
/*      */         public void focusLost(FocusEvent e) {
/*  101 */           MetalJideSplitButtonUI.this.menuItem.repaint();
/*      */         }
/*      */       };
/*  105 */     this.menuItem.addFocusListener(this._focusListener);
/*      */   }
/*      */ 
/*      */   protected void uninstallListeners()
/*      */   {
/*  110 */     super.uninstallListeners();
/*      */ 
/*  112 */     if (this._propertyChangeListener != null)
/*  113 */       this.menuItem.removePropertyChangeListener(this._propertyChangeListener);
/*  114 */     this._propertyChangeListener = null;
/*      */ 
/*  116 */     if (this._focusListener != null) {
/*  117 */       this.menuItem.removeFocusListener(this._focusListener);
/*      */     }
/*  119 */     this._focusListener = null;
/*      */   }
/*      */ 
/*      */   protected PropertyChangeListener createSplitButtonPropertyChangeListener(JComponent c)
/*      */   {
/*  124 */     return new PropertyChangeHandler(null);
/*      */   }
/*      */ 
/*      */   static Object getUIOfType(ComponentUI ui, Class klass)
/*      */   {
/*  132 */     if (klass.isInstance(ui)) {
/*  133 */       return ui;
/*      */     }
/*  135 */     return null;
/*      */   }
/*      */ 
/*      */   public InputMap getInputMap(int condition, JComponent c)
/*      */   {
/*  147 */     if (condition == 0) {
/*  148 */       MetalJideSplitButtonUI ui = (MetalJideSplitButtonUI)getUIOfType(((JideSplitButton)c).getUI(), MetalJideSplitButtonUI.class);
/*      */ 
/*  150 */       if (ui != null) {
/*  151 */         return (InputMap)UIDefaultsLookup.get(ui.getPropertyPrefix() + ".focusInputMap");
/*      */       }
/*      */     }
/*  154 */     return null;
/*      */   }
/*      */ 
/*      */   protected void installKeyboardActions()
/*      */   {
/*  159 */     super.installKeyboardActions();
/*  160 */     AbstractButton b = this.menuItem;
/*      */ 
/*  162 */     LazyActionMap.installLazyActionMap(b, MetalJideSplitButtonUI.class, "JideSplitButton.actionMap");
/*      */ 
/*  165 */     InputMap km = getInputMap(0, b);
/*      */ 
/*  167 */     SwingUtilities.replaceUIInputMap(b, 0, km);
/*      */   }
/*      */ 
/*      */   protected void uninstallKeyboardActions()
/*      */   {
/*  172 */     AbstractButton b = this.menuItem;
/*  173 */     SwingUtilities.replaceUIInputMap(b, 2, null);
/*      */ 
/*  175 */     SwingUtilities.replaceUIInputMap(b, 0, null);
/*  176 */     SwingUtilities.replaceUIActionMap(b, null);
/*  177 */     super.uninstallKeyboardActions();
/*      */   }
/*      */ 
/*      */   protected MouseInputListener createMouseInputListener(JComponent c)
/*      */   {
/*  182 */     return new MouseInputHandler();
/*      */   }
/*      */ 
/*      */   protected void paintBackground(Graphics g, JMenuItem menuItem, Color bgColor)
/*      */   {
/*  187 */     ButtonModel model = menuItem.getModel();
/*      */ 
/*  190 */     int orientation = JideSwingUtilities.getOrientationOf(menuItem);
/*      */     int menuHeight;
/*      */     int menuWidth;
/*      */     int menuHeight;
/*  191 */     if (orientation == 0) {
/*  192 */       int menuWidth = menuItem.getWidth();
/*  193 */       menuHeight = menuItem.getHeight();
/*      */     }
/*      */     else {
/*  196 */       menuWidth = menuItem.getHeight();
/*  197 */       menuHeight = menuItem.getWidth();
/*      */     }
/*      */ 
/*  200 */     orientation = 0;
/*      */ 
/*  203 */     Object o = menuItem.getClientProperty("JideSplitButton.alwaysPaintBackground");
/*      */     boolean paintBackground;
/*      */     boolean paintBackground;
/*  204 */     if ((o instanceof Boolean)) {
/*  205 */       paintBackground = ((Boolean)o).booleanValue();
/*      */     }
/*      */     else {
/*  208 */       paintBackground = menuItem.isOpaque();
/*      */     }
/*  210 */     JideSplitButton b = (JideSplitButton)menuItem;
/*  211 */     if (model.isRollover()) {
/*  212 */       System.out.println("b.isRolloverEnabled():" + b.isRolloverEnabled());
/*  213 */       System.out.println("b.hasFocus():" + b.hasFocus());
/*  214 */       System.out.println("model.isEnabled():" + model.isEnabled());
/*  215 */       System.out.println("isMouseOver():" + isMouseOver());
/*      */     }
/*      */ 
/*  218 */     if (!((JMenu)menuItem).isTopLevelMenu()) {
/*  219 */       super.paintBackground(g, menuItem, bgColor);
/*  220 */       if (menuItem.isEnabled()) {
/*  221 */         if ((model.isArmed()) || (model.isPressed()) || (isMouseOver())) {
/*  222 */           g.setColor(this.selectionForeground);
/*  223 */           g.drawLine(menuWidth - this._splitButtonMarginOnMenu, 0, menuWidth - this._splitButtonMarginOnMenu, menuHeight - 2);
/*  224 */           JideSwingUtilities.paintArrow(g, this.selectionForeground, menuWidth - this._splitButtonMarginOnMenu / 2 - 2, menuHeight / 2 - 3, 7, 1);
/*      */         }
/*      */         else {
/*  227 */           g.setColor(getForegroundOfState(menuItem));
/*  228 */           g.drawLine(menuWidth - this._splitButtonMarginOnMenu, 0, menuWidth - this._splitButtonMarginOnMenu, menuHeight - 2);
/*  229 */           JideSwingUtilities.paintArrow(g, getForegroundOfState(menuItem), menuWidth - this._splitButtonMarginOnMenu / 2 - 2, menuHeight / 2 - 3, 7, 1);
/*      */         }
/*      */       }
/*      */       else {
/*  233 */         g.setColor(UIDefaultsLookup.getColor("controlDkShadow"));
/*  234 */         g.drawLine(menuWidth - this._splitButtonMarginOnMenu, 0, menuWidth - this._splitButtonMarginOnMenu, menuHeight - 2);
/*  235 */         JideSwingUtilities.paintArrow(g, UIDefaultsLookup.getColor("controlDkShadow"), menuWidth - this._splitButtonMarginOnMenu / 2 - 2, menuHeight / 2 - 3, 7, 1);
/*      */       }
/*  237 */       return;
/*      */     }
/*      */ 
/*  240 */     if (paintBackground) {
/*  241 */       if (menuItem.getParent() != null) {
/*  242 */         g.setColor(menuItem.getParent().getBackground());
/*      */       }
/*      */       else {
/*  245 */         g.setColor(menuItem.getBackground());
/*      */       }
/*  247 */       g.fillRect(0, 0, menuWidth, menuHeight);
/*      */     }
/*      */ 
/*  250 */     if (b.getButtonStyle() == 0) {
/*  251 */       Object segmentPosition = b.getClientProperty("JButton.segmentPosition");
/*  252 */       if (model.isSelected()) {
/*  253 */         if (isAlwaysDropdown(b)) {
/*  254 */           Rectangle rect = new Rectangle(0, 0, menuWidth, menuHeight);
/*  255 */           getPainter().paintButtonBackground(b, g, rect, orientation, 2);
/*      */         }
/*  258 */         else if (segmentPosition != null) {
/*  259 */           Rectangle rect = getButtonRect(b, orientation, menuWidth, menuHeight);
/*  260 */           if (b.isButtonEnabled()) {
/*  261 */             getPainter().paintButtonBackground(b, g, rect, orientation, 2);
/*      */           }
/*  263 */           else if (paintBackground) {
/*  264 */             getPainter().paintButtonBackground(b, g, rect, orientation, 6);
/*      */           }
/*  266 */           rect = getDropDownRect(b, orientation, menuWidth, menuHeight);
/*  267 */           getPainter().paintButtonBackground(b, g, rect, orientation, 1);
/*      */         }
/*      */         else {
/*  270 */           Rectangle rect = getButtonRect(b, orientation, menuWidth, menuHeight);
/*  271 */           if (b.isButtonEnabled()) {
/*  272 */             getPainter().paintButtonBackground(b, g, rect, orientation, 3);
/*      */           }
/*  274 */           else if (paintBackground) {
/*  275 */             getPainter().paintButtonBackground(b, g, rect, orientation, 5);
/*      */           }
/*  277 */           rect = getDropDownRect(b, orientation, menuWidth, menuHeight);
/*  278 */           getPainter().paintButtonBackground(b, g, rect, orientation, 3);
/*  279 */           getPainter().paintSelectedMenu(b, g, new Rectangle(0, 0, menuWidth, menuHeight), orientation, 3);
/*      */         }
/*      */ 
/*      */       }
/*  283 */       else if ((model.isArmed()) || (model.isPressed())) {
/*  284 */         Rectangle rect = getButtonRect(b, orientation, menuWidth, menuHeight);
/*  285 */         if (b.isButtonEnabled()) {
/*  286 */           getPainter().paintButtonBackground(b, g, rect, orientation, 1);
/*      */         }
/*  288 */         else if (paintBackground) {
/*  289 */           getPainter().paintButtonBackground(b, g, rect, orientation, 4);
/*      */         }
/*  291 */         rect = getDropDownRect(b, orientation, menuWidth, menuHeight);
/*  292 */         getPainter().paintButtonBackground(b, g, rect, orientation, 2);
/*      */       }
/*  294 */       else if (((model instanceof SplitButtonModel)) && (((DefaultSplitButtonModel)model).isButtonSelected())) {
/*  295 */         if (((isMouseOver()) || (b.hasFocus())) && (model.isEnabled())) {
/*  296 */           Rectangle rect = getDropDownRect(b, orientation, menuWidth, menuHeight);
/*  297 */           getPainter().paintButtonBackground(b, g, rect, orientation, 2);
/*  298 */           rect = getButtonRect(b, orientation, menuWidth, menuHeight);
/*  299 */           if (b.isButtonEnabled()) {
/*  300 */             getPainter().paintButtonBackground(b, g, rect, orientation, 1);
/*      */           }
/*  302 */           else if (paintBackground)
/*  303 */             getPainter().paintButtonBackground(b, g, rect, orientation, 4);
/*      */         }
/*      */         else
/*      */         {
/*  307 */           Rectangle rect = getDropDownRect(b, orientation, menuWidth, menuHeight);
/*  308 */           getPainter().paintButtonBackground(b, g, rect, orientation, 0);
/*  309 */           rect = getButtonRect(b, orientation, menuWidth, menuHeight);
/*  310 */           if (b.isButtonEnabled()) {
/*  311 */             getPainter().paintButtonBackground(b, g, rect, orientation, 3);
/*      */           }
/*  313 */           else if (paintBackground) {
/*  314 */             getPainter().paintButtonBackground(b, g, rect, orientation, 5);
/*      */           }
/*      */         }
/*      */       }
/*  318 */       else if (((b.isRolloverEnabled()) && (isMouseOver())) || ((b.hasFocus()) && (model.isEnabled()))) {
/*  319 */         if (isAlwaysDropdown(b)) {
/*  320 */           Rectangle rect = new Rectangle(0, 0, menuWidth, menuHeight);
/*  321 */           getPainter().paintButtonBackground(b, g, rect, orientation, 2);
/*      */         }
/*      */         else
/*      */         {
/*  325 */           Rectangle rect = getButtonRect(b, orientation, menuWidth, menuHeight);
/*  326 */           if (b.isButtonEnabled()) {
/*  327 */             getPainter().paintButtonBackground(b, g, rect, orientation, 2);
/*      */           }
/*  329 */           else if (paintBackground) {
/*  330 */             getPainter().paintButtonBackground(b, g, rect, orientation, 6);
/*      */           }
/*  332 */           rect = getDropDownRect(b, orientation, menuWidth, menuHeight);
/*  333 */           getPainter().paintButtonBackground(b, g, rect, orientation, 2);
/*      */         }
/*      */ 
/*      */       }
/*  337 */       else if (paintBackground) {
/*  338 */         Rectangle rect = getButtonRect(b, orientation, menuWidth, menuHeight);
/*  339 */         if ((b.isEnabled()) && (b.isButtonEnabled())) {
/*  340 */           getPainter().paintButtonBackground(b, g, rect, 0, 0);
/*      */         }
/*      */         else {
/*  343 */           getPainter().paintButtonBackground(b, g, rect, 0, 4);
/*      */         }
/*  345 */         if ("true".equals(SecurityUtils.getProperty("shadingtheme", "false"))) {
/*  346 */           JideSwingUtilities.fillGradient(g, rect, 0);
/*      */         }
/*  348 */         rect = getDropDownRect(b, orientation, menuWidth, menuHeight);
/*  349 */         if (b.isEnabled()) {
/*  350 */           getPainter().paintButtonBackground(b, g, rect, 0, 0);
/*      */         }
/*      */         else {
/*  353 */           getPainter().paintButtonBackground(b, g, rect, 0, 4);
/*      */         }
/*  355 */         if ("true".equals(SecurityUtils.getProperty("shadingtheme", "false"))) {
/*  356 */           JideSwingUtilities.fillGradient(g, rect, 0);
/*      */         }
/*      */       }
/*      */ 
/*      */     }
/*  361 */     else if (b.getButtonStyle() == 2) {
/*  362 */       if (model.isSelected())
/*      */       {
/*  364 */         getPainter().paintSelectedMenu(b, g, new Rectangle(0, 0, menuWidth, menuHeight), orientation, 3);
/*      */       }
/*  366 */       else if ((model.isArmed()) || (model.isPressed())) {
/*  367 */         Rectangle rect = getButtonRect(b, orientation, menuWidth, menuHeight);
/*  368 */         if (b.isButtonEnabled()) {
/*  369 */           JideSwingUtilities.paintBackground(g, rect, this._highlight, this._highlight);
/*      */         }
/*  371 */         rect = getDropDownRect(b, orientation, menuWidth, menuHeight);
/*  372 */         JideSwingUtilities.paintBackground(g, rect, this._highlight, this._highlight);
/*      */ 
/*  374 */         if (!b.isOpaque()) {
/*  375 */           rect = getButtonRect(b, orientation, menuWidth, menuHeight);
/*  376 */           paintSunkenBorder(g, rect);
/*  377 */           rect = getDropDownRect(b, orientation, menuWidth, menuHeight);
/*  378 */           paintRaisedBorder(g, rect);
/*      */         }
/*      */       }
/*  381 */       else if (((model instanceof SplitButtonModel)) && (((DefaultSplitButtonModel)model).isButtonSelected())) {
/*  382 */         if (((isMouseOver()) || (b.hasFocus())) && (model.isEnabled())) {
/*  383 */           Rectangle rect = getDropDownRect(b, orientation, menuWidth, menuHeight);
/*  384 */           JideSwingUtilities.paintBackground(g, rect, this._highlight, this._highlight);
/*  385 */           rect = getButtonRect(b, orientation, menuWidth, menuHeight);
/*  386 */           if (b.isButtonEnabled()) {
/*  387 */             JideSwingUtilities.paintBackground(g, rect, this._highlight, this._highlight);
/*      */           }
/*  389 */           if (!b.isOpaque()) {
/*  390 */             rect = getButtonRect(b, orientation, menuWidth, menuHeight);
/*  391 */             paintSunkenBorder(g, rect);
/*  392 */             rect = getDropDownRect(b, orientation, menuWidth, menuHeight);
/*  393 */             paintRaisedBorder(g, rect);
/*      */           }
/*      */         }
/*      */         else
/*      */         {
/*  398 */           if (b.isOpaque()) {
/*  399 */             Rectangle rect = getDropDownRect(b, orientation, menuWidth, menuHeight);
/*  400 */             JideSwingUtilities.paintBackground(g, rect, this._highlight, this._highlight);
/*      */           }
/*  402 */           Rectangle rect = getButtonRect(b, orientation, menuWidth, menuHeight);
/*  403 */           JideSwingUtilities.paintBackground(g, rect, this._highlight, this._highlight);
/*      */ 
/*  405 */           if (!b.isOpaque()) {
/*  406 */             rect = getButtonRect(b, orientation, menuWidth, menuHeight);
/*  407 */             paintSunkenBorder(g, rect);
/*      */           }
/*      */ 
/*      */         }
/*      */ 
/*      */       }
/*  415 */       else if (((b.isRolloverEnabled()) && (isMouseOver())) || ((b.hasFocus()) && (model.isEnabled())))
/*      */       {
/*  417 */         Rectangle rect = getButtonRect(b, orientation, menuWidth, menuHeight);
/*  418 */         if (b.isButtonEnabled()) {
/*  419 */           JideSwingUtilities.paintBackground(g, rect, this._highlight, this._highlight);
/*      */         }
/*  421 */         rect = getDropDownRect(b, orientation, menuWidth, menuHeight);
/*  422 */         JideSwingUtilities.paintBackground(g, rect, this._highlight, this._highlight);
/*      */ 
/*  424 */         if (isAlwaysDropdown(b)) {
/*  425 */           rect = new Rectangle(0, 0, menuWidth, menuHeight);
/*  426 */           paintRaisedBorder(g, rect);
/*      */         }
/*      */         else {
/*  429 */           rect = getButtonRect(b, orientation, menuWidth, menuHeight);
/*  430 */           paintRaisedBorder(g, rect);
/*  431 */           rect = getDropDownRect(b, orientation, menuWidth, menuHeight);
/*  432 */           paintRaisedBorder(g, rect);
/*      */         }
/*      */ 
/*      */       }
/*  436 */       else if (b.isOpaque()) {
/*  437 */         Rectangle rect = getButtonRect(b, orientation, menuWidth, menuHeight);
/*  438 */         if (b.isButtonEnabled()) {
/*  439 */           getPainter().paintButtonBackground(b, g, rect, orientation, 0);
/*      */         }
/*  441 */         rect = getDropDownRect(b, orientation, menuWidth, menuHeight);
/*  442 */         getPainter().paintButtonBackground(b, g, rect, orientation, 0);
/*      */       }
/*      */ 
/*      */     }
/*  447 */     else if (b.getButtonStyle() == 1) {
/*  448 */       if (model.isSelected())
/*      */       {
/*  450 */         getPainter().paintSelectedMenu(b, g, new Rectangle(0, 0, menuWidth, menuHeight), orientation, 3);
/*      */       }
/*  452 */       else if ((model.isArmed()) || (model.isPressed())) {
/*  453 */         Rectangle rect = getButtonRect(b, orientation, menuWidth, menuHeight);
/*  454 */         if (b.isButtonEnabled()) {
/*  455 */           getPainter().paintButtonBackground(b, g, rect, orientation, 1);
/*      */         }
/*  457 */         rect = getDropDownRect(b, orientation, menuWidth, menuHeight);
/*  458 */         getPainter().paintButtonBackground(b, g, rect, orientation, 2);
/*      */ 
/*  460 */         if (!b.isOpaque()) {
/*  461 */           rect = getButtonRect(b, orientation, menuWidth, menuHeight);
/*  462 */           paintSunken2Border(g, rect);
/*  463 */           rect = getDropDownRect(b, orientation, menuWidth, menuHeight);
/*  464 */           paintRaisedBorder(g, rect);
/*      */         }
/*      */       }
/*  467 */       else if (((model instanceof SplitButtonModel)) && (((DefaultSplitButtonModel)model).isButtonSelected())) {
/*  468 */         if ((isMouseOver()) && (model.isEnabled())) {
/*  469 */           Rectangle rect = getDropDownRect(b, orientation, menuWidth, menuHeight);
/*  470 */           getPainter().paintButtonBackground(b, g, rect, orientation, 2);
/*  471 */           rect = getButtonRect(b, orientation, menuWidth, menuHeight);
/*  472 */           if (b.isButtonEnabled()) {
/*  473 */             getPainter().paintButtonBackground(b, g, rect, orientation, 1);
/*      */           }
/*  475 */           if (!b.isOpaque()) {
/*  476 */             rect = getButtonRect(b, orientation, menuWidth, menuHeight);
/*  477 */             paintSunken2Border(g, rect);
/*  478 */             rect = getDropDownRect(b, orientation, menuWidth, menuHeight);
/*  479 */             paintRaisedBorder(g, rect);
/*      */           }
/*      */         }
/*      */         else
/*      */         {
/*  484 */           if (b.isOpaque()) {
/*  485 */             Rectangle rect = getDropDownRect(b, orientation, menuWidth, menuHeight);
/*  486 */             getPainter().paintButtonBackground(b, g, rect, orientation, 0);
/*      */           }
/*  488 */           Rectangle rect = getButtonRect(b, orientation, menuWidth, menuHeight);
/*  489 */           getPainter().paintButtonBackground(b, g, rect, orientation, 3);
/*      */ 
/*  491 */           if (!b.isOpaque()) {
/*  492 */             rect = getButtonRect(b, orientation, menuWidth, menuHeight);
/*  493 */             paintSunken2Border(g, rect);
/*  494 */             rect = getDropDownRect(b, orientation, menuWidth, menuHeight);
/*  495 */             paintRaisedBorder(g, rect);
/*      */           }
/*      */ 
/*      */         }
/*      */ 
/*      */       }
/*  501 */       else if ((b.isRolloverEnabled()) && (isMouseOver()) && (model.isEnabled()))
/*      */       {
/*  503 */         if (isAlwaysDropdown(b)) {
/*  504 */           Rectangle rect = new Rectangle(0, 0, menuWidth, menuHeight);
/*  505 */           getPainter().paintButtonBackground(b, g, rect, orientation, 2);
/*  506 */           paintRaised2Border(g, rect);
/*      */         }
/*      */         else {
/*  509 */           Rectangle rect = getButtonRect(b, orientation, menuWidth, menuHeight);
/*  510 */           if (b.isButtonEnabled()) {
/*  511 */             getPainter().paintButtonBackground(b, g, rect, orientation, 2);
/*      */           }
/*  513 */           rect = getDropDownRect(b, orientation, menuWidth, menuHeight);
/*  514 */           getPainter().paintButtonBackground(b, g, rect, orientation, 2);
/*  515 */           rect = getButtonRect(b, orientation, menuWidth, menuHeight);
/*  516 */           paintRaised2Border(g, rect);
/*  517 */           rect = getDropDownRect(b, orientation, menuWidth, menuHeight);
/*  518 */           paintRaised2Border(g, rect);
/*      */         }
/*      */ 
/*      */       }
/*  522 */       else if (b.isOpaque()) {
/*  523 */         Rectangle rect = getButtonRect(b, orientation, menuWidth, menuHeight);
/*  524 */         if (b.isButtonEnabled()) {
/*  525 */           getPainter().paintButtonBackground(b, g, rect, orientation, 0);
/*      */         }
/*  527 */         rect = getDropDownRect(b, orientation, menuWidth, menuHeight);
/*  528 */         getPainter().paintButtonBackground(b, g, rect, orientation, 0);
/*      */       }
/*  531 */       else if (isAlwaysDropdown(b)) {
/*  532 */         Rectangle rect = new Rectangle(0, 0, menuWidth, menuHeight);
/*  533 */         paintRaisedBorder(g, rect);
/*      */       }
/*      */       else {
/*  536 */         Rectangle rect = getButtonRect(b, orientation, menuWidth, menuHeight);
/*  537 */         paintRaisedBorder(g, rect);
/*  538 */         rect = getDropDownRect(b, orientation, menuWidth, menuHeight);
/*  539 */         paintRaisedBorder(g, rect);
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/*  546 */     menuItem.putClientProperty("JButton.segmentPosition", null);
/*      */ 
/*  548 */     paintArrow(menuItem, g);
/*      */   }
/*      */ 
/*      */   protected Rectangle getDropDownRect(JComponent c, int orientation, int width, int height)
/*      */   {
/*  561 */     Object position = c.getClientProperty("JButton.segmentPosition");
/*      */     Rectangle rect;
/*      */     Rectangle rect;
/*  563 */     if (c.getComponentOrientation().isLeftToRight()) {
/*  564 */       rect = new Rectangle(width - this._splitButtonMargin - 1 + getOffset(), 0, this._splitButtonMargin - getOffset(), height);
/*      */     }
/*      */     else {
/*  567 */       rect = new Rectangle(0, 0, this._splitButtonMargin - getOffset(), height);
/*      */     }
/*  569 */     if ((position != null) && (!"only".equals(position)))
/*      */     {
/*  571 */       if ("first".equals(position)) {
/*  572 */         if (orientation == 0) {
/*  573 */           rect.width += 1;
/*      */         }
/*      */         else {
/*  576 */           rect.height += 1;
/*      */         }
/*      */       }
/*  579 */       else if ("middle".equals(position)) {
/*  580 */         if (orientation == 0) {
/*  581 */           rect.width += 1;
/*      */         }
/*      */         else {
/*  584 */           rect.height += 1;
/*      */         }
/*  587 */       } else if (!"last".equals(position));
/*      */     }
/*  589 */     c.putClientProperty("JButton.segmentPosition", "last");
/*  590 */     return rect;
/*      */   }
/*      */ 
/*      */   protected void paintArrow(JMenuItem menuItem, Graphics g)
/*      */   {
/*  596 */     int orientation = JideSwingUtilities.getOrientationOf(menuItem);
/*      */     int menuHeight;
/*      */     int menuWidth;
/*      */     int menuHeight;
/*  597 */     if (orientation == 0) {
/*  598 */       int menuWidth = menuItem.getWidth();
/*  599 */       menuHeight = menuItem.getHeight();
/*      */     }
/*      */     else {
/*  602 */       menuWidth = menuItem.getHeight();
/*  603 */       menuHeight = menuItem.getWidth();
/*      */     }
/*      */     int startX;
/*      */     int startX;
/*  606 */     if (menuItem.getComponentOrientation().isLeftToRight()) {
/*  607 */       startX = menuWidth - 9;
/*      */     }
/*      */     else {
/*  610 */       startX = 4;
/*      */     }
/*  612 */     if (menuItem.isEnabled()) {
/*  613 */       JideSwingUtilities.paintArrow(g, getForegroundOfState(menuItem), startX, menuHeight / 2 - 1, 5, 0);
/*      */     }
/*      */     else
/*  616 */       JideSwingUtilities.paintArrow(g, UIDefaultsLookup.getColor("controlShadow"), startX, menuHeight / 2 - 1, 5, 0);
/*      */   }
/*      */ 
/*      */   protected Rectangle getButtonRect(JComponent c, int orientation, int width, int height)
/*      */   {
/*      */     Rectangle rect;
/*      */     Rectangle rect;
/*  631 */     if ((orientation == 0) && (c.getComponentOrientation().isLeftToRight())) {
/*  632 */       rect = new Rectangle(0, 0, width - this._splitButtonMargin, height);
/*      */     }
/*      */     else {
/*  635 */       rect = new Rectangle(this._splitButtonMargin - 1, 0, width - this._splitButtonMargin, height);
/*      */     }
/*  637 */     c.putClientProperty("JButton.segmentPosition", "first");
/*  638 */     return rect;
/*      */   }
/*      */ 
/*      */   private void paintSunkenBorder(Graphics g, Rectangle b) {
/*  642 */     Color old = g.getColor();
/*  643 */     g.setColor(this._shadowColor);
/*  644 */     g.drawLine(b.x, b.y, b.x + b.width - 1, b.y);
/*  645 */     g.drawLine(b.x, b.y, b.x, b.y + b.height - 1);
/*      */ 
/*  647 */     g.setColor(this._lightHighlightColor);
/*  648 */     g.drawLine(b.x, b.y + b.height - 1, b.x + b.width - 1, b.y + b.height - 1);
/*  649 */     g.drawLine(b.x + b.width - 1, b.y, b.x + b.width - 1, b.y + b.height - 1);
/*  650 */     g.setColor(old);
/*      */   }
/*      */ 
/*      */   private void paintSunken2Border(Graphics g, Rectangle b) {
/*  654 */     Color old = g.getColor();
/*  655 */     g.setColor(this._darkShadowColor);
/*  656 */     g.drawLine(b.x, b.y, b.x + b.width - 2, b.y);
/*  657 */     g.drawLine(b.x, b.y, b.x, b.y + b.height - 2);
/*      */ 
/*  659 */     g.setColor(this._shadowColor);
/*  660 */     g.drawLine(b.x + 1, b.y + 1, b.x + b.width - 3, b.y + 1);
/*  661 */     g.drawLine(b.x + 1, b.y + 1, b.x + 1, b.y + b.height - 3);
/*      */ 
/*  663 */     g.setColor(this._lightHighlightColor);
/*  664 */     g.drawLine(b.x, b.y + b.height - 1, b.x + b.width - 1, b.y + b.height - 1);
/*  665 */     g.drawLine(b.x + b.width - 1, b.x, b.x + b.width - 1, b.y + b.height - 1);
/*  666 */     g.setColor(old);
/*      */   }
/*      */ 
/*      */   private void paintRaised2Border(Graphics g, Rectangle b) {
/*  670 */     Color old = g.getColor();
/*  671 */     g.setColor(this._lightHighlightColor);
/*  672 */     g.drawLine(b.x, b.y, b.x + b.width - 1, b.y);
/*  673 */     g.drawLine(b.x, b.y, b.x, b.y + b.height - 1);
/*      */ 
/*  675 */     g.setColor(this._shadowColor);
/*  676 */     g.drawLine(b.x + 1, b.y + b.height - 2, b.x + b.width - 2, b.y + b.height - 2);
/*  677 */     g.drawLine(b.x + b.width - 2, 1, b.x + b.width - 2, b.y + b.height - 2);
/*      */ 
/*  679 */     g.setColor(this._darkShadowColor);
/*  680 */     g.drawLine(b.x, b.y + b.height - 1, b.x + b.width - 1, b.y + b.height - 1);
/*  681 */     g.drawLine(b.x + b.width - 1, b.y, b.x + b.width - 1, b.y + b.height - 1);
/*  682 */     g.setColor(old);
/*      */   }
/*      */ 
/*      */   private void paintRaisedBorder(Graphics g, Rectangle b) {
/*  686 */     Color old = g.getColor();
/*  687 */     g.setColor(this._lightHighlightColor);
/*  688 */     g.drawLine(b.x, b.y, b.x + b.width - 1, b.y);
/*  689 */     g.drawLine(b.x, b.y, b.x, b.y + b.height - 1);
/*      */ 
/*  691 */     g.setColor(this._shadowColor);
/*  692 */     g.drawLine(b.x, b.y + b.height - 1, b.x + b.width - 1, b.y + b.height - 1);
/*  693 */     g.drawLine(b.x + b.width - 1, b.y, b.x + b.width - 1, b.y + b.height - 1);
/*  694 */     g.setColor(old);
/*      */   }
/*      */ 
/*      */   protected void paintText(Graphics g, JMenuItem menuItem, Rectangle textRect, String text)
/*      */   {
/*  699 */     ButtonModel model = menuItem.getModel();
/*  700 */     FontMetrics fm = g.getFontMetrics();
/*  701 */     int mnemIndex = menuItem.getDisplayedMnemonicIndex();
/*      */ 
/*  703 */     if ((!model.isEnabled()) || (!(menuItem instanceof JideSplitButton)) || (!((JideSplitButton)menuItem).isButtonEnabled()))
/*      */     {
/*  705 */       if ((UIDefaultsLookup.get("MenuItem.disabledForeground") instanceof Color)) {
/*  706 */         g.setColor(UIDefaultsLookup.getColor("MenuItem.disabledForeground"));
/*  707 */         JideSwingUtilities.drawStringUnderlineCharAt(menuItem, g, text, mnemIndex, textRect.x, textRect.y + fm.getAscent());
/*      */       }
/*      */       else
/*      */       {
/*  712 */         g.setColor(menuItem.getBackground().brighter());
/*  713 */         JideSwingUtilities.drawStringUnderlineCharAt(menuItem, g, text, mnemIndex, textRect.x, textRect.y + fm.getAscent());
/*      */ 
/*  716 */         g.setColor(menuItem.getBackground().darker());
/*  717 */         JideSwingUtilities.drawStringUnderlineCharAt(menuItem, g, text, mnemIndex, textRect.x - 1, textRect.y + fm.getAscent() - 1);
/*      */       }
/*      */ 
/*      */     }
/*      */     else
/*      */     {
/*  724 */       if ((model.isArmed()) || (model.isSelected())) {
/*  725 */         g.setColor(this.selectionForeground);
/*      */       }
/*  727 */       JideSwingUtilities.drawStringUnderlineCharAt(menuItem, g, text, mnemIndex, textRect.x, textRect.y + fm.getAscent());
/*      */     }
/*      */   }
/*      */ 
/*      */   public Dimension getMinimumSize(JComponent c)
/*      */   {
/*  917 */     if ((!(c instanceof JMenu)) || (!((JMenu)c).isTopLevelMenu())) {
/*  918 */       return super.getMinimumSize(c);
/*      */     }
/*      */ 
/*  921 */     Dimension d = getPreferredSize(c);
/*  922 */     View v = (View)c.getClientProperty("html");
/*  923 */     if (v != null) {
/*  924 */       if (JideSwingUtilities.getOrientationOf(c) == 0)
/*      */       {
/*      */         Dimension tmp51_50 = d; tmp51_50.width = (int)(tmp51_50.width - (v.getPreferredSpan(0) - v.getMinimumSpan(0)));
/*      */       }
/*      */       else
/*      */       {
/*      */         Dimension tmp76_75 = d; tmp76_75.height = (int)(tmp76_75.height - (v.getPreferredSpan(0) - v.getMinimumSpan(0)));
/*      */       }
/*      */     }
/*  930 */     int size = ((JMenu)this.menuItem).isTopLevelMenu() ? this._splitButtonMargin : this._splitButtonMarginOnMenu;
/*  931 */     d.width += size;
/*      */ 
/*  933 */     return d;
/*      */   }
/*      */ 
/*      */   public Dimension getPreferredSize(JComponent c)
/*      */   {
/*  938 */     if ((!(c instanceof JMenu)) || (!((JMenu)c).isTopLevelMenu())) {
/*  939 */       return super.getPreferredSize(c);
/*      */     }
/*      */ 
/*  942 */     AbstractButton b = (AbstractButton)c;
/*      */ 
/*  944 */     boolean isHorizontal = true;
/*  945 */     if (JideSwingUtilities.getOrientationOf(c) == 1) {
/*  946 */       isHorizontal = false;
/*      */     }
/*      */ 
/*  951 */     Dimension d = BasicGraphicsUtils.getPreferredButtonSize(b, this.defaultTextIconGap);
/*      */ 
/*  955 */     int size = ((JMenu)this.menuItem).isTopLevelMenu() ? this._splitButtonMargin : this._splitButtonMarginOnMenu;
/*  956 */     d.width += size;
/*      */ 
/*  958 */     if (isHorizontal) {
/*  959 */       return d;
/*      */     }
/*  961 */     return new Dimension(d.height, d.width);
/*      */   }
/*      */ 
/*      */   public Dimension getMaximumSize(JComponent c)
/*      */   {
/*  966 */     if ((!(c instanceof JMenu)) || (!((JMenu)c).isTopLevelMenu())) {
/*  967 */       return super.getMaximumSize(c);
/*      */     }
/*      */ 
/*  970 */     Dimension d = getPreferredSize(c);
/*  971 */     View v = (View)c.getClientProperty("html");
/*  972 */     if (v != null)
/*      */     {
/*      */       Dimension tmp44_43 = d; tmp44_43.width = (int)(tmp44_43.width + (v.getMaximumSpan(0) - v.getPreferredSpan(0)));
/*      */     }
/*  975 */     boolean isHorizontal = true;
/*  976 */     if (JideSwingUtilities.getOrientationOf(c) == 1) {
/*  977 */       isHorizontal = false;
/*      */     }
/*      */ 
/*  980 */     int size = ((JMenu)this.menuItem).isTopLevelMenu() ? this._splitButtonMargin : this._splitButtonMarginOnMenu;
/*  981 */     if (isHorizontal)
/*  982 */       d.width += size;
/*      */     else
/*  984 */       d.height += size;
/*  985 */     return d;
/*      */   }
/*      */ 
/*      */   private Color getForegroundOfState(JMenuItem menuItem) {
/*  989 */     int state = JideSwingUtilities.getButtonState(menuItem);
/*  990 */     Color foreground = null;
/*  991 */     if ((menuItem instanceof ComponentStateSupport)) {
/*  992 */       foreground = ((ComponentStateSupport)menuItem).getForegroundOfState(state);
/*      */     }
/*  994 */     if ((foreground == null) || ((foreground instanceof UIResource))) {
/*  995 */       foreground = menuItem.getForeground();
/*      */     }
/*  997 */     return foreground;
/*      */   }
/*      */ 
/*      */   protected void paintIcon(JMenuItem b, Graphics g) {
/* 1001 */     ButtonModel model = b.getModel();
/*      */ 
/* 1004 */     if (b.getIcon() != null)
/*      */     {
/* 1006 */       if (JideSwingUtilities.getOrientationOf(b) == 1) {
/* 1007 */         g.translate(0, b.getWidth() - 1);
/* 1008 */         ((Graphics2D)g).rotate(-1.570796326794897D);
/*      */       }
/*      */       Icon icon;
/* 1011 */       if (!model.isEnabled()) {
/* 1012 */         Icon icon = b.getDisabledIcon();
/* 1013 */         if (icon == null) {
/* 1014 */           icon = b.getIcon();
/* 1015 */           if ((icon instanceof ImageIcon)) {
/* 1016 */             icon = IconsFactory.createGrayImage(((ImageIcon)icon).getImage());
/*      */           }
/*      */           else {
/* 1019 */             icon = IconsFactory.createGrayImage(b, icon);
/*      */           }
/*      */         }
/*      */       }
/* 1023 */       else if ((model.isPressed()) && (model.isArmed())) {
/* 1024 */         Icon icon = b.getPressedIcon();
/* 1025 */         if (icon == null)
/*      */         {
/* 1027 */           icon = b.getIcon();
/*      */         }
/*      */       }
/*      */       else {
/* 1031 */         icon = b.getIcon();
/*      */       }
/*      */ 
/* 1034 */       if (icon != null) {
/* 1035 */         icon.paintIcon(b, g, iconRect.x, iconRect.y);
/*      */       }
/*      */ 
/* 1044 */       if (JideSwingUtilities.getOrientationOf(b) == 1) {
/* 1045 */         ((Graphics2D)g).rotate(1.570796326794897D);
/* 1046 */         g.translate(0, -b.getHeight() + 1);
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   protected int getOffset() {
/* 1052 */     return 0;
/*      */   }
/*      */ 
/*      */   protected boolean isAlwaysDropdown(JMenuItem menuItem) {
/* 1056 */     return ((menuItem instanceof JideSplitButton)) && (((JideSplitButton)menuItem).isAlwaysDropdown());
/*      */   }
/*      */ 
/*      */   public static void loadActionMap(LazyActionMap map)
/*      */   {
/* 1115 */     map.put(new Actions("pressed"));
/* 1116 */     map.put(new Actions("released"));
/* 1117 */     map.put(new Actions("downPressed"));
/* 1118 */     map.put(new Actions("downReleased"));
/*      */   }
/*      */ 
/*      */   protected static void downButtonPressed(JMenu menu) {
/* 1122 */     MenuSelectionManager manager = MenuSelectionManager.defaultManager();
/* 1123 */     if (menu.isTopLevelMenu()) {
/* 1124 */       if (menu.isSelected()) {
/* 1125 */         manager.clearSelectedPath();
/*      */       }
/*      */       else
/*      */       {
/* 1129 */         Container cnt = getFirstParentMenuElement(menu);
/*      */ 
/* 1131 */         if ((cnt != null) && ((cnt instanceof MenuElement))) {
/* 1132 */           ArrayList parents = new ArrayList();
/* 1133 */           while ((cnt instanceof MenuElement)) {
/* 1134 */             parents.add(0, cnt);
/* 1135 */             if ((cnt instanceof JPopupMenu)) {
/* 1136 */               cnt = (Container)((JPopupMenu)cnt).getInvoker(); continue;
/*      */             }
/*      */ 
/* 1140 */             cnt = getFirstParentMenuElement(cnt);
/*      */           }
/*      */ 
/* 1144 */           MenuElement[] me = new MenuElement[parents.size() + 1];
/* 1145 */           for (int i = 0; i < parents.size(); i++) {
/* 1146 */             Container container = (Container)parents.get(i);
/* 1147 */             me[i] = ((MenuElement)container);
/*      */           }
/* 1149 */           me[parents.size()] = menu;
/* 1150 */           manager.setSelectedPath(me);
/*      */         }
/*      */         else {
/* 1153 */           MenuElement[] me = new MenuElement[1];
/* 1154 */           me[0] = menu;
/* 1155 */           manager.setSelectedPath(me);
/*      */         }
/*      */       }
/*      */     }
/*      */ 
/* 1160 */     MenuElement[] selectedPath = manager.getSelectedPath();
/* 1161 */     if ((selectedPath.length > 0) && (selectedPath[(selectedPath.length - 1)] != menu.getPopupMenu()))
/*      */     {
/* 1163 */       if ((menu.isTopLevelMenu()) || (menu.getDelay() == 0))
/*      */       {
/* 1165 */         appendPath(selectedPath, menu.getPopupMenu());
/*      */       }
/*      */       else
/* 1168 */         setupPostTimer(menu);
/*      */     }
/*      */   }
/*      */ 
/*      */   protected static Container getFirstParentMenuElement(Component comp)
/*      */   {
/* 1174 */     Container parent = comp.getParent();
/*      */ 
/* 1176 */     while (parent != null) {
/* 1177 */       if ((parent instanceof MenuElement)) {
/* 1178 */         return parent;
/*      */       }
/* 1180 */       parent = parent.getParent();
/*      */     }
/*      */ 
/* 1183 */     return null;
/*      */   }
/*      */ 
/*      */   protected int getRightMargin()
/*      */   {
/* 1198 */     return this._splitButtonMargin - 1;
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
/* 1188 */       String prop = e.getPropertyName();
/* 1189 */       if (prop.equals("buttonStyle")) {
/* 1190 */         AbstractButton b = (AbstractButton)e.getSource();
/* 1191 */         b.repaint();
/*      */       }
/*      */     }
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
/* 1070 */       super();
/*      */     }
/*      */ 
/*      */     public void actionPerformed(ActionEvent e) {
/* 1074 */       AbstractButton b = (AbstractButton)e.getSource();
/* 1075 */       String key = getName();
/*      */ 
/* 1078 */       if (("pressed".equals(key)) && (((JideSplitButton)b).isAlwaysDropdown())) {
/* 1079 */         key = "downPressed";
/*      */       }
/*      */ 
/* 1082 */       if ("pressed".equals(key)) {
/* 1083 */         ButtonModel model = b.getModel();
/* 1084 */         model.setArmed(true);
/* 1085 */         model.setPressed(true);
/* 1086 */         if (!b.hasFocus()) {
/* 1087 */           b.requestFocus();
/*      */         }
/*      */       }
/* 1090 */       else if ("released".equals(key)) {
/* 1091 */         ButtonModel model = b.getModel();
/* 1092 */         model.setPressed(false);
/* 1093 */         model.setArmed(false);
/*      */       }
/* 1095 */       else if ("downPressed".equals(key)) {
/* 1096 */         MetalJideSplitButtonUI.downButtonPressed((JMenu)b);
/*      */       }
/* 1098 */       else if (!"downReleased".equals(key));
/*      */     }
/*      */ 
/*      */     public boolean isEnabled(Object sender)
/*      */     {
/* 1104 */       return (sender == null) || (!(sender instanceof AbstractButton)) || (((AbstractButton)sender).getModel().isEnabled());
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
/*  735 */       cancelMenuIfNecessary(e);
/*      */     }
/*      */ 
/*      */     public void mousePressed(MouseEvent e)
/*      */     {
/*  745 */       JMenu menu = (JMenu)MetalJideSplitButtonUI.this.menuItem;
/*  746 */       if (!menu.isEnabled()) {
/*  747 */         return;
/*      */       }
/*  749 */       MetalJideSplitButtonUI.this.setMouseOver(true);
/*      */ 
/*  751 */       if (!SwingUtilities.isLeftMouseButton(e)) {
/*  752 */         return;
/*      */       }
/*  754 */       if (isClickOnButton(e, menu)) {
/*  755 */         if (((JideSplitButton)MetalJideSplitButtonUI.this.menuItem).isButtonEnabled())
/*      */         {
/*  757 */           menu.getModel().setArmed(true);
/*  758 */           menu.getModel().setPressed(true);
/*      */         }
/*  760 */         if ((!menu.hasFocus()) && (menu.isRequestFocusEnabled()))
/*  761 */           menu.requestFocus();
/*      */       }
/*      */       else
/*      */       {
/*  765 */         MetalJideSplitButtonUI.downButtonPressed(menu);
/*      */       }
/*      */     }
/*      */ 
/*      */     private boolean isClickOnButton(MouseEvent e, JMenu menu) {
/*  770 */       if (((JideSplitButton)menu).isAlwaysDropdown()) {
/*  771 */         return false;
/*      */       }
/*      */ 
/*  774 */       boolean clickOnDropDown = false;
/*  775 */       int size = ((JMenu)MetalJideSplitButtonUI.this.menuItem).isTopLevelMenu() ? MetalJideSplitButtonUI.this._splitButtonMargin : MetalJideSplitButtonUI.this._splitButtonMarginOnMenu;
/*  776 */       if (JideSwingUtilities.getOrientationOf(MetalJideSplitButtonUI.this.menuItem) == 0) {
/*  777 */         if (e.getPoint().getX() < menu.getWidth() - size) {
/*  778 */           clickOnDropDown = true;
/*      */         }
/*      */ 
/*      */       }
/*  782 */       else if (e.getPoint().getY() < menu.getHeight() - size) {
/*  783 */         clickOnDropDown = true;
/*      */       }
/*      */ 
/*  786 */       return clickOnDropDown;
/*      */     }
/*      */ 
/*      */     public void mouseReleased(MouseEvent e)
/*      */     {
/*  795 */       JMenu menu = (JMenu)MetalJideSplitButtonUI.this.menuItem;
/*  796 */       if (!menu.isEnabled()) {
/*  797 */         return;
/*      */       }
/*  799 */       if (!isClickOnButton(e, menu))
/*      */       {
/*  801 */         MetalJideSplitButtonUI.this.menuItem.getModel().setArmed(false);
/*  802 */         MetalJideSplitButtonUI.this.menuItem.getModel().setPressed(false);
/*      */       }
/*  804 */       cancelMenuIfNecessary(e);
/*      */     }
/*      */ 
/*      */     private void cancelMenuIfNecessary(MouseEvent e) {
/*  808 */       JMenu menu = (JMenu)MetalJideSplitButtonUI.this.menuItem;
/*  809 */       if (!menu.isEnabled())
/*  810 */         return;
/*  811 */       if ((isClickOnButton(e, menu)) && 
/*  812 */         (((JideSplitButton)MetalJideSplitButtonUI.this.menuItem).isButtonEnabled()))
/*      */       {
/*  815 */         if (SwingUtilities.isLeftMouseButton(e)) {
/*  816 */           menu.getModel().setPressed(false);
/*  817 */           menu.getModel().setArmed(false);
/*      */         }
/*      */         else {
/*  820 */           menu.getModel().setArmed(false);
/*  821 */           menu.getModel().setPressed(false);
/*      */         }
/*      */ 
/*  824 */         MenuSelectionManager manager = MenuSelectionManager.defaultManager();
/*  825 */         MenuElement[] menuElements = manager.getSelectedPath();
/*  826 */         for (int i = menuElements.length - 1; i >= 0; i--) {
/*  827 */           MenuElement menuElement = menuElements[i];
/*  828 */           if (((menuElement instanceof JPopupMenu)) && (((JPopupMenu)menuElement).isAncestorOf(menu))) {
/*  829 */             menu.getModel().setRollover(false);
/*  830 */             MetalJideSplitButtonUI.this.setMouseOver(false);
/*  831 */             manager.clearSelectedPath();
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */ 
/*      */     public void mouseEntered(MouseEvent e)
/*      */     {
/*  853 */       JMenu menu = (JMenu)MetalJideSplitButtonUI.this.menuItem;
/*  854 */       if (!menu.isEnabled()) {
/*  855 */         return;
/*      */       }
/*  857 */       MenuSelectionManager manager = MenuSelectionManager.defaultManager();
/*      */ 
/*  859 */       MenuElement[] selectedPath = manager.getSelectedPath();
/*  860 */       if (!menu.isTopLevelMenu()) {
/*  861 */         if ((selectedPath.length <= 0) || (selectedPath[(selectedPath.length - 1)] != menu.getPopupMenu()))
/*      */         {
/*  864 */           if (menu.getDelay() == 0) {
/*  865 */             MetalMenuUI.appendPath(MetalJideSplitButtonUI.this.getPath(), menu.getPopupMenu());
/*      */           }
/*      */           else {
/*  868 */             manager.setSelectedPath(MetalJideSplitButtonUI.this.getPath());
/*  869 */             MetalMenuUI.setupPostTimer(menu);
/*      */           }
/*      */         }
/*      */ 
/*      */       }
/*  874 */       else if ((selectedPath.length > 0) && (selectedPath[0] == menu.getParent()))
/*      */       {
/*  876 */         MenuElement[] newPath = new MenuElement[3];
/*      */ 
/*  879 */         newPath[0] = ((MenuElement)menu.getParent());
/*  880 */         newPath[1] = menu;
/*  881 */         newPath[2] = menu.getPopupMenu();
/*  882 */         manager.setSelectedPath(newPath);
/*      */       }
/*      */ 
/*  886 */       if (!SwingUtilities.isLeftMouseButton(e)) {
/*  887 */         MetalJideSplitButtonUI.this.setMouseOver(true);
/*      */       }
/*  889 */       MetalJideSplitButtonUI.this.menuItem.repaint();
/*      */     }
/*      */ 
/*      */     public void mouseExited(MouseEvent e) {
/*  893 */       MetalJideSplitButtonUI.this.setMouseOver(false);
/*  894 */       MetalJideSplitButtonUI.this.menuItem.repaint();
/*      */     }
/*      */ 
/*      */     public void mouseDragged(MouseEvent e)
/*      */     {
/*  905 */       JMenu menu = (JMenu)MetalJideSplitButtonUI.this.menuItem;
/*  906 */       if (!menu.isEnabled())
/*  907 */         return;
/*  908 */       MenuSelectionManager.defaultManager().processMouseEvent(e);
/*      */     }
/*      */ 
/*      */     public void mouseMoved(MouseEvent e)
/*      */     {
/*      */     }
/*      */   }
/*      */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     com.jidesoft.plaf.metal.MetalJideSplitButtonUI
 * JD-Core Version:    0.6.0
 */