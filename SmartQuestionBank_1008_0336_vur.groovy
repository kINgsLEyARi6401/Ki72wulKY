// 代码生成时间: 2025-10-08 03:36:24
//@Grapes([@Grab(group='org.grails', module='grails-datastore-jpa-plugin', version='3.2.9')])

import grails.transaction.Transactional
import org.grails.datastore.mapping.jpa.JpaEntity
import javax.persistence.*

// 题目类别实体
@Entity
@Table(name = 'question_category')
class QuestionCategory implements JpaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id
    String name
    String description
}

// 题目实体
@Entity
@Table(name = 'question')
class Question implements JpaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id
    String content
    String answer
    Long categoryId
    QuestionCategory category
    static hasMany = [options: Option]
}

// 选项实体
@Entity
@Table(name = 'option')
class Option implements JpaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id
    String content
    Long questionId
    Question question
}

// 题库服务
@Transactional
class QuestionBankService {
    def questionCategoryService
    def questionService
    def optionService
    
    // 添加题目类别
    void addCategory(String name, String description) {
        try {
            QuestionCategory category = new QuestionCategory(name: name, description: description)
            questionCategoryService.save(category)
        } catch (Exception e) {
            // 错误处理
            println 'Error adding category: ' + e.message
        }
    }
    
    // 添加题目
    void addQuestion(String content, String answer, Long categoryId) {
        try {
            QuestionCategory category = questionCategoryService.get(categoryId)
            if (category) {
                Question question = new Question(content: content, answer: answer, categoryId: categoryId, category: category)
                questionService.save(question)
            } else {
                throw new Exception('Category not found')
            }
        } catch (Exception e) {
            // 错误处理
            println 'Error adding question: ' + e.message
        }
    }
    
    // 添加选项
    void addOption(String content, Long questionId) {
        try {
            Question question = questionService.get(questionId)
            if (question) {
                Option option = new Option(content: content, questionId: questionId, question: question)
                optionService.save(option)
            } else {
                throw new Exception('Question not found')
            }
        } catch (Exception e) {
            // 错误处理
            println 'Error adding option: ' + e.message
        }
    }
    
    // 获取所有题目类别
    List<QuestionCategory> getAllCategories() {
        return questionCategoryService.list()
    }
    
    // 根据类别ID获取题目
    List<Question> getQuestionsByCategory(Long categoryId) {
        return questionService.findAllByCategoryId(categoryId)
    }
}

// 命令行接口
class QuestionBankCli {
    static void main(String[] args) {
        // 这里可以添加命令行交互逻辑
    }
}
