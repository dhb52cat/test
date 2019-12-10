package service;

import com.github.pagehelper.ISelect;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import dao.ClassifiedAnnouncementMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vo.AnnouncePageParam;
@Service
public class AnnounceService {
    @Autowired
    private ClassifiedAnnouncementMapper classifiedAnnouncementMapper;
    Page selectAnnouncePage(AnnouncePageParam param){
        classifiedAnnouncementMapper.selectAnnouncePage(param);
        return null;
    }
}
