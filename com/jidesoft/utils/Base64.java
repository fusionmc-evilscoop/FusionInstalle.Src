/*      */ package com.jidesoft.utils;
/*      */ 
/*      */ import java.io.BufferedInputStream;
/*      */ import java.io.ByteArrayInputStream;
/*      */ import java.io.ByteArrayOutputStream;
/*      */ import java.io.File;
/*      */ import java.io.FileInputStream;
/*      */ import java.io.FileOutputStream;
/*      */ import java.io.FilterInputStream;
/*      */ import java.io.FilterOutputStream;
/*      */ import java.io.IOException;
/*      */ import java.io.InputStream;
/*      */ import java.io.ObjectInputStream;
/*      */ import java.io.ObjectOutputStream;
/*      */ import java.io.OutputStream;
/*      */ import java.io.PrintStream;
/*      */ import java.io.Serializable;
/*      */ import java.io.UnsupportedEncodingException;
/*      */ import java.util.zip.GZIPInputStream;
/*      */ import java.util.zip.GZIPOutputStream;
/*      */ 
/*      */ public class Base64
/*      */ {
/*      */   public static final int NO_OPTIONS = 0;
/*      */   public static final int ENCODE = 1;
/*      */   public static final int DECODE = 0;
/*      */   public static final int GZIP = 2;
/*      */   public static final int DONT_BREAK_LINES = 8;
/*      */   private static final int MAX_LINE_LENGTH = 76;
/*      */   private static final byte EQUALS_SIGN = 61;
/*      */   private static final byte NEW_LINE = 10;
/*      */   private static final String PREFERRED_ENCODING = "UTF-8";
/*      */   private static final byte[] ALPHABET;
/*   93 */   private static final byte[] _NATIVE_ALPHABET = { 65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78, 79, 80, 81, 82, 83, 84, 85, 86, 87, 88, 89, 90, 97, 98, 99, 100, 101, 102, 103, 104, 105, 106, 107, 108, 109, 110, 111, 112, 113, 114, 115, 116, 117, 118, 119, 120, 121, 122, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 43, 47 };
/*      */   private static final byte[] DECODABET;
/*      */   private static final byte WHITE_SPACE_ENC = -5;
/*      */   private static final byte EQUALS_SIGN_ENC = -1;
/*      */ 
/*      */   private static byte[] encode3to4(byte[] b4, byte[] threeBytes, int numSigBytes)
/*      */   {
/*  188 */     encode3to4(threeBytes, 0, numSigBytes, b4, 0);
/*  189 */     return b4;
/*      */   }
/*      */ 
/*      */   private static byte[] encode3to4(byte[] source, int srcOffset, int numSigBytes, byte[] destination, int destOffset)
/*      */   {
/*  224 */     int inBuff = (numSigBytes > 0 ? source[srcOffset] << 24 >>> 8 : 0) | (numSigBytes > 1 ? source[(srcOffset + 1)] << 24 >>> 16 : 0) | (numSigBytes > 2 ? source[(srcOffset + 2)] << 24 >>> 24 : 0);
/*      */ 
/*  228 */     switch (numSigBytes) {
/*      */     case 3:
/*  230 */       destination[destOffset] = ALPHABET[(inBuff >>> 18)];
/*  231 */       destination[(destOffset + 1)] = ALPHABET[(inBuff >>> 12 & 0x3F)];
/*  232 */       destination[(destOffset + 2)] = ALPHABET[(inBuff >>> 6 & 0x3F)];
/*  233 */       destination[(destOffset + 3)] = ALPHABET[(inBuff & 0x3F)];
/*  234 */       return destination;
/*      */     case 2:
/*  237 */       destination[destOffset] = ALPHABET[(inBuff >>> 18)];
/*  238 */       destination[(destOffset + 1)] = ALPHABET[(inBuff >>> 12 & 0x3F)];
/*  239 */       destination[(destOffset + 2)] = ALPHABET[(inBuff >>> 6 & 0x3F)];
/*  240 */       destination[(destOffset + 3)] = 61;
/*  241 */       return destination;
/*      */     case 1:
/*  244 */       destination[destOffset] = ALPHABET[(inBuff >>> 18)];
/*  245 */       destination[(destOffset + 1)] = ALPHABET[(inBuff >>> 12 & 0x3F)];
/*  246 */       destination[(destOffset + 2)] = 61;
/*  247 */       destination[(destOffset + 3)] = 61;
/*  248 */       return destination;
/*      */     }
/*      */ 
/*  251 */     return destination;
/*      */   }
/*      */ 
/*      */   public static String encodeObject(Serializable serializableObject)
/*      */   {
/*  267 */     return encodeObject(serializableObject, 0);
/*      */   }
/*      */ 
/*      */   public static String encodeObject(Serializable serializableObject, int options)
/*      */   {
/*  295 */     ByteArrayOutputStream baos = null;
/*  296 */     OutputStream b64os = null;
/*  297 */     ObjectOutputStream oos = null;
/*  298 */     GZIPOutputStream gzos = null;
/*      */ 
/*  301 */     int gzip = options & 0x2;
/*  302 */     int dontBreakLines = options & 0x8;
/*      */     try
/*      */     {
/*  306 */       baos = new ByteArrayOutputStream();
/*  307 */       b64os = new OutputStream(baos, 0x1 | dontBreakLines);
/*      */ 
/*  310 */       if (gzip == 2) {
/*  311 */         gzos = new GZIPOutputStream(b64os);
/*  312 */         oos = new ObjectOutputStream(gzos);
/*      */       }
/*      */       else {
/*  315 */         oos = new ObjectOutputStream(b64os);
/*      */       }
/*  317 */       oos.writeObject(serializableObject);
/*      */     }
/*      */     catch (IOException e) {
/*  320 */       e.printStackTrace();
/*  321 */       Object localObject1 = null;
/*      */       return localObject1;
/*      */     }
/*      */     finally
/*      */     {
/*      */       try
/*      */       {
/*  325 */         oos.close();
/*      */       }
/*      */       catch (Exception e) {
/*      */       }
/*      */       try {
/*  330 */         gzos.close();
/*      */       }
/*      */       catch (Exception e) {
/*      */       }
/*      */       try {
/*  335 */         b64os.close();
/*      */       }
/*      */       catch (Exception e) {
/*      */       }
/*      */       try {
/*  340 */         baos.close();
/*      */       }
/*      */       catch (Exception e)
/*      */       {
/*      */       }
/*      */     }
/*      */     try
/*      */     {
/*  348 */       return new String(baos.toByteArray(), "UTF-8");
/*      */     } catch (UnsupportedEncodingException uue) {
/*      */     }
/*  351 */     return new String(baos.toByteArray());
/*      */   }
/*      */ 
/*      */   public static String encodeBytes(byte[] source)
/*      */   {
/*  364 */     return encodeBytes(source, 0, source.length, 0);
/*      */   }
/*      */ 
/*      */   public static String encodeBytes(byte[] source, int options)
/*      */   {
/*  388 */     return encodeBytes(source, 0, source.length, options);
/*      */   }
/*      */ 
/*      */   public static String encodeBytes(byte[] source, int off, int len)
/*      */   {
/*  401 */     return encodeBytes(source, off, len, 0);
/*      */   }
/*      */ 
/*      */   public static String encodeBytes(byte[] source, int off, int len, int options)
/*      */   {
/*  428 */     int dontBreakLines = options & 0x8;
/*  429 */     int gzip = options & 0x2;
/*      */ 
/*  432 */     if (gzip == 2) {
/*  433 */       ByteArrayOutputStream baos = null;
/*  434 */       GZIPOutputStream gzos = null;
/*  435 */       OutputStream b64os = null;
/*      */       try
/*      */       {
/*  440 */         baos = new ByteArrayOutputStream();
/*  441 */         b64os = new OutputStream(baos, 0x1 | dontBreakLines);
/*  442 */         gzos = new GZIPOutputStream(b64os);
/*      */ 
/*  444 */         gzos.write(source, off, len);
/*  445 */         gzos.close();
/*      */       }
/*      */       catch (IOException e) {
/*  448 */         e.printStackTrace();
/*  449 */         Object localObject1 = null;
/*      */         return localObject1;
/*      */       }
/*      */       finally
/*      */       {
/*      */         try
/*      */         {
/*  453 */           gzos.close();
/*      */         }
/*      */         catch (Exception e) {
/*      */         }
/*      */         try {
/*  458 */           b64os.close();
/*      */         }
/*      */         catch (Exception e) {
/*      */         }
/*      */         try {
/*  463 */           baos.close();
/*      */         }
/*      */         catch (Exception e)
/*      */         {
/*      */         }
/*      */       }
/*      */       try
/*      */       {
/*  471 */         return new String(baos.toByteArray(), "UTF-8");
/*      */       }
/*      */       catch (UnsupportedEncodingException uue) {
/*  474 */         return new String(baos.toByteArray());
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/*  481 */     boolean breakLines = dontBreakLines == 0;
/*      */ 
/*  483 */     int len43 = len * 4 / 3;
/*  484 */     byte[] outBuff = new byte[len43 + (len % 3 > 0 ? 4 : 0) + (breakLines ? len43 / 76 : 0)];
/*      */ 
/*  487 */     int d = 0;
/*  488 */     int e = 0;
/*  489 */     int len2 = len - 2;
/*  490 */     int lineLength = 0;
/*  491 */     for (; d < len2; e += 4) {
/*  492 */       encode3to4(source, d + off, 3, outBuff, e);
/*      */ 
/*  494 */       lineLength += 4;
/*  495 */       if ((breakLines) && (lineLength == 76)) {
/*  496 */         outBuff[(e + 4)] = 10;
/*  497 */         e++;
/*  498 */         lineLength = 0;
/*      */       }
/*  491 */       d += 3;
/*      */     }
/*      */ 
/*  502 */     if (d < len) {
/*  503 */       encode3to4(source, d + off, len - d, outBuff, e);
/*  504 */       e += 4;
/*      */     }
/*      */ 
/*      */     try
/*      */     {
/*  509 */       return new String(outBuff, 0, e, "UTF-8");
/*      */     } catch (UnsupportedEncodingException uue) {
/*      */     }
/*  512 */     return new String(outBuff, 0, e);
/*      */   }
/*      */ 
/*      */   private static int decode4to3(byte[] source, int srcOffset, byte[] destination, int destOffset)
/*      */   {
/*  540 */     if (source[(srcOffset + 2)] == 61)
/*      */     {
/*  544 */       int outBuff = (DECODABET[source[srcOffset]] & 0xFF) << 18 | (DECODABET[source[(srcOffset + 1)]] & 0xFF) << 12;
/*      */ 
/*  547 */       destination[destOffset] = (byte)(outBuff >>> 16);
/*  548 */       return 1;
/*      */     }
/*      */ 
/*  552 */     if (source[(srcOffset + 3)] == 61)
/*      */     {
/*  557 */       int outBuff = (DECODABET[source[srcOffset]] & 0xFF) << 18 | (DECODABET[source[(srcOffset + 1)]] & 0xFF) << 12 | (DECODABET[source[(srcOffset + 2)]] & 0xFF) << 6;
/*      */ 
/*  561 */       destination[destOffset] = (byte)(outBuff >>> 16);
/*  562 */       destination[(destOffset + 1)] = (byte)(outBuff >>> 8);
/*  563 */       return 2;
/*      */     }
/*      */ 
/*      */     try
/*      */     {
/*  574 */       int outBuff = (DECODABET[source[srcOffset]] & 0xFF) << 18 | (DECODABET[source[(srcOffset + 1)]] & 0xFF) << 12 | (DECODABET[source[(srcOffset + 2)]] & 0xFF) << 6 | DECODABET[source[(srcOffset + 3)]] & 0xFF;
/*      */ 
/*  580 */       destination[destOffset] = (byte)(outBuff >> 16);
/*  581 */       destination[(destOffset + 1)] = (byte)(outBuff >> 8);
/*  582 */       destination[(destOffset + 2)] = (byte)outBuff;
/*      */ 
/*  584 */       return 3;
/*      */     }
/*      */     catch (Exception e) {
/*  587 */       System.out.println("" + source[srcOffset] + ": " + DECODABET[source[srcOffset]]);
/*  588 */       System.out.println("" + source[(srcOffset + 1)] + ": " + DECODABET[source[(srcOffset + 1)]]);
/*  589 */       System.out.println("" + source[(srcOffset + 2)] + ": " + DECODABET[source[(srcOffset + 2)]]);
/*  590 */       System.out.println("" + source[(srcOffset + 3)] + ": " + DECODABET[source[(srcOffset + 3)]]);
/*  591 */     }return -1;
/*      */   }
/*      */ 
/*      */   public static byte[] decode(byte[] source, int off, int len)
/*      */   {
/*  609 */     int len34 = len * 3 / 4;
/*  610 */     byte[] outBuff = new byte[len34];
/*  611 */     int outBuffPosn = 0;
/*      */ 
/*  613 */     byte[] b4 = new byte[4];
/*  614 */     int b4Posn = 0;
/*  615 */     int i = 0;
/*  616 */     byte sbiCrop = 0;
/*  617 */     byte sbiDecode = 0;
/*  618 */     for (i = off; i < off + len; i++) {
/*  619 */       sbiCrop = (byte)(source[i] & 0x7F);
/*  620 */       sbiDecode = DECODABET[sbiCrop];
/*      */ 
/*  622 */       if (sbiDecode >= -5)
/*      */       {
/*      */         byte[] out;
/*  624 */         if (sbiDecode >= -1) {
/*  625 */           b4[(b4Posn++)] = sbiCrop;
/*  626 */           if (b4Posn > 3) {
/*  627 */             outBuffPosn += decode4to3(b4, 0, outBuff, outBuffPosn);
/*  628 */             b4Posn = 0;
/*      */ 
/*  631 */             if (sbiCrop == 61) {
/*  632 */               break;
/*      */             }
/*      */           }
/*      */         }
/*      */       }
/*      */       else
/*      */       {
/*  639 */         System.err.println("Bad Base64 input character at " + i + ": " + source[i] + "(decimal)");
/*  640 */         return null;
/*      */       }
/*      */     }
/*      */ 
/*  644 */     out = new byte[outBuffPosn];
/*  645 */     System.arraycopy(outBuff, 0, out, 0, outBuffPosn);
/*  646 */     return out;
/*      */   }
/*      */ 
/*      */   public static byte[] decode(String s)
/*      */   {
/*      */     try
/*      */     {
/*  661 */       bytes = s.getBytes("UTF-8");
/*      */     }
/*      */     catch (UnsupportedEncodingException uee) {
/*  664 */       bytes = s.getBytes();
/*      */     }
/*      */ 
/*  669 */     byte[] bytes = decode(bytes, 0, bytes.length);
/*      */ 
/*  673 */     if ((bytes != null) && (bytes.length >= 4))
/*      */     {
/*  675 */       int head = bytes[0] & 0xFF | bytes[1] << 8 & 0xFF00;
/*  676 */       if (35615 == head) {
/*  677 */         ByteArrayInputStream bais = null;
/*  678 */         GZIPInputStream gzis = null;
/*  679 */         ByteArrayOutputStream baos = null;
/*  680 */         byte[] buffer = new byte[2048];
/*  681 */         int length = 0;
/*      */         try
/*      */         {
/*  684 */           baos = new ByteArrayOutputStream();
/*  685 */           bais = new ByteArrayInputStream(bytes);
/*  686 */           gzis = new GZIPInputStream(bais);
/*      */ 
/*  688 */           while ((length = gzis.read(buffer)) >= 0) {
/*  689 */             baos.write(buffer, 0, length);
/*      */           }
/*      */ 
/*  693 */           bytes = baos.toByteArray();
/*      */         }
/*      */         catch (IOException e)
/*      */         {
/*      */         }
/*      */         finally
/*      */         {
/*      */           try {
/*  701 */             baos.close();
/*      */           }
/*      */           catch (Exception e) {
/*      */           }
/*      */           try {
/*  706 */             gzis.close();
/*      */           }
/*      */           catch (Exception e) {
/*      */           }
/*      */           try {
/*  711 */             bais.close();
/*      */           }
/*      */           catch (Exception e)
/*      */           {
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */ 
/*  720 */     return bytes;
/*      */   }
/*      */ 
/*      */   public static Object decodeToObject(String encodedObject)
/*      */   {
/*  735 */     byte[] objBytes = decode(encodedObject);
/*      */ 
/*  737 */     ByteArrayInputStream bais = null;
/*  738 */     ObjectInputStream ois = null;
/*  739 */     Object obj = null;
/*      */     try
/*      */     {
/*  742 */       bais = new ByteArrayInputStream(objBytes);
/*  743 */       ois = new ObjectInputStream(bais);
/*      */ 
/*  745 */       obj = ois.readObject();
/*      */     }
/*      */     catch (IOException e) {
/*  748 */       e.printStackTrace();
/*  749 */       obj = null;
/*      */     }
/*      */     catch (ClassNotFoundException e) {
/*  752 */       e.printStackTrace();
/*  753 */       obj = null;
/*      */     }
/*      */     finally {
/*      */       try {
/*  757 */         bais.close();
/*      */       }
/*      */       catch (Exception e) {
/*      */       }
/*      */       try {
/*  762 */         ois.close();
/*      */       }
/*      */       catch (Exception e)
/*      */       {
/*      */       }
/*      */     }
/*  768 */     return obj;
/*      */   }
/*      */ 
/*      */   public static boolean encodeToFile(byte[] dataToEncode, String filename)
/*      */   {
/*  782 */     boolean success = false;
/*  783 */     OutputStream bos = null;
/*      */     try {
/*  785 */       bos = new OutputStream(new FileOutputStream(filename), 1);
/*      */ 
/*  787 */       bos.write(dataToEncode);
/*  788 */       success = true;
/*      */     }
/*      */     catch (IOException e)
/*      */     {
/*  792 */       success = false;
/*      */     }
/*      */     finally {
/*      */       try {
/*  796 */         bos.close();
/*      */       }
/*      */       catch (Exception e)
/*      */       {
/*      */       }
/*      */     }
/*  802 */     return success;
/*      */   }
/*      */ 
/*      */   public static boolean decodeToFile(String dataToDecode, String filename)
/*      */   {
/*  816 */     boolean success = false;
/*  817 */     OutputStream bos = null;
/*      */     try {
/*  819 */       bos = new OutputStream(new FileOutputStream(filename), 0);
/*      */ 
/*  821 */       bos.write(dataToDecode.getBytes("UTF-8"));
/*  822 */       success = true;
/*      */     }
/*      */     catch (IOException e) {
/*  825 */       success = false;
/*      */     }
/*      */     finally {
/*      */       try {
/*  829 */         bos.close();
/*      */       }
/*      */       catch (Exception e)
/*      */       {
/*      */       }
/*      */     }
/*  835 */     return success;
/*      */   }
/*      */ 
/*      */   public static byte[] decodeFromFile(String filename)
/*      */   {
/*  848 */     byte[] decodedData = null;
/*  849 */     InputStream bis = null;
/*      */     try
/*      */     {
/*  852 */       File file = new File(filename);
/*  853 */       byte[] buffer = null;
/*  854 */       int length = 0;
/*  855 */       int numBytes = 0;
/*      */ 
/*  858 */       if (file.length() > 2147483647L) {
/*  859 */         System.err.println("File is too big for this convenience method (" + file.length() + " bytes).");
/*  860 */         Object localObject1 = null;
/*      */         return localObject1;
/*      */       }
/*  862 */       buffer = new byte[(int)file.length()];
/*      */ 
/*  865 */       bis = new InputStream(new BufferedInputStream(new FileInputStream(file)), 0);
/*      */ 
/*  870 */       while ((numBytes = bis.read(buffer, length, 4096)) >= 0) {
/*  871 */         length += numBytes;
/*      */       }
/*      */ 
/*  874 */       decodedData = new byte[length];
/*  875 */       System.arraycopy(buffer, 0, decodedData, 0, length);
/*      */     }
/*      */     catch (IOException e)
/*      */     {
/*  879 */       System.err.println("Error decoding from file " + filename);
/*      */     }
/*      */     finally {
/*      */       try {
/*  883 */         bis.close();
/*      */       }
/*      */       catch (Exception e)
/*      */       {
/*      */       }
/*      */     }
/*  889 */     return decodedData;
/*      */   }
/*      */ 
/*      */   public static String encodeFromFile(String filename)
/*      */   {
/*  902 */     String encodedData = null;
/*  903 */     InputStream bis = null;
/*      */     try
/*      */     {
/*  906 */       File file = new File(filename);
/*  907 */       byte[] buffer = new byte[(int)(file.length() * 1.4D)];
/*  908 */       int length = 0;
/*  909 */       int numBytes = 0;
/*      */ 
/*  912 */       bis = new InputStream(new BufferedInputStream(new FileInputStream(file)), 1);
/*      */ 
/*  917 */       while ((numBytes = bis.read(buffer, length, 4096)) >= 0) {
/*  918 */         length += numBytes;
/*      */       }
/*      */ 
/*  921 */       encodedData = new String(buffer, 0, length, "UTF-8");
/*      */     }
/*      */     catch (IOException e)
/*      */     {
/*  925 */       System.err.println("Error encoding from file " + filename);
/*      */     }
/*      */     finally {
/*      */       try {
/*  929 */         bis.close();
/*      */       }
/*      */       catch (Exception e)
/*      */       {
/*      */       }
/*      */     }
/*  935 */     return encodedData;
/*      */   }
/*      */ 
/*      */   static
/*      */   {
/*      */     byte[] __bytes;
/*      */     try
/*      */     {
/*  111 */       __bytes = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/".getBytes("UTF-8");
/*      */     }
/*      */     catch (UnsupportedEncodingException use) {
/*  114 */       __bytes = _NATIVE_ALPHABET;
/*      */     }
/*  116 */     ALPHABET = __bytes;
/*      */ 
/*  124 */     DECODABET = new byte[] { -9, -9, -9, -9, -9, -9, -9, -9, -9, -5, -5, -9, -9, -5, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -5, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, 62, -9, -9, -9, 63, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61, -9, -9, -9, -1, -9, -9, -9, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, -9, -9, -9, -9, -9, -9, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, -9, -9, -9, -9 };
/*      */   }
/*      */ 
/*      */   public static class OutputStream extends FilterOutputStream
/*      */   {
/*      */     private boolean encode;
/*      */     private int position;
/*      */     private byte[] buffer;
/*      */     private int bufferLength;
/*      */     private int lineLength;
/*      */     private boolean breakLines;
/*      */     private byte[] b4;
/*      */     private boolean suspendEncoding;
/*      */ 
/*      */     public OutputStream(OutputStream out)
/*      */     {
/* 1167 */       this(out, 1);
/*      */     }
/*      */ 
/*      */     public OutputStream(OutputStream out, int options)
/*      */     {
/* 1191 */       super();
/* 1192 */       this.breakLines = ((options & 0x8) != 8);
/* 1193 */       this.encode = ((options & 0x1) == 1);
/* 1194 */       this.bufferLength = (this.encode ? 3 : 4);
/* 1195 */       this.buffer = new byte[this.bufferLength];
/* 1196 */       this.position = 0;
/* 1197 */       this.lineLength = 0;
/* 1198 */       this.suspendEncoding = false;
/* 1199 */       this.b4 = new byte[4];
/*      */     }
/*      */ 
/*      */     public void write(int theByte)
/*      */       throws IOException
/*      */     {
/* 1214 */       if (this.suspendEncoding) {
/* 1215 */         this.out.write(theByte);
/* 1216 */         return;
/*      */       }
/*      */ 
/* 1220 */       if (this.encode) {
/* 1221 */         this.buffer[(this.position++)] = (byte)theByte;
/* 1222 */         if (this.position >= this.bufferLength)
/*      */         {
/* 1224 */           this.out.write(Base64.access$300(this.b4, this.buffer, this.bufferLength));
/*      */ 
/* 1226 */           this.lineLength += 4;
/* 1227 */           if ((this.breakLines) && (this.lineLength >= 76)) {
/* 1228 */             this.out.write(10);
/* 1229 */             this.lineLength = 0;
/*      */           }
/*      */ 
/* 1232 */           this.position = 0;
/*      */         }
/*      */ 
/*      */       }
/* 1239 */       else if (Base64.DECODABET[(theByte & 0x7F)] > -5) {
/* 1240 */         this.buffer[(this.position++)] = (byte)theByte;
/* 1241 */         if (this.position >= this.bufferLength)
/*      */         {
/* 1243 */           int len = Base64.access$200(this.buffer, 0, this.b4, 0);
/* 1244 */           this.out.write(this.b4, 0, len);
/*      */ 
/* 1246 */           this.position = 0;
/*      */         }
/*      */       }
/* 1249 */       else if (Base64.DECODABET[(theByte & 0x7F)] != -5) {
/* 1250 */         throw new IOException("Invalid character in Base64 data.");
/*      */       }
/*      */     }
/*      */ 
/*      */     public void write(byte[] theBytes, int off, int len)
/*      */       throws IOException
/*      */     {
/* 1267 */       if (this.suspendEncoding) {
/* 1268 */         this.out.write(theBytes, off, len);
/* 1269 */         return;
/*      */       }
/*      */ 
/* 1272 */       for (int i = 0; i < len; i++)
/* 1273 */         write(theBytes[(off + i)]);
/*      */     }
/*      */ 
/*      */     public void flushBase64()
/*      */       throws IOException
/*      */     {
/* 1283 */       if (this.position > 0)
/* 1284 */         if (this.encode) {
/* 1285 */           this.out.write(Base64.access$300(this.b4, this.buffer, this.position));
/* 1286 */           this.position = 0;
/*      */         }
/*      */         else {
/* 1289 */           throw new IOException("Base64 input not properly padded.");
/*      */         }
/*      */     }
/*      */ 
/*      */     public void close()
/*      */       throws IOException
/*      */     {
/* 1304 */       flushBase64();
/*      */ 
/* 1308 */       super.close();
/*      */ 
/* 1310 */       this.buffer = null;
/* 1311 */       this.out = null;
/*      */     }
/*      */ 
/*      */     public void suspendEncoding()
/*      */       throws IOException
/*      */     {
/* 1322 */       flushBase64();
/* 1323 */       this.suspendEncoding = true;
/*      */     }
/*      */ 
/*      */     public void resumeEncoding()
/*      */     {
/* 1334 */       this.suspendEncoding = false;
/*      */     }
/*      */   }
/*      */ 
/*      */   public static class InputStream extends FilterInputStream
/*      */   {
/*      */     private boolean encode;
/*      */     private int position;
/*      */     private byte[] buffer;
/*      */     private int bufferLength;
/*      */     private int numSigBytes;
/*      */     private int lineLength;
/*      */     private boolean breakLines;
/*      */ 
/*      */     public InputStream(InputStream in)
/*      */     {
/*  965 */       this(in, 0);
/*      */     }
/*      */ 
/*      */     public InputStream(InputStream in, int options)
/*      */     {
/*  989 */       super();
/*  990 */       this.breakLines = ((options & 0x8) != 8);
/*  991 */       this.encode = ((options & 0x1) == 1);
/*  992 */       this.bufferLength = (this.encode ? 4 : 3);
/*  993 */       this.buffer = new byte[this.bufferLength];
/*  994 */       this.position = -1;
/*  995 */       this.lineLength = 0;
/*      */     }
/*      */ 
/*      */     public int read()
/*      */       throws IOException
/*      */     {
/* 1008 */       if (this.position < 0) {
/* 1009 */         if (this.encode) {
/* 1010 */           byte[] b3 = new byte[3];
/* 1011 */           int numBinaryBytes = 0;
/* 1012 */           for (int i = 0; i < 3; i++) {
/*      */             try {
/* 1014 */               int b = this.in.read();
/*      */ 
/* 1017 */               if (b >= 0) {
/* 1018 */                 b3[i] = (byte)b;
/* 1019 */                 numBinaryBytes++;
/*      */               }
/*      */ 
/*      */             }
/*      */             catch (IOException e)
/*      */             {
/* 1025 */               if (i == 0) {
/* 1026 */                 throw e;
/*      */               }
/*      */             }
/*      */           }
/*      */ 
/* 1031 */           if (numBinaryBytes > 0) {
/* 1032 */             Base64.access$000(b3, 0, numBinaryBytes, this.buffer, 0);
/* 1033 */             this.position = 0;
/* 1034 */             this.numSigBytes = 4;
/*      */           }
/*      */           else {
/* 1037 */             return -1;
/*      */           }
/*      */ 
/*      */         }
/*      */         else
/*      */         {
/* 1043 */           byte[] b4 = new byte[4];
/* 1044 */           int i = 0;
/* 1045 */           for (i = 0; i < 4; i++)
/*      */           {
/* 1047 */             int b = 0;
/*      */             do {
/* 1049 */               b = this.in.read();
/*      */             }
/* 1051 */             while ((b >= 0) && (Base64.DECODABET[(b & 0x7F)] <= -5));
/*      */ 
/* 1053 */             if (b < 0) {
/*      */               break;
/*      */             }
/* 1056 */             b4[i] = (byte)b;
/*      */           }
/*      */ 
/* 1059 */           if (i == 4) {
/* 1060 */             this.numSigBytes = Base64.access$200(b4, 0, this.buffer, 0);
/* 1061 */             this.position = 0;
/*      */           } else {
/* 1063 */             if (i == 0) {
/* 1064 */               return -1;
/*      */             }
/*      */ 
/* 1068 */             throw new IOException("Improperly padded Base64 input.");
/*      */           }
/*      */ 
/*      */         }
/*      */ 
/*      */       }
/*      */ 
/* 1075 */       if (this.position >= 0)
/*      */       {
/* 1077 */         if (this.position >= this.numSigBytes) {
/* 1078 */           return -1;
/*      */         }
/* 1080 */         if ((this.encode) && (this.breakLines) && (this.lineLength >= 76)) {
/* 1081 */           this.lineLength = 0;
/* 1082 */           return 10;
/*      */         }
/*      */ 
/* 1085 */         this.lineLength += 1;
/*      */ 
/* 1089 */         int b = this.buffer[(this.position++)];
/*      */ 
/* 1091 */         if (this.position >= this.bufferLength) {
/* 1092 */           this.position = -1;
/*      */         }
/* 1094 */         return b & 0xFF;
/*      */       }
/*      */ 
/* 1102 */       throw new IOException("Error in Base64 code reading stream.");
/*      */     }
/*      */ 
/*      */     public int read(byte[] dest, int off, int len)
/*      */       throws IOException
/*      */     {
/* 1122 */       for (int i = 0; i < len; i++) {
/* 1123 */         int b = read();
/*      */ 
/* 1128 */         if (b >= 0) {
/* 1129 */           dest[(off + i)] = (byte)b; } else {
/* 1130 */           if (i != 0) break;
/* 1131 */           return -1;
/*      */         }
/*      */       }
/*      */ 
/* 1135 */       return i;
/*      */     }
/*      */   }
/*      */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     com.jidesoft.utils.Base64
 * JD-Core Version:    0.6.0
 */