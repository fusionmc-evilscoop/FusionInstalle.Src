/*      */ package org.apache.commons.io;
/*      */ 
/*      */ import java.io.File;
/*      */ import java.io.FileInputStream;
/*      */ import java.io.FileNotFoundException;
/*      */ import java.io.FileOutputStream;
/*      */ import java.io.IOException;
/*      */ import java.io.InputStream;
/*      */ import java.io.OutputStream;
/*      */ import java.net.URL;
/*      */ import java.util.Collection;
/*      */ import java.util.Date;
/*      */ import java.util.Iterator;
/*      */ import java.util.LinkedList;
/*      */ import java.util.List;
/*      */ import java.util.zip.CRC32;
/*      */ import java.util.zip.CheckedInputStream;
/*      */ import java.util.zip.Checksum;
/*      */ import org.apache.commons.io.filefilter.DirectoryFileFilter;
/*      */ import org.apache.commons.io.filefilter.FalseFileFilter;
/*      */ import org.apache.commons.io.filefilter.FileFilterUtils;
/*      */ import org.apache.commons.io.filefilter.IOFileFilter;
/*      */ import org.apache.commons.io.filefilter.SuffixFileFilter;
/*      */ import org.apache.commons.io.filefilter.TrueFileFilter;
/*      */ import org.apache.commons.io.output.NullOutputStream;
/*      */ 
/*      */ public class FileUtils
/*      */ {
/*      */   public static final long ONE_KB = 1024L;
/*      */   public static final long ONE_MB = 1048576L;
/*      */   public static final long ONE_GB = 1073741824L;
/*  106 */   public static final File[] EMPTY_FILE_ARRAY = new File[0];
/*      */ 
/*      */   public static FileInputStream openInputStream(File file)
/*      */     throws IOException
/*      */   {
/*  128 */     if (file.exists()) {
/*  129 */       if (file.isDirectory()) {
/*  130 */         throw new IOException("File '" + file + "' exists but is a directory");
/*      */       }
/*  132 */       if (!file.canRead())
/*  133 */         throw new IOException("File '" + file + "' cannot be read");
/*      */     }
/*      */     else {
/*  136 */       throw new FileNotFoundException("File '" + file + "' does not exist");
/*      */     }
/*  138 */     return new FileInputStream(file);
/*      */   }
/*      */ 
/*      */   public static FileOutputStream openOutputStream(File file)
/*      */     throws IOException
/*      */   {
/*  163 */     if (file.exists()) {
/*  164 */       if (file.isDirectory()) {
/*  165 */         throw new IOException("File '" + file + "' exists but is a directory");
/*      */       }
/*  167 */       if (!file.canWrite())
/*  168 */         throw new IOException("File '" + file + "' cannot be written to");
/*      */     }
/*      */     else {
/*  171 */       File parent = file.getParentFile();
/*  172 */       if ((parent != null) && (!parent.exists()) && 
/*  173 */         (!parent.mkdirs())) {
/*  174 */         throw new IOException("File '" + file + "' could not be created");
/*      */       }
/*      */     }
/*      */ 
/*  178 */     return new FileOutputStream(file);
/*      */   }
/*      */ 
/*      */   public static String byteCountToDisplaySize(long size)
/*      */   {
/*      */     String displaySize;
/*      */     String displaySize;
/*  192 */     if (size / 1073741824L > 0L) {
/*  193 */       displaySize = String.valueOf(size / 1073741824L) + " GB";
/*      */     }
/*      */     else
/*      */     {
/*      */       String displaySize;
/*  194 */       if (size / 1048576L > 0L) {
/*  195 */         displaySize = String.valueOf(size / 1048576L) + " MB";
/*      */       }
/*      */       else
/*      */       {
/*      */         String displaySize;
/*  196 */         if (size / 1024L > 0L)
/*  197 */           displaySize = String.valueOf(size / 1024L) + " KB";
/*      */         else
/*  199 */           displaySize = String.valueOf(size) + " bytes"; 
/*      */       }
/*      */     }
/*  201 */     return displaySize;
/*      */   }
/*      */ 
/*      */   public static void touch(File file)
/*      */     throws IOException
/*      */   {
/*  218 */     if (!file.exists()) {
/*  219 */       OutputStream out = openOutputStream(file);
/*  220 */       IOUtils.closeQuietly(out);
/*      */     }
/*  222 */     boolean success = file.setLastModified(System.currentTimeMillis());
/*  223 */     if (!success)
/*  224 */       throw new IOException("Unable to set the last modification time for " + file);
/*      */   }
/*      */ 
/*      */   public static File[] convertFileCollectionToFileArray(Collection files)
/*      */   {
/*  238 */     return (File[])(File[])files.toArray(new File[files.size()]);
/*      */   }
/*      */ 
/*      */   private static void innerListFiles(Collection files, File directory, IOFileFilter filter)
/*      */   {
/*  252 */     File[] found = directory.listFiles(filter);
/*  253 */     if (found != null)
/*  254 */       for (int i = 0; i < found.length; i++)
/*  255 */         if (found[i].isDirectory())
/*  256 */           innerListFiles(files, found[i], filter);
/*      */         else
/*  258 */           files.add(found[i]);
/*      */   }
/*      */ 
/*      */   public static Collection listFiles(File directory, IOFileFilter fileFilter, IOFileFilter dirFilter)
/*      */   {
/*  291 */     if (!directory.isDirectory()) {
/*  292 */       throw new IllegalArgumentException("Parameter 'directory' is not a directory");
/*      */     }
/*      */ 
/*  295 */     if (fileFilter == null) {
/*  296 */       throw new NullPointerException("Parameter 'fileFilter' is null");
/*      */     }
/*      */ 
/*  300 */     IOFileFilter effFileFilter = FileFilterUtils.andFileFilter(fileFilter, FileFilterUtils.notFileFilter(DirectoryFileFilter.INSTANCE));
/*      */     IOFileFilter effDirFilter;
/*      */     IOFileFilter effDirFilter;
/*  305 */     if (dirFilter == null)
/*  306 */       effDirFilter = FalseFileFilter.INSTANCE;
/*      */     else {
/*  308 */       effDirFilter = FileFilterUtils.andFileFilter(dirFilter, DirectoryFileFilter.INSTANCE);
/*      */     }
/*      */ 
/*  313 */     Collection files = new LinkedList();
/*  314 */     innerListFiles(files, directory, FileFilterUtils.orFileFilter(effFileFilter, effDirFilter));
/*      */ 
/*  316 */     return files;
/*      */   }
/*      */ 
/*      */   public static Iterator iterateFiles(File directory, IOFileFilter fileFilter, IOFileFilter dirFilter)
/*      */   {
/*  338 */     return listFiles(directory, fileFilter, dirFilter).iterator();
/*      */   }
/*      */ 
/*      */   private static String[] toSuffixes(String[] extensions)
/*      */   {
/*  350 */     String[] suffixes = new String[extensions.length];
/*  351 */     for (int i = 0; i < extensions.length; i++) {
/*  352 */       suffixes[i] = ("." + extensions[i]);
/*      */     }
/*  354 */     return suffixes;
/*      */   }
/*      */ 
/*      */   public static Collection listFiles(File directory, String[] extensions, boolean recursive)
/*      */   {
/*      */     IOFileFilter filter;
/*      */     IOFileFilter filter;
/*  371 */     if (extensions == null) {
/*  372 */       filter = TrueFileFilter.INSTANCE;
/*      */     } else {
/*  374 */       String[] suffixes = toSuffixes(extensions);
/*  375 */       filter = new SuffixFileFilter(suffixes);
/*      */     }
/*  377 */     return listFiles(directory, filter, recursive ? TrueFileFilter.INSTANCE : FalseFileFilter.INSTANCE);
/*      */   }
/*      */ 
/*      */   public static Iterator iterateFiles(File directory, String[] extensions, boolean recursive)
/*      */   {
/*  395 */     return listFiles(directory, extensions, recursive).iterator();
/*      */   }
/*      */ 
/*      */   public static boolean contentEquals(File file1, File file2)
/*      */     throws IOException
/*      */   {
/*  415 */     boolean file1Exists = file1.exists();
/*  416 */     if (file1Exists != file2.exists()) {
/*  417 */       return false;
/*      */     }
/*      */ 
/*  420 */     if (!file1Exists)
/*      */     {
/*  422 */       return true;
/*      */     }
/*      */ 
/*  425 */     if ((file1.isDirectory()) || (file2.isDirectory()))
/*      */     {
/*  427 */       throw new IOException("Can't compare directories, only files");
/*      */     }
/*      */ 
/*  430 */     if (file1.length() != file2.length())
/*      */     {
/*  432 */       return false;
/*      */     }
/*      */ 
/*  435 */     if (file1.getCanonicalFile().equals(file2.getCanonicalFile()))
/*      */     {
/*  437 */       return true;
/*      */     }
/*      */ 
/*  440 */     InputStream input1 = null;
/*  441 */     InputStream input2 = null;
/*      */     try {
/*  443 */       input1 = new FileInputStream(file1);
/*  444 */       input2 = new FileInputStream(file2);
/*  445 */       boolean bool1 = IOUtils.contentEquals(input1, input2);
/*      */       return bool1;
/*      */     }
/*      */     finally
/*      */     {
/*  448 */       IOUtils.closeQuietly(input1);
/*  449 */       IOUtils.closeQuietly(input2); } throw localObject;
/*      */   }
/*      */ 
/*      */   public static File toFile(URL url)
/*      */   {
/*  467 */     if ((url == null) || (!url.getProtocol().equals("file"))) {
/*  468 */       return null;
/*      */     }
/*  470 */     String filename = url.getFile().replace('/', File.separatorChar);
/*  471 */     int pos = 0;
/*  472 */     while ((pos = filename.indexOf('%', pos)) >= 0) {
/*  473 */       if (pos + 2 < filename.length()) {
/*  474 */         String hexStr = filename.substring(pos + 1, pos + 3);
/*  475 */         char ch = (char)Integer.parseInt(hexStr, 16);
/*  476 */         filename = filename.substring(0, pos) + ch + filename.substring(pos + 3);
/*      */       }
/*      */     }
/*  479 */     return new File(filename);
/*      */   }
/*      */ 
/*      */   public static File[] toFiles(URL[] urls)
/*      */   {
/*  503 */     if ((urls == null) || (urls.length == 0)) {
/*  504 */       return EMPTY_FILE_ARRAY;
/*      */     }
/*  506 */     File[] files = new File[urls.length];
/*  507 */     for (int i = 0; i < urls.length; i++) {
/*  508 */       URL url = urls[i];
/*  509 */       if (url != null) {
/*  510 */         if (!url.getProtocol().equals("file")) {
/*  511 */           throw new IllegalArgumentException("URL could not be converted to a File: " + url);
/*      */         }
/*      */ 
/*  514 */         files[i] = toFile(url);
/*      */       }
/*      */     }
/*  517 */     return files;
/*      */   }
/*      */ 
/*      */   public static URL[] toURLs(File[] files)
/*      */     throws IOException
/*      */   {
/*  530 */     URL[] urls = new URL[files.length];
/*      */ 
/*  532 */     for (int i = 0; i < urls.length; i++) {
/*  533 */       urls[i] = files[i].toURL();
/*      */     }
/*      */ 
/*  536 */     return urls;
/*      */   }
/*      */ 
/*      */   public static void copyFileToDirectory(File srcFile, File destDir)
/*      */     throws IOException
/*      */   {
/*  557 */     copyFileToDirectory(srcFile, destDir, true);
/*      */   }
/*      */ 
/*      */   public static void copyFileToDirectory(File srcFile, File destDir, boolean preserveFileDate)
/*      */     throws IOException
/*      */   {
/*  580 */     if (destDir == null) {
/*  581 */       throw new NullPointerException("Destination must not be null");
/*      */     }
/*  583 */     if ((destDir.exists()) && (!destDir.isDirectory())) {
/*  584 */       throw new IllegalArgumentException("Destination '" + destDir + "' is not a directory");
/*      */     }
/*  586 */     copyFile(srcFile, new File(destDir, srcFile.getName()), preserveFileDate);
/*      */   }
/*      */ 
/*      */   public static void copyFile(File srcFile, File destFile)
/*      */     throws IOException
/*      */   {
/*  606 */     copyFile(srcFile, destFile, true);
/*      */   }
/*      */ 
/*      */   public static void copyFile(File srcFile, File destFile, boolean preserveFileDate)
/*      */     throws IOException
/*      */   {
/*  629 */     if (srcFile == null) {
/*  630 */       throw new NullPointerException("Source must not be null");
/*      */     }
/*  632 */     if (destFile == null) {
/*  633 */       throw new NullPointerException("Destination must not be null");
/*      */     }
/*  635 */     if (!srcFile.exists()) {
/*  636 */       throw new FileNotFoundException("Source '" + srcFile + "' does not exist");
/*      */     }
/*  638 */     if (srcFile.isDirectory()) {
/*  639 */       throw new IOException("Source '" + srcFile + "' exists but is a directory");
/*      */     }
/*  641 */     if (srcFile.getCanonicalPath().equals(destFile.getCanonicalPath())) {
/*  642 */       throw new IOException("Source '" + srcFile + "' and destination '" + destFile + "' are the same");
/*      */     }
/*  644 */     if ((destFile.getParentFile() != null) && (!destFile.getParentFile().exists()) && 
/*  645 */       (!destFile.getParentFile().mkdirs())) {
/*  646 */       throw new IOException("Destination '" + destFile + "' directory cannot be created");
/*      */     }
/*      */ 
/*  649 */     if ((destFile.exists()) && (!destFile.canWrite())) {
/*  650 */       throw new IOException("Destination '" + destFile + "' exists but is read-only");
/*      */     }
/*  652 */     doCopyFile(srcFile, destFile, preserveFileDate);
/*      */   }
/*      */ 
/*      */   private static void doCopyFile(File srcFile, File destFile, boolean preserveFileDate)
/*      */     throws IOException
/*      */   {
/*  664 */     if ((destFile.exists()) && (destFile.isDirectory())) {
/*  665 */       throw new IOException("Destination '" + destFile + "' exists but is a directory");
/*      */     }
/*      */ 
/*  668 */     FileInputStream input = new FileInputStream(srcFile);
/*      */     try {
/*  670 */       FileOutputStream output = new FileOutputStream(destFile);
/*      */       try {
/*  672 */         IOUtils.copy(input, output);
/*      */       } finally {
/*  674 */         IOUtils.closeQuietly(output);
/*      */       }
/*      */     } finally {
/*  677 */       IOUtils.closeQuietly(input);
/*      */     }
/*      */ 
/*  680 */     if (srcFile.length() != destFile.length()) {
/*  681 */       throw new IOException("Failed to copy full contents from '" + srcFile + "' to '" + destFile + "'");
/*      */     }
/*      */ 
/*  684 */     if (preserveFileDate)
/*  685 */       destFile.setLastModified(srcFile.lastModified());
/*      */   }
/*      */ 
/*      */   public static void copyDirectoryToDirectory(File srcDir, File destDir)
/*      */     throws IOException
/*      */   {
/*  709 */     if (srcDir == null) {
/*  710 */       throw new NullPointerException("Source must not be null");
/*      */     }
/*  712 */     if ((srcDir.exists()) && (!srcDir.isDirectory())) {
/*  713 */       throw new IllegalArgumentException("Source '" + destDir + "' is not a directory");
/*      */     }
/*  715 */     if (destDir == null) {
/*  716 */       throw new NullPointerException("Destination must not be null");
/*      */     }
/*  718 */     if ((destDir.exists()) && (!destDir.isDirectory())) {
/*  719 */       throw new IllegalArgumentException("Destination '" + destDir + "' is not a directory");
/*      */     }
/*  721 */     copyDirectory(srcDir, new File(destDir, srcDir.getName()), true);
/*      */   }
/*      */ 
/*      */   public static void copyDirectory(File srcDir, File destDir)
/*      */     throws IOException
/*      */   {
/*  744 */     copyDirectory(srcDir, destDir, true);
/*      */   }
/*      */ 
/*      */   public static void copyDirectory(File srcDir, File destDir, boolean preserveFileDate)
/*      */     throws IOException
/*      */   {
/*  769 */     if (srcDir == null) {
/*  770 */       throw new NullPointerException("Source must not be null");
/*      */     }
/*  772 */     if (destDir == null) {
/*  773 */       throw new NullPointerException("Destination must not be null");
/*      */     }
/*  775 */     if (!srcDir.exists()) {
/*  776 */       throw new FileNotFoundException("Source '" + srcDir + "' does not exist");
/*      */     }
/*  778 */     if (!srcDir.isDirectory()) {
/*  779 */       throw new IOException("Source '" + srcDir + "' exists but is not a directory");
/*      */     }
/*  781 */     if (srcDir.getCanonicalPath().equals(destDir.getCanonicalPath())) {
/*  782 */       throw new IOException("Source '" + srcDir + "' and destination '" + destDir + "' are the same");
/*      */     }
/*  784 */     doCopyDirectory(srcDir, destDir, preserveFileDate);
/*      */   }
/*      */ 
/*      */   private static void doCopyDirectory(File srcDir, File destDir, boolean preserveFileDate)
/*      */     throws IOException
/*      */   {
/*  797 */     if (destDir.exists()) {
/*  798 */       if (!destDir.isDirectory())
/*  799 */         throw new IOException("Destination '" + destDir + "' exists but is not a directory");
/*      */     }
/*      */     else {
/*  802 */       if (!destDir.mkdirs()) {
/*  803 */         throw new IOException("Destination '" + destDir + "' directory cannot be created");
/*      */       }
/*  805 */       if (preserveFileDate) {
/*  806 */         destDir.setLastModified(srcDir.lastModified());
/*      */       }
/*      */     }
/*  809 */     if (!destDir.canWrite()) {
/*  810 */       throw new IOException("Destination '" + destDir + "' cannot be written to");
/*      */     }
/*      */ 
/*  813 */     File[] files = srcDir.listFiles();
/*  814 */     if (files == null) {
/*  815 */       throw new IOException("Failed to list contents of " + srcDir);
/*      */     }
/*  817 */     for (int i = 0; i < files.length; i++) {
/*  818 */       File copiedFile = new File(destDir, files[i].getName());
/*  819 */       if (files[i].isDirectory())
/*  820 */         doCopyDirectory(files[i], copiedFile, preserveFileDate);
/*      */       else
/*  822 */         doCopyFile(files[i], copiedFile, preserveFileDate);
/*      */     }
/*      */   }
/*      */ 
/*      */   public static void copyURLToFile(URL source, File destination)
/*      */     throws IOException
/*      */   {
/*  844 */     InputStream input = source.openStream();
/*      */     try {
/*  846 */       FileOutputStream output = openOutputStream(destination);
/*      */       try {
/*  848 */         IOUtils.copy(input, output);
/*      */       } finally {
/*  850 */         IOUtils.closeQuietly(output);
/*      */       }
/*      */     } finally {
/*  853 */       IOUtils.closeQuietly(input);
/*      */     }
/*      */   }
/*      */ 
/*      */   public static void deleteDirectory(File directory)
/*      */     throws IOException
/*      */   {
/*  865 */     if (!directory.exists()) {
/*  866 */       return;
/*      */     }
/*      */ 
/*  869 */     cleanDirectory(directory);
/*  870 */     if (!directory.delete()) {
/*  871 */       String message = "Unable to delete directory " + directory + ".";
/*      */ 
/*  873 */       throw new IOException(message);
/*      */     }
/*      */   }
/*      */ 
/*      */   public static void cleanDirectory(File directory)
/*      */     throws IOException
/*      */   {
/*  884 */     if (!directory.exists()) {
/*  885 */       String message = directory + " does not exist";
/*  886 */       throw new IllegalArgumentException(message);
/*      */     }
/*      */ 
/*  889 */     if (!directory.isDirectory()) {
/*  890 */       String message = directory + " is not a directory";
/*  891 */       throw new IllegalArgumentException(message);
/*      */     }
/*      */ 
/*  894 */     File[] files = directory.listFiles();
/*  895 */     if (files == null) {
/*  896 */       throw new IOException("Failed to list contents of " + directory);
/*      */     }
/*      */ 
/*  899 */     IOException exception = null;
/*  900 */     for (int i = 0; i < files.length; i++) {
/*  901 */       File file = files[i];
/*      */       try {
/*  903 */         forceDelete(file);
/*      */       } catch (IOException ioe) {
/*  905 */         exception = ioe;
/*      */       }
/*      */     }
/*      */ 
/*  909 */     if (null != exception)
/*  910 */       throw exception;
/*      */   }
/*      */ 
/*      */   public static boolean waitFor(File file, int seconds)
/*      */   {
/*  927 */     int timeout = 0;
/*  928 */     int tick = 0;
/*      */     while (true) if (!file.exists()) {
/*  930 */         if (tick++ >= 10) {
/*  931 */           tick = 0;
/*  932 */           if (timeout++ > seconds)
/*  933 */             return false;
/*      */         }
/*      */         try
/*      */         {
/*  937 */           Thread.sleep(100L);
/*      */         }
/*      */         catch (InterruptedException ignore)
/*      */         {
/*      */         }
/*      */         catch (Exception ex) {
/*      */         }
/*      */       } return true;
/*      */   }
/*      */ 
/*      */   public static String readFileToString(File file, String encoding)
/*      */     throws IOException
/*      */   {
/*  959 */     InputStream in = null;
/*      */     try {
/*  961 */       in = openInputStream(file);
/*  962 */       String str = IOUtils.toString(in, encoding);
/*      */       return str; } finally { IOUtils.closeQuietly(in); } throw localObject;
/*      */   }
/*      */ 
/*      */   public static String readFileToString(File file)
/*      */     throws IOException
/*      */   {
/*  979 */     return readFileToString(file, null);
/*      */   }
/*      */ 
/*      */   public static byte[] readFileToByteArray(File file)
/*      */     throws IOException
/*      */   {
/*  992 */     InputStream in = null;
/*      */     try {
/*  994 */       in = openInputStream(file);
/*  995 */       byte[] arrayOfByte = IOUtils.toByteArray(in);
/*      */       return arrayOfByte; } finally { IOUtils.closeQuietly(in); } throw localObject;
/*      */   }
/*      */ 
/*      */   public static List readLines(File file, String encoding)
/*      */     throws IOException
/*      */   {
/* 1013 */     InputStream in = null;
/*      */     try {
/* 1015 */       in = openInputStream(file);
/* 1016 */       List localList = IOUtils.readLines(in, encoding);
/*      */       return localList; } finally { IOUtils.closeQuietly(in); } throw localObject;
/*      */   }
/*      */ 
/*      */   public static List readLines(File file)
/*      */     throws IOException
/*      */   {
/* 1032 */     return readLines(file, null);
/*      */   }
/*      */ 
/*      */   public static LineIterator lineIterator(File file, String encoding)
/*      */     throws IOException
/*      */   {
/* 1067 */     InputStream in = null;
/*      */     try {
/* 1069 */       in = openInputStream(file);
/* 1070 */       return IOUtils.lineIterator(in, encoding);
/*      */     } catch (IOException ex) {
/* 1072 */       IOUtils.closeQuietly(in);
/* 1073 */       throw ex;
/*      */     } catch (RuntimeException ex) {
/* 1075 */       IOUtils.closeQuietly(in);
/* 1076 */     }throw ex;
/*      */   }
/*      */ 
/*      */   public static LineIterator lineIterator(File file)
/*      */     throws IOException
/*      */   {
/* 1090 */     return lineIterator(file, null);
/*      */   }
/*      */ 
/*      */   public static void writeStringToFile(File file, String data, String encoding)
/*      */     throws IOException
/*      */   {
/* 1107 */     OutputStream out = null;
/*      */     try {
/* 1109 */       out = openOutputStream(file);
/* 1110 */       IOUtils.write(data, out, encoding);
/*      */     } finally {
/* 1112 */       IOUtils.closeQuietly(out);
/*      */     }
/*      */   }
/*      */ 
/*      */   public static void writeStringToFile(File file, String data)
/*      */     throws IOException
/*      */   {
/* 1124 */     writeStringToFile(file, data, null);
/*      */   }
/*      */ 
/*      */   public static void writeByteArrayToFile(File file, byte[] data)
/*      */     throws IOException
/*      */   {
/* 1139 */     OutputStream out = null;
/*      */     try {
/* 1141 */       out = openOutputStream(file);
/* 1142 */       out.write(data);
/*      */     } finally {
/* 1144 */       IOUtils.closeQuietly(out);
/*      */     }
/*      */   }
/*      */ 
/*      */   public static void writeLines(File file, String encoding, Collection lines)
/*      */     throws IOException
/*      */   {
/* 1164 */     writeLines(file, encoding, lines, null);
/*      */   }
/*      */ 
/*      */   public static void writeLines(File file, Collection lines)
/*      */     throws IOException
/*      */   {
/* 1178 */     writeLines(file, null, lines, null);
/*      */   }
/*      */ 
/*      */   public static void writeLines(File file, String encoding, Collection lines, String lineEnding)
/*      */     throws IOException
/*      */   {
/* 1198 */     OutputStream out = null;
/*      */     try {
/* 1200 */       out = openOutputStream(file);
/* 1201 */       IOUtils.writeLines(lines, lineEnding, out, encoding);
/*      */     } finally {
/* 1203 */       IOUtils.closeQuietly(out);
/*      */     }
/*      */   }
/*      */ 
/*      */   public static void writeLines(File file, Collection lines, String lineEnding)
/*      */     throws IOException
/*      */   {
/* 1219 */     writeLines(file, null, lines, lineEnding);
/*      */   }
/*      */ 
/*      */   public static void forceDelete(File file)
/*      */     throws IOException
/*      */   {
/* 1238 */     if (file.isDirectory()) {
/* 1239 */       deleteDirectory(file);
/*      */     } else {
/* 1241 */       if (!file.exists()) {
/* 1242 */         throw new FileNotFoundException("File does not exist: " + file);
/*      */       }
/* 1244 */       if (!file.delete()) {
/* 1245 */         String message = "Unable to delete file: " + file;
/*      */ 
/* 1247 */         throw new IOException(message);
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   public static void forceDeleteOnExit(File file)
/*      */     throws IOException
/*      */   {
/* 1261 */     if (file.isDirectory())
/* 1262 */       deleteDirectoryOnExit(file);
/*      */     else
/* 1264 */       file.deleteOnExit();
/*      */   }
/*      */ 
/*      */   private static void deleteDirectoryOnExit(File directory)
/*      */     throws IOException
/*      */   {
/* 1276 */     if (!directory.exists()) {
/* 1277 */       return;
/*      */     }
/*      */ 
/* 1280 */     cleanDirectoryOnExit(directory);
/* 1281 */     directory.deleteOnExit();
/*      */   }
/*      */ 
/*      */   private static void cleanDirectoryOnExit(File directory)
/*      */     throws IOException
/*      */   {
/* 1292 */     if (!directory.exists()) {
/* 1293 */       String message = directory + " does not exist";
/* 1294 */       throw new IllegalArgumentException(message);
/*      */     }
/*      */ 
/* 1297 */     if (!directory.isDirectory()) {
/* 1298 */       String message = directory + " is not a directory";
/* 1299 */       throw new IllegalArgumentException(message);
/*      */     }
/*      */ 
/* 1302 */     File[] files = directory.listFiles();
/* 1303 */     if (files == null) {
/* 1304 */       throw new IOException("Failed to list contents of " + directory);
/*      */     }
/*      */ 
/* 1307 */     IOException exception = null;
/* 1308 */     for (int i = 0; i < files.length; i++) {
/* 1309 */       File file = files[i];
/*      */       try {
/* 1311 */         forceDeleteOnExit(file);
/*      */       } catch (IOException ioe) {
/* 1313 */         exception = ioe;
/*      */       }
/*      */     }
/*      */ 
/* 1317 */     if (null != exception)
/* 1318 */       throw exception;
/*      */   }
/*      */ 
/*      */   public static void forceMkdir(File directory)
/*      */     throws IOException
/*      */   {
/* 1332 */     if (directory.exists()) {
/* 1333 */       if (directory.isFile()) {
/* 1334 */         String message = "File " + directory + " exists and is " + "not a directory. Unable to create directory.";
/*      */ 
/* 1339 */         throw new IOException(message);
/*      */       }
/*      */     }
/* 1342 */     else if (!directory.mkdirs()) {
/* 1343 */       String message = "Unable to create directory " + directory;
/*      */ 
/* 1345 */       throw new IOException(message);
/*      */     }
/*      */   }
/*      */ 
/*      */   public static long sizeOfDirectory(File directory)
/*      */   {
/* 1359 */     if (!directory.exists()) {
/* 1360 */       String message = directory + " does not exist";
/* 1361 */       throw new IllegalArgumentException(message);
/*      */     }
/*      */ 
/* 1364 */     if (!directory.isDirectory()) {
/* 1365 */       String message = directory + " is not a directory";
/* 1366 */       throw new IllegalArgumentException(message);
/*      */     }
/*      */ 
/* 1369 */     long size = 0L;
/*      */ 
/* 1371 */     File[] files = directory.listFiles();
/* 1372 */     if (files == null) {
/* 1373 */       return 0L;
/*      */     }
/* 1375 */     for (int i = 0; i < files.length; i++) {
/* 1376 */       File file = files[i];
/*      */ 
/* 1378 */       if (file.isDirectory())
/* 1379 */         size += sizeOfDirectory(file);
/*      */       else {
/* 1381 */         size += file.length();
/*      */       }
/*      */     }
/*      */ 
/* 1385 */     return size;
/*      */   }
/*      */ 
/*      */   public static boolean isFileNewer(File file, File reference)
/*      */   {
/* 1403 */     if (reference == null) {
/* 1404 */       throw new IllegalArgumentException("No specified reference file");
/*      */     }
/* 1406 */     if (!reference.exists()) {
/* 1407 */       throw new IllegalArgumentException("The reference file '" + file + "' doesn't exist");
/*      */     }
/*      */ 
/* 1410 */     return isFileNewer(file, reference.lastModified());
/*      */   }
/*      */ 
/*      */   public static boolean isFileNewer(File file, Date date)
/*      */   {
/* 1426 */     if (date == null) {
/* 1427 */       throw new IllegalArgumentException("No specified date");
/*      */     }
/* 1429 */     return isFileNewer(file, date.getTime());
/*      */   }
/*      */ 
/*      */   public static boolean isFileNewer(File file, long timeMillis)
/*      */   {
/* 1445 */     if (file == null) {
/* 1446 */       throw new IllegalArgumentException("No specified file");
/*      */     }
/* 1448 */     if (!file.exists()) {
/* 1449 */       return false;
/*      */     }
/* 1451 */     return file.lastModified() > timeMillis;
/*      */   }
/*      */ 
/*      */   public static boolean isFileOlder(File file, File reference)
/*      */   {
/* 1470 */     if (reference == null) {
/* 1471 */       throw new IllegalArgumentException("No specified reference file");
/*      */     }
/* 1473 */     if (!reference.exists()) {
/* 1474 */       throw new IllegalArgumentException("The reference file '" + file + "' doesn't exist");
/*      */     }
/*      */ 
/* 1477 */     return isFileOlder(file, reference.lastModified());
/*      */   }
/*      */ 
/*      */   public static boolean isFileOlder(File file, Date date)
/*      */   {
/* 1493 */     if (date == null) {
/* 1494 */       throw new IllegalArgumentException("No specified date");
/*      */     }
/* 1496 */     return isFileOlder(file, date.getTime());
/*      */   }
/*      */ 
/*      */   public static boolean isFileOlder(File file, long timeMillis)
/*      */   {
/* 1512 */     if (file == null) {
/* 1513 */       throw new IllegalArgumentException("No specified file");
/*      */     }
/* 1515 */     if (!file.exists()) {
/* 1516 */       return false;
/*      */     }
/* 1518 */     return file.lastModified() < timeMillis;
/*      */   }
/*      */ 
/*      */   public static long checksumCRC32(File file)
/*      */     throws IOException
/*      */   {
/* 1534 */     CRC32 crc = new CRC32();
/* 1535 */     checksum(file, crc);
/* 1536 */     return crc.getValue();
/*      */   }
/*      */ 
/*      */   public static Checksum checksum(File file, Checksum checksum)
/*      */     throws IOException
/*      */   {
/* 1557 */     if (file.isDirectory()) {
/* 1558 */       throw new IllegalArgumentException("Checksums can't be computed on directories");
/*      */     }
/* 1560 */     InputStream in = null;
/*      */     try {
/* 1562 */       in = new CheckedInputStream(new FileInputStream(file), checksum);
/* 1563 */       IOUtils.copy(in, new NullOutputStream());
/*      */     } finally {
/* 1565 */       IOUtils.closeQuietly(in);
/*      */     }
/* 1567 */     return checksum;
/*      */   }
/*      */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     org.apache.commons.io.FileUtils
 * JD-Core Version:    0.6.0
 */