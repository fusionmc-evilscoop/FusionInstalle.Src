/*     */ package org.apache.commons.io.input;
/*     */ 
/*     */ import java.io.DataInput;
/*     */ import java.io.EOFException;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import org.apache.commons.io.EndianUtils;
/*     */ 
/*     */ public class SwappedDataInputStream extends ProxyInputStream
/*     */   implements DataInput
/*     */ {
/*     */   public SwappedDataInputStream(InputStream input)
/*     */   {
/*  47 */     super(input);
/*     */   }
/*     */ 
/*     */   public boolean readBoolean()
/*     */     throws IOException, EOFException
/*     */   {
/*  54 */     return 0 == readByte();
/*     */   }
/*     */ 
/*     */   public byte readByte()
/*     */     throws IOException, EOFException
/*     */   {
/*  61 */     return (byte)this.in.read();
/*     */   }
/*     */ 
/*     */   public char readChar()
/*     */     throws IOException, EOFException
/*     */   {
/*  68 */     return (char)readShort();
/*     */   }
/*     */ 
/*     */   public double readDouble()
/*     */     throws IOException, EOFException
/*     */   {
/*  75 */     return EndianUtils.readSwappedDouble(this.in);
/*     */   }
/*     */ 
/*     */   public float readFloat()
/*     */     throws IOException, EOFException
/*     */   {
/*  82 */     return EndianUtils.readSwappedFloat(this.in);
/*     */   }
/*     */ 
/*     */   public void readFully(byte[] data)
/*     */     throws IOException, EOFException
/*     */   {
/*  89 */     readFully(data, 0, data.length);
/*     */   }
/*     */ 
/*     */   public void readFully(byte[] data, int offset, int length)
/*     */     throws IOException, EOFException
/*     */   {
/*  96 */     int remaining = length;
/*     */ 
/*  98 */     while (remaining > 0)
/*     */     {
/* 100 */       int location = offset + (length - remaining);
/* 101 */       int count = read(data, location, remaining);
/*     */ 
/* 103 */       if (-1 == count)
/*     */       {
/* 105 */         throw new EOFException();
/*     */       }
/*     */ 
/* 108 */       remaining -= count;
/*     */     }
/*     */   }
/*     */ 
/*     */   public int readInt()
/*     */     throws IOException, EOFException
/*     */   {
/* 116 */     return EndianUtils.readSwappedInteger(this.in);
/*     */   }
/*     */ 
/*     */   public String readLine()
/*     */     throws IOException, EOFException
/*     */   {
/* 127 */     throw new UnsupportedOperationException("Operation not supported: readLine()");
/*     */   }
/*     */ 
/*     */   public long readLong()
/*     */     throws IOException, EOFException
/*     */   {
/* 135 */     return EndianUtils.readSwappedLong(this.in);
/*     */   }
/*     */ 
/*     */   public short readShort()
/*     */     throws IOException, EOFException
/*     */   {
/* 142 */     return EndianUtils.readSwappedShort(this.in);
/*     */   }
/*     */ 
/*     */   public int readUnsignedByte()
/*     */     throws IOException, EOFException
/*     */   {
/* 149 */     return this.in.read();
/*     */   }
/*     */ 
/*     */   public int readUnsignedShort()
/*     */     throws IOException, EOFException
/*     */   {
/* 156 */     return EndianUtils.readSwappedUnsignedShort(this.in);
/*     */   }
/*     */ 
/*     */   public String readUTF()
/*     */     throws IOException, EOFException
/*     */   {
/* 167 */     throw new UnsupportedOperationException("Operation not supported: readUTF()");
/*     */   }
/*     */ 
/*     */   public int skipBytes(int count)
/*     */     throws IOException, EOFException
/*     */   {
/* 175 */     return (int)this.in.skip(count);
/*     */   }
/*     */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     org.apache.commons.io.input.SwappedDataInputStream
 * JD-Core Version:    0.6.0
 */