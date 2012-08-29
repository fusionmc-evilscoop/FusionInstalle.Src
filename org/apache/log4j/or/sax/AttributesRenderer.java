/*    */ package org.apache.log4j.or.sax;
/*    */ 
/*    */ import org.apache.log4j.or.ObjectRenderer;
/*    */ import org.xml.sax.Attributes;
/*    */ 
/*    */ public class AttributesRenderer
/*    */   implements ObjectRenderer
/*    */ {
/*    */   public String doRender(Object o)
/*    */   {
/* 40 */     if ((o instanceof Attributes)) {
/* 41 */       StringBuffer sbuf = new StringBuffer();
/* 42 */       Attributes a = (Attributes)o;
/* 43 */       int len = a.getLength();
/* 44 */       boolean first = true;
/* 45 */       for (int i = 0; i < len; i++) {
/* 46 */         if (first)
/* 47 */           first = false;
/*    */         else {
/* 49 */           sbuf.append(", ");
/*    */         }
/* 51 */         sbuf.append(a.getQName(i));
/* 52 */         sbuf.append('=');
/* 53 */         sbuf.append(a.getValue(i));
/*    */       }
/* 55 */       return sbuf.toString();
/*    */     }
/* 57 */     return o.toString();
/*    */   }
/*    */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     org.apache.log4j.or.sax.AttributesRenderer
 * JD-Core Version:    0.6.0
 */