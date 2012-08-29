/*     */ package com.jidesoft.spinner;
/*     */ 
/*     */ import java.awt.Point;
/*     */ import javax.swing.AbstractSpinnerModel;
/*     */ 
/*     */ public class SpinnerPointModel extends AbstractSpinnerModel
/*     */ {
/*     */   public static final int FIELD_X = 0;
/*     */   public static final int FIELD_Y = 1;
/*     */   private Point point;
/*  21 */   private int field = 0;
/*     */ 
/*     */   public SpinnerPointModel()
/*     */   {
/*  27 */     this(null);
/*     */   }
/*     */ 
/*     */   public SpinnerPointModel(Point point)
/*     */   {
/*  36 */     this.point = (point == null ? new Point() : point);
/*     */   }
/*     */ 
/*     */   public Object getValue()
/*     */   {
/*  48 */     return this.point;
/*     */   }
/*     */ 
/*     */   public void setValue(Object value)
/*     */   {
/*  63 */     if ((value instanceof Point))
/*  64 */       setPoint((Point)value);
/*     */   }
/*     */ 
/*     */   public Point getPoint()
/*     */   {
/*  78 */     return this.point;
/*     */   }
/*     */ 
/*     */   public void setPoint(Point point)
/*     */   {
/*  85 */     if (!this.point.equals(point)) {
/*  86 */       this.point = point;
/*  87 */       fireStateChanged();
/*     */     }
/*     */   }
/*     */ 
/*     */   public int getField()
/*     */   {
/*  95 */     return this.field;
/*     */   }
/*     */ 
/*     */   public void setField(int field)
/*     */   {
/* 102 */     this.field = field;
/*     */   }
/*     */ 
/*     */   public Object getNextValue()
/*     */   {
/* 115 */     Point p = (Point)this.point.clone();
/* 116 */     if (this.field == 0) {
/* 117 */       p.x += 1;
/*     */     }
/*     */     else {
/* 120 */       p.y += 1;
/*     */     }
/* 122 */     return p;
/*     */   }
/*     */ 
/*     */   public Object getPreviousValue()
/*     */   {
/* 135 */     Point p = (Point)this.point.clone();
/* 136 */     if (this.field == 0) {
/* 137 */       p.x -= 1;
/*     */     }
/*     */     else {
/* 140 */       p.y -= 1;
/*     */     }
/* 142 */     return p;
/*     */   }
/*     */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     com.jidesoft.spinner.SpinnerPointModel
 * JD-Core Version:    0.6.0
 */