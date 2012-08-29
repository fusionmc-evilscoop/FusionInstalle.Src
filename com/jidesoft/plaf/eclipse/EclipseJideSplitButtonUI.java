/*     */ package com.jidesoft.plaf.eclipse;
/*     */ 
/*     */ import com.jidesoft.icons.IconsFactory;
/*     */ import com.jidesoft.plaf.UIDefaultsLookup;
/*     */ import com.jidesoft.plaf.basic.LazyActionMap;
/*     */ import com.jidesoft.plaf.basic.ThemePainter;
/*     */ import com.jidesoft.plaf.basic.UIAction;
/*     */ import com.jidesoft.swing.JideSplitButton;
/*     */ import com.jidesoft.swing.JideSwingUtilities;
/*     */ import java.awt.Color;
/*     */ import java.awt.Component;
/*     */ import java.awt.Container;
/*     */ import java.awt.Dimension;
/*     */ import java.awt.Graphics;
/*     */ import java.awt.Graphics2D;
/*     */ import java.awt.Point;
/*     */ import java.awt.Rectangle;
/*     */ import java.awt.event.ActionEvent;
/*     */ import java.awt.event.FocusEvent;
/*     */ import java.awt.event.FocusListener;
/*     */ import java.awt.event.MouseEvent;
/*     */ import java.util.ArrayList;
/*     */ import javax.swing.AbstractButton;
/*     */ import javax.swing.ButtonModel;
/*     */ import javax.swing.Icon;
/*     */ import javax.swing.ImageIcon;
/*     */ import javax.swing.InputMap;
/*     */ import javax.swing.JComponent;
/*     */ import javax.swing.JMenu;
/*     */ import javax.swing.JMenuItem;
/*     */ import javax.swing.JPopupMenu;
/*     */ import javax.swing.MenuElement;
/*     */ import javax.swing.MenuSelectionManager;
/*     */ import javax.swing.SwingUtilities;
/*     */ import javax.swing.event.MouseInputListener;
/*     */ import javax.swing.plaf.ComponentUI;
/*     */ import javax.swing.plaf.basic.BasicGraphicsUtils;
/*     */ import javax.swing.text.View;
/*     */ 
/*     */ public class EclipseJideSplitButtonUI extends EclipseMenuUI
/*     */ {
/*     */   protected ThemePainter _painter;
/*     */   protected Color _shadowColor;
/*     */   protected Color _darkShadowColor;
/*     */   protected Color _highlight;
/*     */   protected Color _lightHighlightColor;
/*     */   protected int _splitButtonMargin;
/*     */   protected int _splitButtonMarginOnMenu;
/*     */   private FocusListener _focusListener;
/*     */   private static final String propertyPrefix = "JideSplitButton";
/*     */ 
/*     */   public EclipseJideSplitButtonUI()
/*     */   {
/*  42 */     this._splitButtonMargin = 12;
/*  43 */     this._splitButtonMarginOnMenu = 20;
/*     */   }
/*     */ 
/*     */   protected String getPropertyPrefix()
/*     */   {
/*  51 */     return "JideSplitButton";
/*     */   }
/*     */ 
/*     */   public static ComponentUI createUI(JComponent c)
/*     */   {
/*  56 */     return new EclipseJideSplitButtonUI();
/*     */   }
/*     */ 
/*     */   protected void installDefaults()
/*     */   {
/*  61 */     this._painter = ((ThemePainter)UIDefaultsLookup.get("Theme.painter"));
/*  62 */     this._shadowColor = UIDefaultsLookup.getColor("controlShadow");
/*  63 */     this._darkShadowColor = UIDefaultsLookup.getColor("controlDkShadow");
/*  64 */     this._highlight = UIDefaultsLookup.getColor("controlHighlight");
/*  65 */     this._lightHighlightColor = UIDefaultsLookup.getColor("controlLtHighlight");
/*  66 */     this.menuItem.setRolloverEnabled(true);
/*     */ 
/*  68 */     super.installDefaults();
/*     */   }
/*     */ 
/*     */   protected void uninstallDefaults()
/*     */   {
/*  73 */     this._painter = null;
/*  74 */     this._shadowColor = null;
/*  75 */     this._highlight = null;
/*  76 */     this._lightHighlightColor = null;
/*  77 */     this._darkShadowColor = null;
/*     */ 
/*  79 */     super.uninstallDefaults();
/*     */   }
/*     */ 
/*     */   protected void installListeners()
/*     */   {
/*  84 */     super.installListeners();
/*  85 */     if (this._focusListener == null)
/*  86 */       this._focusListener = new FocusListener() {
/*     */         public void focusGained(FocusEvent e) {
/*  88 */           EclipseJideSplitButtonUI.this.menuItem.repaint();
/*     */         }
/*     */ 
/*     */         public void focusLost(FocusEvent e) {
/*  92 */           EclipseJideSplitButtonUI.this.menuItem.repaint();
/*     */         }
/*     */       };
/*  96 */     this.menuItem.addFocusListener(this._focusListener);
/*     */   }
/*     */ 
/*     */   protected void uninstallListeners()
/*     */   {
/* 101 */     super.uninstallListeners();
/* 102 */     if (this._focusListener != null)
/* 103 */       this.menuItem.removeFocusListener(this._focusListener);
/*     */   }
/*     */ 
/*     */   static Object getUIOfType(ComponentUI ui, Class klass)
/*     */   {
/* 111 */     if (klass.isInstance(ui)) {
/* 112 */       return ui;
/*     */     }
/* 114 */     return null;
/*     */   }
/*     */ 
/*     */   public InputMap getInputMap(int condition, JComponent c)
/*     */   {
/* 126 */     if (condition == 0) {
/* 127 */       EclipseJideSplitButtonUI ui = (EclipseJideSplitButtonUI)getUIOfType(((JideSplitButton)c).getUI(), EclipseJideSplitButtonUI.class);
/*     */ 
/* 129 */       if (ui != null) {
/* 130 */         return (InputMap)UIDefaultsLookup.get(ui.getPropertyPrefix() + ".focusInputMap");
/*     */       }
/*     */     }
/* 133 */     return null;
/*     */   }
/*     */ 
/*     */   protected void installKeyboardActions()
/*     */   {
/* 138 */     super.installKeyboardActions();
/* 139 */     AbstractButton b = this.menuItem;
/*     */ 
/* 141 */     LazyActionMap.installLazyActionMap(b, EclipseJideSplitButtonUI.class, "JideSplitButton.actionMap");
/*     */ 
/* 144 */     InputMap km = getInputMap(0, b);
/*     */ 
/* 146 */     SwingUtilities.replaceUIInputMap(b, 0, km);
/*     */   }
/*     */ 
/*     */   protected void uninstallKeyboardActions()
/*     */   {
/* 151 */     AbstractButton b = this.menuItem;
/* 152 */     SwingUtilities.replaceUIInputMap(b, 2, null);
/*     */ 
/* 154 */     SwingUtilities.replaceUIInputMap(b, 0, null);
/* 155 */     SwingUtilities.replaceUIActionMap(b, null);
/* 156 */     super.uninstallKeyboardActions();
/*     */   }
/*     */ 
/*     */   protected MouseInputListener createMouseInputListener(JComponent c)
/*     */   {
/* 161 */     return new MouseInputHandler();
/*     */   }
/*     */ 
/*     */   protected void paintBackground(Graphics g, JMenuItem menuItem, Color bgColor)
/*     */   {
/* 166 */     ButtonModel model = menuItem.getModel();
/*     */     int menuHeight;
/*     */     int menuWidth;
/*     */     int menuHeight;
/* 169 */     if (JideSwingUtilities.getOrientationOf(menuItem) == 0) {
/* 170 */       int menuWidth = menuItem.getWidth();
/* 171 */       menuHeight = menuItem.getHeight();
/*     */     }
/*     */     else {
/* 174 */       menuWidth = menuItem.getHeight();
/* 175 */       menuHeight = menuItem.getWidth();
/*     */     }
/*     */ 
/* 178 */     if (!((JMenu)menuItem).isTopLevelMenu()) {
/* 179 */       super.paintBackground(g, menuItem, bgColor);
/* 180 */       Color oldColor = g.getColor();
/* 181 */       if (menuItem.isEnabled()) {
/* 182 */         if ((model.isArmed()) || (model.isPressed()) || (isMouseOver())) {
/* 183 */           g.setColor(this.selectionForeground);
/* 184 */           g.drawLine(menuWidth - this._splitButtonMarginOnMenu, 0, menuWidth - this._splitButtonMarginOnMenu, menuHeight - 2);
/* 185 */           JideSwingUtilities.paintArrow(g, this.selectionForeground, menuWidth - this._splitButtonMarginOnMenu / 2 - 2, menuHeight / 2 - 3, 7, 1);
/*     */         }
/*     */         else {
/* 188 */           g.setColor(menuItem.getForeground());
/* 189 */           g.drawLine(menuWidth - this._splitButtonMarginOnMenu, 0, menuWidth - this._splitButtonMarginOnMenu, menuHeight - 2);
/* 190 */           JideSwingUtilities.paintArrow(g, menuItem.getForeground(), menuWidth - this._splitButtonMarginOnMenu / 2 - 2, menuHeight / 2 - 3, 7, 1);
/*     */         }
/*     */       }
/*     */       else {
/* 194 */         g.setColor(UIDefaultsLookup.getColor("controlDkShadow"));
/* 195 */         g.drawLine(menuWidth - this._splitButtonMarginOnMenu, 0, menuWidth - this._splitButtonMarginOnMenu, menuHeight - 2);
/* 196 */         JideSwingUtilities.paintArrow(g, UIDefaultsLookup.getColor("controlDkShadow"), menuWidth - this._splitButtonMarginOnMenu / 2 - 2, menuHeight / 2 - 3, 7, 1);
/*     */       }
/* 198 */       g.setColor(oldColor);
/* 199 */       return;
/*     */     }
/*     */ 
/* 202 */     if (menuItem.isOpaque()) {
/* 203 */       Color oldColor = g.getColor();
/* 204 */       if (menuItem.getParent() != null) {
/* 205 */         g.setColor(menuItem.getParent().getBackground());
/*     */       }
/*     */       else {
/* 208 */         g.setColor(menuItem.getBackground());
/*     */       }
/* 210 */       g.fillRect(0, 0, menuWidth, menuHeight);
/* 211 */       g.setColor(oldColor);
/*     */     }
/*     */ 
/* 214 */     if (((menuItem instanceof JMenu)) && (model.isSelected()))
/*     */     {
/* 216 */       getPainter().paintSelectedMenu(menuItem, g, new Rectangle(0, 0, menuWidth, menuHeight), JideSwingUtilities.getOrientationOf(menuItem), 3);
/*     */     }
/* 218 */     else if ((model.isArmed()) || (model.isPressed()))
/*     */     {
/* 220 */       Rectangle rect = new Rectangle(0, 0, menuWidth - this._splitButtonMargin, menuHeight);
/* 221 */       getPainter().paintButtonBackground(menuItem, g, rect, JideSwingUtilities.getOrientationOf(menuItem), 1);
/* 222 */       rect = new Rectangle(menuWidth - this._splitButtonMargin - 1 + getOffset(), 0, this._splitButtonMargin - getOffset(), menuHeight);
/* 223 */       getPainter().paintButtonBackground(menuItem, g, rect, JideSwingUtilities.getOrientationOf(menuItem), 2);
/*     */     }
/* 226 */     else if ((isMouseOver()) && (model.isEnabled()))
/*     */     {
/* 229 */       Rectangle rect = new Rectangle(0, 0, menuWidth - this._splitButtonMargin, menuHeight);
/* 230 */       getPainter().paintButtonBackground(menuItem, g, rect, JideSwingUtilities.getOrientationOf(menuItem), 2);
/* 231 */       rect = new Rectangle(menuWidth - this._splitButtonMargin - 1 + getOffset(), 0, this._splitButtonMargin - getOffset(), menuHeight);
/* 232 */       getPainter().paintButtonBackground(menuItem, g, rect, JideSwingUtilities.getOrientationOf(menuItem), 2);
/*     */     }
/*     */ 
/* 236 */     if (menuItem.isEnabled()) {
/* 237 */       JideSwingUtilities.paintArrow(g, menuItem.getForeground(), menuWidth - 10, menuHeight / 2 - 1, 5, 0);
/*     */     }
/*     */     else
/* 240 */       JideSwingUtilities.paintArrow(g, UIDefaultsLookup.getColor("controlDkShadow"), menuWidth - 10, menuHeight / 2 - 1, 5, 0);
/*     */   }
/*     */ 
/*     */   public Dimension getMinimumSize(JComponent c)
/*     */   {
/* 428 */     if ((!(c instanceof JMenu)) || (!((JMenu)c).isTopLevelMenu())) {
/* 429 */       return super.getMinimumSize(c);
/*     */     }
/*     */ 
/* 432 */     Dimension d = getPreferredSize(c);
/* 433 */     View v = (View)c.getClientProperty("html");
/* 434 */     if (v != null) {
/* 435 */       if (JideSwingUtilities.getOrientationOf(c) == 0)
/*     */       {
/*     */         Dimension tmp51_50 = d; tmp51_50.width = (int)(tmp51_50.width - (v.getPreferredSpan(0) - v.getMinimumSpan(0)));
/*     */       }
/*     */       else
/*     */       {
/*     */         Dimension tmp76_75 = d; tmp76_75.height = (int)(tmp76_75.height - (v.getPreferredSpan(0) - v.getMinimumSpan(0)));
/*     */       }
/*     */     }
/* 441 */     return d;
/*     */   }
/*     */ 
/*     */   public Dimension getMaximumSize(JComponent c)
/*     */   {
/* 446 */     if ((!(c instanceof JMenu)) || (!((JMenu)c).isTopLevelMenu())) {
/* 447 */       return super.getMaximumSize(c);
/*     */     }
/*     */ 
/* 450 */     Dimension d = getPreferredSize(c);
/* 451 */     View v = (View)c.getClientProperty("html");
/* 452 */     if (v != null) {
/* 453 */       if (JideSwingUtilities.getOrientationOf(c) == 0)
/*     */       {
/*     */         Dimension tmp51_50 = d; tmp51_50.width = (int)(tmp51_50.width + (v.getMaximumSpan(0) - v.getPreferredSpan(0)));
/*     */       }
/*     */       else
/*     */       {
/*     */         Dimension tmp76_75 = d; tmp76_75.height = (int)(tmp76_75.height + (v.getMaximumSpan(0) - v.getPreferredSpan(0)));
/*     */       }
/*     */     }
/* 459 */     return d;
/*     */   }
/*     */ 
/*     */   public Dimension getPreferredSize(JComponent c)
/*     */   {
/* 464 */     if ((!(c instanceof JMenu)) || (!((JMenu)c).isTopLevelMenu())) {
/* 465 */       return super.getPreferredSize(c);
/*     */     }
/*     */ 
/* 468 */     AbstractButton b = (AbstractButton)c;
/*     */ 
/* 470 */     boolean isHorizontal = true;
/* 471 */     if (JideSwingUtilities.getOrientationOf(c) == 1) {
/* 472 */       isHorizontal = false;
/*     */     }
/*     */ 
/* 477 */     Dimension d = BasicGraphicsUtils.getPreferredButtonSize(b, this.defaultTextIconGap);
/*     */ 
/* 481 */     int size = ((JMenu)this.menuItem).isTopLevelMenu() ? this._splitButtonMargin : this._splitButtonMarginOnMenu;
/* 482 */     d.width += size;
/*     */ 
/* 484 */     if (isHorizontal) {
/* 485 */       return d;
/*     */     }
/* 487 */     return new Dimension(d.height, d.width);
/*     */   }
/*     */ 
/*     */   protected void paintIcon(JMenuItem b, Graphics g) {
/* 491 */     ButtonModel model = b.getModel();
/*     */ 
/* 494 */     if (b.getIcon() != null)
/*     */     {
/* 496 */       if (JideSwingUtilities.getOrientationOf(b) == 1) {
/* 497 */         g.translate(0, b.getWidth() - 1);
/* 498 */         ((Graphics2D)g).rotate(-1.570796326794897D);
/*     */       }
/*     */       Icon icon;
/* 501 */       if (!model.isEnabled()) {
/* 502 */         Icon icon = b.getDisabledIcon();
/* 503 */         if (icon == null) {
/* 504 */           icon = b.getIcon();
/* 505 */           if ((icon instanceof ImageIcon)) {
/* 506 */             icon = IconsFactory.createGrayImage(((ImageIcon)icon).getImage());
/*     */           }
/*     */           else {
/* 509 */             icon = IconsFactory.createGrayImage(b, icon);
/*     */           }
/*     */         }
/*     */       }
/* 513 */       else if ((model.isPressed()) && (model.isArmed())) {
/* 514 */         Icon icon = b.getPressedIcon();
/* 515 */         if (icon == null)
/*     */         {
/* 517 */           icon = b.getIcon();
/*     */         }
/*     */       }
/*     */       else {
/* 521 */         icon = b.getIcon();
/*     */       }
/*     */ 
/* 524 */       if (icon != null) {
/* 525 */         icon.paintIcon(b, g, iconRect.x, iconRect.y);
/*     */       }
/*     */ 
/* 534 */       if (JideSwingUtilities.getOrientationOf(b) == 1) {
/* 535 */         ((Graphics2D)g).rotate(1.570796326794897D);
/* 536 */         g.translate(0, -b.getHeight() + 1);
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   protected int getOffset()
/*     */   {
/* 592 */     return 1;
/*     */   }
/*     */ 
/*     */   public static void loadActionMap(LazyActionMap map)
/*     */   {
/* 600 */     map.put(new Actions("pressed"));
/* 601 */     map.put(new Actions("released"));
/* 602 */     map.put(new Actions("downPressed"));
/* 603 */     map.put(new Actions("downReleased"));
/*     */   }
/*     */ 
/*     */   protected static void downButtonPressed(JMenu menu) {
/* 607 */     MenuSelectionManager manager = MenuSelectionManager.defaultManager();
/* 608 */     if (menu.isTopLevelMenu()) {
/* 609 */       if (menu.isSelected()) {
/* 610 */         manager.clearSelectedPath();
/*     */       }
/*     */       else
/*     */       {
/* 614 */         Container cnt = getFirstParentMenuElement(menu);
/*     */ 
/* 616 */         if ((cnt != null) && ((cnt instanceof MenuElement))) {
/* 617 */           ArrayList parents = new ArrayList();
/* 618 */           while ((cnt instanceof MenuElement)) {
/* 619 */             parents.add(0, cnt);
/* 620 */             if ((cnt instanceof JPopupMenu)) {
/* 621 */               cnt = (Container)((JPopupMenu)cnt).getInvoker(); continue;
/*     */             }
/*     */ 
/* 625 */             cnt = getFirstParentMenuElement(cnt);
/*     */           }
/*     */ 
/* 629 */           MenuElement[] me = new MenuElement[parents.size() + 1];
/* 630 */           for (int i = 0; i < parents.size(); i++) {
/* 631 */             Container container = (Container)parents.get(i);
/* 632 */             me[i] = ((MenuElement)container);
/*     */           }
/* 634 */           me[parents.size()] = menu;
/* 635 */           manager.setSelectedPath(me);
/*     */         }
/*     */         else {
/* 638 */           MenuElement[] me = new MenuElement[1];
/* 639 */           me[0] = menu;
/* 640 */           manager.setSelectedPath(me);
/*     */         }
/*     */       }
/*     */     }
/*     */ 
/* 645 */     MenuElement[] selectedPath = manager.getSelectedPath();
/* 646 */     if ((selectedPath.length > 0) && (selectedPath[(selectedPath.length - 1)] != menu.getPopupMenu()))
/*     */     {
/* 648 */       if ((menu.isTopLevelMenu()) || (menu.getDelay() == 0))
/*     */       {
/* 650 */         appendPath(selectedPath, menu.getPopupMenu());
/*     */       }
/*     */       else
/* 653 */         setupPostTimer(menu);
/*     */     }
/*     */   }
/*     */ 
/*     */   protected static Container getFirstParentMenuElement(Component comp)
/*     */   {
/* 659 */     Container parent = comp.getParent();
/*     */ 
/* 661 */     while (parent != null) {
/* 662 */       if ((parent instanceof MenuElement)) {
/* 663 */         return parent;
/*     */       }
/* 665 */       parent = parent.getParent();
/*     */     }
/*     */ 
/* 668 */     return null;
/*     */   }
/*     */ 
/*     */   private static class Actions extends UIAction
/*     */   {
/*     */     private static final String PRESS = "pressed";
/*     */     private static final String RELEASE = "released";
/*     */     private static final String DOWN_PRESS = "downPressed";
/*     */     private static final String DOWN_RELEASE = "downReleased";
/*     */ 
/*     */     Actions(String name)
/*     */     {
/* 552 */       super();
/*     */     }
/*     */ 
/*     */     public void actionPerformed(ActionEvent e) {
/* 556 */       AbstractButton b = (AbstractButton)e.getSource();
/* 557 */       String key = getName();
/*     */ 
/* 560 */       if (("pressed".equals(key)) && (((JideSplitButton)b).isAlwaysDropdown())) {
/* 561 */         key = "downPressed";
/*     */       }
/*     */ 
/* 564 */       if ("pressed".equals(key)) {
/* 565 */         ButtonModel model = b.getModel();
/* 566 */         model.setArmed(true);
/* 567 */         model.setPressed(true);
/* 568 */         if (!b.hasFocus()) {
/* 569 */           b.requestFocus();
/*     */         }
/*     */       }
/* 572 */       else if ("released".equals(key)) {
/* 573 */         ButtonModel model = b.getModel();
/* 574 */         model.setPressed(false);
/* 575 */         model.setArmed(false);
/*     */       }
/* 577 */       else if ("downPressed".equals(key)) {
/* 578 */         EclipseJideSplitButtonUI.downButtonPressed((JMenu)b);
/*     */       }
/* 580 */       else if (!"downReleased".equals(key));
/*     */     }
/*     */ 
/*     */     public boolean isEnabled(Object sender)
/*     */     {
/* 586 */       return (sender == null) || (!(sender instanceof AbstractButton)) || (((AbstractButton)sender).getModel().isEnabled());
/*     */     }
/*     */   }
/*     */ 
/*     */   protected class MouseInputHandler
/*     */     implements MouseInputListener
/*     */   {
/*     */     protected MouseInputHandler()
/*     */     {
/*     */     }
/*     */ 
/*     */     public void mouseClicked(MouseEvent e)
/*     */     {
/* 246 */       cancelMenuIfNecessary(e);
/*     */     }
/*     */ 
/*     */     public void mousePressed(MouseEvent e)
/*     */     {
/* 256 */       JMenu menu = (JMenu)EclipseJideSplitButtonUI.this.menuItem;
/* 257 */       if (!menu.isEnabled()) {
/* 258 */         return;
/*     */       }
/* 260 */       EclipseJideSplitButtonUI.this.setMouseOver(true);
/*     */ 
/* 262 */       if (!SwingUtilities.isLeftMouseButton(e)) {
/* 263 */         return;
/*     */       }
/* 265 */       if (isClickOnButton(e, menu)) {
/* 266 */         if (((JideSplitButton)EclipseJideSplitButtonUI.this.menuItem).isButtonEnabled())
/*     */         {
/* 268 */           menu.getModel().setArmed(true);
/* 269 */           menu.getModel().setPressed(true);
/*     */         }
/* 271 */         if ((!menu.hasFocus()) && (menu.isRequestFocusEnabled()))
/* 272 */           menu.requestFocus();
/*     */       }
/*     */       else
/*     */       {
/* 276 */         EclipseJideSplitButtonUI.downButtonPressed(menu);
/*     */       }
/*     */     }
/*     */ 
/*     */     private boolean isClickOnButton(MouseEvent e, JMenu menu) {
/* 281 */       if (((JideSplitButton)menu).isAlwaysDropdown()) {
/* 282 */         return false;
/*     */       }
/*     */ 
/* 285 */       boolean clickOnDropDown = false;
/* 286 */       int size = ((JMenu)EclipseJideSplitButtonUI.this.menuItem).isTopLevelMenu() ? EclipseJideSplitButtonUI.this._splitButtonMargin : EclipseJideSplitButtonUI.this._splitButtonMarginOnMenu;
/* 287 */       if (JideSwingUtilities.getOrientationOf(EclipseJideSplitButtonUI.this.menuItem) == 0) {
/* 288 */         if (e.getPoint().getX() < menu.getWidth() - size) {
/* 289 */           clickOnDropDown = true;
/*     */         }
/*     */ 
/*     */       }
/* 293 */       else if (e.getPoint().getY() < menu.getHeight() - size) {
/* 294 */         clickOnDropDown = true;
/*     */       }
/*     */ 
/* 297 */       return clickOnDropDown;
/*     */     }
/*     */ 
/*     */     public void mouseReleased(MouseEvent e)
/*     */     {
/* 306 */       JMenu menu = (JMenu)EclipseJideSplitButtonUI.this.menuItem;
/* 307 */       if (!menu.isEnabled()) {
/* 308 */         return;
/*     */       }
/* 310 */       if (!isClickOnButton(e, menu))
/*     */       {
/* 312 */         EclipseJideSplitButtonUI.this.menuItem.getModel().setArmed(false);
/* 313 */         EclipseJideSplitButtonUI.this.menuItem.getModel().setPressed(false);
/*     */       }
/* 315 */       cancelMenuIfNecessary(e);
/*     */     }
/*     */ 
/*     */     private void cancelMenuIfNecessary(MouseEvent e) {
/* 319 */       JMenu menu = (JMenu)EclipseJideSplitButtonUI.this.menuItem;
/* 320 */       if (!menu.isEnabled())
/* 321 */         return;
/* 322 */       if ((isClickOnButton(e, menu)) && 
/* 323 */         (((JideSplitButton)EclipseJideSplitButtonUI.this.menuItem).isButtonEnabled()))
/*     */       {
/* 326 */         if (SwingUtilities.isLeftMouseButton(e)) {
/* 327 */           menu.getModel().setPressed(false);
/* 328 */           menu.getModel().setArmed(false);
/*     */         }
/*     */         else {
/* 331 */           menu.getModel().setArmed(false);
/* 332 */           menu.getModel().setPressed(false);
/*     */         }
/*     */ 
/* 335 */         MenuSelectionManager manager = MenuSelectionManager.defaultManager();
/* 336 */         MenuElement[] menuElements = manager.getSelectedPath();
/* 337 */         for (int i = menuElements.length - 1; i >= 0; i--) {
/* 338 */           MenuElement menuElement = menuElements[i];
/* 339 */           if (((menuElement instanceof JPopupMenu)) && (((JPopupMenu)menuElement).isAncestorOf(menu))) {
/* 340 */             menu.getModel().setRollover(false);
/* 341 */             EclipseJideSplitButtonUI.this.setMouseOver(false);
/* 342 */             manager.clearSelectedPath();
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */ 
/*     */     public void mouseEntered(MouseEvent e)
/*     */     {
/* 364 */       JMenu menu = (JMenu)EclipseJideSplitButtonUI.this.menuItem;
/* 365 */       if (!menu.isEnabled()) {
/* 366 */         return;
/*     */       }
/* 368 */       MenuSelectionManager manager = MenuSelectionManager.defaultManager();
/*     */ 
/* 370 */       MenuElement[] selectedPath = manager.getSelectedPath();
/* 371 */       if (!menu.isTopLevelMenu()) {
/* 372 */         if ((selectedPath.length <= 0) || (selectedPath[(selectedPath.length - 1)] != menu.getPopupMenu()))
/*     */         {
/* 375 */           if (menu.getDelay() == 0) {
/* 376 */             EclipseMenuUI.appendPath(EclipseJideSplitButtonUI.this.getPath(), menu.getPopupMenu());
/*     */           }
/*     */           else {
/* 379 */             manager.setSelectedPath(EclipseJideSplitButtonUI.this.getPath());
/* 380 */             EclipseMenuUI.setupPostTimer(menu);
/*     */           }
/*     */         }
/*     */ 
/*     */       }
/* 385 */       else if ((selectedPath.length > 0) && (selectedPath[0] == menu.getParent()))
/*     */       {
/* 387 */         MenuElement[] newPath = new MenuElement[3];
/*     */ 
/* 390 */         newPath[0] = ((MenuElement)menu.getParent());
/* 391 */         newPath[1] = menu;
/* 392 */         newPath[2] = menu.getPopupMenu();
/* 393 */         manager.setSelectedPath(newPath);
/*     */       }
/*     */ 
/* 397 */       if (!SwingUtilities.isLeftMouseButton(e)) {
/* 398 */         EclipseJideSplitButtonUI.this.setMouseOver(true);
/*     */       }
/* 400 */       EclipseJideSplitButtonUI.this.menuItem.repaint();
/*     */     }
/*     */ 
/*     */     public void mouseExited(MouseEvent e) {
/* 404 */       EclipseJideSplitButtonUI.this.setMouseOver(false);
/* 405 */       EclipseJideSplitButtonUI.this.menuItem.repaint();
/*     */     }
/*     */ 
/*     */     public void mouseDragged(MouseEvent e)
/*     */     {
/* 416 */       JMenu menu = (JMenu)EclipseJideSplitButtonUI.this.menuItem;
/* 417 */       if (!menu.isEnabled())
/* 418 */         return;
/* 419 */       MenuSelectionManager.defaultManager().processMouseEvent(e);
/*     */     }
/*     */ 
/*     */     public void mouseMoved(MouseEvent e)
/*     */     {
/*     */     }
/*     */   }
/*     */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     com.jidesoft.plaf.eclipse.EclipseJideSplitButtonUI
 * JD-Core Version:    0.6.0
 */