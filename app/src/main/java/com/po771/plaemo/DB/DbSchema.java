package com.po771.plaemo.DB;

public class DbSchema {
    public static final class AlarmTable {
        public static final String NAME = "book_alarm";

        public static final class Cols {
            public static final String ALARMNAME = "alarm_name";
            public static final String BOOKID = "book_id";
            public static final String HOUR = "hour";
            public static final String MINUTE = "minute";
            public static final String REPEAT = "repeat";
            public static final String DAYSOFTHEWEEK = "daysoftheweek";
            public static final String ON = "ison";
            public static final String ALARMTONE = "tone";
            public static final String SNOOZE = "snooze";
            public static final String VIBRATE = "vibrate";
            public static final String AMPM = "ampm";
            public static final String CASTID = "case_id";
        }
    }

    public static final class BookList {
        public static final String NAME = "book_list";

        public static final class Cols {
            public static final String BOOKNAME = "book_name";
            public static final String BOOKURI = "book_uri";
            public static final String CURRNETPAGE = "current_page";
            public static final String TOTALPAGE = "total_page";
            public static final String BOOKINFO = "book_info";
            public static final String STAR = "book_star";
            public static final String FOLDER = "folder";
        }
    }

    public static final class BookMemo {
        public static final String NAME = "book_memo";

        public static final class Cols {
            public static final String BOOKID = "book_id";
            public static final String PAGESTART = "page_start";
            public static final String PAGEEND = "page_end";
            public static final String CONTENT = "content";
            public static final String DATA = "date";
        }
    }

    public static final class BookImage{
        public static final String NAME = "book_image";

        public static final class Cols {
            public static final String IMAGE = "image_data";
        }
    }

    public static final class Folder{
        public static final String NAME="book_folder";
        public static final class Cols {
            public static final String FOLDERNAME = "folder_name";
            public static final String BOOKID = "book_id";
        }
    }
}
