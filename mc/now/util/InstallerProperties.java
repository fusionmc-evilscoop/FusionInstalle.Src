/*    */ package mc.now.util;
/*    */ 
/*    */ import java.io.File;
/*    */ import java.io.FileInputStream;
/*    */ import java.net.URI;
/*    */ import java.net.URL;
/*    */ import java.security.CodeSource;
/*    */ import java.security.ProtectionDomain;
/*    */ import java.util.Properties;
/*    */ import org.apache.commons.io.FilenameUtils;
/*    */ import org.apache.log4j.Logger;
/*    */ 
/*    */ public class InstallerProperties
/*    */ {
/* 14 */   private static final Logger LOGGER = Logger.getLogger(InstallerProperties.class);
/*    */ 
/* 21 */   private static String installerDir = "./";
/* 22 */   private static final Properties properties = new Properties();
/*    */ 
/*    */   public static String getMinecraftVersion()
/*    */   {
/* 36 */     return properties.getProperty("mc.version");
/*    */   }
/*    */ 
/*    */   public static String getMinecraftJarMD5() {
/* 40 */     return properties.getProperty("mc.jar.md5");
/*    */   }
/*    */ 
/*    */   public static String getLogoFile() {
/* 44 */     return FilenameUtils.concat(installerDir, "config/logo.png");
/*    */   }
/*    */ 
/*    */   public static String getInitTextFile() {
/* 48 */     return FilenameUtils.concat(installerDir, "config/init_text.txt");
/*    */   }
/*    */ 
/*    */   public static String getFrameTitle() {
/* 52 */     return properties.getProperty("installer.frame_title", "Minecraft Modpack Installer");
/*    */   }
/*    */ 
/*    */   public static String getInstallerDir() {
/* 56 */     return installerDir;
/*    */   }
/*    */ 
/*    */   static
/*    */   {
/*    */     try
/*    */     {
/* 25 */       URL url = InstallerProperties.class.getProtectionDomain().getCodeSource().getLocation();
/* 26 */       File f = new File(new URI(url.toString()));
/* 27 */       File dir = f.getParentFile();
/* 28 */       installerDir = dir.getPath();
/* 29 */       properties.load(new FileInputStream(FilenameUtils.concat(installerDir, "config/installer.properties")));
/*    */     } catch (Exception e) {
/* 31 */       LOGGER.error("Error loading installer.properties file", e);
/*    */     }
/*    */   }
/*    */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     mc.now.util.InstallerProperties
 * JD-Core Version:    0.6.0
 */