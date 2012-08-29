/*     */ package com.jidesoft.plaf.eclipse;
/*     */ 
/*     */ import com.jidesoft.plaf.UIDefaultsLookup;
/*     */ import com.jidesoft.swing.JideSwingUtilities;
/*     */ import com.jidesoft.swing.TopLevelMenuContainer;
/*     */ import com.sun.java.swing.plaf.windows.WindowsGraphicsUtils;
/*     */ import com.sun.java.swing.plaf.windows.WindowsLookAndFeel;
/*     */ import java.awt.Color;
/*     */ import java.awt.Component;
/*     */ import java.awt.ComponentOrientation;
/*     */ import java.awt.Container;
/*     */ import java.awt.Dimension;
/*     */ import java.awt.FontMetrics;
/*     */ import java.awt.Graphics;
/*     */ import java.awt.Point;
/*     */ import java.awt.Rectangle;
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
/*     */ import javax.swing.border.Border;
/*     */ import javax.swing.event.ChangeEvent;
/*     */ import javax.swing.event.ChangeListener;
/*     */ import javax.swing.event.MenuDragMouseEvent;
/*     */ import javax.swing.event.MenuDragMouseListener;
/*     */ import javax.swing.event.MenuEvent;
/*     */ import javax.swing.event.MenuKeyEvent;
/*     */ import javax.swing.event.MenuKeyListener;
/*     */ import javax.swing.event.MenuListener;
/*     */ import javax.swing.event.MouseInputListener;
/*     */ import javax.swing.plaf.ComponentUI;
/*     */ 
/*     */ public class EclipseMenuUI extends EclipseMenuItemUI
/*     */ {
/*     */   protected ChangeListener changeListener;
/*     */   protected PropertyChangeListener propertyChangeListener;
/*     */   protected MenuListener menuListener;
/*     */   private int lastMnemonic;
/*  44 */   private static boolean crossMenuMnemonic = true;
/*     */   private boolean isMouseOver;
/*     */ 
/*     */   public EclipseMenuUI()
/*     */   {
/*  37 */     this.lastMnemonic = 0;
/*     */ 
/*  46 */     this.isMouseOver = false;
/*     */   }
/*     */   public static ComponentUI createUI(JComponent x) {
/*  49 */     return new EclipseMenuUI();
/*     */   }
/*     */ 
/*     */   protected void installDefaults()
/*     */   {
/*  54 */     super.installDefaults();
/*  55 */     ((JMenu)this.menuItem).setDelay(200);
/*  56 */     crossMenuMnemonic = UIDefaultsLookup.getBoolean("Menu.crossMenuMnemonic");
/*     */   }
/*     */ 
/*     */   protected String getPropertyPrefix()
/*     */   {
/*  61 */     return "Menu";
/*     */   }
/*     */ 
/*     */   protected void paintBackground(Graphics g, JMenuItem menuItem, Color bgColor)
/*     */   {
/*  74 */     if ((!(menuItem instanceof JMenu)) || (!((JMenu)menuItem).isTopLevelMenu())) {
/*  75 */       super.paintBackground(g, menuItem, bgColor);
/*  76 */       return;
/*     */     }
/*     */ 
/*  79 */     ButtonModel model = menuItem.getModel();
/*  80 */     Color oldColor = g.getColor();
/*  81 */     int menuWidth = 0;
/*  82 */     int menuHeight = 0;
/*  83 */     if (JideSwingUtilities.getOrientationOf(menuItem) == 0) {
/*  84 */       menuWidth = menuItem.getWidth();
/*  85 */       menuHeight = menuItem.getHeight();
/*     */     }
/*     */     else {
/*  88 */       menuWidth = menuItem.getHeight();
/*  89 */       menuHeight = menuItem.getWidth();
/*     */     }
/*     */ 
/*  92 */     Color darkShadow = UIDefaultsLookup.getColor("controlDkShadow");
/*  93 */     Color mouseHoverBackground = UIDefaultsLookup.getColor("Menu.mouseHoverBackground");
/*  94 */     Border mouseHoverBorder = UIDefaultsLookup.getBorder("Menu.mouseHoverBorder");
/*  95 */     Border mouseSelectedBorder = UIDefaultsLookup.getBorder("Menu.mouseSelectedBorder");
/*     */ 
/*  97 */     if (menuItem.isOpaque()) {
/*  98 */       if (menuItem.getParent() != null) {
/*  99 */         g.setColor(menuItem.getParent().getBackground());
/*     */       }
/*     */       else {
/* 102 */         g.setColor(menuItem.getBackground());
/*     */       }
/* 104 */       g.fillRect(0, 0, menuWidth, menuHeight);
/*     */     }
/*     */ 
/* 108 */     if ((model.isArmed()) || (((menuItem instanceof JMenu)) && (model.isSelected())))
/*     */     {
/* 110 */       if (mouseSelectedBorder != null) {
/* 111 */         mouseSelectedBorder.paintBorder(menuItem, g, 0, 0, menuWidth - 1, menuHeight);
/*     */       }
/* 113 */       g.setColor(mouseHoverBackground);
/* 114 */       g.fillRect(1, 1, menuWidth - 3, menuHeight - 2);
/*     */     }
/* 117 */     else if ((isMouseOver()) && (model.isEnabled()))
/*     */     {
/* 119 */       if (mouseHoverBorder != null) {
/* 120 */         mouseHoverBorder.paintBorder(menuItem, g, 0, 0, menuWidth - 1, menuHeight);
/*     */       }
/* 122 */       g.setColor(mouseHoverBackground);
/* 123 */       g.fillRect(1, 1, menuWidth - 3, menuHeight - 2);
/*     */     }
/*     */ 
/* 133 */     if (isDownArrowVisible(menuItem.getParent())) {
/* 134 */       int middle = menuWidth - 9;
/* 135 */       if ((model.isSelected()) || (model.isArmed()) || (model.isPressed()) || (isMouseOver())) {
/* 136 */         JideSwingUtilities.paintArrow(g, this.selectionForeground, middle - 2, menuHeight / 2 - 1, 5, 0);
/*     */       }
/*     */       else {
/* 139 */         JideSwingUtilities.paintArrow(g, menuItem.getForeground(), middle - 2, menuHeight / 2 - 1, 5, 0);
/*     */       }
/*     */     }
/* 142 */     g.setColor(oldColor);
/*     */   }
/*     */ 
/*     */   protected void installListeners()
/*     */   {
/* 147 */     super.installListeners();
/*     */ 
/* 149 */     if (this.changeListener == null) {
/* 150 */       this.changeListener = createChangeListener(this.menuItem);
/*     */     }
/* 152 */     if (this.changeListener != null) {
/* 153 */       this.menuItem.addChangeListener(this.changeListener);
/*     */     }
/* 155 */     if (this.propertyChangeListener == null) {
/* 156 */       this.propertyChangeListener = createPropertyChangeListener(this.menuItem);
/*     */     }
/* 158 */     if (this.propertyChangeListener != null) {
/* 159 */       this.menuItem.addPropertyChangeListener(this.propertyChangeListener);
/*     */     }
/* 161 */     if (this.menuListener == null) {
/* 162 */       this.menuListener = createMenuListener(this.menuItem);
/*     */     }
/* 164 */     if (this.menuListener != null)
/* 165 */       ((JMenu)this.menuItem).addMenuListener(this.menuListener);
/*     */   }
/*     */ 
/*     */   protected void installKeyboardActions()
/*     */   {
/* 170 */     super.installKeyboardActions();
/* 171 */     updateMnemonicBinding();
/*     */   }
/*     */ 
/*     */   void updateMnemonicBinding() {
/* 175 */     int mnemonic = this.menuItem.getModel().getMnemonic();
/* 176 */     int[] shortcutKeys = (int[])(int[])UIDefaultsLookup.get("Menu.shortcutKeys");
/* 177 */     if ((mnemonic == this.lastMnemonic) || (shortcutKeys == null)) {
/* 178 */       return;
/*     */     }
/* 180 */     if ((this.lastMnemonic != 0) && (this.windowInputMap != null)) {
/* 181 */       for (int shortcutKey : shortcutKeys) {
/* 182 */         this.windowInputMap.remove(KeyStroke.getKeyStroke(this.lastMnemonic, shortcutKey, false));
/*     */       }
/*     */     }
/*     */ 
/* 186 */     if (mnemonic != 0) {
/* 187 */       if (this.windowInputMap == null) {
/* 188 */         this.windowInputMap = createInputMap(2);
/*     */ 
/* 190 */         SwingUtilities.replaceUIInputMap(this.menuItem, 2, this.windowInputMap);
/*     */       }
/*     */ 
/* 193 */       for (int shortcutKey : shortcutKeys) {
/* 194 */         this.windowInputMap.put(KeyStroke.getKeyStroke(mnemonic, shortcutKey, false), "selectMenu");
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 199 */     this.lastMnemonic = mnemonic;
/*     */   }
/*     */ 
/*     */   protected void uninstallKeyboardActions()
/*     */   {
/* 204 */     super.uninstallKeyboardActions();
/*     */   }
/*     */ 
/*     */   ActionMap getActionMap()
/*     */   {
/* 212 */     return createActionMap();
/*     */   }
/*     */ 
/*     */   ActionMap createActionMap()
/*     */   {
/* 220 */     ActionMap am = super.createActionMap();
/* 221 */     if (am != null) {
/* 222 */       am.put("selectMenu", new PostAction((JMenu)this.menuItem, true));
/*     */     }
/* 224 */     return am;
/*     */   }
/*     */ 
/*     */   protected MouseInputListener createMouseInputListener(JComponent c)
/*     */   {
/* 229 */     return new MouseInputHandler();
/*     */   }
/*     */ 
/*     */   protected MenuListener createMenuListener(JComponent c) {
/* 233 */     return new MenuHandler(null);
/*     */   }
/*     */ 
/*     */   protected ChangeListener createChangeListener(JComponent c) {
/* 237 */     return null;
/*     */   }
/*     */ 
/*     */   protected PropertyChangeListener createPropertyChangeListener(JComponent c) {
/* 241 */     return new PropertyChangeHandler(null);
/*     */   }
/*     */ 
/*     */   protected void uninstallDefaults()
/*     */   {
/* 246 */     this.menuItem.setArmed(false);
/* 247 */     this.menuItem.setSelected(false);
/* 248 */     this.menuItem.resetKeyboardActions();
/* 249 */     super.uninstallDefaults();
/*     */   }
/*     */ 
/*     */   protected void uninstallListeners()
/*     */   {
/* 254 */     super.uninstallListeners();
/*     */ 
/* 256 */     if (this.changeListener != null) {
/* 257 */       this.menuItem.removeChangeListener(this.changeListener);
/*     */     }
/* 259 */     if (this.propertyChangeListener != null) {
/* 260 */       this.menuItem.removePropertyChangeListener(this.propertyChangeListener);
/*     */     }
/* 262 */     if (this.menuListener != null) {
/* 263 */       ((JMenu)this.menuItem).removeMenuListener(this.menuListener);
/*     */     }
/* 265 */     this.changeListener = null;
/* 266 */     this.propertyChangeListener = null;
/* 267 */     this.menuListener = null;
/*     */   }
/*     */ 
/*     */   protected MenuDragMouseListener createMenuDragMouseListener(JComponent c)
/*     */   {
/* 272 */     return new MenuDragMouseHandler(null);
/*     */   }
/*     */ 
/*     */   protected MenuKeyListener createMenuKeyListener(JComponent c)
/*     */   {
/* 277 */     return new MenuKeyHandler(null);
/*     */   }
/*     */ 
/*     */   public Dimension getMaximumSize(JComponent c)
/*     */   {
/* 282 */     if (((JMenu)this.menuItem).isTopLevelMenu() == true) {
/* 283 */       Dimension d = c.getPreferredSize();
/* 284 */       return new Dimension(d.width, 32767);
/*     */     }
/* 286 */     return null;
/*     */   }
/*     */ 
/*     */   public static void setupPostTimer(JMenu menu) {
/* 290 */     Timer timer = new Timer(menu.getDelay(), new PostAction(menu, false));
/* 291 */     timer.setRepeats(false);
/* 292 */     timer.start();
/*     */   }
/*     */ 
/*     */   protected static void appendPath(MenuElement[] path, MenuElement elem) {
/* 296 */     MenuElement[] newPath = new MenuElement[path.length + 1];
/* 297 */     System.arraycopy(path, 0, newPath, 0, path.length);
/* 298 */     newPath[path.length] = elem;
/* 299 */     MenuSelectionManager.defaultManager().setSelectedPath(newPath);
/*     */   }
/*     */ 
/*     */   static JPopupMenu getActivePopupMenu()
/*     */   {
/* 634 */     MenuElement[] path = MenuSelectionManager.defaultManager().getSelectedPath();
/*     */ 
/* 636 */     for (int i = path.length - 1; i >= 0; i--) {
/* 637 */       MenuElement elem = path[i];
/* 638 */       if ((elem instanceof JPopupMenu)) {
/* 639 */         return (JPopupMenu)elem;
/*     */       }
/*     */     }
/* 642 */     return null;
/*     */   }
/*     */ 
/*     */   protected void paintText(Graphics g, JMenuItem menuItem, Rectangle textRect, String text)
/*     */   {
/* 789 */     ButtonModel model = menuItem.getModel();
/*     */ 
/* 791 */     if (((!(menuItem instanceof JMenu)) || (!((JMenu)menuItem).isTopLevelMenu())) && 
/* 792 */       (menuItem.getComponentOrientation().isLeftToRight())) {
/* 793 */       int defaultTextIconGap = UIDefaultsLookup.getInt("MenuItem.textIconGap");
/* 794 */       int defaultShadowWidth = UIDefaultsLookup.getInt("MenuItem.shadowWidth");
/* 795 */       textRect.x = (defaultShadowWidth + defaultTextIconGap);
/*     */     }
/*     */ 
/* 802 */     if (!model.isEnabled())
/*     */     {
/* 804 */       textRect.y += 1;
/* 805 */       WindowsGraphicsUtils.paintText(g, menuItem, textRect, text, 0);
/*     */     }
/*     */     else {
/* 808 */       FontMetrics fm = g.getFontMetrics();
/* 809 */       int mnemonicIndex = menuItem.getDisplayedMnemonicIndex();
/*     */ 
/* 811 */       if (WindowsLookAndFeel.isMnemonicHidden()) {
/* 812 */         mnemonicIndex = -1;
/*     */       }
/*     */ 
/* 815 */       Color oldColor = g.getColor();
/*     */ 
/* 817 */       if ((model.isArmed()) || (((menuItem instanceof JMenu)) && (model.isSelected())) || (isMouseOver())) {
/* 818 */         g.setColor(this.selectionForeground);
/*     */       }
/* 820 */       JideSwingUtilities.drawStringUnderlineCharAt(menuItem, g, text, mnemonicIndex, textRect.x, textRect.y + fm.getAscent() - 1);
/*     */ 
/* 824 */       g.setColor(oldColor);
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void setMouseOver(boolean over)
/*     */   {
/* 832 */     this.isMouseOver = over;
/* 833 */     this.menuItem.getModel().setRollover(this.isMouseOver);
/*     */   }
/*     */ 
/*     */   protected boolean isMouseOver()
/*     */   {
/* 840 */     return this.isMouseOver;
/*     */   }
/*     */ 
/*     */   public Dimension getPreferredSize(JComponent c)
/*     */   {
/* 845 */     Dimension size = super.getPreferredSize(c);
/* 846 */     if (((this.menuItem instanceof JMenu)) && (((JMenu)this.menuItem).isTopLevelMenu()) && (isDownArrowVisible(this.menuItem.getParent())))
/*     */     {
/* 848 */       if (JideSwingUtilities.getOrientationOf(this.menuItem) == 0)
/* 849 */         size.width += 11;
/*     */       else
/* 851 */         size.height += 11;
/*     */     }
/* 853 */     return size;
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
/* 657 */       if (!EclipseMenuUI.crossMenuMnemonic) {
/* 658 */         JPopupMenu pm = EclipseMenuUI.getActivePopupMenu();
/* 659 */         if ((pm != null) && (pm != EclipseMenuUI.this.menuItem.getParent())) {
/* 660 */           return;
/*     */         }
/*     */       }
/*     */ 
/* 664 */       int key = EclipseMenuUI.this.menuItem.getMnemonic();
/* 665 */       if (key == 0)
/* 666 */         return;
/* 667 */       MenuElement[] path = e.getPath();
/* 668 */       if (lower((char)key) == lower(e.getKeyChar())) {
/* 669 */         JPopupMenu popupMenu = ((JMenu)EclipseMenuUI.this.menuItem).getPopupMenu();
/* 670 */         ArrayList newList = new ArrayList(Arrays.asList(path));
/* 671 */         newList.add(popupMenu);
/* 672 */         MenuElement[] sub = popupMenu.getSubElements();
/* 673 */         if (sub.length > 0) {
/* 674 */           newList.add(sub[0]);
/*     */         }
/* 676 */         MenuSelectionManager manager = e.getMenuSelectionManager();
/* 677 */         MenuElement[] newPath = new MenuElement[0];
/* 678 */         newPath = (MenuElement[])(MenuElement[])newList.toArray(newPath);
/* 679 */         manager.setSelectedPath(newPath);
/* 680 */         e.consume();
/*     */       }
/*     */     }
/*     */ 
/*     */     public void menuKeyPressed(MenuKeyEvent e)
/*     */     {
/* 693 */       char keyChar = e.getKeyChar();
/* 694 */       if (!Character.isLetterOrDigit(keyChar)) {
/* 695 */         return;
/*     */       }
/* 697 */       MenuSelectionManager manager = e.getMenuSelectionManager();
/* 698 */       MenuElement[] path = e.getPath();
/* 699 */       MenuElement[] selectedPath = manager.getSelectedPath();
/*     */ 
/* 701 */       for (int i = selectedPath.length - 1; i >= 0; i--)
/* 702 */         if (selectedPath[i] == EclipseMenuUI.this.menuItem) {
/* 703 */           JPopupMenu popupMenu = ((JMenu)EclipseMenuUI.this.menuItem).getPopupMenu();
/* 704 */           if (!popupMenu.isVisible()) {
/* 705 */             return;
/*     */           }
/* 707 */           MenuElement[] items = popupMenu.getSubElements();
/*     */ 
/* 709 */           MenuElement currentItem = selectedPath[(selectedPath.length - 1)];
/* 710 */           int currentIndex = -1;
/* 711 */           int matches = 0;
/* 712 */           int firstMatch = -1;
/* 713 */           int[] indexes = null;
/*     */ 
/* 715 */           for (int j = 0; j < items.length; j++) {
/* 716 */             int key = ((JMenuItem)items[j]).getMnemonic();
/* 717 */             if (lower((char)key) == lower(keyChar)) {
/* 718 */               if (matches == 0) {
/* 719 */                 firstMatch = j;
/* 720 */                 matches++;
/*     */               }
/*     */               else {
/* 723 */                 if (indexes == null) {
/* 724 */                   indexes = new int[items.length];
/* 725 */                   indexes[0] = firstMatch;
/*     */                 }
/* 727 */                 indexes[(matches++)] = j;
/*     */               }
/*     */             }
/* 730 */             if (currentItem == items[j]) {
/* 731 */               currentIndex = matches - 1;
/*     */             }
/*     */           }
/*     */ 
/* 735 */           if (matches != 0)
/*     */           {
/* 738 */             if (matches == 1)
/*     */             {
/* 740 */               JMenuItem item = (JMenuItem)items[firstMatch];
/* 741 */               if (!(item instanceof JMenu))
/*     */               {
/* 743 */                 manager.clearSelectedPath();
/* 744 */                 item.doClick();
/*     */               }
/*     */ 
/*     */             }
/*     */             else
/*     */             {
/* 751 */               MenuElement newItem = null;
/* 752 */               if (indexes != null) {
/* 753 */                 newItem = items[indexes[((currentIndex + 1) % matches)]];
/*     */               }
/*     */ 
/* 756 */               MenuElement[] newPath = new MenuElement[path.length + 2];
/* 757 */               System.arraycopy(path, 0, newPath, 0, path.length);
/* 758 */               newPath[path.length] = popupMenu;
/* 759 */               newPath[(path.length + 1)] = newItem;
/* 760 */               manager.setSelectedPath(newPath);
/*     */             }
/*     */           }
/* 762 */           e.consume();
/* 763 */           return;
/*     */         }
/*     */     }
/*     */ 
/*     */     public void menuKeyReleased(MenuKeyEvent e)
/*     */     {
/*     */     }
/*     */ 
/*     */     private char lower(char keyChar) {
/* 772 */       return Character.toLowerCase(keyChar);
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
/* 590 */       if (!(EclipseMenuUI.this.menuItem instanceof JMenu)) {
/* 591 */         return;
/*     */       }
/* 593 */       if (!EclipseMenuUI.this.menuItem.isEnabled()) {
/* 594 */         return;
/*     */       }
/* 596 */       MenuSelectionManager manager = e.getMenuSelectionManager();
/* 597 */       MenuElement[] path = e.getPath();
/*     */ 
/* 599 */       Point p = e.getPoint();
/* 600 */       if ((p.x >= 0) && (p.x < EclipseMenuUI.this.menuItem.getWidth()) && (p.y >= 0) && (p.y < EclipseMenuUI.this.menuItem.getHeight()))
/*     */       {
/* 602 */         JMenu menu = (JMenu)EclipseMenuUI.this.menuItem;
/* 603 */         MenuElement[] selectedPath = manager.getSelectedPath();
/* 604 */         if ((selectedPath.length <= 0) || (selectedPath[(selectedPath.length - 1)] != menu.getPopupMenu()))
/*     */         {
/* 607 */           if ((menu.isTopLevelMenu()) || (menu.getDelay() == 0) || (e.getID() == 506))
/*     */           {
/* 610 */             EclipseMenuUI.appendPath(path, menu.getPopupMenu());
/*     */           }
/*     */           else {
/* 613 */             manager.setSelectedPath(path);
/* 614 */             EclipseMenuUI.setupPostTimer(menu);
/*     */           }
/*     */         }
/*     */       }
/* 618 */       else if (e.getID() == 502) {
/* 619 */         Component comp = manager.componentForPoint(e.getComponent(), e.getPoint());
/* 620 */         if (comp == null)
/* 621 */           manager.clearSelectedPath();
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
/*     */     public EclipseMenuUI ui;
/* 573 */     public boolean isSelected = false;
/*     */     public Component wasFocused;
/*     */ 
/*     */     public ChangeHandler(JMenu m, EclipseMenuUI ui)
/*     */     {
/* 577 */       this.menu = m;
/* 578 */       this.ui = ui;
/*     */     }
/*     */ 
/*     */     public void stateChanged(ChangeEvent e)
/*     */     {
/*     */     }
/*     */   }
/*     */ 
/*     */   private static class MenuHandler
/*     */     implements MenuListener
/*     */   {
/*     */     public void menuSelected(MenuEvent e)
/*     */     {
/*     */     }
/*     */ 
/*     */     public void menuDeselected(MenuEvent e)
/*     */     {
/*     */     }
/*     */ 
/*     */     public void menuCanceled(MenuEvent e)
/*     */     {
/* 558 */       JMenu m = (JMenu)e.getSource();
/* 559 */       MenuSelectionManager manager = MenuSelectionManager.defaultManager();
/* 560 */       if (manager.isComponentPartOfCurrentMenu(m))
/* 561 */         MenuSelectionManager.defaultManager().clearSelectedPath();
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
/* 382 */       if (!(EclipseMenuUI.this.menuItem instanceof JMenu)) {
/* 383 */         return;
/*     */       }
/*     */ 
/* 386 */       JMenu menu = (JMenu)EclipseMenuUI.this.menuItem;
/* 387 */       if (!menu.isEnabled()) {
/* 388 */         return;
/*     */       }
/* 390 */       MenuSelectionManager manager = MenuSelectionManager.defaultManager();
/*     */ 
/* 392 */       if (((menu.getParent() instanceof JMenuBar)) || ((menu.getParent() instanceof TopLevelMenuContainer))) {
/* 393 */         if (menu.isSelected()) {
/* 394 */           manager.clearSelectedPath();
/*     */         }
/*     */         else
/*     */         {
/* 398 */           Container cnt = getFirstParentMenuElement(menu);
/*     */ 
/* 400 */           if ((cnt != null) && ((cnt instanceof MenuElement))) {
/* 401 */             ArrayList parents = new ArrayList();
/* 402 */             while ((cnt instanceof MenuElement)) {
/* 403 */               parents.add(0, cnt);
/* 404 */               if ((cnt instanceof JPopupMenu)) {
/* 405 */                 cnt = (Container)((JPopupMenu)cnt).getInvoker(); continue;
/*     */               }
/*     */ 
/* 409 */               cnt = getFirstParentMenuElement(cnt);
/*     */             }
/*     */ 
/* 413 */             MenuElement[] me = new MenuElement[parents.size() + 1];
/* 414 */             for (int i = 0; i < parents.size(); i++) {
/* 415 */               Container container = (Container)parents.get(i);
/* 416 */               me[i] = ((MenuElement)container);
/*     */             }
/* 418 */             me[parents.size()] = menu;
/* 419 */             manager.setSelectedPath(me);
/*     */           }
/*     */           else {
/* 422 */             MenuElement[] me = new MenuElement[1];
/* 423 */             me[0] = menu;
/* 424 */             manager.setSelectedPath(me);
/*     */           }
/*     */         }
/*     */       }
/*     */ 
/* 429 */       MenuElement[] selectedPath = manager.getSelectedPath();
/* 430 */       if ((selectedPath.length > 0) && (selectedPath[(selectedPath.length - 1)] != menu.getPopupMenu()))
/*     */       {
/* 433 */         if ((menu.isTopLevelMenu()) || (menu.getDelay() == 0))
/*     */         {
/* 435 */           EclipseMenuUI.appendPath(selectedPath, menu.getPopupMenu());
/*     */         }
/*     */         else
/* 438 */           EclipseMenuUI.setupPostTimer(menu);
/*     */       }
/*     */     }
/*     */ 
/*     */     protected Container getFirstParentMenuElement(Component comp)
/*     */     {
/* 444 */       Container parent = comp.getParent();
/*     */ 
/* 446 */       while (parent != null) {
/* 447 */         if ((parent instanceof MenuElement)) {
/* 448 */           return parent;
/*     */         }
/* 450 */         parent = parent.getParent();
/*     */       }
/*     */ 
/* 453 */       return null;
/*     */     }
/*     */ 
/*     */     public void mouseReleased(MouseEvent e)
/*     */     {
/* 462 */       if (!(EclipseMenuUI.this.menuItem instanceof JMenu)) {
/* 463 */         return;
/*     */       }
/*     */ 
/* 466 */       JMenu menu = (JMenu)EclipseMenuUI.this.menuItem;
/* 467 */       if (!menu.isEnabled())
/* 468 */         return;
/* 469 */       MenuSelectionManager manager = MenuSelectionManager.defaultManager();
/*     */ 
/* 471 */       manager.processMouseEvent(e);
/* 472 */       if (!e.isConsumed())
/* 473 */         manager.clearSelectedPath();
/*     */     }
/*     */ 
/*     */     public void mouseEntered(MouseEvent e)
/*     */     {
/* 484 */       if (!(EclipseMenuUI.this.menuItem instanceof JMenu)) {
/* 485 */         return;
/*     */       }
/* 487 */       JMenu menu = (JMenu)EclipseMenuUI.this.menuItem;
/* 488 */       if (!menu.isEnabled()) {
/* 489 */         return;
/*     */       }
/* 491 */       MenuSelectionManager manager = MenuSelectionManager.defaultManager();
/*     */ 
/* 493 */       MenuElement[] selectedPath = manager.getSelectedPath();
/* 494 */       if (!menu.isTopLevelMenu()) {
/* 495 */         if ((selectedPath.length <= 0) || (selectedPath[(selectedPath.length - 1)] != menu.getPopupMenu()))
/*     */         {
/* 498 */           if (menu.getDelay() == 0) {
/* 499 */             EclipseMenuUI.appendPath(EclipseMenuUI.this.getPath(), menu.getPopupMenu());
/*     */           }
/*     */           else {
/* 502 */             manager.setSelectedPath(EclipseMenuUI.this.getPath());
/* 503 */             EclipseMenuUI.setupPostTimer(menu);
/*     */           }
/*     */         }
/*     */ 
/*     */       }
/* 508 */       else if ((selectedPath.length > 0) && (selectedPath[0] == menu.getParent()))
/*     */       {
/* 510 */         MenuElement[] newPath = new MenuElement[3];
/*     */ 
/* 513 */         newPath[0] = ((MenuElement)menu.getParent());
/* 514 */         newPath[1] = menu;
/* 515 */         newPath[2] = menu.getPopupMenu();
/* 516 */         manager.setSelectedPath(newPath);
/*     */       }
/*     */ 
/* 520 */       EclipseMenuUI.this.setMouseOver(true);
/* 521 */       EclipseMenuUI.this.menuItem.repaint();
/*     */     }
/*     */ 
/*     */     public void mouseExited(MouseEvent e) {
/* 525 */       EclipseMenuUI.this.setMouseOver(false);
/* 526 */       EclipseMenuUI.this.menuItem.repaint();
/*     */     }
/*     */ 
/*     */     public void mouseDragged(MouseEvent e)
/*     */     {
/* 537 */       if (!(EclipseMenuUI.this.menuItem instanceof JMenu)) {
/* 538 */         return;
/*     */       }
/* 540 */       JMenu menu = (JMenu)EclipseMenuUI.this.menuItem;
/* 541 */       if (!menu.isEnabled())
/* 542 */         return;
/* 543 */       MenuSelectionManager.defaultManager().processMouseEvent(e);
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
/* 360 */       String prop = e.getPropertyName();
/* 361 */       if (prop.equals("mnemonic"))
/* 362 */         EclipseMenuUI.this.updateMnemonicBinding();
/*     */     }
/*     */   }
/*     */ 
/*     */   private static class PostAction extends AbstractAction
/*     */   {
/*     */     JMenu menu;
/* 304 */     boolean force = false;
/*     */ 
/*     */     PostAction(JMenu menu, boolean shouldForce) {
/* 307 */       this.menu = menu;
/* 308 */       this.force = shouldForce;
/*     */     }
/*     */ 
/*     */     public void actionPerformed(ActionEvent e) {
/* 312 */       if (!EclipseMenuUI.crossMenuMnemonic) {
/* 313 */         JPopupMenu pm = EclipseMenuUI.getActivePopupMenu();
/* 314 */         if ((pm != null) && (pm != this.menu.getParent())) {
/* 315 */           return;
/*     */         }
/*     */       }
/*     */ 
/* 319 */       MenuSelectionManager defaultManager = MenuSelectionManager.defaultManager();
/* 320 */       if (this.force) {
/* 321 */         Container cnt = this.menu.getParent();
/* 322 */         if ((cnt != null) && ((cnt instanceof JMenuBar)))
/*     */         {
/* 326 */           MenuElement[] subElements = this.menu.getPopupMenu().getSubElements();
/*     */           MenuElement[] me;
/* 327 */           if (subElements.length > 0) {
/* 328 */             MenuElement[] me = new MenuElement[4];
/* 329 */             me[0] = ((MenuElement)cnt);
/* 330 */             me[1] = this.menu;
/* 331 */             me[2] = this.menu.getPopupMenu();
/* 332 */             me[3] = subElements[0];
/*     */           }
/*     */           else {
/* 335 */             me = new MenuElement[3];
/* 336 */             me[0] = ((MenuElement)cnt);
/* 337 */             me[1] = this.menu;
/* 338 */             me[2] = this.menu.getPopupMenu();
/*     */           }
/* 340 */           defaultManager.setSelectedPath(me);
/*     */         }
/*     */       }
/*     */       else {
/* 344 */         MenuElement[] path = defaultManager.getSelectedPath();
/* 345 */         if ((path.length > 0) && (path[(path.length - 1)] == this.menu))
/* 346 */           EclipseMenuUI.appendPath(path, this.menu.getPopupMenu());
/*     */       }
/*     */     }
/*     */ 
/*     */     public boolean isEnabled()
/*     */     {
/* 353 */       return this.menu.getModel().isEnabled();
/*     */     }
/*     */   }
/*     */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     com.jidesoft.plaf.eclipse.EclipseMenuUI
 * JD-Core Version:    0.6.0
 */