/*    */ package com.jidesoft.icons;
/*    */ 
/*    */ import java.awt.Component;
/*    */ import java.awt.Graphics;
/*    */ import java.io.Serializable;
/*    */ import javax.swing.AbstractButton;
/*    */ import javax.swing.Icon;
/*    */ import javax.swing.ImageIcon;
/*    */ import javax.swing.plaf.UIResource;
/*    */ 
/*    */ public class MenuCheckIcon
/*    */   implements Icon, UIResource, Serializable
/*    */ {
/*    */   private ImageIcon _icon;
/*    */   private static final long serialVersionUID = -6303936713472505092L;
/*    */ 
/*    */   public MenuCheckIcon(ImageIcon icon)
/*    */   {
/* 28 */     if (icon == null) {
/* 29 */       throw new IllegalArgumentException("The icon should not be null.");
/*    */     }
/* 31 */     this._icon = icon;
/*    */   }
/*    */ 
/*    */   public int getIconHeight() {
/* 35 */     return getIcon().getIconHeight();
/*    */   }
/*    */ 
/*    */   public int getIconWidth() {
/* 39 */     return getIcon().getIconWidth();
/*    */   }
/*    */ 
/*    */   public void paintIcon(Component c, Graphics g, int x, int y) {
/* 43 */     Icon icon = getIcon();
/* 44 */     if ((c instanceof AbstractButton)) {
/* 45 */       AbstractButton b = (AbstractButton)c;
/* 46 */       if (b.isSelected())
/* 47 */         icon.paintIcon(c, g, x, y);
/*    */     }
/*    */   }
/*    */ 
/*    */   private Icon getIcon()
/*    */   {
/* 53 */     return this._icon;
/*    */   }
/*    */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     com.jidesoft.icons.MenuCheckIcon
 * JD-Core Version:    0.6.0
 */