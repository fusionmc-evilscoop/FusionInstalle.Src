/*     */ package org.apache.log4j.varia;
/*     */ 
/*     */ import org.apache.log4j.Level;
/*     */ import org.apache.log4j.Priority;
/*     */ import org.apache.log4j.spi.Filter;
/*     */ import org.apache.log4j.spi.LoggingEvent;
/*     */ 
/*     */ public class LevelRangeFilter extends Filter
/*     */ {
/*  59 */   boolean acceptOnMatch = false;
/*     */   Level levelMin;
/*     */   Level levelMax;
/*     */ 
/*     */   public int decide(LoggingEvent event)
/*     */   {
/*  70 */     if ((this.levelMin != null) && 
/*  71 */       (!event.getLevel().isGreaterOrEqual(this.levelMin)))
/*     */     {
/*  73 */       return -1;
/*     */     }
/*     */ 
/*  77 */     if ((this.levelMax != null) && 
/*  78 */       (event.getLevel().toInt() > this.levelMax.toInt()))
/*     */     {
/*  83 */       return -1;
/*     */     }
/*     */ 
/*  87 */     if (this.acceptOnMatch)
/*     */     {
/*  90 */       return 1;
/*     */     }
/*     */ 
/*  94 */     return 0;
/*     */   }
/*     */ 
/*     */   public Level getLevelMax()
/*     */   {
/* 102 */     return this.levelMax;
/*     */   }
/*     */ 
/*     */   public Level getLevelMin()
/*     */   {
/* 110 */     return this.levelMin;
/*     */   }
/*     */ 
/*     */   public boolean getAcceptOnMatch()
/*     */   {
/* 118 */     return this.acceptOnMatch;
/*     */   }
/*     */ 
/*     */   public void setLevelMax(Level levelMax)
/*     */   {
/* 126 */     this.levelMax = levelMax;
/*     */   }
/*     */ 
/*     */   public void setLevelMin(Level levelMin)
/*     */   {
/* 134 */     this.levelMin = levelMin;
/*     */   }
/*     */ 
/*     */   public void setAcceptOnMatch(boolean acceptOnMatch)
/*     */   {
/* 142 */     this.acceptOnMatch = acceptOnMatch;
/*     */   }
/*     */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     org.apache.log4j.varia.LevelRangeFilter
 * JD-Core Version:    0.6.0
 */