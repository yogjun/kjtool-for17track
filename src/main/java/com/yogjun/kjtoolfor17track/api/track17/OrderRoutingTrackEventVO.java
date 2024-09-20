package com.yogjun.kjtoolfor17track.api.track17;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderRoutingTrackEventVO implements Serializable {
  private String time;
  private String location;
  private String content;
}
