package org.example.webaimanagement.utils;
import org.example.webaimanagement.pojo.ReserveTimeDTO;

import java.time.LocalDateTime;
import java.util.*;
public class ContinuousTimes{


    public List<ReserveTimeDTO> mergeContinuousTimes(List<ReserveTimeDTO> list) {
        if (list == null || list.isEmpty()) {
            return new ArrayList<>();
        }

        // 排序
        list.sort(Comparator.comparing(ReserveTimeDTO::getStart_time));

        List<ReserveTimeDTO> result = new ArrayList<>();
        ReserveTimeDTO current = list.get(0);

        for (int i = 1; i < list.size(); i++) {
            ReserveTimeDTO next = list.get(i);

            // 如果当前结束时间 == 下一个开始时间 → 连续，合并
            if (current.getEnd_time().equals(next.getStart_time())) {
                current.setEnd_time(next.getEnd_time());
            } else {
                // 不连续，加入结果
                result.add(current);
                current = next;
            }
        }

        // 加入最后一个
        result.add(current);
        return result;
    }
}
