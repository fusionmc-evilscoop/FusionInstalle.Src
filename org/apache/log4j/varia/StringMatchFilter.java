/*     */ package org.apache.log4j.varia;
/*     */ 
/*     */ import org.apache.log4j.helpers.OptionConverter;
/*     */ import org.apache.log4j.spi.Filter;
/*     */ import org.apache.log4j.spi.LoggingEvent;
/*     */ 
/*     */ public class StringMatchFilter extends Filter
/*     */ {
/*     */ 
/*     */   /** @deprecated */
/*     */   public static final String STRING_TO_MATCH_OPTION = "StringToMatch";
/*     */ 
/*     */   /** @deprecated */
/*     */   public static final String ACCEPT_ON_MATCH_OPTION = "AcceptOnMatch";
/*  53 */   boolean acceptOnMatch = true;
/*     */   String stringToMatch;
/*     */ 
/*     */   /** @deprecated */
/*     */   public String[] getOptionStrings()
/*     */   {
/*  62 */     return new String[] { "StringToMatch", "AcceptOnMatch" };
/*     */   }
/*     */ 
/*     */   /** @deprecated */
/*     */   public void setOption(String key, String value)
/*     */   {
/*  72 */     if (key.equalsIgnoreCase("StringToMatch"))
/*  73 */       this.stringToMatch = value;
/*  74 */     else if (key.equalsIgnoreCase("AcceptOnMatch"))
/*  75 */       this.acceptOnMatch = OptionConverter.toBoolean(value, this.acceptOnMatch);
/*     */   }
/*     */ 
/*     */   public void setStringToMatch(String s)
/*     */   {
/*  81 */     this.stringToMatch = s;
/*     */   }
/*     */ 
/*     */   public String getStringToMatch()
/*     */   {
/*  86 */     return this.stringToMatch;
/*     */   }
/*     */ 
/*     */   public void setAcceptOnMatch(boolean acceptOnMatch)
/*     */   {
/*  91 */     this.acceptOnMatch = acceptOnMatch;
/*     */   }
/*     */ 
/*     */   public boolean getAcceptOnMatch()
/*     */   {
/*  96 */     return this.acceptOnMatch;
/*     */   }
/*     */ 
/*     */   public int decide(LoggingEvent event)
/*     */   {
/* 104 */     String msg = event.getRenderedMessage();
/*     */ 
/* 106 */     if ((msg == null) || (this.stringToMatch == null)) {
/* 107 */       return 0;
/*     */     }
/*     */ 
/* 110 */     if (msg.indexOf(this.stringToMatch) == -1) {
/* 111 */       return 0;
/*     */     }
/* 113 */     if (this.acceptOnMatch) {
/* 114 */       return 1;
/*     */     }
/* 116 */     return -1;
/*     */   }
/*     */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     org.apache.log4j.varia.StringMatchFilter
 * JD-Core Version:    0.6.0
 */