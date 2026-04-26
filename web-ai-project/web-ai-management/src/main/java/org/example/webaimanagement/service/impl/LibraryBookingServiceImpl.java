package org.example.webaimanagement.service.impl;

import org.example.webaimanagement.mapper.LibraryBookMapper;
import org.example.webaimanagement.mapper.LibraryBookingMapper;
import org.example.webaimanagement.pojo.LibraryBook;
import org.example.webaimanagement.pojo.LibraryBooking;
import org.example.webaimanagement.pojo.ReserveTimeDTO;
import org.example.webaimanagement.service.LibraryBookService;
import org.example.webaimanagement.service.LibraryBookingService;
import org.example.webaimanagement.utils.ContinuousTimes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class LibraryBookingServiceImpl implements LibraryBookingService{
     @Autowired
     private LibraryBookingMapper librarybookingMapper;


    @Override
    public List<LibraryBooking> getbooking(String userphone, String content) {
        String HISTORY_BOOKING = "Y"; // 历史预约
        String CURRENT_BOOKING = "N";
        if (HISTORY_BOOKING.equals(content)) {
            return librarybookingMapper.getHistorybooking(userphone);
        }
        if (CURRENT_BOOKING.equals(content)) {
            return librarybookingMapper.getbooking(userphone);
        }
             return List.of();
    }

    @Override
    public List<LibraryBooking> getAllBooking() {
        return librarybookingMapper.getAllBooking();
    }

    @Override
    public Integer updatebooking(LibraryBooking libraryBooking) {
        return librarybookingMapper.updateBooking(libraryBooking);

    }

    @Override
    public Integer deletebooking(String bookingID) {
        LibraryBooking libraryBooking=librarybookingMapper.getbooking_bookingid(bookingID);
        librarybookingMapper.insertHistoryBooking(libraryBooking);
        return librarybookingMapper.deleteBooking(bookingID);
    }

    @Override
    public Integer addbooking(LibraryBooking libraryBooking) {
        libraryBooking.setDate( LocalDateTime.now().withMinute(0).withSecond(0).withNano(0));
        return librarybookingMapper.addBooking(libraryBooking);
    }

    @Override
    public List<ReserveTimeDTO> getMergedReserveTimes(String seat) {
        // 核心：获取当前时间，并把分钟、秒、纳秒全部置为 00 → 2026-03-23 19:00
        LocalDateTime nowTime = LocalDateTime.now().withMinute(0).withSecond(0).withNano(0);
        List<ReserveTimeDTO> timeList =librarybookingMapper.getBookingTime(seat,nowTime);
        ContinuousTimes continuousTimes = new ContinuousTimes();
        return continuousTimes.mergeContinuousTimes(timeList);
    }
    /**
     * 通用删除+归档方法，支持自定义状态
     * @param bookingID 预约ID
     * @param status 目标状态（已取消/已结束）
     */
    @Transactional(rollbackFor = Exception.class)
    public void autoDeleteAndArchive(String bookingID, String status) {
        LibraryBooking booking = librarybookingMapper.getbooking_bookingid(bookingID);
        if (booking == null) return;

        // 设置自定义状态
        booking.setSeat_status(status);
        // 插入历史表
        librarybookingMapper.insertHistoryBookingWithStatus(booking);
        // 删除原表
        librarybookingMapper.deleteBooking(bookingID);
    }
    // ====================== 新增：定时任务1：超时10分钟未签到自动取消 ======================
    /**
     * 每1分钟执行一次：检查所有超时未签到的预约，自动取消并归档
     */
    @Scheduled(fixedRate = 600000) // 60000毫秒=1分钟，可根据需求调整
    @Transactional(rollbackFor = Exception.class)
    public void autoCancelTimeoutUncheckIn() {
        // 查询所有超时未签到的预约ID
        List<String> timeoutBookingIds = librarybookingMapper.getTimeoutUncheckBookings();
        if (timeoutBookingIds.isEmpty()) return;

        // 逐个处理：删除+归档（状态=已取消）
        for (String bookingId : timeoutBookingIds) {
            autoDeleteAndArchive(bookingId, "未入座，已取消！");
        }
    }

    // ====================== 新增：定时任务2：预约结束自动归档 ======================
    /**
     * 每1分钟执行一次：检查所有已结束的预约，自动归档
     */
    @Scheduled(fixedRate = 600000)
    @Transactional(rollbackFor = Exception.class)
    public void autoArchiveEndedBooking() {
        // 查询所有已结束的预约ID
        List<String> endedBookingIds = librarybookingMapper.getEndTimeBookings();
        if (endedBookingIds.isEmpty()) return;

        // 逐个处理：删除+归档（状态=已结束）
        for (String bookingId : endedBookingIds) {
            autoDeleteAndArchive(bookingId, "已结束");
        }
    }


}
