/*     */ package com.jidesoft.plaf.xerto;
/*     */ 
/*     */ import com.jidesoft.plaf.UIDefaultsLookup;
/*     */ import java.awt.Color;
/*     */ import javax.swing.UIDefaults;
/*     */ import javax.swing.UIManager;
/*     */ 
/*     */ public class XertoUtils
/*     */ {
/*     */   private static Color BASE_COLOR;
/*     */   private static Color CONTROL_COLOR;
/*     */   private static Color LIGHT_CONTROL_COLOR;
/*     */   private static Color MID_CONTROL_COLOR;
/*     */   private static Color CONTROL_VERY_LIGHT_SHADOW_COLOR;
/*     */   private static Color CONTROL_LIGHT_SHADOW_COLOR;
/*     */   private static Color CONTROL_MID_SHADOW_COLOR;
/*     */   private static Color CONTROL_DARK_SHADOW_COLOR;
/*     */   private static Color SELECTION_COLOR;
/*     */   private static Color INACTIVE_CAPTION_COLOR;
/*     */   private static Color SELECTED_TAB_BACKGROUND_COLOR;
/*     */   private static Color TAB_FORGROUND_COLOR;
/*     */   private static Color FRAME_ACTIVE_TITLE_TOP_COLOR;
/*     */   private static Color FRAME_ACTIVE_TITLE_BOTTOM_COLOR;
/*     */   private static Color FRAME_INACTIVE_TITLE_TOP_COLOR;
/*     */   private static Color FRAME_INACTIVE_TITLE_BOTTOM_COLOR;
/*     */   public static final double RATIO1 = 0.67D;
/*     */   public static final double RATIO2 = 0.78D;
/*     */   public static final double RATIO3 = 0.86D;
/*     */   static final Color DARK_GREEN;
/*     */   static final Color DARK_MAGENTA;
/*     */ 
/*     */   public static void updateColors()
/*     */   {
/*  44 */     UIDefaults uiDefaults = UIManager.getDefaults();
/*  45 */     BASE_COLOR = uiDefaults.getColor("activeCaption");
/*  46 */     CONTROL_COLOR = uiDefaults.getColor("control");
/*  47 */     INACTIVE_CAPTION_COLOR = uiDefaults.getColor("inactiveCaption");
/*  48 */     LIGHT_CONTROL_COLOR = uiDefaults.getColor("MenuItem.background");
/*     */ 
/*  50 */     float[] oControlHSB = Color.RGBtoHSB(CONTROL_COLOR.getRed(), CONTROL_COLOR.getGreen(), CONTROL_COLOR.getBlue(), null);
/*     */ 
/*  52 */     float[] oBaseHSB = Color.RGBtoHSB(BASE_COLOR.getRed(), BASE_COLOR.getGreen(), BASE_COLOR.getBlue(), null);
/*     */ 
/*  54 */     MID_CONTROL_COLOR = Color.getHSBColor(oControlHSB[0], Math.max(oControlHSB[1] - 0.05F, 0.0F), oControlHSB[2] < 0.95F ? oControlHSB[2] + 0.05F : 0.98F);
/*     */ 
/*  56 */     SELECTED_TAB_BACKGROUND_COLOR = Color.getHSBColor(oControlHSB[0], oControlHSB[1], oControlHSB[1] == 0.0F ? 0.75F : 0.85F);
/*     */ 
/*  58 */     TAB_FORGROUND_COLOR = Color.getHSBColor(oControlHSB[0], oControlHSB[1] > 0.01D ? 0.45F : oControlHSB[0], 0.2F);
/*  59 */     CONTROL_VERY_LIGHT_SHADOW_COLOR = Color.getHSBColor(oControlHSB[0], oControlHSB[1], oControlHSB[2] - 0.02F);
/*  60 */     CONTROL_LIGHT_SHADOW_COLOR = Color.getHSBColor(oControlHSB[0], oControlHSB[1], oControlHSB[2] - 0.06F);
/*  61 */     CONTROL_MID_SHADOW_COLOR = Color.getHSBColor(oControlHSB[0], oControlHSB[1], oControlHSB[2] - 0.16F);
/*  62 */     CONTROL_DARK_SHADOW_COLOR = Color.getHSBColor(oControlHSB[0], oControlHSB[1], oControlHSB[2] - 0.32F);
/*  63 */     SELECTION_COLOR = Color.getHSBColor(oBaseHSB[0], oBaseHSB[1] > 0.01D ? 0.45F : oBaseHSB[0], 0.8F);
/*  64 */     FRAME_ACTIVE_TITLE_TOP_COLOR = Color.getHSBColor(oBaseHSB[0], oBaseHSB[1] > 0.01D ? 0.3F : oBaseHSB[0], 0.9F);
/*  65 */     FRAME_ACTIVE_TITLE_BOTTOM_COLOR = Color.getHSBColor(oBaseHSB[0], oBaseHSB[1] > 0.01D ? 0.45F : oBaseHSB[0], 0.7F);
/*  66 */     FRAME_INACTIVE_TITLE_TOP_COLOR = Color.getHSBColor(oControlHSB[0], oControlHSB[1], 0.75F);
/*  67 */     FRAME_INACTIVE_TITLE_BOTTOM_COLOR = Color.getHSBColor(oControlHSB[0], oControlHSB[1], 0.5F);
/*     */   }
/*     */ 
/*     */   public static Color getBaseColor() {
/*  71 */     return BASE_COLOR;
/*     */   }
/*     */ 
/*     */   public static Color getInActiveCaptionColor() {
/*  75 */     return INACTIVE_CAPTION_COLOR;
/*     */   }
/*     */ 
/*     */   public static Color getControlColor() {
/*  79 */     return CONTROL_COLOR;
/*     */   }
/*     */ 
/*     */   public static Color getMidControlColor() {
/*  83 */     return MID_CONTROL_COLOR;
/*     */   }
/*     */ 
/*     */   public static Color getLightControlColor() {
/*  87 */     return LIGHT_CONTROL_COLOR;
/*     */   }
/*     */ 
/*     */   public static Color getSelectedTabBackgroundColor() {
/*  91 */     return SELECTED_TAB_BACKGROUND_COLOR;
/*     */   }
/*     */ 
/*     */   public static Color getTabForgroundColor() {
/*  95 */     return TAB_FORGROUND_COLOR;
/*     */   }
/*     */ 
/*     */   public static Color getControlVeryLightShadowColor() {
/*  99 */     return CONTROL_VERY_LIGHT_SHADOW_COLOR;
/*     */   }
/*     */ 
/*     */   public static Color getControlLightShadowColor() {
/* 103 */     return CONTROL_LIGHT_SHADOW_COLOR;
/*     */   }
/*     */ 
/*     */   public static Color getControlMidShadowColor() {
/* 107 */     return CONTROL_MID_SHADOW_COLOR;
/*     */   }
/*     */ 
/*     */   public static Color getControlDarkShadowColor() {
/* 111 */     return CONTROL_DARK_SHADOW_COLOR;
/*     */   }
/*     */ 
/*     */   public static Color getSelectionColor() {
/* 115 */     return SELECTION_COLOR;
/*     */   }
/*     */ 
/*     */   public static Color getApplicationFrameBackgroundColor() {
/* 119 */     return getControlColor();
/*     */   }
/*     */ 
/*     */   public static Color getFrameBorderColor() {
/* 123 */     return UIDefaultsLookup.getColor("controlShadow");
/*     */   }
/*     */ 
/*     */   public static Color getFrameActiveTitleTopColor() {
/* 127 */     return FRAME_ACTIVE_TITLE_TOP_COLOR;
/*     */   }
/*     */ 
/*     */   public static Color getFrameActiveTitleBottomColor() {
/* 131 */     return FRAME_ACTIVE_TITLE_BOTTOM_COLOR;
/*     */   }
/*     */ 
/*     */   public static Color getFrameInactiveTitleTopColor() {
/* 135 */     return FRAME_INACTIVE_TITLE_TOP_COLOR;
/*     */   }
/*     */ 
/*     */   public static Color getFrameInactiveTitleBottomColor() {
/* 139 */     return FRAME_INACTIVE_TITLE_BOTTOM_COLOR;
/*     */   }
/*     */ 
/*     */   public static Color getLighterColor(Color backColor)
/*     */   {
/* 146 */     int r = getLighterColor(backColor.getRed());
/* 147 */     int g = getLighterColor(backColor.getGreen());
/* 148 */     int b = getLighterColor(backColor.getBlue());
/* 149 */     if (r >= 255) r = 255;
/* 150 */     if (g >= 255) g = 255;
/* 151 */     if (b >= 255) b = 255;
/* 152 */     return new Color(r, g, b);
/*     */   }
/*     */ 
/*     */   public static int getLighterColor(int x) {
/* 156 */     return (int)(x * 36.0D / 255.0D + 219.5D);
/*     */   }
/*     */ 
/*     */   public static Color getMenuSelectionColor(Color backColor)
/*     */   {
/* 161 */     int r = getMenuSelectionValue(backColor.getRed());
/* 162 */     int g = getMenuSelectionValue(backColor.getGreen());
/* 163 */     int b = getMenuSelectionValue(backColor.getBlue());
/* 164 */     if (r >= 255) r = 255;
/* 165 */     if (g >= 255) g = 255;
/* 166 */     if (b >= 255) b = 255;
/* 167 */     return new Color(r, g, b);
/*     */   }
/*     */ 
/*     */   public static int getMenuSelectionValue(int x) {
/* 171 */     return (int)(x * 76.0D / 255.0D + 179.5D);
/*     */   }
/*     */ 
/*     */   public static Color getMenuBackgroundColor(Color color) {
/* 175 */     if (Color.BLACK.equals(color)) {
/* 176 */       return color;
/*     */     }
/* 178 */     if (Color.WHITE.equals(color)) {
/* 179 */       return color;
/*     */     }
/* 181 */     int r = getMenuValue(color.getRed());
/* 182 */     int g = getMenuValue(color.getGreen());
/* 183 */     int b = getMenuValue(color.getBlue());
/* 184 */     if (r >= 255) r = 255;
/* 185 */     if (g >= 255) g = 255;
/* 186 */     if (b >= 255) b = 255;
/* 187 */     return new Color(r, g, b);
/*     */   }
/*     */ 
/*     */   public static int getMenuValue(int x) {
/* 191 */     return (int)(x * 36.0D / 255.0D + 219.5D);
/*     */   }
/*     */ 
/*     */   public static Color getToolBarBackgroundColor(Color color) {
/* 195 */     if (Color.BLACK.equals(color)) {
/* 196 */       return color;
/*     */     }
/* 198 */     if (Color.WHITE.equals(color)) {
/* 199 */       return color;
/*     */     }
/* 201 */     int r = getToolBarValue(color.getRed());
/* 202 */     int g = getToolBarValue(color.getGreen());
/* 203 */     int b = getToolBarValue(color.getBlue());
/* 204 */     if (r >= 255) r = 255;
/* 205 */     if (g >= 255) g = 255;
/* 206 */     if (b >= 255) b = 255;
/* 207 */     return new Color(r, g, b);
/*     */   }
/*     */ 
/*     */   public static int getToolBarValue(int x) {
/* 211 */     return x * 215 / 255 + 40;
/*     */   }
/*     */ 
/*     */   public static Color getGripperForegroundColor(Color backColor) {
/* 215 */     int r = getGripperValue(backColor.getRed());
/* 216 */     int g = getGripperValue(backColor.getGreen());
/* 217 */     int b = getGripperValue(backColor.getBlue());
/* 218 */     if (r >= 255) r = 255;
/* 219 */     if (g >= 255) g = 255;
/* 220 */     if (b >= 255) b = 255;
/* 221 */     return new Color(r, g, b);
/*     */   }
/*     */ 
/*     */   public static int getGripperValue(int x) {
/* 225 */     if (x == 255) {
/* 226 */       return 0;
/*     */     }
/* 228 */     if ((x >= 0) && (x <= 64)) {
/* 229 */       return x * 33 / 64 + 123;
/*     */     }
/*     */ 
/* 232 */     return (x - 65) * 157 / 189 + 33;
/*     */   }
/*     */ 
/*     */   public static Color getDefaultBackgroundColor(Color backColor)
/*     */   {
/*     */     Color backIDE;
/*     */     Color backIDE;
/* 240 */     if ((backColor.getRed() == 212) && (backColor.getGreen() == 208) && (backColor.getBlue() == 200))
/*     */     {
/* 242 */       backIDE = new Color(247, 243, 233);
/*     */     }
/*     */     else
/*     */     {
/*     */       Color backIDE;
/* 244 */       if ((backColor.getRed() == 236) && (backColor.getGreen() == 233) && (backColor.getBlue() == 216))
/*     */       {
/* 247 */         backIDE = new Color(255, 251, 233);
/*     */       }
/*     */       else
/*     */       {
/* 251 */         int r = backColor.getRed() + 35;
/* 252 */         int g = backColor.getGreen() + 35;
/* 253 */         int b = backColor.getBlue() + 35;
/* 254 */         if (r >= 255) r = 255;
/* 255 */         if (g >= 255) g = 255;
/* 256 */         if (b >= 255) b = 255;
/* 257 */         backIDE = new Color(r, g, b);
/*     */       }
/*     */     }
/* 260 */     return backIDE;
/*     */   }
/*     */ 
/*     */   public static int getLightColor(int x, double ratio)
/*     */   {
/* 271 */     return (int)((255 - x) * ratio + x);
/*     */   }
/*     */ 
/*     */   public static Color getLighterColor(Color color, double ratio) {
/* 275 */     if ((DARK_GREEN.equals(color)) || (DARK_MAGENTA.equals(color))) {
/* 276 */       return color;
/*     */     }
/* 278 */     int r = getLightColor(color.getRed(), ratio);
/* 279 */     int g = getLightColor(color.getGreen(), ratio) + 1;
/* 280 */     int b = getLightColor(color.getBlue(), ratio);
/* 281 */     if (r >= 255) r = 255;
/* 282 */     if (g >= 255) g = 255;
/* 283 */     if (b >= 255) b = 255;
/* 284 */     return new Color(r, g, b);
/*     */   }
/*     */ 
/*     */   public static Color getHighlightColor(Color bottomColor) {
/* 288 */     float[] oHSB = new float[3];
/* 289 */     Color.RGBtoHSB(bottomColor.getRed(), bottomColor.getGreen(), bottomColor.getBlue(), oHSB);
/* 290 */     oHSB[1] -= 0.07F;
/* 291 */     if (oHSB[1] < 0.0F) oHSB[1] = 0.0F;
/* 292 */     oHSB[2] += 0.04F;
/* 293 */     if (oHSB[2] > 1.0F) oHSB[2] = 1.0F;
/* 294 */     return Color.getHSBColor(oHSB[0], oHSB[1], oHSB[2]);
/*     */   }
/*     */ 
/*     */   public static Color getEmBaseColor(Color bottomColor) {
/* 298 */     float[] oHSB = new float[3];
/* 299 */     Color.RGBtoHSB(bottomColor.getRed(), bottomColor.getGreen(), bottomColor.getBlue(), oHSB);
/* 300 */     oHSB[1] += 0.1F;
/* 301 */     if (oHSB[1] > 1.0F) oHSB[1] = 1.0F;
/* 302 */     oHSB[2] -= 0.1F;
/* 303 */     if (oHSB[2] < 0.0F) oHSB[2] = 0.0F;
/* 304 */     return Color.getHSBColor(oHSB[0], oHSB[1], oHSB[2]);
/*     */   }
/*     */ 
/*     */   public static Color getTextColor(Color backgroundColor) {
/* 308 */     float[] oHSB = new float[3];
/* 309 */     Color.RGBtoHSB(backgroundColor.getRed(), backgroundColor.getGreen(), backgroundColor.getBlue(), oHSB);
/* 310 */     return oHSB[2] > 0.53D ? UIDefaultsLookup.getColor("controlText") : Color.WHITE;
/*     */   }
/*     */ 
/*     */   public static Color getSelectedAndFocusedButtonColor(Color backColor) {
/* 314 */     return getLighterColor(backColor, 0.67D);
/*     */   }
/*     */ 
/*     */   public static Color getFocusedButtonColor(Color backColor) {
/* 318 */     return getLighterColor(backColor, 0.78D);
/*     */   }
/*     */ 
/*     */   public static Color getSelectedButtonColor(Color backColor) {
/* 322 */     return getLighterColor(backColor, 0.86D);
/*     */   }
/*     */ 
/*     */   static
/*     */   {
/*  37 */     updateColors();
/*     */ 
/* 267 */     DARK_GREEN = new Color(0, 128, 0);
/* 268 */     DARK_MAGENTA = new Color(128, 0, 128);
/*     */   }
/*     */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     com.jidesoft.plaf.xerto.XertoUtils
 * JD-Core Version:    0.6.0
 */