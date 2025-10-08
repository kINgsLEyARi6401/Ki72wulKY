// 代码生成时间: 2025-10-09 00:00:36
class Defect implements Serializable {
    static final long serialVersionUID = 1L

    String title
    String description
    Date dateCreated
    Date lastUpdated
    String status
    User createdBy
    User assignedTo

    // 缺陷状态枚举
    static final List<String> STATUSES = ['NEW', 'IN_PROGRESS', 'RESOLVED', 'VERIFIED', 'CLOSED']

    static constraints = {
        title(size: 1..255, blank: false, nullable: false)
        description(size: 1..1024, blank: false, nullable: false)
        dateCreated(nullable: true)
        lastUpdated(nullable: true)
        status(inList: STATUSES, blank: false, nullable: false)
        createdBy(nullable: true)
        assignedTo(nullable: true)
    }
}

/**
 * UserController Class
 * 用于处理用户相关的请求。
 */
class UserController {

    def userService
    def缺陷Service
def index() {
        // 获取所有缺陷并渲染到index页面
        def defects = 缺陷Service.findAllDefects()
        render(view: 'index', model: [defects: defects])
    }

    def create() {
        def defectInstance = new Defect()
        defectInstance.properties = params
        if (!defectInstance.hasErrors() && defectInstance.save(flush: true)) {
            flash.message = "缺陷创建成功。"
            redirect(action: "index", id: defectInstance.id)
        } else {
            render(view: 'create', model: [defectInstance: defectInstance])
        }
    }

    def edit() {
        def defectInstance = Defect.get(params.id)
        if (!defectInstance) {
            flash.message = '缺陷不存在。'
            redirect(action: 'index')
        } else {
            render(view: 'edit', model: [defectInstance: defectInstance])
        }
    }

    def update() {
        def defectInstance = Defect.get(params.id)
        if (defectInstance) {
            defectInstance.properties = params
            if (!defectInstance.hasErrors() && defectInstance.save(flush: true)) {
                flash.message = "缺陷更新成功。"
                redirect(action: 'index', id: defectInstance.id)
            } else {
                render(view: 'edit', model: [defectInstance: defectInstance])
            }
        } else {
            flash.message = '缺陷不存在。'
            redirect(action: 'index')
        }
    }

    def delete() {
        def defectInstance = Defect.get(params.id)
        if (defectInstance) {
            defectInstance.delete(flush: true)
            flash.message = "缺陷删除成功。"
            redirect(action: 'index')
        } else {
            flash.message = '缺陷不存在。'
            redirect(action: 'index')
        }
    }
}

/**
 * 缺陷服务类
 * 用于处理缺陷相关的业务逻辑。
 */
class DefectService {

    def userService
    def transactional = true

    List<Defect> findAllDefects() {
        Defect.list()
    }

    void saveDefect(Defect defect) {
        defect.save(flush: true)
    }

    void updateDefect(Defect defect) {
        defect.save(flush: true)
    }

    void deleteDefect(Defect defect) {
        defect.delete(flush: true)
    }
}