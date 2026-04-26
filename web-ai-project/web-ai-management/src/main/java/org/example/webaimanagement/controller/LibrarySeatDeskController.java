package org.example.webaimanagement.controller;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import org.example.webaimanagement.pojo.*;
import org.example.webaimanagement.service.LibraryBookService;
import org.example.webaimanagement.service.LibrarySeatDeskService;
import org.example.webaimanagement.utils.AliyunOSSOperator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;


import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.List;

@RestController
public class LibrarySeatDeskController {
    @Autowired
    LibrarySeatDeskService librarySeatDeskService;

    //获取座位信息
    @GetMapping("/getseat")
    public Result getseat() {

        List<LibrarySeatDesk> seat = librarySeatDeskService.getseat();
        return seat != null ? Result.success(seat) : Result.error("没有找到相关数据！");
    }

    //获取桌子信息
    @GetMapping("/getdesk")
    public Result getdesk() {

        List<LibraryDeskSeat> desk = librarySeatDeskService.getdesk();
        return desk != null ? Result.success(desk) : Result.error("没有找到相关数据！");
    }

    //获取楼层信息
    @GetMapping("/getfloor")
    public Result getfloor() {
        List<LibraryFloor> floors = librarySeatDeskService.getfloor();
        return floors != null ? Result.success(floors) : Result.error("没有找到相关数据！");
    }

    @GetMapping("/addfloor")
    public Result addfloor(String floor_name, String floor_no) {
        Integer floor = librarySeatDeskService.addfloor(floor_name, floor_no);
        return floor != 0 ? Result.success("添加成功！") : Result.error("没有找到相关数据！");
    }

    @PostMapping("/savedeskseat")
    public Result saveDeskSeat(@RequestBody LibraryDeskSeatList libraryDeskSeatList) {
        Integer a = librarySeatDeskService.saveDeskSeat(libraryDeskSeatList);

        return a != 0 ? Result.success("添加成功！") : Result.error("没有找到相关数据！");
    }

    //删除座位
    @DeleteMapping("/deleteseat")
    public Result deleteSeat(String seat_name) {
        Integer seat = librarySeatDeskService.delelteSeat(seat_name);
        return seat != 0 ? Result.success("删除成功！") : Result.error("没有找到相关数据！");
    }

    //删除桌子
    @DeleteMapping("/deletedesk")
    public Result deleteDesk(String desk_name) {
        Integer seat = librarySeatDeskService.delelteDecsk(desk_name);
        return seat != 0 ? Result.success("删除成功！") : Result.error("没有找到相关数据！");
    }

    @DeleteMapping("/deletefloor")
    public Result deletefloor(String floor_no, String floor_name) {
        Integer seat = librarySeatDeskService.delelteFloor(floor_no, floor_name);
        return seat != 0 ? Result.success("删除成功！") : Result.error("没有找到相关数据！");
    }

    @GetMapping("/getOccupiedSeats")
    public Result getOccupieSeats() {
        List<String> seats = librarySeatDeskService.getOccupieSeats();
        return seats != null ? Result.success(seats) : Result.error("没有找到相关数据！");
    }

    @Autowired
    private AliyunOSSOperator aliyunOSSOperator;

    @GetMapping("/getCode")
    public Result getCode(String seat_name) throws Exception {
        // 1. 生成座位专属二维码
        QRCodeWriter writer = new QRCodeWriter();
        HashMap<EncodeHintType, Object> hints = new HashMap<>();
        hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");

        String qrContent = "http://192.168.43.143:8080/#/QianDao?seat_name=" + seat_name;
        BitMatrix bitMatrix = writer.encode(qrContent, BarcodeFormat.QR_CODE, 300, 300, hints);
        BufferedImage image = MatrixToImageWriter.toBufferedImage(bitMatrix);

        // 2. 图片转字节流
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(image, "png", baos);
        byte[] imageBytes = baos.toByteArray();

        // 3. 直接用字节流调用阿里云上传（完全绕开MultipartFile，彻底解决报错）
        String fileName = "seat_qr_" + seat_name + ".png";
        String qrUrl = aliyunOSSOperator.upload(imageBytes, fileName);

        return Result.success(qrUrl);

    }
}
