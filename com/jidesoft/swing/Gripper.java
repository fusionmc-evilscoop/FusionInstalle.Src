/*     */ package com.jidesoft.swing;
/*     */ 
/*     */ import com.jidesoft.plaf.GripperUI;
/*     */ import com.jidesoft.plaf.LookAndFeelFactory;
/*     */ import com.jidesoft.plaf.UIDefaultsLookup;
/*     */ import java.awt.Cursor;
/*     */ import javax.swing.JComponent;
/*     */ import javax.swing.SwingConstants;
/*     */ import javax.swing.UIManager;
/*     */ import javax.swing.plaf.UIResource;
/*     */ 
/*     */ public class Gripper extends JComponent
/*     */   implements SwingConstants, Alignable, DragableHandle, UIResource
/*     */ {
/*     */   private static final String uiClassID = "GripperUI";
/*     */   public static final String ROLLOVER_ENABLED_CHANGED_PROPERTY = "rolloverEnabled";
/*  37 */   private boolean _rolloverEnabled = false;
/*     */   private boolean _rollover;
/*     */   public static final String ROLLOVER_PROPERTY = "ROLLOVER";
/*     */   public static final String SELECTED_PROPERTY = "SELECTED";
/*     */   private int _orientation;
/*     */   private boolean _selected;
/*     */ 
/*     */   public Gripper()
/*     */   {
/*  52 */     this(0);
/*     */   }
/*     */ 
/*     */   public Gripper(int orientation)
/*     */   {
/*  64 */     setOrientation(orientation);
/*  65 */     setFocusable(false);
/*  66 */     updateUI();
/*     */   }
/*     */ 
/*     */   public GripperUI getUI()
/*     */   {
/*  75 */     return (GripperUI)this.ui;
/*     */   }
/*     */ 
/*     */   public void updateUI()
/*     */   {
/*  85 */     if (UIDefaultsLookup.get("GripperUI") == null) {
/*  86 */       LookAndFeelFactory.installJideExtension();
/*     */     }
/*  88 */     setUI(UIManager.getUI(this));
/*     */   }
/*     */ 
/*     */   public String getUIClassID()
/*     */   {
/* 102 */     return "GripperUI";
/*     */   }
/*     */ 
/*     */   public boolean supportVerticalOrientation()
/*     */   {
/* 111 */     return true;
/*     */   }
/*     */ 
/*     */   public boolean supportHorizontalOrientation()
/*     */   {
/* 120 */     return true;
/*     */   }
/*     */ 
/*     */   public void setOrientation(int orientation)
/*     */   {
/* 129 */     int old = this._orientation;
/* 130 */     if (old != orientation) {
/* 131 */       this._orientation = orientation;
/* 132 */       firePropertyChange("orientation", old, orientation);
/*     */     }
/*     */   }
/*     */ 
/*     */   public int getOrientation()
/*     */   {
/* 142 */     return this._orientation;
/*     */   }
/*     */ 
/*     */   public Cursor getCursor()
/*     */   {
/* 154 */     if (isEnabled()) {
/* 155 */       return Cursor.getPredefinedCursor(13);
/*     */     }
/*     */ 
/* 158 */     return super.getCursor();
/*     */   }
/*     */ 
/*     */   public boolean isRolloverEnabled()
/*     */   {
/* 170 */     return this._rolloverEnabled;
/*     */   }
/*     */ 
/*     */   public void setRolloverEnabled(boolean b)
/*     */   {
/* 182 */     boolean oldValue = this._rolloverEnabled;
/* 183 */     if (b != oldValue) {
/* 184 */       this._rolloverEnabled = b;
/* 185 */       firePropertyChange("rolloverEnabled", oldValue, this._rolloverEnabled);
/* 186 */       repaint();
/*     */     }
/*     */   }
/*     */ 
/*     */   public boolean isRollover() {
/* 191 */     return this._rollover;
/*     */   }
/*     */ 
/*     */   public void setRollover(boolean rollover) {
/* 195 */     boolean oldValue = this._rollover;
/* 196 */     if (rollover != oldValue) {
/* 197 */       this._rollover = rollover;
/* 198 */       firePropertyChange("ROLLOVER", oldValue, rollover);
/* 199 */       repaint();
/*     */     }
/*     */   }
/*     */ 
/*     */   public boolean isSelected() {
/* 204 */     return this._selected;
/*     */   }
/*     */ 
/*     */   public void setSelected(boolean selected) {
/* 208 */     boolean oldValue = this._selected;
/* 209 */     if (selected != oldValue) {
/* 210 */       this._selected = selected;
/* 211 */       firePropertyChange("SELECTED", oldValue, this._selected);
/* 212 */       repaint();
/*     */     }
/*     */   }
/*     */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     com.jidesoft.swing.Gripper
 * JD-Core Version:    0.6.0
 */