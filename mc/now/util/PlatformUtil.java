/*    */ package mc.now.util;
/*    */ 
/*    */ import org.apache.commons.io.FilenameUtils;
/*    */ 
/*    */ public class PlatformUtil
/*    */ {
/*    */   public static OS currentOS;
/* 13 */   private static String mcFolder = null;
/*    */ 
/*    */   public static String getMinecraftFolder()
/*    */   {
/* 34 */     return mcFolder;
/*    */   }
/*    */ 
/*    */   public static String getMinecraftJar() {
/* 38 */     return FilenameUtils.normalize(FilenameUtils.concat(getMinecraftFolder(), "bin/minecraft.jar"));
/*    */   }
/*    */ 
/*    */   public static String getMinecraftModsFolder() {
/* 42 */     return FilenameUtils.normalize(FilenameUtils.concat(getMinecraftFolder(), "mods"));
/*    */   }
/*    */ 
/*    */   public static void setMinecraftFolder(String target) {
/* 46 */     mcFolder = target;
/*    */   }
/*    */ 
/*    */   static
/*    */   {
/* 16 */     String osname = System.getProperty("os.name").toLowerCase();
/* 17 */     if (osname.startsWith("mac"))
/* 18 */       currentOS = OS.Mac;
/* 19 */     else if (osname.startsWith("linux"))
/* 20 */       currentOS = OS.Linux;
/* 21 */     else if (osname.startsWith("win"))
/* 22 */       currentOS = OS.Windows;
/*    */     else {
/* 24 */       throw new RuntimeException("Unknown OS: " + osname);
/*    */     }
/* 26 */     switch (1.$SwitchMap$mc$now$util$PlatformUtil$OS[currentOS.ordinal()]) { case 1:
/* 27 */       mcFolder = System.getProperty("user.home") + "/Library/Application Support/minecraft/"; break;
/*    */     case 2:
/* 28 */       mcFolder = System.getProperty("user.home") + "/.minecraft/"; break;
/*    */     case 3:
/* 29 */       mcFolder = System.getenv("APPDATA") + "\\.minecraft\\";
/*    */     }
/*    */   }
/*    */ 
/*    */   public static enum OS
/*    */   {
/*  8 */     Mac, Linux, Windows;
/*    */   }
/*    */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     mc.now.util.PlatformUtil
 * JD-Core Version:    0.6.0
 */