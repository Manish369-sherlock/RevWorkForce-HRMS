package org.example.workforce.dto;

import lombok.*;
import java.util.List;
import java.util.Map;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class LeaveAnalysisResponse {
    private String employeeName;
    private String department;
    private String designation;
    private int totalLeavesTakenThisYear;
    private int totalLeavesTakenLastYear;
    private Map<String, Integer> leavesByType;          // e.g. {"Sick Leave": 5, "Casual Leave": 3}
    private Map<String, Integer> leavesByMonth;          // e.g. {"January": 2, "March": 1}
    private int pendingLeaveRequests;
    private double averageLeaveDuration;
    private Map<String, Integer> currentBalances;        // e.g. {"Sick Leave": 4, "Casual Leave": 7}
    private List<String> patterns;                       // e.g. ["Takes leave mostly on Mondays/Fridays"]
    private String frequencyTrend;                        // "INCREASING", "STABLE", "DECREASING"
    private int teamMembersOnLeaveToday;
    private int requestedDays;
    private String requestedType;
    private int balanceAfterApproval;
    private String aiSummary;
    private String aiRecommendation;                     // "APPROVE" or "REVIEW_FURTHER"
    private List<String> aiReasons;
}

