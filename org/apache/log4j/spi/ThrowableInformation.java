/*    */ package org.apache.log4j.spi;
/*    */ 
/*    */ import java.io.Serializable;
/*    */ 
/*    */ public class ThrowableInformation
/*    */   implements Serializable
/*    */ {
/*    */   static final long serialVersionUID = -4748765566864322735L;
/*    */   private transient Throwable throwable;
/*    */   private String[] rep;
/*    */ 
/*    */   public ThrowableInformation(Throwable throwable)
/*    */   {
/* 45 */     this.throwable = throwable;
/*    */   }
/*    */ 
/*    */   public Throwable getThrowable()
/*    */   {
/* 50 */     return this.throwable;
/*    */   }
/*    */ 
/*    */   public String[] getThrowableStrRep()
/*    */   {
/* 55 */     if (this.rep != null) {
/* 56 */       return (String[])this.rep.clone();
/*    */     }
/* 58 */     VectorWriter vw = new VectorWriter();
/* 59 */     this.throwable.printStackTrace(vw);
/* 60 */     this.rep = vw.toStringArray();
/* 61 */     return this.rep;
/*    */   }
/*    */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     org.apache.log4j.spi.ThrowableInformation
 * JD-Core Version:    0.6.0
 */