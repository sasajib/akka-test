package me.webstar.akka.messages;

import java.util.List;

/**
 * @author sasajib
 */
public final class MapData
{
  private final List<WordCount> dataList;

  public MapData(List<WordCount> list)
  {
    this.dataList = list;
  }

  public List<WordCount> getDataList()
  {
    return dataList;
  }
}
