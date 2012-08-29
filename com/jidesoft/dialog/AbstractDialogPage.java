/*     */ package com.jidesoft.dialog;
/*     */ 
/*     */ import java.awt.Component;
/*     */ import javax.swing.Icon;
/*     */ import javax.swing.SwingUtilities;
/*     */ import javax.swing.event.EventListenerList;
/*     */ 
/*     */ public abstract class AbstractDialogPage extends AbstractPage
/*     */ {
/*  13 */   protected transient ButtonEvent _buttonEvent = null;
/*     */   protected String _title;
/*     */   protected String _description;
/*     */   protected Icon _icon;
/*  18 */   protected boolean _pageEnabled = true;
/*     */   protected AbstractDialogPage _parentPage;
/*     */   public static final String TITLE_PROPERTY = "title";
/*     */   public static final String DESCRIPTION_PROPERTY = "description";
/*     */   public static final String ICON_PROPERTY = "icon";
/*     */   public static final String PROPERTY_PAGE_ENABLED = "enabled";
/*     */   private Component _defaultFocusComponent;
/*     */ 
/*     */   protected AbstractDialogPage()
/*     */   {
/*     */   }
/*     */ 
/*     */   public AbstractDialogPage(String title)
/*     */   {
/*  40 */     this._title = title;
/*     */   }
/*     */ 
/*     */   public AbstractDialogPage(String title, String description)
/*     */   {
/*  50 */     this._title = title;
/*  51 */     this._description = description;
/*     */   }
/*     */ 
/*     */   public AbstractDialogPage(String title, Icon icon)
/*     */   {
/*  61 */     this._title = title;
/*  62 */     this._icon = icon;
/*     */   }
/*     */ 
/*     */   public AbstractDialogPage(String title, String description, Icon icon)
/*     */   {
/*  73 */     this._title = title;
/*  74 */     this._icon = icon;
/*  75 */     this._description = description;
/*     */   }
/*     */ 
/*     */   public AbstractDialogPage(String title, String description, Icon icon, AbstractDialogPage parentPage)
/*     */   {
/*  87 */     this._title = title;
/*  88 */     this._icon = icon;
/*  89 */     this._description = description;
/*  90 */     this._parentPage = parentPage;
/*     */   }
/*     */ 
/*     */   public void addButtonListener(ButtonListener l)
/*     */   {
/*  99 */     this.listenerList.add(ButtonListener.class, l);
/*     */   }
/*     */ 
/*     */   public void removeButtonListener(ButtonListener l)
/*     */   {
/* 108 */     this.listenerList.remove(ButtonListener.class, l);
/*     */   }
/*     */ 
/*     */   public ButtonListener[] getButtonListeners()
/*     */   {
/* 120 */     return (ButtonListener[])this.listenerList.getListeners(ButtonListener.class);
/*     */   }
/*     */ 
/*     */   public void fireButtonEvent(int id)
/*     */   {
/* 131 */     fireButtonEvent(id, null, null);
/*     */   }
/*     */ 
/*     */   public void fireButtonEvent(int id, String buttonName)
/*     */   {
/* 141 */     fireButtonEvent(id, buttonName, null);
/*     */   }
/*     */ 
/*     */   public void fireButtonEvent(int id, String buttonName, String userObject)
/*     */   {
/* 152 */     Object[] listeners = this.listenerList.getListenerList();
/* 153 */     for (int i = listeners.length - 2; i >= 0; i -= 2)
/* 154 */       if (listeners[i] == ButtonListener.class) {
/* 155 */         if (this._buttonEvent == null) {
/* 156 */           this._buttonEvent = new ButtonEvent(this, id, buttonName, userObject);
/*     */         }
/*     */         else {
/* 159 */           this._buttonEvent.setID(id);
/* 160 */           this._buttonEvent.setButtonName(buttonName);
/* 161 */           this._buttonEvent.setUserObject(userObject);
/*     */         }
/* 163 */         ((ButtonListener)listeners[(i + 1)]).buttonEventFired(this._buttonEvent);
/*     */       }
/*     */   }
/*     */ 
/*     */   public String getTitle()
/*     */   {
/* 174 */     return this._title;
/*     */   }
/*     */ 
/*     */   public void setTitle(String title)
/*     */   {
/* 183 */     String old = this._title;
/* 184 */     this._title = title;
/* 185 */     firePropertyChange("title", old, this._title);
/*     */   }
/*     */ 
/*     */   public Icon getIcon()
/*     */   {
/* 194 */     return this._icon;
/*     */   }
/*     */ 
/*     */   public void setIcon(Icon icon)
/*     */   {
/* 203 */     Icon old = this._icon;
/* 204 */     this._icon = icon;
/* 205 */     firePropertyChange("icon", old, this._icon);
/*     */   }
/*     */ 
/*     */   public boolean isPageEnabled()
/*     */   {
/* 215 */     return this._pageEnabled;
/*     */   }
/*     */ 
/*     */   public void setPageEnabled(boolean pageEnabled)
/*     */   {
/* 225 */     if (this._pageEnabled != pageEnabled) {
/* 226 */       Boolean oldValue = this._pageEnabled ? Boolean.TRUE : Boolean.FALSE;
/* 227 */       Boolean newValue = pageEnabled ? Boolean.TRUE : Boolean.FALSE;
/* 228 */       this._pageEnabled = pageEnabled;
/* 229 */       firePropertyChange("enabled", oldValue, newValue);
/*     */     }
/*     */   }
/*     */ 
/*     */   public String getDescription()
/*     */   {
/* 239 */     return this._description;
/*     */   }
/*     */ 
/*     */   public void setDescription(String description)
/*     */   {
/* 248 */     String old = this._description;
/* 249 */     this._description = description;
/* 250 */     firePropertyChange("description", old, this._description);
/*     */   }
/*     */ 
/*     */   public AbstractDialogPage getParentPage()
/*     */   {
/* 259 */     return this._parentPage;
/*     */   }
/*     */ 
/*     */   public void setParentPage(AbstractDialogPage parentPage)
/*     */   {
/* 268 */     this._parentPage = parentPage;
/*     */   }
/*     */ 
/*     */   public String getFullTitle()
/*     */   {
/* 277 */     StringBuffer buffer = new StringBuffer(getTitle());
/* 278 */     AbstractDialogPage page = this;
/* 279 */     while (page.getParentPage() != null) {
/* 280 */       AbstractDialogPage parent = page.getParentPage();
/* 281 */       buffer.insert(0, ".");
/* 282 */       buffer.insert(0, parent.getTitle());
/* 283 */       page = parent;
/*     */     }
/* 285 */     return new String(buffer);
/*     */   }
/*     */ 
/*     */   public Component getDefaultFocusComponent()
/*     */   {
/* 294 */     return this._defaultFocusComponent;
/*     */   }
/*     */ 
/*     */   public void setDefaultFocusComponent(Component defaultFocusComponent)
/*     */   {
/* 303 */     this._defaultFocusComponent = defaultFocusComponent;
/*     */   }
/*     */ 
/*     */   public void focusDefaultFocusComponent()
/*     */   {
/* 310 */     Component focusComponent = getDefaultFocusComponent();
/* 311 */     if (focusComponent != null) {
/* 312 */       Runnable runnable = new Runnable(focusComponent) {
/*     */         public void run() {
/* 314 */           if (this.val$focusComponent != null)
/* 315 */             this.val$focusComponent.requestFocusInWindow();
/*     */         }
/*     */       };
/* 319 */       SwingUtilities.invokeLater(runnable);
/*     */     }
/*     */   }
/*     */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     com.jidesoft.dialog.AbstractDialogPage
 * JD-Core Version:    0.6.0
 */