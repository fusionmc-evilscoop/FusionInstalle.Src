/*     */ package org.apache.log4j.helpers;
/*     */ 
/*     */ public class Transform
/*     */ {
/*     */   private static final String CDATA_START = "<![CDATA[";
/*     */   private static final String CDATA_END = "]]>";
/*     */   private static final String CDATA_PSEUDO_END = "]]&gt;";
/*     */   private static final String CDATA_EMBEDED_END = "]]>]]&gt;<![CDATA[";
/*  34 */   private static final int CDATA_END_LEN = "]]>".length();
/*     */ 
/*     */   public static String escapeTags(String input)
/*     */   {
/*  49 */     if ((input == null) || (input.length() == 0)) {
/*  50 */       return input;
/*     */     }
/*     */ 
/*  56 */     StringBuffer buf = new StringBuffer(input.length() + 6);
/*  57 */     char ch = ' ';
/*     */ 
/*  59 */     int len = input.length();
/*  60 */     for (int i = 0; i < len; i++) {
/*  61 */       ch = input.charAt(i);
/*  62 */       if (ch == '<')
/*  63 */         buf.append("&lt;");
/*  64 */       else if (ch == '>')
/*  65 */         buf.append("&gt;");
/*     */       else {
/*  67 */         buf.append(ch);
/*     */       }
/*     */     }
/*  70 */     return buf.toString();
/*     */   }
/*     */ 
/*     */   public static void appendEscapingCDATA(StringBuffer buf, String str)
/*     */   {
/*  83 */     if (str == null) {
/*  84 */       buf.append("");
/*  85 */       return;
/*     */     }
/*     */ 
/*  88 */     int end = str.indexOf("]]>");
/*  89 */     if (end < 0) {
/*  90 */       buf.append(str);
/*  91 */       return;
/*     */     }
/*     */ 
/*  94 */     int start = 0;
/*  95 */     while (end > -1) {
/*  96 */       buf.append(str.substring(start, end));
/*  97 */       buf.append("]]>]]&gt;<![CDATA[");
/*  98 */       start = end + CDATA_END_LEN;
/*  99 */       if (start < str.length())
/* 100 */         end = str.indexOf("]]>", start);
/*     */       else {
/* 102 */         return;
/*     */       }
/*     */     }
/*     */ 
/* 106 */     buf.append(str.substring(start));
/*     */   }
/*     */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     org.apache.log4j.helpers.Transform
 * JD-Core Version:    0.6.0
 */