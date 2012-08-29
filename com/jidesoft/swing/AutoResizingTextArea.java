/*     */ package com.jidesoft.swing;
/*     */ 
/*     */ import java.awt.Component;
/*     */ import java.awt.Container;
/*     */ import javax.swing.JComponent;
/*     */ import javax.swing.JScrollPane;
/*     */ import javax.swing.JTextArea;
/*     */ import javax.swing.JViewport;
/*     */ import javax.swing.event.DocumentEvent;
/*     */ import javax.swing.event.DocumentListener;
/*     */ import javax.swing.text.Document;
/*     */ import javax.swing.text.Element;
/*     */ 
/*     */ public class AutoResizingTextArea extends JTextArea
/*     */ {
/*     */   public static final int DEFAULT_MAX_ROWS = 20;
/*     */   public static final int DEFAULT_MIN_ROWS = 1;
/*     */   private int _maxRows;
/*     */   private int _minRows;
/*     */ 
/*     */   public AutoResizingTextArea()
/*     */   {
/*  40 */     this(1, 20);
/*     */   }
/*     */ 
/*     */   public AutoResizingTextArea(int minRows)
/*     */   {
/*  49 */     this(minRows, 20);
/*     */   }
/*     */ 
/*     */   public AutoResizingTextArea(int minRows, int maxRows)
/*     */   {
/*  60 */     setMinRows(minRows);
/*  61 */     setMaxRows(maxRows);
/*  62 */     setRows(minRows);
/*  63 */     setupDocument();
/*     */   }
/*     */ 
/*     */   public AutoResizingTextArea(String text)
/*     */   {
/*  73 */     this();
/*  74 */     setText(text);
/*     */   }
/*     */ 
/*     */   public AutoResizingTextArea(int minRows, int maxRows, int columns)
/*     */   {
/*  86 */     this(minRows, maxRows);
/*  87 */     setMinRows(minRows);
/*  88 */     setMaxRows(maxRows);
/*  89 */     setColumns(columns);
/*     */   }
/*     */ 
/*     */   public AutoResizingTextArea(String text, int minRows, int maxRows, int columns)
/*     */   {
/* 103 */     this(minRows, maxRows, columns);
/* 104 */     setText(text);
/*     */   }
/*     */ 
/*     */   public AutoResizingTextArea(Document doc)
/*     */   {
/* 114 */     this();
/* 115 */     setDocument(doc);
/*     */   }
/*     */ 
/*     */   public AutoResizingTextArea(Document doc, String text, int minRows, int maxRows, int columns)
/*     */   {
/* 131 */     super(doc, text, minRows, columns);
/* 132 */     setMaxRows(maxRows);
/* 133 */     setMinRows(minRows);
/* 134 */     setupDocument();
/*     */   }
/*     */ 
/*     */   public void setRows(int rows)
/*     */   {
/* 145 */     int oldRow = super.getRows();
/* 146 */     int newRow = clipRowCount(rows);
/* 147 */     super.setRows(newRow);
/*     */ 
/* 149 */     numberOfRowsUpdated(oldRow, newRow);
/*     */   }
/*     */ 
/*     */   protected void numberOfRowsUpdated(int oldRow, int newRow)
/*     */   {
/* 162 */     JScrollPane scroll = getParentScrollPane();
/* 163 */     if (scroll != null) {
/* 164 */       Container parent = scroll.getParent();
/* 165 */       if ((parent != null) && ((parent instanceof JComponent))) {
/* 166 */         JComponent component = (JComponent)parent;
/* 167 */         component.revalidate();
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public int getMaxRows()
/*     */   {
/* 179 */     return this._maxRows;
/*     */   }
/*     */ 
/*     */   public void setMaxRows(int maxRows)
/*     */   {
/* 188 */     this._maxRows = maxRows;
/* 189 */     setRows(clipRowCount(getRows()));
/*     */   }
/*     */ 
/*     */   public int getMinRows()
/*     */   {
/* 199 */     return this._minRows;
/*     */   }
/*     */ 
/*     */   public void setMinRows(int minRows)
/*     */   {
/* 208 */     this._minRows = minRows;
/* 209 */     setRows(clipRowCount(getRows()));
/*     */   }
/*     */ 
/*     */   private void setupDocument() {
/* 213 */     getDocument().addDocumentListener(new ResizingDocumentListener(null));
/*     */   }
/*     */ 
/*     */   private int clipRowCount(int rows)
/*     */   {
/* 223 */     int r = Math.min(this._maxRows, rows);
/* 224 */     r = Math.max(this._minRows, r);
/* 225 */     return r;
/*     */   }
/*     */ 
/*     */   private void updateSize(DocumentEvent e)
/*     */   {
/* 253 */     Element[] roots = e.getDocument().getRootElements();
/* 254 */     Element root = roots[0];
/*     */ 
/* 258 */     int rowCount = root.getElementCount();
/* 259 */     setRows(clipRowCount(rowCount));
/*     */   }
/*     */ 
/*     */   private JScrollPane getParentScrollPane()
/*     */   {
/* 269 */     Component parent = getParent();
/* 270 */     if ((parent != null) && ((parent instanceof JViewport))) {
/* 271 */       return (JScrollPane)parent.getParent();
/*     */     }
/* 273 */     return null;
/*     */   }
/*     */ 
/*     */   private class ResizingDocumentListener
/*     */     implements DocumentListener
/*     */   {
/*     */     private ResizingDocumentListener()
/*     */     {
/*     */     }
/*     */ 
/*     */     public void insertUpdate(DocumentEvent e)
/*     */     {
/* 233 */       AutoResizingTextArea.this.updateSize(e);
/*     */     }
/*     */ 
/*     */     public void removeUpdate(DocumentEvent e) {
/* 237 */       AutoResizingTextArea.this.updateSize(e);
/*     */     }
/*     */ 
/*     */     public void changedUpdate(DocumentEvent e) {
/* 241 */       AutoResizingTextArea.this.updateSize(e);
/*     */     }
/*     */   }
/*     */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     com.jidesoft.swing.AutoResizingTextArea
 * JD-Core Version:    0.6.0
 */