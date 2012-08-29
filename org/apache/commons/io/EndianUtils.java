/*     */ package org.apache.commons.io;
/*     */ 
/*     */ import java.io.EOFException;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ 
/*     */ public class EndianUtils
/*     */ {
/*     */   public static short swapShort(short value)
/*     */   {
/*  57 */     return (short)(((value >> 0 & 0xFF) << 8) + ((value >> 8 & 0xFF) << 0));
/*     */   }
/*     */ 
/*     */   public static int swapInteger(int value)
/*     */   {
/*  67 */     return ((value >> 0 & 0xFF) << 24) + ((value >> 8 & 0xFF) << 16) + ((value >> 16 & 0xFF) << 8) + ((value >> 24 & 0xFF) << 0);
/*     */   }
/*     */ 
/*     */   public static long swapLong(long value)
/*     */   {
/*  80 */     return ((value >> 0 & 0xFF) << 56) + ((value >> 8 & 0xFF) << 48) + ((value >> 16 & 0xFF) << 40) + ((value >> 24 & 0xFF) << 32) + ((value >> 32 & 0xFF) << 24) + ((value >> 40 & 0xFF) << 16) + ((value >> 48 & 0xFF) << 8) + ((value >> 56 & 0xFF) << 0);
/*     */   }
/*     */ 
/*     */   public static float swapFloat(float value)
/*     */   {
/*  97 */     return Float.intBitsToFloat(swapInteger(Float.floatToIntBits(value)));
/*     */   }
/*     */ 
/*     */   public static double swapDouble(double value)
/*     */   {
/* 106 */     return Double.longBitsToDouble(swapLong(Double.doubleToLongBits(value)));
/*     */   }
/*     */ 
/*     */   public static void writeSwappedShort(byte[] data, int offset, short value)
/*     */   {
/* 119 */     data[(offset + 0)] = (byte)(value >> 0 & 0xFF);
/* 120 */     data[(offset + 1)] = (byte)(value >> 8 & 0xFF);
/*     */   }
/*     */ 
/*     */   public static short readSwappedShort(byte[] data, int offset)
/*     */   {
/* 131 */     return (short)(((data[(offset + 0)] & 0xFF) << 0) + ((data[(offset + 1)] & 0xFF) << 8));
/*     */   }
/*     */ 
/*     */   public static int readSwappedUnsignedShort(byte[] data, int offset)
/*     */   {
/* 144 */     return ((data[(offset + 0)] & 0xFF) << 0) + ((data[(offset + 1)] & 0xFF) << 8);
/*     */   }
/*     */ 
/*     */   public static void writeSwappedInteger(byte[] data, int offset, int value)
/*     */   {
/* 156 */     data[(offset + 0)] = (byte)(value >> 0 & 0xFF);
/* 157 */     data[(offset + 1)] = (byte)(value >> 8 & 0xFF);
/* 158 */     data[(offset + 2)] = (byte)(value >> 16 & 0xFF);
/* 159 */     data[(offset + 3)] = (byte)(value >> 24 & 0xFF);
/*     */   }
/*     */ 
/*     */   public static int readSwappedInteger(byte[] data, int offset)
/*     */   {
/* 170 */     return ((data[(offset + 0)] & 0xFF) << 0) + ((data[(offset + 1)] & 0xFF) << 8) + ((data[(offset + 2)] & 0xFF) << 16) + ((data[(offset + 3)] & 0xFF) << 24);
/*     */   }
/*     */ 
/*     */   public static long readSwappedUnsignedInteger(byte[] data, int offset)
/*     */   {
/* 185 */     long low = ((data[(offset + 0)] & 0xFF) << 0) + ((data[(offset + 1)] & 0xFF) << 8) + ((data[(offset + 2)] & 0xFF) << 16);
/*     */ 
/* 189 */     long high = data[(offset + 3)] & 0xFF;
/*     */ 
/* 191 */     return (high << 24) + (0xFFFFFFFF & low);
/*     */   }
/*     */ 
/*     */   public static void writeSwappedLong(byte[] data, int offset, long value)
/*     */   {
/* 202 */     data[(offset + 0)] = (byte)(int)(value >> 0 & 0xFF);
/* 203 */     data[(offset + 1)] = (byte)(int)(value >> 8 & 0xFF);
/* 204 */     data[(offset + 2)] = (byte)(int)(value >> 16 & 0xFF);
/* 205 */     data[(offset + 3)] = (byte)(int)(value >> 24 & 0xFF);
/* 206 */     data[(offset + 4)] = (byte)(int)(value >> 32 & 0xFF);
/* 207 */     data[(offset + 5)] = (byte)(int)(value >> 40 & 0xFF);
/* 208 */     data[(offset + 6)] = (byte)(int)(value >> 48 & 0xFF);
/* 209 */     data[(offset + 7)] = (byte)(int)(value >> 56 & 0xFF);
/*     */   }
/*     */ 
/*     */   public static long readSwappedLong(byte[] data, int offset)
/*     */   {
/* 220 */     long low = ((data[(offset + 0)] & 0xFF) << 0) + ((data[(offset + 1)] & 0xFF) << 8) + ((data[(offset + 2)] & 0xFF) << 16) + ((data[(offset + 3)] & 0xFF) << 24);
/*     */ 
/* 225 */     long high = ((data[(offset + 4)] & 0xFF) << 0) + ((data[(offset + 5)] & 0xFF) << 8) + ((data[(offset + 6)] & 0xFF) << 16) + ((data[(offset + 7)] & 0xFF) << 24);
/*     */ 
/* 230 */     return (high << 32) + (0xFFFFFFFF & low);
/*     */   }
/*     */ 
/*     */   public static void writeSwappedFloat(byte[] data, int offset, float value)
/*     */   {
/* 241 */     writeSwappedInteger(data, offset, Float.floatToIntBits(value));
/*     */   }
/*     */ 
/*     */   public static float readSwappedFloat(byte[] data, int offset)
/*     */   {
/* 252 */     return Float.intBitsToFloat(readSwappedInteger(data, offset));
/*     */   }
/*     */ 
/*     */   public static void writeSwappedDouble(byte[] data, int offset, double value)
/*     */   {
/* 263 */     writeSwappedLong(data, offset, Double.doubleToLongBits(value));
/*     */   }
/*     */ 
/*     */   public static double readSwappedDouble(byte[] data, int offset)
/*     */   {
/* 274 */     return Double.longBitsToDouble(readSwappedLong(data, offset));
/*     */   }
/*     */ 
/*     */   public static void writeSwappedShort(OutputStream output, short value)
/*     */     throws IOException
/*     */   {
/* 287 */     output.write((byte)(value >> 0 & 0xFF));
/* 288 */     output.write((byte)(value >> 8 & 0xFF));
/*     */   }
/*     */ 
/*     */   public static short readSwappedShort(InputStream input)
/*     */     throws IOException
/*     */   {
/* 301 */     return (short)(((read(input) & 0xFF) << 0) + ((read(input) & 0xFF) << 8));
/*     */   }
/*     */ 
/*     */   public static int readSwappedUnsignedShort(InputStream input)
/*     */     throws IOException
/*     */   {
/* 315 */     int value1 = read(input);
/* 316 */     int value2 = read(input);
/*     */ 
/* 318 */     return ((value1 & 0xFF) << 0) + ((value2 & 0xFF) << 8);
/*     */   }
/*     */ 
/*     */   public static void writeSwappedInteger(OutputStream output, int value)
/*     */     throws IOException
/*     */   {
/* 332 */     output.write((byte)(value >> 0 & 0xFF));
/* 333 */     output.write((byte)(value >> 8 & 0xFF));
/* 334 */     output.write((byte)(value >> 16 & 0xFF));
/* 335 */     output.write((byte)(value >> 24 & 0xFF));
/*     */   }
/*     */ 
/*     */   public static int readSwappedInteger(InputStream input)
/*     */     throws IOException
/*     */   {
/* 348 */     int value1 = read(input);
/* 349 */     int value2 = read(input);
/* 350 */     int value3 = read(input);
/* 351 */     int value4 = read(input);
/*     */ 
/* 353 */     return ((value1 & 0xFF) << 0) + ((value2 & 0xFF) << 8) + ((value3 & 0xFF) << 16) + ((value4 & 0xFF) << 24);
/*     */   }
/*     */ 
/*     */   public static long readSwappedUnsignedInteger(InputStream input)
/*     */     throws IOException
/*     */   {
/* 369 */     int value1 = read(input);
/* 370 */     int value2 = read(input);
/* 371 */     int value3 = read(input);
/* 372 */     int value4 = read(input);
/*     */ 
/* 374 */     long low = ((value1 & 0xFF) << 0) + ((value2 & 0xFF) << 8) + ((value3 & 0xFF) << 16);
/*     */ 
/* 378 */     long high = value4 & 0xFF;
/*     */ 
/* 380 */     return (high << 24) + (0xFFFFFFFF & low);
/*     */   }
/*     */ 
/*     */   public static void writeSwappedLong(OutputStream output, long value)
/*     */     throws IOException
/*     */   {
/* 393 */     output.write((byte)(int)(value >> 0 & 0xFF));
/* 394 */     output.write((byte)(int)(value >> 8 & 0xFF));
/* 395 */     output.write((byte)(int)(value >> 16 & 0xFF));
/* 396 */     output.write((byte)(int)(value >> 24 & 0xFF));
/* 397 */     output.write((byte)(int)(value >> 32 & 0xFF));
/* 398 */     output.write((byte)(int)(value >> 40 & 0xFF));
/* 399 */     output.write((byte)(int)(value >> 48 & 0xFF));
/* 400 */     output.write((byte)(int)(value >> 56 & 0xFF));
/*     */   }
/*     */ 
/*     */   public static long readSwappedLong(InputStream input)
/*     */     throws IOException
/*     */   {
/* 413 */     byte[] bytes = new byte[8];
/* 414 */     for (int i = 0; i < 8; i++) {
/* 415 */       bytes[i] = (byte)read(input);
/*     */     }
/* 417 */     return readSwappedLong(bytes, 0);
/*     */   }
/*     */ 
/*     */   public static void writeSwappedFloat(OutputStream output, float value)
/*     */     throws IOException
/*     */   {
/* 430 */     writeSwappedInteger(output, Float.floatToIntBits(value));
/*     */   }
/*     */ 
/*     */   public static float readSwappedFloat(InputStream input)
/*     */     throws IOException
/*     */   {
/* 443 */     return Float.intBitsToFloat(readSwappedInteger(input));
/*     */   }
/*     */ 
/*     */   public static void writeSwappedDouble(OutputStream output, double value)
/*     */     throws IOException
/*     */   {
/* 456 */     writeSwappedLong(output, Double.doubleToLongBits(value));
/*     */   }
/*     */ 
/*     */   public static double readSwappedDouble(InputStream input)
/*     */     throws IOException
/*     */   {
/* 469 */     return Double.longBitsToDouble(readSwappedLong(input));
/*     */   }
/*     */ 
/*     */   private static int read(InputStream input)
/*     */     throws IOException
/*     */   {
/* 481 */     int value = input.read();
/*     */ 
/* 483 */     if (-1 == value) {
/* 484 */       throw new EOFException("Unexpected EOF reached");
/*     */     }
/*     */ 
/* 487 */     return value;
/*     */   }
/*     */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     org.apache.commons.io.EndianUtils
 * JD-Core Version:    0.6.0
 */