/*     */ package org.apache.log4j;
/*     */ 
/*     */ import java.util.Enumeration;
/*     */ import java.util.Hashtable;
/*     */ import java.util.Stack;
/*     */ import java.util.Vector;
/*     */ import org.apache.log4j.helpers.LogLog;
/*     */ 
/*     */ public class NDC
/*     */ {
/* 113 */   static Hashtable ht = new Hashtable();
/*     */ 
/* 115 */   static int pushCounter = 0;
/*     */   static final int REAP_THRESHOLD = 5;
/*     */ 
/*     */   private static Stack getCurrentStack()
/*     */   {
/* 133 */     if (ht != null) {
/* 134 */       return (Stack)ht.get(Thread.currentThread());
/*     */     }
/* 136 */     return null;
/*     */   }
/*     */ 
/*     */   public static void clear()
/*     */   {
/* 152 */     Stack stack = getCurrentStack();
/* 153 */     if (stack != null)
/* 154 */       stack.setSize(0);
/*     */   }
/*     */ 
/*     */   public static Stack cloneStack()
/*     */   {
/* 175 */     Stack stack = getCurrentStack();
/* 176 */     if (stack == null) {
/* 177 */       return null;
/*     */     }
/* 179 */     return (Stack)stack.clone();
/*     */   }
/*     */ 
/*     */   public static void inherit(Stack stack)
/*     */   {
/* 207 */     if (stack != null)
/* 208 */       ht.put(Thread.currentThread(), stack);
/*     */   }
/*     */ 
/*     */   public static String get()
/*     */   {
/* 219 */     Stack s = getCurrentStack();
/* 220 */     if ((s != null) && (!s.isEmpty())) {
/* 221 */       return ((DiagnosticContext)s.peek()).fullMessage;
/*     */     }
/* 223 */     return null;
/*     */   }
/*     */ 
/*     */   public static int getDepth()
/*     */   {
/* 235 */     Stack stack = getCurrentStack();
/* 236 */     if (stack == null) {
/* 237 */       return 0;
/*     */     }
/* 239 */     return stack.size();
/*     */   }
/*     */ 
/*     */   private static void lazyRemove()
/*     */   {
/* 245 */     if (ht == null) return;
/*     */     Vector v;
/* 252 */     synchronized (ht)
/*     */     {
/* 254 */       if (++pushCounter <= 5) {
/* 255 */         return;
/*     */       }
/* 257 */       pushCounter = 0;
/*     */ 
/* 260 */       int misses = 0;
/* 261 */       v = new Vector();
/* 262 */       Enumeration enumeration = ht.keys();
/*     */       do
/*     */       {
/* 268 */         Thread t = (Thread)enumeration.nextElement();
/* 269 */         if (t.isAlive()) {
/* 270 */           misses++;
/*     */         } else {
/* 272 */           misses = 0;
/* 273 */           v.addElement(t);
/*     */         }
/* 267 */         if (!enumeration.hasMoreElements()) break; 
/* 267 */       }while (misses <= 4);
/*     */     }
/*     */ 
/* 278 */     int size = v.size();
/* 279 */     for (int i = 0; i < size; i++) {
/* 280 */       Thread t = (Thread)v.elementAt(i);
/* 281 */       LogLog.debug("Lazy NDC removal for thread [" + t.getName() + "] (" + ht.size() + ").");
/*     */ 
/* 283 */       ht.remove(t);
/*     */     }
/*     */   }
/*     */ 
/*     */   public static String pop()
/*     */   {
/* 300 */     Stack stack = getCurrentStack();
/* 301 */     if ((stack != null) && (!stack.isEmpty())) {
/* 302 */       return ((DiagnosticContext)stack.pop()).message;
/*     */     }
/* 304 */     return "";
/*     */   }
/*     */ 
/*     */   public static String peek()
/*     */   {
/* 320 */     Stack stack = getCurrentStack();
/* 321 */     if ((stack != null) && (!stack.isEmpty())) {
/* 322 */       return ((DiagnosticContext)stack.peek()).message;
/*     */     }
/* 324 */     return "";
/*     */   }
/*     */ 
/*     */   public static void push(String message)
/*     */   {
/* 337 */     Stack stack = getCurrentStack();
/*     */ 
/* 339 */     if (stack == null) {
/* 340 */       DiagnosticContext dc = new DiagnosticContext(message, null);
/* 341 */       stack = new Stack();
/* 342 */       Thread key = Thread.currentThread();
/* 343 */       ht.put(key, stack);
/* 344 */       stack.push(dc);
/* 345 */     } else if (stack.isEmpty()) {
/* 346 */       DiagnosticContext dc = new DiagnosticContext(message, null);
/* 347 */       stack.push(dc);
/*     */     } else {
/* 349 */       DiagnosticContext parent = (DiagnosticContext)stack.peek();
/* 350 */       stack.push(new DiagnosticContext(message, parent));
/*     */     }
/*     */   }
/*     */ 
/*     */   public static void remove()
/*     */   {
/* 376 */     ht.remove(Thread.currentThread());
/*     */ 
/* 379 */     lazyRemove();
/*     */   }
/*     */ 
/*     */   public static void setMaxDepth(int maxDepth)
/*     */   {
/* 412 */     Stack stack = getCurrentStack();
/* 413 */     if ((stack != null) && (maxDepth < stack.size()))
/* 414 */       stack.setSize(maxDepth);
/*     */   }
/*     */ 
/*     */   private static class DiagnosticContext {
/*     */     String fullMessage;
/*     */     String message;
/*     */ 
/*     */     DiagnosticContext(String message, DiagnosticContext parent) {
/* 424 */       this.message = message;
/* 425 */       if (parent != null)
/* 426 */         this.fullMessage = (parent.fullMessage + ' ' + message);
/*     */       else
/* 428 */         this.fullMessage = message;
/*     */     }
/*     */   }
/*     */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     org.apache.log4j.NDC
 * JD-Core Version:    0.6.0
 */