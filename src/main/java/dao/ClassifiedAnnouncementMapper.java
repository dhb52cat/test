package dao;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import vo.AnnouncePageParam;
import vo.ClassifiedAnnouncements;

import java.util.List;

public interface ClassifiedAnnouncementMapper {

    int insertAnnouncement(List<ClassifiedAnnouncements> insertList);
    Integer test();
    List<ClassifiedAnnouncements> selectAnnouncePage(AnnouncePageParam param);

    void  truncateTempAnnounce();
    int insertTempAnnouncement(List<ClassifiedAnnouncements> insertList);
}
