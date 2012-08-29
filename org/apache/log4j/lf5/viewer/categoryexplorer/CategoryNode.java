/*     */ package org.apache.log4j.lf5.viewer.categoryexplorer;
/*     */ 
/*     */ import java.util.Enumeration;
/*     */ import javax.swing.tree.DefaultMutableTreeNode;
/*     */ import javax.swing.tree.TreeNode;
/*     */ 
/*     */ public class CategoryNode extends DefaultMutableTreeNode
/*     */ {
/*  39 */   protected boolean _selected = true;
/*  40 */   protected int _numberOfContainedRecords = 0;
/*  41 */   protected int _numberOfRecordsFromChildren = 0;
/*  42 */   protected boolean _hasFatalChildren = false;
/*  43 */   protected boolean _hasFatalRecords = false;
/*     */ 
/*     */   public CategoryNode(String title)
/*     */   {
/*  57 */     setUserObject(title);
/*     */   }
/*     */ 
/*     */   public String getTitle()
/*     */   {
/*  64 */     return (String)getUserObject();
/*     */   }
/*     */ 
/*     */   public void setSelected(boolean s) {
/*  68 */     if (s != this._selected)
/*  69 */       this._selected = s;
/*     */   }
/*     */ 
/*     */   public boolean isSelected()
/*     */   {
/*  74 */     return this._selected;
/*     */   }
/*     */ 
/*     */   /** @deprecated */
/*     */   public void setAllDescendantsSelected()
/*     */   {
/*  81 */     Enumeration children = children();
/*  82 */     while (children.hasMoreElements()) {
/*  83 */       CategoryNode node = (CategoryNode)children.nextElement();
/*  84 */       node.setSelected(true);
/*  85 */       node.setAllDescendantsSelected();
/*     */     }
/*     */   }
/*     */ 
/*     */   /** @deprecated */
/*     */   public void setAllDescendantsDeSelected()
/*     */   {
/*  93 */     Enumeration children = children();
/*  94 */     while (children.hasMoreElements()) {
/*  95 */       CategoryNode node = (CategoryNode)children.nextElement();
/*  96 */       node.setSelected(false);
/*  97 */       node.setAllDescendantsDeSelected();
/*     */     }
/*     */   }
/*     */ 
/*     */   public String toString() {
/* 102 */     return getTitle();
/*     */   }
/*     */ 
/*     */   public boolean equals(Object obj) {
/* 106 */     if ((obj instanceof CategoryNode)) {
/* 107 */       CategoryNode node = (CategoryNode)obj;
/* 108 */       String tit1 = getTitle().toLowerCase();
/* 109 */       String tit2 = node.getTitle().toLowerCase();
/*     */ 
/* 111 */       if (tit1.equals(tit2)) {
/* 112 */         return true;
/*     */       }
/*     */     }
/* 115 */     return false;
/*     */   }
/*     */ 
/*     */   public int hashCode() {
/* 119 */     return getTitle().hashCode();
/*     */   }
/*     */ 
/*     */   public void addRecord() {
/* 123 */     this._numberOfContainedRecords += 1;
/* 124 */     addRecordToParent();
/*     */   }
/*     */ 
/*     */   public int getNumberOfContainedRecords() {
/* 128 */     return this._numberOfContainedRecords;
/*     */   }
/*     */ 
/*     */   public void resetNumberOfContainedRecords() {
/* 132 */     this._numberOfContainedRecords = 0;
/* 133 */     this._numberOfRecordsFromChildren = 0;
/* 134 */     this._hasFatalRecords = false;
/* 135 */     this._hasFatalChildren = false;
/*     */   }
/*     */ 
/*     */   public boolean hasFatalRecords() {
/* 139 */     return this._hasFatalRecords;
/*     */   }
/*     */ 
/*     */   public boolean hasFatalChildren() {
/* 143 */     return this._hasFatalChildren;
/*     */   }
/*     */ 
/*     */   public void setHasFatalRecords(boolean flag) {
/* 147 */     this._hasFatalRecords = flag;
/*     */   }
/*     */ 
/*     */   public void setHasFatalChildren(boolean flag) {
/* 151 */     this._hasFatalChildren = flag;
/*     */   }
/*     */ 
/*     */   protected int getTotalNumberOfRecords()
/*     */   {
/* 159 */     return getNumberOfRecordsFromChildren() + getNumberOfContainedRecords();
/*     */   }
/*     */ 
/*     */   protected void addRecordFromChild()
/*     */   {
/* 166 */     this._numberOfRecordsFromChildren += 1;
/* 167 */     addRecordToParent();
/*     */   }
/*     */ 
/*     */   protected int getNumberOfRecordsFromChildren() {
/* 171 */     return this._numberOfRecordsFromChildren;
/*     */   }
/*     */ 
/*     */   protected void addRecordToParent() {
/* 175 */     TreeNode parent = getParent();
/* 176 */     if (parent == null) {
/* 177 */       return;
/*     */     }
/* 179 */     ((CategoryNode)parent).addRecordFromChild();
/*     */   }
/*     */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     org.apache.log4j.lf5.viewer.categoryexplorer.CategoryNode
 * JD-Core Version:    0.6.0
 */