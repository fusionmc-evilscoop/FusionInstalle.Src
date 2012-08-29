/*    */ package com.jidesoft.plaf;
/*    */ 
/*    */ import com.jidesoft.utils.SystemInfo;
/*    */ import java.awt.Toolkit;
/*    */ import java.io.PrintStream;
/*    */ 
/*    */ public class XPUtils
/*    */ {
/*    */   public static final String PROPERTY_THEMEACTIVE = "win.xpstyle.themeActive";
/*    */   public static final String PROPERTY_COLORNAME = "win.xpstyle.colorName";
/*    */   public static final String PROPERTY_DLLNAME = "win.xpstyle.dllName";
/*    */   public static final String DEFAULT = "Default";
/*    */   public static final String GRAY = "Gray";
/*    */   public static final String BLUE = "NormalColor";
/*    */   public static final String HOMESTEAD = "HomeStead";
/*    */   public static final String METALLIC = "Metallic";
/*    */ 
/*    */   public static boolean isXPStyleOn()
/*    */     throws UnsupportedOperationException
/*    */   {
/* 36 */     if (SystemInfo.isJdk142Above()) {
/* 37 */       Toolkit toolkit = Toolkit.getDefaultToolkit();
/* 38 */       return Boolean.TRUE.equals(toolkit.getDesktopProperty("win.xpstyle.themeActive"));
/*    */     }
/*    */ 
/* 41 */     throw new UnsupportedOperationException("JDK 1.4.2 and up is required to support this method call.");
/*    */   }
/*    */ 
/*    */   public static String getColorName()
/*    */     throws UnsupportedOperationException
/*    */   {
/* 56 */     if (SystemInfo.isJdk142Above()) {
/* 57 */       Toolkit toolkit = Toolkit.getDefaultToolkit();
/* 58 */       return (String)toolkit.getDesktopProperty("win.xpstyle.colorName");
/*    */     }
/*    */ 
/* 61 */     throw new UnsupportedOperationException("JDK 1.4.2 and up is required to support this method call.");
/*    */   }
/*    */ 
/*    */   private static String getXPStyleDll()
/*    */   {
/* 66 */     Toolkit toolkit = Toolkit.getDefaultToolkit();
/* 67 */     return (String)toolkit.getDesktopProperty("win.xpstyle.dllName");
/*    */   }
/*    */ 
/*    */   public static void main(String[] args)
/*    */   {
/*    */     try {
/* 73 */       System.out.println(isXPStyleOn());
/*    */     }
/*    */     catch (UnsupportedOperationException e) {
/* 76 */       System.out.println("Unknown XP style because " + e.getMessage());
/*    */     }
/*    */     try {
/* 79 */       System.out.println(getColorName());
/*    */     }
/*    */     catch (UnsupportedOperationException e) {
/* 82 */       System.out.println("Unknown XP color because " + e.getMessage());
/*    */     }
/* 84 */     System.out.println(getXPStyleDll());
/*    */   }
/*    */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     com.jidesoft.plaf.XPUtils
 * JD-Core Version:    0.6.0
 */