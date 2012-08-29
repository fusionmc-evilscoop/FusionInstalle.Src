/*    */ package org.apache.log4j.lf5;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import java.net.URL;
/*    */ import org.apache.log4j.PropertyConfigurator;
/*    */ import org.apache.log4j.spi.Configurator;
/*    */ import org.apache.log4j.spi.LoggerRepository;
/*    */ 
/*    */ public class DefaultLF5Configurator
/*    */   implements Configurator
/*    */ {
/*    */   public static void configure()
/*    */     throws IOException
/*    */   {
/* 77 */     String resource = "/org/apache/log4j/lf5/config/defaultconfig.properties";
/*    */ 
/* 79 */     URL configFileResource = DefaultLF5Configurator.class.getResource(resource);
/*    */ 
/* 82 */     if (configFileResource != null)
/* 83 */       PropertyConfigurator.configure(configFileResource);
/*    */     else
/* 85 */       throw new IOException("Error: Unable to open the resource" + resource);
/*    */   }
/*    */ 
/*    */   public void doConfigure(URL configURL, LoggerRepository repository)
/*    */   {
/* 96 */     throw new IllegalStateException("This class should NOT be instantiated!");
/*    */   }
/*    */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     org.apache.log4j.lf5.DefaultLF5Configurator
 * JD-Core Version:    0.6.0
 */