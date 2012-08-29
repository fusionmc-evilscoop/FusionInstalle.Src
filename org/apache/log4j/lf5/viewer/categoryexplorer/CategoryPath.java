/*     */ package org.apache.log4j.lf5.viewer.categoryexplorer;
/*     */ 
/*     */ import java.util.LinkedList;
/*     */ import java.util.StringTokenizer;
/*     */ 
/*     */ public class CategoryPath
/*     */ {
/*  39 */   protected LinkedList _categoryElements = new LinkedList();
/*     */ 
/*     */   public CategoryPath()
/*     */   {
/*     */   }
/*     */ 
/*     */   public CategoryPath(String category)
/*     */   {
/*  57 */     String processedCategory = category;
/*     */ 
/*  59 */     if (processedCategory == null) {
/*  60 */       processedCategory = "Debug";
/*     */     }
/*     */ 
/*  63 */     processedCategory.replace('/', '.');
/*  64 */     processedCategory = processedCategory.replace('\\', '.');
/*     */ 
/*  66 */     StringTokenizer st = new StringTokenizer(processedCategory, ".");
/*  67 */     while (st.hasMoreTokens()) {
/*  68 */       String element = st.nextToken();
/*  69 */       addCategoryElement(new CategoryElement(element));
/*     */     }
/*     */   }
/*     */ 
/*     */   public int size()
/*     */   {
/*  81 */     int count = this._categoryElements.size();
/*     */ 
/*  83 */     return count;
/*     */   }
/*     */ 
/*     */   public boolean isEmpty() {
/*  87 */     boolean empty = false;
/*     */ 
/*  89 */     if (this._categoryElements.size() == 0) {
/*  90 */       empty = true;
/*     */     }
/*     */ 
/*  93 */     return empty;
/*     */   }
/*     */ 
/*     */   public void removeAllCategoryElements()
/*     */   {
/* 101 */     this._categoryElements.clear();
/*     */   }
/*     */ 
/*     */   public void addCategoryElement(CategoryElement categoryElement)
/*     */   {
/* 108 */     this._categoryElements.addLast(categoryElement);
/*     */   }
/*     */ 
/*     */   public CategoryElement categoryElementAt(int index)
/*     */   {
/* 115 */     return (CategoryElement)this._categoryElements.get(index);
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/* 120 */     StringBuffer out = new StringBuffer(100);
/*     */ 
/* 122 */     out.append("\n");
/* 123 */     out.append("===========================\n");
/* 124 */     out.append("CategoryPath:                   \n");
/* 125 */     out.append("---------------------------\n");
/*     */ 
/* 127 */     out.append("\nCategoryPath:\n\t");
/*     */ 
/* 129 */     if (size() > 0)
/* 130 */       for (int i = 0; i < size(); i++) {
/* 131 */         out.append(categoryElementAt(i).toString());
/* 132 */         out.append("\n\t");
/*     */       }
/*     */     else {
/* 135 */       out.append("<<NONE>>");
/*     */     }
/*     */ 
/* 138 */     out.append("\n");
/* 139 */     out.append("===========================\n");
/*     */ 
/* 141 */     return out.toString();
/*     */   }
/*     */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     org.apache.log4j.lf5.viewer.categoryexplorer.CategoryPath
 * JD-Core Version:    0.6.0
 */