/*    */ package org.apache.commons.io.output;
/*    */ 
/*    */ import java.io.FilterWriter;
/*    */ import java.io.IOException;
/*    */ import java.io.Writer;
/*    */ 
/*    */ public class ProxyWriter extends FilterWriter
/*    */ {
/*    */   public ProxyWriter(Writer proxy)
/*    */   {
/* 42 */     super(proxy);
/*    */   }
/*    */ 
/*    */   public void write(int idx)
/*    */     throws IOException
/*    */   {
/* 48 */     this.out.write(idx);
/*    */   }
/*    */ 
/*    */   public void write(char[] chr) throws IOException
/*    */   {
/* 53 */     this.out.write(chr);
/*    */   }
/*    */ 
/*    */   public void write(char[] chr, int st, int end) throws IOException
/*    */   {
/* 58 */     this.out.write(chr, st, end);
/*    */   }
/*    */ 
/*    */   public void write(String str) throws IOException
/*    */   {
/* 63 */     this.out.write(str);
/*    */   }
/*    */ 
/*    */   public void write(String str, int st, int end) throws IOException
/*    */   {
/* 68 */     this.out.write(str, st, end);
/*    */   }
/*    */ 
/*    */   public void flush() throws IOException
/*    */   {
/* 73 */     this.out.flush();
/*    */   }
/*    */ 
/*    */   public void close() throws IOException
/*    */   {
/* 78 */     this.out.close();
/*    */   }
/*    */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     org.apache.commons.io.output.ProxyWriter
 * JD-Core Version:    0.6.0
 */