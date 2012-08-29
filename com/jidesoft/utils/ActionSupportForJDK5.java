/*     */ package com.jidesoft.utils;
/*     */ 
/*     */ import java.awt.event.ItemEvent;
/*     */ import java.awt.event.ItemListener;
/*     */ import java.beans.PropertyChangeEvent;
/*     */ import java.beans.PropertyChangeListener;
/*     */ import javax.swing.AbstractButton;
/*     */ import javax.swing.Action;
/*     */ import javax.swing.ButtonGroup;
/*     */ import javax.swing.DefaultButtonModel;
/*     */ import javax.swing.Icon;
/*     */ 
/*     */ public class ActionSupportForJDK5
/*     */ {
/*     */   public static final String SELECTED_KEY = "SwingSelectedKey";
/*     */   public static final String DISPLAYED_MNEMONIC_INDEX_KEY = "SwingDisplayedMnemonicIndexKey";
/*     */   public static final String LARGE_ICON_KEY = "SwingLargeIconKey";
/*     */   protected static final String CLIENT_PROPERTY_PROPERTY_CHANGE_LISTENER = "ActionSupportForJDK5.propertyChangeListener";
/*     */   protected static final String CLIENT_PROPERTY_ITEM_LISTENER = "ActionSupportForJDK5.itemListener";
/*     */ 
/*     */   public static void setActionSelected(Action action, boolean selected)
/*     */   {
/*  46 */     action.putValue("SwingSelectedKey", Boolean.valueOf(selected));
/*     */   }
/*     */ 
/*     */   public static boolean isActionSelected(Action action) {
/*  50 */     return Boolean.TRUE.equals(action.getValue("SwingSelectedKey"));
/*     */   }
/*     */ 
/*     */   public static void setDisplayedMnemonicIndex(Action action, int newIndex) {
/*  54 */     action.putValue("SwingDisplayedMnemonicIndexKey", Integer.valueOf(newIndex));
/*     */   }
/*     */ 
/*     */   public static int getDisplayedMnemonicIndex(Action action) {
/*  58 */     return ((Integer)action.getValue("SwingDisplayedMnemonicIndexKey")).intValue();
/*     */   }
/*     */ 
/*     */   public static void setLargeIcon(Action action, Icon icon) {
/*  62 */     action.putValue("SwingLargeIconKey", icon);
/*     */   }
/*     */ 
/*     */   public static Icon getLargeIcon(Action action) {
/*  66 */     Object o = action.getValue("SwingLargeIconKey");
/*  67 */     return (o instanceof Icon) ? (Icon)o : null;
/*     */   }
/*     */ 
/*     */   public static void install(AbstractButton button, Action action) {
/*  71 */     PropertyChangeListener listener = new PropertyChangeListener(button, action) {
/*     */       public void propertyChange(PropertyChangeEvent e) {
/*  73 */         ActionSupportForJDK5.actionPropertyChanged(this.val$button, this.val$action, e.getPropertyName());
/*     */       }
/*     */     };
/*  76 */     action.addPropertyChangeListener(listener);
/*  77 */     ItemListener itemListener = new ItemListener(action) {
/*     */       public void itemStateChanged(ItemEvent e) {
/*  79 */         ActionSupportForJDK5.setActionSelected(this.val$action, e.getStateChange() == 1);
/*     */       }
/*     */     };
/*  82 */     button.addItemListener(itemListener);
/*  83 */     button.putClientProperty("ActionSupportForJDK5.propertyChangeListener", listener);
/*  84 */     button.putClientProperty("ActionSupportForJDK5.itemListener", itemListener);
/*     */   }
/*     */ 
/*     */   public static void install(AbstractButton button) {
/*  88 */     Action action = button.getAction();
/*  89 */     install(button, action);
/*     */   }
/*     */ 
/*     */   public static void uninstall(AbstractButton button, Action action) {
/*  93 */     Object o = button.getClientProperty("ActionSupportForJDK5.propertyChangeListener");
/*  94 */     if ((o instanceof PropertyChangeListener)) {
/*  95 */       action.removePropertyChangeListener((PropertyChangeListener)o);
/*     */     }
/*  97 */     o = button.getClientProperty("ActionSupportForJDK5.itemListener");
/*  98 */     if ((o instanceof ItemListener))
/*  99 */       button.removeItemListener((ItemListener)o);
/*     */   }
/*     */ 
/*     */   public static void uninstall(AbstractButton button)
/*     */   {
/* 104 */     Action action = button.getAction();
/* 105 */     uninstall(button, action);
/*     */   }
/*     */ 
/*     */   public static void actionPropertyChanged(AbstractButton button, Action action, String propertyName) {
/* 109 */     if (("SwingSelectedKey".equals(propertyName)) && (hasSelectedKey(action)))
/* 110 */       setSelectedFromAction(button, action);
/* 111 */     else if ("SwingDisplayedMnemonicIndexKey".equals(propertyName))
/* 112 */       setDisplayedMnemonicIndexFromAction(button, action, true);
/* 113 */     else if ("SwingLargeIconKey".equals(propertyName))
/* 114 */       largeIconChanged(button, action);
/*     */   }
/*     */ 
/*     */   static boolean hasSelectedKey(Action action) {
/* 118 */     return (action != null) && (action.getValue("SwingSelectedKey") != null);
/*     */   }
/*     */ 
/*     */   private static void setSelectedFromAction(AbstractButton button, Action action) {
/* 122 */     boolean flag = false;
/* 123 */     if (action != null)
/* 124 */       flag = isActionSelected(action);
/* 125 */     if (flag != button.isSelected()) {
/* 126 */       button.setSelected(flag);
/* 127 */       if ((!flag) && (button.isSelected()) && ((button.getModel() instanceof DefaultButtonModel))) {
/* 128 */         ButtonGroup buttongroup = ((DefaultButtonModel)button.getModel()).getGroup();
/* 129 */         if (buttongroup != null) {
/* 130 */           buttongroup.remove(button);
/* 131 */           button.setSelected(false);
/* 132 */           buttongroup.add(button);
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   private static void setDisplayedMnemonicIndexFromAction(AbstractButton button, Action action, boolean flag) {
/* 139 */     Integer integer = action != null ? (Integer)action.getValue("SwingDisplayedMnemonicIndexKey") : null;
/* 140 */     if ((flag) || (integer != null))
/*     */     {
/*     */       int i;
/*     */       int i;
/* 142 */       if (integer == null) {
/* 143 */         i = -1;
/*     */       }
/*     */       else {
/* 146 */         i = integer.intValue();
/* 147 */         String s = button.getText();
/* 148 */         if ((s == null) || (i >= s.length()))
/* 149 */           i = -1;
/*     */       }
/* 151 */       button.setDisplayedMnemonicIndex(i);
/*     */     }
/*     */   }
/*     */ 
/*     */   static void largeIconChanged(AbstractButton button, Action action) {
/* 156 */     setIconFromAction(button, action);
/*     */   }
/*     */ 
/*     */   static void setIconFromAction(AbstractButton button, Action action) {
/* 160 */     Icon icon = null;
/* 161 */     if (action != null) {
/* 162 */       icon = (Icon)action.getValue("SwingLargeIconKey");
/* 163 */       if (icon == null)
/* 164 */         icon = (Icon)action.getValue("SmallIcon");
/*     */     }
/* 166 */     button.setIcon(icon);
/*     */   }
/*     */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     com.jidesoft.utils.ActionSupportForJDK5
 * JD-Core Version:    0.6.0
 */