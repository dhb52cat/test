package dao;

import com.github.pagehelper.Page;
import vo.AnnouncePageParam;
import vo.ClassifiedAnnouncements;

import java.util.List;

public interface ClassifiedAnnouncementMapper {

    void insertAnnouncement(List<ClassifiedAnnouncements> insertList);
    Integer test();
    Page selectAnnouncePage(AnnouncePageParam param);
}
