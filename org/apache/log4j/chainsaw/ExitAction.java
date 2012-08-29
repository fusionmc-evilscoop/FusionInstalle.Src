/*    */ package org.apache.log4j.chainsaw;
/*    */ 
/*    */ import java.awt.event.ActionEvent;
/*    */ import javax.swing.AbstractAction;
/*    */ import org.apache.log4j.Category;
/*    */ import org.apache.log4j.Logger;
/*    */ 
/*    */ class ExitAction extends AbstractAction
/*    */ {
/* 32 */   private static final Logger LOG = Logger.getLogger(ExitAction.class);
/*    */ 
/* 34 */   public static final ExitAction INSTANCE = new ExitAction();
/*    */ 
/*    */   public void actionPerformed(ActionEvent aIgnore)
/*    */   {
/* 44 */     LOG.info("shutting down");
/* 45 */     System.exit(0);
/*    */   }
/*    */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     org.apache.log4j.chainsaw.ExitAction
 * JD-Core Version:    0.6.0
 */