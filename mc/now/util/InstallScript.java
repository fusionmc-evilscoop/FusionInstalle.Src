/*     */ package mc.now.util;
/*     */ 
/*     */ import java.io.File;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.FileOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.util.Enumeration;
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
/*     */ import java.util.Queue;
/*     */ import java.util.Random;
/*     */ import java.util.jar.JarEntry;
/*     */ import java.util.jar.JarFile;
/*     */ import java.util.jar.JarOutputStream;
/*     */ import javax.swing.JProgressBar;
/*     */ import javax.swing.JTextArea;
/*     */ import org.apache.commons.codec.digest.DigestUtils;
/*     */ import org.apache.commons.io.FileUtils;
/*     */ import org.apache.commons.io.FilenameUtils;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ public class InstallScript
/*     */ {
/*  28 */   public static final String MODS_FOLDER = FilenameUtils.concat(InstallerProperties.getInstallerDir(), "mods/");
/*  29 */   public static final String REQUIRED_MODS_FOLDER = FilenameUtils.concat(MODS_FOLDER, "required/");
/*  30 */   public static final String EXTRA_MODS_FOLDER = FilenameUtils.concat(MODS_FOLDER, "extra/");
/*     */ 
/*  32 */   private static final Logger LOGGER = Logger.getLogger(InstallScript.class);
/*     */ 
/* 243 */   private static final String[] otherThingsToBackup = { "millenaire" };
/*     */ 
/*     */   public static void repackMCJar(File tmp, File mcjar)
/*     */     throws IOException
/*     */   {
/*  35 */     byte[] dat = new byte[4096];
/*     */ 
/*  37 */     JarOutputStream jarout = new JarOutputStream(FileUtils.openOutputStream(mcjar));
/*     */ 
/*  39 */     Queue queue = new LinkedList();
/*  40 */     for (File f : tmp.listFiles()) {
/*  41 */       queue.add(f);
/*     */     }
/*     */ 
/*  44 */     while (!queue.isEmpty()) {
/*  45 */       File f = (File)queue.poll();
/*  46 */       if (f.isDirectory()) {
/*  47 */         for (File child : f.listFiles())
/*  48 */           queue.add(child);
/*     */       }
/*     */       else
/*     */       {
/*  52 */         String name = f.getPath().substring(tmp.getPath().length() + 1);
/*     */ 
/*  54 */         name = name.replace("\\", "/");
/*  55 */         if ((f.isDirectory()) && (!name.endsWith("/"))) {
/*  56 */           name = name + "/";
/*     */         }
/*  58 */         JarEntry entry = new JarEntry(name);
/*  59 */         jarout.putNextEntry(entry);
/*     */ 
/*  61 */         FileInputStream in = new FileInputStream(f);
/*  62 */         int len = -1;
/*  63 */         while ((len = in.read(dat)) > 0) {
/*  64 */           jarout.write(dat, 0, len);
/*     */         }
/*  66 */         in.close();
/*     */       }
/*  68 */       jarout.closeEntry();
/*     */     }
/*  70 */     jarout.close();
/*     */   }
/*     */ 
/*     */   public static void unpackMCJar(File tmpdir, File mcjar) throws IOException
/*     */   {
/*  75 */     byte[] dat = new byte[4096];
/*  76 */     JarFile jar = new JarFile(mcjar);
/*  77 */     Enumeration entries = jar.entries();
/*  78 */     while (entries.hasMoreElements()) {
/*  79 */       JarEntry entry = (JarEntry)entries.nextElement();
/*  80 */       String name = entry.getName();
/*  81 */       if (name.startsWith("META-INF")) {
/*     */         continue;
/*     */       }
/*  84 */       InputStream in = jar.getInputStream(entry);
/*  85 */       File dest = new File(FilenameUtils.concat(tmpdir.getPath(), name));
/*  86 */       if (entry.isDirectory())
/*     */       {
/*  88 */         LOGGER.warn("Found a directory while iterating over jar.");
/*  89 */         dest.mkdirs();
/*  90 */       } else if ((!dest.getParentFile().exists()) && 
/*  91 */         (!dest.getParentFile().mkdirs())) {
/*  92 */         throw new IOException("Couldn't create directory for " + name);
/*     */       }
/*     */ 
/*  95 */       FileOutputStream out = new FileOutputStream(dest);
/*  96 */       int len = -1;
/*  97 */       while ((len = in.read(dat)) > 0) {
/*  98 */         out.write(dat, 0, len);
/*     */       }
/* 100 */       out.flush();
/* 101 */       out.close();
/* 102 */       in.close();
/*     */     }
/*     */   }
/*     */ 
/*     */   private static File getTempDir() throws IOException {
/* 107 */     Random rand = new Random();
/* 108 */     String hex = Integer.toHexString(rand.nextInt(2147483647));
/* 109 */     File tmp = new File(FilenameUtils.concat(InstallerProperties.getInstallerDir(), hex + "/"));
/* 110 */     int t = 0;
/* 111 */     while ((tmp.exists()) && (t < 10)) {
/* 112 */       hex = Integer.toHexString(rand.nextInt(2147483647));
/* 113 */       tmp = new File(FilenameUtils.normalize("./" + hex + "/"));
/* 114 */       t++;
/*     */     }
/* 116 */     if (tmp.exists()) {
/* 117 */       throw new IOException("Error creating temporary folder. Too many failures.");
/*     */     }
/* 119 */     return tmp;
/*     */   }
/*     */ 
/*     */   public static String preInstallCheck()
/*     */   {
/*     */     try {
/* 125 */       InputStream mcJarIn = new FileInputStream(PlatformUtil.getMinecraftJar());
/* 126 */       String digest = DigestUtils.md5Hex(mcJarIn);
/* 127 */       mcJarIn.close();
/* 128 */       boolean jarValid = InstallerProperties.getMinecraftJarMD5().equalsIgnoreCase(digest);
/* 129 */       File modsDir = new File(PlatformUtil.getMinecraftModsFolder());
/* 130 */       boolean noMods = (!modsDir.exists()) || (modsDir.listFiles().length == 0);
/* 131 */       String msg = null;
/* 132 */       if (!jarValid) {
/* 133 */         msg = String.format("Your minecraft.jar has been modified or is not the correct version (%s).", new Object[] { InstallerProperties.getMinecraftVersion() });
/*     */       }
/* 135 */       if (!noMods) {
/* 136 */         msg = (msg == null ? "" : new StringBuilder().append(msg).append("\n").toString()) + "You already have mods installed to the minecraft mods folder.";
/*     */       }
/* 138 */       return msg;
/*     */     } catch (Exception e) {
/* 140 */       LOGGER.error("Pre-installation check error", e);
/* 141 */     }return "There was an error verifying your minecraft installation. " + e.getMessage();
/*     */   }
/*     */ 
/*     */   public static void guiInstall(List<String> mods, JTextArea text, JProgressBar progressBar)
/*     */   {
/*     */     try
/*     */     {
/* 149 */       createBackup();
/*     */     } catch (IOException e) {
/* 151 */       text.append("Failed to create backup copies of minecraft.jar and mods folder");
/* 152 */       LOGGER.error("Failed to create backup copies of minecraft.jar and mods folder", e);
/* 153 */       return;
/*     */     }
/*     */     File tmp;
/*     */     try {
/* 158 */       tmp = getTempDir();
/*     */     } catch (IOException e) {
/* 160 */       text.append("Error creating temp directory!");
/* 161 */       LOGGER.error("Install Error", e);
/* 162 */       return;
/*     */     }
/* 164 */     if (!tmp.mkdirs()) {
/* 165 */       text.append("Error creating temp directory!");
/* 166 */       return;
/*     */     }
/*     */ 
/* 169 */     File mcDir = new File(PlatformUtil.getMinecraftFolder());
/* 170 */     File mcJar = new File(PlatformUtil.getMinecraftJar());
/* 171 */     File reqDir = new File(REQUIRED_MODS_FOLDER);
/*     */ 
/* 173 */     int reqMods = 0;
/* 174 */     for (File f : reqDir.listFiles()) {
/* 175 */       if (f.isDirectory()) {
/* 176 */         reqMods++;
/*     */       }
/*     */     }
/*     */ 
/* 180 */     int baseTasks = 3;
/* 181 */     int taskSize = reqMods + mods.size() + baseTasks;
/*     */ 
/* 183 */     progressBar.setMinimum(0);
/* 184 */     progressBar.setMaximum(taskSize);
/* 185 */     int task = 1;
/*     */     try
/*     */     {
/* 188 */       text.append("Unpacking minecraft.jar\n");
/* 189 */       unpackMCJar(tmp, mcJar);
/* 190 */       progressBar.setValue(task++);
/*     */ 
/* 192 */       text.append("Installing Core mods\n");
/*     */ 
/* 194 */       for (File mod : reqDir.listFiles()) {
/* 195 */         if (!mod.isDirectory()) {
/*     */           continue;
/*     */         }
/* 198 */         String name = mod.getName();
/* 199 */         text.append("...Installing " + name + "\n");
/* 200 */         installMod(mod, tmp, mcDir);
/* 201 */         progressBar.setValue(task++);
/*     */       }
/*     */ 
/* 204 */       if (!mods.isEmpty()) {
/* 205 */         text.append("Installing Extra mods\n");
/*     */ 
/* 207 */         for (String name : mods) {
/* 208 */           File mod = new File(FilenameUtils.normalize(FilenameUtils.concat(EXTRA_MODS_FOLDER, name)));
/* 209 */           text.append("...Installing " + name + "\n");
/* 210 */           installMod(mod, tmp, mcDir);
/* 211 */           progressBar.setValue(task++);
/*     */         }
/*     */       }
/*     */ 
/* 215 */       text.append("Repacking minecraft.jar\n");
/* 216 */       repackMCJar(tmp, mcJar);
/* 217 */       progressBar.setValue(task++);
/*     */     } catch (Exception e) {
/* 219 */       text.append("!!!Error installing mods!!!");
/* 220 */       LOGGER.error("Installation error", e);
/*     */       try {
/* 222 */         restoreBackup();
/*     */       } catch (IOException ioe) {
/* 224 */         text.append("Error while restoring backup files minecraft.jar.backup and mods_backup folder\n!");
/* 225 */         LOGGER.error("Error while restoring backup files minecraft.jar.backup and mods_backup folder!", ioe);
/*     */       }
/*     */     }
/*     */ 
/* 229 */     text.append("Deleting temporary files\n");
/*     */     try {
/* 231 */       FileUtils.deleteDirectory(tmp);
/* 232 */       progressBar.setValue(task++);
/*     */     } catch (IOException e) {
/* 234 */       text.append("Error deleting temporary files!\n");
/* 235 */       LOGGER.error("Install Error", e);
/* 236 */       return;
/*     */     }
/* 238 */     text.append("Finished!");
/*     */   }
/*     */ 
/*     */   private static void createBackup()
/*     */     throws IOException
/*     */   {
/* 247 */     FileUtils.copyFile(new File(PlatformUtil.getMinecraftJar()), new File(PlatformUtil.getMinecraftJar() + ".backup"));
/* 248 */     File mods = new File(PlatformUtil.getMinecraftModsFolder());
/* 249 */     File modsBackup = new File(PlatformUtil.getMinecraftModsFolder() + "_backup/");
/* 250 */     if (modsBackup.exists()) {
/* 251 */       FileUtils.deleteDirectory(modsBackup);
/*     */     }
/* 253 */     if (mods.exists()) {
/* 254 */       FileUtils.copyDirectory(mods, modsBackup);
/*     */     }
/*     */ 
/* 257 */     for (String name : otherThingsToBackup) {
/* 258 */       String fname = FilenameUtils.normalize(FilenameUtils.concat(PlatformUtil.getMinecraftFolder(), name));
/* 259 */       String fnameBackup = fname + "_backup";
/* 260 */       File f = new File(fname);
/* 261 */       File backup = new File(fnameBackup);
/* 262 */       if (backup.exists()) {
/* 263 */         FileUtils.deleteDirectory(backup);
/*     */       }
/* 265 */       if (f.exists())
/* 266 */         FileUtils.copyDirectory(f, backup);
/*     */     }
/*     */   }
/*     */ 
/*     */   private static void restoreBackup()
/*     */     throws IOException
/*     */   {
/* 273 */     FileUtils.copyFile(new File(PlatformUtil.getMinecraftJar() + ".backup"), new File(PlatformUtil.getMinecraftJar()));
/* 274 */     File mods = new File(PlatformUtil.getMinecraftModsFolder());
/* 275 */     File modsBackup = new File(PlatformUtil.getMinecraftModsFolder() + "_backup");
/* 276 */     if (modsBackup.exists()) {
/* 277 */       FileUtils.deleteDirectory(mods);
/* 278 */       FileUtils.copyDirectory(modsBackup, mods);
/*     */     }
/* 280 */     for (String name : otherThingsToBackup) {
/* 281 */       String fname = FilenameUtils.normalize(FilenameUtils.concat(PlatformUtil.getMinecraftFolder(), name));
/* 282 */       String fnameBackup = fname + "_backup";
/* 283 */       File f = new File(fname);
/* 284 */       File backup = new File(fnameBackup);
/* 285 */       if (backup.exists())
/* 286 */         FileUtils.copyDirectory(backup, f);
/*     */     }
/*     */   }
/*     */ 
/*     */   private static void installMod(File modDir, File jarDir, File mcDir) throws IOException
/*     */   {
/* 292 */     for (File dir : modDir.listFiles()) {
/* 293 */       if (!dir.isDirectory()) {
/*     */         continue;
/*     */       }
/* 296 */       if (dir.getName().equals("jar"))
/* 297 */         FileUtils.copyDirectory(dir, jarDir);
/* 298 */       else if (dir.getName().equals("resources"))
/* 299 */         FileUtils.copyDirectory(dir, mcDir);
/*     */     }
/*     */   }
/*     */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     mc.now.util.InstallScript
 * JD-Core Version:    0.6.0
 */