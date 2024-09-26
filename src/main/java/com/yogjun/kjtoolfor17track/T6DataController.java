package com.yogjun.kjtoolfor17track;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.json.JSONUtil;
import com.yogjun.kjtoolfor17track.api.t6data.ListData;
import com.yogjun.kjtoolfor17track.api.t6data.ListDataPak;
import com.yogjun.kjtoolfor17track.api.track17.OrderRoutingTrackEventVO;
import com.yogjun.kjtoolfor17track.api.track17.OrderRoutingTrackVO;
import java.util.HashMap;
import java.util.List;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * {@link T6DataController}
 *
 * @author <a href="mailto:280536928@qq.com">yogjun</a>
 * @version ${project.version} - 2024/9/20
 */
@RequestMapping("/t6")
@Controller
public class T6DataController {

  @RequestMapping("/index")
  public String index(Model model) {
    return "index.html";
  }

  @RequestMapping("/info.html")
  public String html(
      @RequestParam(name = "waybillnumber", required = false) String waybillnumber, Model model) {
    OrderRoutingTrackVO data = query(waybillnumber);
    model.addAttribute("waybillnumber", waybillnumber);
    model.addAttribute("data", data);
    model.addAttribute("events", data.getEvents());
    return "events.html";
  }

  @RequestMapping("/query")
  @ResponseBody
  public OrderRoutingTrackVO query(
      @RequestParam(name = "waybillnumber", defaultValue = "") String waybillnumber) {
    String url = "http://www.jaytrans.com/trackList";
    HashMap<String, Object> map = new HashMap<>();
    map.put("page", 1);
    map.put("limit", 10);
    map.put("searchList.waybillnumber", waybillnumber);
    map.put(
        "searchListField.waybillnumber",
        "border.systemnumber,border.customernumber1,border.waybillnumber,border.tracknumber,border.newtracknumber");
    String body = HttpRequest.post(url).form(map).execute().body();
    ListDataPak data = JSONUtil.toBean(body, ListDataPak.class);
    List<OrderRoutingTrackEventVO> orderRoutingTrackEventVOS = CollUtil.newArrayList();
    OrderRoutingTrackVO orderRoutingTrackVO = new OrderRoutingTrackVO();
    if (CollUtil.isNotEmpty(data.getData())) {
      ListData ld = data.getData().get(0);
      orderRoutingTrackEventVOS = findItem(ld.getPkid(), ld.getWaybillnumber());
      orderRoutingTrackVO.setEvents(orderRoutingTrackEventVOS);
      orderRoutingTrackVO.setDestCountry(ld.getCountrycode());
      orderRoutingTrackVO.setNumber(ld.getWaybillnumber());
      orderRoutingTrackVO.setOriNumber(ld.getCustomernumber1());
      if (StrUtil.equals(ld.getStatus(), "signed")) {
        // 已签收
        orderRoutingTrackVO.setStatus("Delivered");
      }
    }

    return orderRoutingTrackVO;
  }

  private List<OrderRoutingTrackEventVO> findItem(Integer orderpkid, String waybillnumber) {
    String url = "http://www.jaytrans.com/trackItem";
    HashMap<String, Object> map = new HashMap<>();
    map.put("orderpkid", orderpkid);
    map.put("waybillnumber", waybillnumber);
    String body = HttpRequest.post(url).form(map).execute().body();
    // 解析 body
    Document doc = Jsoup.parse(body);
    Elements elements = doc.getElementsByClass("layui-timeline-content");
    List<OrderRoutingTrackEventVO> list = CollUtil.newArrayList();
    for (Element element : elements) {
      OrderRoutingTrackEventVO eventVO = new OrderRoutingTrackEventVO();
      Elements trackdate = element.getElementsByClass("trackdate");
      Elements tracklocation = element.getElementsByClass("tracklocation");
      Elements trackinfo = element.getElementsByClass("trackinfo");
      eventVO.setContent(getValueFromElement(trackinfo));
      eventVO.setLocation(getValueFromElement(tracklocation));
      eventVO.setTime(getValueFromElement(trackdate));
      list.add(eventVO);
    }
    return list;
  }

  private String getValueFromElement(Elements elements) {
    if (CollUtil.isEmpty(elements)) {
      return "";
    }
    return elements.get(0).text();
  }
}
