package com.kob.backend.consumer.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// 用来表示蛇的身体细胞
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Cell {
    int x,y;
}
