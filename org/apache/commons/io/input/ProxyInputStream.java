/*    */ package org.apache.commons.io.input;
/*    */ 
/*    */ import java.io.FilterInputStream;
/*    */ import java.io.IOException;
/*    */ import java.io.InputStream;
/*    */ 
/*    */ public abstract class ProxyInputStream extends FilterInputStream
/*    */ {
/*    */   public ProxyInputStream(InputStream proxy)
/*    */   {
/* 43 */     super(proxy);
/*    */   }
/*    */ 
/*    */   public int read()
/*    */     throws IOException
/*    */   {
/* 49 */     return this.in.read();
/*    */   }
/*    */ 
/*    */   public int read(byte[] bts) throws IOException
/*    */   {
/* 54 */     return this.in.read(bts);
/*    */   }
/*    */ 
/*    */   public int read(byte[] bts, int st, int end) throws IOException
/*    */   {
/* 59 */     return this.in.read(bts, st, end);
/*    */   }
/*    */ 
/*    */   public long skip(long ln) throws IOException
/*    */   {
/* 64 */     return this.in.skip(ln);
/*    */   }
/*    */ 
/*    */   public int available() throws IOException
/*    */   {
/* 69 */     return this.in.available();
/*    */   }
/*    */ 
/*    */   public void close() throws IOException
/*    */   {
/* 74 */     this.in.close();
/*    */   }
/*    */ 
/*    */   public synchronized void mark(int idx)
/*    */   {
/* 79 */     this.in.mark(idx);
/*    */   }
/*    */ 
/*    */   public synchronized void reset() throws IOException
/*    */   {
/* 84 */     this.in.reset();
/*    */   }
/*    */ 
/*    */   public boolean markSupported()
/*    */   {
/* 89 */     return this.in.markSupported();
/*    */   }
/*    */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     org.apache.commons.io.input.ProxyInputStream
 * JD-Core Version:    0.6.0
 */