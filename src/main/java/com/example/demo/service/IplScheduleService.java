package com.example.demo.service;

import com.example.demo.dto.ipl.Schedule;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
@Component
public class IplScheduleService {

    public void uploadSchedule(InputStream in) throws Exception {
        BufferedReader bf = new BufferedReader(new InputStreamReader(in));
        List<Schedule> scheduleList = new ArrayList<>();
        String line = null;
        while ((line = bf.readLine()) != null) {
            scheduleList.add(addSchedule(line));
        }
    }

    public Schedule addSchedule(String line) {
        Schedule schedule = new Schedule();
        schedule.setId(1l);
        schedule.setTeam1("1");
        return schedule;
    }
}
