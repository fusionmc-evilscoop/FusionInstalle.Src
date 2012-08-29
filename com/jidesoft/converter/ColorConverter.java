/*    */ package com.jidesoft.converter;
/*    */ 
/*    */ public abstract class ColorConverter
/*    */   implements ObjectConverter
/*    */ {
/* 17 */   public static ConverterContext CONTEXT_RGB = ConverterContext.DEFAULT_CONTEXT;
/*    */ 
/* 22 */   public static ConverterContext CONTEXT_HEX = new ConverterContext("Color.Hex");
/*    */ 
/* 27 */   public static ConverterContext CONTEXT_RGBA = new ConverterContext("Color.rgba");
/*    */ 
/* 33 */   public static ConverterContext CONTEXT_HEX_WITH_ALPHA = new ConverterContext("Color.HexWithAlpha");
/*    */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     com.jidesoft.converter.ColorConverter
 * JD-Core Version:    0.6.0
 */