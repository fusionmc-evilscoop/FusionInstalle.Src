/*    */ package org.apache.log4j.xml;
/*    */ 
/*    */ import java.io.InputStream;
/*    */ import org.apache.log4j.helpers.LogLog;
/*    */ import org.xml.sax.EntityResolver;
/*    */ import org.xml.sax.InputSource;
/*    */ 
/*    */ public class Log4jEntityResolver
/*    */   implements EntityResolver
/*    */ {
/*    */   public InputSource resolveEntity(String publicId, String systemId)
/*    */   {
/* 39 */     if (systemId.endsWith("log4j.dtd")) {
/* 40 */       Class clazz = getClass();
/* 41 */       InputStream in = clazz.getResourceAsStream("/org/apache/log4j/xml/log4j.dtd");
/* 42 */       if (in == null) {
/* 43 */         LogLog.error("Could not find [log4j.dtd]. Used [" + clazz.getClassLoader() + "] class loader in the search.");
/*    */ 
/* 45 */         return null;
/*    */       }
/* 47 */       return new InputSource(in);
/*    */     }
/*    */ 
/* 50 */     return null;
/*    */   }
/*    */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     org.apache.log4j.xml.Log4jEntityResolver
 * JD-Core Version:    0.6.0
 */