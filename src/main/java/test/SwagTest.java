package test;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.ISelect;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import dao.ClassifiedAnnouncementMapper;
import dao.TestMapper;
import dao.TuShareMapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import service.AnnounceService;
import utils.HttpUtil2;
import vo.*;

import java.util.*;

@Controller
@RequestMapping("test")
@Api(value="/finance/payment/apply", tags={"财务-付款申请管理"})
public class SwagTest {

    @Autowired
    private TestMapper testMapper;
    @Autowired
    private ClassifiedAnnouncementMapper classifiedAnnouncementMapper;
    @Autowired
    private AnnounceService announceService;
    @Autowired
    private TuShareMapper tuShareMapper;

    @RequestMapping("/test")
    @ResponseBody
    public Object test() {
        Map param = new HashMap<String, String>();
        Map<String,String> paramMap = new HashMap();
        paramMap.put("trade_date","20180717");
        
        param.put("api_name","daily");
        param.put("token","55ae1dd509eb9c79b879b664842eadc1931a88575bafb84892223e4f");
        param.put("params",paramMap );
        param.put("fields"," ts_code,trade_date,open,high,low,close,pre_close,change,pct_chg" +
                ",vol,amount");
        String url="http://api.waditu.com/";
        if(StringUtils.isNotBlank(url)){
            String req=JSON.toJSONString(param);
            String responseContext = HttpUtil2.post(req,url);
            Daily daily=JSON.parseObject(responseContext, Daily.class);
//            String[] dailyItems=StringUtils.split(daily.getData().getItems(),',');
//            List<String> list=JSON.parseArray(daily.getData().getItems(),String.class);
//            List<String> list1=JSON.parseArray(list.get(0),String.class);
        }
        return null;
    }

    @RequestMapping("chart")
    public Object chart(){
        return  new ModelAndView("/gram");
    }
    @RequestMapping("/notice")
    @ResponseBody
    public Object notice() {
        Map param = new HashMap<String, String>();

        param.put("column","szse_main_latest");
        param.put("pageNum","2");
        param.put("pageSize","50");
        int page=1;
        boolean hasMore=true;
        String url="http://www.cninfo.com.cn/new/disclosure?column=szse_latest&pageSize=20&pageNum=";
        List <ClassifiedAnnouncements> insertList =new ArrayList<>();
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
        int count=announceService.insertAnnouncement(insertList);
        return "success1,"+new Date()+",updated:"+count;
    }
    @ResponseBody
    @RequestMapping("/noticeList")
    public Object getNoticeList(final AnnouncePageParam param){
        param.setPageNum(1);
        param.setPageSize(10);
        PageInfo<ClassifiedAnnouncements> page= PageHelper.startPage(param.getPageNum(),param.getPageSize(),true)
                .doSelectPageInfo(new ISelect() {
                    @Override
                    public void doSelect() {
                        classifiedAnnouncementMapper.selectAnnouncePage(param);
                    }
                });
        return page;
    }
    @ResponseBody
    @RequestMapping("/tuShare")
    public Object tuShareTest(String name,String param1){
        try {


        Map param = new HashMap<String, String>();
        Map<String,String> paramMap = new HashMap();
        paramMap.put("list_stauts","L");
        param.put("api_name","stock_basic");
        param.put("token","55ae1dd509eb9c79b879b664842eadc1931a88575bafb84892223e4f");
        param.put("params",paramMap );
        param.put("fields","ts_code,name,area,industry,list_date");
        String url="http://api.waditu.com/";
        if(StringUtils.isNotBlank(url)){
            String req=JSON.toJSONString(param);
            String responseContext = HttpUtil2.post(req,url);
            JsonRootBean data=JSON.parseObject(responseContext, JsonRootBean.class);
            data.setApi("stock_basic");
            data.setFields("ts_code,name,area,industry,list_date");
            tuShareMapper.insert(data);
            System.out.println(1);
        }}catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

}
