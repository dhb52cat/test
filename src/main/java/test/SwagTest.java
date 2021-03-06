package test;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.Page;
import dao.ClassifiedAnnouncementMapper;
import dao.TestMapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import utils.HttpUtil2;
import vo.AnnouncePageParam;
import vo.ClassifiedAnnouncements;
import vo.Notice;
import vo.Daily;

import java.util.*;

@Controller
@RequestMapping("test")
@Api(value="/finance/payment/apply", tags={"财务-付款申请管理"})
public class SwagTest {

    @Autowired
    private TestMapper testMapper;
    @Autowired
    private ClassifiedAnnouncementMapper classifiedAnnouncementMapper;


    @ApiOperation(value = "删除用户信息", notes = "删除用户", httpMethod = "DELETE",
            produces = MediaType.APPLICATION_JSON_VALUE)
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
            System.out.println(responseContext);
            Daily daily=JSON.parseObject(responseContext, Daily.class);
            String[] dailyItems=StringUtils.split(daily.getData().getItems(),',');
            List<String> list=JSON.parseArray(daily.getData().getItems(),String.class);
            List<String> list1=JSON.parseArray(list.get(0),String.class);
            System.out.println(daily);
        }
        return null;
    }


    @ApiOperation(value = "添加用户信息", notes = "添加用户", httpMethod = "GET",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @RequestMapping("/add1")
    @ResponseBody
    public Object add() {
        return "s";
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
        String url="http://www.cninfo.com.cn/new/disclosure?column=szse_latest&pageSize=50&pageNum=";
        List <ClassifiedAnnouncements> insertList =new ArrayList<>();
        while(hasMore){
            String responseContext = HttpUtil2.post(JSON.toJSONString(param),url+page);
            Notice notice=JSON.parseObject(responseContext, Notice.class);
            hasMore=notice.getHasMore();
            List<List<ClassifiedAnnouncements>> classifiedAnnouncementsList=notice.getClassifiedAnnouncements();
            for(List<ClassifiedAnnouncements> list:classifiedAnnouncementsList){
                insertList.addAll(list);
            }
            System.out.println(notice);
            page++;
        }
        for(ClassifiedAnnouncements s:insertList){
             s.setRealTime(new Date(s.getAnnouncementTime()));
        }
        classifiedAnnouncementMapper.insertAnnouncement(insertList);
        return null;
    }
    @ResponseBody
    @RequestMapping("/noticeList")
    public Object getNoticeList(AnnouncePageParam param){
        Page page=classifiedAnnouncementMapper.selectAnnouncePage(param);
        return null;
    }

}
