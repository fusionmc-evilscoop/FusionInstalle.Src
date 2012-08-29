/*      */ package org.apache.commons.io;
/*      */ 
/*      */ import java.io.File;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Collection;
/*      */ import java.util.Iterator;
/*      */ import java.util.Stack;
/*      */ 
/*      */ public class FilenameUtils
/*      */ {
/*      */   private static final char EXTENSION_SEPARATOR = '.';
/*      */   private static final char UNIX_SEPARATOR = '/';
/*      */   private static final char WINDOWS_SEPARATOR = '\\';
/*  113 */   private static final char SYSTEM_SEPARATOR = File.separatorChar;
/*      */   private static final char OTHER_SEPARATOR;
/*      */ 
/*      */   static boolean isSystemWindows()
/*      */   {
/*  141 */     return SYSTEM_SEPARATOR == '\\';
/*      */   }
/*      */ 
/*      */   private static boolean isSeparator(char ch)
/*      */   {
/*  152 */     return (ch == '/') || (ch == '\\');
/*      */   }
/*      */ 
/*      */   public static String normalize(String filename)
/*      */   {
/*  197 */     return doNormalize(filename, true);
/*      */   }
/*      */ 
/*      */   public static String normalizeNoEndSeparator(String filename)
/*      */   {
/*  243 */     return doNormalize(filename, false);
/*      */   }
/*      */ 
/*      */   private static String doNormalize(String filename, boolean keepSeparator)
/*      */   {
/*  254 */     if (filename == null) {
/*  255 */       return null;
/*      */     }
/*  257 */     int size = filename.length();
/*  258 */     if (size == 0) {
/*  259 */       return filename;
/*      */     }
/*  261 */     int prefix = getPrefixLength(filename);
/*  262 */     if (prefix < 0) {
/*  263 */       return null;
/*      */     }
/*      */ 
/*  266 */     char[] array = new char[size + 2];
/*  267 */     filename.getChars(0, filename.length(), array, 0);
/*      */ 
/*  270 */     for (int i = 0; i < array.length; i++) {
/*  271 */       if (array[i] == OTHER_SEPARATOR) {
/*  272 */         array[i] = SYSTEM_SEPARATOR;
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/*  277 */     boolean lastIsDirectory = true;
/*  278 */     if (array[(size - 1)] != SYSTEM_SEPARATOR) {
/*  279 */       array[(size++)] = SYSTEM_SEPARATOR;
/*  280 */       lastIsDirectory = false;
/*      */     }
/*      */ 
/*  284 */     for (int i = prefix + 1; i < size; i++) {
/*  285 */       if ((array[i] == SYSTEM_SEPARATOR) && (array[(i - 1)] == SYSTEM_SEPARATOR)) {
/*  286 */         System.arraycopy(array, i, array, i - 1, size - i);
/*  287 */         size--;
/*  288 */         i--;
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/*  293 */     for (int i = prefix + 1; i < size; i++) {
/*  294 */       if ((array[i] != SYSTEM_SEPARATOR) || (array[(i - 1)] != '.') || ((i != prefix + 1) && (array[(i - 2)] != SYSTEM_SEPARATOR)))
/*      */         continue;
/*  296 */       if (i == size - 1) {
/*  297 */         lastIsDirectory = true;
/*      */       }
/*  299 */       System.arraycopy(array, i + 1, array, i - 1, size - i);
/*  300 */       size -= 2;
/*  301 */       i--;
/*      */     }
/*      */ 
/*  307 */     for (int i = prefix + 2; i < size; i++) {
/*  308 */       if ((array[i] != SYSTEM_SEPARATOR) || (array[(i - 1)] != '.') || (array[(i - 2)] != '.') || ((i != prefix + 2) && (array[(i - 3)] != SYSTEM_SEPARATOR)))
/*      */         continue;
/*  310 */       if (i == prefix + 2) {
/*  311 */         return null;
/*      */       }
/*  313 */       if (i == size - 1) {
/*  314 */         lastIsDirectory = true;
/*      */       }
/*      */ 
/*  317 */       int j = i - 4;
/*      */       while (true) if (j >= prefix) {
/*  318 */           if (array[j] == SYSTEM_SEPARATOR)
/*      */           {
/*  320 */             System.arraycopy(array, i + 1, array, j + 1, size - i);
/*  321 */             size -= i - j;
/*  322 */             i = j + 1;
/*      */           }
/*      */           else
/*      */           {
/*  317 */             j--; continue;
/*      */           }
/*      */ 
/*      */         }
/*      */         else
/*      */         {
/*  327 */           System.arraycopy(array, i + 1, array, prefix, size - i);
/*  328 */           size -= i + 1 - prefix;
/*  329 */           i = prefix + 1;
/*      */         }
/*      */     }
/*      */ 
/*  333 */     if (size <= 0) {
/*  334 */       return "";
/*      */     }
/*  336 */     if (size <= prefix) {
/*  337 */       return new String(array, 0, size);
/*      */     }
/*  339 */     if ((lastIsDirectory) && (keepSeparator)) {
/*  340 */       return new String(array, 0, size);
/*      */     }
/*  342 */     return new String(array, 0, size - 1);
/*      */   }
/*      */ 
/*      */   public static String concat(String basePath, String fullFilenameToAdd)
/*      */   {
/*  387 */     int prefix = getPrefixLength(fullFilenameToAdd);
/*  388 */     if (prefix < 0) {
/*  389 */       return null;
/*      */     }
/*  391 */     if (prefix > 0) {
/*  392 */       return normalize(fullFilenameToAdd);
/*      */     }
/*  394 */     if (basePath == null) {
/*  395 */       return null;
/*      */     }
/*  397 */     int len = basePath.length();
/*  398 */     if (len == 0) {
/*  399 */       return normalize(fullFilenameToAdd);
/*      */     }
/*  401 */     char ch = basePath.charAt(len - 1);
/*  402 */     if (isSeparator(ch)) {
/*  403 */       return normalize(basePath + fullFilenameToAdd);
/*      */     }
/*  405 */     return normalize(basePath + '/' + fullFilenameToAdd);
/*      */   }
/*      */ 
/*      */   public static String separatorsToUnix(String path)
/*      */   {
/*  417 */     if ((path == null) || (path.indexOf('\\') == -1)) {
/*  418 */       return path;
/*      */     }
/*  420 */     return path.replace('\\', '/');
/*      */   }
/*      */ 
/*      */   public static String separatorsToWindows(String path)
/*      */   {
/*  430 */     if ((path == null) || (path.indexOf('/') == -1)) {
/*  431 */       return path;
/*      */     }
/*  433 */     return path.replace('/', '\\');
/*      */   }
/*      */ 
/*      */   public static String separatorsToSystem(String path)
/*      */   {
/*  443 */     if (path == null) {
/*  444 */       return null;
/*      */     }
/*  446 */     if (isSystemWindows()) {
/*  447 */       return separatorsToWindows(path);
/*      */     }
/*  449 */     return separatorsToUnix(path);
/*      */   }
/*      */ 
/*      */   public static int getPrefixLength(String filename)
/*      */   {
/*  486 */     if (filename == null) {
/*  487 */       return -1;
/*      */     }
/*  489 */     int len = filename.length();
/*  490 */     if (len == 0) {
/*  491 */       return 0;
/*      */     }
/*  493 */     char ch0 = filename.charAt(0);
/*  494 */     if (ch0 == ':') {
/*  495 */       return -1;
/*      */     }
/*  497 */     if (len == 1) {
/*  498 */       if (ch0 == '~') {
/*  499 */         return 2;
/*      */       }
/*  501 */       return isSeparator(ch0) ? 1 : 0;
/*      */     }
/*  503 */     if (ch0 == '~') {
/*  504 */       int posUnix = filename.indexOf('/', 1);
/*  505 */       int posWin = filename.indexOf('\\', 1);
/*  506 */       if ((posUnix == -1) && (posWin == -1)) {
/*  507 */         return len + 1;
/*      */       }
/*  509 */       posUnix = posUnix == -1 ? posWin : posUnix;
/*  510 */       posWin = posWin == -1 ? posUnix : posWin;
/*  511 */       return Math.min(posUnix, posWin) + 1;
/*      */     }
/*  513 */     char ch1 = filename.charAt(1);
/*  514 */     if (ch1 == ':') {
/*  515 */       ch0 = Character.toUpperCase(ch0);
/*  516 */       if ((ch0 >= 'A') && (ch0 <= 'Z')) {
/*  517 */         if ((len == 2) || (!isSeparator(filename.charAt(2)))) {
/*  518 */           return 2;
/*      */         }
/*  520 */         return 3;
/*      */       }
/*  522 */       return -1;
/*      */     }
/*  524 */     if ((isSeparator(ch0)) && (isSeparator(ch1))) {
/*  525 */       int posUnix = filename.indexOf('/', 2);
/*  526 */       int posWin = filename.indexOf('\\', 2);
/*  527 */       if (((posUnix == -1) && (posWin == -1)) || (posUnix == 2) || (posWin == 2)) {
/*  528 */         return -1;
/*      */       }
/*  530 */       posUnix = posUnix == -1 ? posWin : posUnix;
/*  531 */       posWin = posWin == -1 ? posUnix : posWin;
/*  532 */       return Math.min(posUnix, posWin) + 1;
/*      */     }
/*  534 */     return isSeparator(ch0) ? 1 : 0;
/*      */   }
/*      */ 
/*      */   public static int indexOfLastSeparator(String filename)
/*      */   {
/*  552 */     if (filename == null) {
/*  553 */       return -1;
/*      */     }
/*  555 */     int lastUnixPos = filename.lastIndexOf('/');
/*  556 */     int lastWindowsPos = filename.lastIndexOf('\\');
/*  557 */     return Math.max(lastUnixPos, lastWindowsPos);
/*      */   }
/*      */ 
/*      */   public static int indexOfExtension(String filename)
/*      */   {
/*  574 */     if (filename == null) {
/*  575 */       return -1;
/*      */     }
/*  577 */     int extensionPos = filename.lastIndexOf('.');
/*  578 */     int lastSeparator = indexOfLastSeparator(filename);
/*  579 */     return lastSeparator > extensionPos ? -1 : extensionPos;
/*      */   }
/*      */ 
/*      */   public static String getPrefix(String filename)
/*      */   {
/*  613 */     if (filename == null) {
/*  614 */       return null;
/*      */     }
/*  616 */     int len = getPrefixLength(filename);
/*  617 */     if (len < 0) {
/*  618 */       return null;
/*      */     }
/*  620 */     if (len > filename.length()) {
/*  621 */       return filename + '/';
/*      */     }
/*  623 */     return filename.substring(0, len);
/*      */   }
/*      */ 
/*      */   public static String getPath(String filename)
/*      */   {
/*  649 */     return doGetPath(filename, 1);
/*      */   }
/*      */ 
/*      */   public static String getPathNoEndSeparator(String filename)
/*      */   {
/*  676 */     return doGetPath(filename, 0);
/*      */   }
/*      */ 
/*      */   private static String doGetPath(String filename, int separatorAdd)
/*      */   {
/*  687 */     if (filename == null) {
/*  688 */       return null;
/*      */     }
/*  690 */     int prefix = getPrefixLength(filename);
/*  691 */     if (prefix < 0) {
/*  692 */       return null;
/*      */     }
/*  694 */     int index = indexOfLastSeparator(filename);
/*  695 */     if ((prefix >= filename.length()) || (index < 0)) {
/*  696 */       return "";
/*      */     }
/*  698 */     return filename.substring(prefix, index + separatorAdd);
/*      */   }
/*      */ 
/*      */   public static String getFullPath(String filename)
/*      */   {
/*  727 */     return doGetFullPath(filename, true);
/*      */   }
/*      */ 
/*      */   public static String getFullPathNoEndSeparator(String filename)
/*      */   {
/*  757 */     return doGetFullPath(filename, false);
/*      */   }
/*      */ 
/*      */   private static String doGetFullPath(String filename, boolean includeSeparator)
/*      */   {
/*  768 */     if (filename == null) {
/*  769 */       return null;
/*      */     }
/*  771 */     int prefix = getPrefixLength(filename);
/*  772 */     if (prefix < 0) {
/*  773 */       return null;
/*      */     }
/*  775 */     if (prefix >= filename.length()) {
/*  776 */       if (includeSeparator) {
/*  777 */         return getPrefix(filename);
/*      */       }
/*  779 */       return filename;
/*      */     }
/*      */ 
/*  782 */     int index = indexOfLastSeparator(filename);
/*  783 */     if (index < 0) {
/*  784 */       return filename.substring(0, prefix);
/*      */     }
/*  786 */     int end = index + (includeSeparator ? 1 : 0);
/*  787 */     return filename.substring(0, end);
/*      */   }
/*      */ 
/*      */   public static String getName(String filename)
/*      */   {
/*  808 */     if (filename == null) {
/*  809 */       return null;
/*      */     }
/*  811 */     int index = indexOfLastSeparator(filename);
/*  812 */     return filename.substring(index + 1);
/*      */   }
/*      */ 
/*      */   public static String getBaseName(String filename)
/*      */   {
/*  833 */     return removeExtension(getName(filename));
/*      */   }
/*      */ 
/*      */   public static String getExtension(String filename)
/*      */   {
/*  854 */     if (filename == null) {
/*  855 */       return null;
/*      */     }
/*  857 */     int index = indexOfExtension(filename);
/*  858 */     if (index == -1) {
/*  859 */       return "";
/*      */     }
/*  861 */     return filename.substring(index + 1);
/*      */   }
/*      */ 
/*      */   public static String removeExtension(String filename)
/*      */   {
/*  884 */     if (filename == null) {
/*  885 */       return null;
/*      */     }
/*  887 */     int index = indexOfExtension(filename);
/*  888 */     if (index == -1) {
/*  889 */       return filename;
/*      */     }
/*  891 */     return filename.substring(0, index);
/*      */   }
/*      */ 
/*      */   public static boolean equals(String filename1, String filename2)
/*      */   {
/*  908 */     return equals(filename1, filename2, false, IOCase.SENSITIVE);
/*      */   }
/*      */ 
/*      */   public static boolean equalsOnSystem(String filename1, String filename2)
/*      */   {
/*  923 */     return equals(filename1, filename2, false, IOCase.SYSTEM);
/*      */   }
/*      */ 
/*      */   public static boolean equalsNormalized(String filename1, String filename2)
/*      */   {
/*  939 */     return equals(filename1, filename2, true, IOCase.SENSITIVE);
/*      */   }
/*      */ 
/*      */   public static boolean equalsNormalizedOnSystem(String filename1, String filename2)
/*      */   {
/*  956 */     return equals(filename1, filename2, true, IOCase.SYSTEM);
/*      */   }
/*      */ 
/*      */   public static boolean equals(String filename1, String filename2, boolean normalized, IOCase caseSensitivity)
/*      */   {
/*  974 */     if ((filename1 == null) || (filename2 == null)) {
/*  975 */       return filename1 == filename2;
/*      */     }
/*  977 */     if (normalized) {
/*  978 */       filename1 = normalize(filename1);
/*  979 */       filename2 = normalize(filename2);
/*      */     }
/*  981 */     if (caseSensitivity == null) {
/*  982 */       caseSensitivity = IOCase.SENSITIVE;
/*      */     }
/*  984 */     return caseSensitivity.checkEquals(filename1, filename2);
/*      */   }
/*      */ 
/*      */   public static boolean isExtension(String filename, String extension)
/*      */   {
/* 1000 */     if (filename == null) {
/* 1001 */       return false;
/*      */     }
/* 1003 */     if ((extension == null) || (extension.length() == 0)) {
/* 1004 */       return indexOfExtension(filename) == -1;
/*      */     }
/* 1006 */     String fileExt = getExtension(filename);
/* 1007 */     return fileExt.equals(extension);
/*      */   }
/*      */ 
/*      */   public static boolean isExtension(String filename, String[] extensions)
/*      */   {
/* 1022 */     if (filename == null) {
/* 1023 */       return false;
/*      */     }
/* 1025 */     if ((extensions == null) || (extensions.length == 0)) {
/* 1026 */       return indexOfExtension(filename) == -1;
/*      */     }
/* 1028 */     String fileExt = getExtension(filename);
/* 1029 */     for (int i = 0; i < extensions.length; i++) {
/* 1030 */       if (fileExt.equals(extensions[i])) {
/* 1031 */         return true;
/*      */       }
/*      */     }
/* 1034 */     return false;
/*      */   }
/*      */ 
/*      */   public static boolean isExtension(String filename, Collection extensions)
/*      */   {
/* 1049 */     if (filename == null) {
/* 1050 */       return false;
/*      */     }
/* 1052 */     if ((extensions == null) || (extensions.isEmpty())) {
/* 1053 */       return indexOfExtension(filename) == -1;
/*      */     }
/* 1055 */     String fileExt = getExtension(filename);
/* 1056 */     for (Iterator it = extensions.iterator(); it.hasNext(); ) {
/* 1057 */       if (fileExt.equals(it.next())) {
/* 1058 */         return true;
/*      */       }
/*      */     }
/* 1061 */     return false;
/*      */   }
/*      */ 
/*      */   public static boolean wildcardMatch(String filename, String wildcardMatcher)
/*      */   {
/* 1087 */     return wildcardMatch(filename, wildcardMatcher, IOCase.SENSITIVE);
/*      */   }
/*      */ 
/*      */   public static boolean wildcardMatchOnSystem(String filename, String wildcardMatcher)
/*      */   {
/* 1112 */     return wildcardMatch(filename, wildcardMatcher, IOCase.SYSTEM);
/*      */   }
/*      */ 
/*      */   public static boolean wildcardMatch(String filename, String wildcardMatcher, IOCase caseSensitivity)
/*      */   {
/* 1129 */     if ((filename == null) && (wildcardMatcher == null)) {
/* 1130 */       return true;
/*      */     }
/* 1132 */     if ((filename == null) || (wildcardMatcher == null)) {
/* 1133 */       return false;
/*      */     }
/* 1135 */     if (caseSensitivity == null) {
/* 1136 */       caseSensitivity = IOCase.SENSITIVE;
/*      */     }
/* 1138 */     filename = caseSensitivity.convertCase(filename);
/* 1139 */     wildcardMatcher = caseSensitivity.convertCase(wildcardMatcher);
/* 1140 */     String[] wcs = splitOnTokens(wildcardMatcher);
/* 1141 */     boolean anyChars = false;
/* 1142 */     int textIdx = 0;
/* 1143 */     int wcsIdx = 0;
/* 1144 */     Stack backtrack = new Stack();
/*      */     do
/*      */     {
/* 1148 */       if (backtrack.size() > 0) {
/* 1149 */         int[] array = (int[])(int[])backtrack.pop();
/* 1150 */         wcsIdx = array[0];
/* 1151 */         textIdx = array[1];
/* 1152 */         anyChars = true;
/*      */       }
/*      */ 
/* 1156 */       while (wcsIdx < wcs.length)
/*      */       {
/* 1158 */         if (wcs[wcsIdx].equals("?"))
/*      */         {
/* 1160 */           textIdx++;
/* 1161 */           anyChars = false;
/*      */         }
/* 1163 */         else if (wcs[wcsIdx].equals("*"))
/*      */         {
/* 1165 */           anyChars = true;
/* 1166 */           if (wcsIdx == wcs.length - 1) {
/* 1167 */             textIdx = filename.length();
/*      */           }
/*      */         }
/*      */         else
/*      */         {
/* 1172 */           if (anyChars)
/*      */           {
/* 1174 */             textIdx = filename.indexOf(wcs[wcsIdx], textIdx);
/* 1175 */             if (textIdx == -1)
/*      */             {
/*      */               break;
/*      */             }
/* 1179 */             int repeat = filename.indexOf(wcs[wcsIdx], textIdx + 1);
/* 1180 */             if (repeat >= 0)
/* 1181 */               backtrack.push(new int[] { wcsIdx, repeat });
/*      */           }
/*      */           else
/*      */           {
/* 1185 */             if (!filename.startsWith(wcs[wcsIdx], textIdx))
/*      */             {
/*      */               break;
/*      */             }
/*      */ 
/*      */           }
/*      */ 
/* 1192 */           textIdx += wcs[wcsIdx].length();
/* 1193 */           anyChars = false;
/*      */         }
/*      */ 
/* 1196 */         wcsIdx++;
/*      */       }
/*      */ 
/* 1200 */       if ((wcsIdx == wcs.length) && (textIdx == filename.length())) {
/* 1201 */         return true;
/*      */       }
/*      */     }
/* 1204 */     while (backtrack.size() > 0);
/*      */ 
/* 1206 */     return false;
/*      */   }
/*      */ 
/*      */   static String[] splitOnTokens(String text)
/*      */   {
/* 1219 */     if ((text.indexOf("?") == -1) && (text.indexOf("*") == -1)) {
/* 1220 */       return new String[] { text };
/*      */     }
/*      */ 
/* 1223 */     char[] array = text.toCharArray();
/* 1224 */     ArrayList list = new ArrayList();
/* 1225 */     StringBuffer buffer = new StringBuffer();
/* 1226 */     for (int i = 0; i < array.length; i++) {
/* 1227 */       if ((array[i] == '?') || (array[i] == '*')) {
/* 1228 */         if (buffer.length() != 0) {
/* 1229 */           list.add(buffer.toString());
/* 1230 */           buffer.setLength(0);
/*      */         }
/* 1232 */         if (array[i] == '?') {
/* 1233 */           list.add("?"); } else {
/* 1234 */           if ((list.size() != 0) && ((i <= 0) || (list.get(list.size() - 1).equals("*"))))
/*      */             continue;
/* 1236 */           list.add("*");
/*      */         }
/*      */       } else {
/* 1239 */         buffer.append(array[i]);
/*      */       }
/*      */     }
/* 1242 */     if (buffer.length() != 0) {
/* 1243 */       list.add(buffer.toString());
/*      */     }
/*      */ 
/* 1246 */     return (String[])(String[])list.toArray(new String[list.size()]);
/*      */   }
/*      */ 
/*      */   static
/*      */   {
/*  120 */     if (isSystemWindows())
/*  121 */       OTHER_SEPARATOR = '/';
/*      */     else
/*  123 */       OTHER_SEPARATOR = '\\';
/*      */   }
/*      */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     org.apache.commons.io.FilenameUtils
 * JD-Core Version:    0.6.0
 */