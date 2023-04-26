package com.evan.winfile.common.util;


import org.springframework.util.StringUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributeView;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Evan
 * @date 2020-08-17
 */
public class FileUtils {

  private static final Set<String> PICTURES = new HashSet<>(16);
  private static final Set<String> VIDEOS = new HashSet<>(16);

      static{
        PICTURES.addAll(Arrays.asList("bmp", "jpg", "tiff", "tif", "gif", "jpeg", "svg", "png"));
        VIDEOS.addAll(Arrays.asList("avi", "mov", "rmvb", "rm", "flv", "mp4", "3gp"));
      }

  public static boolean isPicture(String fileType) {
    return PICTURES.contains(fileType.toLowerCase());
  }

  public static boolean isVideo(String fileType) {
    return StringUtils.hasText(fileType) && VIDEOS.contains(fileType.toLowerCase());
  }

  public static String getUrlFileType(String url) {
    String fileFullName = getUrlFullFileName(url);
    if (fileFullName != null) {
      String fileType = fileFullName.substring(fileFullName.lastIndexOf(".") + 1);
      if (fileType.length() > 0) {
        return fileType.toLowerCase();
      }
    }
    return "";
  }

  public static String getUrlFileName(String url) {
    String fileFullName = getUrlFullFileName(url);
    if (fileFullName != null && fileFullName.contains(".")) {
      fileFullName = fileFullName.substring(0, fileFullName.lastIndexOf("."));
    }
    if (fileFullName.length() > 0) {
      return fileFullName;
    }
    return null;
  }

  public static String getUrlFullFileName(String url) {
    if (url.contains("?")) {
      url = url.substring(0, url.indexOf("?"));
    }
    String fileFullName = url.substring(url.lastIndexOf(File.separator) + 1);
    if (fileFullName.length() > 0) {
      return fileFullName;
    }
    return null;
  }

  public static Date getFileCreateTime(String filePath) {
    return getFileCreateTime(new File(filePath));
  }

  public static Date getFileCreateTime(File file) {
    try {
      Path path = Paths.get(file.getPath());
      BasicFileAttributeView basicview =
          Files.getFileAttributeView(path, BasicFileAttributeView.class, LinkOption.NOFOLLOW_LINKS);
      BasicFileAttributes attr = basicview.readAttributes();
      return new Date(attr.creationTime().toMillis());
    } catch (Exception e) {
      e.printStackTrace();
      return new Date(file.lastModified());
    }
  }

  public static File createDirectory(String path) {
    File file = new File(path);
    file.mkdirs();
    return file;
  }

  public static void writeFile(String fileName, byte[] data) {
    try {
      File file = new File(fileName);
      if (!file.exists()) {
        file.createNewFile();
      }
      try (OutputStream os = new FileOutputStream(file)) {
        os.write(data);
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public static void deleteFile(String path){
        try{
          Files.deleteIfExists(Paths.get(path));
        }catch (Exception e){
          
        }
  }

  public static void clearDirectory(String directory) {
    File file = new File(directory);
    if (file.isDirectory()) {
      File[] files = file.listFiles();
      for (File f : files) {
        if (f.isDirectory()) {
          clearDirectory(f.getPath());
        }
        FileUtils.delete(f.getPath());
      }
    }
    FileUtils.delete(directory);
  }

  public static double transFileSize(long length, FileSizeType fileSizeType) {
    double size = 0;
    switch (fileSizeType) {
      case B:
        return length;
      case KB:
        return new BigDecimal(length)
            .divide(new BigDecimal(1024), RoundingMode.HALF_UP)
            .setScale(2, RoundingMode.HALF_UP)
            .doubleValue();
      case MB:
        return new BigDecimal(length)
            .divide(new BigDecimal(1024 * 1024), RoundingMode.HALF_UP)
            .setScale(2, RoundingMode.HALF_UP)
            .doubleValue();
      case GB:
        return new BigDecimal(length)
            .divide(new BigDecimal(1024 * 1024 * 1024), RoundingMode.HALF_UP)
            .setScale(2, RoundingMode.HALF_UP)
            .doubleValue();
      default:
        break;
    }
    return size;
  }

  public static String clearUrl(String url) {
    if (url.contains("?")) {
      url = url.substring(0, url.indexOf("?"));
    }
    return url;
  }

    public static void delete(String targetPath) {
        if(!StringUtils.hasText(targetPath)){
          return ;
        }
        try{
          Files.deleteIfExists(Paths.get(targetPath));
        }catch (Exception e){
          e.printStackTrace();
        }
    }

  public static void remove(String detectPath, String savePath) {
        File source = new File(detectPath);
        if(source.exists()){
          File target = new File(savePath);
          if(target.exists()){
            delete(savePath);
          }
          try{
            Files.copy(Paths.get(detectPath), Paths.get(savePath));
            delete(detectPath);
          }catch (Exception e){
            e.printStackTrace();
          }
        }
  }

    public static void copy(String source, String target) {
        try{
          Files.copy(Paths.get(source), Paths.get(target));
        }catch (Exception e){
          e.printStackTrace();
        }
    }

    public enum FileSizeType {
    B,
    KB,
    MB,
    GB,
  }
}
