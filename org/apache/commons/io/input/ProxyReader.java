/*    */ package org.apache.commons.io.input;
/*    */ 
/*    */ import java.io.FilterReader;
/*    */ import java.io.IOException;
/*    */ import java.io.Reader;
/*    */ 
/*    */ public abstract class ProxyReader extends FilterReader
/*    */ {
/*    */   public ProxyReader(Reader proxy)
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
/*    */   public int read(char[] chr) throws IOException
/*    */   {
/* 54 */     return this.in.read(chr);
/*    */   }
/*    */ 
/*    */   public int read(char[] chr, int st, int end) throws IOException
/*    */   {
/* 59 */     return this.in.read(chr, st, end);
/*    */   }
/*    */ 
/*    */   public long skip(long ln) throws IOException
/*    */   {
/* 64 */     return this.in.skip(ln);
/*    */   }
/*    */ 
/*    */   public boolean ready() throws IOException
/*    */   {
/* 69 */     return this.in.ready();
/*    */   }
/*    */ 
/*    */   public void close() throws IOException
/*    */   {
/* 74 */     this.in.close();
/*    */   }
/*    */ 
/*    */   public synchronized void mark(int idx) throws IOException
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
 * Qualified Name:     org.apache.commons.io.input.ProxyReader
 * JD-Core Version:    0.6.0
 */