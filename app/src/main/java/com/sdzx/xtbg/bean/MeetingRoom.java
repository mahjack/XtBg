package com.sdzx.xtbg.bean;

import java.util.List;

/**
 * Author：Mark
 * Date：2016/5/13 0013
 * Tell：15006330640
 */
public class MeetingRoom {
    private List<Room> meetinfo;

    public List<Room> getMeetinfo() {
        return meetinfo;
    }

    public void setMeetinfo(List<Room> meetinfo) {
        this.meetinfo = meetinfo;
    }

    public class Room{
        private String id;
        private String name;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
