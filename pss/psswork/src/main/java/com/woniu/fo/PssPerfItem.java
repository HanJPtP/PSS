package com.woniu.fo;
import com.woniu.pojo.PssQuota;
import com.woniu.pojo.PssStaffs;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@AllArgsConstructor
@NoArgsConstructor
@Data
public class PssPerfItem {
    List<PssQuota> quotaItem;
    PssStaffs staffs;
    String appname;
}
