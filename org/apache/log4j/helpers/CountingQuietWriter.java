/*    */ package org.apache.log4j.helpers;
/*    */ 
/*    */ import java.io.FilterWriter;
/*    */ import java.io.IOException;
/*    */ import java.io.Writer;
/*    */ import org.apache.log4j.spi.ErrorHandler;
/*    */ 
/*    */ public class CountingQuietWriter extends QuietWriter
/*    */ {
/*    */   protected long count;
/*    */ 
/*    */   public CountingQuietWriter(Writer writer, ErrorHandler eh)
/*    */   {
/* 38 */     super(writer, eh);
/*    */   }
/*    */ 
/*    */   public void write(String string)
/*    */   {
/*    */     try {
/* 44 */       this.out.write(string);
/* 45 */       this.count += string.length();
/*    */     }
/*    */     catch (IOException e) {
/* 48 */       this.errorHandler.error("Write failure.", e, 1);
/*    */     }
/*    */   }
/*    */ 
/*    */   public long getCount()
/*    */   {
/* 54 */     return this.count;
/*    */   }
/*    */ 
/*    */   public void setCount(long count)
/*    */   {
/* 59 */     this.count = count;
/*    */   }
/*    */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     org.apache.log4j.helpers.CountingQuietWriter
 * JD-Core Version:    0.6.0
 */