package com.vishvendra.journeylens.analytics.processor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AnalyticsProcessorRegistry {

  public final static Map<String, AnalyticProcessor> commandRegistry = new HashMap<>();

  public final static Map<String, List<AnalyticProcessor>> categoryRegistry = new HashMap<>();

  protected static void registerCommandWithCategory(String categoryName,
      AnalyticProcessor command) {
    if (categoryRegistry.containsKey(categoryName)) {
      categoryRegistry.get(categoryName).add(command);
    } else {
      List<AnalyticProcessor> commands = new ArrayList<>();
      commands.add(command);
      categoryRegistry.put(categoryName, commands);
    }
  }

  protected static void registerCommand(String commandName, AnalyticProcessor command) {
    commandRegistry.put(commandName, command);
  }

  public static List<AnalyticProcessor> getCommandListByCategory(String categoryName) {
    return categoryRegistry.getOrDefault(categoryName,
        List.of(new DefaultAnalyticsProcessor("", categoryName)));
  }

  public static AnalyticProcessor getCommandByCommandName(String commandName) {
    return commandRegistry.getOrDefault(commandName,
        new DefaultAnalyticsProcessor(commandName, ""));
  }

}
