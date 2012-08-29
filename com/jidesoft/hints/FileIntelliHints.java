/*     */ package com.jidesoft.hints;
/*     */ 
/*     */ import java.awt.Component;
/*     */ import java.io.File;
/*     */ import java.io.FilenameFilter;
/*     */ import javax.swing.DefaultListCellRenderer;
/*     */ import javax.swing.JList;
/*     */ import javax.swing.text.BadLocationException;
/*     */ import javax.swing.text.Document;
/*     */ import javax.swing.text.JTextComponent;
/*     */ 
/*     */ public class FileIntelliHints extends AbstractListIntelliHints
/*     */ {
/*  22 */   private boolean _folderOnly = false;
/*  23 */   private boolean _showFullPath = true;
/*     */   private FilenameFilter _filter;
/*     */ 
/*     */   public FileIntelliHints(JTextComponent comp)
/*     */   {
/*  27 */     super(comp);
/*     */   }
/*     */ 
/*     */   public boolean isFolderOnly()
/*     */   {
/*  36 */     return this._folderOnly;
/*     */   }
/*     */ 
/*     */   public void setFolderOnly(boolean folderOnly)
/*     */   {
/*  46 */     this._folderOnly = folderOnly;
/*     */   }
/*     */ 
/*     */   public boolean isShowFullPath()
/*     */   {
/*  55 */     return this._showFullPath;
/*     */   }
/*     */ 
/*     */   public void setShowFullPath(boolean showFullPath)
/*     */   {
/*  65 */     this._showFullPath = showFullPath;
/*     */   }
/*     */ 
/*     */   public boolean updateHints(Object value) {
/*  69 */     if (value == null) {
/*  70 */       return false;
/*     */     }
/*  72 */     String s = value.toString();
/*  73 */     if (s.length() == 0) {
/*  74 */       return false;
/*     */     }
/*  76 */     int index1 = s.lastIndexOf('\\');
/*  77 */     int index2 = s.lastIndexOf('/');
/*  78 */     int index = Math.max(index1, index2);
/*  79 */     if (index == -1)
/*  80 */       return false;
/*  81 */     String dir = s.substring(0, index + 1);
/*  82 */     String prefix = index == s.length() - 1 ? null : s.substring(index + 1).toLowerCase();
/*  83 */     String[] files = new File(dir).list(new FilenameFilter(prefix) {
/*     */       public boolean accept(File dir, String name) {
/*  85 */         if ((FileIntelliHints.this.isFolderOnly()) && 
/*  86 */           (new File(dir.getAbsolutePath() + File.separator + name).isFile())) {
/*  87 */           return false;
/*     */         }
/*     */ 
/*  90 */         boolean result = (this.val$prefix == null) || (name.toLowerCase().startsWith(this.val$prefix));
/*  91 */         if ((result) && (FileIntelliHints.this.getFilter() != null)) {
/*  92 */           return FileIntelliHints.this.getFilter().accept(dir, name);
/*     */         }
/*  94 */         return result;
/*     */       }
/*     */     });
/*  98 */     if ((files == null) || (files.length == 0) || ((files.length == 1) && (files[0].equalsIgnoreCase(prefix)))) {
/*  99 */       setListData(new String[0]);
/* 100 */       return false;
/*     */     }
/*     */ 
/* 103 */     getList().setCellRenderer(new PrefixListCellRenderer(isShowFullPath() ? dir : ""));
/* 104 */     setListData(files);
/* 105 */     return true;
/*     */   }
/*     */ 
/*     */   public void acceptHint(Object selected)
/*     */   {
/* 111 */     if (selected == null) {
/* 112 */       return;
/*     */     }
/* 114 */     String selectedValue = "" + selected;
/*     */ 
/* 116 */     String value = getTextComponent().getText();
/* 117 */     int caretPosition = getTextComponent().getCaretPosition();
/* 118 */     int index1 = value.lastIndexOf('\\', caretPosition);
/* 119 */     int index2 = value.lastIndexOf('/', caretPosition);
/* 120 */     int index = Math.max(index1, index2);
/* 121 */     if (index == -1) {
/* 122 */       return;
/*     */     }
/* 124 */     int prefixlen = caretPosition - index - 1;
/*     */     try {
/* 126 */       getTextComponent().getDocument().insertString(caretPosition, selectedValue.substring(prefixlen), null);
/*     */     }
/*     */     catch (BadLocationException e) {
/* 129 */       e.printStackTrace();
/*     */     }
/*     */   }
/*     */ 
/*     */   public FilenameFilter getFilter()
/*     */   {
/* 141 */     return this._filter;
/*     */   }
/*     */ 
/*     */   public void setFilter(FilenameFilter filter)
/*     */   {
/* 151 */     this._filter = filter;
/*     */   }
/*     */   private class PrefixListCellRenderer extends DefaultListCellRenderer {
/*     */     private String _prefix;
/*     */ 
/*     */     public PrefixListCellRenderer(String prefix) {
/* 158 */       this._prefix = prefix;
/*     */     }
/*     */ 
/*     */     public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus)
/*     */     {
/* 163 */       return super.getListCellRendererComponent(list, this._prefix + value, index, isSelected, cellHasFocus);
/*     */     }
/*     */   }
/*     */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     com.jidesoft.hints.FileIntelliHints
 * JD-Core Version:    0.6.0
 */