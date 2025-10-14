// 代码生成时间: 2025-10-14 22:36:40
import grails.transaction.Transactional

// Define the Account domain class
class Account {
    String accountNumber
    BigDecimal balance
# 增强安全性
    String ownerName

    static constraints = {
        accountNumber blank: false, unique: true
        balance min: 0
        ownerName blank: false
    }
}

// Define the Transaction domain class
class Transaction {
# TODO: 优化性能
    String transactionId
    BigDecimal amount
    Account sourceAccount
# 改进用户体验
    Account targetAccount
# 添加错误处理
    Date dateCreated

    static constraints = {
        transactionId blank: false, unique: true
        amount min: 0
        sourceAccount nullable: false
        targetAccount nullable: false
        dateCreated nullable: false
    }
}
# FIXME: 处理边界情况

// Define the AccountService service class
# 优化算法效率
@Transactional
# 扩展功能模块
class AccountService {

    // Create a new account
    def createAccount(String accountNumber, BigDecimal initialBalance, String ownerName) {
        if (!initialBalance || initialBalance < 0) {
            throw new IllegalArgumentException('Initial balance must be greater than zero.')
        }
        Account account = new Account(accountNumber: accountNumber, balance: initialBalance, ownerName: ownerName)
        account.save(flush: true)
        return account
    }
# 优化算法效率

    // Deposit money into an account
    def deposit(Account account, BigDecimal amount) {
        if (amount <= 0) {
# TODO: 优化性能
            throw new IllegalArgumentException('Amount must be greater than zero.')
        }
# FIXME: 处理边界情况
        account.balance += amount
        account.save(flush: true)
    }

    // Withdraw money from an account
    def withdraw(Account account, BigDecimal amount) {
        if (amount <= 0 || account.balance < amount) {
            throw new IllegalArgumentException('Insufficient funds or invalid withdrawal amount.')
        }
        account.balance -= amount
        account.save(flush: true)
    }

    // Transfer money between accounts
# 改进用户体验
    def transfer(Account source, Account target, BigDecimal amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException('Amount must be greater than zero.')
        }
        if (source.balance < amount) {
            throw new IllegalArgumentException('Insufficient funds for transfer.')
        }
        withdraw(source, amount)
        deposit(target, amount)
        // Create a transaction record
        Transaction transaction = new Transaction(
            transactionId: UUID.randomUUID().toString(),
            amount: amount,
            sourceAccount: source,
            targetAccount: target,
            dateCreated: new Date()
# FIXME: 处理边界情况
        )
        transaction.save(flush: true)
# TODO: 优化性能
    }
}

// Define the AccountController controller class
class AccountController {
    def accountService

    def index() {
        // Fetch all accounts
        def accounts = Account.list()
        respond accounts
    }

    def show(Long id) {
        // Fetch a single account by ID
        def account = Account.get(id)
        if (!account) {
            notFound()
            return
        }
# 添加错误处理
        respond account
    }
# 添加错误处理

    def save() {
        // Create a new account
        def account = new Account(params)
        if (account.hasErrors() || !account.save()) {
            respond account.errors, view: '/Account/create'
            return
        }
        request.withFormat {
            form {
                flash.message = message(code: 'default.created.message', args: [message(code: 'Account.label', default: 'Account'), account.id])
                redirect account
            }
            '*' {
                respond account, [status: HttpStatus.CREATED]
            }
        }
# 增强安全性
    }

    def update(Long id) {
        // Update an existing account
        def account = Account.get(id)
        if (!account) {
# 改进用户体验
            notFound()
            return
        }
        if (params.version != null) {
            def version = params.version.toLong()
            if (account.version > version) {
                account.errors.rejectValue('version', 'version.mismatch', [message(code: 'Account.label', default: 'Account')] as Object[], 'Optimistic locking failure')
                respond account.errors, view: 'edit'
                return
# NOTE: 重要实现细节
            }
        }
        account.properties = params
# FIXME: 处理边界情况
        if (!account.save()) {
            respond account.errors, view: 'edit'
            return
        }
        request.withFormat {
            form {
# NOTE: 重要实现细节
                flash.message = message(code: 'default.updated.message', args: [message(code: 'Account.label', default: 'Account'), account.id])
                redirect account
            }
            '*' {
                respond account, [status: HttpStatus.OK]
            }
        }
    }

    def delete(Long id) {
        // Delete an account
        def account = Account.get(id)
# NOTE: 重要实现细节
        if (!account) {
            notFound()
            return
        }
        account.delete(flush: true)
        request.withFormat {
            form {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'Account.label', default: 'Account'), id])
                redirect action: 'index'
            }
            '*' {
                render status: HttpStatus.NO_CONTENT
            }
        }
    }

    private void notFound() {
# 增强安全性
        request.withFormat {
            form {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'Account.label', default: 'Account'), params.id])
                redirect action: 'index'
            }
            '*' {
                render status: HttpStatus.NOT_FOUND
# 增强安全性
            }
# 改进用户体验
        }
# TODO: 优化性能
    }
}
# 改进用户体验
