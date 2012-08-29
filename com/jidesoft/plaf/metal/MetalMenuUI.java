/*     */ package com.jidesoft.plaf.metal;
/*     */ 
/*     */ import com.jidesoft.plaf.UIDefaultsLookup;
/*     */ import com.jidesoft.swing.JideSwingUtilities;
/*     */ import com.jidesoft.swing.TopLevelMenuContainer;
/*     */ import java.awt.Color;
/*     */ import java.awt.Component;
/*     */ import java.awt.Container;
/*     */ import java.awt.Dimension;
/*     */ import java.awt.Graphics;
/*     */ import java.awt.Point;
/*     */ import java.awt.event.ActionEvent;
/*     */ import java.awt.event.MouseEvent;
/*     */ import java.beans.PropertyChangeEvent;
/*     */ import java.beans.PropertyChangeListener;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import javax.swing.AbstractAction;
/*     */ import javax.swing.ActionMap;
/*     */ import javax.swing.ButtonModel;
/*     */ import javax.swing.InputMap;
/*     */ import javax.swing.JComponent;
/*     */ import javax.swing.JMenu;
/*     */ import javax.swing.JMenuBar;
/*     */ import javax.swing.JMenuItem;
/*     */ import javax.swing.JPopupMenu;
/*     */ import javax.swing.KeyStroke;
/*     */ import javax.swing.MenuElement;
/*     */ import javax.swing.MenuSelectionManager;
/*     */ import javax.swing.SwingUtilities;
/*     */ import javax.swing.Timer;
/*     */ import javax.swing.event.ChangeEvent;
/*     */ import javax.swing.event.ChangeListener;
/*     */ import javax.swing.event.MenuDragMouseEvent;
/*     */ import javax.swing.event.MenuDragMouseListener;
/*     */ import javax.swing.event.MenuKeyEvent;
/*     */ import javax.swing.event.MenuKeyListener;
/*     */ import javax.swing.event.MenuListener;
/*     */ import javax.swing.event.MouseInputListener;
/*     */ import javax.swing.plaf.ComponentUI;
/*     */ import javax.swing.plaf.UIResource;
/*     */ 
/*     */ public class MetalMenuUI extends MetalMenuItemUI
/*     */ {
/*     */   protected ChangeListener changeListener;
/*     */   protected PropertyChangeListener propertyChangeListener;
/*     */   protected MenuListener menuListener;
/*     */   private int lastMnemonic;
/*     */   private InputMap selectedWindowInputMap;
/*     */   private static final boolean TRACE = false;
/*     */   private static final boolean VERBOSE = false;
/*     */   private static final boolean DEBUG = false;
/*  46 */   private static boolean crossMenuMnemonic = true;
/*     */   private boolean isMouseOver;
/*     */ 
/*     */   public MetalMenuUI()
/*     */   {
/*  34 */     this.lastMnemonic = 0;
/*     */ 
/*  48 */     this.isMouseOver = false;
/*     */   }
/*     */   public static ComponentUI createUI(JComponent x) {
/*  51 */     return new MetalMenuUI();
/*     */   }
/*     */ 
/*     */   protected void installDefaults()
/*     */   {
/*  56 */     super.installDefaults();
/*  57 */     updateDefaultBackgroundColor();
/*  58 */     ((JMenu)this.menuItem).setDelay(200);
/*  59 */     crossMenuMnemonic = UIDefaultsLookup.getBoolean("Menu.crossMenuMnemonic");
/*     */   }
/*     */ 
/*     */   protected String getPropertyPrefix()
/*     */   {
/*  64 */     return "Menu";
/*     */   }
/*     */ 
/*     */   protected void installListeners()
/*     */   {
/*  69 */     super.installListeners();
/*     */ 
/*  71 */     if (this.changeListener == null) {
/*  72 */       this.changeListener = createChangeListener(this.menuItem);
/*     */     }
/*  74 */     if (this.changeListener != null) {
/*  75 */       this.menuItem.addChangeListener(this.changeListener);
/*     */     }
/*  77 */     if (this.propertyChangeListener == null) {
/*  78 */       this.propertyChangeListener = createPropertyChangeListener(this.menuItem);
/*     */     }
/*  80 */     if (this.propertyChangeListener != null) {
/*  81 */       this.menuItem.addPropertyChangeListener(this.propertyChangeListener);
/*     */     }
/*  83 */     if (this.menuListener == null) {
/*  84 */       this.menuListener = createMenuListener(this.menuItem);
/*     */     }
/*  86 */     if (this.menuListener != null)
/*  87 */       ((JMenu)this.menuItem).addMenuListener(this.menuListener);
/*     */   }
/*     */ 
/*     */   protected void installKeyboardActions()
/*     */   {
/*  92 */     super.installKeyboardActions();
/*  93 */     updateMnemonicBinding();
/*     */   }
/*     */ 
/*     */   void updateMnemonicBinding() {
/*  97 */     int mnemonic = this.menuItem.getModel().getMnemonic();
/*  98 */     int[] shortcutKeys = (int[])(int[])UIDefaultsLookup.get("Menu.shortcutKeys");
/*  99 */     if ((mnemonic == this.lastMnemonic) || (shortcutKeys == null)) {
/* 100 */       return;
/*     */     }
/* 102 */     if ((this.lastMnemonic != 0) && (this.windowInputMap != null)) {
/* 103 */       for (int shortcutKey : shortcutKeys) {
/* 104 */         this.windowInputMap.remove(KeyStroke.getKeyStroke(this.lastMnemonic, shortcutKey, false));
/*     */       }
/*     */     }
/*     */ 
/* 108 */     if (mnemonic != 0) {
/* 109 */       if (this.windowInputMap == null) {
/* 110 */         this.windowInputMap = createInputMap(2);
/*     */ 
/* 112 */         SwingUtilities.replaceUIInputMap(this.menuItem, 2, this.windowInputMap);
/*     */       }
/*     */ 
/* 115 */       for (int shortcutKey : shortcutKeys) {
/* 116 */         this.windowInputMap.put(KeyStroke.getKeyStroke(mnemonic, shortcutKey, false), "selectMenu");
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 121 */     this.lastMnemonic = mnemonic;
/*     */   }
/*     */ 
/*     */   protected void uninstallKeyboardActions()
/*     */   {
/* 126 */     super.uninstallKeyboardActions();
/*     */   }
/*     */ 
/*     */   ActionMap getActionMap()
/*     */   {
/* 134 */     return createActionMap();
/*     */   }
/*     */ 
/*     */   ActionMap createActionMap()
/*     */   {
/* 142 */     ActionMap am = super.createActionMap();
/* 143 */     if (am != null) {
/* 144 */       am.put("selectMenu", new PostAction((JMenu)this.menuItem, true));
/*     */     }
/* 146 */     return am;
/*     */   }
/*     */ 
/*     */   protected MouseInputListener createMouseInputListener(JComponent c)
/*     */   {
/* 151 */     return new MouseInputHandler();
/*     */   }
/*     */ 
/*     */   protected MenuListener createMenuListener(JComponent c) {
/* 155 */     return null;
/*     */   }
/*     */ 
/*     */   protected ChangeListener createChangeListener(JComponent c) {
/* 159 */     return null;
/*     */   }
/*     */ 
/*     */   protected PropertyChangeListener createPropertyChangeListener(JComponent c) {
/* 163 */     return new PropertyChangeHandler(null);
/*     */   }
/*     */ 
/*     */   protected void uninstallDefaults()
/*     */   {
/* 168 */     this.menuItem.setArmed(false);
/* 169 */     this.menuItem.setSelected(false);
/* 170 */     this.menuItem.resetKeyboardActions();
/* 171 */     super.uninstallDefaults();
/*     */   }
/*     */ 
/*     */   protected void uninstallListeners()
/*     */   {
/* 176 */     super.uninstallListeners();
/*     */ 
/* 178 */     if (this.changeListener != null) {
/* 179 */       this.menuItem.removeChangeListener(this.changeListener);
/*     */     }
/* 181 */     if (this.propertyChangeListener != null) {
/* 182 */       this.menuItem.removePropertyChangeListener(this.propertyChangeListener);
/*     */     }
/* 184 */     if (this.menuListener != null) {
/* 185 */       ((JMenu)this.menuItem).removeMenuListener(this.menuListener);
/*     */     }
/* 187 */     this.changeListener = null;
/* 188 */     this.propertyChangeListener = null;
/* 189 */     this.menuListener = null;
/*     */   }
/*     */ 
/*     */   protected MenuDragMouseListener createMenuDragMouseListener(JComponent c)
/*     */   {
/* 194 */     return new MenuDragMouseHandler(null);
/*     */   }
/*     */ 
/*     */   protected MenuKeyListener createMenuKeyListener(JComponent c)
/*     */   {
/* 199 */     return new MenuKeyHandler(null);
/*     */   }
/*     */ 
/*     */   public Dimension getMaximumSize(JComponent c)
/*     */   {
/* 204 */     if (((JMenu)this.menuItem).isTopLevelMenu() == true) {
/* 205 */       Dimension d = c.getPreferredSize();
/* 206 */       return new Dimension(d.width, 32767);
/*     */     }
/* 208 */     return null;
/*     */   }
/*     */ 
/*     */   protected static void setupPostTimer(JMenu menu) {
/* 212 */     Timer timer = new Timer(menu.getDelay(), new PostAction(menu, false));
/* 213 */     timer.setRepeats(false);
/* 214 */     timer.start();
/*     */   }
/*     */ 
/*     */   protected static void appendPath(MenuElement[] path, MenuElement elem)
/*     */   {
/* 219 */     MenuElement[] newPath = new MenuElement[path.length + 1];
/* 220 */     System.arraycopy(path, 0, newPath, 0, path.length);
/* 221 */     newPath[path.length] = elem;
/* 222 */     MenuSelectionManager.defaultManager().setSelectedPath(newPath);
/*     */   }
/*     */ 
/*     */   private void updateDefaultBackgroundColor()
/*     */   {
/* 285 */     if (!UIDefaultsLookup.getBoolean("Menu.useMenuBarBackgroundForTopLevel")) {
/* 286 */       return;
/*     */     }
/* 288 */     JMenu menu = (JMenu)this.menuItem;
/* 289 */     if ((menu.getBackground() instanceof UIResource))
/* 290 */       if (menu.isTopLevelMenu()) {
/* 291 */         menu.setBackground(UIDefaultsLookup.getColor("MenuBar.background"));
/*     */       }
/*     */       else
/* 294 */         menu.setBackground(UIDefaultsLookup.getColor(getPropertyPrefix() + ".background"));
/*     */   }
/*     */ 
/*     */   static JPopupMenu getActivePopupMenu()
/*     */   {
/* 558 */     MenuElement[] path = MenuSelectionManager.defaultManager().getSelectedPath();
/*     */ 
/* 560 */     for (int i = path.length - 1; i >= 0; i--) {
/* 561 */       MenuElement elem = path[i];
/* 562 */       if ((elem instanceof JPopupMenu)) {
/* 563 */         return (JPopupMenu)elem;
/*     */       }
/*     */     }
/* 566 */     return null;
/*     */   }
/*     */ 
/*     */   protected void setMouseOver(boolean over)
/*     */   {
/* 704 */     this.isMouseOver = over;
/* 705 */     this.menuItem.getModel().setRollover(this.isMouseOver);
/*     */   }
/*     */ 
/*     */   protected boolean isMouseOver()
/*     */   {
/* 712 */     return this.isMouseOver;
/*     */   }
/*     */ 
/*     */   public Dimension getPreferredSize(JComponent c)
/*     */   {
/* 717 */     Dimension size = super.getPreferredSize(c);
/* 718 */     if (((this.menuItem instanceof JMenu)) && (((JMenu)this.menuItem).isTopLevelMenu()) && (isDownArrowVisible(this.menuItem.getParent())))
/*     */     {
/* 720 */       if (JideSwingUtilities.getOrientationOf(this.menuItem) == 0)
/* 721 */         size.width += 11;
/*     */       else
/* 723 */         size.height += 11;
/*     */     }
/* 725 */     return size;
/*     */   }
/*     */ 
/*     */   protected void paintBackground(Graphics g, JMenuItem menuItem, Color bgColor)
/*     */   {
/* 738 */     if ((!(menuItem instanceof JMenu)) || (!((JMenu)menuItem).isTopLevelMenu())) {
/* 739 */       super.paintBackground(g, menuItem, bgColor);
/* 740 */       return;
/*     */     }
/*     */ 
/* 743 */     Color oldColor = g.getColor();
/* 744 */     int menuWidth = 0;
/* 745 */     int menuHeight = 0;
/* 746 */     if (JideSwingUtilities.getOrientationOf(menuItem) == 0) {
/* 747 */       menuWidth = menuItem.getWidth();
/* 748 */       menuHeight = menuItem.getHeight();
/*     */     }
/*     */     else {
/* 751 */       menuWidth = menuItem.getHeight();
/* 752 */       menuHeight = menuItem.getWidth();
/*     */     }
/*     */ 
/* 755 */     if (menuItem.isOpaque()) {
/* 756 */       if ((menuItem.getModel().isArmed()) || (((menuItem instanceof JMenu)) && (menuItem.getModel().isSelected()))) {
/* 757 */         g.setColor(bgColor);
/* 758 */         g.fillRect(0, 0, menuWidth, menuHeight);
/*     */       }
/*     */       else {
/* 761 */         g.setColor(menuItem.getBackground());
/* 762 */         g.fillRect(0, 0, menuWidth, menuHeight);
/*     */       }
/* 764 */       g.setColor(oldColor);
/*     */     }
/*     */ 
/* 767 */     if (isDownArrowVisible(menuItem.getParent())) {
/* 768 */       g.setColor(Color.BLACK);
/* 769 */       int middle = menuWidth - 9;
/* 770 */       g.drawLine(middle - 2, menuHeight / 2 - 1, middle + 2, menuHeight / 2 - 1);
/* 771 */       g.drawLine(middle - 1, menuHeight / 2, middle + 1, menuHeight / 2);
/* 772 */       g.drawLine(middle, menuHeight / 2 + 1, middle, menuHeight / 2 + 1);
/*     */     }
/*     */   }
/*     */ 
/*     */   private class MenuKeyHandler
/*     */     implements MenuKeyListener
/*     */   {
/*     */     private MenuKeyHandler()
/*     */     {
/*     */     }
/*     */ 
/*     */     public void menuKeyTyped(MenuKeyEvent e)
/*     */     {
/* 581 */       if (!MetalMenuUI.crossMenuMnemonic) {
/* 582 */         JPopupMenu pm = MetalMenuUI.getActivePopupMenu();
/* 583 */         if ((pm != null) && (pm != MetalMenuUI.this.menuItem.getParent())) {
/* 584 */           return;
/*     */         }
/*     */       }
/*     */ 
/* 588 */       int key = MetalMenuUI.this.menuItem.getMnemonic();
/* 589 */       if (key == 0)
/* 590 */         return;
/* 591 */       MenuElement[] path = e.getPath();
/* 592 */       if (lower((char)key) == lower(e.getKeyChar())) {
/* 593 */         JPopupMenu popupMenu = ((JMenu)MetalMenuUI.this.menuItem).getPopupMenu();
/* 594 */         ArrayList newList = new ArrayList(Arrays.asList(path));
/* 595 */         newList.add(popupMenu);
/* 596 */         MenuElement[] sub = popupMenu.getSubElements();
/* 597 */         if (sub.length > 0) {
/* 598 */           newList.add(sub[0]);
/*     */         }
/* 600 */         MenuSelectionManager manager = e.getMenuSelectionManager();
/* 601 */         MenuElement[] newPath = new MenuElement[0];
/* 602 */         newPath = (MenuElement[])(MenuElement[])newList.toArray(newPath);
/* 603 */         manager.setSelectedPath(newPath);
/* 604 */         e.consume();
/*     */       }
/*     */     }
/*     */ 
/*     */     public void menuKeyPressed(MenuKeyEvent e)
/*     */     {
/* 617 */       char keyChar = e.getKeyChar();
/* 618 */       if (!Character.isLetterOrDigit(keyChar)) {
/* 619 */         return;
/*     */       }
/* 621 */       MenuSelectionManager manager = e.getMenuSelectionManager();
/* 622 */       MenuElement[] path = e.getPath();
/* 623 */       MenuElement[] selectedPath = manager.getSelectedPath();
/*     */ 
/* 625 */       for (int i = selectedPath.length - 1; i >= 0; i--)
/* 626 */         if (selectedPath[i] == MetalMenuUI.this.menuItem) {
/* 627 */           JPopupMenu popupMenu = ((JMenu)MetalMenuUI.this.menuItem).getPopupMenu();
/* 628 */           if (!popupMenu.isVisible()) {
/* 629 */             return;
/*     */           }
/* 631 */           MenuElement[] items = popupMenu.getSubElements();
/*     */ 
/* 633 */           MenuElement currentItem = selectedPath[(selectedPath.length - 1)];
/* 634 */           int currentIndex = -1;
/* 635 */           int matches = 0;
/* 636 */           int firstMatch = -1;
/* 637 */           int[] indexes = null;
/*     */ 
/* 639 */           for (int j = 0; j < items.length; j++) {
/* 640 */             int key = ((JMenuItem)items[j]).getMnemonic();
/* 641 */             if (lower((char)key) == lower(keyChar)) {
/* 642 */               if (matches == 0) {
/* 643 */                 firstMatch = j;
/* 644 */                 matches++;
/*     */               }
/*     */               else {
/* 647 */                 if (indexes == null) {
/* 648 */                   indexes = new int[items.length];
/* 649 */                   indexes[0] = firstMatch;
/*     */                 }
/* 651 */                 indexes[(matches++)] = j;
/*     */               }
/*     */             }
/* 654 */             if (currentItem == items[j]) {
/* 655 */               currentIndex = matches - 1;
/*     */             }
/*     */           }
/*     */ 
/* 659 */           if (matches != 0)
/*     */           {
/* 662 */             if (matches == 1)
/*     */             {
/* 664 */               JMenuItem item = (JMenuItem)items[firstMatch];
/* 665 */               if (!(item instanceof JMenu))
/*     */               {
/* 667 */                 manager.clearSelectedPath();
/* 668 */                 item.doClick();
/*     */               }
/*     */ 
/*     */             }
/*     */             else
/*     */             {
/* 675 */               MenuElement newItem = null;
/* 676 */               if (indexes != null) {
/* 677 */                 newItem = items[indexes[((currentIndex + 1) % matches)]];
/*     */               }
/*     */ 
/* 680 */               MenuElement[] newPath = new MenuElement[path.length + 2];
/* 681 */               System.arraycopy(path, 0, newPath, 0, path.length);
/* 682 */               newPath[path.length] = popupMenu;
/* 683 */               newPath[(path.length + 1)] = newItem;
/* 684 */               manager.setSelectedPath(newPath);
/*     */             }
/*     */           }
/* 686 */           e.consume();
/* 687 */           return;
/*     */         }
/*     */     }
/*     */ 
/*     */     public void menuKeyReleased(MenuKeyEvent e)
/*     */     {
/*     */     }
/*     */ 
/*     */     private char lower(char keyChar) {
/* 696 */       return Character.toLowerCase(keyChar);
/*     */     }
/*     */   }
/*     */ 
/*     */   private class MenuDragMouseHandler
/*     */     implements MenuDragMouseListener
/*     */   {
/*     */     private MenuDragMouseHandler()
/*     */     {
/*     */     }
/*     */ 
/*     */     public void menuDragMouseEntered(MenuDragMouseEvent e)
/*     */     {
/*     */     }
/*     */ 
/*     */     public void menuDragMouseDragged(MenuDragMouseEvent e)
/*     */     {
/* 517 */       if (!MetalMenuUI.this.menuItem.isEnabled()) {
/* 518 */         return;
/*     */       }
/* 520 */       MenuSelectionManager manager = e.getMenuSelectionManager();
/* 521 */       MenuElement[] path = e.getPath();
/*     */ 
/* 523 */       Point p = e.getPoint();
/* 524 */       if ((p.x >= 0) && (p.x < MetalMenuUI.this.menuItem.getWidth()) && (p.y >= 0) && (p.y < MetalMenuUI.this.menuItem.getHeight()))
/*     */       {
/* 526 */         JMenu menu = (JMenu)MetalMenuUI.this.menuItem;
/* 527 */         MenuElement[] selectedPath = manager.getSelectedPath();
/* 528 */         if ((selectedPath.length <= 0) || (selectedPath[(selectedPath.length - 1)] != menu.getPopupMenu()))
/*     */         {
/* 531 */           if ((menu.isTopLevelMenu()) || (menu.getDelay() == 0) || (e.getID() == 506))
/*     */           {
/* 534 */             MetalMenuUI.appendPath(path, menu.getPopupMenu());
/*     */           }
/*     */           else {
/* 537 */             manager.setSelectedPath(path);
/* 538 */             MetalMenuUI.setupPostTimer(menu);
/*     */           }
/*     */         }
/*     */       }
/* 542 */       else if (e.getID() == 502) {
/* 543 */         Component comp = manager.componentForPoint(e.getComponent(), e.getPoint());
/* 544 */         if (comp == null)
/* 545 */           manager.clearSelectedPath();
/*     */       }
/*     */     }
/*     */ 
/*     */     public void menuDragMouseExited(MenuDragMouseEvent e)
/*     */     {
/*     */     }
/*     */ 
/*     */     public void menuDragMouseReleased(MenuDragMouseEvent e)
/*     */     {
/*     */     }
/*     */   }
/*     */ 
/*     */   public class ChangeHandler
/*     */     implements ChangeListener
/*     */   {
/*     */     public JMenu menu;
/*     */     public MetalMenuUI ui;
/* 500 */     public boolean isSelected = false;
/*     */     public Component wasFocused;
/*     */ 
/*     */     public ChangeHandler(JMenu m, MetalMenuUI ui)
/*     */     {
/* 504 */       this.menu = m;
/* 505 */       this.ui = ui;
/*     */     }
/*     */ 
/*     */     public void stateChanged(ChangeEvent e)
/*     */     {
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
/*     */     }
/*     */ 
/*     */     public void mousePressed(MouseEvent e)
/*     */     {
/* 326 */       if (!(MetalMenuUI.this.menuItem instanceof JMenu)) {
/* 327 */         return;
/*     */       }
/*     */ 
/* 330 */       JMenu menu = (JMenu)MetalMenuUI.this.menuItem;
/* 331 */       if (!menu.isEnabled()) {
/* 332 */         return;
/*     */       }
/* 334 */       MenuSelectionManager manager = MenuSelectionManager.defaultManager();
/*     */ 
/* 336 */       if (((menu.getParent() instanceof JMenuBar)) || ((menu.getParent() instanceof TopLevelMenuContainer))) {
/* 337 */         if (menu.isSelected()) {
/* 338 */           manager.clearSelectedPath();
/*     */         }
/*     */         else
/*     */         {
/* 342 */           Container cnt = getFirstParentMenuElement(menu);
/*     */ 
/* 344 */           if ((cnt != null) && ((cnt instanceof MenuElement))) {
/* 345 */             ArrayList parents = new ArrayList();
/* 346 */             while ((cnt instanceof MenuElement)) {
/* 347 */               parents.add(0, cnt);
/* 348 */               if ((cnt instanceof JPopupMenu)) {
/* 349 */                 cnt = (Container)((JPopupMenu)cnt).getInvoker(); continue;
/*     */               }
/*     */ 
/* 353 */               cnt = getFirstParentMenuElement(cnt);
/*     */             }
/*     */ 
/* 357 */             MenuElement[] me = new MenuElement[parents.size() + 1];
/* 358 */             for (int i = 0; i < parents.size(); i++) {
/* 359 */               Container container = (Container)parents.get(i);
/* 360 */               me[i] = ((MenuElement)container);
/*     */             }
/* 362 */             me[parents.size()] = menu;
/* 363 */             manager.setSelectedPath(me);
/*     */           }
/*     */           else {
/* 366 */             MenuElement[] me = new MenuElement[1];
/* 367 */             me[0] = menu;
/* 368 */             manager.setSelectedPath(me);
/*     */           }
/*     */         }
/*     */       }
/*     */ 
/* 373 */       MenuElement[] selectedPath = manager.getSelectedPath();
/* 374 */       if ((selectedPath.length > 0) && (selectedPath[(selectedPath.length - 1)] != menu.getPopupMenu()))
/*     */       {
/* 377 */         if ((menu.isTopLevelMenu()) || (menu.getDelay() == 0))
/*     */         {
/* 379 */           MetalMenuUI.appendPath(selectedPath, menu.getPopupMenu());
/*     */         }
/*     */         else
/* 382 */           MetalMenuUI.setupPostTimer(menu);
/*     */       }
/*     */     }
/*     */ 
/*     */     protected Container getFirstParentMenuElement(Component comp)
/*     */     {
/* 388 */       Container parent = comp.getParent();
/*     */ 
/* 390 */       while (parent != null) {
/* 391 */         if ((parent instanceof MenuElement)) {
/* 392 */           return parent;
/*     */         }
/* 394 */         parent = parent.getParent();
/*     */       }
/*     */ 
/* 397 */       return null;
/*     */     }
/*     */ 
/*     */     public void mouseReleased(MouseEvent e)
/*     */     {
/* 407 */       if (!SwingUtilities.isLeftMouseButton(e)) {
/* 408 */         return;
/*     */       }
/*     */ 
/* 412 */       if (!(MetalMenuUI.this.menuItem instanceof JMenu)) {
/* 413 */         return;
/*     */       }
/* 415 */       JMenu menu = (JMenu)MetalMenuUI.this.menuItem;
/* 416 */       if (!menu.isEnabled())
/* 417 */         return;
/* 418 */       MenuSelectionManager manager = MenuSelectionManager.defaultManager();
/*     */ 
/* 420 */       manager.processMouseEvent(e);
/* 421 */       if (!e.isConsumed())
/* 422 */         manager.clearSelectedPath();
/*     */     }
/*     */ 
/*     */     public void mouseEntered(MouseEvent e)
/*     */     {
/* 434 */       if (!(MetalMenuUI.this.menuItem instanceof JMenu)) {
/* 435 */         return;
/*     */       }
/*     */ 
/* 438 */       JMenu menu = (JMenu)MetalMenuUI.this.menuItem;
/* 439 */       if (!menu.isEnabled()) {
/* 440 */         return;
/*     */       }
/* 442 */       MenuSelectionManager manager = MenuSelectionManager.defaultManager();
/*     */ 
/* 444 */       MenuElement[] selectedPath = manager.getSelectedPath();
/* 445 */       if (!menu.isTopLevelMenu()) {
/* 446 */         if ((selectedPath.length <= 0) || (selectedPath[(selectedPath.length - 1)] != menu.getPopupMenu()))
/*     */         {
/* 449 */           if (menu.getDelay() == 0) {
/* 450 */             MetalMenuUI.appendPath(MetalMenuUI.this.getPath(), menu.getPopupMenu());
/*     */           }
/*     */           else {
/* 453 */             manager.setSelectedPath(MetalMenuUI.this.getPath());
/* 454 */             MetalMenuUI.setupPostTimer(menu);
/*     */           }
/*     */         }
/*     */ 
/*     */       }
/* 459 */       else if ((selectedPath.length > 0) && (selectedPath[0] == menu.getParent()))
/*     */       {
/* 461 */         MenuElement[] newPath = new MenuElement[3];
/*     */ 
/* 464 */         newPath[0] = ((MenuElement)menu.getParent());
/* 465 */         newPath[1] = menu;
/* 466 */         newPath[2] = menu.getPopupMenu();
/* 467 */         manager.setSelectedPath(newPath);
/*     */       }
/*     */     }
/*     */ 
/*     */     public void mouseExited(MouseEvent e)
/*     */     {
/*     */     }
/*     */ 
/*     */     public void mouseDragged(MouseEvent e)
/*     */     {
/* 483 */       JMenu menu = (JMenu)MetalMenuUI.this.menuItem;
/* 484 */       if (!menu.isEnabled())
/* 485 */         return;
/* 486 */       MenuSelectionManager.defaultManager().processMouseEvent(e);
/*     */     }
/*     */ 
/*     */     public void mouseMoved(MouseEvent e)
/*     */     {
/*     */     }
/*     */   }
/*     */ 
/*     */   private class PropertyChangeHandler
/*     */     implements PropertyChangeListener
/*     */   {
/*     */     private PropertyChangeHandler()
/*     */     {
/*     */     }
/*     */ 
/*     */     public void propertyChange(PropertyChangeEvent e)
/*     */     {
/* 301 */       String prop = e.getPropertyName();
/* 302 */       if (prop.equals("mnemonic")) {
/* 303 */         MetalMenuUI.this.updateMnemonicBinding();
/*     */       }
/* 305 */       else if (prop.equals("ancestor"))
/* 306 */         MetalMenuUI.this.updateDefaultBackgroundColor();
/*     */     }
/*     */   }
/*     */ 
/*     */   private static class PostAction extends AbstractAction
/*     */   {
/*     */     JMenu menu;
/* 227 */     boolean force = false;
/*     */ 
/*     */     PostAction(JMenu menu, boolean shouldForce) {
/* 230 */       this.menu = menu;
/* 231 */       this.force = shouldForce;
/*     */     }
/*     */ 
/*     */     public void actionPerformed(ActionEvent e) {
/* 235 */       if (!MetalMenuUI.crossMenuMnemonic) {
/* 236 */         JPopupMenu pm = MetalMenuUI.getActivePopupMenu();
/* 237 */         if ((pm != null) && (pm != this.menu.getParent())) {
/* 238 */           return;
/*     */         }
/*     */       }
/*     */ 
/* 242 */       MenuSelectionManager defaultManager = MenuSelectionManager.defaultManager();
/* 243 */       if (this.force) {
/* 244 */         Container cnt = this.menu.getParent();
/* 245 */         if ((cnt != null) && ((cnt instanceof JMenuBar)))
/*     */         {
/* 249 */           MenuElement[] subElements = this.menu.getPopupMenu().getSubElements();
/*     */           MenuElement[] me;
/* 250 */           if (subElements.length > 0) {
/* 251 */             MenuElement[] me = new MenuElement[4];
/* 252 */             me[0] = ((MenuElement)cnt);
/* 253 */             me[1] = this.menu;
/* 254 */             me[2] = this.menu.getPopupMenu();
/* 255 */             me[3] = subElements[0];
/*     */           }
/*     */           else {
/* 258 */             me = new MenuElement[3];
/* 259 */             me[0] = ((MenuElement)cnt);
/* 260 */             me[1] = this.menu;
/* 261 */             me[2] = this.menu.getPopupMenu();
/*     */           }
/* 263 */           defaultManager.setSelectedPath(me);
/*     */         }
/*     */       }
/*     */       else {
/* 267 */         MenuElement[] path = defaultManager.getSelectedPath();
/* 268 */         if ((path.length > 0) && (path[(path.length - 1)] == this.menu))
/* 269 */           MetalMenuUI.appendPath(path, this.menu.getPopupMenu());
/*     */       }
/*     */     }
/*     */ 
/*     */     public boolean isEnabled()
/*     */     {
/* 276 */       return this.menu.getModel().isEnabled();
/*     */     }
/*     */   }
/*     */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     com.jidesoft.plaf.metal.MetalMenuUI
 * JD-Core Version:    0.6.0
 */