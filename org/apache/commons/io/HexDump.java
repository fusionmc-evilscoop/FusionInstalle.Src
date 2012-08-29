/*     */ package org.apache.commons.io;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.OutputStream;
/*     */ 
/*     */ public class HexDump
/*     */ {
/* 108 */   public static final String EOL = System.getProperty("line.separator");
/*     */ 
/* 110 */   private static final StringBuffer _lbuffer = new StringBuffer(8);
/* 111 */   private static final StringBuffer _cbuffer = new StringBuffer(2);
/* 112 */   private static final char[] _hexcodes = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };
/*     */ 
/* 117 */   private static final int[] _shifts = { 28, 24, 20, 16, 12, 8, 4, 0 };
/*     */ 
/*     */   public static void dump(byte[] data, long offset, OutputStream stream, int index)
/*     */     throws IOException, ArrayIndexOutOfBoundsException, IllegalArgumentException
/*     */   {
/*  64 */     if ((index < 0) || (index >= data.length)) {
/*  65 */       throw new ArrayIndexOutOfBoundsException("illegal index: " + index + " into array of length " + data.length);
/*     */     }
/*     */ 
/*  69 */     if (stream == null) {
/*  70 */       throw new IllegalArgumentException("cannot write to nullstream");
/*     */     }
/*  72 */     long display_offset = offset + index;
/*  73 */     StringBuffer buffer = new StringBuffer(74);
/*     */ 
/*  75 */     for (int j = index; j < data.length; j += 16) {
/*  76 */       int chars_read = data.length - j;
/*     */ 
/*  78 */       if (chars_read > 16) {
/*  79 */         chars_read = 16;
/*     */       }
/*  81 */       buffer.append(dump(display_offset)).append(' ');
/*  82 */       for (int k = 0; k < 16; k++) {
/*  83 */         if (k < chars_read)
/*  84 */           buffer.append(dump(data[(k + j)]));
/*     */         else {
/*  86 */           buffer.append("  ");
/*     */         }
/*  88 */         buffer.append(' ');
/*     */       }
/*  90 */       for (int k = 0; k < chars_read; k++) {
/*  91 */         if ((data[(k + j)] >= 32) && (data[(k + j)] < 127))
/*  92 */           buffer.append((char)data[(k + j)]);
/*     */         else {
/*  94 */           buffer.append('.');
/*     */         }
/*     */       }
/*  97 */       buffer.append(EOL);
/*  98 */       stream.write(buffer.toString().getBytes());
/*  99 */       stream.flush();
/* 100 */       buffer.setLength(0);
/* 101 */       display_offset += chars_read;
/*     */     }
/*     */   }
/*     */ 
/*     */   private static StringBuffer dump(long value)
/*     */   {
/* 129 */     _lbuffer.setLength(0);
/* 130 */     for (int j = 0; j < 8; j++) {
/* 131 */       _lbuffer.append(_hexcodes[((int)(value >> _shifts[j]) & 0xF)]);
/*     */     }
/*     */ 
/* 134 */     return _lbuffer;
/*     */   }
/*     */ 
/*     */   private static StringBuffer dump(byte value)
/*     */   {
/* 144 */     _cbuffer.setLength(0);
/* 145 */     for (int j = 0; j < 2; j++) {
/* 146 */       _cbuffer.append(_hexcodes[(value >> _shifts[(j + 6)] & 0xF)]);
/*     */     }
/* 148 */     return _cbuffer;
/*     */   }
/*     */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     org.apache.commons.io.HexDump
 * JD-Core Version:    0.6.0
 */