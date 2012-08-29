/*     */ package com.jidesoft.plaf.aqua;
/*     */ 
/*     */ class XMLParseException extends RuntimeException
/*     */ {
/*     */   public static final int NO_LINE = -1;
/*     */   private int lineNr;
/*     */ 
/*     */   public XMLParseException(String name, String message)
/*     */   {
/*  72 */     super("XML Parse Exception during parsing of " + (name == null ? "the XML definition" : new StringBuilder().append("a ").append(name).append(" element").toString()) + ": " + message);
/*     */ 
/*  76 */     this.lineNr = -1;
/*     */   }
/*     */ 
/*     */   public XMLParseException(String name, int lineNr, String message)
/*     */   {
/*  95 */     super("XML Parse Exception during parsing of " + (name == null ? "the XML definition" : new StringBuilder().append("a ").append(name).append(" element").toString()) + " at line " + lineNr + ": " + message);
/*     */ 
/*  99 */     this.lineNr = lineNr;
/*     */   }
/*     */ 
/*     */   public int getLineNr()
/*     */   {
/* 109 */     return this.lineNr;
/*     */   }
/*     */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     com.jidesoft.plaf.aqua.XMLParseException
 * JD-Core Version:    0.6.0
 */