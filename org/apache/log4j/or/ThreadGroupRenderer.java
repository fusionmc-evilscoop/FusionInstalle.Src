/*    */ package org.apache.log4j.or;
/*    */ 
/*    */ import org.apache.log4j.Layout;
/*    */ 
/*    */ public class ThreadGroupRenderer
/*    */   implements ObjectRenderer
/*    */ {
/*    */   public String doRender(Object o)
/*    */   {
/* 50 */     if ((o instanceof ThreadGroup)) {
/* 51 */       StringBuffer sbuf = new StringBuffer();
/* 52 */       ThreadGroup tg = (ThreadGroup)o;
/* 53 */       sbuf.append("java.lang.ThreadGroup[name=");
/* 54 */       sbuf.append(tg.getName());
/* 55 */       sbuf.append(", maxpri=");
/* 56 */       sbuf.append(tg.getMaxPriority());
/* 57 */       sbuf.append("]");
/* 58 */       Thread[] t = new Thread[tg.activeCount()];
/* 59 */       tg.enumerate(t);
/* 60 */       for (int i = 0; i < t.length; i++) {
/* 61 */         sbuf.append(Layout.LINE_SEP);
/* 62 */         sbuf.append("   Thread=[");
/* 63 */         sbuf.append(t[i].getName());
/* 64 */         sbuf.append(",");
/* 65 */         sbuf.append(t[i].getPriority());
/* 66 */         sbuf.append(",");
/* 67 */         sbuf.append(t[i].isDaemon());
/* 68 */         sbuf.append("]");
/*    */       }
/* 70 */       return sbuf.toString();
/*    */     }
/*    */ 
/* 73 */     return o.toString();
/*    */   }
/*    */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     org.apache.log4j.or.ThreadGroupRenderer
 * JD-Core Version:    0.6.0
 */