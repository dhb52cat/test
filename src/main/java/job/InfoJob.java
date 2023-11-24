package job;

import com.alibaba.fastjson.JSON;
import dao.ClassifiedAnnouncementMapper;
import dao.TuShareMapper;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import utils.HttpUtil2;
import vo.ClassifiedAnnouncements;
import vo.JsonRootBean;
import vo.Notice;

import java.util.*;


@Component("taskJob")
public class InfoJob {

    private Logger LOG= LoggerFactory.getLogger(InfoJob.class);
    @Autowired
    private ClassifiedAnnouncementMapper classifiedAnnouncementMapper;
    @Autowired
    private TuShareMapper tuShareMapper;

    private String url="http://api.waditu.com/";
    private String token="55ae1dd509eb9c79b879b664842eadc1931a88575bafb84892223e4f";
    private HashMap<String,Object> param=new HashMap<>();

    {
        param.put("token",token);
    }

    @Scheduled(cron = "0 0 7,10,11,12,13,14,15,20,23 * * ? ")
    public void c() {
        Map param = new HashMap<String, String>();
        param.put("column","szse_main_latest");
        param.put("pageNum","2");
        param.put("pageSize","50");

        int page=1;
        boolean hasMore=true;
        String url="http://www.cninfo.com.cn/new/disclosure?column=szse_latest&pageSize=20&pageNum=";
        List<ClassifiedAnnouncements> insertList =new ArrayList<>();
        while(hasMore){
            String responseContext = HttpUtil2.post(JSON.toJSONString(param),url+page);
            Notice notice=JSON.parseObject(responseContext, Notice.class);
            hasMore=notice.getHasMore();
            List<List<ClassifiedAnnouncements>> classifiedAnnouncementsList=notice.getClassifiedAnnouncements();
            for(List<ClassifiedAnnouncements> list:classifiedAnnouncementsList){
                insertList.addAll(list);
            }
            page++;
        }
        for(ClassifiedAnnouncements s:insertList){
            s.setRealTime(new Date(s.getAnnouncementTime()));
        }
        LOG.info("notice insert :{}",insertList.size());

        int count=classifiedAnnouncementMapper.insertAnnouncement(insertList);
    }

//    @Scheduled(cron = "0 0 0 * * ? ")
   // @Scheduled(cron = "0 0/1 * * * ?")
    //0 0/1 * * * ?
    private void stockListJob(){

        Map<String,String> paramMap = new HashMap();
        paramMap.put("list_stauts","L");
        param.put("api_name","stock_basic");
        param.put("params",paramMap );
        param.put("fields","ts_code,symbol,name,area,industry,fullname,enname,market,exchange,curr_type,list_status,list_date,delist_date,is_hs");
        JsonRootBean data =getData(param);
        for (List<String> l:data.getData().getItems()){
//            l.set(6,"'"+StringUtils.replace(StringUtils.removeEnd(StringUtils.removeStart(l.get(6),"'"),"'")
//                    ,"'","''")+"'");
            l.set(6,StringUtils.replace(l.get(6),"'","''"));
            if(StringUtils.contains(l.get(5),"浙江英特集团股份有限公司")){
                System.out.println(1);
            };
        }
        tuShareMapper.insert(data);
    }

    private JsonRootBean getData(HashMap<String,Object> param){
        String req=JSON.toJSONString(param);
        String responseContext = HttpUtil2.post(req,url);
        JsonRootBean data=JSON.parseObject(responseContext, JsonRootBean.class);
        data.setApi((String)param.get("api_name"));
        data.setFields((String)param.get("fields"));
        return data;
    }

    public static void main(String[] args) {

    }

}
