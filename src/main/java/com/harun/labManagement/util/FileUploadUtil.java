package com.harun.labManagement.util;

import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class FileUploadUtil {
    /**
     * It takes a directory, a file name, and a multipart file as parameters, and then it creates the directory if it
     * doesn't exist, and then it copies the multipart file to the directory with the given file name
     *
     * @param dir The directory where the file will be saved.
     * @param fileName The name of the file to be saved.
     * @param multipartFile The file that is being uploaded.
     */
    public static void saveFile(String dir, String fileName, MultipartFile multipartFile) throws IOException {
        Path uploadPath = Paths.get(dir);

        if(!Files.exists(uploadPath)){
            Files.createDirectories(uploadPath);
        }

        try(InputStream inputStream = multipartFile.getInputStream()){
            Path filePath = uploadPath.resolve(fileName);
            Files.copy(inputStream,filePath, StandardCopyOption.REPLACE_EXISTING);
        }catch (IOException ioe){
            throw new IOException("Could not save image file: " + fileName ,ioe);
        }
    }

    /**
     * Read the file, convert it to a byte array, and return the byte array.
     *
     * @param dir The directory where the file is located.
     * @param fileName The name of the file you want to get.
     * @return A byte array of the image.
     */
    public static byte[] getFile(String dir, String fileName) throws IOException {
        Path filePath = Paths.get(dir + fileName);

        BufferedImage img = ImageIO.read(filePath.toFile());
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(img, "PNG", baos);

        return baos.toByteArray();
    }

    /**
     * It checks if a file exists in a given directory
     *
     * @param dir the directory where the file is located
     * @param fileName The name of the file you want to check.
     * @return A boolean value.
     */
    public static boolean isFileExist(String dir, String fileName){
        Path filePath = Paths.get(dir + fileName);
        return Files.exists(filePath);
    }
}
