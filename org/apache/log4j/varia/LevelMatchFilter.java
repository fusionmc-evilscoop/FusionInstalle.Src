/*     */ package org.apache.log4j.varia;
/*     */ 
/*     */ import org.apache.log4j.Level;
/*     */ import org.apache.log4j.Priority;
/*     */ import org.apache.log4j.helpers.OptionConverter;
/*     */ import org.apache.log4j.spi.Filter;
/*     */ import org.apache.log4j.spi.LoggingEvent;
/*     */ 
/*     */ public class LevelMatchFilter extends Filter
/*     */ {
/*  44 */   boolean acceptOnMatch = true;
/*     */   Level levelToMatch;
/*     */ 
/*     */   public void setLevelToMatch(String level)
/*     */   {
/*  53 */     this.levelToMatch = OptionConverter.toLevel(level, null);
/*     */   }
/*     */ 
/*     */   public String getLevelToMatch()
/*     */   {
/*  58 */     return this.levelToMatch == null ? null : this.levelToMatch.toString();
/*     */   }
/*     */ 
/*     */   public void setAcceptOnMatch(boolean acceptOnMatch)
/*     */   {
/*  63 */     this.acceptOnMatch = acceptOnMatch;
/*     */   }
/*     */ 
/*     */   public boolean getAcceptOnMatch()
/*     */   {
/*  68 */     return this.acceptOnMatch;
/*     */   }
/*     */ 
/*     */   public int decide(LoggingEvent event)
/*     */   {
/*  85 */     if (this.levelToMatch == null) {
/*  86 */       return 0;
/*     */     }
/*     */ 
/*  89 */     boolean matchOccured = false;
/*  90 */     if (this.levelToMatch.equals(event.getLevel())) {
/*  91 */       matchOccured = true;
/*     */     }
/*     */ 
/*  94 */     if (matchOccured) {
/*  95 */       if (this.acceptOnMatch) {
/*  96 */         return 1;
/*     */       }
/*  98 */       return -1;
/*     */     }
/* 100 */     return 0;
/*     */   }
/*     */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     org.apache.log4j.varia.LevelMatchFilter
 * JD-Core Version:    0.6.0
 */