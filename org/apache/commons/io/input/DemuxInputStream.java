/*    */ package org.apache.commons.io.input;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import java.io.InputStream;
/*    */ 
/*    */ public class DemuxInputStream extends InputStream
/*    */ {
/* 32 */   private InheritableThreadLocal m_streams = new InheritableThreadLocal();
/*    */ 
/*    */   public InputStream bindStream(InputStream input)
/*    */   {
/* 42 */     InputStream oldValue = getStream();
/* 43 */     this.m_streams.set(input);
/* 44 */     return oldValue;
/*    */   }
/*    */ 
/*    */   public void close()
/*    */     throws IOException
/*    */   {
/* 55 */     InputStream input = getStream();
/* 56 */     if (null != input)
/*    */     {
/* 58 */       input.close();
/*    */     }
/*    */   }
/*    */ 
/*    */   public int read()
/*    */     throws IOException
/*    */   {
/* 71 */     InputStream input = getStream();
/* 72 */     if (null != input)
/*    */     {
/* 74 */       return input.read();
/*    */     }
/*    */ 
/* 78 */     return -1;
/*    */   }
/*    */ 
/*    */   private InputStream getStream()
/*    */   {
/* 89 */     return (InputStream)this.m_streams.get();
/*    */   }
/*    */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     org.apache.commons.io.input.DemuxInputStream
 * JD-Core Version:    0.6.0
 */