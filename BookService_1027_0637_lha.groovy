// 代码生成时间: 2025-10-27 06:37:32
package com.example
grails-app/services

import grails.transaction.Transactional
import org.springframework.validation.ObjectError
import org.springframework.validation.FieldError

// 数据模型设计
// BookService.groovy
@Transactional
class BookService {
    
    // 依赖注入，Book Domain对象
    def bookDao
    
    // 添加书籍
    def addBook(String title, String author, String isbn) {
        // 创建新的Book对象
        def book = new Book(title: title, author: author, isbn: isbn)
        try {
            // 保存书籍对象
            if (book.validate()) {
                bookDao.save(book)
                return book
            } else {
                // 验证失败，抛出异常
                throw new IllegalArgumentException('Validation errors: ' + book.errors)
            }
        } catch (Exception e) {
            // 异常处理
            e.printStackTrace()
            throw e
        }
    }
    
    // 更新书籍信息
    def updateBook(Long id, String title, String author, String isbn) {
        def book = bookDao.get(id)
        if (book == null) {
            throw new IllegalArgumentException('Book not found with id: ' + id)
        }
        book.title = title
        book.author = author
        book.isbn = isbn
        try {
            if (book.validate()) {
                bookDao.save(book)
                return book
            } else {
                throw new IllegalArgumentException('Validation errors: ' + book.errors)
            }
        } catch (Exception e) {
            e.printStackTrace()
            throw e
        }
    }
    
    // 删除书籍
    def deleteBook(Long id) {
        def book = bookDao.get(id)
        if (book == null) {
            throw new IllegalArgumentException('Book not found with id: ' + id)
        }
        try {
            bookDao.delete(book)
        } catch (Exception e) {
            e.printStackTrace()
            throw e
        }
    }
    
    // 获取书籍信息
    def getBook(Long id) {
        def book = bookDao.get(id)
        if (book == null) {
            throw new IllegalArgumentException('Book not found with id: ' + id)
        }
        return book
    }
    
    // 获取所有书籍列表
    def listBooks() {
        return bookDao.list()
    }
}
