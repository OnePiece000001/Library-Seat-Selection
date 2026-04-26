package org.example.webaimanagement.controller;

import org.example.webaimanagement.pojo.*;
import org.example.webaimanagement.service.BookService;
import org.example.webaimanagement.service.LibraryBookService;
import org.example.webaimanagement.utils.AliyunOSSOperator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
public class LibraryBookController {
    @Autowired
    LibraryBookService libraryBookService;
    //获取前50推荐
    @GetMapping("/gettop50")
    public Result gettopbook(){
        List<LibraryBook> book=libraryBookService.gettopbooks();
        return book != null ? Result.success(book) : Result.error("没有找到相关数据！");
    }
    //获取推荐图书信息
    @GetMapping("/recommentbook")
    public Result getbook(@RequestParam String user_phone) {
          List<LibraryBook> book=libraryBookService.getbook(user_phone);
        return book != null ? Result.success(book) : Result.error("没有找到相关数据！");
    }
    //获取图书信息
    @GetMapping("/getbook")
    public Result getbook1() {

        List<LibraryBook> book=libraryBookService.getbook1();
        return book != null ? Result.success(book) : Result.error("没有找到相关数据！");
    }
    //获取全部图书信息
    @GetMapping("/getallbook")
    public Result getAllBook() {
        List<LibraryBook> book=libraryBookService.getAllbook();
        return !book.isEmpty() ? Result.success(book) : Result.error("没有找到相关数据！");
    }
    //修改图书信息
    @PostMapping("/updatebook")
    public Result updatebook(LibraryBookPhotoParam libraryBook) throws Exception {
        MultipartFile bookPhoto = libraryBook.getBook_photo();
        String url = null;

        // ========== 核心修复：判断文件是否为空 ==========
        if (bookPhoto != null && !bookPhoto.isEmpty()) {
            // 有图片才上传
            url = aliyunOSSOperator.upload(bookPhoto.getBytes(), bookPhoto.getOriginalFilename());
            libraryBook.setBook_photo1(url);
        } else {
            // 没图片 → 设为null 或 空字符串
            libraryBook.setBook_photo1(null);
        }
        Integer book=libraryBookService.updatebook(libraryBook);

        return book != 0 ? Result.success("该图书信息已更新！") : Result.error("没有找到相关数据！");
    }
    //根据id修改图片
    @Autowired
    private AliyunOSSOperator aliyunOSSOperator;
    @PostMapping("/uploadbookphoto")
    public Result updateBookPhoto(LibraryBookPhotoParam libraryBookPhotoParam) throws Exception {
        MultipartFile bookPhoto = libraryBookPhotoParam.getBook_photo();
        String url= aliyunOSSOperator.upload(bookPhoto.getBytes(),bookPhoto.getOriginalFilename());
        String bookId = libraryBookPhotoParam.getBook_id();
        Integer book=libraryBookService.updateBookphoto(url,bookId);


        return book != null ? Result.success("封面已更新！") : Result.error("没有找到相关数据！");
    }
    //删除图书信息
    @DeleteMapping("/deletebook")
    public Result deletebooking(String book_id) {
        Integer deleteBook = libraryBookService.deletebook(book_id);
        return deleteBook!= 0 ? Result.success("该图书已经删除！") : Result.error("没有该编号图书！");
    }
    //添加图书
    @PostMapping("/addbook")
    public Result addbook(LibraryBookPhotoParam libraryBook) throws Exception {
        MultipartFile bookPhoto = libraryBook.getBook_photo();
        String url = null;

        // ========== 核心修复：判断文件是否为空 ==========
        if (bookPhoto != null && !bookPhoto.isEmpty()) {
            // 有图片才上传
            url = aliyunOSSOperator.upload(bookPhoto.getBytes(), bookPhoto.getOriginalFilename());
            libraryBook.setBook_photo1(url);
        } else {
            // 没图片 → 设为null 或 空字符串
            libraryBook.setBook_photo1(null);
        }
        Integer book=libraryBookService.addbook(libraryBook);

        return book != 0 ? Result.success("该图书信息已添加！") : Result.error("出现问题，请联系管理员！");
    }
    //根据书名查询图书信息
    @GetMapping("/searchbook")
    public Result searchBook(String book_name) {

         LibraryBook book=libraryBookService.searchBook(book_name);
        return book != null ? Result.success(book) : Result.error("没有找到相关数据！");
    }
    //收藏图书

    @PostMapping("/collectbook")
    public Result collectbook(String book_no) {
        Integer number= libraryBookService.collectbook(book_no);
        return number !=0 ? Result.success("亲，已收藏！") : Result.error("没有找到相关数据！");
    }


}

