/*     */ package org.apache.log4j.lf5.viewer.configure;
/*     */ 
/*     */ import java.io.BufferedInputStream;
/*     */ import java.io.File;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.FileNotFoundException;
/*     */ import java.io.FileOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.ObjectOutputStream;
/*     */ import java.net.URL;
/*     */ import java.util.AbstractSequentialList;
/*     */ import java.util.Iterator;
/*     */ import java.util.LinkedList;
/*     */ 
/*     */ public class MRUFileManager
/*     */ {
/*     */   private static final String CONFIG_FILE_NAME = "mru_file_manager";
/*     */   private static final int DEFAULT_MAX_SIZE = 3;
/*  48 */   private int _maxSize = 0;
/*     */   private LinkedList _mruFileList;
/*     */ 
/*     */   public MRUFileManager()
/*     */   {
/*  55 */     load();
/*  56 */     setMaxSize(3);
/*     */   }
/*     */ 
/*     */   public MRUFileManager(int maxSize) {
/*  60 */     load();
/*  61 */     setMaxSize(maxSize);
/*     */   }
/*     */ 
/*     */   public void save()
/*     */   {
/*  71 */     File file = new File(getFilename());
/*     */     try
/*     */     {
/*  74 */       ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file));
/*     */ 
/*  76 */       oos.writeObject(this._mruFileList);
/*  77 */       oos.flush();
/*  78 */       oos.close();
/*     */     }
/*     */     catch (Exception e) {
/*  81 */       e.printStackTrace();
/*     */     }
/*     */   }
/*     */ 
/*     */   public int size()
/*     */   {
/*  89 */     return this._mruFileList.size();
/*     */   }
/*     */ 
/*     */   public Object getFile(int index)
/*     */   {
/*  97 */     if (index < size()) {
/*  98 */       return this._mruFileList.get(index);
/*     */     }
/*     */ 
/* 101 */     return null;
/*     */   }
/*     */ 
/*     */   public InputStream getInputStream(int index)
/*     */     throws IOException, FileNotFoundException
/*     */   {
/* 109 */     if (index < size()) {
/* 110 */       Object o = getFile(index);
/* 111 */       if ((o instanceof File)) {
/* 112 */         return getInputStream((File)o);
/*     */       }
/* 114 */       return getInputStream((URL)o);
/*     */     }
/*     */ 
/* 117 */     return null;
/*     */   }
/*     */ 
/*     */   public void set(File file)
/*     */   {
/* 124 */     setMRU(file);
/*     */   }
/*     */ 
/*     */   public void set(URL url)
/*     */   {
/* 131 */     setMRU(url);
/*     */   }
/*     */ 
/*     */   public String[] getMRUFileList()
/*     */   {
/* 138 */     if (size() == 0) {
/* 139 */       return null;
/*     */     }
/*     */ 
/* 142 */     String[] ss = new String[size()];
/*     */ 
/* 144 */     for (int i = 0; i < size(); i++) {
/* 145 */       Object o = getFile(i);
/* 146 */       if ((o instanceof File)) {
/* 147 */         ss[i] = ((File)o).getAbsolutePath();
/*     */       }
/*     */       else {
/* 150 */         ss[i] = o.toString();
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 155 */     return ss;
/*     */   }
/*     */ 
/*     */   public void moveToTop(int index)
/*     */   {
/* 164 */     this._mruFileList.add(0, this._mruFileList.remove(index));
/*     */   }
/*     */ 
/*     */   public static void createConfigurationDirectory()
/*     */   {
/* 174 */     String home = System.getProperty("user.home");
/* 175 */     String sep = System.getProperty("file.separator");
/* 176 */     File f = new File(home + sep + "lf5");
/* 177 */     if (!f.exists())
/*     */       try {
/* 179 */         f.mkdir();
/*     */       } catch (SecurityException e) {
/* 181 */         e.printStackTrace();
/*     */       }
/*     */   }
/*     */ 
/*     */   protected InputStream getInputStream(File file)
/*     */     throws IOException, FileNotFoundException
/*     */   {
/* 197 */     BufferedInputStream reader = new BufferedInputStream(new FileInputStream(file));
/*     */ 
/* 200 */     return reader;
/*     */   }
/*     */ 
/*     */   protected InputStream getInputStream(URL url)
/*     */     throws IOException
/*     */   {
/* 210 */     return url.openStream();
/*     */   }
/*     */ 
/*     */   protected void setMRU(Object o)
/*     */   {
/* 217 */     int index = this._mruFileList.indexOf(o);
/*     */ 
/* 219 */     if (index == -1) {
/* 220 */       this._mruFileList.add(0, o);
/* 221 */       setMaxSize(this._maxSize);
/*     */     } else {
/* 223 */       moveToTop(index);
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void load()
/*     */   {
/* 232 */     createConfigurationDirectory();
/* 233 */     File file = new File(getFilename());
/* 234 */     if (file.exists())
/*     */       try {
/* 236 */         ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file));
/*     */ 
/* 238 */         this._mruFileList = ((LinkedList)ois.readObject());
/* 239 */         ois.close();
/*     */ 
/* 242 */         Iterator it = this._mruFileList.iterator();
/* 243 */         while (it.hasNext()) {
/* 244 */           Object o = it.next();
/* 245 */           if ((!(o instanceof File)) && (!(o instanceof URL)))
/* 246 */             it.remove();
/*     */         }
/*     */       }
/*     */       catch (Exception e) {
/* 250 */         this._mruFileList = new LinkedList();
/*     */       }
/*     */     else
/* 253 */       this._mruFileList = new LinkedList();
/*     */   }
/*     */ 
/*     */   protected String getFilename()
/*     */   {
/* 259 */     String home = System.getProperty("user.home");
/* 260 */     String sep = System.getProperty("file.separator");
/*     */ 
/* 262 */     return home + sep + "lf5" + sep + "mru_file_manager";
/*     */   }
/*     */ 
/*     */   protected void setMaxSize(int maxSize)
/*     */   {
/* 269 */     if (maxSize < this._mruFileList.size()) {
/* 270 */       for (int i = 0; i < this._mruFileList.size() - maxSize; i++) {
/* 271 */         this._mruFileList.removeLast();
/*     */       }
/*     */     }
/*     */ 
/* 275 */     this._maxSize = maxSize;
/*     */   }
/*     */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     org.apache.log4j.lf5.viewer.configure.MRUFileManager
 * JD-Core Version:    0.6.0
 */