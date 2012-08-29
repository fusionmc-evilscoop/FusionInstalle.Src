/*     */ package com.jidesoft.swing;
/*     */ 
/*     */ import java.awt.event.ActionEvent;
/*     */ import java.awt.event.ActionListener;
/*     */ import javax.swing.AbstractAction;
/*     */ import javax.swing.Action;
/*     */ import javax.swing.ActionMap;
/*     */ import javax.swing.InputMap;
/*     */ import javax.swing.JComponent;
/*     */ import javax.swing.KeyStroke;
/*     */ 
/*     */ public abstract class DelegateAction extends AbstractAction
/*     */ {
/*     */   private Action _action;
/*     */   private JComponent _target;
/*     */ 
/*     */   public DelegateAction()
/*     */   {
/*     */   }
/*     */ 
/*     */   public DelegateAction(Action action)
/*     */   {
/*  44 */     this._action = action;
/*     */   }
/*     */ 
/*     */   public DelegateAction(Action action, JComponent target) {
/*  48 */     this._action = action;
/*  49 */     this._target = target;
/*     */   }
/*     */ 
/*     */   public boolean isEnabled()
/*     */   {
/*  60 */     return (isDelegateEnabled()) || ((this._action != null) && (this._action.isEnabled()));
/*     */   }
/*     */ 
/*     */   public final void actionPerformed(ActionEvent e) {
/*  64 */     if ((!delegateActionPerformed(e)) && 
/*  65 */       (this._action != null))
/*  66 */       if (this._target == null) {
/*  67 */         this._action.actionPerformed(e);
/*     */       }
/*     */       else
/*  70 */         this._action.actionPerformed(new ActionEvent(getTarget(), e.getID(), e.getActionCommand(), e.getWhen(), e.getModifiers()));
/*     */   }
/*     */ 
/*     */   protected Action getAction()
/*     */   {
/*  77 */     return this._action;
/*     */   }
/*     */ 
/*     */   protected void setAction(Action action) {
/*  81 */     this._action = action;
/*     */   }
/*     */ 
/*     */   protected JComponent getTarget() {
/*  85 */     return this._target;
/*     */   }
/*     */ 
/*     */   protected void setTarget(JComponent target) {
/*  89 */     this._target = target;
/*     */   }
/*     */ 
/*     */   public boolean isDelegateEnabled()
/*     */   {
/*  99 */     return super.isEnabled();
/*     */   }
/*     */ 
/*     */   public abstract boolean delegateActionPerformed(ActionEvent paramActionEvent);
/*     */ 
/*     */   public static void replaceAction(JComponent component, int condition, KeyStroke keyStroke, DelegateAction delegateAction)
/*     */   {
/* 125 */     replaceAction(component, condition, component, condition, keyStroke, delegateAction);
/*     */   }
/*     */ 
/*     */   public static void replaceAction(JComponent component, int condition, KeyStroke keyStroke, DelegateAction delegateAction, boolean first) {
/* 129 */     replaceAction(component, condition, component, condition, keyStroke, delegateAction, first);
/*     */   }
/*     */ 
/*     */   public static void replaceAction(JComponent component, int condition, JComponent target, int targetCondition, KeyStroke keyStroke) {
/* 133 */     replaceAction(component, condition, target, targetCondition, keyStroke, new PassthroughDelegateAction(), false);
/*     */   }
/*     */ 
/*     */   public static void replaceAction(JComponent component, int condition, JComponent target, int targetCondition, KeyStroke keyStroke, DelegateAction delegateAction) {
/* 137 */     replaceAction(component, condition, target, targetCondition, keyStroke, delegateAction, false);
/*     */   }
/*     */ 
/*     */   public static void replaceAction(JComponent component, int condition, JComponent target, int targetCondition, KeyStroke keyStroke, DelegateAction delegateAction, boolean first) {
/* 141 */     Object actionCommand = target.getInputMap(targetCondition).get(keyStroke);
/* 142 */     if (actionCommand != null) {
/* 143 */       Action action = target.getActionMap().get(actionCommand);
/* 144 */       if (action != delegateAction) {
/* 145 */         if ((!first) && ((action instanceof DelegateAction))) {
/* 146 */           delegateAction.setAction(((DelegateAction)action).getAction());
/* 147 */           ((DelegateAction)action).setAction(delegateAction);
/* 148 */           delegateAction = (DelegateAction)action;
/*     */         }
/*     */         else {
/* 151 */           delegateAction.setAction(action);
/*     */         }
/* 153 */         if (target != component) {
/* 154 */           delegateAction.setTarget(target);
/* 155 */           replaceAction(component, condition, keyStroke, delegateAction);
/*     */         }
/*     */         else {
/* 158 */           component.getActionMap().put(actionCommand, delegateAction);
/*     */         }
/*     */       }
/*     */ 
/*     */     }
/* 163 */     else if (target != component) {
/* 164 */       delegateAction.setTarget(target);
/* 165 */       replaceAction(component, condition, keyStroke, delegateAction);
/*     */     }
/*     */     else {
/* 168 */       component.registerKeyboardAction(delegateAction, keyStroke, condition);
/*     */     }
/*     */   }
/*     */ 
/*     */   public static void restoreAction(JComponent component, int condition, KeyStroke keyStroke)
/*     */   {
/* 174 */     if (component == null) {
/* 175 */       return;
/*     */     }
/* 177 */     ActionListener action = component.getActionForKeyStroke(keyStroke);
/* 178 */     if ((action instanceof DelegateAction))
/* 179 */       component.registerKeyboardAction(((DelegateAction)action).getAction(), keyStroke, condition);
/*     */   }
/*     */ 
/*     */   public static void restoreAction(JComponent component, int condition, KeyStroke keyStroke, Class<?> actionClass)
/*     */   {
/* 184 */     ActionListener action = component.getActionForKeyStroke(keyStroke);
/* 185 */     ActionListener parent = action;
/* 186 */     ActionListener top = action;
/* 187 */     while ((action instanceof DelegateAction)) {
/* 188 */       if (actionClass.isAssignableFrom(action.getClass())) {
/* 189 */         if (top == action) {
/* 190 */           component.registerKeyboardAction(((DelegateAction)action).getAction(), keyStroke, condition); break;
/*     */         }
/*     */ 
/* 193 */         ((DelegateAction)parent).setAction(((DelegateAction)action).getAction());
/*     */ 
/* 195 */         break;
/*     */       }
/* 197 */       parent = action;
/* 198 */       action = ((DelegateAction)action).getAction();
/*     */     }
/*     */   }
/*     */ 
/*     */   public static void restoreAction(JComponent component, int condition, KeyStroke keyStroke, Action actionToBeRemoved) {
/* 203 */     ActionListener action = component.getActionForKeyStroke(keyStroke);
/* 204 */     ActionListener parent = action;
/* 205 */     ActionListener top = action;
/* 206 */     while ((action instanceof DelegateAction)) {
/* 207 */       if (actionToBeRemoved == action) {
/* 208 */         if (top == action) {
/* 209 */           component.registerKeyboardAction(((DelegateAction)action).getAction(), keyStroke, condition); break;
/*     */         }
/*     */ 
/* 212 */         ((DelegateAction)parent).setAction(((DelegateAction)action).getAction());
/*     */ 
/* 214 */         break;
/*     */       }
/* 216 */       parent = action;
/* 217 */       action = ((DelegateAction)action).getAction();
/*     */     }
/*     */   }
/*     */ 
/*     */   public static class PassthroughDelegateAction extends DelegateAction
/*     */   {
/*     */     private static final long serialVersionUID = -1555177105658867899L;
/*     */ 
/*     */     public boolean delegateActionPerformed(ActionEvent e)
/*     */     {
/* 115 */       return false;
/*     */     }
/*     */ 
/*     */     public boolean isDelegateEnabled()
/*     */     {
/* 120 */       return false;
/*     */     }
/*     */   }
/*     */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     com.jidesoft.swing.DelegateAction
 * JD-Core Version:    0.6.0
 */