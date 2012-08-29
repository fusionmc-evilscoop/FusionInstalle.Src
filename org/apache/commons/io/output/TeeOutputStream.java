/*    */ package org.apache.commons.io.output;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import java.io.OutputStream;
/*    */ 
/*    */ public class TeeOutputStream extends ProxyOutputStream
/*    */ {
/*    */   protected OutputStream branch;
/*    */ 
/*    */   public TeeOutputStream(OutputStream out, OutputStream branch)
/*    */   {
/* 40 */     super(out);
/* 41 */     this.branch = branch;
/*    */   }
/*    */ 
/*    */   public synchronized void write(byte[] b) throws IOException
/*    */   {
/* 46 */     super.write(b);
/* 47 */     this.branch.write(b);
/*    */   }
/*    */ 
/*    */   public synchronized void write(byte[] b, int off, int len) throws IOException
/*    */   {
/* 52 */     super.write(b, off, len);
/* 53 */     this.branch.write(b, off, len);
/*    */   }
/*    */ 
/*    */   public synchronized void write(int b) throws IOException
/*    */   {
/* 58 */     super.write(b);
/* 59 */     this.branch.write(b);
/*    */   }
/*    */ 
/*    */   public void flush()
/*    */     throws IOException
/*    */   {
/* 68 */     super.flush();
/* 69 */     this.branch.flush();
/*    */   }
/*    */ 
/*    */   public void close()
/*    */     throws IOException
/*    */   {
/* 78 */     super.close();
/* 79 */     this.branch.close();
/*    */   }
/*    */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     org.apache.commons.io.output.TeeOutputStream
 * JD-Core Version:    0.6.0
 */