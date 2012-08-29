/*     */ package com.jidesoft.swing;
/*     */ 
/*     */ import com.jidesoft.utils.SystemInfo;
/*     */ import java.awt.AWTEvent;
/*     */ import java.awt.EventQueue;
/*     */ import java.awt.event.ActionEvent;
/*     */ import java.awt.event.InputEvent;
/*     */ import java.awt.event.ItemEvent;
/*     */ import java.awt.event.ItemListener;
/*     */ import javax.accessibility.Accessible;
/*     */ import javax.accessibility.AccessibleContext;
/*     */ import javax.accessibility.AccessibleRole;
/*     */ import javax.accessibility.AccessibleState;
/*     */ import javax.swing.AbstractButton;
/*     */ import javax.swing.AbstractButton.AccessibleAbstractButton;
/*     */ import javax.swing.Action;
/*     */ import javax.swing.ButtonGroup;
/*     */ import javax.swing.ButtonModel;
/*     */ import javax.swing.DefaultButtonModel;
/*     */ import javax.swing.Icon;
/*     */ 
/*     */ public class JideToggleButton extends JideButton
/*     */   implements Accessible
/*     */ {
/*     */   private ItemListener _itemListener;
/*     */ 
/*     */   public JideToggleButton()
/*     */   {
/*  32 */     this(null, null, false);
/*     */   }
/*     */ 
/*     */   public JideToggleButton(Icon icon)
/*     */   {
/*  41 */     this(null, icon, false);
/*     */   }
/*     */ 
/*     */   public JideToggleButton(Icon icon, boolean selected)
/*     */   {
/*  51 */     this(null, icon, selected);
/*     */   }
/*     */ 
/*     */   public JideToggleButton(String text)
/*     */   {
/*  60 */     this(text, null, false);
/*     */   }
/*     */ 
/*     */   public JideToggleButton(String text, boolean selected)
/*     */   {
/*  70 */     this(text, null, selected);
/*     */   }
/*     */ 
/*     */   public JideToggleButton(Action a)
/*     */   {
/*  79 */     this();
/*  80 */     setAction(a);
/*     */   }
/*     */ 
/*     */   public JideToggleButton(String text, Icon icon)
/*     */   {
/*  90 */     this(text, icon, false);
/*     */   }
/*     */ 
/*     */   public JideToggleButton(String text, Icon icon, boolean selected)
/*     */   {
/* 102 */     setModel(new ToggleButtonModel());
/*     */ 
/* 104 */     this.model.setSelected(selected);
/*     */ 
/* 107 */     init(text, icon);
/*     */   }
/*     */ 
/*     */   static boolean hasSelectedKey(Action a)
/*     */   {
/* 220 */     return (SystemInfo.isJdk6Above()) && (a != null) && (a.getValue("SwingSelectedKey") != null);
/*     */   }
/*     */ 
/*     */   static boolean isSelected(Action a) {
/* 224 */     return (SystemInfo.isJdk6Above()) && (Boolean.TRUE.equals(a.getValue("SwingSelectedKey")));
/*     */   }
/*     */ 
/*     */   private void setSelectedFromAction(Action a)
/*     */   {
/* 234 */     boolean selected = false;
/* 235 */     if (a != null) {
/* 236 */       selected = isSelected(a);
/*     */     }
/* 238 */     if (selected != isSelected())
/*     */     {
/* 241 */       setSelected(selected);
/*     */ 
/* 243 */       if ((!selected) && (isSelected()) && 
/* 244 */         ((getModel() instanceof DefaultButtonModel))) {
/* 245 */         ButtonGroup group = ((DefaultButtonModel)getModel()).getGroup();
/* 246 */         if ((group != null) && (SystemInfo.isJdk6Above()))
/* 247 */           group.clearSelection();
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void actionPropertyChanged(Action action, String propertyName)
/*     */   {
/* 256 */     if (SystemInfo.isJdk6Above()) {
/* 257 */       super.actionPropertyChanged(action, propertyName);
/* 258 */       if (("SwingSelectedKey".equals(propertyName)) && (hasSelectedKey(action)))
/* 259 */         setSelectedFromAction(action);
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void configurePropertiesFromAction(Action a)
/*     */   {
/* 266 */     super.configurePropertiesFromAction(a);
/* 267 */     if (hasSelectedKey(a))
/* 268 */       setSelectedFromAction(a);
/*     */   }
/*     */ 
/*     */   protected ItemListener createItemListener()
/*     */   {
/* 274 */     if (this._itemListener == null) {
/* 275 */       this._itemListener = new ItemListener() {
/*     */         public void itemStateChanged(ItemEvent event) {
/* 277 */           JideToggleButton.this.fireItemStateChanged(event);
/* 278 */           Action action = JideToggleButton.this.getAction();
/* 279 */           if ((action != null) && (JideToggleButton.hasSelectedKey(action))) {
/* 280 */             boolean selected = JideToggleButton.this.isSelected();
/* 281 */             boolean isActionSelected = JideToggleButton.isSelected(action);
/* 282 */             if ((isActionSelected != selected) && (SystemInfo.isJdk6Above()))
/* 283 */               action.putValue("SwingSelectedKey", Boolean.valueOf(selected));
/*     */           }
/*     */         }
/*     */       };
/*     */     }
/* 289 */     return this._itemListener;
/*     */   }
/*     */ 
/*     */   public AccessibleContext getAccessibleContext()
/*     */   {
/* 306 */     if (this.accessibleContext == null) {
/* 307 */       this.accessibleContext = new AccessibleJToggleButton();
/*     */     }
/* 309 */     return this.accessibleContext;
/*     */   }
/*     */ 
/*     */   protected class AccessibleJToggleButton extends AbstractButton.AccessibleAbstractButton
/*     */     implements ItemListener
/*     */   {
/*     */     public AccessibleJToggleButton()
/*     */     {
/* 325 */       super();
/* 326 */       JideToggleButton.this.addItemListener(this);
/*     */     }
/*     */ 
/*     */     public void itemStateChanged(ItemEvent e)
/*     */     {
/* 333 */       JideToggleButton button = (JideToggleButton)e.getSource();
/* 334 */       if (JideToggleButton.this.accessibleContext != null)
/* 335 */         if (button.isSelected()) {
/* 336 */           JideToggleButton.this.accessibleContext.firePropertyChange("AccessibleState", null, AccessibleState.CHECKED);
/*     */         }
/*     */         else
/*     */         {
/* 340 */           JideToggleButton.this.accessibleContext.firePropertyChange("AccessibleState", AccessibleState.CHECKED, null);
/*     */         }
/*     */     }
/*     */ 
/*     */     public AccessibleRole getAccessibleRole()
/*     */     {
/* 353 */       return AccessibleRole.TOGGLE_BUTTON;
/*     */     }
/*     */   }
/*     */ 
/*     */   public static class ToggleButtonModel extends DefaultButtonModel
/*     */   {
/*     */     public boolean isSelected()
/*     */     {
/* 136 */       return (this.stateMask & 0x2) != 0;
/*     */     }
/*     */ 
/*     */     public void setSelected(boolean b)
/*     */     {
/* 148 */       ButtonGroup group = getGroup();
/* 149 */       if (group != null)
/*     */       {
/* 151 */         group.setSelected(this, b);
/* 152 */         b = group.isSelected(this);
/*     */       }
/*     */ 
/* 155 */       if (isSelected() == b) {
/* 156 */         return;
/*     */       }
/*     */ 
/* 159 */       if (b) {
/* 160 */         this.stateMask |= 2;
/*     */       }
/*     */       else {
/* 163 */         this.stateMask &= -3;
/*     */       }
/*     */ 
/* 167 */       fireStateChanged();
/*     */ 
/* 170 */       fireItemStateChanged(new ItemEvent(this, 701, this, isSelected() ? 1 : 2));
/*     */     }
/*     */ 
/*     */     public void setPressed(boolean b)
/*     */     {
/* 182 */       if ((isPressed() == b) || (!isEnabled())) {
/* 183 */         return;
/*     */       }
/*     */ 
/* 186 */       if ((!b) && (isArmed())) {
/* 187 */         setSelected(!isSelected());
/*     */       }
/*     */ 
/* 190 */       if (b) {
/* 191 */         this.stateMask |= 4;
/*     */       }
/*     */       else {
/* 194 */         this.stateMask &= -5;
/*     */       }
/*     */ 
/* 197 */       fireStateChanged();
/*     */ 
/* 199 */       if ((!isPressed()) && (isArmed())) {
/* 200 */         int modifiers = 0;
/* 201 */         AWTEvent currentEvent = EventQueue.getCurrentEvent();
/* 202 */         if ((currentEvent instanceof InputEvent)) {
/* 203 */           modifiers = ((InputEvent)currentEvent).getModifiers();
/*     */         }
/* 205 */         else if ((currentEvent instanceof ActionEvent)) {
/* 206 */           modifiers = ((ActionEvent)currentEvent).getModifiers();
/*     */         }
/* 208 */         fireActionPerformed(new ActionEvent(this, 1001, getActionCommand(), EventQueue.getMostRecentEventTime(), modifiers));
/*     */       }
/*     */     }
/*     */   }
/*     */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     com.jidesoft.swing.JideToggleButton
 * JD-Core Version:    0.6.0
 */