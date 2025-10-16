// 代码生成时间: 2025-10-16 18:23:52
package com.example.iot

import grails.transaction.Transactional

// IoT网关管理服务类
@Transactional
class IoTGatewayManager {

    // 添加日志依赖
    def log

    // 添加依赖服务
    def iotGatewayService

    // 添加网关
    def addGateway(String gatewayId, String location) {
        try {
            // 验证网关ID的唯一性
            if (iotGatewayService.findGatewayById(gatewayId)) {
                throw new Exception("Gateway ID already exists.")
            }

            // 创建新的网关
            def gateway = new IoTGateway(gatewayId: gatewayId, location: location)

            // 保存网关
            if (!gateway.save()) {
                log.error("Failed to save gateway: " + gateway.errors)
                throw new Exception("Failed to add gateway.")
            }

            return gateway
        } catch (Exception e) {
            log.error("Error adding gateway: " + e.message)
            throw e
        }
    }

    // 更新网关
    def updateGateway(String gatewayId, String newLocation) {
        try {
            // 通过网关ID查找网关
            def gateway = iotGatewayService.findGatewayById(gatewayId)
            if (!gateway) {
                throw new Exception("Gateway not found.")
            }

            // 更新网关位置
            gateway.location = newLocation

            // 保存网关
            if (!gateway.save()) {
                log.error("Failed to update gateway: " + gateway.errors)
                throw new Exception("Failed to update gateway.")
            }

            return gateway
        } catch (Exception e) {
            log.error("Error updating gateway: " + e.message)
            throw e
        }
    }

    // 删除网关
    def deleteGateway(String gatewayId) {
        try {
            // 通过网关ID查找网关
            def gateway = iotGatewayService.findGatewayById(gatewayId)
            if (!gateway) {
                throw new Exception("Gateway not found.")
            }

            // 删除网关
            gateway.delete()

            return true
        } catch (Exception e) {
            log.error("Error deleting gateway: " + e.message)
            throw e
        }
    }

    // 查询网关
    def findGateways() {
        return IoTGateway.list()
    }

    // 查询单个网关
    def findGatewayById(String gatewayId) {
        return iotGatewayService.findGatewayById(gatewayId)
    }
}

// IoT网关模型类
class IoTGateway {
    String gatewayId
    String location

    static constraints = {
        gatewayId(nullable: false, blank: false, unique: true)
        location(nullable: false, blank: false)
    }
}

// IoT网关服务类
class IoTGatewayService {
    // 通过网关ID查找网关
    def findGatewayById(String gatewayId) {
        IoTGateway.findByGatewayId(gatewayId)
    }
}