/*     */ package com.jidesoft.dialog;
/*     */ 
/*     */ import com.jidesoft.swing.JideSwingUtilities;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import javax.swing.DefaultComboBoxModel;
/*     */ 
/*     */ public class PageList extends DefaultComboBoxModel
/*     */ {
/*     */   public AbstractDialogPage getPageByFullTitle(String title)
/*     */   {
/*  25 */     for (int i = 0; i < getSize(); i++) {
/*  26 */       AbstractDialogPage page = (AbstractDialogPage)getElementAt(i);
/*  27 */       if (page.getFullTitle().equals(title)) {
/*  28 */         return page;
/*     */       }
/*     */     }
/*  31 */     return null;
/*     */   }
/*     */ 
/*     */   public int getPageIndexByFullTitle(String title)
/*     */   {
/*  41 */     for (int i = 0; i < getSize(); i++) {
/*  42 */       AbstractDialogPage page = (AbstractDialogPage)getElementAt(i);
/*  43 */       if (page.getFullTitle().equals(title)) {
/*  44 */         return i;
/*     */       }
/*     */     }
/*  47 */     return -1;
/*     */   }
/*     */ 
/*     */   public int getPageCount()
/*     */   {
/*  56 */     return getSize();
/*     */   }
/*     */ 
/*     */   public void append(AbstractDialogPage page)
/*     */   {
/*  65 */     addElement(page);
/*     */   }
/*     */ 
/*     */   public void remove(AbstractDialogPage page)
/*     */   {
/*  74 */     removeElement(page);
/*     */   }
/*     */ 
/*     */   public void clear()
/*     */   {
/*  81 */     removeAllElements();
/*     */   }
/*     */ 
/*     */   public void insertAfter(AbstractDialogPage page, String title)
/*     */   {
/*  92 */     int index = getPageIndexByFullTitle(title);
/*  93 */     if ((index == -1) || (index == getPageCount() - 1)) {
/*  94 */       append(page);
/*     */     }
/*     */     else
/*  97 */       insertElementAt(page, index + 1);
/*     */   }
/*     */ 
/*     */   public AbstractDialogPage getPage(int i)
/*     */   {
/* 108 */     return (AbstractDialogPage)getElementAt(i);
/*     */   }
/*     */ 
/*     */   public List<String> getPageTitlesAsList()
/*     */   {
/* 117 */     List list = new ArrayList();
/* 118 */     for (int i = 0; i < getPageCount(); i++) {
/* 119 */       AbstractDialogPage page = getPage(i);
/* 120 */       list.add(page.getTitle());
/*     */     }
/* 122 */     return list;
/*     */   }
/*     */ 
/*     */   public AbstractDialogPage getCurrentPage()
/*     */   {
/* 131 */     return (AbstractDialogPage)getSelectedItem();
/*     */   }
/*     */ 
/*     */   public void setCurrentPage(AbstractDialogPage page)
/*     */   {
/* 140 */     setCurrentPage(page, null);
/*     */   }
/*     */ 
/*     */   protected boolean setCurrentPage(AbstractDialogPage page, Object source) {
/* 144 */     AbstractDialogPage oldPage = getCurrentPage();
/* 145 */     if ((oldPage != null) && (!oldPage.equals(page))) {
/* 146 */       oldPage.setAllowClosing(true);
/* 147 */       oldPage.firePageEvent(source, 3200);
/* 148 */       if (!oldPage.allowClosing()) {
/* 149 */         return false;
/*     */       }
/* 151 */       oldPage.firePageEvent(source, 3201);
/*     */     }
/*     */ 
/* 154 */     if (!JideSwingUtilities.equals(oldPage, page)) {
/* 155 */       setSelectedItem(page);
/*     */     }
/*     */     else {
/* 158 */       AbstractDialogPage newPage = getCurrentPage();
/* 159 */       if (newPage != null) {
/* 160 */         newPage.firePageEvent(source, 3199);
/*     */       }
/*     */     }
/*     */ 
/* 164 */     return true;
/*     */   }
/*     */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     com.jidesoft.dialog.PageList
 * JD-Core Version:    0.6.0
 */