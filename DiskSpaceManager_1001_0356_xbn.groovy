// 代码生成时间: 2025-10-01 03:56:23
import groovy.io.FileType
import org.apache.commons.io.FileUtils

class DiskSpaceManager {
    // Method to get free disk space in human-readable format
    public String getFreeDiskSpace() {
        try {
            // Get the root directory of the file system
            File rootDir = new File("/")
            // Get the usable space of the file system in bytes
            long usableSpace = rootDir.getUsableSpace()
            // Convert bytes to human-readable format
            return FileUtils.byteCountToDisplaySize(usableSpace)
        } catch (Exception e) {
            // Handle the exception and return an error message
            return "Error: Unable to retrieve free disk space."
        }
    }

    // Method to get total disk space in human-readable format
    public String getTotalDiskSpace() {
        try {
            // Get the root directory of the file system
            File rootDir = new File("/")
            // Get the total space of the file system in bytes
            long totalSpace = rootDir.getTotalSpace()
            // Convert bytes to human-readable format
            return FileUtils.byteCountToDisplaySize(totalSpace)
        } catch (Exception e) {
            // Handle the exception and return an error message
            return "Error: Unable to retrieve total disk space."
        }
    }

    // Method to get used disk space in human-readable format
    public String getUsedDiskSpace() {
        try {
            // Get the root directory of the file system
            File rootDir = new File("/")
            // Get the used space of the file system in bytes
            long usedSpace = rootDir.getTotalSpace() - rootDir.getFreeSpace()
            // Convert bytes to human-readable format
            return FileUtils.byteCountToDisplaySize(usedSpace)
        } catch (Exception e) {
            // Handle the exception and return an error message
            return "Error: Unable to retrieve used disk space."
        }
    }

    // Method to delete files older than a specified number of days
    public boolean deleteFilesOlderThan(int days) {
        try {
            // Get the current directory
            File currentDir = new File(".")
            // List all files in the current directory
            File[] files = currentDir.listFiles()
            long currentTime = System.currentTimeMillis()
            for (File file : files) {
                if (file.isFile() &&
                    (currentTime - file.lastModified()) > (days * 24 * 60 * 60 * 1000)) {
                    // Delete the file if it's older than the specified number of days
                    boolean deleted = file.delete()
                    if (!deleted) {
                        // Handle the failure to delete the file
                        return false
                    }
                }
            }
            return true
        } catch (Exception e) {
            // Handle the exception and return false
            return false
        }
    }
}
