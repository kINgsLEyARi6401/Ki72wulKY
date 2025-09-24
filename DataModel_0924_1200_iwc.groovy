// 代码生成时间: 2025-09-24 12:00:02
 * It includes necessary properties and constraints as specified by the requirements.
 */
class DataModel {

    // Unique identifier for the data model
    Long id
    // Other properties as per the data model requirements
    String name
    Date dateCreated
    Date lastUpdated

    // Validation constraints
    static constraints = {
        name blank: false, size: 1..255
        dateCreated nullable: true
        lastUpdated nullable: true
    }

    // Default constructor
    def DataModel() {}

    // Constructor with all properties
    def DataModel(Long id, String name, Date dateCreated, Date lastUpdated) {
        this.id = id
        this.name = name
        this.dateCreated = dateCreated
        this.lastUpdated = lastUpdated
    }

    /**
     * Update the data model with new values
     * @param name The new name for the data model
     * @param dateCreated The new creation date for the data model
     * @param lastUpdated The new last updated date for the data model
     */
    def update(String name, Date dateCreated, Date lastUpdated) {
        this.name = name
        this.dateCreated = dateCreated
        this.lastUpdated = lastUpdated
    }

    // toString method for easy representation of the data model
    String toString() {
        return "DataModel{id: $id, name: "$name", dateCreated: $dateCreated, lastUpdated: $lastUpdated}"
    }
}
