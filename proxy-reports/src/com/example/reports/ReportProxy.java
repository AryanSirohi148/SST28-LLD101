package com.example.reports;

public class ReportProxy implements Report {

    private final String reportId;
    private final String title;
    private final String classification;
    private final AccessControl accessControl = new AccessControl();

    // idhar cached real subject hai — null until first authorized access
    private RealReport cachedReport;

    public ReportProxy(String reportId, String title, String classification) {
        this.reportId = reportId;
        this.title = title;
        this.classification = classification;
    }

    @Override
    public void display(User user) {
        // 1. Access check
        if (!accessControl.canAccess(user, classification)) {
            System.out.println("[ACCESS DENIED] " + user.getName()
                    + " (" + user.getRole() + ") cannot view " + classification + " report: " + title);
            return;
        }

        // 2. Lazy load + cache
        if (cachedReport == null) {
            cachedReport = new RealReport(reportId, title, classification);
        }

        // 3. Delegate
        cachedReport.display(user);
    }
}
