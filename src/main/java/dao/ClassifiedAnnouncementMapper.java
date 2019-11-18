package dao;

import vo.ClassifiedAnnouncements;

import java.util.List;

public interface ClassifiedAnnouncementMapper {
    void insertAnnouncement(List<ClassifiedAnnouncements> insertList);
}
