/*     */ package com.jidesoft.swing;
/*     */ 
/*     */ import com.jidesoft.plaf.UIDefaultsLookup;
/*     */ import java.awt.ComponentOrientation;
/*     */ import java.awt.Container;
/*     */ import java.awt.Dimension;
/*     */ import java.awt.GraphicsConfiguration;
/*     */ import java.awt.GraphicsDevice;
/*     */ import java.awt.GraphicsEnvironment;
/*     */ import java.awt.Insets;
/*     */ import java.awt.Point;
/*     */ import java.awt.Rectangle;
/*     */ import java.awt.Toolkit;
/*     */ import java.awt.event.ActionEvent;
/*     */ import java.awt.event.ActionListener;
/*     */ import javax.swing.Action;
/*     */ import javax.swing.JMenu;
/*     */ import javax.swing.JPopupMenu;
/*     */ import javax.swing.MenuSelectionManager;
/*     */ import javax.swing.Timer;
/*     */ import javax.swing.event.MenuEvent;
/*     */ import javax.swing.event.MenuListener;
/*     */ 
/*     */ public class JideMenu extends JMenu
/*     */   implements Alignable
/*     */ {
/*  44 */   private int _preferredPopupHorizontalAlignment = 2;
/*     */ 
/*  46 */   private int _preferredPopupVerticalAlignment = 3;
/*     */   private MenuCreator _menuCreator;
/*     */   private PopupMenuCustomizer _customizer;
/*     */   private PopupMenuOriginCalculator _originCalculator;
/*  54 */   public static int DELAY = 400;
/*     */   private int _orientation;
/*     */   private static JideMenu _pendingMenu;
/*     */   private static HideTimer _timer;
/*     */   private static final boolean DISABLE_TIMER = true;
/*     */ 
/*     */   public JideMenu()
/*     */   {
/*  59 */     initMenu();
/*     */   }
/*     */ 
/*     */   public JideMenu(String s) {
/*  63 */     super(s);
/*  64 */     initMenu();
/*     */   }
/*     */ 
/*     */   public JideMenu(Action a) {
/*  68 */     super(a);
/*  69 */     initMenu();
/*     */   }
/*     */ 
/*     */   public JideMenu(String s, boolean b) {
/*  73 */     super(s, b);
/*  74 */     initMenu();
/*     */   }
/*     */ 
/*     */   protected void initMenu()
/*     */   {
/*  79 */     addMenuListener(new MenuListener()
/*     */     {
/*     */       public void menuSelected(MenuEvent e)
/*     */       {
/*     */         JideMenu.MenuCreator menuCreator;
/*  82 */         if ((menuCreator = JideMenu.this.getMenuCreator()) != null) {
/*  83 */           menuCreator.createMenu();
/*  84 */           if (JideMenu.this.getPopupMenu().getComponentCount() == 0)
/*  85 */             return;
/*     */         }
/*     */         JideMenu.PopupMenuCustomizer customizer;
/*  90 */         if ((customizer = JideMenu.this.getPopupMenuCustomizer()) != null)
/*  91 */           customizer.customize(JideMenu.this.getPopupMenu());
/*     */       }
/*     */ 
/*     */       public void menuDeselected(MenuEvent e)
/*     */       {
/*     */       }
/*     */ 
/*     */       public void menuCanceled(MenuEvent e)
/*     */       {
/*     */       }
/*     */     });
/*     */   }
/*     */ 
/*     */   public boolean isTopLevelMenu()
/*     */   {
/* 112 */     return (getParent() == null) || (!(getParent() instanceof JPopupMenu));
/*     */   }
/*     */ 
/*     */   public PopupMenuOriginCalculator getOriginCalculator()
/*     */   {
/* 144 */     return this._originCalculator;
/*     */   }
/*     */ 
/*     */   public void setOriginCalculator(PopupMenuOriginCalculator originCalculator)
/*     */   {
/* 153 */     this._originCalculator = originCalculator;
/*     */   }
/*     */ 
/*     */   @Deprecated
/*     */   public MenuCreator getMenuCreator()
/*     */   {
/* 164 */     return this._menuCreator;
/*     */   }
/*     */ 
/*     */   @Deprecated
/*     */   public void setMenuCreator(MenuCreator menuCreator)
/*     */   {
/* 177 */     this._menuCreator = menuCreator;
/*     */   }
/*     */ 
/*     */   public PopupMenuCustomizer getPopupMenuCustomizer()
/*     */   {
/* 186 */     return this._customizer;
/*     */   }
/*     */ 
/*     */   public void setPopupMenuCustomizer(PopupMenuCustomizer customizer)
/*     */   {
/* 216 */     this._customizer = customizer;
/*     */   }
/*     */ 
/*     */   protected Point getPopupMenuOrigin()
/*     */   {
/* 221 */     if (this._originCalculator != null) {
/* 222 */       return this._originCalculator.getPopupMenuOrigin(this);
/*     */     }
/*     */ 
/* 226 */     JPopupMenu pm = getPopupMenu();
/*     */ 
/* 229 */     Dimension s = getSize();
/* 230 */     Dimension pmSize = pm.getPreferredSize();
/*     */ 
/* 232 */     Point position = getLocationOnScreen();
/* 233 */     Toolkit toolkit = Toolkit.getDefaultToolkit();
/* 234 */     GraphicsConfiguration gc = getGraphicsConfiguration();
/* 235 */     Rectangle screenBounds = new Rectangle(toolkit.getScreenSize());
/* 236 */     GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
/*     */ 
/* 238 */     GraphicsDevice[] gds = ge.getScreenDevices();
/* 239 */     for (GraphicsDevice gd : gds) {
/* 240 */       if (gd.getType() == 0) {
/* 241 */         GraphicsConfiguration dgc = gd.getDefaultConfiguration();
/*     */ 
/* 243 */         if (dgc.getBounds().contains(position)) {
/* 244 */           gc = dgc;
/* 245 */           break;
/*     */         }
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 251 */     if (gc != null) {
/* 252 */       screenBounds = gc.getBounds();
/*     */ 
/* 254 */       Insets screenInsets = toolkit.getScreenInsets(gc);
/*     */ 
/* 256 */       screenBounds.width -= Math.abs(screenInsets.left + screenInsets.right);
/*     */ 
/* 258 */       screenBounds.height -= Math.abs(screenInsets.top + screenInsets.bottom);
/*     */ 
/* 260 */       position.x -= Math.abs(screenInsets.left);
/* 261 */       position.y -= Math.abs(screenInsets.top);
/*     */     }
/*     */ 
/* 264 */     Container parent = getParent();
/*     */     int x;
/*     */     int y;
/* 265 */     if ((parent instanceof JPopupMenu))
/*     */     {
/* 267 */       int xOffset = UIDefaultsLookup.getInt("Menu.submenuPopupOffsetX");
/* 268 */       int yOffset = UIDefaultsLookup.getInt("Menu.submenuPopupOffsetY");
/*     */ 
/* 270 */       if (getComponentOrientation().isLeftToRight()) {
/* 271 */         if (JideSwingUtilities.getOrientationOf(this) == 0)
/*     */         {
/* 273 */           int x = s.width + xOffset;
/* 274 */           if ((position.x + x + pmSize.width >= screenBounds.width + screenBounds.x) && (screenBounds.width - s.width < 2 * (position.x - screenBounds.x)))
/*     */           {
/* 280 */             x = 0 - xOffset - pmSize.width;
/*     */           }
/*     */         }
/*     */         else {
/* 284 */           int x = s.width + xOffset;
/* 285 */           if ((position.x + x + pmSize.width >= screenBounds.width + screenBounds.x) && (screenBounds.width - s.width < 2 * (position.x - screenBounds.x)))
/*     */           {
/* 289 */             x = 0 - xOffset - pmSize.width;
/*     */           }
/*     */         }
/*     */       }
/*     */       else {
/* 294 */         int x = 0 - xOffset - pmSize.width;
/* 295 */         if ((position.x + x < screenBounds.x) && (screenBounds.width - s.width > 2 * (position.x - screenBounds.x)))
/*     */         {
/* 300 */           x = s.width + xOffset;
/*     */         }
/*     */       }
/*     */ 
/* 304 */       int y = yOffset;
/* 305 */       if ((position.y + y + pmSize.height >= screenBounds.height + screenBounds.y) && (screenBounds.height - s.height < 2 * (position.y - screenBounds.y)))
/*     */       {
/* 309 */         y = s.height - yOffset - pmSize.height;
/*     */       }
/*     */     }
/*     */     else {
/* 313 */       int xOffset = UIDefaultsLookup.getInt("Menu.menuPopupOffsetX");
/* 314 */       int yOffset = UIDefaultsLookup.getInt("Menu.menuPopupOffsetY");
/*     */ 
/* 316 */       if (getComponentOrientation().isLeftToRight()) {
/* 317 */         if (JideSwingUtilities.getOrientationOf(this) == 0)
/*     */         {
/* 319 */           if (getPreferredPopupHorizontalAlignment() == 2) {
/* 320 */             int x = xOffset;
/* 321 */             if ((position.x + x + pmSize.width >= screenBounds.width + screenBounds.x) && (screenBounds.width - s.width < 2 * (position.x - screenBounds.x)))
/*     */             {
/* 327 */               x = s.width - xOffset - pmSize.width;
/*     */             }
/*     */           } else {
/* 330 */             int x = -pmSize.width + xOffset + s.width;
/* 331 */             if (position.x + x < screenBounds.x)
/* 332 */               x = screenBounds.x - position.x;
/*     */           }
/*     */         }
/*     */         else
/*     */         {
/* 337 */           int x = 1 - xOffset - pmSize.width;
/* 338 */           if ((position.x + x < screenBounds.x) && (screenBounds.width - s.width > 2 * (position.x - screenBounds.x)))
/*     */           {
/* 343 */             x = s.width + xOffset - 1;
/*     */           }
/*     */         }
/*     */ 
/*     */       }
/* 348 */       else if (getPreferredPopupHorizontalAlignment() == 2) {
/* 349 */         int x = s.width - xOffset - pmSize.width;
/* 350 */         if ((position.x + x < screenBounds.x) && (screenBounds.width - s.width > 2 * (position.x - screenBounds.x)))
/*     */         {
/* 355 */           x = xOffset;
/*     */         }
/*     */       }
/*     */       else {
/* 359 */         x = xOffset;
/*     */       }
/*     */ 
/* 364 */       if (JideSwingUtilities.getOrientationOf(this) == 0) {
/* 365 */         int y = s.height + yOffset - 1;
/* 366 */         if ((getPreferredPopupVerticalAlignment() == 1) || ((position.y + y + pmSize.height >= screenBounds.height) && (screenBounds.height - s.height < 2 * (position.y - screenBounds.y))))
/*     */         {
/* 372 */           y = 1 - yOffset - pmSize.height;
/*     */         }
/*     */       } else {
/* 375 */         y = -yOffset;
/* 376 */         if ((position.y + y + pmSize.height >= screenBounds.height) && (screenBounds.height - s.height < 2 * (position.y - screenBounds.y)))
/*     */         {
/* 381 */           y = 0 - yOffset - pmSize.height;
/*     */         }
/*     */       }
/*     */     }
/*     */ 
/* 386 */     return new Point(x, y);
/*     */   }
/*     */ 
/*     */   public boolean isOpaque()
/*     */   {
/* 396 */     return (!isTopLevelMenu()) && (super.isOpaque());
/*     */   }
/*     */ 
/*     */   public boolean originalIsOpaque() {
/* 400 */     return super.isOpaque();
/*     */   }
/*     */ 
/*     */   protected void hideMenu() {
/* 404 */     MenuSelectionManager msm = MenuSelectionManager.defaultManager();
/* 405 */     msm.clearSelectedPath();
/*     */   }
/*     */ 
/*     */   public int getPreferredPopupHorizontalAlignment() {
/* 409 */     return this._preferredPopupHorizontalAlignment;
/*     */   }
/*     */ 
/*     */   public void setPreferredPopupHorizontalAlignment(int preferredPopupHorizontalAlignment) {
/* 413 */     this._preferredPopupHorizontalAlignment = preferredPopupHorizontalAlignment;
/*     */   }
/*     */ 
/*     */   public int getPreferredPopupVerticalAlignment() {
/* 417 */     return this._preferredPopupVerticalAlignment;
/*     */   }
/*     */ 
/*     */   public void setPreferredPopupVerticalAlignment(int preferredPopupVerticalAlignment) {
/* 421 */     this._preferredPopupVerticalAlignment = preferredPopupVerticalAlignment;
/*     */   }
/*     */ 
/*     */   public boolean supportVerticalOrientation() {
/* 425 */     return true;
/*     */   }
/*     */ 
/*     */   public boolean supportHorizontalOrientation() {
/* 429 */     return true;
/*     */   }
/*     */ 
/*     */   public void setOrientation(int orientation) {
/* 433 */     int old = this._orientation;
/* 434 */     if (old != orientation) {
/* 435 */       this._orientation = orientation;
/* 436 */       firePropertyChange("orientation", old, orientation);
/*     */     }
/*     */   }
/*     */ 
/*     */   public int getOrientation() {
/* 441 */     return this._orientation;
/*     */   }
/*     */ 
/*     */   public void setPopupMenuVisible(boolean b)
/*     */   {
/*     */     MenuCreator menuCreator;
/* 454 */     if ((b) && ((menuCreator = getMenuCreator()) != null))
/* 455 */       menuCreator.createMenu();
/*     */     PopupMenuCustomizer customizer;
/* 459 */     if ((b) && ((customizer = getPopupMenuCustomizer()) != null)) {
/* 460 */       customizer.customize(getPopupMenu());
/* 461 */       if (shouldHidePopupMenu())
/* 462 */         return;
/*     */     }
/* 464 */     else if ((b) && (shouldHidePopupMenu())) {
/* 465 */       return;
/*     */     }
/*     */ 
/* 490 */     setPopupMenuVisibleImmediately(b);
/*     */   }
/*     */ 
/*     */   protected boolean shouldHidePopupMenu()
/*     */   {
/* 503 */     return getPopupMenu().getComponentCount() == 0;
/*     */   }
/*     */ 
/*     */   void setPopupMenuVisibleImmediately(boolean b) {
/* 507 */     super.setPopupMenuVisible(b);
/*     */   }
/*     */ 
/*     */   private void startTimer()
/*     */   {
/* 526 */     if (_timer != null) {
/* 527 */       stopTimer();
/*     */     }
/* 529 */     _pendingMenu = this;
/* 530 */     _timer = new HideTimer();
/* 531 */     _timer.start();
/*     */   }
/*     */ 
/*     */   private void stopTimer() {
/* 535 */     if (_timer != null)
/*     */     {
/* 537 */       if (_pendingMenu != null)
/*     */       {
/* 539 */         _pendingMenu.setPopupMenuVisibleImmediately(false);
/* 540 */         _pendingMenu = null;
/*     */       }
/* 542 */       _timer.stop();
/* 543 */       _timer = null;
/*     */     }
/*     */   }
/*     */ 
/*     */   private class HideTimer extends Timer
/*     */     implements ActionListener
/*     */   {
/*     */     private static final long serialVersionUID = 561631364532967870L;
/*     */ 
/*     */     public HideTimer()
/*     */     {
/* 514 */       super(null);
/* 515 */       addActionListener(this);
/* 516 */       setRepeats(false);
/*     */     }
/*     */ 
/*     */     public void actionPerformed(ActionEvent e) {
/* 520 */       JideMenu.this.stopTimer();
/*     */     }
/*     */   }
/*     */ 
/*     */   public static abstract interface PopupMenuOriginCalculator
/*     */   {
/*     */     public abstract Point getPopupMenuOrigin(JideMenu paramJideMenu);
/*     */   }
/*     */ 
/*     */   public static abstract interface PopupMenuCustomizer
/*     */   {
/*     */     public abstract void customize(JPopupMenu paramJPopupMenu);
/*     */   }
/*     */ 
/*     */   @Deprecated
/*     */   public static abstract interface MenuCreator
/*     */   {
/*     */     public abstract void createMenu();
/*     */   }
/*     */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     com.jidesoft.swing.JideMenu
 * JD-Core Version:    0.6.0
 */