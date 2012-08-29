/*    */ package com.jidesoft.swing;
/*    */ 
/*    */ import com.jidesoft.plaf.LookAndFeelFactory;
/*    */ import com.jidesoft.plaf.UIDefaultsLookup;
/*    */ import com.jidesoft.plaf.basic.ThemePainter;
/*    */ import java.awt.Graphics;
/*    */ import java.awt.Rectangle;
/*    */ import javax.swing.BorderFactory;
/*    */ import javax.swing.JPanel;
/*    */ import javax.swing.LookAndFeel;
/*    */ 
/*    */ public class ContentContainer extends JPanel
/*    */ {
/*    */   private ThemePainter _painter;
/*    */ 
/*    */   public ContentContainer()
/*    */   {
/* 29 */     setBorder(BorderFactory.createEmptyBorder());
/* 30 */     setOpaque(true);
/* 31 */     updateUI();
/* 32 */     setFocusCycleRoot(false);
/* 33 */     setFocusable(false);
/*    */   }
/*    */ 
/*    */   public void updateUI()
/*    */   {
/* 38 */     super.updateUI();
/* 39 */     if (UIDefaultsLookup.get("Theme.painter") == null) {
/* 40 */       LookAndFeelFactory.installJideExtension();
/*    */     }
/* 42 */     LookAndFeel.installColors(this, "ContentContainer.background", "ContentContainer.foreground");
/* 43 */     this._painter = ((ThemePainter)UIDefaultsLookup.get("Theme.painter"));
/*    */   }
/*    */ 
/*    */   protected void paintComponent(Graphics g)
/*    */   {
/* 48 */     super.paintComponent(g);
/* 49 */     if ((this._painter != null) && (isOpaque()))
/* 50 */       this._painter.paintContentBackground(this, g, new Rectangle(0, 0, getWidth(), getHeight()), 0, 0);
/*    */   }
/*    */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     com.jidesoft.swing.ContentContainer
 * JD-Core Version:    0.6.0
 */