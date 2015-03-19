package me.webstar.akka.messages;

import java.util.Map;

/**
 * @author sasajib
 */
public final class ReduceData
{
  private final Map<String, Integer> reducedDataList;

  public ReduceData(Map<String, Integer> reducedDataList)
  {
    this.reducedDataList = reducedDataList;
  }

  public Map<String, Integer> getReducedDataList()
  {
    return reducedDataList;
  }
}
