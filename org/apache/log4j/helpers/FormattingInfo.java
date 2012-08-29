/*    */ package org.apache.log4j.helpers;
/*    */ 
/*    */ public class FormattingInfo
/*    */ {
/* 30 */   int min = -1;
/* 31 */   int max = 2147483647;
/* 32 */   boolean leftAlign = false;
/*    */ 
/*    */   void reset() {
/* 35 */     this.min = -1;
/* 36 */     this.max = 2147483647;
/* 37 */     this.leftAlign = false;
/*    */   }
/*    */ 
/*    */   void dump() {
/* 41 */     LogLog.debug("min=" + this.min + ", max=" + this.max + ", leftAlign=" + this.leftAlign);
/*    */   }
/*    */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     org.apache.log4j.helpers.FormattingInfo
 * JD-Core Version:    0.6.0
 */