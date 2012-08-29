/*     */ package com.jidesoft.swing;
/*     */ 
/*     */ import com.jidesoft.plaf.LookAndFeelFactory;
/*     */ import com.jidesoft.plaf.UIDefaultsLookup;
/*     */ import com.jidesoft.utils.PortingUtils;
/*     */ import java.awt.Component;
/*     */ import java.awt.Container;
/*     */ import java.awt.Dimension;
/*     */ import java.awt.Point;
/*     */ import java.awt.Rectangle;
/*     */ import javax.swing.AbstractButton;
/*     */ import javax.swing.JMenuItem;
/*     */ import javax.swing.JPopupMenu;
/*     */ import javax.swing.Scrollable;
/*     */ import javax.swing.SwingUtilities;
/*     */ import javax.swing.ToolTipManager;
/*     */ import javax.swing.UIManager;
/*     */ import javax.swing.event.PopupMenuEvent;
/*     */ import javax.swing.event.PopupMenuListener;
/*     */ import javax.swing.plaf.PopupMenuUI;
/*     */ 
/*     */ public class JidePopupMenu extends JPopupMenu
/*     */   implements Scrollable
/*     */ {
/*     */   private static final String uiClassID = "JidePopupMenuUI";
/*     */   private boolean _useLightWeightPopup;
/*     */ 
/*     */   public JidePopupMenu()
/*     */   {
/*  35 */     setupPopupMenu();
/*     */   }
/*     */ 
/*     */   public JidePopupMenu(String label)
/*     */   {
/*  44 */     super(label);
/*  45 */     setupPopupMenu();
/*     */   }
/*     */ 
/*     */   public String getUIClassID()
/*     */   {
/*  50 */     return "JidePopupMenuUI";
/*     */   }
/*     */ 
/*     */   private void setupPopupMenu() {
/*  54 */     addPopupMenuListener(new ToolTipSwitchPopupMenuListener(null));
/*     */   }
/*     */ 
/*     */   public void updateUI()
/*     */   {
/*  59 */     if (UIDefaultsLookup.get("JidePopupMenuUI") == null) {
/*  60 */       LookAndFeelFactory.installJideExtension();
/*     */     }
/*  62 */     setUI((PopupMenuUI)UIManager.getUI(this));
/*     */   }
/*     */ 
/*     */   public void show(Component invoker, int x, int y)
/*     */   {
/*  74 */     Point p = getPopupMenuOrigin(invoker, x, y);
/*  75 */     super.show(invoker, p.x, p.y);
/*     */   }
/*     */ 
/*     */   protected Point getPopupMenuOrigin(Component invoker, int x, int y)
/*     */   {
/*  88 */     Dimension size = getPreferredSize();
/*  89 */     if (size.width == 0) {
/*  90 */       size = getPreferredScrollableViewportSize();
/*     */     }
/*     */ 
/*  93 */     Point p = new Point(x, y);
/*  94 */     SwingUtilities.convertPointToScreen(p, invoker);
/*  95 */     Rectangle bounds = PortingUtils.ensureOnScreen(new Rectangle(p, size));
/*  96 */     p = bounds.getLocation();
/*  97 */     SwingUtilities.convertPointFromScreen(p, invoker);
/*  98 */     return p;
/*     */   }
/*     */ 
/*     */   public void setLocation(int x, int y)
/*     */   {
/* 104 */     if ((isVisible()) && (y <= 0)) {
/* 105 */       move(x, y);
/*     */     }
/*     */     else
/* 108 */       super.setLocation(x, y);
/*     */   }
/*     */ 
/*     */   public Dimension getPreferredScrollableViewportSize()
/*     */   {
/* 113 */     Dimension size = getPreferredSize();
/* 114 */     Dimension screenSize = PortingUtils.getLocalScreenSize(this);
/* 115 */     Container container = SwingUtilities.getAncestorOfClass(SimpleScrollPane.class, this);
/* 116 */     if ((container instanceof SimpleScrollPane)) {
/* 117 */       SimpleScrollPane scrollPane = (SimpleScrollPane)container;
/* 118 */       size.height = Math.min(size.height, screenSize.height - scrollPane.getScrollUpButton().getPreferredSize().height - scrollPane.getScrollDownButton().getPreferredSize().height);
/*     */     }
/* 120 */     return size;
/*     */   }
/*     */ 
/*     */   public int getScrollableUnitIncrement(Rectangle visibleRect, int orientation, int direction) {
/* 124 */     return new JMenuItem("ABC").getPreferredSize().height;
/*     */   }
/*     */ 
/*     */   public int getScrollableBlockIncrement(Rectangle visibleRect, int orientation, int direction) {
/* 128 */     return new JMenuItem("ABC").getPreferredSize().height * 5;
/*     */   }
/*     */ 
/*     */   public boolean getScrollableTracksViewportWidth() {
/* 132 */     return true;
/*     */   }
/*     */ 
/*     */   public boolean getScrollableTracksViewportHeight() {
/* 136 */     return false;
/*     */   }
/*     */   private class ToolTipSwitchPopupMenuListener implements PopupMenuListener {
/*     */     private ToolTipSwitchPopupMenuListener() {
/*     */     }
/* 141 */     public void popupMenuWillBecomeVisible(PopupMenuEvent e) { JidePopupMenu.access$102(JidePopupMenu.this, ToolTipManager.sharedInstance().isLightWeightPopupEnabled());
/* 142 */       ToolTipManager.sharedInstance().setLightWeightPopupEnabled(false); }
/*     */ 
/*     */     public void popupMenuWillBecomeInvisible(PopupMenuEvent e)
/*     */     {
/* 146 */       ToolTipManager.sharedInstance().setLightWeightPopupEnabled(JidePopupMenu.this._useLightWeightPopup);
/*     */     }
/*     */ 
/*     */     public void popupMenuCanceled(PopupMenuEvent e) {
/* 150 */       ToolTipManager.sharedInstance().setLightWeightPopupEnabled(JidePopupMenu.this._useLightWeightPopup);
/*     */     }
/*     */   }
/*     */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     com.jidesoft.swing.JidePopupMenu
 * JD-Core Version:    0.6.0
 */