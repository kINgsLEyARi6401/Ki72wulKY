// 代码生成时间: 2025-10-19 03:44:06
package com.yourcompany.achievement

import grails.transaction.Transactional

// Import necessary Grails domain classes
import com.yourcompany.user.User
import com.yourcompany.achievement.Achievement
import com.yourcompany.achievement.AchievementCriteria

// Service class for managing achievements
@Transactional
class AchievementService {

    // Method to unlock an achievement for a user
    def unlockAchievement(User user, AchievementCriteria criteria) {
        // Check if the user and criteria are valid
        if (!user || !criteria) {
            throw new IllegalArgumentException('User or AchievementCriteria cannot be null')
        }

        // Create a new achievement instance based on the criteria
        Achievement achievement = new Achievement(
            user: user,
            criteria: criteria,
            unlockedDate: new Date()
        )

        // Save the achievement to the database
        achievement.save(flush: true, failOnError: true)

        // Return the unlocked achievement
        return achievement
    }

    // Method to check if an achievement has been unlocked by a user
    boolean isAchievementUnlocked(User user, AchievementCriteria criteria) {
        // Find the achievement based on user and criteria
        def achievement = Achievement.find {
            user == user &&
            criteria == criteria
        }

        // Return true if the achievement exists, false otherwise
        return achievement != null
    }
}

// Domain class for AchievementCriteria
class AchievementCriteria {
    String name
    String description
    // Additional fields and constraints can be added here
}

// Domain class for Achievement
class Achievement {
    User user
    AchievementCriteria criteria
    Date unlockedDate
    // Additional fields and constraints can be added here
    static constraints = {
        user nullable: false
        criteria nullable: false
        unlockedDate nullable: false
    }
}
