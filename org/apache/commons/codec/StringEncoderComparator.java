/*    */ package org.apache.commons.codec;
/*    */ 
/*    */ import java.util.Comparator;
/*    */ 
/*    */ public class StringEncoderComparator
/*    */   implements Comparator
/*    */ {
/*    */   private final StringEncoder stringEncoder;
/*    */ 
/*    */   /** @deprecated */
/*    */   public StringEncoderComparator()
/*    */   {
/* 47 */     this.stringEncoder = null;
/*    */   }
/*    */ 
/*    */   public StringEncoderComparator(StringEncoder stringEncoder)
/*    */   {
/* 55 */     this.stringEncoder = stringEncoder;
/*    */   }
/*    */ 
/*    */   public int compare(Object o1, Object o2)
/*    */   {
/* 73 */     int compareCode = 0;
/*    */     try
/*    */     {
/* 76 */       Comparable s1 = (Comparable)this.stringEncoder.encode(o1);
/* 77 */       Comparable s2 = (Comparable)this.stringEncoder.encode(o2);
/* 78 */       compareCode = s1.compareTo(s2);
/*    */     }
/*    */     catch (EncoderException ee) {
/* 81 */       compareCode = 0;
/*    */     }
/* 83 */     return compareCode;
/*    */   }
/*    */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     org.apache.commons.codec.StringEncoderComparator
 * JD-Core Version:    0.6.0
 */