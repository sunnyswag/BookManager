package org.henhaoqi.BookManager.service;

import org.henhaoqi.BookManager.dao.BookDAO;
import org.henhaoqi.BookManager.entity.Book;
import org.henhaoqi.BookManager.entity.enums.BookStatusEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {
    @Autowired
    private BookDAO bookDao;

    public List<Book> getAllBooks(){
        return bookDao.selectAll();
    }

    public int addBooks(Book book){
        return bookDao.addBook(book);
    }

    public void deleteBooks(int id){
        bookDao.updateBookStatus(id, BookStatusEnum.DELETE.getValue());
    }

    public void recoverBooks(int id){
        bookDao.updateBookStatus(id, BookStatusEnum.NORMAL.getValue());
    }
}
