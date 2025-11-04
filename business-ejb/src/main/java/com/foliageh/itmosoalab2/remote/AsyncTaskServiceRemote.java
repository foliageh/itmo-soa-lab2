package com.foliageh.itmosoalab2.remote;

import jakarta.ejb.Remote;
import java.util.List;

@Remote
public interface AsyncTaskServiceRemote extends BaseServiceRemote {
    List<Double> getTaskResult();
    boolean isTaskInProgress();
    boolean isTaskCompleted();
    boolean launchUniqueLivingSpacesJob();
    boolean cancelUniqueLivingSpacesJob();
}