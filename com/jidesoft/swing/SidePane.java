/*     */ package com.jidesoft.swing;
/*     */ 
/*     */ import com.jidesoft.plaf.LookAndFeelFactory;
/*     */ import com.jidesoft.plaf.SidePaneUI;
/*     */ import com.jidesoft.plaf.UIDefaultsLookup;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import javax.accessibility.Accessible;
/*     */ import javax.accessibility.AccessibleContext;
/*     */ import javax.accessibility.AccessibleRole;
/*     */ import javax.accessibility.AccessibleStateSet;
/*     */ import javax.swing.JPanel;
/*     */ import javax.swing.JPanel.AccessibleJPanel;
/*     */ import javax.swing.SwingConstants;
/*     */ import javax.swing.UIManager;
/*     */ 
/*     */ public class SidePane extends JPanel
/*     */   implements SwingConstants, Accessible
/*     */ {
/*  34 */   private final List<SidePaneGroup> _groups = new ArrayList();
/*     */   private int _attachedSide;
/*  50 */   private boolean _rollover = true;
/*     */   private static final String uiClassID = "SidePaneUI";
/*     */ 
/*     */   public SidePane(int attachedSide)
/*     */   {
/*  71 */     setAttachedSide(attachedSide);
/*     */   }
/*     */ 
/*     */   public SidePaneUI getUI()
/*     */   {
/*  82 */     return (SidePaneUI)this.ui;
/*     */   }
/*     */ 
/*     */   public void setUI(SidePaneUI ui)
/*     */   {
/*  92 */     super.setUI(ui);
/*     */   }
/*     */ 
/*     */   public void updateUI()
/*     */   {
/* 102 */     if (UIDefaultsLookup.get("SidePaneUI") == null) {
/* 103 */       LookAndFeelFactory.installJideExtension();
/*     */     }
/* 105 */     setUI((SidePaneUI)UIManager.getUI(this));
/*     */   }
/*     */ 
/*     */   public String getUIClassID()
/*     */   {
/* 119 */     return "SidePaneUI";
/*     */   }
/*     */ 
/*     */   public void addGroup(SidePaneGroup group)
/*     */   {
/* 129 */     getGroups().add(group);
/*     */   }
/*     */ 
/*     */   public void removeGroup(SidePaneGroup group)
/*     */   {
/* 138 */     getGroups().remove(group);
/*     */   }
/*     */ 
/*     */   public void removeGroup(int index)
/*     */   {
/* 147 */     removeGroup((SidePaneGroup)getGroups().get(index));
/*     */   }
/*     */ 
/*     */   public List<SidePaneGroup> getGroups()
/*     */   {
/* 156 */     return this._groups;
/*     */   }
/*     */ 
/*     */   public int getAttachedSide()
/*     */   {
/* 165 */     return this._attachedSide;
/*     */   }
/*     */ 
/*     */   public void setAttachedSide(int attachedSide)
/*     */   {
/* 183 */     if ((attachedSide != 1) && (attachedSide != 5) && (attachedSide != 7) && (attachedSide != 3))
/*     */     {
/* 185 */       throw new IllegalArgumentException("illegal attached side: must be NORTH, SOUTH, WEST, or EAST");
/*     */     }
/* 187 */     this._attachedSide = attachedSide;
/* 188 */     updateUI();
/*     */   }
/*     */ 
/*     */   public boolean isRollover()
/*     */   {
/* 197 */     return this._rollover;
/*     */   }
/*     */ 
/*     */   public void setRollover(boolean rollover)
/*     */   {
/* 206 */     this._rollover = rollover;
/* 207 */     updateUI();
/*     */   }
/*     */ 
/*     */   public AccessibleContext getAccessibleContext()
/*     */   {
/* 221 */     if (this.accessibleContext == null) {
/* 222 */       this.accessibleContext = new AccessibleSidePane();
/*     */     }
/* 224 */     return this.accessibleContext;
/*     */   }
/*     */ 
/*     */   protected class AccessibleSidePane extends JPanel.AccessibleJPanel
/*     */   {
/*     */     protected AccessibleSidePane()
/*     */     {
/* 232 */       super();
/*     */     }
/*     */ 
/*     */     public AccessibleStateSet getAccessibleStateSet()
/*     */     {
/* 243 */       AccessibleStateSet states = super.getAccessibleStateSet();
/* 244 */       return states;
/*     */     }
/*     */ 
/*     */     public AccessibleRole getAccessibleRole()
/*     */     {
/* 254 */       return AccessibleRole.PAGE_TAB_LIST;
/*     */     }
/*     */   }
/*     */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     com.jidesoft.swing.SidePane
 * JD-Core Version:    0.6.0
 */