package com.yogjun.kjtoolfor17track.api.track17;

import cn.hutool.core.collection.CollUtil;
import java.io.Serializable;
import java.util.List;
import lombok.Data;

@Data
public class OrderRoutingTrackVO implements Serializable {
  private String number;
  private String oriChannel;
  private String oriNumber;
  private String oriCountry;
  private String destCountry;
  private String status;
  private List<OrderRoutingTrackEventVO> events = CollUtil.newArrayList();
}
