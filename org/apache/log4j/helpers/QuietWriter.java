/*    */ package org.apache.log4j.helpers;
/*    */ 
/*    */ import java.io.FilterWriter;
/*    */ import java.io.IOException;
/*    */ import java.io.Writer;
/*    */ import org.apache.log4j.spi.ErrorHandler;
/*    */ 
/*    */ public class QuietWriter extends FilterWriter
/*    */ {
/*    */   protected ErrorHandler errorHandler;
/*    */ 
/*    */   public QuietWriter(Writer writer, ErrorHandler errorHandler)
/*    */   {
/* 40 */     super(writer);
/* 41 */     setErrorHandler(errorHandler);
/*    */   }
/*    */ 
/*    */   public void write(String string)
/*    */   {
/*    */     try {
/* 47 */       this.out.write(string);
/*    */     } catch (IOException e) {
/* 49 */       this.errorHandler.error("Failed to write [" + string + "].", e, 1);
/*    */     }
/*    */   }
/*    */ 
/*    */   public void flush()
/*    */   {
/*    */     try
/*    */     {
/* 57 */       this.out.flush();
/*    */     } catch (IOException e) {
/* 59 */       this.errorHandler.error("Failed to flush writer,", e, 2);
/*    */     }
/*    */   }
/*    */ 
/*    */   public void setErrorHandler(ErrorHandler eh)
/*    */   {
/* 67 */     if (eh == null)
/*    */     {
/* 69 */       throw new IllegalArgumentException("Attempted to set null ErrorHandler.");
/*    */     }
/* 71 */     this.errorHandler = eh;
/*    */   }
/*    */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     org.apache.log4j.helpers.QuietWriter
 * JD-Core Version:    0.6.0
 */