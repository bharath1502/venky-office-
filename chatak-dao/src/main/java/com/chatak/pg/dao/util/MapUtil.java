package com.chatak.pg.dao.util;


import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.chatak.pg.model.FeatureDTO;

public class MapUtil
{
 
  MapUtil() {
    super();
  }

  public static Map<Long, List<FeatureDTO>> mySortedMap(final Map<Long, List<FeatureDTO>> orig)
  {
      final Comparator<Long> c = ((final Long o1, final Long o2) ->
      {
        // Compare the size of the lists. If they are the same, compare
        // the keys themsevles.
        final int sizeCompare = orig.get(o2).size() - orig.get(o1).size();
        return sizeCompare != 0 ? sizeCompare : o1.compareTo(o2);
      });

      final Map<Long, List<FeatureDTO>> ret = new TreeMap<Long, List<FeatureDTO>>(c);
      ret.putAll(orig);
      return ret;
  }

}
