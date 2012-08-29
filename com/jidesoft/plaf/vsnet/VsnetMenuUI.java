/*     */ package com.jidesoft.plaf.vsnet;
/*     */ 
/*     */ import com.jidesoft.plaf.UIDefaultsLookup;
/*     */ import com.jidesoft.plaf.basic.ThemePainter;
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
/*     */ import javax.swing.AbstractButton;
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
/*     */ import javax.swing.event.ChangeListener;
/*     */ import javax.swing.event.MenuDragMouseEvent;
/*     */ import javax.swing.event.MenuDragMouseListener;
/*     */ import javax.swing.event.MenuEvent;
/*     */ import javax.swing.event.MenuKeyEvent;
/*     */ import javax.swing.event.MenuKeyListener;
/*     */ import javax.swing.event.MenuListener;
/*     */ import javax.swing.event.MouseInputListener;
/*     */ import javax.swing.event.PopupMenuEvent;
/*     */ import javax.swing.event.PopupMenuListener;
/*     */ import javax.swing.plaf.ComponentUI;
/*     */ 
/*     */ public class VsnetMenuUI extends VsnetMenuItemUI
/*     */ {
/*     */   protected ChangeListener changeListener;
/*     */   protected PropertyChangeListener propertyChangeListener;
/*     */   protected MenuListener menuListener;
/*     */   private int lastMnemonic;
/*     */   private static final boolean DEBUG = false;
/*  43 */   private static boolean crossMenuMnemonic = true;
/*     */   private boolean isMouseOver;
/*     */   private PopupMenuListener popupMenuListener;
/*     */ 
/*     */   public VsnetMenuUI()
/*     */   {
/*  39 */     this.lastMnemonic = 0;
/*     */ 
/*  45 */     this.isMouseOver = false;
/*     */   }
/*     */ 
/*     */   public static ComponentUI createUI(JComponent x) {
/*  49 */     return new VsnetMenuUI();
/*     */   }
/*     */ 
/*     */   protected void installDefaults()
/*     */   {
/*  54 */     super.installDefaults();
/*  55 */     if ((this.menuItem instanceof JMenu)) {
/*  56 */       ((JMenu)this.menuItem).setDelay(200);
/*     */     }
/*  58 */     crossMenuMnemonic = UIDefaultsLookup.getBoolean("Menu.crossMenuMnemonic");
/*     */   }
/*     */ 
/*     */   protected String getPropertyPrefix()
/*     */   {
/*  63 */     return "Menu";
/*     */   }
/*     */ 
/*     */   protected void paintBackground(Graphics g, JMenuItem menuItem, Color bgColor)
/*     */   {
/*  68 */     if ((!(menuItem instanceof JMenu)) || (!((JMenu)menuItem).isTopLevelMenu())) {
/*  69 */       super.paintBackground(g, menuItem, bgColor);
/*  70 */       return;
/*     */     }
/*     */ 
/*  73 */     ButtonModel model = menuItem.getModel();
/*  74 */     Color oldColor = g.getColor();
/*     */     int menuHeight;
/*     */     int menuWidth;
/*     */     int menuHeight;
/*  77 */     if (JideSwingUtilities.getOrientationOf(menuItem) == 0) {
/*  78 */       int menuWidth = menuItem.getWidth();
/*  79 */       menuHeight = menuItem.getHeight();
/*     */     }
/*     */     else {
/*  82 */       menuWidth = menuItem.getHeight();
/*  83 */       menuHeight = menuItem.getWidth();
/*     */     }
/*     */ 
/*  86 */     Color borderColor = getPainter().getMenuItemBorderColor();
/*     */ 
/*  90 */     if (menuItem.isOpaque()) {
/*  91 */       if (menuItem.getParent() != null) {
/*  92 */         g.setColor(menuItem.getParent().getBackground());
/*     */       }
/*     */       else {
/*  95 */         g.setColor(menuItem.getBackground());
/*     */       }
/*  97 */       g.fillRect(0, 0, menuWidth, menuHeight);
/*     */     }
/*     */ 
/* 100 */     Rectangle rect = new Rectangle(0, 0, menuWidth, menuHeight);
/* 101 */     if (((menuItem instanceof JMenu)) && (model.isSelected()))
/*     */     {
/* 105 */       if (((JMenu)menuItem).getPopupMenu().isVisible()) {
/* 106 */         if (JideSwingUtilities.getOrientationOf(menuItem) == 0) {
/* 107 */           getPainter().paintMenuItemBackground(menuItem, g, rect, JideSwingUtilities.getOrientationOf(menuItem), 0, false);
/* 108 */           g.setColor(borderColor);
/* 109 */           g.drawLine(0, 0, menuWidth - 1, 0);
/* 110 */           g.drawLine(menuWidth - 1, 0, menuWidth - 1, menuHeight - 1);
/* 111 */           g.drawLine(0, menuHeight - 1, 0, 1);
/* 112 */           g.drawLine(0, menuHeight - 1, menuWidth - 1, menuHeight - 1);
/*     */         }
/*     */         else {
/* 115 */           getPainter().paintMenuItemBackground(menuItem, g, rect, JideSwingUtilities.getOrientationOf(menuItem), 0, false);
/* 116 */           g.setColor(borderColor);
/* 117 */           g.drawLine(0, menuHeight - 1, menuWidth - 1, menuHeight - 1);
/* 118 */           g.drawLine(menuWidth - 1, 0, menuWidth - 1, menuHeight);
/* 119 */           g.drawLine(0, menuHeight - 1, 0, 1);
/* 120 */           g.drawLine(0, 0, menuWidth - 1, 0);
/*     */         }
/*     */       }
/*     */       else {
/* 124 */         getPainter().paintMenuItemBackground(menuItem, g, rect, JideSwingUtilities.getOrientationOf(menuItem), 2);
/*     */       }
/*     */     }
/* 127 */     else if ((isMouseOver()) && (model.isEnabled())) {
/* 128 */       getPainter().paintMenuItemBackground(menuItem, g, rect, JideSwingUtilities.getOrientationOf(menuItem), 2);
/*     */     }
/*     */ 
/* 131 */     if (isDownArrowVisible(menuItem.getParent())) {
/* 132 */       g.setColor(menuItem.isEnabled() ? Color.BLACK : Color.GRAY);
/* 133 */       int middle = menuWidth - 9;
/* 134 */       g.drawLine(middle - 2, menuHeight / 2 - 1, middle + 2, menuHeight / 2 - 1);
/* 135 */       g.drawLine(middle - 1, menuHeight / 2, middle + 1, menuHeight / 2);
/* 136 */       g.drawLine(middle, menuHeight / 2 + 1, middle, menuHeight / 2 + 1);
/*     */     }
/* 138 */     g.setColor(oldColor);
/*     */   }
/*     */ 
/*     */   protected void installListeners()
/*     */   {
/* 143 */     super.installListeners();
/*     */ 
/* 145 */     if (this.changeListener == null) {
/* 146 */       this.changeListener = createChangeListener(this.menuItem);
/*     */     }
/* 148 */     if (this.changeListener != null) {
/* 149 */       this.menuItem.addChangeListener(this.changeListener);
/*     */     }
/* 151 */     if (this.propertyChangeListener == null) {
/* 152 */       this.propertyChangeListener = createPropertyChangeListener(this.menuItem);
/*     */     }
/* 154 */     if (this.propertyChangeListener != null) {
/* 155 */       this.menuItem.addPropertyChangeListener(this.propertyChangeListener);
/*     */     }
/* 157 */     if (this.menuListener == null) {
/* 158 */       this.menuListener = createMenuListener(this.menuItem);
/*     */     }
/* 160 */     if ((this.menuListener != null) && ((this.menuItem instanceof JMenu))) {
/* 161 */       ((JMenu)this.menuItem).addMenuListener(this.menuListener);
/*     */     }
/*     */ 
/* 164 */     if (this.popupMenuListener == null) {
/* 165 */       this.popupMenuListener = createPopupMenuListener();
/*     */     }
/* 167 */     if ((this.popupMenuListener != null) && ((this.menuItem instanceof JMenu)))
/* 168 */       ((JMenu)this.menuItem).getPopupMenu().addPopupMenuListener(this.popupMenuListener);
/*     */   }
/*     */ 
/*     */   protected PopupMenuListener createPopupMenuListener()
/*     */   {
/* 173 */     return new PopupMenuListener() {
/*     */       public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
/* 175 */         repaint();
/*     */       }
/*     */ 
/*     */       public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {
/* 179 */         repaint();
/*     */       }
/*     */ 
/*     */       public void popupMenuCanceled(PopupMenuEvent e) {
/* 183 */         repaint();
/*     */       }
/*     */ 
/*     */       private void repaint() {
/* 187 */         if (VsnetMenuUI.this.menuItem != null)
/* 188 */           VsnetMenuUI.this.menuItem.repaint();
/*     */       }
/*     */     };
/*     */   }
/*     */ 
/*     */   protected void installKeyboardActions()
/*     */   {
/* 197 */     super.installKeyboardActions();
/* 198 */     updateMnemonicBinding();
/*     */   }
/*     */ 
/*     */   protected void updateMnemonicBinding() {
/* 202 */     int mnemonic = this.menuItem.getModel().getMnemonic();
/* 203 */     int[] shortcutKeys = (int[])(int[])UIDefaultsLookup.get("Menu.shortcutKeys");
/* 204 */     if (shortcutKeys == null) {
/* 205 */       shortcutKeys = new int[] { 8 };
/*     */     }
/* 207 */     if (mnemonic == this.lastMnemonic) {
/* 208 */       return;
/*     */     }
/* 210 */     if ((this.lastMnemonic != 0) && (this.windowInputMap != null)) {
/* 211 */       for (int shortcutKey : shortcutKeys) {
/* 212 */         this.windowInputMap.remove(KeyStroke.getKeyStroke(this.lastMnemonic, shortcutKey, false));
/*     */ 
/* 214 */         this.windowInputMap.remove(KeyStroke.getKeyStroke(this.lastMnemonic, shortcutKey, true));
/*     */       }
/*     */     }
/*     */ 
/* 218 */     if (mnemonic != 0) {
/* 219 */       if (this.windowInputMap == null) {
/* 220 */         this.windowInputMap = createInputMap(2);
/*     */ 
/* 222 */         SwingUtilities.replaceUIInputMap(this.menuItem, 2, this.windowInputMap);
/*     */       }
/*     */ 
/* 225 */       for (int shortcutKey : shortcutKeys) {
/* 226 */         this.windowInputMap.put(KeyStroke.getKeyStroke(mnemonic, shortcutKey, false), "selectMenu");
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 231 */     this.lastMnemonic = mnemonic;
/*     */   }
/*     */ 
/*     */   protected void uninstallKeyboardActions()
/*     */   {
/* 236 */     super.uninstallKeyboardActions();
/*     */   }
/*     */ 
/*     */   ActionMap getActionMap()
/*     */   {
/* 244 */     return createActionMap();
/*     */   }
/*     */ 
/*     */   protected ActionMap createActionMap()
/*     */   {
/* 252 */     ActionMap am = super.createActionMap();
/* 253 */     if ((am != null) && ((this.menuItem instanceof JMenu))) {
/* 254 */       am.put("selectMenu", new PostAction((JMenu)this.menuItem, true));
/*     */     }
/* 256 */     return am;
/*     */   }
/*     */ 
/*     */   protected MouseInputListener createMouseInputListener(JComponent c)
/*     */   {
/* 261 */     return new MouseInputHandler();
/*     */   }
/*     */ 
/*     */   protected MenuListener createMenuListener(JComponent c) {
/* 265 */     return new MenuHandler(null);
/*     */   }
/*     */ 
/*     */   protected ChangeListener createChangeListener(JComponent c) {
/* 269 */     return null;
/*     */   }
/*     */ 
/*     */   protected PropertyChangeListener createPropertyChangeListener(JComponent c) {
/* 273 */     return new PropertyChangeHandler(null);
/*     */   }
/*     */ 
/*     */   protected void uninstallDefaults()
/*     */   {
/* 278 */     this.menuItem.setArmed(false);
/* 279 */     this.menuItem.setSelected(false);
/* 280 */     this.menuItem.resetKeyboardActions();
/* 281 */     super.uninstallDefaults();
/*     */   }
/*     */ 
/*     */   protected void uninstallListeners()
/*     */   {
/* 286 */     super.uninstallListeners();
/*     */ 
/* 288 */     if (this.changeListener != null) {
/* 289 */       this.menuItem.removeChangeListener(this.changeListener);
/*     */     }
/* 291 */     this.changeListener = null;
/*     */ 
/* 293 */     if (this.propertyChangeListener != null) {
/* 294 */       this.menuItem.removePropertyChangeListener(this.propertyChangeListener);
/*     */     }
/* 296 */     this.propertyChangeListener = null;
/*     */ 
/* 298 */     if ((this.menuListener != null) && ((this.menuItem instanceof JMenu))) {
/* 299 */       ((JMenu)this.menuItem).removeMenuListener(this.menuListener);
/*     */     }
/*     */ 
/* 302 */     this.menuListener = null;
/*     */ 
/* 304 */     if ((this.popupMenuListener != null) && ((this.menuItem instanceof JMenu))) {
/* 305 */       ((JMenu)this.menuItem).getPopupMenu().removePopupMenuListener(this.popupMenuListener);
/*     */     }
/*     */ 
/* 308 */     this.popupMenuListener = null;
/*     */   }
/*     */ 
/*     */   protected MenuDragMouseListener createMenuDragMouseListener(JComponent c)
/*     */   {
/* 313 */     return new MenuDragMouseHandler(null);
/*     */   }
/*     */ 
/*     */   protected MenuKeyListener createMenuKeyListener(JComponent c)
/*     */   {
/* 318 */     return new MenuKeyHandler(null);
/*     */   }
/*     */ 
/*     */   public Dimension getMaximumSize(JComponent c)
/*     */   {
/* 323 */     if (((this.menuItem instanceof JMenu)) && (((JMenu)this.menuItem).isTopLevelMenu())) {
/* 324 */       Dimension d = c.getPreferredSize();
/* 325 */       if (JideSwingUtilities.getOrientationOf(this.menuItem) == 0) {
/* 326 */         return new Dimension(d.width, 32767);
/*     */       }
/*     */ 
/* 329 */       return new Dimension(32767, d.height);
/*     */     }
/*     */ 
/* 332 */     return null;
/*     */   }
/*     */ 
/*     */   protected static void setupPostTimer(JMenu menu) {
/* 336 */     Timer timer = new Timer(menu.getDelay(), new PostAction(menu, false));
/* 337 */     timer.setRepeats(false);
/* 338 */     timer.start();
/*     */   }
/*     */ 
/*     */   protected static void appendPath(MenuElement[] path, MenuElement elem) {
/* 342 */     MenuElement[] newPath = new MenuElement[path.length + 1];
/* 343 */     System.arraycopy(path, 0, newPath, 0, path.length);
/* 344 */     newPath[path.length] = elem;
/* 345 */     MenuSelectionManager.defaultManager().setSelectedPath(newPath);
/*     */   }
/*     */ 
/*     */   static JPopupMenu getActivePopupMenu()
/*     */   {
/* 687 */     MenuElement[] path = MenuSelectionManager.defaultManager().getSelectedPath();
/*     */ 
/* 689 */     for (int i = path.length - 1; i >= 0; i--) {
/* 690 */       MenuElement elem = path[i];
/* 691 */       if ((elem instanceof JPopupMenu)) {
/* 692 */         return (JPopupMenu)elem;
/*     */       }
/*     */     }
/* 695 */     return null;
/*     */   }
/*     */ 
/*     */   protected void paintText(Graphics g, JMenuItem menuItem, Rectangle textRect, String text)
/*     */   {
/* 845 */     ButtonModel model = menuItem.getModel();
/*     */ 
/* 847 */     if ((!(menuItem instanceof JMenu)) || (!((JMenu)menuItem).isTopLevelMenu())) {
/* 848 */       if (menuItem.getComponentOrientation().isLeftToRight()) {
/* 849 */         int defaultTextIconGap = UIDefaultsLookup.getInt("MenuItem.textIconGap");
/* 850 */         int defaultShadowWidth = UIDefaultsLookup.getInt("MenuItem.shadowWidth");
/* 851 */         textRect.x = (defaultShadowWidth + defaultTextIconGap);
/*     */       }
/*     */       else {
/* 854 */         int defaultTextIconGap = UIDefaultsLookup.getInt("MenuItem.textIconGap");
/* 855 */         int defaultShadowWidth = UIDefaultsLookup.getInt("MenuItem.shadowWidth");
/* 856 */         textRect.x = (viewRect.width - (defaultShadowWidth + defaultTextIconGap + textRect.width));
/*     */       }
/*     */     }
/*     */ 
/* 860 */     if (!model.isEnabled())
/*     */     {
/* 862 */       textRect.y += 1;
/* 863 */       WindowsGraphicsUtils.paintText(g, menuItem, textRect, text, 0);
/*     */     }
/*     */     else {
/* 866 */       FontMetrics fm = g.getFontMetrics();
/* 867 */       int mnemonicIndex = menuItem.getDisplayedMnemonicIndex();
/*     */ 
/* 869 */       if (WindowsLookAndFeel.isMnemonicHidden()) {
/* 870 */         mnemonicIndex = -1;
/*     */       }
/*     */ 
/* 873 */       Color oldColor = g.getColor();
/*     */ 
/* 876 */       if ((model.isSelected()) && 
/* 877 */         ((menuItem instanceof JMenu)) && (!((JMenu)menuItem).isTopLevelMenu()))
/*     */       {
/* 880 */         g.setColor(this.selectionForeground);
/*     */       }
/*     */ 
/* 883 */       JideSwingUtilities.drawStringUnderlineCharAt(menuItem, g, text, mnemonicIndex, textRect.x, textRect.y + fm.getAscent());
/*     */ 
/* 887 */       g.setColor(oldColor);
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void setMouseOver(boolean over)
/*     */   {
/* 898 */     this.isMouseOver = over;
/* 899 */     this.menuItem.getModel().setRollover(this.isMouseOver);
/*     */   }
/*     */ 
/*     */   protected boolean isMouseOver()
/*     */   {
/* 908 */     return this.isMouseOver;
/*     */   }
/*     */ 
/*     */   public Dimension getPreferredSize(JComponent c)
/*     */   {
/* 913 */     Dimension size = super.getPreferredSize(c);
/* 914 */     if (((this.menuItem instanceof JMenu)) && (((JMenu)this.menuItem).isTopLevelMenu()) && (isDownArrowVisible(this.menuItem.getParent())))
/*     */     {
/* 916 */       if (JideSwingUtilities.getOrientationOf(this.menuItem) == 0)
/* 917 */         size.width += 11;
/*     */       else
/* 919 */         size.height += 11;
/*     */     }
/* 921 */     return size;
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
/* 710 */       if (!VsnetMenuUI.crossMenuMnemonic) {
/* 711 */         JPopupMenu pm = VsnetMenuUI.getActivePopupMenu();
/* 712 */         if ((pm != null) && (pm != VsnetMenuUI.this.menuItem.getParent())) {
/* 713 */           return;
/*     */         }
/*     */       }
/*     */ 
/* 717 */       int key = VsnetMenuUI.this.menuItem.getMnemonic();
/* 718 */       if (key == 0)
/* 719 */         return;
/* 720 */       MenuElement[] path = e.getPath();
/* 721 */       if (lower((char)key) == lower(e.getKeyChar())) {
/* 722 */         JPopupMenu popupMenu = ((JMenu)VsnetMenuUI.this.menuItem).getPopupMenu();
/* 723 */         ArrayList newList = new ArrayList(Arrays.asList(path));
/* 724 */         newList.add(popupMenu);
/* 725 */         MenuElement[] sub = popupMenu.getSubElements();
/* 726 */         if (sub.length > 0) {
/* 727 */           newList.add(sub[0]);
/*     */         }
/* 729 */         MenuSelectionManager manager = e.getMenuSelectionManager();
/* 730 */         MenuElement[] newPath = new MenuElement[0];
/* 731 */         newPath = (MenuElement[])(MenuElement[])newList.toArray(newPath);
/* 732 */         manager.setSelectedPath(newPath);
/* 733 */         e.consume();
/*     */       }
/*     */     }
/*     */ 
/*     */     public void menuKeyPressed(MenuKeyEvent e)
/*     */     {
/* 746 */       char keyChar = e.getKeyChar();
/* 747 */       if (!Character.isLetterOrDigit(keyChar)) {
/* 748 */         return;
/*     */       }
/* 750 */       MenuSelectionManager manager = e.getMenuSelectionManager();
/* 751 */       MenuElement[] path = e.getPath();
/* 752 */       MenuElement[] selectedPath = manager.getSelectedPath();
/*     */ 
/* 754 */       for (int i = selectedPath.length - 1; i >= 0; i--)
/* 755 */         if (selectedPath[i] == VsnetMenuUI.this.menuItem) {
/* 756 */           JPopupMenu popupMenu = ((JMenu)VsnetMenuUI.this.menuItem).getPopupMenu();
/* 757 */           if (!popupMenu.isVisible()) {
/* 758 */             return;
/*     */           }
/* 760 */           MenuElement[] items = popupMenu.getSubElements();
/*     */ 
/* 762 */           MenuElement currentItem = selectedPath[(selectedPath.length - 1)];
/* 763 */           int currentIndex = -1;
/* 764 */           int matches = 0;
/* 765 */           int firstMatch = -1;
/* 766 */           int[] indexes = null;
/*     */ 
/* 768 */           for (int j = 0; j < items.length; j++) {
/* 769 */             if (!(items[j] instanceof JMenuItem)) {
/*     */               continue;
/*     */             }
/* 772 */             int key = ((JMenuItem)items[j]).getMnemonic();
/* 773 */             if (lower((char)key) == lower(keyChar)) {
/* 774 */               if (matches == 0) {
/* 775 */                 firstMatch = j;
/* 776 */                 matches++;
/*     */               }
/*     */               else {
/* 779 */                 if (indexes == null) {
/* 780 */                   indexes = new int[items.length];
/* 781 */                   indexes[0] = firstMatch;
/*     */                 }
/* 783 */                 indexes[(matches++)] = j;
/*     */               }
/*     */             }
/* 786 */             if (currentItem == items[j]) {
/* 787 */               currentIndex = matches - 1;
/*     */             }
/*     */           }
/*     */ 
/* 791 */           if (matches != 0)
/*     */           {
/* 794 */             if (matches == 1)
/*     */             {
/* 796 */               JMenuItem item = (JMenuItem)items[firstMatch];
/* 797 */               if (!(item instanceof JMenu))
/*     */               {
/* 799 */                 manager.clearSelectedPath();
/* 800 */                 item.doClick();
/*     */               }
/*     */ 
/*     */             }
/*     */             else
/*     */             {
/* 807 */               MenuElement newItem = null;
/* 808 */               if (indexes != null) {
/* 809 */                 newItem = items[indexes[((currentIndex + 1) % matches)]];
/*     */               }
/*     */ 
/* 812 */               MenuElement[] newPath = new MenuElement[path.length + 2];
/* 813 */               System.arraycopy(path, 0, newPath, 0, path.length);
/* 814 */               newPath[path.length] = popupMenu;
/* 815 */               newPath[(path.length + 1)] = newItem;
/* 816 */               manager.setSelectedPath(newPath);
/*     */             }
/*     */           }
/* 818 */           e.consume();
/* 819 */           return;
/*     */         }
/*     */     }
/*     */ 
/*     */     public void menuKeyReleased(MenuKeyEvent e)
/*     */     {
/*     */     }
/*     */ 
/*     */     private char lower(char keyChar) {
/* 828 */       return Character.toLowerCase(keyChar);
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
/* 643 */       if (!(VsnetMenuUI.this.menuItem instanceof JMenu)) {
/* 644 */         return;
/*     */       }
/* 646 */       if (!VsnetMenuUI.this.menuItem.isEnabled()) {
/* 647 */         return;
/*     */       }
/* 649 */       MenuSelectionManager manager = e.getMenuSelectionManager();
/* 650 */       MenuElement[] path = e.getPath();
/*     */ 
/* 652 */       Point p = e.getPoint();
/* 653 */       if ((p.x >= 0) && (p.x < VsnetMenuUI.this.menuItem.getWidth()) && (p.y >= 0) && (p.y < VsnetMenuUI.this.menuItem.getHeight()))
/*     */       {
/* 655 */         JMenu menu = (JMenu)VsnetMenuUI.this.menuItem;
/* 656 */         MenuElement[] selectedPath = manager.getSelectedPath();
/* 657 */         if ((selectedPath.length <= 0) || (selectedPath[(selectedPath.length - 1)] != menu.getPopupMenu()))
/*     */         {
/* 660 */           if ((menu.isTopLevelMenu()) || (menu.getDelay() == 0) || (e.getID() == 506))
/*     */           {
/* 663 */             VsnetMenuUI.appendPath(path, menu.getPopupMenu());
/*     */           }
/*     */           else {
/* 666 */             manager.setSelectedPath(path);
/* 667 */             VsnetMenuUI.setupPostTimer(menu);
/*     */           }
/*     */         }
/*     */       }
/* 671 */       else if (e.getID() == 502) {
/* 672 */         Component comp = manager.componentForPoint(e.getComponent(), e.getPoint());
/* 673 */         if (comp == null)
/* 674 */           manager.clearSelectedPath();
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
/*     */   private class MenuHandler
/*     */     implements MenuListener
/*     */   {
/*     */     private MenuHandler()
/*     */     {
/*     */     }
/*     */ 
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
/* 627 */       if (!(VsnetMenuUI.this.menuItem instanceof JMenu)) {
/* 628 */         return;
/*     */       }
/* 630 */       JMenu m = (JMenu)e.getSource();
/* 631 */       MenuSelectionManager manager = MenuSelectionManager.defaultManager();
/* 632 */       if (manager.isComponentPartOfCurrentMenu(m))
/* 633 */         MenuSelectionManager.defaultManager().clearSelectedPath();
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
/* 445 */       if (!(VsnetMenuUI.this.menuItem instanceof JMenu)) {
/* 446 */         return;
/*     */       }
/*     */ 
/* 449 */       JMenu menu = (JMenu)VsnetMenuUI.this.menuItem;
/* 450 */       if (!menu.isEnabled()) {
/* 451 */         return;
/*     */       }
/* 453 */       MenuSelectionManager manager = MenuSelectionManager.defaultManager();
/*     */ 
/* 455 */       if (((menu.getParent() instanceof JMenuBar)) || ((menu.getParent() instanceof TopLevelMenuContainer))) {
/* 456 */         if (menu.isSelected()) {
/* 457 */           manager.clearSelectedPath();
/*     */         }
/*     */         else
/*     */         {
/* 461 */           Container cnt = getFirstParentMenuElement(menu);
/*     */ 
/* 463 */           if ((cnt != null) && ((cnt instanceof MenuElement))) {
/* 464 */             ArrayList parents = new ArrayList();
/* 465 */             while ((cnt instanceof MenuElement)) {
/* 466 */               parents.add(0, cnt);
/* 467 */               if ((cnt instanceof JPopupMenu)) {
/* 468 */                 cnt = (Container)((JPopupMenu)cnt).getInvoker(); continue;
/*     */               }
/*     */ 
/* 472 */               cnt = getFirstParentMenuElement(cnt);
/*     */             }
/*     */ 
/* 476 */             MenuElement[] me = new MenuElement[parents.size() + 1];
/* 477 */             for (int i = 0; i < parents.size(); i++) {
/* 478 */               Container container = (Container)parents.get(i);
/* 479 */               me[i] = ((MenuElement)container);
/*     */             }
/* 481 */             me[parents.size()] = menu;
/* 482 */             manager.setSelectedPath(me);
/*     */           }
/*     */           else {
/* 485 */             MenuElement[] me = new MenuElement[1];
/* 486 */             me[0] = menu;
/* 487 */             manager.setSelectedPath(me);
/*     */           }
/*     */         }
/*     */       }
/*     */ 
/* 492 */       MenuElement[] selectedPath = manager.getSelectedPath();
/* 493 */       if ((selectedPath.length > 0) && (selectedPath[(selectedPath.length - 1)] != menu.getPopupMenu()))
/*     */       {
/* 496 */         if ((menu.isTopLevelMenu()) || (menu.getDelay() == 0))
/*     */         {
/* 498 */           VsnetMenuUI.appendPath(selectedPath, menu.getPopupMenu());
/*     */         }
/*     */         else
/* 501 */           VsnetMenuUI.setupPostTimer(menu);
/*     */       }
/*     */     }
/*     */ 
/*     */     protected Container getFirstParentMenuElement(Component comp)
/*     */     {
/* 507 */       Container parent = comp.getParent();
/*     */ 
/* 509 */       while (parent != null) {
/* 510 */         if ((parent instanceof MenuElement)) {
/* 511 */           return parent;
/*     */         }
/* 513 */         parent = parent.getParent();
/*     */       }
/*     */ 
/* 516 */       return null;
/*     */     }
/*     */ 
/*     */     public void mouseReleased(MouseEvent e)
/*     */     {
/* 525 */       if (!SwingUtilities.isLeftMouseButton(e)) {
/* 526 */         return;
/*     */       }
/*     */ 
/* 529 */       if (!(VsnetMenuUI.this.menuItem instanceof JMenu)) {
/* 530 */         return;
/*     */       }
/*     */ 
/* 533 */       JMenu menu = (JMenu)VsnetMenuUI.this.menuItem;
/* 534 */       if (!menu.isEnabled())
/* 535 */         return;
/* 536 */       MenuSelectionManager manager = MenuSelectionManager.defaultManager();
/*     */ 
/* 538 */       manager.processMouseEvent(e);
/* 539 */       if (!e.isConsumed())
/* 540 */         manager.clearSelectedPath();
/*     */     }
/*     */ 
/*     */     public void mouseEntered(MouseEvent e)
/*     */     {
/* 551 */       if (!(VsnetMenuUI.this.menuItem instanceof JMenu)) {
/* 552 */         return;
/*     */       }
/* 554 */       JMenu menu = (JMenu)VsnetMenuUI.this.menuItem;
/* 555 */       if (!menu.isEnabled()) {
/* 556 */         return;
/*     */       }
/* 558 */       MenuSelectionManager manager = MenuSelectionManager.defaultManager();
/*     */ 
/* 560 */       MenuElement[] selectedPath = manager.getSelectedPath();
/* 561 */       if (!menu.isTopLevelMenu()) {
/* 562 */         if ((selectedPath.length <= 0) || (selectedPath[(selectedPath.length - 1)] != menu.getPopupMenu()))
/*     */         {
/* 565 */           if (menu.getDelay() == 0) {
/* 566 */             VsnetMenuUI.appendPath(VsnetMenuUI.this.getPath(), menu.getPopupMenu());
/*     */           }
/*     */           else {
/* 569 */             manager.setSelectedPath(VsnetMenuUI.this.getPath());
/* 570 */             VsnetMenuUI.setupPostTimer(menu);
/*     */           }
/*     */         }
/*     */ 
/*     */       }
/* 575 */       else if ((selectedPath.length > 0) && (selectedPath[0] == menu.getParent()))
/*     */       {
/* 577 */         MenuElement[] newPath = new MenuElement[3];
/*     */ 
/* 580 */         newPath[0] = ((MenuElement)menu.getParent());
/* 581 */         newPath[1] = menu;
/* 582 */         newPath[2] = menu.getPopupMenu();
/* 583 */         manager.setSelectedPath(newPath);
/*     */       }
/*     */ 
/* 587 */       if (!SwingUtilities.isLeftMouseButton(e)) {
/* 588 */         VsnetMenuUI.this.setMouseOver(true);
/*     */       }
/* 590 */       VsnetMenuUI.this.menuItem.repaint();
/*     */     }
/*     */ 
/*     */     public void mouseExited(MouseEvent e) {
/* 594 */       VsnetMenuUI.this.setMouseOver(false);
/* 595 */       VsnetMenuUI.this.menuItem.repaint();
/*     */     }
/*     */ 
/*     */     public void mouseDragged(MouseEvent e)
/*     */     {
/* 606 */       if (!(VsnetMenuUI.this.menuItem instanceof JMenu)) {
/* 607 */         return;
/*     */       }
/* 609 */       JMenu menu = (JMenu)VsnetMenuUI.this.menuItem;
/* 610 */       if (!menu.isEnabled())
/* 611 */         return;
/* 612 */       MenuSelectionManager.defaultManager().processMouseEvent(e);
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
/* 407 */       String prop = e.getPropertyName();
/* 408 */       if (prop.equals("mnemonic")) {
/* 409 */         VsnetMenuUI.this.updateMnemonicBinding();
/*     */       }
/* 411 */       else if (prop.equals("buttonStyle")) {
/* 412 */         AbstractButton b = (AbstractButton)e.getSource();
/* 413 */         b.repaint();
/*     */       }
/* 415 */       else if (("verticalTextPosition".equals(prop)) || ("horizontalTextPosition".equals(prop)))
/*     */       {
/* 417 */         AbstractButton b = (AbstractButton)e.getSource();
/* 418 */         b.updateUI();
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   protected static class PostAction extends AbstractAction
/*     */   {
/*     */     JMenu menu;
/* 350 */     boolean force = false;
/*     */     private static final long serialVersionUID = 8723756757971612903L;
/*     */ 
/*     */     PostAction(JMenu menu, boolean shouldForce)
/*     */     {
/* 354 */       this.menu = menu;
/* 355 */       this.force = shouldForce;
/*     */     }
/*     */ 
/*     */     public void actionPerformed(ActionEvent e) {
/* 359 */       if (!VsnetMenuUI.crossMenuMnemonic) {
/* 360 */         JPopupMenu pm = VsnetMenuUI.getActivePopupMenu();
/* 361 */         if ((pm != null) && (pm != this.menu.getParent())) {
/* 362 */           return;
/*     */         }
/*     */       }
/*     */ 
/* 366 */       MenuSelectionManager defaultManager = MenuSelectionManager.defaultManager();
/* 367 */       if (this.force) {
/* 368 */         Container cnt = this.menu.getParent();
/* 369 */         if ((cnt != null) && ((cnt instanceof JMenuBar)))
/*     */         {
/* 373 */           MenuElement[] subElements = this.menu.getPopupMenu().getSubElements();
/*     */           MenuElement[] me;
/* 374 */           if (subElements.length > 0) {
/* 375 */             MenuElement[] me = new MenuElement[4];
/* 376 */             me[0] = ((MenuElement)cnt);
/* 377 */             me[1] = this.menu;
/* 378 */             me[2] = this.menu.getPopupMenu();
/* 379 */             me[3] = subElements[0];
/*     */           }
/*     */           else {
/* 382 */             me = new MenuElement[3];
/* 383 */             me[0] = ((MenuElement)cnt);
/* 384 */             me[1] = this.menu;
/* 385 */             me[2] = this.menu.getPopupMenu();
/*     */           }
/* 387 */           defaultManager.setSelectedPath(me);
/*     */         }
/*     */       }
/*     */       else {
/* 391 */         MenuElement[] path = defaultManager.getSelectedPath();
/* 392 */         if ((path.length > 0) && (path[(path.length - 1)] == this.menu))
/* 393 */           VsnetMenuUI.appendPath(path, this.menu.getPopupMenu());
/*     */       }
/*     */     }
/*     */ 
/*     */     public boolean isEnabled()
/*     */     {
/* 400 */       return this.menu.getModel().isEnabled();
/*     */     }
/*     */   }
/*     */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     com.jidesoft.plaf.vsnet.VsnetMenuUI
 * JD-Core Version:    0.6.0
 */