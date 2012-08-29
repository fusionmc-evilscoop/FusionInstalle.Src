/*    */ package org.apache.commons.io.output;
/*    */ 
/*    */ import java.io.FilterOutputStream;
/*    */ import java.io.IOException;
/*    */ import java.io.OutputStream;
/*    */ 
/*    */ public class ProxyOutputStream extends FilterOutputStream
/*    */ {
/*    */   public ProxyOutputStream(OutputStream proxy)
/*    */   {
/* 40 */     super(proxy);
/*    */   }
/*    */ 
/*    */   public void write(int idx)
/*    */     throws IOException
/*    */   {
/* 46 */     this.out.write(idx);
/*    */   }
/*    */ 
/*    */   public void write(byte[] bts) throws IOException
/*    */   {
/* 51 */     this.out.write(bts);
/*    */   }
/*    */ 
/*    */   public void write(byte[] bts, int st, int end) throws IOException
/*    */   {
/* 56 */     this.out.write(bts, st, end);
/*    */   }
/*    */ 
/*    */   public void flush() throws IOException
/*    */   {
/* 61 */     this.out.flush();
/*    */   }
/*    */ 
/*    */   public void close() throws IOException
/*    */   {
/* 66 */     this.out.close();
/*    */   }
/*    */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     org.apache.commons.io.output.ProxyOutputStream
 * JD-Core Version:    0.6.0
 */