// 代码生成时间: 2025-10-11 20:56:25
package com.example.monitoring

import groovy.util.logging.Slf4j
import org.springframework.stereotype.Service
import oshi.SystemInfo
import oshi.hardware.CentralProcessor
import oshi.hardware.GlobalMemory
import oshi.hardware.HWPartition
import oshi.software.os.OperatingSystem

/**
 * Service class responsible for monitoring system performance.
 */
@Slf4j
@Service
class SystemPerformanceMonitor {

    /**
     * Retrieves system performance metrics.
     *
     * @return A map containing CPU, Memory, and Disk usage metrics.
     * @throws Exception If an error occurs while retrieving system metrics.
     */
    Map<String, Object> getSystemMetrics() throws Exception {
        try {
            SystemInfo si = new SystemInfo()
            OperatingSystem os = si.getOperatingSystem()
            CentralProcessor processor = si.getHardware().getProcessor()
            GlobalMemory memory = si.getHardware().getMemory()
            List<HWPartition> partitions = si.getHardware().getDiskStores().get(0).getPartitions()

            // CPU Metrics
            double cpuLoad = processor.getSystemCpuLoadBetweenTicks()

            // Memory Metrics
            long availableMemory = memory.getAvailable()
            long totalMemory = memory.getTotal()
            double memoryUsage = ((totalMemory - availableMemory) / (double) totalMemory) * 100

            // Disk Metrics
            long totalDiskSpace = 0
            long usedDiskSpace = 0
            double diskUsage = 0
            for (HWPartition partition : partitions) {
                totalDiskSpace += partition.getSize()
                usedDiskSpace += partition.getUsed()
            }
            diskUsage = (usedDiskSpace / (double) totalDiskSpace) * 100

            // Return system metrics as a map
            return [
                cpuLoad: cpuLoad,
                memoryUsage: memoryUsage,
                diskUsage: diskUsage
            ]
        } catch (Exception e) {
            log.error("Error retrieving system metrics", e)
            throw e
        }
    }
}
