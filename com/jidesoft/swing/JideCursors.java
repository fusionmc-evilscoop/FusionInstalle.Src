/*     */ package com.jidesoft.swing;
/*     */ 
/*     */ import com.jidesoft.plaf.UIDefaultsLookup;
/*     */ import java.awt.Cursor;
/*     */ import java.awt.Dimension;
/*     */ import java.awt.Point;
/*     */ import java.awt.Toolkit;
/*     */ import javax.swing.ImageIcon;
/*     */ 
/*     */ public class JideCursors
/*     */ {
/*     */   public static final int FIRST_CUSTOM_CURSOR = 20;
/*     */   public static final int HSPLIT_CURSOR = 20;
/*     */   public static final int VSPLIT_CURSOR = 21;
/*     */   public static final int DRAG_CURSOR = 22;
/*     */   public static final int DRAG_STOP_CURSOR = 23;
/*     */   public static final int NORTH_CURSOR = 24;
/*     */   public static final int SOUTH_CURSOR = 25;
/*     */   public static final int EAST_CURSOR = 26;
/*     */   public static final int WEST_CURSOR = 27;
/*     */   public static final int TAB_CURSOR = 28;
/*     */   public static final int FLOAT_CURSOR = 29;
/*     */   public static final int VERTICAL_CURSOR = 30;
/*     */   public static final int HORIZONTAL_CURSOR = 31;
/*     */   public static final int DELETE_CURSOR = 32;
/*     */   public static final int DRAG_TEXT_CURSOR = 33;
/*     */   public static final int DRAG_TEXT_STOP_CURSOR = 34;
/*     */   public static final int PERCENTAGE_CURSOR = 35;
/*     */   public static final int MOVE_EAST_CURSOR = 36;
/*     */   public static final int MOVE_WEST_CURSOR = 37;
/*     */   public static final int LAST_CUSTOM_CURSOR = 38;
/* 122 */   private static final Cursor[] predefined = new Cursor[19];
/*     */ 
/*     */   public static Cursor getPredefinedCursor(int type)
/*     */   {
/* 137 */     if ((type < 20) || (type > 38)) {
/* 138 */       throw new IllegalArgumentException("illegal cursor type");
/*     */     }
/* 140 */     if (predefined[(type - 20)] == null) {
/* 141 */       predefined[(type - 20)] = createCursor(type);
/*     */     }
/* 143 */     return predefined[(type - 20)];
/*     */   }
/*     */ 
/*     */   public static void setPredefinedCursor(int type, Cursor cursor)
/*     */   {
/* 154 */     if ((type < 20) || (type > 38)) {
/* 155 */       throw new IllegalArgumentException("illegal cursor type");
/*     */     }
/* 157 */     predefined[(type - 20)] = (cursor == null ? createCursor(type) : cursor);
/*     */   }
/*     */ 
/*     */   protected static Cursor createCursor(int type)
/*     */   {
/*     */     try
/*     */     {
/* 168 */       Toolkit toolkit = Toolkit.getDefaultToolkit();
/* 169 */       Dimension bestSize = toolkit.getBestCursorSize(32, 32);
/* 170 */       int maxColor = toolkit.getMaximumCursorColors();
/* 171 */       switch (type) {
/*     */       case 20:
/* 173 */         if ((bestSize.width != 0) && (maxColor > 3)) {
/* 174 */           ImageIcon icon = (ImageIcon)UIDefaultsLookup.getIcon("Cursor.hsplit");
/* 175 */           if (icon == null)
/* 176 */             return Cursor.getPredefinedCursor(11);
/* 177 */           return toolkit.createCustomCursor(icon.getImage(), new Point(15, 15), "Horizonal Split");
/*     */         }
/* 179 */         return Cursor.getPredefinedCursor(11);
/*     */       case 21:
/* 182 */         if ((bestSize.width != 0) && (maxColor > 3)) {
/* 183 */           ImageIcon icon = (ImageIcon)UIDefaultsLookup.getIcon("Cursor.vsplit");
/* 184 */           if (icon == null)
/* 185 */             return Cursor.getPredefinedCursor(9);
/* 186 */           return toolkit.createCustomCursor(icon.getImage(), new Point(15, 15), "Vertical Split");
/*     */         }
/* 188 */         return Cursor.getPredefinedCursor(9);
/*     */       case 22:
/* 191 */         if ((bestSize.width != 0) && (maxColor > 3)) {
/* 192 */           ImageIcon icon = (ImageIcon)UIDefaultsLookup.getIcon("Cursor.drag");
/* 193 */           if (icon == null)
/* 194 */             return Cursor.getDefaultCursor();
/* 195 */           return toolkit.createCustomCursor(icon.getImage(), new Point(17, 12), "Drag");
/*     */         }
/* 197 */         return Cursor.getDefaultCursor();
/*     */       case 23:
/* 200 */         if ((bestSize.width != 0) && (maxColor > 3)) {
/* 201 */           ImageIcon icon = (ImageIcon)UIDefaultsLookup.getIcon("Cursor.dragStop");
/* 202 */           if (icon == null)
/* 203 */             return Cursor.getDefaultCursor();
/* 204 */           return toolkit.createCustomCursor(icon.getImage(), new Point(17, 12), "Drag Stop");
/*     */         }
/* 206 */         return Cursor.getDefaultCursor();
/*     */       case 33:
/* 209 */         if ((bestSize.width != 0) && (maxColor > 3)) {
/* 210 */           ImageIcon icon = (ImageIcon)UIDefaultsLookup.getIcon("Cursor.dragText");
/* 211 */           if (icon == null)
/* 212 */             return Cursor.getDefaultCursor();
/* 213 */           return toolkit.createCustomCursor(icon.getImage(), new Point(0, 0), "Drag Text");
/*     */         }
/* 215 */         return Cursor.getDefaultCursor();
/*     */       case 34:
/* 218 */         if ((bestSize.width != 0) && (maxColor > 3)) {
/* 219 */           ImageIcon icon = (ImageIcon)UIDefaultsLookup.getIcon("Cursor.dragTextStop");
/* 220 */           if (icon == null)
/* 221 */             return Cursor.getDefaultCursor();
/* 222 */           return toolkit.createCustomCursor(icon.getImage(), new Point(15, 15), "Drag Text Stop");
/*     */         }
/* 224 */         return Cursor.getDefaultCursor();
/*     */       case 24:
/* 227 */         if ((bestSize.width != 0) && (maxColor > 3)) {
/* 228 */           ImageIcon icon = (ImageIcon)UIDefaultsLookup.getIcon("Cursor.north");
/* 229 */           if (icon == null)
/* 230 */             return Cursor.getDefaultCursor();
/* 231 */           return toolkit.createCustomCursor(icon.getImage(), new Point(15, 10), "North");
/*     */         }
/* 233 */         return Cursor.getDefaultCursor();
/*     */       case 25:
/* 236 */         if ((bestSize.width != 0) && (maxColor > 3)) {
/* 237 */           ImageIcon icon = (ImageIcon)UIDefaultsLookup.getIcon("Cursor.south");
/* 238 */           if (icon == null)
/* 239 */             return Cursor.getDefaultCursor();
/* 240 */           return toolkit.createCustomCursor(icon.getImage(), new Point(15, 20), "South");
/*     */         }
/* 242 */         return Cursor.getDefaultCursor();
/*     */       case 26:
/* 245 */         if ((bestSize.width != 0) && (maxColor > 3)) {
/* 246 */           ImageIcon icon = (ImageIcon)UIDefaultsLookup.getIcon("Cursor.east");
/* 247 */           if (icon == null)
/* 248 */             return Cursor.getDefaultCursor();
/* 249 */           return toolkit.createCustomCursor(icon.getImage(), new Point(20, 15), "East");
/*     */         }
/* 251 */         return Cursor.getDefaultCursor();
/*     */       case 27:
/* 254 */         if ((bestSize.width != 0) && (maxColor > 3)) {
/* 255 */           ImageIcon icon = (ImageIcon)UIDefaultsLookup.getIcon("Cursor.west");
/* 256 */           if (icon == null)
/* 257 */             return Cursor.getDefaultCursor();
/* 258 */           return toolkit.createCustomCursor(icon.getImage(), new Point(10, 15), "West");
/*     */         }
/* 260 */         return Cursor.getDefaultCursor();
/*     */       case 28:
/* 263 */         if ((bestSize.width != 0) && (maxColor > 3)) {
/* 264 */           ImageIcon icon = (ImageIcon)UIDefaultsLookup.getIcon("Cursor.tab");
/* 265 */           if (icon == null)
/* 266 */             return Cursor.getDefaultCursor();
/* 267 */           return toolkit.createCustomCursor(icon.getImage(), new Point(15, 15), "Tabbed");
/*     */         }
/* 269 */         return Cursor.getDefaultCursor();
/*     */       case 29:
/* 272 */         if ((bestSize.width != 0) && (maxColor > 3)) {
/* 273 */           ImageIcon icon = (ImageIcon)UIDefaultsLookup.getIcon("Cursor.float");
/* 274 */           if (icon == null)
/* 275 */             return Cursor.getDefaultCursor();
/* 276 */           return toolkit.createCustomCursor(icon.getImage(), new Point(15, 15), "Floating");
/*     */         }
/* 278 */         return Cursor.getDefaultCursor();
/*     */       case 30:
/* 281 */         if ((bestSize.width != 0) && (maxColor > 3)) {
/* 282 */           ImageIcon icon = (ImageIcon)UIDefaultsLookup.getIcon("Cursor.vertical");
/* 283 */           if (icon == null)
/* 284 */             return Cursor.getDefaultCursor();
/* 285 */           return toolkit.createCustomCursor(icon.getImage(), new Point(15, 15), "Vertical");
/*     */         }
/* 287 */         return Cursor.getDefaultCursor();
/*     */       case 31:
/* 290 */         if ((bestSize.width != 0) && (maxColor > 3)) {
/* 291 */           ImageIcon icon = (ImageIcon)UIDefaultsLookup.getIcon("Cursor.horizontal");
/* 292 */           if (icon == null)
/* 293 */             return Cursor.getDefaultCursor();
/* 294 */           return toolkit.createCustomCursor(icon.getImage(), new Point(15, 15), "Horizontal");
/*     */         }
/* 296 */         return Cursor.getDefaultCursor();
/*     */       case 32:
/* 299 */         if ((bestSize.width != 0) && (maxColor > 3)) {
/* 300 */           ImageIcon icon = (ImageIcon)UIDefaultsLookup.getIcon("Cursor.delete");
/* 301 */           if (icon == null)
/* 302 */             return Cursor.getDefaultCursor();
/* 303 */           return toolkit.createCustomCursor(icon.getImage(), new Point(10, 10), "Delete");
/*     */         }
/* 305 */         return Cursor.getDefaultCursor();
/*     */       case 35:
/* 308 */         if ((bestSize.width != 0) && (maxColor > 3)) {
/* 309 */           ImageIcon icon = (ImageIcon)UIDefaultsLookup.getIcon("Cursor.percentage");
/* 310 */           if (icon == null)
/* 311 */             return Cursor.getDefaultCursor();
/* 312 */           return toolkit.createCustomCursor(icon.getImage(), new Point(20, 15), "Percentage");
/*     */         }
/* 314 */         return Cursor.getDefaultCursor();
/*     */       case 36:
/* 317 */         if ((bestSize.width != 0) && (maxColor > 3)) {
/* 318 */           ImageIcon icon = (ImageIcon)UIDefaultsLookup.getIcon("Cursor.moveEast");
/* 319 */           if (icon == null)
/* 320 */             return Cursor.getDefaultCursor();
/* 321 */           return toolkit.createCustomCursor(icon.getImage(), new Point(11, 15), "Move East");
/*     */         }
/* 323 */         return Cursor.getDefaultCursor();
/*     */       case 37:
/* 326 */         if ((bestSize.width != 0) && (maxColor > 3)) {
/* 327 */           ImageIcon icon = (ImageIcon)UIDefaultsLookup.getIcon("Cursor.moveWest");
/* 328 */           if (icon == null)
/* 329 */             return Cursor.getDefaultCursor();
/* 330 */           return toolkit.createCustomCursor(icon.getImage(), new Point(20, 15), "Move West");
/*     */         }
/* 332 */         return Cursor.getDefaultCursor();
/*     */       }
/*     */ 
/* 335 */       return null;
/*     */     } catch (Exception e) {
/*     */     }
/* 338 */     return Cursor.getDefaultCursor();
/*     */   }
/*     */ 
/*     */   static
/*     */   {
/* 125 */     for (int i = 20; i < 38; i++)
/* 126 */       getPredefinedCursor(i);
/*     */   }
/*     */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     com.jidesoft.swing.JideCursors
 * JD-Core Version:    0.6.0
 */