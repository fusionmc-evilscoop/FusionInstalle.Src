/*    */ package mc.now.util;
/*    */ 
/*    */ import java.io.File;
/*    */ import java.io.PrintStream;
/*    */ import java.util.HashMap;
/*    */ import java.util.LinkedList;
/*    */ import java.util.Map;
/*    */ import java.util.Queue;
/*    */ 
/*    */ public class CollisionCheck
/*    */ {
/*    */   public static void main(String[] args)
/*    */   {
/* 15 */     String dirstr = "./mods/extra";
/*    */ 
/* 17 */     File dir = new File(dirstr);
/*    */ 
/* 19 */     Map parentMap = new HashMap();
/* 20 */     for (File moddir : dir.listFiles()) {
/* 21 */       if (!moddir.isDirectory()) {
/*    */         continue;
/*    */       }
/* 24 */       String parent = moddir.getName();
/* 25 */       System.out.println(parent);
/*    */ 
/* 27 */       Queue queue = new LinkedList();
/* 28 */       for (File f : moddir.listFiles()) {
/* 29 */         queue.add(f);
/*    */       }
/*    */ 
/* 32 */       while (!queue.isEmpty()) {
/* 33 */         File f = (File)queue.poll();
/* 34 */         if (f.getName().startsWith(".")) {
/*    */           continue;
/*    */         }
/* 37 */         if (f.isDirectory()) {
/* 38 */           for (File g : f.listFiles())
/* 39 */             queue.add(g);
/*    */         }
/*    */         else {
/* 42 */           String child = f.getPath().substring(moddir.getPath().length());
/* 43 */           if (!parentMap.containsKey(child)) {
/* 44 */             parentMap.put(child, parent);
/*    */           } else {
/* 46 */             String other = (String)parentMap.get(child);
/* 47 */             System.err.printf("\tTried to add %s to %s but it already belonged to %s\n", new Object[] { child, parent, other });
/*    */           }
/*    */         }
/*    */       }
/*    */     }
/*    */   }
/*    */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     mc.now.util.CollisionCheck
 * JD-Core Version:    0.6.0
 */