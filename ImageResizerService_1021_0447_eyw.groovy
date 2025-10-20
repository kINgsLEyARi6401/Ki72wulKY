// 代码生成时间: 2025-10-21 04:47:10
package com.example.imageresizer

import groovy.util.logging.Slf4j
import org.apache.commons.imaging.ImageFormats
import org.apache.commons.imaging.Imaging
import org.apache.commons.imaging.common.GenericFormat
import org.apache.commons.imaging.common.ImageMetadata
import org.apache.commons.imaging.common.RationalNumber
import org.apache.commons.imaging.common.GenericImageMetadata
import org.apache.commons.imaging.formats.jpeg.JpegImageMetadata
import org.apache.commons.imaging.formats.tiff.TiffImageMetadata
import org.apache.commons.imaging.formats.tiff.constants.TiffTagConstants
import org.apache.commons.imaging.common.IImageMetadata
import org.apache.commons.imaging.ImagingUtils
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile

import java.awt.image.BufferedImage
import javax.imageio.ImageIO
import java.io.*
import java.nio.file.Files
import java.nio.file.Paths

@Slf4j
@Service
class ImageResizerService {

    // The path where the original images are stored
    private static final String ORIGINAL_IMAGES_DIR = 'path/to/original/images'
    // The path where the resized images will be stored
    private static final String RESIZED_IMAGES_DIR = 'path/to/resized/images'

    // Method to resize a single image
    def resizeImage(MultipartFile file) {
        File tempFile = convertMultiPartToFile(file)
        File originalImage = new File(ORIGINAL_IMAGES_DIR, tempFile.name)
        tempFile.transferTo(originalImage)
        log.info("Image uploaded and saved as original")

        try {
            int imageWidth = Imaging.getFormatIdentifier(originalImage).getWidth(originalImage)
            int imageHeight = Imaging.getFormatIdentifier(originalImage).getHeight(originalImage)

            // Define the desired dimensions for the resized image
            int targetWidth = 800
            int targetHeight = (imageHeight * targetWidth) / imageWidth

            BufferedImage resizedImage = Imaging.getBufferedImage(originalImage)
            BufferedImage outputImage = new BufferedImage(targetWidth, targetHeight, BufferedImage.TYPE_INT_RGB)
            outputImage.getGraphics().drawImage(resizedImage.getScaledInstance(targetWidth, targetHeight, Image.SCALE_SMOOTH), 0, 0, null)
            outputImage.getGraphics().dispose()

            // Save the resized image
            File resizedFile = new File(RESIZED_IMAGES_DIR, tempFile.name)
            ImageIO.write(outputImage, ImageFormats.JPEG, resizedFile)
            log.info("Image resized and saved")
            return resizedFile
        } catch (Exception e) {
            log.error("Error resizing image: ${e.message}")
            throw new RuntimeException("Error resizing image", e)
        }
    }

    // Method to convert a MultipartFile to a File
    private File convertMultiPartToFile(MultipartFile file) {
        try {
            File convFile = new File(file.originalFilename)
            try (InputStream in = file.getInputStream(); OutputStream out = new FileOutputStream(convFile)) {
                byte[] buf = new byte[1024]
                int len
                while ((len = in.read(buf)) > 0) {
                    out.write(buf, 0, len)
                }
            }
            return convFile
        } catch (Exception e) {
            log.error("Could not convert multiPart file to file: ${e.message}")
            throw new RuntimeException("Could not convert multiPart file to file", e)
        }
    }
}
