// 代码生成时间: 2025-10-02 00:00:36
package com.example.versioncontrol;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class VersionControlService {
    // In-memory storage for file versions
    private final Map<String, Map<String, String>> repository = new HashMap<>();

    /**<ol>
     * Adds a new file to the version control system.
     * 
     * @param filePath The path of the file to add.
     * @param content The initial content of the file.
     * @return The unique identifier for the new file.
     */
    public String addFile(String filePath, String content) {
        String fileId = UUID.randomUUID().toString();
        repository.put(fileId, new HashMap<>());
        commitFile(fileId, 1, content);
        return fileId;
    }

    /**<ol>
     * Commits changes to a file in the version control system.
     * 
     * @param fileId The unique identifier of the file to commit changes to.
     * @param version The version number of the file.
     * @param content The new content of the file.
     */
    public void commitFile(String fileId, int version, String content) {
        if (!repository.containsKey(fileId)) {
            throw new IllegalArgumentException("File ID not found: " + fileId);
        }
        repository.get(fileId).put(String.valueOf(version), content);
    }

    /**<ol>
     * Retrieves a specific version of a file from the version control system.
     * 
     * @param fileId The unique identifier of the file to retrieve.
     * @param version The version number of the file to retrieve.
     * @return The content of the file at the specified version.
     */
    public String retrieveFile(String fileId, int version) {
        if (!repository.containsKey(fileId)) {
            throw new IllegalArgumentException("File ID not found: " + fileId);
        }
        Map<String, String> versions = repository.get(fileId);
        if (!versions.containsKey(String.valueOf(version))) {
            throw new IllegalArgumentException("Version not found for file ID: " + fileId);
        }
        return versions.get(String.valueOf(version));
    }
}
