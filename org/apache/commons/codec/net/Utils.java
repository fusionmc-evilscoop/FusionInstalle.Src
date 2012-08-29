/*    */ package org.apache.commons.codec.net;
/*    */ 
/*    */ import org.apache.commons.codec.DecoderException;
/*    */ 
/*    */ class Utils
/*    */ {
/*    */   static int digit16(byte b)
/*    */     throws DecoderException
/*    */   {
/* 42 */     int i = Character.digit((char)b, 16);
/* 43 */     if (i == -1) {
/* 44 */       throw new DecoderException("Invalid URL encoding: not a valid digit (radix 16): " + b);
/*    */     }
/* 46 */     return i;
/*    */   }
/*    */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     org.apache.commons.codec.net.Utils
 * JD-Core Version:    0.6.0
 */