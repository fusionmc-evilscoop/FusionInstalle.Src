/*    */ package com.jidesoft.swing;
/*    */ 
/*    */ import com.jidesoft.plaf.UIDefaultsLookup;
/*    */ import java.awt.BorderLayout;
/*    */ import java.awt.LayoutManager;
/*    */ import javax.swing.JPanel;
/*    */ 
/*    */ public class ResizablePanel extends JPanel
/*    */   implements ResizableSupport
/*    */ {
/*    */   private Resizable _resizable;
/*    */ 
/*    */   public ResizablePanel()
/*    */   {
/* 24 */     initComponents();
/*    */   }
/*    */ 
/*    */   public ResizablePanel(boolean isDoubleBuffered)
/*    */   {
/* 35 */     super(isDoubleBuffered);
/* 36 */     initComponents();
/*    */   }
/*    */ 
/*    */   public ResizablePanel(LayoutManager layout)
/*    */   {
/* 45 */     super(layout);
/* 46 */     initComponents();
/*    */   }
/*    */ 
/*    */   public ResizablePanel(LayoutManager layout, boolean isDoubleBuffered)
/*    */   {
/* 57 */     super(layout, isDoubleBuffered);
/* 58 */     initComponents();
/*    */   }
/*    */ 
/*    */   protected void initComponents()
/*    */   {
/* 65 */     this._resizable = createResizable();
/* 66 */     setLayout(new BorderLayout());
/*    */   }
/*    */ 
/*    */   protected Resizable createResizable()
/*    */   {
/* 76 */     return new Resizable(this);
/*    */   }
/*    */ 
/*    */   public Resizable getResizable()
/*    */   {
/* 85 */     return this._resizable;
/*    */   }
/*    */ 
/*    */   public void updateUI()
/*    */   {
/* 93 */     super.updateUI();
/* 94 */     setBorder(UIDefaultsLookup.getBorder("Resizable.resizeBorder"));
/*    */   }
/*    */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     com.jidesoft.swing.ResizablePanel
 * JD-Core Version:    0.6.0
 */