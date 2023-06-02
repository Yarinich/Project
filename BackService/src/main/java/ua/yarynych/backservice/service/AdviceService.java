package ua.yarynych.backservice.service;

import java.util.List;
import java.util.Map;


public interface AdviceService {
    Map<String, List<String>> createRecommendation(String email);
    Map<String, List<String>> refreshRecommendation(String email);
}
