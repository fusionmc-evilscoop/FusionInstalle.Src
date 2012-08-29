/*     */ package com.jidesoft.plaf.basic;
/*     */ 
/*     */ import com.jidesoft.swing.FolderChooser;
/*     */ import java.io.File;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import javax.swing.Icon;
/*     */ import javax.swing.filechooser.FileSystemView;
/*     */ 
/*     */ class BasicFileSystemTreeNode extends LazyMutableTreeNode
/*     */   implements Comparable
/*     */ {
/*     */   private FolderChooser _folderChooser;
/*     */   private File _file;
/*  21 */   static HashMap _icons = new HashMap();
/*     */ 
/*  23 */   static HashMap _nodes = new HashMap();
/*     */ 
/*     */   protected BasicFileSystemTreeNode(File file) {
/*  26 */     this(file, null);
/*     */   }
/*     */ 
/*     */   protected BasicFileSystemTreeNode(File file, FolderChooser folderChooser) {
/*  30 */     this._file = file;
/*  31 */     this._folderChooser = folderChooser;
/*     */   }
/*     */ 
/*     */   public boolean isLeaf()
/*     */   {
/*  36 */     if (!isLoaded()) {
/*  37 */       return false;
/*     */     }
/*     */ 
/*  40 */     return super.isLeaf();
/*     */   }
/*     */ 
/*     */   public boolean hasChildren()
/*     */   {
/*  45 */     if (!this._loaded) {
/*  46 */       if ((BasicFolderChooserUI.isFileSystem(this._file)) && (this._file.isDirectory())) {
/*  47 */         File[] files = this._folderChooser.getFileSystemView().getFiles(this._file, this._folderChooser.isFileHidingEnabled());
/*  48 */         for (File file : files) {
/*  49 */           if (file.isDirectory()) {
/*  50 */             return true;
/*     */           }
/*     */         }
/*  53 */         this._loaded = true;
/*     */       }
/*  55 */       return false;
/*     */     }
/*     */ 
/*  58 */     return getChildCount() != 0;
/*     */   }
/*     */ 
/*     */   protected void initChildren()
/*     */   {
/*  64 */     if (this._folderChooser == null) {
/*  65 */       return;
/*     */     }
/*  67 */     if (this._file.isDirectory()) {
/*  68 */       File[] files = new File[0];
/*     */       try {
/*  70 */         files = this._folderChooser.getFileSystemView().getFiles(this._file, this._folderChooser.isFileHidingEnabled());
/*     */       }
/*     */       catch (Error e)
/*     */       {
/*     */       }
/*     */       catch (Exception e) {
/*     */       }
/*  77 */       List children = new ArrayList();
/*  78 */       for (File file : files) {
/*  79 */         if (file.isDirectory()) {
/*  80 */           BasicFileSystemTreeNode fileTreeNode = createFileSystemTreeNode(file, this._folderChooser);
/*  81 */           children.add(fileTreeNode);
/*     */         }
/*     */       }
/*  84 */       BasicFileSystemTreeNode[] results = (BasicFileSystemTreeNode[])(BasicFileSystemTreeNode[])children.toArray(new BasicFileSystemTreeNode[children.size()]);
/*  85 */       Arrays.sort(results);
/*  86 */       for (BasicFileSystemTreeNode result : results)
/*  87 */         add(result);
/*     */     }
/*     */   }
/*     */ 
/*     */   public File getFile()
/*     */   {
/*  93 */     return this._file;
/*     */   }
/*     */ 
/*     */   public String getName() {
/*  97 */     return getName(getFile());
/*     */   }
/*     */ 
/*     */   public Icon getIcon() {
/* 101 */     Icon icon = (Icon)_icons.get(this);
/* 102 */     if (icon == null) {
/* 103 */       icon = getIcon(getFile());
/* 104 */       _icons.put(this, icon);
/* 105 */       return icon;
/*     */     }
/*     */ 
/* 108 */     return icon;
/*     */   }
/*     */ 
/*     */   public String getTypeDescription()
/*     */   {
/* 113 */     String desc = getTypeDescription(getFile());
/* 114 */     return desc == null ? "" : desc;
/*     */   }
/*     */ 
/*     */   public Icon getIcon(File file) {
/* 118 */     return this._folderChooser.getFileSystemView().getSystemIcon(file);
/*     */   }
/*     */ 
/*     */   public String getTypeDescription(File file) {
/* 122 */     return this._folderChooser.getFileSystemView().getSystemTypeDescription(file);
/*     */   }
/*     */ 
/*     */   public String getName(File file) {
/* 126 */     return this._folderChooser.getFileSystemView().getSystemDisplayName(file);
/*     */   }
/*     */ 
/*     */   public boolean equals(Object o)
/*     */   {
/* 131 */     if (this == o) return true;
/* 132 */     if ((o == null) || (getClass() != o.getClass())) return false;
/*     */ 
/* 134 */     BasicFileSystemTreeNode that = (BasicFileSystemTreeNode)o;
/*     */ 
/* 136 */     return this._file != null ? this._file.equals(that._file) : that._file == null;
/*     */   }
/*     */ 
/*     */   public int compareTo(Object o)
/*     */   {
/* 141 */     if (!(o instanceof BasicFileSystemTreeNode)) {
/* 142 */       return 0;
/*     */     }
/* 144 */     return getFile().compareTo(((BasicFileSystemTreeNode)o).getFile());
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/* 149 */     return this._file != null ? this._file.hashCode() : 0;
/*     */   }
/*     */ 
/*     */   public boolean canEnqueue() {
/* 153 */     return (!isLoaded()) && (!this._folderChooser.getFileSystemView().isFloppyDrive(getFile())) && (!this._folderChooser.getFileSystemView().isFileSystemRoot(getFile()));
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/* 160 */     return this._file != null ? this._file.toString() : "null";
/*     */   }
/*     */ 
/*     */   public static BasicFileSystemTreeNode createFileSystemTreeNode(File file, FolderChooser folderChooser)
/*     */   {
/* 171 */     BasicFileSystemTreeNode node = (BasicFileSystemTreeNode)_nodes.get(file);
/* 172 */     if (node == null) {
/* 173 */       node = new BasicFileSystemTreeNode(file, folderChooser);
/* 174 */       _nodes.put(file, node);
/*     */     }
/* 176 */     return node;
/*     */   }
/*     */ 
/*     */   public static void clearCache()
/*     */   {
/* 183 */     _nodes.clear();
/*     */   }
/*     */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     com.jidesoft.plaf.basic.BasicFileSystemTreeNode
 * JD-Core Version:    0.6.0
 */