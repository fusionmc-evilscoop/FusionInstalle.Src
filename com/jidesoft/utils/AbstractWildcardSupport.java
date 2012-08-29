/*    */ package com.jidesoft.utils;
/*    */ 
/*    */ import java.io.Serializable;
/*    */ 
/*    */ public abstract class AbstractWildcardSupport
/*    */   implements WildcardSupport, Serializable
/*    */ {
/*    */   public String convert(String s)
/*    */   {
/* 13 */     char zeroOrMoreQuantifier = getZeroOrMoreQuantifier();
/* 14 */     int posAny = zeroOrMoreQuantifier == 0 ? -1 : s.indexOf(zeroOrMoreQuantifier);
/* 15 */     char zeroOrOneQuantifier = getZeroOrOneQuantifier();
/* 16 */     int posOne = zeroOrOneQuantifier == 0 ? -1 : s.indexOf(zeroOrOneQuantifier);
/* 17 */     char oneOrMoreQuantifier = getOneOrMoreQuantifier();
/* 18 */     int posOneOrMore = oneOrMoreQuantifier == 0 ? -1 : s.indexOf(oneOrMoreQuantifier);
/*    */ 
/* 20 */     if ((posAny == -1) && (posOne == -1) && (posOneOrMore == -1)) {
/* 21 */       return s;
/*    */     }
/*    */ 
/* 24 */     StringBuffer buffer = new StringBuffer();
/* 25 */     int length = s.length();
/* 26 */     for (int i = 0; i < length; i++) {
/* 27 */       char c = s.charAt(i);
/* 28 */       if ((zeroOrOneQuantifier != 0) && (c == zeroOrOneQuantifier)) {
/* 29 */         buffer.append(".");
/*    */       }
/* 31 */       else if ((zeroOrMoreQuantifier != 0) && (c == zeroOrMoreQuantifier)) {
/* 32 */         buffer.append(".*");
/*    */       }
/* 34 */       else if ((oneOrMoreQuantifier != 0) && (c == oneOrMoreQuantifier)) {
/* 35 */         buffer.append("..*");
/*    */       }
/* 37 */       else if ("(){}[].^$\\".indexOf(c) != -1) {
/* 38 */         buffer.append('\\');
/* 39 */         buffer.append(c);
/*    */       }
/*    */       else {
/* 42 */         buffer.append(c);
/*    */       }
/*    */     }
/*    */ 
/* 46 */     return buffer.toString();
/*    */   }
/*    */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     com.jidesoft.utils.AbstractWildcardSupport
 * JD-Core Version:    0.6.0
 */