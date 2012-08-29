/*     */ package com.jidesoft.utils;
/*     */ 
/*     */ import java.awt.datatransfer.DataFlavor;
/*     */ import java.awt.datatransfer.Transferable;
/*     */ import java.awt.datatransfer.UnsupportedFlavorException;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.PrintStream;
/*     */ import java.io.Reader;
/*     */ import java.io.StringBufferInputStream;
/*     */ import java.io.StringReader;
/*     */ import javax.swing.plaf.UIResource;
/*     */ 
/*     */ public class BasicTransferable
/*     */   implements Transferable, UIResource
/*     */ {
/*     */   protected String plainData;
/*     */   protected String htmlData;
/*     */   private static DataFlavor[] htmlFlavors;
/*     */   private static DataFlavor[] stringFlavors;
/*     */   private static DataFlavor[] plainFlavors;
/*     */ 
/*     */   public BasicTransferable(String plainData, String htmlData)
/*     */   {
/*  55 */     this.plainData = plainData;
/*  56 */     this.htmlData = htmlData;
/*     */   }
/*     */ 
/*     */   public DataFlavor[] getTransferDataFlavors()
/*     */   {
/*  67 */     DataFlavor[] richerFlavors = getRicherFlavors();
/*  68 */     int nRicher = richerFlavors != null ? richerFlavors.length : 0;
/*  69 */     int nHTML = isHTMLSupported() ? htmlFlavors.length : 0;
/*  70 */     int nPlain = isPlainSupported() ? plainFlavors.length : 0;
/*  71 */     int nString = isPlainSupported() ? stringFlavors.length : 0;
/*  72 */     int nFlavors = nRicher + nHTML + nPlain + nString;
/*  73 */     DataFlavor[] flavors = new DataFlavor[nFlavors];
/*     */ 
/*  76 */     int nDone = 0;
/*  77 */     if (nRicher > 0) {
/*  78 */       System.arraycopy(richerFlavors, 0, flavors, nDone, nRicher);
/*  79 */       nDone += nRicher;
/*     */     }
/*  81 */     if (nHTML > 0) {
/*  82 */       System.arraycopy(htmlFlavors, 0, flavors, nDone, nHTML);
/*  83 */       nDone += nHTML;
/*     */     }
/*  85 */     if (nPlain > 0) {
/*  86 */       System.arraycopy(plainFlavors, 0, flavors, nDone, nPlain);
/*  87 */       nDone += nPlain;
/*     */     }
/*  89 */     if (nString > 0) {
/*  90 */       System.arraycopy(stringFlavors, 0, flavors, nDone, nString);
/*  91 */       nDone += nString;
/*     */     }
/*  93 */     return flavors;
/*     */   }
/*     */ 
/*     */   public boolean isDataFlavorSupported(DataFlavor flavor)
/*     */   {
/* 103 */     DataFlavor[] flavors = getTransferDataFlavors();
/* 104 */     for (DataFlavor f : flavors) {
/* 105 */       if (f.equals(flavor)) {
/* 106 */         return true;
/*     */       }
/*     */     }
/* 109 */     return false;
/*     */   }
/*     */ 
/*     */   public Object getTransferData(DataFlavor flavor)
/*     */     throws UnsupportedFlavorException, IOException
/*     */   {
/* 123 */     DataFlavor[] richerFlavors = getRicherFlavors();
/* 124 */     if (isRicherFlavor(flavor)) {
/* 125 */       return getRicherData(flavor);
/*     */     }
/* 127 */     if (isHTMLFlavor(flavor)) {
/* 128 */       String data = getHTMLData();
/* 129 */       data = data == null ? "" : data;
/* 130 */       if (String.class.equals(flavor.getRepresentationClass())) {
/* 131 */         return data;
/*     */       }
/* 133 */       if (Reader.class.equals(flavor.getRepresentationClass())) {
/* 134 */         return new StringReader(data);
/*     */       }
/* 136 */       if (InputStream.class.equals(flavor.getRepresentationClass())) {
/* 137 */         return new StringBufferInputStream(data);
/*     */       }
/*     */ 
/*     */     }
/* 141 */     else if (isPlainFlavor(flavor)) {
/* 142 */       String data = getPlainData();
/* 143 */       data = data == null ? "" : data;
/* 144 */       if (String.class.equals(flavor.getRepresentationClass())) {
/* 145 */         return data;
/*     */       }
/* 147 */       if (Reader.class.equals(flavor.getRepresentationClass())) {
/* 148 */         return new StringReader(data);
/*     */       }
/* 150 */       if (InputStream.class.equals(flavor.getRepresentationClass())) {
/* 151 */         return new StringBufferInputStream(data);
/*     */       }
/*     */ 
/*     */     }
/* 156 */     else if (isStringFlavor(flavor)) {
/* 157 */       String data = getPlainData();
/* 158 */       data = data == null ? "" : data;
/* 159 */       return data;
/*     */     }
/* 161 */     throw new UnsupportedFlavorException(flavor);
/*     */   }
/*     */ 
/*     */   protected boolean isRicherFlavor(DataFlavor flavor)
/*     */   {
/* 167 */     DataFlavor[] richerFlavors = getRicherFlavors();
/* 168 */     int nFlavors = richerFlavors != null ? richerFlavors.length : 0;
/* 169 */     for (int i = 0; i < nFlavors; i++) {
/* 170 */       if (richerFlavors[i].equals(flavor)) {
/* 171 */         return true;
/*     */       }
/*     */     }
/* 174 */     return false;
/*     */   }
/*     */ 
/*     */   protected DataFlavor[] getRicherFlavors()
/*     */   {
/* 182 */     return null;
/*     */   }
/*     */ 
/*     */   protected Object getRicherData(DataFlavor flavor) throws UnsupportedFlavorException {
/* 186 */     return null;
/*     */   }
/*     */ 
/*     */   protected boolean isHTMLFlavor(DataFlavor flavor)
/*     */   {
/* 198 */     DataFlavor[] flavors = htmlFlavors;
/* 199 */     for (DataFlavor flavor1 : flavors) {
/* 200 */       if (flavor1.equals(flavor)) {
/* 201 */         return true;
/*     */       }
/*     */     }
/* 204 */     return false;
/*     */   }
/*     */ 
/*     */   protected boolean isHTMLSupported()
/*     */   {
/* 212 */     return this.htmlData != null;
/*     */   }
/*     */ 
/*     */   protected String getHTMLData()
/*     */   {
/* 219 */     return this.htmlData;
/*     */   }
/*     */ 
/*     */   protected boolean isPlainFlavor(DataFlavor flavor)
/*     */   {
/* 231 */     DataFlavor[] flavors = plainFlavors;
/* 232 */     for (DataFlavor flavor1 : flavors) {
/* 233 */       if (flavor1.equals(flavor)) {
/* 234 */         return true;
/*     */       }
/*     */     }
/* 237 */     return false;
/*     */   }
/*     */ 
/*     */   protected boolean isPlainSupported()
/*     */   {
/* 245 */     return this.plainData != null;
/*     */   }
/*     */ 
/*     */   protected String getPlainData()
/*     */   {
/* 252 */     return this.plainData;
/*     */   }
/*     */ 
/*     */   protected boolean isStringFlavor(DataFlavor flavor)
/*     */   {
/* 264 */     DataFlavor[] flavors = stringFlavors;
/* 265 */     for (DataFlavor f : flavors) {
/* 266 */       if (f.equals(flavor)) {
/* 267 */         return true;
/*     */       }
/*     */     }
/* 270 */     return false;
/*     */   }
/*     */ 
/*     */   static
/*     */   {
/*     */     try
/*     */     {
/*  34 */       htmlFlavors = new DataFlavor[3];
/*  35 */       htmlFlavors[0] = new DataFlavor("text/html;class=java.lang.String");
/*  36 */       htmlFlavors[1] = new DataFlavor("text/html;class=java.io.Reader");
/*  37 */       htmlFlavors[2] = new DataFlavor("text/html;charset=unicode;class=java.io.InputStream");
/*     */ 
/*  39 */       plainFlavors = new DataFlavor[3];
/*  40 */       plainFlavors[0] = new DataFlavor("text/plain;class=java.lang.String");
/*  41 */       plainFlavors[1] = new DataFlavor("text/plain;class=java.io.Reader");
/*  42 */       plainFlavors[2] = new DataFlavor("text/plain;charset=unicode;class=java.io.InputStream");
/*     */ 
/*  44 */       stringFlavors = new DataFlavor[2];
/*  45 */       stringFlavors[0] = new DataFlavor("application/x-java-jvm-local-objectref;class=java.lang.String");
/*  46 */       stringFlavors[1] = DataFlavor.stringFlavor;
/*     */     }
/*     */     catch (ClassNotFoundException cle)
/*     */     {
/*  50 */       System.err.println("error initializing BasicTranserable");
/*     */     }
/*     */   }
/*     */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     com.jidesoft.utils.BasicTransferable
 * JD-Core Version:    0.6.0
 */