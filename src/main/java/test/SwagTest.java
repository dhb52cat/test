package test;

import com.alibaba.fastjson.JSON;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import utils.HttpUtil2;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("test")
//@Api(value="/test1" ,tags = {"测试"},description = "Operations about user")
@Api(value="/finance/payment/apply", tags={"财务-付款申请管理"})
public class SwagTest {

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
        }
        return null;
    }


    @ApiOperation(value = "添加用户信息", notes = "添加用户", httpMethod = "GET",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @RequestMapping("/add")
    @ResponseBody
    public Object add() {
        return "s";
    }

    @RequestMapping("chart")
    public Object chart(){
        return  new ModelAndView("/gram");
    }
}
