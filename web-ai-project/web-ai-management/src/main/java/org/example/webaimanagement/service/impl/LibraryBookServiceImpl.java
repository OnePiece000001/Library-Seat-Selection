package org.example.webaimanagement.service.impl;

import org.example.webaimanagement.mapper.BookMapper;
import org.example.webaimanagement.mapper.LibraryBookMapper;
import org.example.webaimanagement.mapper.LibraryCollectMapper;
import org.example.webaimanagement.pojo.Book;
import org.example.webaimanagement.pojo.LibraryBook;
import org.example.webaimanagement.pojo.LibraryBookPhotoParam;
import org.example.webaimanagement.service.BookService;
import org.example.webaimanagement.service.LibraryBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class LibraryBookServiceImpl implements LibraryBookService {
     @Autowired
     private LibraryBookMapper librarybookMapper;
     @Autowired
     private LibraryCollectMapper libraryCollectMapper;
    @Override
    public List<LibraryBook> getbook() {
        return librarybookMapper.getbook();
    }

    @Override
    public List<LibraryBook> getbook(String userPhone) {
        // 获取用户收藏的图书
        List<LibraryBook> collectedBooks = libraryCollectMapper.getCollectbook_userPone(userPhone);
        if (collectedBooks == null || collectedBooks.isEmpty()) {
            // 如果用户没有收藏图书，返回默认推荐
            return getDefaultBooks();
        }

        // 获取所有图书
        List<LibraryBook> allBooks = librarybookMapper.getbook();
        if (allBooks == null || allBooks.isEmpty()) {
            return new ArrayList<>();
        }

        // 基于协同过滤算法推荐图书
        List<LibraryBook> recommendedBooks = recommendBooks(collectedBooks, allBooks);

        // 返回推荐的图书，最多返回10本
        return recommendedBooks.stream().limit(30).collect(Collectors.toList());
    }

    /**
     * 基于协同过滤算法推荐图书
     * @param collectedBooks 用户收藏的图书
     * @param allBooks 所有图书
     * @return 推荐的图书列表
     */
    private List<LibraryBook> recommendBooks(List<LibraryBook> collectedBooks, List<LibraryBook> allBooks) {
        // 计算每本书的推荐分数
        List<BookScore> bookScores = new ArrayList<>();

        for (LibraryBook book : allBooks) {
            // 跳过用户已经收藏的图书
            boolean isCollected = collectedBooks.stream()
                    .anyMatch(collectedBook -> collectedBook.getBook_id().equals(book.getBook_id()));
            if (isCollected) {
                continue;
            }

            // 计算推荐分数（基于图书类型相似度）
            double score = calculateScore(book, collectedBooks);
            if (score > 0) {
                bookScores.add(new BookScore(book, score));
            }
        }

        // 按分数排序，返回推荐结果
        return bookScores.stream()
                .sorted(Comparator.comparingDouble(BookScore::getScore).reversed())
                .map(BookScore::getBook)
                .collect(Collectors.toList());
    }

    /**
     * 计算图书的推荐分数
     * @param book 待推荐的图书
     * @param collectedBooks 用户收藏的图书
     * @return 推荐分数
     */
    private double calculateScore(LibraryBook book, List<LibraryBook> collectedBooks) {
        double score = 0.0;
        for (LibraryBook collectedBook : collectedBooks) {
            // 如果图书类型相同，增加推荐分数
            if (book.getBook_type() != null && book.getBook_type().equals(collectedBook.getBook_type())) {
                score += 1.0;
            }
        }
        return score;
    }

    /**
     * 图书分数类，用于存储图书及其推荐分数
     */
    private static class BookScore {
        private final LibraryBook book;
        private final double score;

        public BookScore(LibraryBook book, double score) {
            this.book = book;
            this.score = score;
        }

        public LibraryBook getBook() {
            return book;
        }

        public double getScore() {
            return score;
        }
    }

    @Override
    public List<LibraryBook> getbook1() {
        return librarybookMapper.getbook1();
    }

    @Override
    public Integer updatebook(LibraryBookPhotoParam libraryBook) {
        return librarybookMapper.updatebook(libraryBook);
    }

    @Override
    public Integer deletebook(String book_id) {
        return librarybookMapper.deleltebook(book_id);
    }

    @Override
    public Integer updateBookphoto(String url,String bookId) {
        return  librarybookMapper.updateBookphoto(url,bookId);
    }

    @Override
    public Integer addbook(LibraryBookPhotoParam libraryBook) {
        return  librarybookMapper.addBook(libraryBook);
    }

    @Override
    public LibraryBook searchBook(String book_name) {
        return  librarybookMapper.searchBook(book_name);
    }

    @Override
    public List<LibraryBook> getAllbook() {
        return  librarybookMapper.searchAllBook();
    }

    @Override
    public List<LibraryBook> gettopbooks() {
        return  librarybookMapper.getTopBooks();
    }

    @Override
    public List<LibraryBook> getDefaultBooks() {
        return librarybookMapper.getDefaultBooks();
    }

    @Override
    public Integer collectbook(String book_no) {
        LibraryBook getcollectbook = librarybookMapper.getcollectbook(book_no);
        Integer number=librarybookMapper.insertcollectbook(getcollectbook);

        return number;
    }


}
