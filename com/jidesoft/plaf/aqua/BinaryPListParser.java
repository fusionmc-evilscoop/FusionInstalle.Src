/*     */ package com.jidesoft.plaf.aqua;
/*     */ 
/*     */ import com.jidesoft.utils.Base64;
/*     */ import com.jidesoft.utils.SecurityUtils;
/*     */ import java.io.ByteArrayInputStream;
/*     */ import java.io.DataInputStream;
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import java.io.PrintStream;
/*     */ import java.io.RandomAccessFile;
/*     */ import java.text.DateFormat;
/*     */ import java.text.SimpleDateFormat;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Date;
/*     */ import java.util.HashMap;
/*     */ 
/*     */ class BinaryPListParser
/*     */ {
/*     */   private int refCount;
/*     */   private int offsetCount;
/*     */   private int objectCount;
/*     */   private int topLevelOffset;
/*     */   private ArrayList objectTable;
/*     */ 
/*     */   public XMLElement parse(File file)
/*     */     throws IOException
/*     */   {
/* 262 */     RandomAccessFile raf = null;
/* 263 */     byte[] buf = null;
/*     */     try {
/* 265 */       raf = new RandomAccessFile(file, "r");
/*     */ 
/* 271 */       int bpli = raf.readInt();
/* 272 */       int st00 = raf.readInt();
/* 273 */       if ((bpli != 1651534953) || (st00 != 1936994352)) {
/* 274 */         throw new IOException("parseHeader: File does not start with 'bplist00' magic.");
/*     */       }
/*     */ 
/* 283 */       raf.seek(raf.length() - 32L);
/*     */ 
/* 285 */       this.offsetCount = (int)raf.readLong();
/*     */ 
/* 287 */       this.refCount = (int)raf.readLong();
/*     */ 
/* 289 */       this.objectCount = (int)raf.readLong();
/*     */ 
/* 291 */       this.topLevelOffset = (int)raf.readLong();
/* 292 */       buf = new byte[this.topLevelOffset - 8];
/* 293 */       raf.seek(8L);
/* 294 */       raf.readFully(buf);
/*     */     }
/*     */     finally {
/* 297 */       if (raf != null) {
/* 298 */         raf.close();
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 304 */     this.objectTable = new ArrayList();
/* 305 */     DataInputStream in = null;
/*     */     try {
/* 307 */       in = new DataInputStream(new ByteArrayInputStream(buf));
/*     */ 
/* 310 */       parseObjectTable(in);
/*     */     }
/*     */     finally {
/* 313 */       if (in != null) {
/* 314 */         in.close();
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 319 */     XMLElement root = new XMLElement(new HashMap(), false, false);
/* 320 */     root.setName("plist");
/* 321 */     root.setAttribute("version", "1.0");
/* 322 */     convertObjectTableToXML(root, this.objectTable.get(0));
/* 323 */     return root;
/*     */   }
/*     */ 
/*     */   private void convertObjectTableToXML(XMLElement parent, Object object)
/*     */   {
/* 331 */     XMLElement elem = parent.createAnotherElement();
/* 332 */     if ((object instanceof BPLDict)) {
/* 333 */       BPLDict dict = (BPLDict)object;
/* 334 */       elem.setName("dict");
/* 335 */       for (int i = 0; i < dict.keyref.length; i++) {
/* 336 */         XMLElement key = parent.createAnotherElement();
/* 337 */         key.setName("key");
/* 338 */         key.setContent(dict.getKey(i));
/* 339 */         elem.addChild(key);
/* 340 */         convertObjectTableToXML(elem, dict.getValue(i));
/*     */       }
/*     */     }
/* 343 */     else if ((object instanceof BPLArray)) {
/* 344 */       BPLArray arr = (BPLArray)object;
/* 345 */       elem.setName("array");
/* 346 */       for (int i = 0; i < arr.objref.length; i++) {
/* 347 */         convertObjectTableToXML(elem, arr.getValue(i));
/*     */       }
/*     */ 
/*     */     }
/* 351 */     else if ((object instanceof String)) {
/* 352 */       elem.setName("string");
/* 353 */       elem.setContent((String)object);
/*     */     }
/* 355 */     else if ((object instanceof Integer)) {
/* 356 */       elem.setName("integer");
/* 357 */       elem.setContent(object.toString());
/*     */     }
/* 359 */     else if ((object instanceof Long)) {
/* 360 */       elem.setName("integer");
/* 361 */       elem.setContent(object.toString());
/*     */     }
/* 363 */     else if ((object instanceof Float)) {
/* 364 */       elem.setName("real");
/* 365 */       elem.setContent(object.toString());
/*     */     }
/* 367 */     else if ((object instanceof Double)) {
/* 368 */       elem.setName("real");
/* 369 */       elem.setContent(object.toString());
/*     */     }
/* 371 */     else if ((object instanceof Boolean)) {
/* 372 */       elem.setName("boolean");
/* 373 */       elem.setContent(object.toString());
/*     */     }
/* 375 */     else if ((object instanceof byte[])) {
/* 376 */       elem.setName("data");
/* 377 */       elem.setContent(Base64.encodeBytes((byte[])(byte[])object));
/*     */     }
/* 379 */     else if ((object instanceof Date)) {
/* 380 */       elem.setName("date");
/* 381 */       DateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
/* 382 */       elem.setContent(format.format((Date)object));
/*     */     }
/*     */     else {
/* 385 */       elem.setName("unsupported");
/* 386 */       elem.setContent(object.toString());
/*     */     }
/* 388 */     parent.addChild(elem);
/*     */   }
/*     */ 
/*     */   private void parseObjectTable(DataInputStream in)
/*     */     throws IOException
/*     */   {
/*     */     int marker;
/* 415 */     while ((marker = in.read()) != -1)
/*     */     {
/* 417 */       switch ((marker & 0xF0) >> 4) {
/*     */       case 0:
/* 419 */         parsePrimitive(in, marker & 0xF);
/* 420 */         break;
/*     */       case 1:
/* 423 */         int count = 1 << (marker & 0xF);
/* 424 */         parseInt(in, count);
/* 425 */         break;
/*     */       case 2:
/* 428 */         int count = 1 << (marker & 0xF);
/* 429 */         parseReal(in, count);
/* 430 */         break;
/*     */       case 3:
/* 433 */         if ((marker & 0xF) != 3) {
/* 434 */           throw new IOException("parseObjectTable: illegal marker " + Integer.toBinaryString(marker));
/*     */         }
/* 436 */         parseDate(in);
/* 437 */         break;
/*     */       case 4:
/* 440 */         int count = marker & 0xF;
/* 441 */         if (count == 15) {
/* 442 */           count = readCount(in);
/*     */         }
/* 444 */         parseData(in, count);
/* 445 */         break;
/*     */       case 5:
/* 448 */         int count = marker & 0xF;
/* 449 */         if (count == 15) {
/* 450 */           count = readCount(in);
/*     */         }
/* 452 */         parseAsciiString(in, count);
/* 453 */         break;
/*     */       case 6:
/* 456 */         int count = marker & 0xF;
/* 457 */         if (count == 15) {
/* 458 */           count = readCount(in);
/*     */         }
/* 460 */         parseUnicodeString(in, count);
/* 461 */         break;
/*     */       case 7:
/* 464 */         System.out.println("parseObjectTable: illegal marker " + Integer.toBinaryString(marker));
/* 465 */         return;
/*     */       case 8:
/* 470 */         int count = (marker & 0xF) + 1;
/* 471 */         System.out.println("uid " + count);
/* 472 */         break;
/*     */       case 9:
/* 475 */         throw new IOException("parseObjectTable: illegal marker " + Integer.toBinaryString(marker));
/*     */       case 10:
/* 479 */         int count = marker & 0xF;
/* 480 */         if (count == 15) {
/* 481 */           count = readCount(in);
/*     */         }
/* 483 */         if (this.refCount > 255) {
/* 484 */           parseShortArray(in, count);
/*     */         }
/*     */         else {
/* 487 */           parseByteArray(in, count);
/*     */         }
/* 489 */         break;
/*     */       case 11:
/* 492 */         throw new IOException("parseObjectTable: illegal marker " + Integer.toBinaryString(marker));
/*     */       case 12:
/* 496 */         throw new IOException("parseObjectTable: illegal marker " + Integer.toBinaryString(marker));
/*     */       case 13:
/* 500 */         int count = marker & 0xF;
/* 501 */         if (count == 15) {
/* 502 */           count = readCount(in);
/*     */         }
/* 504 */         if (this.refCount > 256) {
/* 505 */           parseShortDict(in, count);
/*     */         }
/*     */         else {
/* 508 */           parseByteDict(in, count);
/*     */         }
/* 510 */         break;
/*     */       case 14:
/* 513 */         throw new IOException("parseObjectTable: illegal marker " + Integer.toBinaryString(marker));
/*     */       case 15:
/* 517 */         throw new IOException("parseObjectTable: illegal marker " + Integer.toBinaryString(marker));
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   private int readCount(DataInputStream in)
/*     */     throws IOException
/*     */   {
/* 532 */     int marker = in.read();
/* 533 */     if (marker == -1) {
/* 534 */       throw new IOException("variableLengthInt: Illegal EOF in marker");
/*     */     }
/* 536 */     if ((marker & 0xF0) >> 4 != 1) {
/* 537 */       throw new IOException("variableLengthInt: Illegal marker " + Integer.toBinaryString(marker));
/*     */     }
/* 539 */     int count = 1 << (marker & 0xF);
/* 540 */     int value = 0;
/* 541 */     for (int i = 0; i < count; i++) {
/* 542 */       int b = in.read();
/* 543 */       if (b == -1) {
/* 544 */         throw new IOException("variableLengthInt: Illegal EOF in value");
/*     */       }
/* 546 */       value = value << 8 | b;
/*     */     }
/* 548 */     return value;
/*     */   }
/*     */ 
/*     */   private void parsePrimitive(DataInputStream in, int primitive)
/*     */     throws IOException
/*     */   {
/* 559 */     switch (primitive) {
/*     */     case 0:
/* 561 */       this.objectTable.add(null);
/* 562 */       break;
/*     */     case 8:
/* 564 */       this.objectTable.add(Boolean.FALSE);
/* 565 */       break;
/*     */     case 9:
/* 567 */       this.objectTable.add(Boolean.TRUE);
/* 568 */       break;
/*     */     case 15:
/* 571 */       break;
/*     */     default:
/* 573 */       throw new IOException("parsePrimitive: illegal primitive " + Integer.toBinaryString(primitive));
/*     */     }
/*     */   }
/*     */ 
/*     */   private void parseByteArray(DataInputStream in, int count)
/*     */     throws IOException
/*     */   {
/* 581 */     BPLArray arr = new BPLArray(null);
/* 582 */     arr.objectTable = this.objectTable;
/* 583 */     arr.objref = new int[count];
/*     */ 
/* 585 */     for (int i = 0; i < count; i++) {
/* 586 */       arr.objref[i] = (in.readByte() & 0xFF);
/* 587 */       if (arr.objref[i] == -1) {
/* 588 */         throw new IOException("parseByteArray: illegal EOF in objref*");
/*     */       }
/*     */     }
/*     */ 
/* 592 */     this.objectTable.add(arr);
/*     */   }
/*     */ 
/*     */   private void parseShortArray(DataInputStream in, int count)
/*     */     throws IOException
/*     */   {
/* 599 */     BPLArray arr = new BPLArray(null);
/* 600 */     arr.objectTable = this.objectTable;
/* 601 */     arr.objref = new int[count];
/*     */ 
/* 603 */     for (int i = 0; i < count; i++) {
/* 604 */       arr.objref[i] = (in.readShort() & 0xFFFF);
/* 605 */       if (arr.objref[i] == -1) {
/* 606 */         throw new IOException("parseShortArray: illegal EOF in objref*");
/*     */       }
/*     */     }
/*     */ 
/* 610 */     this.objectTable.add(arr);
/*     */   }
/*     */ 
/*     */   private void parseData(DataInputStream in, int count)
/*     */     throws IOException
/*     */   {
/* 617 */     byte[] data = new byte[count];
/* 618 */     in.readFully(data);
/* 619 */     this.objectTable.add(data);
/*     */   }
/*     */ 
/*     */   private void parseByteDict(DataInputStream in, int count)
/*     */     throws IOException
/*     */   {
/* 626 */     BPLDict dict = new BPLDict(null);
/* 627 */     dict.objectTable = this.objectTable;
/* 628 */     dict.keyref = new int[count];
/* 629 */     dict.objref = new int[count];
/*     */ 
/* 631 */     for (int i = 0; i < count; i++) {
/* 632 */       dict.keyref[i] = (in.readByte() & 0xFF);
/*     */     }
/* 634 */     for (int i = 0; i < count; i++) {
/* 635 */       dict.objref[i] = (in.readByte() & 0xFF);
/*     */     }
/* 637 */     this.objectTable.add(dict);
/*     */   }
/*     */ 
/*     */   private void parseShortDict(DataInputStream in, int count)
/*     */     throws IOException
/*     */   {
/* 644 */     BPLDict dict = new BPLDict(null);
/* 645 */     dict.objectTable = this.objectTable;
/* 646 */     dict.keyref = new int[count];
/* 647 */     dict.objref = new int[count];
/*     */ 
/* 649 */     for (int i = 0; i < count; i++) {
/* 650 */       dict.keyref[i] = (in.readShort() & 0xFFFF);
/*     */     }
/* 652 */     for (int i = 0; i < count; i++) {
/* 653 */       dict.objref[i] = (in.readShort() & 0xFFFF);
/*     */     }
/* 655 */     this.objectTable.add(dict);
/*     */   }
/*     */ 
/*     */   private void parseAsciiString(DataInputStream in, int count)
/*     */     throws IOException
/*     */   {
/* 662 */     byte[] buf = new byte[count];
/* 663 */     in.readFully(buf);
/* 664 */     String str = new String(buf, "ASCII");
/* 665 */     this.objectTable.add(str);
/*     */   }
/*     */ 
/*     */   private void parseInt(DataInputStream in, int count)
/*     */     throws IOException
/*     */   {
/* 672 */     if (count > 8) {
/* 673 */       throw new IOException("parseInt: unsupported byte count:" + count);
/*     */     }
/* 675 */     long value = 0L;
/* 676 */     for (int i = 0; i < count; i++) {
/* 677 */       int b = in.read();
/* 678 */       if (b == -1) {
/* 679 */         throw new IOException("parseInt: Illegal EOF in value");
/*     */       }
/* 681 */       value = value << 8 | b;
/*     */     }
/* 683 */     this.objectTable.add(Long.valueOf(value));
/*     */   }
/*     */ 
/*     */   private void parseReal(DataInputStream in, int count)
/*     */     throws IOException
/*     */   {
/* 690 */     switch (count) {
/*     */     case 4:
/* 692 */       this.objectTable.add(Float.valueOf(in.readFloat()));
/* 693 */       break;
/*     */     case 8:
/* 695 */       this.objectTable.add(Double.valueOf(in.readDouble()));
/* 696 */       break;
/*     */     default:
/* 698 */       throw new IOException("parseReal: unsupported byte count:" + count);
/*     */     }
/*     */   }
/*     */ 
/*     */   private void parseDate(DataInputStream in)
/*     */     throws IOException
/*     */   {
/* 707 */     double date = in.readDouble();
/*     */ 
/* 709 */     this.objectTable.add(new Date());
/*     */   }
/*     */ 
/*     */   private void parseUnicodeString(DataInputStream in, int count)
/*     */     throws IOException
/*     */   {
/* 716 */     char[] buf = new char[count];
/* 717 */     for (int i = 0; i < count; i++) {
/* 718 */       buf[i] = in.readChar();
/*     */     }
/* 720 */     String str = new String(buf);
/* 721 */     this.objectTable.add(str);
/*     */   }
/*     */ 
/*     */   public static void main(String[] args)
/*     */   {
/*     */     try {
/* 727 */       File[] list = new File(SecurityUtils.getProperty("user.home", ""), "Library/Preferences").listFiles();
/*     */ 
/* 733 */       for (int i = 0; i < list.length; i++) {
/* 734 */         String name = list[i].getName();
/* 735 */         if ((list[i].isDirectory()) || (!name.endsWith(".plist")) || (name.endsWith("internetconfig.plist")))
/*     */         {
/*     */           continue;
/*     */         }
/*     */ 
/*     */         try
/*     */         {
/* 743 */           System.out.println(list[i]);
/* 744 */           BinaryPListParser bplr = new BinaryPListParser();
/* 745 */           XMLElement xml = bplr.parse(list[i]);
/* 746 */           System.out.println(xml);
/*     */         }
/*     */         catch (IOException e) {
/* 749 */           if ((e.getMessage() != null) && ((e.getMessage().startsWith("parseHeader")) || (e.getMessage().startsWith("parseTrailer"))))
/*     */           {
/* 752 */             System.out.println(e);
/*     */           }
/*     */           else
/*     */           {
/* 757 */             throw e;
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */     catch (Throwable e) {
/* 763 */       e.printStackTrace();
/*     */     }
/*     */   }
/*     */ 
/*     */   private static class BPLDict
/*     */   {
/*     */     ArrayList objectTable;
/*     */     int[] keyref;
/*     */     int[] objref;
/*     */ 
/*     */     public String getKey(int i)
/*     */     {
/* 206 */       return this.objectTable.get(this.keyref[i]).toString();
/*     */     }
/*     */ 
/*     */     public Object getValue(int i) {
/* 210 */       return this.objectTable.get(this.objref[i]);
/*     */     }
/*     */ 
/*     */     public String toString()
/*     */     {
/* 215 */       StringBuffer buf = new StringBuffer("BPLDict{");
/* 216 */       for (int i = 0; i < this.keyref.length; i++) {
/* 217 */         if (i > 0) {
/* 218 */           buf.append(',');
/*     */         }
/* 220 */         if ((this.keyref[i] < 0) || (this.keyref[i] >= this.objectTable.size())) {
/* 221 */           buf.append("#" + this.keyref[i]);
/*     */         }
/* 223 */         else if (this.objectTable.get(this.keyref[i]) == this) {
/* 224 */           buf.append("*" + this.keyref[i]);
/*     */         }
/*     */         else {
/* 227 */           buf.append(this.objectTable.get(this.keyref[i]));
/*     */         }
/*     */ 
/* 230 */         buf.append(":");
/* 231 */         if ((this.objref[i] < 0) || (this.objref[i] >= this.objectTable.size())) {
/* 232 */           buf.append("#" + this.objref[i]);
/*     */         }
/* 234 */         else if (this.objectTable.get(this.objref[i]) == this) {
/* 235 */           buf.append("*" + this.objref[i]);
/*     */         }
/*     */         else {
/* 238 */           buf.append(this.objectTable.get(this.objref[i]));
/*     */         }
/*     */       }
/*     */ 
/* 242 */       buf.append('}');
/* 243 */       return buf.toString();
/*     */     }
/*     */   }
/*     */ 
/*     */   private static class BPLArray
/*     */   {
/*     */     ArrayList objectTable;
/*     */     int[] objref;
/*     */ 
/*     */     public Object getValue(int i)
/*     */     {
/* 174 */       return this.objectTable.get(this.objref[i]);
/*     */     }
/*     */ 
/*     */     public String toString()
/*     */     {
/* 179 */       StringBuffer buf = new StringBuffer("Array{");
/* 180 */       for (int i = 0; i < this.objref.length; i++) {
/* 181 */         if (i > 0) {
/* 182 */           buf.append(',');
/*     */         }
/* 184 */         if ((this.objectTable.size() > this.objref[i]) && (this.objectTable.get(this.objref[i]) != this))
/*     */         {
/* 186 */           buf.append(this.objectTable.get(this.objref[i]));
/*     */         }
/*     */         else {
/* 189 */           buf.append("*" + this.objref[i]);
/*     */         }
/*     */       }
/* 192 */       buf.append('}');
/* 193 */       return buf.toString();
/*     */     }
/*     */   }
/*     */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     com.jidesoft.plaf.aqua.BinaryPListParser
 * JD-Core Version:    0.6.0
 */