package test;




//import cn.hittec.risk.common.util.ExceptionUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
//import org.apache.commons.compress.utils.IOUtils;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.URI;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.LogFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
//import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * httpclient请求的工具类
 *
 * @author hongping
 */


public class InterfaceTest {

    private static Logger logger = LoggerFactory.getLogger(InterfaceTest.class);
    /**
     * 连接超时时间30秒
     */
    private static int CONNECT_TIMEOUT = 30000;
    /**
     * 读超时时间10分钟
     */
    private static int READ_TIMEOUT = 600000;
    /**
     * 默认的字符集编码
     */
    private static String DEFAULT_CHARSET = "UTF-8";
    /**
     * 默认的请求方式
     */
    private static String DEFAULT_METHOD = "POST";


    public static String doPostStream(String reqUrl, InputStream inputStream, Map<String, String> headerMap) throws Exception {
        HttpURLConnection httpURLConnection = null;
        String responseContent = null;
        try {
            URL url = new URL(reqUrl);
            httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod(DEFAULT_METHOD);
            httpURLConnection.setConnectTimeout(CONNECT_TIMEOUT);
            httpURLConnection.setChunkedStreamingMode(0);
            httpURLConnection.setReadTimeout(READ_TIMEOUT);

            httpURLConnection.setRequestProperty("User-Agent", "Mozilla/5.0 ( compatible ) ");
//            httpURLConnection.setRequestProperty("Accept", "*/*");
            httpURLConnection.setDoOutput(true);
            if (headerMap != null && headerMap.size() > 0) {
                for (String key : headerMap.keySet()) {
                    httpURLConnection.setRequestProperty(key, headerMap.get(key));
                }
            }

//     TODO       byte[] data = IOUtils.toByteArray(inputStream);
            byte[] data = null;
            httpURLConnection.getOutputStream().write(data, 0, data.length);
            httpURLConnection.getOutputStream().flush();
            httpURLConnection.getOutputStream().close();

            InputStream in = httpURLConnection.getInputStream();
            BufferedReader rd = new BufferedReader(new InputStreamReader(in, DEFAULT_CHARSET));
            String tempLine = rd.readLine();
            StringBuffer tempStr = new StringBuffer();
            String crlf = System.getProperty("line.separator");
            while (tempLine != null) {
                tempStr.append(tempLine);
                tempStr.append(crlf);
                tempLine = rd.readLine();
            }

            responseContent = tempStr.toString();

            rd.close();
            in.close();
        } catch (IOException e) {
            logger.error("请求失败》》》url:{},exception:{}");
        } finally {
            if (httpURLConnection != null) {
                httpURLConnection.disconnect();
            }
        }
        return responseContent;
    }

    public static JSONObject post(String url, NameValuePair[] reqParam) {
        try {
            PostMethod postMethod = new PostMethod(url);
            postMethod.setRequestBody(reqParam);
            postMethod.setRequestHeader("Content-Type", "application/x-www-form-urlencoded;utf-8");
            HttpClient httpClient = new HttpClient();
            httpClient.executeMethod(postMethod);
            String rs = postMethod.getResponseBodyAsString();
            JSONObject jsonObject = JSON.parseObject(rs);
            jsonObject.put("responseHeader", postMethod.getResponseHeaders());
            return jsonObject;
        } catch (Exception e) {
            //logger.error("请求失败》》》url:{},param:{},e:{}", url, JSON.toJSONString(reqParam), ExceptionUtil.getExceptionInfo(e));
        }
        return null;
    }

    public static JSONObject post(String url, String reqParam) {
        try {
            PostMethod postMethod = new PostMethod(url);
            postMethod.setRequestBody(reqParam);
            postMethod.setRequestHeader("Content-Type", "application/json");
            HttpClient httpClient = new HttpClient();
            httpClient.executeMethod(postMethod);
            String rs = postMethod.getResponseBodyAsString();
            JSONObject jsonObject = JSON.parseObject(rs);
            return jsonObject;
        } catch (Exception e) {
           // logger.error("请求失败》》》url:{},param:{},e:{}", url, JSON.toJSONString(reqParam), ExceptionUtil.getExceptionInfo(e));
        }
        return null;
    }

    public static void main(String[] args) throws Exception {
        System.setProperty("org.apache.commons.logging.LogFactory", "org.apache.commons.logging.impl.LogFactoryImpl");
        LogFactory.getFactory().setAttribute("org.apache.commons.logging.Log", "org.apache.commons.logging.impl.SimpleLog");
        LogFactory.getFactory().setAttribute("org.apache.commons.logging.simplelog.defaultlog", "error");
        List<String> datas = Lists.newArrayList("{\"data\":\"IpC3Zr06PygSNeYOM2Rpa3ZQGKO0eNlnSuea1LurakOiNgj+WM9k1DxzFJ8lWt059KqB1RW29zbx7ou7kszxl40SjwAywap6T7gOElv1Hm6itcIcRQec9OaYOsxmy95y+5B8pv3/0shPfuz/fBSJP7Bnimu6lSIq3ibx6eb97es=\",\"name\":\"CRw8CK9YfVAaB/17mX1N8dQwj2nN3VU8\"}"
                , "{\"data\":\"IpC3Zr06PygSNeYOM2Rpa3ZQGKO0eNlnSuea1LurakOiNgj+WM9k1DxzFJ8lWt059KqB1RW29zaKMFFEf+DA/sQITHG7uxOkmAgiMKTp5RBFX2b8KTI+dg5VIRyPpuhj+5B8pv3/0shPfuz/fBSJP7Bnimu6lSIq3ibx6eb97es=\",\"name\":\"CRw8CK9YfVAaB/17mX1N8dQwj2nN3VU8\"}",
                "{\"data\":\"IpC3Zr06PygSNeYOM2Rpa3ZQGKO0eNlnSuea1LurakOiNgj+WM9k1DxzFJ8lWt059KqB1RW29zbX5HJ0Azw63nBy6ND/EThUBUxBrby8CdPF/RMIOswDeOrzXHuLJWb6+5B8pv3/0shPfuz/fBSJP7Bnimu6lSIq3ibx6eb97es=\",\"name\":\"CRw8CK9YfVAaB/17mX1N8dQwj2nN3VU8\"}",
                "{\"data\":\"IpC3Zr06PygSNeYOM2Rpa3ZQGKO0eNlnSuea1LurakOiNgj+WM9k1DxzFJ8lWt059KqB1RW29zZb/EC6QlkUZm219b9GoKZcg1NmR974yo7aAI1QMJfzky+Dscgwo4s0+5B8pv3/0shPfuz/fBSJP7Bnimu6lSIq3ibx6eb97es=\",\"name\":\"CRw8CK9YfVAaB/17mX1N8dQwj2nN3VU8\"}",
                "{\"data\":\"IpC3Zr06PygSNeYOM2Rpa3ZQGKO0eNlnSuea1LurakOiNgj+WM9k1DxzFJ8lWt059KqB1RW29zaIoVwxN8W/Ar/Eoow3JiSX3CjTGfJVGlTJimMN0zasxMq7Tf6J+hiU+5B8pv3/0shPfuz/fBSJP7Bnimu6lSIq3ibx6eb97es=\",\"name\":\"CRw8CK9YfVAaB/17mX1N8dQwj2nN3VU8\"}");
        String checkCity = "{\"data\":\"yye3Jbph7JqYkjnab3t5biDgqn20XyWlFuguaNXS5bTZS9wsBJq6kefx+1vnKKMSUTTdrXkY8ivOWiij2OGWVBnLJge083Y85ebXgErdVPmJO3pZNqBwIxRf7sGffNIr\",\"name\":\"YSvIGP7eWEPRKh1SEgsIgghUdXpgpINl\"}";
        String checkCityList = "{\"data\":\"IpC3Zr06PygSNeYOM2Rpa3ZQGKO0eNlnSuea1LurakOiNgj+WM9k1MYgxIMTCcxoQKU2DD4wXwabqKbaDb01UQs+8h7XJmmnePAbHoHHRurCYZntalXYMdOC3RIczX33C+fmClAeYbmGtkOur2Ut32xw7Jl3uFJuJhVtFkAzhh3EP2Ppq718xuMgpvx0tdaXXfi76iXofg9DWLMlaVZkHjXYUWdUmpwthtRIhO0dERJCcNaX6ZWUBFvYteQy3rcET9LHEN0vposqnGSXDC92w7IlckU6m3tbe2H8UeWvkXxdflrBcbAHjc2+eXMNOzW0BBhGB+ggcQUg4Kp9tF8lpeeN2eJz/tNbIOCqfbRfJaUCMNDoFYw7MAQYRgfoIHEF94IOq8ODZ63HD+SaSyhOQyDgqn20XyWlXpKtNu2RCfog4Kp9tF8lpd7Im7oi1KcD+yd7u3CXlaMg4Kp9tF8lpWXFMdwQL2iMIOCqfbRfJaWoY7AnLJlmciDgqn20XyWlCbL20h0m4hIgs2ATtaH3rSDgqn20XyWluzp+dlWuSCMg4Kp9tF8lpaYrvnKJKIOPBBhGB+ggcQX3gg6rw4Nnrc4g9CyT8NkOIOCqfbRfJaVvQg8M+H/8tCDgqn20XyWldvrUM74ILND7J3u7cJeVoyDgqn20XyWldOAptJ3MLoYg4Kp9tF8lpe7Qbfp5gwI2IOCqfbRfJaUvzKnh1RbxXi4Gsx3508n4IOCqfbRfJaXeNYZEPl+JnyDgqn20XyWlM4Y0AWYMSzlRVut4XdMXbveCDqvDg2ety8vp2QnldhMg4Kp9tF8lpeWqiyU2P0ebIOCqfbRfJaXRWpiXG/wlyUyB3GOgsEpiIOCqfbRfJaVSnCAWjPT83yDgqn20XyWlh3QOivp0z/wg4Kp9tF8lpS/MqeHVFvFesd4wtNvlP+wg4Kp9tF8lpfxMH1ZFd6zjIOCqfbRfJaXfiVTRh0LKgAQYRgfoIHEF94IOq8ODZ61krw/dyJJylCDgqn20XyWlzSVnhOIZ0fwg4Kp9tF8lpTejKJ9Xu6XRTIHcY6CwSmIg4Kp9tF8lpYaz9C9mnC+BIOCqfbRfJaUKFoI1gB85ciDgqn20XyWluZqrOn7IvdIKtAGaN2loACDgqn20XyWlVkyRW6LIEtMg4Kp9tF8lpSyrvoLmjhS7BBhGB+ggcQX3gg6rw4NnraG9yO6g75J+IOCqfbRfJaUUWUSz3/5MEiDgqn20XyWlGI9HUtOwkRJMgdxjoLBKYiDgqn20XyWleXrrX46z2QUg4Kp9tF8lpdbOB3dj2JVQIOCqfbRfJaW5mqs6fsi90p3Tvk4SmTpRIOCqfbRfJaVegCmYsEl+KiDgqn20XyWlfG8V0kfrBocEGEYH6CBxBfeCDqvDg2etrwRTjlvauwgg4Kp9tF8lpXaFluBLch9EIOCqfbRfJaXKV8/BWmCTPfsne7twl5WjIOCqfbRfJaWXZKrzIItM7iDgqn20XyWlfeWSbbQmJI8g4Kp9tF8lpXz7gobmqdpkjS5LrF59Q4Mg4Kp9tF8lpQ2Ku6zi8sEuIOCqfbRfJaUtEd4kaxhjGk4FfnCjn7QeUSipVRSq35jUMI9pzd1VPA==\",\"name\":\"83C9v94Gsf9f39/7rRXnv0RXChU2u9ye\"}";
       // MailUtil.sendMail("dhb52cat@163.com", "job 开始", "这是一封测试邮件！");
        while (true) {
            try {
               /* if (1 == 1) {
                    JSONObject k2 = post("https://event.chinapmp.cn/PMP/restservices/web/singleFunction/query",
                            checkCity);
                    k2 = JSON.parseObject(k2.toString());
                    JSONObject res = k2.getJSONObject("result");
                    String city = res.getString("resultdesc");
                    if (!StringUtils.equals(city, "该报考城市其余考点已无剩余座位")) {
                        //send mail
                        System.out.println("send mail+++++++++++++++++");
                        try {
                            MailUtil.sendMail("dhb52cat@163.com", "可以报名了！", "该报考城市其余考点已无剩余座位！");
                        } catch (MessagingException e) {
                            e.printStackTrace();
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }
                    }
                }*/
                /* try {
                    System.out.println("城市间休眠1s开始");
                    Thread.sleep(1500);
                    System.out.println("城市间休眠1s结束");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
               if (1 == 1) {
                    JSONObject k2 = post("https://event.chinapmp.cn/PMP/restservices/web/singleFunction/query",
                            checkCityList);
                    k2 = JSON.parseObject(k2.toString());
                    JSONObject res = k2.getJSONObject("result");
                    String city = res.getString("resultdesc");
                    JSONObject innerRes = res.getJSONObject("result");
                    JSONArray cityList = innerRes.getJSONArray("citylist");
                    cityList.forEach(x -> {
                        JSONObject cityObj = (JSONObject) x;
                        String cityName = cityObj.getString("showname");
                        if (StringUtils.equals(cityName, "北京市") && cityObj.getJSONArray("examsitelist").size() > 5) {
                            System.out.println(cityObj.getJSONArray("examsitelist").size());
                            //send mail
                            System.out.println("send mail+++++++++++++++++");
                            try {
                                MailUtil.sendMail("dhb52cat@163.com", "可以报名了！", "examsitelist>5！");
                            } catch (MessagingException e) {
                                e.printStackTrace();
                            } catch (UnsupportedEncodingException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                }*/
                try {
                    System.out.println("城市间休眠1s开始");
                    Thread.sleep(1500);
                    System.out.println("城市间休眠1s结束");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                //k2
                /*datas.forEach(data -> {
                    try {
                        System.out.println("开始请求数据");
                        JSONObject k2 = post("https://event.chinapmp.cn/PMP/restservices/web/singleFunction/query",
                                data);
                        try {
                            System.out.println("城市间休眠1s开始");
                            Thread.sleep(1500);
                            System.out.println("城市间休眠1s结束");
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        k2 = JSON.parseObject(k2.toString());
                        JSONObject res = k2.getJSONObject("result");
                        JSONObject innerRes = res.getJSONObject("result");
                        String sitename = innerRes.getString("sitename");
                        System.out.println(LocalDateTime.now() + "===" + sitename + ":" + res.getString("resultdesc"));
                       *//* if (!StringUtils.equals(res.getString("resultdesc"), "您选择的考点已报满，请选择其他考点。")) {
                            //send mail
                            System.out.println("send mail+++++++++++++++++");
                            try {
                               // MailUtil.sendMail("dhb52cat@163.com", "可以报名了！" + sitename, sitename);
                            } catch (MessagingException e) {
                                e.printStackTrace();
                            } catch (UnsupportedEncodingException e) {
                                e.printStackTrace();
                            }
                        }*//*
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });*/
                System.out.println("【组间】休眠开始");
                Thread.sleep(3000);
                System.out.println("【组间】休眠结束");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static JSONObject get(String url) {
        try {
            GetMethod getMethod = new GetMethod();
            getMethod.setURI(new URI(url));
            HttpClient httpClient = new HttpClient();
            httpClient.executeMethod(getMethod);
            String responseBody = getMethod.getResponseBodyAsString();
            JSONObject jsonObject = JSONObject.parseObject(responseBody);
            return jsonObject;
        } catch (Exception e) {
            //logger.error("请求失败》》》url:{},e:{}", url, ExceptionUtil.getExceptionInfo(e));
        }
        return null;
    }
}
